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

package com.liferay.portal.kernel.counter;

import com.liferay.portal.kernel.concurrent.IncreasableEntry;
import com.liferay.portal.kernel.util.StringPool;

import java.lang.reflect.Method;

/**
 * <a href="IncreasableCounterEntry.java.html"><b><i>View Source</i></b></a>
 *
 * @author Zsolt Berentey
 */
public class IncreasableCounterEntry
	extends IncreasableEntry<String, Counter<?>> {

	public IncreasableCounterEntry(
		String service, Method updateMethod, Object id, Counter<?> value) {

		super(service + StringPool.POUND + updateMethod.getName() +
			StringPool.POUND + id, value);

		_service = service;
		_updateMethod = updateMethod;
		_id = id;
	}

	@Override
	public Counter<?> doIncrease(
		Counter<?> originalValue, Counter<?> deltaValue) {

		return originalValue.incrementByCreate(deltaValue);
	}

	public Object getId() {
		return _id;
	}

	public String getService() {
		return _service;
	}

	public Method getUpdateMethod() {
		return _updateMethod;
	}

	private Object _id;
	private String _service;
	private Method _updateMethod;

}