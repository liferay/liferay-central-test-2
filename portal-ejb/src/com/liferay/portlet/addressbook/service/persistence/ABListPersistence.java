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

package com.liferay.portlet.addressbook.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.addressbook.NoSuchListException;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * <a href="ABListPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ABListPersistence extends BasePersistence {
	public com.liferay.portlet.addressbook.model.ABList create(String listId) {
		return new com.liferay.portlet.addressbook.model.ABList(listId);
	}

	public com.liferay.portlet.addressbook.model.ABList remove(String listId)
		throws NoSuchListException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ABListHBM abListHBM = (ABListHBM)session.get(ABListHBM.class, listId);

			if (abListHBM == null) {
				_log.warn("No ABList exists with the primary key " +
					listId.toString());
				throw new NoSuchListException(
					"No ABList exists with the primary key " +
					listId.toString());
			}

			com.liferay.portlet.addressbook.model.ABList abList = ABListHBMUtil.model(abListHBM);
			session.delete(abListHBM);
			session.flush();
			ABListPool.remove(listId);

			return abList;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.addressbook.model.ABList update(
		com.liferay.portlet.addressbook.model.ABList abList)
		throws SystemException {
		Session session = null;

		try {
			if (abList.isNew() || abList.isModified()) {
				session = openSession();

				if (abList.isNew()) {
					ABListHBM abListHBM = new ABListHBM(abList.getListId(),
							abList.getUserId(), abList.getName());
					session.save(abListHBM);
					session.flush();
				}
				else {
					ABListHBM abListHBM = (ABListHBM)session.get(ABListHBM.class,
							abList.getPrimaryKey());

					if (abListHBM != null) {
						abListHBM.setUserId(abList.getUserId());
						abListHBM.setName(abList.getName());
						session.flush();
					}
					else {
						abListHBM = new ABListHBM(abList.getListId(),
								abList.getUserId(), abList.getName());
						session.save(abListHBM);
						session.flush();
					}
				}

				abList.setNew(false);
				abList.setModified(false);
				abList.protect();
				ABListPool.update(abList.getPrimaryKey(), abList);
			}

			return abList;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List getABContacts(String pk)
		throws NoSuchListException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ABListHBM abListHBM = (ABListHBM)session.get(ABListHBM.class, pk);

			if (abListHBM == null) {
				_log.warn("No ABList exists with the primary key " +
					pk.toString());
				throw new NoSuchListException(
					"No ABList exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT abContactHBM FROM ");
			query.append(com.liferay.portlet.addressbook.service.persistence.ABListHBM.class.getName());
			query.append(" abListHBM ");
			query.append("JOIN abListHBM.contacts AS abContactHBM ");
			query.append("WHERE abListHBM.listId = ? ");
			query.append("ORDER BY ");
			query.append("abContactHBM.firstName ASC").append(", ");
			query.append("abContactHBM.lastName ASC").append(", ");
			query.append("abContactHBM.emailAddress ASC");

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				com.liferay.portlet.addressbook.service.persistence.ABContactHBM abContactHBM =
					(com.liferay.portlet.addressbook.service.persistence.ABContactHBM)itr.next();
				list.add(com.liferay.portlet.addressbook.service.persistence.ABContactHBMUtil.model(
						abContactHBM));
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

	public List getABContacts(String pk, int begin, int end)
		throws NoSuchListException, SystemException {
		return getABContacts(pk, begin, end, null);
	}

	public List getABContacts(String pk, int begin, int end,
		OrderByComparator obc) throws NoSuchListException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ABListHBM abListHBM = (ABListHBM)session.get(ABListHBM.class, pk);

			if (abListHBM == null) {
				_log.warn("No ABList exists with the primary key " +
					pk.toString());
				throw new NoSuchListException(
					"No ABList exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT abContactHBM FROM ");
			query.append(com.liferay.portlet.addressbook.service.persistence.ABListHBM.class.getName());
			query.append(" abListHBM ");
			query.append("JOIN abListHBM.contacts AS abContactHBM ");
			query.append("WHERE abListHBM.listId = ? ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("abContactHBM.firstName ASC").append(", ");
				query.append("abContactHBM.lastName ASC").append(", ");
				query.append("abContactHBM.emailAddress ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				com.liferay.portlet.addressbook.service.persistence.ABContactHBM abContactHBM =
					(com.liferay.portlet.addressbook.service.persistence.ABContactHBM)itr.next();
				list.add(com.liferay.portlet.addressbook.service.persistence.ABContactHBMUtil.model(
						abContactHBM));
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

	public int getABContactsSize(String pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) FROM ");
			query.append(com.liferay.portlet.addressbook.service.persistence.ABListHBM.class.getName());
			query.append(" abListHBM ");
			query.append("JOIN abListHBM.contacts AS abContactHBM ");
			query.append("WHERE abListHBM.listId = ? ");

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			Iterator itr = q.iterate();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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

	public void setABContacts(String pk, String[] pks)
		throws NoSuchListException, 
			com.liferay.portlet.addressbook.NoSuchContactException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABListHBM abListHBM = (ABListHBM)session.get(ABListHBM.class, pk);

			if (abListHBM == null) {
				_log.warn("No ABList exists with the primary key " +
					pk.toString());
				throw new NoSuchListException(
					"No ABList exists with the primary key " + pk.toString());
			}

			Set contactsSet = new HashSet();

			for (int i = 0; (pks != null) && (i < pks.length); i++) {
				com.liferay.portlet.addressbook.service.persistence.ABContactHBM abContactHBM =
					(com.liferay.portlet.addressbook.service.persistence.ABContactHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABContactHBM.class,
						pks[i]);

				if (abContactHBM == null) {
					_log.warn("No ABContact exists with the primary key " +
						pks[i].toString());
					throw new com.liferay.portlet.addressbook.NoSuchContactException(
						"No ABContact exists with the primary key " +
						pks[i].toString());
				}

				contactsSet.add(abContactHBM);
			}

			abListHBM.setContacts(contactsSet);
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void setABContacts(String pk, List contacts)
		throws NoSuchListException, 
			com.liferay.portlet.addressbook.NoSuchContactException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABListHBM abListHBM = (ABListHBM)session.get(ABListHBM.class, pk);

			if (abListHBM == null) {
				_log.warn("No ABList exists with the primary key " +
					pk.toString());
				throw new NoSuchListException(
					"No ABList exists with the primary key " + pk.toString());
			}

			Set contactsSet = new HashSet();
			Iterator itr = contacts.iterator();

			while (itr.hasNext()) {
				com.liferay.portlet.addressbook.model.ABContact abContact = (com.liferay.portlet.addressbook.model.ABContact)itr.next();
				com.liferay.portlet.addressbook.service.persistence.ABContactHBM abContactHBM =
					(com.liferay.portlet.addressbook.service.persistence.ABContactHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABContactHBM.class,
						abContact.getPrimaryKey());

				if (abContactHBM == null) {
					_log.warn("No ABContact exists with the primary key " +
						abContact.getPrimaryKey().toString());
					throw new com.liferay.portlet.addressbook.NoSuchContactException(
						"No ABContact exists with the primary key " +
						abContact.getPrimaryKey().toString());
				}

				contactsSet.add(abContactHBM);
			}

			abListHBM.setContacts(contactsSet);
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addABContact(String pk, String abContactPK)
		throws NoSuchListException, 
			com.liferay.portlet.addressbook.NoSuchContactException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABListHBM abListHBM = (ABListHBM)session.get(ABListHBM.class, pk);

			if (abListHBM == null) {
				_log.warn("No ABList exists with the primary key " +
					pk.toString());
				throw new NoSuchListException(
					"No ABList exists with the primary key " + pk.toString());
			}

			com.liferay.portlet.addressbook.service.persistence.ABContactHBM abContactHBM =
				null;
			abContactHBM = (com.liferay.portlet.addressbook.service.persistence.ABContactHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABContactHBM.class,
					abContactPK);

			if (abContactHBM == null) {
				_log.warn("No ABContact exists with the primary key " +
					abContactPK.toString());
				throw new com.liferay.portlet.addressbook.NoSuchContactException(
					"No ABContact exists with the primary key " +
					abContactPK.toString());
			}

			boolean value = abListHBM.getContacts().add(abContactHBM);
			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addABContact(String pk,
		com.liferay.portlet.addressbook.model.ABContact abContact)
		throws NoSuchListException, 
			com.liferay.portlet.addressbook.NoSuchContactException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABListHBM abListHBM = (ABListHBM)session.get(ABListHBM.class, pk);

			if (abListHBM == null) {
				_log.warn("No ABList exists with the primary key " +
					pk.toString());
				throw new NoSuchListException(
					"No ABList exists with the primary key " + pk.toString());
			}

			com.liferay.portlet.addressbook.service.persistence.ABContactHBM abContactHBM =
				null;
			abContactHBM = (com.liferay.portlet.addressbook.service.persistence.ABContactHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABContactHBM.class,
					abContact.getPrimaryKey());

			if (abContactHBM == null) {
				_log.warn("No ABContact exists with the primary key " +
					abContact.getPrimaryKey().toString());
				throw new com.liferay.portlet.addressbook.NoSuchContactException(
					"No ABContact exists with the primary key " +
					abContact.getPrimaryKey().toString());
			}

			boolean value = abListHBM.getContacts().add(abContactHBM);
			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addABContacts(String pk, String[] abContactPKs)
		throws NoSuchListException, 
			com.liferay.portlet.addressbook.NoSuchContactException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABListHBM abListHBM = (ABListHBM)session.get(ABListHBM.class, pk);

			if (abListHBM == null) {
				_log.warn("No ABList exists with the primary key " +
					pk.toString());
				throw new NoSuchListException(
					"No ABList exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < abContactPKs.length; i++) {
				com.liferay.portlet.addressbook.service.persistence.ABContactHBM abContactHBM =
					null;
				abContactHBM = (com.liferay.portlet.addressbook.service.persistence.ABContactHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABContactHBM.class,
						abContactPKs[i]);

				if (abContactHBM == null) {
					_log.warn("No ABContact exists with the primary key " +
						abContactPKs[i].toString());
					throw new com.liferay.portlet.addressbook.NoSuchContactException(
						"No ABContact exists with the primary key " +
						abContactPKs[i].toString());
				}

				if (abListHBM.getContacts().add(abContactHBM)) {
					value = true;
				}
			}

			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addABContacts(String pk, List abContacts)
		throws NoSuchListException, 
			com.liferay.portlet.addressbook.NoSuchContactException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABListHBM abListHBM = (ABListHBM)session.get(ABListHBM.class, pk);

			if (abListHBM == null) {
				_log.warn("No ABList exists with the primary key " +
					pk.toString());
				throw new NoSuchListException(
					"No ABList exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < abContacts.size(); i++) {
				com.liferay.portlet.addressbook.model.ABContact abContact = (com.liferay.portlet.addressbook.model.ABContact)abContacts.get(i);
				com.liferay.portlet.addressbook.service.persistence.ABContactHBM abContactHBM =
					(com.liferay.portlet.addressbook.service.persistence.ABContactHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABContactHBM.class,
						abContact.getPrimaryKey());

				if (abContactHBM == null) {
					_log.warn("No ABContact exists with the primary key " +
						abContact.getPrimaryKey().toString());
					throw new com.liferay.portlet.addressbook.NoSuchContactException(
						"No ABContact exists with the primary key " +
						abContact.getPrimaryKey().toString());
				}

				if (abListHBM.getContacts().add(abContactHBM)) {
					value = true;
				}
			}

			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void clearABContacts(String pk)
		throws NoSuchListException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ABListHBM abListHBM = (ABListHBM)session.get(ABListHBM.class, pk);

			if (abListHBM == null) {
				_log.warn("No ABList exists with the primary key " +
					pk.toString());
				throw new NoSuchListException(
					"No ABList exists with the primary key " + pk.toString());
			}

			abListHBM.getContacts().clear();
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean containsABContact(String pk, String abContactPK)
		throws NoSuchListException, 
			com.liferay.portlet.addressbook.NoSuchContactException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABListHBM abListHBM = (ABListHBM)session.get(ABListHBM.class, pk);

			if (abListHBM == null) {
				_log.warn("No ABList exists with the primary key " +
					pk.toString());
				throw new NoSuchListException(
					"No ABList exists with the primary key " + pk.toString());
			}

			com.liferay.portlet.addressbook.service.persistence.ABContactHBM abContactHBM =
				null;
			abContactHBM = (com.liferay.portlet.addressbook.service.persistence.ABContactHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABContactHBM.class,
					abContactPK);

			if (abContactHBM == null) {
				_log.warn("No ABContact exists with the primary key " +
					abContactPK.toString());
				throw new com.liferay.portlet.addressbook.NoSuchContactException(
					"No ABContact exists with the primary key " +
					abContactPK.toString());
			}

			Collection c = abListHBM.getContacts();

			return c.contains(abContactHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean containsABContact(String pk,
		com.liferay.portlet.addressbook.model.ABContact abContact)
		throws NoSuchListException, 
			com.liferay.portlet.addressbook.NoSuchContactException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABListHBM abListHBM = (ABListHBM)session.get(ABListHBM.class, pk);

			if (abListHBM == null) {
				_log.warn("No ABList exists with the primary key " +
					pk.toString());
				throw new NoSuchListException(
					"No ABList exists with the primary key " + pk.toString());
			}

			com.liferay.portlet.addressbook.service.persistence.ABContactHBM abContactHBM =
				null;
			abContactHBM = (com.liferay.portlet.addressbook.service.persistence.ABContactHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABContactHBM.class,
					abContact.getPrimaryKey());

			if (abContactHBM == null) {
				_log.warn("No ABContact exists with the primary key " +
					abContact.getPrimaryKey().toString());
				throw new com.liferay.portlet.addressbook.NoSuchContactException(
					"No ABContact exists with the primary key " +
					abContact.getPrimaryKey().toString());
			}

			Collection c = abListHBM.getContacts();

			return c.contains(abContactHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean removeABContact(String pk, String abContactPK)
		throws NoSuchListException, 
			com.liferay.portlet.addressbook.NoSuchContactException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABListHBM abListHBM = (ABListHBM)session.get(ABListHBM.class, pk);

			if (abListHBM == null) {
				_log.warn("No ABList exists with the primary key " +
					pk.toString());
				throw new NoSuchListException(
					"No ABList exists with the primary key " + pk.toString());
			}

			com.liferay.portlet.addressbook.service.persistence.ABContactHBM abContactHBM =
				null;
			abContactHBM = (com.liferay.portlet.addressbook.service.persistence.ABContactHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABContactHBM.class,
					abContactPK);

			if (abContactHBM == null) {
				_log.warn("No ABContact exists with the primary key " +
					abContactPK.toString());
				throw new com.liferay.portlet.addressbook.NoSuchContactException(
					"No ABContact exists with the primary key " +
					abContactPK.toString());
			}

			boolean value = abListHBM.getContacts().remove(abContactHBM);
			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean removeABContact(String pk,
		com.liferay.portlet.addressbook.model.ABContact abContact)
		throws NoSuchListException, 
			com.liferay.portlet.addressbook.NoSuchContactException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABListHBM abListHBM = (ABListHBM)session.get(ABListHBM.class, pk);

			if (abListHBM == null) {
				_log.warn("No ABList exists with the primary key " +
					pk.toString());
				throw new NoSuchListException(
					"No ABList exists with the primary key " + pk.toString());
			}

			com.liferay.portlet.addressbook.service.persistence.ABContactHBM abContactHBM =
				null;
			abContactHBM = (com.liferay.portlet.addressbook.service.persistence.ABContactHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABContactHBM.class,
					abContact.getPrimaryKey());

			if (abContactHBM == null) {
				_log.warn("No ABContact exists with the primary key " +
					abContact.getPrimaryKey().toString());
				throw new com.liferay.portlet.addressbook.NoSuchContactException(
					"No ABContact exists with the primary key " +
					abContact.getPrimaryKey().toString());
			}

			boolean value = abListHBM.getContacts().remove(abContactHBM);
			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean removeABContacts(String pk, String[] abContactPKs)
		throws NoSuchListException, 
			com.liferay.portlet.addressbook.NoSuchContactException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABListHBM abListHBM = (ABListHBM)session.get(ABListHBM.class, pk);

			if (abListHBM == null) {
				_log.warn("No ABList exists with the primary key " +
					pk.toString());
				throw new NoSuchListException(
					"No ABList exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < abContactPKs.length; i++) {
				com.liferay.portlet.addressbook.service.persistence.ABContactHBM abContactHBM =
					null;
				abContactHBM = (com.liferay.portlet.addressbook.service.persistence.ABContactHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABContactHBM.class,
						abContactPKs[i]);

				if (abContactHBM == null) {
					_log.warn("No ABContact exists with the primary key " +
						abContactPKs[i].toString());
					throw new com.liferay.portlet.addressbook.NoSuchContactException(
						"No ABContact exists with the primary key " +
						abContactPKs[i].toString());
				}

				if (abListHBM.getContacts().remove(abContactHBM)) {
					value = true;
				}
			}

			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean removeABContacts(String pk, List abContacts)
		throws NoSuchListException, 
			com.liferay.portlet.addressbook.NoSuchContactException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABListHBM abListHBM = (ABListHBM)session.get(ABListHBM.class, pk);

			if (abListHBM == null) {
				_log.warn("No ABList exists with the primary key " +
					pk.toString());
				throw new NoSuchListException(
					"No ABList exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < abContacts.size(); i++) {
				com.liferay.portlet.addressbook.model.ABContact abContact = (com.liferay.portlet.addressbook.model.ABContact)abContacts.get(i);
				com.liferay.portlet.addressbook.service.persistence.ABContactHBM abContactHBM =
					(com.liferay.portlet.addressbook.service.persistence.ABContactHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABContactHBM.class,
						abContact.getPrimaryKey());

				if (abContactHBM == null) {
					_log.warn("No ABContact exists with the primary key " +
						abContact.getPrimaryKey().toString());
					throw new com.liferay.portlet.addressbook.NoSuchContactException(
						"No ABContact exists with the primary key " +
						abContact.getPrimaryKey().toString());
				}

				if (abListHBM.getContacts().remove(abContactHBM)) {
					value = true;
				}
			}

			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.addressbook.model.ABList findByPrimaryKey(
		String listId) throws NoSuchListException, SystemException {
		com.liferay.portlet.addressbook.model.ABList abList = ABListPool.get(listId);
		Session session = null;

		try {
			if (abList == null) {
				session = openSession();

				ABListHBM abListHBM = (ABListHBM)session.get(ABListHBM.class,
						listId);

				if (abListHBM == null) {
					_log.warn("No ABList exists with the primary key " +
						listId.toString());
					throw new NoSuchListException(
						"No ABList exists with the primary key " +
						listId.toString());
				}

				abList = ABListHBMUtil.model(abListHBM, false);
			}

			return abList;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByUserId(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ABList IN CLASS com.liferay.portlet.addressbook.service.persistence.ABListHBM WHERE ");
			query.append("userId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				ABListHBM abListHBM = (ABListHBM)itr.next();
				list.add(ABListHBMUtil.model(abListHBM));
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

	public List findByUserId(String userId, int begin, int end)
		throws SystemException {
		return findByUserId(userId, begin, end, null);
	}

	public List findByUserId(String userId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ABList IN CLASS com.liferay.portlet.addressbook.service.persistence.ABListHBM WHERE ");
			query.append("userId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				ABListHBM abListHBM = (ABListHBM)itr.next();
				list.add(ABListHBMUtil.model(abListHBM));
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

	public com.liferay.portlet.addressbook.model.ABList findByUserId_First(
		String userId, OrderByComparator obc)
		throws NoSuchListException, SystemException {
		List list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No ABList exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchListException(msg);
		}
		else {
			return (com.liferay.portlet.addressbook.model.ABList)list.get(0);
		}
	}

	public com.liferay.portlet.addressbook.model.ABList findByUserId_Last(
		String userId, OrderByComparator obc)
		throws NoSuchListException, SystemException {
		int count = countByUserId(userId);
		List list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No ABList exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchListException(msg);
		}
		else {
			return (com.liferay.portlet.addressbook.model.ABList)list.get(0);
		}
	}

	public com.liferay.portlet.addressbook.model.ABList[] findByUserId_PrevAndNext(
		String listId, String userId, OrderByComparator obc)
		throws NoSuchListException, SystemException {
		com.liferay.portlet.addressbook.model.ABList abList = findByPrimaryKey(listId);
		int count = countByUserId(userId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ABList IN CLASS com.liferay.portlet.addressbook.service.persistence.ABListHBM WHERE ");
			query.append("userId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, abList,
					ABListHBMUtil.getInstance());
			com.liferay.portlet.addressbook.model.ABList[] array = new com.liferay.portlet.addressbook.model.ABList[3];
			array[0] = (com.liferay.portlet.addressbook.model.ABList)objArray[0];
			array[1] = (com.liferay.portlet.addressbook.model.ABList)objArray[1];
			array[2] = (com.liferay.portlet.addressbook.model.ABList)objArray[2];

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
				"FROM ABList IN CLASS com.liferay.portlet.addressbook.service.persistence.ABListHBM ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				ABListHBM abListHBM = (ABListHBM)itr.next();
				list.add(ABListHBMUtil.model(abListHBM));
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

	public void removeByUserId(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ABList IN CLASS com.liferay.portlet.addressbook.service.persistence.ABListHBM WHERE ");
			query.append("userId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				ABListHBM abListHBM = (ABListHBM)itr.next();
				ABListPool.remove((String)abListHBM.getPrimaryKey());
				session.delete(abListHBM);
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

	public int countByUserId(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM ABList IN CLASS com.liferay.portlet.addressbook.service.persistence.ABListHBM WHERE ");
			query.append("userId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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

	private static Log _log = LogFactory.getLog(ABListPersistence.class);
}