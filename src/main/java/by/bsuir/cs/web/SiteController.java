package by.bsuir.cs.web;

import by.bsuir.cs.dao.DataAccessObject;
import by.bsuir.cs.dao.exception.DataAccessException;
import by.bsuir.cs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Danilau_MO on 19.12.2016.
 */

@Controller
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
            model.addAttribute(ModelAttributeName.LOGGED, true);
            return PageTemplateName.ACCOUNT_PAGE;
        } catch (DataAccessException ex) {
            model.addAttribute(ModelAttributeName.FAILURE, true);
            return PageTemplateName.SIGN_IN;
        }
    }

    @RequestMapping("/")
    public String index() {
        return PageTemplateName.INDEX;
    }

    @RequestMapping("/signIn")
    public String signIn() {
        return PageTemplateName.SIGN_IN;
    }

    @RequestMapping("/signUp")
    public String signUp() {
        return PageTemplateName.SIGN_UP;
    }

    private class PageTemplateName {

        static final String ACCOUNT_PAGE = "accountPage";
        static final String INDEX = "index";
        static final String SIGN_IN = "signIn";
        static final String SIGN_UP = "signUp";

    }

    public class ModelAttributeName {

        public static final String LOGGED = "logged";
        public static final String EMAIL = "email";
        public static final String LOGIN = "login";
        public static final String FAILURE = "failure";
        public static final String EMPTY_INPUT = "empty_input";
    }
}
