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

package com.liferay.portal.audit.events.user;

import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.audit.events.AuditThreadLocal;
import com.liferay.portal.audit.messaging.AuditMessage;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

/**
 * <a href="UserGroupModificationAuditMessageBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mika Koivisto
 *
 */
public class UserGroupModificationAuditMessageBuilder {
	
	public static final String USERGROUP_ASSIGN_TYPE = "usergroup-assign";
	public static final String USERGROUP_CREATE_TYPE = "usergroup-create";
	public static final String USERGROUP_DELETE_TYPE = "usergroup-delete";
	public static final String USERGROUP_REMOVE_TYPE = "usergroup-remove";
	
	public static AuditMessage build(long classPK, String className, 
			long userGroupId, boolean grant) 
		throws PortalException, SystemException, JSONException {
		
		UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(userGroupId);
		
		JSONObject additionalInfo = new JSONObject();
		
		long companyId = CompanyThreadLocal.getCompanyId();
		long realUserId = AuditThreadLocal.getRealUserId();
		long userId = 0;
		long modifierId = 0;
		String modifierUserName = StringPool.BLANK;

		if (PrincipalThreadLocal.getName() != null) {
			userId = Long.parseLong(PrincipalThreadLocal.getName());
		}
		
		if (realUserId > 0 && userId != realUserId) {
			modifierId = realUserId;
			
			additionalInfo.put("doAsUserId", String.valueOf(userId));
			additionalInfo.put("doAsUserName", PortalUtil.getUserName(
					userId, StringPool.BLANK));
		}
		
		modifierUserName = PortalUtil.getUserName(modifierId, StringPool.BLANK);		
		
		additionalInfo.put("userGroupId", userGroupId);
		additionalInfo.put("userGroupName", userGroup.getName());

		String type = grant ? USERGROUP_ASSIGN_TYPE : 
			USERGROUP_REMOVE_TYPE;
		
		String clientIP = AuditThreadLocal.getClientIP();
		String serverIP = AuditThreadLocal.getServerIP();
		String sessionID = AuditThreadLocal.getSessionID();
		
		return new AuditMessage(companyId, modifierId, modifierUserName, 
				String.valueOf(classPK), className, type, sessionID, 
				clientIP, serverIP, additionalInfo);
	}

	public static AuditMessage build(UserGroup userGroup, boolean create) 
		throws PortalException, SystemException, JSONException {		
		
		JSONObject additionalInfo = new JSONObject();
		
		long companyId = CompanyThreadLocal.getCompanyId();
		long realUserId = AuditThreadLocal.getRealUserId();
		long userId = 0;
		long modifierId = 0;
		String modifierUserName = StringPool.BLANK;

		if (PrincipalThreadLocal.getName() != null) {
			userId = Long.parseLong(PrincipalThreadLocal.getName());
			modifierId = userId;
		}
		
		if (realUserId > 0 && userId != realUserId) {
			modifierId = realUserId;
			
			additionalInfo.put("doAsUserId", String.valueOf(userId));
			additionalInfo.put("doAsUserName", PortalUtil.getUserName(
					userId, StringPool.BLANK));
		}
		
		modifierUserName = PortalUtil.getUserName(modifierId, StringPool.BLANK);
		
		additionalInfo.put("userGroupId", userGroup.getUserGroupId());
		additionalInfo.put("userGroupName", userGroup.getName());

		String type = create ? USERGROUP_CREATE_TYPE : 
			USERGROUP_DELETE_TYPE;
		
		String clientIP = AuditThreadLocal.getClientIP();
		String serverIP = AuditThreadLocal.getServerIP();
		String sessionID = AuditThreadLocal.getSessionID();
		
		return new AuditMessage(companyId, modifierId, modifierUserName, 
				String.valueOf(userGroup.getUserGroupId()), 
				UserGroup.class.getName(), type, sessionID, clientIP, serverIP, 
				additionalInfo);	
	}

	public static AuditMessage build(UserGroup userGroup,
			Collection<ProfileAttribute> attributes) 
		throws PortalException, SystemException, JSONException {		

		JSONObject additionalInfo = new JSONObject();

		long companyId = CompanyThreadLocal.getCompanyId();
		long realUserId = AuditThreadLocal.getRealUserId();
		long userId = 0;
		long modifierId = 0;
		String modifierUserName = StringPool.BLANK;

		if (PrincipalThreadLocal.getName() != null) {
			userId = Long.parseLong(PrincipalThreadLocal.getName());
		}
		
		if (realUserId > 0 && userId != realUserId) {
			modifierId = realUserId;
			
			additionalInfo.put("doAsUserId", String.valueOf(userId));
			additionalInfo.put("doAsUserName", PortalUtil.getUserName(
					userId, StringPool.BLANK));
		}
		
		modifierUserName = PortalUtil.getUserName(modifierId, StringPool.BLANK);
		
		additionalInfo.put("attributes", getAttributesJSON(attributes));
		
		String clientIP = AuditThreadLocal.getClientIP();
		String serverIP = AuditThreadLocal.getServerIP();
		String sessionID = AuditThreadLocal.getSessionID();
		
		return new AuditMessage(companyId, modifierId, modifierUserName, 
				String.valueOf(userGroup.getUserGroupId()), 
				UserGroup.class.getName(), "usergroup-update", sessionID, 
				clientIP, serverIP, additionalInfo);	
	}
	
	protected static JSONArray getAttributesJSON(
			Collection<ProfileAttribute> attributes) 
		throws JSONException {
		
		JSONArray jsonObj = new JSONArray();

		for (ProfileAttribute attribute : attributes) {
			JSONObject attrObj = new JSONObject();

			attrObj.put("name", attribute.getName());
			attrObj.put("oldValue", attribute.getOldValue());
			attrObj.put("newValue", attribute.getNewValue());

			jsonObj.put(attrObj);
		}
		
		return jsonObj;
	}	
}
