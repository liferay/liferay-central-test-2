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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.notifications.util.ChannelHubManagerUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Edward Han
 */
public class ChannelLoginPostAction extends Action {
    @Override
    public void run(HttpServletRequest request, HttpServletResponse response)
		throws ActionException {

		try {
			long userId = PortalUtil.getUserId(request);
			long companyId = PortalUtil.getCompanyId(request);

			if (userId >= 0 &&
				userId != UserLocalServiceUtil.getDefaultUserId(companyId)) {

				ChannelHubManagerUtil.createChannel(companyId, userId);
            }
        }
        catch (Exception e) {
            if (_log.isErrorEnabled()) {
                _log.error(e, e);
            }
        }
    }

    private static final Log _log =
		LogFactoryUtil.getLog(ChannelLoginPostAction.class);
}