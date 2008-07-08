/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.ruon.service.impl;

import com.liferay.portal.ruon.service.base.PresenceStatusesLocalServiceBaseImpl;
import com.liferay.portal.ruon.service.PresenceStatusesLocalServiceUtil;
import com.liferay.portal.ruon.model.PresenceStatuses;
import com.liferay.portal.ruon.model.impl.PresenceStatusesImpl;
import com.liferay.portal.ruon.util.RUONException;

/**
 * <a href="PresenceStatusesLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PresenceStatusesLocalServiceImpl
        extends PresenceStatusesLocalServiceBaseImpl {

    public void populateWithDefaultStatuses() throws RUONException {
        try {
            if (presenceStatusesPersistence.countAll() == 0) {
                PresenceStatuses pstatusesOnline = new PresenceStatusesImpl();
                pstatusesOnline.setPresenceStatusId(1);
                pstatusesOnline.setPresenceStatusMessage("Online");
                PresenceStatusesLocalServiceUtil.addPresenceStatuses(pstatusesOnline);
                PresenceStatuses pstatusesoffline = new PresenceStatusesImpl();
                pstatusesoffline.setPresenceStatusId(2);
                pstatusesoffline.setPresenceStatusMessage("Offline");
                PresenceStatusesLocalServiceUtil.addPresenceStatuses(pstatusesoffline);
            }
        } catch (Exception e) {
            throw new RUONException(e);
        }
    }
}