package com.services.common.utils;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {

			Configuration conf = new AnnotationConfiguration().configure();
			conf.setProperty("hibernate.connection.url", "jdbc:hsqldb:file:" + getUrl(false));
			System.out.println(">>>>>>>>>>>>>>>>"+getUrl(false));
			// Create the SessionFactory from hibernate.cfg.xml
			return conf.buildSessionFactory();

		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static String getUrl(boolean diretorio) {
		String nomeBanco = "data";
//		String sistema_operacional = System.getProperty("os.name");
//		if (sistema_operacional.equals("Linux")) {
			String home = System.getProperty("user.home") + "/.lms/";
			new File(home).mkdirs();
			return (diretorio) ? home : home + nomeBanco;
//		} else {
//			return (diretorio) ? "." : nomeBanco;
//		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}

}