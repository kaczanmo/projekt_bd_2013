package model;

import java.util.ArrayList;
import java.util.List;

import dao.ImageDAOI;
import dao.ImageDAOImpl;
import data.Image;

public class ImagesModel implements ImagesModelI {

	public List<Image> getImages() {
		ImageDAOI imageDao = new ImageDAOImpl();
		ArrayList<Image> imagesList = new ArrayList<Image>(imageDao.findAll());
		return imagesList;
	}
	
	public void updateImage(Image img) {
		ImageDAOI imageDao = new ImageDAOImpl();
		imageDao.makePersistent(img);
	}

	public void deleteImage(Image img) {
		ImageDAOI imageDao = new ImageDAOImpl();
		imageDao.makeTransient(img);
		
	}
}
