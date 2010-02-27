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

package com.liferay.counter.model;

import com.liferay.portal.kernel.concurrent.CompeteLatch;

/**
 * <a href="CounterRegister.java.html"><b><i>View Source</i></b></a>
 *
 * @author Harry Mark
 * @author Shuyang Zhou
 */
public class CounterRegister {

	public CounterRegister(
		String name, long rangeMin, long rangeMax, int rangeSize) {

		_name = name;
		_rangeSize = rangeSize;
		_holder = new CounterHolder(rangeMin, rangeMax);
		_latch = new CompeteLatch();
	}

	public CompeteLatch getCompeteLatch() {
		return _latch;
	}

	public CounterHolder getCounterHolder() {
		return _holder;
	}

	public String getName() {
		return _name;
	}

	public int getRangeSize() {
		return _rangeSize;
	}

	public void setCounterHolder(CounterHolder holder) {
		_holder = holder;
	}

	public void setName(String name) {
		_name = name;
	}

	private volatile CounterHolder _holder;
	private final CompeteLatch _latch;
	private String _name;
	private final int _rangeSize;

}