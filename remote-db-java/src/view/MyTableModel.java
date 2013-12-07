package view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.Application;
import com.Application.Mode;

import data.Image;
import data.User;

public class MyTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4783399676209096533L;
	private boolean DEBUG = false;
    private String[] columnNames = {"Id", "Nazwa",
                                    "Data utworzenia",
                                    "U¿ytkownik", "Obraz"};
//    private Object[][] data  = {
            //{1, "Obraz1","10/10/10", new Integer(1), "www"}
//               };
    private List<Object[]> data = new ArrayList<Object[]>();
    public List<Boolean> changes = new ArrayList<Boolean>();
    
    
    

    public MyTableModel() {
		super();
	}

	public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
    	if(row < getRowCount() && col == 3)
    		return ((User)data.get(row)[col]).getName();
    	else if(row < getRowCount() && col < 5)
    		return data.get(row)[col];
		return null;
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    @Override
    public Class<?> getColumnClass(int c) {
    	if(c==0) return Long.class;
    	if(c==1) return String.class;
    	if(c==2) return Date.class;
    	if(c==3) return Long.class;
    	if(c==4) return byte[].class;
		return String.class;
    }
 
    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
    	
    	if(col < 1 || col==3 || Application.appMode.equals(Mode.DBALL))
    		return false;
    	
    	if(Application.appMode.equals(Mode.DBAO) || Application.appMode.equals(Mode.DBPZ))
    		return true;
    	
		return false;
    	
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        if (DEBUG) {
            System.out.println("Setting value at " + row + "," + col
                               + " to " + value
                               + " (an instance of "
                               + value.getClass() + ")");
        }

        Object[] i = data.get(row);
        i[col] = value;
        data.set(row, i);
        changes.set(row, true);
//        fireTableDataChanged();


        if (DEBUG) {
            System.out.println("New value of data:");
            printDebugData();
        }
    }

    private void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for(int j=0;j<numCols;j++)
            	System.out.print("  " + data.get(i)[j] );
            System.out.println();
        }
        System.out.println("--------------------------");
    }

	public void addRow(Object[] obj, boolean isChanged) {
		data.add(obj);
		changes.add(isChanged);
	}
	
	public void clearData() {
		data.clear();
		changes.clear();
	}
	
	public List<Object[]> getData() {
		return data;		
	}
	
	public void deleteRow(int i){
		data.remove(i);
		changes.remove(i);
	}

}
