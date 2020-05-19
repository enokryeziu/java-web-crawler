/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ek.webcrawle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.validator.UrlValidator;
import org.jsoup.Connection;

public class WebCrawler {
    private static int MAX_DEPTH;
    private HashSet<String> links;
    private int count = 1,deCount=0;
    private int inLinkCount = 0, outLinkCount = 0;
    public static boolean isCancelledSW = false;
    private String inURL = null;

    public WebCrawler(int MAX_DEPTH) {
        this.MAX_DEPTH = MAX_DEPTH; 
        links = new HashSet<>();
    }
    DefaultTableModel model = null;
    String[] schemes = {"http","https"};
    UrlValidator urlValidator = new UrlValidator(schemes);
    public void getPageLinks(String URL, String keyword, int depth, javax.swing.JTable tabela, javax.swing.JLabel label, javax.swing.JLabel label2) throws IOException {
        if (!isCancelledSW) {
            if(deCount == 0){
                model = (DefaultTableModel ) tabela.getModel();
                URL url = new URL(URL);
                inURL = url.getHost();
                deCount++;
            }
            if ((!links.contains(URL) && (depth < MAX_DEPTH)) && urlValidator.isValid(URL)) {
                if(Jsoup.connect(URL).userAgent("Mozilla").get().title().toLowerCase().contains(keyword.toLowerCase())){
                    model.addRow(new Object[]{count++,Jsoup.connect(URL).get().title(),URL,depth});
                    if(URL.contains(inURL)){
                        label.setText(Integer.toString(++inLinkCount));
                    }else{
                        label2.setText(Integer.toString(++outLinkCount));
                    }
                }
                try {
                    links.add(URL);
                    
                    Document document = Jsoup.connect(URL).userAgent("Mozilla").get();
                    Elements linksOnPage = document.select("a[href]");

                    depth++;
                    for (Element page : linksOnPage) {
                        if (depth >= MAX_DEPTH) {
                            break;
                        }
                        getPageLinks(page.attr("abs:href"),keyword , depth,tabela,label,label2);
                    }
                } catch (IOException e) {
                    System.err.println("For '" + URL + "': " + e.getMessage());
                }
            }
        }else {
            return;
        }
    }
/*
    public static void main(String[] args) {
        new WebCrawler().getPageLinks("http://www.telegrafi.com/", 0);
    }
*/
}