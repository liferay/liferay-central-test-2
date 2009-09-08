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

package com.liferay.portal.audit.events.authentication;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.liferay.portal.audit.events.AuditThreadLocal;
import com.liferay.portal.audit.messaging.AuditMessage;
import com.liferay.portal.kernel.util.StringPool;

/**
 * <a href="LoginFailureAuditMessageBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mika Koivisto
 *
 */
public class LoginFailureAuditMessageBuilder {
	
	public static final String REASON_EMAIL = "tried to login by email";

	public static final String REASON_USERID = "tried to login by userId";
	
	public static final String REASON_SCREENNAME = 
		"tried to login by screen name";
	
	public static AuditMessage build(long companyId, long userId, 
			String userName, Map<String, String[]> headerMap, String reason) 
		throws JSONException {

		
		JSONObject additionalInfo = new JSONObject();
		additionalInfo.put("reason", reason);
		additionalInfo.put("headers", headerMap);

		String clientIP = AuditThreadLocal.getClientIP();
		String serverIP = AuditThreadLocal.getServerIP();
		String sessionID = AuditThreadLocal.getSessionID();
		
		return new AuditMessage(companyId, userId, userName, StringPool.BLANK, 
				StringPool.BLANK, "login-failure", sessionID, clientIP, 
				serverIP, additionalInfo);
	}

}
