/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.counter.service;

import com.liferay.portal.kernel.exception.SystemException;

import java.util.List;

/**
 * <a href="CounterService.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface CounterService {

	public List<String> getNames() throws SystemException;

	public long increment() throws SystemException;

	public long increment(String name) throws SystemException;

	public long increment(String name, int size) throws SystemException;

	public void rename(String oldName, String newName) throws SystemException;

	public void reset(String name) throws SystemException;

	public void reset(String name, long size) throws SystemException;

}