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

package com.liferay.portal.shared.log;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <a href="Jdk14LogImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class Jdk14LogImpl implements Log {

	public Jdk14LogImpl(Logger log) {
		_log = log;
	}

	public void debug(String msg) {
		_log.log(Level.FINE, msg);
	}

	public void debug(Throwable t) {
		_log.log(Level.FINE, t.getMessage(), t);
	}

	public void debug(String msg, Throwable t) {
		_log.log(Level.FINE, msg, t);
	}

	public void error(String msg) {
		_log.log(Level.SEVERE, msg);
	}

	public void error(Throwable t) {
		_log.log(Level.SEVERE, t.getMessage(), t);
	}

	public void error(String msg, Throwable t) {
		_log.log(Level.SEVERE, msg, t);
	}

	public void fatal(String msg) {
		_log.log(Level.SEVERE, msg);
	}

	public void fatal(Throwable t) {
		_log.log(Level.SEVERE, t.getMessage(), t);
	}

	public void fatal(String msg, Throwable t) {
		_log.log(Level.SEVERE, msg, t);
	}

	public void info(String msg) {
		_log.log(Level.INFO, msg);
	}

	public void info(Throwable t) {
		_log.log(Level.INFO, t.getMessage(), t);
	}

	public void info(String msg, Throwable t) {
		_log.log(Level.INFO, msg, t);
	}

	public boolean isDebugEnabled() {
		return _log.isLoggable(Level.FINE);
	}

	public boolean isErrorEnabled() {
		return _log.isLoggable(Level.SEVERE);
	}

	public boolean isFatalEnabled() {
		return _log.isLoggable(Level.SEVERE);
	}

	public boolean isInfoEnabled() {
		return _log.isLoggable(Level.INFO);
	}

	public boolean isTraceEnabled() {
		return _log.isLoggable(Level.FINEST);
	}

	public boolean isWarnEnabled() {
		return _log.isLoggable(Level.WARNING);
	}

	public void trace(String msg) {
		_log.log(Level.FINEST, msg);
	}

	public void trace(Throwable t) {
		_log.log(Level.FINEST, t.getMessage(), t);
	}

	public void trace(String msg, Throwable t) {
		_log.log(Level.FINEST, msg, t);
	}

	public void warn(String msg) {
		_log.log(Level.WARNING, msg);
	}

	public void warn(Throwable t) {
		_log.log(Level.WARNING, t.getMessage(), t);
	}

	public void warn(String msg, Throwable t) {
		_log.log(Level.WARNING, msg, t);
	}

	private Logger _log;

}