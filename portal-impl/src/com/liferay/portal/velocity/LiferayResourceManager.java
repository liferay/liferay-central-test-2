/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.deploy.sandbox.SandboxDeployListener;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.ResourceManagerImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class LiferayResourceManager extends ResourceManagerImpl {

	@Override
	public Resource getResource(
		String resourceName, int resourceType, String encoding)
		throws ResourceNotFoundException, ParseErrorException, Exception {

		if (resourceName.indexOf(SandboxDeployListener.SANDBOX_MARKER) != -1) {
			return loadResource(resourceName, resourceType, encoding);
		}

		return super.getResource(resourceName, resourceType, encoding);
	}

	public String getLoaderNameForResource(String source) {

		// Velocity's default implementation makes its cache useless because
		// getResourceStream is called to test the availability of a template

		if (globalCache.get(source) != null) {
			return LiferayResourceLoader.class.getName();
		}
		else {
			return super.getLoaderNameForResource(source);
		}
	}

}
