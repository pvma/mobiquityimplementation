package com.mobiquity.exception;

public class APIIndexException extends Exception {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public APIIndexException(String message, Exception e) {
    super(message, e);
  }

  public APIIndexException(String message) {
    super(message);
  }
}
