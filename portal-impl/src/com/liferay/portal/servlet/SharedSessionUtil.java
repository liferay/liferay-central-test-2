/**
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

package com.liferay.portal.servlet;

import com.liferay.portal.util.PropsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <a href="SharedSessionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 *
 */
public class SharedSessionUtil {

	public static final String[] SHARED_SESSION_ATTRIBUTES =
		PropsUtil.getArray(PropsUtil.SESSION_SHARED_ATTRIBUTES);

	public static Map<String, Object> getSharedSessionAttributes(
		final HttpServletRequest req) {

		//final Map<String, Object> map = new ConcurrentHashMap<String, Object>();

		final HttpSession ses = req.getSession();
        final SharedSessionAttributeCache cache =
                SharedSessionAttributeCache.getInstance(ses);
        final Map<String, Object> values = cache.getValues();
        if (_log.isDebugEnabled()) {
            _log.debug("Shared Values: " + values);
        }
        return values;

/*
        Enumeration<String> enu = ses.getAttributeNames();

		while (enu.hasMoreElements()) {
			String attrName = enu.nextElement();

			Object attrValue = ses.getAttribute(attrName);

			if (attrValue != null) {
				for (int i = 0; i < SHARED_SESSION_ATTRIBUTES.length; i++) {
					if (attrName.startsWith(SHARED_SESSION_ATTRIBUTES[i])) {
						map.put(attrName, attrValue);

						if (_log.isDebugEnabled()) {
							_log.debug("Sharing " + attrName);
						}

						break;
					}
				}
			}
		}
		return map;
*/
	}

	private static Log _log = LogFactory.getLog(SharedSessionUtil.class);

}