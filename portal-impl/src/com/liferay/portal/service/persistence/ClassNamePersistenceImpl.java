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

import com.liferay.portal.NoSuchClassNameException;
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
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.ClassNameImpl;
import com.liferay.portal.model.impl.ClassNameModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ClassNamePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ClassNamePersistence
 * @see       ClassNameUtil
 * @generated
 */
public class ClassNamePersistenceImpl extends BasePersistenceImpl<ClassName>
	implements ClassNamePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ClassNameImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FETCH_BY_VALUE = new FinderPath(ClassNameModelImpl.ENTITY_CACHE_ENABLED,
			ClassNameModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByValue", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_VALUE = new FinderPath(ClassNameModelImpl.ENTITY_CACHE_ENABLED,
			ClassNameModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByValue", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ClassNameModelImpl.ENTITY_CACHE_ENABLED,
			ClassNameModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ClassNameModelImpl.ENTITY_CACHE_ENABLED,
			ClassNameModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(ClassName className) {
		EntityCacheUtil.putResult(ClassNameModelImpl.ENTITY_CACHE_ENABLED,
			ClassNameImpl.class, className.getPrimaryKey(), className);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VALUE,
			new Object[] { className.getValue() }, className);
	}

	public void cacheResult(List<ClassName> classNames) {
		for (ClassName className : classNames) {
			if (EntityCacheUtil.getResult(
						ClassNameModelImpl.ENTITY_CACHE_ENABLED,
						ClassNameImpl.class, className.getPrimaryKey(), this) == null) {
				cacheResult(className);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(ClassNameImpl.class.getName());
		EntityCacheUtil.clearCache(ClassNameImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public ClassName create(long classNameId) {
		ClassName className = new ClassNameImpl();

		className.setNew(true);
		className.setPrimaryKey(classNameId);

		return className;
	}

	public ClassName remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public ClassName remove(long classNameId)
		throws NoSuchClassNameException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ClassName className = (ClassName)session.get(ClassNameImpl.class,
					new Long(classNameId));

			if (className == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + classNameId);
				}

				throw new NoSuchClassNameException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					classNameId);
			}

			return remove(className);
		}
		catch (NoSuchClassNameException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ClassName remove(ClassName className) throws SystemException {
		for (ModelListener<ClassName> listener : listeners) {
			listener.onBeforeRemove(className);
		}

		className = removeImpl(className);

		for (ModelListener<ClassName> listener : listeners) {
			listener.onAfterRemove(className);
		}

		return className;
	}

	protected ClassName removeImpl(ClassName className)
		throws SystemException {
		className = toUnwrappedModel(className);

		Session session = null;

		try {
			session = openSession();

			if (className.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ClassNameImpl.class,
						className.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(className);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		ClassNameModelImpl classNameModelImpl = (ClassNameModelImpl)className;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_VALUE,
			new Object[] { classNameModelImpl.getOriginalValue() });

		EntityCacheUtil.removeResult(ClassNameModelImpl.ENTITY_CACHE_ENABLED,
			ClassNameImpl.class, className.getPrimaryKey());

		return className;
	}

	public ClassName updateImpl(com.liferay.portal.model.ClassName className,
		boolean merge) throws SystemException {
		className = toUnwrappedModel(className);

		boolean isNew = className.isNew();

		ClassNameModelImpl classNameModelImpl = (ClassNameModelImpl)className;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, className, merge);

			className.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ClassNameModelImpl.ENTITY_CACHE_ENABLED,
			ClassNameImpl.class, className.getPrimaryKey(), className);

		if (!isNew &&
				(!Validator.equals(className.getValue(),
					classNameModelImpl.getOriginalValue()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_VALUE,
				new Object[] { classNameModelImpl.getOriginalValue() });
		}

		if (isNew ||
				(!Validator.equals(className.getValue(),
					classNameModelImpl.getOriginalValue()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VALUE,
				new Object[] { className.getValue() }, className);
		}

		return className;
	}

	protected ClassName toUnwrappedModel(ClassName className) {
		if (className instanceof ClassNameImpl) {
			return className;
		}

		ClassNameImpl classNameImpl = new ClassNameImpl();

		classNameImpl.setNew(className.isNew());
		classNameImpl.setPrimaryKey(className.getPrimaryKey());

		classNameImpl.setClassNameId(className.getClassNameId());
		classNameImpl.setValue(className.getValue());

		return classNameImpl;
	}

	public ClassName findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ClassName findByPrimaryKey(long classNameId)
		throws NoSuchClassNameException, SystemException {
		ClassName className = fetchByPrimaryKey(classNameId);

		if (className == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + classNameId);
			}

			throw new NoSuchClassNameException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				classNameId);
		}

		return className;
	}

	public ClassName fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ClassName fetchByPrimaryKey(long classNameId)
		throws SystemException {
		ClassName className = (ClassName)EntityCacheUtil.getResult(ClassNameModelImpl.ENTITY_CACHE_ENABLED,
				ClassNameImpl.class, classNameId, this);

		if (className == null) {
			Session session = null;

			try {
				session = openSession();

				className = (ClassName)session.get(ClassNameImpl.class,
						new Long(classNameId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (className != null) {
					cacheResult(className);
				}

				closeSession(session);
			}
		}

		return className;
	}

	public ClassName findByValue(String value)
		throws NoSuchClassNameException, SystemException {
		ClassName className = fetchByValue(value);

		if (className == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("value=");
			msg.append(value);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchClassNameException(msg.toString());
		}

		return className;
	}

	public ClassName fetchByValue(String value) throws SystemException {
		return fetchByValue(value, true);
	}

	public ClassName fetchByValue(String value, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { value };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_VALUE,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_CLASSNAME_WHERE);

				if (value == null) {
					query.append(_FINDER_COLUMN_VALUE_VALUE_1);
				}
				else {
					if (value.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_VALUE_VALUE_3);
					}
					else {
						query.append(_FINDER_COLUMN_VALUE_VALUE_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (value != null) {
					qPos.add(value);
				}

				List<ClassName> list = q.list();

				result = list;

				ClassName className = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VALUE,
						finderArgs, list);
				}
				else {
					className = list.get(0);

					cacheResult(className);

					if ((className.getValue() == null) ||
							!className.getValue().equals(value)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VALUE,
							finderArgs, className);
					}
				}

				return className;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VALUE,
						finderArgs, new ArrayList<ClassName>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (ClassName)result;
			}
		}
	}

	public List<ClassName> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ClassName> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ClassName> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<ClassName> list = (List<ClassName>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_CLASSNAME);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				sql = _SQL_SELECT_CLASSNAME;

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<ClassName>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ClassName>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ClassName>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByValue(String value)
		throws NoSuchClassNameException, SystemException {
		ClassName className = findByValue(value);

		remove(className);
	}

	public void removeAll() throws SystemException {
		for (ClassName className : findAll()) {
			remove(className);
		}
	}

	public int countByValue(String value) throws SystemException {
		Object[] finderArgs = new Object[] { value };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_VALUE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_CLASSNAME_WHERE);

				if (value == null) {
					query.append(_FINDER_COLUMN_VALUE_VALUE_1);
				}
				else {
					if (value.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_VALUE_VALUE_3);
					}
					else {
						query.append(_FINDER_COLUMN_VALUE_VALUE_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (value != null) {
					qPos.add(value);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_VALUE,
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

				Query q = session.createQuery(_SQL_COUNT_CLASSNAME);

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
						"value.object.listener.com.liferay.portal.model.ClassName")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ClassName>> listenersList = new ArrayList<ModelListener<ClassName>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ClassName>)Class.forName(
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
	private static final String _SQL_SELECT_CLASSNAME = "SELECT className FROM ClassName className";
	private static final String _SQL_SELECT_CLASSNAME_WHERE = "SELECT className FROM ClassName className WHERE ";
	private static final String _SQL_COUNT_CLASSNAME = "SELECT COUNT(className) FROM ClassName className";
	private static final String _SQL_COUNT_CLASSNAME_WHERE = "SELECT COUNT(className) FROM ClassName className WHERE ";
	private static final String _FINDER_COLUMN_VALUE_VALUE_1 = "className.value IS NULL";
	private static final String _FINDER_COLUMN_VALUE_VALUE_2 = "className.value = ?";
	private static final String _FINDER_COLUMN_VALUE_VALUE_3 = "(className.value IS NULL OR className.value = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "className.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ClassName exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ClassName exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(ClassNamePersistenceImpl.class);
}