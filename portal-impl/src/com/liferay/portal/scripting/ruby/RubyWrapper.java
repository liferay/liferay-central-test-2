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

import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.scripting.ScriptingWrapper;
import com.liferay.portal.scripting.ScriptExecutionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jruby.Ruby;
import org.jruby.RubyInstanceConfig;
import org.jruby.exceptions.RaiseException;
import org.jruby.javasupport.Java;
import org.jruby.javasupport.JavaEmbedUtils;
import org.jruby.javasupport.JavaObject;
import org.jruby.javasupport.JavaUtil;
import org.jruby.runtime.Block;
import org.jruby.runtime.IAccessor;
import org.jruby.runtime.builtin.IRubyObject;

/**
 * <a href="RubyWrapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alberto Montero
 *
 */
public class RubyWrapper implements ScriptingWrapper {
	public static final String LANG = "javascript";

	public RubyWrapper() {
		RubyInstanceConfig config = new RubyInstanceConfig();

		config.setLoader(PortalClassLoaderUtil.getClassLoader());

		_runtime = JavaEmbedUtils.initialize(new ArrayList(), config);
	}

	public void clearCache() {
		//No cache
	}

	public Map<String, Object> eval(
			Map<String, Object> inputObjects,
			Map<String, Class> inputObjectTypes,
			Set<String> outputObjectNames,
			String script,
			Set<String> accessibleClassesGroups,
			Set<String> accessibleIndividualClasses)
		throws ScriptExecutionException {

		throw new ScriptExecutionException(
			"Constrained execution not supported for Ruby");
	}

	public Map<String, Object> evalUnconstrained(
			Map<String, Object> inputObjects,
			Map<String, Class> inputObjectTypes, Set<String> outputObjectNames,
			String script)
		throws ScriptExecutionException {

		return _eval(
			inputObjects, inputObjectTypes, script, outputObjectNames);

	}

	public String getLanguage() {
		return LANG;
	}

	private Map<String, Object> _eval(
			Map<String, Object> inputObjects,
			Map<String, Class> inputObjectTypes, String script,
			Set<String> outputObjectNames)
		throws ScriptExecutionException {

		Map <String, Object> scriptOutputObjects =
			new HashMap<String, Object>();

		try {
			for (final String inputObjectName: inputObjects.keySet()) {
				String varName = inputObjectName;

				if (!inputObjectName.startsWith("$")) {
					varName = "$" + inputObjectName;
				}

				_runtime.getGlobalVariables().define(
					varName,
					new BeanGlobalVariable(
						_runtime, inputObjects.get(inputObjectName),
						inputObjectTypes.get(inputObjectName)));
			}

			_runtime.evalScriptlet(script);

			for (String outputObjectName: outputObjectNames) {
				scriptOutputObjects.put(
					outputObjectName,
					_runtime.getGlobalVariables().get(outputObjectName));
			}
		}
		catch (RaiseException re) {
			StringBuilder sb = new StringBuilder();

			sb.append(re.getException().message.asJavaString());
			sb.append("\n\n");

			throw new ScriptExecutionException(sb.toString(), re);
		}
		finally {
			JavaEmbedUtils.terminate(_runtime);
			_runtime = null;
		}

		return scriptOutputObjects;
	}

	private static class BeanGlobalVariable implements IAccessor {
		private Ruby runtime;
		private Object bean;
		private Class type;

		public BeanGlobalVariable(Ruby runtime, Object bean, Class type) {
			this.runtime = runtime;
			this.bean = bean;
			this.type = type;
		}

		public IRubyObject getValue() {
			IRubyObject result = JavaUtil.convertJavaToRuby(
				runtime, bean, type);

			return result instanceof JavaObject ?
				Java.wrap(runtime, result) : result;
		}

		public IRubyObject setValue(IRubyObject value) {
			this.bean = JavaUtil.convertArgument(
				runtime, Java.ruby_to_java(
					runtime.getObject(), value, Block.NULL_BLOCK),
				type);
			return value;
		}
	}

	private Ruby _runtime;

}