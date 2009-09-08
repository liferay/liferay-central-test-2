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

package com.liferay.portal.audit.events;

import javax.servlet.http.HttpServletRequest;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

/**
 * <a href="AuditThreadLocal.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mika Koivisto
 *
 */
public class AuditThreadLocal {
	
	public static String getClientIP() {
		String clientIP = null;
		
		if (_threadLocal.get() != null) {
			clientIP = _threadLocal.get().getRemoteAddr();
		}
 		
		if (_log.isDebugEnabled()) {
			_log.debug("getClientIP " + clientIP);
		}

		return clientIP;
	}
	
	public static long getRealUserId() {
		long realUserId = 0;
		
		if (_threadLocal.get() != null) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_threadLocal.get().getAttribute(WebKeys.THEME_DISPLAY);
			realUserId = themeDisplay.getRealUserId();
		}
		
		if (_log.isDebugEnabled()) {
			_log.debug("getRealUserId " + realUserId);
		}
		
		return realUserId;
	}
	
	public static String getServerIP() {
		String serverIP = null;
		if (_threadLocal.get() != null) {	
			serverIP = _threadLocal.get().getServerName();
		}
		
		if (_log.isDebugEnabled()) {
			_log.debug("getServerIP " + serverIP);
		}

		return serverIP;
	}
	
	public static String getSessionID() {
		String sessionID = null;
		if (_threadLocal.get() != null) {
			sessionID = _threadLocal.get().getSession().getId();
		}
		
		if (_log.isDebugEnabled()) {
			_log.debug("getSessionID " + sessionID);
		}

		return sessionID;
	}	

	public static void setHttpServletRequest(HttpServletRequest req) {
		if (_log.isDebugEnabled()) {
			_log.debug("setHttpServletRequest " + req);
		}

		_threadLocal.set(req);
	}

	private static Log _log = LogFactoryUtil.getLog(AuditThreadLocal.class);

	private static ThreadLocal<HttpServletRequest> _threadLocal = new ThreadLocal<HttpServletRequest>();
}
