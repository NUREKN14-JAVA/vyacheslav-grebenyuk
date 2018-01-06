package com.nixsolutions.usermanagement.db;

import com.nixsolutions.usermanagement.User;

public class DaoFactoryImpl extends DaoFactory {

    @SuppressWarnings("unchecked")
    public Dao<User> getUserDao() {
        Dao<User> result = null;
        try {
            Class<?> clazz = Class.forName(properties.getProperty(USER_DAO));
            result = (Dao<User>) clazz.newInstance();
            result.setConnectionFactory(getConnectionFactory());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
