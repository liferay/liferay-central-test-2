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

package com.liferay.portal.workflow;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.service.WorkflowInstanceLinkLocalServiceUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;

import javax.portlet.PortletURL;

/**
 * <a href="BaseWokflowHandler.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Marcellus Tavares
 */
public abstract class BaseWokflowHandler implements WorkflowHandler {

	public String getTitle(long classPK) throws Exception {
		AssetRenderer assetRenderer = getAssetRenderer(classPK);

		if (assetRenderer != null) {
			return assetRenderer.getTitle();
		}
		else {
			return null;
		}
	}

	public String getType() {
		return TYPE_UNKNOWN;
	}

	public PortletURL getURLEdit(
			long classPK, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		AssetRenderer assetRenderer = getAssetRenderer(classPK);

		if (assetRenderer != null) {
			return assetRenderer.getURLEdit(
				liferayPortletRequest, liferayPortletResponse);
		}
		else {
			return null;
		}
	}

	public void startWorkflowInstance(
			long companyId, long groupId, long userId, long classPK,
			Object model)
		throws Exception {

		WorkflowInstanceLinkLocalServiceUtil.startWorkflowInstance(
			companyId, groupId, userId, getClassName(), classPK);
	}

	protected AssetRenderer getAssetRenderer(long classPK) throws Exception {
		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				getClassName());

		if (assetRendererFactory != null) {
			return assetRendererFactory.getAssetRenderer(classPK);
		}
		else {
			return null;
		}
	}

}