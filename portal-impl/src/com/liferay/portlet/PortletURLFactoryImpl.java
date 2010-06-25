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

package com.liferay.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletURLListener;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.lang.reflect.Constructor;

import java.util.Set;

import javax.portlet.PortletException;
import javax.portlet.PortletModeException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURLGenerationListener;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="PortletURLFactoryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortletURLFactoryImpl implements PortletURLFactory {

	public LiferayPortletURL create(
		HttpServletRequest request, String portletName, long plid,
		String lifecycle) {

		LiferayPortletRequest liferayPortletRequest =
			(LiferayPortletRequest)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);
		LiferayPortletResponse liferayPortletResponse =
			(LiferayPortletResponse)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		if (liferayPortletRequest == null) {
			return new PortletURLImpl(request, portletName, plid, lifecycle);
		}

		try {
			Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

			PortletPreferences portletSetup =
				PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					layout, portletName);

			long layoutId = GetterUtil.getLong(portletSetup.getValue(
				"portlet-setup-link-to-layout-id", null));

			if (layoutId > 0) {
				try {
					Layout linkedLayout = LayoutLocalServiceUtil.getLayout(
						layout.getGroupId(), layout.isPrivateLayout(),
						layoutId);

					plid = linkedLayout.getPlid();
				}
				catch (PortalException pe) {
				}
			}
			else {

				// Backwards compatibility

				plid = GetterUtil.getLong(portletSetup.getValue(
					"portlet-setup-link-to-plid", String.valueOf(plid)));
			}
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(se, se);
			}
		}

		if (plid == LayoutConstants.DEFAULT_PLID) {
			plid = liferayPortletRequest.getPlid();
		}

		LiferayPortletURL liferayPortletURL = null;

		Portlet portlet = liferayPortletRequest.getPortlet();

		String portletURLClass = portlet.getPortletURLClass();

		if (portlet.getPortletId().equals(portletName) &&
			Validator.isNotNull(portletURLClass)) {

			try {
				Class<?> portletURLClassObj = Class.forName(portletURLClass);

				Constructor<?> constructor = portletURLClassObj.getConstructor(
					new Class[] {
						com.liferay.portlet.PortletResponseImpl.class,
						long.class, String.class
					});

				liferayPortletURL = (PortletURLImpl)constructor.newInstance(
					new Object[] {liferayPortletResponse, plid, lifecycle});
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (liferayPortletURL == null) {
			liferayPortletURL = new PortletURLImpl(
				request, portletName, plid, lifecycle);
		}

		PortletApp portletApp = portlet.getPortletApp();

		Set<PortletURLListener> portletURLListeners =
			portletApp.getPortletURLListeners();

		for (PortletURLListener portletURLListener : portletURLListeners) {
			try {
				PortletURLGenerationListener portletURLGenerationListener =
					PortletURLListenerFactory.create(portletURLListener);

				if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
					portletURLGenerationListener.filterActionURL(
						liferayPortletURL);
				}
				else if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
					portletURLGenerationListener.filterRenderURL(
						liferayPortletURL);
				}
				else if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
					portletURLGenerationListener.filterResourceURL(
						liferayPortletURL);
				}
			}
			catch (PortletException pe) {
				_log.error(pe, pe);
			}
		}

		try {
			liferayPortletURL.setWindowState(
				liferayPortletRequest.getWindowState());
		}
		catch (WindowStateException wse) {
			_log.error(wse, wse);
		}

		try {
			liferayPortletURL.setPortletMode(
				liferayPortletRequest.getPortletMode());
		}
		catch (PortletModeException pme) {
			_log.error(pme, pme);
		}

		if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
			liferayPortletURL.setCopyCurrentPublicRenderParameters(true);
		}

		if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
			liferayPortletURL.setCopyCurrentPublicRenderParameters(true);
			liferayPortletURL.setCopyCurrentRenderParameters(true);
		}

		return liferayPortletURL;
	}

	public LiferayPortletURL create(
		PortletRequest portletRequest, String portletName, long plid,
		String lifecycle) {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return create(request, portletName, plid, lifecycle);
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletURLFactoryImpl.class);

}