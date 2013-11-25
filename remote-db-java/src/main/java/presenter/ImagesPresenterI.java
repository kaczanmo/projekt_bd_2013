package presenter;

import model.ImagesModelI;
import model.LoginModelI;
import view.ImagesViewI;
import view.LoginViewI;

public interface ImagesPresenterI {
	  ImagesModelI getModel();

	    void setModel(ImagesModelI model);

	    ImagesViewI getView();

	    public void setView(ImagesViewI view);

	    //void setOnLogin(Runnable onLogin);

	    void run();

	    //void login();
}
