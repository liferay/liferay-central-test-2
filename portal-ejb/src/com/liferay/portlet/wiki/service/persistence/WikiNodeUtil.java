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

package com.liferay.portlet.wiki.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="WikiNodeUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class WikiNodeUtil {
	public static com.liferay.portlet.wiki.model.WikiNode create(
		java.lang.String nodeId) {
		return getPersistence().create(nodeId);
	}

	public static com.liferay.portlet.wiki.model.WikiNode remove(
		java.lang.String nodeId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.wiki.NoSuchNodeException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(nodeId));
		}

		com.liferay.portlet.wiki.model.WikiNode wikiNode = getPersistence()
															   .remove(nodeId);

		if (listener != null) {
			listener.onAfterRemove(wikiNode);
		}

		return wikiNode;
	}

	public static com.liferay.portlet.wiki.model.WikiNode remove(
		com.liferay.portlet.wiki.model.WikiNode wikiNode)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(wikiNode);
		}

		wikiNode = getPersistence().remove(wikiNode);

		if (listener != null) {
			listener.onAfterRemove(wikiNode);
		}

		return wikiNode;
	}

	public static com.liferay.portlet.wiki.model.WikiNode update(
		com.liferay.portlet.wiki.model.WikiNode wikiNode)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = wikiNode.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(wikiNode);
			}
			else {
				listener.onBeforeUpdate(wikiNode);
			}
		}

		wikiNode = getPersistence().update(wikiNode);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(wikiNode);
			}
			else {
				listener.onAfterUpdate(wikiNode);
			}
		}

		return wikiNode;
	}

	public static com.liferay.portlet.wiki.model.WikiNode update(
		com.liferay.portlet.wiki.model.WikiNode wikiNode, boolean saveOrUpdate)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = wikiNode.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(wikiNode);
			}
			else {
				listener.onBeforeUpdate(wikiNode);
			}
		}

		wikiNode = getPersistence().update(wikiNode, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(wikiNode);
			}
			else {
				listener.onAfterUpdate(wikiNode);
			}
		}

		return wikiNode;
	}

	public static com.liferay.portlet.wiki.model.WikiNode findByPrimaryKey(
		java.lang.String nodeId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.wiki.NoSuchNodeException {
		return getPersistence().findByPrimaryKey(nodeId);
	}

	public static com.liferay.portlet.wiki.model.WikiNode fetchByPrimaryKey(
		java.lang.String nodeId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(nodeId);
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

	public static com.liferay.portlet.wiki.model.WikiNode findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.wiki.NoSuchNodeException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiNode findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.wiki.NoSuchNodeException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiNode[] findByGroupId_PrevAndNext(
		java.lang.String nodeId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.wiki.NoSuchNodeException {
		return getPersistence().findByGroupId_PrevAndNext(nodeId, groupId, obc);
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

	public static com.liferay.portlet.wiki.model.WikiNode findByCompanyId_First(
		java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.wiki.NoSuchNodeException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiNode findByCompanyId_Last(
		java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.wiki.NoSuchNodeException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiNode[] findByCompanyId_PrevAndNext(
		java.lang.String nodeId, java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.wiki.NoSuchNodeException {
		return getPersistence().findByCompanyId_PrevAndNext(nodeId, companyId,
			obc);
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

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
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

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static WikiNodePersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(WikiNodePersistence persistence) {
		_persistence = persistence;
	}

	private static WikiNodeUtil _getUtil() {
		if (_util == null) {
			_util = (WikiNodeUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = WikiNodeUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.wiki.model.WikiNode"));
	private static Log _log = LogFactory.getLog(WikiNodeUtil.class);
	private static WikiNodeUtil _util;
	private WikiNodePersistence _persistence;
}