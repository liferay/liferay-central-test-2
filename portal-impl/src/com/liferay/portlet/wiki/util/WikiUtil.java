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

package com.liferay.portlet.wiki.util;

import com.germinus.easyconf.Filter;

import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.wiki.PageContentException;
import com.liferay.portlet.wiki.WikiFormatException;
import com.liferay.portlet.wiki.engines.WikiEngine;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.util.Http;

import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;

/**
 * <a href="WikiUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 *
 */
public class WikiUtil {

	public static final String POP_PORTLET_PREFIX = "wiki.";

	public static String convert(
			WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
			String attachmentURLPrefix)
		throws PageContentException, WikiFormatException {

		return _instance._convert(
			page, viewPageURL, editPageURL, attachmentURLPrefix);
	}

	public static String getEditPage(String format) {
		return _instance._getEditPage(format);
	}

	public static String getEmailFromAddress(PortletPreferences prefs) {
		String emailFromAddress = PropsUtil.get(
			PropsUtil.MESSAGE_BOARDS_EMAIL_FROM_ADDRESS);

		return prefs.getValue("email-from-address", emailFromAddress);
	}

	public static String getEmailFromName(PortletPreferences prefs) {
		String emailFromName = PropsUtil.get(
			PropsUtil.MESSAGE_BOARDS_EMAIL_FROM_NAME);

		return prefs.getValue("email-from-name", emailFromName);
	}

	public static boolean getEmailPageAddedEnabled(
		PortletPreferences prefs) {

		String emailPageAddedEnabled = prefs.getValue(
			"email-page-added-enabled", StringPool.BLANK);

		if (Validator.isNotNull(emailPageAddedEnabled)) {
			return GetterUtil.getBoolean(emailPageAddedEnabled);
		}
		else {
			return GetterUtil.getBoolean(PropsUtil.get(
				PropsUtil.WIKI_EMAIL_PAGE_ADDED_ENABLED));
		}
	}

