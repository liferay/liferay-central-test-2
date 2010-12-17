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

package com.liferay.portal.kernel.longpoll.comet.impl;

import com.liferay.portal.kernel.longpoll.comet.CometRequest;

/**
 * @author Edward Han
 */
public abstract class BaseCometRequestImpl implements CometRequest {
	
	public BaseCometRequestImpl(
		long companyId, String pathInfo, long timestamp, long userId) {

		_companyId = companyId;
		_pathInfo = pathInfo;
		_timestamp = timestamp;
		_userId = userId;
	}

	public long getCompanyId() {
        return _companyId;
    }

	public String getPathInfo() {
		return _pathInfo;
	}

	public long getTimestamp() {
        return _timestamp;
    }

    public long getUserId() {
        return _userId;
    }

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setPathInfo(String pathInfo) {
		_pathInfo = pathInfo;
	}

	public void setTimestamp(long timestamp) {
		_timestamp = timestamp;
	}

    public void setUserId(long userId) {
        _userId = userId;
    }

    private long _companyId;
	private String _pathInfo;
    private long _timestamp;
    private long _userId;
	
}