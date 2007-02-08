/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.tags.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portlet.tags.model.TagsEntry;
import com.liferay.portlet.tags.service.TagsEntryLocalServiceUtil;
import com.liferay.portlet.tags.service.TagsEntryService;

import java.util.List;

/**
 * <a href="TagsEntryServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsEntryServiceImpl
	extends PrincipalBean implements TagsEntryService {

	public TagsEntry addEntry(String name)
		throws PortalException, SystemException {

		return TagsEntryLocalServiceUtil.addEntry(getUserId(), name);
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		TagsEntryLocalServiceUtil.deleteEntry(entryId);
	}

	public List search(String companyId, String name, String[] properties)
		throws SystemException {

		return TagsEntryLocalServiceUtil.search(companyId, name, properties);
	}

	public List search(
		String companyId, String name, String[] properties, int begin, int end)
		throws SystemException {

		return TagsEntryLocalServiceUtil.search(
			companyId, name, properties, begin, end);
	}

	public String searchAutocomplete(
			String companyId, String name, String[] properties, int begin,
			int end)
		throws SystemException {

		return TagsEntryLocalServiceUtil.searchAutocomplete(
			companyId, name, properties, begin, end);
	}

	public int searchCount(String companyId, String name, String[] properties)
		throws SystemException {

		return TagsEntryLocalServiceUtil.searchCount(
			companyId, name, properties);
	}

	public TagsEntry updateEntry(long entryId, String name)
		throws PortalException, SystemException {

		return TagsEntryLocalServiceUtil.updateEntry(entryId, name);
	}

	public TagsEntry updateEntry(long entryId, String name, String[] properties)
		throws PortalException, SystemException {

		return TagsEntryLocalServiceUtil.updateEntry(
			getUserId(), entryId, name, properties);
	}

}