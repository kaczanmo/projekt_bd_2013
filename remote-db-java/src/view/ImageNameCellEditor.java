package view;

import javax.swing.DefaultCellEditor;
import javax.swing.InputVerifier;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

public class ImageNameCellEditor extends DefaultCellEditor {

	ImageNameVerifier verifier = null;
	 
	 
	public ImageNameCellEditor(ImageNameVerifier verifier) {
		 super(new JTextField());
	     this.verifier = verifier;
	}

	   @Override
	    public boolean stopCellEditing() {
	        return verifier.verify(editorComponent) && super.stopCellEditing();
	    }

}
