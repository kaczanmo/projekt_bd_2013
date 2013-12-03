package view;

import presenter.LoginPresenterI;


public interface LoginViewI {
	 	LoginPresenterI getPresenter();

	    void setPresenter(LoginPresenterI presenter);

	    void updateModelFromView();

	    void updateViewFromModel();

	    void open();

	    void close();

	    void userRejected();
}