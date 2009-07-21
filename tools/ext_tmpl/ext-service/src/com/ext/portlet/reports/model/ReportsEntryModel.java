package com.ext.portlet.reports.model;

import com.liferay.portal.model.BaseModel;

import java.util.Date;


public interface ReportsEntryModel extends BaseModel<ReportsEntry> {
    public String getPrimaryKey();

    public void setPrimaryKey(String pk);

    public String getEntryId();

    public void setEntryId(String entryId);

    public String getCompanyId();

    public void setCompanyId(String companyId);

    public String getUserId();

    public void setUserId(String userId);

    public String getUserName();

    public void setUserName(String userName);

    public Date getCreateDate();

    public void setCreateDate(Date createDate);

    public Date getModifiedDate();

    public void setModifiedDate(Date modifiedDate);

    public String getName();

    public void setName(String name);

    public ReportsEntry toEscapedModel();
}