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
import com.liferay.portal.model.Contact;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.ContactLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <a href="UserContactModificationAudit.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Mika Koivisto
 */
public class UserContactModificationAudit extends BaseModelListener<Contact> {

	public void onBeforeUpdate(Contact newContact)
		throws ModelListenerException {

		try {

			Contact oldContact = ContactLocalServiceUtil.getContact(
				newContact.getContactId());

			Collection<ProfileAttribute> attributes = getChangedAttributes(
				newContact, oldContact);

			if (attributes.size() > 0) {
				AuditMessageSenderUtil.send(
					UserProfileModificationAuditMessageBuilder.build(
						newContact.getUserId(), attributes));
			}
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}

	}

	protected Collection<ProfileAttribute> getChangedAttributes(
		Contact newContact,
		Contact oldContact) {
		Collection<ProfileAttribute> attributes =
			new ArrayList<ProfileAttribute>();


		if (!newContact.getFirstName().equals(oldContact.getFirstName())) {
			attributes.add(
				new ProfileAttribute(
					"firstname",
					oldContact.getFirstName(), newContact.getFirstName()));
		}
		if (!newContact.getMiddleName().equals(oldContact.getMiddleName())) {
			attributes.add(
				new ProfileAttribute(
					"middleName",
					oldContact.getMiddleName(), newContact.getMiddleName()));
		}
		if (!newContact.getLastName().equals(oldContact.getLastName())) {
			attributes.add(
				new ProfileAttribute(
					"lastname",
					oldContact.getLastName(), newContact.getLastName()));
		}
		if (newContact.isMale() != oldContact.isMale()) {
			attributes.add(
				new ProfileAttribute(
					"gender",
					(oldContact.isMale() ? "male" : "female"),
					(newContact.isMale() ? "male" : "female")));
		}
		if (!newContact.getBirthday().equals(oldContact.getBirthday())) {
			attributes.add(
				new ProfileAttribute(
					"birthday",
					String.valueOf(oldContact.getBirthday().toString()),
					String.valueOf(newContact.getBirthday().toString())));
		}
		if (!newContact.getJobTitle().equals(oldContact.getJobTitle())) {
			attributes.add(
				new ProfileAttribute(
					"jobTitle",
					oldContact.getJobTitle(), newContact.getJobTitle()));
		}
		if (newContact.getPrefixId() != oldContact.getPrefixId()) {
			attributes.add(
				new ProfileAttribute(
					"prefixId",
					String.valueOf(oldContact.getPrefixId()),
					String.valueOf(newContact.getPrefixId())));
		}
		if (newContact.getSuffixId() != oldContact.getSuffixId()) {
			attributes.add(
				new ProfileAttribute(
					"suffixId",
					String.valueOf(oldContact.getSuffixId()),
					String.valueOf(newContact.getSuffixId())));
		}
		if (!newContact.getAimSn().equals(oldContact.getAimSn())) {
			attributes.add(
				new ProfileAttribute(
					"aimSn",
					oldContact.getAimSn(), newContact.getAimSn()));
		}
		if (!newContact.getFacebookSn().equals(oldContact.getFacebookSn())) {
			attributes.add(
				new ProfileAttribute(
					"facebookSn",
					oldContact.getFacebookSn(), newContact.getFacebookSn()));
		}
		if (!newContact.getIcqSn().equals(oldContact.getIcqSn())) {
			attributes.add(
				new ProfileAttribute(
					"icqSn",
					oldContact.getIcqSn(), newContact.getIcqSn()));
		}
		if (!newContact.getJabberSn().equals(oldContact.getJabberSn())) {
			attributes.add(
				new ProfileAttribute(
					"jabberSn",
					oldContact.getJabberSn(), newContact.getJabberSn()));
		}
		if (!newContact.getMsnSn().equals(oldContact.getMsnSn())) {
			attributes.add(
				new ProfileAttribute(
					"msnSn",
					oldContact.getMsnSn(), newContact.getMsnSn()));
		}
		if (!newContact.getMySpaceSn().equals(oldContact.getMySpaceSn())) {
			attributes.add(
				new ProfileAttribute(
					"myspaceSn",
					oldContact.getMySpaceSn(), newContact.getMySpaceSn()));
		}
		if (!newContact.getSkypeSn().equals(oldContact.getSkypeSn())) {
			attributes.add(
				new ProfileAttribute(
					"skypeSn",
					oldContact.getSkypeSn(), newContact.getSkypeSn()));
		}
		if (!newContact.getSmsSn().equals(oldContact.getSmsSn())) {
			attributes.add(
				new ProfileAttribute(
					"smsSn",
					oldContact.getSmsSn(), newContact.getSmsSn()));
		}
		if (!newContact.getTwitterSn().equals(oldContact.getTwitterSn())) {
			attributes.add(
				new ProfileAttribute(
					"twitterSn",
					oldContact.getTwitterSn(), newContact.getTwitterSn()));
		}
		if (!newContact.getYmSn().equals(oldContact.getYmSn())) {
			attributes.add(
				new ProfileAttribute(
					"ymSn",
					oldContact.getYmSn(), newContact.getYmSn()));
		}
		if (!newContact.getEmployeeNumber().equals(
			oldContact.getEmployeeNumber())) {
			attributes.add(
				new ProfileAttribute(
					"employeeNumber",
					oldContact.getEmployeeNumber(),
					newContact.getEmployeeNumber()));
		}
		if (!newContact.getEmployeeStatusId().equals(
			oldContact.getEmployeeStatusId())) {
			attributes.add(
				new ProfileAttribute(
					"employeeStatusId",
					oldContact.getEmployeeStatusId(),
					newContact.getEmployeeStatusId()));
		}
		if (!newContact.getHoursOfOperation().equals(
			oldContact.getHoursOfOperation())) {
			attributes.add(
				new ProfileAttribute(
					"hoursOfOperation",
					oldContact.getHoursOfOperation(),
					newContact.getHoursOfOperation()));
		}

		return attributes;
	}
}
