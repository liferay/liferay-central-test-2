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

package com.liferay.portlet.announcements.service.persistence;

/**
 * <a href="AnnouncementFinder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface AnnouncementFinder {
	public int countByC_C_F(long userId, long classNameId, long[] classPKs,
		int displayMonth, int displayDay, int displayYear, int expirationMonth,
		int expirationDay, int expirationYear, int flag, boolean alert)
		throws com.liferay.portal.SystemException;

	public int countByCNM_F(long userId,
		java.util.LinkedHashMap<Long, Long[]> announcementsParams,
		int displayMonth, int displayDay, int displayYear, int expirationMonth,
		int expirationDay, int expirationYear, int flag, boolean alert)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.Announcement> findByC_C_F(
		long userId, long classNameId, long[] classPKs, int displayMonth,
		int displayDay, int displayYear, int expirationMonth,
		int expirationDay, int expirationYear, int flag, boolean alert,
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.Announcement> findByCNM_F(
		long userId, java.util.LinkedHashMap<Long, Long[]> announcementsParams,
		int displayMonth, int displayDay, int displayYear, int expirationMonth,
		int expirationDay, int expirationYear, int flag, boolean alert,
		int begin, int end) throws com.liferay.portal.SystemException;
}