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

package com.liferay.portal.search.web.internal.display.context;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcherManager;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(immediate = true, service = SearchDisplayContextFactory.class)
public class SearchDisplayContextFactoryImpl
	implements SearchDisplayContextFactory {

	@Override
	public SearchDisplayContext create(
			RenderRequest renderRequest, RenderResponse renderResponse,
			PortletPreferences portletPreferences)
		throws PortletException {

		try {
			return new SearchDisplayContext(
				renderRequest, portletPreferences, portal, HtmlUtil.getHtml(),
				language, facetedSearcherManager,
				new IndexSearchPropsValuesImpl(),
				new PortletURLFactoryImpl(renderRequest, renderResponse));
		}
		catch (PortletException pe) {
			throw pe;
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	@Reference
	protected FacetedSearcherManager facetedSearcherManager;

	@Reference
	protected Language language;

	@Reference
	protected Portal portal;

	@Reference
	private Portal _portal;

}