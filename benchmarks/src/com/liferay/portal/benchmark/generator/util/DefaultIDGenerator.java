/*
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.benchmark.generator.util;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="DefaultIDGenerator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class DefaultIDGenerator implements IDGenerator {

	public DefaultIDGenerator() {
		_idValues.put(IDGenerator.GENERAL, new Id(IDGenerator.GENERAL, 15000));
		_idValues.put(IDGenerator.RESOURCE,  new Id(IDGenerator.RESOURCE, 500));
		_idValues.put(IDGenerator.PERMISSION, new Id(IDGenerator.PERMISSION, 600));
		_idValues.put(IDGenerator.RESOURCE_CODE, new Id(IDGenerator.RESOURCE_CODE, 700));
		_idValues.put(IDGenerator.SOCIAL_ACTIVITY, new Id(IDGenerator.SOCIAL_ACTIVITY, 800));
	}

	public long generate() {
		return generate(IDGenerator.GENERAL);
	}

	public long generate(String objectType) {
		Id counter = _idValues.get(objectType);
		if (counter == null) {
			throw new IllegalArgumentException("Invalid counter: " + objectType);
		}
		return counter.next();
	}

	public synchronized void add(String name, long startValue) {
		if (!_idValues.containsKey(name)) {
			_idValues.put(name, new Id(name, startValue));
		}
	}

	public Collection<Id> report() {
		return _idValues.values();
	}

	private Map<String, Id> _idValues = new ConcurrentHashMap<String, Id>();
}