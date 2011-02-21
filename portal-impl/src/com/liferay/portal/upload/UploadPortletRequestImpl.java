/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upload;

import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequestWrapper;

/**
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

		if (Validator.isNull(contentType) ||
			contentType.equals(ContentTypes.APPLICATION_OCTET_STREAM)) {

			contentType = MimeTypesUtil.getContentType(getFile(name));
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