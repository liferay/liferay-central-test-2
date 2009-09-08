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

import org.json.JSONException;
import org.json.JSONObject;

import com.liferay.portal.audit.events.AuditThreadLocal;
import com.liferay.portal.audit.messaging.AuditMessage;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.util.PortalUtil;

/**
 * <a href="UserAuditMessageBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mika Koivisto
 *
 */
public class UserAuditMessageBuilder {

	public static AuditMessage build(User user, boolean create) 
			throws JSONException {
		
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
		
		additionalInfo.put("userId", user.getUserId());
		additionalInfo.put("emailAddress", user.getEmailAddress());
		additionalInfo.put("screenName", user.getScreenName());
		additionalInfo.put("userName", user.getFullName());
		
		String type = create ? "user-create" : "user-remove";
		
		String clientIP = AuditThreadLocal.getClientIP();
		String serverIP = AuditThreadLocal.getServerIP();
		String sessionID = AuditThreadLocal.getSessionID();
		
		return new AuditMessage(companyId, modifierId, modifierUserName, 
				String.valueOf(user.getUserId()), User.class.getName(), type, 
				sessionID, clientIP, serverIP, additionalInfo);
	}
}
