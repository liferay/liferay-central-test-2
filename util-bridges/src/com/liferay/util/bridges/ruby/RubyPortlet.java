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

package com.liferay.util.bridges.ruby;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.util.bridges.bsf.BaseBSFPortlet;

import org.apache.bsf.BSFException;

import org.jruby.RubyException;
import org.jruby.exceptions.RaiseException;

/**
 * <a href="RubyPortlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class RubyPortlet extends BaseBSFPortlet {

	protected String getFileParam() {
		return _FILE_PARAM;
	}

	protected String getScriptingEngineClassName() {
		return _SCRIPTING_ENGINE_CLASS_NAME;
	}

	protected String getScriptingEngineExtension() {
		return _SCRIPTING_ENGINE_EXTENSION;
	}

	protected String getScriptingEngineLanguage() {
		return _SCRIPTING_ENGINE_LANGUAGE;
	}

	protected void logBSFException(BSFException bsfe, String path) {
		String message =
			"The script at " + path + " or one of the global files has errors.";

		Throwable t = bsfe.getTargetException();

		if (t instanceof RaiseException) {
			RubyException re = ((RaiseException)t).getException();

			message +=
				re.message + " (" + re.getMetaClass().toString() + ")";

			_log.error(message);

			if (_log.isDebugEnabled()) {
				_log.debug(t, t);
			}
		}
		else {
			_log.error(message, t);
		}
	}

	private static final String _FILE_PARAM = "rubyFile";

	private static final String _SCRIPTING_ENGINE_CLASS_NAME =
		"org.jruby.javasupport.bsf.JRubyEngine";

	private static final String _SCRIPTING_ENGINE_EXTENSION = "rb";

	private static final String _SCRIPTING_ENGINE_LANGUAGE = "ruby";

	private static Log _log = LogFactoryUtil.getLog(RubyPortlet.class);

}