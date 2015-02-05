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

package com.liferay.arquillian.bridge.deployment;

import com.liferay.arquillian.bridge.extension.LiferayArquillianExtension;
import com.liferay.arquillian.bridge.junit.observer.JUnitBridgeObserver;

import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

/**
 * @author Shuyang Zhou
 */
public class JUnitBridgeAuxiliaryArchiveAppender
	implements AuxiliaryArchiveAppender {

	@Override
	public Archive<?> createAuxiliaryArchive() {
		JavaArchive javaArchive = ShrinkWrap.create(
			JavaArchive.class, "arquillian-junit-bridge.jar");

		javaArchive.add(EmptyAsset.INSTANCE, "/arquillian.remote.marker");
		javaArchive.addAsServiceProviderAndClasses(
			RemoteLoadableExtension.class, LiferayArquillianExtension.class);
		javaArchive.addClass(JUnitBridgeObserver.class);

		return javaArchive;
	}

}