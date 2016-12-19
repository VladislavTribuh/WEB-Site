package by.bsuir.cs.dao.exception;

import by.bsuir.cs.dao.DataAccessObject;

/**
 * Created by Maksim Danilov on 19.12.2016.
 * WEB-site
 */
public class DataAccessException extends Exception {

    private static final String MESSAGE = "Data Access Exception";

    public DataAccessException(Exception ex) {
        super(DataAccessException.MESSAGE, ex);
    }
}
