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

package com.liferay.portlet.social.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Zsolt Berentey
 */
public class SocialActivityToEquityActionMapper {

	public static void addMapping(int socialActivity, String equityAction) {
		actionToActivityMap.put(equityAction, socialActivity);

		activityToActionMap.put(socialActivity, equityAction);
	}

	public static void addMapping(
		String socialActivityKeyClass, String socialActivityKey,
		String equityAction) {

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		try {
			Class<?> keyConstants =
				classLoader.loadClass(socialActivityKeyClass);

			Integer socialActivity =
				(Integer)keyConstants.getField(socialActivityKey).get(null);

			addMapping(socialActivity, equityAction);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public static String getEquityAction(int socialActivity) {
		return activityToActionMap.get(socialActivity);
	}

	public static Integer getSocialActivity(String equityAction) {
		return actionToActivityMap.get(equityAction);
	}

	private static Log _log = LogFactoryUtil.getLog(
		SocialActivityToEquityActionMapper.class);

	private static Map<String, Integer> actionToActivityMap =
		new ConcurrentHashMap<String, Integer>();

	private static Map<Integer, String> activityToActionMap =
		new ConcurrentHashMap<Integer, String>();

}