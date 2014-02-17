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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.Portlet;

import java.util.List;

/**
 * @author Carlos Sierra Andrés
 */
public interface PortletResourceAccessor
	extends Accessor<Portlet, List<String>> {

	public static PortletResourceAccessor footerPortalCss =
		new PortletPortalResourcesAccessor() {
			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getFooterPortalCss();
			}
		};
	public static PortletResourceAccessor footerPortalJavaScript =
		new PortletPortalResourcesAccessor() {
			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getFooterPortalJavaScript();
			}
		};
	public static PortletResourceAccessor footerPortletCss =
		new PortletResourceDefaultAccessor() {
			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getFooterPortletCss();
			}
		};
	public static PortletResourceAccessor footerPortletJavaScript =
		new PortletResourceDefaultAccessor() {
			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getFooterPortletJavaScript();
			}
		};
	public static PortletResourceAccessor headerPortalCss =
		new PortletPortalResourcesAccessor() {
			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getHeaderPortalCss();
			}
		};
	public static PortletResourceAccessor headerPortalJavaScript =
		new PortletPortalResourcesAccessor() {
			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getHeaderPortalJavaScript();
			}
		};
	public static PortletResourceAccessor headerPortletCss =
		new PortletResourceDefaultAccessor() {
			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getHeaderPortletCss();
			}
		};
	public static PortletResourceAccessor headerPortletJavaScript =
		new PortletResourceDefaultAccessor() {
			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getHeaderPortletJavaScript();
			}
		};

	public boolean isPortalResource();

	public static abstract class PortletPortalResourcesAccessor
		implements PortletResourceAccessor {

		@Override
		public boolean isPortalResource() {
			return true;
		}
	}

	public static abstract class PortletResourceDefaultAccessor
		implements PortletResourceAccessor {

		@Override
		public boolean isPortalResource() {
			return false;
		}
	}

}