package com.ext.portlet.reports.model;

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;


/**
 * <a href="ReportsEntryModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the ReportsEntry table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ReportsEntry
 * @see       com.ext.portlet.reports.model.impl.ReportsEntryImpl
 * @see       com.ext.portlet.reports.model.impl.ReportsEntryModelImpl
 * @generated
 */
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

    public boolean isNew();

    public boolean setNew(boolean n);

    public boolean isCachedModel();

    public void setCachedModel(boolean cachedModel);

    public boolean isEscapedModel();

    public void setEscapedModel(boolean escapedModel);

    public Serializable getPrimaryKeyObj();

    public ExpandoBridge getExpandoBridge();

    public void setExpandoBridgeAttributes(ServiceContext serviceContext);

    public Object clone();

    public int compareTo(ReportsEntry reportsEntry);

    public int hashCode();

    public String toString();

    public String toXmlString();
}
