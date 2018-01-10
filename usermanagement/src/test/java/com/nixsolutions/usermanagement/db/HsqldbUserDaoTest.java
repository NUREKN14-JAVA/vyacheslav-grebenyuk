package com.nixsolutions.usermanagement.db;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Collection;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.junit.Test;

import com.nixsolutions.usermanagement.User;

public class HsqldbUserDaoTest extends DatabaseTestCase {
    private HsqldbUserDao dao;
    private ConnectionFactory connectionFactory;

    private static final int TEST_CREATE_DAY = 1;
    private static final int TEST_CREATE_MONTH = Calendar.JANUARY;
    private static final int TEST_CREATE_YEAR = 2010;
    private static final String TEST_LASTNAME = "Gates";
    private static final String TEST_NAME = "Bill";
    private static final Long TEST_FIND_DELETE_ID = 1000L;
    private static final String UPDATED_NAME = "Helen";
    private static final Long WRONG_ID = 10000L;

    protected void setUp() throws Exception {
        super.setUp();
        dao = new HsqldbUserDao(connectionFactory);
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
        User userToCheck = dao.create(user);
        assertNotNull(userToCheck);
        assertNotNull(userToCheck.getId());
        assertEquals(user.getFirstName(), userToCheck.getFirstName());
        assertEquals(user.getLastName(), userToCheck.getLastName());
        assertEquals(user.getDateOfBirth(), userToCheck.getDateOfBirth());
    }

    /**
     * Testing method for {@link HsqldbUserDAO#find(Long)}
     * 
     * @throws DatabaseException
     */
    @Test
    public void testFind() throws DatabaseException {
        User userToCheck = dao.find(TEST_FIND_DELETE_ID);
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
            dao.find(WRONG_ID);
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
        Collection<User> items = dao.findAll();
        assertNotNull("Collection is null", items);
        assertEquals("Collection size doesn't match.", 2, items.size());
    }

    /**
     * Testing method for {@link HsqldbUserDAO#delete(User)}
     * 
     * @throws DatabaseException
     */
    @Test
    public void testDelete() {
        User deletedUser = new User();
        deletedUser.setId(TEST_FIND_DELETE_ID);
        try {
            dao.delete(deletedUser);
            dao.find(TEST_FIND_DELETE_ID);
            fail();
        } catch (DatabaseException e) {
            assertThat(e.getMessage(), containsString(TEST_FIND_DELETE_ID.toString()));
        }
    }

    /**
     * Testing method for {@link HsqldbUserDAO#update(User)}
     * 
     * @throws DatabaseException
     */
    @Test
    public void testUpdateUser() throws DatabaseException {
        User glenElg = dao.find(TEST_FIND_DELETE_ID);
        assertNotNull(glenElg);
        glenElg.setFirstName(UPDATED_NAME);
        dao.update(glenElg);
        User updatedUser = dao.find(TEST_FIND_DELETE_ID);
        assertEquals(glenElg.getFirstName(), updatedUser.getFirstName());
    }

    @Test
    public void testFindByName() throws DatabaseException {
        Collection<User> users = dao.find(TEST_NAME, TEST_LASTNAME);
        assertNotNull(users);
        assertEquals(1, users.size());
    }

    protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = new ConnectionFactoryImpl("org.hsqldb.jdbcDriver", "jdbc:hsqldb:file:db/usermanagement",
                "sa", "");
        return new DatabaseConnection(connectionFactory.createConnection());
    }

    protected IDataSet getDataSet() throws Exception {
        IDataSet dataSet = new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));
        return dataSet;
    }

}
