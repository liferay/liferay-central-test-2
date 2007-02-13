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

package com.liferay.portlet.tags.service;

/**
 * <a href="TagsEntryService.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface TagsEntryService {
	public com.liferay.portlet.tags.model.TagsEntry addEntry(
		java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public void deleteEntry(long entryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public java.util.List getEntries(java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public java.util.List search(java.lang.String companyId,
		java.lang.String name, java.lang.String[] properties)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException;

	public java.util.List search(java.lang.String companyId,
		java.lang.String name, java.lang.String[] properties, int begin, int end)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException;

	public java.lang.String searchAutocomplete(java.lang.String companyId,
		java.lang.String name, java.lang.String[] properties, int begin, int end)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException;

	public int searchCount(java.lang.String companyId, java.lang.String name,
		java.lang.String[] properties)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException;

	public com.liferay.portlet.tags.model.TagsEntry updateEntry(long entryId,
		java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;

	public com.liferay.portlet.tags.model.TagsEntry updateEntry(long entryId,
		java.lang.String name, java.lang.String[] properties)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException;
}