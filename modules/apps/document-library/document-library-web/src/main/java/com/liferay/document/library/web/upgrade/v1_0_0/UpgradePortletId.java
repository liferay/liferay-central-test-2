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

package com.liferay.document.library.web.upgrade.v1_0_0;

import com.liferay.document.library.web.constants.DLPortletKeys;

/**
 * @author Sergio González
 */
public class UpgradePortletId
	extends com.liferay.portal.upgrade.util.UpgradePortletId {

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			new String[] {"20", DLPortletKeys.DOCUMENT_LIBRARY},
			new String[] {"199", DLPortletKeys.DOCUMENT_LIBRARY_ADMIN},
			new String[] {"110", DLPortletKeys.DOCUMENT_LIBRARY_DISPLAY},
			new String[] {"31", DLPortletKeys.MEDIA_GALLERY_DISPLAY}
		};
	}

}