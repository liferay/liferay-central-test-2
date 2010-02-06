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

package com.liferay.portal.log;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogWrapper;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * <a href="Log4jLogImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class Log4jLogImpl implements Log {

	public Log4jLogImpl(Logger logger) {
		_logger = logger;
	}

	public void debug(Object msg) {
		_logger.log(_FQCN, Level.DEBUG, msg, null);
	}

	public void debug(Throwable t) {
		_logger.log(_FQCN, Level.DEBUG, null, t);
	}

	public void debug(Object msg, Throwable t) {
		_logger.log(_FQCN, Level.DEBUG, msg, t);
	}

	public void error(Object msg) {
		_logger.log(_FQCN, Level.ERROR, msg, null);
	}

	public void error(Throwable t) {
		_logger.log(_FQCN, Level.ERROR, null, t);
	}

	public void error(Object msg, Throwable t) {
		_logger.log(_FQCN, Level.ERROR, msg, t);
	}

	public void fatal(Object msg) {
		_logger.log(_FQCN, Level.FATAL, msg, null);
	}

	public void fatal(Throwable t) {
		_logger.log(_FQCN, Level.FATAL, null, t);
	}

	public void fatal(Object msg, Throwable t) {
		_logger.log(_FQCN, Level.FATAL, msg, t);
	}

	public void info(Object msg) {
		_logger.log(_FQCN, Level.INFO, msg, null);
	}

	public void info(Throwable t) {
		_logger.log(_FQCN, Level.INFO, null, t);
	}

	public void info(Object msg, Throwable t) {
		_logger.log(_FQCN, Level.INFO, msg, t);
	}

	public boolean isDebugEnabled() {
		return _logger.isDebugEnabled();
	}

	public boolean isErrorEnabled() {
		return _logger.isEnabledFor(Level.ERROR);
	}

	public boolean isFatalEnabled() {
		return _logger.isEnabledFor(Level.FATAL);
	}

	public boolean isInfoEnabled() {
		return _logger.isInfoEnabled();
	}

	public boolean isTraceEnabled() {
		return _logger.isTraceEnabled();
	}

	public boolean isWarnEnabled() {
		return _logger.isEnabledFor(Level.WARN);
	}

	public void trace(Object msg) {
		_logger.log(_FQCN, Level.TRACE, msg, null);
	}

	public void trace(Throwable t) {
		_logger.log(_FQCN, Level.TRACE, null, t);
	}

	public void trace(Object msg, Throwable t) {
		_logger.log(_FQCN, Level.TRACE, msg, t);
	}

	public void warn(Object msg) {
		_logger.log(_FQCN, Level.WARN, msg, null);
	}

	public void warn(Throwable t) {
		_logger.log(_FQCN, Level.WARN, null, t);
	}

	public void warn(Object msg, Throwable t) {
		_logger.log(_FQCN, Level.WARN, msg, t);
	}

	private static final String _FQCN = LogWrapper.class.getName();

	private Logger _logger;

}