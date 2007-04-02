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

package com.liferay.portal.theme;

import com.liferay.portal.service.impl.ThemeLocalUtil;
import com.liferay.portal.velocity.VelocityContextPool;
import com.liferay.util.CollectionFactory;
import com.liferay.util.FileUtil;

import java.io.File;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ThemeLoader.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ThemeLoader {

	public static ThemeLoader getInstance() {
		ThemeLoader themeLoader = null;

		Iterator itr = _themeLoaders.entrySet().iterator();

		if (itr.hasNext()) {
			Map.Entry entry = (Map.Entry)itr.next();

			themeLoader = (ThemeLoader)entry.getValue();
		}

		return themeLoader;
	}

	public static void init(String servletContextName, ServletContext ctx) {
		VelocityContextPool.put(servletContextName, ctx);

		ThemeLoader themeLoader = new ThemeLoader(servletContextName, ctx);

		_themeLoaders.put(servletContextName, themeLoader);
	}

	public static boolean destroy(String servletContextName) {
		ThemeLoader themeLoader =
			(ThemeLoader)_themeLoaders.remove(servletContextName);

		if (themeLoader == null) {
			return false;
		}
		else {
			VelocityContextPool.remove(servletContextName);

			themeLoader.destroy();

			return true;
		}
	}

	public String getServletContextName() {
		return _servletContextName;
	}

	public File getRoot() {
		return _root;
	}

	public synchronized void loadThemes() {
		if (_log.isInfoEnabled()) {
			_log.info("Loading themes in " + _root);
		}

		File[] files = _root.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (_log.isDebugEnabled()) {
				_log.debug("Process directory " + files[i]);
			}

			File liferayLookAndFeelXML = new File(
				files[i] + "/liferay-look-and-feel.xml");

			if (liferayLookAndFeelXML.exists()) {
				String lastModifiedKey = liferayLookAndFeelXML.toString();

				Long prevLastModified =
					(Long)_lastModifiedMap.get(lastModifiedKey);

				long lastModified = liferayLookAndFeelXML.lastModified();

				if ((prevLastModified == null) ||
					(prevLastModified.longValue() < lastModified)) {

					registerTheme(liferayLookAndFeelXML);

					_lastModifiedMap.put(
						lastModifiedKey, new Long(lastModified));
				}
				else {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Do not refresh " + liferayLookAndFeelXML +
								" because it is has not been modified");
					}
				}
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn(liferayLookAndFeelXML + " does not exist");
				}
			}
		}
	}

	private ThemeLoader(String servletContextName, ServletContext ctx) {
		_servletContextName = servletContextName;
		_ctx = ctx;
		_root = new File(ctx.getRealPath("/themes"));

		loadThemes();
	}

	private void destroy() {
	}

	private void registerTheme(File liferayLookAndFeelXML) {
		if (_log.isDebugEnabled()) {
			_log.debug("Registering " + liferayLookAndFeelXML);
		}

		try {
			String content = FileUtil.read(liferayLookAndFeelXML);

			List themeIds = ThemeLocalUtil.init(
				_servletContextName, _ctx, new String[] {content}, null);
		}
		catch (Exception e) {
			_log.error(
				"Error registering theme " + liferayLookAndFeelXML.toString(),
				e);
		}
	}

	private static Log _log = LogFactory.getLog(ThemeLoader.class);

	private static Map _themeLoaders = CollectionFactory.getHashMap();

	private String _servletContextName;
	private ServletContext _ctx;
	private File _root;
	private Map _lastModifiedMap = CollectionFactory.getHashMap();

}