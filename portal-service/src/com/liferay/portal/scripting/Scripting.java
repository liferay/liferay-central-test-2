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

import java.util.Map;
import java.util.Set;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * <a href="Scripting.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alberto Montero
 *
 */
public interface Scripting {

	public void clearCache(String lang);

	public Map<String, Object> eval(
			Set<String> accessibleClassesGroups,
			Set<String> accessibleIndividualClasses,
			Map<String, Object> inputObjects, String lang,
			Set<String> outputObjectNames, String script
	)
		throws ScriptExecutionException, UnsupportedLanguageException;

	public Map<String, Object> eval(
			Set<String> accessibleClassesGroups,
			Set<String> accessibleIndividualClasses,
			Map<String, Object> inputObjects,
			Map<String, Class> inputObjectsTypes, String lang,
			Set<String> outputObjectNames, String script
	)
		throws ScriptExecutionException, UnsupportedLanguageException;

	public Map<String, Object> evalUnconstrained(
			Map<String, Object> inputObjects, String lang,
			Set<String> outputObjectNames, String script)
		throws ScriptExecutionException, UnsupportedLanguageException;

	public Map<String, Object> evalUnconstrained(
			Map<String, Object> inputObjects,
			Map<String, Class> inputObjectsTypes, String lang,
			Set<String> outputObjectNames, String script)
		throws ScriptExecutionException, UnsupportedLanguageException;

	public void exec(
			Set<String> accessibleClassesGroups,
			Set<String> accessibleIndividualClasses,
			Map<String, Object> inputObjects, String lang, String script
	)
		throws ScriptExecutionException, UnsupportedLanguageException;

	public void exec(
			Set<String> accessibleClassesGroups,
			Set<String> accessibleIndividualClasses,
			Map<String, Object> inputObjects,
			Map<String, Class> inputObjectsTypes, String lang, String script
	)
		throws ScriptExecutionException, UnsupportedLanguageException;

	public void execUnconstrained(
			Map<String, Object> inputObjects, String lang, String script)
		throws ScriptExecutionException, UnsupportedLanguageException;

	public void execUnconstrained(
			Map<String, Object> inputObjects,
			Map<String, Class> inputObjectsTypes, String lang, String script)
		throws ScriptExecutionException, UnsupportedLanguageException;

	public Map[] getPortletObjects(
		PortletConfig portletConfig, PortletContext portletContext,
		PortletRequest portletRequest, PortletResponse portletResponse);

	public Set<String> getSupportedLanguages();

}