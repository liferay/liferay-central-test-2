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
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <a href="RoleModificationAudit.java.html"><b><i>View Source</i></b></a> <p/>
 * <p> This class handles audit events for adding or removing Regular roles for
 * User, UserGroup, Community or Organization. </p>
 *
 * @author Mika Koivisto
 */
public class RoleModificationAudit extends BaseModelListener<Role> {

	public void onBeforeAddAssociation(
		Object classPK, String associationClassName,
		Object associationClassPK)
		throws ModelListenerException {

		auditRoleGrants(
			classPK, associationClassName, associationClassPK, true);
	}


	public void onBeforeCreate(Role role) throws ModelListenerException {
		auditRoleUpdate(role, true);
	}

	public void onBeforeRemove(Role role) throws ModelListenerException {
		auditRoleUpdate(role, false);
	}

	public void onBeforeUpdate(Role role) throws ModelListenerException {

		try {

			Role oldRole = RoleLocalServiceUtil.getRole(role.getRoleId());

			Collection<ProfileAttribute> attributes = getAttributes(
				role, oldRole);

			if (attributes.size() > 0) {

				AuditMessageSenderUtil.send(
					RoleModificationAuditMessageBuilder.build(
						role, attributes));
			}
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}

	}

	public void onBeforeRemoveAssociation(
		Object classPK, String associationClassName,
		Object associationClassPK)
		throws ModelListenerException {

		auditRoleGrants(
			classPK, associationClassName, associationClassPK, false);

	}

	protected Collection<ProfileAttribute> getAttributes(
		Role newRole,
		Role oldRole) {

		Collection<ProfileAttribute> attributes =
			new ArrayList<ProfileAttribute>();

		if (!newRole.getName().equals(oldRole.getName())) {
			attributes.add(
				new ProfileAttribute(
					"name", oldRole.getName(), newRole.getName()));
		}
		if (!newRole.getDescription().equals(oldRole.getDescription())) {
			attributes.add(
				new ProfileAttribute(
					"description", oldRole.getDescription(),
					newRole.getDescription()));
		}
		if (newRole.getType() != oldRole.getType()) {
			attributes.add(
				new ProfileAttribute(
					"type", String.valueOf(oldRole.getType()),
					String.valueOf(newRole.getType())));
		}
		if (!newRole.getSubtype().equals(oldRole.getSubtype())) {
			attributes.add(
				new ProfileAttribute(
					"subtype", oldRole.getSubtype(), newRole.getSubtype()));
		}

		return attributes;
	}

	private void auditRoleGrants(
		Object classPK, String associationClassName,
		Object associationClassPK, boolean grant)
		throws ModelListenerException {

		if (!User.class.getName().equals(associationClassName) &&
			!Group.class.getName().equals(associationClassName)) {
			return;
		}

		try {

			long associationClassId = ((Long)associationClassPK);
			long roleId = ((Long)classPK);

			if (Group.class.getName().equals(associationClassName)) {

				Group group = GroupLocalServiceUtil.getGroup(
					associationClassId);

				AuditMessageSenderUtil.send(
					RoleModificationAuditMessageBuilder.build(
						group.getClassPK(), group.getClassName(), roleId, grant));
			}
			else {

				AuditMessageSenderUtil.send(
					RoleModificationAuditMessageBuilder.build(
						associationClassId, associationClassName, roleId, grant));
			}
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	private void auditRoleUpdate(Role role, boolean created)
		throws ModelListenerException {
		try {

			AuditMessageSenderUtil.send(
				RoleModificationAuditMessageBuilder.build(
					role, created));
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

}
