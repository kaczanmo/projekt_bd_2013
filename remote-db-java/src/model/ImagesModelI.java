package model;

import java.util.ArrayList;
import java.util.List;

import data.Image;
import data.User;

public interface ImagesModelI {
	public List<Image> getImages() ;

	public void updateImage(Image img);

	public void deleteImage(Image img);

	public User getUser(Long id);
}
