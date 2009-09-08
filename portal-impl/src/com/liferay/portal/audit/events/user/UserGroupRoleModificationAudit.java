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

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.audit.messaging.AuditMessageSenderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.util.PortalUtil;

/**
 * <a href="UserGroupRoleModificationAudit.java.html"><b><i>View Source</i></b></a>
 * 
 * <p>
 * This class handles audit events for adding or removing Organization and 
 * Community scoped roles.
 * </p>
 *
 * @author Mika Koivisto
 *
 */
public class UserGroupRoleModificationAudit extends
		BaseModelListener<UserGroupRole> {
	

	public void onBeforeCreate(UserGroupRole userGroupRole)
			throws ModelListenerException {

		auditGroupRoleChange(userGroupRole, true);
	}

	public void onBeforeRemove(UserGroupRole userGroupRole)
		throws ModelListenerException {
		
		auditGroupRoleChange(userGroupRole, false);

	}

	private void auditGroupRoleChange(
		UserGroupRole userGroupRole, boolean created)
		throws ModelListenerException {

		try {

			long roleId = userGroupRole.getRoleId();
			String scopeClassName = userGroupRole.getGroup().getClassName();
			long scopeClassId = userGroupRole.getGroup().getClassPK();

			AuditMessageSenderUtil.send(
					RoleModificationAuditMessageBuilder.build(
							userGroupRole.getUserId(), User.class.getName(),
							roleId, scopeClassId, scopeClassName, created));
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}
}
