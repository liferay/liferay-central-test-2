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

package com.liferay.portal.scripting.javascript;

import com.liferay.mozilla.javascript.Context;
import com.liferay.mozilla.javascript.Script;
import com.liferay.mozilla.javascript.Scriptable;
import com.liferay.mozilla.javascript.ScriptableObject;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.scripting.ScriptingWrapper;
import com.liferay.portal.scripting.ScriptExecutionException;
import com.liferay.portal.scripting.ClassVisibilityChecker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <a href="JavaScriptWrapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alberto Montero
 *
 */
public class JavaScriptWrapper implements ScriptingWrapper {
	public static final String LANG = "javascript";

	public void clearCache() {
		SingleVMPoolUtil.clear(LANG);
	}

	public Map<String, Object> evalUnconstrained(
			Map<String, Object> inputObjects,
			Map<String, Class> inputObjectTypes, Set<String> outputObjectNames,
			String script)
		throws ScriptExecutionException {

		return _eval(
			inputObjects, script, outputObjectNames, getAllClassesSet(),
			new HashSet<String>());
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
			Set<String> accesibleIndividualClasses)
		throws ScriptExecutionException {

		Script compiledScript = getCompiledScript(script);

		Map <String, Object> scriptOutputObjects =
			new HashMap<String, Object>();

		try {
			Context context = Context.enter();

			Scriptable scope = context.initStandardObjects();

			for (String varName: inputObjects.keySet()) {
				if (Validator.isNotNull(accesibleIndividualClasses)) {
					accesibleIndividualClasses.add(
						inputObjects.get(varName).getClass().
							getCanonicalName());
				}
				ScriptableObject.putProperty(
					scope, varName,
					Context.javaToJS(inputObjects.get(varName), scope));
			}

			if (Validator.isNotNull(accessibleClassesGroups) ||
				Validator.isNotNull(accesibleIndividualClasses)) {

				context.setClassShutter(
					new JavaScriptClassVisibilityChecker(
						accessibleClassesGroups, accesibleIndividualClasses));
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

	private Set<String> getAllClassesSet() {
		Set<String> visibleGroups = new HashSet<String>();
		visibleGroups.add(ClassVisibilityChecker.ALL_CLASSES);
		return visibleGroups;
	}

	private Script getCompiledScript(String script) {
		String cacheKey = Integer.toString(script.hashCode());

		Script compiledScript = (Script) SingleVMPoolUtil.get(LANG, cacheKey);

		if (Validator.isNull(compiledScript)) {
			try {
				Context cx = Context.enter();
				compiledScript = cx.compileString(script, "script", 0, null);
			} finally {
				Context.exit();
			}

			SingleVMPoolUtil.put(LANG, cacheKey, compiledScript);
		}
		return compiledScript;
	}

	private static Log _log = LogFactoryUtil.getLog(JavaScriptWrapper.class);

}


