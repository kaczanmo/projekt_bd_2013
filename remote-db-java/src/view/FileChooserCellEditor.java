package view;



import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableCellEditor;

public class FileChooserCellEditor extends DefaultCellEditor implements TableCellEditor
{
    /** Number of clicks to start editing */
    private static final int CLICK_COUNT_TO_START = 2;
    private static final int MAX_KB_IMAGE_SIZE = 100;
    /** Editor component */
    private JButton button;
    /** File chooser */
    private JFileChooser fileChooser;
    /** Selected file */
    private byte[] data ;

    /**
     * Constructor.
     */
    public FileChooserCellEditor() {
        super(new JTextField());
        setClickCountToStart(CLICK_COUNT_TO_START);

        // Using a JButton as the editor component
        button = new JButton();
        button.setBackground(Color.white);
        button.setFont(button.getFont().deriveFont(Font.PLAIN));
        button.setBorder(null);

        // Dialog which will do the actual editing
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileFilter() {
        	 
            public String getDescription() {
                return "--Images--";
            }
         
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                	String fName = f.getName().toLowerCase();
                    return (
                    		(fName.endsWith(".png")  || fName.endsWith(".jpeg") ||
                    				fName.endsWith(".jpg") || fName.endsWith(".gif") ||
                    				fName.endsWith(".ico") || fName.endsWith(".bmp")  ) 
                    				&& f.length()<(1024*MAX_KB_IMAGE_SIZE));
                }
            }
        });
        
    }

    @Override
    public byte[] getCellEditorValue() {
        return data;
    }

    @Override
    public Component getTableCellEditorComponent(final JTable table, Object value, boolean isSelected, int row, int column) {
    	if(value == null)
    		value = "/";
//        file = value.toString();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                fileChooser.setSelectedFile(new File("/"));
                int retBtn = fileChooser.showOpenDialog(button);
                if(retBtn == JFileChooser.CANCEL_OPTION){
                	fireEditingCanceled();	
                }
                else if (retBtn == JFileChooser.OPEN_DIALOG) {
//                	data = fileChooser.getSelectedFile().getAbsolutePath();
                    
                    File file = fileChooser.getSelectedFile();
                	data = new byte[(int) file.length()];
       			 
        			try {
        			    FileInputStream fileInputStream = new FileInputStream(file);
        			    fileInputStream.read(data);
        			    fileInputStream.close();
        			} catch (Exception e1) {
        			    e1.printStackTrace();
        			}
                }
                else 
                	data = null;
                fireEditingStopped();
            }
        });
//        button.setText(file);
        return button;
    }
}