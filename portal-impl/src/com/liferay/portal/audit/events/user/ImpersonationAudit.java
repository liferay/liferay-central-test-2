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

import com.liferay.portal.audit.messaging.AuditMessage;
import com.liferay.portal.audit.messaging.AuditMessageSenderUtil;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <a href="ImpersonationAudit.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mika Koivisto
 *
 */
public class ImpersonationAudit extends Action {

	public void run(HttpServletRequest request, HttpServletResponse response)
			throws ActionException {
		
		long plid = ParamUtil.getLong(request, "p_l_id");
		
		if (plid <= 0) {
			return;
		}
		
		ThemeDisplay themeDisplay =
			(ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);

		long companyId = PortalUtil.getCompanyId(request);
		long userId = themeDisplay.getRealUserId();
		long impersonatedUserId = themeDisplay.getUserId();
		String impersonatedUserName = PortalUtil.getUserName(
			impersonatedUserId, StringPool.BLANK);
		String doAsUserId = themeDisplay.getDoAsUserId();
		String userName = PortalUtil.getUserName(userId, StringPool.BLANK);

		String clientIP = request.getRemoteAddr();
		String serverIP = request.getServerName();

		HttpSession session = request.getSession();
		String sessionID = session.getId();

		if (doAsUserId != null && userId != impersonatedUserId) {
			if (session.getAttribute(_IMPERSONATING_FLAG) == null) {

				session.setAttribute(
					_IMPERSONATING_FLAG, StringPool.TRUE);
				
				try {
					JSONObject additionalInfo = new JSONObject();
					additionalInfo.put("userId", impersonatedUserId);
					additionalInfo.put("userName", impersonatedUserName);
					
					AuditMessage message = new AuditMessage(
						companyId, userId, userName, String.valueOf(impersonatedUserId),
						User.class.getName(), "impersonate", sessionID,
						clientIP, serverIP, additionalInfo);
				
					AuditMessageSenderUtil.send(message);
				} 
				catch (JSONException e) {
					throw new ActionException(e);
				}
			}
		}
		else if (session.getAttribute(_IMPERSONATING_FLAG) != null) {
			// Impersonation ended
			session.removeAttribute(_IMPERSONATING_FLAG);
			
			if (_log.isDebugEnabled()) {
			    _log.debug("Impersonalization ended");
			}
		}
	}
	
	private static final String _IMPERSONATING_FLAG = "audit.impersonating";
	private static final Log _log =
		LogFactoryUtil.getLog(ImpersonationAudit.class);
}
