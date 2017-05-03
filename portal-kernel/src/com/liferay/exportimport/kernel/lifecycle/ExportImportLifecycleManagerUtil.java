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

package com.liferay.exportimport.kernel.lifecycle;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.io.Serializable;

/**
 * @author Michael C. Han
 */
@ProviderType
public class ExportImportLifecycleManagerUtil {

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public static void fireExportImportLifecycleEvent(
		int code, int processFlag, Serializable... arguments) {

		_exportImportLifecycleManager.fireExportImportLifecycleEvent(
			code, processFlag, arguments);
	}

	public static void fireExportImportLifecycleEvent(
		int code, int processFlag, String processId,
		Serializable... arguments) {

		_exportImportLifecycleManager.fireExportImportLifecycleEvent(
			code, processFlag, processId, arguments);
	}

	private static volatile ExportImportLifecycleManager
		_exportImportLifecycleManager =
			ServiceProxyFactory.newServiceTrackedInstance(
				ExportImportLifecycleManager.class,
				ExportImportLifecycleManagerUtil.class,
				"_exportImportLifecycleManager", false);

}