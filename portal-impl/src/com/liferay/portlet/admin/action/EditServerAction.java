/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.portal.lastmodified.LastModifiedCSS;
import com.liferay.portal.lastmodified.LastModifiedJavaScript;
import com.liferay.portal.search.lucene.LuceneIndexer;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.ShutdownUtil;
import com.liferay.portal.util.WebKeys;

import java.util.Enumeration;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

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

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!permissionChecker.isOmniadmin()) {
			SessionErrors.add(req, PrincipalException.class.getName());

			setForward(req, "portlet.admin.error");

			return;
		}

		PortletPreferences prefs = PrefsPropsUtil.getPreferences();

		String cmd = ParamUtil.getString(req, Constants.CMD);

		if (cmd.equals("addLogLevel")) {
			addLogLevel(req);
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
		else if (cmd.equals("reIndex")) {
			reIndex();
		}
		else if (cmd.equals("shutdown")) {
			shutdown(req);
		}
		else if (cmd.equals("threadDump")) {
			threadDump();
		}
		else if (cmd.equals("updateLogLevels")) {
			updateLogLevels(req);
		}
		else if (cmd.equals("updateOpenOffice")) {
			updateOpenOffice(req, prefs);
		}

		sendRedirect(req, res);
	}

	protected void addLogLevel(ActionRequest req) throws Exception {
		String loggerName = ParamUtil.getString(req, "loggerName");
		String priority = ParamUtil.getString(req, "priority");

		Logger logger = Logger.getLogger(loggerName);

		logger.setLevel(Level.toLevel(priority));
	}

	protected void cacheDb() throws Exception {
		CacheRegistry.clear();
	}

	protected void cacheMulti() throws Exception {
		MultiVMPoolUtil.clear();
	}

	protected void cacheSingle() throws Exception {
		LastModifiedCSS.clear();
		LastModifiedJavaScript.clear();
		WebCachePoolUtil.clear();
	}

	protected void gc() throws Exception {
		Runtime.getRuntime().gc();
	}

	protected void reIndex() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIds();

		for (int i = 0; i < companyIds.length; i++) {
			long companyId = companyIds[i];

			try {
				LuceneIndexer indexer = new LuceneIndexer(companyId);

				indexer.reIndex();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
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

	protected void threadDump() throws Exception {
		String jvm =
			System.getProperty("java.vm.name") + " " +
				System.getProperty("java.vm.version");

		StringMaker sm = new StringMaker("Full thread dump " + jvm + "\n\n");

		Map<Thread, StackTraceElement[]> stackTraces =
			Thread.getAllStackTraces();

		for (Thread thread : stackTraces.keySet()) {
			StackTraceElement[] elements = stackTraces.get(thread);

			sm.append(StringPool.QUOTE);
			sm.append(thread.getName());
			sm.append(StringPool.QUOTE);

			if (thread.getThreadGroup() != null) {
				sm.append(StringPool.SPACE);
				sm.append(StringPool.OPEN_PARENTHESIS);
				sm.append(thread.getThreadGroup().getName());
				sm.append(StringPool.CLOSE_PARENTHESIS);
			}

			sm.append(", priority=" + thread.getPriority());
			sm.append(", id=" + thread.getId());
			sm.append(", state=" + thread.getState());
			sm.append("\n");

			for (int i = 0; i < elements.length; i++) {
				sm.append("\t" + elements[i] + "\n");
			}

			sm.append("\n");
		}

		if (_log.isInfoEnabled()) {
			_log.info(sm.toString());
		}
		else {
			_log.error(
				"Thread dumps require the log level to be at least INFO for " +
					getClass().getName());
		}
	}

	protected void updateLogLevels(ActionRequest req) throws Exception {
		Enumeration<String> enu = req.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.startsWith("logLevel")) {
				String loggerName = name.substring(8, name.length());

				String priority = ParamUtil.getString(
					req, name, Level.INFO.toString());

				Logger logger = Logger.getLogger(loggerName);

				logger.setLevel(Level.toLevel(priority));
			}
		}
	}

	protected void updateOpenOffice(ActionRequest req, PortletPreferences prefs)
		throws Exception {

		boolean enabled = ParamUtil.getBoolean(req, "enabled");
		String host = ParamUtil.getString(req, "host");
		int port = ParamUtil.getInteger(req, "port");

		prefs.setValue(
			PropsKeys.OPENOFFICE_SERVER_ENABLED, String.valueOf(enabled));
		prefs.setValue(PropsKeys.OPENOFFICE_SERVER_HOST, host);
		prefs.setValue(PropsKeys.OPENOFFICE_SERVER_PORT, String.valueOf(port));

		prefs.store();
	}

	private static Log _log = LogFactory.getLog(EditServerAction.class);

}