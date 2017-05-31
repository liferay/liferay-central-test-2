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

package com.liferay.journal.demo.data.creator.internal;

import com.liferay.journal.demo.data.creator.JournalArticleDemoDataCreator;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.IOException;

import java.net.URL;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = JournalArticleDemoDataCreator.class)
public class JournalArticleDemoDataCreatorImpl
	implements JournalArticleDemoDataCreator {

	@Activate
	public void activate(BundleContext bundleContext) {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		Enumeration<URL> paths = bundle.findEntries(
			"/com/liferay/journal/demo/data/creator/internal/dependencies",
			"article*", true);

		List<URL> pathList = Collections.list(paths);

		_availablePaths.addAll(pathList);

		Collections.shuffle(_availablePaths);
	}

	@Override
	public JournalArticle create(long userId, long groupId)
		throws IOException, PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		URL path = _getNextPath();

		Map<Locale, String> titleMap = _getTitleMap(defaultLocale, path);

		Map<Locale, String> descriptionMap = _getDescriptionMap(
			defaultLocale, path);

		String content = _getContent(defaultLocale, path);

		JournalArticle journalArticle = journalArticleLocalService.addArticle(
			userId, groupId, 0, titleMap, descriptionMap, content,
			"BASIC-WEB-CONTENT", "BASIC-WEB-CONTENT", serviceContext);

		entryIds.add(journalArticle.getId());

		return journalArticle;
	}

	@Override
	public void delete() throws PortalException {
		for (long entryId : entryIds) {
			journalArticleLocalService.deleteJournalArticle(entryId);
			entryIds.remove(entryId);
		}
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		this.journalArticleLocalService = journalArticleLocalService;
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.journal.service)(release.schema.version=1.0.0))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	protected final List<Long> entryIds = new CopyOnWriteArrayList<>();
	protected JournalArticleLocalService journalArticleLocalService;

	private Document _createDocumentContent(String locale) {
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute("available-locales", locale);
		rootElement.addAttribute("default-locale", locale);

		return document;
	}

	private String _getContent(Locale locale, URL path) throws IOException {
		Class<?> clazz = getClass();

		String contentPath = path.getPath() + "content.txt";

		String content = StringUtil.read(
			clazz.getClassLoader(), contentPath, false);

		return _getStructuredContent(content, locale);
	}

	private Map<Locale, String> _getDescriptionMap(Locale locale, URL path)
		throws IOException {

		Class<?> clazz = getClass();

		Map<Locale, String> descriptionMap = new HashMap<>();

		String descriptionPath = path.getPath() + "description.txt";

		String description = StringUtil.read(
			clazz.getClassLoader(), descriptionPath, false);

		descriptionMap.put(locale, description);

		return descriptionMap;
	}

	private URL _getNextPath() {
		int index = _atomicInteger.getAndIncrement();

		if (index == (_availablePaths.size() - 1)) {
			_atomicInteger.set(0);
		}

		return _availablePaths.get(index);
	}

	private String _getStructuredContent(String contents, Locale locale) {
		Document document = _createDocumentContent(locale.toString());

		Element rootElement = document.getRootElement();

		Element dynamicElementElement = rootElement.addElement(
			"dynamic-element");

		dynamicElementElement.addAttribute("index-type", "text");
		dynamicElementElement.addAttribute("name", "content");
		dynamicElementElement.addAttribute("type", "text_area");

		Element element = dynamicElementElement.addElement("dynamic-content");

		element.addAttribute("language-id", LocaleUtil.toLanguageId(locale));
		element.addCDATA(contents);

		return document.asXML();
	}

	private Map<Locale, String> _getTitleMap(Locale locale, URL path)
		throws IOException {

		Class<?> clazz = getClass();

		Map<Locale, String> titleMap = new HashMap<>();

		String titlePath = path.getPath() + "title.txt";

		String title = StringUtil.read(
			clazz.getClassLoader(), titlePath, false);

		titleMap.put(locale, title);

		return titleMap;
	}

	private final AtomicInteger _atomicInteger = new AtomicInteger(0);
	private final List<URL> _availablePaths = new CopyOnWriteArrayList<>();

}