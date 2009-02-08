/*
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.benchmark.generator.db;

import java.util.Map;
import java.util.HashMap;

/**
 * <a href="DefaultIDGenerator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class DefaultIDGenerator implements IDGenerator {

	public DefaultIDGenerator() {
		idValues.put(IDGenerator.GENERAL, 15000);
		idValues.put(IDGenerator.RESOURCE, 500);
		idValues.put(IDGenerator.PERMISSION, 600);
		idValues.put(IDGenerator.RESOURCE_CODE, 700);
		idValues.put(IDGenerator.SOCIAL_ACTIVITY, 800);
	}

	public long generate() {
		return generate(IDGenerator.GENERAL);
	}

	public synchronized long generate(String objectType) {
		Integer counter = idValues.get(objectType);
		if (counter == null) {
			throw new IllegalArgumentException("Invalid counter: " + objectType);
		}
		counter++;
		idValues.put(objectType, counter);
		return counter;
	}

	public String report() {
		return "Current id counter values: " + idValues.toString();
	}

	private Map<String, Integer> idValues = new HashMap<String, Integer>();
}
