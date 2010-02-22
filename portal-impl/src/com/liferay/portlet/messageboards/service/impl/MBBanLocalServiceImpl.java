/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.messageboards.BannedUserException;
import com.liferay.portlet.messageboards.NoSuchBanException;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.service.base.MBBanLocalServiceBaseImpl;
import com.liferay.portlet.messageboards.util.MBUtil;

import java.util.Date;
import java.util.List;

/**
 * <a href="MBBanLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MBBanLocalServiceImpl extends MBBanLocalServiceBaseImpl {

	public MBBan addBan(
			long userId, long banUserId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
		Date now = new Date();

		long banId = counterLocalService.increment();

		MBBan ban = mbBanPersistence.fetchByG_B(groupId, banUserId);

		if (ban == null) {
			ban = mbBanPersistence.create(banId);

			ban.setGroupId(groupId);
			ban.setCompanyId(user.getCompanyId());
			ban.setUserId(user.getUserId());
			ban.setUserName(user.getFullName());
			ban.setCreateDate(now);
			ban.setBanUserId(banUserId);
		}

		ban.setModifiedDate(now);

		mbBanPersistence.update(ban, false);

		return ban;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void checkBan(long groupId, long banUserId)
		throws PortalException, SystemException {

		if (hasBan(groupId, banUserId)) {
			throw new BannedUserException();
		}
	}

	public void deleteBan(long banUserId, ServiceContext serviceContext)
		throws SystemException {

		long groupId = serviceContext.getScopeGroupId();

		try {
			mbBanPersistence.removeByG_B(groupId, banUserId);
		}
		catch (NoSuchBanException nsbe) {
		}
	}

	public void deleteBansByBanUserId(long banUserId) throws SystemException {
		mbBanPersistence.removeByBanUserId(banUserId);
	}

	public void deleteBansByGroupId(long groupId) throws SystemException {
		mbBanPersistence.removeByGroupId(groupId);
	}

	public void expireBans() throws SystemException {
		if (PropsValues.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL <= 0) {
			return;
		}

		long now = System.currentTimeMillis();

		List<MBBan> bans = mbBanPersistence.findAll();

		for (MBBan ban : bans) {
			long unbanDate = MBUtil.getUnbanDate(
				ban, PropsValues.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL).getTime();

			if (now >= unbanDate) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Auto expiring ban " + ban.getBanId() + " on user " +
							ban.getBanUserId());
				}

				mbBanPersistence.remove(ban);
			}
		}
	}

	public List<MBBan> getBans(long groupId, int start, int end)
		throws SystemException {

		return mbBanPersistence.findByGroupId(groupId, start, end);
	}

	public int getBansCount(long groupId) throws SystemException {
		return mbBanPersistence.countByGroupId(groupId);
	}

	public boolean hasBan(long groupId, long banUserId)
		throws SystemException {

		if (mbBanPersistence.fetchByG_B(groupId, banUserId) == null) {
			return false;
		}
		else {
			return true;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		MBBanLocalServiceImpl.class);

}