/* Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.ruon.service.impl;

import com.liferay.portal.ruon.NoSuchPresenceUserException;
import com.liferay.portal.ruon.model.PresenceUser;
import com.liferay.portal.ruon.model.impl.PresenceUserImpl;
import com.liferay.portal.ruon.service.PresenceStatusesLocalServiceUtil;
import com.liferay.portal.ruon.service.PresenceUserLocalServiceUtil;
import com.liferay.portal.ruon.service.base.PresenceLocalServiceBaseImpl;
import com.liferay.portal.ruon.util.RUONException;

import java.util.List;

/**
 * <a href="PresenceLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PresenceLocalServiceImpl extends PresenceLocalServiceBaseImpl {

    public List<PresenceUser> getListOfAllUsers()throws RUONException{
        return PresenceUserLocalServiceUtil.getAllUsers();
    }

   public String getPresenceStatusOfUser(Long userId)throws RUONException{
        try{
        Long presenceStatus = PresenceUserLocalServiceUtil.
                                    getPresenceUser(userId).getPresenceStatus();

        String presenceMessage =
                    PresenceStatusesLocalServiceUtil.getPresenceStatuses(
                            presenceStatus).getPresenceStatusMessage();

            if (presenceMessage.equalsIgnoreCase("online")){
               return "<img src=\"/ruon/images/status_online.png\"/>";
            }else if (presenceMessage.equalsIgnoreCase("offline")){
                return "<img src=\"/ruon/images/status_offline.png\"/>";
            }else{
               return "";
            }
        }catch(Exception e){
            throw  new RUONException(e);
        }
    }

    public boolean isUserOnline(Long userId) throws RUONException{
      try{
        Long presenceStatus = PresenceUserLocalServiceUtil.
                                    getPresenceUser(userId).getPresenceStatus();

        String presenceMessage =
                    PresenceStatusesLocalServiceUtil.getPresenceStatuses(
                            presenceStatus).getPresenceStatusMessage();

            if (presenceMessage.equalsIgnoreCase("online")){
               return true;
            }else if (presenceMessage.equalsIgnoreCase("offline")){
                return false;
            }else{
               return false;
            }
        }catch(Exception e){
            throw  new RUONException(e);
        }
    }

    public void makeAllUsersOffline()throws RUONException{
        for(PresenceUser puser : getListOfAllUsers()){
            Long puserId = puser.getPresenceUserId();
            makeUserOffline(puserId);
        }
    }

    public void makeUserOffline(Long userId) throws RUONException {
        try {
            PresenceUser pUser = PresenceUserLocalServiceUtil.
                                            getPresenceUser(userId);

            pUser.setPresenceStatus(2);
            PresenceUserLocalServiceUtil.updatePresenceUser(pUser);
        } catch (Exception e) {
            throw new RUONException(e);
        }
    }

    public void makeUserOnline(Long userId) throws RUONException {
        try {
            PresenceUser pUser = PresenceUserLocalServiceUtil.
                                            getPresenceUser(userId);

            pUser.setPresenceStatus(1);
            PresenceUserLocalServiceUtil.updatePresenceUser(pUser);
        }catch (NoSuchPresenceUserException e) {
            PresenceUser pUserNew = new PresenceUserImpl();
            pUserNew.setPresenceUserId(userId);
            pUserNew.setPresenceStatus(1);
            try {
                PresenceUserLocalServiceUtil.addPresenceUser(pUserNew);
            } catch (Exception se) {
                throw new RUONException(se);
            }
        }catch (Exception e) {
            throw new RUONException(e);
        }
    }

}