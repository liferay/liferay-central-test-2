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

package com.liferay.portlet.expando.service.persistence;

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

import com.liferay.portlet.expando.NoSuchValueException;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.model.impl.ExpandoValueImpl;
import com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ExpandoValuePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoValuePersistence
 * @see       ExpandoValueUtil
 * @generated
 */
public class ExpandoValuePersistenceImpl extends BasePersistenceImpl<ExpandoValue>
	implements ExpandoValuePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ExpandoValueImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_TABLEID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByTableId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_TABLEID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByTableId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_TABLEID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByTableId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COLUMNID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByColumnId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COLUMNID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByColumnId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COLUMNID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByColumnId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_ROWID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByRowId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_ROWID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByRowId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ROWID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByRowId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_T_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByT_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_T_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByT_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_T_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByT_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_T_CPK = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByT_CPK",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_T_CPK = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByT_CPK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_T_CPK = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByT_CPK",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_T_R = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByT_R",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_T_R = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByT_R",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_T_R = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByT_R",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_R = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_R",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_R = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_R",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_T_C_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByT_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_T_C_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByT_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_T_C_D = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByT_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_T_C_D = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByT_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_T_C_D = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByT_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(ExpandoValue expandoValue) {
		EntityCacheUtil.putResult(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueImpl.class, expandoValue.getPrimaryKey(), expandoValue);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_R,
			new Object[] {
				new Long(expandoValue.getColumnId()),
				new Long(expandoValue.getRowId())
			}, expandoValue);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C_C,
			new Object[] {
				new Long(expandoValue.getTableId()),
				new Long(expandoValue.getColumnId()),
				new Long(expandoValue.getClassPK())
			}, expandoValue);
	}

	public void cacheResult(List<ExpandoValue> expandoValues) {
		for (ExpandoValue expandoValue : expandoValues) {
			if (EntityCacheUtil.getResult(
						ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
						ExpandoValueImpl.class, expandoValue.getPrimaryKey(),
						this) == null) {
				cacheResult(expandoValue);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(ExpandoValueImpl.class.getName());
		EntityCacheUtil.clearCache(ExpandoValueImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public ExpandoValue create(long valueId) {
		ExpandoValue expandoValue = new ExpandoValueImpl();

		expandoValue.setNew(true);
		expandoValue.setPrimaryKey(valueId);

		return expandoValue;
	}

	public ExpandoValue remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public ExpandoValue remove(long valueId)
		throws NoSuchValueException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ExpandoValue expandoValue = (ExpandoValue)session.get(ExpandoValueImpl.class,
					new Long(valueId));

			if (expandoValue == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + valueId);
				}

				throw new NoSuchValueException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					valueId);
			}

			return remove(expandoValue);
		}
		catch (NoSuchValueException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoValue remove(ExpandoValue expandoValue)
		throws SystemException {
		for (ModelListener<ExpandoValue> listener : listeners) {
			listener.onBeforeRemove(expandoValue);
		}

		expandoValue = removeImpl(expandoValue);

		for (ModelListener<ExpandoValue> listener : listeners) {
			listener.onAfterRemove(expandoValue);
		}

		return expandoValue;
	}

	protected ExpandoValue removeImpl(ExpandoValue expandoValue)
		throws SystemException {
		expandoValue = toUnwrappedModel(expandoValue);

		Session session = null;

		try {
			session = openSession();

			if (expandoValue.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ExpandoValueImpl.class,
						expandoValue.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(expandoValue);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		ExpandoValueModelImpl expandoValueModelImpl = (ExpandoValueModelImpl)expandoValue;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_R,
			new Object[] {
				new Long(expandoValueModelImpl.getOriginalColumnId()),
				new Long(expandoValueModelImpl.getOriginalRowId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_C_C,
			new Object[] {
				new Long(expandoValueModelImpl.getOriginalTableId()),
				new Long(expandoValueModelImpl.getOriginalColumnId()),
				new Long(expandoValueModelImpl.getOriginalClassPK())
			});

		EntityCacheUtil.removeResult(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueImpl.class, expandoValue.getPrimaryKey());

		return expandoValue;
	}

	public ExpandoValue updateImpl(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue,
		boolean merge) throws SystemException {
		expandoValue = toUnwrappedModel(expandoValue);

		boolean isNew = expandoValue.isNew();

		ExpandoValueModelImpl expandoValueModelImpl = (ExpandoValueModelImpl)expandoValue;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, expandoValue, merge);

			expandoValue.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueImpl.class, expandoValue.getPrimaryKey(), expandoValue);

		if (!isNew &&
				((expandoValue.getColumnId() != expandoValueModelImpl.getOriginalColumnId()) ||
				(expandoValue.getRowId() != expandoValueModelImpl.getOriginalRowId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_R,
				new Object[] {
					new Long(expandoValueModelImpl.getOriginalColumnId()),
					new Long(expandoValueModelImpl.getOriginalRowId())
				});
		}

		if (isNew ||
				((expandoValue.getColumnId() != expandoValueModelImpl.getOriginalColumnId()) ||
				(expandoValue.getRowId() != expandoValueModelImpl.getOriginalRowId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_R,
				new Object[] {
					new Long(expandoValue.getColumnId()),
					new Long(expandoValue.getRowId())
				}, expandoValue);
		}

		if (!isNew &&
				((expandoValue.getTableId() != expandoValueModelImpl.getOriginalTableId()) ||
				(expandoValue.getColumnId() != expandoValueModelImpl.getOriginalColumnId()) ||
				(expandoValue.getClassPK() != expandoValueModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_C_C,
				new Object[] {
					new Long(expandoValueModelImpl.getOriginalTableId()),
					new Long(expandoValueModelImpl.getOriginalColumnId()),
					new Long(expandoValueModelImpl.getOriginalClassPK())
				});
		}

		if (isNew ||
				((expandoValue.getTableId() != expandoValueModelImpl.getOriginalTableId()) ||
				(expandoValue.getColumnId() != expandoValueModelImpl.getOriginalColumnId()) ||
				(expandoValue.getClassPK() != expandoValueModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C_C,
				new Object[] {
					new Long(expandoValue.getTableId()),
					new Long(expandoValue.getColumnId()),
					new Long(expandoValue.getClassPK())
				}, expandoValue);
		}

		return expandoValue;
	}

	protected ExpandoValue toUnwrappedModel(ExpandoValue expandoValue) {
		if (expandoValue instanceof ExpandoValueImpl) {
			return expandoValue;
		}

		ExpandoValueImpl expandoValueImpl = new ExpandoValueImpl();

		expandoValueImpl.setNew(expandoValue.isNew());
		expandoValueImpl.setPrimaryKey(expandoValue.getPrimaryKey());

		expandoValueImpl.setValueId(expandoValue.getValueId());
		expandoValueImpl.setCompanyId(expandoValue.getCompanyId());
		expandoValueImpl.setTableId(expandoValue.getTableId());
		expandoValueImpl.setColumnId(expandoValue.getColumnId());
		expandoValueImpl.setRowId(expandoValue.getRowId());
		expandoValueImpl.setClassNameId(expandoValue.getClassNameId());
		expandoValueImpl.setClassPK(expandoValue.getClassPK());
		expandoValueImpl.setData(expandoValue.getData());

		return expandoValueImpl;
	}

	public ExpandoValue findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ExpandoValue findByPrimaryKey(long valueId)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByPrimaryKey(valueId);

		if (expandoValue == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + valueId);
			}

			throw new NoSuchValueException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				valueId);
		}

		return expandoValue;
	}

	public ExpandoValue fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public ExpandoValue fetchByPrimaryKey(long valueId)
		throws SystemException {
		ExpandoValue expandoValue = (ExpandoValue)EntityCacheUtil.getResult(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
				ExpandoValueImpl.class, valueId, this);

		if (expandoValue == null) {
			Session session = null;

			try {
				session = openSession();

				expandoValue = (ExpandoValue)session.get(ExpandoValueImpl.class,
						new Long(valueId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (expandoValue != null) {
					cacheResult(expandoValue);
				}

				closeSession(session);
			}
		}

		return expandoValue;
	}

	public List<ExpandoValue> findByTableId(long tableId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId) };

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_TABLEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_TABLEID_TABLEID_2);

				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_TABLEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ExpandoValue> findByTableId(long tableId, int start, int end)
		throws SystemException {
		return findByTableId(tableId, start, end, null);
	}

	public List<ExpandoValue> findByTableId(long tableId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tableId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_TABLEID,
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

				query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_TABLEID_TABLEID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_TABLEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ExpandoValue findByTableId_First(long tableId, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		List<ExpandoValue> list = findByTableId(tableId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tableId=");
			msg.append(tableId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByTableId_Last(long tableId, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		int count = countByTableId(tableId);

		List<ExpandoValue> list = findByTableId(tableId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tableId=");
			msg.append(tableId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByTableId_PrevAndNext(long valueId, long tableId,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByTableId(tableId);

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

			query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_TABLEID_TABLEID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(tableId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoValue> findByColumnId(long columnId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(columnId) };

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COLUMNID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_COLUMNID_COLUMNID_2);

				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(columnId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COLUMNID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ExpandoValue> findByColumnId(long columnId, int start, int end)
		throws SystemException {
		return findByColumnId(columnId, start, end, null);
	}

	public List<ExpandoValue> findByColumnId(long columnId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(columnId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COLUMNID,
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

				query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_COLUMNID_COLUMNID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(columnId);

				list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COLUMNID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ExpandoValue findByColumnId_First(long columnId,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		List<ExpandoValue> list = findByColumnId(columnId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("columnId=");
			msg.append(columnId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByColumnId_Last(long columnId, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		int count = countByColumnId(columnId);

		List<ExpandoValue> list = findByColumnId(columnId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("columnId=");
			msg.append(columnId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByColumnId_PrevAndNext(long valueId,
		long columnId, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByColumnId(columnId);

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

			query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_COLUMNID_COLUMNID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(columnId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoValue> findByRowId(long rowId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(rowId) };

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ROWID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_ROWID_ROWID_2);

				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(rowId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ROWID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ExpandoValue> findByRowId(long rowId, int start, int end)
		throws SystemException {
		return findByRowId(rowId, start, end, null);
	}

	public List<ExpandoValue> findByRowId(long rowId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(rowId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_ROWID,
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

				query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_ROWID_ROWID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(rowId);

				list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_ROWID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ExpandoValue findByRowId_First(long rowId, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		List<ExpandoValue> list = findByRowId(rowId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("rowId=");
			msg.append(rowId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByRowId_Last(long rowId, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		int count = countByRowId(rowId);

		List<ExpandoValue> list = findByRowId(rowId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("rowId=");
			msg.append(rowId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByRowId_PrevAndNext(long valueId, long rowId,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByRowId(rowId);

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

			query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_ROWID_ROWID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(rowId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoValue> findByT_C(long tableId, long columnId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId), new Long(columnId) };

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_T_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_T_C_TABLEID_2);

				query.append(_FINDER_COLUMN_T_C_COLUMNID_2);

				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_T_C, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ExpandoValue> findByT_C(long tableId, long columnId, int start,
		int end) throws SystemException {
		return findByT_C(tableId, columnId, start, end, null);
	}

	public List<ExpandoValue> findByT_C(long tableId, long columnId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tableId), new Long(columnId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_T_C,
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

				query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_T_C_TABLEID_2);

				query.append(_FINDER_COLUMN_T_C_COLUMNID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_T_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ExpandoValue findByT_C_First(long tableId, long columnId,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		List<ExpandoValue> list = findByT_C(tableId, columnId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tableId=");
			msg.append(tableId);

			msg.append(", columnId=");
			msg.append(columnId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByT_C_Last(long tableId, long columnId,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		int count = countByT_C(tableId, columnId);

		List<ExpandoValue> list = findByT_C(tableId, columnId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tableId=");
			msg.append(tableId);

			msg.append(", columnId=");
			msg.append(columnId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByT_C_PrevAndNext(long valueId, long tableId,
		long columnId, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByT_C(tableId, columnId);

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

			query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_T_C_TABLEID_2);

			query.append(_FINDER_COLUMN_T_C_COLUMNID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(tableId);

			qPos.add(columnId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoValue> findByT_CPK(long tableId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId), new Long(classPK) };

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_T_CPK,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_T_CPK_TABLEID_2);

				query.append(_FINDER_COLUMN_T_CPK_CLASSPK_2);

				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(classPK);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_T_CPK,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ExpandoValue> findByT_CPK(long tableId, long classPK,
		int start, int end) throws SystemException {
		return findByT_CPK(tableId, classPK, start, end, null);
	}

	public List<ExpandoValue> findByT_CPK(long tableId, long classPK,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tableId), new Long(classPK),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_T_CPK,
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

				query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_T_CPK_TABLEID_2);

				query.append(_FINDER_COLUMN_T_CPK_CLASSPK_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(classPK);

				list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_T_CPK,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ExpandoValue findByT_CPK_First(long tableId, long classPK,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		List<ExpandoValue> list = findByT_CPK(tableId, classPK, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tableId=");
			msg.append(tableId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByT_CPK_Last(long tableId, long classPK,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		int count = countByT_CPK(tableId, classPK);

		List<ExpandoValue> list = findByT_CPK(tableId, classPK, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tableId=");
			msg.append(tableId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByT_CPK_PrevAndNext(long valueId, long tableId,
		long classPK, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByT_CPK(tableId, classPK);

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

			query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_T_CPK_TABLEID_2);

			query.append(_FINDER_COLUMN_T_CPK_CLASSPK_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(tableId);

			qPos.add(classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoValue> findByT_R(long tableId, long rowId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId), new Long(rowId) };

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_T_R,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_T_R_TABLEID_2);

				query.append(_FINDER_COLUMN_T_R_ROWID_2);

				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(rowId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_T_R, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ExpandoValue> findByT_R(long tableId, long rowId, int start,
		int end) throws SystemException {
		return findByT_R(tableId, rowId, start, end, null);
	}

	public List<ExpandoValue> findByT_R(long tableId, long rowId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tableId), new Long(rowId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_T_R,
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

				query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_T_R_TABLEID_2);

				query.append(_FINDER_COLUMN_T_R_ROWID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(rowId);

				list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_T_R,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ExpandoValue findByT_R_First(long tableId, long rowId,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		List<ExpandoValue> list = findByT_R(tableId, rowId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tableId=");
			msg.append(tableId);

			msg.append(", rowId=");
			msg.append(rowId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByT_R_Last(long tableId, long rowId,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		int count = countByT_R(tableId, rowId);

		List<ExpandoValue> list = findByT_R(tableId, rowId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tableId=");
			msg.append(tableId);

			msg.append(", rowId=");
			msg.append(rowId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByT_R_PrevAndNext(long valueId, long tableId,
		long rowId, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByT_R(tableId, rowId);

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

			query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_T_R_TABLEID_2);

			query.append(_FINDER_COLUMN_T_R_ROWID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(tableId);

			qPos.add(rowId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoValue findByC_R(long columnId, long rowId)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByC_R(columnId, rowId);

		if (expandoValue == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("columnId=");
			msg.append(columnId);

			msg.append(", rowId=");
			msg.append(rowId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchValueException(msg.toString());
		}

		return expandoValue;
	}

	public ExpandoValue fetchByC_R(long columnId, long rowId)
		throws SystemException {
		return fetchByC_R(columnId, rowId, true);
	}

	public ExpandoValue fetchByC_R(long columnId, long rowId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(columnId), new Long(rowId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_R,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_C_R_COLUMNID_2);

				query.append(_FINDER_COLUMN_C_R_ROWID_2);

				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(columnId);

				qPos.add(rowId);

				List<ExpandoValue> list = q.list();

				result = list;

				ExpandoValue expandoValue = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_R,
						finderArgs, list);
				}
				else {
					expandoValue = list.get(0);

					cacheResult(expandoValue);

					if ((expandoValue.getColumnId() != columnId) ||
							(expandoValue.getRowId() != rowId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_R,
							finderArgs, expandoValue);
					}
				}

				return expandoValue;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_R,
						finderArgs, new ArrayList<ExpandoValue>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (ExpandoValue)result;
			}
		}
	}

	public List<ExpandoValue> findByC_C(long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

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
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_C, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ExpandoValue> findByC_C(long classNameId, long classPK,
		int start, int end) throws SystemException {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	public List<ExpandoValue> findByC_C(long classNameId, long classPK,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_C,
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

				query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

				query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ExpandoValue findByC_C_First(long classNameId, long classPK,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		List<ExpandoValue> list = findByC_C(classNameId, classPK, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByC_C_Last(long classNameId, long classPK,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		int count = countByC_C(classNameId, classPK);

		List<ExpandoValue> list = findByC_C(classNameId, classPK, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByC_C_PrevAndNext(long valueId, long classNameId,
		long classPK, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByC_C(classNameId, classPK);

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

			query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			qPos.add(classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoValue findByT_C_C(long tableId, long columnId, long classPK)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByT_C_C(tableId, columnId, classPK);

		if (expandoValue == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tableId=");
			msg.append(tableId);

			msg.append(", columnId=");
			msg.append(columnId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchValueException(msg.toString());
		}

		return expandoValue;
	}

	public ExpandoValue fetchByT_C_C(long tableId, long columnId, long classPK)
		throws SystemException {
		return fetchByT_C_C(tableId, columnId, classPK, true);
	}

	public ExpandoValue fetchByT_C_C(long tableId, long columnId, long classPK,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tableId), new Long(columnId), new Long(classPK)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_T_C_C,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_T_C_C_TABLEID_2);

				query.append(_FINDER_COLUMN_T_C_C_COLUMNID_2);

				query.append(_FINDER_COLUMN_T_C_C_CLASSPK_2);

				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				qPos.add(classPK);

				List<ExpandoValue> list = q.list();

				result = list;

				ExpandoValue expandoValue = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C_C,
						finderArgs, list);
				}
				else {
					expandoValue = list.get(0);

					cacheResult(expandoValue);

					if ((expandoValue.getTableId() != tableId) ||
							(expandoValue.getColumnId() != columnId) ||
							(expandoValue.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C_C,
							finderArgs, expandoValue);
					}
				}

				return expandoValue;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C_C,
						finderArgs, new ArrayList<ExpandoValue>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (ExpandoValue)result;
			}
		}
	}

	public List<ExpandoValue> findByT_C_D(long tableId, long columnId,
		String data) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tableId), new Long(columnId),
				
				data
			};

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_T_C_D,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_T_C_D_TABLEID_2);

				query.append(_FINDER_COLUMN_T_C_D_COLUMNID_2);

				if (data == null) {
					query.append(_FINDER_COLUMN_T_C_D_DATA_1);
				}
				else {
					if (data.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_T_C_D_DATA_3);
					}
					else {
						query.append(_FINDER_COLUMN_T_C_D_DATA_2);
					}
				}

				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				if (data != null) {
					qPos.add(data);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_T_C_D,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ExpandoValue> findByT_C_D(long tableId, long columnId,
		String data, int start, int end) throws SystemException {
		return findByT_C_D(tableId, columnId, data, start, end, null);
	}

	public List<ExpandoValue> findByT_C_D(long tableId, long columnId,
		String data, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tableId), new Long(columnId),
				
				data,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_T_C_D,
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

				query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_T_C_D_TABLEID_2);

				query.append(_FINDER_COLUMN_T_C_D_COLUMNID_2);

				if (data == null) {
					query.append(_FINDER_COLUMN_T_C_D_DATA_1);
				}
				else {
					if (data.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_T_C_D_DATA_3);
					}
					else {
						query.append(_FINDER_COLUMN_T_C_D_DATA_2);
					}
				}

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				if (data != null) {
					qPos.add(data);
				}

				list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_T_C_D,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ExpandoValue findByT_C_D_First(long tableId, long columnId,
		String data, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		List<ExpandoValue> list = findByT_C_D(tableId, columnId, data, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tableId=");
			msg.append(tableId);

			msg.append(", columnId=");
			msg.append(columnId);

			msg.append(", data=");
			msg.append(data);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByT_C_D_Last(long tableId, long columnId,
		String data, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		int count = countByT_C_D(tableId, columnId, data);

		List<ExpandoValue> list = findByT_C_D(tableId, columnId, data,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tableId=");
			msg.append(tableId);

			msg.append(", columnId=");
			msg.append(columnId);

			msg.append(", data=");
			msg.append(data);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByT_C_D_PrevAndNext(long valueId, long tableId,
		long columnId, String data, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByT_C_D(tableId, columnId, data);

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

			query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_T_C_D_TABLEID_2);

			query.append(_FINDER_COLUMN_T_C_D_COLUMNID_2);

			if (data == null) {
				query.append(_FINDER_COLUMN_T_C_D_DATA_1);
			}
			else {
				if (data.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_T_C_D_DATA_3);
				}
				else {
					query.append(_FINDER_COLUMN_T_C_D_DATA_2);
				}
			}

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(tableId);

			qPos.add(columnId);

			if (data != null) {
				qPos.add(data);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

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

	public List<ExpandoValue> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ExpandoValue> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ExpandoValue> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_EXPANDOVALUE);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_EXPANDOVALUE.concat(ExpandoValueModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ExpandoValue>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByTableId(long tableId) throws SystemException {
		for (ExpandoValue expandoValue : findByTableId(tableId)) {
			remove(expandoValue);
		}
	}

	public void removeByColumnId(long columnId) throws SystemException {
		for (ExpandoValue expandoValue : findByColumnId(columnId)) {
			remove(expandoValue);
		}
	}

	public void removeByRowId(long rowId) throws SystemException {
		for (ExpandoValue expandoValue : findByRowId(rowId)) {
			remove(expandoValue);
		}
	}

	public void removeByT_C(long tableId, long columnId)
		throws SystemException {
		for (ExpandoValue expandoValue : findByT_C(tableId, columnId)) {
			remove(expandoValue);
		}
	}

	public void removeByT_CPK(long tableId, long classPK)
		throws SystemException {
		for (ExpandoValue expandoValue : findByT_CPK(tableId, classPK)) {
			remove(expandoValue);
		}
	}

	public void removeByT_R(long tableId, long rowId) throws SystemException {
		for (ExpandoValue expandoValue : findByT_R(tableId, rowId)) {
			remove(expandoValue);
		}
	}

	public void removeByC_R(long columnId, long rowId)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByC_R(columnId, rowId);

		remove(expandoValue);
	}

	public void removeByC_C(long classNameId, long classPK)
		throws SystemException {
		for (ExpandoValue expandoValue : findByC_C(classNameId, classPK)) {
			remove(expandoValue);
		}
	}

	public void removeByT_C_C(long tableId, long columnId, long classPK)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByT_C_C(tableId, columnId, classPK);

		remove(expandoValue);
	}

	public void removeByT_C_D(long tableId, long columnId, String data)
		throws SystemException {
		for (ExpandoValue expandoValue : findByT_C_D(tableId, columnId, data)) {
			remove(expandoValue);
		}
	}

	public void removeAll() throws SystemException {
		for (ExpandoValue expandoValue : findAll()) {
			remove(expandoValue);
		}
	}

	public int countByTableId(long tableId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_TABLEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_TABLEID_TABLEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_TABLEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByColumnId(long columnId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(columnId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COLUMNID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_COLUMNID_COLUMNID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(columnId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COLUMNID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByRowId(long rowId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(rowId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ROWID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_ROWID_ROWID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(rowId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ROWID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByT_C(long tableId, long columnId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId), new Long(columnId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_T_C_TABLEID_2);

				query.append(_FINDER_COLUMN_T_C_COLUMNID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByT_CPK(long tableId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId), new Long(classPK) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_CPK,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_T_CPK_TABLEID_2);

				query.append(_FINDER_COLUMN_T_CPK_CLASSPK_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_CPK,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByT_R(long tableId, long rowId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId), new Long(rowId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_R,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_T_R_TABLEID_2);

				query.append(_FINDER_COLUMN_T_R_ROWID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(rowId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_R, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_R(long columnId, long rowId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(columnId), new Long(rowId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_R,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_C_R_COLUMNID_2);

				query.append(_FINDER_COLUMN_C_R_ROWID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(columnId);

				qPos.add(rowId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_R, finderArgs,
					count);

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

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

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

	public int countByT_C_C(long tableId, long columnId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tableId), new Long(columnId), new Long(classPK)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_C_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_T_C_C_TABLEID_2);

				query.append(_FINDER_COLUMN_T_C_C_COLUMNID_2);

				query.append(_FINDER_COLUMN_T_C_C_CLASSPK_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_C_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByT_C_D(long tableId, long columnId, String data)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tableId), new Long(columnId),
				
				data
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_C_D,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

				query.append(_FINDER_COLUMN_T_C_D_TABLEID_2);

				query.append(_FINDER_COLUMN_T_C_D_COLUMNID_2);

				if (data == null) {
					query.append(_FINDER_COLUMN_T_C_D_DATA_1);
				}
				else {
					if (data.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_T_C_D_DATA_3);
					}
					else {
						query.append(_FINDER_COLUMN_T_C_D_DATA_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				if (data != null) {
					qPos.add(data);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_C_D,
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

				Query q = session.createQuery(_SQL_COUNT_EXPANDOVALUE);

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
						"value.object.listener.com.liferay.portlet.expando.model.ExpandoValue")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ExpandoValue>> listenersList = new ArrayList<ModelListener<ExpandoValue>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ExpandoValue>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoColumnPersistence")
	protected com.liferay.portlet.expando.service.persistence.ExpandoColumnPersistence expandoColumnPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoRowPersistence")
	protected com.liferay.portlet.expando.service.persistence.ExpandoRowPersistence expandoRowPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoTablePersistence")
	protected com.liferay.portlet.expando.service.persistence.ExpandoTablePersistence expandoTablePersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static final String _SQL_SELECT_EXPANDOVALUE = "SELECT expandoValue FROM ExpandoValue expandoValue";
	private static final String _SQL_SELECT_EXPANDOVALUE_WHERE = "SELECT expandoValue FROM ExpandoValue expandoValue WHERE ";
	private static final String _SQL_COUNT_EXPANDOVALUE = "SELECT COUNT(expandoValue) FROM ExpandoValue expandoValue";
	private static final String _SQL_COUNT_EXPANDOVALUE_WHERE = "SELECT COUNT(expandoValue) FROM ExpandoValue expandoValue WHERE ";
	private static final String _FINDER_COLUMN_TABLEID_TABLEID_2 = "expandoValue.tableId = ?";
	private static final String _FINDER_COLUMN_COLUMNID_COLUMNID_2 = "expandoValue.columnId = ?";
	private static final String _FINDER_COLUMN_ROWID_ROWID_2 = "expandoValue.rowId = ?";
	private static final String _FINDER_COLUMN_T_C_TABLEID_2 = "expandoValue.tableId = ? AND ";
	private static final String _FINDER_COLUMN_T_C_COLUMNID_2 = "expandoValue.columnId = ?";
	private static final String _FINDER_COLUMN_T_CPK_TABLEID_2 = "expandoValue.tableId = ? AND ";
	private static final String _FINDER_COLUMN_T_CPK_CLASSPK_2 = "expandoValue.classPK = ?";
	private static final String _FINDER_COLUMN_T_R_TABLEID_2 = "expandoValue.tableId = ? AND ";
	private static final String _FINDER_COLUMN_T_R_ROWID_2 = "expandoValue.rowId = ?";
	private static final String _FINDER_COLUMN_C_R_COLUMNID_2 = "expandoValue.columnId = ? AND ";
	private static final String _FINDER_COLUMN_C_R_ROWID_2 = "expandoValue.rowId = ?";
	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "expandoValue.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "expandoValue.classPK = ?";
	private static final String _FINDER_COLUMN_T_C_C_TABLEID_2 = "expandoValue.tableId = ? AND ";
	private static final String _FINDER_COLUMN_T_C_C_COLUMNID_2 = "expandoValue.columnId = ? AND ";
	private static final String _FINDER_COLUMN_T_C_C_CLASSPK_2 = "expandoValue.classPK = ?";
	private static final String _FINDER_COLUMN_T_C_D_TABLEID_2 = "expandoValue.tableId = ? AND ";
	private static final String _FINDER_COLUMN_T_C_D_COLUMNID_2 = "expandoValue.columnId = ? AND ";
	private static final String _FINDER_COLUMN_T_C_D_DATA_1 = "expandoValuedata IS NULL";
	private static final String _FINDER_COLUMN_T_C_D_DATA_2 = "expandoValue.data = ?";
	private static final String _FINDER_COLUMN_T_C_D_DATA_3 = "(expandoValuedata IS NULL OR expandoValue.data = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "expandoValue.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ExpandoValue exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ExpandoValue exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(ExpandoValuePersistenceImpl.class);
}