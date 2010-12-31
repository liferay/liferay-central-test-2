/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Set;

/**
 * @author Ryan Park
 * @author Brian Wing Shun Chan
 */
public class CustomJspRegistryImpl implements CustomJspRegistry {

	public CustomJspRegistryImpl() {
		_servletContextNames = new ConcurrentHashSet<String>();
	}

	public String getCustomJspFileName(
		String servletContextName, String fileName) {

		int pos = fileName.lastIndexOf(CharPool.PERIOD);

		if (pos == -1) {
			return fileName.concat(StringPool.PERIOD).concat(
				servletContextName);
		}

		StringBundler sb = new StringBundler(4);

		sb.append(fileName.substring(0, pos));
		sb.append(CharPool.PERIOD);
		sb.append(servletContextName);
		sb.append(fileName.substring(pos));

		return sb.toString();
	}

	public Set<String> getServletContextNames() {
		return _servletContextNames;
	}

	public void registerServletContextName(String servletContextName) {
		_servletContextNames.add(servletContextName);
	}

	public void unregisterServletContextName(String servletContextName) {
		_servletContextNames.remove(servletContextName);
	}

	private Set<String> _servletContextNames;

}