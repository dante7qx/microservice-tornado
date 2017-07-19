package com.tornado.common.api.exception;

/**
 * 统一Rest API异常类
 * 
 * @author dante
 *
 */
public class TornadoAPIServiceException extends Exception {

	private static final long serialVersionUID = -2253964864226758398L;

	/**
	 * 构造函数
	 */
	public TornadoAPIServiceException() {
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public TornadoAPIServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * 构造函数
	 * 
	 * @param message
	 * @param cause
	 */
	public TornadoAPIServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 构造函数
	 * 
	 * @param message
	 */
	public TornadoAPIServiceException(String message) {
		super(message);
	}

	/**
	 * 构造函数
	 * 
	 * @param cause
	 */
	public TornadoAPIServiceException(Throwable cause) {
		super(cause);
	}

}
