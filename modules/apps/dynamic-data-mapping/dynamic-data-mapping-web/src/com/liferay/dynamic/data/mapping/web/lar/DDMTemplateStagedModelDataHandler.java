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

package com.liferay.dynamic.data.mapping.web.lar;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.exportimport.api.ExportImportContentProcessor;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;

import java.io.File;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING},
	service = StagedModelDataHandler.class
)
public class DDMTemplateStagedModelDataHandler
	extends BaseStagedModelDataHandler<DDMTemplate> {

	public static final String[] CLASS_NAMES = {DDMTemplate.class.getName()};

	@Override
	public void deleteStagedModel(DDMTemplate template) throws PortalException {
		DDMTemplateLocalServiceUtil.deleteTemplate(template);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		DDMTemplate ddmTemplate = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (ddmTemplate != null) {
			deleteStagedModel(ddmTemplate);
		}
	}

	@Override
	public DDMTemplate fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return DDMTemplateLocalServiceUtil.fetchDDMTemplateByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<DDMTemplate> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return DDMTemplateLocalServiceUtil.getDDMTemplatesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<DDMTemplate>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(DDMTemplate template) {
		return template.getNameCurrentValue();
	}

	@Override
	public Map<String, String> getReferenceAttributes(
		PortletDataContext portletDataContext, DDMTemplate template) {

		Map<String, String> referenceAttributes = new HashMap<>();

		referenceAttributes.put(
			"referenced-class-name", template.getClassName());
		referenceAttributes.put("template-key", template.getTemplateKey());

		long defaultUserId = 0;

		try {
			defaultUserId = UserLocalServiceUtil.getDefaultUserId(
				template.getCompanyId());
		}
		catch (Exception e) {
			return referenceAttributes;
		}

		boolean preloaded = false;

		if (defaultUserId == template.getUserId()) {
			preloaded = true;
		}

		referenceAttributes.put("preloaded", String.valueOf(preloaded));

		return referenceAttributes;
	}

	@Override
	public void importMissingReference(
			PortletDataContext portletDataContext, Element referenceElement)
		throws PortletDataException {

		importMissingGroupReference(portletDataContext, referenceElement);

		String uuid = referenceElement.attributeValue("uuid");

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));

		groupId = MapUtil.getLong(groupIds, groupId);

		long classNameId = PortalUtil.getClassNameId(
			referenceElement.attributeValue("referenced-class-name"));
		String templateKey = referenceElement.attributeValue("template-key");
		boolean preloaded = GetterUtil.getBoolean(
			referenceElement.attributeValue("preloaded"));

		DDMTemplate existingTemplate = null;

		existingTemplate = fetchExistingTemplate(
			uuid, groupId, classNameId, templateKey, preloaded);

		Map<Long, Long> templateIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMTemplate.class);

		long templateId = GetterUtil.getLong(
			referenceElement.attributeValue("class-pk"));

		templateIds.put(templateId, existingTemplate.getTemplateId());

		Map<String, String> templateKeys =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DDMTemplate.class + ".ddmTemplateKey");

		templateKeys.put(templateKey, existingTemplate.getTemplateKey());
	}

	@Override
	public boolean validateReference(
		PortletDataContext portletDataContext, Element referenceElement) {

		validateMissingGroupReference(portletDataContext, referenceElement);

		String uuid = referenceElement.attributeValue("uuid");

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));

		groupId = MapUtil.getLong(groupIds, groupId);

		long classNameId = PortalUtil.getClassNameId(
			referenceElement.attributeValue("referenced-class-name"));
		String templateKey = referenceElement.attributeValue("template-key");
		boolean preloaded = GetterUtil.getBoolean(
			referenceElement.attributeValue("preloaded"));

		DDMTemplate existingTemplate = fetchExistingTemplate(
			uuid, groupId, classNameId, templateKey, preloaded);

		if (existingTemplate == null) {
			return false;
		}

		return true;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, DDMTemplate template)
		throws Exception {

		Element templateElement = portletDataContext.getExportDataElement(
			template);

		DDMStructure structure = DDMStructureLocalServiceUtil.fetchStructure(
			template.getClassPK());

		if (structure != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, template, structure,
				PortletDataContext.REFERENCE_TYPE_STRONG);
		}

		ExportImportContentProcessor exportImportContentProcessor =
			getExportImportContentProcessor(DDMTemplate.class);

		if (template.isSmallImage()) {
			Image smallImage = ImageLocalServiceUtil.fetchImage(
				template.getSmallImageId());

			if (Validator.isNotNull(template.getSmallImageURL())) {
				String smallImageURL =
					exportImportContentProcessor.replaceExportContentReferences(
						portletDataContext, template,
						template.getSmallImageURL() + StringPool.SPACE, true,
						true);

				template.setSmallImageURL(smallImageURL);
			}
			else if (smallImage != null) {
				String smallImagePath = ExportImportPathUtil.getModelPath(
					template,
					smallImage.getImageId() + StringPool.PERIOD +
						template.getSmallImageType());

				templateElement.addAttribute(
					"small-image-path", smallImagePath);

				template.setSmallImageType(smallImage.getType());

				portletDataContext.addZipEntry(
					smallImagePath, smallImage.getTextObj());
			}
		}

		String script =
			exportImportContentProcessor.replaceExportContentReferences(
				portletDataContext, template, template.getScript(),
				portletDataContext.getBooleanParameter(
					DDMPortletDataHandler.NAMESPACE, "referenced-content"),
				true);

		template.setScript(script);

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
			template.getCompanyId());

		if (defaultUserId == template.getUserId()) {
			templateElement.addAttribute("preloaded", "true");
		}

		portletDataContext.addClassedModel(
			templateElement, ExportImportPathUtil.getModelPath(template),
			template);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, DDMTemplate template)
		throws Exception {

		long userId = portletDataContext.getUserId(template.getUserUuid());

		long classPK = template.getClassPK();

		if (classPK > 0) {
			Map<Long, Long> structureIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					DDMStructure.class);

			classPK = MapUtil.getLong(structureIds, classPK, classPK);
		}

		File smallFile = null;

		try {
			ExportImportContentProcessor exportImportContentProcessor =
				getExportImportContentProcessor(DDMTemplate.class);

			if (template.isSmallImage()) {
				Element element =
					portletDataContext.getImportDataStagedModelElement(
						template);

				String smallImagePath = element.attributeValue(
					"small-image-path");

				if (Validator.isNotNull(template.getSmallImageURL())) {
					String smallImageURL =
						exportImportContentProcessor.
							replaceImportContentReferences(
								portletDataContext, template,
								template.getSmallImageURL());

					template.setSmallImageURL(smallImageURL);
				}
				else if (Validator.isNotNull(smallImagePath)) {
					byte[] bytes = portletDataContext.getZipEntryAsByteArray(
						smallImagePath);

					if (bytes != null) {
						smallFile = FileUtil.createTempFile(
							template.getSmallImageType());

						FileUtil.write(smallFile, bytes);
					}
				}
			}

			String script =
				exportImportContentProcessor.replaceImportContentReferences(
					portletDataContext, template, template.getScript());

			template.setScript(script);

			ServiceContext serviceContext =
				portletDataContext.createServiceContext(template);

			DDMTemplate importedTemplate = null;

			if (portletDataContext.isDataStrategyMirror()) {
				Element element =
					portletDataContext.getImportDataStagedModelElement(
						template);

				boolean preloaded = GetterUtil.getBoolean(
					element.attributeValue("preloaded"));

				DDMTemplate existingTemplate = fetchExistingTemplate(
					template.getUuid(), portletDataContext.getScopeGroupId(),
					template.getClassNameId(), template.getTemplateKey(),
					preloaded);

				if (existingTemplate == null) {
					serviceContext.setUuid(template.getUuid());

					importedTemplate = DDMTemplateLocalServiceUtil.addTemplate(
						userId, portletDataContext.getScopeGroupId(),
						template.getClassNameId(), classPK,
						template.getResourceClassNameId(),
						template.getTemplateKey(), template.getNameMap(),
						template.getDescriptionMap(), template.getType(),
						template.getMode(), template.getLanguage(),
						template.getScript(), template.isCacheable(),
						template.isSmallImage(), template.getSmallImageURL(),
						smallFile, serviceContext);
				}
				else {
					importedTemplate =
						DDMTemplateLocalServiceUtil.updateTemplate(
							userId, existingTemplate.getTemplateId(),
							template.getClassPK(), template.getNameMap(),
							template.getDescriptionMap(), template.getType(),
							template.getMode(), template.getLanguage(),
							template.getScript(), template.isCacheable(),
							template.isSmallImage(),
							template.getSmallImageURL(), smallFile,
							serviceContext);
				}
			}
			else {
				importedTemplate = DDMTemplateLocalServiceUtil.addTemplate(
					userId, portletDataContext.getScopeGroupId(),
					template.getClassNameId(), classPK,
					template.getResourceClassNameId(), null,
					template.getNameMap(), template.getDescriptionMap(),
					template.getType(), template.getMode(),
					template.getLanguage(), template.getScript(),
					template.isCacheable(), template.isSmallImage(),
					template.getSmallImageURL(), smallFile, serviceContext);
			}

			portletDataContext.importClassedModel(template, importedTemplate);

			Map<String, String> ddmTemplateKeys =
				(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
					DDMTemplate.class + ".ddmTemplateKey");

			ddmTemplateKeys.put(
				template.getTemplateKey(), importedTemplate.getTemplateKey());
		}
		finally {
			if (smallFile != null) {
				smallFile.delete();
			}
		}
	}

	protected DDMTemplate fetchExistingTemplate(
		String uuid, long groupId, long classNameId, String templateKey,
		boolean preloaded) {

		DDMTemplate existingTemplate = null;

		if (!preloaded) {
			existingTemplate = fetchStagedModelByUuidAndGroupId(uuid, groupId);
		}
		else {
			existingTemplate = DDMTemplateLocalServiceUtil.fetchTemplate(
				groupId, classNameId, templateKey);
		}

		return existingTemplate;
	}

}