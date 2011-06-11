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

package com.liferay.portal.atom;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Company;

import java.util.Date;
import java.util.List;

import org.apache.abdera.factory.Factory;
import org.apache.abdera.i18n.iri.IRI;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Person;
import org.apache.abdera.model.Text;
import org.apache.abdera.protocol.server.RequestContext;
import org.apache.abdera.protocol.server.TargetType;
import org.apache.abdera.protocol.server.context.ResponseContextException;
import org.apache.abdera.protocol.server.impl.AbstractEntityCollectionAdapter;

/**
 * @author Igor Spasic
 */
public abstract class BaseEntityCollectionAdapter<T>
	extends AbstractEntityCollectionAdapter<T> {

	public String getAuthor(RequestContext requestContext)
		throws ResponseContextException {

		String author = null;

		try {
			Company company = AtomUtil.getCompany();

			author = company.getName();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return author;
	}

	public String getHref(RequestContext requestContext) {
		return requestContext.urlFor(
			TargetType.TYPE_COLLECTION, collectionName);
	}

	public String getId(RequestContext requestContext) {
		String id = AtomUtil.createIdTagPrefix(collectionName);

		id = id.concat("feed");

		return id;
	}

	public String getId(T entry) throws ResponseContextException {
		String id = AtomUtil.createIdTagPrefix(collectionName);

		id = id.concat("entry:");

		id = id.concat(getName(entry));

		return id;
	}

	public String getName(T entry)
		throws ResponseContextException {

		return getEntryId(entry);
	}

	protected BaseEntityCollectionAdapter(String collectionName) {
		this.collectionName = collectionName;
	}

	protected String addEntryDetails(
		RequestContext requestContext, Entry entry, IRI feedIri, T entryObj)
		throws ResponseContextException {

		String link = getLink(entryObj, feedIri, requestContext);

		entry.addLink(link);
		entry.setId(getId(entryObj));
		entry.setTitle(getTitle(entryObj));
		entry.setUpdated(getUpdated(entryObj));

		List<Person> authors = getAuthors(entryObj, requestContext);
		if (authors != null) {
			for (Person a : authors) {
				entry.addAuthor(a);
			}
		}

		Text t = getSummary(entryObj, requestContext);
		if (t != null) {
			entry.setSummaryElement(t);
		}
		return link;
	}

	protected void addFeedDetails(Feed feed, RequestContext requestContext)
		throws ResponseContextException {

		String currentUrl = requestContext.getResolvedUri().toString();

		super.addFeedDetails(feed, requestContext);

		AtomPager atomPager = AtomUtil.getPager(requestContext);

		if (atomPager != null) {
			atomPager.setFeedPagingLinks(feed, currentUrl);
		}
	}

	protected Feed createFeedBase(RequestContext requestContext)
		throws ResponseContextException {

		String url = requestContext.getResolvedUri().toString();

		url = AtomUtil.resolveCollectionUrl(url, collectionName);

		Factory factory = requestContext.getAbdera().getFactory();
		Feed feed = factory.newFeed();

		feed.setId(getId(requestContext));
		feed.setTitle(getTitle(requestContext));
		feed.addLink(url);
		feed.addLink(url, "self");
		feed.addAuthor(getAuthor(requestContext));
		feed.setUpdated(new Date());

		return feed;
	}

	protected abstract String getEntryId(T entry);

	protected final String collectionName;

	private static Log _log = LogFactoryUtil.getLog(
		BaseEntityCollectionAdapter.class);

}