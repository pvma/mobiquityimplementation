package com.mobiquity.exception;

public class APIWeightException extends Exception {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public APIWeightException(String message, Exception e) {
    super(message, e);
  }

  public APIWeightException(String message) {
    super(message);
  }
}
