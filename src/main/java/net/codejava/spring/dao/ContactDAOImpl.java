package net.codejava.spring.dao;

import net.codejava.spring.model.Contact;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ContactDAOImpl implements ContactDAO {
    private final JdbcTemplate jdbcTemplate;

    public ContactDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int saveOrUpdate(Contact contact) {
        String sql;
        if (contact.getId() > 0) {
            //update
            sql = "UPDATE contact_db.public.contact SET name=?, email=?, address=?, telephone=? WHERE contact_id=?";
        } else {
            //insert
            sql = "INSERT INTO contact_db.public.contact(name, email, address, telephone) VALUES(?, ?, ?, ?)";
        }
        return jdbcTemplate.update(sql, contact.getName(), contact.getEmail(), contact.getAddress(), contact.getTelephone());
    }

    @Override
    public int delete(int contactId) {
        String sql = "DELETE FROM contact_db.public.contact WHERE contact_id=?";
        return jdbcTemplate.update(sql, contactId);
    }

    private Contact getContact(ResultSet rs) throws SQLException {
        Contact contact = new Contact();
        contact.setId(rs.getInt("contact_id"));
        contact.setName(rs.getString("name"));
        contact.setEmail(rs.getString("email"));
        contact.setAddress(rs.getString("address"));
        contact.setTelephone(rs.getString("telephone"));
        return contact;
    }

    @Override
    public Contact get(int contactId) {
        String sql = "SELECT * FROM contact_db.public.contact WHERE contact_id=" + contactId;
        // return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Contact.class));
        // - значительно упрощает код, но взамен снижает производительность
        return jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                return getContact(rs);
            }
            return null;
        });
    }

    @Override
    public List<Contact> list() {
        String sql = "SELECT * FROM contact_db.public.contact";
        // return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Contact.class));
        // - значительно упрощает код, но взамен снижает производительность
        return jdbcTemplate.query(sql, (rs, rowNum) -> getContact(rs));
    }
}
