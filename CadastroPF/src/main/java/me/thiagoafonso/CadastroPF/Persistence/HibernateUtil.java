package me.thiagoafonso.CadastroPF.Persistence;

import org.hibernate.service.ServiceRegistry;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.jboss.logging.Logger;

public class HibernateUtil {

	static Logger logger = Logger.getLogger(HibernateUtil.class.getName());
	
	private static SessionFactory sessionFactory;
	
	//Hibernate 5:
	private static SessionFactory buildSessionFactory() {
		logger.info("private static SessionFactory buildSessionFactory() {...");
		try {
			//create the ServiceRegistry from hibernate.cfg.xml
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
			
			//create a metadata sources using the specifies service registry.
			Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
			
			return metadata.getSessionFactoryBuilder().build();
		} catch(Throwable ex) {
			
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		logger.info("public static SessionFactory getSessionFactory() {...");
		if(HibernateUtil.sessionFactory == null) {			
			logger.info("HibernateUtil.sessionFactory was null.. Calling HibernateUtil.buildSessionFactory()...");
			HibernateUtil.sessionFactory = HibernateUtil.buildSessionFactory();
		}
		return HibernateUtil.sessionFactory;
	}
	
	public static void shutdown() {
		//close caches and connection polls
		getSessionFactory().close();
	}
}