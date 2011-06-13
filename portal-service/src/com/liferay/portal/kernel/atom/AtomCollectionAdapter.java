/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.atom;

import java.util.Date;
import java.util.List;

/**
 * @author Igor Spasic
 */
public interface AtomCollectionAdapter<E> {

	public void deleteEntry(String resourceName) throws AtomException;

	public String getCollectionName();

	public E getEntry(String resourceName) throws AtomException;

	public List<String> getEntryAuthors(E entry);

	public String getEntryContent(E entry);

	public String getEntryId(E entry);

	public String getEntryTitle(E entry);

	public Date getEntryUpdated(E entry);

	public Iterable<E> getFeedEntries(AtomRequestContext atomRequestContext)
		throws AtomException;

	public String getFeedTitle(AtomRequestContext atomRequestContext);

	public E postEntry(
			String title, String summary, String content, Date date,
			AtomRequestContext atomRequestContext)
		throws AtomException;

	public void putEntry(
			E entry, String title, String summary, String content, Date date,
			AtomRequestContext atomRequestContext)
		throws AtomException;

}