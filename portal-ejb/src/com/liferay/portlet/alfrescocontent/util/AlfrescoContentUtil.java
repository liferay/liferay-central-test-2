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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

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
 * @author Brian Wing Shun Chan
 * @author Raymond Auge
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

			return Http.URLtoString(contents[0].getUrl() + "?ticket=" + ticket);
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

		content = content.replaceAll("%28", "(");
		content = content.replaceAll("%29", ")");

		Pattern p = Pattern.compile("\"/c/portal/\\$link=\\((.*)\\)\"");

		Matcher m = p.matcher(content);

		StringBuffer sb = new StringBuffer();

		try {
			while (m.find()) {
				String[] rawPath = m.group(1).split("/");

				String path = "/app:company_home";

				for (int i = 0; i < rawPath.length; i++) {
					if (Validator.isNull(rawPath[i])) {
						continue;
					}

					path = path + "/cm:" + rawPath[i];
				}

				PortletURL portletURL = res.createRenderURL();

				portletURL.setParameter("path", path);

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

		return sb.toString();
	}

	private static final Store _SPACES_STORE =
		new Store(StoreEnum.workspace, "SpacesStore");

	private static Log _log = LogFactory.getLog(AlfrescoContentUtil.class);

}