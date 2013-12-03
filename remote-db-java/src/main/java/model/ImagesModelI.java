package model;

import java.util.ArrayList;
import java.util.List;

import data.Image;

public interface ImagesModelI {
	public List<Image> getImages() ;

	public void updateImage(Image img);

	public void deleteImage(Image img);
}
