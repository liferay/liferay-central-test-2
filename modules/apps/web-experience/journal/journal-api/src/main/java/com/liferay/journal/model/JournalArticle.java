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

package com.liferay.journal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.TreeModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the JournalArticle service. Represents a row in the &quot;JournalArticle&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleModel
 * @see com.liferay.journal.model.impl.JournalArticleImpl
 * @see com.liferay.journal.model.impl.JournalArticleModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.journal.model.impl.JournalArticleImpl")
@ProviderType
public interface JournalArticle extends JournalArticleModel, PersistedModel,
	TreeModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.journal.model.impl.JournalArticleImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<JournalArticle, Long> ID_ACCESSOR = new Accessor<JournalArticle, Long>() {
			@Override
			public Long get(JournalArticle journalArticle) {
				return journalArticle.getId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<JournalArticle> getTypeClass() {
				return JournalArticle.class;
			}
		};

	public static final Accessor<JournalArticle, String> ARTICLE_ID_ACCESSOR = new Accessor<JournalArticle, String>() {
			@Override
			public String get(JournalArticle journalArticle) {
				return journalArticle.getArticleId();
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<JournalArticle> getTypeClass() {
				return JournalArticle.class;
			}
		};

	public com.liferay.portal.kernel.repository.model.Folder addImagesFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	@Override
	public java.lang.String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getArticleImageURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay);

	public JournalArticleResource getArticleResource()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getArticleResourceUuid()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String[] getAvailableLanguageIds();

	public java.lang.String getContentByLocale(java.lang.String languageId);

	public com.liferay.dynamic.data.mapping.model.DDMStructure getDDMStructure()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.dynamic.data.mapping.model.DDMTemplate getDDMTemplate()
		throws com.liferay.portal.kernel.exception.PortalException;

	@com.liferay.portal.kernel.json.JSON()
	public java.lang.String getDescription();

	public java.lang.String getDescription(java.util.Locale locale);

	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault);

	public java.lang.String getDescription(java.lang.String languageId);

	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault);

	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap();

	public java.lang.String getDescriptionMapAsXML();

	public com.liferay.portal.kernel.xml.Document getDocument();

	public JournalFolder getFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getImagesFileEntries()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getImagesFileEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getImagesFileEntries(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException;

	public int getImagesFileEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getImagesFolderId();

	public com.liferay.portal.kernel.model.Layout getLayout();

	/**
	* @deprecated As of 7.0.0
	*/
	@java.lang.Deprecated()
	public java.lang.String getLegacyDescription();

	/**
	* @deprecated As of 7.0.0
	*/
	@java.lang.Deprecated()
	public java.lang.String getLegacyTitle();

	public java.lang.String getSmallImageType()
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getDDMStructureKey()}
	*/
	@java.lang.Deprecated()
	public java.lang.String getStructureId();

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getDDMTemplateKey()}
	*/
	@java.lang.Deprecated()
	public java.lang.String getTemplateId();

	@com.liferay.portal.kernel.json.JSON()
	public java.lang.String getTitle();

	public java.lang.String getTitle(java.util.Locale locale);

	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault);

	public java.lang.String getTitle(java.lang.String languageId);

	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault);

	@com.liferay.portal.kernel.json.JSON()
	public java.lang.String getTitleCurrentValue();

	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap();

	public java.lang.String getTitleMapAsXML();

	public boolean hasApprovedVersion();

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated()
	public boolean isTemplateDriven();

	/**
	* @deprecated As of 7.0.0
	*/
	@java.lang.Deprecated()
	public void setDescription(java.lang.String description);

	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap);

	public void setDocument(com.liferay.portal.kernel.xml.Document document);

	public void setImagesFolderId(long imagesFolderId);

	public void setSmallImageType(java.lang.String smallImageType);

	/**
	* @deprecated As of 7.0.0, replaced by {@link #setDDMStructureKey(String)}
	*/
	@java.lang.Deprecated()
	public void setStructureId(java.lang.String ddmStructureKey);

	/**
	* @deprecated As of 7.0.0, replaced by {@link #setDDMTemplateKey(String)}
	*/
	@java.lang.Deprecated()
	public void setTemplateId(java.lang.String ddmTemplateKey);

	/**
	* @deprecated As of 7.0.0
	*/
	@java.lang.Deprecated()
	public void setTitle(java.lang.String title);

	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap);
}