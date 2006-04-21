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

import com.liferay.portlet.addressbook.NoSuchContactException;

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
 * <a href="ABContactPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ABContactPersistence extends BasePersistence {
	public com.liferay.portlet.addressbook.model.ABContact create(
		String contactId) {
		return new com.liferay.portlet.addressbook.model.ABContact(contactId);
	}

	public com.liferay.portlet.addressbook.model.ABContact remove(
		String contactId) throws NoSuchContactException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ABContactHBM abContactHBM = (ABContactHBM)session.get(ABContactHBM.class,
					contactId);

			if (abContactHBM == null) {
				_log.warn("No ABContact exists with the primary key " +
					contactId.toString());
				throw new NoSuchContactException(
					"No ABContact exists with the primary key " +
					contactId.toString());
			}

			com.liferay.portlet.addressbook.model.ABContact abContact = ABContactHBMUtil.model(abContactHBM);
			session.delete(abContactHBM);
			session.flush();
			ABContactPool.remove(contactId);

			return abContact;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portlet.addressbook.model.ABContact update(
		com.liferay.portlet.addressbook.model.ABContact abContact)
		throws SystemException {
		Session session = null;

		try {
			if (abContact.isNew() || abContact.isModified()) {
				session = openSession();

				if (abContact.isNew()) {
					ABContactHBM abContactHBM = new ABContactHBM(abContact.getContactId(),
							abContact.getUserId(), abContact.getFirstName(),
							abContact.getMiddleName(), abContact.getLastName(),
							abContact.getNickName(),
							abContact.getEmailAddress(),
							abContact.getHomeStreet(), abContact.getHomeCity(),
							abContact.getHomeState(), abContact.getHomeZip(),
							abContact.getHomeCountry(),
							abContact.getHomePhone(), abContact.getHomeFax(),
							abContact.getHomeCell(), abContact.getHomePager(),
							abContact.getHomeTollFree(),
							abContact.getHomeEmailAddress(),
							abContact.getBusinessCompany(),
							abContact.getBusinessStreet(),
							abContact.getBusinessCity(),
							abContact.getBusinessState(),
							abContact.getBusinessZip(),
							abContact.getBusinessCountry(),
							abContact.getBusinessPhone(),
							abContact.getBusinessFax(),
							abContact.getBusinessCell(),
							abContact.getBusinessPager(),
							abContact.getBusinessTollFree(),
							abContact.getBusinessEmailAddress(),
							abContact.getEmployeeNumber(),
							abContact.getJobTitle(), abContact.getJobClass(),
							abContact.getHoursOfOperation(),
							abContact.getBirthday(), abContact.getTimeZoneId(),
							abContact.getInstantMessenger(),
							abContact.getWebsite(), abContact.getComments());
					session.save(abContactHBM);
					session.flush();
				}
				else {
					ABContactHBM abContactHBM = (ABContactHBM)session.get(ABContactHBM.class,
							abContact.getPrimaryKey());

					if (abContactHBM != null) {
						abContactHBM.setUserId(abContact.getUserId());
						abContactHBM.setFirstName(abContact.getFirstName());
						abContactHBM.setMiddleName(abContact.getMiddleName());
						abContactHBM.setLastName(abContact.getLastName());
						abContactHBM.setNickName(abContact.getNickName());
						abContactHBM.setEmailAddress(abContact.getEmailAddress());
						abContactHBM.setHomeStreet(abContact.getHomeStreet());
						abContactHBM.setHomeCity(abContact.getHomeCity());
						abContactHBM.setHomeState(abContact.getHomeState());
						abContactHBM.setHomeZip(abContact.getHomeZip());
						abContactHBM.setHomeCountry(abContact.getHomeCountry());
						abContactHBM.setHomePhone(abContact.getHomePhone());
						abContactHBM.setHomeFax(abContact.getHomeFax());
						abContactHBM.setHomeCell(abContact.getHomeCell());
						abContactHBM.setHomePager(abContact.getHomePager());
						abContactHBM.setHomeTollFree(abContact.getHomeTollFree());
						abContactHBM.setHomeEmailAddress(abContact.getHomeEmailAddress());
						abContactHBM.setBusinessCompany(abContact.getBusinessCompany());
						abContactHBM.setBusinessStreet(abContact.getBusinessStreet());
						abContactHBM.setBusinessCity(abContact.getBusinessCity());
						abContactHBM.setBusinessState(abContact.getBusinessState());
						abContactHBM.setBusinessZip(abContact.getBusinessZip());
						abContactHBM.setBusinessCountry(abContact.getBusinessCountry());
						abContactHBM.setBusinessPhone(abContact.getBusinessPhone());
						abContactHBM.setBusinessFax(abContact.getBusinessFax());
						abContactHBM.setBusinessCell(abContact.getBusinessCell());
						abContactHBM.setBusinessPager(abContact.getBusinessPager());
						abContactHBM.setBusinessTollFree(abContact.getBusinessTollFree());
						abContactHBM.setBusinessEmailAddress(abContact.getBusinessEmailAddress());
						abContactHBM.setEmployeeNumber(abContact.getEmployeeNumber());
						abContactHBM.setJobTitle(abContact.getJobTitle());
						abContactHBM.setJobClass(abContact.getJobClass());
						abContactHBM.setHoursOfOperation(abContact.getHoursOfOperation());
						abContactHBM.setBirthday(abContact.getBirthday());
						abContactHBM.setTimeZoneId(abContact.getTimeZoneId());
						abContactHBM.setInstantMessenger(abContact.getInstantMessenger());
						abContactHBM.setWebsite(abContact.getWebsite());
						abContactHBM.setComments(abContact.getComments());
						session.flush();
					}
					else {
						abContactHBM = new ABContactHBM(abContact.getContactId(),
								abContact.getUserId(),
								abContact.getFirstName(),
								abContact.getMiddleName(),
								abContact.getLastName(),
								abContact.getNickName(),
								abContact.getEmailAddress(),
								abContact.getHomeStreet(),
								abContact.getHomeCity(),
								abContact.getHomeState(),
								abContact.getHomeZip(),
								abContact.getHomeCountry(),
								abContact.getHomePhone(),
								abContact.getHomeFax(),
								abContact.getHomeCell(),
								abContact.getHomePager(),
								abContact.getHomeTollFree(),
								abContact.getHomeEmailAddress(),
								abContact.getBusinessCompany(),
								abContact.getBusinessStreet(),
								abContact.getBusinessCity(),
								abContact.getBusinessState(),
								abContact.getBusinessZip(),
								abContact.getBusinessCountry(),
								abContact.getBusinessPhone(),
								abContact.getBusinessFax(),
								abContact.getBusinessCell(),
								abContact.getBusinessPager(),
								abContact.getBusinessTollFree(),
								abContact.getBusinessEmailAddress(),
								abContact.getEmployeeNumber(),
								abContact.getJobTitle(),
								abContact.getJobClass(),
								abContact.getHoursOfOperation(),
								abContact.getBirthday(),
								abContact.getTimeZoneId(),
								abContact.getInstantMessenger(),
								abContact.getWebsite(), abContact.getComments());
						session.save(abContactHBM);
						session.flush();
					}
				}

				abContact.setNew(false);
				abContact.setModified(false);
				abContact.protect();
				ABContactPool.update(abContact.getPrimaryKey(), abContact);
			}

			return abContact;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List getABLists(String pk)
		throws NoSuchContactException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ABContactHBM abContactHBM = (ABContactHBM)session.get(ABContactHBM.class,
					pk);

			if (abContactHBM == null) {
				_log.warn("No ABContact exists with the primary key " +
					pk.toString());
				throw new NoSuchContactException(
					"No ABContact exists with the primary key " +
					pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT abListHBM FROM ");
			query.append(com.liferay.portlet.addressbook.service.persistence.ABContactHBM.class.getName());
			query.append(" abContactHBM ");
			query.append("JOIN abContactHBM.lists AS abListHBM ");
			query.append("WHERE abContactHBM.contactId = ? ");
			query.append("ORDER BY ");
			query.append("abListHBM.name ASC");

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				com.liferay.portlet.addressbook.service.persistence.ABListHBM abListHBM =
					(com.liferay.portlet.addressbook.service.persistence.ABListHBM)itr.next();
				list.add(com.liferay.portlet.addressbook.service.persistence.ABListHBMUtil.model(
						abListHBM));
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

	public List getABLists(String pk, int begin, int end)
		throws NoSuchContactException, SystemException {
		return getABLists(pk, begin, end, null);
	}

	public List getABLists(String pk, int begin, int end, OrderByComparator obc)
		throws NoSuchContactException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ABContactHBM abContactHBM = (ABContactHBM)session.get(ABContactHBM.class,
					pk);

			if (abContactHBM == null) {
				_log.warn("No ABContact exists with the primary key " +
					pk.toString());
				throw new NoSuchContactException(
					"No ABContact exists with the primary key " +
					pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT abListHBM FROM ");
			query.append(com.liferay.portlet.addressbook.service.persistence.ABContactHBM.class.getName());
			query.append(" abContactHBM ");
			query.append("JOIN abContactHBM.lists AS abListHBM ");
			query.append("WHERE abContactHBM.contactId = ? ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("abListHBM.name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				com.liferay.portlet.addressbook.service.persistence.ABListHBM abListHBM =
					(com.liferay.portlet.addressbook.service.persistence.ABListHBM)itr.next();
				list.add(com.liferay.portlet.addressbook.service.persistence.ABListHBMUtil.model(
						abListHBM));
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

	public int getABListsSize(String pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) FROM ");
			query.append(com.liferay.portlet.addressbook.service.persistence.ABContactHBM.class.getName());
			query.append(" abContactHBM ");
			query.append("JOIN abContactHBM.lists AS abListHBM ");
			query.append("WHERE abContactHBM.contactId = ? ");

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

	public void setABLists(String pk, String[] pks)
		throws NoSuchContactException, 
			com.liferay.portlet.addressbook.NoSuchListException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABContactHBM abContactHBM = (ABContactHBM)session.get(ABContactHBM.class,
					pk);

			if (abContactHBM == null) {
				_log.warn("No ABContact exists with the primary key " +
					pk.toString());
				throw new NoSuchContactException(
					"No ABContact exists with the primary key " +
					pk.toString());
			}

			Set listsSet = new HashSet();

			for (int i = 0; (pks != null) && (i < pks.length); i++) {
				com.liferay.portlet.addressbook.service.persistence.ABListHBM abListHBM =
					(com.liferay.portlet.addressbook.service.persistence.ABListHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABListHBM.class,
						pks[i]);

				if (abListHBM == null) {
					_log.warn("No ABList exists with the primary key " +
						pks[i].toString());
					throw new com.liferay.portlet.addressbook.NoSuchListException(
						"No ABList exists with the primary key " +
						pks[i].toString());
				}

				listsSet.add(abListHBM);
			}

			abContactHBM.setLists(listsSet);
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void setABLists(String pk, List lists)
		throws NoSuchContactException, 
			com.liferay.portlet.addressbook.NoSuchListException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABContactHBM abContactHBM = (ABContactHBM)session.get(ABContactHBM.class,
					pk);

			if (abContactHBM == null) {
				_log.warn("No ABContact exists with the primary key " +
					pk.toString());
				throw new NoSuchContactException(
					"No ABContact exists with the primary key " +
					pk.toString());
			}

			Set listsSet = new HashSet();
			Iterator itr = lists.iterator();

			while (itr.hasNext()) {
				com.liferay.portlet.addressbook.model.ABList abList = (com.liferay.portlet.addressbook.model.ABList)itr.next();
				com.liferay.portlet.addressbook.service.persistence.ABListHBM abListHBM =
					(com.liferay.portlet.addressbook.service.persistence.ABListHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABListHBM.class,
						abList.getPrimaryKey());

				if (abListHBM == null) {
					_log.warn("No ABList exists with the primary key " +
						abList.getPrimaryKey().toString());
					throw new com.liferay.portlet.addressbook.NoSuchListException(
						"No ABList exists with the primary key " +
						abList.getPrimaryKey().toString());
				}

				listsSet.add(abListHBM);
			}

			abContactHBM.setLists(listsSet);
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addABList(String pk, String abListPK)
		throws NoSuchContactException, 
			com.liferay.portlet.addressbook.NoSuchListException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABContactHBM abContactHBM = (ABContactHBM)session.get(ABContactHBM.class,
					pk);

			if (abContactHBM == null) {
				_log.warn("No ABContact exists with the primary key " +
					pk.toString());
				throw new NoSuchContactException(
					"No ABContact exists with the primary key " +
					pk.toString());
			}

			com.liferay.portlet.addressbook.service.persistence.ABListHBM abListHBM =
				null;
			abListHBM = (com.liferay.portlet.addressbook.service.persistence.ABListHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABListHBM.class,
					abListPK);

			if (abListHBM == null) {
				_log.warn("No ABList exists with the primary key " +
					abListPK.toString());
				throw new com.liferay.portlet.addressbook.NoSuchListException(
					"No ABList exists with the primary key " +
					abListPK.toString());
			}

			boolean value = abContactHBM.getLists().add(abListHBM);
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

	public boolean addABList(String pk,
		com.liferay.portlet.addressbook.model.ABList abList)
		throws NoSuchContactException, 
			com.liferay.portlet.addressbook.NoSuchListException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABContactHBM abContactHBM = (ABContactHBM)session.get(ABContactHBM.class,
					pk);

			if (abContactHBM == null) {
				_log.warn("No ABContact exists with the primary key " +
					pk.toString());
				throw new NoSuchContactException(
					"No ABContact exists with the primary key " +
					pk.toString());
			}

			com.liferay.portlet.addressbook.service.persistence.ABListHBM abListHBM =
				null;
			abListHBM = (com.liferay.portlet.addressbook.service.persistence.ABListHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABListHBM.class,
					abList.getPrimaryKey());

			if (abListHBM == null) {
				_log.warn("No ABList exists with the primary key " +
					abList.getPrimaryKey().toString());
				throw new com.liferay.portlet.addressbook.NoSuchListException(
					"No ABList exists with the primary key " +
					abList.getPrimaryKey().toString());
			}

			boolean value = abContactHBM.getLists().add(abListHBM);
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

	public boolean addABLists(String pk, String[] abListPKs)
		throws NoSuchContactException, 
			com.liferay.portlet.addressbook.NoSuchListException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABContactHBM abContactHBM = (ABContactHBM)session.get(ABContactHBM.class,
					pk);

			if (abContactHBM == null) {
				_log.warn("No ABContact exists with the primary key " +
					pk.toString());
				throw new NoSuchContactException(
					"No ABContact exists with the primary key " +
					pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < abListPKs.length; i++) {
				com.liferay.portlet.addressbook.service.persistence.ABListHBM abListHBM =
					null;
				abListHBM = (com.liferay.portlet.addressbook.service.persistence.ABListHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABListHBM.class,
						abListPKs[i]);

				if (abListHBM == null) {
					_log.warn("No ABList exists with the primary key " +
						abListPKs[i].toString());
					throw new com.liferay.portlet.addressbook.NoSuchListException(
						"No ABList exists with the primary key " +
						abListPKs[i].toString());
				}

				if (abContactHBM.getLists().add(abListHBM)) {
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

	public boolean addABLists(String pk, List abLists)
		throws NoSuchContactException, 
			com.liferay.portlet.addressbook.NoSuchListException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABContactHBM abContactHBM = (ABContactHBM)session.get(ABContactHBM.class,
					pk);

			if (abContactHBM == null) {
				_log.warn("No ABContact exists with the primary key " +
					pk.toString());
				throw new NoSuchContactException(
					"No ABContact exists with the primary key " +
					pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < abLists.size(); i++) {
				com.liferay.portlet.addressbook.model.ABList abList = (com.liferay.portlet.addressbook.model.ABList)abLists.get(i);
				com.liferay.portlet.addressbook.service.persistence.ABListHBM abListHBM =
					(com.liferay.portlet.addressbook.service.persistence.ABListHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABListHBM.class,
						abList.getPrimaryKey());

				if (abListHBM == null) {
					_log.warn("No ABList exists with the primary key " +
						abList.getPrimaryKey().toString());
					throw new com.liferay.portlet.addressbook.NoSuchListException(
						"No ABList exists with the primary key " +
						abList.getPrimaryKey().toString());
				}

				if (abContactHBM.getLists().add(abListHBM)) {
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

	public void clearABLists(String pk)
		throws NoSuchContactException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ABContactHBM abContactHBM = (ABContactHBM)session.get(ABContactHBM.class,
					pk);

			if (abContactHBM == null) {
				_log.warn("No ABContact exists with the primary key " +
					pk.toString());
				throw new NoSuchContactException(
					"No ABContact exists with the primary key " +
					pk.toString());
			}

			abContactHBM.getLists().clear();
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean containsABList(String pk, String abListPK)
		throws NoSuchContactException, 
			com.liferay.portlet.addressbook.NoSuchListException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABContactHBM abContactHBM = (ABContactHBM)session.get(ABContactHBM.class,
					pk);

			if (abContactHBM == null) {
				_log.warn("No ABContact exists with the primary key " +
					pk.toString());
				throw new NoSuchContactException(
					"No ABContact exists with the primary key " +
					pk.toString());
			}

			com.liferay.portlet.addressbook.service.persistence.ABListHBM abListHBM =
				null;
			abListHBM = (com.liferay.portlet.addressbook.service.persistence.ABListHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABListHBM.class,
					abListPK);

			if (abListHBM == null) {
				_log.warn("No ABList exists with the primary key " +
					abListPK.toString());
				throw new com.liferay.portlet.addressbook.NoSuchListException(
					"No ABList exists with the primary key " +
					abListPK.toString());
			}

			Collection c = abContactHBM.getLists();

			return c.contains(abListHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean containsABList(String pk,
		com.liferay.portlet.addressbook.model.ABList abList)
		throws NoSuchContactException, 
			com.liferay.portlet.addressbook.NoSuchListException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABContactHBM abContactHBM = (ABContactHBM)session.get(ABContactHBM.class,
					pk);

			if (abContactHBM == null) {
				_log.warn("No ABContact exists with the primary key " +
					pk.toString());
				throw new NoSuchContactException(
					"No ABContact exists with the primary key " +
					pk.toString());
			}

			com.liferay.portlet.addressbook.service.persistence.ABListHBM abListHBM =
				null;
			abListHBM = (com.liferay.portlet.addressbook.service.persistence.ABListHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABListHBM.class,
					abList.getPrimaryKey());

			if (abListHBM == null) {
				_log.warn("No ABList exists with the primary key " +
					abList.getPrimaryKey().toString());
				throw new com.liferay.portlet.addressbook.NoSuchListException(
					"No ABList exists with the primary key " +
					abList.getPrimaryKey().toString());
			}

			Collection c = abContactHBM.getLists();

			return c.contains(abListHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean removeABList(String pk, String abListPK)
		throws NoSuchContactException, 
			com.liferay.portlet.addressbook.NoSuchListException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABContactHBM abContactHBM = (ABContactHBM)session.get(ABContactHBM.class,
					pk);

			if (abContactHBM == null) {
				_log.warn("No ABContact exists with the primary key " +
					pk.toString());
				throw new NoSuchContactException(
					"No ABContact exists with the primary key " +
					pk.toString());
			}

			com.liferay.portlet.addressbook.service.persistence.ABListHBM abListHBM =
				null;
			abListHBM = (com.liferay.portlet.addressbook.service.persistence.ABListHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABListHBM.class,
					abListPK);

			if (abListHBM == null) {
				_log.warn("No ABList exists with the primary key " +
					abListPK.toString());
				throw new com.liferay.portlet.addressbook.NoSuchListException(
					"No ABList exists with the primary key " +
					abListPK.toString());
			}

			boolean value = abContactHBM.getLists().remove(abListHBM);
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

	public boolean removeABList(String pk,
		com.liferay.portlet.addressbook.model.ABList abList)
		throws NoSuchContactException, 
			com.liferay.portlet.addressbook.NoSuchListException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABContactHBM abContactHBM = (ABContactHBM)session.get(ABContactHBM.class,
					pk);

			if (abContactHBM == null) {
				_log.warn("No ABContact exists with the primary key " +
					pk.toString());
				throw new NoSuchContactException(
					"No ABContact exists with the primary key " +
					pk.toString());
			}

			com.liferay.portlet.addressbook.service.persistence.ABListHBM abListHBM =
				null;
			abListHBM = (com.liferay.portlet.addressbook.service.persistence.ABListHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABListHBM.class,
					abList.getPrimaryKey());

			if (abListHBM == null) {
				_log.warn("No ABList exists with the primary key " +
					abList.getPrimaryKey().toString());
				throw new com.liferay.portlet.addressbook.NoSuchListException(
					"No ABList exists with the primary key " +
					abList.getPrimaryKey().toString());
			}

			boolean value = abContactHBM.getLists().remove(abListHBM);
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

	public boolean removeABLists(String pk, String[] abListPKs)
		throws NoSuchContactException, 
			com.liferay.portlet.addressbook.NoSuchListException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABContactHBM abContactHBM = (ABContactHBM)session.get(ABContactHBM.class,
					pk);

			if (abContactHBM == null) {
				_log.warn("No ABContact exists with the primary key " +
					pk.toString());
				throw new NoSuchContactException(
					"No ABContact exists with the primary key " +
					pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < abListPKs.length; i++) {
				com.liferay.portlet.addressbook.service.persistence.ABListHBM abListHBM =
					null;
				abListHBM = (com.liferay.portlet.addressbook.service.persistence.ABListHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABListHBM.class,
						abListPKs[i]);

				if (abListHBM == null) {
					_log.warn("No ABList exists with the primary key " +
						abListPKs[i].toString());
					throw new com.liferay.portlet.addressbook.NoSuchListException(
						"No ABList exists with the primary key " +
						abListPKs[i].toString());
				}

				if (abContactHBM.getLists().remove(abListHBM)) {
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

	public boolean removeABLists(String pk, List abLists)
		throws NoSuchContactException, 
			com.liferay.portlet.addressbook.NoSuchListException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			ABContactHBM abContactHBM = (ABContactHBM)session.get(ABContactHBM.class,
					pk);

			if (abContactHBM == null) {
				_log.warn("No ABContact exists with the primary key " +
					pk.toString());
				throw new NoSuchContactException(
					"No ABContact exists with the primary key " +
					pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < abLists.size(); i++) {
				com.liferay.portlet.addressbook.model.ABList abList = (com.liferay.portlet.addressbook.model.ABList)abLists.get(i);
				com.liferay.portlet.addressbook.service.persistence.ABListHBM abListHBM =
					(com.liferay.portlet.addressbook.service.persistence.ABListHBM)session.get(com.liferay.portlet.addressbook.service.persistence.ABListHBM.class,
						abList.getPrimaryKey());

				if (abListHBM == null) {
					_log.warn("No ABList exists with the primary key " +
						abList.getPrimaryKey().toString());
					throw new com.liferay.portlet.addressbook.NoSuchListException(
						"No ABList exists with the primary key " +
						abList.getPrimaryKey().toString());
				}

				if (abContactHBM.getLists().remove(abListHBM)) {
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

	public com.liferay.portlet.addressbook.model.ABContact findByPrimaryKey(
		String contactId) throws NoSuchContactException, SystemException {
		com.liferay.portlet.addressbook.model.ABContact abContact = ABContactPool.get(contactId);
		Session session = null;

		try {
			if (abContact == null) {
				session = openSession();

				ABContactHBM abContactHBM = (ABContactHBM)session.get(ABContactHBM.class,
						contactId);

				if (abContactHBM == null) {
					_log.warn("No ABContact exists with the primary key " +
						contactId.toString());
					throw new NoSuchContactException(
						"No ABContact exists with the primary key " +
						contactId.toString());
				}

				abContact = ABContactHBMUtil.model(abContactHBM, false);
			}

			return abContact;
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
				"FROM ABContact IN CLASS com.liferay.portlet.addressbook.service.persistence.ABContactHBM WHERE ");
			query.append("userId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("firstName ASC").append(", ");
			query.append("lastName ASC").append(", ");
			query.append("emailAddress ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				ABContactHBM abContactHBM = (ABContactHBM)itr.next();
				list.add(ABContactHBMUtil.model(abContactHBM));
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
				"FROM ABContact IN CLASS com.liferay.portlet.addressbook.service.persistence.ABContactHBM WHERE ");
			query.append("userId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("firstName ASC").append(", ");
				query.append("lastName ASC").append(", ");
				query.append("emailAddress ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				ABContactHBM abContactHBM = (ABContactHBM)itr.next();
				list.add(ABContactHBMUtil.model(abContactHBM));
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

	public com.liferay.portlet.addressbook.model.ABContact findByUserId_First(
		String userId, OrderByComparator obc)
		throws NoSuchContactException, SystemException {
		List list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No ABContact exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchContactException(msg);
		}
		else {
			return (com.liferay.portlet.addressbook.model.ABContact)list.get(0);
		}
	}

	public com.liferay.portlet.addressbook.model.ABContact findByUserId_Last(
		String userId, OrderByComparator obc)
		throws NoSuchContactException, SystemException {
		int count = countByUserId(userId);
		List list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No ABContact exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "userId=";
			msg += userId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchContactException(msg);
		}
		else {
			return (com.liferay.portlet.addressbook.model.ABContact)list.get(0);
		}
	}

	public com.liferay.portlet.addressbook.model.ABContact[] findByUserId_PrevAndNext(
		String contactId, String userId, OrderByComparator obc)
		throws NoSuchContactException, SystemException {
		com.liferay.portlet.addressbook.model.ABContact abContact = findByPrimaryKey(contactId);
		int count = countByUserId(userId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM ABContact IN CLASS com.liferay.portlet.addressbook.service.persistence.ABContactHBM WHERE ");
			query.append("userId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("firstName ASC").append(", ");
				query.append("lastName ASC").append(", ");
				query.append("emailAddress ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					abContact, ABContactHBMUtil.getInstance());
			com.liferay.portlet.addressbook.model.ABContact[] array = new com.liferay.portlet.addressbook.model.ABContact[3];
			array[0] = (com.liferay.portlet.addressbook.model.ABContact)objArray[0];
			array[1] = (com.liferay.portlet.addressbook.model.ABContact)objArray[1];
			array[2] = (com.liferay.portlet.addressbook.model.ABContact)objArray[2];

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
				"FROM ABContact IN CLASS com.liferay.portlet.addressbook.service.persistence.ABContactHBM ");
			query.append("ORDER BY ");
			query.append("firstName ASC").append(", ");
			query.append("lastName ASC").append(", ");
			query.append("emailAddress ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				ABContactHBM abContactHBM = (ABContactHBM)itr.next();
				list.add(ABContactHBMUtil.model(abContactHBM));
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
				"FROM ABContact IN CLASS com.liferay.portlet.addressbook.service.persistence.ABContactHBM WHERE ");
			query.append("userId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("firstName ASC").append(", ");
			query.append("lastName ASC").append(", ");
			query.append("emailAddress ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, userId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				ABContactHBM abContactHBM = (ABContactHBM)itr.next();
				ABContactPool.remove((String)abContactHBM.getPrimaryKey());
				session.delete(abContactHBM);
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
				"FROM ABContact IN CLASS com.liferay.portlet.addressbook.service.persistence.ABContactHBM WHERE ");
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

	private static Log _log = LogFactory.getLog(ABContactPersistence.class);
}