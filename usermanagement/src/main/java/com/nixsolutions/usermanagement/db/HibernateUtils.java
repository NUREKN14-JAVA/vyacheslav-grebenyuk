package com.nixsolutions.usermanagement.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
        
   private static SessionFactory sessionFactory;
   
   static {
       Configuration configuration = new AnnotationConfiguration().configure();
       sessionFactory = configuration.buildSessionFactory();
   }

   static SessionFactory getSessionFactory() {
       return sessionFactory;
   }
}
