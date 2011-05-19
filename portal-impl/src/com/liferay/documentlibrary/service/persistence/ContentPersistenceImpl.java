/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.documentlibrary.service.persistence;

import com.liferay.documentlibrary.NoSuchContentException;
import com.liferay.documentlibrary.model.Content;
import com.liferay.documentlibrary.util.comparator.ContentVersionComparator;
import com.liferay.portal.kernel.dao.orm.ObjectNotFoundException;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Dummy;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

/**
 * @author Shuyang Zhou
 */
public class ContentPersistenceImpl
	extends BasePersistenceImpl<Dummy> implements ContentPersistence {

	public int countByC_R_P_V(
			long companyId, long repositoryId, String path, String version)
		throws SystemException {

		StringBundler sb = new StringBundler(5);

		sb.append("SELECT count(content) FROM Content content WHERE ");
		sb.append("content.companyId = ? AND ");
		sb.append("content.repositoryId = ? AND ");
		sb.append("content.path = ? AND ");
		sb.append("content.version = ?");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(repositoryId);
			qPos.add(path);
			qPos.add(version);

			return ((Number)q.uniqueResult()).intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Content fetchByC_R_P(long companyId, long repositoryId, String path)
		throws SystemException {

		StringBundler sb = new StringBundler(6);

		sb.append("SELECT content FROM Content content WHERE ");
		sb.append("content.companyId = ? AND ");
		sb.append("content.repositoryId = ? AND ");
		sb.append("content.path = ? ");

		appendOrderByComparator(sb, "content.", new ContentVersionComparator());

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			q.setFirstResult(0);
			q.setMaxResults(1);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(repositoryId);
			qPos.add(path);

			Content content = (Content)q.uniqueResult();

			return content;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Content fetchByC_R_P_V(
			long companyId, long repositoryId, String path, String version)
		throws SystemException {

		StringBundler sb = new StringBundler(5);

		sb.append("SELECT content FROM Content content WHERE ");
		sb.append("content.companyId = ? AND ");
		sb.append("content.repositoryId = ? AND ");
		sb.append("content.path = ? AND ");
		sb.append("content.version = ?");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(repositoryId);
			qPos.add(path);
			qPos.add(version);

			Content content = (Content)q.uniqueResult();

			return content;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Content fetchByPrimaryKey(long contentId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (Content)session.get(Content.class, contentId);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Content findByC_R_P(
			long companyId, long repositoryId, String path)
		throws NoSuchContentException, SystemException {

		Content content = fetchByC_R_P(companyId, repositoryId, path);

		if (content == null) {
			StringBundler msg = new StringBundler(8);

			msg.append("No Content exists with the key {");
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", repositoryId=");
			msg.append(repositoryId);
			msg.append(", path=");
			msg.append(path);

			msg.append("}");

			throw new NoSuchContentException(msg.toString());
		}

		return content;
	}

	public Content findByC_R_P_V(
			long companyId, long repositoryId, String path, String version)
		throws NoSuchContentException, SystemException {

		Content content = fetchByC_R_P_V(companyId, repositoryId, path,
			version);

		if (content == null) {
			StringBundler msg = new StringBundler(10);

			msg.append("No Content exists with the key {");
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", repositoryId=");
			msg.append(repositoryId);
			msg.append(", path=");
			msg.append(path);
			msg.append(", version=");
			msg.append(version);
			msg.append("}");

			throw new NoSuchContentException(msg.toString());
		}

		return content;
	}

	public Content findByPrimaryKey(long contentId)
		throws NoSuchContentException, SystemException {

		Content content = fetchByPrimaryKey(contentId);

		if (content == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No Content exists with the primary key " + contentId);
			}

			throw new NoSuchContentException(
				"No Content exists with the primary key " + contentId);
		}

		return content;
	}

	public void remove(long contentId)
		throws NoSuchContentException, SystemException {

		Session session = null;

		try {
			session = openSession();

			Content content = (Content)session.load(Content.class, contentId);

			session.delete(content);

			session.flush();
		}
		catch (ObjectNotFoundException onfe) {
			throw new NoSuchContentException(onfe);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean removeByC_R_P_V(
			long companyId, long repositoryId, String path, String version)
		throws SystemException {

		StringBundler sb = new StringBundler(5);

		sb.append("DELETE FROM Content WHERE ");
		sb.append("companyId = ? AND ");
		sb.append("repositoryId = ? AND ");
		sb.append("path_ = ? AND ");
		sb.append("version = ?");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(repositoryId);
			qPos.add(path);
			qPos.add(version);

			if (q.executeUpdate() > 0) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public void update(Content content) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.saveOrUpdate(content);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ContentPersistenceImpl.class);

}