package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

	private static String hibernateConfig = "hibernate.cfg.xml";
	private static HibernateUtil instance = null;
	private SessionFactory sessionFactory = null;

	private HibernateUtil() {
		try {
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

}
