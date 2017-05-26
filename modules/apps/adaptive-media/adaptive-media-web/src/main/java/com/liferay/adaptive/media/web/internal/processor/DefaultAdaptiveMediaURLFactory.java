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

package com.liferay.adaptive.media.web.internal.processor;

import com.liferay.adaptive.media.processor.AdaptiveMedia;
import com.liferay.adaptive.media.processor.AdaptiveMediaURLFactory;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.net.URI;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = AdaptiveMediaURLFactory.class)
public class DefaultAdaptiveMediaURLFactory implements AdaptiveMediaURLFactory {

	@Override
	public <T> URI createAdaptiveMediaURI(AdaptiveMedia<T> media) {
		String pathModule = PortalUtil.getPathModule();

		if (!pathModule.endsWith(StringPool.SLASH)) {
			pathModule += StringPool.SLASH;
		}

		URI moduleURI = URI.create(pathModule);
		URI relativeURI = media.getRelativeURI();

		return moduleURI.resolve(relativeURI);
	}

}