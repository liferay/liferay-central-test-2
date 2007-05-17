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

package com.liferay.portlet.plugininstaller.action;

import com.liferay.portal.events.GlobalStartupAction;
import com.liferay.portal.kernel.deploy.auto.AutoDeployDir;
import com.liferay.portal.kernel.deploy.auto.AutoDeployUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.lastmodified.LastModifiedCSS;
import com.liferay.portal.lastmodified.LastModifiedJavaScript;
import com.liferay.portal.plugin.PluginPackageUtil;
import com.liferay.portal.plugin.RepositoryReport;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.spring.hibernate.CacheRegistry;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.ClusterPool;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebCachePool;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.admin.util.OmniadminUtil;
import com.liferay.util.FileUtil;
import com.liferay.util.Http;
import com.liferay.util.ParamUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.ProgressInputStream;
import com.liferay.util.servlet.SessionErrors;
import com.liferay.util.servlet.SessionMessages;
import com.liferay.util.servlet.UploadException;
import com.liferay.util.servlet.UploadPortletRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="InstallPluginAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class InstallPluginAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		long userId = PortalUtil.getUserId(req);

		if (!OmniadminUtil.isOmniadmin(userId)) {
			SessionErrors.add(req, PrincipalException.class.getName());

			setForward(req, "portlet.admin.error");

			return;
		}

		String cmd = ParamUtil.getString(req, Constants.CMD);

		if (cmd.equals("deployConfiguration")) {
			deployConfiguration(req);
		}
		else if (cmd.equals("localDeploy")) {
			localDeploy(req);
		}
		else if (cmd.equals("reloadRepositories")) {
			reloadRepositories(req);
		}
		else if (cmd.equals("remoteDeploy")) {
			remoteDeploy(req);
		}

		sendRedirect(req, res);
	}

	protected void cacheDb() throws Exception {
		CacheRegistry.clear();
	}

	protected void cacheMulti() throws Exception {
		ClusterPool.clear();
	}

	protected void cacheSingle() throws Exception {
		LastModifiedCSS.clear();
		LastModifiedJavaScript.clear();
		WebCachePool.clear();
	}

	protected void deployConfiguration(ActionRequest req) throws Exception {
		boolean enabled = ParamUtil.getBoolean(req, "enabled");
		String deployDir = ParamUtil.getString(req, "deployDir");
		String destDir = ParamUtil.getString(req, "destDir");
		long interval = ParamUtil.getLong(req, "interval");
		int blacklistThreshold = ParamUtil.getInteger(
			req, "blacklistThreshold");
		boolean unpackWar = ParamUtil.getBoolean(req, "unpackWar");
		boolean customPortletXml = ParamUtil.getBoolean(
			req, "customPortletXml");
		String jbossPrefix = ParamUtil.getString(req, "jbossPrefix");
		String tomcatConfDir = ParamUtil.getString(req, "tomcatConfDir");
		String tomcatLibDir = ParamUtil.getString(req, "tomcatLibDir");
		String pluginRepositories = ParamUtil.getString(
			req, "pluginRepositories");

		PortletPreferences prefs = PrefsPropsUtil.getPreferences();

		prefs.setValue(PropsUtil.AUTO_DEPLOY_ENABLED, String.valueOf(enabled));
		prefs.setValue(PropsUtil.AUTO_DEPLOY_DEPLOY_DIR, deployDir);
		prefs.setValue(PropsUtil.AUTO_DEPLOY_DEST_DIR, destDir);
		prefs.setValue(
			PropsUtil.AUTO_DEPLOY_INTERVAL, String.valueOf(interval));
		prefs.setValue(
			PropsUtil.AUTO_DEPLOY_BLACKLIST_THRESHOLD,
			String.valueOf(blacklistThreshold));
		prefs.setValue(
			PropsUtil.AUTO_DEPLOY_UNPACK_WAR, String.valueOf(unpackWar));
		prefs.setValue(
			PropsUtil.AUTO_DEPLOY_CUSTOM_PORTLET_XML,
			String.valueOf(customPortletXml));
		prefs.setValue(PropsUtil.AUTO_DEPLOY_JBOSS_PREFIX, jbossPrefix);
		prefs.setValue(PropsUtil.AUTO_DEPLOY_TOMCAT_CONF_DIR, tomcatConfDir);
		prefs.setValue(PropsUtil.AUTO_DEPLOY_TOMCAT_LIB_DIR, tomcatLibDir);

		String oldPluginRepositories = PrefsPropsUtil.getString(
			PropsUtil.AUTO_DEPLOY_DEPLOY_DIR);

		prefs.setValue(PropsUtil.PLUGIN_REPOSITORIES, pluginRepositories);

		prefs.store();

		if (!pluginRepositories.equals(oldPluginRepositories)) {
			reloadRepositories(req);
		}

		if (_log.isInfoEnabled()) {
			_log.info("Unregistering auto deploy directories");
		}

		AutoDeployUtil.unregisterDir("defaultAutoDeployDir");

		if (enabled) {
			if (_log.isInfoEnabled()) {
				_log.info("Registering auto deploy directories");
			}

			List autoDeployListeners =
				GlobalStartupAction.getAutoDeployListeners();

			AutoDeployDir autoDeployDir = new AutoDeployDir(
				"defaultAutoDeployDir", new File(deployDir), new File(destDir),
				interval, blacklistThreshold, autoDeployListeners);

			AutoDeployUtil.registerDir(autoDeployDir);
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info("Not registering auto deploy directories");
			}
		}
	}

	protected void localDeploy(ActionRequest req) throws Exception {
		UploadPortletRequest uploadReq =
			PortalUtil.getUploadPortletRequest(req);

		String fileName = null;

		String deploymentContext = ParamUtil.getString(
			req, "deploymentContext");

		if (Validator.isNotNull(deploymentContext)) {
			fileName =
				Constants.DEPLOY_TO_PREFIX + deploymentContext + ".war";
		}
		else {
			fileName = uploadReq.getFileName("file");

			deploymentContext = fileName.substring(0, fileName.length() - 4);
		}

		File file = uploadReq.getFile("file");

		PluginPackageUtil.registerInstallingPluginPackage(deploymentContext);

		try {
			byte[] bytes = FileUtil.getBytes(file);

			if ((bytes != null) && (bytes.length > 0)) {
				String source = file.toString();

				String deployDir = PrefsPropsUtil.getString(
					PropsUtil.AUTO_DEPLOY_DEPLOY_DIR);

				String destination = deployDir + StringPool.SLASH + fileName;

				FileUtil.copyFile(source, destination);

				SessionMessages.add(req, "pluginUploaded");
			}
			else {
				PluginPackageUtil.endPluginPackageInstallation(
					deploymentContext);
				SessionErrors.add(req, UploadException.class.getName());
			}
		}
		catch (Exception e) {
			PluginPackageUtil.endPluginPackageInstallation(
				deploymentContext);
			throw e;
		}
	}

	protected void reloadRepositories(ActionRequest req) throws Exception {
		RepositoryReport report = PluginPackageUtil.reloadRepositories();

		SessionMessages.add(req, WebKeys.PLUGIN_REPOSITORY_REPORT, report);
	}

	protected void remoteDeploy(ActionRequest req) throws Exception {
		GetMethod getMethod = null;
		String fileName = null;

		String url = ParamUtil.getString(req, "url");
		String deploymentContext = ParamUtil.getString(
			req, "deploymentContext");

		try {
			if (Validator.isNotNull(deploymentContext)) {
				fileName =
					Constants.DEPLOY_TO_PREFIX + deploymentContext + ".war";
			}
			else {
				fileName = url.substring(url.lastIndexOf(StringPool.SLASH) + 1);

				deploymentContext = fileName.substring(
					0, fileName.length() - 4);
			}

			URL urlObj = new URL(url);

			String progressId = ParamUtil.getString(req, Constants.PROGRESS_ID);

			HostConfiguration hostConfig = Http.getHostConfig(url.toString());

			HttpClient client = Http.getClient(hostConfig);

			getMethod = new GetMethod(url);

			PluginPackageUtil.registerInstallingPluginPackage(
				deploymentContext);

			int responseCode = client.executeMethod(hostConfig, getMethod);

			if (responseCode != 200) {
				SessionErrors.add(
					req, "errorResponseFromServer",
					new Object[] {String.valueOf(responseCode)});

				PluginPackageUtil.endPluginPackageInstallation(
					deploymentContext);
				return;
			}

			long contentLength = getMethod.getResponseContentLength();

			ProgressInputStream pis = new ProgressInputStream(
				req, getMethod.getResponseBodyAsStream(), contentLength,
				progressId);

			String deployDir = PrefsPropsUtil.getString(
				PropsUtil.AUTO_DEPLOY_DEPLOY_DIR);

			String tmpFilePath =
				deployDir + StringPool.SLASH + _DOWNLOAD_DIR +
					StringPool.SLASH + fileName;

			File tmpFile = new File(tmpFilePath);

			if (!tmpFile.getParentFile().exists()) {
				tmpFile.getParentFile().mkdirs();
			}

			FileOutputStream fos = new FileOutputStream(tmpFile);

			try {
				pis.readAll(fos);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Downloaded plugin from " + urlObj + " has " +
							pis.getTotalRead() + " bytes");
				}
			}
			finally {
				pis.clearProgress();
			}

			getMethod.releaseConnection();

			if (pis.getTotalRead() > 0) {
				String destination = deployDir + StringPool.SLASH + fileName;

				File destinationFile = new File(destination);

				boolean moved = FileUtil.move(tmpFile, destinationFile);

				if (!moved) {
					FileUtil.copyFile(tmpFile, destinationFile);
					FileUtil.delete(tmpFile);
				}

				SessionMessages.add(req, "pluginDownloaded");
			}
			else {
				SessionErrors.add(req, UploadException.class.getName());
			}
		}
		catch (MalformedURLException murle) {
			if (getMethod != null) {
				getMethod.releaseConnection();
			}

			SessionErrors.add(req, "invalidUrl", murle);
		}
		catch (IOException ioe) {
			if (getMethod != null) {
				getMethod.releaseConnection();
			}

			SessionErrors.add(req, "errorConnectingToServer", ioe);

			if (Validator.isNotNull(deploymentContext)) {
				PluginPackageUtil.endPluginPackageInstallation(
					deploymentContext);
			}
		}
		catch (Exception e) {
			if (Validator.isNotNull(deploymentContext)) {
				PluginPackageUtil.endPluginPackageInstallation(
					deploymentContext);
			}
			throw e;
		}
	}

	private static final String _DOWNLOAD_DIR = "download";

	private static Log _log = LogFactory.getLog(InstallPluginAction.class);

}