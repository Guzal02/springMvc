package net.codejava.spring.model;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ContactDAOImpl implements ContactDAO {
    private JdbcTemplate jdbcTemplate;

    public ContactDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveOrUpdate(Contact contact) {
        if (contact.getId() > 0) {
            //update
            String sql = "UPDATE contact_db.public.contact SET name=?, email=?, address=?, telephone=? WHERE contact_id=?";
            jdbcTemplate.update(sql, contact.getName(), contact.getEmail(), contact.getAddress(), contact.getTelephone());
        } else {
            //insert
            String sql = "INSERT INTO contact_db.public.contact(name, email, address, telephone) VALUES(?, ?, ?, ?)";
            jdbcTemplate.update(sql, contact.getName(), contact.getEmail(), contact.getAddress(), contact.getTelephone());
        }
    }

    @Override
    public void delete(int contactId) {
        String sql = "DELETE FROM contact_db.public.contact WHERE contact_id=?";
        jdbcTemplate.update(sql, contactId);
    }

    @Override
    public Contact get(int contactId) {
        String sql = "SELECT * FROM contact_db.public.contact WHERE contact_id=" + contactId;
        // return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Contact.class));
        // - значительно упрощает код, но взамен снижает производительность
        return jdbcTemplate.query(sql, new ResultSetExtractor<Contact>() {
            @Override
            public Contact extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    Contact contact = new Contact();
                    contact.setId(rs.getInt("contact_id"));
                    contact.setName(rs.getString("name"));
                    contact.setEmail(rs.getString("email"));
                    contact.setAddress(rs.getString("address"));
                    contact.setTelephone(rs.getString("telephone"));
                    return contact;
                }
                return null;
            }
        });
    }

    @Override
    public List<Contact> list() {
        String sql = "SELECT * FROM contact_db.public.contact";
        // return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Contact.class));
        // - значительно упрощает код, но взамен снижает производительность
        List<Contact> contactList = jdbcTemplate.query(sql, new RowMapper<Contact>() {
            @Override
            public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
                Contact contact = new Contact();
                contact.setId(rs.getInt("contact_id"));
                contact.setName(rs.getString("name"));
                contact.setEmail(rs.getString("email"));
                contact.setAddress(rs.getString("address"));
                contact.setTelephone(rs.getString("telephone"));
                return contact;
            }
        });
        return contactList;
    }
}
