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

package com.liferay.portlet.wiki.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="WikiPageModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>WikiPage</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.wiki.model.WikiPage
 * @see com.liferay.portlet.wiki.model.WikiPageModel
 * @see com.liferay.portlet.wiki.model.impl.WikiPageImpl
 *
 */
public class WikiPageModelImpl extends BaseModelImpl<WikiPage> {
	public static final String TABLE_NAME = "WikiPage";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", new Integer(Types.VARCHAR) },
			

			{ "pageId", new Integer(Types.BIGINT) },
			

			{ "resourcePrimKey", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "userId", new Integer(Types.BIGINT) },
			

			{ "userName", new Integer(Types.VARCHAR) },
			

			{ "createDate", new Integer(Types.TIMESTAMP) },
			

			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			

			{ "nodeId", new Integer(Types.BIGINT) },
			

			{ "title", new Integer(Types.VARCHAR) },
			

			{ "version", new Integer(Types.DOUBLE) },
			

			{ "minorEdit", new Integer(Types.BOOLEAN) },
			

			{ "content", new Integer(Types.CLOB) },
			

			{ "summary", new Integer(Types.VARCHAR) },
			

			{ "format", new Integer(Types.VARCHAR) },
			

			{ "head", new Integer(Types.BOOLEAN) },
			

			{ "parentTitle", new Integer(Types.VARCHAR) },
			

