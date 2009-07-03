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

package com.liferay.portal.scripting.ruby;

import com.liferay.portal.kernel.scripting.ExecutionException;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.scripting.ScriptingExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jruby.Ruby;
import org.jruby.RubyInstanceConfig;
import org.jruby.exceptions.RaiseException;
import org.jruby.internal.runtime.GlobalVariables;
import org.jruby.javasupport.JavaEmbedUtils;

/**
 * <a href="RubyExecutor.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alberto Montero
 *
 */
public class RubyExecutor implements ScriptingExecutor {

	public static final String CACHE_NAME = RubyExecutor.class.getName();

	public RubyExecutor() {
		RubyInstanceConfig config = new RubyInstanceConfig();

		config.setLoader(PortalClassLoaderUtil.getClassLoader());

		_runtime = JavaEmbedUtils.initialize(new ArrayList(), config);
	}

	public void clearCache() {
	}

	public String getLanguage() {
		return _LANGUAGE;
	}

	public Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, String script)
		throws ScriptingException {

		if (allowedClasses != null) {
			throw new ExecutionException(
				"Constrained execution not supported for Ruby");
		}

		try {
			GlobalVariables globalVariables = _runtime.getGlobalVariables();

			for (final String varName: inputObjects.keySet()) {
				String rubyVarName = varName;

				if (!rubyVarName.startsWith("$")) {
					rubyVarName = "$" + rubyVarName;
				}

				BeanGlobalVariable rubyVarValue = new BeanGlobalVariable(
					_runtime, inputObjects.get(varName),
					inputObjects.get(varName).getClass());

				globalVariables.define(rubyVarName, rubyVarValue);
			}

			_runtime.evalScriptlet(script);

			if (outputNames == null) {
				return null;
			}

			Map <String, Object> outputObjects = new HashMap<String, Object>();

			for (String outputName: outputNames) {
				outputObjects.put(outputName, globalVariables.get(outputName));
			}

			return outputObjects;
		}
		catch (RaiseException re) {
			StringBuilder sb = new StringBuilder();

			sb.append(re.getException().message.asJavaString());
			sb.append("\n\n");

			throw new ScriptingException(sb.toString(), re);
		}
		finally {
			JavaEmbedUtils.terminate(_runtime);
		}
	}

	private static final String _LANGUAGE = "ruby";

	private Ruby _runtime;

}