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

		// Read directly from the portlet input stream

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

		Document doc = SAXReaderUtil.createDocument();

		Element reqEl = doc.addElement("request");

		DocUtil.add(reqEl, "container-type", "portlet");
		DocUtil.add(
			reqEl, "container-namespace", portletRequest.getContextPath());
		DocUtil.add(
			reqEl, "content-type", portletRequest.getResponseContentType());
		DocUtil.add(reqEl, "server-name", portletRequest.getServerName());
		DocUtil.add(reqEl, "server-port", portletRequest.getServerPort());
		DocUtil.add(reqEl, "secure", portletRequest.isSecure());
		DocUtil.add(reqEl, "auth-type", portletRequest.getAuthType());
		DocUtil.add(reqEl, "remote-user", portletRequest.getRemoteUser());
		DocUtil.add(reqEl, "context-path", portletRequest.getContextPath());
		DocUtil.add(reqEl, "locale", portletRequest.getLocale());
		DocUtil.add(reqEl, "portlet-mode", portletRequest.getPortletMode());
		DocUtil.add(
			reqEl, "portlet-session-id",
			portletRequest.getRequestedSessionId());
		DocUtil.add(reqEl, "scheme", portletRequest.getScheme());
		DocUtil.add(reqEl, "window-state", portletRequest.getWindowState());

		if (portletRequest instanceof ActionRequest) {
			DocUtil.add(reqEl, "lifecycle", RenderRequest.ACTION_PHASE);
		}
		else if (portletRequest instanceof RenderRequest) {
			DocUtil.add(reqEl, "lifecycle", RenderRequest.RENDER_PHASE);
		}
		else if (portletRequest instanceof ResourceRequest) {
			DocUtil.add(reqEl, "lifecycle", RenderRequest.RESOURCE_PHASE);
		}

		if (portletResponse instanceof MimeResponse) {
			_mimeResponseToXML((MimeResponse)portletResponse, reqEl);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			Element themeDisplayEl = reqEl.addElement("theme-display");

			_themeDisplayToXML(themeDisplay, themeDisplayEl);
		}

		Element parametersEl = reqEl.addElement("parameters");

		Enumeration<String> enu = portletRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			Element parameterEl = parametersEl.addElement("parameter");

			DocUtil.add(parameterEl, "name", name);

			String[] values = portletRequest.getParameterValues(name);

			for (int i = 0; i < values.length; i++) {
				DocUtil.add(parameterEl, "value", values[i]);
			}
		}

		Element attributesEl = reqEl.addElement("attributes");

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

			Element attributeEl = attributesEl.addElement("attribute");

			DocUtil.add(attributeEl, "name", name);
			DocUtil.add(attributeEl, "value", String.valueOf(value));
		}

		Element portletSessionEl = reqEl.addElement("portlet-session");

		attributesEl = portletSessionEl.addElement("portlet-attributes");

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

				Element attributeEl = attributesEl.addElement("attribute");

				DocUtil.add(attributeEl, "name", name);
				DocUtil.add(attributeEl, "value", String.valueOf(value));
			}

			attributesEl = portletSessionEl.addElement(
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

				Element attributeEl = attributesEl.addElement("attribute");

				DocUtil.add(attributeEl, "name", name);
				DocUtil.add(attributeEl, "value", String.valueOf(value));
			}
		}
		catch (IllegalStateException ise) {
			if (_log.isWarnEnabled()) {
				_log.warn(ise.getMessage());
			}
		}

		try {
			xml = doc.formattedString();
		}
		catch (IOException ioe) {
		}

		return xml;
	}

	private static void _mimeResponseToXML(
		MimeResponse mimeResponse, Element reqEl) {

		String namespace = mimeResponse.getNamespace();

		DocUtil.add(reqEl, "portlet-namespace", namespace);

		try {
			PortletURL actionUrl = mimeResponse.createActionURL();

			DocUtil.add(reqEl, "action-url", actionUrl);
		}
		catch (IllegalStateException ise) {
			if (_log.isWarnEnabled()) {
				_log.warn(ise.getMessage());
			}
		}

		try {
			PortletURL renderUrl = mimeResponse.createRenderURL();

			DocUtil.add(reqEl, "render-url", renderUrl);

			try {
				renderUrl.setWindowState(LiferayWindowState.EXCLUSIVE);

				DocUtil.add(reqEl, "render-url-exclusive", renderUrl);
			}
			catch (WindowStateException wse) {
			}

			try {
				renderUrl.setWindowState(LiferayWindowState.MAXIMIZED);

				DocUtil.add(reqEl, "render-url-maximized", renderUrl);
			}
			catch (WindowStateException wse) {
			}

			try {
				renderUrl.setWindowState(LiferayWindowState.MINIMIZED);

				DocUtil.add(reqEl, "render-url-minimized", renderUrl);
			}
			catch (WindowStateException wse) {
			}

			try {
				renderUrl.setWindowState(LiferayWindowState.NORMAL);

				DocUtil.add(reqEl, "render-url-normal", renderUrl);
			}
			catch (WindowStateException wse) {
			}

			try {
				renderUrl.setWindowState(LiferayWindowState.POP_UP);

				DocUtil.add(reqEl, "render-url-pop-up", renderUrl);
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

		DocUtil.add(reqEl, "resource-url", resourceURLString);
	}

	private static void _themeDisplayToXML(
		ThemeDisplay themeDisplay, Element themeDisplayEl) {

		DocUtil.add(themeDisplayEl, "cdn-host", themeDisplay.getCDNHost());
		DocUtil.add(themeDisplayEl, "company-id", themeDisplay.getCompanyId());
		DocUtil.add(
			themeDisplayEl, "do-as-user-id", themeDisplay.getDoAsUserId());
		DocUtil.add(
			themeDisplayEl, "i18n-language-id",
			themeDisplay.getI18nLanguageId());
		DocUtil.add(themeDisplayEl, "i18n-path", themeDisplay.getI18nPath());
		DocUtil.add(
			themeDisplayEl, "language-id", themeDisplay.getLanguageId());
		DocUtil.add(themeDisplayEl, "locale", themeDisplay.getLocale());
		DocUtil.add(
			themeDisplayEl, "path-context", themeDisplay.getPathContext());
		DocUtil.add(
			themeDisplayEl, "path-friendly-url-private-group",
			themeDisplay.getPathFriendlyURLPrivateGroup());
		DocUtil.add(
			themeDisplayEl, "path-friendly-url-private-user",
			themeDisplay.getPathFriendlyURLPrivateUser());
		DocUtil.add(
			themeDisplayEl, "path-friendly-url-public",
			themeDisplay.getPathFriendlyURLPublic());
		DocUtil.add(themeDisplayEl, "path-image", themeDisplay.getPathImage());
		DocUtil.add(themeDisplayEl, "path-main", themeDisplay.getPathMain());
		DocUtil.add(
			themeDisplayEl, "path-theme-images",
			themeDisplay.getPathThemeImages());
		DocUtil.add(themeDisplayEl, "plid", themeDisplay.getPlid());
		DocUtil.add(
			themeDisplayEl, "portal-url",
			HttpUtil.removeProtocol(themeDisplay.getPortalURL()));
		DocUtil.add(
			themeDisplayEl, "real-user-id", themeDisplay.getRealUserId());
		DocUtil.add(
			themeDisplayEl, "scope-group-id", themeDisplay.getScopeGroupId());
		DocUtil.add(themeDisplayEl, "secure", themeDisplay.isSecure());
		DocUtil.add(
			themeDisplayEl, "server-name", themeDisplay.getServerName());
		DocUtil.add(
			themeDisplayEl, "server-port", themeDisplay.getServerPort());
		DocUtil.add(
			themeDisplayEl, "time-zone", themeDisplay.getTimeZone().getID());
		DocUtil.add(
			themeDisplayEl, "url-portal",
			HttpUtil.removeProtocol(themeDisplay.getURLPortal()));
		DocUtil.add(themeDisplayEl, "user-id", themeDisplay.getUserId());

		if (themeDisplay.getPortletDisplay() != null) {
			Element portletDisplayEl = themeDisplayEl.addElement(
				"portlet-display");

			_portletDisplayToXML(
				themeDisplay.getPortletDisplay(), portletDisplayEl);
		}
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

	private static void _portletDisplayToXML(
		PortletDisplay portletDisplay, Element portletDisplayEl) {

		DocUtil.add(portletDisplayEl, "id", portletDisplay.getId());
		DocUtil.add(
			portletDisplayEl, "instance-id", portletDisplay.getInstanceId());
		DocUtil.add(
			portletDisplayEl, "portlet-name", portletDisplay.getPortletName());
		DocUtil.add(
			portletDisplayEl, "resource-pk", portletDisplay.getResourcePK());
		DocUtil.add(
			portletDisplayEl, "root-portlet-id",
			portletDisplay.getRootPortletId());
		DocUtil.add(
			portletDisplayEl, "title", portletDisplay.getTitle());
	}

	private static Log _log = LogFactoryUtil.getLog(PortletRequestUtil.class);

}