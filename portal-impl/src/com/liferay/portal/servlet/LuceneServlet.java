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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.lucene.LuceneHelperUtil;
import com.liferay.portal.security.auth.TransientTokenUtil;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 */
public class LuceneServlet extends HttpServlet {

	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		String pathInfo = request.getPathInfo();
		if (!Validator.equals(LUCENE_DUMP_INDEX_URL_PATH, pathInfo)) {
			if (_log.isWarnEnabled()) {
				_log.warn("Request for path : " + pathInfo +
					" is not supported.");
			}

			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// Checking token
		String token = request.getParameter(LUCENE_DUMP_INDEX_TOKEN);

		if (Validator.isNull(token)) {
			if (_log.isWarnEnabled()) {
				_log.warn("Request does not have an index dump token");
			}

			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		boolean valid = TransientTokenUtil.checkToken(token);

		if (!valid) {
			if (_log.isWarnEnabled()) {
				_log.warn("Request does not have a valid index dump token");
			}

			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		// Checking companyId
		String companyIdString = request.getParameter(
			LUCENE_DUMP_INDEX_COMPANY_ID);

		if (Validator.isNull(companyIdString)) {
			if (_log.isWarnEnabled()) {
				_log.warn("Request does not have an index dump companyId");
			}

			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		long companyId = GetterUtil.getLong(companyIdString, -1);

		if (companyId == -1) {
			if (_log.isWarnEnabled()) {
				_log.warn("Request does not have a valid index dump companyId");
			}

			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		// Dump out indexes
		LuceneHelperUtil.dumpIndex(companyId, response.getOutputStream());
	}

	public static final String LUCENE_DUMP_INDEX_COMPANY_ID =
		"LUCENE_DUMP_INDEX_COMPANY_ID";

	public static final String LUCENE_DUMP_INDEX_TOKEN =
		"LUCENE_DUMP_INDEX_TOKEN";

	public static final String LUCENE_DUMP_INDEX_URL_PATH = "/dump";

	private static Log _log = LogFactoryUtil.getLog(LuceneServlet.class);

}