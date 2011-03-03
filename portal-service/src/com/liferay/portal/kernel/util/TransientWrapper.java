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

package com.liferay.portal.kernel.util;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class TransientWrapper<T> implements Serializable {

	public TransientWrapper() {
	}

	public TransientWrapper(T transientObject) {
		_transientObject = transientObject;
	}

	public T getTransientObject() {
		return _transientObject;
	}

	public void setTransientObject(T transientObject) {
		_transientObject = transientObject;
	}

	private transient T _transientObject;

}