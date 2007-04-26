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

import com.liferay.counter.model.Counter;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portlet.tags.model.TagsAsset;
import com.liferay.portlet.tags.model.TagsEntry;
import com.liferay.portlet.tags.service.base.TagsAssetLocalServiceBaseImpl;
import com.liferay.portlet.tags.service.persistence.TagsAssetFinder;
import com.liferay.portlet.tags.service.persistence.TagsAssetUtil;
import com.liferay.portlet.tags.service.persistence.TagsEntryUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="TagsAssetLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsAssetLocalServiceImpl extends TagsAssetLocalServiceBaseImpl {

	public void deleteAsset(long assetId)
		throws PortalException, SystemException {

		TagsAsset asset = TagsAssetUtil.findByPrimaryKey(assetId);

		deleteAsset(asset);
	}

	public void deleteAsset(String className, String classPK)
		throws PortalException, SystemException {

		TagsAsset asset = TagsAssetUtil.fetchByC_C(className, classPK);

		if (asset != null) {
			deleteAsset(asset);
		}
	}

	public void deleteAsset(TagsAsset asset)
		throws PortalException, SystemException {

		TagsAssetUtil.remove(asset.getAssetId());
	}

	public TagsAsset getAsset(long assetId)
		throws PortalException, SystemException {

		return TagsAssetUtil.findByPrimaryKey(assetId);
	}

	public TagsAsset getAsset(String className, String classPK)
		throws PortalException, SystemException {

		return TagsAssetUtil.findByC_C(className, classPK);
	}

	public List getAssets(
			long[] entryIds, long[] notEntryIds, boolean andOperator, int begin,
			int end)
		throws SystemException {

		if (andOperator) {
			return TagsAssetFinder.findByAndEntryIds(
				entryIds, notEntryIds, begin, end);
		}
		else {
			return TagsAssetFinder.findByOrEntryIds(
				entryIds, notEntryIds, begin, end);
		}
	}

	public int getAssetsCount(
			long[] entryIds, long[] notEntryIds, boolean andOperator)
		throws SystemException {

		if (andOperator) {
			return TagsAssetFinder.countByAndEntryIds(entryIds, notEntryIds);
		}
		else {
			return TagsAssetFinder.countByOrEntryIds(entryIds, notEntryIds);
		}
	}

	public TagsAsset updateAsset(
			long userId, String className, String classPK, String[] entryNames)
		throws PortalException, SystemException {

		// Asset

		User user = UserUtil.findByPrimaryKey(userId);
		Date now = new Date();

		TagsAsset asset = TagsAssetUtil.fetchByC_C(className, classPK);

		if (asset == null) {
			long assetId = CounterLocalServiceUtil.increment(
				Counter.class.getName());

			asset = TagsAssetUtil.create(assetId);

			asset.setCompanyId(user.getCompanyId());
			asset.setUserId(user.getUserId());
			asset.setUserName(user.getFullName());
			asset.setCreateDate(now);
			asset.setClassName(className);
			asset.setClassPK(classPK);
		}

		asset.setModifiedDate(now);

		TagsAssetUtil.update(asset);

		// Entries

		List entries = new ArrayList(entryNames.length);

		for (int i = 0; i < entryNames.length; i++) {
			TagsEntry entry = TagsEntryUtil.fetchByC_N(
				user.getCompanyId(), entryNames[i]);

			if (entry != null) {
				entries.add(entry);
			}
		}

		TagsAssetUtil.setTagsEntries(asset.getAssetId(), entries);

		return asset;
	}

}