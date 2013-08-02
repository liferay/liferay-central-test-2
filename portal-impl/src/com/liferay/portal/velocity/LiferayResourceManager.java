/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.deploy.sandbox.SandboxHandler;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.util.PropsUtil;

import java.io.IOException;

import java.lang.reflect.Field;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.runtime.RuntimeInstance;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.ResourceManager;
import org.apache.velocity.runtime.resource.ResourceManagerImpl;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class LiferayResourceManager extends ResourceManagerImpl {

	@Override
	public String getLoaderNameForResource(String source) {

		// Velocity's default implementation makes its cache useless because
		// getResourceStream is called to test the availability of a template

		if ((globalCache.get(ResourceManager.RESOURCE_CONTENT + source) !=
				null) ||
			(globalCache.get(ResourceManager.RESOURCE_TEMPLATE + source) !=
				null)) {

			return LiferayResourceLoader.class.getName();
		}
		else {
			return super.getLoaderNameForResource(source);
		}
	}

	@Override
	public Resource getResource(
			String resourceName, int resourceType, String encoding)
		throws Exception {

		String[] macroTemplateIds = PropsUtil.getArray(
			PropsKeys.VELOCITY_ENGINE_VELOCIMACRO_LIBRARY);

		for (String macroTemplateId : macroTemplateIds) {
			if (resourceName.equals(macroTemplateId)) {

				// This resource is provided by the portal, so invoke it from an
				// access controller

				try {
					return AccessController.doPrivileged(
						new ResourcePrivilegedExceptionAction(
							resourceName, resourceType, encoding));
				}
				catch (PrivilegedActionException pae) {
					throw (IOException)pae.getException();
				}
			}
		}

		return doGetResource(resourceName, resourceType, encoding);
	}

	@Override
	public synchronized void initialize(RuntimeServices runtimeServices)
		throws Exception {

		ExtendedProperties extendedProperties =
			runtimeServices.getConfiguration();

		Field field = ReflectionUtil.getDeclaredField(
			RuntimeInstance.class, "configuration");

		field.set(
			runtimeServices, new FastExtendedProperties(extendedProperties));

		super.initialize(runtimeServices);
	}

	private Resource doGetResource(
			String resourceName, int resourceType, String encoding)
		throws Exception {

		if (resourceName.contains(SandboxHandler.SANDBOX_MARKER)) {
			return loadResource(resourceName, resourceType, encoding);
		}
		else {
			return super.getResource(resourceName, resourceType, encoding);
		}
	}

	private class ResourcePrivilegedExceptionAction
		implements PrivilegedExceptionAction<Resource> {

		public ResourcePrivilegedExceptionAction(
			String resourceName, int resourceType, String encoding) {

			_resourceName = resourceName;
			_resourceType = resourceType;
			_encoding = encoding;
		}

		@Override
		public Resource run() throws Exception {
			return doGetResource(_resourceName, _resourceType, _encoding);
		}

		private String _encoding;
		private String _resourceName;
		private int _resourceType;

	}

}