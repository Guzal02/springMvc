package net.codejava.spring.model;

import java.util.List;

public interface ContactDAO {
    void saveOrUpdate(Contact contact);

    void delete(int contactId);

    Contact get(int contactId);

    List<Contact> list();
}
