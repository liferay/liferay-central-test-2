/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.alfrescocontent.util;

import com.liferay.util.Http;
import com.liferay.util.Validator;

import java.io.InputStream;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.alfresco.webservice.accesscontrol.AccessControlServiceSoapBindingStub;
import org.alfresco.webservice.accesscontrol.AccessStatus;
import org.alfresco.webservice.accesscontrol.HasPermissionsResult;
import org.alfresco.webservice.content.Content;
import org.alfresco.webservice.content.ContentServiceSoapBindingStub;
import org.alfresco.webservice.repository.QueryResult;
import org.alfresco.webservice.repository.RepositoryServiceSoapBindingStub;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.types.Node;
import org.alfresco.webservice.types.Predicate;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.types.ResultSetRow;
import org.alfresco.webservice.types.Store;
import org.alfresco.webservice.types.StoreEnum;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.WebServiceFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="AlfrescoContentUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 * @author Brian Wing Shun Chan
 *
 */
public class AlfrescoContentUtil {

	public static ResultSetRow[] getChildNodes(
			String userId, String password, String uuid)
		throws Exception {

		ResultSetRow[] rows = null;

		try {
			AuthenticationUtils.startSession(userId, password);

			RepositoryServiceSoapBindingStub repositoryService =
				WebServiceFactory.getRepositoryService();

			Reference reference = null;

			if (Validator.isNull(uuid)) {
				reference = new Reference(_SPACES_STORE, null, null);
			}
			else {
				reference = new Reference(_SPACES_STORE, uuid, null);
			}

			QueryResult result = repositoryService.queryChildren(reference);

			rows = result.getResultSet().getRows();
		}
		finally {
			AuthenticationUtils.endSession();
		}

		return rows;
	}

	public static String getContent(
			String userId, String password, String uuid, String path)
		throws Exception {

		if (Validator.isNotNull(path)) {
			uuid = null;
		}
		else if (Validator.isNotNull(uuid)) {
			path = null;
		}
		else {
			return null;
		}

		try {
			AuthenticationUtils.startSession(userId, password);

			ContentServiceSoapBindingStub contentService =
				WebServiceFactory.getContentService();

			Reference reference = new Reference(_SPACES_STORE, uuid, path);

			Predicate predicate = new Predicate(
				new Reference[] {reference}, _SPACES_STORE, null);

			Content[] contents = contentService.read(
				predicate, Constants.PROP_CONTENT);

			String ticket = AuthenticationUtils.getCurrentTicket();

			String content = Http.URLtoString(contents[0].getUrl() +
				"?ticket=" + ticket);

			return content;
		}
		finally {
			AuthenticationUtils.endSession();
		}
	}

	public static String getContent(
			String userId, String password, String uuid, String path,
			boolean maximizeLinks, RenderResponse res)
		throws Exception {

		String content = getContent(userId, password, uuid, path);

		return formatContent(content, maximizeLinks, res);
	}

	public static String getNamedValue(NamedValue[] namedValues, String name) {
		String value = null;

		for (int i = 0; i < namedValues.length; i++) {
			NamedValue namedValue = namedValues[i];

			if (namedValue.getName().endsWith(name)) {
				value = namedValue.getValue();
			}
		}

		return value;
	}

	public static Node getNode(String userId, String password, String uuid)
		throws Exception {

		if (Validator.isNull(uuid)) {
			return null;
		}

		Node[] nodes = null;

		try {
			AuthenticationUtils.startSession(userId, password);

			RepositoryServiceSoapBindingStub repositoryService =
				WebServiceFactory.getRepositoryService();

			Reference reference = new Reference(_SPACES_STORE, uuid, null);

			Predicate predicate = new Predicate(
				new Reference[] {reference}, _SPACES_STORE, null);

			nodes = repositoryService.get(predicate);
		}
		finally {
			AuthenticationUtils.endSession();
		}

		return nodes[0];
	}

