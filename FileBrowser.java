/*
 * This class creates the excel file.
 * The data is obtained from the facebook backup.
 * 
 * @author Ariel Bravo (TheHaker117)
 * @version %I%, %G%
 */



package AnLex;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileBrowser extends SwingWorker<Void, String>{
	
	private XSSFWorkbook wb;
	private XSSFSheet sheet;
	private int rc = 2;
	private int err = 0;
	private JTextArea ta_log;
	private String exlpath, htmlpath, fpath, keyword;
	private JFrame parent;
	
	public FileBrowser(String exlpath, String htmlpath, String fpath, String keyword, JFrame parent, JTextArea ta_log) throws Exception{
		
		this.exlpath = exlpath;
		this.htmlpath = htmlpath;
		this.fpath = fpath;
		this. keyword = keyword;
		this.parent = parent;
		this.ta_log = ta_log;
		
		createExcel();
		
	}
	
	
	private void createExcel() throws Exception{
		wb = new XSSFWorkbook();
	    sheet = (XSSFSheet) wb.createSheet("Mensajes");
	    
	    Row row = sheet.createRow(rc);
	    row.createCell(0).setCellValue("COUNT");
	    row.createCell(1).setCellValue("REMITENTE");
	    row.createCell(2).setCellValue("DESTINATARIO");
	    row.createCell(3).setCellValue("FECHA");
	    row.createCell(4).setCellValue("MENSAJE");
	    row.createCell(5).setCellValue("RESPUESTA");
	    
	}
	
	private void closeFile(String exlpath) throws Exception{
		FileOutputStream fileOut = new FileOutputStream(exlpath);
	    wb.write(fileOut);
	    fileOut.close();
	}

	@Override
	protected Void doInBackground() throws Exception {
		
		File dir = new File(htmlpath);
		String[] list = dir.list();
		
		FriendExtractor links = new FriendExtractor(fpath);
		
		int count00 = 1;
		int count01 = 1;
		
		for(int i = 0; i < list.length; i++)
			if(list[i].contains(".html")){
				count00++;
				publish("\n#~ " + list[i]);
				
				try{
					HTMLCleaner cln = new HTMLCleaner(htmlpath + "//" + list[i], keyword);
					String[] data = cln.clean();
					
					
					if(data != null){
						publish("Menssage found");
						Row row = sheet.createRow(++rc);
						
						row.createCell(0).setCellValue(count01++);	// Count
					    row.createCell(1).setCellValue(data[0]);	// Remitente
					    
					    String val;
					    
					    if((val = links.getLink(data[3])) != null)
					    	row.createCell(2).setCellValue(val);	// Destinatario
					    else
					    	row.createCell(2).setCellValue(data[3]);	// Destinatario
					    
					    row.createCell(3).setCellValue(data[1]);	// Fecha
					    row.createCell(4).setCellValue(data[2]);	// Mensaje
					    row.createCell(5).setCellValue(data[4]);	// Respuesta
					  
					}
					
					else
						publish("Message not found");
				}
				catch(Exception exc){
					publish("\n#~ Error -> " + exc.getMessage());
					err++;
					continue;
				}
			}
		
		sheet = (XSSFSheet) wb.createSheet("Amigos");
		ArrayList<String[]> temo = links.getTupla();
		
		for(int i = 0; i < temo.size(); i++){
			Row row = sheet.createRow(i);
			row.createCell(0).setCellValue(temo.get(i)[0]);
			row.createCell(1).setCellValue(temo.get(i)[1]);
			
		}
		
		closeFile(exlpath);
		
		publish("\n#~ <<< OPERACIÓN TERMINADA >>>");
		publish("#~ Mensajes totales: " + (count00 - 1));
		publish("#~ Contenido encontrado: " + (count01 - 1));
		publish("#~ Errores: " + err);
		
		
		return null;
	}
	
	@Override
	protected void process(List<String> chunks){
		
		for(String str : chunks){
			ta_log.append(str);
			ta_log.append("\n");
		}
	}
	
	@Override
	protected void done(){
		JOptionPane.showMessageDialog(parent, "Excel generado satisfactoriamente", 
				"Información", JOptionPane.INFORMATION_MESSAGE);
	}
	
}