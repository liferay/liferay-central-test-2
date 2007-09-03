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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.plugin.RemotePluginPackageRepository;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.lucene.LuceneFields;
import com.liferay.portal.lucene.LuceneUtil;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.ReleaseInfo;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.util.Html;
import com.liferay.util.Http;
import com.liferay.util.License;
import com.liferay.util.Screenshot;
import com.liferay.util.Time;
import com.liferay.util.Version;
import com.liferay.util.XSSUtil;
import com.liferay.util.lucene.HitsImpl;
import com.liferay.util.xml.XMLSafeReader;

import java.io.IOException;

import java.net.MalformedURLException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Searcher;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="PluginPackageUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 *
 */
public class PluginPackageUtil {

	public static final String REPOSITORY_XML_FILENAME =
		"liferay-plugin-repository.xml";

	public static void endPluginPackageInstallation(String preliminaryContext) {
		_installedPluginPackages.unregisterPluginPackageInstallation(
			preliminaryContext);
	}

	public static List getAllAvailablePluginPackages()
		throws PluginPackageException {

		List plugins = new ArrayList();

		String[] repositoryURLs = getRepositoryURLs();

		for (int i = 0; i < repositoryURLs.length; i++) {
			try {
				RemotePluginPackageRepository repository =
					getRepository(repositoryURLs[i]);

				plugins.addAll(repository.getPluginPackages());
			}
			catch(PluginPackageException ppe) {
				String message = ppe.getMessage();

				if (message.startsWith("Unable to communicate")) {
					if (_log.isWarnEnabled()) {
						_log.warn(message);
					}
				}
				else {
					_log.error(message);
				}
			}
		}

		return plugins;
	}

	public static Collection getAvailableTags() {
		return _availableTagsCache;
	}

	public static List getInstalledPluginPackages() {
		return _installedPluginPackages.getSortedPluginPackages();
	}

	public static PluginPackage getLatestAvailablePluginPackage(
			String groupId, String artifactId)
		throws SystemException {

		List pluginPackages = new ArrayList();

		String[] repositoryURLs = getRepositoryURLs();

		for (int i = 0; i < repositoryURLs.length; i++) {
			RemotePluginPackageRepository repository =
				getRepository(repositoryURLs[i]);

			List curPluginPackages =
				repository.findPluginsByGroupIdAndArtifactId(
					groupId, artifactId);

			if (curPluginPackages != null) {
				pluginPackages.addAll(curPluginPackages);
			}
		}

		return _findLatestVersion(pluginPackages);
	}

	public static PluginPackage getLatestInstalledPluginPackage(
		String groupId, String artifactId) {

		return _installedPluginPackages.getLatestPluginPackage(
			groupId, artifactId);
	}

	public static Date getLastUpdateDate() {
		return _lastUpdateDate;
	}

	public static PluginPackage getPluginPackageByModuleId(
			String moduleId, String repositoryURL)
		throws DocumentException, IOException, PluginPackageException {

		RemotePluginPackageRepository repository = getRepository(repositoryURL);

		return repository.findPluginPackageByModuleId(moduleId);
	}

	public static PluginPackage getPluginPackageByURL(String url)
		throws PluginPackageException {

		String[] repositoryURLs = getRepositoryURLs();

		for (int i = 0; i < repositoryURLs.length; i++) {
			String repositoryURL = repositoryURLs[i];

			try {
				RemotePluginPackageRepository repository =
					getRepository(repositoryURL);

				return repository.findPluginByArtifactURL(url);
			}
			catch (PluginPackageException pe) {
				_log.error("Unable to load repository " + repositoryURL, pe);
			}
		}

		return null;
	}

	public static RemotePluginPackageRepository getRepository(
			String repositoryURL)
		throws PluginPackageException {

		RemotePluginPackageRepository repository =
			(RemotePluginPackageRepository)_repositoryCache.get(repositoryURL);

		if (repository != null) {
			return repository;
		}

		return _loadRepository(repositoryURL);
	}

