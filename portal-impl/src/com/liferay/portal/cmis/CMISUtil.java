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
import com.liferay.portal.util.PropsValues;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Collection;
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
 */
public class CMISUtil {

	public static Entry createDocument(
			Entry entry, String title, InputStream is)
		throws CMISException {

		return _instance._createDocument(entry, title, is);
	}

	public static Entry createDocument(String url, String title, InputStream is)
		throws CMISException {

		return _instance._createDocument(url, title, is);
	}

	public static Entry createFolder(Entry entry, String title)
		throws CMISException {

		return _instance._createFolder(entry, title);
	}

	public static Entry createFolder(String title) throws CMISException {
		return _instance._createFolder(title);
	}

	public static Entry createFolder(String url, String title)
		throws CMISException {

		return _instance._createFolder(url, title);
	}

	public static void delete(Entry entry) throws CMISException {
		_instance._delete(entry);
	}

	public static void delete(String url) throws CMISException {
		_instance._delete(url);
	}

	public static String getCollectionUrl(
		Workspace workspace, String collectionType) {

		return _instance._getCollectionUrl(workspace, collectionType);
	}

	public static Entry getDocument(Entry entry, String title)
		throws CMISException {

		return _instance._getDocument(entry, title);
	}

	public static Entry getDocument(String url, String title)
		throws CMISException {

		return _instance._getDocument(url, title);
	}

	public static Entry getEntry(String url, String title, String baseType)
		throws CMISException {

		return _instance._getEntry(url, title, baseType);
	}

	public static Entry getFolder(Entry entry, String title)
		throws CMISException {

		return _instance._getFolder(entry, title);
	}

	public static Entry getFolder(String title) throws CMISException {
		return _instance._getFolder(title);
	}

	public static Entry getFolder(String url, String title)
		throws CMISException {

		return _instance._getFolder(url, title);
	}

	public static List<String> getFolders(Entry entry) throws CMISException {
		return _instance._getFolders(entry);
	}

	public static InputStream getInputStream(Entry entry) throws CMISException {
		return _instance._getInputStream(entry);
	}

	public static Service getService() throws CMISException {
		return _instance._getService();
	}

	public static String verifyRepository() throws Exception {
		return _instance._verifyRepository();
	}

