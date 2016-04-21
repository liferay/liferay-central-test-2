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

package com.liferay.knowledge.base.web.selector;

import com.liferay.knowledge.base.exception.NoSuchKBArticleSelectorException;
import com.liferay.knowledge.base.service.util.PortletPropsKeys;
import com.liferay.knowledge.base.service.util.ServiceProps;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Adolfo Pérez
 */
public class DefaultKBArticleSelectorFactory
	implements KBArticleSelectorFactory {

	@Override
	public KBArticleSelector getKBArticleSelector(long classNameId)
		throws PortalException {

		ClassName className = ClassNameLocalServiceUtil.getClassName(
			classNameId);

		return getKBArticleSelector(className.getClassName());
	}

	protected KBArticleSelector createKBArticleSelector(String className)
		throws NoSuchKBArticleSelectorException {

		try {
			String kbArticleSelectorClassName = ServiceProps.get(
				PortletPropsKeys.KNOWLEDGE_BASE_DISPLAY_SELECTOR,
				new Filter(className));

			if (Validator.isNull(kbArticleSelectorClassName)) {
				throw new NoSuchKBArticleSelectorException(
					"No KBArticleSelector found for key " + className);
			}

			Class<?> kbArticleSelectorClass = Class.forName(
				kbArticleSelectorClassName);

			return (KBArticleSelector)kbArticleSelectorClass.newInstance();
		}
		catch (ClassNotFoundException cnfe) {
			throw new NoSuchKBArticleSelectorException(cnfe);
		}
		catch (InstantiationException ie) {
			throw new NoSuchKBArticleSelectorException(ie);
		}
		catch (IllegalAccessException iae) {
			throw new NoSuchKBArticleSelectorException(iae);
		}
	}

	protected KBArticleSelector getKBArticleSelector(String className)
		throws PortalException {

		KBArticleSelector kbArticleSelector = _kbArticleSelectorMap.get(
			className);

		if (kbArticleSelector == null) {
			kbArticleSelector = createKBArticleSelector(className);

			_kbArticleSelectorMap.put(className, kbArticleSelector);
		}

		return kbArticleSelector;
	}

	private final Map<String, KBArticleSelector> _kbArticleSelectorMap =
		new ConcurrentHashMap<>();

}