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

package com.liferay.portal.kernel.notifications;

/**
 * @author Edward Han
 */
public class UnknownChannelHubException extends ChannelException {
	public UnknownChannelHubException(long companyId) {
		_companyId = companyId;
	}

	public UnknownChannelHubException(Throwable cause, long companyId) {
		super(cause);
		_companyId = companyId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public String getMessage() {
		return MESSAGE_PREFIX + _companyId;
	}

	protected long _companyId;

	protected final String MESSAGE_PREFIX =
		"Unable to locate ChannelHub associated with companyId: ";
}