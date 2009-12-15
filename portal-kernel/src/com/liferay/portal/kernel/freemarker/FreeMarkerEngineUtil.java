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

package com.liferay.portal.kernel.freemarker;

import java.io.StringWriter;

/**
 * <a href="FreeMarkerEngineUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mika Koivisto
 */
public class FreeMarkerEngineUtil {

	public static FreeMarkerEngine getFreeMarkerEngine() {
		return _freeMarkerEngine;
	}

	public static FreeMarkerContext getWrappedRestrictedToolsContext() {
		return getFreeMarkerEngine().getWrappedRestrictedToolsContext();
	}

	public static FreeMarkerContext getWrappedStandardToolsContext() {
		return getFreeMarkerEngine().getWrappedStandardToolsContext();
	}

	public static void init() throws Exception {
		getFreeMarkerEngine().init();
	}

	public static boolean mergeTemplate(
			String freeMarkerTemplateId, FreeMarkerContext freeMarkerContext,
			StringWriter stringWriter)
		throws Exception {

		return getFreeMarkerEngine().mergeTemplate(
			freeMarkerTemplateId, freeMarkerContext, stringWriter);
	}

	public static boolean mergeTemplate(
			String freeMarkerTemplateId, String freemarkerTemplateContent,
			FreeMarkerContext freeMarkerContext, StringWriter stringWriter)
		throws Exception {

		return getFreeMarkerEngine().mergeTemplate(
			freeMarkerTemplateId, freemarkerTemplateContent, freeMarkerContext,
			stringWriter);
	}

	public static boolean resourceExists(String resource) {
		return getFreeMarkerEngine().resourceExists(resource);
	}

	public void setFreeMarkerEngine(FreeMarkerEngine freeMarkerEngine) {
		_freeMarkerEngine = freeMarkerEngine;
	}

	private static FreeMarkerEngine _freeMarkerEngine;

}