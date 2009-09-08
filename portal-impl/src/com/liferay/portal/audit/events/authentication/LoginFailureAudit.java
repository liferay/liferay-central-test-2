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

import com.liferay.portal.audit.messaging.AuditMessageSenderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AuthException;
import com.liferay.portal.security.auth.AuthFailure;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

/**
 * <a href="LoginFailureAudit.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Mika Koivisto
 *
 */
public class LoginFailureAudit implements AuthFailure  {

	public void onFailureByEmailAddress(long companyId, String emailAddress,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap) 
		throws AuthException {
		
		try {
			User user = UserLocalServiceUtil.getUserByEmailAddress(
				companyId, emailAddress);
			
			long userId = user.getUserId();
			String userName = PortalUtil.getUserName(userId, StringPool.BLANK);
			
			AuditMessageSenderUtil.send(
					LoginFailureAuditMessageBuilder.build(companyId, userId, 
							userName, headerMap,
							LoginFailureAuditMessageBuilder.REASON_EMAIL));
		}
		catch (Exception e) {
			throw new AuthException(e);
		}
	}

	public void onFailureByScreenName(long companyId, String screenName,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException {
		
		try {
			User user = UserLocalServiceUtil.getUserByScreenName(
				companyId, screenName);
			
			long userId = user.getUserId();
			String userName = PortalUtil.getUserName(userId, StringPool.BLANK);
			
			AuditMessageSenderUtil.send(
					LoginFailureAuditMessageBuilder.build(companyId, userId, 
							userName, headerMap,
							LoginFailureAuditMessageBuilder.REASON_SCREENNAME));
		}
		catch (Exception e) {
			throw new AuthException(e);
		}
	}

	public void onFailureByUserId(long companyId, long userId,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException {
		
		try {
			
			String userName = PortalUtil.getUserName(userId, StringPool.BLANK);
			
			AuditMessageSenderUtil.send(
					LoginFailureAuditMessageBuilder.build(companyId, userId, 
							userName, headerMap,
							LoginFailureAuditMessageBuilder.REASON_USERID));
		}
		catch (Exception e) {
			throw new AuthException(e);
		}
	}

}
