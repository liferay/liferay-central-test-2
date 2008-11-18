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

package com.liferay.wsrp.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;

import com.liferay.wsrp.model.WSRPConsumerRegistration;
import com.liferay.wsrp.model.WSRPConsumerRegistrationSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="WSRPConsumerRegistrationModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>WSRPConsumerRegistration</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.wsrp.model.WSRPConsumerRegistration
 * @see com.liferay.wsrp.model.WSRPConsumerRegistrationModel
 * @see com.liferay.wsrp.model.impl.WSRPConsumerRegistrationImpl
 *
 */
public class WSRPConsumerRegistrationModelImpl extends BaseModelImpl {
	public static final String TABLE_NAME = "WSRPConsumerRegistration";
	public static final Object[][] TABLE_COLUMNS = {
			{ "consumerRegistrationId", new Integer(Types.BIGINT) },
			

			{ "consumerName", new Integer(Types.VARCHAR) },
			

			{ "status", new Integer(Types.BOOLEAN) },
			

			{ "registrationHandle", new Integer(Types.VARCHAR) },
			

			{ "consumerAgent", new Integer(Types.VARCHAR) },
			

			{ "methodGetSupported", new Integer(Types.BOOLEAN) },
			

			{ "consumerModes", new Integer(Types.VARCHAR) },
			

			{ "consumerWindowStates", new Integer(Types.VARCHAR) },
			

			{ "consumerUserScopes", new Integer(Types.VARCHAR) },
			

			{ "customUserProfileData", new Integer(Types.VARCHAR) },
			

			{ "registrationProperties", new Integer(Types.VARCHAR) },
			

			{ "lifetimeTerminationTime", new Integer(Types.VARCHAR) },
			

			{ "producerKey", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table WSRPConsumerRegistration (consumerRegistrationId LONG not null primary key,consumerName VARCHAR(100) null,status BOOLEAN,registrationHandle VARCHAR(75) null,consumerAgent VARCHAR(200) null,methodGetSupported BOOLEAN,consumerModes VARCHAR(200) null,consumerWindowStates VARCHAR(200) null,consumerUserScopes VARCHAR(200) null,customUserProfileData VARCHAR(75) null,registrationProperties STRING null,lifetimeTerminationTime VARCHAR(75) null,producerKey VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table WSRPConsumerRegistration";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.wsrp.model.WSRPConsumerRegistration"),
			true);

	public static WSRPConsumerRegistration toModel(
		WSRPConsumerRegistrationSoap soapModel) {
		WSRPConsumerRegistration model = new WSRPConsumerRegistrationImpl();

		model.setConsumerRegistrationId(soapModel.getConsumerRegistrationId());
		model.setConsumerName(soapModel.getConsumerName());
		model.setStatus(soapModel.getStatus());
		model.setRegistrationHandle(soapModel.getRegistrationHandle());
		model.setConsumerAgent(soapModel.getConsumerAgent());
		model.setMethodGetSupported(soapModel.getMethodGetSupported());
		model.setConsumerModes(soapModel.getConsumerModes());
		model.setConsumerWindowStates(soapModel.getConsumerWindowStates());
		model.setConsumerUserScopes(soapModel.getConsumerUserScopes());
		model.setCustomUserProfileData(soapModel.getCustomUserProfileData());
		model.setRegistrationProperties(soapModel.getRegistrationProperties());
		model.setLifetimeTerminationTime(soapModel.getLifetimeTerminationTime());
		model.setProducerKey(soapModel.getProducerKey());

		return model;
	}

	public static List<WSRPConsumerRegistration> toModels(
		WSRPConsumerRegistrationSoap[] soapModels) {
		List<WSRPConsumerRegistration> models = new ArrayList<WSRPConsumerRegistration>(soapModels.length);

		for (WSRPConsumerRegistrationSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.wsrp.model.WSRPConsumerRegistration"));

	public WSRPConsumerRegistrationModelImpl() {
	}

	public long getPrimaryKey() {
		return _consumerRegistrationId;
	}

	public void setPrimaryKey(long pk) {
		setConsumerRegistrationId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_consumerRegistrationId);
	}

	public long getConsumerRegistrationId() {
		return _consumerRegistrationId;
	}

	public void setConsumerRegistrationId(long consumerRegistrationId) {
		if (consumerRegistrationId != _consumerRegistrationId) {
			_consumerRegistrationId = consumerRegistrationId;
		}
	}

	public String getConsumerName() {
		return GetterUtil.getString(_consumerName);
	}

	public void setConsumerName(String consumerName) {
		if (((consumerName == null) && (_consumerName != null)) ||
				((consumerName != null) && (_consumerName == null)) ||
				((consumerName != null) && (_consumerName != null) &&
				!consumerName.equals(_consumerName))) {
			_consumerName = consumerName;
		}
	}

	public boolean getStatus() {
		return _status;
	}

	public boolean isStatus() {
		return _status;
	}

	public void setStatus(boolean status) {
		if (status != _status) {
			_status = status;
		}
	}

	public String getRegistrationHandle() {
		return GetterUtil.getString(_registrationHandle);
	}

	public void setRegistrationHandle(String registrationHandle) {
		if (((registrationHandle == null) && (_registrationHandle != null)) ||
				((registrationHandle != null) && (_registrationHandle == null)) ||
				((registrationHandle != null) && (_registrationHandle != null) &&
				!registrationHandle.equals(_registrationHandle))) {
			_registrationHandle = registrationHandle;
		}
	}

	public String getConsumerAgent() {
		return GetterUtil.getString(_consumerAgent);
	}

	public void setConsumerAgent(String consumerAgent) {
		if (((consumerAgent == null) && (_consumerAgent != null)) ||
				((consumerAgent != null) && (_consumerAgent == null)) ||
				((consumerAgent != null) && (_consumerAgent != null) &&
				!consumerAgent.equals(_consumerAgent))) {
			_consumerAgent = consumerAgent;
		}
	}

	public boolean getMethodGetSupported() {
		return _methodGetSupported;
	}

	public boolean isMethodGetSupported() {
		return _methodGetSupported;
	}

	public void setMethodGetSupported(boolean methodGetSupported) {
		if (methodGetSupported != _methodGetSupported) {
			_methodGetSupported = methodGetSupported;
		}
	}

	public String getConsumerModes() {
		return GetterUtil.getString(_consumerModes);
	}

	public void setConsumerModes(String consumerModes) {
		if (((consumerModes == null) && (_consumerModes != null)) ||
				((consumerModes != null) && (_consumerModes == null)) ||
				((consumerModes != null) && (_consumerModes != null) &&
				!consumerModes.equals(_consumerModes))) {
			_consumerModes = consumerModes;
		}
	}

	public String getConsumerWindowStates() {
		return GetterUtil.getString(_consumerWindowStates);
	}

	public void setConsumerWindowStates(String consumerWindowStates) {
		if (((consumerWindowStates == null) && (_consumerWindowStates != null)) ||
				((consumerWindowStates != null) &&
				(_consumerWindowStates == null)) ||
				((consumerWindowStates != null) &&
				(_consumerWindowStates != null) &&
				!consumerWindowStates.equals(_consumerWindowStates))) {
			_consumerWindowStates = consumerWindowStates;
		}
	}

	public String getConsumerUserScopes() {
		return GetterUtil.getString(_consumerUserScopes);
	}

	public void setConsumerUserScopes(String consumerUserScopes) {
		if (((consumerUserScopes == null) && (_consumerUserScopes != null)) ||
				((consumerUserScopes != null) && (_consumerUserScopes == null)) ||
				((consumerUserScopes != null) && (_consumerUserScopes != null) &&
				!consumerUserScopes.equals(_consumerUserScopes))) {
			_consumerUserScopes = consumerUserScopes;
		}
	}

	public String getCustomUserProfileData() {
		return GetterUtil.getString(_customUserProfileData);
	}

	public void setCustomUserProfileData(String customUserProfileData) {
		if (((customUserProfileData == null) &&
				(_customUserProfileData != null)) ||
				((customUserProfileData != null) &&
				(_customUserProfileData == null)) ||
				((customUserProfileData != null) &&
				(_customUserProfileData != null) &&
				!customUserProfileData.equals(_customUserProfileData))) {
			_customUserProfileData = customUserProfileData;
		}
	}

	public String getRegistrationProperties() {
		return GetterUtil.getString(_registrationProperties);
	}

	public void setRegistrationProperties(String registrationProperties) {
		if (((registrationProperties == null) &&
				(_registrationProperties != null)) ||
				((registrationProperties != null) &&
				(_registrationProperties == null)) ||
				((registrationProperties != null) &&
				(_registrationProperties != null) &&
				!registrationProperties.equals(_registrationProperties))) {
			_registrationProperties = registrationProperties;
		}
	}

	public String getLifetimeTerminationTime() {
		return GetterUtil.getString(_lifetimeTerminationTime);
	}

	public void setLifetimeTerminationTime(String lifetimeTerminationTime) {
		if (((lifetimeTerminationTime == null) &&
				(_lifetimeTerminationTime != null)) ||
				((lifetimeTerminationTime != null) &&
				(_lifetimeTerminationTime == null)) ||
				((lifetimeTerminationTime != null) &&
				(_lifetimeTerminationTime != null) &&
				!lifetimeTerminationTime.equals(_lifetimeTerminationTime))) {
			_lifetimeTerminationTime = lifetimeTerminationTime;
		}
	}

	public String getProducerKey() {
		return GetterUtil.getString(_producerKey);
	}

	public void setProducerKey(String producerKey) {
		if (((producerKey == null) && (_producerKey != null)) ||
				((producerKey != null) && (_producerKey == null)) ||
				((producerKey != null) && (_producerKey != null) &&
				!producerKey.equals(_producerKey))) {
			_producerKey = producerKey;
		}
	}

	public WSRPConsumerRegistration toEscapedModel() {
		if (isEscapedModel()) {
			return (WSRPConsumerRegistration)this;
		}
		else {
			WSRPConsumerRegistration model = new WSRPConsumerRegistrationImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setConsumerRegistrationId(getConsumerRegistrationId());
			model.setConsumerName(HtmlUtil.escape(getConsumerName()));
			model.setStatus(getStatus());
			model.setRegistrationHandle(HtmlUtil.escape(getRegistrationHandle()));
			model.setConsumerAgent(HtmlUtil.escape(getConsumerAgent()));
			model.setMethodGetSupported(getMethodGetSupported());
			model.setConsumerModes(HtmlUtil.escape(getConsumerModes()));
			model.setConsumerWindowStates(HtmlUtil.escape(
					getConsumerWindowStates()));
			model.setConsumerUserScopes(HtmlUtil.escape(getConsumerUserScopes()));
			model.setCustomUserProfileData(HtmlUtil.escape(
					getCustomUserProfileData()));
			model.setRegistrationProperties(HtmlUtil.escape(
					getRegistrationProperties()));
			model.setLifetimeTerminationTime(HtmlUtil.escape(
					getLifetimeTerminationTime()));
			model.setProducerKey(HtmlUtil.escape(getProducerKey()));

			model = (WSRPConsumerRegistration)Proxy.newProxyInstance(WSRPConsumerRegistration.class.getClassLoader(),
					new Class[] { WSRPConsumerRegistration.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(WSRPConsumerRegistration.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		WSRPConsumerRegistrationImpl clone = new WSRPConsumerRegistrationImpl();

		clone.setConsumerRegistrationId(getConsumerRegistrationId());
		clone.setConsumerName(getConsumerName());
		clone.setStatus(getStatus());
		clone.setRegistrationHandle(getRegistrationHandle());
		clone.setConsumerAgent(getConsumerAgent());
		clone.setMethodGetSupported(getMethodGetSupported());
		clone.setConsumerModes(getConsumerModes());
		clone.setConsumerWindowStates(getConsumerWindowStates());
		clone.setConsumerUserScopes(getConsumerUserScopes());
		clone.setCustomUserProfileData(getCustomUserProfileData());
		clone.setRegistrationProperties(getRegistrationProperties());
		clone.setLifetimeTerminationTime(getLifetimeTerminationTime());
		clone.setProducerKey(getProducerKey());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		WSRPConsumerRegistrationImpl wsrpConsumerRegistration = (WSRPConsumerRegistrationImpl)obj;

		long pk = wsrpConsumerRegistration.getPrimaryKey();

		if (getPrimaryKey() < pk) {
			return -1;
		}
		else if (getPrimaryKey() > pk) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		WSRPConsumerRegistrationImpl wsrpConsumerRegistration = null;

		try {
			wsrpConsumerRegistration = (WSRPConsumerRegistrationImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = wsrpConsumerRegistration.getPrimaryKey();

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

	private long _consumerRegistrationId;
	private String _consumerName;
	private boolean _status;
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
	private transient ExpandoBridge _expandoBridge;
}