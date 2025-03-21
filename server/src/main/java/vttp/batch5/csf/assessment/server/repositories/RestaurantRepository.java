package vttp.batch5.csf.assessment.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

// Use the following class for MySQL database
@Repository
public class RestaurantRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    String GET_PASSWORD_SQL = "SELECT password FROM customers WHERE username = ?";
    String storedPassword = null;

    public String getPasswordByUsername(String username) {
        try {
       return jdbcTemplate.queryForObject(GET_PASSWORD_SQL, new Object[]{username}, String.class);
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

}
