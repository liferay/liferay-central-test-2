/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DiffHtmlUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.wiki.PageContentException;
import com.liferay.portlet.wiki.WikiFormatException;
import com.liferay.portlet.wiki.engines.WikiEngine;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.permission.WikiNodePermission;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

/**
 * <a href="WikiUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
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

	public static String diffHtml (
			WikiPage sourcePage, WikiPage targetPage, PortletURL viewPageURL,
			PortletURL editPageURL, String attachmentURLPrefix)
		throws Exception {

		String sourceContent = StringPool.BLANK;
		String targetContent = StringPool.BLANK;

		if (sourcePage != null) {
			sourceContent = WikiUtil.convert(
				sourcePage, viewPageURL, editPageURL,attachmentURLPrefix);
		}

		if (targetPage != null) {
			targetContent = WikiUtil.convert(
				targetPage, viewPageURL, editPageURL, attachmentURLPrefix);
		}

		return DiffHtmlUtil.diff(
			new UnsyncStringReader(sourceContent),
			new UnsyncStringReader(targetContent));
	}

	public static String getEditPage(String format) {
		return _instance._getEditPage(format);
	}

	public static String getEmailFromAddress(PortletPreferences preferences) {
		String emailFromAddress = PropsUtil.get(
			PropsKeys.WIKI_EMAIL_FROM_ADDRESS);

		return preferences.getValue("email-from-address", emailFromAddress);
	}

	public static String getEmailFromName(PortletPreferences preferences) {
		String emailFromName = PropsUtil.get(PropsKeys.WIKI_EMAIL_FROM_NAME);

		return preferences.getValue("email-from-name", emailFromName);
	}

	public static boolean getEmailPageAddedEnabled(
		PortletPreferences preferences) {

		String emailPageAddedEnabled = preferences.getValue(
			"email-page-added-enabled", StringPool.BLANK);

		if (Validator.isNotNull(emailPageAddedEnabled)) {
			return GetterUtil.getBoolean(emailPageAddedEnabled);
		}
		else {
			return GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.WIKI_EMAIL_PAGE_ADDED_ENABLED));
		}
	}

	public static String getEmailPageAddedBody(PortletPreferences preferences) {
		String emailPageAddedBody = preferences.getValue(
			"email-page-added-body", StringPool.BLANK);

		if (Validator.isNotNull(emailPageAddedBody)) {
			return emailPageAddedBody;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsKeys.WIKI_EMAIL_PAGE_ADDED_BODY));
		}
	}

	public static String getEmailPageAddedSignature(
		PortletPreferences preferences) {

		String emailPageAddedSignature = preferences.getValue(
			"email-page-added-signature", StringPool.BLANK);

		if (Validator.isNotNull(emailPageAddedSignature)) {
			return emailPageAddedSignature;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsKeys.WIKI_EMAIL_PAGE_ADDED_SIGNATURE));
		}
	}

	public static String getEmailPageAddedSubjectPrefix(
		PortletPreferences preferences) {

		String emailPageAddedSubjectPrefix = preferences.getValue(
			"email-page-added-subject-prefix", StringPool.BLANK);

		if (Validator.isNotNull(emailPageAddedSubjectPrefix)) {
			return emailPageAddedSubjectPrefix;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsKeys.WIKI_EMAIL_PAGE_ADDED_SUBJECT_PREFIX));
		}
	}

	public static boolean getEmailPageUpdatedEnabled(
		PortletPreferences preferences) {

		String emailPageUpdatedEnabled = preferences.getValue(
			"email-page-updated-enabled", StringPool.BLANK);

		if (Validator.isNotNull(emailPageUpdatedEnabled)) {
			return GetterUtil.getBoolean(emailPageUpdatedEnabled);
		}
		else {
			return GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.WIKI_EMAIL_PAGE_UPDATED_ENABLED));
		}
	}

	public static String getEmailPageUpdatedBody(
		PortletPreferences preferences) {

		String emailPageUpdatedBody = preferences.getValue(
			"email-page-updated-body", StringPool.BLANK);

		if (Validator.isNotNull(emailPageUpdatedBody)) {
			return emailPageUpdatedBody;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsKeys.WIKI_EMAIL_PAGE_UPDATED_BODY));
		}
	}

	public static String getEmailPageUpdatedSignature(
		PortletPreferences preferences) {

		String emailPageUpdatedSignature = preferences.getValue(
			"email-page-updated-signature", StringPool.BLANK);

		if (Validator.isNotNull(emailPageUpdatedSignature)) {
			return emailPageUpdatedSignature;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsKeys.WIKI_EMAIL_PAGE_UPDATED_SIGNATURE));
		}
	}

	public static String getEmailPageUpdatedSubjectPrefix(
		PortletPreferences preferences) {

		String emailPageUpdatedSubject = preferences.getValue(
			"email-page-updated-subject-prefix", StringPool.BLANK);

		if (Validator.isNotNull(emailPageUpdatedSubject)) {
			return emailPageUpdatedSubject;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsKeys.WIKI_EMAIL_PAGE_UPDATED_SUBJECT_PREFIX));
		}
	}

	public static String getHelpPage(String format) {
		return _instance._getHelpPage(format);
	}

	public static String getHelpURL(String format) {
		return _instance._getHelpURL(format);
	}

	public static Map<String, Boolean> getLinks(WikiPage page)
		throws PageContentException {

		return _instance._getLinks(page);
	}

	public static String getMailId(String mx, long nodeId, long pageId) {
		StringBundler sb = new StringBundler(10);

		sb.append(StringPool.LESS_THAN);
		sb.append(POP_PORTLET_PREFIX);
		sb.append(nodeId);
		sb.append(StringPool.PERIOD);
		sb.append(pageId);
		sb.append(StringPool.AT);
		sb.append(PropsValues.POP_SERVER_SUBDOMAIN);
		sb.append(StringPool.PERIOD);
		sb.append(mx);
		sb.append(StringPool.GREATER_THAN);

		return sb.toString();
	}

	public static List<WikiNode> getNodes(RenderRequest renderRequest)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = themeDisplay.getScopeGroupId();

		String allNodes = ListUtil.toString(
			WikiNodeLocalServiceUtil.getNodes(groupId), "name");

		String[] visibleNodes = StringUtil.split(
			renderRequest.getPreferences().getValue("visible-nodes", allNodes));
		String[] hiddenNodes = StringUtil.split(
			renderRequest.getPreferences().getValue(
				"hidden-nodes", StringPool.BLANK));

		return getNodes(
			groupId, visibleNodes, hiddenNodes,
			themeDisplay.getPermissionChecker());
	}

	public static List<WikiNode> getNodes(
			long groupId, String[] visibleNodes, String[] hiddenNodes,
			PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		List<WikiNode> nodes = WikiNodeLocalServiceUtil.getNodes(groupId);

		nodes = ListUtil.copy(nodes);
		nodes = _orderNodes(nodes, visibleNodes);

		Iterator<WikiNode> itr = nodes.iterator();

		while (itr.hasNext()) {
			WikiNode node = itr.next();

			if (ArrayUtil.contains(hiddenNodes, node.getName()) ||
				!WikiNodePermission.contains(
					permissionChecker, node.getNodeId(), ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return nodes;
	}

	public static String processContent(String content) {
		content = content.replaceAll("</p>", "</p>\n");
		content = content.replaceAll("</br>", "</br>\n");
		content = content.replaceAll("</div>", "</div>\n");

		return content;
	}

	public static boolean validate(
			long nodeId, String content, String format)
		throws WikiFormatException {

		return _instance._validate(nodeId, content, format);
	}

	private static List<WikiNode> _orderNodes(
		List<WikiNode> nodes, String[] nodeNames) {

		List<WikiNode> orderedNodes = new ArrayList<WikiNode>();

		for (String nodeName : nodeNames) {
			for (WikiNode node : nodes) {
				if (node.getName().equals(nodeName)) {
					orderedNodes.add(node);

					nodes.remove(node);

					break;
				}
			}
		}

		orderedNodes.addAll(nodes);

		return orderedNodes;
	}

	private String _convert(
			WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
			String attachmentURLPrefix)
		throws PageContentException, WikiFormatException {

		LiferayPortletURL liferayViewPageURL = (LiferayPortletURL)viewPageURL;
		LiferayPortletURL liferayEditPageURL = (LiferayPortletURL)editPageURL;

		WikiEngine engine = _getEngine(page.getFormat());

		String content = engine.convert(page, editPageURL);

		String editPageURLString = StringPool.BLANK;

		if (editPageURL != null) {
			liferayEditPageURL.setParameter("title", "__REPLACEMENT__", false);

			editPageURLString = editPageURL.toString();

			editPageURLString = StringUtil.replace(
				editPageURLString, "__REPLACEMENT__", "$1");
		}

		Matcher matcher = _editPageURLPattern.matcher(content);

		content = matcher.replaceAll(editPageURLString);

		String viewPageURLString = StringPool.BLANK;

		if (viewPageURL != null) {
			liferayViewPageURL.setParameter("title", "__REPLACEMENT__", false);

			viewPageURLString = viewPageURL.toString();

			viewPageURLString = StringUtil.replace(
				viewPageURLString, "__REPLACEMENT__", "$1");
		}

		matcher = _viewPageURLPattern.matcher(content);

		content = matcher.replaceAll(viewPageURLString);

		content = _replaceAttachments(
			content, page.getTitle(), attachmentURLPrefix);

		return content;
	}

	private String _getEditPage(String format) {
		return PropsUtil.get(
			PropsKeys.WIKI_FORMATS_EDIT_PAGE, new Filter(format));
	}

	private WikiEngine _getEngine(String format) throws WikiFormatException {
		WikiEngine engine = _engines.get(format);

		if (engine == null) {
			try {
				String engineClass = PropsUtil.get(
					PropsKeys.WIKI_FORMATS_ENGINE, new Filter(format));

				if (engineClass != null) {
					if (!InstancePool.contains(engineClass)) {
						engine = (WikiEngine)InstancePool.get(engineClass);

						engine.setMainConfiguration(
							_readConfigurationFile(
								PropsKeys.WIKI_FORMATS_CONFIGURATION_MAIN,
								format));

						engine.setInterWikiConfiguration(
							_readConfigurationFile(
								PropsKeys.WIKI_FORMATS_CONFIGURATION_INTERWIKI,
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
		return PropsUtil.get(
			PropsKeys.WIKI_FORMATS_HELP_PAGE, new Filter(format));
	}

	private String _getHelpURL(String format) {
		return PropsUtil.get(
			PropsKeys.WIKI_FORMATS_HELP_URL, new Filter(format));
	}

	private Map<String, Boolean> _getLinks(WikiPage page)
		throws PageContentException {

		try {
			return _getEngine(page.getFormat()).getOutgoingLinks(page);
		}
		catch (WikiFormatException wfe) {
			return Collections.EMPTY_MAP;
		}
	}

	private String _readConfigurationFile(String propertyName, String format)
		throws IOException {

		ClassLoader classLoader = getClass().getClassLoader();

		String configurationFile = PropsUtil.get(
			propertyName, new Filter(format));

		if (Validator.isNotNull(configurationFile)) {
			return HttpUtil.URLtoString(
				classLoader.getResource(configurationFile));
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

	private boolean _validate(long nodeId, String content, String format)
		throws WikiFormatException {

		return _getEngine(format).validate(nodeId, content);
	}

	private static WikiUtil _instance = new WikiUtil();

	private static Pattern _editPageURLPattern = Pattern.compile(
		"\\[\\$BEGIN_PAGE_TITLE_EDIT\\$\\](.*?)\\[\\$END_PAGE_TITLE_EDIT\\$\\]");
	private static Pattern _viewPageURLPattern = Pattern.compile(
		"\\[\\$BEGIN_PAGE_TITLE\\$\\](.*?)\\[\\$END_PAGE_TITLE\\$\\]");

	private Map<String, WikiEngine> _engines =
		new HashMap<String, WikiEngine>();

}