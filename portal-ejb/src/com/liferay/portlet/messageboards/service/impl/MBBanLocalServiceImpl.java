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

import com.liferay.counter.model.Counter;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.messageboards.BannedUserException;
import com.liferay.portlet.messageboards.NoSuchBanException;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.service.base.MBBanLocalServiceBaseImpl;
import com.liferay.portlet.messageboards.service.persistence.MBBanUtil;
import com.liferay.util.GetterUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

		long banId = CounterLocalServiceUtil.increment(Counter.class.getName());

		MBBan ban = MBBanUtil.fetchByG_B(groupId, banUserId);

		if (ban == null) {
			ban = MBBanUtil.create(banId);

			ban.setGroupId(groupId);
			ban.setCompanyId(user.getCompanyId());
			ban.setUserId(user.getUserId());
			ban.setUserName(user.getFullName());
			ban.setCreateDate(now);
			ban.setBanUserId(banUserId);
		}

		ban.setModifiedDate(now);

		MBBanUtil.update(ban);

		return ban;
	}

	public void checkBan(long groupId, String banUserId)
		throws PortalException, SystemException {

		if (hasBan(groupId, banUserId)) {
			throw new BannedUserException();
		}
	}

	public void deleteBan(String plid, String banUserId)
		throws PortalException, SystemException {

		long groupId = PortalUtil.getPortletGroupId(plid);

		try {
			MBBanUtil.removeByG_B(groupId, banUserId);
		}
		catch (NoSuchBanException nsbe) {
		}
	}

	public void deleteBans(long groupId) throws SystemException {
		MBBanUtil.removeByGroupId(groupId);
	}

	public void deleteBans(String banUserId) throws SystemException {
		MBBanUtil.removeByBanUserId(banUserId);
	}

	public void expireBans() throws SystemException {
		int expireInterval = 
			GetterUtil.get(PropsUtil.get(
				PropsUtil.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL), 0);
		
		if (expireInterval <= 0) {
			if (_log.isDebugEnabled()) {
				_log.debug("Message boards ban auto-expire disabled.");
			}
			
			return;
		}
		
		long now = System.currentTimeMillis();

		Calendar cal = Calendar.getInstance(); 
		
		List bans = MBBanUtil.findAll();
		
		for (int i = 0; i < bans.size(); i++) {
			MBBan ban = (MBBan)bans.get(i);
			
			Date banDate = ban.getCreateDate();
			
			cal.setTime(banDate);
			
			cal.add(Calendar.DATE, expireInterval);
			
			long unbanDate = cal.getTimeInMillis();
			
			if (now >= unbanDate) {
				if (_log.isDebugEnabled()) {
					_log.debug("Auto-expiring ban " + ban.getBanId() + 
						" on user " + ban.getBanUserId());
				}
				
				MBBanUtil.remove(ban);
			}
		}
	}
	
	public List getBans(long groupId, int start, int end)
		throws SystemException {

		return MBBanUtil.findByGroupId(groupId, start, end);
	}

	public int getBansCount(long groupId) throws SystemException {
		return MBBanUtil.countByGroupId(groupId);
	}

	public boolean hasBan(long groupId, String banUserId)
		throws SystemException {

		if (MBBanUtil.fetchByG_B(groupId, banUserId) == null) {
			return false;
		}
		else {
			return true;
		}
	}

	private static Log _log = 
		LogFactoryUtil.getLog(MBBanLocalServiceImpl.class);

}