/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ek.webcrawle;
import java.io.*;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 *
 * @author EN
 */
public class ExportExcel {
    DefaultTableModel model = null;
    public void writeXLSXFile(String path, javax.swing.JTable tabela) throws IOException {
		model = (DefaultTableModel ) tabela.getModel();
		String excelFileName = path + ".xlsx";//name of excel file

		String sheetName = "WebCrawel";//name of sheet

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName) ;

		XSSFRow rowHead = sheet.createRow(0);
                for (int c=0;c < model.getColumnCount(); c++ )
			{
                                
				XSSFCell cell = rowHead.createCell(c);
	
				cell.setCellValue(model.getColumnName(c));
			}
		for (int r=0;r < model.getRowCount(); r++ )
		{
			XSSFRow row = sheet.createRow(r+1);
			//iterating c number of columns
			for (int c=0;c < model.getColumnCount(); c++ )
			{   
                            XSSFCell cell = row.createCell(c);
                            cell.setCellValue(model.getValueAt(r, c).toString());
                            sheet.autoSizeColumn(c);
			}
		}

		FileOutputStream fileOut = new FileOutputStream(excelFileName);
		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}
}
