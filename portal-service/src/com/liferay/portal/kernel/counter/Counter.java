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

/**
 * <a href="Counter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Zsolt Berentey
 */
public interface Counter<T> {

	public void increment(T delta);

	public Counter<T> incrementByCreate(T delta);

	public Counter<T> incrementByCreate(Counter<?> delta);

	public void decrement(T delta);

	public Counter<T> decrementByCreate(T delta);

	public Counter<T> decrementByCreate(Counter<?> delta);

	public T getValue();

	public void setValue(T value);

}