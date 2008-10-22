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

package com.liferay.wsrp.producer.impl;

import com.liferay.wsrp.model.WSRPConsumerRegistration;
import com.liferay.wsrp.model.WSRPProducer;
import com.liferay.wsrp.service.WSRPConsumerRegistrationLocalServiceUtil;
import com.liferay.wsrp.service.persistence.WSRPConsumerRegistrationUtil;

import com.sun.portal.wsrp.common.KeyGenerator;
import com.sun.portal.wsrp.common.LeaseTime;
import com.sun.portal.wsrp.common.stubs.v2.PropertyDescription;
import com.sun.portal.wsrp.common.stubs.v2.RegistrationData;
import com.sun.portal.wsrp.producer.AbstractProducer;
import com.sun.portal.wsrp.producer.ProducerException;
import com.sun.portal.wsrp.producer.ProducerVersion;
import com.sun.portal.wsrp.producer.ProfileMapManager;
import com.sun.portal.wsrp.producer.registration.RegistrationRecord;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

/**
 * <a href="ProducerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Manish Gupta
 *
 */
public class ProducerImpl extends AbstractProducer {

	public ProducerImpl(WSRPProducer wsrpProducer){
		_wsrpProducer = wsrpProducer;
	}

	public String addRegistration(RegistrationRecord registrationRecord)
		throws ProducerException {

		String registrationHandle = KeyGenerator.generateKey();

		String consumerModes = _getCommaSeperatedString(
			registrationRecord.getConsumerModes());

		String consumerWindowStates = _getCommaSeperatedString(
			registrationRecord.getConsumerWindowStates());

		String customUserProfileData = _getCommaSeperatedString(
			registrationRecord.getCustomUserProfileData());

		String consumerUserScopes = _getCommaSeperatedString(
			registrationRecord.getConsumerUserScopes());

		String registrationProperties = null;
		String lifeTime = null;

		if (registrationRecord.getLifetime() != null) {
			LeaseTime leaseTime = registrationRecord.getLifetime();

			if (leaseTime.getTerminationTime() != null) {
				lifeTime = registrationRecord.getLifetime()
							.getTerminationTime().toXMLFormat();
			}
		}

		try {
			WSRPConsumerRegistrationLocalServiceUtil.addConsumerRegistration(
				registrationRecord.getConsumerName(),
				registrationRecord.isEnabled(), registrationHandle,
				registrationRecord.getConsumerAgent(),
				registrationRecord.isMethodGetSupported(),
				consumerModes,
				consumerWindowStates,
				consumerUserScopes,
				customUserProfileData,
				registrationProperties,
				lifeTime,
				_wsrpProducer.getInstanceName());
		}
		catch (Exception e) {
			throw new ProducerException(e);
		}

		return registrationHandle;
	}

