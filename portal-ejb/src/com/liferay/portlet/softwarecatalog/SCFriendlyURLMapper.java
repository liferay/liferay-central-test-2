/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.softwarecatalog;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.BaseFriendlyURLMapper;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;

import java.util.Map;

import javax.portlet.PortletURL;

/**
 * <a href="SCFriendlyURLMapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class SCFriendlyURLMapper extends BaseFriendlyURLMapper {

	public String getMapping() {
		return _MAPPING;
	}

	public String getPortletId() {
		return _PORTLET_ID;
	}

	public String buildPath(PortletURL portletURL) {
		if (!(portletURL instanceof PortletURLImpl)) {
			return null;
		}

		PortletURLImpl url = (PortletURLImpl)portletURL;

		String friendlyURL = null;

		String tabs1 = url.getParameter("tabs1");

		String action = GetterUtil.getString(
			url.getParameter("struts_action"));

		if (action.equals("/software_catalog/view")) {
			friendlyURL = "/software_catalog/" + tabs1;

		}
		else if (action.equals("/software_catalog/view_product_entry")) {
			String productEntryId = url.getParameter("productEntryId");

			friendlyURL = "/software_catalog/products/" + productEntryId;

			url.addParameterIncludedInPath("productEntryId");
		}
		else if (action.equals("/software_catalog/edit_product_entry")) {
			String productEntryId = url.getParameter("productEntryId");

			if (Validator.isNotNull(productEntryId)) {
				friendlyURL = "/software_catalog/products/" +
					productEntryId + "/edit";

				url.addParameterIncludedInPath("productEntryId");
			}
			else {
				friendlyURL = "/software_catalog/products/new";
			}
		}
		else if (action.equals("/software_catalog/edit_product_version")){
			String productEntryId = url.getParameter("productEntryId");
			String productVersionId = url.getParameter("productVersionId");

			if (Validator.isNotNull(productVersionId)) {
				friendlyURL = "/software_catalog/products/" +
					productEntryId + "/versions/" + productVersionId + "/edit";

				url.addParameterIncludedInPath("productEntryId");
				url.addParameterIncludedInPath("productVersionId");
			}
			else {
				friendlyURL = "/software_catalog/products/" +
					productEntryId + "/versions/new";
			}
		}
		else if (action.equals(
					"/software_catalog/edit_framework_version")) {

			String frameworkVersionId = url.getParameter(
				"frameworkVersionId");

			if (Validator.isNotNull(frameworkVersionId)) {
				friendlyURL = "/software_catalog/framework-versions/" +
					frameworkVersionId + "/edit";

				url.addParameterIncludedInPath("frameworkVersionId");
			}
			else {
				friendlyURL = "/software_catalog/framework-versions/new";
			}
		}
		else if (action.equals(
					"/software_catalog/edit_license")) {

			String licenseId = url.getParameter("licenseId");

			if (Validator.isNotNull(licenseId)) {
				friendlyURL = "/software_catalog/licenses/" +
					licenseId + "/edit";

				url.addParameterIncludedInPath("licenseId");
			}
			else {
				friendlyURL = "/software_catalog/licenses/new";
			}
		}
		else if (action.equals(
					"/software_catalog/search")) {

			friendlyURL = "/software_catalog/search";
		}

		if (Validator.isNotNull(friendlyURL)) {
			url.addParameterIncludedInPath("p_p_id");
			url.addParameterIncludedInPath("struts_action");
			url.addParameterIncludedInPath("tabs1");
		}

		return friendlyURL;
	}

	public void populateParams(String friendlyURLPath, Map params) {
		params.put("p_p_id", _PORTLET_ID);

		int x = friendlyURLPath.indexOf(StringPool.SLASH, 1);

		String[] urlFragments = StringUtil.split(
			friendlyURLPath.substring(x + 1), StringPool.SLASH);

		String resourceIdParam = getResourceIdParam(urlFragments[0]);

		if (urlFragments.length == 1) {
			addParam(
				params, "struts_action", "/software_catalog/view");
			addParam(params, "tabs1", urlFragments[0]);
		}
		else if (urlFragments.length == 2) {
			if (urlFragments[1].equals("new")) {
				addParam(
					params, "struts_action", getEditAction(urlFragments[0]));
				addParam(params, "tabs1", urlFragments[0]);
			}
			else if (urlFragments[0].equals("products")) {
				addParam(
					params,
					"struts_action", "/software_catalog/view_product_entry");
				addParam(params, "tabs1", urlFragments[0]);
				addParam(params, resourceIdParam, urlFragments[1]);
			}
		}
		else if (urlFragments.length == 3) {
			if (urlFragments[2].equals("edit")) {
				addParam(
					params, "struts_action", getEditAction(urlFragments[0]));
				addParam(params, "tabs1", urlFragments[0]);
				addParam(params, resourceIdParam, urlFragments[1]);
			}
		}
		else if (urlFragments.length == 4) {
			if (urlFragments[3].equals("new")) {
				addParam(
					params, "struts_action", getEditAction(urlFragments[2]));
				addParam(params, "tabs1", urlFragments[0]);
				addParam(params, resourceIdParam, urlFragments[1]);
			}
		}
		else if (urlFragments.length == 5) {
			if (urlFragments[0].equals("products") &&
				urlFragments[4].equals("edit")) {

				addParam(
					params, "struts_action", getEditAction(urlFragments[2]));
				addParam(params, "tabs1", urlFragments[0]);
				addParam(params, resourceIdParam, urlFragments[1]);
				addParam(
					params, getResourceIdParam(urlFragments[2]),
					urlFragments[3]);
			}
		}
	}

	protected String getEditAction(String resource) {
		String action = null;

		if (resource.equals("my-products") || resource.equals("products")) {
			action = "edit_product_entry";
		}
		else if (resource.equals("framework-versions")) {
			action = "edit_framework_version";
		}
		else if (resource.equals("licenses")) {
			action = "edit_license";
		}
		else if (resource.equals("versions")) {
			action = "edit_product_version";
		}
		else {
			return null;
		}

		return "/software_catalog/" + action;
	}

	protected String getResourceIdParam(String resource) {
		if (resource.equals("my-products") || resource.equals("products")) {
			return "productEntryId";
		}
		else if (resource.equals("versions")) {
			return "productVersionId";
		}
		else if (resource.equals("framework-versions")) {
			return "frameworkVersionId";
		}
		else if (resource.equals("licenses")) {
			return "licenseId";
		}
		else if (resource.equals("discussion")) {
			return "messageId";
		}
		else {
			return null;
		}
	}

	private static final String _MAPPING = "software_catalog";

	private static final String _PORTLET_ID = PortletKeys.SOFTWARE_CATALOG;

}