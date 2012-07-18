/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.*;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.TestPropsValues;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Eduardo Garcia
 */
public class BaseExportImportTestCase extends PowerMockito {

	protected Layout addLayout(
			long groupId, String name, LayoutPrototype layoutPrototype,
			boolean linkEnabled)
		throws Exception {

		String friendlyURL =
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name);

		Layout layout = null;

		try {
			layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
				groupId, false, friendlyURL);

			return layout;
		}
		catch (NoSuchLayoutException nsle) {
		}

		String description = "This is a test page.";

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setAttribute("layoutPrototypeLinkEnabled", linkEnabled);
		serviceContext.setAttribute(
			"layoutPrototypeUuid", layoutPrototype.getUuid());

		return LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), groupId, false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, name, null, description,
			LayoutConstants.TYPE_PORTLET, false, friendlyURL, serviceContext);
	}

	protected void propagateChanges(Group group) throws Exception {
		LayoutLocalServiceUtil.getLayouts(
			group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
	}

	protected void propagateChanges(Layout layout) throws Exception {
		LayoutLocalServiceUtil.getLayout(layout.getPlid());
	}

	protected Layout updateLayoutTemplateId(
		Layout layout, String layoutTemplateId) throws Exception {

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(
			TestPropsValues.getUserId(), layoutTemplateId);

		return LayoutServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());
	}

}