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

package com.liferay.portlet.announcements.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.announcements.model.AnnouncementsFlag;
import com.liferay.portlet.announcements.service.base.AnnouncementsFlagLocalServiceBaseImpl;

import java.util.Date;

/**
 * <a href="AnnouncementsFlagLocalServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Thiago Moreira
 * @author Raymond Augé
 */
public class AnnouncementsFlagLocalServiceImpl
	extends AnnouncementsFlagLocalServiceBaseImpl {

	public AnnouncementsFlag addFlag(long userId, long entryId, int value)
		throws SystemException {

		long flagId = counterLocalService.increment();

		AnnouncementsFlag flag = announcementsFlagPersistence.create(flagId);

		flag.setUserId(userId);
		flag.setCreateDate(new Date());
		flag.setEntryId(entryId);
		flag.setValue(value);

		announcementsFlagPersistence.update(flag, false);

		return flag;
	}

	public void deleteFlag(long flagId)
		throws PortalException, SystemException {

		announcementsFlagPersistence.remove(flagId);
	}

	public void deleteFlags(long entryId) throws SystemException {
		announcementsFlagPersistence.removeByEntryId(entryId);
	}

	public AnnouncementsFlag getFlag(long userId, long entryId, int value)
		throws PortalException, SystemException {

		return announcementsFlagPersistence.findByU_E_V(
			userId, entryId, value);
	}

}