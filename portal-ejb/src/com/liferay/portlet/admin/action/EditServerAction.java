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

package com.liferay.portlet.admin.action;

import com.liferay.portal.deploy.AutoDeployLayoutTemplateListener;
import com.liferay.portal.deploy.AutoDeployPortletListener;
import com.liferay.portal.deploy.AutoDeployThemeListener;
import com.liferay.portal.kernel.deploy.AutoDeployDir;
import com.liferay.portal.kernel.deploy.AutoDeployUtil;
import com.liferay.portal.kernel.plugin.Plugin;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.lastmodified.LastModifiedCSS;
import com.liferay.portal.lastmodified.LastModifiedJavaScript;
import com.liferay.portal.plugin.PluginException;
import com.liferay.portal.plugin.PluginUtil;
import com.liferay.portal.plugin.RepositoryReport;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.spring.hibernate.CacheRegistry;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.ClusterPool;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.portal.util.ShutdownUtil;
import com.liferay.portal.util.WebCachePool;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.admin.util.OmniadminUtil;
import com.liferay.util.FileUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.ProgressInputStream;
import com.liferay.util.Time;
import com.liferay.util.Validator;
import com.liferay.util.servlet.NullServletResponse;
import com.liferay.util.servlet.SessionErrors;
import com.liferay.util.servlet.SessionMessages;
import com.liferay.util.servlet.UploadException;
import com.liferay.util.servlet.UploadPortletRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="EditServerAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class EditServerAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		if (!OmniadminUtil.isOmniadmin(req.getRemoteUser())) {
			SessionErrors.add(req, PrincipalException.class.getName());

			setForward(req, "portlet.admin.error");

			return;
		}

		String cmd = ParamUtil.getString(req, Constants.CMD);

		if (cmd.equals("autoDeploy")) {
			autoDeploy(req);
		}
		else if (cmd.equals("cacheDb")) {
			cacheDb();
		}
		else if (cmd.equals("cacheMulti")) {
			cacheMulti();
		}
		else if (cmd.equals("cacheSingle")) {
			cacheSingle();
		}
		else if (cmd.equals("gc")) {
			gc();
		}
		else if (cmd.equals("hotDeploy")) {
			hotDeploy(req);
		}
		else if (cmd.equals("precompile")) {
			precompile(req, res);
		}
		else if (cmd.equals("reloadRepositories")) {
			reloadRepositories(req);
		}
		else if (cmd.equals("remoteDeploy")) {
			remoteDeploy(req);
		}
		else if (cmd.equals("shutdown")) {
			shutdown(req);
		}
		else if (cmd.equals("updateLogLevels")) {
			updateLogLevels(req);
		}

		sendRedirect(req, res);
	}

	protected void autoDeploy(ActionRequest req) throws Exception {
		boolean enabled = ParamUtil.getBoolean(req, "enabled");
		String deployDir = ParamUtil.getString(req, "deployDir");
		String destDir = ParamUtil.getString(req, "destDir");
		long interval = ParamUtil.getLong(req, "interval");
		boolean unpackWar = ParamUtil.getBoolean(req, "unpackWar");
		String tomcatLibDir = ParamUtil.getString(req, "tomcatLibDir");
		String pluginRepositories =
				ParamUtil.getString(req, "pluginRepositories");

		PortletPreferences prefs = PrefsPropsUtil.getPreferences();

		prefs.setValue(PropsUtil.AUTO_DEPLOY_ENABLED, String.valueOf(enabled));
		prefs.setValue(PropsUtil.AUTO_DEPLOY_DEPLOY_DIR, deployDir);
		prefs.setValue(PropsUtil.AUTO_DEPLOY_DEST_DIR, destDir);
		prefs.setValue(
			PropsUtil.AUTO_DEPLOY_INTERVAL, String.valueOf(interval));
		prefs.setValue(
			PropsUtil.AUTO_DEPLOY_UNPACK_WAR, String.valueOf(unpackWar));

		if (Validator.isNotNull(tomcatLibDir)) {
			prefs.setValue(PropsUtil.AUTO_DEPLOY_TOMCAT_LIB_DIR, tomcatLibDir);
		}

		String oldPluginRepositories =
				PrefsPropsUtil.getString(PropsUtil.AUTO_DEPLOY_DEPLOY_DIR);
		prefs.setValue(
			PropsUtil.PLUGIN_REPOSITORIES, pluginRepositories);

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

			List autoDeployListeners = new ArrayList();

			autoDeployListeners.add(new AutoDeployLayoutTemplateListener());
			autoDeployListeners.add(new AutoDeployPortletListener());
			autoDeployListeners.add(new AutoDeployThemeListener());

			AutoDeployDir autoDeployDir = new AutoDeployDir(
				"defaultAutoDeployDir", new File(deployDir), new File(destDir),
				interval, autoDeployListeners);

			AutoDeployUtil.registerDir(autoDeployDir);
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info("Not registering auto deploy directories");
			}
		}
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

	protected void gc() throws Exception {
		Runtime.getRuntime().gc();
	}

	protected void hotDeploy(ActionRequest req) throws Exception {
		UploadPortletRequest uploadReq =
			PortalUtil.getUploadPortletRequest(req);
		String recommendedWARName =
				ParamUtil.getString(req, "recommendedWARName");

		File file = uploadReq.getFile("file");
		String fileName = recommendedWARName;
		if (Validator.isNull(fileName)) {
			fileName = uploadReq.getFileName("file");
		}

		byte[] bytes = FileUtil.getBytes(file);

		if ((bytes != null) && (bytes.length > 0)) {
			String source = file.toString();
			String destination =
				PrefsPropsUtil.getString(PropsUtil.AUTO_DEPLOY_DEPLOY_DIR) +
					StringPool.SLASH + fileName;

			FileUtil.copyFile(source, destination);
			SessionMessages.add(req, "pluginUploaded");
		}
		else {
			SessionErrors.add(req, UploadException.class.getName());
		}
	}

	protected void precompile(ActionRequest req, ActionResponse res)
		throws Exception {

		Set jsps = new TreeSet();

		ServletContext ctx = (ServletContext)req.getAttribute(WebKeys.CTX);

		// Struts

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(ctx.getResource(
			"/WEB-INF/struts-config.xml"));

		Element root = doc.getRootElement();

		Iterator itr1 = root.element("global-forwards").elements(
			"forward").iterator();

		while (itr1.hasNext()) {
			Element action = (Element)itr1.next();

			String fileName = action.attributeValue("path");

			if ((Validator.isNotNull(fileName)) &&
				(fileName.endsWith(".jsp"))) {

				jsps.add(fileName);
			}
		}

		itr1 = root.element("action-mappings").elements("action").iterator();

		while (itr1.hasNext()) {
			Element action = (Element)itr1.next();

			String fileName = action.attributeValue("forward");

			if ((Validator.isNotNull(fileName)) &&
				(fileName.endsWith(".jsp"))) {

				jsps.add(fileName);
			}
			else {
				Iterator itr2 = action.elements("forward").iterator();

				while (itr2.hasNext()) {
					Element forward = (Element)itr2.next();

					fileName = forward.attributeValue("path");

					if ((Validator.isNotNull(fileName)) &&
						(fileName.endsWith(".jsp"))) {

						jsps.add(fileName);
					}
				}
			}
		}

		// Tiles

		reader = SAXReaderFactory.getInstance();

		doc = reader.read(ctx.getResource("/WEB-INF/tiles-defs.xml"));

		root = doc.getRootElement();

		itr1 = root.elements("definition").iterator();

		while (itr1.hasNext()) {
			Element definition = (Element)itr1.next();

			String fileName = definition.attributeValue("path");

			if ((Validator.isNotNull(fileName)) &&
				(fileName.endsWith(".jsp"))) {

				jsps.add(fileName);
			}
			else {
				Iterator itr2 = definition.elements("put").iterator();

				while (itr2.hasNext()) {
					Element put = (Element)itr2.next();

					fileName = put.attributeValue("value");

					if ((Validator.isNotNull(fileName)) &&
						(fileName.endsWith(".jsp"))) {

						jsps.add(fileName);
					}
				}
			}
		}

		// Precompile JSPs

		ActionRequestImpl reqImpl = (ActionRequestImpl)req;
		ActionResponseImpl resImpl = (ActionResponseImpl)res;

		HttpServletRequest httpReq = reqImpl.getHttpServletRequest();
		HttpServletResponse httpRes = new NullServletResponse(
			(HttpServletResponse)resImpl.getHttpServletResponse());

		itr1 = jsps.iterator();

		while (itr1.hasNext()) {
			try {
				String jsp = Constants.TEXT_HTML_DIR + itr1.next();

				RequestDispatcher rd = ctx.getRequestDispatcher(jsp);

				if (rd != null) {
					if (_log.isInfoEnabled()) {
						_log.info("Precompiling " + jsp);
					}

					rd.include(httpReq, httpRes);
				}
			}
			catch (Exception e) {
				_log.debug(e, e);
			}
		}
	}

	protected void reloadRepositories(ActionRequest req) throws Exception {
		RepositoryReport report = PluginUtil.reloadRepositories();
		req.getPortletSession().setAttribute(
				WebKeys.PLUGIN_REPOSITORY_REPORT, report);
	}

	protected void remoteDeploy(ActionRequest req) throws Exception {

		String url = ParamUtil.getString(req, "url");
		String recommendedWARName =
				ParamUtil.getString(req, "recommendedWARName");
		String progressId = ParamUtil.getString(req, Constants.PROGRESS_ID);

		URL urlObj = new URL(url);
		GetMethod getFileMethod = new GetMethod(urlObj.toString());

		try {
			int responseCode = _client.executeMethod(getFileMethod);
			if (responseCode != 200) {
				SessionErrors.add(
						req, "errorResponseFromServer",
						new Object[]{Integer.toString(responseCode)});
				return;
			}

			long contentLength = getFileMethod.getResponseContentLength();

			String fileName = url.substring(
					url.lastIndexOf(StringPool.SLASH) + 1);

			String destFileName = _getDestFileName(
					recommendedWARName, url, fileName);

			ProgressInputStream pis = new ProgressInputStream(
					req, getFileMethod.getResponseBodyAsStream(),
					contentLength, progressId);

			String deployDir = PrefsPropsUtil.getString(
					PropsUtil.AUTO_DEPLOY_DEPLOY_DIR);
			String tmpFilePath = deployDir + StringPool.SLASH + _DOWNLOAD_DIR +
					StringPool.SLASH + destFileName;
			File tmpFile = new File(tmpFilePath);
			if (!tmpFile.getParentFile().exists()) {
				tmpFile.getParentFile().mkdirs();
			}

			FileOutputStream fos = new FileOutputStream(tmpFile);
			try {
				pis.readAll(fos);
				_log.info("Downloaded plugin from " + urlObj + " (" +
						pis.getTotalRead() + " bytes)");
			} finally {
				pis.clearProgress();
			}

			getFileMethod.releaseConnection();

			if (pis.getTotalRead() > 0) {
				String destination = PrefsPropsUtil.getString(
						PropsUtil.AUTO_DEPLOY_DEPLOY_DIR) + StringPool.SLASH +
						destFileName;

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
		catch (MalformedURLException mue) {
			getFileMethod.releaseConnection();
			SessionErrors.add(req, "invalidUrl", url); // Inform user of bad URL
		}
		catch (IOException ioe) {
			getFileMethod.releaseConnection();
			SessionErrors.add(req, "errorConnectingToServer", ioe);
		}

}

	protected void shutdown(ActionRequest req) throws Exception {
		long minutes = ParamUtil.getInteger(req, "minutes") * Time.MINUTE;
		String message = ParamUtil.getString(req, "message");

		if (minutes <= 0) {
			ShutdownUtil.cancel();
		}
		else {
			ShutdownUtil.shutdown(minutes, message);
		}
	}

	protected void updateLogLevels(ActionRequest req) throws Exception {
		Enumeration enu = req.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = (String)enu.nextElement();

			if (name.startsWith("logLevel")) {
				String loggerName = name.substring(8, name.length());

				String priority = ParamUtil.getString(
					req, name, Level.INFO.toString());

				Logger logger = Logger.getLogger(loggerName);

				logger.setLevel(Level.toLevel(priority));
			}
		}
	}

	private String _getDestFileName(
			String recommendedWARName, String url, String fileName)
			throws PluginException {
		String destFileName = null;

		if (Validator.isNull(destFileName)) {
			destFileName = recommendedWARName;
		}

		if (Validator.isNull(destFileName)) {
			Plugin plugin = PluginUtil.getPluginByURL(url);
			if (plugin != null) {
				destFileName = plugin.getWARName();
			}
		}

		if (Validator.isNull(destFileName)) {
			destFileName = fileName;
		}
		return destFileName;
	}

	private static Log _log = LogFactory.getLog(EditServerAction.class);

	private static HttpClient _client = new HttpClient();

	private static final String _DOWNLOAD_DIR = "download";

	static {
		int timeout = GetterUtil.getInteger(
				PropsUtil.get(PropsUtil.PLUGIN_TIMEOUT_ARTIFACT));
		_client.getHttpConnectionManager().getParams().
				setConnectionTimeout(timeout);

	}

}