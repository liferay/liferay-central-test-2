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

package com.liferay.portal.kernel.velocity;

import java.io.Writer;

/**
 * <a href="VelocityEngineUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class VelocityEngineUtil {

	public static void flushTemplate(String resource) {
		getVelocityEngine().flushTemplate(resource);
	}

	public static VelocityContext getEmptyContext() {
		return getVelocityEngine().getEmptyContext();
	}

	public static VelocityContext getRestrictedToolsContext() {
		return getVelocityEngine().getRestrictedToolsContext();
	}

	public static VelocityContext getStandardToolsContext() {
		return getVelocityEngine().getStandardToolsContext();
	}

	public static VelocityEngine getVelocityEngine() {
		return _velocityEngine;
	}

	public static VelocityContext getWrappedRestrictedToolsContext() {
		return getVelocityEngine().getWrappedRestrictedToolsContext();
	}

	public static VelocityContext getWrappedStandardToolsContext() {
		return getVelocityEngine().getWrappedStandardToolsContext();
	}

	public static void init() throws Exception {
		getVelocityEngine().init();
	}

	public static boolean mergeTemplate(
			String velocityTemplateId, String velocityTemplateContent,
			VelocityContext velocityContext, Writer writer)
		throws Exception {

		return getVelocityEngine().mergeTemplate(
			velocityTemplateId, velocityTemplateContent, velocityContext,
			writer);
	}

	public static boolean mergeTemplate(
			String velocityTemplateId, VelocityContext velocityContext,
			Writer writer)
		throws Exception {

		return getVelocityEngine().mergeTemplate(
			velocityTemplateId, velocityContext, writer);
	}

	public static boolean resourceExists(String resource) {
		return getVelocityEngine().resourceExists(resource);
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		_velocityEngine = velocityEngine;
	}

	private static VelocityEngine _velocityEngine;

}