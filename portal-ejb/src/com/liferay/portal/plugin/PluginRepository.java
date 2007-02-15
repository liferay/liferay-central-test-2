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

import com.liferay.portal.kernel.plugin.Plugin;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * <a href="PluginRepository.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class PluginRepository {

	public void addPlugin(Plugin plugin) {
		_artifactURLIndex.put(plugin.getArtifactURL(), plugin);
		_moduleIdIndex.put(plugin.getModuleId(), plugin);
		_addToGroupAndArtifactIndex(
			plugin.getGroupId(), plugin.getArtifactId(), plugin);

		_plugins.add(plugin);
		_tags.addAll(plugin.getTags());
	}

	public Plugin findPluginByArtifactURL(String artifactURL) {
		return (Plugin)_artifactURLIndex.get(artifactURL);
	}

	public Plugin findPluginByModuleId(String moduleId) {
		return (Plugin)_moduleIdIndex.get(moduleId);
	}

	public List getPlugins() {
		return _plugins;
	}

	public Set getTags() {
		return _tags;
	}

	public List findPluginsByGroupIdAndArtifactId(
		String groupId, String artifactId) {
		return (List) _groupAndArtifactIndex.get(
			groupId+ StringPool.SLASH + artifactId);
	}

	public void removePlugin(Plugin plugin) {
		_artifactURLIndex.remove(plugin.getArtifactURL());
		_moduleIdIndex.remove(plugin.getModuleId());
		_removeFromGroupAndArtifactIndex(
			plugin.getGroupId(), plugin.getArtifactId(), plugin.getModuleId());

		_plugins.remove(plugin);
	}

	private void _addToGroupAndArtifactIndex(
		String groupId, String artifactId, Plugin plugin) {
		List plugins = findPluginsByGroupIdAndArtifactId(groupId, artifactId);
		if (plugins == null) {
			plugins = new ArrayList();
			_groupAndArtifactIndex.put(
				groupId+ StringPool.SLASH + artifactId, plugins);
		}
		plugins.add(plugin);
	}

	private void _removeFromGroupAndArtifactIndex(
		String groupId, String artifactId, String moduleId) {
		List plugins = findPluginsByGroupIdAndArtifactId(groupId, artifactId);
		if (plugins != null) {
			Iterator iterator = plugins.iterator();
			while (iterator.hasNext()) {
				Plugin plugin =  (Plugin) iterator.next();
				if (plugin.getModuleId().equals(moduleId)) {
					iterator.remove();
					break;
				}
			}
		}
	}

	private Map _groupAndArtifactIndex = new HashMap();
	private Map _artifactURLIndex = new HashMap();
	private Map _moduleIdIndex = new HashMap();
	private List _plugins = new ArrayList();
	private Set _tags = new TreeSet();

}