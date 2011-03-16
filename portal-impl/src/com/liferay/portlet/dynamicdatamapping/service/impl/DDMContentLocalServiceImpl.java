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

package com.liferay.portlet.dynamicdatamapping.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.ContentDuplicateContentKeyException;
import com.liferay.portlet.dynamicdatamapping.ContentException;
import com.liferay.portlet.dynamicdatamapping.ContentKeyException;
import com.liferay.portlet.dynamicdatamapping.ContentNameException;
import com.liferay.portlet.dynamicdatamapping.ContentXmlException;
import com.liferay.portlet.dynamicdatamapping.model.DDMContent;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMContentLocalServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class DDMContentLocalServiceImpl extends DDMContentLocalServiceBaseImpl {

	public DDMContent addContent(
			long userId, long groupId, String contentKey,
			boolean autoContentKey, String name, String description,
			String xml, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Content

		User user = userPersistence.findByPrimaryKey(
			serviceContext.getUserId());

		contentKey = contentKey.trim().toUpperCase();

		if (autoContentKey) {
			contentKey = String.valueOf(counterLocalService.increment());
		}

		try {
			xml = DDMXMLUtil.formatXML(xml);
		}
		catch (Exception e) {
			throw new ContentXmlException();
		}

		Date now = new Date();

		validate(groupId, name, xml, contentKey, autoContentKey);

		long contentId = counterLocalService.increment();

		DDMContent content = ddmContentPersistence.create(contentId);

		content.setUuid(serviceContext.getUuid());
		content.setGroupId(serviceContext.getScopeGroupId());
		content.setCompanyId(user.getCompanyId());
		content.setUserId(user.getUserId());
		content.setUserName(user.getFullName());
		content.setCreateDate(serviceContext.getCreateDate(now));
		content.setModifiedDate(serviceContext.getModifiedDate(now));
		content.setContentKey(contentKey);
		content.setName(name);
		content.setDescription(description);
		content.setXml(xml);

		ddmContentPersistence.update(content, false);

		return content;
	}

	public void deleteContent(DDMContent content)
		throws PortalException, SystemException {

		// Structure

		ddmContentPersistence.remove(content);
	}

	public void deleteContent(long groupId, String contentKey)
		throws PortalException, SystemException {

		DDMContent content =
			ddmContentPersistence.findByG_C(groupId, contentKey);

		deleteContent(content);
	}

	public void deleteContents(long groupId)
		throws PortalException, SystemException {

		for (DDMContent content :
			ddmContentPersistence.findByGroupId(groupId)) {

			deleteContent(content);
		}
	}

	public DDMContent getContent(long contentId)
		throws PortalException, SystemException {

		return ddmContentPersistence.findByPrimaryKey(contentId);
	}

	public DDMContent getContent(long groupId, String contentKey)
		throws PortalException, SystemException {

		return ddmContentPersistence.findByG_C(groupId, contentKey);
	}

	public List<DDMContent> getContents() throws SystemException {
		return ddmContentPersistence.findAll();
	}

	public List<DDMContent> getContents(long groupId) throws SystemException {
		return ddmContentPersistence.findByGroupId(groupId);
	}

	public List<DDMContent> getContents(long groupId, int start, int end)
		throws SystemException {

		return ddmContentPersistence.findByGroupId(groupId, start, end);
	}

	public int getContentsCount(long groupId) throws SystemException {
		return ddmContentPersistence.countByGroupId(groupId);
	}

	public DDMContent updateContent(
			long groupId, String contentKey, String name,
			String description, String xml, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			xml = DDMXMLUtil.formatXML(xml);
		}
		catch (Exception e) {
			throw new ContentXmlException();
		}

		validate(groupId, name, xml);

		DDMContent content =
			ddmContentPersistence.findByG_C(groupId, contentKey);

		content.setModifiedDate(serviceContext.getModifiedDate(null));
		content.setName(name);
		content.setDescription(description);
		content.setXml(xml);

		ddmContentPersistence.update(content, false);

		return content;
	}

	protected void validate(long groupId, String name, String xml)
		throws PortalException, SystemException {

		if (Validator.isNull(name)) {
			throw new ContentNameException();
		}

		validateXml(xml);
	}

	protected void validate(
			long groupId, String name, String xml, String contentKey,
			boolean autoContentKey)
		throws PortalException, SystemException {

		if (!autoContentKey) {
			validate(contentKey);

			DDMContent content = ddmContentPersistence.fetchByG_C(
				groupId, contentKey);

			if (content != null) {
				throw new ContentDuplicateContentKeyException();
			}
		}

		validate(groupId, name, xml);
	}

	protected void validate(String contentKey) throws PortalException {
		if ((Validator.isNull(contentKey)) ||
			(contentKey.indexOf(CharPool.SPACE) != -1)) {

			throw new ContentKeyException();
		}
	}

	protected void validateXml(String xml) throws PortalException {
		if (Validator.isNull(xml)) {
			throw new ContentException();
		}

		try {
			SAXReaderUtil.read(xml);
		}
		catch (DocumentException de) {
			throw new ContentException();
		}
	}

}