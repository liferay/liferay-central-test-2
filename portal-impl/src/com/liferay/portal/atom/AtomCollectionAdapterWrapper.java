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

import com.liferay.portal.kernel.atom.AtomCollectionAdapter;
import com.liferay.portal.kernel.atom.AtomException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.abdera.factory.Factory;
import org.apache.abdera.i18n.iri.IRI;
import org.apache.abdera.model.Content;
import org.apache.abdera.model.Person;
import org.apache.abdera.protocol.server.RequestContext;
import org.apache.abdera.protocol.server.context.ResponseContextException;

/**
 * @author Igor Spasic
 */
public class AtomCollectionAdapterWrapper<E> extends
	BaseEntityCollectionAdapter<E> {

	public AtomCollectionAdapterWrapper(
		AtomCollectionAdapter<E> atomCollectionAdapter) {

		super(atomCollectionAdapter.getCollectionName().toLowerCase());

		_atomCollectionAdapter = atomCollectionAdapter;
	}

	public void deleteEntry(String resourceName, RequestContext requestContext)
		throws ResponseContextException {

		try {
			_atomCollectionAdapter.deleteEntry(resourceName);
		}
		catch (AtomException e) {
			throw new ResponseContextException(e.getErrorCode(), e.getCause());
		}
	}

	public List<Person> getAuthors(E entry, RequestContext requestContext)
		throws ResponseContextException {

		List<String> authorList = _atomCollectionAdapter.getEntryAuthors(entry);

		List<Person> persons = new ArrayList<Person>();

		Factory abderaFactory = requestContext.getAbdera().getFactory();

		for (String author : authorList) {
			Person person = abderaFactory.newAuthor();

			person.setName(author);

			persons.add(person);
		}

		return persons;
	}

	public Object getContent(E entry, RequestContext requestContext)
		throws ResponseContextException {

		Content content = requestContext.getAbdera().getFactory().newContent(
			Content.Type.HTML);

		content.setText(_atomCollectionAdapter.getEntryContent(entry));

		return content;
	}

	public Iterable<E> getEntries(RequestContext requestContext)
		throws ResponseContextException {

		try {
			return _atomCollectionAdapter.getFeedEntries(
				new AtomRequestContextImpl(requestContext));
		}
		catch (AtomException e) {
			throw new ResponseContextException(e.getErrorCode(), e.getCause());
		}
	}

	public E getEntry(String resourceName, RequestContext requestContext)
		throws ResponseContextException {

		try {
			return _atomCollectionAdapter.getEntry(resourceName);
		}
		catch (AtomException e) {
			throw new ResponseContextException(e.getErrorCode(), e.getCause());
		}
	}

	public String getTitle(E entry) throws ResponseContextException {
		return _atomCollectionAdapter.getEntryTitle(entry);
	}

	public String getTitle(RequestContext requestContext) {
		return _atomCollectionAdapter.getFeedTitle(
			new AtomRequestContextImpl(requestContext));
	}

	public Date getUpdated(E entry) throws ResponseContextException {
		return _atomCollectionAdapter.getEntryUpdated(entry);
	}

	public E postEntry(
		String title, IRI id, String summary, Date updated,
		List<Person> authors, Content content, RequestContext requestContext)
		throws ResponseContextException {

		try {
			return _atomCollectionAdapter.postEntry(title, summary, updated,
				content.getText(), new AtomRequestContextImpl(requestContext));
		}
		catch (AtomException e) {
			throw new ResponseContextException(e.getErrorCode(), e.getCause());
		}
	}

	public void putEntry(
		E entry, String title, Date updated, List<Person> authors,
		String summary, Content content, RequestContext requestContext)
		throws ResponseContextException {

		try {
			_atomCollectionAdapter.putEntry(entry, title, updated, summary,
				content.getText(), new AtomRequestContextImpl(requestContext));
		}
		catch (AtomException e) {
			throw new ResponseContextException(e.getErrorCode(), e.getCause());
		}
	}

	protected String getEntryId(E entry) {
		return _atomCollectionAdapter.getEntryId(entry);
	}

	private final AtomCollectionAdapter<E> _atomCollectionAdapter;

}