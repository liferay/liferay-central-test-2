/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.messageboards.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.util.LocalizationUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * <a href="ConfigurationActionImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ConfigurationActionImpl implements ConfigurationAction {

	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (!cmd.equals(Constants.UPDATE)) {
			return;
		}

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		PortletPreferences prefs =
			PortletPreferencesFactoryUtil.getPortletSetup(
				actionRequest, portletResource);

		String tabs2 = ParamUtil.getString(actionRequest, "tabs2");

		if (tabs2.equals("anonymous-posting")) {
			updateAnonymousPosting(actionRequest, prefs);
		}
		else if (tabs2.equals("email-from")) {
			updateEmailFrom(actionRequest, prefs);
		}
		else if (tabs2.equals("message-added-email")) {
			updateEmailMessageAdded(actionRequest, prefs);
		}
		else if (tabs2.equals("message-updated-email")) {
			updateEmailMessageUpdated(actionRequest, prefs);
		}
		else if (tabs2.equals("ratings")) {
			updateRatings(actionRequest, prefs);
		}
		else if (tabs2.equals("rss")) {
			updateRSS(actionRequest, prefs);
		}
		else if (tabs2.equals("thread-priorities")) {
			updateThreadPriorities(actionRequest, prefs);
		}
		else if (tabs2.equals("user-ranks")) {
			updateUserRanks(actionRequest, prefs);
		}

		if (SessionErrors.isEmpty(actionRequest)) {
			prefs.store();

			SessionMessages.add(
				actionRequest, portletConfig.getPortletName() + ".doConfigure");
		}
	}

	public String render(
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return "/html/portlet/message_boards/configuration.jsp";
	}

	protected void updateAnonymousPosting(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		String allowAnonymousPosting = ParamUtil.getString(
			actionRequest, "allowAnonymousPosting");

		prefs.setValue("allow-anonymous-posting", allowAnonymousPosting);
	}

	protected void updateEmailFrom(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		String emailFromName = ParamUtil.getString(
			actionRequest, "emailFromName");
		String emailFromAddress = ParamUtil.getString(
			actionRequest, "emailFromAddress");
		boolean emailHtmlFormat = ParamUtil.getBoolean(
			actionRequest, "emailHtmlFormat");

		if (Validator.isNull(emailFromName)) {
			SessionErrors.add(actionRequest, "emailFromName");
		}
		else if (!Validator.isEmailAddress(emailFromAddress) &&
				 !Validator.isVariableTerm(emailFromAddress)) {

			SessionErrors.add(actionRequest, "emailFromAddress");
		}
		else {
			prefs.setValue("email-from-name", emailFromName);
			prefs.setValue("email-from-address", emailFromAddress);
			prefs.setValue(
				"email-html-format", String.valueOf(emailHtmlFormat));
		}
	}

	protected void updateEmailMessageAdded(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		boolean emailMessageAddedEnabled = ParamUtil.getBoolean(
			actionRequest, "emailMessageAddedEnabled");
		String emailMessageAddedSubjectPrefix = ParamUtil.getString(
			actionRequest, "emailMessageAddedSubjectPrefix");
		String emailMessageAddedBody = ParamUtil.getString(
			actionRequest, "emailMessageAddedBody");
		String emailMessageAddedSignature = ParamUtil.getString(
			actionRequest, "emailMessageAddedSignature");

		if (Validator.isNull(emailMessageAddedSubjectPrefix)) {
			SessionErrors.add(actionRequest, "emailMessageAddedSubjectPrefix");
		}
		else if (Validator.isNull(emailMessageAddedBody)) {
			SessionErrors.add(actionRequest, "emailMessageAddedBody");
		}
		else {
			prefs.setValue(
				"email-message-added-enabled",
				String.valueOf(emailMessageAddedEnabled));
			prefs.setValue(
				"email-message-added-subject-prefix",
				emailMessageAddedSubjectPrefix);
			prefs.setValue("email-message-added-body", emailMessageAddedBody);
			prefs.setValue(
				"email-message-added-signature", emailMessageAddedSignature);
		}
	}

	protected void updateEmailMessageUpdated(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		boolean emailMessageUpdatedEnabled = ParamUtil.getBoolean(
			actionRequest, "emailMessageUpdatedEnabled");
		String emailMessageUpdatedSubjectPrefix = ParamUtil.getString(
			actionRequest, "emailMessageUpdatedSubjectPrefix");
		String emailMessageUpdatedBody = ParamUtil.getString(
			actionRequest, "emailMessageUpdatedBody");
		String emailMessageUpdatedSignature = ParamUtil.getString(
			actionRequest, "emailMessageUpdatedSignature");

		if (Validator.isNull(emailMessageUpdatedSubjectPrefix)) {
			SessionErrors.add(
				actionRequest, "emailMessageUpdatedSubjectPrefix");
		}
		else if (Validator.isNull(emailMessageUpdatedBody)) {
			SessionErrors.add(actionRequest, "emailMessageUpdatedBody");
		}
		else {
			prefs.setValue(
				"email-message-updated-enabled",
				String.valueOf(emailMessageUpdatedEnabled));
			prefs.setValue(
				"email-message-updated-subject-prefix",
				emailMessageUpdatedSubjectPrefix);
			prefs.setValue(
				"email-message-updated-body", emailMessageUpdatedBody);
			prefs.setValue(
				"email-message-updated-signature",
				emailMessageUpdatedSignature);
		}
	}

	protected void updateRatings(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		boolean enableMessageRatings = ParamUtil.getBoolean(
			actionRequest, "enableMessageRatings");

		prefs.setValue(
			"enable-message-ratings", String.valueOf(enableMessageRatings));
	}

	protected void updateRSS(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		int rssDelta = ParamUtil.getInteger(actionRequest, "rssDelta");
		String rssDisplayStyle = ParamUtil.getString(
			actionRequest, "rssDisplayStyle");
		String rssFormat = ParamUtil.getString(actionRequest, "rssFormat");

		prefs.setValue("rss-delta", String.valueOf(rssDelta));
		prefs.setValue("rss-display-style", rssDisplayStyle);
		prefs.setValue("rss-format", rssFormat);
	}

	protected void updateThreadPriorities(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (int i = 0; i < locales.length; i++) {
			String languageId = LocaleUtil.toLanguageId(locales[i]);

			List<String> priorities = new ArrayList<String>();

			for (int j = 0; j < 10; j++) {
				String name = ParamUtil.getString(
					actionRequest, "priorityName" + j + "_" + languageId);
				String image = ParamUtil.getString(
					actionRequest, "priorityImage" + j + "_" + languageId);
				double value = ParamUtil.getDouble(
					actionRequest, "priorityValue" + j + "_" + languageId);

				if (Validator.isNotNull(name) || Validator.isNotNull(image) ||
					(value != 0.0)) {

					priorities.add(
						name + StringPool.COMMA + image + StringPool.COMMA +
							value);
				}
			}

			LocalizationUtil.setPrefsValues(
				prefs, "priorities", languageId,
				priorities.toArray(new String[priorities.size()]));
		}
	}

	protected void updateUserRanks(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (int i = 0; i < locales.length; i++) {
			String languageId = LocaleUtil.toLanguageId(locales[i]);

			String[] ranks = StringUtil.split(
				ParamUtil.getString(actionRequest, "ranks_" + languageId),
				StringPool.NEW_LINE);

			Map<String, String> map = new TreeMap<String, String>();

			for (int j = 0; j < ranks.length; j++) {
				String[] kvp = StringUtil.split(ranks[j], StringPool.EQUAL);

				String kvpName = kvp[0];
				String kvpValue = kvp[1];

				map.put(kvpValue, kvpName);
			}

			ranks = new String[map.size()];

			int count = 0;

			Iterator<Map.Entry<String, String>> itr =
				map.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, String> entry = itr.next();

				String kvpValue = entry.getKey();
				String kvpName = entry.getValue();

				ranks[count++] = kvpName + StringPool.EQUAL + kvpValue;
			}

			LocalizationUtil.setPrefsValues(prefs, "ranks", languageId, ranks);
		}
	}

}