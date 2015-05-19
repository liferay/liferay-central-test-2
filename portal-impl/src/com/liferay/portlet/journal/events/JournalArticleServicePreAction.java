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

package com.liferay.portlet.journal.events;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Julio Camarero
 */
@OSGiBeanProperties(property = {"key=servlet.service.events.pre"})
public class JournalArticleServicePreAction extends Action {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
		throws ActionException {

		try {
			servicePre(request);
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	public void servicePre(HttpServletRequest request) throws PortalException {
		String strutsAction = PortalUtil.getStrutsAction(request);

		if (!strutsAction.equals(_PATH_PORTAL_LAYOUT)) {
			return;
		}

		long mainJournalArticleId = ParamUtil.getLong(request, "p_j_a_id");

		if (mainJournalArticleId <= 0) {
			return;
		}

		try {
			JournalArticle mainJournalArticle =
				JournalArticleServiceUtil.getArticle(mainJournalArticleId);

			AssetEntry layoutAssetEntry = AssetEntryLocalServiceUtil.getEntry(
				JournalArticle.class.getName(),
				mainJournalArticle.getResourcePrimKey());

			request.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, layoutAssetEntry);
		}
		catch (NoSuchArticleException nsae) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsae.getMessage());
			}
		}
	}

	private static final String _PATH_PORTAL_LAYOUT = "/portal/layout";

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleServicePreAction.class);

}