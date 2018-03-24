package AnLex;

import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JTextArea;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileBrowser {
	
	private XSSFWorkbook wb;
	private XSSFSheet sheet;
	private int rc = 2;
	private int err = 0;
	private JTextArea ta_log;
	
	public FileBrowser(String exlpath, String htmlpath, String keyword, JTextArea ta_log) throws Exception{
		this.ta_log = ta_log;
		createExcel();
		setData(htmlpath, keyword);
		closeFile(exlpath);
		//getFile(path, keyword);
	}
	
	public static void main(String[] args) throws Exception{
		/*FileBrowser fb = new FileBrowser("C://Users//Osmosys 2//Desktop//Reporte.xlsx", 
				"C://Users//Osmosys 2//Desktop//facebook-susanacuevas395454//messages", "Hogares Unión");
		*/
	}
	
	
	private void createExcel() throws Exception{
		wb = new XSSFWorkbook();
	    sheet = (XSSFSheet) wb.createSheet("new sheet");
	    
	    Row row = sheet.createRow(rc);/*
	    XSSFFont font = sheet.getWorkbook().createFont();
	    font.setFontName("Arial");
	    font.setBoldweight(Font.BOLDWEIGHT_BOLD);
	    */
	    row.createCell(0).setCellValue("COUNT");
	    row.createCell(1).setCellValue("REMITENTE");
	    row.createCell(2).setCellValue("DESTINATARIO");
	    row.createCell(3).setCellValue("FECHA");
	    row.createCell(4).setCellValue("MENSAJE");
	    row.createCell(5).setCellValue("RESPUESTA");
	    
	}
	
	private void setData(String htmlpath, String keyword) {
		File dir = new File(htmlpath);
		String[] list = dir.list();
		
		
		int count = 1;
		
		for(int i = 0; i < list.length; i++)
			if(list[i].contains(".html")){
				// System.out.println(list[i]);
				ta_log.append("\n#~ " + list[i]);
				
				try{
					HTMLCleaner cln = new HTMLCleaner(htmlpath + "//" + list[i]);
					String[] data = cln.search(keyword);
					
					if(data != null){
						Row row = sheet.createRow(++rc);
						
						row.createCell(0).setCellValue(count++);	// Count
					    row.createCell(1).setCellValue(data[0]);	// Remitente
					    row.createCell(2).setCellValue(data[3]);	// Destinatario
					    row.createCell(3).setCellValue(data[1]);	// Fecha
					    row.createCell(4).setCellValue(data[2]);	// Mensaje
					    row.createCell(5).setCellValue(data[4]);	// Respuesta
					  
					}
				}
				catch(Exception exc){
					//System.out.println("Error -> " + exc.getMessage());
					ta_log.append("\n#~ Error -> " + exc.getMessage());
					err++;
					continue;
				}
			}
		
		//System.out.println("<<< Operacion terminada >>>");
		//System.out.println("Errores: " + err);
		ta_log.append("\n#~ <<< OPERACIÓN TERMINADA >>>");
		ta_log.append("\n#~ Errores: " + err);
		
	}
	
	private void closeFile(String exlpath) throws Exception{
		FileOutputStream fileOut = new FileOutputStream(exlpath);
	    wb.write(fileOut);
	    fileOut.close();
	}
	
}
