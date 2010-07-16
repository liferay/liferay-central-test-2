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

package com.liferay.portal.cache;

import com.liferay.portal.kernel.concurrent.IncreasableEntry;
import com.liferay.portal.kernel.counter.Counter;
import com.liferay.portal.kernel.util.StringUtil;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Zsolt Berentey
 */
public class IncreasableCounterEntry
	extends IncreasableEntry<String, Counter<?>> {

	public IncreasableCounterEntry(
		MethodInterceptor nextInterceptor, MethodInvocation methodInvocation,
		Object id, Counter<?> value) {

		super(StringUtil.merge(
			new Object[] {
				methodInvocation.getMethod().getClass().getName(),
				methodInvocation.getMethod().getName(),
				id
			}), value);

		_id = id;
		_methodInvocation = methodInvocation;
		_nextInterceptor = nextInterceptor;
	}

	@Override
	public Counter<?> doIncrease(
		Counter<?> originalValue, Counter<?> deltaValue) {

		return originalValue.incrementByCreate(deltaValue);
	}

	public Object getId() {
		return _id;
	}

	public MethodInterceptor getNextInterceptor() {
		return _nextInterceptor;
	}

	public MethodInvocation getMethodInvocation() {
		return _methodInvocation;
	}

	private Object _id;
	private MethodInterceptor _nextInterceptor;
	private MethodInvocation _methodInvocation;

}