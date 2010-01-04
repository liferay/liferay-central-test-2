package com.ext.portlet.reports.model.impl;

import com.ext.portlet.reports.model.ReportsEntry;
import com.ext.portlet.reports.model.ReportsEntrySoap;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.impl.BaseModelImpl;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <a href="ReportsEntryModelImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see       ReportsEntryImpl
 * @see       com.ext.portlet.reports.model.ReportsEntry
 * @see       com.ext.portlet.reports.model.ReportsEntryModel
 * @generated
 */
public class ReportsEntryModelImpl extends BaseModelImpl<ReportsEntry> {
    public static final String TABLE_NAME = "ReportsEntry";
    public static final Object[][] TABLE_COLUMNS = {
            { "entryId", new Integer(Types.VARCHAR) },
            { "companyId", new Integer(Types.VARCHAR) },
            { "userId", new Integer(Types.VARCHAR) },
            { "userName", new Integer(Types.VARCHAR) },
            { "createDate", new Integer(Types.TIMESTAMP) },
            { "modifiedDate", new Integer(Types.TIMESTAMP) },
            { "name", new Integer(Types.VARCHAR) }
        };
    public static final String TABLE_SQL_CREATE = "create table ReportsEntry (entryId VARCHAR(75) not null primary key,companyId VARCHAR(75) null,userId VARCHAR(75) null,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(75) null)";
    public static final String TABLE_SQL_DROP = "drop table ReportsEntry";
    public static final String ORDER_BY_JPQL = " ORDER BY reportsEntry.name ASC";
    public static final String ORDER_BY_SQL = " ORDER BY ReportsEntry.name ASC";
    public static final String DATA_SOURCE = "liferayDataSource";
    public static final String SESSION_FACTORY = "liferaySessionFactory";
    public static final String TX_MANAGER = "liferayTransactionManager";
    public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
                "value.object.entity.cache.enabled.com.ext.portlet.reports.model.ReportsEntry"),
            true);
    public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
                "value.object.finder.cache.enabled.com.ext.portlet.reports.model.ReportsEntry"),
            true);
    public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
                "lock.expiration.time.com.ext.portlet.reports.model.ReportsEntry"));
    private String _entryId;
    private String _companyId;
    private String _userId;
    private String _userName;
    private Date _createDate;
    private Date _modifiedDate;
    private String _name;

    public ReportsEntryModelImpl() {
    }

    public static ReportsEntry toModel(ReportsEntrySoap soapModel) {
        ReportsEntry model = new ReportsEntryImpl();

        model.setEntryId(soapModel.getEntryId());
        model.setCompanyId(soapModel.getCompanyId());
        model.setUserId(soapModel.getUserId());
        model.setUserName(soapModel.getUserName());
        model.setCreateDate(soapModel.getCreateDate());
        model.setModifiedDate(soapModel.getModifiedDate());
        model.setName(soapModel.getName());

        return model;
    }

    public static List<ReportsEntry> toModels(ReportsEntrySoap[] soapModels) {
        List<ReportsEntry> models = new ArrayList<ReportsEntry>(soapModels.length);

        for (ReportsEntrySoap soapModel : soapModels) {
            models.add(toModel(soapModel));
        }

        return models;
    }

    public String getPrimaryKey() {
        return _entryId;
    }

    public void setPrimaryKey(String pk) {
        setEntryId(pk);
    }

    public Serializable getPrimaryKeyObj() {
        return _entryId;
    }

    public String getEntryId() {
        return GetterUtil.getString(_entryId);
    }

    public void setEntryId(String entryId) {
        _entryId = entryId;
    }

    public String getCompanyId() {
        return GetterUtil.getString(_companyId);
    }

    public void setCompanyId(String companyId) {
        _companyId = companyId;
    }

    public String getUserId() {
        return GetterUtil.getString(_userId);
    }

    public void setUserId(String userId) {
        _userId = userId;
    }

    public String getUserName() {
        return GetterUtil.getString(_userName);
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
        return GetterUtil.getString(_name);
    }

    public void setName(String name) {
        _name = name;
    }

    public ReportsEntry toEscapedModel() {
        if (isEscapedModel()) {
            return (ReportsEntry) this;
        } else {
            ReportsEntry model = new ReportsEntryImpl();

            model.setNew(isNew());
            model.setEscapedModel(true);

            model.setEntryId(HtmlUtil.escape(getEntryId()));
            model.setCompanyId(HtmlUtil.escape(getCompanyId()));
            model.setUserId(HtmlUtil.escape(getUserId()));
            model.setUserName(HtmlUtil.escape(getUserName()));
            model.setCreateDate(getCreateDate());
            model.setModifiedDate(getModifiedDate());
            model.setName(HtmlUtil.escape(getName()));

            model = (ReportsEntry) Proxy.newProxyInstance(ReportsEntry.class.getClassLoader(),
                    new Class[] { ReportsEntry.class },
                    new ReadOnlyBeanHandler(model));

            return model;
        }
    }

    public Object clone() {
        ReportsEntryImpl clone = new ReportsEntryImpl();

        clone.setEntryId(getEntryId());
        clone.setCompanyId(getCompanyId());
        clone.setUserId(getUserId());
        clone.setUserName(getUserName());
        clone.setCreateDate(getCreateDate());
        clone.setModifiedDate(getModifiedDate());
        clone.setName(getName());

        return clone;
    }

    public int compareTo(ReportsEntry reportsEntry) {
        int value = 0;

        value = getName().toLowerCase()
                    .compareTo(reportsEntry.getName().toLowerCase());

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

    public String toString() {
        StringBundler sb = new StringBundler(15);

        sb.append("{entryId=");
        sb.append(getEntryId());
        sb.append(", companyId=");
        sb.append(getCompanyId());
        sb.append(", userId=");
        sb.append(getUserId());
        sb.append(", userName=");
        sb.append(getUserName());
        sb.append(", createDate=");
        sb.append(getCreateDate());
        sb.append(", modifiedDate=");
        sb.append(getModifiedDate());
        sb.append(", name=");
        sb.append(getName());
        sb.append("}");

        return sb.toString();
    }

    public String toXmlString() {
        StringBundler sb = new StringBundler(25);

        sb.append("<model><model-name>");
        sb.append("com.ext.portlet.reports.model.ReportsEntry");
        sb.append("</model-name>");

        sb.append(
            "<column><column-name>entryId</column-name><column-value><![CDATA[");
        sb.append(getEntryId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>companyId</column-name><column-value><![CDATA[");
        sb.append(getCompanyId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>userId</column-name><column-value><![CDATA[");
        sb.append(getUserId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>userName</column-name><column-value><![CDATA[");
        sb.append(getUserName());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>createDate</column-name><column-value><![CDATA[");
        sb.append(getCreateDate());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
        sb.append(getModifiedDate());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>name</column-name><column-value><![CDATA[");
        sb.append(getName());
        sb.append("]]></column-value></column>");

        sb.append("</model>");

        return sb.toString();
    }
}
