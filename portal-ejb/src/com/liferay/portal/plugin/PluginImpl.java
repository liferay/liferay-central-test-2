/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * <a href="PluginImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class PluginImpl implements Plugin, Comparable {

	public String getArtifactURL() {
		StringTokenizer st = new StringTokenizer(
				getModuleId(), StringPool.SLASH);
		String groupId = st.nextToken();
		String artifactId = st.nextToken();
		String version = st.nextToken();
		String type = st.nextToken();
		return getRepositoryURL() + StringPool.SLASH + groupId +
				StringPool.SLASH + artifactId + StringPool.SLASH + version +
				StringPool.SLASH + artifactId +	StringPool.DASH + version +
				StringPool.PERIOD + type;
	}

	public String getAuthor() {
		return _author;
	}

	public void setAuthor(String author) {
		_author = author;
	}

	public String getLongDescription() {
		return _longDescription;
	}

	public void setLongDescription(String longDescription) {
		_longDescription = longDescription;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getModuleId() {
		return _moduleId;
	}

	public void setModuleId(String moduleId) {
		_moduleId = moduleId;
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

	public String getShortDescription() {
		return _shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		_shortDescription = shortDescription;
	}

	public List getTags() {
		return _tags;
	}

	public void setTags(List tags) {
		_tags = tags;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public boolean equals(Object o) {

		if (!(o instanceof Plugin)) {
			return false;
		}

		Plugin p = (Plugin) o;

		return new EqualsBuilder()
				.append(getModuleId(), p.getModuleId())
				.append(getRepositoryURL(), p.getRepositoryURL())
				.isEquals();
	}


	public int hashCode() {
		return new HashCodeBuilder().append(getModuleId())
				.append(getRepositoryURL()).hashCode();
	}

	public int compareTo(Object o) {

		if (!(o instanceof Plugin)) {
			return -1;
		}

		Plugin p = (Plugin) o;

		return getName().compareTo(p.getName());
	}

	private String _author;
	private String _name;
	private String _moduleId;
	private String _longDescription;
	private List   _licenses = new ArrayList();
	private List   _liferayVersions  = new ArrayList();
	private String _pageURL;
	private String _repositoryURL;
	private String _shortDescription;
	private List   _tags = new ArrayList();
	private String _type;

}