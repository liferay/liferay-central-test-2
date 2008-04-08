/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.hibernate.CustomSQLUtil;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portlet.social.NoSuchRelationException;
import com.liferay.portlet.social.model.SocialRelation;
import com.liferay.portlet.social.model.SocialRelationConstants;
import com.liferay.portlet.social.model.impl.SocialRelationImpl;
import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * <a href="SocialRelationFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class SocialRelationFinderImpl implements SocialRelationFinder {

	public static String COUNT_BY_U_T =
		SocialRelationFinder.class.getName() + ".countByU_T";

	public static String FIND_BY_U_T =
		SocialRelationFinder.class.getName() + ".findByU_T";

	public static String FIND_BY_U_U_T_BI =
		SocialRelationFinder.class.getName() + ".findByU_U_T_BI";

	public static String FIND_BY_U_U_T_UNI =
		SocialRelationFinder.class.getName() + ".findByU_U_T_UNI";

	public int countByU_T(long userId, int type) throws SystemException {
		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_U_T);

			if (type == SocialRelationConstants.TYPE_UNI_CHILD ||
					type == SocialRelationConstants.TYPE_UNI_PARENT) {
				sql = StringUtil.replace(
					sql,"(SocialRelation.userId2 = ?) OR", StringPool.BLANK);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			if (type != SocialRelationConstants.TYPE_UNI_CHILD &&
					type != SocialRelationConstants.TYPE_UNI_PARENT) {
				qPos.add(userId);
			}

			qPos.add(type);

			Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public List<SocialRelation> findByU_T(
			long userId, int type, int begin, int end)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_U_T);

			if (type == SocialRelationConstants.TYPE_UNI_CHILD ||
					type == SocialRelationConstants.TYPE_UNI_PARENT) {
				sql = StringUtil.replace(
					sql,"(SocialRelation.userId2 = ?) OR", StringPool.BLANK);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("SocialRelation", SocialRelationImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			if (type != SocialRelationConstants.TYPE_UNI_CHILD &&
					type != SocialRelationConstants.TYPE_UNI_PARENT) {
				qPos.add(userId);
			}

			qPos.add(type);

			return (List<SocialRelation>)QueryUtil.list(
				q, HibernateUtil.getDialect(), begin, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public SocialRelation findByU_U_T(long userId1, long userId2, int type)
		throws NoSuchRelationException, SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_U_U_T_BI);

			if (type == SocialRelationConstants.TYPE_UNI_CHILD ||
					type == SocialRelationConstants.TYPE_UNI_PARENT) {
				sql = CustomSQLUtil.get(FIND_BY_U_U_T_UNI);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("SocialRelation", SocialRelationImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId1);
			qPos.add(userId2);
			qPos.add(type);


			if (type != SocialRelationConstants.TYPE_UNI_CHILD &&
					type != SocialRelationConstants.TYPE_UNI_PARENT) {
				qPos.add(userId2);
				qPos.add(userId1);
				qPos.add(type);
			}

			Iterator<SocialRelation> itr = q.list().iterator();

			if (itr.hasNext()) {
				SocialRelation relation = itr.next();

				return relation;
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}

		StringMaker sm = new StringMaker();

		sm.append("No SocialRelation exists with the key {userId1=");
		sm.append(userId1);
		sm.append(", userId2=");
		sm.append(userId2);
		sm.append(", type=");
		sm.append(type);
		sm.append("}");

		throw new NoSuchRelationException(sm.toString());
	}

}