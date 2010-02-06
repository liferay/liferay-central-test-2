/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.test.TestCase;
import com.liferay.portal.util.InitUtil;
import com.liferay.util.PwdGenerator;

import java.util.Date;
import java.util.Random;

/**
 * <a href="BaseTestCase.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Ganesh Ram
 */
public class BaseTestCase extends TestCase {

	public BaseTestCase() {
		if (System.getProperty("external-properties") == null) {
			System.setProperty("external-properties", "portal-test.properties");
		}

		InitUtil.initWithSpring();
	}

	protected Date nextDate() throws Exception {
		return new Date();
	}

	protected double nextDouble() throws Exception {
		return CounterLocalServiceUtil.increment();
	}

	protected int nextInt() throws Exception {
		return (int)CounterLocalServiceUtil.increment();
	}

	protected long nextLong() throws Exception {
		return CounterLocalServiceUtil.increment();
	}

	protected boolean randomBoolean() throws Exception {
		return _random.nextBoolean();
	}

	protected String randomString() throws Exception {
		return PwdGenerator.getPassword();
	}

	private Random _random = new Random();

}