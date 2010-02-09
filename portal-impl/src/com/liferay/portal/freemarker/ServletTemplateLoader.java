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

package com.liferay.portal.freemarker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.velocity.VelocityContextPool;

import java.io.IOException;

import java.net.URL;

import javax.servlet.ServletContext;

/**
 * <a href="ServletTemplateLoader.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mika Koivisto
 */
public class ServletTemplateLoader extends URLTemplateLoader {

	public URL getURL(String name) throws IOException {
		URL url = null;

		int pos = name.indexOf(SERVLET_SEPARATOR);

		if (pos != -1) {
			String servletContextName = name.substring(0, pos);

			if (Validator.isNull(servletContextName)) {
				servletContextName = PortalUtil.getPathContext();
			}

			ServletContext servletContext = VelocityContextPool.get(
				servletContextName);

			if (servletContext != null) {
				String templateName =
					name.substring(pos + SERVLET_SEPARATOR.length());

				if (_log.isDebugEnabled()) {
					_log.debug(
						name + " is associated with the servlet context " +
							servletContextName + " " + servletContext);
				}

				url = servletContext.getResource(templateName);

				if ((url == null) &&
					(templateName.endsWith("/init_custom.ftl"))) {

					if (_log.isWarnEnabled()) {
						_log.warn(
							"The template " + name + " should be created");
					}

					String portalContextName = PortalUtil.getPathContext();

					ServletContext portalContext =
						VelocityContextPool.get(portalContextName);

					url = portalContext.getResource(
						"/html/themes/_unstyled/template/init_custom.ftl");
				}
			}
			else {
				_log.error(
					name + " is not valid because " + servletContextName +
						" does not map to a servlet context");
			}
		}

		return url;
	}

	private static Log _log = LogFactoryUtil.getLog(
		ServletTemplateLoader.class);

}