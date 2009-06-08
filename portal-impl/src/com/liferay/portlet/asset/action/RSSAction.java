/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.action;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.service.AssetServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.util.RSSUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="RSSAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class RSSAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			ServletResponseUtil.sendFile(
				response, null, getRSS(request), ContentTypes.TEXT_XML_UTF8);

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	protected byte[] getRSS(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long companyId = ParamUtil.getLong(request, "companyId");
		long groupId = ParamUtil.getLong(request, "groupId");
		int max = ParamUtil.getInteger(
			request, "max", SearchContainer.DEFAULT_DELTA);
		String type = ParamUtil.getString(
			request, "type", RSSUtil.DEFAULT_TYPE);
		double version = ParamUtil.getDouble(
			request, "version", RSSUtil.DEFAULT_VERSION);
		String displayStyle = ParamUtil.getString(
			request, "displayStyle", RSSUtil.DISPLAY_STYLE_FULL_CONTENT);

		String feedURL = StringPool.BLANK;

		String entryURL =
			themeDisplay.getURLPortal() + themeDisplay.getPathMain() +
				"/asset/find_asset?";

		String rss = StringPool.BLANK;

		if (companyId > 0) {
			rss = AssetServiceUtil.getCompanyAssetsRSS(
				companyId, max, type, version, displayStyle, feedURL, entryURL);
		}
		else if (groupId > 0) {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			companyId = group.getCompanyId();

			long[] classNameIds = new long[0];

			String[] allTagNames = StringUtil.split(
				ParamUtil.getString(request, "tags"));

			long[] tagIds = AssetTagLocalServiceUtil.getTagIds(
				companyId, allTagNames);

			String[] notTagNames = StringUtil.split(
				ParamUtil.getString(request, "noTags"));

			long[] notTagIds = AssetTagLocalServiceUtil.getTagIds(
				companyId, notTagNames);

			boolean andOperator = false;
			String orderByCol1 = null;
			String orderByCol2 = null;
			String orderByType1 = null;
			String orderByType2 = null;
			boolean excludeZeroViewCount = false;
			Date publishDate = null;
			Date expirationDate = null;

			rss = AssetServiceUtil.getAssetsRSS(
				groupId, classNameIds, tagIds, notTagIds, andOperator,
				orderByCol1, orderByCol2, orderByType1, orderByType2,
				excludeZeroViewCount, publishDate, expirationDate, max, type,
				version, displayStyle, feedURL, entryURL);
		}

		return rss.getBytes(StringPool.UTF8);
	}

}