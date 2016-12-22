package by.bsuir.cs.dao;

import by.bsuir.cs.dao.exception.WebSiteDataAccessException;
import by.bsuir.cs.model.Book;
import by.bsuir.cs.model.User;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

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

    public User signIn(String login, String password) throws WebSiteDataAccessException {
        try {
            final String SING_IN_QUERY = "SELECT * FROM user_info WHERE email = ? AND password = ?";
            return this.jdbcTemplate.queryForObject(SING_IN_QUERY, UserMapper.getInstance(), login, password);
        } catch (EmptyResultDataAccessException ex) {
            throw new WebSiteDataAccessException(ex);
        }
    }

    public int signUp(String login, String password, String email) {
        final String SING_UP_QUERY = "INSERT INTO user_info (login, email, password) VALUES (?, ?, ?)";
        return this.jdbcTemplate.update(SING_UP_QUERY, login, email, password);
    }

    public int addBook(String name, String author, String desc, String posterPath, int year, int num) {
        final String ADD_BOOK_QUERY = "INSERT INTO book_info (name, author, \"desc\", \"posterPath\", year, number)\n" +
                "VALUES (?, ?, ?, ?, ?, ?);";
        return this.jdbcTemplate.update(ADD_BOOK_QUERY);
    }

    public List<Book> getBooks() {
        final String GET_BOOKS_QUERY = "SELECT * FROM book_info";
        List<Book> list = this.jdbcTemplate.query(GET_BOOKS_QUERY, BooksListMapper.INSTANCE);
        return list;
    }

    public Book getBook(Integer id) {
        final String GET_BOOK_BY_ID_QUERY = "SELECT * FROM book_info WHERE id = ?";
        Book book = this.jdbcTemplate.queryForObject(GET_BOOK_BY_ID_QUERY, BookMapper.getInstance(), id);
        return book;
    }

    public int createOrder(Integer userId, Integer bookId) throws WebSiteDataAccessException {
        try {
            final String CREATE_ORDER_QUERY = "INSERT INTO orders (user_id, book_id) VALUES (?,?)";
            return this.jdbcTemplate.update(CREATE_ORDER_QUERY, userId, bookId);
        } catch (DuplicateKeyException ex) {
            throw new WebSiteDataAccessException(ex);
        }
    }

    public List<Book> getUserOrders(Integer userId) {
        final String GET_USER_ORDERS = "SELECT * FROM book_info WHERE id IN (SELECT book_id FROM orders WHERE user_id=?)";
        return this.jdbcTemplate.query(GET_USER_ORDERS, BooksListMapper.INSTANCE, userId);
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
            user.setId(resultSet.getInt("id"));
            return user;
        }
    }

    private static final class BookMapper implements RowMapper<Book> {

        private static final BookMapper INSTANCE = new BookMapper();

        private BookMapper() {
        }

        static BookMapper getInstance() {
            return INSTANCE;
        }

        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            Book book = new Book();
            book.setName(resultSet.getString("name"));
            book.setAuthor(resultSet.getString("author"));
            book.setDescription(resultSet.getString("desc"));
            book.setNumber(resultSet.getInt("number"));
            book.setPosterPath(resultSet.getString("posterPath"));
            book.setYear(resultSet.getInt("year"));
            book.setId(resultSet.getInt("id"));
            return book;
        }
    }

    private static final class BooksListMapper implements ResultSetExtractor<List<Book>> {

        private static final BooksListMapper INSTANCE = new BooksListMapper();

        private BooksListMapper() {
        }

        private final BookMapper BOOK_MAPPER = BookMapper.getInstance();

        public List<Book> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            List<Book> bookList = new LinkedList<Book>();
            while (resultSet.next()) {
                bookList.add(BOOK_MAPPER.mapRow(resultSet, resultSet.getRow()));
            }
            return bookList;
        }
    }
}
