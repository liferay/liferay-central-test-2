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

package com.liferay.portal.scripting.javascript;

import com.liferay.mozilla.javascript.Context;
import com.liferay.mozilla.javascript.Script;
import com.liferay.mozilla.javascript.Scriptable;
import com.liferay.mozilla.javascript.ScriptableObject;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.ScriptingExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <a href="JavaScriptExecutor.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alberto Montero
 */
public class JavaScriptExecutor implements ScriptingExecutor {

	public static final String CACHE_NAME = JavaScriptExecutor.class.getName();

	public static final String LANGUAGE = "javascript";

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

		Script compiledScript = getCompiledScript(script);

		try {
			Context context = Context.enter();

			Scriptable scriptable = context.initStandardObjects();

			for (String varName: inputObjects.keySet()) {
				ScriptableObject.putProperty(
					scriptable, varName,
					Context.javaToJS(inputObjects.get(varName), scriptable));
			}

			if (allowedClasses != null) {
				context.setClassShutter(
					new JavaScriptClassVisibilityChecker(allowedClasses));
			}

			compiledScript.exec(context, scriptable);

			if (outputNames == null) {
				return null;
			}

			Map<String, Object> outputObjects = new HashMap<String, Object>();

			for (String outputName : outputNames) {
				outputObjects.put(
					outputName,
					ScriptableObject.getProperty(scriptable, outputName));
			}

			return outputObjects;
		}
		catch (Exception e) {
			throw new ScriptingException(e.getMessage() + "\n\n", e);
		}
		finally {
			Context.exit();
		}
	}

	protected Script getCompiledScript(String script) {
		String key = String.valueOf(script.hashCode());

		Script compiledScript = (Script)SingleVMPoolUtil.get(CACHE_NAME, key);

		if (compiledScript == null) {
			try {
				Context context = Context.enter();

				compiledScript = context.compileString(
					script, "script", 0, null);
			}
			finally {
				Context.exit();
			}

			SingleVMPoolUtil.put(CACHE_NAME, key, compiledScript);
		}

		return compiledScript;
	}

}