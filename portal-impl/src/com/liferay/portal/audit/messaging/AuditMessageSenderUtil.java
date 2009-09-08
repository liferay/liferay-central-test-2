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

import org.json.JSONException;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBusUtil;

/**
 * <a href="AuditMessageSenderUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mika Koivisto
 */
public class AuditMessageSenderUtil {

	public static void send(AuditMessage msg) {

		try {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Sending audit message to " + _DESTINATION_NAME
					+ ": " + msg.toString().toString());
			}

			MessageBusUtil.sendMessage(
				_DESTINATION_NAME, msg.toJSONObject().toString());
		}
		catch (JSONException e) {
			throw new IllegalArgumentException(
				"Unable to convert message to JSON", e);
		}
	}

	private static final String _DESTINATION_NAME = "liferay/audit";
	private static final Log _log = LogFactoryUtil.getLog(
		AuditMessageSenderUtil.class);
}
