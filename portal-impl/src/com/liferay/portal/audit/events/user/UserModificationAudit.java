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
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <a href="UserModificationAudit.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mika Koivisto
 */
public class UserModificationAudit extends BaseModelListener<User> {

	public void onBeforeCreate(User user) throws ModelListenerException {
		auditUserUpdate(user, true);
	}

	public void onBeforeRemove(User user) throws ModelListenerException {
		auditUserUpdate(user, false);
	}

	public void onBeforeUpdate(User newUser) throws ModelListenerException {

		try {

			User oldUser = UserLocalServiceUtil.getUser(newUser.getUserId());

			Collection<ProfileAttribute> attributes = getChangedAttributes(
				newUser, oldUser);

			if (attributes.size() > 0) {
				AuditMessageSenderUtil.send(
					UserProfileModificationAuditMessageBuilder.build(
						newUser.getUserId(), attributes));
			}
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected Collection<ProfileAttribute> getChangedAttributes(
		User newUser,
		User oldUser) {
		Collection<ProfileAttribute> attributes =
			new ArrayList<ProfileAttribute>();


		if (!newUser.getEmailAddress().equals(oldUser.getEmailAddress())) {
			attributes.add(
				new ProfileAttribute(
					"emailAddress", oldUser.getEmailAddress(),
					newUser.getEmailAddress()));
		}
		if (!newUser.getScreenName().equals(oldUser.getScreenName())) {
			attributes.add(
				new ProfileAttribute(
					"screenName", oldUser.getScreenName(),
					newUser.getScreenName()));
		}
		if (newUser.getPasswordModified()) {
			attributes.add(
				new ProfileAttribute(
					"password", StringPool.BLANK, StringPool.BLANK));
		}
		if (newUser.getAgreedToTermsOfUse() !=
			oldUser.getAgreedToTermsOfUse()) {
			attributes.add(
				new ProfileAttribute(
					"agreedToTermsOfUse",
					String.valueOf(oldUser.getAgreedToTermsOfUse()),
					String.valueOf(newUser.getAgreedToTermsOfUse())));
		}
		if (!newUser.getComments().equals(oldUser.getComments())) {
			attributes.add(
				new ProfileAttribute(
					"comments",
					oldUser.getComments(), newUser.getComments()));
		}
		if (!newUser.getLanguageId().equals(oldUser.getLanguageId())) {
			attributes.add(
				new ProfileAttribute(
					"languageId",
					oldUser.getLanguageId(), newUser.getLanguageId()));
		}
		if (!newUser.getReminderQueryQuestion().equals(
			oldUser.getReminderQueryQuestion())) {
			attributes.add(
				new ProfileAttribute(
					"reminderQueryQuestion",
					oldUser.getReminderQueryQuestion(),
					newUser.getReminderQueryQuestion()));
		}
		if (!newUser.getReminderQueryAnswer().equals(
			oldUser.getReminderQueryAnswer())) {
			attributes.add(
				new ProfileAttribute(
					"reminderQueryAnswer",
					oldUser.getReminderQueryAnswer(),
					newUser.getReminderQueryAnswer()));
		}
		if (!newUser.getTimeZoneId().equals(oldUser.getTimeZoneId())) {
			attributes.add(
				new ProfileAttribute(
					"timeZoneId",
					oldUser.getTimeZoneId(), newUser.getTimeZoneId()));
		}
		if (newUser.getActive() != oldUser.getActive()) {
			attributes.add(
				new ProfileAttribute(
					"active",
					String.valueOf(oldUser.getActive()),
					String.valueOf(newUser.getActive())));

		}

		return attributes;
	}

	private void auditUserUpdate(User user, boolean created)
		throws ModelListenerException {

		try {
			AuditMessageSenderUtil.send(
				UserAuditMessageBuilder.build(user, created));

		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}


}
