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

package com.liferay.wsrp.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="WSRPConsumerRegistrationSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * <code>com.liferay.wsrp.service.http.WSRPConsumerRegistrationServiceSoap</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.wsrp.service.http.WSRPConsumerRegistrationServiceSoap
 *
 */
public class WSRPConsumerRegistrationSoap implements Serializable {
	public static WSRPConsumerRegistrationSoap toSoapModel(
		WSRPConsumerRegistration model) {
		WSRPConsumerRegistrationSoap soapModel = new WSRPConsumerRegistrationSoap();

		soapModel.setRegistrationId(model.getRegistrationId());
		soapModel.setStatus(model.getStatus());
		soapModel.setConsumerName(model.getConsumerName());
		soapModel.setRegistrationHandle(model.getRegistrationHandle());
		soapModel.setConsumerAgent(model.getConsumerAgent());
		soapModel.setMethodGetSupported(model.getMethodGetSupported());
		soapModel.setConsumerModes(model.getConsumerModes());
		soapModel.setConsumerWindowStates(model.getConsumerWindowStates());
		soapModel.setConsumerUserScopes(model.getConsumerUserScopes());
		soapModel.setCustomUserProfileData(model.getCustomUserProfileData());
		soapModel.setRegistrationProperties(model.getRegistrationProperties());
		soapModel.setLifetimeTerminationTime(model.getLifetimeTerminationTime());
		soapModel.setProducerKey(model.getProducerKey());

		return soapModel;
	}

	public static WSRPConsumerRegistrationSoap[] toSoapModels(
		List<WSRPConsumerRegistration> models) {
		List<WSRPConsumerRegistrationSoap> soapModels = new ArrayList<WSRPConsumerRegistrationSoap>(models.size());

		for (WSRPConsumerRegistration model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new WSRPConsumerRegistrationSoap[soapModels.size()]);
	}

	public WSRPConsumerRegistrationSoap() {
	}

	public long getPrimaryKey() {
		return _registrationId;
	}

	public void setPrimaryKey(long pk) {
		setRegistrationId(pk);
	}

	public long getRegistrationId() {
		return _registrationId;
	}

	public void setRegistrationId(long registrationId) {
		_registrationId = registrationId;
	}

	public boolean getStatus() {
		return _status;
	}

	public boolean isStatus() {
		return _status;
	}

	public void setStatus(boolean status) {
		_status = status;
	}

	public String getConsumerName() {
		return _consumerName;
	}

	public void setConsumerName(String consumerName) {
		_consumerName = consumerName;
	}

	public String getRegistrationHandle() {
		return _registrationHandle;
	}

	public void setRegistrationHandle(String registrationHandle) {
		_registrationHandle = registrationHandle;
	}

	public String getConsumerAgent() {
		return _consumerAgent;
	}

	public void setConsumerAgent(String consumerAgent) {
		_consumerAgent = consumerAgent;
	}

	public boolean getMethodGetSupported() {
		return _methodGetSupported;
	}

	public boolean isMethodGetSupported() {
		return _methodGetSupported;
	}

	public void setMethodGetSupported(boolean methodGetSupported) {
		_methodGetSupported = methodGetSupported;
	}

	public String getConsumerModes() {
		return _consumerModes;
	}

	public void setConsumerModes(String consumerModes) {
		_consumerModes = consumerModes;
	}

	public String getConsumerWindowStates() {
		return _consumerWindowStates;
	}

	public void setConsumerWindowStates(String consumerWindowStates) {
		_consumerWindowStates = consumerWindowStates;
	}

	public String getConsumerUserScopes() {
		return _consumerUserScopes;
	}

	public void setConsumerUserScopes(String consumerUserScopes) {
		_consumerUserScopes = consumerUserScopes;
	}

	public String getCustomUserProfileData() {
		return _customUserProfileData;
	}

	public void setCustomUserProfileData(String customUserProfileData) {
		_customUserProfileData = customUserProfileData;
	}

	public String getRegistrationProperties() {
		return _registrationProperties;
	}

	public void setRegistrationProperties(String registrationProperties) {
		_registrationProperties = registrationProperties;
	}

	public String getLifetimeTerminationTime() {
		return _lifetimeTerminationTime;
	}

	public void setLifetimeTerminationTime(String lifetimeTerminationTime) {
		_lifetimeTerminationTime = lifetimeTerminationTime;
	}

	public String getProducerKey() {
		return _producerKey;
	}

	public void setProducerKey(String producerKey) {
		_producerKey = producerKey;
	}

	private long _registrationId;
	private boolean _status;
	private String _consumerName;
	private String _registrationHandle;
	private String _consumerAgent;
	private boolean _methodGetSupported;
	private String _consumerModes;
	private String _consumerWindowStates;
	private String _consumerUserScopes;
	private String _customUserProfileData;
	private String _registrationProperties;
	private String _lifetimeTerminationTime;
	private String _producerKey;
}