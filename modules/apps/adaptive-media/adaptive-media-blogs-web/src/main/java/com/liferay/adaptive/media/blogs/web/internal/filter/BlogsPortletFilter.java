/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.adaptive.media.blogs.web.internal.filter;

import com.liferay.adaptive.media.processor.content.ContentProcessor;
import com.liferay.adaptive.media.web.constants.ContentTypes;
import com.liferay.blogs.web.constants.BlogsPortletKeys;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.PortletFilter;
import javax.portlet.filter.RenderFilter;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + BlogsPortletKeys.BLOGS},
	service = PortletFilter.class
)
public class BlogsPortletFilter implements RenderFilter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(
			RenderRequest renderRequest, RenderResponse renderResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		filterChain.doFilter(renderRequest, renderResponse);

		String mvcRenderCommandName = ParamUtil.getString(
			renderRequest, "mvcRenderCommandName");

		if ("/blogs/view_entry".equals(mvcRenderCommandName)) {
			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(renderResponse);

			BufferCacheServletResponse bufferCacheServletResponse =
				(BufferCacheServletResponse)httpServletResponse;

			String content = bufferCacheServletResponse.getString();

			String processedContent = _contentProcessor.process(
				ContentTypes.HTML, content);

			ServletResponseUtil.write(httpServletResponse, processedContent);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Reference
	private ContentProcessor _contentProcessor;

	@Reference
	private Portal _portal;

}