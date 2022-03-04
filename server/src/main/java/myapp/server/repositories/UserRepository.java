package myapp.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import myapp.server.models.User;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate template;

    public static final String SQL_ADD_USER = "insert into user_profile (username, name, email, password) values (?, ?, ?, sha1(?))";
    public static final String SQL_CHECK_USER_EMAIL = "select count(email) as count from user_profile where email=?";

    public boolean addUser(User user) {
        int added = template.update(SQL_ADD_USER, user.getUsername(), user.getName(), user.getEmail(), user.getPassword());
        return added > 0;
    }

    public boolean checkUserEmail(String userEmail) {
        SqlRowSet rs = template.queryForRowSet(SQL_CHECK_USER_EMAIL, userEmail);
        int count = 0;
        while(rs.next()) {
            count = rs.getInt("count");
        }
        if(count == 0) {
            return true;
        } else {
            return false;
        } 
    }
}
