/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.portletconfiguration.util;

import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portlet.PortletQNameUtil;

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
		_publicRenderParameterName =
			PortletQNameUtil.getPublicRenderParameterName(
				_publicRenderParameter.getQName());
		_mapping = mapping;
		_ignore = ignore;
	}

	public String getIgnoreKey() {
		return IGNORE_PREFIX + _publicRenderParameterName;
	}

	public String getMapping() {
		return _mapping;
	}

	public String getMappingKey() {
		return MAPPING_PREFIX + _publicRenderParameterName;
	}

	public PublicRenderParameter getPublicRenderParameter() {
		return _publicRenderParameter;
	}

	public String getPublicRenderParameterName() {
		return _publicRenderParameterName;
	}

	public boolean isIgnore() {
		return _ignore;
	}

	private boolean _ignore;
	private String _mapping;
	private PublicRenderParameter _publicRenderParameter;
	private String _publicRenderParameterName;

}