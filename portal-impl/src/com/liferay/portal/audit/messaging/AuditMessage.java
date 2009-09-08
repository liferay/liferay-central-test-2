/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.audit.messaging;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * <a href="AuditMessage.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mika Koivisto
 *
 */
public class AuditMessage {
	
	public static final String TIMESTAMP_FORMAT = "yyyyMMddkkmmssSSS";

	public AuditMessage(
			long companyId, long userId, String userName,
			String classPK, String className, String type,
			String sessionID, String clientIP, String serverIP) 
		throws JSONException {

		this(
			companyId, userId, userName, classPK, className, type,
			sessionID, clientIP, serverIP, new Date(), new JSONObject());
	}

	public AuditMessage(
			long companyId, long userId, String userName,
			String classPK, String className, String type,
			String sessionID, String clientIP, String serverIP,
			JSONObject additionalInfo) 
		throws JSONException {

		this(
			companyId, userId, userName, classPK, className, type,
			sessionID, clientIP, serverIP, new Date(), additionalInfo);
	}
	
	public AuditMessage(
			long companyId, long userId, String userName,
			String classPK, String className, String type,
			String sessionID, String clientIP, String serverIP,
			Date timestamp, JSONObject additionalInfo) 
		throws JSONException {

		_companyId = companyId;
		_userId = userId;
		_userName = userName;
		_classPK = classPK;
		_className = className;
		_type = type;
		_sessionID = sessionID;
		_clientIP = clientIP;
		_serverIP = serverIP;
		_timestamp = timestamp;
		_additionalInfo = additionalInfo;
		_originalMessage = toJSONObject().toString();
	}
	
	public AuditMessage(String message) throws JSONException, ParseException {
		JSONObject jsonObj = new JSONObject(message);

		_companyId = jsonObj.getLong("companyId");
		_userId = jsonObj.getLong("userId");
		_userName = jsonObj.getString("userName");
		_classPK = jsonObj.getString("classPK");
		_className = jsonObj.getString("className");
		_type = jsonObj.getString("type");
		_sessionID = jsonObj.getString("sessionID");
		_clientIP = jsonObj.getString("clientIP");
		_serverIP = jsonObj.getString("serverIP");
		_timestamp =  GetterUtil.getDate(
			jsonObj.getString("timestamp"), _getDateFormat());
		_additionalInfo = jsonObj.getJSONObject("additionalInfo");
		_originalMessage = message;
	}

	public JSONObject toJSONObject() throws JSONException {
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("companyId", _companyId);
		jsonObj.put("userId", _userId);
		jsonObj.put("userName", _userName);
		jsonObj.put("classPK", _classPK);
		jsonObj.put("className", _className);
		jsonObj.put("type", _type);
		jsonObj.put("sessionID", _sessionID);
		jsonObj.put("clientIP", _clientIP);
		jsonObj.put("serverIP", _serverIP);
		jsonObj.put("timestamp", _getTimestamp());
		jsonObj.put("additionalInfo", _additionalInfo);
		
		return jsonObj;
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

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getClientIP() {
		return _clientIP;
	}

	public void setClientIP(String clientIP) {
		_clientIP = clientIP;
	}

	public String getServerIP() {
		return _serverIP;
	}

	public void setServerIP(String serverIP) {
		_serverIP = serverIP;
	}

	public String getClassPK() {
		return _classPK;
	}

	public void setClassPK(String classPK) {
		_classPK = classPK;
	}

	public String getClassName() {
		return _className;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public String getSessionID() {
		return _sessionID;
	}

	public void setSessionID(String sessionID) {
		_sessionID = sessionID;
	}
	
	public Date getTimestamp() {
		return _timestamp;
	}

	public void setTimestamp(Date timestamp) {
		_timestamp = timestamp;
	}

	public JSONObject getAdditionalInfo() {
		return _additionalInfo;
	}

	public void setAdditionalInfo(JSONObject additionalInfo) {
		_additionalInfo = additionalInfo;
	}
	
	public String getOriginalMessage() {
		return _originalMessage;
	}

	public void setOriginalMessage(String originalMessage) {
		_originalMessage = originalMessage;
	}
	
	private String _getTimestamp() {
		return _getDateFormat().format(new Date());
	}
	
	public DateFormat _getDateFormat() {
		return new SimpleDateFormat(TIMESTAMP_FORMAT);
	}

	private long _companyId;
	private long _userId;
	private String _userName;
	private String _classPK;
	private String _className;
	private String _type;
	private String _sessionID;
	private String _clientIP;
	private String _serverIP;
	private JSONObject _additionalInfo;
	private String _originalMessage;
	
	private Date _timestamp;
}
