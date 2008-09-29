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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.wsrp.consumer.producermanager;

import com.liferay.portal.model.ConfiguredProducer;
import com.liferay.portal.model.impl.ConfiguredProducerImpl;
import com.liferay.portal.service.ConfiguredProducerLocalServiceUtil;
import com.liferay.portal.service.persistence.ConfiguredProducerPK;
import com.liferay.portal.SystemException;

import com.sun.portal.wsrp.common.WSRPVersion;
import com.sun.portal.wsrp.common.stubs.v2.RegistrationContext;
import com.sun.portal.wsrp.common.stubs.v2.RegistrationData;
import com.sun.portal.wsrp.common.stubs.v2.ServiceDescription;
import com.sun.portal.wsrp.consumer.common.WSRPConsumerException;
import com.sun.portal.wsrp.consumer.producermanager.ProducerEntity;
import com.sun.portal.wsrp.consumer.producermanager.ProducerEntityManager;
import com.sun.portal.wsrp.consumer.producermanager.ProducerEntityStatus;
import com.sun.portal.wsrp.consumer.producermanager.impl.AbstractProducerEntityManager;
import com.sun.portal.wsrp.consumer.producermanager.impl.ProducerEntityImpl;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <a href="ProducerEntityManagerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Manish Gupta
 *
 */
