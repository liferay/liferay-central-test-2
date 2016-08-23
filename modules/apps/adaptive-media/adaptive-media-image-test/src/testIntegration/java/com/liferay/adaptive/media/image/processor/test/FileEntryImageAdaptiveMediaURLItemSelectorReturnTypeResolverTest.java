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

package com.liferay.adaptive.media.image.processor.test;

import com.liferay.adaptive.media.image.item.selector.FileEntryImageAdaptiveMediaURLItemSelectorReturnTypeResolver;
import com.liferay.adaptive.media.image.test.util.DestinationReplacer;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
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
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.io.IOException;

import java.util.Dictionary;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
@Sync
public class FileEntryImageAdaptiveMediaURLItemSelectorReturnTypeResolverTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_configurationAdmin = _getService(ConfigurationAdmin.class);
		_dlAppLocalService = _getService(DLAppLocalService.class);
		_resolver = _getService(
			FileEntryImageAdaptiveMediaURLItemSelectorReturnTypeResolver.class);

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_addTestVariant();
	}

	@Test
	public void testAddingFileEntryWithImageCreatesMedia() throws Exception {
		try (DestinationReplacer destinationReplacer = new DestinationReplacer(
				"liferay/adaptive_media_processor")) {

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
				"uuid0");
			_assertSrcSource(
				sourcesJSONArray.getJSONObject(1), fileEntry.getFileEntryId(),
				"uuid2");
			_assertSrcSource(
				sourcesJSONArray.getJSONObject(2), fileEntry.getFileEntryId(),
				"uuid1");
			_assertSrcSource(
				sourcesJSONArray.getJSONObject(3), fileEntry.getFileEntryId(),
				"uuid3");

			_assertAttibutes(sourcesJSONArray.getJSONObject(0), 100, 0);
			_assertAttibutes(sourcesJSONArray.getJSONObject(1), 800, 100);
			_assertAttibutes(sourcesJSONArray.getJSONObject(2), 1200, 800);
			_assertAttibutes(sourcesJSONArray.getJSONObject(3), 2400, 1200);
		}
	}

	private FileEntry _addImageFileEntry(ServiceContext serviceContext)
		throws Exception {

		return _dlAppLocalService.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.IMAGE_JPEG,
			_getImageBytes(), serviceContext);
	}

	private void _addTestVariant() throws IOException {
		Configuration configuration = _configurationAdmin.getConfiguration(
			"com.liferay.adaptive.media.image.internal.configuration." +
				"ImageAdaptiveMediaCompanyConfiguration");

		Dictionary<String, Object> properties = configuration.getProperties();

		if (properties == null) {
			properties = new HashMapDictionary<>();
		}

		properties.put(
			"imageVariants",
			new String[] {
				"small:uuid0:width=100;height=100",
				"big:uuid1:width=1200;height=800",
				"medium:uuid2:width=800;height=600",
				"extra:uuid3:width=2400;height=1800"
			});

		configuration.update(properties);
	}

	private void _assertAttibutes(
		JSONObject sourceJSONObject, int expectedMaxWidth,
		int expectedMinWidth) {

		JSONArray attributesJSONArray = sourceJSONObject.getJSONArray(
			"attributes");

		boolean assertedMaxWidth = false;
		boolean assertedMinWidth = false;

		for (int i = 0; i < attributesJSONArray.length(); i++) {
			JSONObject jsonObject = attributesJSONArray.getJSONObject(i);

			String key = jsonObject.getString("key");

			if (key.equals("max-width")) {
				String value = jsonObject.getString("value");

				Assert.assertEquals(expectedMaxWidth + "px", value);

				assertedMaxWidth = true;
			}
			else if (key.equals("min-width")) {
				String value = jsonObject.getString("value");

				Assert.assertEquals(expectedMinWidth + "px", value);

				assertedMinWidth = true;
			}
			else {
				Assert.fail("Unexpected attribute found '" + key + "'");
			}
		}

		if ((expectedMaxWidth != 0) && !assertedMaxWidth) {
			Assert.fail(
				"Couldn't find expected max-width of '" + expectedMaxWidth +
					"' in '" + sourceJSONObject.toString() + "'");
		}

		if ((expectedMinWidth != 0) && !assertedMinWidth) {
			Assert.fail(
				"Couldn't find expected min-width of '" + expectedMinWidth +
					"' in '" + sourceJSONObject.toString() + "'");
		}
	}

	private void _assertSrcSource(
		JSONObject sourceJSONObject, long fileEntryId,
		String configurationEntryUuid) {

		String srcSource = sourceJSONObject.getString("src");

		Assert.assertTrue(
			srcSource.startsWith(
				"/o/adaptive-media/image/" + fileEntryId + "/" +
					configurationEntryUuid + "/"));
	}

	private byte[] _getImageBytes() throws Exception {
		return FileUtil.getBytes(
			FileEntryImageAdaptiveMediaURLItemSelectorReturnTypeResolverTest.class,
			"/com/liferay/adaptive/media/image/processor/test/dependencies/" +
				"image.jpg");
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

	private ConfigurationAdmin _configurationAdmin;
	private DLAppLocalService _dlAppLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private FileEntryImageAdaptiveMediaURLItemSelectorReturnTypeResolver
		_resolver;

}