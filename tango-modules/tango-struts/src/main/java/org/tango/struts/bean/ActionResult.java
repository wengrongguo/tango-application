/*
 * Copyright (c) 2012. tAngo
 * 	Email : org.java.tango@gmail.com
 */
package org.tango.struts.bean;

public class ActionResult {

    private Object result;

    private String message;

    public ActionResult() {
    }

    public ActionResult(Object result, String message) {
        this.result = result;
        this.message = message;
    }

    public final static ActionResult getActionResult(Object result) {
        return getActionResult(result, null);
    }

    public final static ActionResult getActionResult(Object result, String message) {
        return new ActionResult(result, message);
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "result=" + result +
                ", message='" + message + '\'' +
                '}';
    }
}