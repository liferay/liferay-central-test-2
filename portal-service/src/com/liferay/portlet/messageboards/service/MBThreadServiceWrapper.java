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

package com.liferay.portlet.messageboards.service;


/**
 * <a href="MBThreadServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link MBThreadService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBThreadService
 * @generated
 */
public class MBThreadServiceWrapper implements MBThreadService {
	public MBThreadServiceWrapper(MBThreadService mbThreadService) {
		_mbThreadService = mbThreadService;
	}

	public void deleteThread(long threadId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbThreadService.deleteThread(threadId);
	}

	public com.liferay.portal.model.Lock lockThread(long threadId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadService.lockThread(threadId);
	}

	public com.liferay.portlet.messageboards.model.MBThread moveThread(
		long categoryId, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadService.moveThread(categoryId, threadId);
	}

	public com.liferay.portlet.messageboards.model.MBThread splitThread(
		long messageId, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadService.splitThread(messageId, serviceContext);
	}

	public void unlockThread(long threadId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbThreadService.unlockThread(threadId);
	}

	public MBThreadService getWrappedMBThreadService() {
		return _mbThreadService;
	}

	private MBThreadService _mbThreadService;
}