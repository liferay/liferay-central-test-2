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

import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * <a href="PluginPackage.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public interface PluginPackage {

	public String getArtifactId();

	public String getArtifactURL();

	public String getAuthor();

	public String getChangeLog();

	public String getContext();

	public Properties getDeploymentSettings();

	public String getDownloadURL();

	public String getGroupId();

	public List<License> getLicenses();

	public List<String> getLiferayVersions();

	public String getLongDescription();

	public Date getModifiedDate();

	public String getModuleId();

	public String getName();

	public String getPackageId();

	public String getPageURL();

	public String getRecommendedDeploymentContext();

	public RemotePluginPackageRepository getRepository();

	public String getRepositoryURL();

	public List<Screenshot> getScreenshots();

	public String getShortDescription();

	public List<String> getTags();

	public List<String> getTypes();

	public String getVersion();

	public boolean isLaterVersionThan(PluginPackage pluginPackage);

	public boolean isPreviousVersionThan(PluginPackage pluginPackage);

	public boolean isSameVersionAs(PluginPackage pluginPackage);

	public void setAuthor(String author);

	public void setChangeLog(String changeLog);

	public void setContext(String context);

	public void setDeploymentSettings(Properties properties);

	public void setDownloadURL(String downloadURL);

	public void setLicenses(List<License> licenses);

	public void setLiferayVersions(List<String> liferayVersions);

	public void setLongDescription(String longDescription);

	public void setModifiedDate(Date modifiedDate);

	public void setName(String name);

	public void setPageURL(String pageURL);

	public void setRecommendedDeploymentContext(String deploymentContext);

	public void setRepository(RemotePluginPackageRepository repository);

	public void setScreenshots(List<Screenshot> screenshots);

	public void setShortDescription(String shortDescription);

	public void setTags(List<String> tags);

	public void setTypes(List<String> types);

}