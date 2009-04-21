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

package com.liferay.portal.scripting;

import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * <a href="JavaScriptWrapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alberto Montero
 *
 */
public class JavaScriptWrapper implements ScriptingWrapper {
	public static final String LANG = "javascript";

	public Map<String, Object> eval(
			Map<String, Object> inputObjects,
			Map<String, Class> inputObjectTypes, Set<String> outputObjectNames,
			String script)
		throws ScriptExecutionException {

		String cacheKey = Integer.toString(script.hashCode());

		Script compiledScript = (Script) SingleVMPoolUtil.get(LANG, cacheKey);

		if (Validator.isNull(compiledScript)) {
			compiledScript = _compile(script);

			SingleVMPoolUtil.put(LANG, cacheKey, compiledScript);
		}

		return _eval(inputObjects, compiledScript, outputObjectNames);
	}

	public String getLanguage() {
		return LANG;
	}

	private Script _compile(String script) {
		return Context.enter().compileString(script, "script", 0, null);
	}

	private Map<String, Object> _eval(
			Map<String, Object> inputObjects, Script compiledScript,
			Set<String> outputObjectNames)
		throws ScriptExecutionException {

		Map <String, Object> scriptOutputObjects =
			new HashMap<String, Object>();

		try {
			Context context = Context.enter();
			Scriptable scope = context.initStandardObjects();

			for (String varName: inputObjects.keySet()) {
				ScriptableObject.putProperty(
					scope, varName,
					Context.javaToJS(inputObjects.get(varName), scope));
			}

			compiledScript.exec(context, scope);

			for (String outputObjectName: outputObjectNames) {
				scriptOutputObjects.put(
					outputObjectName,
					ScriptableObject.getProperty(scope, outputObjectName));
			}
		}
		catch (Exception e) {
			StringBuilder sb = new StringBuilder();

			sb.append(e.getMessage());
			sb.append("\n\n");

			throw new ScriptExecutionException(sb.toString(), e);
		}
		finally {
			Context.exit();
		}

		return scriptOutputObjects;
	}

}