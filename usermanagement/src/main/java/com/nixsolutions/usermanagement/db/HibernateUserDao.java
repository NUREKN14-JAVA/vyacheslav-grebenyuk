package com.nixsolutions.usermanagement.db;

import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.nixsolutions.usermanagement.User;

public class HibernateUserDao implements Dao<User> {

    @Override
    public User create(User entity) throws DatabaseException {
        Session session = getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        try {
            session.save(entity);
            t.commit();           
        } catch (Exception e) {
            t.rollback();
            throw new DatabaseException(e);
        }
        return entity;
    }

    @Override
    public void update(User entity) throws DatabaseException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(User entity) throws DatabaseException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public User find(Long id) throws DatabaseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<User> findAll() throws DatabaseException {
        Session session = getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        List<User> users = null;
        try {
            users = (List<User>) session.createCriteria(User.class).list();
        } catch (Exception e) {
            t.rollback();
            throw new DatabaseException(e);
        }
        return users;
    }

    @Override
    public Collection<User> find(String firstName, String lastName) throws DatabaseException {
        Session session = getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        List<User> users = null;
        try {
            users = (List<User>) session.createCriteria(User.class)
                    .add(Restrictions.eq("firstname", firstName))
                    .add(Restrictions.eq("lastname", lastName))
                    .list();
        } catch (Exception e) {
            t.rollback();
            throw new DatabaseException(e);
        }
        return users;
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
    }
    
    public SessionFactory getSessionFactory() {
        return HibernateUtils.getSessionFactory();
    }

}
