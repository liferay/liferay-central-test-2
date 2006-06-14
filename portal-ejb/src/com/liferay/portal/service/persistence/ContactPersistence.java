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
	public com.liferay.portal.model.Contact create(String contactId) {
		ContactHBM contactHBM = new ContactHBM();
		contactHBM.setNew(true);
		contactHBM.setPrimaryKey(contactId);

		return ContactHBMUtil.model(contactHBM);
	}

	public com.liferay.portal.model.Contact remove(String contactId)
		throws NoSuchContactException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ContactHBM contactHBM = (ContactHBM)session.get(ContactHBM.class,
					contactId);

			if (contactHBM == null) {
				_log.warn("No Contact exists with the primary key " +
					contactId.toString());
				throw new NoSuchContactException(
					"No Contact exists with the primary key " +
					contactId.toString());
			}

			session.delete(contactHBM);
			session.flush();

			return ContactHBMUtil.model(contactHBM);
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
					ContactHBM contactHBM = new ContactHBM();
					contactHBM.setContactId(contact.getContactId());
					contactHBM.setCompanyId(contact.getCompanyId());
					contactHBM.setUserId(contact.getUserId());
					contactHBM.setUserName(contact.getUserName());
					contactHBM.setCreateDate(contact.getCreateDate());
					contactHBM.setModifiedDate(contact.getModifiedDate());
					contactHBM.setAccountId(contact.getAccountId());
					contactHBM.setParentContactId(contact.getParentContactId());
					contactHBM.setFirstName(contact.getFirstName());
					contactHBM.setMiddleName(contact.getMiddleName());
					contactHBM.setLastName(contact.getLastName());
					contactHBM.setNickName(contact.getNickName());
					contactHBM.setPrefixId(contact.getPrefixId());
					contactHBM.setSuffixId(contact.getSuffixId());
					contactHBM.setMale(contact.getMale());
					contactHBM.setBirthday(contact.getBirthday());
					contactHBM.setSmsSn(contact.getSmsSn());
					contactHBM.setAimSn(contact.getAimSn());
					contactHBM.setIcqSn(contact.getIcqSn());
					contactHBM.setMsnSn(contact.getMsnSn());
					contactHBM.setSkypeSn(contact.getSkypeSn());
					contactHBM.setYmSn(contact.getYmSn());
					contactHBM.setEmployeeStatusId(contact.getEmployeeStatusId());
					contactHBM.setEmployeeNumber(contact.getEmployeeNumber());
					contactHBM.setJobTitle(contact.getJobTitle());
					contactHBM.setJobClass(contact.getJobClass());
					contactHBM.setHoursOfOperation(contact.getHoursOfOperation());
					session.save(contactHBM);
					session.flush();
				}
				else {
					ContactHBM contactHBM = (ContactHBM)session.get(ContactHBM.class,
							contact.getPrimaryKey());

					if (contactHBM != null) {
						contactHBM.setCompanyId(contact.getCompanyId());
						contactHBM.setUserId(contact.getUserId());
						contactHBM.setUserName(contact.getUserName());
						contactHBM.setCreateDate(contact.getCreateDate());
						contactHBM.setModifiedDate(contact.getModifiedDate());
						contactHBM.setAccountId(contact.getAccountId());
						contactHBM.setParentContactId(contact.getParentContactId());
						contactHBM.setFirstName(contact.getFirstName());
						contactHBM.setMiddleName(contact.getMiddleName());
						contactHBM.setLastName(contact.getLastName());
						contactHBM.setNickName(contact.getNickName());
						contactHBM.setPrefixId(contact.getPrefixId());
						contactHBM.setSuffixId(contact.getSuffixId());
						contactHBM.setMale(contact.getMale());
						contactHBM.setBirthday(contact.getBirthday());
						contactHBM.setSmsSn(contact.getSmsSn());
						contactHBM.setAimSn(contact.getAimSn());
						contactHBM.setIcqSn(contact.getIcqSn());
						contactHBM.setMsnSn(contact.getMsnSn());
						contactHBM.setSkypeSn(contact.getSkypeSn());
						contactHBM.setYmSn(contact.getYmSn());
						contactHBM.setEmployeeStatusId(contact.getEmployeeStatusId());
						contactHBM.setEmployeeNumber(contact.getEmployeeNumber());
						contactHBM.setJobTitle(contact.getJobTitle());
						contactHBM.setJobClass(contact.getJobClass());
						contactHBM.setHoursOfOperation(contact.getHoursOfOperation());
						session.flush();
					}
					else {
						contactHBM = new ContactHBM();
						contactHBM.setContactId(contact.getContactId());
						contactHBM.setCompanyId(contact.getCompanyId());
						contactHBM.setUserId(contact.getUserId());
						contactHBM.setUserName(contact.getUserName());
						contactHBM.setCreateDate(contact.getCreateDate());
						contactHBM.setModifiedDate(contact.getModifiedDate());
						contactHBM.setAccountId(contact.getAccountId());
						contactHBM.setParentContactId(contact.getParentContactId());
						contactHBM.setFirstName(contact.getFirstName());
						contactHBM.setMiddleName(contact.getMiddleName());
						contactHBM.setLastName(contact.getLastName());
						contactHBM.setNickName(contact.getNickName());
						contactHBM.setPrefixId(contact.getPrefixId());
						contactHBM.setSuffixId(contact.getSuffixId());
						contactHBM.setMale(contact.getMale());
						contactHBM.setBirthday(contact.getBirthday());
						contactHBM.setSmsSn(contact.getSmsSn());
						contactHBM.setAimSn(contact.getAimSn());
						contactHBM.setIcqSn(contact.getIcqSn());
						contactHBM.setMsnSn(contact.getMsnSn());
						contactHBM.setSkypeSn(contact.getSkypeSn());
						contactHBM.setYmSn(contact.getYmSn());
						contactHBM.setEmployeeStatusId(contact.getEmployeeStatusId());
						contactHBM.setEmployeeNumber(contact.getEmployeeNumber());
						contactHBM.setJobTitle(contact.getJobTitle());
						contactHBM.setJobClass(contact.getJobClass());
						contactHBM.setHoursOfOperation(contact.getHoursOfOperation());
						session.save(contactHBM);
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

	public com.liferay.portal.model.Contact findByPrimaryKey(String contactId)
		throws NoSuchContactException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ContactHBM contactHBM = (ContactHBM)session.get(ContactHBM.class,
					contactId);

			if (contactHBM == null) {
				_log.warn("No Contact exists with the primary key " +
					contactId.toString());
				throw new NoSuchContactException(
					"No Contact exists with the primary key " +
					contactId.toString());
			}

			return ContactHBMUtil.model(contactHBM);
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
			query.append(
				"FROM Contact_ IN CLASS com.liferay.portal.service.persistence.ContactHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				ContactHBM contactHBM = (ContactHBM)itr.next();
				list.add(ContactHBMUtil.model(contactHBM));
			}

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
			query.append(
				"FROM Contact_ IN CLASS com.liferay.portal.service.persistence.ContactHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				ContactHBM contactHBM = (ContactHBM)itr.next();
				list.add(ContactHBMUtil.model(contactHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Contact findByCompanyId_First(
		String companyId, OrderByComparator obc)
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
			return (com.liferay.portal.model.Contact)list.get(0);
		}
	}

	public com.liferay.portal.model.Contact findByCompanyId_Last(
		String companyId, OrderByComparator obc)
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
			return (com.liferay.portal.model.Contact)list.get(0);
		}
	}

	public com.liferay.portal.model.Contact[] findByCompanyId_PrevAndNext(
		String contactId, String companyId, OrderByComparator obc)
		throws NoSuchContactException, SystemException {
		com.liferay.portal.model.Contact contact = findByPrimaryKey(contactId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Contact_ IN CLASS com.liferay.portal.service.persistence.ContactHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					contact, ContactHBMUtil.getInstance());
			com.liferay.portal.model.Contact[] array = new com.liferay.portal.model.Contact[3];
			array[0] = (com.liferay.portal.model.Contact)objArray[0];
			array[1] = (com.liferay.portal.model.Contact)objArray[1];
			array[2] = (com.liferay.portal.model.Contact)objArray[2];

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
			query.append(
				"FROM Contact_ IN CLASS com.liferay.portal.service.persistence.ContactHBM ");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				ContactHBM contactHBM = (ContactHBM)itr.next();
				list.add(ContactHBMUtil.model(contactHBM));
			}

			return list;
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
			query.append(
				"FROM Contact_ IN CLASS com.liferay.portal.service.persistence.ContactHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				ContactHBM contactHBM = (ContactHBM)itr.next();
				session.delete(contactHBM);
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
			query.append(
				"FROM Contact_ IN CLASS com.liferay.portal.service.persistence.ContactHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

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

	private static Log _log = LogFactory.getLog(ContactPersistence.class);
}