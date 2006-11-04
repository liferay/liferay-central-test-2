/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.util;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingException;

/**
 * <a href="JNDIUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JNDIUtil {

	public static Object lookup(Context ctx, String location)
		throws NamingException {

		return lookup(ctx, location, false);
	}

	public static Object lookup(Context ctx, String location, boolean cache)
		throws NamingException {

		Object obj = null;

		if (cache) {
			obj = _cache.get(location);

			if (obj == null) {
				obj = _lookup(ctx, location);

				_cache.put(location, obj);
			}
		}
		else {
			obj = _lookup(ctx, location);
		}

		return obj;
	}

	private static Object _lookup(Context ctx, String location)
		throws NamingException {

		Object obj = null;

		try {
			obj = ctx.lookup(location);
		}
		catch (NamingException n1) {

			// java:comp/env/ObjectName to ObjectName

			if (location.indexOf("java:comp/env/") != -1) {
				try {
					obj = ctx.lookup(
						StringUtil.replace(location, "java:comp/env/", ""));
				}
				catch (NamingException n2) {

					// java:comp/env/ObjectName to java:ObjectName

					obj = ctx.lookup(
						StringUtil.replace(location, "comp/env/", ""));
				}
			}

			// java:ObjectName to ObjectName

			else if (location.indexOf("java:") != -1) {
				try {
					obj = ctx.lookup(StringUtil.replace(location, "java:", ""));
				}
				catch (NamingException n2) {

					// java:ObjectName to java:comp/env/ObjectName

					obj = ctx.lookup(StringUtil.replace(
						location, "java:", "java:comp/env/"));
				}
			}

			// ObjectName to java:ObjectName

			else if (location.indexOf("java:") == -1) {
				try {
					obj = ctx.lookup("java:" + location);
				}
				catch (NamingException n2) {

					// ObjectName to java:comp/env/ObjectName

					obj = ctx.lookup("java:comp/env/" + location);
				}
			}
			else {
				throw new NamingException();
			}
		}

		return obj;
	}

	private static Map _cache = new HashMap();

}