	public static String getEmailPageAddedBody(PortletPreferences prefs)
		throws IOException {

		String emailPageAddedBody = prefs.getValue(
			"email-page-added-body", StringPool.BLANK);

		if (Validator.isNotNull(emailPageAddedBody)) {
			return emailPageAddedBody;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsUtil.WIKI_EMAIL_PAGE_ADDED_BODY));
		}
	}

	public static String getEmailPageAddedSignature(PortletPreferences prefs)
		throws IOException {

		String emailPageAddedSignature = prefs.getValue(
			"email-page-added-signature", StringPool.BLANK);

		if (Validator.isNotNull(emailPageAddedSignature)) {
			return emailPageAddedSignature;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsUtil.WIKI_EMAIL_PAGE_ADDED_SIGNATURE));
		}
	}

	public static String getEmailPageAddedSubjectPrefix(
			PortletPreferences prefs)
		throws IOException {

		String emailPageAddedSubjectPrefix = prefs.getValue(
			"email-page-added-subject-prefix", StringPool.BLANK);

		if (Validator.isNotNull(emailPageAddedSubjectPrefix)) {
			return emailPageAddedSubjectPrefix;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsUtil.WIKI_EMAIL_PAGE_ADDED_SUBJECT_PREFIX));
		}
	}

	public static boolean getEmailPageUpdatedEnabled(
		PortletPreferences prefs) {

		String emailPageUpdatedEnabled = prefs.getValue(
			"email-page-updated-enabled", StringPool.BLANK);

		if (Validator.isNotNull(emailPageUpdatedEnabled)) {
			return GetterUtil.getBoolean(emailPageUpdatedEnabled);
		}
		else {
			return GetterUtil.getBoolean(PropsUtil.get(
				PropsUtil.WIKI_EMAIL_PAGE_UPDATED_ENABLED));
		}
	}

	public static String getEmailPageUpdatedBody(PortletPreferences prefs)
		throws IOException {

		String emailPageUpdatedBody = prefs.getValue(
			"email-page-updated-body", StringPool.BLANK);

		if (Validator.isNotNull(emailPageUpdatedBody)) {
			return emailPageUpdatedBody;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsUtil.WIKI_EMAIL_PAGE_UPDATED_BODY));
		}
	}

	public static String getEmailPageUpdatedSignature(
			PortletPreferences prefs)
		throws IOException {

		String emailPageUpdatedSignature = prefs.getValue(
			"email-page-updated-signature", StringPool.BLANK);

		if (Validator.isNotNull(emailPageUpdatedSignature)) {
			return emailPageUpdatedSignature;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsUtil.WIKI_EMAIL_PAGE_UPDATED_SIGNATURE));
		}
	}

	public static String getEmailPageUpdatedSubjectPrefix(
			PortletPreferences prefs)
		throws IOException {

		String emailPageUpdatedSubject = prefs.getValue(
			"email-page-updated-subject-prefix", StringPool.BLANK);

		if (Validator.isNotNull(emailPageUpdatedSubject)) {
			return emailPageUpdatedSubject;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsUtil.WIKI_EMAIL_PAGE_UPDATED_SUBJECT_PREFIX));
		}
	}

	public static String getHelpPage(String format) {
		return _instance._getHelpPage(format);
	}

	public static String getHelpURL(String format) {
		return _instance._getHelpURL(format);
	}

	public static Map getLinks(WikiPage page)
		throws PageContentException, WikiFormatException {

		return _instance._getLinks(page);
	}

	public static boolean isLinkedTo(WikiPage page, String title)
		throws PageContentException, WikiFormatException {

		return _instance._isLinkedTo(page, title);
	}

	public static String getMailId(String mx, long nodeId, long pageId) {
		StringMaker sm = new StringMaker();

		sm.append(StringPool.LESS_THAN);
		sm.append(POP_PORTLET_PREFIX);
		sm.append(nodeId);
		sm.append(StringPool.PERIOD);
		sm.append(pageId);
		sm.append(StringPool.AT);
		sm.append(PropsValues.POP_SERVER_SUBDOMAIN);
		sm.append(StringPool.PERIOD);
		sm.append(mx);
		sm.append(StringPool.GREATER_THAN);

		return sm.toString();
	}

	public static boolean validate(
			long nodeId, String content, String format)
		throws WikiFormatException {

		return _instance._validate(nodeId, content, format);
	}

	private String _convert(
			WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
			String attachmentURLPrefix)
		throws PageContentException, WikiFormatException {

		WikiEngine engine = _getEngine(page.getFormat());

		String content = engine.convert(page, editPageURL);

		if ((editPageURL != null) &&
			(editPageURL instanceof LiferayPortletURL)) {

			LiferayPortletURL liferayPageURL = (LiferayPortletURL)viewPageURL;

			liferayPageURL.setParameter(
				"nodeId", String.valueOf(page.getNodeId()));

			LiferayPortletURL liferayEditURL = (LiferayPortletURL)editPageURL;

			liferayEditURL.setParameter(
				"nodeId", String.valueOf(page.getNodeId()));

			Iterator itr = engine.getOutgoingLinks(page).keySet().iterator();

			while (itr.hasNext()) {
				String title = (String)itr.next();

				content = _replaceLinks(
					title, content, liferayPageURL, liferayEditURL);
			}

			content = _replaceAttachments(
				content, page.getTitle(), attachmentURLPrefix);
		}

		return content;
	}

	private String _getEditPage(String format) {
		return PropsUtil.getComponentProperties().getString(
			PropsUtil.WIKI_FORMATS_EDIT_PAGE, Filter.by(format));
	}

	private WikiEngine _getEngine(String format) throws WikiFormatException {
		WikiEngine engine = (WikiEngine)_engines.get(format);

		if (engine == null) {
			try {
				String engineClass =
					PropsUtil.getComponentProperties().getString(
						PropsUtil.WIKI_FORMATS_ENGINE, Filter.by(format));

				if (engineClass != null) {
					if (!InstancePool.contains(engineClass)) {
						engine = (WikiEngine)InstancePool.get(engineClass);

						engine.setMainConfiguration(
							_readConfigurationFile(
								PropsUtil.WIKI_FORMATS_CONFIGURATION_MAIN,
								format));

						engine.setInterWikiConfiguration(
							_readConfigurationFile(
								PropsUtil.WIKI_FORMATS_CONFIGURATION_INTERWIKI,
								format));
					}
					else {
						engine = (WikiEngine)InstancePool.get(engineClass);
					}

					_engines.put(format, engine);
				}
			}
			catch (Exception e) {
				throw new WikiFormatException(e);
			}

			if (engine == null) {
				throw new WikiFormatException(format);
			}
		}

		return engine;
	}

	private String _getHelpPage(String format) {
		return PropsUtil.getComponentProperties().getString(
			PropsUtil.WIKI_FORMATS_HELP_PAGE, Filter.by(format));
	}

	private String _getHelpURL(String format) {
		return PropsUtil.getComponentProperties().getString(
			PropsUtil.WIKI_FORMATS_HELP_URL, Filter.by(format));
	}

	private Map _getLinks(WikiPage page)
		throws PageContentException, WikiFormatException{

		try {
			return _getEngine(page.getFormat()).getOutgoingLinks(page);
		}
		catch (WikiFormatException wfe) {
			return _EMPTY_MAP;
		}
	}

	private boolean _isLinkedTo(WikiPage page, String targetTitle)
		throws PageContentException, WikiFormatException {

		try {
			return _getEngine(page.getFormat()).isLinkedTo(page, targetTitle);
		}
		catch (WikiFormatException wfe) {
			return false;
		}
	}

	private String _readConfigurationFile(String propertyName, String format)
		throws IOException {

		ClassLoader classLoader = getClass().getClassLoader();

		String configurationFile = PropsUtil.getComponentProperties().getString(
			propertyName, Filter.by(format));

		if (Validator.isNotNull(configurationFile)) {
			return Http.URLtoString(classLoader.getResource(configurationFile));
		}
		else {
			return StringPool.BLANK;
		}
	}

	private String _replaceAttachments(
		String content, String title, String attachmentURLPrefix) {

		content = StringUtil.replace(content, "[$WIKI_PAGE_NAME$]", title);

		content = StringUtil.replace(
			content, "[$ATTACHMENT_URL_PREFIX$]", attachmentURLPrefix);

		return content;
	}

	private String _replaceLinks(
		String title, String content, LiferayPortletURL viewPageURL,
		LiferayPortletURL editPageURL) {

		viewPageURL.setParameter("title", title, false);

		content = StringUtil.replace(
			content,
			"[$BEGIN_PAGE_TITLE$]" + title + "[$END_PAGE_TITLE$]",
			viewPageURL.toString());

		editPageURL.setParameter("title", title, false);

		content = StringUtil.replace(
			content,
			"[$BEGIN_PAGE_TITLE_EDIT$]" + title + "[$END_PAGE_TITLE_EDIT$]",
			editPageURL.toString());

		return content;
	}

	private boolean _validate(long nodeId, String content, String format)
		throws WikiFormatException {

		return _getEngine(format).validate(nodeId, content);
	}

	private static final Map _EMPTY_MAP = new HashMap();

	private static WikiUtil _instance = new WikiUtil();

	private Map _engines = new HashMap();

}