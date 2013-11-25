package view;

import presenter.ImagesPresenterI;
import presenter.LoginPresenterI;

public interface ImagesViewI {
	ImagesPresenterI getPresenter();

    void setPresenter(ImagesPresenterI presenter);

    void updateModelFromView();

    void updateViewFromModel();

    void open();

    void close();
}
