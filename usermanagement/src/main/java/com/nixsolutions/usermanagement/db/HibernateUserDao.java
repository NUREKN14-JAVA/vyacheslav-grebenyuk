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
        Session session = getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        try {
            session.update(entity);
            t.commit();           
        } catch (Exception e) {
            t.rollback();
            throw new DatabaseException(e);
        }
    }

    @Override
    public void delete(User entity) throws DatabaseException {
        Session session = getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        try {
            session.delete(entity);
            t.commit();           
        } catch (Exception e) {
            t.rollback();
            throw new DatabaseException(e);
        }                
    }

    @Override
    public User find(Long id) throws DatabaseException {
        Session session = getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        User user = null;
        try {
            user = (User) session.createCriteria(User.class)
                    .add(Restrictions.eq("id", id))
                    .list().get(0);
        } catch (Exception e) {
            t.rollback();
            throw new DatabaseException("Could not find the user with id=" + id);
        }
        return user;
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
                    .add(Restrictions.eq("firstName", firstName))
                    .add(Restrictions.eq("lastName", lastName))
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
