/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.organizations.organizationpage.addorganizationpublicpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewOrganizationPublicPageTest extends BaseTestCase {
	public void testViewOrganizationPublicPage() throws Exception {
		selenium.open("/web/organization-name/");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Organization Name"),
			selenium.getText("//h1[@class='site-title']/span"));
		assertEquals(RuntimeVariables.replace("Public Page"),
			selenium.getText(
				"//nav[@id='navigation']/ul/li/a/span[contains(.,'Public Page')]"));
		assertEquals(RuntimeVariables.replace("Liferay"),
			selenium.getText(
				"//ul[@class='breadcrumbs breadcrumbs-horizontal lfr-component']/li/span/a[contains(.,'Liferay')]"));
		assertEquals(RuntimeVariables.replace("Organization Name"),
			selenium.getText(
				"//ul[@class='breadcrumbs breadcrumbs-horizontal lfr-component']/li/span/a[contains(.,'Organization Name')]"));
		assertEquals(RuntimeVariables.replace("Public Page"),
			selenium.getText(
				"//ul[@class='breadcrumbs breadcrumbs-horizontal lfr-component']/li/span/a[contains(.,'Public Page')]"));
	}
}