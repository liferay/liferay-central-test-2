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

package com.liferay.portal.upload;

import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadServletRequest;

import java.io.File;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequestWrapper;

/**
 * <a href="UploadPortletRequestImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 */
public class UploadPortletRequestImpl
	extends HttpServletRequestWrapper implements UploadPortletRequest {

	public UploadPortletRequestImpl(
		UploadServletRequest uploadRequest, String namespace) {

		super(uploadRequest);

		_uploadRequest = uploadRequest;
		_namespace = namespace;
	}

	public void cleanUp() {
		_uploadRequest.cleanUp();
	}

	public String getContentType(String name) {
		String contentType = _uploadRequest.getContentType(_namespace + name);

		if (contentType == null) {
			contentType = _uploadRequest.getContentType(name);
		}

		return contentType;
	}

	public File getFile(String name) {
		File file = _uploadRequest.getFile(_namespace + name);

		if (file == null) {
			file = _uploadRequest.getFile(name);
		}

		return file;
	}

	public String getFileName(String name) {
		String fileName = _uploadRequest.getFileName(_namespace + name);

		if (fileName == null) {
			fileName = _uploadRequest.getFileName(name);
		}

		return fileName;
	}

	public String getFullFileName(String name) {
		String fullFileName = _uploadRequest.getFullFileName(_namespace + name);

		if (fullFileName == null) {
			fullFileName = _uploadRequest.getFullFileName(name);
		}

		return fullFileName;
	}

	public String getParameter(String name) {
		String parameter = _uploadRequest.getParameter(_namespace + name);

		if (parameter == null) {
			parameter = _uploadRequest.getParameter(name);
		}

		return parameter;
	}

	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> map = new HashMap<String, String[]>();

		Enumeration<String> enu = getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			map.put(name, getParameterValues(name));
		}

		return map;
	}

	public Enumeration<String> getParameterNames() {
		List<String> parameterNames = new ArrayList<String>();

		Enumeration<String> enu = _uploadRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.startsWith(_namespace)) {
				parameterNames.add(
					name.substring(_namespace.length(), name.length()));
			}
			else {
				parameterNames.add(name);
			}
		}

		return Collections.enumeration(parameterNames);
	}

	public String[] getParameterValues(String name) {
		String[] parameterValues = _uploadRequest.getParameterValues(
			_namespace + name);

		if (parameterValues == null) {
			parameterValues = _uploadRequest.getParameterValues(name);
		}

		return parameterValues;
	}

	public boolean isFormField(String name) {
		Boolean formField = _uploadRequest.isFormField(_namespace + name);

		if (formField == null) {
			formField = _uploadRequest.isFormField(name);
		}

		if (formField == null) {
			return true;
		}
		else {
			return formField.booleanValue();
		}
	}

	private UploadServletRequest _uploadRequest;
	private String _namespace;

}