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

package com.liferay.portlet.ratings.service;


/**
 * <a href="RatingsEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link RatingsEntryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RatingsEntryLocalService
 * @generated
 */
public class RatingsEntryLocalServiceWrapper implements RatingsEntryLocalService {
	public RatingsEntryLocalServiceWrapper(
		RatingsEntryLocalService ratingsEntryLocalService) {
		_ratingsEntryLocalService = ratingsEntryLocalService;
	}

	public com.liferay.portlet.ratings.model.RatingsEntry addRatingsEntry(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.addRatingsEntry(ratingsEntry);
	}

	public com.liferay.portlet.ratings.model.RatingsEntry createRatingsEntry(
		long entryId) {
		return _ratingsEntryLocalService.createRatingsEntry(entryId);
	}

	public void deleteRatingsEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ratingsEntryLocalService.deleteRatingsEntry(entryId);
	}

	public void deleteRatingsEntry(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		_ratingsEntryLocalService.deleteRatingsEntry(ratingsEntry);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.ratings.model.RatingsEntry getRatingsEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.getRatingsEntry(entryId);
	}

	public java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> getRatingsEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.getRatingsEntries(start, end);
	}

	public int getRatingsEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.getRatingsEntriesCount();
	}

	public com.liferay.portlet.ratings.model.RatingsEntry updateRatingsEntry(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.updateRatingsEntry(ratingsEntry);
	}

	public com.liferay.portlet.ratings.model.RatingsEntry updateRatingsEntry(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.updateRatingsEntry(ratingsEntry, merge);
	}

	public void deleteEntry(long userId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ratingsEntryLocalService.deleteEntry(userId, className, classPK);
	}

	public java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> getEntries(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.getEntries(className, classPK);
	}

	public com.liferay.portlet.ratings.model.RatingsEntry getEntry(
		long userId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.getEntry(userId, className, classPK);
	}

	public com.liferay.portlet.ratings.model.RatingsEntry updateEntry(
		long userId, java.lang.String className, long classPK, double score)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.updateEntry(userId, className,
			classPK, score);
	}

	public RatingsEntryLocalService getWrappedRatingsEntryLocalService() {
		return _ratingsEntryLocalService;
	}

	private RatingsEntryLocalService _ratingsEntryLocalService;
}