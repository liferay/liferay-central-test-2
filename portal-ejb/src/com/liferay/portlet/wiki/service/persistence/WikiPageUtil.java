/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="WikiPageUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WikiPageUtil {
	public static final String CLASS_NAME = WikiPageUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.wiki.model.WikiPage"));

	public static com.liferay.portlet.wiki.model.WikiPage create(
		com.liferay.portlet.wiki.service.persistence.WikiPagePK wikiPagePK) {
		return getPersistence().create(wikiPagePK);
	}

	public static com.liferay.portlet.wiki.model.WikiPage remove(
		com.liferay.portlet.wiki.service.persistence.WikiPagePK wikiPagePK)
		throws com.liferay.portlet.wiki.NoSuchPageException, 
			com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(wikiPagePK));
		}

		com.liferay.portlet.wiki.model.WikiPage wikiPage = getPersistence()
															   .remove(wikiPagePK);

		if (listener != null) {
			listener.onAfterRemove(wikiPage);
		}

		return wikiPage;
	}

	public static com.liferay.portlet.wiki.model.WikiPage remove(
		com.liferay.portlet.wiki.model.WikiPage wikiPage)
		throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		if (listener != null) {
			listener.onBeforeRemove(wikiPage);
		}

		wikiPage = getPersistence().remove(wikiPage);

		if (listener != null) {
			listener.onAfterRemove(wikiPage);
		}

		return wikiPage;
	}

	public static com.liferay.portlet.wiki.model.WikiPage update(
		com.liferay.portlet.wiki.model.WikiPage wikiPage)
		throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		boolean isNew = wikiPage.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(wikiPage);
			}
			else {
				listener.onBeforeUpdate(wikiPage);
			}
		}

		wikiPage = getPersistence().update(wikiPage);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(wikiPage);
			}
			else {
				listener.onAfterUpdate(wikiPage);
			}
		}

		return wikiPage;
	}

	public static com.liferay.portlet.wiki.model.WikiPage update(
		com.liferay.portlet.wiki.model.WikiPage wikiPage, boolean saveOrUpdate)
		throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		boolean isNew = wikiPage.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(wikiPage);
			}
			else {
				listener.onBeforeUpdate(wikiPage);
			}
		}

		wikiPage = getPersistence().update(wikiPage, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(wikiPage);
			}
			else {
				listener.onAfterUpdate(wikiPage);
			}
		}

		return wikiPage;
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByPrimaryKey(
		com.liferay.portlet.wiki.service.persistence.WikiPagePK wikiPagePK)
		throws com.liferay.portlet.wiki.NoSuchPageException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(wikiPagePK);
	}

	public static com.liferay.portlet.wiki.model.WikiPage fetchByPrimaryKey(
		com.liferay.portlet.wiki.service.persistence.WikiPagePK wikiPagePK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(wikiPagePK);
	}

	public static java.util.List findByNodeId(java.lang.String nodeId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByNodeId(nodeId);
	}

	public static java.util.List findByNodeId(java.lang.String nodeId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByNodeId(nodeId, begin, end);
	}

	public static java.util.List findByNodeId(java.lang.String nodeId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByNodeId(nodeId, begin, end, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByNodeId_First(
		java.lang.String nodeId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.wiki.NoSuchPageException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByNodeId_First(nodeId, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByNodeId_Last(
		java.lang.String nodeId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.wiki.NoSuchPageException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByNodeId_Last(nodeId, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage[] findByNodeId_PrevAndNext(
		com.liferay.portlet.wiki.service.persistence.WikiPagePK wikiPagePK,
		java.lang.String nodeId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.wiki.NoSuchPageException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByNodeId_PrevAndNext(wikiPagePK, nodeId, obc);
	}

	public static java.util.List findByN_T(java.lang.String nodeId,
		java.lang.String title) throws com.liferay.portal.SystemException {
		return getPersistence().findByN_T(nodeId, title);
	}

	public static java.util.List findByN_T(java.lang.String nodeId,
		java.lang.String title, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_T(nodeId, title, begin, end);
	}

	public static java.util.List findByN_T(java.lang.String nodeId,
		java.lang.String title, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_T(nodeId, title, begin, end, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_T_First(
		java.lang.String nodeId, java.lang.String title,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.wiki.NoSuchPageException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByN_T_First(nodeId, title, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_T_Last(
		java.lang.String nodeId, java.lang.String title,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.wiki.NoSuchPageException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByN_T_Last(nodeId, title, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_T_PrevAndNext(
		com.liferay.portlet.wiki.service.persistence.WikiPagePK wikiPagePK,
		java.lang.String nodeId, java.lang.String title,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.wiki.NoSuchPageException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByN_T_PrevAndNext(wikiPagePK, nodeId,
			title, obc);
	}

	public static java.util.List findByN_H(java.lang.String nodeId, boolean head)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_H(nodeId, head);
	}

	public static java.util.List findByN_H(java.lang.String nodeId,
		boolean head, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_H(nodeId, head, begin, end);
	}

	public static java.util.List findByN_H(java.lang.String nodeId,
		boolean head, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_H(nodeId, head, begin, end, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_H_First(
		java.lang.String nodeId, boolean head,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.wiki.NoSuchPageException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByN_H_First(nodeId, head, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_H_Last(
		java.lang.String nodeId, boolean head,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.wiki.NoSuchPageException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByN_H_Last(nodeId, head, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_H_PrevAndNext(
		com.liferay.portlet.wiki.service.persistence.WikiPagePK wikiPagePK,
		java.lang.String nodeId, boolean head,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.wiki.NoSuchPageException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByN_H_PrevAndNext(wikiPagePK, nodeId, head,
			obc);
	}

	public static java.util.List findByN_T_H(java.lang.String nodeId,
		java.lang.String title, boolean head)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_T_H(nodeId, title, head);
	}

	public static java.util.List findByN_T_H(java.lang.String nodeId,
		java.lang.String title, boolean head, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_T_H(nodeId, title, head, begin, end);
	}

	public static java.util.List findByN_T_H(java.lang.String nodeId,
		java.lang.String title, boolean head, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_T_H(nodeId, title, head, begin, end, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_T_H_First(
		java.lang.String nodeId, java.lang.String title, boolean head,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.wiki.NoSuchPageException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByN_T_H_First(nodeId, title, head, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_T_H_Last(
		java.lang.String nodeId, java.lang.String title, boolean head,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.wiki.NoSuchPageException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByN_T_H_Last(nodeId, title, head, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_T_H_PrevAndNext(
		com.liferay.portlet.wiki.service.persistence.WikiPagePK wikiPagePK,
		java.lang.String nodeId, java.lang.String title, boolean head,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.wiki.NoSuchPageException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByN_T_H_PrevAndNext(wikiPagePK, nodeId,
			title, head, obc);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static void removeByNodeId(java.lang.String nodeId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByNodeId(nodeId);
	}

	public static void removeByN_T(java.lang.String nodeId,
		java.lang.String title) throws com.liferay.portal.SystemException {
		getPersistence().removeByN_T(nodeId, title);
	}

	public static void removeByN_H(java.lang.String nodeId, boolean head)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByN_H(nodeId, head);
	}

	public static void removeByN_T_H(java.lang.String nodeId,
		java.lang.String title, boolean head)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByN_T_H(nodeId, title, head);
	}

	public static int countByNodeId(java.lang.String nodeId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByNodeId(nodeId);
	}

	public static int countByN_T(java.lang.String nodeId, java.lang.String title)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByN_T(nodeId, title);
	}

	public static int countByN_H(java.lang.String nodeId, boolean head)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByN_H(nodeId, head);
	}

	public static int countByN_T_H(java.lang.String nodeId,
		java.lang.String title, boolean head)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByN_T_H(nodeId, title, head);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static WikiPagePersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		WikiPageUtil util = (WikiPageUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(WikiPagePersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(WikiPageUtil.class);
	private WikiPagePersistence _persistence;
}