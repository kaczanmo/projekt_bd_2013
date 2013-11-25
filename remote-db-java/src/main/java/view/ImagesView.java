package view;

import java.io.Serializable;
import java.util.List;

import presenter.ImagesPresenterI;
import presenter.LoginPresenterI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import data.Image;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.miginfocom.swing.MigLayout;

public class ImagesView extends JFrame implements ImagesViewI, Serializable, ActionListener {
	
	private ImagesPresenterI imagesPresenter;
	private JTable table;
	private Object[][] data = null;
	private String[] columnNames = {"Id","Name"};
	private JScrollPane scrollPane = null;
	private JTextField filterText = null;
	private JTextField statusText = null;
	private TableRowSorter<TableModel> sorter;
	
	private Button bRefresh = null;
	
	public ImagesView(){
		super("RDB");
		initComponents();
	}

	private void initComponents() {
		setLayout(null);
		setSize(650,400);
		setLocation(500,300);
		setFont(new Font("Arial",0,16));
		setResizable(false);
//		setBackground(new Color(220,220,220));
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		////////////////
		bRefresh = new Button("Refresh");
		bRefresh.setSize(100,25);
		bRefresh.setLocation(500,20);	
		bRefresh.addActionListener(this);	
		add(bRefresh);	
		///////////////////
		Button bNowa2 = new Button("?");
		bNowa2.setSize(100,25);
		bNowa2.setLocation(500,50);	
		bNowa2.addActionListener(this);	
		add(bNowa2);	
		///////////////
		
		data = new Object[10][2];

		
		table = new JTable(data, columnNames);
		
	    table.setPreferredScrollableViewportSize(new Dimension(500, 70));
	    table.setFillsViewportHeight(true);
	    scrollPane = new JScrollPane(table);
	    scrollPane.setSize(200,250);
	    scrollPane.setLocation(10, 10);
		add(scrollPane);
		
		//Create a separate form for filterText and statusText
        JPanel form = new JPanel();
        form.setLayout(null);
        form.setSize(400,300);
        form.setLocation(50, 250);
        JLabel l1 = new JLabel("Filter Text:");
        l1.setSize(100, 50);
        l1.setLocation(0,0);
        form.add(l1);
        filterText = new JTextField();
        filterText.setLocation(70, 20);
        filterText.setSize(200, 20);
        //Whenever filterText changes, invoke newFilter.
        filterText.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        newFilter();
                    }
                    public void insertUpdate(DocumentEvent e) {
                        newFilter();
                    }
                    public void removeUpdate(DocumentEvent e) {
                        newFilter();
                    }
                });
        l1.setLabelFor(filterText);
        form.add(filterText);
//        JLabel l2 = new JLabel("Status:");
//        l2.setSize(100, 50);
//        l2.setLocation(0,30 );
//        form.add(l2);
//        statusText = new JTextField();
//        statusText.setLocation(70, 50);
//        statusText.setSize(200, 20);
//        l2.setLabelFor(statusText);
//        form.add(statusText);
        add(form);
		show();

	}

	public ImagesPresenterI getPresenter() {
		return imagesPresenter;
	}

	public void setPresenter(ImagesPresenterI presenter) {
		imagesPresenter = presenter;
		
	}

	public void updateModelFromView() {
		// TODO Auto-generated method stub
		
	}

	public void updateViewFromModel() {
		System.err.println("view updated...");
		if(table != null){
			List<Image> imagesList = getPresenter().getModel().getImages();
			for(int i=0;i<10;i++)
				for(int j=0;j<2;j++)
					{
					if(i<imagesList.size()){
						data[i][0] = imagesList.get(i).getId();
						data[i][1] = imagesList.get(i).getName();
					}
					else
						data[i][j] = "";
					
					}
			scrollPane.repaint();
		}
		
	}

	public void open() {
		this.setVisible(true);
		this.show();
		// TODO Auto-generated method stub
		
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}
	
	 /** 
     * Update the row filter regular expression from the expression in
     * the text box.
     */
    private void newFilter() {
        RowFilter<TableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(filterText.getText(), 0);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }

	public void actionPerformed(ActionEvent e) {
		Object 	cel = e.getSource();
		if (cel == bRefresh)
		{	updateViewFromModel();
		} 
		//else if (cel == bTest)
		
	}
}
