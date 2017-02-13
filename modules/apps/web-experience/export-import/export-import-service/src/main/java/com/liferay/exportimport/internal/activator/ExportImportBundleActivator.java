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

package com.liferay.exportimport.internal.activator;

import com.liferay.portal.kernel.service.ServiceContextCallbackUtil;
import com.liferay.portlet.exportimport.staging.ProxiedLayoutsThreadLocal;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Akos Thurzo
 */
public class ExportImportBundleActivator implements BundleActivator {

	public static final String DOT_CLEAR_PROXIED_LAYOUTS =
		".clearProxiedLayouts";

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Bundle bundle = bundleContext.getBundle();

		ServiceContextCallbackUtil.registerPopCallback(
			bundle.getSymbolicName() + DOT_CLEAR_PROXIED_LAYOUTS,
			() -> {
				ProxiedLayoutsThreadLocal.clearProxiedLayouts();
				return null;
			});

		ServiceContextCallbackUtil.registerPushCallback(
			bundle.getSymbolicName() + DOT_CLEAR_PROXIED_LAYOUTS,
			() -> {
				ProxiedLayoutsThreadLocal.clearProxiedLayouts();
				return null;
			});
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		Bundle bundle = bundleContext.getBundle();

		ServiceContextCallbackUtil.unRegisterPopCallback(
			bundle.getSymbolicName() + DOT_CLEAR_PROXIED_LAYOUTS);

		ServiceContextCallbackUtil.unRegisterPushCallback(
			bundle.getSymbolicName() + DOT_CLEAR_PROXIED_LAYOUTS);
	}

}