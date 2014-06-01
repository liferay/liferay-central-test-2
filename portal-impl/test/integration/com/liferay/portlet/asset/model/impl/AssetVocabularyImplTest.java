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

package com.liferay.portlet.asset.model.impl;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.util.test.AssetTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author José Manuel Navarro
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class AssetVocabularyImplTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testhasMoreThanOneCategorySelected() throws Exception {
		AssetVocabulary vocabulary1 =
			_addVocabularyAssociatedToAssetRendererFactory(1, true);

		AssetCategory category11 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary1.getVocabularyId());
		AssetCategory category12 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary1.getVocabularyId());

		Assert.assertTrue(
			vocabulary1.hasMoreThanOneCategorySelected(
				new long[] {
					category11.getCategoryId(), category12.getCategoryId()
				}));
		Assert.assertFalse(
			vocabulary1.hasMoreThanOneCategorySelected(new long[0]));
		Assert.assertFalse(
			vocabulary1.hasMoreThanOneCategorySelected(
				new long[] {category11.getCategoryId()}));

		AssetVocabulary vocabulary2 =
			_addVocabularyAssociatedToAssetRendererFactory(2, true);

		AssetCategory category21 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary2.getVocabularyId());
		AssetCategory category22 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary2.getVocabularyId());

		Assert.assertFalse(
			vocabulary1.hasMoreThanOneCategorySelected(
				new long[] {
					category21.getCategoryId(), category22.getCategoryId()
				}));
	}

	@Test
	public void testIsAssociatedToAssetRendererFactoryAll() throws Exception {
		AssetVocabulary vocabulary =
			_addVocabularyAssociatedToAssetRendererFactory(
				AssetCategoryConstants.ALL_CLASS_NAME_IDS, true);

		Assert.assertTrue(vocabulary.isAssociatedToAssetRendererFactory(1));
	}

	@Test
	public void testIsAssociatedToAssetRendererFactoryClassName()
		throws Exception {

		AssetVocabulary vocabulary =
			_addVocabularyAssociatedToAssetRendererFactory(1, true);

		Assert.assertTrue(vocabulary.isAssociatedToAssetRendererFactory(1));
		Assert.assertFalse(vocabulary.isAssociatedToAssetRendererFactory(2));
	}

	@Test
	public void testIsMissingRequiredCategoryMatches() throws Exception {
		AssetVocabulary vocabulary =
			_addVocabularyAssociatedToAssetRendererFactory(1, true);

		AssetCategory category = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary.getVocabularyId());

		Assert.assertFalse(
			vocabulary.isMissingRequiredCategory(
				1, new long[] {category.getCategoryId()}));
	}

	@Test
	public void testIsMissingRequiredCategoryNoExistingCategories()
		throws Exception {

		AssetVocabulary vocabulary =
			_addVocabularyAssociatedToAssetRendererFactory(1, true);

		Assert.assertTrue(
			vocabulary.isMissingRequiredCategory(1, new long[] {1}));
	}

	@Test
	public void testIsMissingRequiredCategoryNoMatchesAndNoRequired()
		throws Exception {

		AssetVocabulary vocabulary =
			_addVocabularyAssociatedToAssetRendererFactory(1, false);

		AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary.getVocabularyId());

		Assert.assertFalse(
			vocabulary.isMissingRequiredCategory(1, new long[] {1}));
	}

	@Test
	public void testIsMissingRequiredCategoryNoMatchesAndRequired()
		throws Exception {

		AssetVocabulary vocabulary =
			_addVocabularyAssociatedToAssetRendererFactory(1, true);

		AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary.getVocabularyId());

		Assert.assertTrue(
			vocabulary.isMissingRequiredCategory(1, new long[] {1}));
	}

	@Test
	public void testIsMissingRequiredCategoryNoRelated() throws Exception {
		AssetVocabulary vocabulary =
			_addVocabularyAssociatedToAssetRendererFactory(1, true);

		Assert.assertFalse(
			vocabulary.isMissingRequiredCategory(2, new long[0]));
	}

	@Test
	public void testIsRequiredAllNotRequired() throws Exception {
		AssetVocabulary vocabulary =
			_addVocabularyAssociatedToAssetRendererFactory(
				AssetCategoryConstants.ALL_CLASS_NAME_IDS, false);

		Assert.assertFalse(vocabulary.isRequired(1));
		Assert.assertFalse(vocabulary.isRequired(2));
	}

	@Test
	public void testIsRequiredAllRequired() throws Exception {
		AssetVocabulary vocabulary =
			_addVocabularyAssociatedToAssetRendererFactory(
				AssetCategoryConstants.ALL_CLASS_NAME_IDS, true);

		Assert.assertTrue(vocabulary.isRequired(1));
		Assert.assertTrue(vocabulary.isRequired(2));
	}

	@Test
	public void testIsRequiredClassNameNotRequired() throws Exception {
		AssetVocabulary vocabulary =
			_addVocabularyAssociatedToAssetRendererFactory(1, false);

		Assert.assertFalse(vocabulary.isRequired(1));
		Assert.assertFalse(vocabulary.isRequired(2));
	}

	@Test
	public void testIsRequiredClassNameRequired() throws Exception {
		AssetVocabulary vocabulary =
			_addVocabularyAssociatedToAssetRendererFactory(1, true);

		Assert.assertTrue(vocabulary.isRequired(1));
		Assert.assertFalse(vocabulary.isRequired(2));
	}

	private AssetVocabulary _addVocabularyAssociatedToAssetRendererFactory(
			long classNameId, boolean required)
		throws Exception {

		long[] classNameIds = new long[] {classNameId};
		boolean[] requireds = new boolean[] {required};

		return AssetTestUtil.addVocabularyAssociatedToAssetRendererFactory(
			_group.getGroupId(), true, classNameIds, requireds);
	}

	private Group _group;

}