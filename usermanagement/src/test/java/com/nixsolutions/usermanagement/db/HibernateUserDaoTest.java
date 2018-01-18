package com.nixsolutions.usermanagement.db;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nixsolutions.usermanagement.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:context.xml")
public class HibernateUserDaoTest {
    @Autowired
    private Dao<User> userDao;
    
    @Autowired
    private DataSource dataSource;

    private static final int TEST_CREATE_DAY = 1;
    private static final int TEST_CREATE_MONTH = Calendar.JANUARY;
    private static final int TEST_CREATE_YEAR = 2010;
    private static final String TEST_LASTNAME = "Gates";
    private static final String TEST_NAME = "Bill";
    private static final Long TEST_FIND_DELETE_ID = 1000L;
    private static final String UPDATED_NAME = "Helen";
    private static final Long WRONG_ID = 10000L;

    @Before
    public void setUp() throws Exception {
        DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());
    }

    @After
    public void tearDown() throws Exception {
        DatabaseOperation.DELETE_ALL.execute(getConnection(), getDataSet());
    }
    
    /**
     * Testing method for {@link HsqldbUserDAO#find(Long)}
     * 
     * @throws DatabaseException
     */
    @Test
    public void testFind() throws DatabaseException {
        User userToCheck = userDao.find(TEST_FIND_DELETE_ID);
        assertNotNull(userToCheck);
        assertEquals(TEST_NAME, userToCheck.getFirstName());
        assertEquals(TEST_LASTNAME, userToCheck.getLastName());
    }

    /**
     * Testing method for {@link HsqldbUserDAO#find(Long) for user with id that
     * is not present in database.}
     * 
     * @throws DatabaseException
     */
    @Test
    public void testFindMissingUser() {
        try {
            userDao.find(WRONG_ID);
            fail("Exception expected");
        } catch (DatabaseException e) {
            assertThat(e.getMessage(), containsString(WRONG_ID.toString()));
        }
    }

    /**
     * Testing method for {@link HsqldbUserDAO#findAll()}
     * 
     * @throws DatabaseException
     */
    @Test
    public void testFindAll() throws DatabaseException {
        Collection<User> items = userDao.findAll();
        assertNotNull("Collection is null", items);
        assertEquals("Collection size doesn't match.", 2, items.size());
    }

    /**
     * Testing method for {@link HsqldbUserDAO#create(User)}
     * 
     * @throws DatabaseException
     */
    @Test
    public void testCreate() throws DatabaseException {
        User user = new User();
        user.setFirstName("Yevhenii");
        user.setLastName("Baranow");
        Calendar calendar = Calendar.getInstance();
        calendar.set(TEST_CREATE_YEAR, TEST_CREATE_MONTH, TEST_CREATE_DAY);
        user.setDateOfBirth(calendar.getTime());
        assertNull(user.getId());
        User userToCheck = userDao.create(user);
        assertNotNull(userToCheck);
        assertNotNull(userToCheck.getId());
        assertEquals(user.getFirstName(), userToCheck.getFirstName());
        assertEquals(user.getLastName(), userToCheck.getLastName());
        assertEquals(user.getDateOfBirth(), userToCheck.getDateOfBirth());
    }
    
//    @Test
//    public void testDelete() {
//        User deletedUser = new User();
//        deletedUser.setId(TEST_FIND_DELETE_ID);
//        try {
//            userDao.delete(deletedUser);
//            userDao.find(TEST_FIND_DELETE_ID);
//            fail();
//        } catch (DatabaseException e) {
//            assertThat(e.getMessage(), containsString(TEST_FIND_DELETE_ID.toString()));
//        }
//    }
//
//    @Test
//    public void testUpdateUser() throws DatabaseException {
//        User updUser = new User();
//        updUser.setId(TEST_FIND_DELETE_ID);
//        updUser.setFirstName(UPDATED_NAME);
//        updUser.setLastName(TEST_LASTNAME);
//        updUser.setDateOfBirth(new Date());
//        userDao.update(updUser);
//        User foundUser = userDao.find(TEST_FIND_DELETE_ID);
//        assertEquals(UPDATED_NAME, foundUser.getFirstName());
//        assertEquals(TEST_FIND_DELETE_ID, foundUser.getId());
//        assertEquals(TEST_LASTNAME, foundUser.getLastName());
//        assertEquals(TEST_LASTNAME, updUser.getLastName());
//    }
//
//    @Test
//    public void testFindByName() throws DatabaseException {
//        Collection<User> users = userDao.find(TEST_NAME, TEST_LASTNAME);
//        assertNotNull(users);
//        assertEquals(1, users.size());
//    }

    protected IDatabaseConnection getConnection() throws Exception {
        return new DatabaseConnection(dataSource.getConnection());
    }

    protected IDataSet getDataSet() throws Exception {
        IDataSet dataSet = new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));
        return dataSet;
    }

}
