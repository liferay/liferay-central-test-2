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

package com.liferay.portlet.social.service.persistence;

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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.social.NoSuchActivityException;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.impl.SocialActivityImpl;
import com.liferay.portlet.social.model.impl.SocialActivityModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="SocialActivityPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialActivityPersistence
 * @see       SocialActivityUtil
 * @generated
 */
public class SocialActivityPersistenceImpl extends BasePersistenceImpl<SocialActivity>
	implements SocialActivityPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = SocialActivityImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_MIRRORACTIVITYID = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByMirrorActivityId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_MIRRORACTIVITYID = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByMirrorActivityId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_CLASSNAMEID = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByClassNameId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_CLASSNAMEID = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByClassNameId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_CLASSNAMEID = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByClassNameId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_RECEIVERUSERID = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByReceiverUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_RECEIVERUSERID = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByReceiverUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_RECEIVERUSERID = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByReceiverUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_C = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_C = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_M_C_C = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByM_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_M_C_C = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByM_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_M_C_C = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByM_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_G_U_CD_C_C_T_R = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_U_CD_C_C_T_R",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_CD_C_C_T_R = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_U_CD_C_C_T_R",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(SocialActivity socialActivity) {
		EntityCacheUtil.putResult(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityImpl.class, socialActivity.getPrimaryKey(),
			socialActivity);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MIRRORACTIVITYID,
			new Object[] { new Long(socialActivity.getMirrorActivityId()) },
			socialActivity);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U_CD_C_C_T_R,
			new Object[] {
				new Long(socialActivity.getGroupId()),
				new Long(socialActivity.getUserId()),
				new Long(socialActivity.getCreateDate()),
				new Long(socialActivity.getClassNameId()),
				new Long(socialActivity.getClassPK()),
				new Integer(socialActivity.getType()),
				new Long(socialActivity.getReceiverUserId())
			}, socialActivity);
	}

	public void cacheResult(List<SocialActivity> socialActivities) {
		for (SocialActivity socialActivity : socialActivities) {
			if (EntityCacheUtil.getResult(
						SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
						SocialActivityImpl.class,
						socialActivity.getPrimaryKey(), this) == null) {
				cacheResult(socialActivity);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(SocialActivityImpl.class.getName());
		EntityCacheUtil.clearCache(SocialActivityImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public SocialActivity create(long activityId) {
		SocialActivity socialActivity = new SocialActivityImpl();

		socialActivity.setNew(true);
		socialActivity.setPrimaryKey(activityId);

		return socialActivity;
	}

	public SocialActivity remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public SocialActivity remove(long activityId)
		throws NoSuchActivityException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialActivity socialActivity = (SocialActivity)session.get(SocialActivityImpl.class,
					new Long(activityId));

			if (socialActivity == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No SocialActivity exists with the primary key " +
						activityId);
				}

				throw new NoSuchActivityException(
					"No SocialActivity exists with the primary key " +
					activityId);
			}

			return remove(socialActivity);
		}
		catch (NoSuchActivityException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SocialActivity remove(SocialActivity socialActivity)
		throws SystemException {
		for (ModelListener<SocialActivity> listener : listeners) {
			listener.onBeforeRemove(socialActivity);
		}

		socialActivity = removeImpl(socialActivity);

		for (ModelListener<SocialActivity> listener : listeners) {
			listener.onAfterRemove(socialActivity);
		}

		return socialActivity;
	}

	protected SocialActivity removeImpl(SocialActivity socialActivity)
		throws SystemException {
		socialActivity = toUnwrappedModel(socialActivity);

		Session session = null;

		try {
			session = openSession();

			if (socialActivity.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(SocialActivityImpl.class,
						socialActivity.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(socialActivity);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		SocialActivityModelImpl socialActivityModelImpl = (SocialActivityModelImpl)socialActivity;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_MIRRORACTIVITYID,
			new Object[] {
				new Long(socialActivityModelImpl.getOriginalMirrorActivityId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U_CD_C_C_T_R,
			new Object[] {
				new Long(socialActivityModelImpl.getOriginalGroupId()),
				new Long(socialActivityModelImpl.getOriginalUserId()),
				new Long(socialActivityModelImpl.getOriginalCreateDate()),
				new Long(socialActivityModelImpl.getOriginalClassNameId()),
				new Long(socialActivityModelImpl.getOriginalClassPK()),
				new Integer(socialActivityModelImpl.getOriginalType()),
				new Long(socialActivityModelImpl.getOriginalReceiverUserId())
			});

		EntityCacheUtil.removeResult(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityImpl.class, socialActivity.getPrimaryKey());

		return socialActivity;
	}

	public SocialActivity updateImpl(
		com.liferay.portlet.social.model.SocialActivity socialActivity,
		boolean merge) throws SystemException {
		socialActivity = toUnwrappedModel(socialActivity);

		boolean isNew = socialActivity.isNew();

		SocialActivityModelImpl socialActivityModelImpl = (SocialActivityModelImpl)socialActivity;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, socialActivity, merge);

			socialActivity.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityImpl.class, socialActivity.getPrimaryKey(),
			socialActivity);

		if (!isNew &&
				(socialActivity.getMirrorActivityId() != socialActivityModelImpl.getOriginalMirrorActivityId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_MIRRORACTIVITYID,
				new Object[] {
					new Long(socialActivityModelImpl.getOriginalMirrorActivityId())
				});
		}

		if (isNew ||
				(socialActivity.getMirrorActivityId() != socialActivityModelImpl.getOriginalMirrorActivityId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MIRRORACTIVITYID,
				new Object[] { new Long(socialActivity.getMirrorActivityId()) },
				socialActivity);
		}

		if (!isNew &&
				((socialActivity.getGroupId() != socialActivityModelImpl.getOriginalGroupId()) ||
				(socialActivity.getUserId() != socialActivityModelImpl.getOriginalUserId()) ||
				(socialActivity.getCreateDate() != socialActivityModelImpl.getOriginalCreateDate()) ||
				(socialActivity.getClassNameId() != socialActivityModelImpl.getOriginalClassNameId()) ||
				(socialActivity.getClassPK() != socialActivityModelImpl.getOriginalClassPK()) ||
				(socialActivity.getType() != socialActivityModelImpl.getOriginalType()) ||
				(socialActivity.getReceiverUserId() != socialActivityModelImpl.getOriginalReceiverUserId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U_CD_C_C_T_R,
				new Object[] {
					new Long(socialActivityModelImpl.getOriginalGroupId()),
					new Long(socialActivityModelImpl.getOriginalUserId()),
					new Long(socialActivityModelImpl.getOriginalCreateDate()),
					new Long(socialActivityModelImpl.getOriginalClassNameId()),
					new Long(socialActivityModelImpl.getOriginalClassPK()),
					new Integer(socialActivityModelImpl.getOriginalType()),
					new Long(socialActivityModelImpl.getOriginalReceiverUserId())
				});
		}

		if (isNew ||
				((socialActivity.getGroupId() != socialActivityModelImpl.getOriginalGroupId()) ||
				(socialActivity.getUserId() != socialActivityModelImpl.getOriginalUserId()) ||
				(socialActivity.getCreateDate() != socialActivityModelImpl.getOriginalCreateDate()) ||
				(socialActivity.getClassNameId() != socialActivityModelImpl.getOriginalClassNameId()) ||
				(socialActivity.getClassPK() != socialActivityModelImpl.getOriginalClassPK()) ||
				(socialActivity.getType() != socialActivityModelImpl.getOriginalType()) ||
				(socialActivity.getReceiverUserId() != socialActivityModelImpl.getOriginalReceiverUserId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U_CD_C_C_T_R,
				new Object[] {
					new Long(socialActivity.getGroupId()),
					new Long(socialActivity.getUserId()),
					new Long(socialActivity.getCreateDate()),
					new Long(socialActivity.getClassNameId()),
					new Long(socialActivity.getClassPK()),
					new Integer(socialActivity.getType()),
					new Long(socialActivity.getReceiverUserId())
				}, socialActivity);
		}

		return socialActivity;
	}

	protected SocialActivity toUnwrappedModel(SocialActivity socialActivity) {
		if (socialActivity instanceof SocialActivityImpl) {
			return socialActivity;
		}

		SocialActivityImpl socialActivityImpl = new SocialActivityImpl();

		socialActivityImpl.setNew(socialActivity.isNew());
		socialActivityImpl.setPrimaryKey(socialActivity.getPrimaryKey());

		socialActivityImpl.setActivityId(socialActivity.getActivityId());
		socialActivityImpl.setGroupId(socialActivity.getGroupId());
		socialActivityImpl.setCompanyId(socialActivity.getCompanyId());
		socialActivityImpl.setUserId(socialActivity.getUserId());
		socialActivityImpl.setCreateDate(socialActivity.getCreateDate());
		socialActivityImpl.setMirrorActivityId(socialActivity.getMirrorActivityId());
		socialActivityImpl.setClassNameId(socialActivity.getClassNameId());
		socialActivityImpl.setClassPK(socialActivity.getClassPK());
		socialActivityImpl.setType(socialActivity.getType());
		socialActivityImpl.setExtraData(socialActivity.getExtraData());
		socialActivityImpl.setReceiverUserId(socialActivity.getReceiverUserId());

		return socialActivityImpl;
	}

	public SocialActivity findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SocialActivity findByPrimaryKey(long activityId)
		throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = fetchByPrimaryKey(activityId);

		if (socialActivity == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No SocialActivity exists with the primary key " +
					activityId);
			}

			throw new NoSuchActivityException(
				"No SocialActivity exists with the primary key " + activityId);
		}

		return socialActivity;
	}

	public SocialActivity fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SocialActivity fetchByPrimaryKey(long activityId)
		throws SystemException {
		SocialActivity socialActivity = (SocialActivity)EntityCacheUtil.getResult(SocialActivityModelImpl.ENTITY_CACHE_ENABLED,
				SocialActivityImpl.class, activityId, this);

		if (socialActivity == null) {
			Session session = null;

			try {
				session = openSession();

				socialActivity = (SocialActivity)session.get(SocialActivityImpl.class,
						new Long(activityId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (socialActivity != null) {
					cacheResult(socialActivity);
				}

				closeSession(session);
			}
		}

		return socialActivity;
	}

	public List<SocialActivity> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<SocialActivity> list = (List<SocialActivity>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);
				query.append(
					"SELECT socialActivity FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("socialActivity.createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialActivity>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialActivity> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<SocialActivity> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialActivity> list = (List<SocialActivity>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
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
				query.append(
					"SELECT socialActivity FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("socialActivity.");
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

					query.append("socialActivity.createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<SocialActivity>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialActivity>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialActivity findByGroupId_First(long groupId,
		OrderByComparator obc) throws NoSuchActivityException, SystemException {
		List<SocialActivity> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No SocialActivity exists with the key {");
			msg.append("groupId=" + groupId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchActivityException, SystemException {
		int count = countByGroupId(groupId);

		List<SocialActivity> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No SocialActivity exists with the key {");
			msg.append("groupId=" + groupId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity[] findByGroupId_PrevAndNext(long activityId,
		long groupId, OrderByComparator obc)
		throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = findByPrimaryKey(activityId);

		int count = countByGroupId(groupId);

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
			query.append(
				"SELECT socialActivity FROM SocialActivity socialActivity WHERE ");

			query.append("socialActivity.groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("socialActivity.");
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

				query.append("socialActivity.createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialActivity);

			SocialActivity[] array = new SocialActivityImpl[3];

			array[0] = (SocialActivity)objArray[0];
			array[1] = (SocialActivity)objArray[1];
			array[2] = (SocialActivity)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialActivity> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<SocialActivity> list = (List<SocialActivity>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);
				query.append(
					"SELECT socialActivity FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.companyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("socialActivity.createDate DESC");

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
					list = new ArrayList<SocialActivity>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialActivity> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<SocialActivity> findByCompanyId(long companyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialActivity> list = (List<SocialActivity>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
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
				query.append(
					"SELECT socialActivity FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("socialActivity.");
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

					query.append("socialActivity.createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<SocialActivity>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialActivity>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialActivity findByCompanyId_First(long companyId,
		OrderByComparator obc) throws NoSuchActivityException, SystemException {
		List<SocialActivity> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No SocialActivity exists with the key {");
			msg.append("companyId=" + companyId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity findByCompanyId_Last(long companyId,
		OrderByComparator obc) throws NoSuchActivityException, SystemException {
		int count = countByCompanyId(companyId);

		List<SocialActivity> list = findByCompanyId(companyId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No SocialActivity exists with the key {");
			msg.append("companyId=" + companyId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity[] findByCompanyId_PrevAndNext(long activityId,
		long companyId, OrderByComparator obc)
		throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = findByPrimaryKey(activityId);

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
			query.append(
				"SELECT socialActivity FROM SocialActivity socialActivity WHERE ");

			query.append("socialActivity.companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("socialActivity.");
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

				query.append("socialActivity.createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialActivity);

			SocialActivity[] array = new SocialActivityImpl[3];

			array[0] = (SocialActivity)objArray[0];
			array[1] = (SocialActivity)objArray[1];
			array[2] = (SocialActivity)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialActivity> findByUserId(long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		List<SocialActivity> list = (List<SocialActivity>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);
				query.append(
					"SELECT socialActivity FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("socialActivity.createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialActivity>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialActivity> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<SocialActivity> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialActivity> list = (List<SocialActivity>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID,
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
				query.append(
					"SELECT socialActivity FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("socialActivity.");
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

					query.append("socialActivity.createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<SocialActivity>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialActivity>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialActivity findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchActivityException, SystemException {
		List<SocialActivity> list = findByUserId(userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No SocialActivity exists with the key {");
			msg.append("userId=" + userId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchActivityException, SystemException {
		int count = countByUserId(userId);

		List<SocialActivity> list = findByUserId(userId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No SocialActivity exists with the key {");
			msg.append("userId=" + userId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity[] findByUserId_PrevAndNext(long activityId,
		long userId, OrderByComparator obc)
		throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = findByPrimaryKey(activityId);

		int count = countByUserId(userId);

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
			query.append(
				"SELECT socialActivity FROM SocialActivity socialActivity WHERE ");

			query.append("socialActivity.userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("socialActivity.");
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

				query.append("socialActivity.createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialActivity);

			SocialActivity[] array = new SocialActivityImpl[3];

			array[0] = (SocialActivity)objArray[0];
			array[1] = (SocialActivity)objArray[1];
			array[2] = (SocialActivity)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SocialActivity findByMirrorActivityId(long mirrorActivityId)
		throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = fetchByMirrorActivityId(mirrorActivityId);

		if (socialActivity == null) {
			StringBundler msg = new StringBundler(3);
			msg.append("No SocialActivity exists with the key {");
			msg.append("mirrorActivityId=" + mirrorActivityId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchActivityException(msg.toString());
		}

		return socialActivity;
	}

	public SocialActivity fetchByMirrorActivityId(long mirrorActivityId)
		throws SystemException {
		return fetchByMirrorActivityId(mirrorActivityId, true);
	}

	public SocialActivity fetchByMirrorActivityId(long mirrorActivityId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(mirrorActivityId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_MIRRORACTIVITYID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);
				query.append(
					"SELECT socialActivity FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.mirrorActivityId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("socialActivity.createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(mirrorActivityId);

				List<SocialActivity> list = q.list();

				result = list;

				SocialActivity socialActivity = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MIRRORACTIVITYID,
						finderArgs, list);
				}
				else {
					socialActivity = list.get(0);

					cacheResult(socialActivity);

					if ((socialActivity.getMirrorActivityId() != mirrorActivityId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MIRRORACTIVITYID,
							finderArgs, socialActivity);
					}
				}

				return socialActivity;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MIRRORACTIVITYID,
						finderArgs, new ArrayList<SocialActivity>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (SocialActivity)result;
			}
		}
	}

	public List<SocialActivity> findByClassNameId(long classNameId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(classNameId) };

		List<SocialActivity> list = (List<SocialActivity>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_CLASSNAMEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);
				query.append(
					"SELECT socialActivity FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.classNameId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("socialActivity.createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialActivity>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_CLASSNAMEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialActivity> findByClassNameId(long classNameId, int start,
		int end) throws SystemException {
		return findByClassNameId(classNameId, start, end, null);
	}

	public List<SocialActivity> findByClassNameId(long classNameId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialActivity> list = (List<SocialActivity>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_CLASSNAMEID,
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
				query.append(
					"SELECT socialActivity FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.classNameId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("socialActivity.");
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

					query.append("socialActivity.createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				list = (List<SocialActivity>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialActivity>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_CLASSNAMEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialActivity findByClassNameId_First(long classNameId,
		OrderByComparator obc) throws NoSuchActivityException, SystemException {
		List<SocialActivity> list = findByClassNameId(classNameId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No SocialActivity exists with the key {");
			msg.append("classNameId=" + classNameId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity findByClassNameId_Last(long classNameId,
		OrderByComparator obc) throws NoSuchActivityException, SystemException {
		int count = countByClassNameId(classNameId);

		List<SocialActivity> list = findByClassNameId(classNameId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No SocialActivity exists with the key {");
			msg.append("classNameId=" + classNameId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity[] findByClassNameId_PrevAndNext(long activityId,
		long classNameId, OrderByComparator obc)
		throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = findByPrimaryKey(activityId);

		int count = countByClassNameId(classNameId);

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
			query.append(
				"SELECT socialActivity FROM SocialActivity socialActivity WHERE ");

			query.append("socialActivity.classNameId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("socialActivity.");
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

				query.append("socialActivity.createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialActivity);

			SocialActivity[] array = new SocialActivityImpl[3];

			array[0] = (SocialActivity)objArray[0];
			array[1] = (SocialActivity)objArray[1];
			array[2] = (SocialActivity)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialActivity> findByReceiverUserId(long receiverUserId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(receiverUserId) };

		List<SocialActivity> list = (List<SocialActivity>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_RECEIVERUSERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);
				query.append(
					"SELECT socialActivity FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.receiverUserId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("socialActivity.createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(receiverUserId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialActivity>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_RECEIVERUSERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialActivity> findByReceiverUserId(long receiverUserId,
		int start, int end) throws SystemException {
		return findByReceiverUserId(receiverUserId, start, end, null);
	}

	public List<SocialActivity> findByReceiverUserId(long receiverUserId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(receiverUserId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialActivity> list = (List<SocialActivity>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_RECEIVERUSERID,
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
				query.append(
					"SELECT socialActivity FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.receiverUserId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("socialActivity.");
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

					query.append("socialActivity.createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(receiverUserId);

				list = (List<SocialActivity>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialActivity>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_RECEIVERUSERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialActivity findByReceiverUserId_First(long receiverUserId,
		OrderByComparator obc) throws NoSuchActivityException, SystemException {
		List<SocialActivity> list = findByReceiverUserId(receiverUserId, 0, 1,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No SocialActivity exists with the key {");
			msg.append("receiverUserId=" + receiverUserId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity findByReceiverUserId_Last(long receiverUserId,
		OrderByComparator obc) throws NoSuchActivityException, SystemException {
		int count = countByReceiverUserId(receiverUserId);

		List<SocialActivity> list = findByReceiverUserId(receiverUserId,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(3);
			msg.append("No SocialActivity exists with the key {");
			msg.append("receiverUserId=" + receiverUserId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity[] findByReceiverUserId_PrevAndNext(long activityId,
		long receiverUserId, OrderByComparator obc)
		throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = findByPrimaryKey(activityId);

		int count = countByReceiverUserId(receiverUserId);

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
			query.append(
				"SELECT socialActivity FROM SocialActivity socialActivity WHERE ");

			query.append("socialActivity.receiverUserId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("socialActivity.");
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

				query.append("socialActivity.createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(receiverUserId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialActivity);

			SocialActivity[] array = new SocialActivityImpl[3];

			array[0] = (SocialActivity)objArray[0];
			array[1] = (SocialActivity)objArray[1];
			array[2] = (SocialActivity)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialActivity> findByC_C(long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		List<SocialActivity> list = (List<SocialActivity>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(7);
				query.append(
					"SELECT socialActivity FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.classNameId = ?");

				query.append(" AND ");

				query.append("socialActivity.classPK = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("socialActivity.createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialActivity>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_C, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialActivity> findByC_C(long classNameId, long classPK,
		int start, int end) throws SystemException {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	public List<SocialActivity> findByC_C(long classNameId, long classPK,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialActivity> list = (List<SocialActivity>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_C,
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
				query.append(
					"SELECT socialActivity FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.classNameId = ?");

				query.append(" AND ");

				query.append("socialActivity.classPK = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("socialActivity.");
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

					query.append("socialActivity.createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = (List<SocialActivity>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialActivity>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialActivity findByC_C_First(long classNameId, long classPK,
		OrderByComparator obc) throws NoSuchActivityException, SystemException {
		List<SocialActivity> list = findByC_C(classNameId, classPK, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(5);
			msg.append("No SocialActivity exists with the key {");
			msg.append("classNameId=" + classNameId);
			msg.append(", ");
			msg.append("classPK=" + classPK);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity findByC_C_Last(long classNameId, long classPK,
		OrderByComparator obc) throws NoSuchActivityException, SystemException {
		int count = countByC_C(classNameId, classPK);

		List<SocialActivity> list = findByC_C(classNameId, classPK, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(5);
			msg.append("No SocialActivity exists with the key {");
			msg.append("classNameId=" + classNameId);
			msg.append(", ");
			msg.append("classPK=" + classPK);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity[] findByC_C_PrevAndNext(long activityId,
		long classNameId, long classPK, OrderByComparator obc)
		throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = findByPrimaryKey(activityId);

		int count = countByC_C(classNameId, classPK);

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
			query.append(
				"SELECT socialActivity FROM SocialActivity socialActivity WHERE ");

			query.append("socialActivity.classNameId = ?");

			query.append(" AND ");

			query.append("socialActivity.classPK = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("socialActivity.");
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

				query.append("socialActivity.createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			qPos.add(classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialActivity);

			SocialActivity[] array = new SocialActivityImpl[3];

			array[0] = (SocialActivity)objArray[0];
			array[1] = (SocialActivity)objArray[1];
			array[2] = (SocialActivity)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialActivity> findByM_C_C(long mirrorActivityId,
		long classNameId, long classPK) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(mirrorActivityId), new Long(classNameId),
				new Long(classPK)
			};

		List<SocialActivity> list = (List<SocialActivity>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_M_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(9);
				query.append(
					"SELECT socialActivity FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.mirrorActivityId = ?");

				query.append(" AND ");

				query.append("socialActivity.classNameId = ?");

				query.append(" AND ");

				query.append("socialActivity.classPK = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("socialActivity.createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(mirrorActivityId);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialActivity>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_M_C_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialActivity> findByM_C_C(long mirrorActivityId,
		long classNameId, long classPK, int start, int end)
		throws SystemException {
		return findByM_C_C(mirrorActivityId, classNameId, classPK, start, end,
			null);
	}

	public List<SocialActivity> findByM_C_C(long mirrorActivityId,
		long classNameId, long classPK, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(mirrorActivityId), new Long(classNameId),
				new Long(classPK),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialActivity> list = (List<SocialActivity>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_M_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				int arrayCapacity = 7;

				if (obc != null) {
					arrayCapacity += (obc.getOrderByFields().length * 4);
				}

				if (9 > arrayCapacity) {
					arrayCapacity = 9;
				}

				StringBundler query = new StringBundler(arrayCapacity);
				query.append(
					"SELECT socialActivity FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.mirrorActivityId = ?");

				query.append(" AND ");

				query.append("socialActivity.classNameId = ?");

				query.append(" AND ");

				query.append("socialActivity.classPK = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("socialActivity.");
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

					query.append("socialActivity.createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(mirrorActivityId);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = (List<SocialActivity>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialActivity>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_M_C_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SocialActivity findByM_C_C_First(long mirrorActivityId,
		long classNameId, long classPK, OrderByComparator obc)
		throws NoSuchActivityException, SystemException {
		List<SocialActivity> list = findByM_C_C(mirrorActivityId, classNameId,
				classPK, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(7);
			msg.append("No SocialActivity exists with the key {");
			msg.append("mirrorActivityId=" + mirrorActivityId);
			msg.append(", ");
			msg.append("classNameId=" + classNameId);
			msg.append(", ");
			msg.append("classPK=" + classPK);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity findByM_C_C_Last(long mirrorActivityId,
		long classNameId, long classPK, OrderByComparator obc)
		throws NoSuchActivityException, SystemException {
		int count = countByM_C_C(mirrorActivityId, classNameId, classPK);

		List<SocialActivity> list = findByM_C_C(mirrorActivityId, classNameId,
				classPK, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(7);
			msg.append("No SocialActivity exists with the key {");
			msg.append("mirrorActivityId=" + mirrorActivityId);
			msg.append(", ");
			msg.append("classNameId=" + classNameId);
			msg.append(", ");
			msg.append("classPK=" + classPK);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity[] findByM_C_C_PrevAndNext(long activityId,
		long mirrorActivityId, long classNameId, long classPK,
		OrderByComparator obc) throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = findByPrimaryKey(activityId);

		int count = countByM_C_C(mirrorActivityId, classNameId, classPK);

		Session session = null;

		try {
			session = openSession();

			int arrayCapacity = 7;

			if (obc != null) {
				arrayCapacity += (obc.getOrderByFields().length * 4);
			}

			if (9 > arrayCapacity) {
				arrayCapacity = 9;
			}

			StringBundler query = new StringBundler(arrayCapacity);
			query.append(
				"SELECT socialActivity FROM SocialActivity socialActivity WHERE ");

			query.append("socialActivity.mirrorActivityId = ?");

			query.append(" AND ");

			query.append("socialActivity.classNameId = ?");

			query.append(" AND ");

			query.append("socialActivity.classPK = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("socialActivity.");
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

				query.append("socialActivity.createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(mirrorActivityId);

			qPos.add(classNameId);

			qPos.add(classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialActivity);

			SocialActivity[] array = new SocialActivityImpl[3];

			array[0] = (SocialActivity)objArray[0];
			array[1] = (SocialActivity)objArray[1];
			array[2] = (SocialActivity)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SocialActivity findByG_U_CD_C_C_T_R(long groupId, long userId,
		long createDate, long classNameId, long classPK, int type,
		long receiverUserId) throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = fetchByG_U_CD_C_C_T_R(groupId, userId,
				createDate, classNameId, classPK, type, receiverUserId);

		if (socialActivity == null) {
			StringBundler msg = new StringBundler(15);
			msg.append("No SocialActivity exists with the key {");
			msg.append("groupId=" + groupId);
			msg.append(", ");
			msg.append("userId=" + userId);
			msg.append(", ");
			msg.append("createDate=" + createDate);
			msg.append(", ");
			msg.append("classNameId=" + classNameId);
			msg.append(", ");
			msg.append("classPK=" + classPK);
			msg.append(", ");
			msg.append("type=" + type);
			msg.append(", ");
			msg.append("receiverUserId=" + receiverUserId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchActivityException(msg.toString());
		}

		return socialActivity;
	}

	public SocialActivity fetchByG_U_CD_C_C_T_R(long groupId, long userId,
		long createDate, long classNameId, long classPK, int type,
		long receiverUserId) throws SystemException {
		return fetchByG_U_CD_C_C_T_R(groupId, userId, createDate, classNameId,
			classPK, type, receiverUserId, true);
	}

	public SocialActivity fetchByG_U_CD_C_C_T_R(long groupId, long userId,
		long createDate, long classNameId, long classPK, int type,
		long receiverUserId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId), new Long(createDate),
				new Long(classNameId), new Long(classPK), new Integer(type),
				new Long(receiverUserId)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_U_CD_C_C_T_R,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(17);
				query.append(
					"SELECT socialActivity FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.groupId = ?");

				query.append(" AND ");

				query.append("socialActivity.userId = ?");

				query.append(" AND ");

				query.append("socialActivity.createDate = ?");

				query.append(" AND ");

				query.append("socialActivity.classNameId = ?");

				query.append(" AND ");

				query.append("socialActivity.classPK = ?");

				query.append(" AND ");

				query.append("socialActivity.type = ?");

				query.append(" AND ");

				query.append("socialActivity.receiverUserId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("socialActivity.createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(createDate);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(receiverUserId);

				List<SocialActivity> list = q.list();

				result = list;

				SocialActivity socialActivity = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U_CD_C_C_T_R,
						finderArgs, list);
				}
				else {
					socialActivity = list.get(0);

					cacheResult(socialActivity);

					if ((socialActivity.getGroupId() != groupId) ||
							(socialActivity.getUserId() != userId) ||
							(socialActivity.getCreateDate() != createDate) ||
							(socialActivity.getClassNameId() != classNameId) ||
							(socialActivity.getClassPK() != classPK) ||
							(socialActivity.getType() != type) ||
							(socialActivity.getReceiverUserId() != receiverUserId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U_CD_C_C_T_R,
							finderArgs, socialActivity);
					}
				}

				return socialActivity;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U_CD_C_C_T_R,
						finderArgs, new ArrayList<SocialActivity>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (SocialActivity)result;
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

	public List<SocialActivity> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SocialActivity> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<SocialActivity> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<SocialActivity> list = (List<SocialActivity>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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
				query.append(
					"SELECT socialActivity FROM SocialActivity socialActivity ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("socialActivity.");
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

					query.append("socialActivity.createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<SocialActivity>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SocialActivity>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialActivity>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (SocialActivity socialActivity : findByGroupId(groupId)) {
			remove(socialActivity);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (SocialActivity socialActivity : findByCompanyId(companyId)) {
			remove(socialActivity);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (SocialActivity socialActivity : findByUserId(userId)) {
			remove(socialActivity);
		}
	}

	public void removeByMirrorActivityId(long mirrorActivityId)
		throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = findByMirrorActivityId(mirrorActivityId);

		remove(socialActivity);
	}

	public void removeByClassNameId(long classNameId) throws SystemException {
		for (SocialActivity socialActivity : findByClassNameId(classNameId)) {
			remove(socialActivity);
		}
	}

	public void removeByReceiverUserId(long receiverUserId)
		throws SystemException {
		for (SocialActivity socialActivity : findByReceiverUserId(
				receiverUserId)) {
			remove(socialActivity);
		}
	}

	public void removeByC_C(long classNameId, long classPK)
		throws SystemException {
		for (SocialActivity socialActivity : findByC_C(classNameId, classPK)) {
			remove(socialActivity);
		}
	}

	public void removeByM_C_C(long mirrorActivityId, long classNameId,
		long classPK) throws SystemException {
		for (SocialActivity socialActivity : findByM_C_C(mirrorActivityId,
				classNameId, classPK)) {
			remove(socialActivity);
		}
	}

	public void removeByG_U_CD_C_C_T_R(long groupId, long userId,
		long createDate, long classNameId, long classPK, int type,
		long receiverUserId) throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = findByG_U_CD_C_C_T_R(groupId, userId,
				createDate, classNameId, classPK, type, receiverUserId);

		remove(socialActivity);
	}

	public void removeAll() throws SystemException {
		for (SocialActivity socialActivity : findAll()) {
			remove(socialActivity);
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);
				query.append("SELECT COUNT(socialActivity) ");
				query.append("FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
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
				query.append("SELECT COUNT(socialActivity) ");
				query.append("FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.companyId = ?");

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

	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);
				query.append("SELECT COUNT(socialActivity) ");
				query.append("FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

	public int countByMirrorActivityId(long mirrorActivityId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(mirrorActivityId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_MIRRORACTIVITYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);
				query.append("SELECT COUNT(socialActivity) ");
				query.append("FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.mirrorActivityId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(mirrorActivityId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_MIRRORACTIVITYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByClassNameId(long classNameId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(classNameId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CLASSNAMEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);
				query.append("SELECT COUNT(socialActivity) ");
				query.append("FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.classNameId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CLASSNAMEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByReceiverUserId(long receiverUserId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(receiverUserId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_RECEIVERUSERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);
				query.append("SELECT COUNT(socialActivity) ");
				query.append("FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.receiverUserId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(receiverUserId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_RECEIVERUSERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
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

				StringBundler query = new StringBundler(6);
				query.append("SELECT COUNT(socialActivity) ");
				query.append("FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.classNameId = ?");

				query.append(" AND ");

				query.append("socialActivity.classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

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

	public int countByM_C_C(long mirrorActivityId, long classNameId,
		long classPK) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(mirrorActivityId), new Long(classNameId),
				new Long(classPK)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_M_C_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(8);
				query.append("SELECT COUNT(socialActivity) ");
				query.append("FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.mirrorActivityId = ?");

				query.append(" AND ");

				query.append("socialActivity.classNameId = ?");

				query.append(" AND ");

				query.append("socialActivity.classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(mirrorActivityId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_M_C_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_U_CD_C_C_T_R(long groupId, long userId,
		long createDate, long classNameId, long classPK, int type,
		long receiverUserId) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId), new Long(createDate),
				new Long(classNameId), new Long(classPK), new Integer(type),
				new Long(receiverUserId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U_CD_C_C_T_R,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(16);
				query.append("SELECT COUNT(socialActivity) ");
				query.append("FROM SocialActivity socialActivity WHERE ");

				query.append("socialActivity.groupId = ?");

				query.append(" AND ");

				query.append("socialActivity.userId = ?");

				query.append(" AND ");

				query.append("socialActivity.createDate = ?");

				query.append(" AND ");

				query.append("socialActivity.classNameId = ?");

				query.append(" AND ");

				query.append("socialActivity.classPK = ?");

				query.append(" AND ");

				query.append("socialActivity.type = ?");

				query.append(" AND ");

				query.append("socialActivity.receiverUserId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(createDate);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(receiverUserId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U_CD_C_C_T_R,
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
						"SELECT COUNT(socialActivity) FROM SocialActivity socialActivity");

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
						"value.object.listener.com.liferay.portlet.social.model.SocialActivity")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialActivity>> listenersList = new ArrayList<ModelListener<SocialActivity>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialActivity>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialActivityPersistence")
	protected com.liferay.portlet.social.service.persistence.SocialActivityPersistence socialActivityPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialRelationPersistence")
	protected com.liferay.portlet.social.service.persistence.SocialRelationPersistence socialRelationPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialRequestPersistence")
	protected com.liferay.portlet.social.service.persistence.SocialRequestPersistence socialRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static Log _log = LogFactoryUtil.getLog(SocialActivityPersistenceImpl.class);
}