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

package com.liferay.portlet.bookmarks.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksEntrySoap;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="BookmarksEntryModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>BookmarksEntry</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.bookmarks.model.BookmarksEntry
 * @see com.liferay.portlet.bookmarks.model.BookmarksEntryModel
 * @see com.liferay.portlet.bookmarks.model.impl.BookmarksEntryImpl
 *
 */
public class BookmarksEntryModelImpl extends BaseModelImpl<BookmarksEntry> {
	public static final String TABLE_NAME = "BookmarksEntry";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", new Integer(Types.VARCHAR) },
			

			{ "entryId", new Integer(Types.BIGINT) },
			

			{ "groupId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "userId", new Integer(Types.BIGINT) },
			

			{ "createDate", new Integer(Types.TIMESTAMP) },
			

			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			

			{ "folderId", new Integer(Types.BIGINT) },
			

			{ "name", new Integer(Types.VARCHAR) },
			

			{ "url", new Integer(Types.VARCHAR) },
			

			{ "comments", new Integer(Types.VARCHAR) },
			

			{ "visits", new Integer(Types.INTEGER) },
			

			{ "priority", new Integer(Types.INTEGER) }
		};
	public static final String TABLE_SQL_CREATE = "create table BookmarksEntry (uuid_ VARCHAR(75) null,entryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,createDate DATE null,modifiedDate DATE null,folderId LONG,name VARCHAR(255) null,url STRING null,comments STRING null,visits INTEGER,priority INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table BookmarksEntry";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.bookmarks.model.BookmarksEntry"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.bookmarks.model.BookmarksEntry"),
			true);

	public static BookmarksEntry toModel(BookmarksEntrySoap soapModel) {
		BookmarksEntry model = new BookmarksEntryImpl();

		model.setUuid(soapModel.getUuid());
		model.setEntryId(soapModel.getEntryId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setFolderId(soapModel.getFolderId());
		model.setName(soapModel.getName());
		model.setUrl(soapModel.getUrl());
		model.setComments(soapModel.getComments());
		model.setVisits(soapModel.getVisits());
		model.setPriority(soapModel.getPriority());

		return model;
	}

	public static List<BookmarksEntry> toModels(BookmarksEntrySoap[] soapModels) {
		List<BookmarksEntry> models = new ArrayList<BookmarksEntry>(soapModels.length);

		for (BookmarksEntrySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.bookmarks.model.BookmarksEntry"));

	public BookmarksEntryModelImpl() {
	}

	public long getPrimaryKey() {
		return _entryId;
	}

	public void setPrimaryKey(long pk) {
		setEntryId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_entryId);
	}

	public String getUuid() {
		return GetterUtil.getString(_uuid);
	}

	public void setUuid(String uuid) {
		_uuid = uuid;

		if (_originalUuid == null) {
			_originalUuid = uuid;
		}
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	public long getEntryId() {
		return _entryId;
	}

	public void setEntryId(long entryId) {
		_entryId = entryId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = groupId;
		}
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
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

	public long getFolderId() {
		return _folderId;
	}

	public void setFolderId(long folderId) {
		_folderId = folderId;
	}

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		_name = name;
	}

	public String getUrl() {
		return GetterUtil.getString(_url);
	}

	public void setUrl(String url) {
		_url = url;
	}

	public String getComments() {
		return GetterUtil.getString(_comments);
	}

	public void setComments(String comments) {
		_comments = comments;
	}

	public int getVisits() {
		return _visits;
	}

	public void setVisits(int visits) {
		_visits = visits;
	}

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;
	}

	public BookmarksEntry toEscapedModel() {
		if (isEscapedModel()) {
			return (BookmarksEntry)this;
		}
		else {
			BookmarksEntry model = new BookmarksEntryImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setUuid(HtmlUtil.escape(getUuid()));
			model.setEntryId(getEntryId());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setFolderId(getFolderId());
			model.setName(HtmlUtil.escape(getName()));
			model.setUrl(HtmlUtil.escape(getUrl()));
			model.setComments(HtmlUtil.escape(getComments()));
			model.setVisits(getVisits());
			model.setPriority(getPriority());

			model = (BookmarksEntry)Proxy.newProxyInstance(BookmarksEntry.class.getClassLoader(),
					new Class[] { BookmarksEntry.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(BookmarksEntry.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		BookmarksEntryImpl clone = new BookmarksEntryImpl();

		clone.setUuid(getUuid());
		clone.setEntryId(getEntryId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setFolderId(getFolderId());
		clone.setName(getName());
		clone.setUrl(getUrl());
		clone.setComments(getComments());
		clone.setVisits(getVisits());
		clone.setPriority(getPriority());

		return clone;
	}

	public int compareTo(BookmarksEntry bookmarksEntry) {
		int value = 0;

		if (getFolderId() < bookmarksEntry.getFolderId()) {
			value = -1;
		}
		else if (getFolderId() > bookmarksEntry.getFolderId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		value = getName().toLowerCase()
					.compareTo(bookmarksEntry.getName().toLowerCase());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		BookmarksEntry bookmarksEntry = null;

		try {
			bookmarksEntry = (BookmarksEntry)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = bookmarksEntry.getPrimaryKey();

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
	private String _originalUuid;
	private long _entryId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _folderId;
	private String _name;
	private String _url;
	private String _comments;
	private int _visits;
	private int _priority;
	private transient ExpandoBridge _expandoBridge;
}