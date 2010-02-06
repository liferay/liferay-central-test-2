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

package com.liferay.portal.kernel.plugin;

import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * <a href="RemotePluginPackageRepository.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Jorge Ferrer
 */
public class RemotePluginPackageRepository {

	public static final String LOCAL_URL = "LOCAL_URL";

	public static final String SETTING_USE_DOWNLOAD_URL = "use-download-url";

	public RemotePluginPackageRepository(String repositoryURL) {
		_repositoryURL = repositoryURL;
	}

	public void addPluginPackage(PluginPackage pluginPackage) {

		// Avoid duplicates

		PluginPackage existingPackage = _moduleIdIndex.get(
			pluginPackage.getModuleId());

		if (existingPackage != null) {
		   return;
		}

		_artifactURLIndex.put(pluginPackage.getArtifactURL(), pluginPackage);
		_moduleIdIndex.put(pluginPackage.getModuleId(), pluginPackage);
		_addToGroupAndArtifactIndex(
			pluginPackage.getGroupId(), pluginPackage.getArtifactId(),
			pluginPackage);
		_pluginPackages.add(pluginPackage);
		_tags.addAll(pluginPackage.getTags());
	}

	public PluginPackage findPluginByArtifactURL(String artifactURL) {
		return _artifactURLIndex.get(artifactURL);
	}

	public PluginPackage findPluginPackageByModuleId(String moduleId) {
		return _moduleIdIndex.get(moduleId);
	}

	public List<PluginPackage> findPluginsByGroupIdAndArtifactId(
		String groupId, String artifactId) {

		return _groupAndArtifactIndex.get(
			groupId + StringPool.SLASH + artifactId);
	}

	public List<PluginPackage> getPluginPackages() {
		return _pluginPackages;
	}

	public String getRepositoryURL() {
		return _repositoryURL;
	}

	public Properties getSettings() {
		return _settings;
	}

	public Set<String> getTags() {
		return _tags;
	}

	public void removePlugin(PluginPackage pluginPackage) {
		_artifactURLIndex.remove(pluginPackage.getArtifactURL());
		_moduleIdIndex.remove(pluginPackage.getModuleId());
		_removeFromGroupAndArtifactIndex(
			pluginPackage.getGroupId(), pluginPackage.getArtifactId(),
			pluginPackage.getModuleId());
		_pluginPackages.remove(pluginPackage);
	}

	public void setSettings(Properties settings) {
		_settings = settings;
	}

	private void _addToGroupAndArtifactIndex(
		String groupId, String artifactId, PluginPackage pluginPackage) {

		List<PluginPackage> pluginPackages = findPluginsByGroupIdAndArtifactId(
			groupId, artifactId);

		if (pluginPackages == null) {
			pluginPackages = new ArrayList<PluginPackage>();

			_groupAndArtifactIndex.put(
				groupId+ StringPool.SLASH + artifactId, pluginPackages);
		}

		pluginPackages.add(pluginPackage);
	}

	private void _removeFromGroupAndArtifactIndex(
		String groupId, String artifactId, String moduleId) {

		List<PluginPackage> pluginPackages = findPluginsByGroupIdAndArtifactId(
			groupId, artifactId);

		if (pluginPackages != null) {
			Iterator<PluginPackage> itr = pluginPackages.iterator();

			while (itr.hasNext()) {
				PluginPackage pluginPackage = itr.next();

				if (pluginPackage.getModuleId().equals(moduleId)) {
					itr.remove();

					break;
				}
			}
		}
	}

	private Map<String, PluginPackage> _artifactURLIndex =
		new HashMap<String, PluginPackage>();
	private Map<String, List<PluginPackage>> _groupAndArtifactIndex =
		new HashMap<String, List<PluginPackage>>();
	private Map<String, PluginPackage> _moduleIdIndex =
		new HashMap<String, PluginPackage>();
	private List<PluginPackage> _pluginPackages =
		new ArrayList<PluginPackage>();
	private String _repositoryURL;
	private Properties _settings = null;
	private Set<String> _tags = new TreeSet<String>();

}