package com.mobiquity.exception;

public class APIItemParseException extends Exception {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public APIItemParseException(String message, Exception e) {
    super(message, e);
  }

  public APIItemParseException(String message) {
    super(message);
  }
}
