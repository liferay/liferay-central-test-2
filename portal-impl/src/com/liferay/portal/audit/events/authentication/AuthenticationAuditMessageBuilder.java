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

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;

import com.liferay.portal.audit.messaging.AuditMessage;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;

/**
 * <a href="AuthenticationAuditMessageBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mika Koivisto
 *
 */
public class AuthenticationAuditMessageBuilder {

	public static AuditMessage build(HttpServletRequest req, String type)
		throws JSONException {
		long companyId = PortalUtil.getCompanyId(req);
		long userId = PortalUtil.getUserId(req);
		String userName = PortalUtil.getUserName(userId, StringPool.BLANK);
		String sessionID = req.getSession().getId();
		String clientIP = req.getRemoteAddr();
		String serverIP = req.getServerName();
		
		return new AuditMessage(
			companyId, userId, userName, StringPool.BLANK, StringPool.BLANK, 
			type, sessionID, clientIP, serverIP);
	}
}
