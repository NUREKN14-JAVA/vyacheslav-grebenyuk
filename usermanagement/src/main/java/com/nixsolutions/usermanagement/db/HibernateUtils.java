package com.nixsolutions.usermanagement.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
        
   private static SessionFactory sessionFactory;
   
   static {
       Configuration configuration = new Configuration().configure();
       sessionFactory = configuration.buildSessionFactory();
   }

   static SessionFactory getSessionFactory() {
       return sessionFactory;
   }
}
