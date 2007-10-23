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

package com.liferay.portal.lar;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portlet.tags.model.TagsAsset;
import com.liferay.portlet.tags.model.TagsEntry;
import com.liferay.portlet.tags.service.TagsAssetLocalServiceUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <a href="PortletDataContext.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * Holds context information that is used during exporting adn importing portlet
 * data.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond AugÃ©
 *
 */
public class PortletDataContextImpl implements PortletDataContext {

	public PortletDataContextImpl(
		long companyId, long groupId, Map parameterMap, Set primaryKeys,
		ZipReader zipReader) {

		_companyId = companyId;
		_groupId = groupId;
		_parameterMap = parameterMap;
		_primaryKeys = primaryKeys;
		_zipReader = zipReader;
		_zipWriter = null;
	}

	public PortletDataContextImpl(
		long companyId, long groupId, Map parameterMap, Set primaryKeys,
		ZipWriter zipWriter) {

		_companyId = companyId;
		_groupId = groupId;
		_parameterMap = parameterMap;
		_primaryKeys = primaryKeys;
		_zipReader = null;
		_zipWriter = zipWriter;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public long getPlid() {
		return _plid;
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public Map getParameterMap() {
		return _parameterMap;
	}

	public Set getPrimaryKeys() {
		return _primaryKeys;
	}

	public boolean addPrimaryKey(Class classObj, Object primaryKey) {
		boolean value = hasPrimaryKey(classObj, primaryKey);

		if (!value) {
			_primaryKeys.add(getPrimaryKeyString(classObj, primaryKey));
		}

		return value;
	}

	public boolean hasPrimaryKey(Class classObj, Object primaryKey) {
		return _primaryKeys.contains(getPrimaryKeyString(classObj, primaryKey));
	}

	public String[] getTagsEntries(Class classObj, Object primaryKey) {
		return (String[])_tagsEntriesMap.get(
			getPrimaryKeyString(classObj, primaryKey));
	}

	public String[] getTagsEntries(String className, Object primaryKey) {
		return (String[])_tagsEntriesMap.get(
			getPrimaryKeyString(className, primaryKey));
	}

	public Map getTagsEntries() {
		return _tagsEntriesMap;
	}

	public void addTagsEntries(Class classObj, Object classPK)
		throws PortalException, SystemException {

		TagsAsset tagsAsset = TagsAssetLocalServiceUtil.getAsset(
			classObj.getName(), ((Long)classPK).longValue());

		List tagsEntriesList = tagsAsset.getEntries();

		if (tagsEntriesList.size() == 0) {
			return;
		}

		String[] tagsEntries = new String[tagsEntriesList.size()];

		Iterator itr = tagsEntriesList.iterator();

		for (int i = 0; itr.hasNext(); i++) {
			TagsEntry tagsEntry = (TagsEntry)itr.next();

			tagsEntries[i] = tagsEntry.getName();
		}

		_tagsEntriesMap.put(
			getPrimaryKeyString(classObj, classPK), tagsEntries);
	}

	public void addTagsEntries(
			String className, Object classPK, String[] values)
		throws PortalException, SystemException {

		_tagsEntriesMap.put(getPrimaryKeyString(className, classPK), values);
	}

	public ZipReader getZipReader() {
		return _zipReader;
	}

	public ZipWriter getZipWriter() {
		return _zipWriter;
	}

	protected String getPrimaryKeyString(Class classObj, Object primaryKey) {
		StringMaker sm = new StringMaker();

		return getPrimaryKeyString(classObj.getName(), primaryKey);
	}

	protected String getPrimaryKeyString(String className, Object primaryKey) {
		StringMaker sm = new StringMaker();

		sm.append(className);
		sm.append(StringPool.POUND);
		sm.append(primaryKey);

		return sm.toString();
	}

	private long _companyId;
	private long _groupId;
	private long _plid;
	private Map _parameterMap;
	private Map _tagsEntriesMap = new HashMap();
	private Set _primaryKeys;
	private ZipReader _zipReader;
	private ZipWriter _zipWriter;

}