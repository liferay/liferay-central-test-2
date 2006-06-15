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

package com.liferay.portal.log;

import com.liferay.portal.shared.log.Log;

/**
 * <a href="LogImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class LogImpl implements Log {

	public LogImpl(org.apache.commons.logging.Log log) {
		_log = log;
	}

	public void debug(String msg) {
		_log.debug(msg);
	}

	public void debug(Throwable t) {
		_log.debug(t);
	}

	public void error(String msg) {
		_log.error(msg);
	}

	public void error(Throwable t) {
		_log.error(t);
	}

	public void info(String msg) {
		_log.info(msg);
	}

	public void info(Throwable t) {
		_log.info(t);
	}

	public void warn(String msg) {
		_log.warn(msg);
	}

	public void warn(Throwable t) {
		_log.warn(t);
	}

	private org.apache.commons.logging.Log _log;

}