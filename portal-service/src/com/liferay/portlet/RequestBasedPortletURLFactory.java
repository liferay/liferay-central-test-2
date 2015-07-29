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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public abstract class RequestBasedPortletURLFactory {

	public static RequestBasedPortletURLFactory create(
		HttpServletRequest request) {

		return new HttpServletRequestRequestBasedPortletURLFactory(request);
	}

	public static RequestBasedPortletURLFactory create(
		PortletRequest portletRequest) {

		PortletResponse portletResponse =
			(PortletResponse)portletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		if (portletResponse == null) {
			return create(PortalUtil.getHttpServletRequest(portletRequest));
		}

		return new LiferayPortletResponseRequestBasedPortletURLFactory(
			PortalUtil.getLiferayPortletResponse(portletResponse));
	}

	public abstract PortletURL createActionURL(String portletId);

	public abstract PortletURL createRenderURL(String portletId);

	public abstract PortletURL createResourceURL(String portletId);

	private static class HttpServletRequestRequestBasedPortletURLFactory
		extends RequestBasedPortletURLFactory {

		@Override
		public PortletURL createActionURL(String portletId) {
			String actionPhase = PortletRequest.ACTION_PHASE;

			return createPortletURL(portletId, actionPhase);
		}

		@Override
		public PortletURL createRenderURL(String portletId) {
			return createPortletURL(portletId, PortletRequest.RENDER_PHASE);
		}

		@Override
		public PortletURL createResourceURL(String portletId) {
			return createPortletURL(portletId, PortletRequest.RESOURCE_PHASE);
		}

		protected PortletURL createPortletURL(
			String portletId, String lifecycle) {

			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			return PortletURLFactoryUtil.create(
				_request, portletId, themeDisplay.getPlid(), lifecycle);
		}

		private HttpServletRequestRequestBasedPortletURLFactory(
			HttpServletRequest request) {

			_request = request;
		}

		private final HttpServletRequest _request;

	}

	private static class LiferayPortletResponseRequestBasedPortletURLFactory
		extends RequestBasedPortletURLFactory {

		@Override
		public PortletURL createActionURL(String portletId) {
			return _liferayPortletResponse.createActionURL(portletId);
		}

		@Override
		public PortletURL createRenderURL(String portletId) {
			return _liferayPortletResponse.createRenderURL(portletId);
		}

		@Override
		public PortletURL createResourceURL(String portletId) {
			return _liferayPortletResponse.createResourceURL(portletId);
		}

		private LiferayPortletResponseRequestBasedPortletURLFactory(
			LiferayPortletResponse liferayPortletResponse) {

			_liferayPortletResponse = liferayPortletResponse;
		}

		private final LiferayPortletResponse _liferayPortletResponse;

	}

}