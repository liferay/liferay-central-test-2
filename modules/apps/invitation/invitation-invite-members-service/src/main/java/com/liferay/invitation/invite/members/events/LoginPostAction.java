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

package com.liferay.invitation.invite.members.events;

import com.liferay.invitation.invite.members.service.MemberRequestLocalServiceUtil;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eduardo Garcia
 */
public class LoginPostAction extends Action {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
		throws ActionException {

		try {
			String ppid = ParamUtil.getString(request, "p_p_id");

			String portletNamespace = PortalUtil.getPortletNamespace(ppid);

			String memberRequestKey = ParamUtil.getString(
				request, portletNamespace + "key");

			if (Validator.isNull(memberRequestKey)) {
				return;
			}

			User user = PortalUtil.getUser(request);

			MemberRequestLocalServiceUtil.updateMemberRequest(
				memberRequestKey, user.getUserId());
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

}