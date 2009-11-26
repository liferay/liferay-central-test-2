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

import com.liferay.portal.NoSuchCountryException;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Country;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.CountryImpl;
import com.liferay.portal.model.impl.CountryModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="CountryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CountryPersistence
 * @see       CountryUtil
 * @generated
 */
public class CountryPersistenceImpl extends BasePersistenceImpl<Country>
	implements CountryPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = CountryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FETCH_BY_NAME = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByName", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_NAME = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByName", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_A2 = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByA2", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_A2 = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByA2", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_A3 = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByA3", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_A3 = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByA3", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_ACTIVE = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByActive", new String[] { Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_ACTIVE = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByActive",
			new String[] {
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ACTIVE = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByActive", new String[] { Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(Country country) {
		EntityCacheUtil.putResult(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryImpl.class, country.getPrimaryKey(), country);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME,
			new Object[] { country.getName() }, country);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A2,
			new Object[] { country.getA2() }, country);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A3,
			new Object[] { country.getA3() }, country);
	}

	public void cacheResult(List<Country> countries) {
		for (Country country : countries) {
			if (EntityCacheUtil.getResult(
						CountryModelImpl.ENTITY_CACHE_ENABLED,
						CountryImpl.class, country.getPrimaryKey(), this) == null) {
				cacheResult(country);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(CountryImpl.class.getName());
		EntityCacheUtil.clearCache(CountryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public Country create(long countryId) {
		Country country = new CountryImpl();

		country.setNew(true);
		country.setPrimaryKey(countryId);

		return country;
	}

	public Country remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public Country remove(long countryId)
		throws NoSuchCountryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Country country = (Country)session.get(CountryImpl.class,
					new Long(countryId));

			if (country == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Country exists with the primary key " +
						countryId);
				}

				throw new NoSuchCountryException(
					"No Country exists with the primary key " + countryId);
			}

			return remove(country);
		}
		catch (NoSuchCountryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Country remove(Country country) throws SystemException {
		for (ModelListener<Country> listener : listeners) {
			listener.onBeforeRemove(country);
		}

		country = removeImpl(country);

		for (ModelListener<Country> listener : listeners) {
			listener.onAfterRemove(country);
		}

		return country;
	}

	protected Country removeImpl(Country country) throws SystemException {
		country = toUnwrappedModel(country);

		Session session = null;

		try {
			session = openSession();

			if (country.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(CountryImpl.class,
						country.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(country);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		CountryModelImpl countryModelImpl = (CountryModelImpl)country;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_NAME,
			new Object[] { countryModelImpl.getOriginalName() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_A2,
			new Object[] { countryModelImpl.getOriginalA2() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_A3,
			new Object[] { countryModelImpl.getOriginalA3() });

		EntityCacheUtil.removeResult(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryImpl.class, country.getPrimaryKey());

		return country;
	}

	public Country updateImpl(com.liferay.portal.model.Country country,
		boolean merge) throws SystemException {
		country = toUnwrappedModel(country);

		boolean isNew = country.isNew();

		CountryModelImpl countryModelImpl = (CountryModelImpl)country;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, country, merge);

			country.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(CountryModelImpl.ENTITY_CACHE_ENABLED,
			CountryImpl.class, country.getPrimaryKey(), country);

		if (!isNew &&
				(!Validator.equals(country.getName(),
					countryModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_NAME,
				new Object[] { countryModelImpl.getOriginalName() });
		}

		if (isNew ||
				(!Validator.equals(country.getName(),
					countryModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME,
				new Object[] { country.getName() }, country);
		}

		if (!isNew &&
				(!Validator.equals(country.getA2(),
					countryModelImpl.getOriginalA2()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_A2,
				new Object[] { countryModelImpl.getOriginalA2() });
		}

		if (isNew ||
				(!Validator.equals(country.getA2(),
					countryModelImpl.getOriginalA2()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A2,
				new Object[] { country.getA2() }, country);
		}

		if (!isNew &&
				(!Validator.equals(country.getA3(),
					countryModelImpl.getOriginalA3()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_A3,
				new Object[] { countryModelImpl.getOriginalA3() });
		}

		if (isNew ||
				(!Validator.equals(country.getA3(),
					countryModelImpl.getOriginalA3()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A3,
				new Object[] { country.getA3() }, country);
		}

		return country;
	}

	protected Country toUnwrappedModel(Country country) {
		if (country instanceof CountryImpl) {
			return country;
		}

		CountryImpl countryImpl = new CountryImpl();

		countryImpl.setNew(country.isNew());
		countryImpl.setPrimaryKey(country.getPrimaryKey());

		countryImpl.setCountryId(country.getCountryId());
		countryImpl.setName(country.getName());
		countryImpl.setA2(country.getA2());
		countryImpl.setA3(country.getA3());
		countryImpl.setNumber(country.getNumber());
		countryImpl.setIdd(country.getIdd());
		countryImpl.setActive(country.isActive());

		return countryImpl;
	}

	public Country findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Country findByPrimaryKey(long countryId)
		throws NoSuchCountryException, SystemException {
		Country country = fetchByPrimaryKey(countryId);

		if (country == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Country exists with the primary key " +
					countryId);
			}

			throw new NoSuchCountryException(
				"No Country exists with the primary key " + countryId);
		}

		return country;
	}

	public Country fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Country fetchByPrimaryKey(long countryId) throws SystemException {
		Country country = (Country)EntityCacheUtil.getResult(CountryModelImpl.ENTITY_CACHE_ENABLED,
				CountryImpl.class, countryId, this);

		if (country == null) {
			Session session = null;

			try {
				session = openSession();

				country = (Country)session.get(CountryImpl.class,
						new Long(countryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (country != null) {
					cacheResult(country);
				}

				closeSession(session);
			}
		}

		return country;
	}

	public Country findByName(String name)
		throws NoSuchCountryException, SystemException {
		Country country = fetchByName(name);

		if (country == null) {
			StringBundler msg = new StringBundler();

			msg.append("No Country exists with the key {");

			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCountryException(msg.toString());
		}

		return country;
	}

	public Country fetchByName(String name) throws SystemException {
		return fetchByName(name, true);
	}

	public Country fetchByName(String name, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_NAME,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_COUNTRY_WHERE);

				if (name == null) {
					query.append("country.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(country.name IS NULL OR ");
					}

					query.append("country.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ORDER BY ");

				query.append("country.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
					qPos.add(name);
				}

				List<Country> list = q.list();

				result = list;

				Country country = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME,
						finderArgs, list);
				}
				else {
					country = list.get(0);

					cacheResult(country);

					if ((country.getName() == null) ||
							!country.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME,
							finderArgs, country);
					}
				}

				return country;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME,
						finderArgs, new ArrayList<Country>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Country)result;
			}
		}
	}

	public Country findByA2(String a2)
		throws NoSuchCountryException, SystemException {
		Country country = fetchByA2(a2);

		if (country == null) {
			StringBundler msg = new StringBundler();

			msg.append("No Country exists with the key {");

			msg.append("a2=" + a2);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCountryException(msg.toString());
		}

		return country;
	}

	public Country fetchByA2(String a2) throws SystemException {
		return fetchByA2(a2, true);
	}

	public Country fetchByA2(String a2, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { a2 };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_A2,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_COUNTRY_WHERE);

				if (a2 == null) {
					query.append("country.a2 IS NULL");
				}
				else {
					if (a2.equals(StringPool.BLANK)) {
						query.append("(country.a2 IS NULL OR ");
					}

					query.append("country.a2 = ?");

					if (a2.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ORDER BY ");

				query.append("country.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (a2 != null) {
					qPos.add(a2);
				}

				List<Country> list = q.list();

				result = list;

				Country country = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A2,
						finderArgs, list);
				}
				else {
					country = list.get(0);

					cacheResult(country);

					if ((country.getA2() == null) ||
							!country.getA2().equals(a2)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A2,
							finderArgs, country);
					}
				}

				return country;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A2,
						finderArgs, new ArrayList<Country>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Country)result;
			}
		}
	}

	public Country findByA3(String a3)
		throws NoSuchCountryException, SystemException {
		Country country = fetchByA3(a3);

		if (country == null) {
			StringBundler msg = new StringBundler();

			msg.append("No Country exists with the key {");

			msg.append("a3=" + a3);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCountryException(msg.toString());
		}

		return country;
	}

	public Country fetchByA3(String a3) throws SystemException {
		return fetchByA3(a3, true);
	}

	public Country fetchByA3(String a3, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { a3 };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_A3,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_COUNTRY_WHERE);

				if (a3 == null) {
					query.append("country.a3 IS NULL");
				}
				else {
					if (a3.equals(StringPool.BLANK)) {
						query.append("(country.a3 IS NULL OR ");
					}

					query.append("country.a3 = ?");

					if (a3.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ORDER BY ");

				query.append("country.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (a3 != null) {
					qPos.add(a3);
				}

				List<Country> list = q.list();

				result = list;

				Country country = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A3,
						finderArgs, list);
				}
				else {
					country = list.get(0);

					cacheResult(country);

					if ((country.getA3() == null) ||
							!country.getA3().equals(a3)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A3,
							finderArgs, country);
					}
				}

				return country;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A3,
						finderArgs, new ArrayList<Country>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Country)result;
			}
		}
	}

	public List<Country> findByActive(boolean active) throws SystemException {
		Object[] finderArgs = new Object[] { Boolean.valueOf(active) };

		List<Country> list = (List<Country>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ACTIVE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_COUNTRY_WHERE);

				query.append("country.active = ?");

				query.append(" ORDER BY ");

				query.append("country.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Country>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ACTIVE,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Country> findByActive(boolean active, int start, int end)
		throws SystemException {
		return findByActive(active, start, end, null);
	}

	public List<Country> findByActive(boolean active, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				Boolean.valueOf(active),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Country> list = (List<Country>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_ACTIVE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_COUNTRY_WHERE);

				query.append("country.active = ?");

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("country.");
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

				else {
					query.append(" ORDER BY ");

					query.append("country.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				list = (List<Country>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Country>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_ACTIVE,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Country findByActive_First(boolean active, OrderByComparator obc)
		throws NoSuchCountryException, SystemException {
		List<Country> list = findByActive(active, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No Country exists with the key {");

			msg.append("active=" + active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCountryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Country findByActive_Last(boolean active, OrderByComparator obc)
		throws NoSuchCountryException, SystemException {
		int count = countByActive(active);

		List<Country> list = findByActive(active, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler();

			msg.append("No Country exists with the key {");

			msg.append("active=" + active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCountryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Country[] findByActive_PrevAndNext(long countryId, boolean active,
		OrderByComparator obc) throws NoSuchCountryException, SystemException {
		Country country = findByPrimaryKey(countryId);

		int count = countByActive(active);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_COUNTRY_WHERE);

			query.append("country.active = ?");

			if (obc != null) {
				query.append(" ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("country.");
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

			else {
				query.append(" ORDER BY ");

				query.append("country.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(active);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, country);

			Country[] array = new CountryImpl[3];

			array[0] = (Country)objArray[0];
			array[1] = (Country)objArray[1];
			array[2] = (Country)objArray[2];

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

	public List<Country> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Country> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Country> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Country> list = (List<Country>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_SELECT_COUNTRY);

				if (obc != null) {
					query.append(" ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("country.");
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

				else {
					query.append(" ORDER BY ");

					query.append("country.name ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<Country>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Country>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Country>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByName(String name)
		throws NoSuchCountryException, SystemException {
		Country country = findByName(name);

		remove(country);
	}

	public void removeByA2(String a2)
		throws NoSuchCountryException, SystemException {
		Country country = findByA2(a2);

		remove(country);
	}

	public void removeByA3(String a3)
		throws NoSuchCountryException, SystemException {
		Country country = findByA3(a3);

		remove(country);
	}

	public void removeByActive(boolean active) throws SystemException {
		for (Country country : findByActive(active)) {
			remove(country);
		}
	}

	public void removeAll() throws SystemException {
		for (Country country : findAll()) {
			remove(country);
		}
	}

	public int countByName(String name) throws SystemException {
		Object[] finderArgs = new Object[] { name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_NAME,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_COUNTRY_WHERE);

				if (name == null) {
					query.append("country.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(country.name IS NULL OR ");
					}

					query.append("country.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
					qPos.add(name);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_NAME,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByA2(String a2) throws SystemException {
		Object[] finderArgs = new Object[] { a2 };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_A2,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_COUNTRY_WHERE);

				if (a2 == null) {
					query.append("country.a2 IS NULL");
				}
				else {
					if (a2.equals(StringPool.BLANK)) {
						query.append("(country.a2 IS NULL OR ");
					}

					query.append("country.a2 = ?");

					if (a2.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (a2 != null) {
					qPos.add(a2);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_A2, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByA3(String a3) throws SystemException {
		Object[] finderArgs = new Object[] { a3 };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_A3,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_COUNTRY_WHERE);

				if (a3 == null) {
					query.append("country.a3 IS NULL");
				}
				else {
					if (a3.equals(StringPool.BLANK)) {
						query.append("(country.a3 IS NULL OR ");
					}

					query.append("country.a3 = ?");

					if (a3.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (a3 != null) {
					qPos.add(a3);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_A3, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByActive(boolean active) throws SystemException {
		Object[] finderArgs = new Object[] { Boolean.valueOf(active) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ACTIVE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler();

				query.append(_SQL_COUNT_COUNTRY_WHERE);

				query.append("country.active = ?");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(active);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ACTIVE,
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

				Query q = session.createQuery(_SQL_COUNT_COUNTRY);

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
						"value.object.listener.com.liferay.portal.model.Country")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Country>> listenersList = new ArrayList<ModelListener<Country>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Country>)Class.forName(
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
	private static final String _SQL_SELECT_COUNTRY = "SELECT country FROM Country country";
	private static final String _SQL_SELECT_COUNTRY_WHERE = "SELECT country FROM Country country WHERE ";
	private static final String _SQL_COUNT_COUNTRY = "SELECT COUNT(country) FROM Country country";
	private static final String _SQL_COUNT_COUNTRY_WHERE = "SELECT COUNT(country) FROM Country country WHERE ";
	private static Log _log = LogFactoryUtil.getLog(CountryPersistenceImpl.class);
}