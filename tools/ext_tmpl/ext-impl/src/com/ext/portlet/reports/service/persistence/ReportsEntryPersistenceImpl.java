package com.ext.portlet.reports.service.persistence;

import com.ext.portlet.reports.NoSuchEntryException;
import com.ext.portlet.reports.model.ReportsEntry;
import com.ext.portlet.reports.model.impl.ReportsEntryImpl;
import com.ext.portlet.reports.model.impl.ReportsEntryModelImpl;

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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * <a href="ReportsEntryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ReportsEntryPersistence
 * @see       ReportsEntryUtil
 * @generated
 */
public class ReportsEntryPersistenceImpl extends BasePersistenceImpl<ReportsEntry>
    implements ReportsEntryPersistence {
    public static final String FINDER_CLASS_NAME_ENTITY = ReportsEntryImpl.class.getName();
    public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
        ".List";
    public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(ReportsEntryModelImpl.ENTITY_CACHE_ENABLED,
            ReportsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findByCompanyId", new String[] { String.class.getName() });
    public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(ReportsEntryModelImpl.ENTITY_CACHE_ENABLED,
            ReportsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findByCompanyId",
            new String[] {
                String.class.getName(),
                
            "java.lang.Integer", "java.lang.Integer",
                "com.liferay.portal.kernel.util.OrderByComparator"
            });
    public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(ReportsEntryModelImpl.ENTITY_CACHE_ENABLED,
            ReportsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countByCompanyId", new String[] { String.class.getName() });
    public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(ReportsEntryModelImpl.ENTITY_CACHE_ENABLED,
            ReportsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findByUserId", new String[] { String.class.getName() });
    public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID = new FinderPath(ReportsEntryModelImpl.ENTITY_CACHE_ENABLED,
            ReportsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findByUserId",
            new String[] {
                String.class.getName(),
                
            "java.lang.Integer", "java.lang.Integer",
                "com.liferay.portal.kernel.util.OrderByComparator"
            });
    public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(ReportsEntryModelImpl.ENTITY_CACHE_ENABLED,
            ReportsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countByUserId", new String[] { String.class.getName() });
    public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ReportsEntryModelImpl.ENTITY_CACHE_ENABLED,
            ReportsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ReportsEntryModelImpl.ENTITY_CACHE_ENABLED,
            ReportsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countAll", new String[0]);
    private static final String _SQL_SELECT_REPORTSENTRY = "SELECT reportsEntry FROM ReportsEntry reportsEntry";
    private static final String _SQL_SELECT_REPORTSENTRY_WHERE = "SELECT reportsEntry FROM ReportsEntry reportsEntry WHERE ";
    private static final String _SQL_COUNT_REPORTSENTRY = "SELECT COUNT(reportsEntry) FROM ReportsEntry reportsEntry";
    private static final String _SQL_COUNT_REPORTSENTRY_WHERE = "SELECT COUNT(reportsEntry) FROM ReportsEntry reportsEntry WHERE ";
    private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_1 = "reportsEntry.companyId IS NULL";
    private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "reportsEntry.companyId = ?";
    private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_3 = "(reportsEntry.companyId IS NULL OR reportsEntry.companyId = ?)";
    private static final String _FINDER_COLUMN_USERID_USERID_1 = "reportsEntry.userId IS NULL";
    private static final String _FINDER_COLUMN_USERID_USERID_2 = "reportsEntry.userId = ?";
    private static final String _FINDER_COLUMN_USERID_USERID_3 = "(reportsEntry.userId IS NULL OR reportsEntry.userId = ?)";
    private static final String _ORDER_BY_ENTITY_ALIAS = "reportsEntry.";
    private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ReportsEntry exists with the primary key ";
    private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ReportsEntry exists with the key {";
    private static Log _log = LogFactoryUtil.getLog(ReportsEntryPersistenceImpl.class);
    @BeanReference(name = "com.ext.portlet.reports.service.persistence.ReportsEntryPersistence")
    protected com.ext.portlet.reports.service.persistence.ReportsEntryPersistence reportsEntryPersistence;
    @BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
    protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
    @BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
    protected com.liferay.portal.service.persistence.UserPersistence userPersistence;

    public void cacheResult(ReportsEntry reportsEntry) {
        EntityCacheUtil.putResult(ReportsEntryModelImpl.ENTITY_CACHE_ENABLED,
            ReportsEntryImpl.class, reportsEntry.getPrimaryKey(), reportsEntry);
    }

    public void cacheResult(List<ReportsEntry> reportsEntries) {
        for (ReportsEntry reportsEntry : reportsEntries) {
            if (EntityCacheUtil.getResult(
                        ReportsEntryModelImpl.ENTITY_CACHE_ENABLED,
                        ReportsEntryImpl.class, reportsEntry.getPrimaryKey(),
                        this) == null) {
                cacheResult(reportsEntry);
            }
        }
    }

    public void clearCache() {
        CacheRegistry.clear(ReportsEntryImpl.class.getName());
        EntityCacheUtil.clearCache(ReportsEntryImpl.class.getName());
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
    }

    public ReportsEntry create(String entryId) {
        ReportsEntry reportsEntry = new ReportsEntryImpl();

        reportsEntry.setNew(true);
        reportsEntry.setPrimaryKey(entryId);

        return reportsEntry;
    }

    public ReportsEntry remove(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return remove((String) primaryKey);
    }

    public ReportsEntry remove(String entryId)
        throws NoSuchEntryException, SystemException {
        Session session = null;

        try {
            session = openSession();

            ReportsEntry reportsEntry = (ReportsEntry) session.get(ReportsEntryImpl.class,
                    entryId);

            if (reportsEntry == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + entryId);
                }

                throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                    entryId);
            }

            return remove(reportsEntry);
        } catch (NoSuchEntryException nsee) {
            throw nsee;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    public ReportsEntry remove(ReportsEntry reportsEntry)
        throws SystemException {
        for (ModelListener<ReportsEntry> listener : listeners) {
            listener.onBeforeRemove(reportsEntry);
        }

        reportsEntry = removeImpl(reportsEntry);

        for (ModelListener<ReportsEntry> listener : listeners) {
            listener.onAfterRemove(reportsEntry);
        }

        return reportsEntry;
    }

    protected ReportsEntry removeImpl(ReportsEntry reportsEntry)
        throws SystemException {
        reportsEntry = toUnwrappedModel(reportsEntry);

        Session session = null;

        try {
            session = openSession();

            if (reportsEntry.isCachedModel() || BatchSessionUtil.isEnabled()) {
                Object staleObject = session.get(ReportsEntryImpl.class,
                        reportsEntry.getPrimaryKeyObj());

                if (staleObject != null) {
                    session.evict(staleObject);
                }
            }

            session.delete(reportsEntry);

            session.flush();
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.removeResult(ReportsEntryModelImpl.ENTITY_CACHE_ENABLED,
            ReportsEntryImpl.class, reportsEntry.getPrimaryKey());

        return reportsEntry;
    }

    public ReportsEntry updateImpl(
        com.ext.portlet.reports.model.ReportsEntry reportsEntry, boolean merge)
        throws SystemException {
        reportsEntry = toUnwrappedModel(reportsEntry);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.update(session, reportsEntry, merge);

            reportsEntry.setNew(false);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.putResult(ReportsEntryModelImpl.ENTITY_CACHE_ENABLED,
            ReportsEntryImpl.class, reportsEntry.getPrimaryKey(), reportsEntry);

        return reportsEntry;
    }

    protected ReportsEntry toUnwrappedModel(ReportsEntry reportsEntry) {
        if (reportsEntry instanceof ReportsEntryImpl) {
            return reportsEntry;
        }

        ReportsEntryImpl reportsEntryImpl = new ReportsEntryImpl();

        reportsEntryImpl.setNew(reportsEntry.isNew());
        reportsEntryImpl.setPrimaryKey(reportsEntry.getPrimaryKey());

        reportsEntryImpl.setEntryId(reportsEntry.getEntryId());
        reportsEntryImpl.setCompanyId(reportsEntry.getCompanyId());
        reportsEntryImpl.setUserId(reportsEntry.getUserId());
        reportsEntryImpl.setUserName(reportsEntry.getUserName());
        reportsEntryImpl.setCreateDate(reportsEntry.getCreateDate());
        reportsEntryImpl.setModifiedDate(reportsEntry.getModifiedDate());
        reportsEntryImpl.setName(reportsEntry.getName());

        return reportsEntryImpl;
    }

    public ReportsEntry findByPrimaryKey(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return findByPrimaryKey((String) primaryKey);
    }

    public ReportsEntry findByPrimaryKey(String entryId)
        throws NoSuchEntryException, SystemException {
        ReportsEntry reportsEntry = fetchByPrimaryKey(entryId);

        if (reportsEntry == null) {
            if (_log.isWarnEnabled()) {
                _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + entryId);
            }

            throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                entryId);
        }

        return reportsEntry;
    }

    public ReportsEntry fetchByPrimaryKey(Serializable primaryKey)
        throws SystemException {
        return fetchByPrimaryKey((String) primaryKey);
    }

    public ReportsEntry fetchByPrimaryKey(String entryId)
        throws SystemException {
        ReportsEntry reportsEntry = (ReportsEntry) EntityCacheUtil.getResult(ReportsEntryModelImpl.ENTITY_CACHE_ENABLED,
                ReportsEntryImpl.class, entryId, this);

        if (reportsEntry == null) {
            Session session = null;

            try {
                session = openSession();

                reportsEntry = (ReportsEntry) session.get(ReportsEntryImpl.class,
                        entryId);
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (reportsEntry != null) {
                    cacheResult(reportsEntry);
                }

                closeSession(session);
            }
        }

        return reportsEntry;
    }

    public List<ReportsEntry> findByCompanyId(String companyId)
        throws SystemException {
        Object[] finderArgs = new Object[] { companyId };

        List<ReportsEntry> list = (List<ReportsEntry>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
                finderArgs, this);

        if (list == null) {
            Session session = null;

            try {
                session = openSession();

                StringBundler query = new StringBundler(3);

                query.append(_SQL_SELECT_REPORTSENTRY_WHERE);

                if (companyId == null) {
                    query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_1);
                } else {
                    if (companyId.equals(StringPool.BLANK)) {
                        query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_3);
                    } else {
                        query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);
                    }
                }

                query.append(ReportsEntryModelImpl.ORDER_BY_JPQL);

                String sql = query.toString();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                if (companyId != null) {
                    qPos.add(companyId);
                }

                list = q.list();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    list = new ArrayList<ReportsEntry>();
                }

                cacheResult(list);

                FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
                    finderArgs, list);

                closeSession(session);
            }
        }

        return list;
    }

    public List<ReportsEntry> findByCompanyId(String companyId, int start,
        int end) throws SystemException {
        return findByCompanyId(companyId, start, end, null);
    }

    public List<ReportsEntry> findByCompanyId(String companyId, int start,
        int end, OrderByComparator obc) throws SystemException {
        Object[] finderArgs = new Object[] {
                companyId,
                
                String.valueOf(start), String.valueOf(end), String.valueOf(obc)
            };

        List<ReportsEntry> list = (List<ReportsEntry>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
                finderArgs, this);

        if (list == null) {
            Session session = null;

            try {
                session = openSession();

                StringBundler query = null;

                if (obc != null) {
                    query = new StringBundler(3 +
                            (obc.getOrderByFields().length * 3));
                } else {
                    query = new StringBundler(3);
                }

                query.append(_SQL_SELECT_REPORTSENTRY_WHERE);

                if (companyId == null) {
                    query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_1);
                } else {
                    if (companyId.equals(StringPool.BLANK)) {
                        query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_3);
                    } else {
                        query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);
                    }
                }

                if (obc != null) {
                    appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
                }
                else {
                    query.append(ReportsEntryModelImpl.ORDER_BY_JPQL);
                }

                String sql = query.toString();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                if (companyId != null) {
                    qPos.add(companyId);
                }

                list = (List<ReportsEntry>) QueryUtil.list(q, getDialect(),
                        start, end);
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    list = new ArrayList<ReportsEntry>();
                }

                cacheResult(list);

                FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
                    finderArgs, list);

                closeSession(session);
            }
        }

        return list;
    }

    public ReportsEntry findByCompanyId_First(String companyId,
        OrderByComparator obc) throws NoSuchEntryException, SystemException {
        List<ReportsEntry> list = findByCompanyId(companyId, 0, 1, obc);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("companyId=");
            msg.append(companyId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchEntryException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    public ReportsEntry findByCompanyId_Last(String companyId,
        OrderByComparator obc) throws NoSuchEntryException, SystemException {
        int count = countByCompanyId(companyId);

        List<ReportsEntry> list = findByCompanyId(companyId, count - 1, count,
                obc);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("companyId=");
            msg.append(companyId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchEntryException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    public ReportsEntry[] findByCompanyId_PrevAndNext(String entryId,
        String companyId, OrderByComparator obc)
        throws NoSuchEntryException, SystemException {
        ReportsEntry reportsEntry = findByPrimaryKey(entryId);

        int count = countByCompanyId(companyId);

        Session session = null;

        try {
            session = openSession();

            StringBundler query = null;

            if (obc != null) {
                query = new StringBundler(3 +
                        (obc.getOrderByFields().length * 3));
            } else {
                query = new StringBundler(3);
            }

            query.append(_SQL_SELECT_REPORTSENTRY_WHERE);

            if (companyId == null) {
                query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_1);
            } else {
                if (companyId.equals(StringPool.BLANK)) {
                    query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_3);
                } else {
                    query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);
                }
            }

            if (obc != null) {
                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
            }
            else {
                query.append(ReportsEntryModelImpl.ORDER_BY_JPQL);
            }

            String sql = query.toString();

            Query q = session.createQuery(sql);

            QueryPos qPos = QueryPos.getInstance(q);

            if (companyId != null) {
                qPos.add(companyId);
            }

            Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
                    reportsEntry);

            ReportsEntry[] array = new ReportsEntryImpl[3];

            array[0] = (ReportsEntry) objArray[0];
            array[1] = (ReportsEntry) objArray[1];
            array[2] = (ReportsEntry) objArray[2];

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    public List<ReportsEntry> findByUserId(String userId)
        throws SystemException {
        Object[] finderArgs = new Object[] { userId };

        List<ReportsEntry> list = (List<ReportsEntry>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
                finderArgs, this);

        if (list == null) {
            Session session = null;

            try {
                session = openSession();

                StringBundler query = new StringBundler(3);

                query.append(_SQL_SELECT_REPORTSENTRY_WHERE);

                if (userId == null) {
                    query.append(_FINDER_COLUMN_USERID_USERID_1);
                } else {
                    if (userId.equals(StringPool.BLANK)) {
                        query.append(_FINDER_COLUMN_USERID_USERID_3);
                    } else {
                        query.append(_FINDER_COLUMN_USERID_USERID_2);
                    }
                }

                query.append(ReportsEntryModelImpl.ORDER_BY_JPQL);

                String sql = query.toString();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                if (userId != null) {
                    qPos.add(userId);
                }

                list = q.list();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    list = new ArrayList<ReportsEntry>();
                }

                cacheResult(list);

                FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
                    finderArgs, list);

                closeSession(session);
            }
        }

        return list;
    }

    public List<ReportsEntry> findByUserId(String userId, int start, int end)
        throws SystemException {
        return findByUserId(userId, start, end, null);
    }

    public List<ReportsEntry> findByUserId(String userId, int start, int end,
        OrderByComparator obc) throws SystemException {
        Object[] finderArgs = new Object[] {
                userId,
                
                String.valueOf(start), String.valueOf(end), String.valueOf(obc)
            };

        List<ReportsEntry> list = (List<ReportsEntry>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID,
                finderArgs, this);

        if (list == null) {
            Session session = null;

            try {
                session = openSession();

                StringBundler query = null;

                if (obc != null) {
                    query = new StringBundler(3 +
                            (obc.getOrderByFields().length * 3));
                } else {
                    query = new StringBundler(3);
                }

                query.append(_SQL_SELECT_REPORTSENTRY_WHERE);

                if (userId == null) {
                    query.append(_FINDER_COLUMN_USERID_USERID_1);
                } else {
                    if (userId.equals(StringPool.BLANK)) {
                        query.append(_FINDER_COLUMN_USERID_USERID_3);
                    } else {
                        query.append(_FINDER_COLUMN_USERID_USERID_2);
                    }
                }

                if (obc != null) {
                    appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
                }
                else {
                    query.append(ReportsEntryModelImpl.ORDER_BY_JPQL);
                }

                String sql = query.toString();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                if (userId != null) {
                    qPos.add(userId);
                }

                list = (List<ReportsEntry>) QueryUtil.list(q, getDialect(),
                        start, end);
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    list = new ArrayList<ReportsEntry>();
                }

                cacheResult(list);

                FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID,
                    finderArgs, list);

                closeSession(session);
            }
        }

        return list;
    }

    public ReportsEntry findByUserId_First(String userId, OrderByComparator obc)
        throws NoSuchEntryException, SystemException {
        List<ReportsEntry> list = findByUserId(userId, 0, 1, obc);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("userId=");
            msg.append(userId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchEntryException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    public ReportsEntry findByUserId_Last(String userId, OrderByComparator obc)
        throws NoSuchEntryException, SystemException {
        int count = countByUserId(userId);

        List<ReportsEntry> list = findByUserId(userId, count - 1, count, obc);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("userId=");
            msg.append(userId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchEntryException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    public ReportsEntry[] findByUserId_PrevAndNext(String entryId,
        String userId, OrderByComparator obc)
        throws NoSuchEntryException, SystemException {
        ReportsEntry reportsEntry = findByPrimaryKey(entryId);

        int count = countByUserId(userId);

        Session session = null;

        try {
            session = openSession();

            StringBundler query = null;

            if (obc != null) {
                query = new StringBundler(3 +
                        (obc.getOrderByFields().length * 3));
            } else {
                query = new StringBundler(3);
            }

            query.append(_SQL_SELECT_REPORTSENTRY_WHERE);

            if (userId == null) {
                query.append(_FINDER_COLUMN_USERID_USERID_1);
            } else {
                if (userId.equals(StringPool.BLANK)) {
                    query.append(_FINDER_COLUMN_USERID_USERID_3);
                } else {
                    query.append(_FINDER_COLUMN_USERID_USERID_2);
                }
            }

            if (obc != null) {
                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
            }
            else {
                query.append(ReportsEntryModelImpl.ORDER_BY_JPQL);
            }

            String sql = query.toString();

            Query q = session.createQuery(sql);

            QueryPos qPos = QueryPos.getInstance(q);

            if (userId != null) {
                qPos.add(userId);
            }

            Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
                    reportsEntry);

            ReportsEntry[] array = new ReportsEntryImpl[3];

            array[0] = (ReportsEntry) objArray[0];
            array[1] = (ReportsEntry) objArray[1];
            array[2] = (ReportsEntry) objArray[2];

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
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
        } catch (Exception e) {
            throw processException(e);
        } finally {
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
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    public List<ReportsEntry> findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    public List<ReportsEntry> findAll(int start, int end)
        throws SystemException {
        return findAll(start, end, null);
    }

    public List<ReportsEntry> findAll(int start, int end, OrderByComparator obc)
        throws SystemException {
        Object[] finderArgs = new Object[] {
                String.valueOf(start), String.valueOf(end), String.valueOf(obc)
            };

        List<ReportsEntry> list = (List<ReportsEntry>) FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

                    query.append(_SQL_SELECT_REPORTSENTRY);

                    appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

                    sql = query.toString();
                }
                else {
                    sql = _SQL_SELECT_REPORTSENTRY.concat(ReportsEntryModelImpl.ORDER_BY_JPQL);
                }

                Query q = session.createQuery(sql);

                if (obc == null) {
                    list = (List<ReportsEntry>) QueryUtil.list(q, getDialect(),
                            start, end, false);

                    Collections.sort(list);
                } else {
                    list = (List<ReportsEntry>) QueryUtil.list(q, getDialect(),
                            start, end);
                }
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    list = new ArrayList<ReportsEntry>();
                }

                cacheResult(list);

                FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

                closeSession(session);
            }
        }

        return list;
    }

    public void removeByCompanyId(String companyId) throws SystemException {
        for (ReportsEntry reportsEntry : findByCompanyId(companyId)) {
            remove(reportsEntry);
        }
    }

    public void removeByUserId(String userId) throws SystemException {
        for (ReportsEntry reportsEntry : findByUserId(userId)) {
            remove(reportsEntry);
        }
    }

    public void removeAll() throws SystemException {
        for (ReportsEntry reportsEntry : findAll()) {
            remove(reportsEntry);
        }
    }

    public int countByCompanyId(String companyId) throws SystemException {
        Object[] finderArgs = new Object[] { companyId };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
                finderArgs, this);

        if (count == null) {
            Session session = null;

            try {
                session = openSession();

                StringBundler query = new StringBundler(2);

                query.append(_SQL_COUNT_REPORTSENTRY_WHERE);

                if (companyId == null) {
                    query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_1);
                } else {
                    if (companyId.equals(StringPool.BLANK)) {
                        query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_3);
                    } else {
                        query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);
                    }
                }

                String sql = query.toString();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                if (companyId != null) {
                    qPos.add(companyId);
                }

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
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

    public int countByUserId(String userId) throws SystemException {
        Object[] finderArgs = new Object[] { userId };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
                finderArgs, this);

        if (count == null) {
            Session session = null;

            try {
                session = openSession();

                StringBundler query = new StringBundler(2);

                query.append(_SQL_COUNT_REPORTSENTRY_WHERE);

                if (userId == null) {
                    query.append(_FINDER_COLUMN_USERID_USERID_1);
                } else {
                    if (userId.equals(StringPool.BLANK)) {
                        query.append(_FINDER_COLUMN_USERID_USERID_3);
                    } else {
                        query.append(_FINDER_COLUMN_USERID_USERID_2);
                    }
                }

                String sql = query.toString();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                if (userId != null) {
                    qPos.add(userId);
                }

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
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

    public int countAll() throws SystemException {
        Object[] finderArgs = new Object[0];

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
                finderArgs, this);

        if (count == null) {
            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(_SQL_COUNT_REPORTSENTRY);

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
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
                        "value.object.listener.com.ext.portlet.reports.model.ReportsEntry")));

        if (listenerClassNames.length > 0) {
            try {
                List<ModelListener<ReportsEntry>> listenersList = new ArrayList<ModelListener<ReportsEntry>>();

                for (String listenerClassName : listenerClassNames) {
                    listenersList.add((ModelListener<ReportsEntry>) Class.forName(
                            listenerClassName).newInstance());
                }

                listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }
}
