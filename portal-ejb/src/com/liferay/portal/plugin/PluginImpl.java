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
import com.liferay.util.Validator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * <a href="PluginImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class PluginImpl implements Comparable, Plugin {

	public PluginImpl(String moduleId) {
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

	public boolean isLaterVersionThan(Plugin plugin) {
		return _moduleId.isLaterVersionThan(plugin.getVersion());
	}

	public boolean isPreviousVersionThan(Plugin plugin) {
		return _moduleId.isPreviousVersionThan(plugin.getVersion());
	}

	public boolean isSameVersionThan(Plugin plugin) {
		return _moduleId.isSameVersionThan(plugin.getVersion());
	}

	public String getAuthor() {
		return _author;
	}

	public void setAuthor(String author) {
		_author = author;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
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

	public List getScreenshotURLs() {
		return _screenshotURLs;
	}

	public void setScreenshotURLs(List screenshotURLs) {
		_screenshotURLs = screenshotURLs;
	}

	public String getPageURL() {
		return _pageURL;
	}

	public void setPageURL(String pageURL) {
		_pageURL = pageURL;
	}

	public String getRepositoryURL() {
		return _repositoryURL;
	}

	public void setRepositoryURL(String repositoryURL) {
		_repositoryURL = repositoryURL;
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
		if (!(obj instanceof Plugin)) {
			return -1;
		}

		Plugin plugin = (Plugin)obj;

		return getName().compareTo(plugin.getName());
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Plugin)) {
			return false;
		}

		Plugin plugin = (Plugin)obj;

		EqualsBuilder equalsBuilder = new EqualsBuilder();

		equalsBuilder.append(getModuleId(), plugin.getModuleId());
		equalsBuilder.append(getRepositoryURL(), plugin.getRepositoryURL());

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
	private String _type;
	private List _tags = new ArrayList();
	private List _licenses = new ArrayList();
	private List _liferayVersions = new ArrayList();
	private String _shortDescription;
	private String _longDescription;
	private List _screenshotURLs = new ArrayList();
	private String _pageURL;
	private String _repositoryURL;
	private String _recommendedWARName;

}