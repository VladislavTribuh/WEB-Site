package by.bsuir.cs.web;

import by.bsuir.cs.dao.DataAccessObject;
import by.bsuir.cs.dao.exception.WebSiteDataAccessException;
import by.bsuir.cs.model.Book;
import by.bsuir.cs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Created by Danilau_MO on 19.12.2016.
 */

@Controller
@SessionAttributes({"logged", "password", "email", "user_id"})
public class SiteController {

    @Autowired
    private DataAccessObject dataAccessObject;

    @PostMapping("/accountPage")
    public String signIn(@RequestParam(value = "email") String email, @RequestParam(value = "pass") String password, Model model) {
        if (email.length() == 0 || password.length() == 0) {
            model.addAttribute(ModelAttributeName.EMPTY_INPUT, true);
            return PageTemplateName.SIGN_IN;
        }

        try {
            User user = dataAccessObject.signIn(email, password);

            model.addAttribute(ModelAttributeName.EMAIL, user.getEmail());
            model.addAttribute(ModelAttributeName.LOGIN, user.getLogin());
            model.addAttribute(ModelAttributeName.PASSWORD, user.getPassword());
            model.addAttribute(ModelAttributeName.LOGGED, true);
            model.addAttribute(ModelAttributeName.USER_ID, user.getId());

            getOrders(user.getId(), model);
            model.addAttribute(ModelAttributeName.PROFILE, true);

            return PageTemplateName.ACCOUNT_PAGE;
        } catch (WebSiteDataAccessException ex) {
            model.addAttribute(ModelAttributeName.FAILURE, true);
            return PageTemplateName.SIGN_IN;
        }
    }

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute(ModelAttributeName.BOOKS_LIST, dataAccessObject.getBooks());
        return PageTemplateName.INDEX;
    }

    @RequestMapping("/logOut")
    public String logOut(Model model) {
        model.addAttribute(ModelAttributeName.LOGGED, false);
        return index(model);
    }

    @RequestMapping("/signIn")
    public String signIn() {
        return PageTemplateName.SIGN_IN;
    }

    @RequestMapping("/signUp")
    public String signUp() {
        return PageTemplateName.SIGN_UP;
    }

    @RequestMapping("/newAccount")
    public String createAccount(@RequestParam(value = "login") String login, @RequestParam(value = "email") String email,
                                @RequestParam(value = "pass") String password, Model model) {
        int res = dataAccessObject.signUp(login, password, email);
        if (res == 0) {
            model.addAttribute(ModelAttributeName.FAILURE, true);
            return PageTemplateName.SIGN_UP;
        } else {
            model.addAttribute(ModelAttributeName.LOGGED, true);
            return signIn(email, password, model);
        }
    }

    @RequestMapping("/bookInfo")
    public String getBook(@RequestParam(value = "id") Integer id, Model model) {
        model.addAttribute(ModelAttributeName.BOOK, dataAccessObject.getBook(id));
        return PageTemplateName.BOOK;
    }

    @RequestMapping("/newOrder")
    public String createOrder(@RequestParam(value = "user_id") Integer userId,@RequestParam(value = "book_id") Integer bookId, Model model) {
        try {
            dataAccessObject.createOrder(userId, bookId);
        } catch (WebSiteDataAccessException ex) {
            model.addAttribute(ModelAttributeName.FAILURE, true);
        }
        getOrders(userId, model);
        model.addAttribute(ModelAttributeName.PROFILE, true);
        return PageTemplateName.ACCOUNT_PAGE;
    }

    @RequestMapping("/gallery")
    public String gallery() {
        return "gallery";
    }

    private void getOrders(Integer userID, Model model) {
        model.addAttribute(ModelAttributeName.BOOKS_LIST, dataAccessObject.getUserOrders(userID));
    }

    private class PageTemplateName {

        static final String ACCOUNT_PAGE = "accountPage";
        static final String INDEX = "index";
        static final String SIGN_IN = "signIn";
        static final String SIGN_UP = "signUp";
        static final String BOOK = "book";
    }

    private class ModelAttributeName {

        static final String LOGGED = "logged";
        static final String EMAIL = "email";
        static final String LOGIN = "login";
        static final String FAILURE = "failure";
        static final String EMPTY_INPUT = "empty_input";
        static final String BOOKS_LIST = "booksList";
        static final String PASSWORD = "password";
        static final String BOOK = "book";
        static final String USER_ID = "user_id";
        static final String PROFILE = "profile";
    }
}
