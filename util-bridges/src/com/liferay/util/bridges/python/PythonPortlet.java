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

package com.liferay.util.bridges.python;

import com.liferay.util.bridges.bsf.BaseBSFPortlet;

/**
 * <a href="PythonPortlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alberto Montero
 *
 */
public class PythonPortlet extends BaseBSFPortlet {

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

	private static final String _FILE_PARAM = "pythonFile";

	private static final String _SCRIPTING_ENGINE_CLASS_NAME =
		"org.apache.bsf.engines.jython.JythonEngine";

	private static final String _SCRIPTING_ENGINE_EXTENSION = "py";

	private static final String _SCRIPTING_ENGINE_LANGUAGE = "python";

}