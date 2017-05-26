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

package com.liferay.adaptive.media.image.item.selector.internal.resolver.test;

import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.SynchronousDestination;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
@Sync
public class FileEntryAdaptiveMediaImageURLItemSelectorReturnTypeResolverTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_adaptiveMediaImageConfigurationHelper = _getService(
			AdaptiveMediaImageConfigurationHelper.class);
		_dlAppLocalService = _getService(DLAppLocalService.class);

		_resolver = _getService(
			ItemSelectorReturnTypeResolver.class, _RESOLVER_FILTER);

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		Collection<AdaptiveMediaImageConfigurationEntry> configurationEntries =
			_adaptiveMediaImageConfigurationHelper.
				getAdaptiveMediaImageConfigurationEntries(
					TestPropsValues.getCompanyId(), configurationEntry -> true);

		for (AdaptiveMediaImageConfigurationEntry configurationEntry :
				configurationEntries) {

			_adaptiveMediaImageConfigurationHelper.
				forceDeleteAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(),
					configurationEntry.getUUID());
		}
	}

	@After
	public void tearDown() throws Exception {
		Collection<AdaptiveMediaImageConfigurationEntry> configurationEntries =
			_adaptiveMediaImageConfigurationHelper.
				getAdaptiveMediaImageConfigurationEntries(
					TestPropsValues.getCompanyId(), configurationEntry -> true);

		for (AdaptiveMediaImageConfigurationEntry configurationEntry :
				configurationEntries) {

			_adaptiveMediaImageConfigurationHelper.
				forceDeleteAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(),
					configurationEntry.getUUID());
		}
	}

	@Test
	public void testAddingFileEntryWithHDMediaQueries() throws Exception {
		try (DestinationReplacer destinationReplacer = new DestinationReplacer(
				"liferay/adaptive_media_processor")) {

			_addTestVariant("small", "uuid0", 50, 50);
			_addTestVariant("small.hd", "uuid1", 100, 100);
			_addTestVariant("medium", "uuid2", 300, 300);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId());

			final FileEntry fileEntry = _addImageFileEntry(serviceContext);

			String value = _resolver.getValue(fileEntry, null);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			String defaultSource = jsonObject.getString("defaultSource");

			Assert.assertEquals(
				DLUtil.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null,
					StringPool.BLANK, false, false),
				defaultSource);

			JSONArray sourcesJSONArray = jsonObject.getJSONArray("sources");

			Assert.assertEquals(3, sourcesJSONArray.length());

			_assertHDSrcSource(
				sourcesJSONArray.getJSONObject(0), fileEntry.getFileEntryId(),
				"uuid0", "uuid1", fileEntry.getTitle());
			_assertSrcSource(
				sourcesJSONArray.getJSONObject(1), fileEntry.getFileEntryId(),
				"uuid1", fileEntry.getTitle());
			_assertSrcSource(
				sourcesJSONArray.getJSONObject(2), fileEntry.getFileEntryId(),
				"uuid2", fileEntry.getTitle());

			_assertAttibutes(sourcesJSONArray.getJSONObject(0), 50, 0);
			_assertAttibutes(sourcesJSONArray.getJSONObject(1), 100, 50);
			_assertAttibutes(sourcesJSONArray.getJSONObject(2), 300, 100);
		}
	}

	@Test
	public void testAddingFileEntryWithImageCreatesMedia() throws Exception {
		try (DestinationReplacer destinationReplacer = new DestinationReplacer(
				"liferay/adaptive_media_processor")) {

			_addTestVariant("small", "uuid0", 50, 50);
			_addTestVariant("big", "uuid1", 400, 280);
			_addTestVariant("medium", "uuid2", 300, 200);
			_addTestVariant("extra", "uuid3", 500, 330);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId());

			final FileEntry fileEntry = _addImageFileEntry(serviceContext);

			String value = _resolver.getValue(fileEntry, null);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			String defaultSource = jsonObject.getString("defaultSource");

			Assert.assertEquals(
				DLUtil.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null,
					StringPool.BLANK, false, false),
				defaultSource);

			JSONArray sourcesJSONArray = jsonObject.getJSONArray("sources");

			Assert.assertEquals(4, sourcesJSONArray.length());

			_assertSrcSource(
				sourcesJSONArray.getJSONObject(0), fileEntry.getFileEntryId(),
				"uuid0", fileEntry.getTitle());
			_assertSrcSource(
				sourcesJSONArray.getJSONObject(1), fileEntry.getFileEntryId(),
				"uuid2", fileEntry.getTitle());
			_assertSrcSource(
				sourcesJSONArray.getJSONObject(2), fileEntry.getFileEntryId(),
				"uuid1", fileEntry.getTitle());
			_assertSrcSource(
				sourcesJSONArray.getJSONObject(3), fileEntry.getFileEntryId(),
				"uuid3", fileEntry.getTitle());

			_assertAttibutes(sourcesJSONArray.getJSONObject(0), 50, 0);
			_assertAttibutes(sourcesJSONArray.getJSONObject(1), 200, 50);
			_assertAttibutes(sourcesJSONArray.getJSONObject(2), 280, 200);
			_assertAttibutes(sourcesJSONArray.getJSONObject(3), 330, 280);
		}
	}

	@Test
	public void testHDMediaQueryAppliesWhenHeightHas1PXLessThanExpected()
		throws Exception {

		try (DestinationReplacer destinationReplacer = new DestinationReplacer(
				"liferay/adaptive_media_processor")) {

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId());

			final FileEntry fileEntry = _addImageFileEntry(serviceContext);

			_addTestVariant("small", "uuid0", 50, 50);
			_addTestVariant("small.hd", "uuid1", 99, 100);

			String value = _resolver.getValue(fileEntry, null);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			String defaultSource = jsonObject.getString("defaultSource");

			Assert.assertEquals(
				DLUtil.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null,
					StringPool.BLANK, false, false),
				defaultSource);

			JSONArray sourcesJSONArray = jsonObject.getJSONArray("sources");

			Assert.assertEquals(2, sourcesJSONArray.length());

			_assertHDSrcSource(
				sourcesJSONArray.getJSONObject(0), fileEntry.getFileEntryId(),
				"uuid0", "uuid1", fileEntry.getTitle());
			_assertSrcSource(
				sourcesJSONArray.getJSONObject(1), fileEntry.getFileEntryId(),
				"uuid1", fileEntry.getTitle());

			_assertAttibutes(sourcesJSONArray.getJSONObject(0), 50, 0);
			_assertAttibutes(sourcesJSONArray.getJSONObject(1), 100, 50);
		}
	}

	@Test
	public void testHDMediaQueryAppliesWhenHeightHas1PXMoreThanExpected()
		throws Exception {

		try (DestinationReplacer destinationReplacer = new DestinationReplacer(
				"liferay/adaptive_media_processor")) {

			_addTestVariant("small", "uuid0", 50, 50);
			_addTestVariant("small.hd", "uuid1", 101, 100);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId());

			final FileEntry fileEntry = _addImageFileEntry(serviceContext);

			String value = _resolver.getValue(fileEntry, null);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			String defaultSource = jsonObject.getString("defaultSource");

			Assert.assertEquals(
				DLUtil.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null,
					StringPool.BLANK, false, false),
				defaultSource);

			JSONArray sourcesJSONArray = jsonObject.getJSONArray("sources");

			Assert.assertEquals(2, sourcesJSONArray.length());

			_assertHDSrcSource(
				sourcesJSONArray.getJSONObject(0), fileEntry.getFileEntryId(),
				"uuid0", "uuid1", fileEntry.getTitle());
			_assertSrcSource(
				sourcesJSONArray.getJSONObject(1), fileEntry.getFileEntryId(),
				"uuid1", fileEntry.getTitle());

			_assertAttibutes(sourcesJSONArray.getJSONObject(0), 50, 0);
			_assertAttibutes(sourcesJSONArray.getJSONObject(1), 100, 50);
		}
	}

	@Test
	public void testHDMediaQueryAppliesWhenWidthHas1PXLessThanExpected()
		throws Exception {

		try (DestinationReplacer destinationReplacer = new DestinationReplacer(
				"liferay/adaptive_media_processor")) {

			_addTestVariant("small", "uuid0", 50, 50);
			_addTestVariant("small.hd", "uuid1", 100, 99);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId());

			final FileEntry fileEntry = _addImageFileEntry(serviceContext);

			String value = _resolver.getValue(fileEntry, null);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			String defaultSource = jsonObject.getString("defaultSource");

			Assert.assertEquals(
				DLUtil.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null,
					StringPool.BLANK, false, false),
				defaultSource);

			JSONArray sourcesJSONArray = jsonObject.getJSONArray("sources");

			Assert.assertEquals(2, sourcesJSONArray.length());

			_assertHDSrcSource(
				sourcesJSONArray.getJSONObject(0), fileEntry.getFileEntryId(),
				"uuid0", "uuid1", fileEntry.getTitle());
			_assertSrcSource(
				sourcesJSONArray.getJSONObject(1), fileEntry.getFileEntryId(),
				"uuid1", fileEntry.getTitle());

			_assertAttibutes(sourcesJSONArray.getJSONObject(0), 50, 0);
			_assertAttibutes(sourcesJSONArray.getJSONObject(1), 99, 50);
		}
	}

	@Test
	public void testHDMediaQueryAppliesWhenWidthHas1PXMoreThanExpected()
		throws Exception {

		try (DestinationReplacer destinationReplacer = new DestinationReplacer(
				"liferay/adaptive_media_processor")) {

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId());

			final FileEntry fileEntry = _addImageFileEntry(serviceContext);

			_addTestVariant("small", "uuid0", 50, 50);
			_addTestVariant("small.hd", "uuid1", 100, 101);

			String value = _resolver.getValue(fileEntry, null);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			String defaultSource = jsonObject.getString("defaultSource");

			Assert.assertEquals(
				DLUtil.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null,
					StringPool.BLANK, false, false),
				defaultSource);

			JSONArray sourcesJSONArray = jsonObject.getJSONArray("sources");

			Assert.assertEquals(2, sourcesJSONArray.length());

			_assertHDSrcSource(
				sourcesJSONArray.getJSONObject(0), fileEntry.getFileEntryId(),
				"uuid0", "uuid1", fileEntry.getTitle());
			_assertSrcSource(
				sourcesJSONArray.getJSONObject(1), fileEntry.getFileEntryId(),
				"uuid1", fileEntry.getTitle());

			_assertAttibutes(sourcesJSONArray.getJSONObject(0), 50, 0);
			_assertAttibutes(sourcesJSONArray.getJSONObject(1), 101, 50);
		}
	}

	@Test
	public void testHDMediaQueryNotAppliesWhenHeightHas2PXLessThanExpected()
		throws Exception {

		try (DestinationReplacer destinationReplacer = new DestinationReplacer(
				"liferay/adaptive_media_processor")) {

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId());

			final FileEntry fileEntry = _addImageFileEntry(serviceContext);

			_addTestVariant("small", "uuid0", 50, 50);
			_addTestVariant("small.hd", "uuid1", 98, 200);

			String value = _resolver.getValue(fileEntry, null);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			String defaultSource = jsonObject.getString("defaultSource");

			Assert.assertEquals(
				DLUtil.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null,
					StringPool.BLANK, false, false),
				defaultSource);

			JSONArray sourcesJSONArray = jsonObject.getJSONArray("sources");

			Assert.assertEquals(2, sourcesJSONArray.length());

			_assertSrcSource(
				sourcesJSONArray.getJSONObject(0), fileEntry.getFileEntryId(),
				"uuid0", fileEntry.getTitle());
			_assertSrcSource(
				sourcesJSONArray.getJSONObject(1), fileEntry.getFileEntryId(),
				"uuid1", fileEntry.getTitle());

			_assertAttibutes(sourcesJSONArray.getJSONObject(0), 50, 0);
			_assertAttibutes(sourcesJSONArray.getJSONObject(1), 200, 50);
		}
	}

	@Test
	public void testHDMediaQueryNotAppliesWhenHeightHas2PXMoreThanExpected()
		throws Exception {

		try (DestinationReplacer destinationReplacer = new DestinationReplacer(
				"liferay/adaptive_media_processor")) {

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId());

			final FileEntry fileEntry = _addImageFileEntry(serviceContext);

			_addTestVariant("small", "uuid0", 50, 50);
			_addTestVariant("small.hd", "uuid1", 102, 200);

			String value = _resolver.getValue(fileEntry, null);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			String defaultSource = jsonObject.getString("defaultSource");

			Assert.assertEquals(
				DLUtil.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null,
					StringPool.BLANK, false, false),
				defaultSource);

			JSONArray sourcesJSONArray = jsonObject.getJSONArray("sources");

			Assert.assertEquals(2, sourcesJSONArray.length());

			_assertSrcSource(
				sourcesJSONArray.getJSONObject(0), fileEntry.getFileEntryId(),
				"uuid0", fileEntry.getTitle());
			_assertSrcSource(
				sourcesJSONArray.getJSONObject(1), fileEntry.getFileEntryId(),
				"uuid1", fileEntry.getTitle());

			_assertAttibutes(sourcesJSONArray.getJSONObject(0), 50, 0);
			_assertAttibutes(sourcesJSONArray.getJSONObject(1), 200, 50);
		}
	}

	@Test
	public void testHDMediaQueryNotAppliesWhenWidthHas2PXLessThanExpected()
		throws Exception {

		try (DestinationReplacer destinationReplacer = new DestinationReplacer(
				"liferay/adaptive_media_processor")) {

			_addTestVariant("small", "uuid0", 50, 50);
			_addTestVariant("small.hd", "uuid1", 200, 98);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId());

			final FileEntry fileEntry = _addImageFileEntry(serviceContext);

			String value = _resolver.getValue(fileEntry, null);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			String defaultSource = jsonObject.getString("defaultSource");

			Assert.assertEquals(
				DLUtil.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null,
					StringPool.BLANK, false, false),
				defaultSource);

			JSONArray sourcesJSONArray = jsonObject.getJSONArray("sources");

			Assert.assertEquals(2, sourcesJSONArray.length());

			_assertSrcSource(
				sourcesJSONArray.getJSONObject(0), fileEntry.getFileEntryId(),
				"uuid0", fileEntry.getTitle());
			_assertSrcSource(
				sourcesJSONArray.getJSONObject(1), fileEntry.getFileEntryId(),
				"uuid1", fileEntry.getTitle());

			_assertAttibutes(sourcesJSONArray.getJSONObject(0), 50, 0);
			_assertAttibutes(sourcesJSONArray.getJSONObject(1), 98, 50);
		}
	}

	@Test
	public void testHDMediaQueryNotAppliesWhenWidthHas2PXMoreThanExpected()
		throws Exception {

		try (DestinationReplacer destinationReplacer = new DestinationReplacer(
				"liferay/adaptive_media_processor")) {

			_addTestVariant("small", "uuid0", 50, 50);
			_addTestVariant("small.hd", "uuid1", 200, 102);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId());

			final FileEntry fileEntry = _addImageFileEntry(serviceContext);

			String value = _resolver.getValue(fileEntry, null);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			String defaultSource = jsonObject.getString("defaultSource");

			Assert.assertEquals(
				DLUtil.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null,
					StringPool.BLANK, false, false),
				defaultSource);

			JSONArray sourcesJSONArray = jsonObject.getJSONArray("sources");

			Assert.assertEquals(2, sourcesJSONArray.length());

			_assertSrcSource(
				sourcesJSONArray.getJSONObject(0), fileEntry.getFileEntryId(),
				"uuid0", fileEntry.getTitle());
			_assertSrcSource(
				sourcesJSONArray.getJSONObject(1), fileEntry.getFileEntryId(),
				"uuid1", fileEntry.getTitle());

			_assertAttibutes(sourcesJSONArray.getJSONObject(0), 50, 0);
			_assertAttibutes(sourcesJSONArray.getJSONObject(1), 102, 50);
		}
	}

	public class DestinationReplacer implements AutoCloseable {

		public DestinationReplacer(String destinationName) {
			MessageBus messageBus = MessageBusUtil.getMessageBus();

			_destination = messageBus.getDestination(destinationName);

			SynchronousDestination synchronousDestination =
				new SynchronousDestination();

			synchronousDestination.setName(destinationName);

			messageBus.replace(synchronousDestination);
		}

		@Override
		public void close() throws Exception {
			MessageBus messageBus = MessageBusUtil.getMessageBus();

			messageBus.replace(_destination);
		}

		private final Destination _destination;

	}

	private FileEntry _addImageFileEntry(ServiceContext serviceContext)
		throws Exception {

		return _dlAppLocalService.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.IMAGE_JPEG,
			_getImageBytes(), serviceContext);
	}

	private void _addTestVariant(
			String name, String uuid, int height, int width)
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", String.valueOf(height));
		properties.put("max-width", String.valueOf(width));

		_adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), name, StringPool.BLANK, uuid,
				properties);
	}

	private void _assertAttibutes(
		JSONObject sourceJSONObject, int expectedMaxWidth,
		int expectedMinWidth) {

		JSONObject attributesJSONObject = sourceJSONObject.getJSONObject(
			"attributes");

		JSONArray attributeNamesJSONArray = attributesJSONObject.names();

		boolean assertedMaxWidth = false;
		boolean assertedMinWidth = false;

		for (int i = 0; i < attributeNamesJSONArray.length(); i++) {
			String key = attributeNamesJSONArray.getString(i);

			Assert.assertTrue(
				"Unexpected attribute found '" + key + "'",
				key.equals("max-width") || key.equals("min-width"));

			String value = attributesJSONObject.getString(key);

			if (key.equals("max-width")) {
				Assert.assertEquals(expectedMaxWidth + "px", value);

				assertedMaxWidth = true;
			}
			else if (key.equals("min-width")) {
				Assert.assertEquals(expectedMinWidth + "px", value);

				assertedMinWidth = true;
			}
		}

		Assert.assertTrue(
			"Couldn't find expected max-width of '" + expectedMaxWidth +
				"' in '" + sourceJSONObject.toString() + "'",
			(expectedMaxWidth == 0) || assertedMaxWidth);
		Assert.assertTrue(
			"Couldn't find expected min-width of '" + expectedMinWidth +
				"' in '" + sourceJSONObject.toString() + "'",
			(expectedMinWidth == 0) || assertedMinWidth);
	}

	private void _assertHDSrcSource(
		JSONObject sourceJSONObject, long fileEntryId,
		String originalConfigurationEntryUuid, String hdConfigurationEntryUuid,
		String title) {

		String srcSource = sourceJSONObject.getString("src");

		StringBundler sb = new StringBundler();

		sb.append("/o/adaptive-media/image/");
		sb.append(fileEntryId);
		sb.append("/");
		sb.append(originalConfigurationEntryUuid);
		sb.append("/");
		sb.append(title);
		sb.append(", ");
		sb.append("/o/adaptive-media/image/");
		sb.append(fileEntryId);
		sb.append("/");
		sb.append(hdConfigurationEntryUuid);
		sb.append("/");
		sb.append(title);
		sb.append(" 2x");

		Assert.assertEquals(sb.toString(), srcSource);
	}

	private void _assertSrcSource(
		JSONObject sourceJSONObject, long fileEntryId,
		String configurationEntryUuid, String title) {

		String srcSource = sourceJSONObject.getString("src");

		Assert.assertEquals(
			"/o/adaptive-media/image/" + fileEntryId + "/" +
				configurationEntryUuid + "/" + title,
			srcSource);
	}

	private byte[] _getImageBytes() throws Exception {
		return FileUtil.getBytes(
			FileEntryAdaptiveMediaImageURLItemSelectorReturnTypeResolverTest.
				class,
			"/com/liferay/adaptive/media/image/item/selector/internal" +
				"/resolver/test/dependencies/image.jpg");
	}

	private <T> T _getService(Class<T> clazz) {
		try {
			Registry registry = RegistryUtil.getRegistry();

			return registry.getService(clazz);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private <T> T _getService(Class<T> clazz, String filter) {
		try {
			Registry registry = RegistryUtil.getRegistry();

			Collection<T> services = registry.getServices(clazz, filter);

			return services.iterator().next();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static final String _RESOLVER_FILTER =
		"(objectClass=com.liferay.adaptive.media.image.item.selector." +
			"internal.resolver.FileEntryAdaptiveMediaImageURLItem" +
				"SelectorReturnTypeResolver)";

	private AdaptiveMediaImageConfigurationHelper
		_adaptiveMediaImageConfigurationHelper;
	private DLAppLocalService _dlAppLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private ItemSelectorReturnTypeResolver _resolver;

}