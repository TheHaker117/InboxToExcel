package AnLex;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class FBKAnLexGUI extends JFrame implements ActionListener{

	private JTextField  tf_path, tf_keyword;
	private JButton btn_browse, btn_generar;
	private JLabel lbl_keyword;
	private JTextArea ta_log;
	
	
	public FBKAnLexGUI(){
		super();
		init();
		config();
	}
	
	public static void main(String[] args){
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				FBKAnLexGUI fbk = new FBKAnLexGUI();
				fbk.setVisible(true);
			}
			
		});
	}
	
	
	private void init(){
		this.setTitle("InboxToExcel by TheHaker117");
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} 
		catch(Exception e){e.getMessage();}
		
	}
	
	private void config(){
		
		tf_path = new JTextField("Ubicación de la carpeta messages...");
		tf_path.setEditable(false);
		tf_path.setBounds(20, 20, 355, 30);
		
		btn_browse = new JButton("Browse");
		btn_browse.setBounds(150, 60, 80, 30);
		btn_browse.setToolTipText("Buscar carpeta");
		btn_browse.addActionListener(this);
		
		lbl_keyword = new JLabel("Keyword:");
		lbl_keyword.setBounds(25, 100, 50, 30);
		
		tf_keyword = new JTextField();
		tf_keyword.setBounds(90, 100, 285, 30);
		
		btn_generar = new JButton("Generate!");
		btn_generar.setBounds(145, 150, 90, 30);
		btn_generar.setToolTipText("Generar Excel");
		btn_generar.addActionListener(this);
		
		ta_log = new JTextArea();
		//ta_log.setBounds(20, 190, 30, 30);
		//ta_log.setSize(100, 20);
		ta_log.setEditable(false);
		
		JScrollPane sp_log = new JScrollPane(ta_log);
		sp_log.setBounds(20, 190, 355, 70);
		
		this.add(tf_path);
		this.add(btn_browse);
		this.add(lbl_keyword);
		this.add(tf_keyword);
		this.add(btn_generar);
		this.add(sp_log);
		
		
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e){
		
		JFileChooser chooser = new JFileChooser();
		
		
		if(e.getSource().equals(btn_browse)){
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int sel = chooser.showOpenDialog(this);
			
			switch(sel){
				case JFileChooser.APPROVE_OPTION:
					tf_path.setText(chooser.getSelectedFile().getPath());
					break;
				case JFileChooser.CANCEL_OPTION:
					JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún directorio", 
							"Advertencia", JOptionPane.WARNING_MESSAGE);
					break;
				default: break;
			}
			
		}
		
		if(e.getSource().equals(btn_generar)){
			
			JOptionPane.showMessageDialog(this, "Seleccione la ubicación en donde se guardará el archivo excel", 
					"Advertencia", JOptionPane.WARNING_MESSAGE);
			
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			
			
			
			int sel = chooser.showSaveDialog(this);
			
			switch(sel){
				case JFileChooser.APPROVE_OPTION:
					File file = chooser.getSelectedFile();
					
					try{
						new FileBrowser(file.getPath(), tf_path.getText(), tf_keyword.getText(), ta_log);
						JOptionPane.showMessageDialog(this, "Excel generado satisfactoriamente", 
								"Información", JOptionPane.INFORMATION_MESSAGE);
					}
					catch(Exception exc){
						JOptionPane.showMessageDialog(this, "Ha ocurrido un error:" + exc.getMessage(), 
							"Error", JOptionPane.ERROR_MESSAGE);
					}
					
					break;
					
				case JFileChooser.CANCEL_OPTION:
					JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún directorio", 
							"Advertencia", JOptionPane.WARNING_MESSAGE);
					break;
					
				default: break;	
				
			}
			
			
		}
		
	}
	
	
}
