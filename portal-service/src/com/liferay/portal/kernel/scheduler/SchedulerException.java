/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.spring.aop.Swallowable;

/**
 * @author Michael C. Han
 * @author Bruno Farache
 */
public class SchedulerException extends PortalException implements Swallowable {

	public SchedulerException() {
		this(false);
	}

	public SchedulerException(boolean swallowable) {
		super();
		_swallowable = swallowable;
	}

	public SchedulerException(String msg) {
		this(msg, false);
	}

	public SchedulerException(String msg, boolean swallowable) {
		super(msg);
		_swallowable = swallowable;
	}

	public SchedulerException(String msg, Throwable cause) {
		this(msg, cause, false);
	}

	public SchedulerException(
		String msg, Throwable cause, boolean swallowable) {
		super(msg, cause);
		_swallowable = swallowable;
	}

	public SchedulerException(Throwable cause) {
		this(cause, false);
	}

	public SchedulerException(Throwable cause, boolean swallowable) {
		super(cause);
		_swallowable = swallowable;
	}

	public boolean isSwallowable() {
		return _swallowable;
	}

	private final boolean _swallowable;

}