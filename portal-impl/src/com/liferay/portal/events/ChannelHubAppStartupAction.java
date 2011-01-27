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
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.notifications.ChannelException;
import com.liferay.portal.kernel.notifications.util.ChannelHubManagerUtil;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Edward Han
 */
public class ChannelHubAppStartupAction extends SimpleAction {
    @Override
    public void run(String[] ids) throws ActionException {
        if (ids.length > 0) {
            long companyId = GetterUtil.get(ids[0], 0L);

            if (companyId > 0) {
                try {
                    ChannelHubManagerUtil.createChannelHub(companyId);
                }
                catch (ChannelException e) {
                    throw new ActionException(e);
                }
            }
        }
    }
}