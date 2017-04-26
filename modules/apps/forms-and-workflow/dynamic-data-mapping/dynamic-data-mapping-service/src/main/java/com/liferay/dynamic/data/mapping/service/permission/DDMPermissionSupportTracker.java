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

package com.liferay.dynamic.data.mapping.service.permission;

import com.liferay.dynamic.data.mapping.util.DDMStructurePermissionSupport;
import com.liferay.dynamic.data.mapping.util.DDMTemplatePermissionSupport;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDMPermissionSupportTracker.class)
public class DDMPermissionSupportTracker {

	public ServiceWrapper<DDMStructurePermissionSupport>
			getDDMStructurePermissionSupportServiceWrapper(long classNameId)
		throws PortalException {

		String className = _portal.getClassName(classNameId);

		ServiceWrapper<DDMStructurePermissionSupport>
			ddmStructurePermissionSupportServiceWrapper =
				_ddmStructurePermissionSupportServiceTrackerMap.getService(
					className);

		if (ddmStructurePermissionSupportServiceWrapper == null) {
			throw new PortalException(
				"The model does not support DDMStructure permission checking " +
					className);
		}

		return ddmStructurePermissionSupportServiceWrapper;
	}

	public ServiceWrapper<DDMTemplatePermissionSupport>
			getDDMTemplatePermissionSupportServiceWrapper(
				long resourceClassNameId)
		throws PortalException {

		return getDDMTemplatePermissionSupportServiceWrapper(
			_portal.getClassName(resourceClassNameId));
	}

	public ServiceWrapper<DDMTemplatePermissionSupport>
			getDDMTemplatePermissionSupportServiceWrapper(
				String resourceClassName)
		throws PortalException {

		ServiceWrapper<DDMTemplatePermissionSupport>
			ddmTemplatePermissionSupportServiceWrapper =
				_ddmTemplatePermissionSupportServiceTrackerMap.getService(
					resourceClassName);

		if (ddmTemplatePermissionSupportServiceWrapper == null) {
			throw new PortalException(
				"The model does not support DDMTemplate permission checking " +
					resourceClassName);
		}

		return ddmTemplatePermissionSupportServiceWrapper;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_ddmStructurePermissionSupportServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, DDMStructurePermissionSupport.class,
				"model.class.name",
				ServiceTrackerCustomizerFactory.
					<DDMStructurePermissionSupport>serviceWrapper(
						bundleContext));

		_ddmTemplatePermissionSupportServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, DDMTemplatePermissionSupport.class,
				"model.class.name",
				ServiceTrackerCustomizerFactory.
					<DDMTemplatePermissionSupport>serviceWrapper(
						bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_ddmStructurePermissionSupportServiceTrackerMap.close();

		_ddmTemplatePermissionSupportServiceTrackerMap.close();
	}

	private ServiceTrackerMap
		<String, ServiceWrapper<DDMStructurePermissionSupport>>
			_ddmStructurePermissionSupportServiceTrackerMap;
	private ServiceTrackerMap
		<String, ServiceWrapper<DDMTemplatePermissionSupport>>
			_ddmTemplatePermissionSupportServiceTrackerMap;

	@Reference
	private Portal _portal;

}