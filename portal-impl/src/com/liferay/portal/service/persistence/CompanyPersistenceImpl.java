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

import com.liferay.portal.NoSuchCompanyException;
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
import com.liferay.portal.model.Company;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.model.impl.CompanyModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="CompanyPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CompanyPersistenceImpl extends BasePersistenceImpl
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

	public Company create(long companyId) {
		Company company = new CompanyImpl();

		company.setNew(true);
		company.setPrimaryKey(companyId);

		return company;
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
					_log.warn("No Company exists with the primary key " +
						companyId);
				}

				throw new NoSuchCompanyException(
					"No Company exists with the primary key " + companyId);
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

	/**
	 * @deprecated Use <code>update(Company company, boolean merge)</code>.
	 */
	public Company update(Company company) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(Company company) method. Use update(Company company, boolean merge) instead.");
		}

		return update(company, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        company the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when company is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public Company update(Company company, boolean merge)
		throws SystemException {
		boolean isNew = company.isNew();

		for (ModelListener<Company> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(company);
			}
			else {
				listener.onBeforeUpdate(company);
			}
		}

		company = updateImpl(company, merge);

		for (ModelListener<Company> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(company);
			}
			else {
				listener.onAfterUpdate(company);
			}
		}

		return company;
	}

	public Company updateImpl(com.liferay.portal.model.Company company,
		boolean merge) throws SystemException {
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
				(!company.getWebId().equals(companyModelImpl.getOriginalWebId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_WEBID,
				new Object[] { companyModelImpl.getOriginalWebId() });
		}

		if (isNew ||
				(!company.getWebId().equals(companyModelImpl.getOriginalWebId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_WEBID,
				new Object[] { company.getWebId() }, company);
		}

		if (!isNew &&
				(!company.getVirtualHost()
							 .equals(companyModelImpl.getOriginalVirtualHost()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
				new Object[] { companyModelImpl.getOriginalVirtualHost() });
		}

		if (isNew ||
				(!company.getVirtualHost()
							 .equals(companyModelImpl.getOriginalVirtualHost()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
				new Object[] { company.getVirtualHost() }, company);
		}

		if (!isNew &&
				(!company.getMx().equals(companyModelImpl.getOriginalMx()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_MX,
				new Object[] { companyModelImpl.getOriginalMx() });
		}

		if (isNew ||
				(!company.getMx().equals(companyModelImpl.getOriginalMx()))) {
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

	public Company findByPrimaryKey(long companyId)
		throws NoSuchCompanyException, SystemException {
		Company company = fetchByPrimaryKey(companyId);

		if (company == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Company exists with the primary key " +
					companyId);
			}

			throw new NoSuchCompanyException(
				"No Company exists with the primary key " + companyId);
		}

		return company;
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
			StringBuilder msg = new StringBuilder();

			msg.append("No Company exists with the key {");

			msg.append("webId=" + webId);

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

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Company WHERE ");

				if (webId == null) {
					query.append("webId IS NULL");
				}
				else {
					query.append("webId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

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
			if (result instanceof List) {
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
			StringBuilder msg = new StringBuilder();

			msg.append("No Company exists with the key {");

			msg.append("virtualHost=" + virtualHost);

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

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Company WHERE ");

				if (virtualHost == null) {
					query.append("virtualHost IS NULL");
				}
				else {
					query.append("virtualHost = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

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
			if (result instanceof List) {
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
			StringBuilder msg = new StringBuilder();

			msg.append("No Company exists with the key {");

			msg.append("mx=" + mx);

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

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Company WHERE ");

				if (mx == null) {
					query.append("mx IS NULL");
				}
				else {
					query.append("mx = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

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
			if (result instanceof List) {
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
			StringBuilder msg = new StringBuilder();

			msg.append("No Company exists with the key {");

			msg.append("logoId=" + logoId);

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

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Company WHERE ");

				query.append("logoId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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
			if (result instanceof List) {
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

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Company WHERE ");

				query.append("system = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				Boolean.valueOf(system),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Company> list = (List<Company>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_SYSTEM,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Company WHERE ");

				query.append("system = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

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

	public Company findBySystem_First(boolean system, OrderByComparator obc)
		throws NoSuchCompanyException, SystemException {
		List<Company> list = findBySystem(system, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Company exists with the key {");

			msg.append("system=" + system);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCompanyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Company findBySystem_Last(boolean system, OrderByComparator obc)
		throws NoSuchCompanyException, SystemException {
		int count = countBySystem(system);

		List<Company> list = findBySystem(system, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Company exists with the key {");

			msg.append("system=" + system);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCompanyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Company[] findBySystem_PrevAndNext(long companyId, boolean system,
		OrderByComparator obc) throws NoSuchCompanyException, SystemException {
		Company company = findByPrimaryKey(companyId);

		int count = countBySystem(system);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.Company WHERE ");

			query.append("system = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(system);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, company);

			Company[] array = new CompanyImpl[3];

			array[0] = (Company)objArray[0];
			array[1] = (Company)objArray[1];
			array[2] = (Company)objArray[2];

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

	public List<Company> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Company> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Company> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Company> list = (List<Company>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Company ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.Company WHERE ");

				if (webId == null) {
					query.append("webId IS NULL");
				}
				else {
					query.append("webId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.Company WHERE ");

				if (virtualHost == null) {
					query.append("virtualHost IS NULL");
				}
				else {
					query.append("virtualHost = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.Company WHERE ");

				if (mx == null) {
					query.append("mx IS NULL");
				}
				else {
					query.append("mx = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.Company WHERE ");

				query.append("logoId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.Company WHERE ");

				query.append("system = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portal.model.Company");

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
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ListTypePersistence.impl")
	protected com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence;
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
	private static Log _log = LogFactoryUtil.getLog(CompanyPersistenceImpl.class);
}