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

package com.liferay.portlet.tags.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portlet.tags.model.TagsAsset;
import com.liferay.portlet.tags.model.TagsAssetDisplay;
import com.liferay.portlet.tags.model.TagsAssetType;
import com.liferay.portlet.tags.service.TagsAssetLocalServiceUtil;
import com.liferay.portlet.tags.service.base.TagsAssetServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * <a href="TagsAssetServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsAssetServiceImpl extends TagsAssetServiceBaseImpl {

	public void deleteAsset(long assetId)
		throws PortalException, SystemException {

		TagsAssetLocalServiceUtil.deleteAsset(assetId);
	}

	public TagsAsset getAsset(long assetId)
		throws PortalException, SystemException {

		return TagsAssetLocalServiceUtil.getAsset(assetId);
	}

	public TagsAssetType[] getAssetTypes(String languageId) {
		return TagsAssetLocalServiceUtil.getAssetTypes(languageId);
	}

	public TagsAssetDisplay[] getCompanyAssetDisplays(
			long companyId, int begin, int end, String languageId)
		throws PortalException, SystemException {

		return TagsAssetLocalServiceUtil.getCompanyAssetDisplays(
			companyId, begin, end, languageId);
	}

	public List getCompanyAssets(long companyId, int begin, int end)
		throws SystemException {

		return TagsAssetLocalServiceUtil.getCompanyAssets(
			companyId, begin, end);
	}

	public int getCompanyAssetsCount(long companyId) throws SystemException {
		return TagsAssetLocalServiceUtil.getCompanyAssetsCount(companyId);
	}

	public TagsAsset incrementViewCounter(String className, long classPK)
		throws PortalException, SystemException {

		return TagsAssetLocalServiceUtil.incrementViewCounter(
			className, classPK);
	}

	public TagsAssetDisplay[] searchAssetDisplays(
			long companyId, String portletId, String keywords,
			String languageId, int begin, int end)
		throws PortalException, SystemException {

		return TagsAssetLocalServiceUtil.searchAssetDisplays(
			companyId, portletId, keywords, languageId, begin, end);
	}

	public int searchAssetDisplaysCount(
			long companyId, String portletId, String keywords,
			String languageId)
		throws SystemException {

		return TagsAssetLocalServiceUtil.searchAssetDisplaysCount(
			companyId, portletId, keywords, languageId);
	}

	public TagsAsset updateAsset(
			String className, long classPK, String[] entryNames, Date startDate,
			Date endDate, Date publishDate, Date expirationDate,
			String mimeType, String title, String description, String summary,
			String url, int height, int width)
		throws PortalException, SystemException {

		return TagsAssetLocalServiceUtil.updateAsset(
			getUserId(), className, classPK, entryNames, startDate, endDate,
			publishDate, expirationDate, mimeType, title, description, summary,
			url, height, width);
	}

}