	public void addRegistrationPropertyDescription(
			PropertyDescription propertyDescription)
		throws ProducerException {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	WSRPProducer getModel(){
		return _wsrpProducer;
	}

	public String getNamespace() {
		return _wsrpProducer.getNamespace();
	}

	public Set<String> getOfferedPortletNames() throws ProducerException {
		return _getOfferedPortletSet(_wsrpProducer.getOfferedPortlets());
	}

	public String getPortalId() {
		return _wsrpProducer.getPortalId();
	}

	public String getProducerKey() {
		return _wsrpProducer.getInstanceName();
	}

	public ProfileMapManager getProfileMapManager() throws ProducerException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public LeaseTime getRegistrationLifetime(String registrationHandle)
		throws ProducerException {

		LeaseTime leaseTime = null;

		WSRPConsumerRegistration consumerRegistration =
			_getConsumerRegistrationByHandle(registrationHandle);

		String terminationTime =
			consumerRegistration.getLifetimeTerminationTime();

		if (terminationTime != null && !terminationTime.isEmpty()) {

			try {
				DatatypeFactory df = DatatypeFactory.newInstance();

				leaseTime = new LeaseTime();
				leaseTime.setTerminationTime(
					df.newXMLGregorianCalendar(terminationTime));

			}
			catch (DatatypeConfigurationException e) {
				throw new ProducerException(
					"Could not create LeaseTime object", e);
			}
		}
		return leaseTime;
	}

	public Set<PropertyDescription> getRegistrationPropertyDescriptions(
			Set<String> desiredLocales) throws ProducerException {

		return Collections.EMPTY_SET;
	}

	public RegistrationRecord getRegistrationRecord(String registrationHandle)
		throws ProducerException {

		WSRPConsumerRegistration consumerRegistration =
			_getConsumerRegistrationByHandle(registrationHandle);

		return _getRegistrationRecord(consumerRegistration);
	}

	public Set<RegistrationRecord> getRegistrationRecords()
		throws ProducerException {

		try{
			List<WSRPConsumerRegistration> consumerRegistrations =
				WSRPConsumerRegistrationUtil.findByProducerKey(
					_wsrpProducer.getInstanceName());

			Set<RegistrationRecord> registrationRecords =
				new HashSet<RegistrationRecord>();

			for (WSRPConsumerRegistration consumerRegistration :
				consumerRegistrations) {

				RegistrationRecord registrationRecord =
					_getRegistrationRecord(consumerRegistration);

				registrationRecords.add(registrationRecord);
			}

			return registrationRecords;
		}
		catch (Exception e) {
			throw new ProducerException(e);
		}
	}

	public boolean getRegistrationStatus(String registrationHandle)
		throws ProducerException {

		WSRPConsumerRegistration consumerRegistration =
			_getConsumerRegistrationByHandle(registrationHandle);

		return consumerRegistration.getStatus();
	}

	public String getRegistrationValidatorClassName()
		throws ProducerException {

		return _wsrpProducer.getRegistrationValidatorClass();
	}

	public ProducerVersion getVersion() throws ProducerException {
		return ProducerVersion.fromValue(_wsrpProducer.getVersion());
	}

	public boolean inbandRegistrationSupported() throws ProducerException {
		return _wsrpProducer.isSupportsInbandRegistration();
	}

	public boolean isEnabled() throws ProducerException {
		return _wsrpProducer.getStatus();
	}

	public boolean isValidRegistration(String registrationHandle)
		throws ProducerException {

		boolean valid = true;

		try {
			_getConsumerRegistrationByHandle(registrationHandle);
		}
		catch (Exception e) {
			valid = false;
		}
		return valid;
	}

	public void modifyRegistration(RegistrationRecord registrationRecord)
		throws ProducerException {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void removeRegistrationPropertyDescriptions(
			Set<String> propertyNames)
		throws ProducerException {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void removeRegistrations(Set<String> registrationHandles)
		throws ProducerException {

		try {
			for (String registrationHandle : registrationHandles) {
				WSRPConsumerRegistration consumerRegistration =
					_getConsumerRegistrationByHandle(registrationHandle);

				WSRPConsumerRegistrationLocalServiceUtil.
						deleteWSRPConsumerRegistration(consumerRegistration);
			}
		}
		catch (Exception e) {
			throw new ProducerException(
					"Unable to delete consumer registration", e);
		}

	}

	public boolean requiresRegistration() throws ProducerException {
		return _wsrpProducer.getRequiresRegistration();
	}

	public void setInbandRegistrationSupported(boolean value)
		throws ProducerException {

		_wsrpProducer.setSupportsInbandRegistration(value);
	}

	public void setIsEnabled(boolean status) throws ProducerException {
		_wsrpProducer.setStatus(status);
	}

	public void setOfferedPortletNames(Set<String> offeredPortlets)
			throws ProducerException {

		String offeredPortletsString = _getOfferedPortletString(
			offeredPortlets);

		_wsrpProducer.setOfferedPortlets(offeredPortletsString);
	}

	public void setPortalId(String portalId) {
		_wsrpProducer.setPortalId(portalId);
	}

	public void setRegistrationLifetime(
			String registrationHandle, LeaseTime lifetime)
		throws ProducerException {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void setRegistrationStatus(String registrationHandle, boolean value)
		throws ProducerException {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void setRegistrationValidatorClassName(String validatorClassName)
		throws ProducerException {

		_wsrpProducer.setRegistrationValidatorClass(validatorClassName);
	}

	public void setRequiresRegistration(boolean value)
		throws ProducerException {

		_wsrpProducer.setRequiresRegistration(value);
	}

	public void setVersion(ProducerVersion version) throws ProducerException {
		_wsrpProducer.setVersion(version.value());
	}

	private String _getCommaSeperatedString(List values){

		if (values == null || values.size() == 0) {
			return null;
		}

		StringBuffer sb = new StringBuffer();
		boolean prependComma = false;

		for(int i=0; i<values.size(); i++) {
			if (prependComma) {
				sb.append(",");
			}
			sb.append(values.get(i).toString());
			prependComma = true;
		}
		return sb.toString();
	}

	private WSRPConsumerRegistration _getConsumerRegistrationByHandle(
			String registrationHandle) throws ProducerException{

		try {
			return WSRPConsumerRegistrationUtil.fetchByR_P(
				registrationHandle, _wsrpProducer.getInstanceName());
		}
		catch (Exception e) {
			throw new ProducerException(e);
		}
	}

	private String _getOfferedPortletString(Set<String> portlets) {
		StringBuffer sb = new StringBuffer();

		if (portlets.isEmpty()) {
			return sb.toString();
		}

		boolean prependComma = false;
		for (String portlet : portlets) {

			if (!prependComma) {
				sb.append(portlet);
			}
			else {
				sb.append(",").append(portlet);
			}

			prependComma = true;
		}
		return sb.toString();
	}

	private Set<String> _getOfferedPortletSet(String offeredPortlets) {

		if (offeredPortlets == null || offeredPortlets.trim().length() == 0) {
			return Collections.EMPTY_SET;
		}

		Set<String> offeredPortletSet = new HashSet<String>();

		if (offeredPortlets.indexOf(",") == -1) {
			offeredPortletSet.add(offeredPortlets);
		}
		else {
			StringTokenizer st = new StringTokenizer(offeredPortlets, ",");

			while (st.hasMoreTokens()) {
				String portletName = st.nextToken();
				offeredPortletSet.add(portletName);
			}
		}
		return offeredPortletSet;
	}

	private RegistrationRecord _getRegistrationRecord(
			WSRPConsumerRegistration consumerRegistrationModel){

		String handle = consumerRegistrationModel.getRegistrationHandle();
		boolean enabled = consumerRegistrationModel.getStatus();

		RegistrationData data = new RegistrationData();

		data.setConsumerAgent(consumerRegistrationModel.getConsumerAgent());
		data.setConsumerName(consumerRegistrationModel.getConsumerName());
		data.setMethodGetSupported(
				consumerRegistrationModel.getMethodGetSupported());

		RegistrationRecord regRecord =
				new RegistrationRecord(handle, enabled, data, null);

		return regRecord;
	}

	private WSRPProducer _wsrpProducer = null;

}