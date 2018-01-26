package com.nixsolutions.usermanagement.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nixsolutions.usermanagement.User;
import com.nixsolutions.usermanagement.db.DaoFactory;
import com.nixsolutions.usermanagement.db.DatabaseException;

/**
 * @author mak
 */
public class AddServlet extends EditServlet {
    private static final long serialVersionUID = -1439944580914816594L;

    /**
     * 
     */
    @Override
    protected void processUser(User user) throws DatabaseException {
        DaoFactory.getInstance().getUserDao().create(user);
    }
    /**
     * 
     */
    @Override
    protected void showPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/add.jsp").forward(req, resp);
    }
}
