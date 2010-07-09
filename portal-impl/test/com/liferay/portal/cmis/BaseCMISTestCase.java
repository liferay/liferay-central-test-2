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

package com.liferay.portal.cmis;

import com.liferay.portal.cmis.model.CMISConstants;
import com.liferay.portal.cmis.model.CMISRepositoryInfo;

import junit.framework.TestCase;

import org.apache.abdera.model.Service;
import org.apache.abdera.model.Workspace;

/**
 * @author Alexander Chow
 */
public abstract class BaseCMISTestCase extends TestCase {

	public void testService() throws Exception {
		Service service = getService();

		assertNotNull(service);

		for (Workspace workspace : service.getWorkspaces()) {
			assertNotNull(workspace);

			CMISRepositoryInfo cmisRepositoryInfo = workspace.getFirstChild(
				getConstants().REPOSITORY_INFO);

			assertNotNull(cmisRepositoryInfo);
			assertNotNull(cmisRepositoryInfo.getId());
			assertEquals(
				getConstants().VERSION,
				cmisRepositoryInfo.getVersionSupported());

			String rootChildrenUrl = CMISUtil.getCollectionUrl(
				workspace, getConstants().COLLECTION_ROOT);

			assertNotNull(rootChildrenUrl);
		}
	}

	protected abstract CMISConstants getConstants();

	protected abstract Service getService() throws Exception;

}