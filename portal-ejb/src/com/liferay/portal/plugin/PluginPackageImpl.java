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

import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.plugin.PluginPackageRepository;
import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * <a href="PluginPackageImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class PluginPackageImpl implements Comparable, PluginPackage {

	public PluginPackageImpl(String moduleId) {
		_moduleId = new ModuleId(moduleId);
	}

	public String getModuleId() {
		return _moduleId.toString();
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getVersion() {
		return _moduleId.getVersion();
	}

	public boolean isLaterVersionThan(PluginPackage pluginPackage) {
		return _moduleId.isLaterVersionThan(pluginPackage.getVersion());
	}

	public boolean isPreviousVersionThan(PluginPackage pluginPackage) {
		return _moduleId.isPreviousVersionThan(pluginPackage.getVersion());
	}

	public boolean isSameVersionThan(PluginPackage pluginPackage) {
		return _moduleId.isSameVersionThan(pluginPackage.getVersion());
	}

	public String getAuthor() {
		return _author;
	}

	public void setAuthor(String author) {
		_author = author;
	}

	public List getTypes() {
		return _types;
	}

	public void setTypes(List types) {
		_types = types;
	}

	public List getTags() {
		return _tags;
	}

	public void setTags(List tags) {
		_tags = tags;
	}

	public List getLicenses() {
		return _licenses;
	}

	public void setLicenses(List licenses) {
		_licenses = licenses;
	}

	public List getLiferayVersions() {
		return _liferayVersions;
	}

	public void setLiferayVersions(List liferayVersions) {
		_liferayVersions = liferayVersions;
	}

	public String getShortDescription() {
		return _shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		_shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return _longDescription;
	}

	public void setLongDescription(String longDescription) {
		_longDescription = longDescription;
	}

	public List getScreenshots() {
		return _screenshots;
	}

	public void setScreenshots(List screenshots) {
		_screenshots = screenshots;
	}

	public String getPageURL() {
		return _pageURL;
	}

	public void setPageURL(String pageURL) {
		_pageURL = pageURL;
	}

	public String getDownloadURL() {
		String useDownloadURL = getRepository().getSettings().getProperty(
			PluginPackageRepository.SETTING_USE_DOWNLOAD_URL);

		if (!GetterUtil.getBoolean(useDownloadURL, false)) {
			return getArtifactURL();
		}

		return _downloadURL;
	}

	public void setDownloadURL(String downloadURL) {
		_downloadURL = downloadURL;
	}

	public PluginPackageRepository getRepository() {
		return _repository;
	}

	public void setRepository(PluginPackageRepository repository) {
		_repository = repository;
	}

	public String getRepositoryURL() {
		if (_repository != null) {
			return _repository.getRepositoryURL();
		}
		else {
			return PluginPackageRepository.LOCAL_URL; 
		}
	}

	public String getRecommendedWARName() {
		return _recommendedWARName;
	}

	public void setRecommendedWARName(String recommendedWARName) {
		_recommendedWARName = recommendedWARName;
	}

	public String getArtifactURL() {
		return getRepositoryURL() + _moduleId.getArtifactPath();
	}

	public String getArtifactId() {
		return _moduleId.getArtifactId();
	}

	public String getGroupId() {
		return _moduleId.getGroupId();
	}

	public String getWARName() {
		String name = getRecommendedWARName();

		if (Validator.isNull(name)) {
			name = _moduleId.getArtifactWARName();
		}

		return name;
	}

	public int compareTo(Object obj) {
		if (!(obj instanceof PluginPackage)) {
			return -1;
		}

		PluginPackage pluginPackage = (PluginPackage)obj;

		return getName().compareTo(pluginPackage.getName());
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof PluginPackage)) {
			return false;
		}

		PluginPackage pluginPackage = (PluginPackage)obj;

		EqualsBuilder equalsBuilder = new EqualsBuilder();

		equalsBuilder.append(getModuleId(), pluginPackage.getModuleId());
		equalsBuilder.append(
			getRepositoryURL(), pluginPackage.getRepositoryURL());

		return equalsBuilder.isEquals();
	}

	public int hashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();

		hashCodeBuilder.append(getModuleId());
		hashCodeBuilder.append(getRepositoryURL());

		return hashCodeBuilder.hashCode();
	}

	private ModuleId _moduleId;
	private String _name;
	private String _author;
	private List _types;
	private List _tags = new ArrayList();
	private List _licenses = new ArrayList();
	private List _liferayVersions = new ArrayList();
	private String _shortDescription;
	private String _longDescription;
	private List _screenshots = new ArrayList();
	private String _pageURL;
	private String _downloadURL;
	private PluginPackageRepository _repository;
	private String _recommendedWARName;

}