/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.util.portlet;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.xml.DocUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceURL;
import javax.portlet.WindowStateException;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.portlet.PortletFileUpload;

/**
 * <a href="PortletRequestUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PortletRequestUtil {

	public static List<DiskFileItem> testMultipartWithCommonsFileUpload(
			ActionRequest actionRequest)
		throws Exception {

		// Check if the given request is a multipart request

		boolean multiPartContent = PortletFileUpload.isMultipartContent(
			actionRequest);

		if (multiPartContent) {
			_log.info("The given request is a multipart request");
		}
		else {
			_log.info("The given request is NOT a multipart request");
		}

		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();

		PortletFileUpload portletFileUpload = new PortletFileUpload(
			diskFileItemFactory);

		List<DiskFileItem> diskFileItems = portletFileUpload.parseRequest(
			actionRequest);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Apache commons upload was able to parse " +
					diskFileItems.size() + " items");
		}

		for (int i = 0; i < diskFileItems.size(); i++) {
			DiskFileItem diskFileItem = diskFileItems.get(i);

			if (_log.isInfoEnabled()) {
				_log.info("Item " + i + " " + diskFileItem);
			}
		}

		return diskFileItems;
	}

	public static int testMultipartWithPortletInputStream(
			ActionRequest actionRequest)
		throws Exception {

		InputStream inputStream = actionRequest.getPortletInputStream();

		if (inputStream != null) {
			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			StreamUtil.transfer(inputStream, unsyncByteArrayOutputStream);

			int size = unsyncByteArrayOutputStream.size();

			if (_log.isInfoEnabled()) {
				_log.info(
					"Byte array size from the raw input stream is " + size);
			}

			return size;
		}

		return -1;
	}

	public static String toXML(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		String xml = null;

		Document document = SAXReaderUtil.createDocument();

		Element requestElement = document.addElement("request");

		DocUtil.add(requestElement, "container-type", "portlet");
		DocUtil.add(
			requestElement, "container-namespace",
			portletRequest.getContextPath());
		DocUtil.add(
			requestElement, "content-type",
			portletRequest.getResponseContentType());
		DocUtil.add(
			requestElement, "server-name", portletRequest.getServerName());
		DocUtil.add(
			requestElement, "server-port", portletRequest.getServerPort());
		DocUtil.add(requestElement, "secure", portletRequest.isSecure());
		DocUtil.add(requestElement, "auth-type", portletRequest.getAuthType());
		DocUtil.add(
			requestElement, "remote-user", portletRequest.getRemoteUser());
		DocUtil.add(
			requestElement, "context-path", portletRequest.getContextPath());
		DocUtil.add(requestElement, "locale", portletRequest.getLocale());
		DocUtil.add(
			requestElement, "portlet-mode", portletRequest.getPortletMode());
		DocUtil.add(
			requestElement, "portlet-session-id",
			portletRequest.getRequestedSessionId());
		DocUtil.add(requestElement, "scheme", portletRequest.getScheme());
		DocUtil.add(
			requestElement, "window-state", portletRequest.getWindowState());

		if (portletRequest instanceof ActionRequest) {
			DocUtil.add(
				requestElement, "lifecycle", RenderRequest.ACTION_PHASE);
		}
		else if (portletRequest instanceof RenderRequest) {
			DocUtil.add(
				requestElement, "lifecycle", RenderRequest.RENDER_PHASE);
		}
		else if (portletRequest instanceof ResourceRequest) {
			DocUtil.add(
				requestElement, "lifecycle", RenderRequest.RESOURCE_PHASE);
		}

		if (portletResponse instanceof MimeResponse) {
			_mimeResponseToXML((MimeResponse)portletResponse, requestElement);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			Element themeDisplayElement = requestElement.addElement(
				"theme-display");

			_themeDisplayToXML(themeDisplay, themeDisplayElement);
		}

		Element parametersElement = requestElement.addElement("parameters");

		Enumeration<String> enu = portletRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			Element parameterElement = parametersElement.addElement(
				"parameter");

			DocUtil.add(parameterElement, "name", name);

			String[] values = portletRequest.getParameterValues(name);

			for (int i = 0; i < values.length; i++) {
				DocUtil.add(parameterElement, "value", values[i]);
			}
		}

		Element attributesElement = requestElement.addElement("attributes");

		enu = portletRequest.getAttributeNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (!_isValidAttributeName(name)) {
				continue;
			}

			Object value = portletRequest.getAttribute(name);

			if (!_isValidAttributeValue(value)) {
				continue;
			}

			Element attributeElement = attributesElement.addElement(
				"attribute");

			DocUtil.add(attributeElement, "name", name);
			DocUtil.add(attributeElement, "value", String.valueOf(value));
		}

		Element portletSessionElement = requestElement.addElement(
			"portlet-session");

		attributesElement = portletSessionElement.addElement(
			"portlet-attributes");

		PortletSession portletSession = portletRequest.getPortletSession();

		try {
			enu = portletSession.getAttributeNames(
				PortletSession.PORTLET_SCOPE);

			while (enu.hasMoreElements()) {
				String name = enu.nextElement();

				if (!_isValidAttributeName(name)) {
					continue;
				}

				Object value = portletSession.getAttribute(
					name, PortletSession.PORTLET_SCOPE);

				if (!_isValidAttributeValue(value)) {
					continue;
				}

				Element attributeElement = attributesElement.addElement(
					"attribute");

				DocUtil.add(attributeElement, "name", name);
				DocUtil.add(attributeElement, "value", String.valueOf(value));
			}

			attributesElement = portletSessionElement.addElement(
				"application-attributes");

			enu = portletSession.getAttributeNames(
				PortletSession.APPLICATION_SCOPE);

			while (enu.hasMoreElements()) {
				String name = enu.nextElement();

				if (!_isValidAttributeName(name)) {
					continue;
				}

				Object value = portletSession.getAttribute(
					name, PortletSession.APPLICATION_SCOPE);

				if (!_isValidAttributeValue(value)) {
					continue;
				}

				Element attributeElement = attributesElement.addElement(
					"attribute");

				DocUtil.add(attributeElement, "name", name);
				DocUtil.add(attributeElement, "value", String.valueOf(value));
			}
		}
		catch (IllegalStateException ise) {
			if (_log.isWarnEnabled()) {
				_log.warn(ise.getMessage());
			}
		}

		try {
			xml = document.formattedString();
		}
		catch (IOException ioe) {
		}

		return xml;
	}

	private static boolean _isValidAttributeName(String name) {
		if (name.equalsIgnoreCase("j_password") ||
			name.equalsIgnoreCase("LAYOUT_CONTENT") ||
			name.equalsIgnoreCase("LAYOUTS") ||
			name.equalsIgnoreCase("PORTLET_RENDER_PARAMETERS") ||
			name.equalsIgnoreCase("USER_PASSWORD") ||
			name.startsWith("javax.") ||
			name.startsWith("liferay-ui:")) {

			return false;
		}
		else {
			return true;
		}
	}

	private static boolean _isValidAttributeValue(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (obj instanceof Collection<?>) {
			Collection<?> col = (Collection<?>)obj;

			if (col.size() == 0) {
				return false;
			}
			else {
				return true;
			}
		}
		else if (obj instanceof Map<?, ?>) {
			Map<?, ?> map = (Map<?, ?>)obj;

			if (map.size() == 0) {
				return false;
			}
			else {
				return true;
			}
		}
		else {
			String objString = String.valueOf(obj);

			if (Validator.isNull(objString)) {
				return false;
			}

			String hashCode =
				StringPool.AT + Integer.toHexString(obj.hashCode());

			if (objString.endsWith(hashCode)) {
				return false;
			}

			return true;
		}
	}

	private static void _mimeResponseToXML(
		MimeResponse mimeResponse, Element requestElement) {

		String namespace = mimeResponse.getNamespace();

		DocUtil.add(requestElement, "portlet-namespace", namespace);

		try {
			PortletURL actionURL = mimeResponse.createActionURL();

			DocUtil.add(requestElement, "action-url", actionURL);
		}
		catch (IllegalStateException ise) {
			if (_log.isWarnEnabled()) {
				_log.warn(ise.getMessage());
			}
		}

		try {
			PortletURL renderURL = mimeResponse.createRenderURL();

			DocUtil.add(requestElement, "render-url", renderURL);

			try {
				renderURL.setWindowState(LiferayWindowState.EXCLUSIVE);

				DocUtil.add(requestElement, "render-url-exclusive", renderURL);
			}
			catch (WindowStateException wse) {
			}

			try {
				renderURL.setWindowState(LiferayWindowState.MAXIMIZED);

				DocUtil.add(requestElement, "render-url-maximized", renderURL);
			}
			catch (WindowStateException wse) {
			}

			try {
				renderURL.setWindowState(LiferayWindowState.MINIMIZED);

				DocUtil.add(requestElement, "render-url-minimized", renderURL);
			}
			catch (WindowStateException wse) {
			}

			try {
				renderURL.setWindowState(LiferayWindowState.NORMAL);

				DocUtil.add(requestElement, "render-url-normal", renderURL);
			}
			catch (WindowStateException wse) {
			}

			try {
				renderURL.setWindowState(LiferayWindowState.POP_UP);

				DocUtil.add(requestElement, "render-url-pop-up", renderURL);
			}
			catch (WindowStateException wse) {
			}
		}
		catch (IllegalStateException ise) {
			if (_log.isWarnEnabled()) {
				_log.warn(ise.getMessage());
			}
		}

		ResourceURL resourceURL = mimeResponse.createResourceURL();

		String resourceURLString = HttpUtil.removeParameter(
			resourceURL.toString(), namespace + "struts_action");

		resourceURLString = HttpUtil.removeParameter(
			resourceURLString, namespace + "redirect");

		DocUtil.add(requestElement, "resource-url", resourceURLString);
	}

	private static void _portletDisplayToXML(
		PortletDisplay portletDisplay, Element portletDisplayElement) {

		DocUtil.add(portletDisplayElement, "id", portletDisplay.getId());
		DocUtil.add(
			portletDisplayElement, "instance-id",
			portletDisplay.getInstanceId());
		DocUtil.add(
			portletDisplayElement, "portlet-name",
			portletDisplay.getPortletName());
		DocUtil.add(
			portletDisplayElement, "resource-pk",
			portletDisplay.getResourcePK());
		DocUtil.add(
			portletDisplayElement, "root-portlet-id",
			portletDisplay.getRootPortletId());
		DocUtil.add(
			portletDisplayElement, "title", portletDisplay.getTitle());
	}

	private static void _themeDisplayToXML(
		ThemeDisplay themeDisplay, Element themeDisplayElement) {

		DocUtil.add(themeDisplayElement, "cdn-host", themeDisplay.getCDNHost());
		DocUtil.add(
			themeDisplayElement, "company-id", themeDisplay.getCompanyId());
		DocUtil.add(
			themeDisplayElement, "do-as-user-id", themeDisplay.getDoAsUserId());
		DocUtil.add(
			themeDisplayElement, "i18n-language-id",
			themeDisplay.getI18nLanguageId());
		DocUtil.add(
			themeDisplayElement, "i18n-path", themeDisplay.getI18nPath());
		DocUtil.add(
			themeDisplayElement, "language-id", themeDisplay.getLanguageId());
		DocUtil.add(themeDisplayElement, "locale", themeDisplay.getLocale());
		DocUtil.add(
			themeDisplayElement, "path-context", themeDisplay.getPathContext());
		DocUtil.add(
			themeDisplayElement, "path-friendly-url-private-group",
			themeDisplay.getPathFriendlyURLPrivateGroup());
		DocUtil.add(
			themeDisplayElement, "path-friendly-url-private-user",
			themeDisplay.getPathFriendlyURLPrivateUser());
		DocUtil.add(
			themeDisplayElement, "path-friendly-url-public",
			themeDisplay.getPathFriendlyURLPublic());
		DocUtil.add(
			themeDisplayElement, "path-image", themeDisplay.getPathImage());
		DocUtil.add(
			themeDisplayElement, "path-main", themeDisplay.getPathMain());
		DocUtil.add(
			themeDisplayElement, "path-theme-images",
			themeDisplay.getPathThemeImages());
		DocUtil.add(themeDisplayElement, "plid", themeDisplay.getPlid());
		DocUtil.add(
			themeDisplayElement, "portal-url",
			HttpUtil.removeProtocol(themeDisplay.getPortalURL()));
		DocUtil.add(
			themeDisplayElement, "real-user-id", themeDisplay.getRealUserId());
		DocUtil.add(
			themeDisplayElement, "scope-group-id",
			themeDisplay.getScopeGroupId());
		DocUtil.add(themeDisplayElement, "secure", themeDisplay.isSecure());
		DocUtil.add(
			themeDisplayElement, "server-name", themeDisplay.getServerName());
		DocUtil.add(
			themeDisplayElement, "server-port", themeDisplay.getServerPort());
		DocUtil.add(
			themeDisplayElement, "time-zone",
			themeDisplay.getTimeZone().getID());
		DocUtil.add(
			themeDisplayElement, "url-portal",
			HttpUtil.removeProtocol(themeDisplay.getURLPortal()));
		DocUtil.add(themeDisplayElement, "user-id", themeDisplay.getUserId());

		if (themeDisplay.getPortletDisplay() != null) {
			Element portletDisplayElement = themeDisplayElement.addElement(
				"portlet-display");

			_portletDisplayToXML(
				themeDisplay.getPortletDisplay(), portletDisplayElement);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PortletRequestUtil.class);

}