	public static ResultSetRow[] getParentNodes(
			String userId, String password, String uuid)
		throws Exception {

		ResultSetRow[] rows = null;

		try {
			AuthenticationUtils.startSession(userId, password);

			RepositoryServiceSoapBindingStub repositoryService =
				WebServiceFactory.getRepositoryService();

			Reference reference = null;

			if (Validator.isNull(uuid)) {
				reference = new Reference(_SPACES_STORE, null, null);
			}
			else {
				reference = new Reference(_SPACES_STORE, uuid, null);
			}

			QueryResult result = repositoryService.queryParents(reference);

			rows = result.getResultSet().getRows();
		}
		finally {
			AuthenticationUtils.endSession();
		}

		return rows;
	}

	public static String formatContent(
		String content, boolean maximizeLinks, RenderResponse res) {

		if (content == null) {
			return null;
		}

		Matcher m = _PROXY_URL_PATTERN.matcher(content);

		StringBuffer sb = new StringBuffer();

		try {
			while (m.find()) {
				String uuid = m.group(1);

				PortletURL portletURL = res.createRenderURL();

				portletURL.setParameter("uuid", uuid);

				if (maximizeLinks) {
					portletURL.setWindowState(WindowState.MAXIMIZED);
				}

				m.appendReplacement(sb, "\"" + portletURL.toString() + "\"");
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		m.appendTail(sb);

		content = sb.toString();

		m = _RESOURCE_URL_PATTERN.matcher(content);

		sb = new StringBuffer();

		try {
			while (m.find()) {
				String imagePath = m.group(1);

				m.appendReplacement(sb, "\"" + getEndpointAddress() + 
					"/alfresco" + imagePath + "?guest=true" + "\"");
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		m.appendTail(sb);

		content = sb.toString();

		return content;
	}

	public static boolean hasPermission(String login, String password,
		String uuid, String action) {

		AccessControlServiceSoapBindingStub accessControlService = WebServiceFactory
			.getAccessControlService();

		boolean hasPerm = false;

		try {

			AuthenticationUtils.startSession(login, password);

			Reference reference = new Reference(_SPACES_STORE, uuid, null);

			Predicate predicate = new Predicate(new Reference[] { reference },
				_SPACES_STORE, null);

			HasPermissionsResult[] results = accessControlService
				.hasPermissions(predicate, new String[] { Constants.WRITE });

			if ((results.length == 1)
				&& results[0].getPermission().equals(Constants.WRITE)
				&& results[0].getAccessStatus().equals(AccessStatus.acepted)) {

				hasPerm = true;
			}
		}
		catch (Exception e) {
			hasPerm = false;

			_log.warn("Could not start session: \n" + e.getMessage());
		}
		finally {
			AuthenticationUtils.endSession();
		}

		return hasPerm;
	}

    public static String getEndpointAddress() {
		String endPoint = _DEFAULT_ENDPOINT_ADDRESS;
		ClassLoader cl = AlfrescoContentUtil.class.getClassLoader();
		InputStream is = cl.getResourceAsStream(_PROPERTY_FILE_NAME);

		if (is != null) {

			Properties props = new Properties();

			try {
				props.load(is);

				endPoint = props.getProperty(_REPO_LOCATION);

				if (_log.isDebugEnabled() == true) {
					_log.debug("Using endpoint " + endPoint);
				}
			}
			catch (Exception e) {
				if (_log.isDebugEnabled() == true) {
					_log.debug(
						"Unable to file web service client properties file.  Using default.");
				}
			}
		}

		return endPoint;
	}

    private static final Pattern _PROXY_URL_PATTERN = Pattern.compile(
		"\"workspace://SpacesStore/([\\w\\-]*)\"");

    private static final Pattern _RESOURCE_URL_PATTERN = Pattern.compile(
		"\"(?:\\.\\.)?(?:/\\.\\.)*(/download/direct/workspace/SpacesStore/[\\w\\-/\\.]*)\"");

    private static final String _DEFAULT_ENDPOINT_ADDRESS =
    	"http://localhost:8080";

    private static final String _PROPERTY_FILE_NAME =
    	"alfresco/webserviceclient.properties";

    private static final String _REPO_LOCATION =
    	"repository.location";

	private static final Store _SPACES_STORE =
		new Store(StoreEnum.workspace, "SpacesStore");

	private static Log _log = LogFactory.getLog(AlfrescoContentUtil.class);

}