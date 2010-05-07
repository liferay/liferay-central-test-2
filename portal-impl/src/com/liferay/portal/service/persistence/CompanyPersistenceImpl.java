/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchCompanyException;
import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.model.impl.CompanyModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="CompanyPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CompanyPersistence
 * @see       CompanyUtil
 * @generated
 */
public class CompanyPersistenceImpl extends BasePersistenceImpl<Company>
	implements CompanyPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = CompanyImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FETCH_BY_WEBID = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByWebId", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_WEBID = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByWebId", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_VIRTUALHOST = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByVirtualHost", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_VIRTUALHOST = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByVirtualHost", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_MX = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByMx", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_MX = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByMx", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_LOGOID = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByLogoId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_LOGOID = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByLogoId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_SYSTEM = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findBySystem", new String[] { Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_SYSTEM = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findBySystem",
			new String[] {
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_SYSTEM = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countBySystem", new String[] { Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(Company company) {
		EntityCacheUtil.putResult(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyImpl.class, company.getPrimaryKey(), company);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_WEBID,
			new Object[] { company.getWebId() }, company);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
			new Object[] { company.getVirtualHost() }, company);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MX,
			new Object[] { company.getMx() }, company);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LOGOID,
			new Object[] { new Long(company.getLogoId()) }, company);
	}

	public void cacheResult(List<Company> companies) {
		for (Company company : companies) {
			if (EntityCacheUtil.getResult(
						CompanyModelImpl.ENTITY_CACHE_ENABLED,
						CompanyImpl.class, company.getPrimaryKey(), this) == null) {
				cacheResult(company);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(CompanyImpl.class.getName());
		EntityCacheUtil.clearCache(CompanyImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(Company company) {
		EntityCacheUtil.removeResult(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyImpl.class, company.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_WEBID,
			new Object[] { company.getWebId() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
			new Object[] { company.getVirtualHost() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_MX,
			new Object[] { company.getMx() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_LOGOID,
			new Object[] { new Long(company.getLogoId()) });
	}

	public Company create(long companyId) {
		Company company = new CompanyImpl();

		company.setNew(true);
		company.setPrimaryKey(companyId);

		return company;
	}

	public Company remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public Company remove(long companyId)
		throws NoSuchCompanyException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Company company = (Company)session.get(CompanyImpl.class,
					new Long(companyId));

			if (company == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + companyId);
				}

				throw new NoSuchCompanyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					companyId);
			}

			return remove(company);
		}
		catch (NoSuchCompanyException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Company remove(Company company) throws SystemException {
		for (ModelListener<Company> listener : listeners) {
			listener.onBeforeRemove(company);
		}

		company = removeImpl(company);

		for (ModelListener<Company> listener : listeners) {
			listener.onAfterRemove(company);
		}

		return company;
	}

	protected Company removeImpl(Company company) throws SystemException {
		company = toUnwrappedModel(company);

		Session session = null;

		try {
			session = openSession();

			if (company.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(CompanyImpl.class,
						company.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(company);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		CompanyModelImpl companyModelImpl = (CompanyModelImpl)company;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_WEBID,
			new Object[] { companyModelImpl.getOriginalWebId() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
			new Object[] { companyModelImpl.getOriginalVirtualHost() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_MX,
			new Object[] { companyModelImpl.getOriginalMx() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_LOGOID,
			new Object[] { new Long(companyModelImpl.getOriginalLogoId()) });

		EntityCacheUtil.removeResult(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyImpl.class, company.getPrimaryKey());

		return company;
	}

	public Company updateImpl(com.liferay.portal.model.Company company,
		boolean merge) throws SystemException {
		company = toUnwrappedModel(company);

		boolean isNew = company.isNew();

		CompanyModelImpl companyModelImpl = (CompanyModelImpl)company;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, company, merge);

			company.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(CompanyModelImpl.ENTITY_CACHE_ENABLED,
			CompanyImpl.class, company.getPrimaryKey(), company);

		if (!isNew &&
				(!Validator.equals(company.getWebId(),
					companyModelImpl.getOriginalWebId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_WEBID,
				new Object[] { companyModelImpl.getOriginalWebId() });
		}

		if (isNew ||
				(!Validator.equals(company.getWebId(),
					companyModelImpl.getOriginalWebId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_WEBID,
				new Object[] { company.getWebId() }, company);
		}

		if (!isNew &&
				(!Validator.equals(company.getVirtualHost(),
					companyModelImpl.getOriginalVirtualHost()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
				new Object[] { companyModelImpl.getOriginalVirtualHost() });
		}

		if (isNew ||
				(!Validator.equals(company.getVirtualHost(),
					companyModelImpl.getOriginalVirtualHost()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
				new Object[] { company.getVirtualHost() }, company);
		}

		if (!isNew &&
				(!Validator.equals(company.getMx(),
					companyModelImpl.getOriginalMx()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_MX,
				new Object[] { companyModelImpl.getOriginalMx() });
		}

		if (isNew ||
				(!Validator.equals(company.getMx(),
					companyModelImpl.getOriginalMx()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MX,
				new Object[] { company.getMx() }, company);
		}

		if (!isNew &&
				(company.getLogoId() != companyModelImpl.getOriginalLogoId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_LOGOID,
				new Object[] { new Long(companyModelImpl.getOriginalLogoId()) });
		}

		if (isNew ||
				(company.getLogoId() != companyModelImpl.getOriginalLogoId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LOGOID,
				new Object[] { new Long(company.getLogoId()) }, company);
		}

		return company;
	}

	protected Company toUnwrappedModel(Company company) {
		if (company instanceof CompanyImpl) {
			return company;
		}

		CompanyImpl companyImpl = new CompanyImpl();

		companyImpl.setNew(company.isNew());
		companyImpl.setPrimaryKey(company.getPrimaryKey());

		companyImpl.setCompanyId(company.getCompanyId());
		companyImpl.setAccountId(company.getAccountId());
		companyImpl.setWebId(company.getWebId());
		companyImpl.setKey(company.getKey());
		companyImpl.setVirtualHost(company.getVirtualHost());
		companyImpl.setMx(company.getMx());
		companyImpl.setHomeURL(company.getHomeURL());
		companyImpl.setLogoId(company.getLogoId());
		companyImpl.setSystem(company.isSystem());

		return companyImpl;
	}

	public Company findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Company findByPrimaryKey(long companyId)
		throws NoSuchCompanyException, SystemException {
		Company company = fetchByPrimaryKey(companyId);

		if (company == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + companyId);
			}

			throw new NoSuchCompanyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				companyId);
		}

		return company;
	}

	public Company fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Company fetchByPrimaryKey(long companyId) throws SystemException {
		Company company = (Company)EntityCacheUtil.getResult(CompanyModelImpl.ENTITY_CACHE_ENABLED,
				CompanyImpl.class, companyId, this);

		if (company == null) {
			Session session = null;

			try {
				session = openSession();

				company = (Company)session.get(CompanyImpl.class,
						new Long(companyId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (company != null) {
					cacheResult(company);
				}

				closeSession(session);
			}
		}

		return company;
	}

	public Company findByWebId(String webId)
		throws NoSuchCompanyException, SystemException {
		Company company = fetchByWebId(webId);

		if (company == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("webId=");
			msg.append(webId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCompanyException(msg.toString());
		}

		return company;
	}

	public Company fetchByWebId(String webId) throws SystemException {
		return fetchByWebId(webId, true);
	}

	public Company fetchByWebId(String webId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { webId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_WEBID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_COMPANY_WHERE);

				if (webId == null) {
					query.append(_FINDER_COLUMN_WEBID_WEBID_1);
				}
				else {
					if (webId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_WEBID_WEBID_3);
					}
					else {
						query.append(_FINDER_COLUMN_WEBID_WEBID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (webId != null) {
					qPos.add(webId);
				}

				List<Company> list = q.list();

				result = list;

				Company company = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_WEBID,
						finderArgs, list);
				}
				else {
					company = list.get(0);

					cacheResult(company);

					if ((company.getWebId() == null) ||
							!company.getWebId().equals(webId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_WEBID,
							finderArgs, company);
					}
				}

				return company;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_WEBID,
						finderArgs, new ArrayList<Company>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Company)result;
			}
		}
	}

	public Company findByVirtualHost(String virtualHost)
		throws NoSuchCompanyException, SystemException {
		Company company = fetchByVirtualHost(virtualHost);

		if (company == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("virtualHost=");
			msg.append(virtualHost);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCompanyException(msg.toString());
		}

		return company;
	}

	public Company fetchByVirtualHost(String virtualHost)
		throws SystemException {
		return fetchByVirtualHost(virtualHost, true);
	}

	public Company fetchByVirtualHost(String virtualHost,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { virtualHost };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_COMPANY_WHERE);

				if (virtualHost == null) {
					query.append(_FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_1);
				}
				else {
					if (virtualHost.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_3);
					}
					else {
						query.append(_FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (virtualHost != null) {
					qPos.add(virtualHost);
				}

				List<Company> list = q.list();

				result = list;

				Company company = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
						finderArgs, list);
				}
				else {
					company = list.get(0);

					cacheResult(company);

					if ((company.getVirtualHost() == null) ||
							!company.getVirtualHost().equals(virtualHost)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
							finderArgs, company);
					}
				}

				return company;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
						finderArgs, new ArrayList<Company>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Company)result;
			}
		}
	}

	public Company findByMx(String mx)
		throws NoSuchCompanyException, SystemException {
		Company company = fetchByMx(mx);

		if (company == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("mx=");
			msg.append(mx);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCompanyException(msg.toString());
		}

		return company;
	}

	public Company fetchByMx(String mx) throws SystemException {
		return fetchByMx(mx, true);
	}

	public Company fetchByMx(String mx, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { mx };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_MX,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_COMPANY_WHERE);

				if (mx == null) {
					query.append(_FINDER_COLUMN_MX_MX_1);
				}
				else {
					if (mx.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_MX_MX_3);
					}
					else {
						query.append(_FINDER_COLUMN_MX_MX_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (mx != null) {
					qPos.add(mx);
				}

				List<Company> list = q.list();

				result = list;

				Company company = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MX,
						finderArgs, list);
				}
				else {
					company = list.get(0);

					cacheResult(company);

					if ((company.getMx() == null) ||
							!company.getMx().equals(mx)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MX,
							finderArgs, company);
					}
				}

				return company;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MX,
						finderArgs, new ArrayList<Company>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Company)result;
			}
		}
	}

	public Company findByLogoId(long logoId)
		throws NoSuchCompanyException, SystemException {
		Company company = fetchByLogoId(logoId);

		if (company == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("logoId=");
			msg.append(logoId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCompanyException(msg.toString());
		}

		return company;
	}

	public Company fetchByLogoId(long logoId) throws SystemException {
		return fetchByLogoId(logoId, true);
	}

	public Company fetchByLogoId(long logoId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(logoId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_LOGOID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_COMPANY_WHERE);

				query.append(_FINDER_COLUMN_LOGOID_LOGOID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(logoId);

				List<Company> list = q.list();

				result = list;

				Company company = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LOGOID,
						finderArgs, list);
				}
				else {
					company = list.get(0);

					cacheResult(company);

					if ((company.getLogoId() != logoId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LOGOID,
							finderArgs, company);
					}
				}

				return company;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LOGOID,
						finderArgs, new ArrayList<Company>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Company)result;
			}
		}
	}

	public List<Company> findBySystem(boolean system) throws SystemException {
		Object[] finderArgs = new Object[] { Boolean.valueOf(system) };

		List<Company> list = (List<Company>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_SYSTEM,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_COMPANY_WHERE);

				query.append(_FINDER_COLUMN_SYSTEM_SYSTEM_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(system);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Company>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_SYSTEM,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Company> findBySystem(boolean system, int start, int end)
		throws SystemException {
		return findBySystem(system, start, end, null);
	}

	public List<Company> findBySystem(boolean system, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				Boolean.valueOf(system),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Company> list = (List<Company>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_SYSTEM,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_COMPANY_WHERE);

				query.append(_FINDER_COLUMN_SYSTEM_SYSTEM_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(system);

				list = (List<Company>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Company>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_SYSTEM,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Company findBySystem_First(boolean system,
		OrderByComparator orderByComparator)
		throws NoSuchCompanyException, SystemException {
		List<Company> list = findBySystem(system, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("system=");
			msg.append(system);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCompanyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Company findBySystem_Last(boolean system,
		OrderByComparator orderByComparator)
		throws NoSuchCompanyException, SystemException {
		int count = countBySystem(system);

		List<Company> list = findBySystem(system, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("system=");
			msg.append(system);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCompanyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Company[] findBySystem_PrevAndNext(long companyId, boolean system,
		OrderByComparator orderByComparator)
		throws NoSuchCompanyException, SystemException {
		Company company = findByPrimaryKey(companyId);

		Session session = null;

		try {
			session = openSession();

			Company[] array = new CompanyImpl[3];

			array[0] = getBySystem_PrevAndNext(session, company, system,
					orderByComparator, true);

			array[1] = company;

			array[2] = getBySystem_PrevAndNext(session, company, system,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Company getBySystem_PrevAndNext(Session session, Company company,
		boolean system, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMPANY_WHERE);

		query.append(_FINDER_COLUMN_SYSTEM_SYSTEM_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}

			query.append(WHERE_LIMIT_2);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(system);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(company);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Company> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<Company> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Company> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Company> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Company> list = (List<Company>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (orderByComparator != null) {
					query = new StringBundler(2 +
							(orderByComparator.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_COMPANY);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				sql = _SQL_SELECT_COMPANY;

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Company>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Company>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Company>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByWebId(String webId)
		throws NoSuchCompanyException, SystemException {
		Company company = findByWebId(webId);

		remove(company);
	}

	public void removeByVirtualHost(String virtualHost)
		throws NoSuchCompanyException, SystemException {
		Company company = findByVirtualHost(virtualHost);

		remove(company);
	}

	public void removeByMx(String mx)
		throws NoSuchCompanyException, SystemException {
		Company company = findByMx(mx);

		remove(company);
	}

	public void removeByLogoId(long logoId)
		throws NoSuchCompanyException, SystemException {
		Company company = findByLogoId(logoId);

		remove(company);
	}

	public void removeBySystem(boolean system) throws SystemException {
		for (Company company : findBySystem(system)) {
			remove(company);
		}
	}

	public void removeAll() throws SystemException {
		for (Company company : findAll()) {
			remove(company);
		}
	}

	public int countByWebId(String webId) throws SystemException {
		Object[] finderArgs = new Object[] { webId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_WEBID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_COMPANY_WHERE);

				if (webId == null) {
					query.append(_FINDER_COLUMN_WEBID_WEBID_1);
				}
				else {
					if (webId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_WEBID_WEBID_3);
					}
					else {
						query.append(_FINDER_COLUMN_WEBID_WEBID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (webId != null) {
					qPos.add(webId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_WEBID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByVirtualHost(String virtualHost) throws SystemException {
		Object[] finderArgs = new Object[] { virtualHost };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_VIRTUALHOST,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_COMPANY_WHERE);

				if (virtualHost == null) {
					query.append(_FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_1);
				}
				else {
					if (virtualHost.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_3);
					}
					else {
						query.append(_FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (virtualHost != null) {
					qPos.add(virtualHost);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_VIRTUALHOST,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByMx(String mx) throws SystemException {
		Object[] finderArgs = new Object[] { mx };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_MX,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_COMPANY_WHERE);

				if (mx == null) {
					query.append(_FINDER_COLUMN_MX_MX_1);
				}
				else {
					if (mx.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_MX_MX_3);
					}
					else {
						query.append(_FINDER_COLUMN_MX_MX_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (mx != null) {
					qPos.add(mx);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_MX, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByLogoId(long logoId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(logoId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_LOGOID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_COMPANY_WHERE);

				query.append(_FINDER_COLUMN_LOGOID_LOGOID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(logoId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_LOGOID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countBySystem(boolean system) throws SystemException {
		Object[] finderArgs = new Object[] { Boolean.valueOf(system) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_SYSTEM,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_COMPANY_WHERE);

				query.append(_FINDER_COLUMN_SYSTEM_SYSTEM_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(system);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_SYSTEM,
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

				Query q = session.createQuery(_SQL_COUNT_COMPANY);

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
						"value.object.listener.com.liferay.portal.model.Company")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Company>> listenersList = new ArrayList<ModelListener<Company>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Company>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = ContactPersistence.class)
	protected ContactPersistence contactPersistence;
	@BeanReference(type = CountryPersistence.class)
	protected CountryPersistence countryPersistence;
	@BeanReference(type = EmailAddressPersistence.class)
	protected EmailAddressPersistence emailAddressPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = ListTypePersistence.class)
	protected ListTypePersistence listTypePersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrgGroupPermissionPersistence.class)
	protected OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(type = OrgGroupRolePersistence.class)
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(type = OrgLaborPersistence.class)
	protected OrgLaborPersistence orgLaborPersistence;
	@BeanReference(type = PasswordPolicyPersistence.class)
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(type = PasswordPolicyRelPersistence.class)
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(type = PasswordTrackerPersistence.class)
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(type = PermissionPersistence.class)
	protected PermissionPersistence permissionPersistence;
	@BeanReference(type = PhonePersistence.class)
	protected PhonePersistence phonePersistence;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = PortletItemPersistence.class)
	protected PortletItemPersistence portletItemPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;
	@BeanReference(type = ReleasePersistence.class)
	protected ReleasePersistence releasePersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceCodePersistence.class)
	protected ResourceCodePersistence resourceCodePersistence;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(type = ShardPersistence.class)
	protected ShardPersistence shardPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = TicketPersistence.class)
	protected TicketPersistence ticketPersistence;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserIdMapperPersistence.class)
	protected UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;
	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_COMPANY = "SELECT company FROM Company company";
	private static final String _SQL_SELECT_COMPANY_WHERE = "SELECT company FROM Company company WHERE ";
	private static final String _SQL_COUNT_COMPANY = "SELECT COUNT(company) FROM Company company";
	private static final String _SQL_COUNT_COMPANY_WHERE = "SELECT COUNT(company) FROM Company company WHERE ";
	private static final String _FINDER_COLUMN_WEBID_WEBID_1 = "company.webId IS NULL";
	private static final String _FINDER_COLUMN_WEBID_WEBID_2 = "company.webId = ?";
	private static final String _FINDER_COLUMN_WEBID_WEBID_3 = "(company.webId IS NULL OR company.webId = ?)";
	private static final String _FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_1 = "company.virtualHost IS NULL";
	private static final String _FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_2 = "company.virtualHost = ?";
	private static final String _FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_3 = "(company.virtualHost IS NULL OR company.virtualHost = ?)";
	private static final String _FINDER_COLUMN_MX_MX_1 = "company.mx IS NULL";
	private static final String _FINDER_COLUMN_MX_MX_2 = "company.mx = ?";
	private static final String _FINDER_COLUMN_MX_MX_3 = "(company.mx IS NULL OR company.mx = ?)";
	private static final String _FINDER_COLUMN_LOGOID_LOGOID_2 = "company.logoId = ?";
	private static final String _FINDER_COLUMN_SYSTEM_SYSTEM_2 = "company.system = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "company.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Company exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Company exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(CompanyPersistenceImpl.class);
}