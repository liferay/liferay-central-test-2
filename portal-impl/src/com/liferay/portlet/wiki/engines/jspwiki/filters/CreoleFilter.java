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

package com.liferay.portlet.wiki.engines.jspwiki.filters;

import com.ecyrd.jspwiki.WikiEngine;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 * @author Samuel Liu
 */
public class CreoleFilter extends org.wikiwizard.jspwiki.filters.CreoleFilter {

	public void initialize(WikiEngine wikiEngine, Properties properties) {
		if (_log.isDebugEnabled()) {
			_log.debug("Initializing");
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CreoleFilter.class);

}