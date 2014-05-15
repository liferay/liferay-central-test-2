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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.test.Sync;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.journal.lar.JournalArticleStagedModelDataHandlerTest;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

/**
 * @author Zsolt Berentey
 */
@Sync
public class ManifestSummaryTest
	extends JournalArticleStagedModelDataHandlerTest {

	@Override
	protected void addComments(StagedModel stagedModel) throws Exception {
		return;
	}

	@Override
	protected void addRatings(StagedModel stagedModel) throws Exception {
		return;
	}

	@Override
	protected AssetEntry fetchAssetEntry(StagedModel stagedModel, Group group)
		throws Exception {

		return null;
	}

	@Override
	protected void validateExport(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		Map<String, LongWrapper> modelAdditionCounters =
			manifestSummary.getModelAdditionCounters();

		Assert.assertEquals(4, modelAdditionCounters.size());
		Assert.assertEquals(
			1,
			manifestSummary.getModelAdditionCount(
				DDMStructure.class, JournalArticle.class));
		Assert.assertEquals(
			1,
			manifestSummary.getModelAdditionCount(
				DDMTemplate.class, DDMStructure.class));
		Assert.assertEquals(
			1, manifestSummary.getModelAdditionCount(JournalArticle.class));
		Assert.assertEquals(
			1, manifestSummary.getModelAdditionCount(JournalFolder.class));

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		Element headerElement = rootElement.addElement("header");

		_exportDate = new Date();

		headerElement.addAttribute("export-date", Time.getRFC822(_exportDate));

		ExportImportHelperUtil.writeManifestSummary(document, manifestSummary);

		zipWriter.addEntry("/manifest.xml", document.asXML());
	}

	@Override
	protected void validateImport(
			StagedModel stagedModel, StagedModelAssets stagedModelAssets,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		ManifestSummary manifestSummary =
			ExportImportHelperUtil.getManifestSummary(portletDataContext);

		Map<String, LongWrapper> modelAdditionCounters =
			manifestSummary.getModelAdditionCounters();

		Assert.assertEquals(4, modelAdditionCounters.size());
		Assert.assertEquals(
			1,
			manifestSummary.getModelAdditionCount(
				DDMStructure.class, JournalArticle.class));
		Assert.assertEquals(
			1,
			manifestSummary.getModelAdditionCount(
				DDMTemplate.class, DDMStructure.class));
		Assert.assertEquals(
			1, manifestSummary.getModelAdditionCount(JournalArticle.class));
		Assert.assertEquals(
			1, manifestSummary.getModelAdditionCount(JournalFolder.class));
		Assert.assertTrue(
			DateUtil.equals(
				_exportDate, manifestSummary.getExportDate(), true));
	}

	private Date _exportDate;

}