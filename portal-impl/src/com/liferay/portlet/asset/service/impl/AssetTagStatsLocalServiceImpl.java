/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetTagStats;
import com.liferay.portlet.asset.service.base.AssetTagStatsLocalServiceBaseImpl;

/**
 * <a href="AssetTagStatsLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Jorge Ferrer
 *
 */
public class AssetTagStatsLocalServiceImpl
	extends AssetTagStatsLocalServiceBaseImpl {

	public AssetTagStats addTagStats(long classNameId, long tagId)
		throws SystemException {

		long tagStatsId = counterLocalService.increment();

		AssetTagStats tagStats = assetTagStatsPersistence.create(tagStatsId);

		tagStats.setTagId(tagId);
		tagStats.setClassNameId(classNameId);

		try {
			assetTagStatsPersistence.update(tagStats, false);
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Add failed, fetch {classNameId=" + classNameId +
						", tagId=" + tagId + "}");
			}

			tagStats = assetTagStatsPersistence.fetchByC_T(
				classNameId, tagId, false);

			if (tagStats == null) {
				throw se;
			}
		}

		return tagStats;
	}

	public void deleteTagStatsByTagId(long tagId)
		throws SystemException {

		assetTagStatsPersistence.removeByTagId(tagId);
	}

	public void deleteTagStatsByClassNameId(long classNameId)
		throws SystemException {

		assetTagStatsPersistence.removeByClassNameId(classNameId);
	}

	public AssetTagStats getTagStats(long classNameId, long tagId)
		throws SystemException {

		AssetTagStats tagStats = assetTagStatsPersistence.fetchByC_T(
			classNameId, tagId);

		if (tagStats == null) {
			tagStats = assetTagStatsLocalService.addTagStats(
				classNameId, tagId);
		}

		return tagStats;
	}

	public AssetTagStats updateTagStats(long classNameId, long tagId)
		throws PortalException, SystemException {

		AssetTag tag = assetTagPersistence.findByPrimaryKey(tagId);

		int assetCount = assetTagFinder.countByG_C_N(
			tag.getGroupId(), classNameId, tag.getName());

		AssetTagStats tagStats = getTagStats(classNameId, tagId);

		tagStats.setAssetCount(assetCount);

		assetTagStatsPersistence.update(tagStats, false);

		return tagStats;
	}

	private static Log _log =
		LogFactoryUtil.getLog(AssetTagStatsLocalServiceImpl.class);

}