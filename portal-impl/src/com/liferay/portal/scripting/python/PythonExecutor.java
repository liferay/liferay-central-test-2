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

package com.liferay.portal.scripting.python;

import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.scripting.ExecutionException;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.ScriptingExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.python.core.CompileMode;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PySystemState;
import org.python.util.InteractiveInterpreter;

/**
 * <a href="PythonExecutor.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alberto Montero
 */
public class PythonExecutor implements ScriptingExecutor {

	public static final String CACHE_NAME = PythonExecutor.class.getName();

	public static final String LANGUAGE = "python";

	public void clearCache() {
		SingleVMPoolUtil.clear(CACHE_NAME);
	}

	public String getLanguage() {
		return LANGUAGE;
	}

	public Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, String script)
		throws ScriptingException {

		if (allowedClasses != null) {
			throw new ExecutionException(
				"Constrained execution not supported for Python");
		}

		PyCode compiledScript = getCompiledScript(script);

		InteractiveInterpreter interactiveInterpreter =
			new InteractiveInterpreter();

		for (String varName: inputObjects.keySet()) {
			interactiveInterpreter.set(varName, inputObjects.get(varName));
		}

		interactiveInterpreter.exec(compiledScript);

		if (outputNames == null) {
			return null;
		}

		Map<String, Object> outputObjects = new HashMap<String, Object>();

		for (String outputName : outputNames) {
			outputObjects.put(
				outputName, interactiveInterpreter.get(outputName));
		}

		return outputObjects;
	}

	protected PyCode getCompiledScript(String script) {
		if (!_initialized) {
			synchronized (this) {
				PySystemState.initialize();

				_initialized = true;
			}
		}

		String key = String.valueOf(script.hashCode());

		PyCode compiledScript = (PyCode)SingleVMPoolUtil.get(CACHE_NAME, key);

		if (compiledScript == null) {
			compiledScript = Py.compile_flags(
				script, "<string>", CompileMode.exec, Py.getCompilerFlags());

			SingleVMPoolUtil.put(CACHE_NAME, key, compiledScript);
		}

		return compiledScript;
	}

	private boolean _initialized;

}