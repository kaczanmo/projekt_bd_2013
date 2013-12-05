package com;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.ImagesModel;
import model.ImagesModelI;
import model.LoginModelI;
import model.LoginModel;

import org.hibernate.Session;
import org.slf4j.Logger;

import presenter.ImagesPresenter;
import presenter.ImagesPresenterI;
import presenter.LoginPresenterI;
import presenter.LoginPresenter;

import dao.ImageDAOI;
import dao.ImageDAOImpl;
import data.Image;
import util.HibernateUtil;
import view.ImagesView;
import view.ImagesViewI;
import view.LoginViewI;
import view.LoginView;

public class Application {

//APPLICATION
	public enum Mode{
		DBALL, DBAO, DBPZ
	}
	public static Mode appMode = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		for (String s: args) 
//            System.out.println(s);

		if(args.length > 0){
			appMode = Mode.valueOf(args[0]);
			if(appMode.equals(Mode.DBALL))
				HibernateUtil.setHibernateConfig("hibernate_DBALL.cfg.xml");
			if(appMode.equals(Mode.DBAO))
				HibernateUtil.setHibernateConfig("hibernate_DBAO.cfg.xml");
			if(appMode.equals(Mode.DBPZ))
				HibernateUtil.setHibernateConfig("hibernate_DBPZ.cfg.xml");
		}
		else
		{
			appMode = Mode.DBALL;
			HibernateUtil.setHibernateConfig("hibernate_DBALL.cfg.xml");
		}
//			
		// TODO Auto-generated method stub
		
		System.out.println("Hibernate + Oracle");


        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
            	
//                LoginModelI loginModel = new LoginModel();
//                LoginPresenterI loginPresenter = new LoginPresenter();
//                loginPresenter.setModel(loginModel);
//                LoginViewI loginView = new LoginView();
//                loginPresenter.setView(loginView);
//                loginPresenter.setOnLogin(new Runnable() {
//
//                    public void run() {
//                        JOptionPane.showMessageDialog(null, "Welcome user!");
//                    }
//                });
//                loginPresenter.run();
            	
            	ImagesModelI imagesModel = new ImagesModel();
            	ImagesPresenterI imagesPresenter = new ImagesPresenter();
            	imagesPresenter.setModel(imagesModel);
            	ImagesViewI imagesView = new ImagesView();
            	imagesPresenter.setView(imagesView);
            	
            	imagesPresenter.run();
                
            }
        });
		    
		
		
	}

}
