/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.etag;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.util.servlet.filters.CacheResponse;
import com.liferay.util.servlet.filters.CacheResponseData;
import com.liferay.util.servlet.filters.CacheResponseUtil;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="ETagFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class ETagFilter extends BasePortalFilter {

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		boolean etag = ParamUtil.getBoolean(request, _ETAG, true);

		if (etag) {
			CacheResponse cacheResponse = new CacheResponse(
				response, StringPool.UTF8);

			processFilter(
				ETagFilter.class, request, cacheResponse, filterChain);

			CacheResponseData cacheResponseData = new CacheResponseData(
				cacheResponse.unsafeGetData(), cacheResponse.getContentLength(),
				cacheResponse.getContentType(), cacheResponse.getHeaders());

			if (!ETagUtil.processETag(
					request, response, cacheResponse.unsafeGetData(),
					cacheResponse.getContentLength())) {

				CacheResponseUtil.write(response, cacheResponseData);
			}
		}
		else {
			processFilter(
				ETagFilter.class, request, response, filterChain);
		}
	}

	private static final String _ETAG = "etag";

}