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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="MBCategoryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBCategoryUtil {
	public static com.liferay.portlet.messageboards.model.MBCategory create(
		java.lang.String categoryId) {
		return getPersistence().create(categoryId);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory remove(
		java.lang.String categoryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(categoryId));
		}

		com.liferay.portlet.messageboards.model.MBCategory mbCategory = getPersistence()
																			.remove(categoryId);

		if (listener != null) {
			listener.onAfterRemove(mbCategory);
		}

		return mbCategory;
	}

	public static com.liferay.portlet.messageboards.model.MBCategory remove(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(mbCategory);
		}

		mbCategory = getPersistence().remove(mbCategory);

		if (listener != null) {
			listener.onAfterRemove(mbCategory);
		}

		return mbCategory;
	}

	public static com.liferay.portlet.messageboards.model.MBCategory update(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = mbCategory.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(mbCategory);
			}
			else {
				listener.onBeforeUpdate(mbCategory);
			}
		}

		mbCategory = getPersistence().update(mbCategory);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(mbCategory);
			}
			else {
				listener.onAfterUpdate(mbCategory);
			}
		}

		return mbCategory;
	}

	public static com.liferay.portlet.messageboards.model.MBCategory update(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = mbCategory.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(mbCategory);
			}
			else {
				listener.onBeforeUpdate(mbCategory);
			}
		}

		mbCategory = getPersistence().update(mbCategory, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(mbCategory);
			}
			else {
				listener.onAfterUpdate(mbCategory);
			}
		}

		return mbCategory;
	}

	public static com.liferay.portlet.messageboards.model.MBCategory findByPrimaryKey(
		java.lang.String categoryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByPrimaryKey(categoryId);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory fetchByPrimaryKey(
		java.lang.String categoryId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(categoryId);
	}

	public static java.util.List findByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List findByGroupId(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end);
	}

	public static java.util.List findByGroupId(long groupId, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory[] findByGroupId_PrevAndNext(
		java.lang.String categoryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByGroupId_PrevAndNext(categoryId, groupId,
			obc);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory findByCompanyId_First(
		java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory findByCompanyId_Last(
		java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory[] findByCompanyId_PrevAndNext(
		java.lang.String categoryId, java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByCompanyId_PrevAndNext(categoryId,
			companyId, obc);
	}

	public static java.util.List findByG_P(long groupId,
		java.lang.String parentCategoryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_P(groupId, parentCategoryId);
	}

	public static java.util.List findByG_P(long groupId,
		java.lang.String parentCategoryId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_P(groupId, parentCategoryId, begin, end);
	}

	public static java.util.List findByG_P(long groupId,
		java.lang.String parentCategoryId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_P(groupId, parentCategoryId, begin,
			end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory findByG_P_First(
		long groupId, java.lang.String parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByG_P_First(groupId, parentCategoryId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory findByG_P_Last(
		long groupId, java.lang.String parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByG_P_Last(groupId, parentCategoryId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory[] findByG_P_PrevAndNext(
		java.lang.String categoryId, long groupId,
		java.lang.String parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByG_P_PrevAndNext(categoryId, groupId,
			parentCategoryId, obc);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.util.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.util.dao.DynamicQueryInitializer queryInitializer,
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

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByG_P(long groupId,
		java.lang.String parentCategoryId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_P(groupId, parentCategoryId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByG_P(long groupId, java.lang.String parentCategoryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_P(groupId, parentCategoryId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static MBCategoryPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(MBCategoryPersistence persistence) {
		_persistence = persistence;
	}

	private static MBCategoryUtil _getUtil() {
		if (_util == null) {
			_util = (MBCategoryUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = MBCategoryUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.messageboards.model.MBCategory"));
	private static Log _log = LogFactory.getLog(MBCategoryUtil.class);
	private static MBCategoryUtil _util;
	private MBCategoryPersistence _persistence;
}