/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.imagegallery.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.model.IGImageSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="IGImageModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the IGImage table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       IGImageImpl
 * @see       com.liferay.portlet.imagegallery.model.IGImage
 * @see       com.liferay.portlet.imagegallery.model.IGImageModel
 * @generated
 */
public class IGImageModelImpl extends BaseModelImpl<IGImage> {
	public static final String TABLE_NAME = "IGImage";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", new Integer(Types.VARCHAR) },
			{ "imageId", new Integer(Types.BIGINT) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "createDate", new Integer(Types.TIMESTAMP) },
			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			{ "folderId", new Integer(Types.BIGINT) },
			{ "name", new Integer(Types.VARCHAR) },
			{ "description", new Integer(Types.VARCHAR) },
			{ "smallImageId", new Integer(Types.BIGINT) },
			{ "largeImageId", new Integer(Types.BIGINT) },
			{ "custom1ImageId", new Integer(Types.BIGINT) },
			{ "custom2ImageId", new Integer(Types.BIGINT) }
		};
	public static final String TABLE_SQL_CREATE = "create table IGImage (uuid_ VARCHAR(75) null,imageId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,createDate DATE null,modifiedDate DATE null,folderId LONG,name VARCHAR(75) null,description STRING null,smallImageId LONG,largeImageId LONG,custom1ImageId LONG,custom2ImageId LONG)";
	public static final String TABLE_SQL_DROP = "drop table IGImage";
	public static final String ORDER_BY_JPQL = " ORDER BY igImage.imageId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY IGImage.imageId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.imagegallery.model.IGImage"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.imagegallery.model.IGImage"),
			true);

	public static IGImage toModel(IGImageSoap soapModel) {
		IGImage model = new IGImageImpl();

		model.setUuid(soapModel.getUuid());
		model.setImageId(soapModel.getImageId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setFolderId(soapModel.getFolderId());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());
		model.setSmallImageId(soapModel.getSmallImageId());
		model.setLargeImageId(soapModel.getLargeImageId());
		model.setCustom1ImageId(soapModel.getCustom1ImageId());
		model.setCustom2ImageId(soapModel.getCustom2ImageId());

		return model;
	}

	public static List<IGImage> toModels(IGImageSoap[] soapModels) {
		List<IGImage> models = new ArrayList<IGImage>(soapModels.length);

		for (IGImageSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.imagegallery.model.IGImage"));

	public IGImageModelImpl() {
	}

	public long getPrimaryKey() {
		return _imageId;
	}

	public void setPrimaryKey(long pk) {
		setImageId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_imageId);
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

	public long getImageId() {
		return _imageId;
	}

	public void setImageId(long imageId) {
		_imageId = imageId;
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

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
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

	public String getDescription() {
		return GetterUtil.getString(_description);
	}

	public void setDescription(String description) {
		_description = description;
	}

	public long getSmallImageId() {
		return _smallImageId;
	}

	public void setSmallImageId(long smallImageId) {
		_smallImageId = smallImageId;

		if (!_setOriginalSmallImageId) {
			_setOriginalSmallImageId = true;

			_originalSmallImageId = smallImageId;
		}
	}

	public long getOriginalSmallImageId() {
		return _originalSmallImageId;
	}

	public long getLargeImageId() {
		return _largeImageId;
	}

	public void setLargeImageId(long largeImageId) {
		_largeImageId = largeImageId;

		if (!_setOriginalLargeImageId) {
			_setOriginalLargeImageId = true;

			_originalLargeImageId = largeImageId;
		}
	}

	public long getOriginalLargeImageId() {
		return _originalLargeImageId;
	}

	public long getCustom1ImageId() {
		return _custom1ImageId;
	}

	public void setCustom1ImageId(long custom1ImageId) {
		_custom1ImageId = custom1ImageId;

		if (!_setOriginalCustom1ImageId) {
			_setOriginalCustom1ImageId = true;

			_originalCustom1ImageId = custom1ImageId;
		}
	}

	public long getOriginalCustom1ImageId() {
		return _originalCustom1ImageId;
	}

	public long getCustom2ImageId() {
		return _custom2ImageId;
	}

	public void setCustom2ImageId(long custom2ImageId) {
		_custom2ImageId = custom2ImageId;

		if (!_setOriginalCustom2ImageId) {
			_setOriginalCustom2ImageId = true;

			_originalCustom2ImageId = custom2ImageId;
		}
	}

	public long getOriginalCustom2ImageId() {
		return _originalCustom2ImageId;
	}

	public IGImage toEscapedModel() {
		if (isEscapedModel()) {
			return (IGImage)this;
		}
		else {
			IGImage model = new IGImageImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setUuid(HtmlUtil.escape(getUuid()));
			model.setImageId(getImageId());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setFolderId(getFolderId());
			model.setName(HtmlUtil.escape(getName()));
			model.setDescription(HtmlUtil.escape(getDescription()));
			model.setSmallImageId(getSmallImageId());
			model.setLargeImageId(getLargeImageId());
			model.setCustom1ImageId(getCustom1ImageId());
			model.setCustom2ImageId(getCustom2ImageId());

			model = (IGImage)Proxy.newProxyInstance(IGImage.class.getClassLoader(),
					new Class[] { IGImage.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(IGImage.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		IGImageImpl clone = new IGImageImpl();

		clone.setUuid(getUuid());
		clone.setImageId(getImageId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setFolderId(getFolderId());
		clone.setName(getName());
		clone.setDescription(getDescription());
		clone.setSmallImageId(getSmallImageId());
		clone.setLargeImageId(getLargeImageId());
		clone.setCustom1ImageId(getCustom1ImageId());
		clone.setCustom2ImageId(getCustom2ImageId());

		return clone;
	}

	public int compareTo(IGImage igImage) {
		int value = 0;

		if (getImageId() < igImage.getImageId()) {
			value = -1;
		}
		else if (getImageId() > igImage.getImageId()) {
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

		IGImage igImage = null;

		try {
			igImage = (IGImage)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = igImage.getPrimaryKey();

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

	public String toString() {
		StringBundler sb = new StringBundler(29);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", imageId=");
		sb.append(getImageId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", folderId=");
		sb.append(getFolderId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", smallImageId=");
		sb.append(getSmallImageId());
		sb.append(", largeImageId=");
		sb.append(getLargeImageId());
		sb.append(", custom1ImageId=");
		sb.append(getCustom1ImageId());
		sb.append(", custom2ImageId=");
		sb.append(getCustom2ImageId());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(46);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.imagegallery.model.IGImage");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>imageId</column-name><column-value><![CDATA[");
		sb.append(getImageId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
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
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>folderId</column-name><column-value><![CDATA[");
		sb.append(getFolderId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>smallImageId</column-name><column-value><![CDATA[");
		sb.append(getSmallImageId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>largeImageId</column-name><column-value><![CDATA[");
		sb.append(getLargeImageId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>custom1ImageId</column-name><column-value><![CDATA[");
		sb.append(getCustom1ImageId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>custom2ImageId</column-name><column-value><![CDATA[");
		sb.append(getCustom2ImageId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private String _uuid;
	private String _originalUuid;
	private long _imageId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private Date _createDate;
	private Date _modifiedDate;
	private long _folderId;
	private String _name;
	private String _description;
	private long _smallImageId;
	private long _originalSmallImageId;
	private boolean _setOriginalSmallImageId;
	private long _largeImageId;
	private long _originalLargeImageId;
	private boolean _setOriginalLargeImageId;
	private long _custom1ImageId;
	private long _originalCustom1ImageId;
	private boolean _setOriginalCustom1ImageId;
	private long _custom2ImageId;
	private long _originalCustom2ImageId;
	private boolean _setOriginalCustom2ImageId;
	private transient ExpandoBridge _expandoBridge;
}