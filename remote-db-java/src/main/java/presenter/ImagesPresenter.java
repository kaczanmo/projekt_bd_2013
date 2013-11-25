package presenter;

import model.ImagesModelI;
import view.ImagesViewI;

public class ImagesPresenter implements ImagesPresenterI {
	
	ImagesModelI imagesModel;
	ImagesViewI imagesView;
	

	public ImagesModelI getModel() {
		return imagesModel;
	}

	public void setModel(ImagesModelI model) {
		imagesModel = model;
		
	}

	public ImagesViewI getView() {
		return imagesView;
	}

	public void setView(ImagesViewI view) {
		imagesView = view;
		
	}

	public void run() {
		imagesView.setPresenter(this);
		imagesView.updateViewFromModel();
		imagesView.open();
		
	}

}
