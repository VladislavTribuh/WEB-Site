package by.bsuir.cs.dao.exception;

/**
 * Created by Maksim Danilov on 19.12.2016.
 * WEB-site
 */
public class WebSiteDataAccessException extends Exception {

    private static final String MESSAGE = "Data Access Exception";

    public WebSiteDataAccessException(Exception ex) {
        super(WebSiteDataAccessException.MESSAGE, ex);
    }
}
