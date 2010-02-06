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

package com.liferay.portlet.portletconfiguration.util;

import com.liferay.portal.model.PublicRenderParameter;

import java.util.Comparator;

/**
 * <a href="PublicRenderParameterIdentifierConfigurationComparator.java.html">
 * <b><i>View Source</i></b></a>
 *
 * @author Alberto Montero
 */
public class PublicRenderParameterIdentifierConfigurationComparator
	implements Comparator<PublicRenderParameterConfiguration> {

	public int compare(
		PublicRenderParameterConfiguration publicRenderParameterConfiguration1,
		PublicRenderParameterConfiguration
			publicRenderParameterConfiguration2) {

		PublicRenderParameter publicRenderParameter1 =
			publicRenderParameterConfiguration1.getPublicRenderParameter();
		PublicRenderParameter publicRenderParameter2 =
			publicRenderParameterConfiguration2.getPublicRenderParameter();

		String identifier1 = publicRenderParameter1.getIdentifier();
		String identifier2 = publicRenderParameter2.getIdentifier();

		return identifier1.compareTo(identifier2);
	}

}