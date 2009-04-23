/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.softwarecatalog.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.model.SCProductEntrySoap;

import java.io.Serializable;

import java.lang.StringBuilder;
import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="SCProductEntryModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>SCProductEntry</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.softwarecatalog.model.SCProductEntry
 * @see com.liferay.portlet.softwarecatalog.model.SCProductEntryModel
 * @see com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryImpl
 *
 */
public class SCProductEntryModelImpl extends BaseModelImpl<SCProductEntry> {
	public static final String TABLE_NAME = "SCProductEntry";
	public static final Object[][] TABLE_COLUMNS = {
			{ "productEntryId", new Integer(Types.BIGINT) },
			

			{ "groupId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "userId", new Integer(Types.BIGINT) },
			

			{ "userName", new Integer(Types.VARCHAR) },
			

			{ "createDate", new Integer(Types.TIMESTAMP) },
			

			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			

			{ "name", new Integer(Types.VARCHAR) },
			

			{ "type_", new Integer(Types.VARCHAR) },
			

			{ "tags", new Integer(Types.VARCHAR) },
			

			{ "shortDescription", new Integer(Types.VARCHAR) },
			

			{ "longDescription", new Integer(Types.VARCHAR) },
			

			{ "pageURL", new Integer(Types.VARCHAR) },
			

			{ "author", new Integer(Types.VARCHAR) },
			

			{ "repoGroupId", new Integer(Types.VARCHAR) },
			

			{ "repoArtifactId", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table SCProductEntry (productEntryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(75) null,type_ VARCHAR(75) null,tags VARCHAR(255) null,shortDescription STRING null,longDescription STRING null,pageURL STRING null,author VARCHAR(75) null,repoGroupId VARCHAR(75) null,repoArtifactId VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table SCProductEntry";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.softwarecatalog.model.SCProductEntry"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.softwarecatalog.model.SCProductEntry"),
			true);

	public static SCProductEntry toModel(SCProductEntrySoap soapModel) {
		SCProductEntry model = new SCProductEntryImpl();

		model.setProductEntryId(soapModel.getProductEntryId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setName(soapModel.getName());
		model.setType(soapModel.getType());
		model.setTags(soapModel.getTags());
		model.setShortDescription(soapModel.getShortDescription());
		model.setLongDescription(soapModel.getLongDescription());
		model.setPageURL(soapModel.getPageURL());
		model.setAuthor(soapModel.getAuthor());
		model.setRepoGroupId(soapModel.getRepoGroupId());
		model.setRepoArtifactId(soapModel.getRepoArtifactId());

		return model;
	}

	public static List<SCProductEntry> toModels(SCProductEntrySoap[] soapModels) {
		List<SCProductEntry> models = new ArrayList<SCProductEntry>(soapModels.length);

		for (SCProductEntrySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final boolean FINDER_CACHE_ENABLED_SCLICENSES_SCPRODUCTENTRIES =
		com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl.FINDER_CACHE_ENABLED_SCLICENSES_SCPRODUCTENTRIES;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.softwarecatalog.model.SCProductEntry"));

	public SCProductEntryModelImpl() {
	}

	public long getPrimaryKey() {
		return _productEntryId;
	}

	public void setPrimaryKey(long pk) {
		setProductEntryId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_productEntryId);
	}

	public long getProductEntryId() {
		return _productEntryId;
	}

	public void setProductEntryId(long productEntryId) {
		_productEntryId = productEntryId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
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

	public String getType() {
		return GetterUtil.getString(_type);
	}

	public void setType(String type) {
		_type = type;
	}

	public String getTags() {
		return GetterUtil.getString(_tags);
	}

	public void setTags(String tags) {
		_tags = tags;
	}

	public String getShortDescription() {
		return GetterUtil.getString(_shortDescription);
	}

	public void setShortDescription(String shortDescription) {
		_shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return GetterUtil.getString(_longDescription);
	}

	public void setLongDescription(String longDescription) {
		_longDescription = longDescription;
	}

	public String getPageURL() {
		return GetterUtil.getString(_pageURL);
	}

	public void setPageURL(String pageURL) {
		_pageURL = pageURL;
	}

	public String getAuthor() {
		return GetterUtil.getString(_author);
	}

	public void setAuthor(String author) {
		_author = author;
	}

	public String getRepoGroupId() {
		return GetterUtil.getString(_repoGroupId);
	}

	public void setRepoGroupId(String repoGroupId) {
		_repoGroupId = repoGroupId;

		if (_originalRepoGroupId == null) {
			_originalRepoGroupId = repoGroupId;
		}
	}

	public String getOriginalRepoGroupId() {
		return GetterUtil.getString(_originalRepoGroupId);
	}

	public String getRepoArtifactId() {
		return GetterUtil.getString(_repoArtifactId);
	}

	public void setRepoArtifactId(String repoArtifactId) {
		_repoArtifactId = repoArtifactId;

		if (_originalRepoArtifactId == null) {
			_originalRepoArtifactId = repoArtifactId;
		}
	}

	public String getOriginalRepoArtifactId() {
		return GetterUtil.getString(_originalRepoArtifactId);
	}

	public SCProductEntry toEscapedModel() {
		if (isEscapedModel()) {
			return (SCProductEntry)this;
		}
		else {
			SCProductEntry model = new SCProductEntryImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setProductEntryId(getProductEntryId());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setUserName(HtmlUtil.escape(getUserName()));
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setName(HtmlUtil.escape(getName()));
			model.setType(HtmlUtil.escape(getType()));
			model.setTags(HtmlUtil.escape(getTags()));
			model.setShortDescription(HtmlUtil.escape(getShortDescription()));
			model.setLongDescription(HtmlUtil.escape(getLongDescription()));
			model.setPageURL(HtmlUtil.escape(getPageURL()));
			model.setAuthor(HtmlUtil.escape(getAuthor()));
			model.setRepoGroupId(HtmlUtil.escape(getRepoGroupId()));
			model.setRepoArtifactId(HtmlUtil.escape(getRepoArtifactId()));

			model = (SCProductEntry)Proxy.newProxyInstance(SCProductEntry.class.getClassLoader(),
					new Class[] { SCProductEntry.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(SCProductEntry.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		SCProductEntryImpl clone = new SCProductEntryImpl();

		clone.setProductEntryId(getProductEntryId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setName(getName());
		clone.setType(getType());
		clone.setTags(getTags());
		clone.setShortDescription(getShortDescription());
		clone.setLongDescription(getLongDescription());
		clone.setPageURL(getPageURL());
		clone.setAuthor(getAuthor());
		clone.setRepoGroupId(getRepoGroupId());
		clone.setRepoArtifactId(getRepoArtifactId());

		return clone;
	}

	public int compareTo(SCProductEntry scProductEntry) {
		int value = 0;

		value = DateUtil.compareTo(getModifiedDate(),
				scProductEntry.getModifiedDate());

		value = value * -1;

		if (value != 0) {
			return value;
		}

		value = getName().compareTo(scProductEntry.getName());

		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		SCProductEntry scProductEntry = null;

		try {
			scProductEntry = (SCProductEntry)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = scProductEntry.getPrimaryKey();

		if (getPrimaryKey() == pk) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (int)getPrimaryKey();
	}

	public String toHtmlString() {
		StringBuilder sb = new StringBuilder();

		sb.append("<table class=\"lfr-table\">\n");

		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>productEntryId</b></td><td>" +
			getProductEntryId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>groupId</b></td><td>" +
			getGroupId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>companyId</b></td><td>" +
			getCompanyId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>userId</b></td><td>" +
			getUserId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>userName</b></td><td>" +
			getUserName() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>createDate</b></td><td>" +
			getCreateDate() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>modifiedDate</b></td><td>" +
			getModifiedDate() + "</td></tr>\n");
		sb.append("<tr><td align=\"right\" valign=\"top\"><b>name</b></td><td>" +
			getName() + "</td></tr>\n");
		sb.append("<tr><td align=\"right\" valign=\"top\"><b>type</b></td><td>" +
			getType() + "</td></tr>\n");
		sb.append("<tr><td align=\"right\" valign=\"top\"><b>tags</b></td><td>" +
			getTags() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>shortDescription</b></td><td>" +
			getShortDescription() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>longDescription</b></td><td>" +
			getLongDescription() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>pageURL</b></td><td>" +
			getPageURL() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>author</b></td><td>" +
			getAuthor() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>repoGroupId</b></td><td>" +
			getRepoGroupId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>repoArtifactId</b></td><td>" +
			getRepoArtifactId() + "</td></tr>\n");

		sb.append("</table>");

		return sb.toString();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("com.liferay.portlet.softwarecatalog.model.SCProductEntry (");

		sb.append("productEntryId: " + getProductEntryId() + ", ");
		sb.append("groupId: " + getGroupId() + ", ");
		sb.append("companyId: " + getCompanyId() + ", ");
		sb.append("userId: " + getUserId() + ", ");
		sb.append("userName: " + getUserName() + ", ");
		sb.append("createDate: " + getCreateDate() + ", ");
		sb.append("modifiedDate: " + getModifiedDate() + ", ");
		sb.append("name: " + getName() + ", ");
		sb.append("type: " + getType() + ", ");
		sb.append("tags: " + getTags() + ", ");
		sb.append("shortDescription: " + getShortDescription() + ", ");
		sb.append("longDescription: " + getLongDescription() + ", ");
		sb.append("pageURL: " + getPageURL() + ", ");
		sb.append("author: " + getAuthor() + ", ");
		sb.append("repoGroupId: " + getRepoGroupId() + ", ");
		sb.append("repoArtifactId: " + getRepoArtifactId() + ", ");

		sb.append(")");

		return sb.toString();
	}

	private long _productEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _type;
	private String _tags;
	private String _shortDescription;
	private String _longDescription;
	private String _pageURL;
	private String _author;
	private String _repoGroupId;
	private String _originalRepoGroupId;
	private String _repoArtifactId;
	private String _originalRepoArtifactId;
	private transient ExpandoBridge _expandoBridge;
}