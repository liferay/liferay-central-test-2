/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.test.spring.remoting;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.test.ResultPrinter;
import com.liferay.test.spring.remoting.portal.PortalHttpTest;

import junit.framework.Test;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

/**
 * <a href="SpringRemotingTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class SpringRemotingTests {

	public static void main(String[] args) {
		if (args.length > 0) {
			_protocol = args[0];

			if (Validator.isNotNull(_protocol)) {
				_protocol =
					_protocol.substring(0, 1).toUpperCase() +
						_protocol.substring(
							1, _protocol.length()).toLowerCase();
			}
		}

		TestRunner runner = new TestRunner(new ResultPrinter(System.out));

		runner.doRun(suite());
	}

	public static Test suite() {
		TestSuite suite = new TestSuite();

		suite.addTestSuite(PortalHttpTest.class);

		return suite;
	}

	public static String getProtocol() {
		return _protocol;
	}

	private static String _protocol = "Http";

}