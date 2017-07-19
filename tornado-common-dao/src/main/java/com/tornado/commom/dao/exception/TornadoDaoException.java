package com.tornado.commom.dao.exception;

/**
 * 统一DAO层异常类
 * 
 * @author dante
 *
 */
public class TornadoDaoException extends Exception {

	private static final long serialVersionUID = -2253964864226758398L;

	/**
	 * 构造函数
	 */
	public TornadoDaoException() {
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
	public TornadoDaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * 构造函数
	 * 
	 * @param message
	 * @param cause
	 */
	public TornadoDaoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 构造函数
	 * 
	 * @param message
	 */
	public TornadoDaoException(String message) {
		super(message);
	}

	/**
	 * 构造函数
	 * 
	 * @param cause
	 */
	public TornadoDaoException(Throwable cause) {
		super(cause);
	}

}
