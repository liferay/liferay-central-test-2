/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.events;

import com.liferay.portal.deploy.AutoDeployLayoutTemplateListener;
import com.liferay.portal.deploy.AutoDeployPortletListener;
import com.liferay.portal.deploy.AutoDeployThemeListener;
import com.liferay.portal.deploy.HotDeployLayoutTemplateListener;
import com.liferay.portal.deploy.HotDeployPortletListener;
import com.liferay.portal.deploy.HotDeployThemeListener;
import com.liferay.portal.jcr.JCRFactoryUtil;
import com.liferay.portal.kernel.deploy.AutoDeployDir;
import com.liferay.portal.kernel.deploy.AutoDeployUtil;
import com.liferay.portal.kernel.deploy.HotDeployUtil;
import com.liferay.portal.kernel.util.PortalInitableUtil;
import com.liferay.portal.struts.ActionException;
import com.liferay.portal.struts.SimpleAction;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.GetterUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="GlobalStartupAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class GlobalStartupAction extends SimpleAction {

	public void run(String[] ids) throws ActionException {

		// JCR

		try {
			if (GetterUtil.getBoolean(PropsUtil.get(
					PropsUtil.JCR_INITIALIZE_ON_STARTUP))) {

				JCRFactoryUtil.initialize();
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		// Portal initable

		PortalInitableUtil.flushInitables();

		// Hot deploy

		_log.debug("Registering hot deploy listeners");

		HotDeployUtil.registerListener(new HotDeployLayoutTemplateListener());
		HotDeployUtil.registerListener(new HotDeployPortletListener());
		HotDeployUtil.registerListener(new HotDeployThemeListener());

		HotDeployUtil.flushEvents();

		// Auto deploy

		try {
			_log.debug("Registering auto deploy directories");

			File deployDir = new File(
				PrefsPropsUtil.getString(PropsUtil.AUTO_DEPLOY_DEPLOY_DIR));
			File destDir = new File(
				PrefsPropsUtil.getString(PropsUtil.AUTO_DEPLOY_DEST_DIR));
			long interval = PrefsPropsUtil.getLong(
				PropsUtil.AUTO_DEPLOY_INTERVAL);

			List autoDeployListeners = new ArrayList();

			autoDeployListeners.add(new AutoDeployLayoutTemplateListener());
			autoDeployListeners.add(new AutoDeployPortletListener());
			autoDeployListeners.add(new AutoDeployThemeListener());

			AutoDeployDir autoDeployDir = new AutoDeployDir(
				"defaultAutoDeployDir", deployDir, destDir, interval,
				autoDeployListeners);

			AutoDeployUtil.registerDir(autoDeployDir);
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	private static Log _log = LogFactory.getLog(GlobalStartupAction.class);

}