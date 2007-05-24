package com.ext.portlet.reports.service.persistence;

import com.ext.portlet.reports.NoSuchEntryException;
import com.ext.portlet.reports.model.ReportsEntry;
import com.ext.portlet.reports.model.impl.ReportsEntryImpl;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;


public class ReportsEntryPersistenceImpl extends BasePersistence
    implements ReportsEntryPersistence {
    private static Log _log = LogFactory.getLog(ReportsEntryPersistenceImpl.class);

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
            throw HibernateUtil.processException(e);
        } finally {
            closeSession(session);
        }
    }

    public ReportsEntry remove(ReportsEntry reportsEntry)
        throws SystemException {
        Session session = null;

        try {
            session = openSession();
            session.delete(reportsEntry);
            session.flush();

            return reportsEntry;
        } catch (Exception e) {
            throw HibernateUtil.processException(e);
        } finally {
            closeSession(session);
        }
    }

    public com.ext.portlet.reports.model.ReportsEntry update(
        com.ext.portlet.reports.model.ReportsEntry reportsEntry)
        throws SystemException {
        return update(reportsEntry, false);
    }

    public com.ext.portlet.reports.model.ReportsEntry update(
        com.ext.portlet.reports.model.ReportsEntry reportsEntry,
        boolean saveOrUpdate) throws SystemException {
        Session session = null;

        try {
            session = openSession();

            if (saveOrUpdate) {
                session.saveOrUpdate(reportsEntry);
            } else {
                if (reportsEntry.isNew()) {
                    session.save(reportsEntry);
                }
            }

            session.flush();
            reportsEntry.setNew(false);

            return reportsEntry;
        } catch (Exception e) {
            throw HibernateUtil.processException(e);
        } finally {
            closeSession(session);
        }
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
        Session session = null;

        try {
            session = openSession();

            return (ReportsEntry) session.get(ReportsEntryImpl.class, entryId);
        } catch (Exception e) {
            throw HibernateUtil.processException(e);
        } finally {
            closeSession(session);
        }
    }

    public List findByCompanyId(String companyId) throws SystemException {
        Session session = null;

        try {
            session = openSession();

            StringMaker query = new StringMaker();
            query.append(
                "FROM com.ext.portlet.reports.model.ReportsEntry WHERE ");

            if (companyId == null) {
                query.append("companyId IS NULL");
            } else {
                query.append("companyId = ?");
            }

            query.append(" ");
            query.append("ORDER BY ");
            query.append("name ASC");

            Query q = session.createQuery(query.toString());
            q.setCacheable(true);

            int queryPos = 0;

            if (companyId != null) {
                q.setString(queryPos++, companyId);
            }

            return q.list();
        } catch (Exception e) {
            throw HibernateUtil.processException(e);
        } finally {
            closeSession(session);
        }
    }

    public List findByCompanyId(String companyId, int begin, int end)
        throws SystemException {
        return findByCompanyId(companyId, begin, end, null);
    }

    public List findByCompanyId(String companyId, int begin, int end,
        OrderByComparator obc) throws SystemException {
        Session session = null;

        try {
            session = openSession();

            StringMaker query = new StringMaker();
            query.append(
                "FROM com.ext.portlet.reports.model.ReportsEntry WHERE ");

            if (companyId == null) {
                query.append("companyId IS NULL");
            } else {
                query.append("companyId = ?");
            }

            query.append(" ");

            if (obc != null) {
                query.append("ORDER BY ");
                query.append(obc.getOrderBy());
            } else {
                query.append("ORDER BY ");
                query.append("name ASC");
            }

            Query q = session.createQuery(query.toString());
            q.setCacheable(true);

            int queryPos = 0;

            if (companyId != null) {
                q.setString(queryPos++, companyId);
            }

            return QueryUtil.list(q, getDialect(), begin, end);
        } catch (Exception e) {
            throw HibernateUtil.processException(e);
        } finally {
            closeSession(session);
        }
    }

    public ReportsEntry findByCompanyId_First(String companyId,
        OrderByComparator obc) throws NoSuchEntryException, SystemException {
        List list = findByCompanyId(companyId, 0, 1, obc);

        if (list.size() == 0) {
            StringMaker msg = new StringMaker();
            msg.append("No ReportsEntry exists with the key ");
            msg.append(StringPool.OPEN_CURLY_BRACE);
            msg.append("companyId=");
            msg.append(companyId);
            msg.append(StringPool.CLOSE_CURLY_BRACE);
            throw new NoSuchEntryException(msg.toString());
        } else {
            return (ReportsEntry) list.get(0);
        }
    }

    public ReportsEntry findByCompanyId_Last(String companyId,
        OrderByComparator obc) throws NoSuchEntryException, SystemException {
        int count = countByCompanyId(companyId);
        List list = findByCompanyId(companyId, count - 1, count, obc);

        if (list.size() == 0) {
            StringMaker msg = new StringMaker();
            msg.append("No ReportsEntry exists with the key ");
            msg.append(StringPool.OPEN_CURLY_BRACE);
            msg.append("companyId=");
            msg.append(companyId);
            msg.append(StringPool.CLOSE_CURLY_BRACE);
            throw new NoSuchEntryException(msg.toString());
        } else {
            return (ReportsEntry) list.get(0);
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

            StringMaker query = new StringMaker();
            query.append(
                "FROM com.ext.portlet.reports.model.ReportsEntry WHERE ");

            if (companyId == null) {
                query.append("companyId IS NULL");
            } else {
                query.append("companyId = ?");
            }

            query.append(" ");

            if (obc != null) {
                query.append("ORDER BY ");
                query.append(obc.getOrderBy());
            } else {
                query.append("ORDER BY ");
                query.append("name ASC");
            }

            Query q = session.createQuery(query.toString());
            q.setCacheable(true);

            int queryPos = 0;

            if (companyId != null) {
                q.setString(queryPos++, companyId);
            }

            Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
                    reportsEntry);
            ReportsEntry[] array = new ReportsEntryImpl[3];
            array[0] = (ReportsEntry) objArray[0];
            array[1] = (ReportsEntry) objArray[1];
            array[2] = (ReportsEntry) objArray[2];

            return array;
        } catch (Exception e) {
            throw HibernateUtil.processException(e);
        } finally {
            closeSession(session);
        }
    }

    public List findByUserId(String userId) throws SystemException {
        Session session = null;

        try {
            session = openSession();

            StringMaker query = new StringMaker();
            query.append(
                "FROM com.ext.portlet.reports.model.ReportsEntry WHERE ");

            if (userId == null) {
                query.append("userId IS NULL");
            } else {
                query.append("userId = ?");
            }

            query.append(" ");
            query.append("ORDER BY ");
            query.append("name ASC");

            Query q = session.createQuery(query.toString());
            q.setCacheable(true);

            int queryPos = 0;

            if (userId != null) {
                q.setString(queryPos++, userId);
            }

            return q.list();
        } catch (Exception e) {
            throw HibernateUtil.processException(e);
        } finally {
            closeSession(session);
        }
    }

    public List findByUserId(String userId, int begin, int end)
        throws SystemException {
        return findByUserId(userId, begin, end, null);
    }

    public List findByUserId(String userId, int begin, int end,
        OrderByComparator obc) throws SystemException {
        Session session = null;

        try {
            session = openSession();

            StringMaker query = new StringMaker();
            query.append(
                "FROM com.ext.portlet.reports.model.ReportsEntry WHERE ");

            if (userId == null) {
                query.append("userId IS NULL");
            } else {
                query.append("userId = ?");
            }

            query.append(" ");

            if (obc != null) {
                query.append("ORDER BY ");
                query.append(obc.getOrderBy());
            } else {
                query.append("ORDER BY ");
                query.append("name ASC");
            }

            Query q = session.createQuery(query.toString());
            q.setCacheable(true);

            int queryPos = 0;

            if (userId != null) {
                q.setString(queryPos++, userId);
            }

            return QueryUtil.list(q, getDialect(), begin, end);
        } catch (Exception e) {
            throw HibernateUtil.processException(e);
        } finally {
            closeSession(session);
        }
    }

    public ReportsEntry findByUserId_First(String userId, OrderByComparator obc)
        throws NoSuchEntryException, SystemException {
        List list = findByUserId(userId, 0, 1, obc);

        if (list.size() == 0) {
            StringMaker msg = new StringMaker();
            msg.append("No ReportsEntry exists with the key ");
            msg.append(StringPool.OPEN_CURLY_BRACE);
            msg.append("userId=");
            msg.append(userId);
            msg.append(StringPool.CLOSE_CURLY_BRACE);
            throw new NoSuchEntryException(msg.toString());
        } else {
            return (ReportsEntry) list.get(0);
        }
    }

    public ReportsEntry findByUserId_Last(String userId, OrderByComparator obc)
        throws NoSuchEntryException, SystemException {
        int count = countByUserId(userId);
        List list = findByUserId(userId, count - 1, count, obc);

        if (list.size() == 0) {
            StringMaker msg = new StringMaker();
            msg.append("No ReportsEntry exists with the key ");
            msg.append(StringPool.OPEN_CURLY_BRACE);
            msg.append("userId=");
            msg.append(userId);
            msg.append(StringPool.CLOSE_CURLY_BRACE);
            throw new NoSuchEntryException(msg.toString());
        } else {
            return (ReportsEntry) list.get(0);
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

            StringMaker query = new StringMaker();
            query.append(
                "FROM com.ext.portlet.reports.model.ReportsEntry WHERE ");

            if (userId == null) {
                query.append("userId IS NULL");
            } else {
                query.append("userId = ?");
            }

            query.append(" ");

            if (obc != null) {
                query.append("ORDER BY ");
                query.append(obc.getOrderBy());
            } else {
                query.append("ORDER BY ");
                query.append("name ASC");
            }

            Query q = session.createQuery(query.toString());
            q.setCacheable(true);

            int queryPos = 0;

            if (userId != null) {
                q.setString(queryPos++, userId);
            }

            Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
                    reportsEntry);
            ReportsEntry[] array = new ReportsEntryImpl[3];
            array[0] = (ReportsEntry) objArray[0];
            array[1] = (ReportsEntry) objArray[1];
            array[2] = (ReportsEntry) objArray[2];

            return array;
        } catch (Exception e) {
            throw HibernateUtil.processException(e);
        } finally {
            closeSession(session);
        }
    }

    public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer)
        throws SystemException {
        Session session = null;

        try {
            session = openSession();

            DynamicQuery query = queryInitializer.initialize(session);

            return query.list();
        } catch (Exception e) {
            throw HibernateUtil.processException(e);
        } finally {
            closeSession(session);
        }
    }

    public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer,
        int begin, int end) throws SystemException {
        Session session = null;

        try {
            session = openSession();

            DynamicQuery query = queryInitializer.initialize(session);
            query.setLimit(begin, end);

            return query.list();
        } catch (Exception e) {
            throw HibernateUtil.processException(e);
        } finally {
            closeSession(session);
        }
    }

    public List findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    public List findAll(int begin, int end) throws SystemException {
        return findAll(begin, end, null);
    }

    public List findAll(int begin, int end, OrderByComparator obc)
        throws SystemException {
        Session session = null;

        try {
            session = openSession();

            StringMaker query = new StringMaker();
            query.append("FROM com.ext.portlet.reports.model.ReportsEntry ");

            if (obc != null) {
                query.append("ORDER BY ");
                query.append(obc.getOrderBy());
            } else {
                query.append("ORDER BY ");
                query.append("name ASC");
            }

            Query q = session.createQuery(query.toString());
            q.setCacheable(true);

            return QueryUtil.list(q, getDialect(), begin, end);
        } catch (Exception e) {
            throw HibernateUtil.processException(e);
        } finally {
            closeSession(session);
        }
    }

    public void removeByCompanyId(String companyId) throws SystemException {
        Iterator itr = findByCompanyId(companyId).iterator();

        while (itr.hasNext()) {
            ReportsEntry reportsEntry = (ReportsEntry) itr.next();
            remove(reportsEntry);
        }
    }

    public void removeByUserId(String userId) throws SystemException {
        Iterator itr = findByUserId(userId).iterator();

        while (itr.hasNext()) {
            ReportsEntry reportsEntry = (ReportsEntry) itr.next();
            remove(reportsEntry);
        }
    }

    public void removeAll() throws SystemException {
        Iterator itr = findAll().iterator();

        while (itr.hasNext()) {
            remove((ReportsEntry) itr.next());
        }
    }

    public int countByCompanyId(String companyId) throws SystemException {
        Session session = null;

        try {
            session = openSession();

            StringMaker query = new StringMaker();
            query.append("SELECT COUNT(*) ");
            query.append(
                "FROM com.ext.portlet.reports.model.ReportsEntry WHERE ");

            if (companyId == null) {
                query.append("companyId IS NULL");
            } else {
                query.append("companyId = ?");
            }

            query.append(" ");

            Query q = session.createQuery(query.toString());
            q.setCacheable(true);

            int queryPos = 0;

            if (companyId != null) {
                q.setString(queryPos++, companyId);
            }

            Iterator itr = q.list().iterator();

            if (itr.hasNext()) {
                Long count = (Long) itr.next();

                if (count != null) {
                    return count.intValue();
                }
            }

            return 0;
        } catch (Exception e) {
            throw HibernateUtil.processException(e);
        } finally {
            closeSession(session);
        }
    }

    public int countByUserId(String userId) throws SystemException {
        Session session = null;

        try {
            session = openSession();

            StringMaker query = new StringMaker();
            query.append("SELECT COUNT(*) ");
            query.append(
                "FROM com.ext.portlet.reports.model.ReportsEntry WHERE ");

            if (userId == null) {
                query.append("userId IS NULL");
            } else {
                query.append("userId = ?");
            }

            query.append(" ");

            Query q = session.createQuery(query.toString());
            q.setCacheable(true);

            int queryPos = 0;

            if (userId != null) {
                q.setString(queryPos++, userId);
            }

            Iterator itr = q.list().iterator();

            if (itr.hasNext()) {
                Long count = (Long) itr.next();

                if (count != null) {
                    return count.intValue();
                }
            }

            return 0;
        } catch (Exception e) {
            throw HibernateUtil.processException(e);
        } finally {
            closeSession(session);
        }
    }

    public int countAll() throws SystemException {
        Session session = null;

        try {
            session = openSession();

            StringMaker query = new StringMaker();
            query.append("SELECT COUNT(*) ");
            query.append("FROM com.ext.portlet.reports.model.ReportsEntry");

            Query q = session.createQuery(query.toString());
            q.setCacheable(true);

            Iterator itr = q.list().iterator();

            if (itr.hasNext()) {
                Long count = (Long) itr.next();

                if (count != null) {
                    return count.intValue();
                }
            }

            return 0;
        } catch (Exception e) {
            throw HibernateUtil.processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected void initDao() {
    }
}
