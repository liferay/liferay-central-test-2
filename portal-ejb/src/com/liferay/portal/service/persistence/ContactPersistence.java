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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchContactException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Contact;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="ContactPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ContactPersistence extends BasePersistence {
	public Contact create(String contactId) {
		Contact contact = new Contact();
		contact.setNew(true);
		contact.setPrimaryKey(contactId);

		return contact;
	}

	public Contact remove(String contactId)
		throws NoSuchContactException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Contact contact = (Contact)session.get(Contact.class, contactId);

			if (contact == null) {
				_log.warn("No Contact exists with the primary key " +
					contactId.toString());
				throw new NoSuchContactException(
					"No Contact exists with the primary key " +
					contactId.toString());
			}

			session.delete(contact);
			session.flush();

			return contact;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Contact update(
		com.liferay.portal.model.Contact contact) throws SystemException {
		Session session = null;

		try {
			if (contact.isNew() || contact.isModified()) {
				session = openSession();

				if (contact.isNew()) {
					Contact contactModel = new Contact();
					contactModel.setContactId(contact.getContactId());
					contactModel.setCompanyId(contact.getCompanyId());
					contactModel.setUserId(contact.getUserId());
					contactModel.setUserName(contact.getUserName());
					contactModel.setCreateDate(contact.getCreateDate());
					contactModel.setModifiedDate(contact.getModifiedDate());
					contactModel.setAccountId(contact.getAccountId());
					contactModel.setParentContactId(contact.getParentContactId());
					contactModel.setFirstName(contact.getFirstName());
					contactModel.setMiddleName(contact.getMiddleName());
					contactModel.setLastName(contact.getLastName());
					contactModel.setNickName(contact.getNickName());
					contactModel.setPrefixId(contact.getPrefixId());
					contactModel.setSuffixId(contact.getSuffixId());
					contactModel.setMale(contact.getMale());
					contactModel.setBirthday(contact.getBirthday());
					contactModel.setSmsSn(contact.getSmsSn());
					contactModel.setAimSn(contact.getAimSn());
					contactModel.setIcqSn(contact.getIcqSn());
					contactModel.setJabberSn(contact.getJabberSn());
					contactModel.setMsnSn(contact.getMsnSn());
					contactModel.setSkypeSn(contact.getSkypeSn());
					contactModel.setYmSn(contact.getYmSn());
					contactModel.setEmployeeStatusId(contact.getEmployeeStatusId());
					contactModel.setEmployeeNumber(contact.getEmployeeNumber());
					contactModel.setJobTitle(contact.getJobTitle());
					contactModel.setJobClass(contact.getJobClass());
					contactModel.setHoursOfOperation(contact.getHoursOfOperation());
					session.save(contactModel);
					session.flush();
				}
				else {
					Contact contactModel = (Contact)session.get(Contact.class,
							contact.getPrimaryKey());

					if (contactModel != null) {
						contactModel.setCompanyId(contact.getCompanyId());
						contactModel.setUserId(contact.getUserId());
						contactModel.setUserName(contact.getUserName());
						contactModel.setCreateDate(contact.getCreateDate());
						contactModel.setModifiedDate(contact.getModifiedDate());
						contactModel.setAccountId(contact.getAccountId());
						contactModel.setParentContactId(contact.getParentContactId());
						contactModel.setFirstName(contact.getFirstName());
						contactModel.setMiddleName(contact.getMiddleName());
						contactModel.setLastName(contact.getLastName());
						contactModel.setNickName(contact.getNickName());
						contactModel.setPrefixId(contact.getPrefixId());
						contactModel.setSuffixId(contact.getSuffixId());
						contactModel.setMale(contact.getMale());
						contactModel.setBirthday(contact.getBirthday());
						contactModel.setSmsSn(contact.getSmsSn());
						contactModel.setAimSn(contact.getAimSn());
						contactModel.setIcqSn(contact.getIcqSn());
						contactModel.setJabberSn(contact.getJabberSn());
						contactModel.setMsnSn(contact.getMsnSn());
						contactModel.setSkypeSn(contact.getSkypeSn());
						contactModel.setYmSn(contact.getYmSn());
						contactModel.setEmployeeStatusId(contact.getEmployeeStatusId());
						contactModel.setEmployeeNumber(contact.getEmployeeNumber());
						contactModel.setJobTitle(contact.getJobTitle());
						contactModel.setJobClass(contact.getJobClass());
						contactModel.setHoursOfOperation(contact.getHoursOfOperation());
						session.flush();
					}
					else {
						contactModel = new Contact();
						contactModel.setContactId(contact.getContactId());
						contactModel.setCompanyId(contact.getCompanyId());
						contactModel.setUserId(contact.getUserId());
						contactModel.setUserName(contact.getUserName());
						contactModel.setCreateDate(contact.getCreateDate());
						contactModel.setModifiedDate(contact.getModifiedDate());
						contactModel.setAccountId(contact.getAccountId());
						contactModel.setParentContactId(contact.getParentContactId());
						contactModel.setFirstName(contact.getFirstName());
						contactModel.setMiddleName(contact.getMiddleName());
						contactModel.setLastName(contact.getLastName());
						contactModel.setNickName(contact.getNickName());
						contactModel.setPrefixId(contact.getPrefixId());
						contactModel.setSuffixId(contact.getSuffixId());
						contactModel.setMale(contact.getMale());
						contactModel.setBirthday(contact.getBirthday());
						contactModel.setSmsSn(contact.getSmsSn());
						contactModel.setAimSn(contact.getAimSn());
						contactModel.setIcqSn(contact.getIcqSn());
						contactModel.setJabberSn(contact.getJabberSn());
						contactModel.setMsnSn(contact.getMsnSn());
						contactModel.setSkypeSn(contact.getSkypeSn());
						contactModel.setYmSn(contact.getYmSn());
						contactModel.setEmployeeStatusId(contact.getEmployeeStatusId());
						contactModel.setEmployeeNumber(contact.getEmployeeNumber());
						contactModel.setJobTitle(contact.getJobTitle());
						contactModel.setJobClass(contact.getJobClass());
						contactModel.setHoursOfOperation(contact.getHoursOfOperation());
						session.save(contactModel);
						session.flush();
					}
				}

				contact.setNew(false);
				contact.setModified(false);
			}

			return contact;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Contact findByPrimaryKey(String contactId)
		throws NoSuchContactException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Contact contact = (Contact)session.get(Contact.class, contactId);

			if (contact == null) {
				_log.warn("No Contact exists with the primary key " +
					contactId.toString());
				throw new NoSuchContactException(
					"No Contact exists with the primary key " +
					contactId.toString());
			}

			return contact;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Contact WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			List list = q.list();

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCompanyId(String companyId, int begin, int end)
		throws SystemException {
		return findByCompanyId(companyId, begin, end, null);
	}

	public List findByCompanyId(String companyId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Contact WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Contact findByCompanyId_First(String companyId, OrderByComparator obc)
		throws NoSuchContactException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Contact exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchContactException(msg);
		}
		else {
			return (Contact)list.get(0);
		}
	}

	public Contact findByCompanyId_Last(String companyId, OrderByComparator obc)
		throws NoSuchContactException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Contact exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchContactException(msg);
		}
		else {
			return (Contact)list.get(0);
		}
	}

	public Contact[] findByCompanyId_PrevAndNext(String contactId,
		String companyId, OrderByComparator obc)
		throws NoSuchContactException, SystemException {
		Contact contact = findByPrimaryKey(contactId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Contact WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, contact);
			Contact[] array = new Contact[3];
			array[0] = (Contact)objArray[0];
			array[1] = (Contact)objArray[1];
			array[2] = (Contact)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Contact ");

			Query q = session.createQuery(query.toString());

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Contact WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				Contact contact = (Contact)itr.next();
				session.delete(contact);
			}

			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Contact WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(ContactPersistence.class);
}