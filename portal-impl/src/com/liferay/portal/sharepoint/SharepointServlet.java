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

package com.liferay.portal.sharepoint;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.sharepoint.methods.Method;
import com.liferay.portal.sharepoint.methods.MethodFactory;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="SharepointServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public class SharepointServlet extends HttpServlet {

	public void doGet(
		HttpServletRequest request,	HttpServletResponse response) {

		try {
			String uri = request.getRequestURI();

			if (uri.equals("/_vti_inf.html")) {
				vtiInfHtml(response);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void doPost(
		HttpServletRequest request, HttpServletResponse response) {

		try {
			String uri = request.getRequestURI();

			if (uri.equals("/_vti_bin/shtml.dll/_vti_rpc") ||
				uri.equals("/sharepoint/_vti_bin/_vti_aut/author.dll")) {

				User user = (User)request.getSession().getAttribute(
					WebKeys.USER);

				SharepointRequest sharepointRequest = new SharepointRequest(
					request, response, user);

				addParams(request, sharepointRequest);

				Method method = MethodFactory.create(sharepointRequest);

				String rootPath = method.getRootPath(sharepointRequest);

				sharepointRequest.setRootPath(rootPath);

				SharepointStorage storage = SharepointUtil.getStorage(rootPath);

				sharepointRequest.setSharepointStorage(storage);

				method.process(sharepointRequest);
			}
		}
		catch (SharepointException se) {
			_log.error(se, se);
		}
	}

	protected void addParams(
			HttpServletRequest request, SharepointRequest sharepointRequest)
		throws SharepointException {

		String contentType = request.getContentType();

		if (!contentType.equals(SharepointUtil.VEERMER_URLENCODED)) {
			return;
		}

		try {
			InputStream is = request.getInputStream();

			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new InputStreamReader(is));

			String url = unsyncBufferedReader.readLine();

			String[] params = url.split(StringPool.AMPERSAND);

			for (String param : params) {
				String[] kvp = param.split(StringPool.EQUAL);

				String key = HttpUtil.decodeURL(kvp[0]);
				String value = StringPool.BLANK;

				if (kvp.length > 1) {
					value = HttpUtil.decodeURL(kvp[1]);
				}

				sharepointRequest.addParam(key, value);
			}

			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			StreamUtil.transfer(is, unsyncByteArrayOutputStream);

			sharepointRequest.setBytes(
				unsyncByteArrayOutputStream.toByteArray());
		}
		catch (Exception e) {
			throw new SharepointException(e);
		}
	}

	protected void vtiInfHtml(HttpServletResponse response) throws Exception {
		StringBundler sb = new StringBundler(13);

		sb.append("<!-- FrontPage Configuration Information");
		sb.append(StringPool.NEW_LINE);
		sb.append(" FPVersion=\"6.0.2.9999\"");
		sb.append(StringPool.NEW_LINE);
		sb.append("FPShtmlScriptUrl=\"_vti_bin/shtml.dll/_vti_rpc\"");
		sb.append(StringPool.NEW_LINE);
		sb.append("FPAuthorScriptUrl=\"_vti_bin/_vti_aut/author.dll\"");
		sb.append(StringPool.NEW_LINE);
		sb.append("FPAdminScriptUrl=\"_vti_bin/_vti_adm/admin.dll\"");
		sb.append(StringPool.NEW_LINE);
		sb.append("TPScriptUrl=\"_vti_bin/owssvr.dll\"");
		sb.append(StringPool.NEW_LINE);
		sb.append("-->");

		ServletResponseUtil.write(response, sb.toString());
	}

	private static Log _log = LogFactoryUtil.getLog(SharepointServlet.class);

}