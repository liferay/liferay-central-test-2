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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Mate Thurzo
 */
@OSGiBeanProperties
public class PortletDataContextIndexer extends BaseIndexer {

	public static final String CLASS_NAME = PortletDataContext.class.getName();

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		return null;
	}

	@Override
	protected Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		return null;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		PortletDataContext portletDataContext = (PortletDataContext)obj;

		Map<String, Map<?, ?>> newPrimaryKeysMaps =
			portletDataContext.getNewPrimaryKeysMaps();

		for (Map.Entry<String, Map<?, ?>> entry :
				newPrimaryKeysMaps.entrySet()) {

			String className = entry.getKey();

			Indexer indexer = IndexerRegistryUtil.getIndexer(className);

			if (indexer == null) {
				continue;
			}

			Map<?, ?> newPrimaryKeysMap = entry.getValue();

			for (Object object : newPrimaryKeysMap.values()) {
				long classPK = GetterUtil.getLong(object);

				if (classPK > 0) {
					indexer.reindex(className, classPK);
				}
			}
		}
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
	}

}