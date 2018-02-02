package com.nixsolutions.usermanagement.db;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.Transaction;
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
    protected EntityManagerFactory myEmf;

    public EntityManagerFactory getMyEmf() {
        return myEmf;
    }

    public void setMyEmf(EntityManagerFactory myEmf) {
        this.myEmf = myEmf;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public User create(User entity) throws DatabaseException {
        EntityManager em = myEmf.createEntityManager();
        Session s = em.unwrap(Session.class);
        Transaction t = s.getTransaction();
        t.begin();
        em.persist(entity);
        t.commit();
        em.close();
        return entity;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public void update(User entity) throws DatabaseException {
        EntityManager em = myEmf.createEntityManager();
        User user = em.find(User.class, entity.getId());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setDateOfBirth(entity.getDateOfBirth());
        Session s = em.unwrap(Session.class);
        Transaction t = s.getTransaction();
        t.begin();
        em.merge(user);
        t.commit();
        em.close();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public void delete(User entity) throws DatabaseException {
        EntityManager em = myEmf.createEntityManager();
        User user = em.find(User.class, entity.getId());
        Session s = em.unwrap(Session.class);
        Transaction t = s.getTransaction();
        t.begin();
        em.remove(user);
        t.commit();
        em.close();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public User find(Long id) throws DatabaseException {
        EntityManager em = myEmf.createEntityManager();
        User user = em.find(User.class, id);
        if (user == null)
            throw new DatabaseException("Could not find the user with id=" + id);
        em.close();
        return user;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Collection<User> findAll() throws DatabaseException {
        EntityManager em = myEmf.createEntityManager();
        Collection<User> users = em.createQuery("from User").getResultList();
        em.close();
        return users;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Collection<User> find(String firstName, String lastName) throws DatabaseException {
        EntityManager em = myEmf.createEntityManager();
        Collection<User> users = em
                .createQuery("from User WHERE firstName='" + firstName + "' AND lastName='" + lastName + "'")
                .getResultList();
        em.close();
        return users;
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
    }
}
