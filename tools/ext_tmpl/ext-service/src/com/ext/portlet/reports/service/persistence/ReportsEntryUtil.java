package com.ext.portlet.reports.service.persistence;

import com.ext.portlet.reports.model.ReportsEntry;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;

import java.util.List;


/**
 * <a href="ReportsEntryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ReportsEntryPersistence
 * @see       ReportsEntryPersistenceImpl
 * @generated
 */
public class ReportsEntryUtil {
    private static ReportsEntryPersistence _persistence;

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
     */
    public static void clearCache() {
        getPersistence().clearCache();
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
     */
    public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
        throws SystemException {
        return getPersistence().findWithDynamicQuery(dynamicQuery);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
     */
    public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
        int start, int end) throws SystemException {
        return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
     */
    public static ReportsEntry remove(ReportsEntry reportsEntry)
        throws SystemException {
        return getPersistence().remove(reportsEntry);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
     */
    public static ReportsEntry update(ReportsEntry reportsEntry, boolean merge)
        throws SystemException {
        return getPersistence().update(reportsEntry, merge);
    }

    public static void cacheResult(
        com.ext.portlet.reports.model.ReportsEntry reportsEntry) {
        getPersistence().cacheResult(reportsEntry);
    }

    public static void cacheResult(
        java.util.List<com.ext.portlet.reports.model.ReportsEntry> reportsEntries) {
        getPersistence().cacheResult(reportsEntries);
    }

    public static com.ext.portlet.reports.model.ReportsEntry create(
        java.lang.String entryId) {
        return getPersistence().create(entryId);
    }

    public static com.ext.portlet.reports.model.ReportsEntry remove(
        java.lang.String entryId)
        throws com.ext.portlet.reports.NoSuchEntryException,
            com.liferay.portal.SystemException {
        return getPersistence().remove(entryId);
    }

    public static com.ext.portlet.reports.model.ReportsEntry updateImpl(
        com.ext.portlet.reports.model.ReportsEntry reportsEntry, boolean merge)
        throws com.liferay.portal.SystemException {
        return getPersistence().updateImpl(reportsEntry, merge);
    }

    public static com.ext.portlet.reports.model.ReportsEntry findByPrimaryKey(
        java.lang.String entryId)
        throws com.ext.portlet.reports.NoSuchEntryException,
            com.liferay.portal.SystemException {
        return getPersistence().findByPrimaryKey(entryId);
    }

    public static com.ext.portlet.reports.model.ReportsEntry fetchByPrimaryKey(
        java.lang.String entryId) throws com.liferay.portal.SystemException {
        return getPersistence().fetchByPrimaryKey(entryId);
    }

    public static java.util.List<com.ext.portlet.reports.model.ReportsEntry> findByCompanyId(
        java.lang.String companyId) throws com.liferay.portal.SystemException {
        return getPersistence().findByCompanyId(companyId);
    }

    public static java.util.List<com.ext.portlet.reports.model.ReportsEntry> findByCompanyId(
        java.lang.String companyId, int start, int end)
        throws com.liferay.portal.SystemException {
        return getPersistence().findByCompanyId(companyId, start, end);
    }

    public static java.util.List<com.ext.portlet.reports.model.ReportsEntry> findByCompanyId(
        java.lang.String companyId, int start, int end,
        com.liferay.portal.kernel.util.OrderByComparator obc)
        throws com.liferay.portal.SystemException {
        return getPersistence().findByCompanyId(companyId, start, end, obc);
    }

    public static com.ext.portlet.reports.model.ReportsEntry findByCompanyId_First(
        java.lang.String companyId,
        com.liferay.portal.kernel.util.OrderByComparator obc)
        throws com.ext.portlet.reports.NoSuchEntryException,
            com.liferay.portal.SystemException {
        return getPersistence().findByCompanyId_First(companyId, obc);
    }

    public static com.ext.portlet.reports.model.ReportsEntry findByCompanyId_Last(
        java.lang.String companyId,
        com.liferay.portal.kernel.util.OrderByComparator obc)
        throws com.ext.portlet.reports.NoSuchEntryException,
            com.liferay.portal.SystemException {
        return getPersistence().findByCompanyId_Last(companyId, obc);
    }

    public static com.ext.portlet.reports.model.ReportsEntry[] findByCompanyId_PrevAndNext(
        java.lang.String entryId, java.lang.String companyId,
        com.liferay.portal.kernel.util.OrderByComparator obc)
        throws com.ext.portlet.reports.NoSuchEntryException,
            com.liferay.portal.SystemException {
        return getPersistence()
                   .findByCompanyId_PrevAndNext(entryId, companyId, obc);
    }

    public static java.util.List<com.ext.portlet.reports.model.ReportsEntry> findByUserId(
        java.lang.String userId) throws com.liferay.portal.SystemException {
        return getPersistence().findByUserId(userId);
    }

    public static java.util.List<com.ext.portlet.reports.model.ReportsEntry> findByUserId(
        java.lang.String userId, int start, int end)
        throws com.liferay.portal.SystemException {
        return getPersistence().findByUserId(userId, start, end);
    }

    public static java.util.List<com.ext.portlet.reports.model.ReportsEntry> findByUserId(
        java.lang.String userId, int start, int end,
        com.liferay.portal.kernel.util.OrderByComparator obc)
        throws com.liferay.portal.SystemException {
        return getPersistence().findByUserId(userId, start, end, obc);
    }

    public static com.ext.portlet.reports.model.ReportsEntry findByUserId_First(
        java.lang.String userId,
        com.liferay.portal.kernel.util.OrderByComparator obc)
        throws com.ext.portlet.reports.NoSuchEntryException,
            com.liferay.portal.SystemException {
        return getPersistence().findByUserId_First(userId, obc);
    }

    public static com.ext.portlet.reports.model.ReportsEntry findByUserId_Last(
        java.lang.String userId,
        com.liferay.portal.kernel.util.OrderByComparator obc)
        throws com.ext.portlet.reports.NoSuchEntryException,
            com.liferay.portal.SystemException {
        return getPersistence().findByUserId_Last(userId, obc);
    }

    public static com.ext.portlet.reports.model.ReportsEntry[] findByUserId_PrevAndNext(
        java.lang.String entryId, java.lang.String userId,
        com.liferay.portal.kernel.util.OrderByComparator obc)
        throws com.ext.portlet.reports.NoSuchEntryException,
            com.liferay.portal.SystemException {
        return getPersistence().findByUserId_PrevAndNext(entryId, userId, obc);
    }

    public static java.util.List<com.ext.portlet.reports.model.ReportsEntry> findAll()
        throws com.liferay.portal.SystemException {
        return getPersistence().findAll();
    }

    public static java.util.List<com.ext.portlet.reports.model.ReportsEntry> findAll(
        int start, int end) throws com.liferay.portal.SystemException {
        return getPersistence().findAll(start, end);
    }

    public static java.util.List<com.ext.portlet.reports.model.ReportsEntry> findAll(
        int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
        throws com.liferay.portal.SystemException {
        return getPersistence().findAll(start, end, obc);
    }

    public static void removeByCompanyId(java.lang.String companyId)
        throws com.liferay.portal.SystemException {
        getPersistence().removeByCompanyId(companyId);
    }

    public static void removeByUserId(java.lang.String userId)
        throws com.liferay.portal.SystemException {
        getPersistence().removeByUserId(userId);
    }

    public static void removeAll() throws com.liferay.portal.SystemException {
        getPersistence().removeAll();
    }

    public static int countByCompanyId(java.lang.String companyId)
        throws com.liferay.portal.SystemException {
        return getPersistence().countByCompanyId(companyId);
    }

    public static int countByUserId(java.lang.String userId)
        throws com.liferay.portal.SystemException {
        return getPersistence().countByUserId(userId);
    }

    public static int countAll() throws com.liferay.portal.SystemException {
        return getPersistence().countAll();
    }

    public static ReportsEntryPersistence getPersistence() {
        if (_persistence == null) {
            _persistence = (ReportsEntryPersistence) PortalBeanLocatorUtil.locate(ReportsEntryPersistence.class.getName());
        }

        return _persistence;
    }

    public void setPersistence(ReportsEntryPersistence persistence) {
        _persistence = persistence;
    }
}
