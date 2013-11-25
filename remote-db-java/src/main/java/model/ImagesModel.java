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
		
//		for(Image i : imagesList)
//		{
//			System.err.println(i);
//		}
	}
}
