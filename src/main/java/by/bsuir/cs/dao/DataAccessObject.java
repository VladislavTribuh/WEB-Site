package by.bsuir.cs.dao;

import by.bsuir.cs.dao.exception.DataAccessException;
import by.bsuir.cs.model.User;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Maksim Danilov on 17.12.2016.
 * WEB-site
 */
@Repository
public class DataAccessObject {

    private JdbcTemplate jdbcTemplate;

    @Required
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User signIn(String login, String password) throws DataAccessException {
        try {
            final String SING_IN_QUERY = "select * from user_info where email = ? AND password = ?";
            return this.jdbcTemplate.queryForObject(SING_IN_QUERY, UserMapper.getInstance(), login, password);
        } catch (EmptyResultDataAccessException ex) {
            throw new DataAccessException(ex);
        }
    }

    public int signUp(String login, String password, String email) {
        final String SING_UP_QUERY = "insert into user_info (login, email, password) values (?, ?, ?)";
        return this.jdbcTemplate.update( SING_UP_QUERY, login, email, password);
    }

    private static final class UserMapper implements RowMapper<User> {

        private final static UserMapper INSTANCE = new UserMapper();

        private UserMapper() {
        }

        static UserMapper getInstance() {
            return INSTANCE;
        }

        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setEmail(resultSet.getString("email"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            return user;
        }
    }
}
