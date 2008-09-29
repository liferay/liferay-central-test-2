/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.ConfiguredProducer;
import com.liferay.portal.model.ConfiguredProducerSoap;
import com.liferay.portal.service.persistence.ConfiguredProducerPK;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ConfiguredProducerModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>ConfiguredProducer</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.model.ConfiguredProducer
 * @see com.liferay.portal.model.ConfiguredProducerModel
 * @see com.liferay.portal.model.impl.ConfiguredProducerImpl
 *
 */
public class ConfiguredProducerModelImpl extends BaseModelImpl {
	public static final String TABLE_NAME = "ConfiguredProducer";
	public static final Object[][] TABLE_COLUMNS = {
			{ "portalId", new Integer(Types.VARCHAR) },
			

			{ "configuredProducerId", new Integer(Types.VARCHAR) },
			

			{ "configuredProducerName", new Integer(Types.VARCHAR) },
			

			{ "namespace", new Integer(Types.VARCHAR) },
			

			{ "producerURL", new Integer(Types.VARCHAR) },
			

			{ "producerVersion", new Integer(Types.VARCHAR) },
			

			{ "producerMarkupEndpoint", new Integer(Types.VARCHAR) },
			

			{ "producerStatus", new Integer(Types.INTEGER) },
			

			{ "registrationData", new Integer(Types.CLOB) },
			

			{ "registrationContext", new Integer(Types.CLOB) },
			

			{ "serviceDescription", new Integer(Types.CLOB) },
			

			{ "userCategoryMapping", new Integer(Types.CLOB) },
			

			{ "customUserProfile", new Integer(Types.CLOB) },
			

			{ "sdLastModified", new Integer(Types.BIGINT) },
			

			{ "identityPropagationType", new Integer(Types.VARCHAR) },
			

			{ "entityVersion", new Integer(Types.INTEGER) }
		};
	public static final String TABLE_SQL_CREATE = "create table ConfiguredProducer (portalId VARCHAR(75) not null,configuredProducerId VARCHAR(75) not null,configuredProducerName VARCHAR(75) null,namespace VARCHAR(75) null,producerURL VARCHAR(256) null,producerVersion VARCHAR(75) null,producerMarkupEndpoint VARCHAR(256) null,producerStatus INTEGER,registrationData TEXT null,registrationContext TEXT null,serviceDescription TEXT null,userCategoryMapping TEXT null,customUserProfile TEXT null,sdLastModified LONG,identityPropagationType VARCHAR(75) null,entityVersion INTEGER,primary key (portalId, configuredProducerId))";
	public static final String TABLE_SQL_DROP = "drop table ConfiguredProducer";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.ConfiguredProducer"),
			true);

	public static ConfiguredProducer toModel(ConfiguredProducerSoap soapModel) {
		ConfiguredProducer model = new ConfiguredProducerImpl();

		model.setPortalId(soapModel.getPortalId());
		model.setConfiguredProducerId(soapModel.getConfiguredProducerId());
		model.setConfiguredProducerName(soapModel.getConfiguredProducerName());
		model.setNamespace(soapModel.getNamespace());
		model.setProducerURL(soapModel.getProducerURL());
		model.setProducerVersion(soapModel.getProducerVersion());
		model.setProducerMarkupEndpoint(soapModel.getProducerMarkupEndpoint());
		model.setProducerStatus(soapModel.getProducerStatus());
		model.setRegistrationData(soapModel.getRegistrationData());
		model.setRegistrationContext(soapModel.getRegistrationContext());
		model.setServiceDescription(soapModel.getServiceDescription());
		model.setUserCategoryMapping(soapModel.getUserCategoryMapping());
		model.setCustomUserProfile(soapModel.getCustomUserProfile());
		model.setSdLastModified(soapModel.getSdLastModified());
		model.setIdentityPropagationType(soapModel.getIdentityPropagationType());
		model.setEntityVersion(soapModel.getEntityVersion());

		return model;
	}

	public static List<ConfiguredProducer> toModels(
		ConfiguredProducerSoap[] soapModels) {
		List<ConfiguredProducer> models = new ArrayList<ConfiguredProducer>(soapModels.length);

		for (ConfiguredProducerSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.ConfiguredProducer"));

	public ConfiguredProducerModelImpl() {
	}

	public ConfiguredProducerPK getPrimaryKey() {
		return new ConfiguredProducerPK(_portalId, _configuredProducerId);
	}

	public void setPrimaryKey(ConfiguredProducerPK pk) {
		setPortalId(pk.portalId);
		setConfiguredProducerId(pk.configuredProducerId);
	}

	public Serializable getPrimaryKeyObj() {
		return new ConfiguredProducerPK(_portalId, _configuredProducerId);
	}

	public String getPortalId() {
		return GetterUtil.getString(_portalId);
	}

	public void setPortalId(String portalId) {
		if (((portalId == null) && (_portalId != null)) ||
				((portalId != null) && (_portalId == null)) ||
				((portalId != null) && (_portalId != null) &&
				!portalId.equals(_portalId))) {
			_portalId = portalId;
		}
	}

	public String getConfiguredProducerId() {
		return GetterUtil.getString(_configuredProducerId);
	}

	public void setConfiguredProducerId(String configuredProducerId) {
		if (((configuredProducerId == null) && (_configuredProducerId != null)) ||
				((configuredProducerId != null) &&
				(_configuredProducerId == null)) ||
				((configuredProducerId != null) &&
				(_configuredProducerId != null) &&
				!configuredProducerId.equals(_configuredProducerId))) {
			_configuredProducerId = configuredProducerId;
		}
	}

	public String getConfiguredProducerName() {
		return GetterUtil.getString(_configuredProducerName);
	}

	public void setConfiguredProducerName(String configuredProducerName) {
		if (((configuredProducerName == null) &&
				(_configuredProducerName != null)) ||
				((configuredProducerName != null) &&
				(_configuredProducerName == null)) ||
				((configuredProducerName != null) &&
				(_configuredProducerName != null) &&
				!configuredProducerName.equals(_configuredProducerName))) {
			_configuredProducerName = configuredProducerName;
		}
	}

	public String getNamespace() {
		return GetterUtil.getString(_namespace);
	}

	public void setNamespace(String namespace) {
		if (((namespace == null) && (_namespace != null)) ||
				((namespace != null) && (_namespace == null)) ||
				((namespace != null) && (_namespace != null) &&
				!namespace.equals(_namespace))) {
			_namespace = namespace;
		}
	}

	public String getProducerURL() {
		return GetterUtil.getString(_producerURL);
	}

	public void setProducerURL(String producerURL) {
		if (((producerURL == null) && (_producerURL != null)) ||
				((producerURL != null) && (_producerURL == null)) ||
				((producerURL != null) && (_producerURL != null) &&
				!producerURL.equals(_producerURL))) {
			_producerURL = producerURL;
		}
	}

	public String getProducerVersion() {
		return GetterUtil.getString(_producerVersion);
	}

	public void setProducerVersion(String producerVersion) {
		if (((producerVersion == null) && (_producerVersion != null)) ||
				((producerVersion != null) && (_producerVersion == null)) ||
				((producerVersion != null) && (_producerVersion != null) &&
				!producerVersion.equals(_producerVersion))) {
			_producerVersion = producerVersion;
		}
	}

	public String getProducerMarkupEndpoint() {
		return GetterUtil.getString(_producerMarkupEndpoint);
	}

	public void setProducerMarkupEndpoint(String producerMarkupEndpoint) {
		if (((producerMarkupEndpoint == null) &&
				(_producerMarkupEndpoint != null)) ||
				((producerMarkupEndpoint != null) &&
				(_producerMarkupEndpoint == null)) ||
				((producerMarkupEndpoint != null) &&
				(_producerMarkupEndpoint != null) &&
				!producerMarkupEndpoint.equals(_producerMarkupEndpoint))) {
			_producerMarkupEndpoint = producerMarkupEndpoint;
		}
	}

	public int getProducerStatus() {
		return _producerStatus;
	}

	public void setProducerStatus(int producerStatus) {
		if (producerStatus != _producerStatus) {
			_producerStatus = producerStatus;
		}
	}

	public String getRegistrationData() {
		return GetterUtil.getString(_registrationData);
	}

	public void setRegistrationData(String registrationData) {
		if (((registrationData == null) && (_registrationData != null)) ||
				((registrationData != null) && (_registrationData == null)) ||
				((registrationData != null) && (_registrationData != null) &&
				!registrationData.equals(_registrationData))) {
			_registrationData = registrationData;
		}
	}

	public String getRegistrationContext() {
		return GetterUtil.getString(_registrationContext);
	}

	public void setRegistrationContext(String registrationContext) {
		if (((registrationContext == null) && (_registrationContext != null)) ||
				((registrationContext != null) &&
				(_registrationContext == null)) ||
				((registrationContext != null) &&
				(_registrationContext != null) &&
				!registrationContext.equals(_registrationContext))) {
			_registrationContext = registrationContext;
		}
	}

	public String getServiceDescription() {
		return GetterUtil.getString(_serviceDescription);
	}

	public void setServiceDescription(String serviceDescription) {
		if (((serviceDescription == null) && (_serviceDescription != null)) ||
				((serviceDescription != null) && (_serviceDescription == null)) ||
				((serviceDescription != null) && (_serviceDescription != null) &&
				!serviceDescription.equals(_serviceDescription))) {
			_serviceDescription = serviceDescription;
		}
	}

	public String getUserCategoryMapping() {
		return GetterUtil.getString(_userCategoryMapping);
	}

	public void setUserCategoryMapping(String userCategoryMapping) {
		if (((userCategoryMapping == null) && (_userCategoryMapping != null)) ||
				((userCategoryMapping != null) &&
				(_userCategoryMapping == null)) ||
				((userCategoryMapping != null) &&
				(_userCategoryMapping != null) &&
				!userCategoryMapping.equals(_userCategoryMapping))) {
			_userCategoryMapping = userCategoryMapping;
		}
	}

	public String getCustomUserProfile() {
		return GetterUtil.getString(_customUserProfile);
	}

	public void setCustomUserProfile(String customUserProfile) {
		if (((customUserProfile == null) && (_customUserProfile != null)) ||
				((customUserProfile != null) && (_customUserProfile == null)) ||
				((customUserProfile != null) && (_customUserProfile != null) &&
				!customUserProfile.equals(_customUserProfile))) {
			_customUserProfile = customUserProfile;
		}
	}

	public long getSdLastModified() {
		return _sdLastModified;
	}

	public void setSdLastModified(long sdLastModified) {
		if (sdLastModified != _sdLastModified) {
			_sdLastModified = sdLastModified;
		}
	}

	public String getIdentityPropagationType() {
		return GetterUtil.getString(_identityPropagationType);
	}

	public void setIdentityPropagationType(String identityPropagationType) {
		if (((identityPropagationType == null) &&
				(_identityPropagationType != null)) ||
				((identityPropagationType != null) &&
				(_identityPropagationType == null)) ||
				((identityPropagationType != null) &&
				(_identityPropagationType != null) &&
				!identityPropagationType.equals(_identityPropagationType))) {
			_identityPropagationType = identityPropagationType;
		}
	}

	public int getEntityVersion() {
		return _entityVersion;
	}

	public void setEntityVersion(int entityVersion) {
		if (entityVersion != _entityVersion) {
			_entityVersion = entityVersion;
		}
	}

	public ConfiguredProducer toEscapedModel() {
		if (isEscapedModel()) {
			return (ConfiguredProducer)this;
		}
		else {
			ConfiguredProducer model = new ConfiguredProducerImpl();

			model.setEscapedModel(true);

			model.setPortalId(HtmlUtil.escape(getPortalId()));
			model.setConfiguredProducerId(HtmlUtil.escape(
					getConfiguredProducerId()));
			model.setConfiguredProducerName(HtmlUtil.escape(
					getConfiguredProducerName()));
			model.setNamespace(HtmlUtil.escape(getNamespace()));
			model.setProducerURL(HtmlUtil.escape(getProducerURL()));
			model.setProducerVersion(HtmlUtil.escape(getProducerVersion()));
			model.setProducerMarkupEndpoint(HtmlUtil.escape(
					getProducerMarkupEndpoint()));
			model.setProducerStatus(getProducerStatus());
			model.setRegistrationData(HtmlUtil.escape(getRegistrationData()));
			model.setRegistrationContext(HtmlUtil.escape(
					getRegistrationContext()));
			model.setServiceDescription(HtmlUtil.escape(getServiceDescription()));
			model.setUserCategoryMapping(HtmlUtil.escape(
					getUserCategoryMapping()));
			model.setCustomUserProfile(HtmlUtil.escape(getCustomUserProfile()));
			model.setSdLastModified(getSdLastModified());
			model.setIdentityPropagationType(HtmlUtil.escape(
					getIdentityPropagationType()));
			model.setEntityVersion(getEntityVersion());

			model = (ConfiguredProducer)Proxy.newProxyInstance(ConfiguredProducer.class.getClassLoader(),
					new Class[] { ConfiguredProducer.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public Object clone() {
		ConfiguredProducerImpl clone = new ConfiguredProducerImpl();

		clone.setPortalId(getPortalId());
		clone.setConfiguredProducerId(getConfiguredProducerId());
		clone.setConfiguredProducerName(getConfiguredProducerName());
		clone.setNamespace(getNamespace());
		clone.setProducerURL(getProducerURL());
		clone.setProducerVersion(getProducerVersion());
		clone.setProducerMarkupEndpoint(getProducerMarkupEndpoint());
		clone.setProducerStatus(getProducerStatus());
		clone.setRegistrationData(getRegistrationData());
		clone.setRegistrationContext(getRegistrationContext());
		clone.setServiceDescription(getServiceDescription());
		clone.setUserCategoryMapping(getUserCategoryMapping());
		clone.setCustomUserProfile(getCustomUserProfile());
		clone.setSdLastModified(getSdLastModified());
		clone.setIdentityPropagationType(getIdentityPropagationType());
		clone.setEntityVersion(getEntityVersion());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		ConfiguredProducerImpl configuredProducer = (ConfiguredProducerImpl)obj;

		ConfiguredProducerPK pk = configuredProducer.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ConfiguredProducerImpl configuredProducer = null;

		try {
			configuredProducer = (ConfiguredProducerImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		ConfiguredProducerPK pk = configuredProducer.getPrimaryKey();

		if (getPrimaryKey().equals(pk)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	private String _portalId;
	private String _configuredProducerId;
	private String _configuredProducerName;
	private String _namespace;
	private String _producerURL;
	private String _producerVersion;
	private String _producerMarkupEndpoint;
	private int _producerStatus;
	private String _registrationData;
	private String _registrationContext;
	private String _serviceDescription;
	private String _userCategoryMapping;
	private String _customUserProfile;
	private long _sdLastModified;
	private String _identityPropagationType;
	private int _entityVersion;
}