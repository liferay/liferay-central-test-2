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

import com.liferay.portal.ruon.service.base.PresenceLocalServiceBaseImpl;
import com.liferay.portal.ruon.service.PresenceUserLocalServiceUtil;
import com.liferay.portal.ruon.model.PresenceUser;
import com.liferay.portal.ruon.model.impl.PresenceUserImpl;
import com.liferay.portal.ruon.util.RUONException;
import com.liferay.portal.ruon.NoSuchPresenceUserException;

import java.util.List;

/**
 * <a href="PresenceLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PresenceLocalServiceImpl extends PresenceLocalServiceBaseImpl {

    public void makeUserOffline(Long userId) throws RUONException {
        try {

            PresenceUser pUser = PresenceUserLocalServiceUtil.getPresenceUser(userId);
            pUser.setPresenceStatus(2);
            PresenceUserLocalServiceUtil.updatePresenceUser(pUser);


        } catch (Exception e) {
            throw new RUONException(e);
        }
    }

    public void makeUserOnline(Long userId) throws RUONException {
        try {

            PresenceUser pUser = PresenceUserLocalServiceUtil.getPresenceUser(userId);
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

    public List<PresenceUser> getListOfAllUsers()throws RUONException{
        return PresenceUserLocalServiceUtil.getAllUsers();
    }

    public void makeAllUsersOffline()throws RUONException{
        for(PresenceUser puser : getListOfAllUsers()){
            Long puserId = puser.getPresenceUserId();
            makeUserOffline(puserId);
        }
    }

}