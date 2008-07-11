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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.mirage.util;

import org.apache.commons.logging.Log;

/**
 * <a href="MirageLoggerUtil.java.html"><b><i>View Source</i></b></a>
 * 
 * @author Prakash Reddy
 */
public class MirageLoggerUtil {

	public static void debug(Log log, String message) {

		if (log.isDebugEnabled()) {
			log.debug(message);
		}
	}

	public static void debug(Log log, String message, Throwable throwable) {

		if (log.isDebugEnabled()) {
			log.debug(message, throwable);
		}
	}

	public static void enter(Log log, String methodName) {

		info(log, "Entering method : " + methodName);
	}

	public static void error(Log log, String message) {

		if (log.isErrorEnabled()) {
			log.error(message);
		}
	}

	public static void error(Log log, String message, Throwable throwable) {

		if (log.isErrorEnabled()) {
			log.error(message, throwable);
		}
	}

	public static void exit(Log log, String methodName) {

		info(log, "Exiting method : " + methodName);
	}

	public static void fatal(Log log, String message) {

		if (log.isFatalEnabled()) {
			log.fatal(message);
		}
	}

	public static void fatal(Log log, String message, Throwable throwable) {

		if (log.isFatalEnabled()) {
			log.fatal(message, throwable);
		}
	}

	public static void info(Log log, String message) {

		if (log.isInfoEnabled()) {
			log.info(message);
		}
	}

	public static void info(Log log, String message, Throwable throwable) {

		if (log.isInfoEnabled()) {
			log.info(message, throwable);
		}
	}
	
	public static void log(Log log, String message) {
		
		info(log, message);
	}
	
	public static void log(Log log, String message, Throwable throwable) {
		
		info(log, message, throwable);
	}

	public static void trace(Log log, String message) {

		if (log.isTraceEnabled()) {
			log.trace(message);
		}
	}

	public static void trace(Log log, String message, Throwable throwable) {

		if (log.isTraceEnabled()) {
			log.trace(message, throwable);
		}
	}

	public static void warn(Log log, String message) {

		if (log.isWarnEnabled()) {
			log.warn(message);
		}
	}

	public static void warn(Log log, String message, Throwable throwable) {

		if (log.isWarnEnabled()) {
			log.warn(message, throwable);
		}
	}

}
