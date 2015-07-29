/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This file is part of Liferay Social Office. Liferay Social Office is free
 * software: you can redistribute it and/or modify it under the terms of the GNU
 * Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * Liferay Social Office is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Liferay Social Office. If not, see http://www.gnu.org/licenses/agpl-3.0.html.
 */

package com.liferay.microblogs.model.impl;

import com.liferay.microblogs.model.MicroblogsEntry;
import com.liferay.microblogs.service.MicroblogsEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class MicroblogsEntryImpl extends MicroblogsEntryBaseImpl {

	public MicroblogsEntryImpl() {
	}

	public long fetchParentMicroblogsEntryUserId() {
		if (getMicroblogsEntryId() == getParentMicroblogsEntryId()) {
			return getUserId();
		}

		MicroblogsEntry microblogsEntry =
			MicroblogsEntryLocalServiceUtil.fetchMicroblogsEntry(
				getParentMicroblogsEntryId());

		if (microblogsEntry == null) {
			return 0;
		}

		return microblogsEntry.getUserId();
	}

	public long getParentMicroblogsEntryUserId() throws PortalException {
		if (getMicroblogsEntryId() == getParentMicroblogsEntryId()) {
			return getUserId();
		}

		MicroblogsEntry microblogsEntry =
			MicroblogsEntryLocalServiceUtil.getMicroblogsEntry(
				getParentMicroblogsEntryId());

		return microblogsEntry.getUserId();
	}

}