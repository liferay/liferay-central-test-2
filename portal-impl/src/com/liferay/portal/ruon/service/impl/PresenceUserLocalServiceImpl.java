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

import com.liferay.portal.ruon.service.base.PresenceUserLocalServiceBaseImpl;
import com.liferay.portal.ruon.service.PresenceUserLocalServiceUtil;
import com.liferay.portal.ruon.service.PresenceStatusesLocalServiceUtil;
import com.liferay.portal.ruon.util.RUONException;

import java.util.List;

/**
 * <a href="PresenceUserLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PresenceUserLocalServiceImpl
    extends PresenceUserLocalServiceBaseImpl {

    public String getPresenceStatusOfUser(Long userId)throws RUONException{
        try{
        Long presenceStatus = PresenceUserLocalServiceUtil.getPresenceUser(userId).getPresenceStatus();
        String presenceMessage      =  PresenceStatusesLocalServiceUtil.getPresenceStatuses(presenceStatus).getPresenceStatusMessage();
            if(presenceMessage.equalsIgnoreCase("online")){
               return "<img src=\"/ruon/images/status_online.png\"/>";
            }else if(presenceMessage.equalsIgnoreCase("offline")){
                return "<img src=\"/ruon/images/status_offline.png\"/>";
            }else{
               return "";
            }
        }catch(Exception e){
            throw  new RUONException(e);
        }
    }

    public List getAllUsers()throws RUONException{
        try{
       return  presenceUserPersistence.findAll();
        }catch(Exception e){
            throw new RUONException(e);
        }
    }
}