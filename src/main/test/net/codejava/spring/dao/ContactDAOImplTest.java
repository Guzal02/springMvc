package net.codejava.spring.dao;

import net.codejava.spring.model.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContactDAOImplTest {
    private DriverManagerDataSource dataSource;
    private ContactDAO dao;

    @BeforeEach
    void SetupBeforeEach() {
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/contact_db");
        dataSource.setUsername("postgres");
        dataSource.setPassword("password");

        dao = new ContactDAOImpl(dataSource);
    }

    @Test
    void saveOrUpdate() {
        Contact contact = new Contact("Steve Jobs", "steve@apple.com", "Cupertino, CA", "1700123456789");
        int result = dao.saveOrUpdate(contact);

        assertTrue(result > 0);
    }

    @Test
    void delete() {
        int id = 2;
        int result = dao.delete(id);

        assertTrue(result > 0);
    }

    @Test
    void get() {
        int id = 3;
        Contact contact = dao.get(id);
        if (contact != null) {
            System.out.println(contact);
        }

        assertNotNull(contact);
    }

    @Test
    void list() {
        List<Contact> listContact = dao.list();

        for (Contact aContact : listContact) {
            System.out.println(aContact);
        }

        assertTrue(!listContact.isEmpty());
    }
}