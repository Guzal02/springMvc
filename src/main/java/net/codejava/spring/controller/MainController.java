package net.codejava.spring.controller;

import net.codejava.spring.dao.ContactDAO;
import net.codejava.spring.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ContactDAO contactDAO;

    @RequestMapping(value = "/")
    public ModelAndView contactList(ModelAndView model) {
        List<Contact> contactList = contactDAO.list();
        model.addObject("contactList", contactList);
        model.setViewName("index");

        return model;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newContact(ModelAndView model) {
        Contact newContact = new Contact();
        model.addObject("contact", newContact);
        model.setViewName("contact_form");

        return model;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveContact(@ModelAttribute Contact contact) {
        contactDAO.saveOrUpdate(contact);

        return new ModelAndView("redirect:/");
    }


    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editContact(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Contact contact = contactDAO.get(id);

        ModelAndView model = new ModelAndView("contact_form");
        model.addObject("contact", contact);

        return model;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView deleteContact(@RequestParam int id) {
        contactDAO.delete(id);

        return new ModelAndView("redirect:/");
    }
}
