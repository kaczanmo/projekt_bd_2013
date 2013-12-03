package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

	private static String hibernateConfig = "";
	private static HibernateUtil instance = null;
	private SessionFactory sessionFactory = null;

	private HibernateUtil() {
		try {
			System.err.println("config file : "+hibernateConfig);
			sessionFactory = new AnnotationConfiguration().configure(
					hibernateConfig).buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized static HibernateUtil getInstance() {
		if (instance == null) {
			instance = new HibernateUtil();
		}
		return instance;
	}

	public synchronized SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static String getHibernateConfig() {
		return hibernateConfig;
	}

	public static void setHibernateConfig(String hibernateConfig) {
		HibernateUtil.hibernateConfig = hibernateConfig;
	}

}
