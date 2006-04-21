package com.ext.portlet.reports.model;

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.util.Date;


public class ReportsEntryModel extends BaseModel {
    public static boolean CACHEABLE = GetterUtil.get(PropsUtil.get(
                "value.object.cacheable.com.ext.portlet.reports.model.ReportsEntry"),
            VALUE_OBJECT_CACHEABLE);
    public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.get(PropsUtil.get(
                "xss.allow.com.ext.portlet.reports.model.ReportsEntry"),
            XSS_ALLOW);
    public static boolean XSS_ALLOW_ENTRYID = GetterUtil.get(PropsUtil.get(
                "xss.allow.com.ext.portlet.reports.model.ReportsEntry.entryId"),
            XSS_ALLOW_BY_MODEL);
    public static boolean XSS_ALLOW_COMPANYID = GetterUtil.get(PropsUtil.get(
                "xss.allow.com.ext.portlet.reports.model.ReportsEntry.companyId"),
            XSS_ALLOW_BY_MODEL);
    public static boolean XSS_ALLOW_USERID = GetterUtil.get(PropsUtil.get(
                "xss.allow.com.ext.portlet.reports.model.ReportsEntry.userId"),
            XSS_ALLOW_BY_MODEL);
    public static boolean XSS_ALLOW_USERNAME = GetterUtil.get(PropsUtil.get(
                "xss.allow.com.ext.portlet.reports.model.ReportsEntry.userName"),
            XSS_ALLOW_BY_MODEL);
    public static boolean XSS_ALLOW_NAME = GetterUtil.get(PropsUtil.get(
                "xss.allow.com.ext.portlet.reports.model.ReportsEntry.name"),
            XSS_ALLOW_BY_MODEL);
    public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
                "lock.expiration.time.com.ext.portlet.reports.model.ReportsEntryModel"));
    private String _entryId;
    private String _companyId;
    private String _userId;
    private String _userName;
    private Date _createDate;
    private Date _modifiedDate;
    private String _name;

    public ReportsEntryModel() {
    }

    public ReportsEntryModel(String entryId) {
        _entryId = entryId;
        setNew(true);
    }

    public ReportsEntryModel(String entryId, String companyId, String userId,
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

    public String getEntryId() {
        return GetterUtil.getString(_entryId);
    }

    public void setEntryId(String entryId) {
        if (((entryId == null) && (_entryId != null)) ||
                ((entryId != null) && (_entryId == null)) ||
                ((entryId != null) && (_entryId != null) &&
                !entryId.equals(_entryId))) {
            if (!XSS_ALLOW_ENTRYID) {
                entryId = XSSUtil.strip(entryId);
            }

            _entryId = entryId;
            setModified(true);
        }
    }

    public String getCompanyId() {
        return GetterUtil.getString(_companyId);
    }

    public void setCompanyId(String companyId) {
        if (((companyId == null) && (_companyId != null)) ||
                ((companyId != null) && (_companyId == null)) ||
                ((companyId != null) && (_companyId != null) &&
                !companyId.equals(_companyId))) {
            if (!XSS_ALLOW_COMPANYID) {
                companyId = XSSUtil.strip(companyId);
            }

            _companyId = companyId;
            setModified(true);
        }
    }

    public String getUserId() {
        return GetterUtil.getString(_userId);
    }

    public void setUserId(String userId) {
        if (((userId == null) && (_userId != null)) ||
                ((userId != null) && (_userId == null)) ||
                ((userId != null) && (_userId != null) &&
                !userId.equals(_userId))) {
            if (!XSS_ALLOW_USERID) {
                userId = XSSUtil.strip(userId);
            }

            _userId = userId;
            setModified(true);
        }
    }

    public String getUserName() {
        return GetterUtil.getString(_userName);
    }

    public void setUserName(String userName) {
        if (((userName == null) && (_userName != null)) ||
                ((userName != null) && (_userName == null)) ||
                ((userName != null) && (_userName != null) &&
                !userName.equals(_userName))) {
            if (!XSS_ALLOW_USERNAME) {
                userName = XSSUtil.strip(userName);
            }

            _userName = userName;
            setModified(true);
        }
    }

    public Date getCreateDate() {
        return _createDate;
    }

    public void setCreateDate(Date createDate) {
        if (((createDate == null) && (_createDate != null)) ||
                ((createDate != null) && (_createDate == null)) ||
                ((createDate != null) && (_createDate != null) &&
                !createDate.equals(_createDate))) {
            _createDate = createDate;
            setModified(true);
        }
    }

    public Date getModifiedDate() {
        return _modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        if (((modifiedDate == null) && (_modifiedDate != null)) ||
                ((modifiedDate != null) && (_modifiedDate == null)) ||
                ((modifiedDate != null) && (_modifiedDate != null) &&
                !modifiedDate.equals(_modifiedDate))) {
            _modifiedDate = modifiedDate;
            setModified(true);
        }
    }

    public String getName() {
        return GetterUtil.getString(_name);
    }

    public void setName(String name) {
        if (((name == null) && (_name != null)) ||
                ((name != null) && (_name == null)) ||
                ((name != null) && (_name != null) && !name.equals(_name))) {
            if (!XSS_ALLOW_NAME) {
                name = XSSUtil.strip(name);
            }

            _name = name;
            setModified(true);
        }
    }

    public BaseModel getProtected() {
        return null;
    }

    public void protect() {
    }

    public Object clone() {
        return new ReportsEntry(getEntryId(), getCompanyId(), getUserId(),
            getUserName(), getCreateDate(), getModifiedDate(), getName());
    }

    public int compareTo(Object obj) {
        if (obj == null) {
            return -1;
        }

        ReportsEntry reportsEntry = (ReportsEntry) obj;
        int value = 0;
        value = getName().toLowerCase().compareTo(reportsEntry.getName()
                                                              .toLowerCase());

        if (value != 0) {
            return value;
        }

        return 0;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        ReportsEntry reportsEntry = null;

        try {
            reportsEntry = (ReportsEntry) obj;
        } catch (ClassCastException cce) {
            return false;
        }

        String pk = reportsEntry.getPrimaryKey();

        if (getPrimaryKey().equals(pk)) {
            return true;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return getPrimaryKey().hashCode();
    }
}
