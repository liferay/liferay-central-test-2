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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.CacheMode;

/**
 * <a href="CacheModeTranslator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CacheModeTranslator {

	public static org.hibernate.CacheMode translate(CacheMode cacheMode) {
		if (cacheMode == CacheMode.GET) {
			return org.hibernate.CacheMode.GET;
		}
		else if (cacheMode == CacheMode.IGNORE) {
			return org.hibernate.CacheMode.IGNORE;
		}
		else if (cacheMode == CacheMode.NORMAL) {
			return org.hibernate.CacheMode.NORMAL;
		}
		else if (cacheMode == CacheMode.PUT) {
			return org.hibernate.CacheMode.PUT;
		}
		else if (cacheMode == CacheMode.REFRESH) {
			return org.hibernate.CacheMode.REFRESH;
		}
		else {
			return org.hibernate.CacheMode.parse(cacheMode.getName());
		}
	}

}