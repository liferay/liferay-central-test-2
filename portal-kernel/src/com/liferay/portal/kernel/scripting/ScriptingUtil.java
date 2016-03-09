/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.scripting;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.ProxyFactory;

import java.util.Map;
import java.util.Set;

/**
 * @author Alberto Montero
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ScriptingUtil {

	public static void clearCache(String language) throws ScriptingException {
		getScripting().clearCache(language);
	}

	public static ScriptingExecutor createScriptingExecutor(
		String language, boolean executeInSeparateThread) {

		return getScripting().createScriptingExecutor(
			language, executeInSeparateThread);
	}

	public static Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, String language, String script)
		throws ScriptingException {

		return getScripting().eval(
			allowedClasses, inputObjects, outputNames, language, script);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #eval(Set, Map, Set, String,
	 *             String)}
	 */
	@Deprecated
	public static Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, String language, String script,
			String... servletContextNames)
		throws ScriptingException {

		return getScripting().eval(
			allowedClasses, inputObjects, outputNames, language, script,
			servletContextNames);
	}

	public static void exec(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			String language, String script)
		throws ScriptingException {

		getScripting().exec(allowedClasses, inputObjects, language, script);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #exec(Set, Map, String,
	 *             String)}
	 */
	@Deprecated
	public static void exec(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			String language, String script, String... servletContextNames)
		throws ScriptingException {

		getScripting().exec(
			allowedClasses, inputObjects, language, script,
			servletContextNames);
	}

	public static Scripting getScripting() {
		PortalRuntimePermission.checkGetBeanProperty(ScriptingUtil.class);

		return _scripting;
	}

	public static Set<String> getSupportedLanguages() {
		return getScripting().getSupportedLanguages();
	}

	private static String[] _getServletContextNames(
		ClassLoader[] classLoaders) {

		String[] servletContextNames = new String[classLoaders.length];

		for (int i = 0; i < classLoaders.length; i++) {
			servletContextNames[i] = ClassLoaderPool.getContextName(
				classLoaders[i]);
		}

		return servletContextNames;
	}

	private static final Scripting _scripting =
		ProxyFactory.newServiceTrackedInstance(Scripting.class);

}