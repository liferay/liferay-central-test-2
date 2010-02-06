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

package com.liferay.portal.plugin;

import com.liferay.portal.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.plugin.PluginPackageNameAndContextComparator;
import com.liferay.portal.kernel.plugin.Version;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="LocalPluginPackageRepository.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Jorge Ferrer
 */
public class LocalPluginPackageRepository {

	public LocalPluginPackageRepository() {
	}

	public void addPluginPackage(PluginPackage pluginPackage) {
		if (pluginPackage.getContext() == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Plugin package cannot be registered because it does not " +
						"have an installation context");
			}

			return;
		}

		_pendingPackages.remove(pluginPackage.getContext());
		_pendingPackages.remove(pluginPackage.getModuleId());

		_pluginPackages.remove(pluginPackage.getContext());
		_pluginPackages.put(pluginPackage.getContext(), pluginPackage);
	}

	public PluginPackage getInstallingPluginPackage(String context) {
		return _pendingPackages.get(context);
	}

	public PluginPackage getLatestPluginPackage(
		String groupId, String artifactId) {

		PluginPackage latestPluginPackage = null;

		for (PluginPackage pluginPackage : _pluginPackages.values()) {
			if ((pluginPackage.getGroupId().equals(groupId)) &&
				(pluginPackage.getArtifactId().equals(artifactId)) &&
				((latestPluginPackage == null) ||
					pluginPackage.isLaterVersionThan(latestPluginPackage))) {

				latestPluginPackage = pluginPackage;
			}
		}

		return latestPluginPackage;
	}

	public PluginPackage getPluginPackage(String context) {
		return _pluginPackages.get(context);
	}

	public List<PluginPackage> getPluginPackages() {
		return new ArrayList<PluginPackage>(_pluginPackages.values());
	}

	public List<PluginPackage> getPluginPackages(
		String groupId, String artifactId) {

		List<PluginPackage> pluginPackages = new ArrayList<PluginPackage>();

		for (PluginPackage pluginPackage : _pluginPackages.values()) {
			if (pluginPackage.getGroupId().equals(groupId) &&
				pluginPackage.getArtifactId().equals(artifactId)) {

				pluginPackages.add(pluginPackage);
			}
		}

		return pluginPackages;
	}

	public List<PluginPackage> getSortedPluginPackages() {
		List<PluginPackage> pluginPackages = new ArrayList<PluginPackage>();

		pluginPackages.addAll(_pluginPackages.values());

		return ListUtil.sort(
			pluginPackages, new PluginPackageNameAndContextComparator());
	}

	public void removePluginPackage(PluginPackage pluginPackage)
		throws PortalException {

		_pluginPackages.remove(pluginPackage.getContext());

		Indexer indexer = IndexerRegistryUtil.getIndexer(PluginPackage.class);

		indexer.delete(pluginPackage);
	}

	public void removePluginPackage(String context) {
		_pluginPackages.remove(context);
	}

	public void registerPluginPackageInstallation(PluginPackage pluginPackage) {
		if (pluginPackage.getContext() != null) {
			PluginPackage previousPluginPackage = _pluginPackages.get(
				pluginPackage.getContext());

			if (previousPluginPackage == null) {
				addPluginPackage(pluginPackage);
			}
		}

		String key = pluginPackage.getContext();

		if (key == null) {
			key = pluginPackage.getModuleId();
		}

		_pendingPackages.put(key, pluginPackage);
	}

	public void registerPluginPackageInstallation(String deploymentContext) {
		PluginPackage pluginPackage = getPluginPackage(deploymentContext);

		if (pluginPackage == null) {
			String moduleId =
				deploymentContext + StringPool.SLASH + deploymentContext +
					StringPool.SLASH + Version.UNKNOWN + StringPool.SLASH +
						"war";

			pluginPackage = new PluginPackageImpl(moduleId);

			pluginPackage.setName(deploymentContext);
			pluginPackage.setContext(deploymentContext);
		}

		registerPluginPackageInstallation(pluginPackage);
	}

	public void unregisterPluginPackageInstallation(String context) {
		_pluginPackages.remove(context);
		_pendingPackages.remove(context);
	}

	private static Log _log =
		LogFactoryUtil.getLog(LocalPluginPackageRepository.class);

	private Map<String, PluginPackage> _pluginPackages =
		new HashMap<String, PluginPackage>();
	private Map<String, PluginPackage> _pendingPackages =
		new HashMap<String, PluginPackage>();

}