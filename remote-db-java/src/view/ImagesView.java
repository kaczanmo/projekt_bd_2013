package view;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

import presenter.ImagesPresenterI;
import presenter.LoginPresenterI;
import util.HibernateUtil;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;

import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import oracle.sql.BLOB;

import com.Application;
import com.Application.Mode;

import data.Image;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ImagesView extends JFrame implements ImagesViewI, Serializable, ActionListener {
	
	private ImagesPresenterI imagesPresenter;
	private JTable table;
	private TableRowSorter<MyTableModel> sorter;
	private MyTableModel tableModel;
	private JScrollPane scrollPane = null;
	private JTextField filterText = null;
	private JTextField statusText = null;
	
	private Button bRefresh = null;
	private Button bSave = null;
	private Button bDel = null;
	private Button bAdd = null;
	private java.awt.Image imagePreview = null;
	private JLabel labelImg = null;
	private static final int IMG_WIDTH = 100;
	private static final int IMG_HEIGHT = 100;
	private FileChooserCellEditor fileChooserCellEditor = null;
	
	
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
		bRefresh = new Button("Odœwie¿");
		bRefresh.setSize(100,25);
		bRefresh.setLocation(500,20);	
		bRefresh.addActionListener(this);	
		add(bRefresh);	
		/////////////
		bAdd = new Button("Dodaj");
		bAdd.setSize(100,25);
		bAdd.setLocation(500,50);	
		bAdd.addActionListener(this);
		if(Application.appMode.equals(Mode.DBALL))
			bAdd.setVisible(false);
		add(bAdd);		
		///////////////////
		bSave = new Button("Zapisz");
		bSave.setSize(100,25);
		bSave.setLocation(500,80);	
		bSave.addActionListener(this);	
		bSave.setEnabled(false);
		if(Application.appMode.equals(Mode.DBALL))
			bSave.setVisible(false);
		add(bSave);	
		///////////////
		bDel = new Button("Usuñ");
		bDel.setSize(100,25);
		bDel.setLocation(500,110);	
		bDel.addActionListener(this);	
		bDel.setEnabled(false);
		if(Application.appMode.equals(Mode.DBALL))
			bDel.setVisible(false);
		add(bDel);	
		///////////////
		showImgPreview(null);
		
		//////////////
		
		tableModel = new MyTableModel();
	    sorter = new TableRowSorter<MyTableModel>(tableModel);
	    table = new JTable(tableModel);
	    table.setRowSorter(sorter);
	    table.setPreferredScrollableViewportSize(new Dimension(500, 70));
	    table.setFillsViewportHeight(true);
	
	    
	    //For the purposes of this example, better to have a single
        //selection.
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 
        //When selection changes, provide user with row numbers for
        //both view and model.
        table.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = table.getSelectedRow();
                        if (viewRow < 0) {
                            //Selection got filtered away.
                            statusText.setText("");
                        } else {
                            int modelRow = 
                                table.convertRowIndexToModel(viewRow);
                            statusText.setText(
                                String.format("Selected Row in view: %d. " +
                                    "Selected Row in model: %d.", 
                                    viewRow, modelRow));
                            

                          showImgPreviewInTable(modelRow);
                    		
                            
                            bDel.setEnabled(true);
                        }
                    }

				
                }
        );
        table.addPropertyChangeListener(new PropertyChangeListener() {
			
			public void propertyChange(PropertyChangeEvent arg0) {
				for(int i = 0; i< tableModel.changes.size(); i++){
					if(tableModel.changes.get(i) == true){
						bSave.setEnabled(true);	
						break;
					}
				}

			}

		});
        fileChooserCellEditor = new FileChooserCellEditor();
		table.getColumnModel().getColumn(5).setCellEditor(fileChooserCellEditor);
		fileChooserCellEditor.addCellEditorListener(new CellEditorListener() {
			
			@Override
			public void editingStopped(ChangeEvent arg0) {
				int viewRow = table.getSelectedRow();
				int modelRow = table.convertRowIndexToModel(viewRow);
				showImgPreviewInTable(modelRow);
			}
			
			@Override
			public void editingCanceled(ChangeEvent arg0) {
				
			}
		});
	    
	    scrollPane = new JScrollPane(table);
	    scrollPane.setSize(450,250);
	    scrollPane.setLocation(10, 10);
		add(scrollPane);
		
		//Create a separate form for filterText and statusText
        JPanel form = new JPanel();
        form.setLayout(null);
        form.setSize(400,300);
        form.setLocation(50, 250);
        JLabel l1 = new JLabel("Szukaj:");
        l1.setSize(100, 50);
        l1.setLocation(0,0);
        form.add(l1);
        filterText = new JTextField();
        filterText.setLocation(70, 20);
        filterText.setSize(320, 20);
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
        JLabel l2 = new JLabel("Status:");
        l2.setSize(100, 50);
        l2.setLocation(0,30 );
        form.add(l2);
        statusText = new JTextField();
        statusText.setLocation(70, 50);
        statusText.setSize(320, 20);
        l2.setLabelFor(statusText);
        form.add(statusText);
        add(form);
		show();

	}

	public ImagesPresenterI getPresenter() {
		return imagesPresenter;
	}

	public void setPresenter(ImagesPresenterI presenter) {
		imagesPresenter = presenter;
		
	}

	public void updateModelFromView(boolean isChanged, boolean isDeleted) {
		if(isChanged){
		for(int i = 0; i< tableModel.changes.size(); i++){
			if(tableModel.changes.get(i) == true){
				Image img = new Image(tableModel.getData().get(i));
				getPresenter().getModel().updateImage(img);
				tableModel.changes.set(i, false);	
			}	
		 }
		}
		if(isDeleted){
			int viewRow = table.getSelectedRow();
			int modelRow = table.convertRowIndexToModel(viewRow);
			Image img = new Image(tableModel.getData().get(modelRow));
			getPresenter().getModel().deleteImage(img);
			tableModel.deleteRow(modelRow);
		}
		
		scrollPane.repaint();
	}
	
	

	public void updateViewFromModel() {
		System.err.println("view updated...");
		if(table != null){
			List<Image> imagesList = getPresenter().getModel().getImages();

			tableModel.clearData();
			scrollPane.repaint();
			System.err.println(tableModel.getRowCount());
			for(Image img : imagesList)
				tableModel.addRow(img.toArray(), false);
		
//	        sorter.toggleSortOrder(0);
	        sorter.sort();
			scrollPane.repaint();
		}
		
	}

	public void open() {
		this.setVisible(true);
		this.show();
		
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
            rf = RowFilter.regexFilter(filterText.getText(), 1);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
//        sorter.toggleSortOrder(0);
//        sorter.sort();
    }

	public void actionPerformed(ActionEvent e) {
		Object 	cel = e.getSource();
		if (cel == bRefresh){
			
			//cofniecie zmian
			for(int i = 0; i< tableModel.changes.size(); i++)
				if(tableModel.changes.get(i) == true)
					tableModel.deleteRow(i);
			
			tableModel.fireTableDataChanged();
			tableModel.fireTableStructureChanged();
			
			updateViewFromModel();
		} 
		else if(cel == bSave){
			updateModelFromView(true, false);
			bSave.setEnabled(false);
			updateViewFromModel();
		}	
		else if(cel == bDel){
			int viewRow = table.getSelectedRow();
			int modelRow = table.convertRowIndexToModel(viewRow);
			if(tableModel.changes.get(modelRow) == true){
				JOptionPane.showMessageDialog(null, "Zapisz najpierw ten rekord !!!");
			}
			else{
			updateModelFromView(true, true);
			bDel.setEnabled(false);
			sorter.modelStructureChanged();
			newFilter();
			}
		}
		else if(cel == bAdd){
			filterText.setText("");
			
			///
			//TODO
			File file = new File("C:\\Users\\Tenac\\Desktop\\obrazy\\barilla.bmp");
			byte[] imageData = new byte[(int) file.length()];
			 
			try {
			    FileInputStream fileInputStream = new FileInputStream(file);
			    fileInputStream.read(imageData);
			    fileInputStream.close();
			} catch (Exception e1) {
			    e1.printStackTrace();
			}
			
			tableModel.addRow(new Image(null, "",new Date(), 1L, imageData).toArray(),true);
			bSave.setEnabled(true);	
			sorter.allRowsChanged();
			//zaznacz wiersz do edycji
			table.setRowSelectionInterval(0, 0);
			scrollPane.repaint();
		}
	}
	
	void showImgPreview(InputStream sourceimage){
//			InputStream sourceimage = this.getClass().getResourceAsStream("/default.jpg"); //new File("images/default.jpg");

			BufferedImage originalImage = null;
			if(sourceimage != null){
				try {
					originalImage = ImageIO.read(sourceimage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if(originalImage == null){
				try {
					originalImage = ImageIO.read( this.getClass().getResourceAsStream("/default.jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
			BufferedImage resizeImage = resizeImage(originalImage, type);
			if(labelImg == null){
				labelImg = new JLabel(new ImageIcon(resizeImage));
				add(labelImg);
			}
			else
				labelImg.setIcon(new ImageIcon(resizeImage));
			labelImg.setSize(IMG_WIDTH, IMG_HEIGHT);
			labelImg.setLocation(500,150);	
	}
	
	 private static BufferedImage resizeImage(BufferedImage originalImage, int type){
			BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
			g.dispose();
		 
			return resizedImage;
		}
	 
		private void showImgPreviewInTable(int modelRow) {
			  byte[] imageSelData = (byte[]) tableModel.getData().get(modelRow)[5];
            File tmpFile = null;
            try {
					tmpFile = File.createTempFile("tmp", ".tmp");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            if(imageSelData != null ) 
            try{
                FileOutputStream fos = new FileOutputStream(tmpFile); 
                fos.write(imageSelData);
                fos.close();
            }catch(Exception e){
                e.printStackTrace();
            }
    		try {
    			FileInputStream fis =  new FileInputStream(tmpFile);
    			if(imageSelData == null)
    				showImgPreview(null);
    			else
    				showImgPreview(fis);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			
		} 
}
