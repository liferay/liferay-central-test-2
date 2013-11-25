/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.spring.util;

import com.liferay.portal.bean.BeanLocatorImpl;
import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.spring.context.ArrayApplicationContext;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsUtil;

import java.util.List;

import org.springframework.context.support.AbstractApplicationContext;

/**
 * <p>
 * In most cases, SpringUtil.setContext() would have been called by
 * com.liferay.portal.spring.context.PortalContextLoaderListener, configured in
 * web.xml for the web application. However, there will be times in which
 * SpringUtil will be called in a non-web application and, therefore, require
 * manual instantiation of the application context.
 * </p>
 *
 * @author Michael Young
 */
public class SpringUtil {

	public static void loadContext() {
		_loadContext(PropsUtil.getArray(PropsKeys.SPRING_CONFIGS));
	}

	public static void loadContext(List<String> extraConfigLocations) {
		List<String> configLocations = ListUtil.fromArray(
			PropsUtil.getArray(PropsKeys.SPRING_CONFIGS));

		if (extraConfigLocations != null) {
			configLocations.addAll(extraConfigLocations);
		}

		_loadContext(
			configLocations.toArray(new String[configLocations.size()]));
	}

	private static void _loadContext(String[] configLocations) {
		AbstractApplicationContext applicationContext =
			new ArrayApplicationContext(configLocations);

		BeanLocator beanLocator = new BeanLocatorImpl(
			ClassLoaderUtil.getPortalClassLoader(), applicationContext);

		PortalBeanLocatorUtil.setBeanLocator(beanLocator);
	}

}