package presenter;

import view.LoginViewI;
import model.LoginModelI;


public interface LoginPresenterI {
    LoginModelI getModel();

    void setModel(LoginModelI model);

    LoginViewI getView();

    public void setView(LoginViewI view);

    void setOnLogin(Runnable onLogin);

    void run();

    void login();
}