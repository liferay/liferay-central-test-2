package com.ext.portlet.reports.service.persistence;

import java.util.Date;


public class ReportsEntryHBM {
    private String _entryId;
    private String _companyId;
    private String _userId;
    private String _userName;
    private Date _createDate;
    private Date _modifiedDate;
    private String _name;

    protected ReportsEntryHBM() {
    }

    protected ReportsEntryHBM(String entryId) {
        _entryId = entryId;
    }

    protected ReportsEntryHBM(String entryId, String companyId, String userId,
        String userName, Date createDate, Date modifiedDate, String name) {
        _entryId = entryId;
        _companyId = companyId;
        _userId = userId;
        _userName = userName;
        _createDate = createDate;
        _modifiedDate = modifiedDate;
        _name = name;
    }

    public String getPrimaryKey() {
        return _entryId;
    }

    protected void setPrimaryKey(String pk) {
        _entryId = pk;
    }

    protected String getEntryId() {
        return _entryId;
    }

    protected void setEntryId(String entryId) {
        _entryId = entryId;
    }

    protected String getCompanyId() {
        return _companyId;
    }

    protected void setCompanyId(String companyId) {
        _companyId = companyId;
    }

    protected String getUserId() {
        return _userId;
    }

    protected void setUserId(String userId) {
        _userId = userId;
    }

    protected String getUserName() {
        return _userName;
    }

    protected void setUserName(String userName) {
        _userName = userName;
    }

    protected Date getCreateDate() {
        return _createDate;
    }

    protected void setCreateDate(Date createDate) {
        _createDate = createDate;
    }

    protected Date getModifiedDate() {
        return _modifiedDate;
    }

    protected void setModifiedDate(Date modifiedDate) {
        _modifiedDate = modifiedDate;
    }

    protected String getName() {
        return _name;
    }

    protected void setName(String name) {
        _name = name;
    }
}
