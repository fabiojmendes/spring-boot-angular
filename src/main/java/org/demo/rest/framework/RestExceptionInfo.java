/**
 *
 */
package org.demo.rest.framework;

import javax.servlet.http.HttpServletRequest;

public class RestExceptionInfo {

	private Class<?> exceptionType;

	private String message;

	private String url;

	private String method;

	/**
	 * @param e
	 */
	public RestExceptionInfo(Exception e) {
		this.exceptionType = e.getClass();
		this.message = e.getMessage();
	}

	/**
	 * @param e
	 */
	public RestExceptionInfo(Exception e, HttpServletRequest request) {
		this(e);
		this.url = request.getRequestURI();
		this.method = request.getMethod();
	}

	/**
	 * @return the exceptionType
	 */
	public Class<?> getExceptionType() {
		return exceptionType;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}
}