public class ProducerEntityManagerImpl extends AbstractProducerEntityManager
	implements ProducerEntityManager {

	public String getConsumerName() throws WSRPConsumerException {
		return _DEFAULT_CONSUMER_NAME;
	}

	public RegistrationData getDefaultRegistrationData()
			throws WSRPConsumerException {

		String globalXML = getGlobalRegistrationDataXML();

		if (globalXML == null) {
			globalXML = DEFAULT_REGISTRATION_DATA;
		}

		RegistrationData defaultRegistrationData =
				getRegistrationDataFromXML(globalXML);

		String consumerName = getConsumerName();

		if (consumerName == null || consumerName.trim().length() == 0) {
			consumerName = defaultRegistrationData.getConsumerName();
		}

		RegistrationData registrationData =  new RegistrationData();

		registrationData.setConsumerName(consumerName);
		registrationData.setConsumerAgent(_DEFAULT_CONSUMER_AGENT);
		registrationData.setMethodGetSupported(
				defaultRegistrationData.isMethodGetSupported());

		registrationData.getConsumerModes().addAll(
				defaultRegistrationData.getConsumerModes());

		registrationData.getConsumerWindowStates().addAll(
				defaultRegistrationData.getConsumerWindowStates());

		registrationData.getConsumerUserScopes().addAll(
				defaultRegistrationData.getConsumerUserScopes());

		registrationData.getRegistrationProperties().addAll(
				defaultRegistrationData.getRegistrationProperties());

		registrationData.getExtensions().addAll(
				defaultRegistrationData.getExtensions());

		return registrationData;
	}

	private String getGlobalRegistrationDataXML(){
		//TODO: global data integration
		return null;
	}

	public ProducerEntity getProducerEntity(String producerEntityId)
			throws WSRPConsumerException {

		ConfiguredProducer configuredProducer = null;

		try {
			configuredProducer =
					ConfiguredProducerLocalServiceUtil.getConfiguredProducer(
						new ConfiguredProducerPK(_portalId, producerEntityId));
		}
		catch (Exception e) {
			throw new WSRPConsumerException(e.getMessage());
		}

		if (configuredProducer == null){
			throw new WSRPConsumerException(
					"ProducerEntityManagerDBImpl.getProducerEntity(): " +
						"failed to load producer entity.  id=" +
							producerEntityId);
		}

		return populateConfiguredProducer(configuredProducer);
	}

	public Set<String> getProducerEntityIds() throws WSRPConsumerException {
		Set<String> producerEntityIds = new HashSet<String>();

		try {
			List<ConfiguredProducer> configuredProducers =
					ConfiguredProducerLocalServiceUtil.findByP_N(
						_portalId, _namespace);

			for(ConfiguredProducer configuredProducer : configuredProducers){
				producerEntityIds.add(
						configuredProducer.getConfiguredProducerId());
			}
		}
		catch (SystemException e) {
			throw new WSRPConsumerException(e.getMessage());
		}

		return producerEntityIds;
	}

	public Map<String, String> getStandardUserProfileMapping()
			throws WSRPConsumerException {

		return Collections.EMPTY_MAP;
	}

	public void init(String portalId, String namespace)
			throws WSRPConsumerException {

		this._portalId = portalId;
		this._namespace = namespace;
	}

	public boolean isActivated() throws WSRPConsumerException {
		return true;
	}

	public void setActivated(boolean status) throws WSRPConsumerException {
		//TODO : Global data
	}

	public void setAllowedUserProfileMapping(String producerEntityId,
		Map<String, String> allowedUserProfileMap)
				throws WSRPConsumerException {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void setConsumerName(String consumerName)
		throws WSRPConsumerException {
		//TODO: Global data
	}

	public void setCustomUserProfileMapping(String producerEntityId,
		Map<String, String> customUserProfileMap)
				throws WSRPConsumerException {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void setDefaultRegistrationData(RegistrationData regisData)
		throws WSRPConsumerException {

		//TODO: Global data
	}

	public void setIdentityPropagationType(String producerEntityId, String type)
			throws WSRPConsumerException {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void setName(String producerEntityId, String name)
		throws WSRPConsumerException {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void setStandardUserProfileMapping(
			Map<String, String> standardUserProfileMap)
				throws WSRPConsumerException {

		//TODO: Global data
	}

	public void setStatus(String producerEntityId, ProducerEntityStatus status)
			throws WSRPConsumerException {

		try {
			ConfiguredProducer configuredProducer =
					ConfiguredProducerLocalServiceUtil.getConfiguredProducer(
						new ConfiguredProducerPK(_portalId, producerEntityId));

			configuredProducer.setProducerStatus(status.getValue());

			ConfiguredProducerLocalServiceUtil.updateConfiguredProducer(
					configuredProducer);
		}
		catch (Exception e) {
			throw new WSRPConsumerException(e.getMessage());
		}
	}

	public void setUserCategoryMapping(
			String producerEntityId, Map<String, String> userCategoryMap)
				throws WSRPConsumerException {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void storeModifiedRegistration(
			String producerEntityId, String registrationDataXML,
				String registrationContextXML)
					throws WSRPConsumerException {
		try {
			ConfiguredProducer configuredProducer =
					ConfiguredProducerLocalServiceUtil.getConfiguredProducer(
						new ConfiguredProducerPK(_portalId, producerEntityId));

			configuredProducer.setRegistrationData(registrationDataXML);
			configuredProducer.setRegistrationContext(registrationContextXML);

			ConfiguredProducerLocalServiceUtil.updateConfiguredProducer(
					configuredProducer);
		}
		catch (Exception e) {
			throw new WSRPConsumerException(e.getMessage());
		}
	}

	public void storeProducerEntity(ProducerEntity producerEntity)
		throws WSRPConsumerException {

		String registrationDataXML = getRegistrationDataXMLFromRD(
				producerEntity.getRegistrationData());

		String registrationContextXML = getRegistrationContextXMLFromRC(
				producerEntity.getRegistrationContext());

		String serviceDescriptionXML = getServiceDescriptionXMLFromSD(
				producerEntity.getServiceDescription());

		ConfiguredProducer configuredProducer = new ConfiguredProducerImpl();

		configuredProducer.setPrimaryKey(new ConfiguredProducerPK(
				_portalId, producerEntity.getId()));

		configuredProducer.setConfiguredProducerName(producerEntity.getName());
		configuredProducer.setNamespace(_namespace);
		configuredProducer.setProducerURL(producerEntity.getURL().toString());
		configuredProducer.setProducerVersion(
				producerEntity.getVersion().value());

		configuredProducer.setProducerMarkupEndpoint(
				producerEntity.getMarkupEndpoint());

		configuredProducer.setProducerStatus(
				producerEntity.getStatus().getValue());

		configuredProducer.setRegistrationData(registrationDataXML);
		configuredProducer.setRegistrationContext(registrationContextXML);
		configuredProducer.setServiceDescription(serviceDescriptionXML);
		//configuredProducer.setUserCategoryMapping("");

		configuredProducer.setIdentityPropagationType(
				producerEntity.getIdentityPropagationType());

		configuredProducer.setSdLastModified(
					producerEntity.getServiceDescriptionLastModified());

		try {
			ConfiguredProducerLocalServiceUtil.addConfiguredProducer(
					configuredProducer);
		} catch (SystemException se) {
			throw new WSRPConsumerException(se.getMessage());
		}

	}

	public void storeUpdatedServiceDescription(
			String producerEntityId, String serviceDescriptionXML)
				throws WSRPConsumerException {

		try {
			ConfiguredProducer configuredProducer =
					ConfiguredProducerLocalServiceUtil.getConfiguredProducer(
						new ConfiguredProducerPK(_portalId, producerEntityId));

			configuredProducer.setServiceDescription(serviceDescriptionXML);

			ConfiguredProducerLocalServiceUtil.updateConfiguredProducer(
					configuredProducer);
		}
		catch (Exception e) {
			throw new WSRPConsumerException(e.getMessage());
		}

	}

	public void purgeProducerEntity(String producerEntityId)
			throws WSRPConsumerException {

		try {
			ConfiguredProducerLocalServiceUtil.deleteConfiguredProducer(
				new ConfiguredProducerPK(_portalId, producerEntityId));
		}
		catch (Exception se) {
			throw new WSRPConsumerException(se.getMessage());
		}
	}

    private ProducerEntity populateConfiguredProducer(
			ConfiguredProducer producer)
				throws WSRPConsumerException{

		String id = producer.getConfiguredProducerId();
		String name = producer.getConfiguredProducerName();
		String urlString = producer.getProducerURL();
		String markupEndpoint = producer.getProducerMarkupEndpoint();
		String version = producer.getProducerVersion();
		String identityPropagationType = producer.getIdentityPropagationType();
		int status = producer.getProducerStatus();
		ProducerEntityStatus entityStatus =
				ProducerEntityStatus.getProducerEntityStatus((short)status);

		String registrationDataXML = producer.getRegistrationData();
		String registrationContextXML = producer.getRegistrationContext();
		String serviceDescriptionXML = producer.getServiceDescription();
		String userCategoryXML = producer.getUserCategoryMapping();
		long sdLastModified = -1;
		String lastModified = null;

		sdLastModified = producer.getSdLastModified();
		lastModified = Long.toString(sdLastModified);

		URL url = null;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException mfue) {
			throw new WSRPConsumerException(
					"ProducerEntityManagerImpl failed to create URL urlString="
						+ urlString, mfue);
		}

		RegistrationData registrationData =
				getRegistrationDataFromXML(registrationDataXML);

		RegistrationContext registrationContext =
				getRegistrationContextFromXML(registrationContextXML);

		ServiceDescription serviceDescription =
				getServiceDesctionFromXML(serviceDescriptionXML);

		Map userCategoryMap =
				getConsumerObjectFactory().getMap(userCategoryXML);

		ProducerEntity pe = new ProducerEntityImpl(
				id, name, url, WSRPVersion.fromValue(version), markupEndpoint,
					entityStatus, registrationData, registrationContext,
						serviceDescription, userCategoryMap, null, null,
							sdLastModified, lastModified,
								producer.getEntityVersion(),
									identityPropagationType, null);

		return pe;

	}

	private String _namespace = null;
	private String _portalId = null;

	private static final String _DEFAULT_CONSUMER_AGENT =
			"Liferay Portal Consumer";

	private static final String _DEFAULT_CONSUMER_NAME =
			"Liferay WSRP Consumer";

}