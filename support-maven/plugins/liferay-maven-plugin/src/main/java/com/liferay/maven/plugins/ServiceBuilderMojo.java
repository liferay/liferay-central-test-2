/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.maven.plugins;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.tools.servicebuilder.ServiceBuilder;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.SystemProperties;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.model.Dependency;

/**
 * <a href="ServiceBuilderMojo.java.html"><b><i>View Source</i></b></a>
 *
 * Builds Liferay Service Builder services.
 * 
 * @author Mika Koivisto
 * @author Thiago Moreira
 * @goal   build-service
 * @phase  process-sources
 */
public class ServiceBuilderMojo extends AbstractMojo {

	public void execute() throws MojoExecutionException {
		try {
			doExecute();
		}
		catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	protected void doExecute() throws Exception {
		File inputFile = new File(serviceFileName);

		if (!inputFile.exists()) {
			getLog().warn(
				"No service.xml file (" + inputFile.getAbsolutePath() + 
					") found! Skipping service generation!");
			return;
		}

		getLog().info("Building service from " + serviceFileName);

		PropsUtil.set("spring.configs", "META-INF/service-builder-spring.xml");
		PropsUtil.set(PropsKeys.RESOURCE_ACTIONS_READ_PORTLET_RESOURCES, "false");

		InitUtil.initWithSpring();

		new ServiceBuilder(
			serviceFileName, hbmFileName, ormFileName, modelHintsFileName,
			springFileName,	springBaseFileName,
			springDynamicDataSourceFileName, springHibernateFileName,
			springInfrastructureFileName, springShardDataSourceFileName,
			apiDir, implDir, jsonFileName, null, sqlDir, sqlFileName,
			sqlIndexesFileName, sqlIndexesPropertiesFileName,
			sqlSequencesFileName, autoNamespaceTables, beanLocatorUtil,
			propsUtil, pluginName, null);
	}

	/**
	 * Location of the service file.
	 * 
	 * @parameter default-value="${basedir}/src/main/webapp/WEB-INF/service.xml"
	 * @required
	 */
	private String serviceFileName;

	/**
	 * Location of the portlet hibernate file.
	 * 
	 * @parameter default-value="${basedir}/src/main/resources/META-INF/portlet-hbm.xml"
	 * @required
	 */
	private String hbmFileName;

	/**
	 * Location of the portlet orm file.
	 * 
	 * @parameter default-value="${basedir}/src/main/resources/META-INF/portlet-orm.xml"
	 * @required
	 */
	private String ormFileName;

	/**
	 * Location of the portlet model hints file.
	 * 
	 * @parameter default-value="${basedir}/src/main/resources/META-INF/portlet-model-hints.xml"
	 * @required
	 */
	private String modelHintsFileName;

	/**
	 * Location of the portlet spring file.
	 * 
	 * @parameter default-value="${basedir}/src/main/resources/META-INF/portlet-spring.xml"
	 * @required
	 */
	private String springFileName;

	/**
	 * Location of the base spring file.
	 * 
	 * @parameter default-value="${basedir}/src/main/resources/META-INF/base-spring.xml"
	 * @required
	 */
	private String springBaseFileName;

	/**
	 * Location of the dynamic data source spring file.
	 * 
	 * @parameter default-value="${basedir}/src/main/resources/META-INF/dynamic-data-source-spring.xml"
	 * @required
	 */
	private String springDynamicDataSourceFileName;

	/**
	 * Location of the hibernate spring file.
	 * 
	 * @parameter default-value="${basedir}/src/main/resources/META-INF/hibernate-spring.xml"
	 * @required
	 */
	private String springHibernateFileName;

	/**
	 * Location of the infrastructure spring file.
	 * 
	 * @parameter default-value="${basedir}/src/main/resources/META-INF/infrastructure-spring.xml"
	 * @required
	 */
	private String springInfrastructureFileName;

	/**
	 * Location of the shard data source spring file.
	 * 
	 * @parameter default-value="${basedir}/src/main/resources/META-INF/shard-data-source-spring.xml"
	 * @required
	 */
	private String springShardDataSourceFileName;

	/**
	 * Location of the generated api classes directory.
	 * 
	 * @parameter default-value="${basedir}/src/main/java"
	 * @required
	 */
	private String apiDir;

	/**
	 * Location of the generated implementation classes directory.
	 * 
	 * @parameter default-value="${basedir}/src/main/java"
	 * @required
	 */
	private String implDir;

	/**
	 * Name of the service.js file.
	 * 
	 * @parameter default-value="${basedir}/src/main/webapp/html/js/liferay/service.js"
	 * @required
	 */
	private String jsonFileName;

	/**
	 * Location of the generated sql directory.
	 * 
	 * @parameter default-value="${basedir}/src/main/webapp/WEB-INF/sql"
	 * @required
	 */
	private String sqlDir;

	/**
	 * Name of the tables.sql file.
	 * 
	 * @parameter default-value="tables.sql"
	 * @required
	 */
	private String sqlFileName;

	/**
	 * Name of the indexes.sql file.
	 * 
	 * @parameter default-value="indexes.sql"
	 * @required
	 */
	private String sqlIndexesFileName;

	/**
	 * Name of the indexes.properties file.
	 * 
	 * @parameter default-value="indexes.properties"
	 * @required
	 */
	private String sqlIndexesPropertiesFileName;

	/**
	 * Name of the sequences.sql file.
	 * 
	 * @parameter default-value="sequences.sql"
	 * @required
	 */
	private String sqlSequencesFileName;

	/**
	 * Set the auto creation of namespaced tables
	 * 
	 * @parameter default-value="true"
	 * @required
	 */
	private boolean autoNamespaceTables;

	/**
	 * Set the bean locator class 
	 * 
	 * @parameter default-value="com.liferay.util.bean.PortletBeanLocatorUtil"
	 * @required
	 */
	private String beanLocatorUtil;

	/**
	 * Set the system property class 
	 * 
	 * @parameter default-value="com.liferay.util.service.ServiceProps"
	 * @required
	 */
	private String propsUtil;

	/**
	 * Set the plugin name
	 * 
	 * @parameter expression="${project.artifactId}"
	 * @required
	 */
	private String pluginName;

}
