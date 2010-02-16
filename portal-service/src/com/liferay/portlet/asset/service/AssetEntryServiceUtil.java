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

package com.liferay.portlet.asset.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="AssetEntryServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link AssetEntryService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetEntryService
 * @generated
 */
public class AssetEntryServiceUtil {
	public static void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntry(entryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getCompanyEntries(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyEntries(companyId, start, end);
	}

	public static int getCompanyEntriesCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyEntriesCount(companyId);
	}

	public static java.lang.String getCompanyEntriesRSS(long companyId,
		int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String tagURL)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getCompanyEntriesRSS(companyId, max, type, version,
			displayStyle, feedURL, tagURL);
	}

	public static com.liferay.portlet.asset.model.AssetEntryDisplay[] getCompanyEntryDisplays(
		long companyId, int start, int end, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getCompanyEntryDisplays(companyId, start, end, languageId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getEntries(
		com.liferay.portlet.asset.service.persistence.AssetEntryQuery entryQuery)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntries(entryQuery);
	}

	public static int getEntriesCount(
		com.liferay.portlet.asset.service.persistence.AssetEntryQuery entryQuery)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntriesCount(entryQuery);
	}

	public static java.lang.String getEntriesRSS(
		com.liferay.portlet.asset.service.persistence.AssetEntryQuery entryQuery,
		java.lang.String type, double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String tagURL)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getEntriesRSS(entryQuery, type, version, displayStyle,
			feedURL, tagURL);
	}

	public static com.liferay.portlet.asset.model.AssetEntry getEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntry(entryId);
	}

	public static com.liferay.portlet.asset.model.AssetEntry incrementViewCounter(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().incrementViewCounter(className, classPK);
	}

	public static com.liferay.portlet.asset.model.AssetEntryDisplay[] searchEntryDisplays(
		long companyId, java.lang.String portletId, java.lang.String keywords,
		java.lang.String languageId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchEntryDisplays(companyId, portletId, keywords,
			languageId, start, end);
	}

	public static int searchEntryDisplaysCount(long companyId,
		java.lang.String portletId, java.lang.String keywords,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchEntryDisplaysCount(companyId, portletId, keywords,
			languageId);
	}

	public static com.liferay.portlet.asset.model.AssetEntry updateEntry(
		long groupId, java.lang.String className, long classPK,
		long[] categoryIds, java.lang.String[] tagNames, boolean visible,
		java.util.Date startDate, java.util.Date endDate,
		java.util.Date publishDate, java.util.Date expirationDate,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String summary,
		java.lang.String url, int height, int width,
		java.lang.Integer priority, boolean sync)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateEntry(groupId, className, classPK, categoryIds,
			tagNames, visible, startDate, endDate, publishDate, expirationDate,
			mimeType, title, description, summary, url, height, width,
			priority, sync);
	}

	public static AssetEntryService getService() {
		if (_service == null) {
			_service = (AssetEntryService)PortalBeanLocatorUtil.locate(AssetEntryService.class.getName());
		}

		return _service;
	}

	public void setService(AssetEntryService service) {
		_service = service;
	}

	private static AssetEntryService _service;
}