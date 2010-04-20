package com.liferay.portal.deploy.auto.exploded.tomcat;

import java.io.File;

import com.liferay.portal.deploy.auto.HookAutoDeployer;
import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.plugin.PluginPackage;

public class HookExplodedTomcatDeployer extends HookAutoDeployer implements ExplodedTomcatDeployer {

	public void explodedTomcatDeploy(File contextFile, File webAppDir,
			PluginPackage pluginPackage) throws AutoDeployException {

		try {
			deployDirectory(
				webAppDir, getDisplayName(contextFile), false, pluginPackage);
		}
		catch (Exception e) {
			throw new AutoDeployException(e);
		}

	}

}
