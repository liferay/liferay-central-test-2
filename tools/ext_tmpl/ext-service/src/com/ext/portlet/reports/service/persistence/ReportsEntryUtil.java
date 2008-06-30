package com.ext.portlet.reports.service.persistence;

public class ReportsEntryUtil {
    private static final String _UTIL = ReportsEntryUtil.class.getName();
    private static ReportsEntryUtil _util;
    private ReportsEntryPersistence _persistence;

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

    public static com.ext.portlet.reports.model.ReportsEntry remove(
        com.ext.portlet.reports.model.ReportsEntry reportsEntry)
        throws com.liferay.portal.SystemException {
        return getPersistence().remove(reportsEntry);
    }

    /**
     * @deprecated Use <code>update(ReportsEntry reportsEntry, boolean merge)</code>.
     */
    public static com.ext.portlet.reports.model.ReportsEntry update(
        com.ext.portlet.reports.model.ReportsEntry reportsEntry)
        throws com.liferay.portal.SystemException {
        return getPersistence().update(reportsEntry);
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
    public static com.ext.portlet.reports.model.ReportsEntry update(
        com.ext.portlet.reports.model.ReportsEntry reportsEntry, boolean merge)
        throws com.liferay.portal.SystemException {
        return getPersistence().update(reportsEntry, merge);
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

    public static java.util.List<com.ext.portlet.reports.model.ReportsEntry> findWithDynamicQuery(
        com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
        throws com.liferay.portal.SystemException {
        return getPersistence().findWithDynamicQuery(queryInitializer);
    }

    public static java.util.List<com.ext.portlet.reports.model.ReportsEntry> findWithDynamicQuery(
        com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
        int start, int end) throws com.liferay.portal.SystemException {
        return getPersistence()
                   .findWithDynamicQuery(queryInitializer, start, end);
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

    public static void registerListener(
        com.liferay.portal.model.ModelListener listener) {
        getPersistence().registerListener(listener);
    }

    public static void unregisterListener(
        com.liferay.portal.model.ModelListener listener) {
        getPersistence().unregisterListener(listener);
    }

    public static ReportsEntryPersistence getPersistence() {
        return _getUtil()._persistence;
    }

    public void setPersistence(ReportsEntryPersistence persistence) {
        _persistence = persistence;
    }

    private static ReportsEntryUtil _getUtil() {
        if (_util == null) {
            _util = (ReportsEntryUtil) com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
        }

        return _util;
    }
}
