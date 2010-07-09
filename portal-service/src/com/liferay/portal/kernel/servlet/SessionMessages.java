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

package com.liferay.portal.kernel.servlet;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 */
public class SessionMessages {

	public static final String KEY = SessionMessages.class.getName();

	// Servlet Request

	public static void add(HttpServletRequest request, String key) {
		add(request.getSession(), key);
	}

	public static void add(HttpSession session, String key) {
		Map<String, Object> messages = _getMessages(session);

		messages.put(key, key);
	}

	public static void add(
		HttpServletRequest request, String key, Object value) {

		add(request.getSession(), key, value);
	}

	public static void add(HttpSession session, String key, Object value) {
		Map<String, Object> messages = _getMessages(session);

		messages.put(key, value);
	}

	public static void clear(HttpServletRequest request) {
		clear(request.getSession());
	}

	public static void clear(HttpSession session) {
		Map<String, Object> messages = _getMessages(session);

		messages.clear();
	}

	public static boolean contains(HttpServletRequest request, String key) {
		return contains(request.getSession(), key);
	}

	public static boolean contains(HttpSession session, String key) {
		Map<String, Object> messages = _getMessages(session);

		return messages.containsKey(key);
	}

	public static Object get(HttpServletRequest request, String key) {
		return get(request.getSession(), key);
	}

	public static Object get(HttpSession session, String key) {
		Map<String, Object> messages = _getMessages(session);

		return messages.get(key);
	}

	public static boolean isEmpty(HttpServletRequest request) {
		return isEmpty(request.getSession());
	}

	public static boolean isEmpty(HttpSession session) {
		Map<String, Object> messages = _getMessages(session);

		return messages.isEmpty();
	}

	public static Iterator<String> iterator(HttpServletRequest request) {
		return iterator(request.getSession());
	}

	public static Iterator<String> iterator(HttpSession session) {
		Map<String, Object> messages = _getMessages(session);

		return Collections.unmodifiableSet(messages.keySet()).iterator();
	}

	public static void print(HttpServletRequest request) {
		print(request.getSession());
	}

	public static void print(HttpSession session) {
		Iterator<String> itr = iterator(session);

		while (itr.hasNext()) {
			System.out.println(itr.next());
		}
	}

	public static int size(HttpServletRequest request) {
		return size(request.getSession());
	}

	public static int size(HttpSession session) {
		Map<String, Object> messages = _getMessages(session);

		return messages.size();
	}

	private static Map<String, Object> _getMessages(HttpSession session) {
		Map<String, Object> messages = null;

		try {
			messages = (Map<String, Object>)session.getAttribute(KEY);

			if (messages == null) {
				messages = new LinkedHashMap<String, Object>();

				session.setAttribute(KEY, messages);
			}
		}
		catch (IllegalStateException ise) {
			messages = new LinkedHashMap<String, Object>();
		}

		return messages;
	}

	// Portlet Request

	public static void add(PortletRequest portletRequest, String key) {
		add(portletRequest.getPortletSession(), key);
	}

	public static void add(PortletSession portletSession, String key) {
		Map<String, Object> messages = _getMessages(portletSession);

		messages.put(key, key);
	}

	public static void add(
		PortletRequest portletRequest, String key, Object value) {

		add(portletRequest.getPortletSession(), key, value);
	}

	public static void add(
		PortletSession portletSession, String key, Object value) {

		Map<String, Object> messages = _getMessages(portletSession);

		messages.put(key, value);
	}

	public static void clear(PortletRequest portletRequest) {
		clear(portletRequest.getPortletSession());
	}

	public static void clear(PortletSession portletSession) {
		Map<String, Object> messages = _getMessages(portletSession);

		messages.clear();
	}

	public static boolean contains(PortletRequest portletRequest, String key) {
		return contains(portletRequest.getPortletSession(), key);
	}

	public static boolean contains(PortletSession portletSession, String key) {
		Map<String, Object> messages = _getMessages(portletSession);

		return messages.containsKey(key);
	}

	public static Object get(PortletRequest portletRequest, String key) {
		return get(portletRequest.getPortletSession(), key);
	}

	public static Object get(PortletSession portletSession, String key) {
		Map<String, Object> messages = _getMessages(portletSession);

		return messages.get(key);
	}

	public static boolean isEmpty(PortletRequest portletRequest) {
		return isEmpty(portletRequest.getPortletSession());
	}

	public static boolean isEmpty(PortletSession portletSession) {
		Map<String, Object> messages = _getMessages(portletSession);

		return messages.isEmpty();
	}

	public static Iterator<String> iterator(PortletRequest portletRequest) {
		return iterator(portletRequest.getPortletSession());
	}

	public static Iterator<String> iterator(PortletSession portletSession) {
		Map<String, Object> messages = _getMessages(portletSession);

		return Collections.unmodifiableSet(messages.keySet()).iterator();
	}

	public static void print(PortletRequest portletRequest) {
		print(portletRequest.getPortletSession());
	}

	public static void print(PortletSession portletSession) {
		Iterator<String> itr = iterator(portletSession);

		while (itr.hasNext()) {
			System.out.println(itr.next());
		}
	}

	public static int size(PortletRequest portletRequest) {
		return size(portletRequest.getPortletSession());
	}

	public static int size(PortletSession portletSession) {
		Map<String, Object> messages = _getMessages(portletSession);

		return messages.size();
	}

	private static Map<String, Object> _getMessages(
		PortletSession portletSession) {

		Map<String, Object> messages = null;

		try {
			messages = (Map<String, Object>)portletSession.getAttribute(KEY);

			if (messages == null) {
				messages = new LinkedHashMap<String, Object>();

				portletSession.setAttribute(KEY, messages);
			}
		}
		catch (IllegalStateException ise) {
			messages = new LinkedHashMap<String, Object>();
		}

		return messages;
	}

}