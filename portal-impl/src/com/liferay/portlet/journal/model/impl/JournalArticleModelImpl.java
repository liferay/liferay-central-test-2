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

package com.liferay.portlet.journal.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="JournalArticleModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>JournalArticle</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.journal.model.JournalArticle
 * @see com.liferay.portlet.journal.model.JournalArticleModel
 * @see com.liferay.portlet.journal.model.impl.JournalArticleImpl
 *
 */
public class JournalArticleModelImpl extends BaseModelImpl<JournalArticle> {
	public static final String TABLE_NAME = "JournalArticle";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", new Integer(Types.VARCHAR) },
			

			{ "id_", new Integer(Types.BIGINT) },
			

			{ "resourcePrimKey", new Integer(Types.BIGINT) },
			

			{ "groupId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "userId", new Integer(Types.BIGINT) },
			

			{ "userName", new Integer(Types.VARCHAR) },
			

			{ "createDate", new Integer(Types.TIMESTAMP) },
			

			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			

			{ "articleId", new Integer(Types.VARCHAR) },
			

			{ "version", new Integer(Types.DOUBLE) },
			

			{ "title", new Integer(Types.VARCHAR) },
			

			{ "urlTitle", new Integer(Types.VARCHAR) },
			

			{ "description", new Integer(Types.VARCHAR) },
			

			{ "content", new Integer(Types.CLOB) },
			

			{ "type_", new Integer(Types.VARCHAR) },
			

			{ "structureId", new Integer(Types.VARCHAR) },
			

			{ "templateId", new Integer(Types.VARCHAR) },
			

			{ "displayDate", new Integer(Types.TIMESTAMP) },
			

			{ "approved", new Integer(Types.BOOLEAN) },
			

			{ "approvedByUserId", new Integer(Types.BIGINT) },
			

			{ "approvedByUserName", new Integer(Types.VARCHAR) },
			

			{ "approvedDate", new Integer(Types.TIMESTAMP) },
			

			{ "expired", new Integer(Types.BOOLEAN) },
			

			{ "expirationDate", new Integer(Types.TIMESTAMP) },
			

			{ "reviewDate", new Integer(Types.TIMESTAMP) },
			

			{ "indexable", new Integer(Types.BOOLEAN) },
			

			{ "smallImage", new Integer(Types.BOOLEAN) },
			

			{ "smallImageId", new Integer(Types.BIGINT) },
			

			{ "smallImageURL", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table JournalArticle (uuid_ VARCHAR(75) null,id_ LONG not null primary key,resourcePrimKey LONG,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,articleId VARCHAR(75) null,version DOUBLE,title VARCHAR(100) null,urlTitle VARCHAR(75) null,description STRING null,content TEXT null,type_ VARCHAR(75) null,structureId VARCHAR(75) null,templateId VARCHAR(75) null,displayDate DATE null,approved BOOLEAN,approvedByUserId LONG,approvedByUserName VARCHAR(75) null,approvedDate DATE null,expired BOOLEAN,expirationDate DATE null,reviewDate DATE null,indexable BOOLEAN,smallImage BOOLEAN,smallImageId LONG,smallImageURL VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table JournalArticle";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.journal.model.JournalArticle"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.journal.model.JournalArticle"),
			true);

	public static JournalArticle toModel(JournalArticleSoap soapModel) {
		JournalArticle model = new JournalArticleImpl();

		model.setUuid(soapModel.getUuid());
		model.setId(soapModel.getId());
		model.setResourcePrimKey(soapModel.getResourcePrimKey());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setArticleId(soapModel.getArticleId());
		model.setVersion(soapModel.getVersion());
		model.setTitle(soapModel.getTitle());
		model.setUrlTitle(soapModel.getUrlTitle());
		model.setDescription(soapModel.getDescription());
		model.setContent(soapModel.getContent());
		model.setType(soapModel.getType());
		model.setStructureId(soapModel.getStructureId());
		model.setTemplateId(soapModel.getTemplateId());
		model.setDisplayDate(soapModel.getDisplayDate());
		model.setApproved(soapModel.getApproved());
		model.setApprovedByUserId(soapModel.getApprovedByUserId());
		model.setApprovedByUserName(soapModel.getApprovedByUserName());
		model.setApprovedDate(soapModel.getApprovedDate());
		model.setExpired(soapModel.getExpired());
		model.setExpirationDate(soapModel.getExpirationDate());
		model.setReviewDate(soapModel.getReviewDate());
		model.setIndexable(soapModel.getIndexable());
		model.setSmallImage(soapModel.getSmallImage());
		model.setSmallImageId(soapModel.getSmallImageId());
		model.setSmallImageURL(soapModel.getSmallImageURL());

		return model;
	}

	public static List<JournalArticle> toModels(JournalArticleSoap[] soapModels) {
		List<JournalArticle> models = new ArrayList<JournalArticle>(soapModels.length);

		for (JournalArticleSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.journal.model.JournalArticle"));

	public JournalArticleModelImpl() {
	}

	public long getPrimaryKey() {
		return _id;
	}

	public void setPrimaryKey(long pk) {
		setId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_id);
	}

	public String getUuid() {
		return GetterUtil.getString(_uuid);
	}

	public void setUuid(String uuid) {
		if ((uuid != null) && !uuid.equals(_uuid)) {
			_uuid = uuid;

			if (_originalUuid == null) {
				_originalUuid = uuid;
			}
		}
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	public long getId() {
		return _id;
	}

	public void setId(long id) {
		if (id != _id) {
			_id = id;
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

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		if (groupId != _groupId) {
			_groupId = groupId;

			if (!_setOriginalGroupId) {
				_setOriginalGroupId = true;

				_originalGroupId = groupId;
			}
		}
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
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

	public String getArticleId() {
		return GetterUtil.getString(_articleId);
	}

	public void setArticleId(String articleId) {
		if (((articleId == null) && (_articleId != null)) ||
				((articleId != null) && (_articleId == null)) ||
				((articleId != null) && (_articleId != null) &&
				!articleId.equals(_articleId))) {
			_articleId = articleId;

			if (_originalArticleId == null) {
				_originalArticleId = articleId;
			}
		}
	}

	public String getOriginalArticleId() {
		return GetterUtil.getString(_originalArticleId);
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

	public String getTitle() {
		return GetterUtil.getString(_title);
	}

	public void setTitle(String title) {
		if (((title == null) && (_title != null)) ||
				((title != null) && (_title == null)) ||
				((title != null) && (_title != null) && !title.equals(_title))) {
			_title = title;
		}
	}

	public String getUrlTitle() {
		return GetterUtil.getString(_urlTitle);
	}

	public void setUrlTitle(String urlTitle) {
		if (((urlTitle == null) && (_urlTitle != null)) ||
				((urlTitle != null) && (_urlTitle == null)) ||
				((urlTitle != null) && (_urlTitle != null) &&
				!urlTitle.equals(_urlTitle))) {
			_urlTitle = urlTitle;

			if (_originalUrlTitle == null) {
				_originalUrlTitle = urlTitle;
			}
		}
	}

	public String getOriginalUrlTitle() {
		return GetterUtil.getString(_originalUrlTitle);
	}

	public String getDescription() {
		return GetterUtil.getString(_description);
	}

	public void setDescription(String description) {
		if (((description == null) && (_description != null)) ||
				((description != null) && (_description == null)) ||
				((description != null) && (_description != null) &&
				!description.equals(_description))) {
			_description = description;
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

	public String getType() {
		return GetterUtil.getString(_type);
	}

	public void setType(String type) {
		if (((type == null) && (_type != null)) ||
				((type != null) && (_type == null)) ||
				((type != null) && (_type != null) && !type.equals(_type))) {
			_type = type;
		}
	}

	public String getStructureId() {
		return GetterUtil.getString(_structureId);
	}

	public void setStructureId(String structureId) {
		if (((structureId == null) && (_structureId != null)) ||
				((structureId != null) && (_structureId == null)) ||
				((structureId != null) && (_structureId != null) &&
				!structureId.equals(_structureId))) {
			_structureId = structureId;
		}
	}

	public String getTemplateId() {
		return GetterUtil.getString(_templateId);
	}

	public void setTemplateId(String templateId) {
		if (((templateId == null) && (_templateId != null)) ||
				((templateId != null) && (_templateId == null)) ||
				((templateId != null) && (_templateId != null) &&
				!templateId.equals(_templateId))) {
			_templateId = templateId;
		}
	}

	public Date getDisplayDate() {
		return _displayDate;
	}

	public void setDisplayDate(Date displayDate) {
		if (((displayDate == null) && (_displayDate != null)) ||
				((displayDate != null) && (_displayDate == null)) ||
				((displayDate != null) && (_displayDate != null) &&
				!displayDate.equals(_displayDate))) {
			_displayDate = displayDate;
		}
	}

	public boolean getApproved() {
		return _approved;
	}

	public boolean isApproved() {
		return _approved;
	}

	public void setApproved(boolean approved) {
		if (approved != _approved) {
			_approved = approved;
		}
	}

	public long getApprovedByUserId() {
		return _approvedByUserId;
	}

	public void setApprovedByUserId(long approvedByUserId) {
		if (approvedByUserId != _approvedByUserId) {
			_approvedByUserId = approvedByUserId;
		}
	}

	public String getApprovedByUserName() {
		return GetterUtil.getString(_approvedByUserName);
	}

	public void setApprovedByUserName(String approvedByUserName) {
		if (((approvedByUserName == null) && (_approvedByUserName != null)) ||
				((approvedByUserName != null) && (_approvedByUserName == null)) ||
				((approvedByUserName != null) && (_approvedByUserName != null) &&
				!approvedByUserName.equals(_approvedByUserName))) {
			_approvedByUserName = approvedByUserName;
		}
	}

	public Date getApprovedDate() {
		return _approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		if (((approvedDate == null) && (_approvedDate != null)) ||
				((approvedDate != null) && (_approvedDate == null)) ||
				((approvedDate != null) && (_approvedDate != null) &&
				!approvedDate.equals(_approvedDate))) {
			_approvedDate = approvedDate;
		}
	}

	public boolean getExpired() {
		return _expired;
	}

	public boolean isExpired() {
		return _expired;
	}

	public void setExpired(boolean expired) {
		if (expired != _expired) {
			_expired = expired;
		}
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		if (((expirationDate == null) && (_expirationDate != null)) ||
				((expirationDate != null) && (_expirationDate == null)) ||
				((expirationDate != null) && (_expirationDate != null) &&
				!expirationDate.equals(_expirationDate))) {
			_expirationDate = expirationDate;
		}
	}

	public Date getReviewDate() {
		return _reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		if (((reviewDate == null) && (_reviewDate != null)) ||
				((reviewDate != null) && (_reviewDate == null)) ||
				((reviewDate != null) && (_reviewDate != null) &&
				!reviewDate.equals(_reviewDate))) {
			_reviewDate = reviewDate;
		}
	}

	public boolean getIndexable() {
		return _indexable;
	}

	public boolean isIndexable() {
		return _indexable;
	}

	public void setIndexable(boolean indexable) {
		if (indexable != _indexable) {
			_indexable = indexable;
		}
	}

	public boolean getSmallImage() {
		return _smallImage;
	}

	public boolean isSmallImage() {
		return _smallImage;
	}

	public void setSmallImage(boolean smallImage) {
		if (smallImage != _smallImage) {
			_smallImage = smallImage;
		}
	}

	public long getSmallImageId() {
		return _smallImageId;
	}

	public void setSmallImageId(long smallImageId) {
		if (smallImageId != _smallImageId) {
			_smallImageId = smallImageId;
		}
	}

	public String getSmallImageURL() {
		return GetterUtil.getString(_smallImageURL);
	}

	public void setSmallImageURL(String smallImageURL) {
		if (((smallImageURL == null) && (_smallImageURL != null)) ||
				((smallImageURL != null) && (_smallImageURL == null)) ||
				((smallImageURL != null) && (_smallImageURL != null) &&
				!smallImageURL.equals(_smallImageURL))) {
			_smallImageURL = smallImageURL;
		}
	}

	public JournalArticle toEscapedModel() {
		if (isEscapedModel()) {
			return (JournalArticle)this;
		}
		else {
			JournalArticle model = new JournalArticleImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setUuid(HtmlUtil.escape(getUuid()));
			model.setId(getId());
			model.setResourcePrimKey(getResourcePrimKey());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setUserName(HtmlUtil.escape(getUserName()));
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setArticleId(getArticleId());
			model.setVersion(getVersion());
			model.setTitle(HtmlUtil.escape(getTitle()));
			model.setUrlTitle(HtmlUtil.escape(getUrlTitle()));
			model.setDescription(HtmlUtil.escape(getDescription()));
			model.setContent(HtmlUtil.escape(getContent()));
			model.setType(HtmlUtil.escape(getType()));
			model.setStructureId(getStructureId());
			model.setTemplateId(getTemplateId());
			model.setDisplayDate(getDisplayDate());
			model.setApproved(getApproved());
			model.setApprovedByUserId(getApprovedByUserId());
			model.setApprovedByUserName(HtmlUtil.escape(getApprovedByUserName()));
			model.setApprovedDate(getApprovedDate());
			model.setExpired(getExpired());
			model.setExpirationDate(getExpirationDate());
			model.setReviewDate(getReviewDate());
			model.setIndexable(getIndexable());
			model.setSmallImage(getSmallImage());
			model.setSmallImageId(getSmallImageId());
			model.setSmallImageURL(HtmlUtil.escape(getSmallImageURL()));

			model = (JournalArticle)Proxy.newProxyInstance(JournalArticle.class.getClassLoader(),
					new Class[] { JournalArticle.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(JournalArticle.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		JournalArticleImpl clone = new JournalArticleImpl();

		clone.setUuid(getUuid());
		clone.setId(getId());
		clone.setResourcePrimKey(getResourcePrimKey());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setArticleId(getArticleId());
		clone.setVersion(getVersion());
		clone.setTitle(getTitle());
		clone.setUrlTitle(getUrlTitle());
		clone.setDescription(getDescription());
		clone.setContent(getContent());
		clone.setType(getType());
		clone.setStructureId(getStructureId());
		clone.setTemplateId(getTemplateId());
		clone.setDisplayDate(getDisplayDate());
		clone.setApproved(getApproved());
		clone.setApprovedByUserId(getApprovedByUserId());
		clone.setApprovedByUserName(getApprovedByUserName());
		clone.setApprovedDate(getApprovedDate());
		clone.setExpired(getExpired());
		clone.setExpirationDate(getExpirationDate());
		clone.setReviewDate(getReviewDate());
		clone.setIndexable(getIndexable());
		clone.setSmallImage(getSmallImage());
		clone.setSmallImageId(getSmallImageId());
		clone.setSmallImageURL(getSmallImageURL());

		return clone;
	}

	public int compareTo(JournalArticle journalArticle) {
		int value = 0;

		value = getArticleId().compareTo(journalArticle.getArticleId());

		if (value != 0) {
			return value;
		}

		if (getVersion() < journalArticle.getVersion()) {
			value = -1;
		}
		else if (getVersion() > journalArticle.getVersion()) {
			value = 1;
		}
		else {
			value = 0;
		}

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

		JournalArticle journalArticle = null;

		try {
			journalArticle = (JournalArticle)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = journalArticle.getPrimaryKey();

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
	private long _id;
	private long _resourcePrimKey;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _articleId;
	private String _originalArticleId;
	private double _version;
	private double _originalVersion;
	private boolean _setOriginalVersion;
	private String _title;
	private String _urlTitle;
	private String _originalUrlTitle;
	private String _description;
	private String _content;
	private String _type;
	private String _structureId;
	private String _templateId;
	private Date _displayDate;
	private boolean _approved;
	private long _approvedByUserId;
	private String _approvedByUserName;
	private Date _approvedDate;
	private boolean _expired;
	private Date _expirationDate;
	private Date _reviewDate;
	private boolean _indexable;
	private boolean _smallImage;
	private long _smallImageId;
	private String _smallImageURL;
	private transient ExpandoBridge _expandoBridge;
}