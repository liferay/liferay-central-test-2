package com.ext.portlet.reports.service.persistence;

import com.ext.portlet.reports.NoSuchEntryException;
import com.ext.portlet.reports.model.ReportsEntry;
import com.ext.portlet.reports.model.impl.ReportsEntryImpl;
import com.ext.portlet.reports.model.impl.ReportsEntryModelImpl;

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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ReportsEntryPersistenceImpl extends BasePersistenceImpl
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
    private static Log _log = LogFactoryUtil.getLog(ReportsEntryPersistenceImpl.class);
    @BeanReference(name = "com.ext.portlet.reports.service.persistence.ReportsEntryPersistence.impl")
    protected com.ext.portlet.reports.service.persistence.ReportsEntryPersistence reportsEntryPersistence;

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

    public ReportsEntry remove(String entryId)
        throws NoSuchEntryException, SystemException {
        Session session = null;

        try {
            session = openSession();

            ReportsEntry reportsEntry = (ReportsEntry) session.get(ReportsEntryImpl.class,
                    entryId);

            if (reportsEntry == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn("No ReportsEntry exists with the primary key " +
                        entryId);
                }

                throw new NoSuchEntryException(
                    "No ReportsEntry exists with the primary key " + entryId);
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

    /**
     * @deprecated Use <code>update(ReportsEntry reportsEntry, boolean merge)</code>.
     */
    public ReportsEntry update(ReportsEntry reportsEntry)
        throws SystemException {
        if (_log.isWarnEnabled()) {
            _log.warn(
                "Using the deprecated update(ReportsEntry reportsEntry) method. Use update(ReportsEntry reportsEntry, boolean merge) instead.");
        }

        return update(reportsEntry, false);
    }

    /**
     * Add, update, or merge, the entity. This method also calls the model
     * listeners to trigger the proper events associated with adding, deleting,
     * or updating an entity.
     *
     * @param                reportsEntry the entity to add, update, or merge
     * @param                merge boolean value for whether to merge the entity. The
     *                                default value is false. Setting merge to true is more
     *                                expensive and should only be true when reportsEntry is
     *                                transient. See LEP-5473 for a detailed discussion of this
     *                                method.
     * @return                true if the portlet can be displayed via Ajax
     */
    public ReportsEntry update(ReportsEntry reportsEntry, boolean merge)
        throws SystemException {
        boolean isNew = reportsEntry.isNew();

        for (ModelListener<ReportsEntry> listener : listeners) {
            if (isNew) {
                listener.onBeforeCreate(reportsEntry);
            } else {
                listener.onBeforeUpdate(reportsEntry);
            }
        }

        reportsEntry = updateImpl(reportsEntry, merge);

        for (ModelListener<ReportsEntry> listener : listeners) {
            if (isNew) {
                listener.onAfterCreate(reportsEntry);
            } else {
                listener.onAfterUpdate(reportsEntry);
            }
        }

        return reportsEntry;
    }

    public ReportsEntry updateImpl(
        com.ext.portlet.reports.model.ReportsEntry reportsEntry, boolean merge)
        throws SystemException {
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

    public ReportsEntry findByPrimaryKey(String entryId)
        throws NoSuchEntryException, SystemException {
        ReportsEntry reportsEntry = fetchByPrimaryKey(entryId);

        if (reportsEntry == null) {
            if (_log.isWarnEnabled()) {
                _log.warn("No ReportsEntry exists with the primary key " +
                    entryId);
            }

            throw new NoSuchEntryException(
                "No ReportsEntry exists with the primary key " + entryId);
        }

        return reportsEntry;
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

                StringBuilder query = new StringBuilder();

                query.append(
                    "SELECT reportsEntry FROM ReportsEntry reportsEntry WHERE ");

                if (companyId == null) {
                    query.append("reportsEntry.companyId IS NULL");
                } else {
                    query.append("reportsEntry.companyId = ?");
                }

                query.append(" ");

                query.append("ORDER BY ");

                query.append("reportsEntry.name ASC");

                Query q = session.createQuery(query.toString());

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

                StringBuilder query = new StringBuilder();

                query.append(
                    "SELECT reportsEntry FROM ReportsEntry reportsEntry WHERE ");

                if (companyId == null) {
                    query.append("reportsEntry.companyId IS NULL");
                } else {
                    query.append("reportsEntry.companyId = ?");
                }

                query.append(" ");

                if (obc != null) {
                    query.append("ORDER BY ");

                    String[] orderByFields = obc.getOrderByFields();

                    for (int i = 0; i < orderByFields.length; i++) {
                        query.append("reportsEntry.");
                        query.append(orderByFields[i]);

                        if (obc.isAscending()) {
                            query.append(" ASC");
                        } else {
                            query.append(" DESC");
                        }

                        if ((i + 1) < orderByFields.length) {
                            query.append(", ");
                        }
                    }
                }
                else {
                    query.append("ORDER BY ");

                    query.append("reportsEntry.name ASC");
                }

                Query q = session.createQuery(query.toString());

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
            StringBuilder msg = new StringBuilder();

            msg.append("No ReportsEntry exists with the key {");

            msg.append("companyId=" + companyId);

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
            StringBuilder msg = new StringBuilder();

            msg.append("No ReportsEntry exists with the key {");

            msg.append("companyId=" + companyId);

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

            StringBuilder query = new StringBuilder();

            query.append(
                "SELECT reportsEntry FROM ReportsEntry reportsEntry WHERE ");

            if (companyId == null) {
                query.append("reportsEntry.companyId IS NULL");
            } else {
                query.append("reportsEntry.companyId = ?");
            }

            query.append(" ");

            if (obc != null) {
                query.append("ORDER BY ");

                String[] orderByFields = obc.getOrderByFields();

                for (int i = 0; i < orderByFields.length; i++) {
                    query.append("reportsEntry.");
                    query.append(orderByFields[i]);

                    if (obc.isAscending()) {
                        query.append(" ASC");
                    } else {
                        query.append(" DESC");
                    }

                    if ((i + 1) < orderByFields.length) {
                        query.append(", ");
                    }
                }
            }
            else {
                query.append("ORDER BY ");

                query.append("reportsEntry.name ASC");
            }

            Query q = session.createQuery(query.toString());

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

                StringBuilder query = new StringBuilder();

                query.append(
                    "SELECT reportsEntry FROM ReportsEntry reportsEntry WHERE ");

                if (userId == null) {
                    query.append("reportsEntry.userId IS NULL");
                } else {
                    query.append("reportsEntry.userId = ?");
                }

                query.append(" ");

                query.append("ORDER BY ");

                query.append("reportsEntry.name ASC");

                Query q = session.createQuery(query.toString());

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

                StringBuilder query = new StringBuilder();

                query.append(
                    "SELECT reportsEntry FROM ReportsEntry reportsEntry WHERE ");

                if (userId == null) {
                    query.append("reportsEntry.userId IS NULL");
                } else {
                    query.append("reportsEntry.userId = ?");
                }

                query.append(" ");

                if (obc != null) {
                    query.append("ORDER BY ");

                    String[] orderByFields = obc.getOrderByFields();

                    for (int i = 0; i < orderByFields.length; i++) {
                        query.append("reportsEntry.");
                        query.append(orderByFields[i]);

                        if (obc.isAscending()) {
                            query.append(" ASC");
                        } else {
                            query.append(" DESC");
                        }

                        if ((i + 1) < orderByFields.length) {
                            query.append(", ");
                        }
                    }
                }
                else {
                    query.append("ORDER BY ");

                    query.append("reportsEntry.name ASC");
                }

                Query q = session.createQuery(query.toString());

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
            StringBuilder msg = new StringBuilder();

            msg.append("No ReportsEntry exists with the key {");

            msg.append("userId=" + userId);

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
            StringBuilder msg = new StringBuilder();

            msg.append("No ReportsEntry exists with the key {");

            msg.append("userId=" + userId);

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

            StringBuilder query = new StringBuilder();

            query.append(
                "SELECT reportsEntry FROM ReportsEntry reportsEntry WHERE ");

            if (userId == null) {
                query.append("reportsEntry.userId IS NULL");
            } else {
                query.append("reportsEntry.userId = ?");
            }

            query.append(" ");

            if (obc != null) {
                query.append("ORDER BY ");

                String[] orderByFields = obc.getOrderByFields();

                for (int i = 0; i < orderByFields.length; i++) {
                    query.append("reportsEntry.");
                    query.append(orderByFields[i]);

                    if (obc.isAscending()) {
                        query.append(" ASC");
                    } else {
                        query.append(" DESC");
                    }

                    if ((i + 1) < orderByFields.length) {
                        query.append(", ");
                    }
                }
            }
            else {
                query.append("ORDER BY ");

                query.append("reportsEntry.name ASC");
            }

            Query q = session.createQuery(query.toString());

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

                StringBuilder query = new StringBuilder();

                query.append(
                    "SELECT reportsEntry FROM ReportsEntry reportsEntry ");

                if (obc != null) {
                    query.append("ORDER BY ");

                    String[] orderByFields = obc.getOrderByFields();

                    for (int i = 0; i < orderByFields.length; i++) {
                        query.append("reportsEntry.");
                        query.append(orderByFields[i]);

                        if (obc.isAscending()) {
                            query.append(" ASC");
                        } else {
                            query.append(" DESC");
                        }

                        if ((i + 1) < orderByFields.length) {
                            query.append(", ");
                        }
                    }
                }
                else {
                    query.append("ORDER BY ");

                    query.append("reportsEntry.name ASC");
                }

                Query q = session.createQuery(query.toString());

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

                StringBuilder query = new StringBuilder();

                query.append("SELECT COUNT(reportsEntry) ");
                query.append("FROM ReportsEntry reportsEntry WHERE ");

                if (companyId == null) {
                    query.append("reportsEntry.companyId IS NULL");
                } else {
                    query.append("reportsEntry.companyId = ?");
                }

                query.append(" ");

                Query q = session.createQuery(query.toString());

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

                StringBuilder query = new StringBuilder();

                query.append("SELECT COUNT(reportsEntry) ");
                query.append("FROM ReportsEntry reportsEntry WHERE ");

                if (userId == null) {
                    query.append("reportsEntry.userId IS NULL");
                } else {
                    query.append("reportsEntry.userId = ?");
                }

                query.append(" ");

                Query q = session.createQuery(query.toString());

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

                Query q = session.createQuery(
                        "SELECT COUNT(reportsEntry) FROM ReportsEntry reportsEntry");

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
