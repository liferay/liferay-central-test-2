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

package com.liferay.portal.scripting.python;

import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.scripting.ScriptingWrapper;
import com.liferay.portal.scripting.ScriptExecutionException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PySystemState;
import org.python.util.InteractiveInterpreter;

/**
 * <a href="PythonWrapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alberto Montero
 *
 */
public class PythonWrapper implements ScriptingWrapper {
	public static final String LANG = "python";

	public PythonWrapper() {
		PySystemState.initialize();
	}

	public void clearCache() {
		SingleVMPoolUtil.clear(LANG);
	}

	public Map<String, Object> evalUnconstrained(
			Map<String, Object> inputObjects,
			Map<String, Class> inputObjectTypes, Set<String> outputObjectNames,
			String script)
		throws ScriptExecutionException {

		return _eval(
			inputObjects, script, outputObjectNames, null, null);
	}

	public Map<String, Object> eval(
			Map<String, Object> inputObjects,
			Map<String, Class> inputObjectTypes, Set<String> outputObjectNames,
			String script, Set<String> accessibleClassesGroups,
			Set<String> accessibleIndividualClasses)
		throws ScriptExecutionException {

		if (Validator.isNull(accessibleClassesGroups)) {
			accessibleClassesGroups = new HashSet<String>();
		}
		if (Validator.isNull(accessibleIndividualClasses)) {
			accessibleIndividualClasses = new HashSet<String>();
		}

		return _eval(
			inputObjects, script, outputObjectNames,
			accessibleClassesGroups, accessibleIndividualClasses);
	}

	public String getLanguage() {
		return LANG;
	}

	private Map<String, Object> _eval(
		Map<String, Object> inputObjects, String script,
		Set<String> outputObjectNames, Set<String> accessibleClassesGroups,
		Set<String> accesibleIndividualClasses) {

		PyCode compiledScript = getCompiledScript(script);

		Map <String, Object> scriptOutputObjects =
			new HashMap<String, Object>();

		InteractiveInterpreter interpreter = new InteractiveInterpreter();

		for (String varName: inputObjects.keySet()) {
			interpreter.set(varName, inputObjects.get(varName));
		}

		interpreter.exec(compiledScript);

		for (Object varName: outputObjectNames) {
			scriptOutputObjects.put(
				(String)varName, interpreter.get((String)varName));
		}

		return scriptOutputObjects;
	}

	private PyCode getCompiledScript(String script) {
		String cacheKey = Integer.toString(script.hashCode());

		PyCode compiledScript = (PyCode) SingleVMPoolUtil.get(LANG, cacheKey);

		if (Validator.isNull(compiledScript)) {
			compiledScript = Py.compile_flags(
				script, "<string>", "exec", Py.getCompilerFlags());

			SingleVMPoolUtil.put(LANG, cacheKey, compiledScript);
		}
		return compiledScript;
	}

}