package example;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.io.*;

class TableFileChooser extends JFrame  
{
    private JTable table;
    private JScrollPane jsPane;
    private TableModel myModel;
    private JPanel dialogPanel;
    private JTextField tf[];
    private JLabel     lbl[];
    public void prepareAndShowGUI()
    {
        setTitle("FileChooser in JTable");
        myModel = new MyModel();
        table = new JTable(myModel);
        jsPane = new JScrollPane(table);
        table.addMouseListener(new MyMouseAdapter());
        getContentPane().add(jsPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        prepareDialogPanel();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }
    private void prepareDialogPanel()
    {
        dialogPanel = new JPanel();
        int col = table.getColumnCount() - 1;
        dialogPanel.setLayout(new GridLayout(col,2));
        tf = new JTextField[col];
        lbl = new JLabel[col];
        for (int i = 0; i < col; i++)
        {
            lbl[i] = new JLabel(table.getColumnName(i));
            tf[i] = new JTextField(10);
            dialogPanel.add(lbl[i]);
            dialogPanel.add(tf[i]);
        }
    }
    private void populateTextField(String[] s)
    {
        for (int i = 0 ; i < s.length ; i++ )
        {
            tf[i].setText(s[i]);
        }
    }

    private class MyMouseAdapter extends MouseAdapter
    {
        @Override
        public void mousePressed(MouseEvent evt)
        {
            int x = evt.getX();
            int y = evt.getY();
            int row = table.rowAtPoint(new Point(x,y));
            int col = table.columnAtPoint(new Point(x,y));
            if (col == 2)
            {
                String value = load();
                if (value!=null && ! "null".equalsIgnoreCase(value.trim()) && ! "".equalsIgnoreCase(value.trim()))
                {
                    myModel.setValueAt(value,row,col);
                }
                else
                {
                    myModel.setValueAt("ChooseFile",row,col);
                }
            }
        }
    }
    //Loads the file
    private String load()
    {
        JFileChooser chooser = new JFileChooser(".");
        chooser.setDialogTitle("Open");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
            File file = chooser.getSelectedFile();
            if (file!= null)
            {
                return file.getAbsolutePath();
            }
            else 
            {
                JOptionPane.showMessageDialog(this,"Please enter a fileName","Error",JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        return null;
    }
    private class MyModel extends AbstractTableModel 
    {
        String[] columns = {
                            "Roll No.",
                            "Name",
                            "File Name"
                            };
        String[][] inData = {
                                {"1","Anthony Hopkins","ChooseFile"},
                                {"2","James William","ChooseFile"},
                                {"3","Mc. Donald","ChooseFile"}
                            };
        @Override
        public void setValueAt(Object value, int row, int col)
        {
            inData[row][col] = (String)value;
            fireTableCellUpdated(row,col);
        }
        @Override
        public Object getValueAt(int row, int col)
        {
            return inData[row][col];
        }
        @Override
        public int getColumnCount()
        {
            return columns.length;
        }
        @Override 
        public int getRowCount()
        {
            return inData.length;
        }
        @Override
        public String getColumnName(int col)
        {
            return columns[col];
        }
        @Override
        public boolean isCellEditable(int row ,int col)
        {
            return false;
        }
    }
    public static void main(String st[])
    {
        SwingUtilities.invokeLater( new Runnable()
        {
            @Override
            public void run()
            {
                TableFileChooser td = new TableFileChooser();
                td.prepareAndShowGUI();
            }
        });
    }
}