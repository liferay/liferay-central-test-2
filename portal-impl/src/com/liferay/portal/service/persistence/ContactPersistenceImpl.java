/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.ContactImpl;
import com.liferay.portal.model.impl.ContactModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ContactPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ContactPersistence
 * @see       ContactUtil
 * @generated
 */
public class ContactPersistenceImpl extends BasePersistenceImpl<Contact>
	implements ContactPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ContactImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(ContactModelImpl.ENTITY_CACHE_ENABLED,
			ContactModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(ContactModelImpl.ENTITY_CACHE_ENABLED,
			ContactModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(ContactModelImpl.ENTITY_CACHE_ENABLED,
			ContactModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ContactModelImpl.ENTITY_CACHE_ENABLED,
			ContactModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ContactModelImpl.ENTITY_CACHE_ENABLED,
			ContactModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(Contact contact) {
		EntityCacheUtil.putResult(ContactModelImpl.ENTITY_CACHE_ENABLED,
			ContactImpl.class, contact.getPrimaryKey(), contact);
	}

	public void cacheResult(List<Contact> contacts) {
		for (Contact contact : contacts) {
			if (EntityCacheUtil.getResult(
						ContactModelImpl.ENTITY_CACHE_ENABLED,
						ContactImpl.class, contact.getPrimaryKey(), this) == null) {
				cacheResult(contact);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(ContactImpl.class.getName());
		EntityCacheUtil.clearCache(ContactImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public Contact create(long contactId) {
		Contact contact = new ContactImpl();

		contact.setNew(true);
		contact.setPrimaryKey(contactId);

		return contact;
	}

	public Contact remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public Contact remove(long contactId)
		throws NoSuchContactException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Contact contact = (Contact)session.get(ContactImpl.class,
					new Long(contactId));

			if (contact == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Contact exists with the primary key " +
						contactId);
				}

				throw new NoSuchContactException(
					"No Contact exists with the primary key " + contactId);
			}

			return remove(contact);
		}
		catch (NoSuchContactException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Contact remove(Contact contact) throws SystemException {
		for (ModelListener<Contact> listener : listeners) {
			listener.onBeforeRemove(contact);
		}

		contact = removeImpl(contact);

		for (ModelListener<Contact> listener : listeners) {
			listener.onAfterRemove(contact);
		}

		return contact;
	}

	protected Contact removeImpl(Contact contact) throws SystemException {
		contact = toUnwrappedModel(contact);

		Session session = null;

		try {
			session = openSession();

			if (contact.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ContactImpl.class,
						contact.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(contact);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(ContactModelImpl.ENTITY_CACHE_ENABLED,
			ContactImpl.class, contact.getPrimaryKey());

		return contact;
	}

	public Contact updateImpl(com.liferay.portal.model.Contact contact,
		boolean merge) throws SystemException {
		contact = toUnwrappedModel(contact);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, contact, merge);

			contact.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ContactModelImpl.ENTITY_CACHE_ENABLED,
			ContactImpl.class, contact.getPrimaryKey(), contact);

		return contact;
	}

	protected Contact toUnwrappedModel(Contact contact) {
		if (contact instanceof ContactImpl) {
			return contact;
		}

		ContactImpl contactImpl = new ContactImpl();

		contactImpl.setNew(contact.isNew());
		contactImpl.setPrimaryKey(contact.getPrimaryKey());

		contactImpl.setContactId(contact.getContactId());
		contactImpl.setCompanyId(contact.getCompanyId());
		contactImpl.setUserId(contact.getUserId());
		contactImpl.setUserName(contact.getUserName());
		contactImpl.setCreateDate(contact.getCreateDate());
		contactImpl.setModifiedDate(contact.getModifiedDate());
		contactImpl.setAccountId(contact.getAccountId());
		contactImpl.setParentContactId(contact.getParentContactId());
		contactImpl.setFirstName(contact.getFirstName());
		contactImpl.setMiddleName(contact.getMiddleName());
		contactImpl.setLastName(contact.getLastName());
		contactImpl.setPrefixId(contact.getPrefixId());
		contactImpl.setSuffixId(contact.getSuffixId());
		contactImpl.setMale(contact.isMale());
		contactImpl.setBirthday(contact.getBirthday());
		contactImpl.setSmsSn(contact.getSmsSn());
		contactImpl.setAimSn(contact.getAimSn());
		contactImpl.setFacebookSn(contact.getFacebookSn());
		contactImpl.setIcqSn(contact.getIcqSn());
		contactImpl.setJabberSn(contact.getJabberSn());
		contactImpl.setMsnSn(contact.getMsnSn());
		contactImpl.setMySpaceSn(contact.getMySpaceSn());
		contactImpl.setSkypeSn(contact.getSkypeSn());
		contactImpl.setTwitterSn(contact.getTwitterSn());
		contactImpl.setYmSn(contact.getYmSn());
		contactImpl.setEmployeeStatusId(contact.getEmployeeStatusId());
		contactImpl.setEmployeeNumber(contact.getEmployeeNumber());
		contactImpl.setJobTitle(contact.getJobTitle());
		contactImpl.setJobClass(contact.getJobClass());
		contactImpl.setHoursOfOperation(contact.getHoursOfOperation());

		return contactImpl;
	}

	public Contact findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Contact findByPrimaryKey(long contactId)
		throws NoSuchContactException, SystemException {
		Contact contact = fetchByPrimaryKey(contactId);

		if (contact == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Contact exists with the primary key " +
					contactId);
			}

			throw new NoSuchContactException(
				"No Contact exists with the primary key " + contactId);
		}

		return contact;
	}

	public Contact fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Contact fetchByPrimaryKey(long contactId) throws SystemException {
		Contact contact = (Contact)EntityCacheUtil.getResult(ContactModelImpl.ENTITY_CACHE_ENABLED,
				ContactImpl.class, contactId, this);

		if (contact == null) {
			Session session = null;

			try {
				session = openSession();

				contact = (Contact)session.get(ContactImpl.class,
						new Long(contactId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (contact != null) {
					cacheResult(contact);
				}

				closeSession(session);
			}
		}

		return contact;
	}

	public List<Contact> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<Contact> list = (List<Contact>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT contact FROM Contact contact WHERE ");

				query.append("contact.companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Contact>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Contact> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<Contact> findByCompanyId(long companyId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Contact> list = (List<Contact>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT contact FROM Contact contact WHERE ");

				query.append("contact.companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("contact.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<Contact>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Contact>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Contact findByCompanyId_First(long companyId, OrderByComparator obc)
		throws NoSuchContactException, SystemException {
		List<Contact> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Contact exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchContactException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Contact findByCompanyId_Last(long companyId, OrderByComparator obc)
		throws NoSuchContactException, SystemException {
		int count = countByCompanyId(companyId);

		List<Contact> list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Contact exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchContactException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Contact[] findByCompanyId_PrevAndNext(long contactId,
		long companyId, OrderByComparator obc)
		throws NoSuchContactException, SystemException {
		Contact contact = findByPrimaryKey(contactId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT contact FROM Contact contact WHERE ");

			query.append("contact.companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("contact.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, contact);

			Contact[] array = new ContactImpl[3];

			array[0] = (Contact)objArray[0];
			array[1] = (Contact)objArray[1];
			array[2] = (Contact)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.setLimit(start, end);

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Contact> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Contact> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Contact> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Contact> list = (List<Contact>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT contact FROM Contact contact ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("contact.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<Contact>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Contact>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Contact>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (Contact contact : findByCompanyId(companyId)) {
			remove(contact);
		}
	}

	public void removeAll() throws SystemException {
		for (Contact contact : findAll()) {
			remove(contact);
		}
	}

	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(contact) ");
				query.append("FROM Contact contact WHERE ");

				query.append("contact.companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(contact) FROM Contact contact");

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Contact")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Contact>> listenersList = new ArrayList<ModelListener<Contact>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Contact>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portal.service.persistence.AccountPersistence.impl")
	protected com.liferay.portal.service.persistence.AccountPersistence accountPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.AddressPersistence.impl")
	protected com.liferay.portal.service.persistence.AddressPersistence addressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.BrowserTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ClassNamePersistence.impl")
	protected com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence.impl")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ContactPersistence.impl")
	protected com.liferay.portal.service.persistence.ContactPersistence contactPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CountryPersistence.impl")
	protected com.liferay.portal.service.persistence.CountryPersistence countryPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.EmailAddressPersistence.impl")
	protected com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence.impl")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence.impl")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPrototypePersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPrototypePersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ListTypePersistence.impl")
	protected com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LockPersistence.impl")
	protected com.liferay.portal.service.persistence.LockPersistence lockPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.MembershipRequestPersistence.impl")
	protected com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrganizationPersistence.impl")
	protected com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupPermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgLaborPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyRelPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PhonePersistence.impl")
	protected com.liferay.portal.service.persistence.PhonePersistence phonePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PluginSettingPersistence.impl")
	protected com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPersistence portletPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletItemPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RegionPersistence.impl")
	protected com.liferay.portal.service.persistence.RegionPersistence regionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ReleasePersistence.impl")
	protected com.liferay.portal.service.persistence.ReleasePersistence releasePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceActionPersistence.impl")
	protected com.liferay.portal.service.persistence.ResourceActionPersistence resourceActionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceCodePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RolePersistence.impl")
	protected com.liferay.portal.service.persistence.RolePersistence rolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ServiceComponentPersistence.impl")
	protected com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ShardPersistence.impl")
	protected com.liferay.portal.service.persistence.ShardPersistence shardPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence.impl")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupPersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserIdMapperPersistence.impl")
	protected com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPathPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence.impl")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebsitePersistence.impl")
	protected com.liferay.portal.service.persistence.WebsitePersistence websitePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence.impl")
	protected com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence.impl")
	protected com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static Log _log = LogFactoryUtil.getLog(ContactPersistenceImpl.class);
}