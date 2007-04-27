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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.impl.ImageImpl;
import com.liferay.portal.service.impl.ImageLocalUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.HttpHeaders;
import com.liferay.util.ParamUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.IOException;

import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ImageServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Brett Randall
 *
 */
public class ImageServlet extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {
		synchronized (ImageServlet.class) {
			super.init(config);

			ServletContext ctx = getServletContext();

			_companyId = PortalUtil.getCompanyIdByWebId(ctx);
		}
	}

	public void service(HttpServletRequest req, HttpServletResponse res)
		throws IOException, ServletException {

		if (!PortalInstances.matches()) {
			return;
		}

		long lastModified = getLastModified(req);

		if (lastModified > 0) {
			long ifModifiedSince =
				req.getDateHeader(HttpHeaders.IF_MODIFIED_SINCE);

			if ((ifModifiedSince > 0) && (ifModifiedSince == lastModified)) {
				res.setStatus(HttpServletResponse.SC_NOT_MODIFIED);

				return;
			}
		}

		res.addHeader(HttpHeaders.CACHE_CONTROL, "max-age=0");

		if (lastModified > 0) {
			res.setDateHeader(HttpHeaders.LAST_MODIFIED, lastModified);
		}

		writeImage(req, res);
	}

	protected long getCompanyId() {
		return _companyId;
	}

	protected Image getDefaultImage(HttpServletRequest req, String imageId) {
		String path = req.getPathInfo();

		// User Portrait

		if (path.startsWith("/user_portrait")) {
			return ImageLocalUtil.getDefaultUserPortrait();
		}
		else {
			return ImageLocalUtil.getDefaultSpacer();
		}
	}

	protected String getImageId(HttpServletRequest req) {
		String path = req.getPathInfo();

		// The image id may be passed in as image_id, img_id, or i_id

		String imageId = ParamUtil.getString(req, "image_id");

		if (Validator.isNull(imageId)) {
			imageId = ParamUtil.getString(req, "img_id");

			if (Validator.isNull(imageId)) {
				imageId = ParamUtil.getString(req, "i_id");
			}
		}

		// Company logo

		if (path.startsWith("/company_logo")) {
			if (ParamUtil.get(req, "png", false)) {
				imageId += ".png";

				//res.setContentType("image/png");
			}
			else if (ParamUtil.get(req, "wbmp", false)) {
				imageId += ".wbmp";

				//res.setContentType("image/vnd.wap.wbmp");
			}
		}

		// Image gallery

		if (path.startsWith("/image_gallery")) {
			if (!imageId.equals(StringPool.BLANK) &&
				!imageId.startsWith(_companyId + ".image_gallery.")) {

				imageId = _companyId + ".image_gallery." + imageId;

				if (ParamUtil.get(req, "small", false)) {
					imageId += ".small";
				}
				else {
					imageId += ".large";
				}
			}
		}

		// Journal article

		if (path.startsWith("/journal/article")) {
			if (!imageId.equals(StringPool.BLANK) &&
				!imageId.startsWith(_companyId + ".journal.article.")) {

				imageId = _companyId + ".journal.article." + imageId;

				String version = req.getParameter("version");

				if (Validator.isNotNull(version)) {
					imageId += "." + version;
				}
			}
		}

		// Journal template

		if (path.startsWith("/journal/template")) {
			if (!imageId.equals(StringPool.BLANK) &&
				!imageId.startsWith(_companyId + ".journal.template.")) {

				imageId = _companyId + ".journal.template." + imageId;
				imageId += ".small";
			}
		}

		// Layout icon

		if (path.startsWith("/layout_icon")) {
			if (!imageId.equals(StringPool.BLANK) &&
				!imageId.startsWith(_companyId + ".layout.")) {

				imageId = _companyId + ".layout." + imageId;
			}
		}

		// Layout set logo

		if (path.startsWith("/layout_set_logo")) {
			if (!imageId.equals(StringPool.BLANK) &&
				!imageId.startsWith(_companyId + ".layout_set.")) {

				imageId = _companyId + ".layout_set." + imageId;
			}

			if (ParamUtil.get(req, "png", false)) {
				imageId += ".png";

				//res.setContentType("image/png");
			}
			else if (ParamUtil.get(req, "wbmp", false)) {
				imageId += ".wbmp";

				//res.setContentType("image/vnd.wap.wbmp");
			}
		}

		// Shopping item

		else if (path.startsWith("/shopping/item")) {
			if (!imageId.equals(StringPool.BLANK) &&
				!imageId.startsWith(_companyId + ".shopping.item.")) {

				imageId = _companyId + ".shopping.item." + imageId;

				if (ParamUtil.get(req, "small", false)) {
					imageId += ".small";
				}
				else if (ParamUtil.get(req, "medium", false)) {
					imageId += ".medium";
				}
				else {
					imageId += ".large";
				}
			}
		}

		// Software catalog

		else if (path.startsWith("/software_catalog")) {
			if (!imageId.equals(StringPool.BLANK)) {
				if (ParamUtil.get(req, "small", false)) {
					imageId += ".small";
				}
				else {
					imageId += ".large";
				}
			}
		}

		return imageId;
	}

	protected long getLastModified(HttpServletRequest req) {
		try {
			String imageId = getImageId(req);

			if (Validator.isNull(imageId)) {
				return -1;
			}

			Image image = ImageLocalUtil.get(imageId);

			if (image == null) {
				return -1;
			}

			Date modifiedDate = image.getModifiedDate();

			if (modifiedDate == null) {
				return -1;
			}

			// Round down and remove milliseconds

			return (modifiedDate.getTime() / 1000) * 1000;
		}
		catch (Exception e) {
			_log.error(e, e);

			return -1;
		}
	}

	protected void writeImage(HttpServletRequest req, HttpServletResponse res)
		throws IOException, ServletException {

		String imageId = getImageId(req);

		if (Validator.isNull(imageId)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Image id should never be null or empty, path is " +
						req.getPathInfo());
			}

			return;
		}

		Image image = ImageLocalUtil.get(imageId);

		if (image == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Get a default image for " + imageId);
			}

			image = getDefaultImage(req, imageId);
		}

		if (image == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No default image exists for " + imageId);
			}
		}
		else {
			if (!image.getType().equals(ImageImpl.TYPE_NOT_AVAILABLE)) {
				res.setContentType("image/" + image.getType());
			}

			try {
				ServletResponseUtil.write(res, image.getTextObj());
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}
	}

	private static Log _log = LogFactory.getLog(ImageServlet.class);

	private long _companyId;

}