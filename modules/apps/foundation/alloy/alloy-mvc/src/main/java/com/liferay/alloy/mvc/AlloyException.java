/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.alloy.mvc;

/**
 * @author Brian Wing Shun Chan
 */
public class AlloyException extends Exception {

	public AlloyException() {
	}

	public AlloyException(String msg) {
		super(msg);
	}

	public AlloyException(String msg, boolean log) {
		super(msg);

		this.log = log;
	}

	public AlloyException(String msg, Object[] arguments) {
		super(msg);

		this.arguments = arguments;
	}

	public AlloyException(String msg, Object[] arguments, boolean log) {
		super(msg);

		this.arguments = arguments;
		this.log = log;
	}

	public AlloyException(
		String msg, Object[] arguments, boolean log, Throwable cause) {

		super(msg, cause);

		this.arguments = arguments;
		this.log = log;
	}

	public AlloyException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public AlloyException(Throwable cause) {
		super(cause);
	}

	protected Object[] arguments;
	protected boolean log = true;

}