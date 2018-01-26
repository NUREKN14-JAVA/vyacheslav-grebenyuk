package com.nixsolutions.usermanagement.web;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.nixsolutions.usermanagement.User;

/**
 * @author mak
 */
public class BrowseServletTest extends MockServletTestCase {
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        createServlet(BrowseServlet.class);
    }
    
    @Test
    public void testBrowse() {
        User user = new User(1000L, "John", "Doe", new Date());
        List<User> list = Collections.singletonList(user);
        getMockUserDao().expectAndReturn("findAll", list);
        doGet();
        @SuppressWarnings("unchecked")
        Collection<User> attrUsers = (Collection<User>) getWebMockObjectFactory()
                .getMockSession().getAttribute("users");
        assertNotNull(attrUsers);
        assertSame(list, attrUsers);
    }
    
    @Test
    public void testEdit() {
        User user = new User(1000L, "John", "Doe", new Date());
        getMockUserDao().expectAndReturn("find", 1000L, user);
        addRequestParameter("editButton", "Edit");
        addRequestParameter("id", "1000");
        doPost();
        User userInSession = (User) getWebMockObjectFactory()
                .getMockSession().getAttribute("user");
        assertNotNull(userInSession);
        assertSame(user, userInSession);
    }
    
    @Test
    public void testEditWithoutId() {
        addRequestParameter("editButton", "Edit");
        doPost();
        assertNotNull(getWebMockObjectFactory().getMockRequest().getAttribute("error"));
    }

    @Test
    public void testDetails() {
        User user = new User(1000L, "John", "Doe", new Date());
        getMockUserDao().expectAndReturn("find", 1000L, user);
        addRequestParameter("detailsButton", "Details");
        addRequestParameter("id", "1000");
        doPost();
        User userInSession = (User) getWebMockObjectFactory()
                .getMockSession().getAttribute("user");
        assertNotNull(userInSession);
        assertSame(user, userInSession);
    }
    
    @Test
    public void testDetailsWithoutId() {
        addRequestParameter("detailsButton", "Details");
        doPost();
        assertNotNull(getWebMockObjectFactory().getMockRequest().getAttribute("error"));
    }

    @Test
    public void testDelete() {
        User user = new User(1000L, "John", "Doe", new Date());
        getMockUserDao().expectAndReturn("find", 1000L, user);
        getMockUserDao().expect("delete", user);
        addRequestParameter("deleteButton", "Delete");
        addRequestParameter("id", "1000");
        doPost();
        assertNotNull(getWebMockObjectFactory().getMockRequest().getAttribute("message"));
    }
    
    @Test
    public void testDeleteWithoutId() {
        addRequestParameter("deleteButton", "Delete");
        doPost();
        assertNotNull(getWebMockObjectFactory().getMockRequest().getAttribute("error"));
    }
}