			{ "redirectTitle", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table WikiPage (uuid_ VARCHAR(75) null,pageId LONG not null primary key,resourcePrimKey LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,nodeId LONG,title VARCHAR(255) null,version DOUBLE,minorEdit BOOLEAN,content TEXT null,summary STRING null,format VARCHAR(75) null,head BOOLEAN,parentTitle VARCHAR(75) null,redirectTitle VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table WikiPage";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.wiki.model.WikiPage"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.wiki.model.WikiPage"),
			true);

	public static WikiPage toModel(WikiPageSoap soapModel) {
		WikiPage model = new WikiPageImpl();

		model.setUuid(soapModel.getUuid());
		model.setPageId(soapModel.getPageId());
		model.setResourcePrimKey(soapModel.getResourcePrimKey());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setNodeId(soapModel.getNodeId());
		model.setTitle(soapModel.getTitle());
		model.setVersion(soapModel.getVersion());
		model.setMinorEdit(soapModel.getMinorEdit());
		model.setContent(soapModel.getContent());
		model.setSummary(soapModel.getSummary());
		model.setFormat(soapModel.getFormat());
		model.setHead(soapModel.getHead());
		model.setParentTitle(soapModel.getParentTitle());
		model.setRedirectTitle(soapModel.getRedirectTitle());

		return model;
	}

	public static List<WikiPage> toModels(WikiPageSoap[] soapModels) {
		List<WikiPage> models = new ArrayList<WikiPage>(soapModels.length);

		for (WikiPageSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.wiki.model.WikiPage"));

	public WikiPageModelImpl() {
	}

	public long getPrimaryKey() {
		return _pageId;
	}

	public void setPrimaryKey(long pk) {
		setPageId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_pageId);
	}

	public String getUuid() {
		return GetterUtil.getString(_uuid);
	}

	public void setUuid(String uuid) {
		if ((uuid != null) && !uuid.equals(_uuid)) {
			_uuid = uuid;
		}
	}

	public long getPageId() {
		return _pageId;
	}

	public void setPageId(long pageId) {
		if (pageId != _pageId) {
			_pageId = pageId;
		}
	}

	public long getResourcePrimKey() {
		return _resourcePrimKey;
	}

	public void setResourcePrimKey(long resourcePrimKey) {
		if (resourcePrimKey != _resourcePrimKey) {
			_resourcePrimKey = resourcePrimKey;
		}
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		if (companyId != _companyId) {
			_companyId = companyId;
		}
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		if (userId != _userId) {
			_userId = userId;
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
			_userName = userName;
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
		}
	}

	public long getNodeId() {
		return _nodeId;
	}

	public void setNodeId(long nodeId) {
		if (nodeId != _nodeId) {
			_nodeId = nodeId;

			if (!_setOriginalNodeId) {
				_setOriginalNodeId = true;

				_originalNodeId = nodeId;
			}
		}
	}

	public long getOriginalNodeId() {
		return _originalNodeId;
	}

	public String getTitle() {
		return GetterUtil.getString(_title);
	}

	public void setTitle(String title) {
		if (((title == null) && (_title != null)) ||
				((title != null) && (_title == null)) ||
				((title != null) && (_title != null) && !title.equals(_title))) {
			_title = title;

			if (_originalTitle == null) {
				_originalTitle = title;
			}
		}
	}

	public String getOriginalTitle() {
		return GetterUtil.getString(_originalTitle);
	}

	public double getVersion() {
		return _version;
	}

	public void setVersion(double version) {
		if (version != _version) {
			_version = version;

			if (!_setOriginalVersion) {
				_setOriginalVersion = true;

				_originalVersion = version;
			}
		}
	}

	public double getOriginalVersion() {
		return _originalVersion;
	}

	public boolean getMinorEdit() {
		return _minorEdit;
	}

	public boolean isMinorEdit() {
		return _minorEdit;
	}

	public void setMinorEdit(boolean minorEdit) {
		if (minorEdit != _minorEdit) {
			_minorEdit = minorEdit;
		}
	}

	public String getContent() {
		return GetterUtil.getString(_content);
	}

	public void setContent(String content) {
		if (((content == null) && (_content != null)) ||
				((content != null) && (_content == null)) ||
				((content != null) && (_content != null) &&
				!content.equals(_content))) {
			_content = content;
		}
	}

	public String getSummary() {
		return GetterUtil.getString(_summary);
	}

	public void setSummary(String summary) {
		if (((summary == null) && (_summary != null)) ||
				((summary != null) && (_summary == null)) ||
				((summary != null) && (_summary != null) &&
				!summary.equals(_summary))) {
			_summary = summary;
		}
	}

	public String getFormat() {
		return GetterUtil.getString(_format);
	}

	public void setFormat(String format) {
		if (((format == null) && (_format != null)) ||
				((format != null) && (_format == null)) ||
				((format != null) && (_format != null) &&
				!format.equals(_format))) {
			_format = format;
		}
	}

	public boolean getHead() {
		return _head;
	}

	public boolean isHead() {
		return _head;
	}

	public void setHead(boolean head) {
		if (head != _head) {
			_head = head;
		}
	}

	public String getParentTitle() {
		return GetterUtil.getString(_parentTitle);
	}

	public void setParentTitle(String parentTitle) {
		if (((parentTitle == null) && (_parentTitle != null)) ||
				((parentTitle != null) && (_parentTitle == null)) ||
				((parentTitle != null) && (_parentTitle != null) &&
				!parentTitle.equals(_parentTitle))) {
			_parentTitle = parentTitle;
		}
	}

	public String getRedirectTitle() {
		return GetterUtil.getString(_redirectTitle);
	}

	public void setRedirectTitle(String redirectTitle) {
		if (((redirectTitle == null) && (_redirectTitle != null)) ||
				((redirectTitle != null) && (_redirectTitle == null)) ||
				((redirectTitle != null) && (_redirectTitle != null) &&
				!redirectTitle.equals(_redirectTitle))) {
			_redirectTitle = redirectTitle;
		}
	}

	public WikiPage toEscapedModel() {
		if (isEscapedModel()) {
			return (WikiPage)this;
		}
		else {
			WikiPage model = new WikiPageImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setUuid(HtmlUtil.escape(getUuid()));
			model.setPageId(getPageId());
			model.setResourcePrimKey(getResourcePrimKey());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setUserName(HtmlUtil.escape(getUserName()));
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setNodeId(getNodeId());
			model.setTitle(HtmlUtil.escape(getTitle()));
			model.setVersion(getVersion());
			model.setMinorEdit(getMinorEdit());
			model.setContent(HtmlUtil.escape(getContent()));
			model.setSummary(HtmlUtil.escape(getSummary()));
			model.setFormat(HtmlUtil.escape(getFormat()));
			model.setHead(getHead());
			model.setParentTitle(HtmlUtil.escape(getParentTitle()));
			model.setRedirectTitle(HtmlUtil.escape(getRedirectTitle()));

			model = (WikiPage)Proxy.newProxyInstance(WikiPage.class.getClassLoader(),
					new Class[] { WikiPage.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(WikiPage.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		WikiPageImpl clone = new WikiPageImpl();

		clone.setUuid(getUuid());
		clone.setPageId(getPageId());
		clone.setResourcePrimKey(getResourcePrimKey());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setNodeId(getNodeId());
		clone.setTitle(getTitle());
		clone.setVersion(getVersion());
		clone.setMinorEdit(getMinorEdit());
		clone.setContent(getContent());
		clone.setSummary(getSummary());
		clone.setFormat(getFormat());
		clone.setHead(getHead());
		clone.setParentTitle(getParentTitle());
		clone.setRedirectTitle(getRedirectTitle());

		return clone;
	}

	public int compareTo(WikiPage wikiPage) {
		int value = 0;

		if (getNodeId() < wikiPage.getNodeId()) {
			value = -1;
		}
		else if (getNodeId() > wikiPage.getNodeId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		value = getTitle().toLowerCase()
					.compareTo(wikiPage.getTitle().toLowerCase());

		if (value != 0) {
			return value;
		}

		if (getVersion() < wikiPage.getVersion()) {
			value = -1;
		}
		else if (getVersion() > wikiPage.getVersion()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		WikiPage wikiPage = null;

		try {
			wikiPage = (WikiPage)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = wikiPage.getPrimaryKey();

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

	private String _uuid;
	private long _pageId;
	private long _resourcePrimKey;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _nodeId;
	private long _originalNodeId;
	private boolean _setOriginalNodeId;
	private String _title;
	private String _originalTitle;
	private double _version;
	private double _originalVersion;
	private boolean _setOriginalVersion;
	private boolean _minorEdit;
	private String _content;
	private String _summary;
	private String _format;
	private boolean _head;
	private String _parentTitle;
	private String _redirectTitle;
	private transient ExpandoBridge _expandoBridge;
}