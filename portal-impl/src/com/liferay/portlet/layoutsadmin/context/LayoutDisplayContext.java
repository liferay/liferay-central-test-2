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

package com.liferay.portlet.layoutsadmin.context;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutDisplayContext {

	public LayoutDisplayContext(HttpServletRequest request) {
		_request = request;
	}

	public Group getGroup() {
		if (_group != null) {
			return _group;
		}

		if (getStagingGroup() != null) {
			_group = getStagingGroup();
		}
		else {
			_group = getLiveGroup();
		}

		return _group;
	}

	public Long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = getLiveGroup().getGroupId();

		if (getGroup() != null) {
			_groupId = getGroup().getGroupId();
		}

		return _groupId;
	}

	public Group getLiveGroup() {
		if (_liveGroup != null) {
			return _liveGroup;
		}

		if (getSelGroup().isStagingGroup()) {
			_liveGroup = getSelGroup().getLiveGroup();
		}
		else {
			_liveGroup = getSelGroup();
		}

		return _liveGroup;
	}

	public Long getRefererPlid() {
		if (_refererPlid != null) {
			return _refererPlid;
		}

		_refererPlid = ParamUtil.getLong(
			_request, "refererPlid", LayoutConstants.DEFAULT_PLID);

		return _refererPlid;
	}

	public Group getSelGroup() {
		if (_selGroup != null) {
			return _selGroup;
		}

		_selGroup = (Group)_request.getAttribute(WebKeys.GROUP);

		return _selGroup;
	}

	public Long getSelPlid() {
		if (_selPlid != null) {
			return _selPlid;
		}

		_selPlid = ParamUtil.getLong(
			_request, "selPlid", LayoutConstants.DEFAULT_PLID);

		return _selPlid;
	}

	public Group getStagingGroup() {
		if (_stagingGroup != null) {
			return _stagingGroup;
		}

		Group selGroup = getSelGroup();

		if (selGroup.isStagingGroup()) {
			_stagingGroup = selGroup;
		}
		else {
			if (selGroup.isStaged()) {
				if (selGroup.isStagedRemotely()) {
					_stagingGroup = selGroup;
				}
				else {
					_stagingGroup = selGroup.getStagingGroup();
				}
			}
		}

		return _stagingGroup;
	}

	public Long getStagingGroupId() {
		if (_stagingGroupId != null) {
			return _stagingGroupId;
		}

		_stagingGroupId = 0L;

		if (getStagingGroup() != null) {
			_stagingGroupId = getStagingGroup().getGroupId();
		}

		return _stagingGroupId;
	}

	private Group _group;
	private Long _groupId;
	private Group _liveGroup;
	private Long _refererPlid;
	private HttpServletRequest _request;
	private Group _selGroup;
	private Long _selPlid;
	private Group _stagingGroup;
	private Long _stagingGroupId;

}