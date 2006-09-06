/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.client.portal.service.http;

import com.liferay.test.TestProps;

import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;

/**
 * <a href="BaseSoapTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class BaseSoapTest extends TestCase {

	protected URL getURL(String serviceName) throws MalformedURLException {
		return getURL(serviceName, true);
	}

	protected URL getURL(String serviceName, boolean authenticated)
		throws MalformedURLException {

		String url = null;

		if (authenticated) {
			String userId = TestProps.get("soap.user.id");
			String password = TestProps.get("soap.password");

			url =
				"http://" + userId + ":" + password +
					"@localhost:8080/tunnel-web/secure/axis/" + serviceName;
		}
		else {
			url = "http://localhost:8080/tunnel-web/axis/" + serviceName;
		}

		return new URL(url);
	}

}