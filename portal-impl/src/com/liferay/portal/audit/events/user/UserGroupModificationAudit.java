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

import java.util.ArrayList;
import java.util.Collection;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.audit.messaging.AuditMessageSenderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

/**
 * <a href="UserGroupModificationAudit.java.html"><b><i>View Source</i></b></a>
 * 
 * <p>
 * This class handles audit events for adding or removing User Groups and 
 * associating UserGroup with a User or Community
 * </p>
 * 
 * @author Mika Koivisto
 *
 */
public class UserGroupModificationAudit extends BaseModelListener<UserGroup> {

	public void onBeforeAddAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {
		
		if (!User.class.getName().equals(associationClassName) &&
			!Group.class.getName().equals(associationClassName)) {
			return;
		}
		
		try {

			long associationClassId = ((Long)associationClassPK).longValue();			
			long userGroupId = ((Long)classPK).longValue();
			
			if (Group.class.getName().equals(associationClassName)) {
				
				Group group = GroupLocalServiceUtil.getGroup(associationClassId);
				
				AuditMessageSenderUtil.send(
						UserGroupModificationAuditMessageBuilder.build(
								group.getClassPK(), group.getClassName(), 
								userGroupId, true));
			}
			else {
				
				AuditMessageSenderUtil.send(
						UserGroupModificationAuditMessageBuilder.build(
								associationClassId, associationClassName, 
								userGroupId, true));				
			}
		} 
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}
	
	public void onBeforeCreate(UserGroup userGroup) throws ModelListenerException {

		try {
			
			AuditMessageSenderUtil.send(
					UserGroupModificationAuditMessageBuilder.build(
							userGroup, true));		
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	public void onBeforeRemove(UserGroup userGroup) throws ModelListenerException {
		
		try {
			
			AuditMessageSenderUtil.send(
					UserGroupModificationAuditMessageBuilder.build(
							userGroup, false));		
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	public void onBeforeUpdate(UserGroup userGroup) throws ModelListenerException {

		try {
			
			UserGroup oldUserGroup = UserGroupLocalServiceUtil.getUserGroup(
					userGroup.getUserGroupId());
			
			Collection<ProfileAttribute> attributes = getAttributes(
					userGroup, oldUserGroup);
						
			if (attributes.size() > 0) {
				
					AuditMessageSenderUtil.send(
						UserGroupModificationAuditMessageBuilder.build(
								userGroup, attributes));
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

		if (!User.class.getName().equals(associationClassName) &&
			!Group.class.getName().equals(associationClassName)) {
			return;
		}

		try {

			long associationClassId = ((Long)associationClassPK).longValue();			
			long userGroupId = ((Long)classPK).longValue();
			
			if (Group.class.getName().equals(associationClassName)) {
				
				Group group = GroupLocalServiceUtil.getGroup(associationClassId);
				
				AuditMessageSenderUtil.send(
						UserGroupModificationAuditMessageBuilder.build(
								group.getClassPK(), group.getClassName(), 
								userGroupId, true));
			}
			else {
				
				AuditMessageSenderUtil.send(
						UserGroupModificationAuditMessageBuilder.build(
								associationClassId, associationClassName, 
								userGroupId, false));				
			}
		} 
		catch (Exception e) {
			throw new ModelListenerException(e);
		}	
	}
	
	protected Collection<ProfileAttribute> getAttributes(UserGroup newUserGroup, 
			UserGroup oldUserGroup) {
		
		Collection<ProfileAttribute> attributes = new ArrayList<ProfileAttribute>();
		
		if (!newUserGroup.getName().equals(oldUserGroup.getName())) {
			attributes.add(new ProfileAttribute(
					"name", oldUserGroup.getName(), newUserGroup.getName()));
		}
		if (!newUserGroup.getDescription().equals(oldUserGroup.getDescription())) {
			attributes.add(new ProfileAttribute(
					"description", oldUserGroup.getDescription(), 
					newUserGroup.getDescription()));			
		}
		
		return attributes;
	}
}
