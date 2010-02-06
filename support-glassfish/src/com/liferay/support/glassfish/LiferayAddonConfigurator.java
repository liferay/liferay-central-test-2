/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.support.glassfish;

import com.sun.appserv.addons.AddonException;
import com.sun.appserv.addons.AddonVersion;
import com.sun.appserv.addons.ConfigurationContext.ConfigurationType;
import com.sun.appserv.addons.ConfigurationContext;
import com.sun.appserv.addons.Configurator;
import com.sun.appserv.addons.InstallationContext;
import com.sun.jbi.management.internal.support.JarFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.Properties;

/**
 * <a href="LiferayAddonConfigurator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raju Uppalapati
 * @author Brian Wing Shun Chan
 */
public class LiferayAddonConfigurator implements Configurator {

	public void configure(ConfigurationContext configurationContext)
		throws AddonException {

		try {
			doConfigure(configurationContext);
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new AddonException(e);
		}
	}

	public void disable(ConfigurationContext configurationContext) {
	}

	public void enable(ConfigurationContext configurationContext) {
	}

	public void unconfigure(ConfigurationContext configurationContext)
		throws AddonException {

		try {
			doUnconfigure(configurationContext);
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new AddonException(e);
		}
	}

	public void upgrade(
		ConfigurationContext configurationContext, AddonVersion addonVersion) {
	}

	protected void doConfigure(ConfigurationContext configurationContext)
		throws Exception {

		InstallationContext installationContext =
			configurationContext.getInstallationContext();

		String rootDir =
			installationContext.getInstallationDirectory().getAbsolutePath();
		String domainDir =
			configurationContext.getDomainDirectory().getAbsolutePath();

		File tmpDir = LiferayAddonUtil.getTmpDir();

		JarFactory jarFactory = new JarFactory(tmpDir.getAbsolutePath());

		File liferayConfiguratorJar = new File(
			rootDir + "/lib/addons/liferay_configurator.jar");

		jarFactory.unJar(liferayConfiguratorJar);

		LiferayAddonUtil.copyFile(
			tmpDir + "/lportal.properties",
			domainDir + "/config/lportal.properties");

		LiferayAddonUtil.copyFile(
			tmpDir + "/lportal.script",
			domainDir + "/config/lportal.script");

		StringBuffer jarFiles = new StringBuffer();
		StringBuffer warFiles = new StringBuffer();

		File[] files = tmpDir.listFiles();

		for (int i = 0; i < files.length; i++) {
			File file = files[i];

			String name = file.getName().toLowerCase();

			if (name.endsWith(".jar")) {
				LiferayAddonUtil.copyFile(
					file, new File(domainDir + "/lib/" + file.getName()));

				jarFiles.append(file.getName());
				jarFiles.append(":");
			}
			else if (name.endsWith(".war")) {
				LiferayAddonUtil.copyFile(
					file,
					new File(domainDir + "/autodeploy/" + file.getName()));

				warFiles.append(file.getName());
				warFiles.append(":");
			}
		}

		Properties props = new Properties();

		props.setProperty("jar.files", jarFiles.toString());
		props.setProperty("war.files", warFiles.toString());

		File propsFile = new File(domainDir + "/lib/liferay_addon.properties");

		FileOutputStream fos = new FileOutputStream(propsFile);

		props.store(fos, "");

		fos.close();
	}

	protected void doUnconfigure(ConfigurationContext configurationContext)
		throws Exception {

		String domainDir =
			configurationContext.getDomainDirectory().getAbsolutePath();

		Properties props = new Properties();

		File propsFile = new File(domainDir + "/lib/liferay_addon.properties");

		FileInputStream fis = new FileInputStream(propsFile);

		props.load(fis);

		fis.close();

		String[] jarFiles = props.getProperty("jar.files", "").split(":");

		for (int i = 0; i < jarFiles.length; i++) {
			File file = new File(domainDir + "/lib/" + jarFiles[i]);

			if (file.exists() && file.isFile()) {
				file.delete();
			}
		}

		String[] warFiles = props.getProperty("war.files", "").split(":");

		for (int i = 0; i < warFiles.length; i++) {
			File file = new File(domainDir + "/autodeploy/" + warFiles[i]);

			if (file.exists() && file.isFile()) {
				file.delete();
			}
		}

		propsFile.delete();

		ConfigurationType ct = configurationContext.getConfigurationType();

		if (ct == ConfigurationType.DAS) {
		}
	}

}