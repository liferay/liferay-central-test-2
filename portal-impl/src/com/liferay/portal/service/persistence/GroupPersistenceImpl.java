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

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.GroupModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * <a href="GroupPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       GroupPersistence
 * @see       GroupUtil
 * @generated
 */
public class GroupPersistenceImpl extends BasePersistenceImpl<Group>
	implements GroupPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = GroupImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_LIVEGROUPID = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByLiveGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_LIVEGROUPID = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByLiveGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_N = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_F = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_F",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_F = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_F",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_T_A = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByT_A",
			new String[] { Integer.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_T_A = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByT_A",
			new String[] {
				Integer.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_T_A = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByT_A",
			new String[] { Integer.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C_C = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_C = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_C_L_N = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_L_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_L_N = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_L_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C_L_N = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_C_L_N",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_L_N = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_C_L_N",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(Group group) {
		EntityCacheUtil.putResult(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupImpl.class, group.getPrimaryKey(), group);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LIVEGROUPID,
			new Object[] { new Long(group.getLiveGroupId()) }, group);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] { new Long(group.getCompanyId()), group.getName() },
			group);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_F,
			new Object[] { new Long(group.getCompanyId()), group.getFriendlyURL() },
			group);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C,
			new Object[] {
				new Long(group.getCompanyId()), new Long(group.getClassNameId()),
				new Long(group.getClassPK())
			}, group);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_L_N,
			new Object[] {
				new Long(group.getCompanyId()), new Long(group.getLiveGroupId()),
				
			group.getName()
			}, group);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_L_N,
			new Object[] {
				new Long(group.getCompanyId()), new Long(group.getClassNameId()),
				new Long(group.getLiveGroupId()),
				
			group.getName()
			}, group);
	}

	public void cacheResult(List<Group> groups) {
		for (Group group : groups) {
			if (EntityCacheUtil.getResult(GroupModelImpl.ENTITY_CACHE_ENABLED,
						GroupImpl.class, group.getPrimaryKey(), this) == null) {
				cacheResult(group);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(GroupImpl.class.getName());
		EntityCacheUtil.clearCache(GroupImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public Group create(long groupId) {
		Group group = new GroupImpl();

		group.setNew(true);
		group.setPrimaryKey(groupId);

		return group;
	}

	public Group remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public Group remove(long groupId)
		throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Group group = (Group)session.get(GroupImpl.class, new Long(groupId));

			if (group == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Group exists with the primary key " +
						groupId);
				}

				throw new NoSuchGroupException(
					"No Group exists with the primary key " + groupId);
			}

			return remove(group);
		}
		catch (NoSuchGroupException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Group remove(Group group) throws SystemException {
		for (ModelListener<Group> listener : listeners) {
			listener.onBeforeRemove(group);
		}

		group = removeImpl(group);

		for (ModelListener<Group> listener : listeners) {
			listener.onAfterRemove(group);
		}

		return group;
	}

	protected Group removeImpl(Group group) throws SystemException {
		group = toUnwrappedModel(group);

		try {
			clearOrganizations.clear(group.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Orgs");
		}

		try {
			clearPermissions.clear(group.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Permissions");
		}

		try {
			clearRoles.clear(group.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Roles");
		}

		try {
			clearUserGroups.clear(group.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_UserGroups");
		}

		try {
			clearUsers.clear(group.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}

		Session session = null;

		try {
			session = openSession();

			if (group.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(GroupImpl.class,
						group.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(group);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		GroupModelImpl groupModelImpl = (GroupModelImpl)group;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_LIVEGROUPID,
			new Object[] { new Long(groupModelImpl.getOriginalLiveGroupId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] {
				new Long(groupModelImpl.getOriginalCompanyId()),
				
			groupModelImpl.getOriginalName()
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_F,
			new Object[] {
				new Long(groupModelImpl.getOriginalCompanyId()),
				
			groupModelImpl.getOriginalFriendlyURL()
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_C,
			new Object[] {
				new Long(groupModelImpl.getOriginalCompanyId()),
				new Long(groupModelImpl.getOriginalClassNameId()),
				new Long(groupModelImpl.getOriginalClassPK())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_L_N,
			new Object[] {
				new Long(groupModelImpl.getOriginalCompanyId()),
				new Long(groupModelImpl.getOriginalLiveGroupId()),
				
			groupModelImpl.getOriginalName()
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_L_N,
			new Object[] {
				new Long(groupModelImpl.getOriginalCompanyId()),
				new Long(groupModelImpl.getOriginalClassNameId()),
				new Long(groupModelImpl.getOriginalLiveGroupId()),
				
			groupModelImpl.getOriginalName()
			});

		EntityCacheUtil.removeResult(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupImpl.class, group.getPrimaryKey());

		return group;
	}

	public Group updateImpl(com.liferay.portal.model.Group group, boolean merge)
		throws SystemException {
		group = toUnwrappedModel(group);

		boolean isNew = group.isNew();

		GroupModelImpl groupModelImpl = (GroupModelImpl)group;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, group, merge);

			group.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupImpl.class, group.getPrimaryKey(), group);

		if (!isNew &&
				(group.getLiveGroupId() != groupModelImpl.getOriginalLiveGroupId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_LIVEGROUPID,
				new Object[] { new Long(groupModelImpl.getOriginalLiveGroupId()) });
		}

		if (isNew ||
				(group.getLiveGroupId() != groupModelImpl.getOriginalLiveGroupId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LIVEGROUPID,
				new Object[] { new Long(group.getLiveGroupId()) }, group);
		}

		if (!isNew &&
				((group.getCompanyId() != groupModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(group.getName(),
					groupModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N,
				new Object[] {
					new Long(groupModelImpl.getOriginalCompanyId()),
					
				groupModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((group.getCompanyId() != groupModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(group.getName(),
					groupModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
				new Object[] { new Long(group.getCompanyId()), group.getName() },
				group);
		}

		if (!isNew &&
				((group.getCompanyId() != groupModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(group.getFriendlyURL(),
					groupModelImpl.getOriginalFriendlyURL()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_F,
				new Object[] {
					new Long(groupModelImpl.getOriginalCompanyId()),
					
				groupModelImpl.getOriginalFriendlyURL()
				});
		}

		if (isNew ||
				((group.getCompanyId() != groupModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(group.getFriendlyURL(),
					groupModelImpl.getOriginalFriendlyURL()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_F,
				new Object[] {
					new Long(group.getCompanyId()),
					
				group.getFriendlyURL()
				}, group);
		}

		if (!isNew &&
				((group.getCompanyId() != groupModelImpl.getOriginalCompanyId()) ||
				(group.getClassNameId() != groupModelImpl.getOriginalClassNameId()) ||
				(group.getClassPK() != groupModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_C,
				new Object[] {
					new Long(groupModelImpl.getOriginalCompanyId()),
					new Long(groupModelImpl.getOriginalClassNameId()),
					new Long(groupModelImpl.getOriginalClassPK())
				});
		}

		if (isNew ||
				((group.getCompanyId() != groupModelImpl.getOriginalCompanyId()) ||
				(group.getClassNameId() != groupModelImpl.getOriginalClassNameId()) ||
				(group.getClassPK() != groupModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C,
				new Object[] {
					new Long(group.getCompanyId()),
					new Long(group.getClassNameId()),
					new Long(group.getClassPK())
				}, group);
		}

		if (!isNew &&
				((group.getCompanyId() != groupModelImpl.getOriginalCompanyId()) ||
				(group.getLiveGroupId() != groupModelImpl.getOriginalLiveGroupId()) ||
				!Validator.equals(group.getName(),
					groupModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_L_N,
				new Object[] {
					new Long(groupModelImpl.getOriginalCompanyId()),
					new Long(groupModelImpl.getOriginalLiveGroupId()),
					
				groupModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((group.getCompanyId() != groupModelImpl.getOriginalCompanyId()) ||
				(group.getLiveGroupId() != groupModelImpl.getOriginalLiveGroupId()) ||
				!Validator.equals(group.getName(),
					groupModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_L_N,
				new Object[] {
					new Long(group.getCompanyId()),
					new Long(group.getLiveGroupId()),
					
				group.getName()
				}, group);
		}

		if (!isNew &&
				((group.getCompanyId() != groupModelImpl.getOriginalCompanyId()) ||
				(group.getClassNameId() != groupModelImpl.getOriginalClassNameId()) ||
				(group.getLiveGroupId() != groupModelImpl.getOriginalLiveGroupId()) ||
				!Validator.equals(group.getName(),
					groupModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_L_N,
				new Object[] {
					new Long(groupModelImpl.getOriginalCompanyId()),
					new Long(groupModelImpl.getOriginalClassNameId()),
					new Long(groupModelImpl.getOriginalLiveGroupId()),
					
				groupModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((group.getCompanyId() != groupModelImpl.getOriginalCompanyId()) ||
				(group.getClassNameId() != groupModelImpl.getOriginalClassNameId()) ||
				(group.getLiveGroupId() != groupModelImpl.getOriginalLiveGroupId()) ||
				!Validator.equals(group.getName(),
					groupModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_L_N,
				new Object[] {
					new Long(group.getCompanyId()),
					new Long(group.getClassNameId()),
					new Long(group.getLiveGroupId()),
					
				group.getName()
				}, group);
		}

		return group;
	}

	protected Group toUnwrappedModel(Group group) {
		if (group instanceof GroupImpl) {
			return group;
		}

		GroupImpl groupImpl = new GroupImpl();

		groupImpl.setNew(group.isNew());
		groupImpl.setPrimaryKey(group.getPrimaryKey());

		groupImpl.setGroupId(group.getGroupId());
		groupImpl.setCompanyId(group.getCompanyId());
		groupImpl.setCreatorUserId(group.getCreatorUserId());
		groupImpl.setClassNameId(group.getClassNameId());
		groupImpl.setClassPK(group.getClassPK());
		groupImpl.setParentGroupId(group.getParentGroupId());
		groupImpl.setLiveGroupId(group.getLiveGroupId());
		groupImpl.setName(group.getName());
		groupImpl.setDescription(group.getDescription());
		groupImpl.setType(group.getType());
		groupImpl.setTypeSettings(group.getTypeSettings());
		groupImpl.setFriendlyURL(group.getFriendlyURL());
		groupImpl.setActive(group.isActive());

		return groupImpl;
	}

	public Group findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Group findByPrimaryKey(long groupId)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByPrimaryKey(groupId);

		if (group == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Group exists with the primary key " + groupId);
			}

			throw new NoSuchGroupException(
				"No Group exists with the primary key " + groupId);
		}

		return group;
	}

	public Group fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Group fetchByPrimaryKey(long groupId) throws SystemException {
		Group group = (Group)EntityCacheUtil.getResult(GroupModelImpl.ENTITY_CACHE_ENABLED,
				GroupImpl.class, groupId, this);

		if (group == null) {
			Session session = null;

			try {
				session = openSession();

				group = (Group)session.get(GroupImpl.class, new Long(groupId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (group != null) {
					cacheResult(group);
				}

				closeSession(session);
			}
		}

		return group;
	}

	public List<Group> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<Group> list = (List<Group>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);
				query.append("SELECT group_ FROM Group group_ WHERE ");

				query.append("group_.companyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("group_.name ASC");

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
					list = new ArrayList<Group>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Group> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<Group> findByCompanyId(long companyId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Group> list = (List<Group>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 3;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				if (5 > arrayCapacity) {
					arrayCapacity = 5;
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append("SELECT group_ FROM Group group_ WHERE ");

				query.append("group_.companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("group_.");
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
					query.append("ORDER BY ");

					query.append("group_.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<Group>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Group>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Group findByCompanyId_First(long companyId, OrderByComparator obc)
		throws NoSuchGroupException, SystemException {
		List<Group> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No Group exists with the key {");
			msg.append("companyId=" + companyId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchGroupException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Group findByCompanyId_Last(long companyId, OrderByComparator obc)
		throws NoSuchGroupException, SystemException {
		int count = countByCompanyId(companyId);

		List<Group> list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No Group exists with the key {");
			msg.append("companyId=" + companyId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchGroupException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Group[] findByCompanyId_PrevAndNext(long groupId, long companyId,
		OrderByComparator obc) throws NoSuchGroupException, SystemException {
		Group group = findByPrimaryKey(groupId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			int arrayCapacity = 3;

			if (obc != null) {
				arrayCapacity += (obc.getOrderByFields().length * 4);
			}

			if (5 > arrayCapacity) {
				arrayCapacity = 5;
			}

			StringBundler query = new StringBundler(arrayCapacity);
			query.append("SELECT group_ FROM Group group_ WHERE ");

			query.append("group_.companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("group_.");
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
				query.append("ORDER BY ");

				query.append("group_.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, group);

			Group[] array = new GroupImpl[3];

			array[0] = (Group)objArray[0];
			array[1] = (Group)objArray[1];
			array[2] = (Group)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Group findByLiveGroupId(long liveGroupId)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByLiveGroupId(liveGroupId);

		if (group == null) {
			StringBundler msg = new StringBundler(3);
			msg.append("No Group exists with the key {");
			msg.append("liveGroupId=" + liveGroupId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchGroupException(msg.toString());
		}

		return group;
	}

	public Group fetchByLiveGroupId(long liveGroupId) throws SystemException {
		return fetchByLiveGroupId(liveGroupId, true);
	}

	public Group fetchByLiveGroupId(long liveGroupId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(liveGroupId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_LIVEGROUPID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);
				query.append("SELECT group_ FROM Group group_ WHERE ");

				query.append("group_.liveGroupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("group_.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(liveGroupId);

				List<Group> list = q.list();

				result = list;

				Group group = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LIVEGROUPID,
						finderArgs, list);
				}
				else {
					group = list.get(0);

					cacheResult(group);

					if ((group.getLiveGroupId() != liveGroupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LIVEGROUPID,
							finderArgs, group);
					}
				}

				return group;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LIVEGROUPID,
						finderArgs, new ArrayList<Group>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Group)result;
			}
		}
	}

	public Group findByC_N(long companyId, String name)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_N(companyId, name);

		if (group == null) {
			StringBundler msg = new StringBundler(5);
			msg.append("No Group exists with the key {");
			msg.append("companyId=" + companyId);
			msg.append(", ");
			msg.append("name=" + name);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchGroupException(msg.toString());
		}

		return group;
	}

	public Group fetchByC_N(long companyId, String name)
		throws SystemException {
		return fetchByC_N(companyId, name, true);
	}

	public Group fetchByC_N(long companyId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_N,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(10);
				query.append("SELECT group_ FROM Group group_ WHERE ");

				query.append("group_.companyId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("group_.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(group_.name IS NULL OR ");
					}

					query.append("group_.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("group_.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				List<Group> list = q.list();

				result = list;

				Group group = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
						finderArgs, list);
				}
				else {
					group = list.get(0);

					cacheResult(group);

					if ((group.getCompanyId() != companyId) ||
							(group.getName() == null) ||
							!group.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
							finderArgs, group);
					}
				}

				return group;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
						finderArgs, new ArrayList<Group>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Group)result;
			}
		}
	}

	public Group findByC_F(long companyId, String friendlyURL)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_F(companyId, friendlyURL);

		if (group == null) {
			StringBundler msg = new StringBundler(5);
			msg.append("No Group exists with the key {");
			msg.append("companyId=" + companyId);
			msg.append(", ");
			msg.append("friendlyURL=" + friendlyURL);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchGroupException(msg.toString());
		}

		return group;
	}

	public Group fetchByC_F(long companyId, String friendlyURL)
		throws SystemException {
		return fetchByC_F(companyId, friendlyURL, true);
	}

	public Group fetchByC_F(long companyId, String friendlyURL,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), friendlyURL };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_F,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(10);
				query.append("SELECT group_ FROM Group group_ WHERE ");

				query.append("group_.companyId = ?");

				query.append(" AND ");

				if (friendlyURL == null) {
					query.append("group_.friendlyURL IS NULL");
				}
				else {
					if (friendlyURL.equals(StringPool.BLANK)) {
						query.append("(group_.friendlyURL IS NULL OR ");
					}

					query.append("group_.friendlyURL = ?");

					if (friendlyURL.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("group_.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (friendlyURL != null) {
					qPos.add(friendlyURL);
				}

				List<Group> list = q.list();

				result = list;

				Group group = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_F,
						finderArgs, list);
				}
				else {
					group = list.get(0);

					cacheResult(group);

					if ((group.getCompanyId() != companyId) ||
							(group.getFriendlyURL() == null) ||
							!group.getFriendlyURL().equals(friendlyURL)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_F,
							finderArgs, group);
					}
				}

				return group;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_F,
						finderArgs, new ArrayList<Group>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Group)result;
			}
		}
	}

	public List<Group> findByT_A(int type, boolean active)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Integer(type), Boolean.valueOf(active)
			};

		List<Group> list = (List<Group>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_T_A,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(7);
				query.append("SELECT group_ FROM Group group_ WHERE ");

				query.append("group_.type = ?");

				query.append(" AND ");

				query.append("group_.active = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("group_.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

				qPos.add(active);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Group>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_T_A, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Group> findByT_A(int type, boolean active, int start, int end)
		throws SystemException {
		return findByT_A(type, active, start, end, null);
	}

	public List<Group> findByT_A(int type, boolean active, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Integer(type), Boolean.valueOf(active),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Group> list = (List<Group>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_T_A,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 5;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				if (7 > arrayCapacity) {
					arrayCapacity = 7;
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append("SELECT group_ FROM Group group_ WHERE ");

				query.append("group_.type = ?");

				query.append(" AND ");

				query.append("group_.active = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("group_.");
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
					query.append("ORDER BY ");

					query.append("group_.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

				qPos.add(active);

				list = (List<Group>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Group>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_T_A,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Group findByT_A_First(int type, boolean active, OrderByComparator obc)
		throws NoSuchGroupException, SystemException {
		List<Group> list = findByT_A(type, active, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(5);
			msg.append("No Group exists with the key {");
			msg.append("type=" + type);
			msg.append(", ");
			msg.append("active=" + active);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchGroupException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Group findByT_A_Last(int type, boolean active, OrderByComparator obc)
		throws NoSuchGroupException, SystemException {
		int count = countByT_A(type, active);

		List<Group> list = findByT_A(type, active, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(5);
			msg.append("No Group exists with the key {");
			msg.append("type=" + type);
			msg.append(", ");
			msg.append("active=" + active);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchGroupException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Group[] findByT_A_PrevAndNext(long groupId, int type,
		boolean active, OrderByComparator obc)
		throws NoSuchGroupException, SystemException {
		Group group = findByPrimaryKey(groupId);

		int count = countByT_A(type, active);

		Session session = null;

		try {
			session = openSession();

			int arrayCapacity = 5;

			if (obc != null) {
				arrayCapacity += (obc.getOrderByFields().length * 4);
			}

			if (7 > arrayCapacity) {
				arrayCapacity = 7;
			}

			StringBundler query = new StringBundler(arrayCapacity);
			query.append("SELECT group_ FROM Group group_ WHERE ");

			query.append("group_.type = ?");

			query.append(" AND ");

			query.append("group_.active = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("group_.");
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
				query.append("ORDER BY ");

				query.append("group_.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(type);

			qPos.add(active);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, group);

			Group[] array = new GroupImpl[3];

			array[0] = (Group)objArray[0];
			array[1] = (Group)objArray[1];
			array[2] = (Group)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Group findByC_C_C(long companyId, long classNameId, long classPK)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_C_C(companyId, classNameId, classPK);

		if (group == null) {
			StringBundler msg = new StringBundler(7);
			msg.append("No Group exists with the key {");
			msg.append("companyId=" + companyId);
			msg.append(", ");
			msg.append("classNameId=" + classNameId);
			msg.append(", ");
			msg.append("classPK=" + classPK);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchGroupException(msg.toString());
		}

		return group;
	}

	public Group fetchByC_C_C(long companyId, long classNameId, long classPK)
		throws SystemException {
		return fetchByC_C_C(companyId, classNameId, classPK, true);
	}

	public Group fetchByC_C_C(long companyId, long classNameId, long classPK,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_C_C,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(9);
				query.append("SELECT group_ FROM Group group_ WHERE ");

				query.append("group_.companyId = ?");

				query.append(" AND ");

				query.append("group_.classNameId = ?");

				query.append(" AND ");

				query.append("group_.classPK = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("group_.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<Group> list = q.list();

				result = list;

				Group group = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C,
						finderArgs, list);
				}
				else {
					group = list.get(0);

					cacheResult(group);

					if ((group.getCompanyId() != companyId) ||
							(group.getClassNameId() != classNameId) ||
							(group.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C,
							finderArgs, group);
					}
				}

				return group;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C,
						finderArgs, new ArrayList<Group>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Group)result;
			}
		}
	}

	public Group findByC_L_N(long companyId, long liveGroupId, String name)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_L_N(companyId, liveGroupId, name);

		if (group == null) {
			StringBundler msg = new StringBundler(7);
			msg.append("No Group exists with the key {");
			msg.append("companyId=" + companyId);
			msg.append(", ");
			msg.append("liveGroupId=" + liveGroupId);
			msg.append(", ");
			msg.append("name=" + name);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchGroupException(msg.toString());
		}

		return group;
	}

	public Group fetchByC_L_N(long companyId, long liveGroupId, String name)
		throws SystemException {
		return fetchByC_L_N(companyId, liveGroupId, name, true);
	}

	public Group fetchByC_L_N(long companyId, long liveGroupId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(liveGroupId),
				
				name
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_L_N,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(12);
				query.append("SELECT group_ FROM Group group_ WHERE ");

				query.append("group_.companyId = ?");

				query.append(" AND ");

				query.append("group_.liveGroupId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("group_.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(group_.name IS NULL OR ");
					}

					query.append("group_.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("group_.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(liveGroupId);

				if (name != null) {
					qPos.add(name);
				}

				List<Group> list = q.list();

				result = list;

				Group group = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_L_N,
						finderArgs, list);
				}
				else {
					group = list.get(0);

					cacheResult(group);

					if ((group.getCompanyId() != companyId) ||
							(group.getLiveGroupId() != liveGroupId) ||
							(group.getName() == null) ||
							!group.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_L_N,
							finderArgs, group);
					}
				}

				return group;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_L_N,
						finderArgs, new ArrayList<Group>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Group)result;
			}
		}
	}

	public Group findByC_C_L_N(long companyId, long classNameId,
		long liveGroupId, String name)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_C_L_N(companyId, classNameId, liveGroupId, name);

		if (group == null) {
			StringBundler msg = new StringBundler(9);
			msg.append("No Group exists with the key {");
			msg.append("companyId=" + companyId);
			msg.append(", ");
			msg.append("classNameId=" + classNameId);
			msg.append(", ");
			msg.append("liveGroupId=" + liveGroupId);
			msg.append(", ");
			msg.append("name=" + name);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchGroupException(msg.toString());
		}

		return group;
	}

	public Group fetchByC_C_L_N(long companyId, long classNameId,
		long liveGroupId, String name) throws SystemException {
		return fetchByC_C_L_N(companyId, classNameId, liveGroupId, name, true);
	}

	public Group fetchByC_C_L_N(long companyId, long classNameId,
		long liveGroupId, String name, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId),
				new Long(liveGroupId),
				
				name
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_C_L_N,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(14);
				query.append("SELECT group_ FROM Group group_ WHERE ");

				query.append("group_.companyId = ?");

				query.append(" AND ");

				query.append("group_.classNameId = ?");

				query.append(" AND ");

				query.append("group_.liveGroupId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("group_.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(group_.name IS NULL OR ");
					}

					query.append("group_.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("group_.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(liveGroupId);

				if (name != null) {
					qPos.add(name);
				}

				List<Group> list = q.list();

				result = list;

				Group group = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_L_N,
						finderArgs, list);
				}
				else {
					group = list.get(0);

					cacheResult(group);

					if ((group.getCompanyId() != companyId) ||
							(group.getClassNameId() != classNameId) ||
							(group.getLiveGroupId() != liveGroupId) ||
							(group.getName() == null) ||
							!group.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_L_N,
							finderArgs, group);
					}
				}

				return group;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_L_N,
						finderArgs, new ArrayList<Group>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Group)result;
			}
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

	public List<Group> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Group> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Group> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Group> list = (List<Group>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 1;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				if (3 > arrayCapacity) {
					arrayCapacity = 3;
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append("SELECT group_ FROM Group group_ ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("group_.");
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
					query.append("ORDER BY ");

					query.append("group_.name ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Group>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (Group group : findByCompanyId(companyId)) {
			remove(group);
		}
	}

	public void removeByLiveGroupId(long liveGroupId)
		throws NoSuchGroupException, SystemException {
		Group group = findByLiveGroupId(liveGroupId);

		remove(group);
	}

	public void removeByC_N(long companyId, String name)
		throws NoSuchGroupException, SystemException {
		Group group = findByC_N(companyId, name);

		remove(group);
	}

	public void removeByC_F(long companyId, String friendlyURL)
		throws NoSuchGroupException, SystemException {
		Group group = findByC_F(companyId, friendlyURL);

		remove(group);
	}

	public void removeByT_A(int type, boolean active) throws SystemException {
		for (Group group : findByT_A(type, active)) {
			remove(group);
		}
	}

	public void removeByC_C_C(long companyId, long classNameId, long classPK)
		throws NoSuchGroupException, SystemException {
		Group group = findByC_C_C(companyId, classNameId, classPK);

		remove(group);
	}

	public void removeByC_L_N(long companyId, long liveGroupId, String name)
		throws NoSuchGroupException, SystemException {
		Group group = findByC_L_N(companyId, liveGroupId, name);

		remove(group);
	}

	public void removeByC_C_L_N(long companyId, long classNameId,
		long liveGroupId, String name)
		throws NoSuchGroupException, SystemException {
		Group group = findByC_C_L_N(companyId, classNameId, liveGroupId, name);

		remove(group);
	}

	public void removeAll() throws SystemException {
		for (Group group : findAll()) {
			remove(group);
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

				StringBundler query = new StringBundler(4);
				query.append("SELECT COUNT(group_) ");
				query.append("FROM Group group_ WHERE ");

				query.append("group_.companyId = ?");

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

	public int countByLiveGroupId(long liveGroupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(liveGroupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_LIVEGROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);
				query.append("SELECT COUNT(group_) ");
				query.append("FROM Group group_ WHERE ");

				query.append("group_.liveGroupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(liveGroupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_LIVEGROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_N(long companyId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_N,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(9);
				query.append("SELECT COUNT(group_) ");
				query.append("FROM Group group_ WHERE ");

				query.append("group_.companyId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("group_.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(group_.name IS NULL OR ");
					}

					query.append("group_.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_F(long companyId, String friendlyURL)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), friendlyURL };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_F,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(9);
				query.append("SELECT COUNT(group_) ");
				query.append("FROM Group group_ WHERE ");

				query.append("group_.companyId = ?");

				query.append(" AND ");

				if (friendlyURL == null) {
					query.append("group_.friendlyURL IS NULL");
				}
				else {
					if (friendlyURL.equals(StringPool.BLANK)) {
						query.append("(group_.friendlyURL IS NULL OR ");
					}

					query.append("group_.friendlyURL = ?");

					if (friendlyURL.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (friendlyURL != null) {
					qPos.add(friendlyURL);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_F, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByT_A(int type, boolean active) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Integer(type), Boolean.valueOf(active)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_A,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(6);
				query.append("SELECT COUNT(group_) ");
				query.append("FROM Group group_ WHERE ");

				query.append("group_.type = ?");

				query.append(" AND ");

				query.append("group_.active = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_A, finderArgs,
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

				StringBundler query = new StringBundler(8);
				query.append("SELECT COUNT(group_) ");
				query.append("FROM Group group_ WHERE ");

				query.append("group_.companyId = ?");

				query.append(" AND ");

				query.append("group_.classNameId = ?");

				query.append(" AND ");

				query.append("group_.classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

	public int countByC_L_N(long companyId, long liveGroupId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(liveGroupId),
				
				name
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_L_N,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(11);
				query.append("SELECT COUNT(group_) ");
				query.append("FROM Group group_ WHERE ");

				query.append("group_.companyId = ?");

				query.append(" AND ");

				query.append("group_.liveGroupId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("group_.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(group_.name IS NULL OR ");
					}

					query.append("group_.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(liveGroupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_L_N,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_C_L_N(long companyId, long classNameId,
		long liveGroupId, String name) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId),
				new Long(liveGroupId),
				
				name
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C_L_N,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(13);
				query.append("SELECT COUNT(group_) ");
				query.append("FROM Group group_ WHERE ");

				query.append("group_.companyId = ?");

				query.append(" AND ");

				query.append("group_.classNameId = ?");

				query.append(" AND ");

				query.append("group_.liveGroupId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("group_.name IS NULL");
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append("(group_.name IS NULL OR ");
					}

					query.append("group_.name = ?");

					if (name.equals(StringPool.BLANK)) {
						query.append(")");
					}
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(liveGroupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_L_N,
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
						"SELECT COUNT(group_) FROM Group group_");

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

	public List<com.liferay.portal.model.Organization> getOrganizations(long pk)
		throws SystemException {
		return getOrganizations(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end) throws SystemException {
		return getOrganizations(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_ORGANIZATIONS = new FinderPath(com.liferay.portal.model.impl.OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_ORGS, "Groups_Orgs",
			"getOrganizations",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		List<com.liferay.portal.model.Organization> list = (List<com.liferay.portal.model.Organization>)FinderCacheUtil.getResult(FINDER_PATH_GET_ORGANIZATIONS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 1;

				if (obc != null) {
					arrayCapacity += 2;
				}

				if (3 > arrayCapacity) {
					arrayCapacity = 3;
				}

				StringBundler sb = new StringBundler(arrayCapacity);
				sb.append(_SQL_GETORGANIZATIONS);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				else {
					sb.append("ORDER BY ");

					sb.append("Organization_.name ASC");
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Organization_",
					com.liferay.portal.model.impl.OrganizationImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.Organization>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portal.model.Organization>();
				}

				organizationPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_ORGANIZATIONS,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_ORGANIZATIONS_SIZE = new FinderPath(com.liferay.portal.model.impl.OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_ORGS, "Groups_Orgs",
			"getOrganizationsSize", new String[] { Long.class.getName() });

	public int getOrganizationsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_ORGANIZATIONS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETORGANIZATIONSSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_ORGANIZATIONS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_ORGANIZATION = new FinderPath(com.liferay.portal.model.impl.OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_ORGS, "Groups_Orgs",
			"containsOrganization",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsOrganization(long pk, long organizationPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk),
				
				new Long(organizationPK)
			};

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_ORGANIZATION,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsOrganization.contains(pk,
							organizationPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_ORGANIZATION,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsOrganizations(long pk) throws SystemException {
		if (getOrganizationsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addOrganization(long pk, long organizationPK)
		throws SystemException {
		try {
			addOrganization.add(pk, organizationPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Orgs");
		}
	}

	public void addOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws SystemException {
		try {
			addOrganization.add(pk, organization.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Orgs");
		}
	}

	public void addOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		try {
			for (long organizationPK : organizationPKs) {
				addOrganization.add(pk, organizationPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Orgs");
		}
	}

	public void addOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Organization organization : organizations) {
				addOrganization.add(pk, organization.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Orgs");
		}
	}

	public void clearOrganizations(long pk) throws SystemException {
		try {
			clearOrganizations.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Orgs");
		}
	}

	public void removeOrganization(long pk, long organizationPK)
		throws SystemException {
		try {
			removeOrganization.remove(pk, organizationPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Orgs");
		}
	}

	public void removeOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws SystemException {
		try {
			removeOrganization.remove(pk, organization.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Orgs");
		}
	}

	public void removeOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		try {
			for (long organizationPK : organizationPKs) {
				removeOrganization.remove(pk, organizationPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Orgs");
		}
	}

	public void removeOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Organization organization : organizations) {
				removeOrganization.remove(pk, organization.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Orgs");
		}
	}

	public void setOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		try {
			Set<Long> organizationPKSet = SetUtil.fromArray(organizationPKs);

			List<com.liferay.portal.model.Organization> organizations = getOrganizations(pk);

			for (com.liferay.portal.model.Organization organization : organizations) {
				if (!organizationPKSet.contains(organization.getPrimaryKey())) {
					removeOrganization.remove(pk, organization.getPrimaryKey());
				}
				else {
					organizationPKSet.remove(organization.getPrimaryKey());
				}
			}

			for (Long organizationPK : organizationPKSet) {
				addOrganization.add(pk, organizationPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Orgs");
		}
	}

	public void setOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations)
		throws SystemException {
		try {
			long[] organizationPKs = new long[organizations.size()];

			for (int i = 0; i < organizations.size(); i++) {
				com.liferay.portal.model.Organization organization = organizations.get(i);

				organizationPKs[i] = organization.getPrimaryKey();
			}

			setOrganizations(pk, organizationPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Orgs");
		}
	}

	public List<com.liferay.portal.model.Permission> getPermissions(long pk)
		throws SystemException {
		return getPermissions(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.Permission> getPermissions(long pk,
		int start, int end) throws SystemException {
		return getPermissions(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_PERMISSIONS = new FinderPath(com.liferay.portal.model.impl.PermissionModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_PERMISSIONS,
			"Groups_Permissions", "getPermissions",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portal.model.Permission> getPermissions(long pk,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		List<com.liferay.portal.model.Permission> list = (List<com.liferay.portal.model.Permission>)FinderCacheUtil.getResult(FINDER_PATH_GET_PERMISSIONS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 1;

				if (obc != null) {
					arrayCapacity += 2;
				}

				StringBundler sb = new StringBundler(arrayCapacity);
				sb.append(_SQL_GETPERMISSIONS);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Permission_",
					com.liferay.portal.model.impl.PermissionImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.Permission>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portal.model.Permission>();
				}

				permissionPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_PERMISSIONS,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_PERMISSIONS_SIZE = new FinderPath(com.liferay.portal.model.impl.PermissionModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_PERMISSIONS,
			"Groups_Permissions", "getPermissionsSize",
			new String[] { Long.class.getName() });

	public int getPermissionsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_PERMISSIONS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETPERMISSIONSSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_PERMISSIONS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_PERMISSION = new FinderPath(com.liferay.portal.model.impl.PermissionModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_PERMISSIONS,
			"Groups_Permissions", "containsPermission",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsPermission(long pk, long permissionPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk), new Long(permissionPK) };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_PERMISSION,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsPermission.contains(pk,
							permissionPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_PERMISSION,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsPermissions(long pk) throws SystemException {
		if (getPermissionsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addPermission(long pk, long permissionPK)
		throws SystemException {
		try {
			addPermission.add(pk, permissionPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Permissions");
		}
	}

	public void addPermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws SystemException {
		try {
			addPermission.add(pk, permission.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Permissions");
		}
	}

	public void addPermissions(long pk, long[] permissionPKs)
		throws SystemException {
		try {
			for (long permissionPK : permissionPKs) {
				addPermission.add(pk, permissionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Permissions");
		}
	}

	public void addPermissions(long pk,
		List<com.liferay.portal.model.Permission> permissions)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Permission permission : permissions) {
				addPermission.add(pk, permission.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Permissions");
		}
	}

	public void clearPermissions(long pk) throws SystemException {
		try {
			clearPermissions.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Permissions");
		}
	}

	public void removePermission(long pk, long permissionPK)
		throws SystemException {
		try {
			removePermission.remove(pk, permissionPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Permissions");
		}
	}

	public void removePermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws SystemException {
		try {
			removePermission.remove(pk, permission.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Permissions");
		}
	}

	public void removePermissions(long pk, long[] permissionPKs)
		throws SystemException {
		try {
			for (long permissionPK : permissionPKs) {
				removePermission.remove(pk, permissionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Permissions");
		}
	}

	public void removePermissions(long pk,
		List<com.liferay.portal.model.Permission> permissions)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Permission permission : permissions) {
				removePermission.remove(pk, permission.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Permissions");
		}
	}

	public void setPermissions(long pk, long[] permissionPKs)
		throws SystemException {
		try {
			Set<Long> permissionPKSet = SetUtil.fromArray(permissionPKs);

			List<com.liferay.portal.model.Permission> permissions = getPermissions(pk);

			for (com.liferay.portal.model.Permission permission : permissions) {
				if (!permissionPKSet.contains(permission.getPrimaryKey())) {
					removePermission.remove(pk, permission.getPrimaryKey());
				}
				else {
					permissionPKSet.remove(permission.getPrimaryKey());
				}
			}

			for (Long permissionPK : permissionPKSet) {
				addPermission.add(pk, permissionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Permissions");
		}
	}

	public void setPermissions(long pk,
		List<com.liferay.portal.model.Permission> permissions)
		throws SystemException {
		try {
			long[] permissionPKs = new long[permissions.size()];

			for (int i = 0; i < permissions.size(); i++) {
				com.liferay.portal.model.Permission permission = permissions.get(i);

				permissionPKs[i] = permission.getPrimaryKey();
			}

			setPermissions(pk, permissionPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Permissions");
		}
	}

	public List<com.liferay.portal.model.Role> getRoles(long pk)
		throws SystemException {
		return getRoles(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.Role> getRoles(long pk, int start,
		int end) throws SystemException {
		return getRoles(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_ROLES = new FinderPath(com.liferay.portal.model.impl.RoleModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_ROLES, "Groups_Roles",
			"getRoles",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portal.model.Role> getRoles(long pk, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		List<com.liferay.portal.model.Role> list = (List<com.liferay.portal.model.Role>)FinderCacheUtil.getResult(FINDER_PATH_GET_ROLES,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 1;

				if (obc != null) {
					arrayCapacity += 2;
				}

				if (3 > arrayCapacity) {
					arrayCapacity = 3;
				}

				StringBundler sb = new StringBundler(arrayCapacity);
				sb.append(_SQL_GETROLES);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				else {
					sb.append("ORDER BY ");

					sb.append("Role_.name ASC");
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Role_",
					com.liferay.portal.model.impl.RoleImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.Role>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portal.model.Role>();
				}

				rolePersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_ROLES, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_ROLES_SIZE = new FinderPath(com.liferay.portal.model.impl.RoleModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_ROLES, "Groups_Roles",
			"getRolesSize", new String[] { Long.class.getName() });

	public int getRolesSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_ROLES_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETROLESSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_ROLES_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_ROLE = new FinderPath(com.liferay.portal.model.impl.RoleModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_ROLES, "Groups_Roles",
			"containsRole",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsRole(long pk, long rolePK) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk), new Long(rolePK) };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_ROLE,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsRole.contains(pk, rolePK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_ROLE,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsRoles(long pk) throws SystemException {
		if (getRolesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addRole(long pk, long rolePK) throws SystemException {
		try {
			addRole.add(pk, rolePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Roles");
		}
	}

	public void addRole(long pk, com.liferay.portal.model.Role role)
		throws SystemException {
		try {
			addRole.add(pk, role.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Roles");
		}
	}

	public void addRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			for (long rolePK : rolePKs) {
				addRole.add(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Roles");
		}
	}

	public void addRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Role role : roles) {
				addRole.add(pk, role.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Roles");
		}
	}

	public void clearRoles(long pk) throws SystemException {
		try {
			clearRoles.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Roles");
		}
	}

	public void removeRole(long pk, long rolePK) throws SystemException {
		try {
			removeRole.remove(pk, rolePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Roles");
		}
	}

	public void removeRole(long pk, com.liferay.portal.model.Role role)
		throws SystemException {
		try {
			removeRole.remove(pk, role.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Roles");
		}
	}

	public void removeRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			for (long rolePK : rolePKs) {
				removeRole.remove(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Roles");
		}
	}

	public void removeRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Role role : roles) {
				removeRole.remove(pk, role.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Roles");
		}
	}

	public void setRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			Set<Long> rolePKSet = SetUtil.fromArray(rolePKs);

			List<com.liferay.portal.model.Role> roles = getRoles(pk);

			for (com.liferay.portal.model.Role role : roles) {
				if (!rolePKSet.contains(role.getPrimaryKey())) {
					removeRole.remove(pk, role.getPrimaryKey());
				}
				else {
					rolePKSet.remove(role.getPrimaryKey());
				}
			}

			for (Long rolePK : rolePKSet) {
				addRole.add(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Roles");
		}
	}

	public void setRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			long[] rolePKs = new long[roles.size()];

			for (int i = 0; i < roles.size(); i++) {
				com.liferay.portal.model.Role role = roles.get(i);

				rolePKs[i] = role.getPrimaryKey();
			}

			setRoles(pk, rolePKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_Roles");
		}
	}

	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk)
		throws SystemException {
		return getUserGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk,
		int start, int end) throws SystemException {
		return getUserGroups(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_USERGROUPS = new FinderPath(com.liferay.portal.model.impl.UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_USERGROUPS,
			"Groups_UserGroups", "getUserGroups",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		List<com.liferay.portal.model.UserGroup> list = (List<com.liferay.portal.model.UserGroup>)FinderCacheUtil.getResult(FINDER_PATH_GET_USERGROUPS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 1;

				if (obc != null) {
					arrayCapacity += 2;
				}

				if (3 > arrayCapacity) {
					arrayCapacity = 3;
				}

				StringBundler sb = new StringBundler(arrayCapacity);
				sb.append(_SQL_GETUSERGROUPS);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				else {
					sb.append("ORDER BY ");

					sb.append("UserGroup.name ASC");
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("UserGroup",
					com.liferay.portal.model.impl.UserGroupImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.UserGroup>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portal.model.UserGroup>();
				}

				userGroupPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_USERGROUPS,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_USERGROUPS_SIZE = new FinderPath(com.liferay.portal.model.impl.UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_USERGROUPS,
			"Groups_UserGroups", "getUserGroupsSize",
			new String[] { Long.class.getName() });

	public int getUserGroupsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_USERGROUPS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETUSERGROUPSSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_USERGROUPS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_USERGROUP = new FinderPath(com.liferay.portal.model.impl.UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_USERGROUPS,
			"Groups_UserGroups", "containsUserGroup",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsUserGroup(long pk, long userGroupPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk), new Long(userGroupPK) };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_USERGROUP,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsUserGroup.contains(pk,
							userGroupPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_USERGROUP,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsUserGroups(long pk) throws SystemException {
		if (getUserGroupsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addUserGroup(long pk, long userGroupPK)
		throws SystemException {
		try {
			addUserGroup.add(pk, userGroupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_UserGroups");
		}
	}

	public void addUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) throws SystemException {
		try {
			addUserGroup.add(pk, userGroup.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_UserGroups");
		}
	}

	public void addUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			for (long userGroupPK : userGroupPKs) {
				addUserGroup.add(pk, userGroupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_UserGroups");
		}
	}

	public void addUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				addUserGroup.add(pk, userGroup.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_UserGroups");
		}
	}

	public void clearUserGroups(long pk) throws SystemException {
		try {
			clearUserGroups.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_UserGroups");
		}
	}

	public void removeUserGroup(long pk, long userGroupPK)
		throws SystemException {
		try {
			removeUserGroup.remove(pk, userGroupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_UserGroups");
		}
	}

	public void removeUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) throws SystemException {
		try {
			removeUserGroup.remove(pk, userGroup.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_UserGroups");
		}
	}

	public void removeUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			for (long userGroupPK : userGroupPKs) {
				removeUserGroup.remove(pk, userGroupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_UserGroups");
		}
	}

	public void removeUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				removeUserGroup.remove(pk, userGroup.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_UserGroups");
		}
	}

	public void setUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			Set<Long> userGroupPKSet = SetUtil.fromArray(userGroupPKs);

			List<com.liferay.portal.model.UserGroup> userGroups = getUserGroups(pk);

			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				if (!userGroupPKSet.contains(userGroup.getPrimaryKey())) {
					removeUserGroup.remove(pk, userGroup.getPrimaryKey());
				}
				else {
					userGroupPKSet.remove(userGroup.getPrimaryKey());
				}
			}

			for (Long userGroupPK : userGroupPKSet) {
				addUserGroup.add(pk, userGroupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_UserGroups");
		}
	}

	public void setUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			long[] userGroupPKs = new long[userGroups.size()];

			for (int i = 0; i < userGroups.size(); i++) {
				com.liferay.portal.model.UserGroup userGroup = userGroups.get(i);

				userGroupPKs[i] = userGroup.getPrimaryKey();
			}

			setUserGroups(pk, userGroupPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Groups_UserGroups");
		}
	}

	public List<com.liferay.portal.model.User> getUsers(long pk)
		throws SystemException {
		return getUsers(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.User> getUsers(long pk, int start,
		int end) throws SystemException {
		return getUsers(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_USERS = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_USERS_GROUPS, "Users_Groups",
			"getUsers",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portal.model.User> getUsers(long pk, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		List<com.liferay.portal.model.User> list = (List<com.liferay.portal.model.User>)FinderCacheUtil.getResult(FINDER_PATH_GET_USERS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 1;

				if (obc != null) {
					arrayCapacity += 2;
				}

				StringBundler sb = new StringBundler(arrayCapacity);
				sb.append(_SQL_GETUSERS);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("User_",
					com.liferay.portal.model.impl.UserImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.User>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portal.model.User>();
				}

				userPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_USERS, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_USERS_SIZE = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_USERS_GROUPS, "Users_Groups",
			"getUsersSize", new String[] { Long.class.getName() });

	public int getUsersSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_USERS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETUSERSSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_USERS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_USER = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED_USERS_GROUPS, "Users_Groups",
			"containsUser",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsUser(long pk, long userPK) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk), new Long(userPK) };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_USER,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsUser.contains(pk, userPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_USER,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsUsers(long pk) throws SystemException {
		if (getUsersSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addUser(long pk, long userPK) throws SystemException {
		try {
			addUser.add(pk, userPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void addUser(long pk, com.liferay.portal.model.User user)
		throws SystemException {
		try {
			addUser.add(pk, user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void addUsers(long pk, long[] userPKs) throws SystemException {
		try {
			for (long userPK : userPKs) {
				addUser.add(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void addUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			for (com.liferay.portal.model.User user : users) {
				addUser.add(pk, user.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void clearUsers(long pk) throws SystemException {
		try {
			clearUsers.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void removeUser(long pk, long userPK) throws SystemException {
		try {
			removeUser.remove(pk, userPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void removeUser(long pk, com.liferay.portal.model.User user)
		throws SystemException {
		try {
			removeUser.remove(pk, user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void removeUsers(long pk, long[] userPKs) throws SystemException {
		try {
			for (long userPK : userPKs) {
				removeUser.remove(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void removeUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			for (com.liferay.portal.model.User user : users) {
				removeUser.remove(pk, user.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void setUsers(long pk, long[] userPKs) throws SystemException {
		try {
			Set<Long> userPKSet = SetUtil.fromArray(userPKs);

			List<com.liferay.portal.model.User> users = getUsers(pk);

			for (com.liferay.portal.model.User user : users) {
				if (!userPKSet.contains(user.getPrimaryKey())) {
					removeUser.remove(pk, user.getPrimaryKey());
				}
				else {
					userPKSet.remove(user.getPrimaryKey());
				}
			}

			for (Long userPK : userPKSet) {
				addUser.add(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void setUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			long[] userPKs = new long[users.size()];

			for (int i = 0; i < users.size(); i++) {
				com.liferay.portal.model.User user = users.get(i);

				userPKs[i] = user.getPrimaryKey();
			}

			setUsers(pk, userPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("Users_Groups");
		}
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Group")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Group>> listenersList = new ArrayList<ModelListener<Group>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Group>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsOrganization = new ContainsOrganization(this);

		addOrganization = new AddOrganization(this);
		clearOrganizations = new ClearOrganizations(this);
		removeOrganization = new RemoveOrganization(this);

		containsPermission = new ContainsPermission(this);

		addPermission = new AddPermission(this);
		clearPermissions = new ClearPermissions(this);
		removePermission = new RemovePermission(this);

		containsRole = new ContainsRole(this);

		addRole = new AddRole(this);
		clearRoles = new ClearRoles(this);
		removeRole = new RemoveRole(this);

		containsUserGroup = new ContainsUserGroup(this);

		addUserGroup = new AddUserGroup(this);
		clearUserGroups = new ClearUserGroups(this);
		removeUserGroup = new RemoveUserGroup(this);

		containsUser = new ContainsUser(this);

		addUser = new AddUser(this);
		clearUsers = new ClearUsers(this);
		removeUser = new RemoveUser(this);
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
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetEntryPersistence")
	protected com.liferay.portlet.asset.service.persistence.AssetEntryPersistence assetEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence")
	protected com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence blogsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence")
	protected com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence blogsStatsUserPersistence;
	@BeanReference(name = "com.liferay.portlet.bookmarks.service.persistence.BookmarksFolderPersistence")
	protected com.liferay.portlet.bookmarks.service.persistence.BookmarksFolderPersistence bookmarksFolderPersistence;
	@BeanReference(name = "com.liferay.portlet.calendar.service.persistence.CalEventPersistence")
	protected com.liferay.portlet.calendar.service.persistence.CalEventPersistence calEventPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence dlFolderPersistence;
	@BeanReference(name = "com.liferay.portlet.imagegallery.service.persistence.IGFolderPersistence")
	protected com.liferay.portlet.imagegallery.service.persistence.IGFolderPersistence igFolderPersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticlePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalArticlePersistence journalArticlePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalStructurePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalStructurePersistence journalStructurePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalTemplatePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalTemplatePersistence journalTemplatePersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBBanPersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBBanPersistence mbBanPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBCategoryPersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBCategoryPersistence mbCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence")
	protected com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence mbStatsUserPersistence;
	@BeanReference(name = "com.liferay.portlet.polls.service.persistence.PollsQuestionPersistence")
	protected com.liferay.portlet.polls.service.persistence.PollsQuestionPersistence pollsQuestionPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingCartPersistence")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingCartPersistence shoppingCartPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingCategoryPersistence")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingCategoryPersistence shoppingCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingCouponPersistence")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingCouponPersistence shoppingCouponPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingOrderPersistence")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingOrderPersistence shoppingOrderPersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCFrameworkVersionPersistence")
	protected com.liferay.portlet.softwarecatalog.service.persistence.SCFrameworkVersionPersistence scFrameworkVersionPersistence;
	@BeanReference(name = "com.liferay.portlet.softwarecatalog.service.persistence.SCProductEntryPersistence")
	protected com.liferay.portlet.softwarecatalog.service.persistence.SCProductEntryPersistence scProductEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.tasks.service.persistence.TasksProposalPersistence")
	protected com.liferay.portlet.tasks.service.persistence.TasksProposalPersistence tasksProposalPersistence;
	@BeanReference(name = "com.liferay.portlet.wiki.service.persistence.WikiNodePersistence")
	protected com.liferay.portlet.wiki.service.persistence.WikiNodePersistence wikiNodePersistence;
	protected ContainsOrganization containsOrganization;
	protected AddOrganization addOrganization;
	protected ClearOrganizations clearOrganizations;
	protected RemoveOrganization removeOrganization;
	protected ContainsPermission containsPermission;
	protected AddPermission addPermission;
	protected ClearPermissions clearPermissions;
	protected RemovePermission removePermission;
	protected ContainsRole containsRole;
	protected AddRole addRole;
	protected ClearRoles clearRoles;
	protected RemoveRole removeRole;
	protected ContainsUserGroup containsUserGroup;
	protected AddUserGroup addUserGroup;
	protected ClearUserGroups clearUserGroups;
	protected RemoveUserGroup removeUserGroup;
	protected ContainsUser containsUser;
	protected AddUser addUser;
	protected ClearUsers clearUsers;
	protected RemoveUser removeUser;

	protected class ContainsOrganization {
		protected ContainsOrganization(GroupPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSORGANIZATION,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long groupId, long organizationId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(groupId), new Long(organizationId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery _mappingSqlQuery;
	}

	protected class AddOrganization {
		protected AddOrganization(GroupPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Groups_Orgs (groupId, organizationId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long groupId, long organizationId)
			throws SystemException {
			if (!_persistenceImpl.containsOrganization.contains(groupId,
						organizationId)) {
				ModelListener<com.liferay.portal.model.Organization>[] organizationListeners =
					organizationPersistence.getListeners();

				for (ModelListener<Group> listener : listeners) {
					listener.onBeforeAddAssociation(groupId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onBeforeAddAssociation(organizationId,
						Group.class.getName(), groupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(groupId), new Long(organizationId)
					});

				for (ModelListener<Group> listener : listeners) {
					listener.onAfterAddAssociation(groupId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onAfterAddAssociation(organizationId,
						Group.class.getName(), groupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private GroupPersistenceImpl _persistenceImpl;
	}

	protected class ClearOrganizations {
		protected ClearOrganizations(GroupPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_Orgs WHERE groupId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long groupId) throws SystemException {
			ModelListener<com.liferay.portal.model.Organization>[] organizationListeners =
				organizationPersistence.getListeners();

			List<com.liferay.portal.model.Organization> organizations = null;

			if ((listeners.length > 0) || (organizationListeners.length > 0)) {
				organizations = getOrganizations(groupId);

				for (com.liferay.portal.model.Organization organization : organizations) {
					for (ModelListener<Group> listener : listeners) {
						listener.onBeforeRemoveAssociation(groupId,
							com.liferay.portal.model.Organization.class.getName(),
							organization.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
						listener.onBeforeRemoveAssociation(organization.getPrimaryKey(),
							Group.class.getName(), groupId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(groupId) });

			if ((listeners.length > 0) || (organizationListeners.length > 0)) {
				for (com.liferay.portal.model.Organization organization : organizations) {
					for (ModelListener<Group> listener : listeners) {
						listener.onAfterRemoveAssociation(groupId,
							com.liferay.portal.model.Organization.class.getName(),
							organization.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
						listener.onAfterRemoveAssociation(organization.getPrimaryKey(),
							Group.class.getName(), groupId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveOrganization {
		protected RemoveOrganization(GroupPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_Orgs WHERE groupId = ? AND organizationId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long groupId, long organizationId)
			throws SystemException {
			if (_persistenceImpl.containsOrganization.contains(groupId,
						organizationId)) {
				ModelListener<com.liferay.portal.model.Organization>[] organizationListeners =
					organizationPersistence.getListeners();

				for (ModelListener<Group> listener : listeners) {
					listener.onBeforeRemoveAssociation(groupId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onBeforeRemoveAssociation(organizationId,
						Group.class.getName(), groupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(groupId), new Long(organizationId)
					});

				for (ModelListener<Group> listener : listeners) {
					listener.onAfterRemoveAssociation(groupId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onAfterRemoveAssociation(organizationId,
						Group.class.getName(), groupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private GroupPersistenceImpl _persistenceImpl;
	}

	protected class ContainsPermission {
		protected ContainsPermission(GroupPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSPERMISSION,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long groupId, long permissionId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(groupId), new Long(permissionId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery _mappingSqlQuery;
	}

	protected class AddPermission {
		protected AddPermission(GroupPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Groups_Permissions (groupId, permissionId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long groupId, long permissionId)
			throws SystemException {
			if (!_persistenceImpl.containsPermission.contains(groupId,
						permissionId)) {
				ModelListener<com.liferay.portal.model.Permission>[] permissionListeners =
					permissionPersistence.getListeners();

				for (ModelListener<Group> listener : listeners) {
					listener.onBeforeAddAssociation(groupId,
						com.liferay.portal.model.Permission.class.getName(),
						permissionId);
				}

				for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
					listener.onBeforeAddAssociation(permissionId,
						Group.class.getName(), groupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(groupId), new Long(permissionId)
					});

				for (ModelListener<Group> listener : listeners) {
					listener.onAfterAddAssociation(groupId,
						com.liferay.portal.model.Permission.class.getName(),
						permissionId);
				}

				for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
					listener.onAfterAddAssociation(permissionId,
						Group.class.getName(), groupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private GroupPersistenceImpl _persistenceImpl;
	}

	protected class ClearPermissions {
		protected ClearPermissions(GroupPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_Permissions WHERE groupId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long groupId) throws SystemException {
			ModelListener<com.liferay.portal.model.Permission>[] permissionListeners =
				permissionPersistence.getListeners();

			List<com.liferay.portal.model.Permission> permissions = null;

			if ((listeners.length > 0) || (permissionListeners.length > 0)) {
				permissions = getPermissions(groupId);

				for (com.liferay.portal.model.Permission permission : permissions) {
					for (ModelListener<Group> listener : listeners) {
						listener.onBeforeRemoveAssociation(groupId,
							com.liferay.portal.model.Permission.class.getName(),
							permission.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
						listener.onBeforeRemoveAssociation(permission.getPrimaryKey(),
							Group.class.getName(), groupId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(groupId) });

			if ((listeners.length > 0) || (permissionListeners.length > 0)) {
				for (com.liferay.portal.model.Permission permission : permissions) {
					for (ModelListener<Group> listener : listeners) {
						listener.onAfterRemoveAssociation(groupId,
							com.liferay.portal.model.Permission.class.getName(),
							permission.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
						listener.onAfterRemoveAssociation(permission.getPrimaryKey(),
							Group.class.getName(), groupId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemovePermission {
		protected RemovePermission(GroupPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_Permissions WHERE groupId = ? AND permissionId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long groupId, long permissionId)
			throws SystemException {
			if (_persistenceImpl.containsPermission.contains(groupId,
						permissionId)) {
				ModelListener<com.liferay.portal.model.Permission>[] permissionListeners =
					permissionPersistence.getListeners();

				for (ModelListener<Group> listener : listeners) {
					listener.onBeforeRemoveAssociation(groupId,
						com.liferay.portal.model.Permission.class.getName(),
						permissionId);
				}

				for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
					listener.onBeforeRemoveAssociation(permissionId,
						Group.class.getName(), groupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(groupId), new Long(permissionId)
					});

				for (ModelListener<Group> listener : listeners) {
					listener.onAfterRemoveAssociation(groupId,
						com.liferay.portal.model.Permission.class.getName(),
						permissionId);
				}

				for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
					listener.onAfterRemoveAssociation(permissionId,
						Group.class.getName(), groupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private GroupPersistenceImpl _persistenceImpl;
	}

	protected class ContainsRole {
		protected ContainsRole(GroupPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSROLE,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long groupId, long roleId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(groupId), new Long(roleId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery _mappingSqlQuery;
	}

	protected class AddRole {
		protected AddRole(GroupPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Groups_Roles (groupId, roleId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long groupId, long roleId) throws SystemException {
			if (!_persistenceImpl.containsRole.contains(groupId, roleId)) {
				ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

				for (ModelListener<Group> listener : listeners) {
					listener.onBeforeAddAssociation(groupId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onBeforeAddAssociation(roleId,
						Group.class.getName(), groupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(groupId), new Long(roleId)
					});

				for (ModelListener<Group> listener : listeners) {
					listener.onAfterAddAssociation(groupId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onAfterAddAssociation(roleId,
						Group.class.getName(), groupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private GroupPersistenceImpl _persistenceImpl;
	}

	protected class ClearRoles {
		protected ClearRoles(GroupPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_Roles WHERE groupId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long groupId) throws SystemException {
			ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

			List<com.liferay.portal.model.Role> roles = null;

			if ((listeners.length > 0) || (roleListeners.length > 0)) {
				roles = getRoles(groupId);

				for (com.liferay.portal.model.Role role : roles) {
					for (ModelListener<Group> listener : listeners) {
						listener.onBeforeRemoveAssociation(groupId,
							com.liferay.portal.model.Role.class.getName(),
							role.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
						listener.onBeforeRemoveAssociation(role.getPrimaryKey(),
							Group.class.getName(), groupId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(groupId) });

			if ((listeners.length > 0) || (roleListeners.length > 0)) {
				for (com.liferay.portal.model.Role role : roles) {
					for (ModelListener<Group> listener : listeners) {
						listener.onAfterRemoveAssociation(groupId,
							com.liferay.portal.model.Role.class.getName(),
							role.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
						listener.onAfterRemoveAssociation(role.getPrimaryKey(),
							Group.class.getName(), groupId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveRole {
		protected RemoveRole(GroupPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_Roles WHERE groupId = ? AND roleId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long groupId, long roleId)
			throws SystemException {
			if (_persistenceImpl.containsRole.contains(groupId, roleId)) {
				ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

				for (ModelListener<Group> listener : listeners) {
					listener.onBeforeRemoveAssociation(groupId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onBeforeRemoveAssociation(roleId,
						Group.class.getName(), groupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(groupId), new Long(roleId)
					});

				for (ModelListener<Group> listener : listeners) {
					listener.onAfterRemoveAssociation(groupId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onAfterRemoveAssociation(roleId,
						Group.class.getName(), groupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private GroupPersistenceImpl _persistenceImpl;
	}

	protected class ContainsUserGroup {
		protected ContainsUserGroup(GroupPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSUSERGROUP,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long groupId, long userGroupId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(groupId), new Long(userGroupId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery _mappingSqlQuery;
	}

	protected class AddUserGroup {
		protected AddUserGroup(GroupPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Groups_UserGroups (groupId, userGroupId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long groupId, long userGroupId)
			throws SystemException {
			if (!_persistenceImpl.containsUserGroup.contains(groupId,
						userGroupId)) {
				ModelListener<com.liferay.portal.model.UserGroup>[] userGroupListeners =
					userGroupPersistence.getListeners();

				for (ModelListener<Group> listener : listeners) {
					listener.onBeforeAddAssociation(groupId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onBeforeAddAssociation(userGroupId,
						Group.class.getName(), groupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(groupId), new Long(userGroupId)
					});

				for (ModelListener<Group> listener : listeners) {
					listener.onAfterAddAssociation(groupId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onAfterAddAssociation(userGroupId,
						Group.class.getName(), groupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private GroupPersistenceImpl _persistenceImpl;
	}

	protected class ClearUserGroups {
		protected ClearUserGroups(GroupPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_UserGroups WHERE groupId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long groupId) throws SystemException {
			ModelListener<com.liferay.portal.model.UserGroup>[] userGroupListeners =
				userGroupPersistence.getListeners();

			List<com.liferay.portal.model.UserGroup> userGroups = null;

			if ((listeners.length > 0) || (userGroupListeners.length > 0)) {
				userGroups = getUserGroups(groupId);

				for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
					for (ModelListener<Group> listener : listeners) {
						listener.onBeforeRemoveAssociation(groupId,
							com.liferay.portal.model.UserGroup.class.getName(),
							userGroup.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
						listener.onBeforeRemoveAssociation(userGroup.getPrimaryKey(),
							Group.class.getName(), groupId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(groupId) });

			if ((listeners.length > 0) || (userGroupListeners.length > 0)) {
				for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
					for (ModelListener<Group> listener : listeners) {
						listener.onAfterRemoveAssociation(groupId,
							com.liferay.portal.model.UserGroup.class.getName(),
							userGroup.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
						listener.onAfterRemoveAssociation(userGroup.getPrimaryKey(),
							Group.class.getName(), groupId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveUserGroup {
		protected RemoveUserGroup(GroupPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_UserGroups WHERE groupId = ? AND userGroupId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long groupId, long userGroupId)
			throws SystemException {
			if (_persistenceImpl.containsUserGroup.contains(groupId, userGroupId)) {
				ModelListener<com.liferay.portal.model.UserGroup>[] userGroupListeners =
					userGroupPersistence.getListeners();

				for (ModelListener<Group> listener : listeners) {
					listener.onBeforeRemoveAssociation(groupId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onBeforeRemoveAssociation(userGroupId,
						Group.class.getName(), groupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(groupId), new Long(userGroupId)
					});

				for (ModelListener<Group> listener : listeners) {
					listener.onAfterRemoveAssociation(groupId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onAfterRemoveAssociation(userGroupId,
						Group.class.getName(), groupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private GroupPersistenceImpl _persistenceImpl;
	}

	protected class ContainsUser {
		protected ContainsUser(GroupPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSUSER,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long groupId, long userId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(groupId), new Long(userId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery _mappingSqlQuery;
	}

	protected class AddUser {
		protected AddUser(GroupPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Groups (groupId, userId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long groupId, long userId) throws SystemException {
			if (!_persistenceImpl.containsUser.contains(groupId, userId)) {
				ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

				for (ModelListener<Group> listener : listeners) {
					listener.onBeforeAddAssociation(groupId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onBeforeAddAssociation(userId,
						Group.class.getName(), groupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(groupId), new Long(userId)
					});

				for (ModelListener<Group> listener : listeners) {
					listener.onAfterAddAssociation(groupId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onAfterAddAssociation(userId,
						Group.class.getName(), groupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private GroupPersistenceImpl _persistenceImpl;
	}

	protected class ClearUsers {
		protected ClearUsers(GroupPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Groups WHERE groupId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long groupId) throws SystemException {
			ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

			List<com.liferay.portal.model.User> users = null;

			if ((listeners.length > 0) || (userListeners.length > 0)) {
				users = getUsers(groupId);

				for (com.liferay.portal.model.User user : users) {
					for (ModelListener<Group> listener : listeners) {
						listener.onBeforeRemoveAssociation(groupId,
							com.liferay.portal.model.User.class.getName(),
							user.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
						listener.onBeforeRemoveAssociation(user.getPrimaryKey(),
							Group.class.getName(), groupId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(groupId) });

			if ((listeners.length > 0) || (userListeners.length > 0)) {
				for (com.liferay.portal.model.User user : users) {
					for (ModelListener<Group> listener : listeners) {
						listener.onAfterRemoveAssociation(groupId,
							com.liferay.portal.model.User.class.getName(),
							user.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
						listener.onAfterRemoveAssociation(user.getPrimaryKey(),
							Group.class.getName(), groupId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveUser {
		protected RemoveUser(GroupPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Groups WHERE groupId = ? AND userId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long groupId, long userId)
			throws SystemException {
			if (_persistenceImpl.containsUser.contains(groupId, userId)) {
				ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

				for (ModelListener<Group> listener : listeners) {
					listener.onBeforeRemoveAssociation(groupId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onBeforeRemoveAssociation(userId,
						Group.class.getName(), groupId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(groupId), new Long(userId)
					});

				for (ModelListener<Group> listener : listeners) {
					listener.onAfterRemoveAssociation(groupId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onAfterRemoveAssociation(userId,
						Group.class.getName(), groupId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private GroupPersistenceImpl _persistenceImpl;
	}

	private static final String _SQL_GETORGANIZATIONS = "SELECT {Organization_.*} FROM Organization_ INNER JOIN Groups_Orgs ON (Groups_Orgs.organizationId = Organization_.organizationId) WHERE (Groups_Orgs.groupId = ?)";
	private static final String _SQL_GETORGANIZATIONSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Orgs WHERE groupId = ?";
	private static final String _SQL_CONTAINSORGANIZATION = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Orgs WHERE groupId = ? AND organizationId = ?";
	private static final String _SQL_GETPERMISSIONS = "SELECT {Permission_.*} FROM Permission_ INNER JOIN Groups_Permissions ON (Groups_Permissions.permissionId = Permission_.permissionId) WHERE (Groups_Permissions.groupId = ?)";
	private static final String _SQL_GETPERMISSIONSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Permissions WHERE groupId = ?";
	private static final String _SQL_CONTAINSPERMISSION = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Permissions WHERE groupId = ? AND permissionId = ?";
	private static final String _SQL_GETROLES = "SELECT {Role_.*} FROM Role_ INNER JOIN Groups_Roles ON (Groups_Roles.roleId = Role_.roleId) WHERE (Groups_Roles.groupId = ?)";
	private static final String _SQL_GETROLESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Roles WHERE groupId = ?";
	private static final String _SQL_CONTAINSROLE = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Roles WHERE groupId = ? AND roleId = ?";
	private static final String _SQL_GETUSERGROUPS = "SELECT {UserGroup.*} FROM UserGroup INNER JOIN Groups_UserGroups ON (Groups_UserGroups.userGroupId = UserGroup.userGroupId) WHERE (Groups_UserGroups.groupId = ?)";
	private static final String _SQL_GETUSERGROUPSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_UserGroups WHERE groupId = ?";
	private static final String _SQL_CONTAINSUSERGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_UserGroups WHERE groupId = ? AND userGroupId = ?";
	private static final String _SQL_GETUSERS = "SELECT {User_.*} FROM User_ INNER JOIN Users_Groups ON (Users_Groups.userId = User_.userId) WHERE (Users_Groups.groupId = ?)";
	private static final String _SQL_GETUSERSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Groups WHERE groupId = ?";
	private static final String _SQL_CONTAINSUSER = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Groups WHERE groupId = ? AND userId = ?";
	private static Log _log = LogFactoryUtil.getLog(GroupPersistenceImpl.class);
}