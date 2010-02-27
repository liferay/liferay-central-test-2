/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search;

/**
 * <a href="IndexWriter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public interface IndexWriter {

	public void addDocument(long companyId, Document document)
		throws SearchException;

	public void deleteDocument(long companyId, String uid)
		throws SearchException;

	public void deletePortletDocuments(long companyId, String portletId)
		throws SearchException;

	public void updateDocument(long companyId, String uid, Document document)
		throws SearchException;

}