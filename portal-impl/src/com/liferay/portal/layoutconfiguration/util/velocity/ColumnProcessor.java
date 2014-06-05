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

package com.liferay.portal.layoutconfiguration.util.velocity;

import java.util.Map;

/**
 * @author Raymond Augé
 * @author Oliver Teichmann
 */
public interface ColumnProcessor {

	public String processColumn(String columnId) throws Exception;

	public String processColumn(String columnId, String classNames)
		throws Exception;

	public String processMax() throws Exception;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #processMax()}
	 */
	@Deprecated
	public String processMax(String classNames) throws Exception;

	public String processPortlet(String portletId) throws Exception;

	public String processPortlet(
			String portletId, Map<String, ?> defaultSettingsMap)
		throws Exception;

}