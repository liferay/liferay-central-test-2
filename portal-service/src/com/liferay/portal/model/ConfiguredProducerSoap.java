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

package com.liferay.portal.model;

import com.liferay.portal.service.persistence.ConfiguredProducerPK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ConfiguredProducerSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * <code>com.liferay.portal.service.http.ConfiguredProducerServiceSoap</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.http.ConfiguredProducerServiceSoap
 *
 */
public class ConfiguredProducerSoap implements Serializable {
	public static ConfiguredProducerSoap toSoapModel(ConfiguredProducer model) {
		ConfiguredProducerSoap soapModel = new ConfiguredProducerSoap();

		soapModel.setPortalId(model.getPortalId());
		soapModel.setConfiguredProducerId(model.getConfiguredProducerId());
		soapModel.setConfiguredProducerName(model.getConfiguredProducerName());
		soapModel.setNamespace(model.getNamespace());
		soapModel.setProducerURL(model.getProducerURL());
		soapModel.setProducerVersion(model.getProducerVersion());
		soapModel.setProducerMarkupEndpoint(model.getProducerMarkupEndpoint());
		soapModel.setProducerStatus(model.getProducerStatus());
		soapModel.setRegistrationData(model.getRegistrationData());
		soapModel.setRegistrationContext(model.getRegistrationContext());
		soapModel.setServiceDescription(model.getServiceDescription());
		soapModel.setUserCategoryMapping(model.getUserCategoryMapping());
		soapModel.setCustomUserProfile(model.getCustomUserProfile());
		soapModel.setSdLastModified(model.getSdLastModified());
		soapModel.setIdentityPropagationType(model.getIdentityPropagationType());
		soapModel.setEntityVersion(model.getEntityVersion());

		return soapModel;
	}

	public static ConfiguredProducerSoap[] toSoapModels(
		List<ConfiguredProducer> models) {
		List<ConfiguredProducerSoap> soapModels = new ArrayList<ConfiguredProducerSoap>(models.size());

		for (ConfiguredProducer model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ConfiguredProducerSoap[soapModels.size()]);
	}

	public ConfiguredProducerSoap() {
	}

	public ConfiguredProducerPK getPrimaryKey() {
		return new ConfiguredProducerPK(_portalId, _configuredProducerId);
	}

	public void setPrimaryKey(ConfiguredProducerPK pk) {
		setPortalId(pk.portalId);
		setConfiguredProducerId(pk.configuredProducerId);
	}

	public String getPortalId() {
		return _portalId;
	}

	public void setPortalId(String portalId) {
		_portalId = portalId;
	}

	public String getConfiguredProducerId() {
		return _configuredProducerId;
	}

	public void setConfiguredProducerId(String configuredProducerId) {
		_configuredProducerId = configuredProducerId;
	}

	public String getConfiguredProducerName() {
		return _configuredProducerName;
	}

	public void setConfiguredProducerName(String configuredProducerName) {
		_configuredProducerName = configuredProducerName;
	}

	public String getNamespace() {
		return _namespace;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	public String getProducerURL() {
		return _producerURL;
	}

	public void setProducerURL(String producerURL) {
		_producerURL = producerURL;
	}

	public String getProducerVersion() {
		return _producerVersion;
	}

	public void setProducerVersion(String producerVersion) {
		_producerVersion = producerVersion;
	}

	public String getProducerMarkupEndpoint() {
		return _producerMarkupEndpoint;
	}

	public void setProducerMarkupEndpoint(String producerMarkupEndpoint) {
		_producerMarkupEndpoint = producerMarkupEndpoint;
	}

	public int getProducerStatus() {
		return _producerStatus;
	}

	public void setProducerStatus(int producerStatus) {
		_producerStatus = producerStatus;
	}

	public String getRegistrationData() {
		return _registrationData;
	}

	public void setRegistrationData(String registrationData) {
		_registrationData = registrationData;
	}

	public String getRegistrationContext() {
		return _registrationContext;
	}

	public void setRegistrationContext(String registrationContext) {
		_registrationContext = registrationContext;
	}

	public String getServiceDescription() {
		return _serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		_serviceDescription = serviceDescription;
	}

	public String getUserCategoryMapping() {
		return _userCategoryMapping;
	}

	public void setUserCategoryMapping(String userCategoryMapping) {
		_userCategoryMapping = userCategoryMapping;
	}

	public String getCustomUserProfile() {
		return _customUserProfile;
	}

	public void setCustomUserProfile(String customUserProfile) {
		_customUserProfile = customUserProfile;
	}

	public long getSdLastModified() {
		return _sdLastModified;
	}

	public void setSdLastModified(long sdLastModified) {
		_sdLastModified = sdLastModified;
	}

	public String getIdentityPropagationType() {
		return _identityPropagationType;
	}

	public void setIdentityPropagationType(String identityPropagationType) {
		_identityPropagationType = identityPropagationType;
	}

	public int getEntityVersion() {
		return _entityVersion;
	}

	public void setEntityVersion(int entityVersion) {
		_entityVersion = entityVersion;
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