package net.codejava.spring.dao;

import net.codejava.spring.model.Contact;

import java.util.List;

public interface ContactDAO {
    int saveOrUpdate(Contact contact);

    int delete(int contactId);

    Contact get(int contactId);

    List<Contact> list();
}
