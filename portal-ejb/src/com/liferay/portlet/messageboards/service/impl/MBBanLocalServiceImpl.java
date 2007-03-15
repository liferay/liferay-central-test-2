/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.BanUserException;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.service.base.MBBanLocalServiceBaseImpl;
import com.liferay.portlet.messageboards.service.persistence.MBBanUtil;

import java.util.Date;

/**
 * <a href="MBBanLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBBanLocalServiceImpl extends MBBanLocalServiceBaseImpl {

	public MBBan addBan(String userId, String plid, String banUserId) 
		throws PortalException, SystemException {
		
		User user = UserUtil.findByPrimaryKey(userId);
		long groupId = PortalUtil.getPortletGroupId(plid);
		Date now = new Date();

		String categoryId = String.valueOf(CounterLocalServiceUtil.increment(
			MBBan.class.getName()));

		MBBan ban = MBBanUtil.create(categoryId);
		
		ban.setGroupId(groupId);
		ban.setCompanyId(user.getCompanyId());
		ban.setUserId(user.getUserId());
		ban.setUserName(user.getFullName());
		ban.setCreateDate(now);
		ban.setModifiedDate(now);
		ban.setBanUserId(banUserId);
		
		MBBanUtil.update(ban);
		
		return ban;
	}
	
	public void checkBan(String plid, String banUserId) 
		throws PortalException, SystemException {
	
		long groupId = PortalUtil.getPortletGroupId(plid);

		checkBan(groupId, banUserId);
	}
	
	public void checkBan(long groupId, String banUserId) 
		throws PortalException, SystemException {

		if (getBan(groupId, banUserId) != null) {
			throw new BanUserException();
		}
	}
	
	public void deleteBan(String banId) 
		throws PortalException, SystemException {
		
		MBBanUtil.remove(banId);
	}

	public MBBan getBan(String plid, String banUserId) 
		throws SystemException {
		
		long groupId = PortalUtil.getPortletGroupId(plid);

		return getBan(groupId, banUserId);
	}

	public MBBan getBan(long groupId, String banUserId) 
		throws SystemException {

		return MBBanUtil.fetchByG_B(groupId, banUserId);
	}
}