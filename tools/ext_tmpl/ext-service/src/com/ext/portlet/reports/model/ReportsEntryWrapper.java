package com.ext.portlet.reports.model;


/**
 * <a href="ReportsEntrySoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ReportsEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ReportsEntry
 * @generated
 */
public class ReportsEntryWrapper implements ReportsEntry {
    private ReportsEntry _reportsEntry;

    public ReportsEntryWrapper(ReportsEntry reportsEntry) {
        _reportsEntry = reportsEntry;
    }

    public java.lang.String getPrimaryKey() {
        return _reportsEntry.getPrimaryKey();
    }

    public void setPrimaryKey(java.lang.String pk) {
        _reportsEntry.setPrimaryKey(pk);
    }

    public java.lang.String getEntryId() {
        return _reportsEntry.getEntryId();
    }

    public void setEntryId(java.lang.String entryId) {
        _reportsEntry.setEntryId(entryId);
    }

    public java.lang.String getCompanyId() {
        return _reportsEntry.getCompanyId();
    }

    public void setCompanyId(java.lang.String companyId) {
        _reportsEntry.setCompanyId(companyId);
    }

    public java.lang.String getUserId() {
        return _reportsEntry.getUserId();
    }

    public void setUserId(java.lang.String userId) {
        _reportsEntry.setUserId(userId);
    }

    public java.lang.String getUserName() {
        return _reportsEntry.getUserName();
    }

    public void setUserName(java.lang.String userName) {
        _reportsEntry.setUserName(userName);
    }

    public java.util.Date getCreateDate() {
        return _reportsEntry.getCreateDate();
    }

    public void setCreateDate(java.util.Date createDate) {
        _reportsEntry.setCreateDate(createDate);
    }

    public java.util.Date getModifiedDate() {
        return _reportsEntry.getModifiedDate();
    }

    public void setModifiedDate(java.util.Date modifiedDate) {
        _reportsEntry.setModifiedDate(modifiedDate);
    }

    public java.lang.String getName() {
        return _reportsEntry.getName();
    }

    public void setName(java.lang.String name) {
        _reportsEntry.setName(name);
    }

    public ReportsEntry toEscapedModel() {
        return _reportsEntry.toEscapedModel();
    }

    public boolean isNew() {
        return _reportsEntry.isNew();
    }

    public boolean setNew(boolean n) {
        return _reportsEntry.setNew(n);
    }

    public boolean isCachedModel() {
        return _reportsEntry.isCachedModel();
    }

    public void setCachedModel(boolean cachedModel) {
        _reportsEntry.setCachedModel(cachedModel);
    }

    public boolean isEscapedModel() {
        return _reportsEntry.isEscapedModel();
    }

    public void setEscapedModel(boolean escapedModel) {
        _reportsEntry.setEscapedModel(escapedModel);
    }

    public java.io.Serializable getPrimaryKeyObj() {
        return _reportsEntry.getPrimaryKeyObj();
    }

    public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
        return _reportsEntry.getExpandoBridge();
    }

    public void setExpandoBridgeAttributes(
        com.liferay.portal.service.ServiceContext serviceContext) {
        _reportsEntry.setExpandoBridgeAttributes(serviceContext);
    }

    public java.lang.Object clone() {
        return _reportsEntry.clone();
    }

    public int compareTo(ReportsEntry reportsEntry) {
        return _reportsEntry.compareTo(reportsEntry);
    }

    public int hashCode() {
        return _reportsEntry.hashCode();
    }

    public java.lang.String toString() {
        return _reportsEntry.toString();
    }

    public java.lang.String toXmlString() {
        return _reportsEntry.toXmlString();
    }

    public ReportsEntry getWrappedReportsEntry() {
        return _reportsEntry;
    }
}
