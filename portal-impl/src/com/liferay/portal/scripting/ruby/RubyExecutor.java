/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.scripting.ruby;

import com.liferay.portal.kernel.scripting.ExecutionException;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.ScriptingExecutor;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;

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
 * @author Alberto Montero
 */
public class RubyExecutor implements ScriptingExecutor {

	public static final String LANGUAGE = "ruby";

	public RubyExecutor() {
		RubyInstanceConfig rubyInstanceConfig = new RubyInstanceConfig();

		rubyInstanceConfig.setLoader(PortalClassLoaderUtil.getClassLoader());

		_ruby = JavaEmbedUtils.initialize(
			new ArrayList<String>(), rubyInstanceConfig);
	}

	public void clearCache() {
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
				"Constrained execution not supported for Ruby");
		}

		try {
			GlobalVariables globalVariables = _ruby.getGlobalVariables();

			for (Map.Entry<String, Object> entry : inputObjects.entrySet()) {
				String inputName = entry.getKey();
				Object inputObject = entry.getValue();

				if (!inputName.startsWith(StringPool.DOLLAR)) {
					inputName = StringPool.DOLLAR + inputName;
				}

				BeanGlobalVariable beanGlobalVariable = new BeanGlobalVariable(
					_ruby, inputObject, inputObject.getClass());

				globalVariables.define(inputName, beanGlobalVariable);
			}

			_ruby.evalScriptlet(script);

			if (outputNames == null) {
				return null;
			}

			Map<String, Object> outputObjects = new HashMap<String, Object>();

			for (String outputName : outputNames) {
				outputObjects.put(outputName, globalVariables.get(outputName));
			}

			return outputObjects;
		}
		catch (RaiseException re) {
			throw new ScriptingException(
				re.getException().message.asJavaString() + "\n\n", re);
		}
		finally {
			JavaEmbedUtils.terminate(_ruby);
		}
	}

	private Ruby _ruby;

}