	public static String[] getRepositoryURLs() throws PluginPackageException {
		try {
			String[] trusted = PrefsPropsUtil.getStringArray(
				PropsUtil.PLUGIN_REPOSITORIES_TRUSTED);
			String[] untrusted = PrefsPropsUtil.getStringArray(
				PropsUtil.PLUGIN_REPOSITORIES_UNTRUSTED);

			return ArrayUtil.append(trusted, untrusted);
		}
		catch (Exception e) {
			throw new PluginPackageException(
				"Unable to read repository list", e);
		}
	}

	public static String[] getSupportedTypes() {
		return PropsUtil.getArray(PropsUtil.PLUGIN_TYPES);
	}

	public static boolean isCurrentVersionSupported(List versions) {
		for (int i = 0; i < versions.size(); i++) {
			Version supportedVersion = Version.getInstance(
				(String)versions.get(i));

			Version currentVersion = Version.getInstance(
				ReleaseInfo.getVersion());

			if (supportedVersion.includes(currentVersion)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isIgnored(PluginPackage pluginPackage)
		throws PortalException, SystemException {

		String packageId = pluginPackage.getPackageId();

		String[] pluginPackagesIgnored = PrefsPropsUtil.getStringArray(
			PropsUtil.PLUGIN_NOTIFICATIONS_PACKAGES_IGNORED);

		for (int i = 0; i < pluginPackagesIgnored.length; i++) {
			String curPluginPackagesIgnored = pluginPackagesIgnored[i];

			if (curPluginPackagesIgnored.endsWith(StringPool.STAR)) {
				String prefix = curPluginPackagesIgnored.substring(
					0, curPluginPackagesIgnored.length() - 2);

				if (packageId.startsWith(prefix)) {
					return true;
				}
			}
			else {
				if (packageId.equals(curPluginPackagesIgnored)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isInstallationInProcess(String context) {
		if (_installedPluginPackages.getInstallingPluginPackage(
				context) != null) {

			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isTrusted(String repositoryURL)
		throws PluginPackageException {

		try {
			String[] trusted = PrefsPropsUtil.getStringArray(
				PropsUtil.PLUGIN_REPOSITORIES_TRUSTED);

			if (ArrayUtil.contains(trusted, repositoryURL)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
			throw new PluginPackageException(
				"Unable to read repository list", e);
		}
	}

	public static boolean isUpdateAvailable()
		throws PortalException, SystemException {

		if (!PrefsPropsUtil.getBoolean(
				PropsUtil.PLUGIN_NOTIFICATIONS_ENABLED)) {

			return false;
		}

		if (_updateAvailable != null) {
			return _updateAvailable.booleanValue();
		}

		Iterator itr = _installedPluginPackages.getPluginPackages().iterator();

		while (itr.hasNext()) {
			PluginPackage pluginPackage = (PluginPackage)itr.next();

			PluginPackage availablePluginPackage = null;

			if (isIgnored(pluginPackage)) {
				continue;
			}

			try {
				availablePluginPackage =
					PluginPackageUtil.getLatestAvailablePluginPackage(
						pluginPackage.getGroupId(),
						pluginPackage.getArtifactId());

				if ((availablePluginPackage != null) &&
					Version.getInstance(
						availablePluginPackage.getVersion()).isLaterVersionThan(
							pluginPackage.getVersion())) {

					_updateAvailable = Boolean.TRUE;

					break;
				}
			}
			catch (Exception e) {
			}
		}

		if (_updateAvailable == null) {
			_updateAvailable = Boolean.FALSE;
		}

		return _updateAvailable.booleanValue();
	}

	public static PluginPackage readPluginPackageXml(String xml)
		throws DocumentException {

		SAXReader reader = SAXReaderFactory.getInstance(false);

		Document doc = reader.read(new XMLSafeReader(xml));

		Element root = doc.getRootElement();

		return readPluginPackageXml(root);
	}

	public static PluginPackage readPluginPackageXml(Element pluginPackageEl) {
		String name = pluginPackageEl.elementText("name");

		if (_log.isDebugEnabled()) {
			_log.debug("Reading pluginPackage definition " + name);
		}

		PluginPackage pluginPackage = new PluginPackageImpl(
			GetterUtil.getString(pluginPackageEl.elementText("module-id")));

		List liferayVersions = _readList(
			pluginPackageEl.element("liferay-versions"), "liferay-version");

		List types = _readList(
			pluginPackageEl.element("types"), "type");

		pluginPackage.setName(_readText(name));
		pluginPackage.setRecommendedDeploymentContext(
			_readText(
				pluginPackageEl.elementText("recommended-deployment-context")));
		pluginPackage.setModifiedDate(
			_readDate(pluginPackageEl.elementText("modified-date")));
		pluginPackage.setAuthor(
			_readText(pluginPackageEl.elementText("author")));
		pluginPackage.setTypes(types);
		pluginPackage.setLicenses(
			_readLicenseList(
				pluginPackageEl.element("licenses"), "license"));
		pluginPackage.setLiferayVersions(liferayVersions);
		pluginPackage.setTags(
			_readList(pluginPackageEl.element("tags"), "tag"));
		pluginPackage.setShortDescription(
			_readText(pluginPackageEl.elementText("short-description")));
		pluginPackage.setLongDescription(
			_readHtml(pluginPackageEl.elementText("long-description")));
		pluginPackage.setChangeLog(
			_readHtml(pluginPackageEl.elementText("change-log")));
		pluginPackage.setScreenshots(
			_readScreenshots(pluginPackageEl.element("screenshots")));
		pluginPackage.setPageURL(
			_readText(pluginPackageEl.elementText("page-url")));
		pluginPackage.setDownloadURL(
			_readText(pluginPackageEl.elementText("download-url")));
		pluginPackage.setDeploymentSettings(
			_readProperties(
				pluginPackageEl.element("deployment-settings"), "setting"));

		return pluginPackage;
	}

	public static void refreshUpdatesAvailableCache() {
		_updateAvailable = null;
	}

	public static void reIndex() throws SystemException {
		try {
			PluginPackageIndexer.cleanIndex();

			Iterator itr = getAllAvailablePluginPackages().iterator();

			while (itr.hasNext()) {
				PluginPackage pluginPackage = (PluginPackage)itr.next();

				_indexPluginPackage(pluginPackage);
			}
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public static RepositoryReport reloadRepositories() throws SystemException {
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
			catch(PluginPackageException pe) {
				report.addError(repositoryURL, pe);

				_log.error(
					"Unable to load repository " + repositoryURL + " " +
						pe.toString());
			}

		}

		reIndex();

		return report;
	}

	public static void registerInstalledPluginPackage(
		PluginPackage pluginPackage) {

		_installedPluginPackages.addPluginPackage(pluginPackage);

		_updateAvailable = null;

		_indexPluginPackage(pluginPackage);
	}

	public static void registerPluginPackageInstallation(
		String preliminaryContext) {

		_installedPluginPackages.registerPluginPackageInstallation(
			preliminaryContext);
	}

	public static Hits search(
			String keywords, String type, String tag, String license,
			String repositoryURL, String status)
		throws SystemException {

		_checkRepositories(repositoryURL);

		Searcher searcher = null;

		try {
			HitsImpl hits = new HitsImpl();

			BooleanQuery contextQuery = new BooleanQuery();

			LuceneUtil.addRequiredTerm(
				contextQuery, LuceneFields.PORTLET_ID,
				PluginPackageIndexer.PORTLET_ID);

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

				if (status.equals(PluginPackageImpl.
						STATUS_NOT_INSTALLED_OR_OLDER_VERSION_INSTALLED)) {

					LuceneUtil.addExactTerm(
						searchQuery, "status",
						PluginPackageImpl.STATUS_NOT_INSTALLED);
					LuceneUtil.addExactTerm(
						searchQuery, "status",
						PluginPackageImpl.STATUS_OLDER_VERSION_INSTALLED);
				}
				else {
					LuceneUtil.addExactTerm(searchQuery, "status", status);
				}

				fullQuery.add(searchQuery, BooleanClause.Occur.MUST);
			}

			searcher = LuceneUtil.getSearcher(CompanyImpl.SYSTEM);

			hits.recordHits(searcher.search(fullQuery), searcher);

			return hits;
		}
		catch (Exception e) {
			return LuceneUtil.closeSearcher(searcher, keywords, e);
		}
	}

	public static void unregisterInstalledPluginPackage(
		PluginPackage pluginPackage) {

		_installedPluginPackages.removePluginPackage(pluginPackage);
	}

	public static void updateInstallingPluginPackage(
		String preliminaryContext, PluginPackage pluginPackage) {

		_installedPluginPackages.unregisterPluginPackageInstallation(
			preliminaryContext);
		_installedPluginPackages.registerPluginPackageInstallation(
			pluginPackage);
	}

	private static void _checkRepositories(String repositoryURL)
		throws PluginPackageException {

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

	private static PluginPackage _findLatestVersion(List pluginPackages) {
		PluginPackage pluginPackage = null;

		Iterator itr = pluginPackages.iterator();

		while (itr.hasNext()) {
			PluginPackage curPluginPackage = (PluginPackage)itr.next();

			if ((pluginPackage == null) ||
				(curPluginPackage.isLaterVersionThan(pluginPackage))) {

				pluginPackage = curPluginPackage;
			}
		}

		return pluginPackage;
	}

	private static void _indexPluginPackage(PluginPackage pluginPackage) {
		PluginPackage installedPluginPackage =
			_installedPluginPackages.getLatestPluginPackage(
				pluginPackage.getGroupId(), pluginPackage.getArtifactId());

		String status = null;
		String installedVersion = null;

		if (installedPluginPackage == null) {
			status = PluginPackageImpl.STATUS_NOT_INSTALLED;
		}
		else {
			installedVersion = installedPluginPackage.getVersion();

			if (installedPluginPackage.isLaterVersionThan(pluginPackage)) {
				status = PluginPackageImpl.STATUS_NEWER_VERSION_INSTALLED;
			}
			else if (installedPluginPackage.isPreviousVersionThan(
				pluginPackage)) {
				status = PluginPackageImpl.STATUS_OLDER_VERSION_INSTALLED;
			}
			else {
				status = PluginPackageImpl.STATUS_SAME_VERSION_INSTALLED;
			}
		}

		try {
			PluginPackageIndexer.updatePluginPackage(
				pluginPackage.getModuleId(), pluginPackage.getName(),
				pluginPackage.getVersion(), pluginPackage.getModifiedDate(),
				pluginPackage.getAuthor(), pluginPackage.getTypes(),
				pluginPackage.getTags(), pluginPackage.getLicenses(),
				pluginPackage.getLiferayVersions(),
				pluginPackage.getShortDescription(),
				pluginPackage.getLongDescription(),
				pluginPackage.getChangeLog(), pluginPackage.getPageURL(),
				pluginPackage.getRepositoryURL(), status, installedVersion);
		}
		catch (Exception e) {
			_log.error("Error reindexing " + pluginPackage.getModuleId(), e);
		}
	}

	private static RemotePluginPackageRepository _loadRepository(
			String repositoryURL)
		throws PluginPackageException {

		RemotePluginPackageRepository repository = null;

		String pluginsXmlURL =
			repositoryURL + StringPool.SLASH + REPOSITORY_XML_FILENAME;

		try {
			HostConfiguration hostConfig = Http.getHostConfig(pluginsXmlURL);

			HttpClient client = Http.getClient(hostConfig);

			GetMethod getFileMethod = new GetMethod(pluginsXmlURL);

			byte[] bytes = null;

			try {
				int responseCode = client.executeMethod(
					hostConfig, getFileMethod);

				if (responseCode != 200) {
					throw new PluginPackageException(
						"Unable to download file " + pluginsXmlURL +
							" because of response code " + responseCode);
				}

				bytes = getFileMethod.getResponseBody();
			}
			finally {
				getFileMethod.releaseConnection();
			}

			if ((bytes != null) && (bytes.length > 0)) {
				repository = _parseRepositoryXml(
					new String(bytes), repositoryURL);

				_repositoryCache.put(repositoryURL, repository);
				_availableTagsCache.addAll(repository.getTags());
				_lastUpdateDate = new Date();
				_updateAvailable = null;

				return repository;
			}
			else {
				_lastUpdateDate = new Date();

				throw new PluginPackageException("Download returned 0 bytes");
			}
		}
		catch (MalformedURLException mue) {
			_repositoryCache.remove(repositoryURL);

			throw new PluginPackageException(
				"Invalid URL " + pluginsXmlURL, mue);
		}
		catch (IOException ioe) {
			_repositoryCache.remove(repositoryURL);

			throw new PluginPackageException(
				"Unable to communicate with repository " + repositoryURL, ioe);
		}
		catch (DocumentException de) {
			_repositoryCache.remove(repositoryURL);

			throw new PluginPackageException(
				"Unable to parse plugin list for repository " + repositoryURL,
				de);
		}
	}

	private static RemotePluginPackageRepository _parseRepositoryXml(
			String xml, String repositoryURL)
		throws DocumentException, IOException {

		List supportedPluginTypes = Arrays.asList(getSupportedTypes());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Loading plugin repository " + repositoryURL + ":\n" + xml);
		}

		RemotePluginPackageRepository pluginPackageRepository =
			new RemotePluginPackageRepository(repositoryURL);

		if (xml == null) {
			return pluginPackageRepository;
		}

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new XMLSafeReader(xml));

		Element root = doc.getRootElement();

		Properties settings = _readProperties(
			root.element("settings"), "setting");

		pluginPackageRepository.setSettings(settings);

		Iterator itr1 = root.elements("plugin-package").iterator();

		while (itr1.hasNext()) {
			Element pluginPackageEl = (Element)itr1.next();

			PluginPackage pluginPackage = readPluginPackageXml(pluginPackageEl);

			if (!isCurrentVersionSupported(
					pluginPackage.getLiferayVersions())) {

				continue;
			}

			Iterator itr2 = pluginPackage.getTypes().iterator();

			boolean containsSupportedTypes = false;

			while (itr2.hasNext()) {
				String type = (String)itr2.next();

				if (supportedPluginTypes.contains(type)) {
					containsSupportedTypes = true;

					break;
				}
			}

			if (!containsSupportedTypes) {
				continue;
			}

			pluginPackage.setRepository(pluginPackageRepository);

			pluginPackageRepository.addPluginPackage(pluginPackage);

			_indexPluginPackage(pluginPackage);
		}

		return pluginPackageRepository;
	}

	private static Date _readDate(String text) {
		if (Validator.isNotNull(text)) {
			DateFormat dateFormat = new SimpleDateFormat(Time.RFC822_FORMAT);

			try {
				return dateFormat.parse(text);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to parse date " + text);
				}
			}
		}

		return new Date();
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

	private static List _readList(Element parent, String childTagName) {
		List result = new ArrayList();

		if (parent != null) {
			Iterator itr = parent.elements(childTagName).iterator();

			while (itr.hasNext()) {
				Element element = (Element)itr.next();

				result.add(element.getText());
			}
		}

		return result;
	}

	private static Properties _readProperties(
		Element parent, String childTagName) {

		Properties result = new Properties();

		if (parent != null) {
			Iterator itr = parent.elements(childTagName).iterator();

			while (itr.hasNext()) {
				Element tagEl = (Element)itr.next();

				result.setProperty(
					tagEl.attribute("name").getValue(),
					tagEl.attribute("value").getValue());
			}
		}

		return result;
	}

	private static List _readScreenshots(Element parent) {
		List result = new ArrayList();

		if (parent != null) {
			List screenshots = parent.elements("screenshot");

			Iterator itr = screenshots.iterator();

			while (itr.hasNext()) {
				Element screenshotEl = (Element)itr.next();

				Screenshot screenshot = new Screenshot();

				screenshot.setThumbnailURL(
					screenshotEl.element("thumbnail-url").getText());
				screenshot.setLargeImageURL(
					screenshotEl.element("large-image-url").getText());

				result.add(screenshot);
			}
		}

		return result;
	}

	private static String _readText(String text) {
		return Html.stripHtml(GetterUtil.getString(text));
	}

	private static Log _log = LogFactory.getLog(PluginPackageUtil.class);

	private static LocalPluginPackageRepository _installedPluginPackages =
		new LocalPluginPackageRepository();
	private static Map _repositoryCache = new HashMap();
	private static Set _availableTagsCache = new TreeSet();
	private static Date _lastUpdateDate;
	private static Boolean _updateAvailable;

}