	private CMISUtil() {
		try {
			_abdera = Abdera.getInstance();
			_cmisConstants = CMISConstants.getInstance();
			_usernamePasswordCredentials = new UsernamePasswordCredentials(
				PropsValues.CMIS_CREDENTIALS_USERNAME,
				PropsValues.CMIS_CREDENTIALS_PASSWORD);

			Factory factory = _abdera.getFactory();

			factory.registerExtension(new CMISExtensionFactory());
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

	private Entry _createDocument(Entry entry, String title, InputStream is)
		throws CMISException {

		Link link = entry.getLink(_cmisConstants.LINK_CHILDREN);

		return createDocument(link.getHref().toString(), title, is);
	}

	private Entry _createDocument(String url, String title, InputStream is)
		throws CMISException {

		Entry entry = _abdera.newEntry();

		entry.setTitle(title);

		if (is == null) {
			CMISObject cmisObject = entry.addExtension(_cmisConstants.OBJECT);

			cmisObject.setValue(
				_cmisConstants.PROPERTY_NAME_OBJECT_TYPE_ID,
				_cmisConstants.BASE_TYPE_FOLDER);
		}
		else {
			entry.setContent(is);

			CMISObject cmisObject = entry.addExtension(_cmisConstants.OBJECT);

			cmisObject.setValue(
				_cmisConstants.PROPERTY_NAME_OBJECT_TYPE_ID,
				_cmisConstants.BASE_TYPE_DOCUMENT);
		}

		ClientResponse clientResponse = _getAbedraClient().post(
			url, entry);

		_verify(clientResponse);

		if (ResponseType.select(
				clientResponse.getStatus()) != ResponseType.SUCCESS) {

			throw new CMISException(
				"Error creating " + title + " " +
					clientResponse.getStatusText());
		}

		return (Entry)clientResponse.getDocument().getRoot();
	}

	private Entry _createFolder(Entry entry, String title)
		throws CMISException {

		return _createDocument(entry, title, null);
	}

	private Entry _createFolder(String title) throws CMISException {
		return _createFolder(_linkChildrenURL, title);
	}

	private Entry _createFolder(String url, String title) throws CMISException {
		return _createDocument(url, title, null);
	}

	private void _delete(Entry entry) throws CMISException {
		Link link = null;

		CMISObject cmisObject = entry.getFirstChild(_cmisConstants.OBJECT);

		if (cmisObject.getBaseType().equals(_cmisConstants.BASE_TYPE_FOLDER)) {
			link = entry.getLink(_cmisConstants.LINK_CHILDREN);
		}
		else {
			link = entry.getLink(_cmisConstants.LINK_ALL_VERSIONS);
		}

		delete(link.getHref().toString());
	}

	private void _delete(String url) throws CMISException {
		ClientResponse clientResponse = _getAbedraClient().delete(url);

		_verify(clientResponse);

		if (ResponseType.select(
				clientResponse.getStatus()) != ResponseType.SUCCESS) {

			throw new CMISException(
				"Error deleting object at " + url + " " +
					clientResponse.getStatusText());
		}
	}

	private AbderaClient _getAbedraClient() throws CMISException {
		try {
			AbderaClient abderaClient = new AbderaClient(_abdera);

			abderaClient.addCredentials(
				null, null, null, _usernamePasswordCredentials);

			return abderaClient;
		}
		catch (Exception e) {
			throw new CMISException(e);
		}
	}

	private String _getCollectionUrl(
		Workspace workspace, String collectionType) {

		for (Collection collection : workspace.getCollections()) {
			String curCollectionType = collection.getAttributeValue(
				_cmisConstants.COLLECTION_TYPE);

			if (collectionType.equals(curCollectionType)) {
				return collection.getHref().toString();
			}
		}

		return null;
	}

	private Entry _getDocument(Entry entry, String title) throws CMISException {
		Link link = entry.getLink(_cmisConstants.LINK_CHILDREN);

		return _getDocument(link.getHref().toString(), title);
	}

	private Entry _getDocument(String url, String title) throws CMISException {
		return _getEntry(url, title, _cmisConstants.BASE_TYPE_DOCUMENT);
	}

	private Entry _getEntry(String url, String title, String baseType)
		throws CMISException {

		ClientResponse clientResponse = _getAbedraClient().get(url);

		_verify(clientResponse);

		Feed feed = (Feed)clientResponse.getDocument().getRoot();

		for (Entry entry : feed.getEntries()) {
			if (entry.getTitle().equals(title)) {
				CMISObject cmisObject = entry.getFirstChild(
					_cmisConstants.OBJECT);

				if (baseType.equals(cmisObject.getBaseType())) {
					return entry;
				}
			}
		}

		return null;
	}

	private Entry _getFolder(Entry entry, String title) throws CMISException {
		Link link = entry.getLink(_cmisConstants.LINK_CHILDREN);

		return _getFolder(link.getHref().toString(), title);
	}

	private Entry _getFolder(String title) throws CMISException {
		return _getFolder(_linkChildrenURL, title);
	}

	private Entry _getFolder(String url, String title) throws CMISException {
		return _getEntry(url, title, _cmisConstants.BASE_TYPE_FOLDER);
	}

	private List<String> _getFolders(Entry entry) throws CMISException {
		List<String> folders = new ArrayList<String>();

		Link link = entry.getLink(_cmisConstants.LINK_CHILDREN);

		String url = link.getHref().toString();

		ClientResponse clientResponse = _getAbedraClient().get(url);

		_verify(clientResponse);

		Feed feed = (Feed)clientResponse.getDocument().getRoot();

		for (Entry curEntry : feed.getEntries()) {
			folders.add(curEntry.getTitle());
		}

		return folders;
	}

	private InputStream _getInputStream(Entry entry) throws CMISException {
		try {
			Link link = entry.getLink(_cmisConstants.LINK_STREAM);

			String url = link.getHref().toString();

			ClientResponse clientResponse = _getAbedraClient().get(url);

			_verify(clientResponse);

			return clientResponse.getInputStream();
		}
		catch (Exception e) {
			throw new CMISException(e);
		}
	}

	private Service _getService() throws CMISException {
		ClientResponse clientResponse = _getAbedraClient().get(
			PropsValues.CMIS_REPOSITORY_URL);

		_verify(clientResponse);

		return (Service)clientResponse.getDocument().getRoot();
	}

	private void _verify(ClientResponse clientResponse) throws CMISException {
		int status = clientResponse.getStatus();
		String statusText = clientResponse.getStatusText();

		if (status >= 300) {
			throw new CMISException(
				"CMIS server returned " + status + " " + statusText);
		}
	}

	private String _verifyRepository() throws Exception {
		Service service = _getService();

		Workspace workspace = service.getWorkspaces().get(0);

		CMISRepositoryInfo cmisRepositoryInfo = workspace.getFirstChild(
			_cmisConstants.REPOSITORY_INFO);

		// CMIS version

		String version = cmisRepositoryInfo.getVersionSupported();

		if (_log.isInfoEnabled()) {
			_log.info(
				"Using CMIS repository " + cmisRepositoryInfo.getProductName() +
					" " + cmisRepositoryInfo.getProductVersion());
			_log.info("CMIS repository supports CMIS version " + version);
		}

		if (!version.equals(_cmisConstants.VERSION)) {
			throw new RuntimeException(
				"CMIS repository is running an unsupported version");
		}

		// Find root folder

		String url = _getCollectionUrl(
			workspace, _cmisConstants.COLLECTION_ROOT_CHILDREN);

		Entry entry = _getEntry(
			url, PropsValues.CMIS_SYSTEM_ROOT_DIR,
			_cmisConstants.BASE_TYPE_FOLDER);

		if (entry == null) {
			entry = _createFolder(url, PropsValues.CMIS_SYSTEM_ROOT_DIR);
		}

		Link link = entry.getLink(_cmisConstants.LINK_CHILDREN);

		_linkChildrenURL = link.getHref().toString();

		return version;
	}

	private static Log _log = LogFactoryUtil.getLog(CMISUtil.class);

	private static CMISUtil _instance = new CMISUtil();

	private Abdera _abdera;
	private CMISConstants _cmisConstants;
	private String _linkChildrenURL;
	private UsernamePasswordCredentials _usernamePasswordCredentials;

}