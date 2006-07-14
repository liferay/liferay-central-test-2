package com.ext.portlet.reports.service.persistence;

import com.ext.portlet.reports.NoSuchEntryException;
import com.ext.portlet.reports.model.ReportsEntry;

import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ReportsEntryPersistence extends BasePersistence {
    private static Log _log = LogFactory.getLog(ReportsEntryPersistence.class);

    public ReportsEntry create(String entryId) {
        ReportsEntry reportsEntry = new ReportsEntry();
        reportsEntry.setNew(true);
        reportsEntry.setPrimaryKey(entryId);

        return reportsEntry;
    }

    public ReportsEntry remove(String entryId)
        throws NoSuchEntryException, SystemException {
        Session session = null;

        try {
            session = openSession();

            ReportsEntry reportsEntry = (ReportsEntry) session.get(ReportsEntry.class,
                    entryId);

            if (reportsEntry == null) {
                _log.warn("No ReportsEntry exists with the primary key " +
                    entryId.toString());
                throw new NoSuchEntryException(
                    "No ReportsEntry exists with the primary key " +
                    entryId.toString());
            }

            session.delete(reportsEntry);
            session.flush();

            return reportsEntry;
        } catch (HibernateException he) {
            throw new SystemException(he);
        } finally {
            closeSession(session);
        }
    }

    public com.ext.portlet.reports.model.ReportsEntry update(
        com.ext.portlet.reports.model.ReportsEntry reportsEntry)
        throws SystemException {
        Session session = null;

        try {
            if (reportsEntry.isNew() || reportsEntry.isModified()) {
                session = openSession();

                if (reportsEntry.isNew()) {
                    ReportsEntry reportsEntryModel = new ReportsEntry();
                    reportsEntryModel.setEntryId(reportsEntry.getEntryId());
                    reportsEntryModel.setCompanyId(reportsEntry.getCompanyId());
                    reportsEntryModel.setUserId(reportsEntry.getUserId());
                    reportsEntryModel.setUserName(reportsEntry.getUserName());
                    reportsEntryModel.setCreateDate(reportsEntry.getCreateDate());
                    reportsEntryModel.setModifiedDate(reportsEntry.getModifiedDate());
                    reportsEntryModel.setName(reportsEntry.getName());
                    session.save(reportsEntryModel);
                    session.flush();
                } else {
                    ReportsEntry reportsEntryModel = (ReportsEntry) session.get(ReportsEntry.class,
                            reportsEntry.getPrimaryKey());

                    if (reportsEntryModel != null) {
                        reportsEntryModel.setCompanyId(reportsEntry.getCompanyId());
                        reportsEntryModel.setUserId(reportsEntry.getUserId());
                        reportsEntryModel.setUserName(reportsEntry.getUserName());
                        reportsEntryModel.setCreateDate(reportsEntry.getCreateDate());
                        reportsEntryModel.setModifiedDate(reportsEntry.getModifiedDate());
                        reportsEntryModel.setName(reportsEntry.getName());
                        session.flush();
                    } else {
                        reportsEntryModel = new ReportsEntry();
                        reportsEntryModel.setEntryId(reportsEntry.getEntryId());
                        reportsEntryModel.setCompanyId(reportsEntry.getCompanyId());
                        reportsEntryModel.setUserId(reportsEntry.getUserId());
                        reportsEntryModel.setUserName(reportsEntry.getUserName());
                        reportsEntryModel.setCreateDate(reportsEntry.getCreateDate());
                        reportsEntryModel.setModifiedDate(reportsEntry.getModifiedDate());
                        reportsEntryModel.setName(reportsEntry.getName());
                        session.save(reportsEntryModel);
                        session.flush();
                    }
                }

                reportsEntry.setNew(false);
                reportsEntry.setModified(false);
            }

            return reportsEntry;
        } catch (HibernateException he) {
            throw new SystemException(he);
        } finally {
            closeSession(session);
        }
    }

    public ReportsEntry findByPrimaryKey(String entryId)
        throws NoSuchEntryException, SystemException {
        Session session = null;

        try {
            session = openSession();

            ReportsEntry reportsEntry = (ReportsEntry) session.get(ReportsEntry.class,
                    entryId);

            if (reportsEntry == null) {
                _log.warn("No ReportsEntry exists with the primary key " +
                    entryId.toString());
                throw new NoSuchEntryException(
                    "No ReportsEntry exists with the primary key " +
                    entryId.toString());
            }

            return reportsEntry;
        } catch (HibernateException he) {
            throw new SystemException(he);
        } finally {
            closeSession(session);
        }
    }

    public List findByCompanyId(String companyId) throws SystemException {
        Session session = null;

        try {
            session = openSession();

            StringBuffer query = new StringBuffer();
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
            int queryPos = 0;

            if (companyId != null) {
                q.setString(queryPos++, companyId);
            }

            List list = q.list();

            return list;
        } catch (HibernateException he) {
            throw new SystemException(he);
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

            StringBuffer query = new StringBuffer();
            query.append(
                "FROM com.ext.portlet.reports.model.ReportsEntry WHERE ");

            if (companyId == null) {
                query.append("companyId IS NULL");
            } else {
                query.append("companyId = ?");
            }

            query.append(" ");

            if (obc != null) {
                query.append("ORDER BY " + obc.getOrderBy());
            } else {
                query.append("ORDER BY ");
                query.append("name ASC");
            }

            Query q = session.createQuery(query.toString());
            int queryPos = 0;

            if (companyId != null) {
                q.setString(queryPos++, companyId);
            }

            return QueryUtil.list(q, getDialect(), begin, end);
        } catch (HibernateException he) {
            throw new SystemException(he);
        } finally {
            closeSession(session);
        }
    }

    public ReportsEntry findByCompanyId_First(String companyId,
        OrderByComparator obc) throws NoSuchEntryException, SystemException {
        List list = findByCompanyId(companyId, 0, 1, obc);

        if (list.size() == 0) {
            String msg = "No ReportsEntry exists with the key ";
            msg += StringPool.OPEN_CURLY_BRACE;
            msg += "companyId=";
            msg += companyId;
            msg += StringPool.CLOSE_CURLY_BRACE;
            throw new NoSuchEntryException(msg);
        } else {
            return (ReportsEntry) list.get(0);
        }
    }

    public ReportsEntry findByCompanyId_Last(String companyId,
        OrderByComparator obc) throws NoSuchEntryException, SystemException {
        int count = countByCompanyId(companyId);
        List list = findByCompanyId(companyId, count - 1, count, obc);

        if (list.size() == 0) {
            String msg = "No ReportsEntry exists with the key ";
            msg += StringPool.OPEN_CURLY_BRACE;
            msg += "companyId=";
            msg += companyId;
            msg += StringPool.CLOSE_CURLY_BRACE;
            throw new NoSuchEntryException(msg);
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

            StringBuffer query = new StringBuffer();
            query.append(
                "FROM com.ext.portlet.reports.model.ReportsEntry WHERE ");

            if (companyId == null) {
                query.append("companyId IS NULL");
            } else {
                query.append("companyId = ?");
            }

            query.append(" ");

            if (obc != null) {
                query.append("ORDER BY " + obc.getOrderBy());
            } else {
                query.append("ORDER BY ");
                query.append("name ASC");
            }

            Query q = session.createQuery(query.toString());
            int queryPos = 0;

            if (companyId != null) {
                q.setString(queryPos++, companyId);
            }

            Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
                    reportsEntry);
            ReportsEntry[] array = new ReportsEntry[3];
            array[0] = (ReportsEntry) objArray[0];
            array[1] = (ReportsEntry) objArray[1];
            array[2] = (ReportsEntry) objArray[2];

            return array;
        } catch (HibernateException he) {
            throw new SystemException(he);
        } finally {
            closeSession(session);
        }
    }

    public List findByUserId(String userId) throws SystemException {
        Session session = null;

        try {
            session = openSession();

            StringBuffer query = new StringBuffer();
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
            int queryPos = 0;

            if (userId != null) {
                q.setString(queryPos++, userId);
            }

            List list = q.list();

            return list;
        } catch (HibernateException he) {
            throw new SystemException(he);
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

            StringBuffer query = new StringBuffer();
            query.append(
                "FROM com.ext.portlet.reports.model.ReportsEntry WHERE ");

            if (userId == null) {
                query.append("userId IS NULL");
            } else {
                query.append("userId = ?");
            }

            query.append(" ");

            if (obc != null) {
                query.append("ORDER BY " + obc.getOrderBy());
            } else {
                query.append("ORDER BY ");
                query.append("name ASC");
            }

            Query q = session.createQuery(query.toString());
            int queryPos = 0;

            if (userId != null) {
                q.setString(queryPos++, userId);
            }

            return QueryUtil.list(q, getDialect(), begin, end);
        } catch (HibernateException he) {
            throw new SystemException(he);
        } finally {
            closeSession(session);
        }
    }

    public ReportsEntry findByUserId_First(String userId, OrderByComparator obc)
        throws NoSuchEntryException, SystemException {
        List list = findByUserId(userId, 0, 1, obc);

        if (list.size() == 0) {
            String msg = "No ReportsEntry exists with the key ";
            msg += StringPool.OPEN_CURLY_BRACE;
            msg += "userId=";
            msg += userId;
            msg += StringPool.CLOSE_CURLY_BRACE;
            throw new NoSuchEntryException(msg);
        } else {
            return (ReportsEntry) list.get(0);
        }
    }

    public ReportsEntry findByUserId_Last(String userId, OrderByComparator obc)
        throws NoSuchEntryException, SystemException {
        int count = countByUserId(userId);
        List list = findByUserId(userId, count - 1, count, obc);

        if (list.size() == 0) {
            String msg = "No ReportsEntry exists with the key ";
            msg += StringPool.OPEN_CURLY_BRACE;
            msg += "userId=";
            msg += userId;
            msg += StringPool.CLOSE_CURLY_BRACE;
            throw new NoSuchEntryException(msg);
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

            StringBuffer query = new StringBuffer();
            query.append(
                "FROM com.ext.portlet.reports.model.ReportsEntry WHERE ");

            if (userId == null) {
                query.append("userId IS NULL");
            } else {
                query.append("userId = ?");
            }

            query.append(" ");

            if (obc != null) {
                query.append("ORDER BY " + obc.getOrderBy());
            } else {
                query.append("ORDER BY ");
                query.append("name ASC");
            }

            Query q = session.createQuery(query.toString());
            int queryPos = 0;

            if (userId != null) {
                q.setString(queryPos++, userId);
            }

            Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
                    reportsEntry);
            ReportsEntry[] array = new ReportsEntry[3];
            array[0] = (ReportsEntry) objArray[0];
            array[1] = (ReportsEntry) objArray[1];
            array[2] = (ReportsEntry) objArray[2];

            return array;
        } catch (HibernateException he) {
            throw new SystemException(he);
        } finally {
            closeSession(session);
        }
    }

    public List findAll() throws SystemException {
        Session session = null;

        try {
            session = openSession();

            StringBuffer query = new StringBuffer();
            query.append("FROM com.ext.portlet.reports.model.ReportsEntry ");
            query.append("ORDER BY ");
            query.append("name ASC");

            Query q = session.createQuery(query.toString());

            return q.list();
        } catch (HibernateException he) {
            throw new SystemException(he);
        } finally {
            closeSession(session);
        }
    }

    public void removeByCompanyId(String companyId) throws SystemException {
        Session session = null;

        try {
            session = openSession();

            StringBuffer query = new StringBuffer();
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
            int queryPos = 0;

            if (companyId != null) {
                q.setString(queryPos++, companyId);
            }

            Iterator itr = q.list().iterator();

            while (itr.hasNext()) {
                ReportsEntry reportsEntry = (ReportsEntry) itr.next();
                session.delete(reportsEntry);
            }

            session.flush();
        } catch (HibernateException he) {
            throw new SystemException(he);
        } finally {
            closeSession(session);
        }
    }

    public void removeByUserId(String userId) throws SystemException {
        Session session = null;

        try {
            session = openSession();

            StringBuffer query = new StringBuffer();
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
            int queryPos = 0;

            if (userId != null) {
                q.setString(queryPos++, userId);
            }

            Iterator itr = q.list().iterator();

            while (itr.hasNext()) {
                ReportsEntry reportsEntry = (ReportsEntry) itr.next();
                session.delete(reportsEntry);
            }

            session.flush();
        } catch (HibernateException he) {
            throw new SystemException(he);
        } finally {
            closeSession(session);
        }
    }

    public int countByCompanyId(String companyId) throws SystemException {
        Session session = null;

        try {
            session = openSession();

            StringBuffer query = new StringBuffer();
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
        } catch (HibernateException he) {
            throw new SystemException(he);
        } finally {
            closeSession(session);
        }
    }

    public int countByUserId(String userId) throws SystemException {
        Session session = null;

        try {
            session = openSession();

            StringBuffer query = new StringBuffer();
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
        } catch (HibernateException he) {
            throw new SystemException(he);
        } finally {
            closeSession(session);
        }
    }

    protected void initDao() {
    }
}
