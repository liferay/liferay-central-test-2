/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.portal.template;

import com.liferay.portal.deploy.sandbox.SandboxHandler;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoader;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;
import java.io.Writer;
import java.util.Map;

/**
 * @author Miroslav Ligas
 */
public abstract class AbstractSingleResourceTemplate extends AbstractTemplate {

	public AbstractSingleResourceTemplate(TemplateResource templateResource,
		TemplateResource errorTemplateResource, Map<String, Object> context,
		TemplateContextHelper templateContextHelper, String templateManagerName,
		long interval) {

		super(errorTemplateResource, context, templateContextHelper,
			templateManagerName);

		if (templateResource == null) {
			throw new IllegalArgumentException("Template resource is null");
		}

		this.templateResource = templateResource;

		if (interval != 0) {
			_cacheTemplateResource(templateManagerName);
		}
	}

	@Override
	public void doProcessTemplate(Writer writer) throws Exception {
		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		put(TemplateConstants.WRITER, unsyncStringWriter);

		processTemplate(templateResource, unsyncStringWriter);

		StringBundler sb = unsyncStringWriter.getStringBundler();

		sb.writeTo(writer);
	}

	@Override
	public void processTemplate(Writer writer) throws TemplateException {
		if (errorTemplateResource == null) {
			try {
				processTemplate(templateResource, writer);

				return;
			} catch (Exception e) {
				throw new TemplateException(
					"Unable to process template " +
						templateResource.getTemplateId(),
					e);
			}
		}

		Writer oldWriter = (Writer) get(TemplateConstants.WRITER);

		try {
			doProcessTemplate(writer);
		} catch (Exception e) {
			put(TemplateConstants.WRITER, writer);

			handleException(e, writer);
		} finally {
			put(TemplateConstants.WRITER, oldWriter);
		}
	}

	protected TemplateResource templateResource;


	private void _cacheTemplateResource(String templateManagerName) {
		String templateId = templateResource.getTemplateId();

		if (templateManagerName.equals(TemplateConstants.LANG_TYPE_VM) &&
			templateId.contains(SandboxHandler.SANDBOX_MARKER)) {

			return;
		}

		if (!(templateResource instanceof CacheTemplateResource) &&
			!(templateResource instanceof StringTemplateResource)) {

			templateResource = new CacheTemplateResource(templateResource);
		}

		String portalCacheName = TemplateResourceLoader.class.getName();

		portalCacheName = portalCacheName.concat(StringPool.PERIOD).concat(
			templateManagerName);

		PortalCache<String, Serializable> portalCache = _getPortalCache(
			templateResource, portalCacheName);

		Object object = portalCache.get(templateResource.getTemplateId());

		if ((object == null) || !templateResource.equals(object)) {
			portalCache.put(templateResource.getTemplateId(), templateResource);
		}

		if (errorTemplateResource == null) {
			return;
		}

		String errorTemplateId = errorTemplateResource.getTemplateId();

		if (templateManagerName.equals(TemplateConstants.LANG_TYPE_VM) &&
			errorTemplateId.contains(SandboxHandler.SANDBOX_MARKER)) {

			return;
		}

		if (!(errorTemplateResource instanceof CacheTemplateResource) &&
			!(errorTemplateResource instanceof StringTemplateResource)) {

			errorTemplateResource = new CacheTemplateResource(
				errorTemplateResource);
		}

		portalCache = _getPortalCache(errorTemplateResource, portalCacheName);

		object = portalCache.get(errorTemplateResource.getTemplateId());

		if ((object == null) || !errorTemplateResource.equals(object)) {
			portalCache.put(
				errorTemplateResource.getTemplateId(), errorTemplateResource);
		}
	}

	private PortalCache<String, Serializable> _getPortalCache(
		TemplateResource templateResource, String portalCacheName) {

		if (!(templateResource instanceof CacheTemplateResource)) {
			return MultiVMPoolUtil.getPortalCache(portalCacheName);
		}

		CacheTemplateResource cacheTemplateResource =
			(CacheTemplateResource) templateResource;

		TemplateResource innerTemplateResource =
			cacheTemplateResource.getInnerTemplateResource();

		if (innerTemplateResource instanceof URLTemplateResource) {
			return SingleVMPoolUtil.getPortalCache(portalCacheName);
		}

		return MultiVMPoolUtil.getPortalCache(portalCacheName);
	}


}
