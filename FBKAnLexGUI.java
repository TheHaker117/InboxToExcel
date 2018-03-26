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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField  tf_path, tf_fpath, tf_keyword;
	private JButton btn_browse00, btn_browse01, btn_generar;
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
		this.setSize(500, 300);
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
		
		btn_browse00 = new JButton("Browse");
		btn_browse00.setBounds(390, 20, 80, 30);
		btn_browse00.setToolTipText("Buscar carpeta");
		btn_browse00.addActionListener(this);
		
		tf_fpath = new JTextField("Ubicación de la página de amigos...");
		tf_fpath.setEditable(false);
		tf_fpath.setBounds(20, 60, 355, 30);
		
		btn_browse01 = new JButton("Browse");
		btn_browse01.setBounds(390, 60, 80, 30);
		btn_browse01.setToolTipText("Buscar archivo HTML");
		btn_browse01.addActionListener(this);
		
		
		lbl_keyword = new JLabel("Keyword:");
		lbl_keyword.setBounds(25, 100, 50, 30);
		
		tf_keyword = new JTextField();
		tf_keyword.setBounds(90, 100, 285, 30);
		
		btn_generar = new JButton("Generate!");
		btn_generar.setBounds(390, 145, 90, 110);
		btn_generar.setToolTipText("Generar Excel");
		btn_generar.addActionListener(this);
		
		ta_log = new JTextArea();
		ta_log.setEditable(false);
		
		JScrollPane sp_log = new JScrollPane(ta_log);
		sp_log.setBounds(20, 145, 355, 110);
		
		
		this.add(tf_path);
		this.add(btn_browse00);
		this.add(tf_fpath);
		this.add(btn_browse01);
		this.add(lbl_keyword);
		this.add(tf_keyword);
		this.add(btn_generar);
		this.add(sp_log);
		
		
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e){
		
		JFileChooser chooser = new JFileChooser();
		
		
		if(e.getSource().equals(btn_browse00)){
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
		
		if(e.getSource().equals(btn_browse01)){
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int sel = chooser.showOpenDialog(this);
			
			switch(sel){
				case JFileChooser.APPROVE_OPTION:
					tf_fpath.setText(chooser.getSelectedFile().getPath());
					break;
				case JFileChooser.CANCEL_OPTION:
					JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún Archivo", 
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
						FileBrowser fbr = new FileBrowser(file.getPath(), tf_path.getText(), tf_fpath.getText(), tf_keyword.getText(), this, ta_log);
						fbr.execute();
						
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
