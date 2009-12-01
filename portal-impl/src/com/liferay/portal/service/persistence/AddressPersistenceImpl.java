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

import com.liferay.portal.NoSuchAddressException;
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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.AddressImpl;
import com.liferay.portal.model.impl.AddressModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="AddressPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AddressPersistence
 * @see       AddressUtil
 * @generated
 */
public class AddressPersistenceImpl extends BasePersistenceImpl<Address>
	implements AddressPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = AddressImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(AddressModelImpl.ENTITY_CACHE_ENABLED,
			AddressModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(AddressModelImpl.ENTITY_CACHE_ENABLED,
			AddressModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(AddressModelImpl.ENTITY_CACHE_ENABLED,
			AddressModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(AddressModelImpl.ENTITY_CACHE_ENABLED,
			AddressModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID = new FinderPath(AddressModelImpl.ENTITY_CACHE_ENABLED,
			AddressModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(AddressModelImpl.ENTITY_CACHE_ENABLED,
			AddressModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_C = new FinderPath(AddressModelImpl.ENTITY_CACHE_ENABLED,
			AddressModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_C = new FinderPath(AddressModelImpl.ENTITY_CACHE_ENABLED,
			AddressModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(AddressModelImpl.ENTITY_CACHE_ENABLED,
			AddressModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_C_C = new FinderPath(AddressModelImpl.ENTITY_CACHE_ENABLED,
			AddressModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_C_C = new FinderPath(AddressModelImpl.ENTITY_CACHE_ENABLED,
			AddressModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_C = new FinderPath(AddressModelImpl.ENTITY_CACHE_ENABLED,
			AddressModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_C_C_C_M = new FinderPath(AddressModelImpl.ENTITY_CACHE_ENABLED,
			AddressModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C_C_M",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_C_C_M = new FinderPath(AddressModelImpl.ENTITY_CACHE_ENABLED,
			AddressModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C_C_M",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_C_M = new FinderPath(AddressModelImpl.ENTITY_CACHE_ENABLED,
			AddressModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_C_C_M",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_C_C_C_P = new FinderPath(AddressModelImpl.ENTITY_CACHE_ENABLED,
			AddressModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_C_C_P = new FinderPath(AddressModelImpl.ENTITY_CACHE_ENABLED,
			AddressModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_C_P = new FinderPath(AddressModelImpl.ENTITY_CACHE_ENABLED,
			AddressModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_C_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(AddressModelImpl.ENTITY_CACHE_ENABLED,
			AddressModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AddressModelImpl.ENTITY_CACHE_ENABLED,
			AddressModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(Address address) {
		EntityCacheUtil.putResult(AddressModelImpl.ENTITY_CACHE_ENABLED,
			AddressImpl.class, address.getPrimaryKey(), address);
	}

	public void cacheResult(List<Address> addresses) {
		for (Address address : addresses) {
			if (EntityCacheUtil.getResult(
						AddressModelImpl.ENTITY_CACHE_ENABLED,
						AddressImpl.class, address.getPrimaryKey(), this) == null) {
				cacheResult(address);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(AddressImpl.class.getName());
		EntityCacheUtil.clearCache(AddressImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public Address create(long addressId) {
		Address address = new AddressImpl();

		address.setNew(true);
		address.setPrimaryKey(addressId);

		return address;
	}

	public Address remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public Address remove(long addressId)
		throws NoSuchAddressException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Address address = (Address)session.get(AddressImpl.class,
					new Long(addressId));

			if (address == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + addressId);
				}

				throw new NoSuchAddressException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					addressId);
			}

			return remove(address);
		}
		catch (NoSuchAddressException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Address remove(Address address) throws SystemException {
		for (ModelListener<Address> listener : listeners) {
			listener.onBeforeRemove(address);
		}

		address = removeImpl(address);

		for (ModelListener<Address> listener : listeners) {
			listener.onAfterRemove(address);
		}

		return address;
	}

	protected Address removeImpl(Address address) throws SystemException {
		address = toUnwrappedModel(address);

		Session session = null;

		try {
			session = openSession();

			if (address.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(AddressImpl.class,
						address.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(address);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(AddressModelImpl.ENTITY_CACHE_ENABLED,
			AddressImpl.class, address.getPrimaryKey());

		return address;
	}

	public Address updateImpl(com.liferay.portal.model.Address address,
		boolean merge) throws SystemException {
		address = toUnwrappedModel(address);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, address, merge);

			address.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(AddressModelImpl.ENTITY_CACHE_ENABLED,
			AddressImpl.class, address.getPrimaryKey(), address);

		return address;
	}

	protected Address toUnwrappedModel(Address address) {
		if (address instanceof AddressImpl) {
			return address;
		}

		AddressImpl addressImpl = new AddressImpl();

		addressImpl.setNew(address.isNew());
		addressImpl.setPrimaryKey(address.getPrimaryKey());

		addressImpl.setAddressId(address.getAddressId());
		addressImpl.setCompanyId(address.getCompanyId());
		addressImpl.setUserId(address.getUserId());
		addressImpl.setUserName(address.getUserName());
		addressImpl.setCreateDate(address.getCreateDate());
		addressImpl.setModifiedDate(address.getModifiedDate());
		addressImpl.setClassNameId(address.getClassNameId());
		addressImpl.setClassPK(address.getClassPK());
		addressImpl.setStreet1(address.getStreet1());
		addressImpl.setStreet2(address.getStreet2());
		addressImpl.setStreet3(address.getStreet3());
		addressImpl.setCity(address.getCity());
		addressImpl.setZip(address.getZip());
		addressImpl.setRegionId(address.getRegionId());
		addressImpl.setCountryId(address.getCountryId());
		addressImpl.setTypeId(address.getTypeId());
		addressImpl.setMailing(address.isMailing());
		addressImpl.setPrimary(address.isPrimary());

		return addressImpl;
	}

	public Address findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Address findByPrimaryKey(long addressId)
		throws NoSuchAddressException, SystemException {
		Address address = fetchByPrimaryKey(addressId);

		if (address == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + addressId);
			}

			throw new NoSuchAddressException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				addressId);
		}

		return address;
	}

	public Address fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Address fetchByPrimaryKey(long addressId) throws SystemException {
		Address address = (Address)EntityCacheUtil.getResult(AddressModelImpl.ENTITY_CACHE_ENABLED,
				AddressImpl.class, addressId, this);

		if (address == null) {
			Session session = null;

			try {
				session = openSession();

				address = (Address)session.get(AddressImpl.class,
						new Long(addressId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (address != null) {
					cacheResult(address);
				}

				closeSession(session);
			}
		}

		return address;
	}

	public List<Address> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<Address> list = (List<Address>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_ADDRESS_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				query.append(AddressModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Address>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Address> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<Address> findByCompanyId(long companyId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Address> list = (List<Address>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(3 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_ADDRESS_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(AddressModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<Address>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Address>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Address findByCompanyId_First(long companyId, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		List<Address> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Address findByCompanyId_Last(long companyId, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		int count = countByCompanyId(companyId);

		List<Address> list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Address[] findByCompanyId_PrevAndNext(long addressId,
		long companyId, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		Address address = findByPrimaryKey(addressId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ADDRESS_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(AddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, address);

			Address[] array = new AddressImpl[3];

			array[0] = (Address)objArray[0];
			array[1] = (Address)objArray[1];
			array[2] = (Address)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Address> findByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		List<Address> list = (List<Address>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_ADDRESS_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				query.append(AddressModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Address>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Address> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<Address> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Address> list = (List<Address>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(3 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_ADDRESS_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(AddressModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<Address>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Address>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Address findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		List<Address> list = findByUserId(userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Address findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		int count = countByUserId(userId);

		List<Address> list = findByUserId(userId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Address[] findByUserId_PrevAndNext(long addressId, long userId,
		OrderByComparator obc) throws NoSuchAddressException, SystemException {
		Address address = findByPrimaryKey(addressId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ADDRESS_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(AddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, address);

			Address[] array = new AddressImpl[3];

			array[0] = (Address)objArray[0];
			array[1] = (Address)objArray[1];
			array[2] = (Address)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Address> findByC_C(long companyId, long classNameId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId)
			};

		List<Address> list = (List<Address>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_ADDRESS_WHERE);

				query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

				query.append(AddressModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Address>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_C, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Address> findByC_C(long companyId, long classNameId, int start,
		int end) throws SystemException {
		return findByC_C(companyId, classNameId, start, end, null);
	}

	public List<Address> findByC_C(long companyId, long classNameId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Address> list = (List<Address>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(4 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(4);
				}

				query.append(_SQL_SELECT_ADDRESS_WHERE);

				query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(AddressModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				list = (List<Address>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Address>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Address findByC_C_First(long companyId, long classNameId,
		OrderByComparator obc) throws NoSuchAddressException, SystemException {
		List<Address> list = findByC_C(companyId, classNameId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Address findByC_C_Last(long companyId, long classNameId,
		OrderByComparator obc) throws NoSuchAddressException, SystemException {
		int count = countByC_C(companyId, classNameId);

		List<Address> list = findByC_C(companyId, classNameId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Address[] findByC_C_PrevAndNext(long addressId, long companyId,
		long classNameId, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		Address address = findByPrimaryKey(addressId);

		int count = countByC_C(companyId, classNameId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(4 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_ADDRESS_WHERE);

			query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(AddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(classNameId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, address);

			Address[] array = new AddressImpl[3];

			array[0] = (Address)objArray[0];
			array[1] = (Address)objArray[1];
			array[2] = (Address)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Address> findByC_C_C(long companyId, long classNameId,
		long classPK) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK)
			};

		List<Address> list = (List<Address>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_ADDRESS_WHERE);

				query.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

				query.append(AddressModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Address>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_C_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Address> findByC_C_C(long companyId, long classNameId,
		long classPK, int start, int end) throws SystemException {
		return findByC_C_C(companyId, classNameId, classPK, start, end, null);
	}

	public List<Address> findByC_C_C(long companyId, long classNameId,
		long classPK, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Address> list = (List<Address>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(5 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(5);
				}

				query.append(_SQL_SELECT_ADDRESS_WHERE);

				query.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(AddressModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = (List<Address>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Address>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_C_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Address findByC_C_C_First(long companyId, long classNameId,
		long classPK, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		List<Address> list = findByC_C_C(companyId, classNameId, classPK, 0, 1,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Address findByC_C_C_Last(long companyId, long classNameId,
		long classPK, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		int count = countByC_C_C(companyId, classNameId, classPK);

		List<Address> list = findByC_C_C(companyId, classNameId, classPK,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Address[] findByC_C_C_PrevAndNext(long addressId, long companyId,
		long classNameId, long classPK, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		Address address = findByPrimaryKey(addressId);

		int count = countByC_C_C(companyId, classNameId, classPK);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(5 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_ADDRESS_WHERE);

			query.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(AddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(classNameId);

			qPos.add(classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, address);

			Address[] array = new AddressImpl[3];

			array[0] = (Address)objArray[0];
			array[1] = (Address)objArray[1];
			array[2] = (Address)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Address> findByC_C_C_M(long companyId, long classNameId,
		long classPK, boolean mailing) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK),
				Boolean.valueOf(mailing)
			};

		List<Address> list = (List<Address>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_C_C_M,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(6);

				query.append(_SQL_SELECT_ADDRESS_WHERE);

				query.append(_FINDER_COLUMN_C_C_C_M_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_C_C_M_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_C_M_CLASSPK_2);

				query.append(_FINDER_COLUMN_C_C_C_M_MAILING_2);

				query.append(AddressModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(mailing);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Address>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_C_C_M,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Address> findByC_C_C_M(long companyId, long classNameId,
		long classPK, boolean mailing, int start, int end)
		throws SystemException {
		return findByC_C_C_M(companyId, classNameId, classPK, mailing, start,
			end, null);
	}

	public List<Address> findByC_C_C_M(long companyId, long classNameId,
		long classPK, boolean mailing, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK),
				Boolean.valueOf(mailing),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Address> list = (List<Address>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_C_C_M,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(6 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(6);
				}

				query.append(_SQL_SELECT_ADDRESS_WHERE);

				query.append(_FINDER_COLUMN_C_C_C_M_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_C_C_M_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_C_M_CLASSPK_2);

				query.append(_FINDER_COLUMN_C_C_C_M_MAILING_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(AddressModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(mailing);

				list = (List<Address>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Address>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_C_C_M,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Address findByC_C_C_M_First(long companyId, long classNameId,
		long classPK, boolean mailing, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		List<Address> list = findByC_C_C_M(companyId, classNameId, classPK,
				mailing, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", mailing=");
			msg.append(mailing);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Address findByC_C_C_M_Last(long companyId, long classNameId,
		long classPK, boolean mailing, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		int count = countByC_C_C_M(companyId, classNameId, classPK, mailing);

		List<Address> list = findByC_C_C_M(companyId, classNameId, classPK,
				mailing, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", mailing=");
			msg.append(mailing);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Address[] findByC_C_C_M_PrevAndNext(long addressId, long companyId,
		long classNameId, long classPK, boolean mailing, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		Address address = findByPrimaryKey(addressId);

		int count = countByC_C_C_M(companyId, classNameId, classPK, mailing);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(6 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_ADDRESS_WHERE);

			query.append(_FINDER_COLUMN_C_C_C_M_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_C_M_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_C_M_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_C_M_MAILING_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(AddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(classNameId);

			qPos.add(classPK);

			qPos.add(mailing);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, address);

			Address[] array = new AddressImpl[3];

			array[0] = (Address)objArray[0];
			array[1] = (Address)objArray[1];
			array[2] = (Address)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Address> findByC_C_C_P(long companyId, long classNameId,
		long classPK, boolean primary) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK),
				Boolean.valueOf(primary)
			};

		List<Address> list = (List<Address>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_C_C_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(6);

				query.append(_SQL_SELECT_ADDRESS_WHERE);

				query.append(_FINDER_COLUMN_C_C_C_P_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_C_C_P_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_C_P_CLASSPK_2);

				query.append(_FINDER_COLUMN_C_C_C_P_PRIMARY_2);

				query.append(AddressModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(primary);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Address>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_C_C_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Address> findByC_C_C_P(long companyId, long classNameId,
		long classPK, boolean primary, int start, int end)
		throws SystemException {
		return findByC_C_C_P(companyId, classNameId, classPK, primary, start,
			end, null);
	}

	public List<Address> findByC_C_C_P(long companyId, long classNameId,
		long classPK, boolean primary, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK),
				Boolean.valueOf(primary),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Address> list = (List<Address>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_C_C_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(6 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(6);
				}

				query.append(_SQL_SELECT_ADDRESS_WHERE);

				query.append(_FINDER_COLUMN_C_C_C_P_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_C_C_P_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_C_P_CLASSPK_2);

				query.append(_FINDER_COLUMN_C_C_C_P_PRIMARY_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(AddressModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(primary);

				list = (List<Address>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Address>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_C_C_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Address findByC_C_C_P_First(long companyId, long classNameId,
		long classPK, boolean primary, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		List<Address> list = findByC_C_C_P(companyId, classNameId, classPK,
				primary, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", primary=");
			msg.append(primary);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Address findByC_C_C_P_Last(long companyId, long classNameId,
		long classPK, boolean primary, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		int count = countByC_C_C_P(companyId, classNameId, classPK, primary);

		List<Address> list = findByC_C_C_P(companyId, classNameId, classPK,
				primary, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", primary=");
			msg.append(primary);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Address[] findByC_C_C_P_PrevAndNext(long addressId, long companyId,
		long classNameId, long classPK, boolean primary, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		Address address = findByPrimaryKey(addressId);

		int count = countByC_C_C_P(companyId, classNameId, classPK, primary);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(6 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_ADDRESS_WHERE);

			query.append(_FINDER_COLUMN_C_C_C_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_C_P_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_C_P_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_C_P_PRIMARY_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(AddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(classNameId);

			qPos.add(classPK);

			qPos.add(primary);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, address);

			Address[] array = new AddressImpl[3];

			array[0] = (Address)objArray[0];
			array[1] = (Address)objArray[1];
			array[2] = (Address)objArray[2];

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

	public List<Address> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Address> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Address> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Address> list = (List<Address>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (obc != null) {
					query = new StringBundler(2 +
							(obc.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_ADDRESS);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_ADDRESS.concat(AddressModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<Address>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Address>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Address>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (Address address : findByCompanyId(companyId)) {
			remove(address);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (Address address : findByUserId(userId)) {
			remove(address);
		}
	}

	public void removeByC_C(long companyId, long classNameId)
		throws SystemException {
		for (Address address : findByC_C(companyId, classNameId)) {
			remove(address);
		}
	}

	public void removeByC_C_C(long companyId, long classNameId, long classPK)
		throws SystemException {
		for (Address address : findByC_C_C(companyId, classNameId, classPK)) {
			remove(address);
		}
	}

	public void removeByC_C_C_M(long companyId, long classNameId, long classPK,
		boolean mailing) throws SystemException {
		for (Address address : findByC_C_C_M(companyId, classNameId, classPK,
				mailing)) {
			remove(address);
		}
	}

	public void removeByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary) throws SystemException {
		for (Address address : findByC_C_C_P(companyId, classNameId, classPK,
				primary)) {
			remove(address);
		}
	}

	public void removeAll() throws SystemException {
		for (Address address : findAll()) {
			remove(address);
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

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_ADDRESS_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

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

	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_ADDRESS_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_C(long companyId, long classNameId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_ADDRESS_WHERE);

				query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_C_C(long companyId, long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_ADDRESS_WHERE);

				query.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_C_C_M(long companyId, long classNameId, long classPK,
		boolean mailing) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK),
				Boolean.valueOf(mailing)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C_C_M,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_COUNT_ADDRESS_WHERE);

				query.append(_FINDER_COLUMN_C_C_C_M_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_C_C_M_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_C_M_CLASSPK_2);

				query.append(_FINDER_COLUMN_C_C_C_M_MAILING_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(mailing);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_C_M,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK),
				Boolean.valueOf(primary)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C_C_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_COUNT_ADDRESS_WHERE);

				query.append(_FINDER_COLUMN_C_C_C_P_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_C_C_P_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_C_P_CLASSPK_2);

				query.append(_FINDER_COLUMN_C_C_C_P_PRIMARY_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(primary);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_C_P,
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

				Query q = session.createQuery(_SQL_COUNT_ADDRESS);

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
						"value.object.listener.com.liferay.portal.model.Address")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Address>> listenersList = new ArrayList<ModelListener<Address>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Address>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portal.service.persistence.AccountPersistence")
	protected com.liferay.portal.service.persistence.AccountPersistence accountPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.AddressPersistence")
	protected com.liferay.portal.service.persistence.AddressPersistence addressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.BrowserTrackerPersistence")
	protected com.liferay.portal.service.persistence.BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ClassNamePersistence")
	protected com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ContactPersistence")
	protected com.liferay.portal.service.persistence.ContactPersistence contactPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CountryPersistence")
	protected com.liferay.portal.service.persistence.CountryPersistence countryPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.EmailAddressPersistence")
	protected com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPrototypePersistence")
	protected com.liferay.portal.service.persistence.LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPersistence")
	protected com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPrototypePersistence")
	protected com.liferay.portal.service.persistence.LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ListTypePersistence")
	protected com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LockPersistence")
	protected com.liferay.portal.service.persistence.LockPersistence lockPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.MembershipRequestPersistence")
	protected com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrganizationPersistence")
	protected com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupPermissionPersistence")
	protected com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupRolePersistence")
	protected com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgLaborPersistence")
	protected com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyPersistence")
	protected com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyRelPersistence")
	protected com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordTrackerPersistence")
	protected com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PermissionPersistence")
	protected com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PhonePersistence")
	protected com.liferay.portal.service.persistence.PhonePersistence phonePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PluginSettingPersistence")
	protected com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPersistence")
	protected com.liferay.portal.service.persistence.PortletPersistence portletPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletItemPersistence")
	protected com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RegionPersistence")
	protected com.liferay.portal.service.persistence.RegionPersistence regionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ReleasePersistence")
	protected com.liferay.portal.service.persistence.ReleasePersistence releasePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceActionPersistence")
	protected com.liferay.portal.service.persistence.ResourceActionPersistence resourceActionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceCodePersistence")
	protected com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePermissionPersistence")
	protected com.liferay.portal.service.persistence.ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RolePersistence")
	protected com.liferay.portal.service.persistence.RolePersistence rolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ServiceComponentPersistence")
	protected com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ShardPersistence")
	protected com.liferay.portal.service.persistence.ShardPersistence shardPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupPersistence")
	protected com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupGroupRolePersistence")
	protected com.liferay.portal.service.persistence.UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupRolePersistence")
	protected com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserIdMapperPersistence")
	protected com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPersistence")
	protected com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPathPersistence")
	protected com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebsitePersistence")
	protected com.liferay.portal.service.persistence.WebsitePersistence websitePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence")
	protected com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence")
	protected com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_ADDRESS = "SELECT address FROM Address address";
	private static final String _SQL_SELECT_ADDRESS_WHERE = "SELECT address FROM Address address WHERE ";
	private static final String _SQL_COUNT_ADDRESS = "SELECT COUNT(address) FROM Address address";
	private static final String _SQL_COUNT_ADDRESS_WHERE = "SELECT COUNT(address) FROM Address address WHERE ";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "address.companyId = ?";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "address.userId = ?";
	private static final String _FINDER_COLUMN_C_C_COMPANYID_2 = "address.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "address.classNameId = ?";
	private static final String _FINDER_COLUMN_C_C_C_COMPANYID_2 = "address.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_CLASSNAMEID_2 = "address.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 = "address.classPK = ?";
	private static final String _FINDER_COLUMN_C_C_C_M_COMPANYID_2 = "address.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_M_CLASSNAMEID_2 = "address.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_M_CLASSPK_2 = "address.classPK = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_M_MAILING_2 = "address.mailing = ?";
	private static final String _FINDER_COLUMN_C_C_C_P_COMPANYID_2 = "address.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_P_CLASSNAMEID_2 = "address.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_P_CLASSPK_2 = "address.classPK = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_P_PRIMARY_2 = "address.primary = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "address.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Address exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Address exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(AddressPersistenceImpl.class);
}