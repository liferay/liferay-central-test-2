package com.ext.portlet.reports.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;


public class ReportsEntryUtil {
    public static final String CLASS_NAME = ReportsEntryUtil.class.getName();
    public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
                "value.object.listener.com.ext.portlet.reports.model.ReportsEntry"));
    private static Log _log = LogFactory.getLog(ReportsEntryUtil.class);
    private ReportsEntryPersistence _persistence;

    public static com.ext.portlet.reports.model.ReportsEntry create(
        java.lang.String entryId) {
        return getPersistence().create(entryId);
    }

    public static com.ext.portlet.reports.model.ReportsEntry remove(
        java.lang.String entryId)
        throws com.ext.portlet.reports.NoSuchEntryException, 
            com.liferay.portal.SystemException {
        ModelListener listener = null;

        if (Validator.isNotNull(LISTENER)) {
            try {
                listener = (ModelListener) Class.forName(LISTENER).newInstance();
            } catch (Exception e) {
                _log.error(e);
            }
        }

        if (listener != null) {
            listener.onBeforeRemove(findByPrimaryKey(entryId));
        }

        com.ext.portlet.reports.model.ReportsEntry reportsEntry = getPersistence()
                                                                      .remove(entryId);

        if (listener != null) {
            listener.onAfterRemove(reportsEntry);
        }

        return reportsEntry;
    }

    public static com.ext.portlet.reports.model.ReportsEntry update(
        com.ext.portlet.reports.model.ReportsEntry reportsEntry)
        throws com.liferay.portal.SystemException {
        ModelListener listener = null;

        if (Validator.isNotNull(LISTENER)) {
            try {
                listener = (ModelListener) Class.forName(LISTENER).newInstance();
            } catch (Exception e) {
                _log.error(e);
            }
        }

        boolean isNew = reportsEntry.isNew();

        if (listener != null) {
            if (isNew) {
                listener.onBeforeCreate(reportsEntry);
            } else {
                listener.onBeforeUpdate(reportsEntry);
            }
        }

        reportsEntry = getPersistence().update(reportsEntry);

        if (listener != null) {
            if (isNew) {
                listener.onAfterCreate(reportsEntry);
            } else {
                listener.onAfterUpdate(reportsEntry);
            }
        }

        return reportsEntry;
    }

    public static com.ext.portlet.reports.model.ReportsEntry findByPrimaryKey(
        java.lang.String entryId)
        throws com.ext.portlet.reports.NoSuchEntryException, 
            com.liferay.portal.SystemException {
        return getPersistence().findByPrimaryKey(entryId);
    }

    public static java.util.List findByCompanyId(java.lang.String companyId)
        throws com.liferay.portal.SystemException {
        return getPersistence().findByCompanyId(companyId);
    }

    public static java.util.List findByCompanyId(java.lang.String companyId,
        int begin, int end) throws com.liferay.portal.SystemException {
        return getPersistence().findByCompanyId(companyId, begin, end);
    }

    public static java.util.List findByCompanyId(java.lang.String companyId,
        int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
        throws com.liferay.portal.SystemException {
        return getPersistence().findByCompanyId(companyId, begin, end, obc);
    }

    public static com.ext.portlet.reports.model.ReportsEntry findByCompanyId_First(
        java.lang.String companyId,
        com.liferay.util.dao.hibernate.OrderByComparator obc)
        throws com.ext.portlet.reports.NoSuchEntryException, 
            com.liferay.portal.SystemException {
        return getPersistence().findByCompanyId_First(companyId, obc);
    }

    public static com.ext.portlet.reports.model.ReportsEntry findByCompanyId_Last(
        java.lang.String companyId,
        com.liferay.util.dao.hibernate.OrderByComparator obc)
        throws com.ext.portlet.reports.NoSuchEntryException, 
            com.liferay.portal.SystemException {
        return getPersistence().findByCompanyId_Last(companyId, obc);
    }

    public static com.ext.portlet.reports.model.ReportsEntry[] findByCompanyId_PrevAndNext(
        java.lang.String entryId, java.lang.String companyId,
        com.liferay.util.dao.hibernate.OrderByComparator obc)
        throws com.ext.portlet.reports.NoSuchEntryException, 
            com.liferay.portal.SystemException {
        return getPersistence().findByCompanyId_PrevAndNext(entryId, companyId,
            obc);
    }

    public static java.util.List findByUserId(java.lang.String userId)
        throws com.liferay.portal.SystemException {
        return getPersistence().findByUserId(userId);
    }

    public static java.util.List findByUserId(java.lang.String userId,
        int begin, int end) throws com.liferay.portal.SystemException {
        return getPersistence().findByUserId(userId, begin, end);
    }

    public static java.util.List findByUserId(java.lang.String userId,
        int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
        throws com.liferay.portal.SystemException {
        return getPersistence().findByUserId(userId, begin, end, obc);
    }

    public static com.ext.portlet.reports.model.ReportsEntry findByUserId_First(
        java.lang.String userId,
        com.liferay.util.dao.hibernate.OrderByComparator obc)
        throws com.ext.portlet.reports.NoSuchEntryException, 
            com.liferay.portal.SystemException {
        return getPersistence().findByUserId_First(userId, obc);
    }

    public static com.ext.portlet.reports.model.ReportsEntry findByUserId_Last(
        java.lang.String userId,
        com.liferay.util.dao.hibernate.OrderByComparator obc)
        throws com.ext.portlet.reports.NoSuchEntryException, 
            com.liferay.portal.SystemException {
        return getPersistence().findByUserId_Last(userId, obc);
    }

    public static com.ext.portlet.reports.model.ReportsEntry[] findByUserId_PrevAndNext(
        java.lang.String entryId, java.lang.String userId,
        com.liferay.util.dao.hibernate.OrderByComparator obc)
        throws com.ext.portlet.reports.NoSuchEntryException, 
            com.liferay.portal.SystemException {
        return getPersistence().findByUserId_PrevAndNext(entryId, userId, obc);
    }

    public static java.util.List findAll()
        throws com.liferay.portal.SystemException {
        return getPersistence().findAll();
    }

    public static void removeByCompanyId(java.lang.String companyId)
        throws com.liferay.portal.SystemException {
        getPersistence().removeByCompanyId(companyId);
    }

    public static void removeByUserId(java.lang.String userId)
        throws com.liferay.portal.SystemException {
        getPersistence().removeByUserId(userId);
    }

    public static int countByCompanyId(java.lang.String companyId)
        throws com.liferay.portal.SystemException {
        return getPersistence().countByCompanyId(companyId);
    }

    public static int countByUserId(java.lang.String userId)
        throws com.liferay.portal.SystemException {
        return getPersistence().countByUserId(userId);
    }

    public static void initDao() {
        getPersistence().initDao();
    }

    public static ReportsEntryPersistence getPersistence() {
        ApplicationContext ctx = SpringUtil.getContext();
        ReportsEntryUtil util = (ReportsEntryUtil) ctx.getBean(CLASS_NAME);

        return util._persistence;
    }

    public void setPersistence(ReportsEntryPersistence persistence) {
        _persistence = persistence;
    }
}
