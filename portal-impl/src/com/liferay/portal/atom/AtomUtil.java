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

package com.liferay.portal.atom;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;

import javax.servlet.http.HttpServletRequest;

import org.apache.abdera.protocol.server.RequestContext;

/**
 * @author Igor Spasic
 */
public class AtomUtil {

	public static Company getCompany() throws SystemException, PortalException {

		long companyId = CompanyThreadLocal.getCompanyId().longValue();

		return CompanyLocalServiceUtil.getCompanyById(companyId);
	}

	public static int getIntParameter(
		RequestContext requestContext, String parameterName) {

		String value = requestContext.getParameter(parameterName);

		return GetterUtil.getInteger(value);
	}

	public static int getIntParameter(
		RequestContext requestContext, String parameterName,
		int defaultValue) {

		String value = requestContext.getParameter(parameterName);

		return GetterUtil.getInteger(value, defaultValue);
	}

	public static long getLongParameter(
		RequestContext requestContext, String parameterName) {

		String value = requestContext.getParameter(parameterName);

		return GetterUtil.getLong(value);
	}

	public static long getLongParameter(
		RequestContext requestContext, String parameterName,
		long defaultValue) {

		String value = requestContext.getParameter(parameterName);

		return GetterUtil.getLong(value, defaultValue);
	}

	public static AtomPager getPager(RequestContext requestContext) {
		return (AtomPager)requestContext.getAttribute(
			RequestContext.Scope.REQUEST, PAGER_ATTR_NAME);
	}

	public static User getUser(RequestContext requestContext) {
		return (User)requestContext.getAttribute(
			RequestContext.Scope.REQUEST, USER_ATTR_NAME);
	}

	public static String resolveCollectionUrl(
		String url, String collectionName) {

		String collection = '/' + collectionName + '/';

		int collectionIndex = url.indexOf(collection);

		if (collectionIndex != -1) {

			collectionIndex += collectionName.length() + 1;

			int markIndex = url.indexOf('?', collectionIndex);

			if (markIndex != -1) {
				url = url.substring(0, collectionIndex) +
					url.substring(markIndex);
			}
			else {
				url = url.substring(0, collectionIndex);
			}
		}
		return url;
	}

	public static void saveAtomPagerInRequest(
		RequestContext requestContext, AtomPager atomPager) {

		requestContext.setAttribute(
			RequestContext.Scope.REQUEST, PAGER_ATTR_NAME, atomPager);

	}

	public static void saveUserInRequest(
		HttpServletRequest request, User user) {

		request.setAttribute(USER_ATTR_NAME, user);
	}

	public static String setPageInUrl(String url, int page) {

		int pageIndex = url.indexOf("page=");

		if (pageIndex == -1) {
			int markIndex = url.indexOf('?');

			if (markIndex == -1) {
				url += "?";
			}
			else {
				url += "&";
			}
			return url + "page=" + page;
		}

		int endIndex = url.indexOf('&', pageIndex);

		if (endIndex == -1) {
			url = url.substring(0, pageIndex);
		}
		else {
			url = url.substring(0, pageIndex) + url.substring(endIndex + 1);

			url += '&';
		}

		url += "page=" + page;

		return url;
	}

	private static final String PAGER_ATTR_NAME =
		AtomUtil.class.getName() + ".atomPager";

	private static final String USER_ATTR_NAME =
		AtomUtil.class.getName() + ".user";

}