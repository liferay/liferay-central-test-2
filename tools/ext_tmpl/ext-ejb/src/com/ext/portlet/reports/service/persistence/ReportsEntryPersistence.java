package com.ext.portlet.reports.service.persistence;

import com.ext.portlet.reports.NoSuchEntryException;

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

    public com.ext.portlet.reports.model.ReportsEntry create(String entryId) {
        ReportsEntryHBM reportsEntryHBM = new ReportsEntryHBM();
        reportsEntryHBM.setNew(true);
        reportsEntryHBM.setPrimaryKey(entryId);

        return ReportsEntryHBMUtil.model(reportsEntryHBM);
    }

    public com.ext.portlet.reports.model.ReportsEntry remove(String entryId)
        throws NoSuchEntryException, SystemException {
        Session session = null;

        try {
            session = openSession();

            ReportsEntryHBM reportsEntryHBM = (ReportsEntryHBM) session.get(ReportsEntryHBM.class,
                    entryId);

            if (reportsEntryHBM == null) {
                _log.warn("No ReportsEntry exists with the primary key " +
                    entryId.toString());
                throw new NoSuchEntryException(
                    "No ReportsEntry exists with the primary key " +
                    entryId.toString());
            }

            session.delete(reportsEntryHBM);
            session.flush();

            return ReportsEntryHBMUtil.model(reportsEntryHBM);
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
                    ReportsEntryHBM reportsEntryHBM = new ReportsEntryHBM();
                    reportsEntryHBM.setEntryId(reportsEntry.getEntryId());
                    reportsEntryHBM.setCompanyId(reportsEntry.getCompanyId());
                    reportsEntryHBM.setUserId(reportsEntry.getUserId());
                    reportsEntryHBM.setUserName(reportsEntry.getUserName());
                    reportsEntryHBM.setCreateDate(reportsEntry.getCreateDate());
                    reportsEntryHBM.setModifiedDate(reportsEntry.getModifiedDate());
                    reportsEntryHBM.setName(reportsEntry.getName());
                    session.save(reportsEntryHBM);
                    session.flush();
                } else {
                    ReportsEntryHBM reportsEntryHBM = (ReportsEntryHBM) session.get(ReportsEntryHBM.class,
                            reportsEntry.getPrimaryKey());

                    if (reportsEntryHBM != null) {
                        reportsEntryHBM.setCompanyId(reportsEntry.getCompanyId());
                        reportsEntryHBM.setUserId(reportsEntry.getUserId());
                        reportsEntryHBM.setUserName(reportsEntry.getUserName());
                        reportsEntryHBM.setCreateDate(reportsEntry.getCreateDate());
                        reportsEntryHBM.setModifiedDate(reportsEntry.getModifiedDate());
                        reportsEntryHBM.setName(reportsEntry.getName());
                        session.flush();
                    } else {
                        reportsEntryHBM = new ReportsEntryHBM();
                        reportsEntryHBM.setEntryId(reportsEntry.getEntryId());
                        reportsEntryHBM.setCompanyId(reportsEntry.getCompanyId());
                        reportsEntryHBM.setUserId(reportsEntry.getUserId());
                        reportsEntryHBM.setUserName(reportsEntry.getUserName());
                        reportsEntryHBM.setCreateDate(reportsEntry.getCreateDate());
                        reportsEntryHBM.setModifiedDate(reportsEntry.getModifiedDate());
                        reportsEntryHBM.setName(reportsEntry.getName());
                        session.save(reportsEntryHBM);
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

    public com.ext.portlet.reports.model.ReportsEntry findByPrimaryKey(
        String entryId) throws NoSuchEntryException, SystemException {
        Session session = null;

        try {
            session = openSession();

            ReportsEntryHBM reportsEntryHBM = (ReportsEntryHBM) session.get(ReportsEntryHBM.class,
                    entryId);

            if (reportsEntryHBM == null) {
                _log.warn("No ReportsEntry exists with the primary key " +
                    entryId.toString());
                throw new NoSuchEntryException(
                    "No ReportsEntry exists with the primary key " +
                    entryId.toString());
            }

            return ReportsEntryHBMUtil.model(reportsEntryHBM);
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
                "FROM ReportsEntry IN CLASS com.ext.portlet.reports.service.persistence.ReportsEntryHBM WHERE ");

            if (companyId == null) {
                query.append("companyId is null");
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
            List list = new ArrayList();

            while (itr.hasNext()) {
                ReportsEntryHBM reportsEntryHBM = (ReportsEntryHBM) itr.next();
                list.add(ReportsEntryHBMUtil.model(reportsEntryHBM));
            }

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
                "FROM ReportsEntry IN CLASS com.ext.portlet.reports.service.persistence.ReportsEntryHBM WHERE ");

            if (companyId == null) {
                query.append("companyId is null");
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

            List list = new ArrayList();
            Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

            while (itr.hasNext()) {
                ReportsEntryHBM reportsEntryHBM = (ReportsEntryHBM) itr.next();
                list.add(ReportsEntryHBMUtil.model(reportsEntryHBM));
            }

            return list;
        } catch (HibernateException he) {
            throw new SystemException(he);
        } finally {
            closeSession(session);
        }
    }

    public com.ext.portlet.reports.model.ReportsEntry findByCompanyId_First(
        String companyId, OrderByComparator obc)
        throws NoSuchEntryException, SystemException {
        List list = findByCompanyId(companyId, 0, 1, obc);

        if (list.size() == 0) {
            String msg = "No ReportsEntry exists with the key ";
            msg += StringPool.OPEN_CURLY_BRACE;
            msg += "companyId=";
            msg += companyId;
            msg += StringPool.CLOSE_CURLY_BRACE;
            throw new NoSuchEntryException(msg);
        } else {
            return (com.ext.portlet.reports.model.ReportsEntry) list.get(0);
        }
    }

    public com.ext.portlet.reports.model.ReportsEntry findByCompanyId_Last(
        String companyId, OrderByComparator obc)
        throws NoSuchEntryException, SystemException {
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
            return (com.ext.portlet.reports.model.ReportsEntry) list.get(0);
        }
    }

    public com.ext.portlet.reports.model.ReportsEntry[] findByCompanyId_PrevAndNext(
        String entryId, String companyId, OrderByComparator obc)
        throws NoSuchEntryException, SystemException {
        com.ext.portlet.reports.model.ReportsEntry reportsEntry = findByPrimaryKey(entryId);
        int count = countByCompanyId(companyId);
        Session session = null;

        try {
            session = openSession();

            StringBuffer query = new StringBuffer();
            query.append(
                "FROM ReportsEntry IN CLASS com.ext.portlet.reports.service.persistence.ReportsEntryHBM WHERE ");

            if (companyId == null) {
                query.append("companyId is null");
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
                    reportsEntry, ReportsEntryHBMUtil.getInstance());
            com.ext.portlet.reports.model.ReportsEntry[] array = new com.ext.portlet.reports.model.ReportsEntry[3];
            array[0] = (com.ext.portlet.reports.model.ReportsEntry) objArray[0];
            array[1] = (com.ext.portlet.reports.model.ReportsEntry) objArray[1];
            array[2] = (com.ext.portlet.reports.model.ReportsEntry) objArray[2];

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
                "FROM ReportsEntry IN CLASS com.ext.portlet.reports.service.persistence.ReportsEntryHBM WHERE ");

            if (userId == null) {
                query.append("userId is null");
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
            List list = new ArrayList();

            while (itr.hasNext()) {
                ReportsEntryHBM reportsEntryHBM = (ReportsEntryHBM) itr.next();
                list.add(ReportsEntryHBMUtil.model(reportsEntryHBM));
            }

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
                "FROM ReportsEntry IN CLASS com.ext.portlet.reports.service.persistence.ReportsEntryHBM WHERE ");

            if (userId == null) {
                query.append("userId is null");
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

            List list = new ArrayList();
            Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

            while (itr.hasNext()) {
                ReportsEntryHBM reportsEntryHBM = (ReportsEntryHBM) itr.next();
                list.add(ReportsEntryHBMUtil.model(reportsEntryHBM));
            }

            return list;
        } catch (HibernateException he) {
            throw new SystemException(he);
        } finally {
            closeSession(session);
        }
    }

    public com.ext.portlet.reports.model.ReportsEntry findByUserId_First(
        String userId, OrderByComparator obc)
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
            return (com.ext.portlet.reports.model.ReportsEntry) list.get(0);
        }
    }

    public com.ext.portlet.reports.model.ReportsEntry findByUserId_Last(
        String userId, OrderByComparator obc)
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
            return (com.ext.portlet.reports.model.ReportsEntry) list.get(0);
        }
    }

    public com.ext.portlet.reports.model.ReportsEntry[] findByUserId_PrevAndNext(
        String entryId, String userId, OrderByComparator obc)
        throws NoSuchEntryException, SystemException {
        com.ext.portlet.reports.model.ReportsEntry reportsEntry = findByPrimaryKey(entryId);
        int count = countByUserId(userId);
        Session session = null;

        try {
            session = openSession();

            StringBuffer query = new StringBuffer();
            query.append(
                "FROM ReportsEntry IN CLASS com.ext.portlet.reports.service.persistence.ReportsEntryHBM WHERE ");

            if (userId == null) {
                query.append("userId is null");
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
                    reportsEntry, ReportsEntryHBMUtil.getInstance());
            com.ext.portlet.reports.model.ReportsEntry[] array = new com.ext.portlet.reports.model.ReportsEntry[3];
            array[0] = (com.ext.portlet.reports.model.ReportsEntry) objArray[0];
            array[1] = (com.ext.portlet.reports.model.ReportsEntry) objArray[1];
            array[2] = (com.ext.portlet.reports.model.ReportsEntry) objArray[2];

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
            query.append(
                "FROM ReportsEntry IN CLASS com.ext.portlet.reports.service.persistence.ReportsEntryHBM ");
            query.append("ORDER BY ");
            query.append("name ASC");

            Query q = session.createQuery(query.toString());
            Iterator itr = q.iterate();
            List list = new ArrayList();

            while (itr.hasNext()) {
                ReportsEntryHBM reportsEntryHBM = (ReportsEntryHBM) itr.next();
                list.add(ReportsEntryHBMUtil.model(reportsEntryHBM));
            }

            return list;
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
                "FROM ReportsEntry IN CLASS com.ext.portlet.reports.service.persistence.ReportsEntryHBM WHERE ");

            if (companyId == null) {
                query.append("companyId is null");
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
                ReportsEntryHBM reportsEntryHBM = (ReportsEntryHBM) itr.next();
                session.delete(reportsEntryHBM);
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
                "FROM ReportsEntry IN CLASS com.ext.portlet.reports.service.persistence.ReportsEntryHBM WHERE ");

            if (userId == null) {
                query.append("userId is null");
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
                ReportsEntryHBM reportsEntryHBM = (ReportsEntryHBM) itr.next();
                session.delete(reportsEntryHBM);
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
                "FROM ReportsEntry IN CLASS com.ext.portlet.reports.service.persistence.ReportsEntryHBM WHERE ");

            if (companyId == null) {
                query.append("companyId is null");
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
                "FROM ReportsEntry IN CLASS com.ext.portlet.reports.service.persistence.ReportsEntryHBM WHERE ");

            if (userId == null) {
                query.append("userId is null");
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
}
