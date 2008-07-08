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

import com.liferay.portal.ruon.service.base.CommunicationLocalServiceBaseImpl;
import com.liferay.portal.ruon.service.PresenceUserLocalServiceUtil;
import com.liferay.portal.ruon.util.RUONException;
import com.liferay.portal.ruon.model.PresenceUser;
import com.liferay.portal.SystemException;
import com.liferay.portal.PortalException;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.List;
import java.util.ArrayList;


/**
 * <a href="CommunicationLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CommunicationLocalServiceImpl
        extends CommunicationLocalServiceBaseImpl {

    public List getListOfWaysToCommunicate(Long userId, Long loggedInUserId) throws RUONException {
        List listOfWays = new ArrayList();
        try {
            PresenceUser puser = PresenceUserLocalServiceUtil.getPresenceUser(userId);
            if (puser != null && isChatPortletDeployed() && isUserInSameGroup(userId, loggedInUserId)) {

                listOfWays.add("<a onclick=\"Liferay.Chat.openChatWindow('" + userId + "');\" href=\"javascript: ;\">" + "Chat" + "</a>");
                return listOfWays;

            }
            return listOfWays;

        } catch (Exception e) {
            throw new RUONException(e);

        }
    }

    private boolean isChatPortletDeployed() {
        boolean isChatdeployed = false;
        for (Portlet p : PortletLocalServiceUtil.getPortlets()) {
            if (p.getDisplayName().equalsIgnoreCase("Chat")) isChatdeployed = true;
        }

        return isChatdeployed;
    }

    private boolean isUserInSameGroup(Long userId, Long loggedInUserId) throws RUONException {
        try {
            Long userGroupId = null;
            Long loggedInUserGroupId = null;
            if(UserLocalServiceUtil.getUser(userId).getGroup()!=null){
            userGroupId = UserLocalServiceUtil.getUser(userId).getGroup().getGroupId();
            }
            if(UserLocalServiceUtil.getUser(loggedInUserId).getGroup()!=null){
             loggedInUserGroupId = UserLocalServiceUtil.getUser(loggedInUserId).getGroup().getGroupId();
            }
            if (userGroupId!=null && loggedInUserGroupId !=null &&
                    !userId.equals(loggedInUserId)&& userGroupId.equals(loggedInUserGroupId)) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            throw new RUONException(e);
        }

    }


}