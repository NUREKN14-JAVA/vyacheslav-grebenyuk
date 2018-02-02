package com.nixsolutions.usermanagement.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nixsolutions.usermanagement.User;
import com.nixsolutions.usermanagement.db.Dao;

@Controller
@RequestMapping("/users")
public class UserController {
    final static Logger logger = Logger.getLogger(UserController.class); 
    
    @Autowired
    private Dao<User> userDao;
    
    @RequestMapping(value = "/browse.html")
    public ModelAndView browse() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("browse");
        List<User> users = new ArrayList<>();
        users.addAll(userDao.findAll());
        logger.debug("users: " + users);
        mav.addObject(users);
        return mav;
    }

    @RequestMapping(value = "/add.html", method = RequestMethod.GET)
    public String add(Model model) {
        logger.debug("new user");
        model.addAttribute(new User());
        return "add";
    }
    
    @RequestMapping(value = "/add.html", method = RequestMethod.POST)
    public String save(User user) throws Exception {
        logger.debug("adding...");
        userDao.create(user);
        logger.debug("add: " + user);
        return "redirect:browse.html";
    }

    @RequestMapping(value = "/delete.html", method = RequestMethod.GET)
    public String delete(Long id) throws Exception{
        logger.debug("delete user");
        User user = userDao.find(id);
        userDao.delete(user);
        logger.debug("deleted: " + user);
        return "redirect:browse.html";        
    }
    
    @RequestMapping(value = "/edit.html", method = RequestMethod.GET)
    public String edit(Model model, Long id) throws Exception{
        logger.debug("update user");
        User user = userDao.find(id);
        model.addAttribute(user);
        return "add";
    }
    
    @RequestMapping(value = "/edit.html", method = RequestMethod.POST)
    public String update(User user) throws Exception {
        logger.debug("updating...");
        userDao.update(user);
        logger.debug("updated: " + user);
        return "redirect:browse.html";
    }
   
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(
                Date.class,
                new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), false));
    }
    
}
