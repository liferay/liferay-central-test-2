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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.notifications.ChannelException;
import com.liferay.portal.kernel.notifications.util.ChannelHubManagerUtil;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Edward Han
 */
public class ChannelHubAppShutdownAction extends SimpleAction {
    @Override
    public void run(String[] ids) throws ActionException {
        if (ids.length > 0) {
            long companyId = GetterUtil.get(ids[0], 0L);

            if (companyId > 0) {
                try {
                    ChannelHubManagerUtil.destroyChannelHub(companyId);
                }
                catch (ChannelException e) {
                    if (_log.isErrorEnabled()) {
                        _log.error("Unable to destroy ChannelHub for companyId "
								+ companyId, e);
                    }
                }
            }
        }
    }

    private static final Log _log =
		LogFactoryUtil.getLog(ChannelHubAppShutdownAction.class);
}