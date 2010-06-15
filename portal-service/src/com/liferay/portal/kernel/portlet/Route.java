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

package com.liferay.portal.kernel.portlet;

import java.util.List;
import java.util.Map;

/**
 * <a href="Route.java.html"><b><i>View Source</i></b></a>
 *
 * @author Connor McKay
 * @author Brian Wing Shun Chan
 */
public interface Route {

	public void addDefaultParameter(String name, String value);

	public void addIgnoredParameter(String name);

	public Map<String, String> getDefaultParameters();

	public List<String> getIgnoredParameters();

	public String parametersToUrl(Map<String, ?> parameters);

	public Map<String, String> urlToParameters(String url);

}