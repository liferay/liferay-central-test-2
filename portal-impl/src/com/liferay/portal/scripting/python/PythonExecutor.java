/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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

		for (Map.Entry<String, Object> entry : inputObjects.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			interactiveInterpreter.set(key, value);
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