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

package com.liferay.portal.kernel.search;

import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.List;

import org.hamcrest.collection.IsEmptyCollection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.answers.ThrowsExceptionClass;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author André de Oliveira
 */
@PrepareForTest( {
	AssetRendererFactoryRegistryUtil.class, IndexerRegistryUtil.class,
	MBMessageLocalServiceUtil.class
})
@RunWith(PowerMockRunner.class)
public class SearchResultUtilMBMessageTest
	extends BaseSearchResultUtilTestCase {

	@Before
	public void setUp() {
		doSetUp();

		setUpMBMessage();
	}

	@Test
	public void testMBMessageMissingFromService() throws Exception {
		when(
			mbMessageLocalService.getMessage(ENTRY_CLASS_PK)
		).thenReturn(
			null
		);

		searchSingleDocument(newDocumentMBMessageWithAlternateKey());

		Assert.assertEquals(DOCUMENT_CLASS_NAME, result.getClassName());
		Assert.assertEquals(DOCUMENT_CLASS_PK, result.getClassPK());

		Assert.assertThat(
			"MBMessageLocalService is attempted, no message returned",
			result.getMBMessages(), IsEmptyCollection.empty());

		Mockito.verify(
			mbMessageLocalService
		).getMessage(
			ENTRY_CLASS_PK
		);

		Assert.assertNull(
			"Indexer and AssetRenderer are both attempted, no summary returned",
			result.getSummary());

		verifyStatic();

		IndexerRegistryUtil.getIndexer(DOCUMENT_CLASS_NAME);

		verifyStatic();

		AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
			DOCUMENT_CLASS_NAME);

		assertThatEverythingUnrelatedIsEmpty();
	}

	@Test
	public void testMBMessageWithKeyInDocument() throws Exception {
		when(
			mbMessageLocalService.getMessage(ENTRY_CLASS_PK)
		).thenReturn(
			mbMessage
		);

		mockStatic(
			IndexerRegistryUtil.class,
			new ThrowsExceptionClass(IllegalStateException.class));

		searchSingleDocument(newDocumentMBMessageWithAlternateKey());

		Assert.assertEquals(DOCUMENT_CLASS_NAME, result.getClassName());
		Assert.assertEquals(DOCUMENT_CLASS_PK, result.getClassPK());

		List<MBMessage> mbMessages = result.getMBMessages();

		Assert.assertSame(mbMessage, mbMessages.get(0));
		Assert.assertEquals(1, mbMessages.size());

		Assert.assertNull(result.getSummary());

		assertThatEverythingUnrelatedIsEmpty();
	}

	@Test
	public void testMBMessageWithoutKeyInDocument() throws Exception {
		searchSingleDocument(newDocumentMBMessage());

		Assert.assertEquals(MBMESSAGE_CLASS_NAME, result.getClassName());
		Assert.assertEquals(ENTRY_CLASS_PK, result.getClassPK());

		Assert.assertThat(
			"MBMessageLocalService must not be invoked at all",
			result.getMBMessages(), IsEmptyCollection.empty());

		verifyZeroInteractions(mbMessageLocalService);

		Assert.assertNull(result.getSummary());

		assertThatEverythingUnrelatedIsEmpty();
	}

	@Test
	public void testTwoDocumentsWithSameAlternateKey() {
		long baseEntryPK = ENTRY_CLASS_PK;

		Document documentA = newDocumentMBMessageWithAlternateKey(baseEntryPK);
		Document documentB = newDocumentMBMessageWithAlternateKey(
			baseEntryPK + 1);

		List<SearchResult> searchResults = getSearchResults(
			documentA, documentB);

		Assert.assertEquals("two hits, one result", 1, searchResults.size());

		result = searchResults.get(0);

		Assert.assertEquals(result.getClassName(), DOCUMENT_CLASS_NAME);
		Assert.assertEquals(result.getClassPK(), DOCUMENT_CLASS_PK);
	}

	protected void assertThatEverythingUnrelatedIsEmpty() {
		Assert.assertThat(
			result.getFileEntryTuples(), IsEmptyCollection.empty());
		Assert.assertThat(result.getVersions(), IsEmptyCollection.empty());
	}

	protected Document newDocumentMBMessage() {
		return newDocument(MBMESSAGE_CLASS_NAME);
	}

	protected Document newDocumentMBMessageWithAlternateKey() {
		return newDocumentWithAlternateKey(MBMESSAGE_CLASS_NAME);
	}

	protected Document newDocumentMBMessageWithAlternateKey(long entryClassPK) {
		Document document = newDocument(MBMESSAGE_CLASS_NAME, entryClassPK);

		setKeyInDocument(document);

		return document;
	}

	protected void setUpMBMessage() {
		mockStatic(MBMessageLocalServiceUtil.class, Mockito.CALLS_REAL_METHODS);

		stub(
			method(MBMessageLocalServiceUtil.class, "getService")
		).toReturn(
			mbMessageLocalService
		);
	}

	protected static final String MBMESSAGE_CLASS_NAME =
		MBMessage.class.getName();

	@Mock
	protected MBMessage mbMessage;

	@Mock
	protected MBMessageLocalService mbMessageLocalService;

}