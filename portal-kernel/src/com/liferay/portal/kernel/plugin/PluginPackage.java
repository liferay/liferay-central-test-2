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

package com.liferay.portal.kernel.plugin;

import java.util.List;

/**
 * <a href="PluginPackage.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public interface PluginPackage {

	public String getModuleId();

	public String getName();

	public void setName(String name);

	public String getAuthor();

	public void setAuthor(String author);

	public List getTypes();

	public void setTypes(List types);

	public List getTags();

	public void setTags(List tags);

	public List getLicenses();

	public void setLicenses(List licenses);

	public List getLiferayVersions();

	public void setLiferayVersions(List liferayVersions);

	public String getShortDescription();

	public void setShortDescription(String shortDescription);

	public String getLongDescription();

	public void setLongDescription(String longDescription);

	public void setScreenshots(List screenshots);

	public List getScreenshots();

	public String getPageURL();

	public void setPageURL(String pageURL);

	public String getDownloadURL();

	public void setDownloadURL(String downloadURL);

	public String getRepositoryURL();

	public PluginPackageRepository getRepository();

	public void setRepository(PluginPackageRepository repository);

	public String getRecommendedWARName();

	public void setRecommendedWARName(String recommendedWARName);

	public String getArtifactURL();

	public String getArtifactId();

	public String getGroupId();

	public String getWARName();

	public String getVersion();

	public boolean isLaterVersionThan(PluginPackage pluginPackage);

	public boolean isPreviousVersionThan(PluginPackage pluginPackage);

	public boolean isSameVersionThan(PluginPackage pluginPackage);

}