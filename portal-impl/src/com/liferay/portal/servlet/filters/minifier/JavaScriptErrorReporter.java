/**
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

package com.liferay.portal.servlet.filters.minifier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

/**
 * <a href="JavaScriptErrorReporter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JavaScriptErrorReporter implements ErrorReporter {

	public void error(
		String message, String sourceName, int line, String lineSource,
		int lineOffset) {

		if (line < 0) {
			_log.error(message);
		}
		else {
			_log.error(line + ": " + lineOffset + ": " + message);
		}
	}

	public EvaluatorException runtimeError(
		String message, String sourceName, int line, String lineSource,
		int lineOffset) {

		error(message, sourceName, line, lineSource, lineOffset);

		return new EvaluatorException(message);
	}

	public void warning(
		String message, String sourceName, int line, String lineSource,
		int lineOffset) {

		if (!_log.isWarnEnabled()) {
			return;
		}

		if (line < 0) {
			_log.warn(message);
		}
		else {
			_log.warn(line + ": " + lineOffset + ": " + message);
		}
	}

	private static Log _log = LogFactory.getLog(MinifierFilter.class);

}