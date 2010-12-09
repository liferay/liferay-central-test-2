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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.VirtualHost;
import com.liferay.portal.service.base.VirtualHostLocalServiceBaseImpl;

/**
 * @author Alexander Chow
 */
public class VirtualHostLocalServiceImpl
	extends VirtualHostLocalServiceBaseImpl {

	public VirtualHost addVirtualHost(
			long companyId, long layoutSetId, String virtualHostName)
		throws SystemException {

		long virtualHostId = counterLocalService.increment();

		VirtualHost virtualHostObj = virtualHostPersistence.create(
			virtualHostId);

		virtualHostObj.setCompanyId(companyId);
		virtualHostObj.setLayoutSetId(layoutSetId);
		virtualHostObj.setVirtualHostName(virtualHostName);

		virtualHostPersistence.update(virtualHostObj, false);

		return virtualHostObj;
	}

	public VirtualHost getVirtualHost(long companyId, long layoutSetId)
		throws PortalException, SystemException {

		return virtualHostPersistence.findByC_L(companyId, layoutSetId);
	}

	public VirtualHost getVirtualHost(String virtualHostName)
		throws PortalException, SystemException {

		return virtualHostPersistence.findByVirtualHostName(virtualHostName);
	}

}