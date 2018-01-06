package com.nixsolutions.usermanagement.db;

import com.nixsolutions.usermanagement.User;

import junit.framework.TestCase;

public class DaoFactoryTest extends TestCase {

    /*
     * Test method for 'com.nixsolutions.usermanagement.db.DaoFactory.getUserDao()'
     */
    public void testGetUserDao() {
        try {
            DaoFactory daoFactory = DaoFactory.getInstance();
            assertNotNull("DaoFactory instance is null", daoFactory);
            Dao<User> userDao = daoFactory.getUserDao();
            assertNotNull("UserDao instance is null", userDao);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

}
