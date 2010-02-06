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

/**
 * <a href="PublicRenderParameterConfiguration.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Alberto Montero
 */
public class PublicRenderParameterConfiguration {

	public static final String IGNORE_PREFIX = "lfr-prp-ignore-";

	public static final String MAPPING_PREFIX = "lfr-prp-mapping-";

	public PublicRenderParameterConfiguration(
		PublicRenderParameter publicRenderParameter, String mapping,
		boolean ignore) {

		_publicRenderParameter = publicRenderParameter;
		_mapping = mapping;
		_ignore = ignore;
	}

	public String getIgnoreKey() {
		return IGNORE_PREFIX + _publicRenderParameter.getIdentifier();
	}

	public String getMapping() {
		return _mapping;
	}

	public String getMappingKey() {
		return MAPPING_PREFIX + _publicRenderParameter.getIdentifier();
	}

	public PublicRenderParameter getPublicRenderParameter() {
		return _publicRenderParameter;
	}

	public boolean isIgnore() {
		return _ignore;
	}

	private boolean _ignore;
	private String _mapping;
	private PublicRenderParameter _publicRenderParameter;

}