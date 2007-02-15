/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.plugin;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.plugin.Plugin;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.lucene.LuceneFields;
import com.liferay.portal.lucene.LuceneUtil;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.ReleaseInfo;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.util.GetterUtil;
import com.liferay.util.Html;
import com.liferay.util.Http;
import com.liferay.util.License;
import com.liferay.util.Validator;
import com.liferay.util.Version;
import com.liferay.util.XSSUtil;
import com.liferay.util.lucene.HitsImpl;
import com.liferay.util.xml.XMLSafeReader;

import java.io.IOException;

import java.net.MalformedURLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Searcher;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="PluginUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class PluginUtil {

	public static List getAllPlugins()
		throws PluginException {

		List plugins = new ArrayList();

		String[] repositoryURLs = getRepositoryURLs();

		for (int i = 0; i < repositoryURLs.length; i++) {
			try {
				PluginRepository repository = getRepository(repositoryURLs[i]);

				Collection repoPlugins = repository.getPlugins();
				plugins.addAll(repoPlugins);
			}
			catch(PluginException pe) {
				_log.error(
					"Unable to load repository " + repositoryURLs[i], pe);
			}
		}

		return plugins;
	}

	public static Collection getAvailableTags() {
		return _availableTagsCache;
	}

	public static Plugin getInstalledVersion(
		String groupId, String artifactId) {
		List pluginList = _installedPlugins.findPluginsByGroupIdAndArtifactId(
			groupId, artifactId);

		return (pluginList == null)?null:(Plugin) pluginList.get(0);
	}

	public static Date getLastUpdateDate() {
		return _lastUpdateDate;
	}

	public static Plugin getPluginById(String moduleId, String repositoryURL)
		throws DocumentException, IOException, PluginException {

		return getRepository(repositoryURL).findPluginByModuleId(moduleId);
	}

	public static Plugin getPluginByURL(String url) throws PluginException {
		String[] repositoryURLs = getRepositoryURLs();

		for (int i = 0; i < repositoryURLs.length; i++) {
			String repositoryURL = repositoryURLs[i];

			try {
				PluginRepository repository = getRepository(repositoryURL);

				return repository.findPluginByArtifactURL(url);
			}
			catch (PluginException pe) {
				_log.error("Unable to load repository " + repositoryURL, pe);
			}
		}

		return null;
	}

	public static PluginRepository getRepository(String repositoryURL)
		throws PluginException {

		PluginRepository repository =
			(PluginRepository)_repositoryCache.get(repositoryURL);

		if (repository != null) {
			return repository;
		}

		return _loadRepository(repositoryURL);
	}

	public static String[] getRepositoryURLs() throws PluginException {
		try {
			return PrefsPropsUtil.getStringArray(PropsUtil.PLUGIN_REPOSITORIES);
		}
		catch (Exception e) {
			throw new PluginException("Unable to read repository list", e);
		}
	}

	public static String[] getSupportedTypes() {
		return PropsUtil.getArray(PropsUtil.PLUGIN_TYPES);
	}

	public static void registerPlugin(String moduleId) {
		Plugin plugin = new PluginImpl(moduleId);
		_installedPlugins.addPlugin(plugin);
	}

	public static void reIndex(String[] ids) throws SystemException {
		try {
			Iterator itr = getAllPlugins().iterator();

			while (itr.hasNext()) {
				Plugin plugin = (Plugin)itr.next();

				indexPlugin(plugin);
			}
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e2) {
			throw new SystemException(e2);
		}
	}

	public static RepositoryReport reloadRepositories() throws PluginException {
		if (_log.isInfoEnabled()) {
			_log.info("Reloading repositories");
		}

		RepositoryReport report = new RepositoryReport();

		String[] repositoryURLs = getRepositoryURLs();

		for (int i = 0; i < repositoryURLs.length; i++) {
			String repositoryURL = repositoryURLs[i];

			try {
				_loadRepository(repositoryURL);

				report.addSuccess(repositoryURL);
			}
			catch(PluginException pe) {
				report.addError(repositoryURL, pe);

				_log.error(
					"Unable to load repository " + repositoryURL + " " +
						pe.toString());
			}
		}
		return report;
	}

	public static Hits search(
			String keywords, String type, String tag, String license,
			String repositoryURL, String status)
		throws SystemException {

		_checkRepositories(repositoryURL);

		try {
			HitsImpl hits = new HitsImpl();

			BooleanQuery contextQuery = new BooleanQuery();

			LuceneUtil.addRequiredTerm(
				contextQuery, LuceneFields.PORTLET_ID,
				PluginIndexer.PORTLET_ID);

			if (Validator.isNotNull(type)) {
				LuceneUtil.addRequiredTerm(contextQuery, "type", type);
			}

			BooleanQuery fullQuery = new BooleanQuery();

			fullQuery.add(contextQuery, BooleanClause.Occur.MUST);

			if (Validator.isNotNull(keywords)) {
				BooleanQuery searchQuery = new BooleanQuery();

				LuceneUtil.addTerm(searchQuery, LuceneFields.TITLE, keywords);
				LuceneUtil.addTerm(searchQuery, LuceneFields.CONTENT, keywords);

				fullQuery.add(searchQuery, BooleanClause.Occur.MUST);
			}

			if (Validator.isNotNull(type)) {
				BooleanQuery searchQuery = new BooleanQuery();

				LuceneUtil.addExactTerm(searchQuery, "type", type);

				fullQuery.add(searchQuery, BooleanClause.Occur.MUST);
			}

			if (Validator.isNotNull(tag)) {
				BooleanQuery searchQuery = new BooleanQuery();

				LuceneUtil.addExactTerm(searchQuery, "tag", tag);

				fullQuery.add(searchQuery, BooleanClause.Occur.MUST);
			}

			if (Validator.isNotNull(repositoryURL)) {
				BooleanQuery searchQuery = new BooleanQuery();

				LuceneUtil.addExactTerm(
					searchQuery, "repositoryURL", repositoryURL);

				fullQuery.add(searchQuery, BooleanClause.Occur.MUST);
			}

			if (Validator.isNotNull(license)) {
				BooleanQuery searchQuery = new BooleanQuery();

				LuceneUtil.addExactTerm(searchQuery, "license", license);

				fullQuery.add(searchQuery, BooleanClause.Occur.MUST);
			}

			if (Validator.isNotNull(status) && !status.equals("all")) {
				BooleanQuery searchQuery = new BooleanQuery();

				if (status.equals("notInstalledOrNewVersion")) {
					LuceneUtil.addExactTerm(
						searchQuery, "status", "notInstalled");
					LuceneUtil.addExactTerm(
						searchQuery, "status", "newVersion");
				}
				else {
					LuceneUtil.addExactTerm(searchQuery, "status", status);
				}

				fullQuery.add(searchQuery, BooleanClause.Occur.MUST);
			}

			Searcher searcher = LuceneUtil.getSearcher(CompanyImpl.SYSTEM);

			hits.recordHits(searcher.search(fullQuery));

			return hits;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (ParseException pe) {
			_log.error("Parsing keywords " + keywords, pe);

			return new HitsImpl();
		}
	}

	public static void unregisterPlugin(String moduleId) {
		Plugin plugin = new PluginImpl(moduleId);
		_installedPlugins.removePlugin(plugin);
	}

	private static PluginRepository _loadRepository(String repositoryURL)
		throws PluginException {

		PluginRepository repository = null;

		String pluginsXmlURL =
			repositoryURL + StringPool.SLASH + _PLUGINS_XML_FILENAME;

		try {
			HostConfiguration hostConfig = Http.getHostConfig(pluginsXmlURL);

			HttpClient client = Http.getClient(hostConfig);

			GetMethod getFileMethod = new GetMethod(pluginsXmlURL);

			byte[] bytes = null;

			try {
				int responseCode = client.executeMethod(
					hostConfig, getFileMethod);

				if (responseCode != 200) {
					throw new PluginException(
						"Unable to download file " + pluginsXmlURL +
							" because of response code " + responseCode);
				}

				bytes = getFileMethod.getResponseBody();
			}
			finally {
				getFileMethod.releaseConnection();
			}

			if ((bytes != null) && (bytes.length > 0)) {
				repository = _parsePluginsXml(new String(bytes), repositoryURL);

				_repositoryCache.put(repositoryURL, repository);
				_availableTagsCache.addAll(repository.getTags());
				_lastUpdateDate = new Date();

				return repository;
			}
			else {
				_lastUpdateDate = new Date();

				throw new PluginException("Download returned 0 bytes");
			}
		}
		catch (MalformedURLException mue) {
			_repositoryCache.remove(repositoryURL);

			throw new PluginException("Invalid URL " + pluginsXmlURL, mue);
		}
		catch (IOException ioe) {
			_repositoryCache.remove(repositoryURL);

			throw new PluginException(
				"Unable to communicate with repository " + repositoryURL, ioe);
		}
		catch (DocumentException de) {
			_repositoryCache.remove(repositoryURL);

			throw new PluginException(
				"Unable to parse plugin list for repository " + repositoryURL,
				de);
		}
	}

	private static PluginRepository _parsePluginsXml(
			String xml, String repositoryURL)
		throws DocumentException, IOException {

		List supportedPluginTypes = Arrays.asList(getSupportedTypes());

		if (_log.isDebugEnabled()) {
			_log.debug("Plugins " + xml);
		}

		PluginRepository plugins = new PluginRepository();

		if (xml == null) {
			return plugins;
		}

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new XMLSafeReader(xml));

		Element root = doc.getRootElement();

		Iterator itr = root.elements().iterator();

		while (itr.hasNext()) {
			Element pluginEl = (Element)itr.next();

			String name = pluginEl.elementText("name");

			if (_log.isDebugEnabled()) {
				_log.debug("Reading plugin definition " + name);
			}

			Plugin plugin = new PluginImpl(
				GetterUtil.getString(pluginEl.elementText("module-id")));

			List liferayVersions = _readList(
				pluginEl.element("liferay-versions"), "liferay-version");

			String type = GetterUtil.getString(pluginEl.elementText("type"));

			if (!_isCurrentVersionSupported(liferayVersions) ||
				!supportedPluginTypes.contains(type)) {

				continue;
			}

			plugin.setName(_readText(name));
			plugin.setAuthor(_readText(pluginEl.elementText("author")));
			plugin.setType(type);
			plugin.setLicenses(
				_readLicenseList(pluginEl.element("licenses"), "license"));
			plugin.setLiferayVersions(liferayVersions);
			plugin.setTags(_readList(pluginEl.element("tags"), "tag"));
			plugin.setShortDescription(
				_readText(pluginEl.elementText("short-description")));
			plugin.setLongDescription(
				_readHtml(pluginEl.elementText("long-description")));
			plugin.setScreenshotURLs(
				_readList(pluginEl.element("screenshots"), "screenshot-url"));
			plugin.setPageURL(_readText(pluginEl.elementText("page-url")));
			plugin.setRepositoryURL(repositoryURL);
			plugin.setRecommendedWARName(
				_readText(pluginEl.elementText("recommended-war-name")));

			plugins.addPlugin(plugin);

			indexPlugin(plugin);
		}

		return plugins;
	}

	private static void indexPlugin(Plugin plugin) {
		String status;
		Plugin installedPlugin = getInstalledVersion(
			plugin.getGroupId(), plugin.getArtifactId());
		if (installedPlugin == null) {
			status = "notInstalled";
		} else if (installedPlugin.isLaterVersionThan(plugin)) {
			status = "laterVersionInstalled";
		} else if (installedPlugin.isPreviousVersionThan(plugin)) {
			status = "newVersion";
		} else {
			status = "alreadyInstalled";
		}

		try {
			PluginIndexer.updatePlugin(
				plugin.getModuleId(), plugin.getName(), plugin.getVersion(),
				plugin.getAuthor(), plugin.getType(), plugin.getTags(),
				plugin.getLicenses(), plugin.getLiferayVersions(),
				plugin.getShortDescription(), plugin.getLongDescription(),
				plugin.getPageURL(), plugin.getRepositoryURL(), status);
		}
		catch (Exception e) {
			_log.error("Error reindexing " + plugin.getModuleId(), e);
		}
	}

	private static boolean _isCurrentVersionSupported(List versions) {
		for (int i = 0; i < versions.size(); i++) {
			Version supportedVersion = new Version((String)versions.get(i));

			Version currentVersion = new Version(ReleaseInfo.getVersion());

			if (supportedVersion.includes(currentVersion)) {
				return true;
			}
		}

		return false;
	}

	private static String _readHtml(String text) {
		return XSSUtil.strip(GetterUtil.getString(text));
	}

	private static List _readLicenseList(Element parent, String childTagName) {
		List result = new ArrayList();

		Iterator itr = parent.elements(childTagName).iterator();

		while (itr.hasNext()) {
			Element tagEl = (Element)itr.next();

			License license = new License();

			license.setName(tagEl.getText());

			Attribute osiApproved = tagEl.attribute("osi-approved");

			if (osiApproved != null) {
				license.setOsiApproved(
					GetterUtil.getBoolean(osiApproved.getText()));
			}

			Attribute url = tagEl.attribute("url");

			if (url != null) {
				license.setUrl(url.getText());
			}

			result.add(license);
		}

		return result;
	}

	private static void _checkRepositories(String repositoryURL)
		throws PluginException {

		String[] repositoryURLs = null;

		if (Validator.isNotNull(repositoryURL)) {
			repositoryURLs = new String[] {repositoryURL};
		}
		else {
			repositoryURLs = getRepositoryURLs();
		}

		for (int i = 0; i < repositoryURLs.length; i++) {
			getRepository(repositoryURLs[i]);
		}
	}

	private static List _readList(Element parent, String childTagName) {
		List result = new ArrayList();

		if (parent != null) {
			Iterator itr = parent.elements(childTagName).iterator();

			while (itr.hasNext()) {
				Element tagEl = (Element)itr.next();

				result.add(tagEl.getText());
			}
		}

		return result;
	}

	private static String _readText(String text) {
		return Html.stripHtml(GetterUtil.getString(text));
	}

	private static final String _PLUGINS_XML_FILENAME = "liferay-plugins.xml";

	private static Log _log = LogFactory.getLog(PluginUtil.class);

	private static PluginRepository _installedPlugins = new PluginRepository();
	private static Map _repositoryCache = new HashMap();
	private static Set _availableTagsCache = new TreeSet();
	private static Date _lastUpdateDate = null;

}