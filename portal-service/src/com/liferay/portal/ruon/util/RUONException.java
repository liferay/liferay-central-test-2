package com.liferay.portal.ruon.util;

/**
 * Created by IntelliJ IDEA.
 * User: Murali
 * Date: Jul 7, 2008
 * Time: 10:09:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class RUONException extends Exception {

    private static final long serialVersionUID = 1L;
    private String errorCode = null;

    public RUONException(String errorCode, String errorMsg, Exception ex) {
        super(errorMsg, ex);
        this.errorCode = errorCode;


    }

    public RUONException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;

    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public RUONException(Exception ex){
        super(ex);
    }

}