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

package com.liferay.portal.cmis;

import com.liferay.portal.cmis.model.CMISConstants;
import com.liferay.portal.cmis.model.CMISExtensionFactory;
import com.liferay.portal.cmis.model.CMISObject;
import com.liferay.portal.cmis.model.CMISRepositoryInfo;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Collection;
import org.apache.abdera.model.Element;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Link;
import org.apache.abdera.model.Service;
import org.apache.abdera.model.Workspace;
import org.apache.abdera.protocol.Response.ResponseType;
import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.commons.httpclient.UsernamePasswordCredentials;

/**
 * <a href="CMISUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class CMISUtil {

	public static final CMISConstants getConstants() {
		return _constants;
	}

	public static Entry createFolder(String name) throws CMISException {
		return createFolder(_liferayChildrenUrl, name);
	}

	public static Entry createFolder(Entry parent, String name)
		throws CMISException {

		return createDocument(parent, name, null);
	}

	public static Entry createFolder(String parentChildrenUrl, String name)
		throws CMISException {

		return createDocument(parentChildrenUrl, name, null);
	}

	public static Entry createDocument(
			Entry parent, String name, InputStream is)
		throws CMISException {

		Link link = parent.getLink(_constants.LINK_CHILDREN);

		return createDocument(link.getHref().toString(), name, is);
	}

	public static Entry createDocument(
			String parentChildrenUrl, String name, InputStream is)
		throws CMISException {

		Entry entry = _abdera.newEntry();

		entry.setTitle(name);

		if (is == null) {
			CMISObject object = entry.addExtension(_constants.OBJECT);

			object.setValue(
				_constants.PROPERTY_NAME_OBJECT_TYPE_ID,
				_constants.BASE_TYPE_FOLDER);
		}
		else {
			entry.setContent(is);

			CMISObject object = entry.addExtension(_constants.OBJECT);

			object.setValue(
				_constants.PROPERTY_NAME_OBJECT_TYPE_ID,
				_constants.BASE_TYPE_DOCUMENT);
		}

		ClientResponse resp = _getClient().post(parentChildrenUrl, entry);

		if (ResponseType.select(resp.getStatus()) != ResponseType.SUCCESS) {
			throw new CMISException(
				"Error creating " + name + " " + resp.getStatusText());
		}

		return (Entry)resp.getDocument().getRoot();
	}

	public static void delete(Entry entry) throws CMISException {
		Link link = null;

		CMISObject cmisObject = entry.getFirstChild(_constants.OBJECT);

		if (cmisObject.getBaseType().equals(_constants.BASE_TYPE_FOLDER)) {
			link = entry.getLink(_constants.LINK_CHILDREN);
		}
		else {
			link = entry.getLink(_constants.LINK_ALL_VERSIONS);
		}

		delete(link.getHref().toString());
	}

	public static void delete(String url) throws CMISException {
		ClientResponse resp = _getClient().delete(url);

		if (ResponseType.select(resp.getStatus()) != ResponseType.SUCCESS) {
			throw new CMISException(
				"Error deleting object at " + url + " " +
					resp.getStatusText());
		}
	}

	public static String getCollectionUrl(Workspace ws, String collectionType) {
		for (Collection coll : ws.getCollections()) {
			String collType =
				coll.getAttributeValue(_constants.COLLECTION_TYPE);

			if (collType.equals(collectionType)) {
				return coll.getHref().toString();
			}
		}

		return null;
	}

	public static Entry getDocument(Entry parent, String title)
		throws CMISException {

		Link link = parent.getLink(_constants.LINK_CHILDREN);

		return getDocument(link.getHref().toString(), title);
	}

	public static Entry getDocument(String childrenUrl, String title)
		throws CMISException {

		return getEntry(childrenUrl, title, _constants.BASE_TYPE_DOCUMENT);
	}

	public static Entry getEntry(
			String childrenUrl, String title, String baseType)
		throws CMISException {

		Feed feed = (Feed)_getClient().get(childrenUrl).getDocument().getRoot();

		for (Entry entry : feed.getEntries()) {
			if (entry.getTitle().equals(title)) {
				CMISObject cmisObject = entry.getFirstChild(_constants.OBJECT);

				if (cmisObject.getBaseType().equals(baseType)) {
					return entry;
				}
			}
		}

		return null;
	}

	public static Entry getFolder(String title) throws CMISException {
		return getFolder(_liferayChildrenUrl, title);
	}

	public static Entry getFolder(Entry parent, String title)
		throws CMISException {

		Link link = parent.getLink(_constants.LINK_CHILDREN);

		return getFolder(link.getHref().toString(), title);
	}

	public static Entry getFolder(String childrenUrl, String title)
		throws CMISException {

		return getEntry(childrenUrl, title, _constants.BASE_TYPE_FOLDER);
	}

	public static List<String> getFolderList(Entry parent)
		throws CMISException {

		List<String> list = new ArrayList<String>();

		Link link = parent.getLink(_constants.LINK_CHILDREN);

		String childrenUrl = link.getHref().toString();

		Feed feed = (Feed)_getClient().get(childrenUrl).getDocument().getRoot();

		for (Entry entry : feed.getEntries()) {
			list.add(entry.getTitle());
		}

		return list;
	}

	public static InputStream getInputStream(Entry file) throws CMISException {
		try {
			Link link = file.getLink(_constants.LINK_STREAM);

			ClientResponse resp = _getClient().get(link.getHref().toString());

			return resp.getInputStream();
		}
		catch (Exception e) {
			throw new CMISException(e);
		}
	}

	public static Service getService() throws CMISException {
		Element element =
			_getClient().get(_REPOSITORY_URL).getDocument().getRoot();

		return (Service)element;
	}

	private static AbderaClient _getClient() throws CMISException {
		try {
			AbderaClient client = new AbderaClient(_abdera);

			client.addCredentials(null, null, null,
				new UsernamePasswordCredentials(_USERNAME, _PASSWORD));

			return client;
		}
		catch (Exception e) {
			throw new CMISException(e);
		}
	}

	private static void _verifyRepository(String repositoryUrl)
		throws Exception {

		Service service =
			(Service)_getClient().get(repositoryUrl).getDocument().getRoot();

		Workspace ws = service.getWorkspaces().get(0);

		CMISRepositoryInfo info = ws.getFirstChild(_constants.REPOSITORY_INFO);

		// CMIS Version

		String version = info.getVersionSupported();

		if (_log.isInfoEnabled()) {
			_log.info(
				"Using CMIS repository " + info.getProductName() + " " +
					info.getProductVersion());
			_log.info("CMIS repository supports CMIS version " + version);
		}

		if (!version.equals(_constants.VERSION)) {
			throw new RuntimeException(
				"CMIS repository is running an unsupported version");
		}

		// Find Root Folder

		String childrenUrl =
			getCollectionUrl(ws, _constants.COLLECTION_ROOT_CHILDREN);

		Entry entry =
			getEntry(childrenUrl, _ROOT_DIR, _constants.BASE_TYPE_FOLDER);

		if (entry == null) {
			entry = createFolder(childrenUrl, _ROOT_DIR);
		}

		_liferayChildrenUrl =
			entry.getLink(_constants.LINK_CHILDREN).getHref().toString();
	}

	private static Abdera _abdera;

	private static String _liferayChildrenUrl;

	private static Log _log = LogFactoryUtil.getLog(CMISUtil.class);

	private static final String _ROOT_DIR = PropsUtil.get(
		PropsKeys.CMIS_SYSTEM_ROOT_DIR);

	private static final String _PASSWORD = PropsUtil.get(
		PropsKeys.CMIS_CREDENTIALS_PASSWORD);

	private static final String _REPOSITORY_URL = PropsUtil.get(
		PropsKeys.CMIS_REPOSITORY_URL);

	private static final String _USERNAME = PropsUtil.get(
		PropsKeys.CMIS_CREDENTIALS_USERNAME);

	private static final String _CMIS_VERSION = PropsUtil.get(
		PropsKeys.CMIS_REPOSITORY_VERSION);

	private static final CMISConstants _constants =
		CMISConstants.getInstance(_CMIS_VERSION);

	static {
		try {
			_abdera = Abdera.getInstance();
			_abdera.getFactory().registerExtension(new CMISExtensionFactory());

			_verifyRepository(_REPOSITORY_URL);
		}
		catch (Exception e) {
			if (e instanceof RuntimeException) {
				throw (RuntimeException)e;
			}
			else {
				throw new RuntimeException(e);
			}
		}
	}

}