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
 * <p>
 * This class is a wrapper for {@link MBMessageFlagService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMessageFlagService
 * @generated
 */
public class MBMessageFlagServiceWrapper implements MBMessageFlagService {
	public MBMessageFlagServiceWrapper(
		MBMessageFlagService mbMessageFlagService) {
		_mbMessageFlagService = mbMessageFlagService;
	}

	public void addAnswerFlag(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMessageFlagService.addAnswerFlag(messageId);
	}

	public void deleteAnswerFlag(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMessageFlagService.deleteAnswerFlag(messageId);
	}

	public MBMessageFlagService getWrappedMBMessageFlagService() {
		return _mbMessageFlagService;
	}

	public void setWrappedMBMessageFlagService(
		MBMessageFlagService mbMessageFlagService) {
		_mbMessageFlagService = mbMessageFlagService;
	}

	private MBMessageFlagService _mbMessageFlagService;
}