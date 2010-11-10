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

package com.liferay.portal.search;

import com.liferay.portal.kernel.messaging.proxy.BaseProxyBean;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchException;

import java.util.Collection;

/**
 * @author Bruno Farache
 * @author Tina Tian
 */
public class IndexWriterProxyBean extends BaseProxyBean implements IndexWriter {

	public void addDocument(long companyId, Document document)
		throws SearchException {

		throw new UnsupportedOperationException();
	}

	public void addDocuments(long companyId, Collection<Document> documents)
		throws SearchException {

		throw new UnsupportedOperationException();
	}

	public void deleteDocument(long companyId, String uid)
		throws SearchException {

		throw new UnsupportedOperationException();
	}

	public void deleteDocuments(long companyId, Collection<String> uids)
		throws SearchException {

		throw new UnsupportedOperationException();
	}

	public void deletePortletDocuments(long companyId, String portletId)
		throws SearchException {

		throw new UnsupportedOperationException();
	}

	public void updateDocument(long companyId, Document document)
		throws SearchException {

		throw new UnsupportedOperationException();
	}

	public void updateDocuments(long companyId, Collection<Document> documents)
		throws SearchException {

		throw new UnsupportedOperationException();
	}

}