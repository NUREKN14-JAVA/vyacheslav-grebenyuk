package com.nixsolutions.usermanagement.db;

import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nixsolutions.usermanagement.User;

@Repository
@Scope("prototype")
public class HibernateUserDao implements Dao<User> {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public User create(User entity) throws DatabaseException {
        Session session = getSessionFactory().getCurrentSession();
        session.persist(entity);
        return entity;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public void update(User entity) throws DatabaseException {
        Session session = getSessionFactory().getCurrentSession();
        session.update(entity);
    }

    @Override
    @Transactional
    public void delete(User entity) throws DatabaseException {
        Session session = getSessionFactory().getCurrentSession();
        session.delete(entity);
    }

    @Override
    @Transactional
    public User find(Long id) throws DatabaseException {
        Session session = getSessionFactory().getCurrentSession();
        User user;
        try {
            user = (User) session.createCriteria(User.class).add(Restrictions.eq("id", id)).list().get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new DatabaseException("Could not find the user with id="
                    + id);
        }
        return user;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public Collection<User> findAll() throws DatabaseException {
        Session session = getSessionFactory().getCurrentSession();
        List<User> users = (List<User>) session.createCriteria(User.class).list();
        return users;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public Collection<User> find(String firstName, String lastName) throws DatabaseException {
        Session session = getSessionFactory().getCurrentSession();
        List<User> users = (List<User>) session.createCriteria(User.class).add(Restrictions.eq("firstName", firstName))
                    .add(Restrictions.eq("lastName", lastName)).list();
        return users;
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
    }
}
