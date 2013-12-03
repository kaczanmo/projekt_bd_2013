package presenter;

import view.LoginViewI;
import model.LoginModelI;

public class LoginPresenter implements LoginPresenterI {

    LoginModelI loginModel;
    LoginViewI loginView;
    private Runnable onLogin;

    public LoginModelI getModel() {
        return loginModel;
    }

    public void setModel(LoginModelI loginModel) {
        this.loginModel = loginModel;
    }

    public LoginViewI getView() {
        return loginView;
    }


    public void setView(LoginViewI loginView) {
        this.loginView = loginView;
    }

    public void setOnLogin(Runnable onLogin) {
        this.onLogin = onLogin;
    }

    public void run() {
        loginModel.setUser("previousUser");
        loginView.setPresenter(this);
        loginView.updateViewFromModel();
        loginView.open();
    }

    public void login() {
        loginView.updateModelFromView();
        if (loginModel.getUser().equalsIgnoreCase("root")) {
            loginView.close();
            loginView.setPresenter(null);// for memory sanity. 
            onLogin.run();
        } else {
            loginView.userRejected();
        }
    }


}