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

package com.liferay.portlet.tags.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="TagsAssetUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsAssetUtil {
	public static com.liferay.portlet.tags.model.TagsAsset create(long assetId) {
		return getPersistence().create(assetId);
	}

	public static com.liferay.portlet.tags.model.TagsAsset remove(long assetId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.tags.NoSuchAssetException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(assetId));
		}

		com.liferay.portlet.tags.model.TagsAsset tagsAsset = getPersistence()
																 .remove(assetId);

		if (listener != null) {
			listener.onAfterRemove(tagsAsset);
		}

		return tagsAsset;
	}

	public static com.liferay.portlet.tags.model.TagsAsset remove(
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(tagsAsset);
		}

		tagsAsset = getPersistence().remove(tagsAsset);

		if (listener != null) {
			listener.onAfterRemove(tagsAsset);
		}

		return tagsAsset;
	}

	public static com.liferay.portlet.tags.model.TagsAsset update(
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = tagsAsset.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(tagsAsset);
			}
			else {
				listener.onBeforeUpdate(tagsAsset);
			}
		}

		tagsAsset = getPersistence().update(tagsAsset);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(tagsAsset);
			}
			else {
				listener.onAfterUpdate(tagsAsset);
			}
		}

		return tagsAsset;
	}

	public static com.liferay.portlet.tags.model.TagsAsset update(
		com.liferay.portlet.tags.model.TagsAsset tagsAsset, boolean saveOrUpdate)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = tagsAsset.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(tagsAsset);
			}
			else {
				listener.onBeforeUpdate(tagsAsset);
			}
		}

		tagsAsset = getPersistence().update(tagsAsset, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(tagsAsset);
			}
			else {
				listener.onAfterUpdate(tagsAsset);
			}
		}

		return tagsAsset;
	}

	public static com.liferay.portlet.tags.model.TagsAsset findByPrimaryKey(
		long assetId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.tags.NoSuchAssetException {
		return getPersistence().findByPrimaryKey(assetId);
	}

	public static com.liferay.portlet.tags.model.TagsAsset fetchByPrimaryKey(
		long assetId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(assetId);
	}

	public static com.liferay.portlet.tags.model.TagsAsset findByC_C(
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.tags.NoSuchAssetException {
		return getPersistence().findByC_C(className, classPK);
	}

	public static com.liferay.portlet.tags.model.TagsAsset fetchByC_C(
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_C(className, classPK);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer, begin,
			end);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List findAll(int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByC_C(java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.tags.NoSuchAssetException {
		getPersistence().removeByC_C(className, classPK);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByC_C(java.lang.String className,
		java.lang.String classPK) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C(className, classPK);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static java.util.List getTagsEntries(long pk)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.tags.NoSuchAssetException {
		return getPersistence().getTagsEntries(pk);
	}

	public static java.util.List getTagsEntries(long pk, int begin, int end)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.tags.NoSuchAssetException {
		return getPersistence().getTagsEntries(pk, begin, end);
	}

	public static java.util.List getTagsEntries(long pk, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.tags.NoSuchAssetException {
		return getPersistence().getTagsEntries(pk, begin, end, obc);
	}

	public static int getTagsEntriesSize(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getTagsEntriesSize(pk);
	}

	public static boolean containsTagsEntry(long pk, long tagsEntryPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsTagsEntry(pk, tagsEntryPK);
	}

	public static boolean containsTagsEntrys(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsTagsEntrys(pk);
	}

	public static void addTagsEntry(long pk, long tagsEntryPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.tags.NoSuchAssetException, 
			com.liferay.portlet.tags.NoSuchEntryException {
		getPersistence().addTagsEntry(pk, tagsEntryPK);
	}

	public static void addTagsEntry(long pk,
		com.liferay.portlet.tags.model.TagsEntry tagsEntry)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.tags.NoSuchAssetException, 
			com.liferay.portlet.tags.NoSuchEntryException {
		getPersistence().addTagsEntry(pk, tagsEntry);
	}

	public static void addTagsEntries(long pk, long[] tagsEntryPKs)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.tags.NoSuchAssetException, 
			com.liferay.portlet.tags.NoSuchEntryException {
		getPersistence().addTagsEntries(pk, tagsEntryPKs);
	}

	public static void addTagsEntries(long pk, java.util.List tagsEntries)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.tags.NoSuchAssetException, 
			com.liferay.portlet.tags.NoSuchEntryException {
		getPersistence().addTagsEntries(pk, tagsEntries);
	}

	public static void clearTagsEntries(long pk)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.tags.NoSuchAssetException {
		getPersistence().clearTagsEntries(pk);
	}

	public static void removeTagsEntry(long pk, long tagsEntryPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.tags.NoSuchAssetException, 
			com.liferay.portlet.tags.NoSuchEntryException {
		getPersistence().removeTagsEntry(pk, tagsEntryPK);
	}

	public static void removeTagsEntry(long pk,
		com.liferay.portlet.tags.model.TagsEntry tagsEntry)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.tags.NoSuchAssetException, 
			com.liferay.portlet.tags.NoSuchEntryException {
		getPersistence().removeTagsEntry(pk, tagsEntry);
	}

	public static void removeTagsEntries(long pk, long[] tagsEntryPKs)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.tags.NoSuchAssetException, 
			com.liferay.portlet.tags.NoSuchEntryException {
		getPersistence().removeTagsEntries(pk, tagsEntryPKs);
	}

	public static void removeTagsEntries(long pk, java.util.List tagsEntries)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.tags.NoSuchAssetException, 
			com.liferay.portlet.tags.NoSuchEntryException {
		getPersistence().removeTagsEntries(pk, tagsEntries);
	}

	public static void setTagsEntries(long pk, long[] tagsEntryPKs)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.tags.NoSuchAssetException, 
			com.liferay.portlet.tags.NoSuchEntryException {
		getPersistence().setTagsEntries(pk, tagsEntryPKs);
	}

	public static void setTagsEntries(long pk, java.util.List tagsEntries)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.tags.NoSuchAssetException, 
			com.liferay.portlet.tags.NoSuchEntryException {
		getPersistence().setTagsEntries(pk, tagsEntries);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static TagsAssetPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(TagsAssetPersistence persistence) {
		_persistence = persistence;
	}

	private static TagsAssetUtil _getUtil() {
		if (_util == null) {
			_util = (TagsAssetUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static ModelListener _getListener() {
		if (Validator.isNotNull(_LISTENER)) {
			try {
				return (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return null;
	}

	private static final String _UTIL = TagsAssetUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.tags.model.TagsAsset"));
	private static Log _log = LogFactory.getLog(TagsAssetUtil.class);
	private static TagsAssetUtil _util;
	private TagsAssetPersistence _persistence;
}