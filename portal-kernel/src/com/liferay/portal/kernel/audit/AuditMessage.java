/*
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

package com.liferay.portal.kernel.audit;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * <a href="AuditMessage.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Mika Koivisto
 * @author Bruno Farache
 */
public class AuditMessage implements Serializable {

	public static final String TIMESTAMP_FORMAT = "yyyyMMddkkmmssSSS";

	public AuditMessage(
			long companyId, long userId, String userName,
			String classPK, String className, String type)
		throws JSONException {

		this(
			companyId, userId, userName, classPK, className, type,
			new Date(), JSONFactoryUtil.createJSONObject());
	}

	public AuditMessage(
			long companyId, long userId, String userName,
			String classPK, String className, String type,
			JSONObject additionalInfo)
		throws JSONException {

		this(
			companyId, userId, userName, classPK, className, type,
			new Date(), additionalInfo);
	}

	public AuditMessage(
			long companyId, long userId, String userName,
			String classPK, String className, String type,
			Date timestamp, JSONObject additionalInfo)
		throws JSONException {

		_companyId = companyId;
		_userId = userId;
		_userName = userName;
		_classPK = classPK;
		_className = className;
		_type = type;
		_timestamp = timestamp;
		_additionalInfo = additionalInfo;
		_originalMessage = toJSONObject().toString();

		AuditRequestThreadLocal auditRequestThreadLocal =
			AuditRequestThreadLocal.getAuditThreadLocal();
		_clientHost = auditRequestThreadLocal.getClientHost();
		_clientIP = auditRequestThreadLocal.getClientIP();
		_serverName = auditRequestThreadLocal.getServerName();
		_serverPort = auditRequestThreadLocal.getServerPort();
		_sessionID = auditRequestThreadLocal.getSessionID();
	}

	public AuditMessage(String message) throws JSONException, ParseException {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject(message);

		_companyId = jsonObj.getLong(_COMPANY_ID);
		_userId = jsonObj.getLong(_USER_ID);
		_userName = jsonObj.getString(_USER_NAME);
		_classPK = jsonObj.getString(_CLASS_PK);
		_className = jsonObj.getString(_CLASS_NAME);
		_type = jsonObj.getString(_TYPE);
		if (jsonObj.has(_SESSION_ID)) {
			_sessionID = jsonObj.getString(_SESSION_ID);
		}
		if (jsonObj.has(_CLIENT_HOST)) {
			_clientHost = jsonObj.getString(_CLIENT_HOST);
		}
		if (jsonObj.has(_CLIENT_IP)) {
			_clientIP = jsonObj.getString(_CLIENT_IP);
		}
		if (jsonObj.has(_SERVER_NAME)) {
			_serverName = jsonObj.getString(_SERVER_NAME);
		}
		if (jsonObj.has(_SERVER_PORT)) {
			_serverPort = jsonObj.getInt(_SERVER_PORT);
		}
		_timestamp = GetterUtil.getDate(
			jsonObj.getString(_TIMESTAMP), _getDateFormat());
		_additionalInfo = jsonObj.getJSONObject(_ADDITIONAL_INFO);
		_originalMessage = message;
	}

	public JSONObject toJSONObject() throws JSONException {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put(_ADDITIONAL_INFO, _additionalInfo);
		jsonObj.put(_COMPANY_ID, _companyId);
		jsonObj.put(_CLASS_PK, _classPK);
		jsonObj.put(_CLASS_NAME, _className);
		jsonObj.put(_CLIENT_IP, _clientIP);
		jsonObj.put(_clientHost, _clientHost);
		jsonObj.put(_SERVER_PORT, _serverPort);
		jsonObj.put(_SERVER_NAME, _serverName);
		jsonObj.put(_SESSION_ID, _sessionID);
		jsonObj.put(_TIMESTAMP, _getTimestampString());
		jsonObj.put(_TYPE, _type);
		jsonObj.put(_USER_ID, _userId);
		jsonObj.put(_USER_NAME, _userName);

		return jsonObj;
	}

	public JSONObject getAdditionalInfo() {
		return _additionalInfo;
	}

	public String getClassName() {
		return _className;
	}

	public String getClassPK() {
		return _classPK;
	}

	public String getClientIP() {
		return _clientIP;
	}

	public String getClientHost() {
		return _clientHost;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public String getOriginalMessage() {
		return _originalMessage;
	}

	public String getServerName() {
		return _serverName;
	}

	public int getServerPort() {
		return _serverPort;
	}

	public String getSessionID() {
		return _sessionID;
	}

	public Date getTimestamp() {
		return _timestamp;
	}

	public String getType() {
		return _type;
	}

	public long getUserId() {
		return _userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setAdditionalInfo(JSONObject additionalInfo) {
		_additionalInfo = additionalInfo;
	}

	public void setClassPK(String classPK) {
		_classPK = classPK;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClientHost(String clientHost) {
		_clientHost = clientHost;
	}

	public void setClientIP(String clientIP) {
		_clientIP = clientIP;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setOriginalMessage(String originalMessage) {
		_originalMessage = originalMessage;
	}

	public void setServerName(String serverName) {
		_serverName = serverName;
	}

	public void setServerPort(int serverPort) {
		_serverPort = serverPort;
	}

	public void setSessionID(String sessionID) {
		_sessionID = sessionID;
	}

	public void setTimestamp(Date timestamp) {
		_timestamp = timestamp;
	}

	public void setType(String type) {
		_type = type;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	private String _getTimestampString() {
		return _getDateFormat().format(new Date());
	}

	private DateFormat _getDateFormat() {
		return new SimpleDateFormat(TIMESTAMP_FORMAT);
	}

	private static final String _COMPANY_ID = "companyId";
	private static final String _USER_ID = "userId";
	private static final String _USER_NAME = "userName";
	private static final String _CLASS_PK = "classPK";
	private static final String _CLASS_NAME = "className";
	private static final String _TYPE = "type";
	private static final String _SESSION_ID = "sessionID";
	private static final String _CLIENT_IP = "clientIP";
	private static final String _CLIENT_HOST = "clientHost";
	private static final String _SERVER_PORT = "serverPort";
	private static final String _SERVER_NAME = "serverName";
	private static final String _TIMESTAMP = "timestamp";
	private static final String _ADDITIONAL_INFO = "additionalInfo";


	private JSONObject _additionalInfo;
	private String _className;
	private String _classPK;
	private String _clientHost;
	private String _clientIP;
	private long _companyId;
	private String _originalMessage;
	private String _serverName;
	private int _serverPort;
	private String _sessionID;
	private Date _timestamp;
	private String _type;
	private long _userId;
	private String _userName;
}
