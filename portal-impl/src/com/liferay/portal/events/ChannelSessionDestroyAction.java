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

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SessionAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.notifications.util.ChannelHubManagerUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;

import javax.servlet.http.HttpSession;

/**
 * @author Edward Han
 */
public class ChannelSessionDestroyAction extends SessionAction {
    @Override
    public void run(HttpSession session) throws ActionException {
        try {
            long userId = (Long)session.getAttribute(WebKeys.USER_ID);

            if (userId >= 0) {
                User user = UserLocalServiceUtil.getUser(userId);

                if (!user.isDefaultUser()) {
                    ChannelHubManagerUtil.destroyChannel(
						user.getCompanyId(), userId);
                }
            }
        }
        catch (Exception e) {
            if (_log.isErrorEnabled()) {
                _log.error(e, e);
            }
        }

    }

    private static final Log _log =
		LogFactoryUtil.getLog(ChannelSessionDestroyAction.class);
}