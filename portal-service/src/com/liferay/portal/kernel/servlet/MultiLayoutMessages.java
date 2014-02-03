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

package com.liferay.portal.kernel.servlet;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class MultiLayoutMessages {

	public static void add(PortletRequest portletRequest, Class<?> clazz) {
		SessionMessages.add(portletRequest, clazz.getName());
	}

	public static void add(
		PortletRequest portletRequest, Class<?> clazz, Object value) {

		SessionMessages.add(portletRequest, clazz.getName(), value);
	}

	public static void add(PortletRequest portletRequest, String key) {
		SessionMessages.add(portletRequest, key);
	}

	public static void add(
		PortletRequest portletRequest, String key, Object value) {

		SessionMessages.add(portletRequest, key, value);
	}

	public static void clear(PortletRequest portletRequest) {
		SessionMessages.clear(portletRequest);
	}

	public static boolean contains(
		PortletRequest portletRequest, Class<?> clazz) {

		return SessionMessages.contains(
			portletRequest, clazz.getName());
	}

	public static boolean contains(PortletRequest portletRequest, String key) {
		return SessionMessages.contains(portletRequest, key);
	}

	public static Object get(PortletRequest portletRequest, Class<?> clazz) {
		return SessionMessages.get(portletRequest, clazz.getName());
	}

	public static Object get(PortletRequest portletRequest, String key) {
		return SessionMessages.get(portletRequest, key);
	}

	public static boolean isEmpty(PortletRequest portletRequest) {
		return SessionMessages.isEmpty(portletRequest);
	}

	public static Iterator<String> iterator(PortletRequest portletRequest) {
		return SessionMessages.iterator(portletRequest);
	}

	public static Set<String> keySet(PortletRequest portletRequest) {
		return SessionMessages.keySet(portletRequest);
	}

	public static void print(PortletRequest portletRequest) {
		SessionMessages.print(portletRequest);
	}

	public static int size(PortletRequest portletRequest) {
		return SessionMessages.size(portletRequest);
	}

}