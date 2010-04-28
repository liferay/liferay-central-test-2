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

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchWebDAVPropsException;
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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.WebDAVProps;
import com.liferay.portal.model.impl.WebDAVPropsImpl;
import com.liferay.portal.model.impl.WebDAVPropsModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="WebDAVPropsPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WebDAVPropsPersistence
 * @see       WebDAVPropsUtil
 * @generated
 */
public class WebDAVPropsPersistenceImpl extends BasePersistenceImpl<WebDAVProps>
	implements WebDAVPropsPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = WebDAVPropsImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C = new FinderPath(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
			WebDAVPropsModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
			WebDAVPropsModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
			WebDAVPropsModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
			WebDAVPropsModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(WebDAVProps webDAVProps) {
		EntityCacheUtil.putResult(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
			WebDAVPropsImpl.class, webDAVProps.getPrimaryKey(), webDAVProps);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				new Long(webDAVProps.getClassNameId()),
				new Long(webDAVProps.getClassPK())
			}, webDAVProps);
	}

	public void cacheResult(List<WebDAVProps> webDAVPropses) {
		for (WebDAVProps webDAVProps : webDAVPropses) {
			if (EntityCacheUtil.getResult(
						WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
						WebDAVPropsImpl.class, webDAVProps.getPrimaryKey(), this) == null) {
				cacheResult(webDAVProps);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(WebDAVPropsImpl.class.getName());
		EntityCacheUtil.clearCache(WebDAVPropsImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public WebDAVProps create(long webDavPropsId) {
		WebDAVProps webDAVProps = new WebDAVPropsImpl();

		webDAVProps.setNew(true);
		webDAVProps.setPrimaryKey(webDavPropsId);

		return webDAVProps;
	}

	public WebDAVProps remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public WebDAVProps remove(long webDavPropsId)
		throws NoSuchWebDAVPropsException, SystemException {
		Session session = null;

		try {
			session = openSession();

			WebDAVProps webDAVProps = (WebDAVProps)session.get(WebDAVPropsImpl.class,
					new Long(webDavPropsId));

			if (webDAVProps == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + webDavPropsId);
				}

				throw new NoSuchWebDAVPropsException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					webDavPropsId);
			}

			return remove(webDAVProps);
		}
		catch (NoSuchWebDAVPropsException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WebDAVProps remove(WebDAVProps webDAVProps)
		throws SystemException {
		for (ModelListener<WebDAVProps> listener : listeners) {
			listener.onBeforeRemove(webDAVProps);
		}

		webDAVProps = removeImpl(webDAVProps);

		for (ModelListener<WebDAVProps> listener : listeners) {
			listener.onAfterRemove(webDAVProps);
		}

		return webDAVProps;
	}

	protected WebDAVProps removeImpl(WebDAVProps webDAVProps)
		throws SystemException {
		webDAVProps = toUnwrappedModel(webDAVProps);

		Session session = null;

		try {
			session = openSession();

			if (webDAVProps.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(WebDAVPropsImpl.class,
						webDAVProps.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(webDAVProps);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		WebDAVPropsModelImpl webDAVPropsModelImpl = (WebDAVPropsModelImpl)webDAVProps;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				new Long(webDAVPropsModelImpl.getOriginalClassNameId()),
				new Long(webDAVPropsModelImpl.getOriginalClassPK())
			});

		EntityCacheUtil.removeResult(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
			WebDAVPropsImpl.class, webDAVProps.getPrimaryKey());

		return webDAVProps;
	}

	public WebDAVProps updateImpl(
		com.liferay.portal.model.WebDAVProps webDAVProps, boolean merge)
		throws SystemException {
		webDAVProps = toUnwrappedModel(webDAVProps);

		boolean isNew = webDAVProps.isNew();

		WebDAVPropsModelImpl webDAVPropsModelImpl = (WebDAVPropsModelImpl)webDAVProps;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, webDAVProps, merge);

			webDAVProps.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
			WebDAVPropsImpl.class, webDAVProps.getPrimaryKey(), webDAVProps);

		if (!isNew &&
				((webDAVProps.getClassNameId() != webDAVPropsModelImpl.getOriginalClassNameId()) ||
				(webDAVProps.getClassPK() != webDAVPropsModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
				new Object[] {
					new Long(webDAVPropsModelImpl.getOriginalClassNameId()),
					new Long(webDAVPropsModelImpl.getOriginalClassPK())
				});
		}

		if (isNew ||
				((webDAVProps.getClassNameId() != webDAVPropsModelImpl.getOriginalClassNameId()) ||
				(webDAVProps.getClassPK() != webDAVPropsModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
				new Object[] {
					new Long(webDAVProps.getClassNameId()),
					new Long(webDAVProps.getClassPK())
				}, webDAVProps);
		}

		return webDAVProps;
	}

	protected WebDAVProps toUnwrappedModel(WebDAVProps webDAVProps) {
		if (webDAVProps instanceof WebDAVPropsImpl) {
			return webDAVProps;
		}

		WebDAVPropsImpl webDAVPropsImpl = new WebDAVPropsImpl();

		webDAVPropsImpl.setNew(webDAVProps.isNew());
		webDAVPropsImpl.setPrimaryKey(webDAVProps.getPrimaryKey());

		webDAVPropsImpl.setWebDavPropsId(webDAVProps.getWebDavPropsId());
		webDAVPropsImpl.setCompanyId(webDAVProps.getCompanyId());
		webDAVPropsImpl.setCreateDate(webDAVProps.getCreateDate());
		webDAVPropsImpl.setModifiedDate(webDAVProps.getModifiedDate());
		webDAVPropsImpl.setClassNameId(webDAVProps.getClassNameId());
		webDAVPropsImpl.setClassPK(webDAVProps.getClassPK());
		webDAVPropsImpl.setProps(webDAVProps.getProps());

		return webDAVPropsImpl;
	}

	public WebDAVProps findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public WebDAVProps findByPrimaryKey(long webDavPropsId)
		throws NoSuchWebDAVPropsException, SystemException {
		WebDAVProps webDAVProps = fetchByPrimaryKey(webDavPropsId);

		if (webDAVProps == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + webDavPropsId);
			}

			throw new NoSuchWebDAVPropsException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				webDavPropsId);
		}

		return webDAVProps;
	}

	public WebDAVProps fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public WebDAVProps fetchByPrimaryKey(long webDavPropsId)
		throws SystemException {
		WebDAVProps webDAVProps = (WebDAVProps)EntityCacheUtil.getResult(WebDAVPropsModelImpl.ENTITY_CACHE_ENABLED,
				WebDAVPropsImpl.class, webDavPropsId, this);

		if (webDAVProps == null) {
			Session session = null;

			try {
				session = openSession();

				webDAVProps = (WebDAVProps)session.get(WebDAVPropsImpl.class,
						new Long(webDavPropsId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (webDAVProps != null) {
					cacheResult(webDAVProps);
				}

				closeSession(session);
			}
		}

		return webDAVProps;
	}

	public WebDAVProps findByC_C(long classNameId, long classPK)
		throws NoSuchWebDAVPropsException, SystemException {
		WebDAVProps webDAVProps = fetchByC_C(classNameId, classPK);

		if (webDAVProps == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchWebDAVPropsException(msg.toString());
		}

		return webDAVProps;
	}

	public WebDAVProps fetchByC_C(long classNameId, long classPK)
		throws SystemException {
		return fetchByC_C(classNameId, classPK, true);
	}

	public WebDAVProps fetchByC_C(long classNameId, long classPK,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_C,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_WEBDAVPROPS_WHERE);

				query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<WebDAVProps> list = q.list();

				result = list;

				WebDAVProps webDAVProps = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
						finderArgs, list);
				}
				else {
					webDAVProps = list.get(0);

					cacheResult(webDAVProps);

					if ((webDAVProps.getClassNameId() != classNameId) ||
							(webDAVProps.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
							finderArgs, webDAVProps);
					}
				}

				return webDAVProps;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
						finderArgs, new ArrayList<WebDAVProps>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (WebDAVProps)result;
			}
		}
	}

	public List<WebDAVProps> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<WebDAVProps> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<WebDAVProps> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<WebDAVProps> list = (List<WebDAVProps>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_WEBDAVPROPS);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				sql = _SQL_SELECT_WEBDAVPROPS;

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<WebDAVProps>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<WebDAVProps>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WebDAVProps>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByC_C(long classNameId, long classPK)
		throws NoSuchWebDAVPropsException, SystemException {
		WebDAVProps webDAVProps = findByC_C(classNameId, classPK);

		remove(webDAVProps);
	}

	public void removeAll() throws SystemException {
		for (WebDAVProps webDAVProps : findAll()) {
			remove(webDAVProps);
		}
	}

	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_WEBDAVPROPS_WHERE);

				query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C, finderArgs,
					count);

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

				Query q = session.createQuery(_SQL_COUNT_WEBDAVPROPS);

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
						"value.object.listener.com.liferay.portal.model.WebDAVProps")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<WebDAVProps>> listenersList = new ArrayList<ModelListener<WebDAVProps>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<WebDAVProps>)Class.forName(
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
	private static final String _SQL_SELECT_WEBDAVPROPS = "SELECT webDAVProps FROM WebDAVProps webDAVProps";
	private static final String _SQL_SELECT_WEBDAVPROPS_WHERE = "SELECT webDAVProps FROM WebDAVProps webDAVProps WHERE ";
	private static final String _SQL_COUNT_WEBDAVPROPS = "SELECT COUNT(webDAVProps) FROM WebDAVProps webDAVProps";
	private static final String _SQL_COUNT_WEBDAVPROPS_WHERE = "SELECT COUNT(webDAVProps) FROM WebDAVProps webDAVProps WHERE ";
	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "webDAVProps.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "webDAVProps.classPK = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "webDAVProps.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No WebDAVProps exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No WebDAVProps exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(WebDAVPropsPersistenceImpl.class);
}