package com.ext.portlet.reports.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ReportsEntrySoap implements Serializable {
    private String _entryId;
    private String _companyId;
    private String _userId;
    private String _userName;
    private Date _createDate;
    private Date _modifiedDate;
    private String _name;

    public ReportsEntrySoap() {
    }

    public static ReportsEntrySoap toSoapModel(ReportsEntry model) {
        ReportsEntrySoap soapModel = new ReportsEntrySoap();

        soapModel.setEntryId(model.getEntryId());
        soapModel.setCompanyId(model.getCompanyId());
        soapModel.setUserId(model.getUserId());
        soapModel.setUserName(model.getUserName());
        soapModel.setCreateDate(model.getCreateDate());
        soapModel.setModifiedDate(model.getModifiedDate());
        soapModel.setName(model.getName());

        return soapModel;
    }

    public static ReportsEntrySoap[] toSoapModels(ReportsEntry[] models) {
        ReportsEntrySoap[] soapModels = new ReportsEntrySoap[models.length];

        for (int i = 0; i < models.length; i++) {
            soapModels[i] = toSoapModel(models[i]);
        }

        return soapModels;
    }

    public static ReportsEntrySoap[][] toSoapModels(ReportsEntry[][] models) {
        ReportsEntrySoap[][] soapModels = null;

        if (models.length > 0) {
            soapModels = new ReportsEntrySoap[models.length][models[0].length];
        } else {
            soapModels = new ReportsEntrySoap[0][0];
        }

        for (int i = 0; i < models.length; i++) {
            soapModels[i] = toSoapModels(models[i]);
        }

        return soapModels;
    }

    public static ReportsEntrySoap[] toSoapModels(List<ReportsEntry> models) {
        List<ReportsEntrySoap> soapModels = new ArrayList<ReportsEntrySoap>(models.size());

        for (ReportsEntry model : models) {
            soapModels.add(toSoapModel(model));
        }

        return soapModels.toArray(new ReportsEntrySoap[soapModels.size()]);
    }

    public String getPrimaryKey() {
        return _entryId;
    }

    public void setPrimaryKey(String pk) {
        setEntryId(pk);
    }

    public String getEntryId() {
        return _entryId;
    }

    public void setEntryId(String entryId) {
        _entryId = entryId;
    }

    public String getCompanyId() {
        return _companyId;
    }

    public void setCompanyId(String companyId) {
        _companyId = companyId;
    }

    public String getUserId() {
        return _userId;
    }

    public void setUserId(String userId) {
        _userId = userId;
    }

    public String getUserName() {
        return _userName;
    }

    public void setUserName(String userName) {
        _userName = userName;
    }

    public Date getCreateDate() {
        return _createDate;
    }

    public void setCreateDate(Date createDate) {
        _createDate = createDate;
    }

    public Date getModifiedDate() {
        return _modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        _modifiedDate = modifiedDate;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }
}