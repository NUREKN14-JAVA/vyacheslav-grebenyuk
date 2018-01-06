package com.nixsolutions.usermanagement.db;

import com.mockobjects.dynamic.Mock;
import com.nixsolutions.usermanagement.User;

public class MockDaoFactory extends DaoFactory {

    private Mock mockUserDao;
    
    public MockDaoFactory() {
        mockUserDao = new Mock(Dao.class);
    }
    
    public Mock getMockUserDao() {
        return mockUserDao;
    }
    
    @SuppressWarnings("unchecked")
    public Dao<User> getUserDao() {
        return (Dao<User>) mockUserDao.proxy();
    }

}
