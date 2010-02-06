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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.NoSuchSubscriptionException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="MBCategoryFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class MBCategoryFinderImpl
	extends BasePersistenceImpl<MBCategory> implements MBCategoryFinder {

	public static String COUNT_BY_S_G_U =
		MBCategoryFinder.class.getName() + ".countByS_G_U";

	public static String FIND_BY_S_G_U =
		MBCategoryFinder.class.getName() + ".findByS_G_U";

	public int countByS_G_U(long groupId, long userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_S_G_U);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(PortalUtil.getClassNameId(MBCategory.class.getName()));
			qPos.add(groupId);
			qPos.add(userId);

			int count = 0;

			Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long l = itr.next();

				if (l != null) {
					count = l.intValue();
				}
			}

			try {
				Group group = GroupLocalServiceUtil.getGroup(groupId);

				SubscriptionLocalServiceUtil.getSubscription(
					group.getCompanyId(), userId, MBCategory.class.getName(),
					groupId);

				count++;
			}
			catch (NoSuchSubscriptionException nsse) {
			}

			return count;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBCategory> findByS_G_U(
			long groupId, long userId, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_S_G_U);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("MBCategory", MBCategoryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(PortalUtil.getClassNameId(MBCategory.class.getName()));
			qPos.add(groupId);
			qPos.add(userId);

			List<MBCategory> list = (List<MBCategory>)QueryUtil.list(
				q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, false);

			try {
				Group group = GroupLocalServiceUtil.getGroup(groupId);

				SubscriptionLocalServiceUtil.getSubscription(
					group.getCompanyId(), userId, MBCategory.class.getName(),
					groupId);

				int threadCount =
					MBThreadLocalServiceUtil.getCategoryThreadsCount(
						groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
						StatusConstants.APPROVED);
				int messageCount =
					MBMessageLocalServiceUtil.getCategoryMessagesCount(
						groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
						StatusConstants.APPROVED);

				MBCategory category = new MBCategoryImpl();

				category.setCompanyId(group.getCompanyId());
				category.setName(group.getName());
				category.setDescription(group.getDescription());
				category.setThreadCount(threadCount);
				category.setMessageCount(messageCount);

				list.add(category);
			}
			catch (NoSuchSubscriptionException nsse) {
			}

			return new UnmodifiableList<MBCategory>(
				ListUtil.subList(list, start, end));
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}