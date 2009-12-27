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

package com.liferay.portal.util;

import com.liferay.mozilla.javascript.ErrorReporter;
import com.liferay.mozilla.javascript.EvaluatorException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.yahoo.platform.yui.compressor.CssCompressor;
import com.liferay.yahoo.platform.yui.compressor.JavaScriptCompressor;

/**
 * <a href="MinifierUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MinifierUtil {

	public static String minifyCss(String content) {
		return _instance._minifyCss(content);
	}

	public static String minifyJavaScript(String content) {
		return _instance._minifyJavaScript(content);
	}

	private MinifierUtil() {
	}

	private String _minifyCss(String content) {
		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(true);

		try {
			CssCompressor cssCompressor = new CssCompressor(
				new UnsyncStringReader(content));

			cssCompressor.compress(unsyncStringWriter, _CSS_LINE_BREAK);
		}
		catch (Exception e) {
			_log.error("CSS Minifier failed for\n" + content);

			unsyncStringWriter.append(content);
		}

		return unsyncStringWriter.toString();
	}

	private String _minifyJavaScript(String content) {
		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(true);

		try {
			JavaScriptCompressor javaScriptCompressor =
				new JavaScriptCompressor(
					new UnsyncStringReader(content),
					new JavaScriptErrorReporter());

			javaScriptCompressor.compress(
					unsyncStringWriter, _JS_LINE_BREAK, _JS_MUNGE, _JS_VERBOSE,
					_JS_PRESERVE_ALL_SEMICOLONS, _JS_DISABLE_OPTIMIZATIONS);
		}
		catch (Exception e) {
			_log.error("JavaScript Minifier failed for\n" + content);

			unsyncStringWriter.append(content);
		}

		return unsyncStringWriter.toString();
	}

	private static final int _CSS_LINE_BREAK = -1;

	private static final boolean _JS_DISABLE_OPTIMIZATIONS = false;

	private static final int _JS_LINE_BREAK = -1;

	private static final boolean _JS_MUNGE = true;

	private static final boolean _JS_PRESERVE_ALL_SEMICOLONS = false;

	private static final boolean _JS_VERBOSE = false;

	private static Log _log = LogFactoryUtil.getLog(MinifierUtil.class);

	private static MinifierUtil _instance = new MinifierUtil();

	private class JavaScriptErrorReporter implements ErrorReporter {

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

	}

}