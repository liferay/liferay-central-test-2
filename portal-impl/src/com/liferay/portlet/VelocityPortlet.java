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

package com.liferay.portlet;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.velocity.VelocityContext;
import com.liferay.portal.kernel.velocity.VelocityEngineUtil;
import com.liferay.portal.struts.StrutsUtil;
import com.liferay.portal.velocity.VelocityResourceListener;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.MimeResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.velocity.io.VelocityWriter;
import org.apache.velocity.util.SimplePool;

/**
 * <a href="VelocityPortlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Steven P. Goldsmith
 * @author Raymond Augé
 */
public class VelocityPortlet extends GenericPortlet {

	public void init(PortletConfig portletConfig) throws PortletException {
		super.init(portletConfig);

		PortletContext portletContext = portletConfig.getPortletContext();

		_portletContextName = portletContext.getPortletContextName();

		_actionTemplateId = getVelocityTemplateId(
			getInitParameter("action-template"));
		_editTemplateId = getVelocityTemplateId(
			getInitParameter("edit-template"));
		_helpTemplateId = getVelocityTemplateId(
			getInitParameter("help-template"));
		_resourceTemplateId = getVelocityTemplateId(
			getInitParameter("resource-template"));
		_viewTemplateId = getVelocityTemplateId(
			getInitParameter("view-template"));
	}

	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		if (Validator.isNull(_actionTemplateId)) {
			return;
		}

		try {
			mergeTemplate(_actionTemplateId, actionRequest, actionResponse);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException, IOException {

		if (Validator.isNull(_resourceTemplateId)) {
			super.serveResource(resourceRequest, resourceResponse);

			return;
		}

		try {
			mergeTemplate(
				_resourceTemplateId, resourceRequest, resourceResponse);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	public void doEdit(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (renderRequest.getPreferences() == null) {
			super.doEdit(renderRequest, renderResponse);

			return;
		}

		try {
			mergeTemplate(_editTemplateId, renderRequest, renderResponse);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	public void doHelp(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			mergeTemplate(_helpTemplateId, renderRequest, renderResponse);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			mergeTemplate(_viewTemplateId, renderRequest, renderResponse);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	protected VelocityContext getVelocityContext(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		VelocityContext velocityContext =
			VelocityEngineUtil.getWrappedStandardToolsContext();

		velocityContext.put("portletConfig", getPortletConfig());
		velocityContext.put("portletContext", getPortletContext());
		velocityContext.put("preferences", portletRequest.getPreferences());
		velocityContext.put(
			"userInfo", portletRequest.getAttribute(PortletRequest.USER_INFO));

		velocityContext.put("portletRequest", portletRequest);

		if (portletRequest instanceof ActionRequest) {
			velocityContext.put("actionRequest", portletRequest);
		}
		else if (portletRequest instanceof RenderRequest) {
			velocityContext.put("renderRequest", portletRequest);
		}
		else {
			velocityContext.put("resourceRequest", portletRequest);
		}

		velocityContext.put("portletResponse", portletResponse);

		if (portletResponse instanceof ActionResponse) {
			velocityContext.put("actionResponse", portletResponse);
		}
		else if (portletRequest instanceof RenderResponse) {
			velocityContext.put("renderResponse", portletResponse);
		}
		else {
			velocityContext.put("resourceResponse", portletResponse);
		}

		return velocityContext;
	}

	protected String getVelocityTemplateId(String name) {
		if (Validator.isNull(name)) {
			return name;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_portletContextName);
		sb.append(VelocityResourceListener.SERVLET_SEPARATOR);
		sb.append(StrutsUtil.TEXT_HTML_DIR);
		sb.append(name);

		return sb.toString();
	}

	protected void mergeTemplate(
			String velocityTemplateId, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws Exception {

		mergeTemplate(
			velocityTemplateId,
			getVelocityContext(portletRequest, portletResponse),
			portletRequest, portletResponse);
	}

	protected void mergeTemplate(
			String velocityTemplateId, VelocityContext velocityContext,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse)portletResponse;

			mimeResponse.setContentType(
				portletRequest.getResponseContentType());
		}

		VelocityWriter velocityWriter = null;

		try {
			velocityWriter = (VelocityWriter)_writerPool.get();

			PrintWriter output = null;

			if (portletResponse instanceof MimeResponse) {
				MimeResponse mimeResponse = (MimeResponse)portletResponse;

				output = mimeResponse.getWriter();
			}
			else {
				output = new PrintWriter(System.out);
			}

			if (velocityWriter == null) {
				velocityWriter = new VelocityWriter(output, 4 * 1024, true);
			}
			else {
				velocityWriter.recycle(output);
			}

			VelocityEngineUtil.mergeTemplate(
				velocityTemplateId, null, velocityContext, velocityWriter);
		}
		finally {
			try {
				if (velocityWriter != null) {
					velocityWriter.flush();
					velocityWriter.recycle(null);

					_writerPool.put(velocityWriter);
				}
			}
			catch (Exception e) {
			}
		}
	}

	private static SimplePool _writerPool = new SimplePool(40);

	private String _portletContextName;
	private String _actionTemplateId;
	private String _editTemplateId;
	private String _helpTemplateId;
	private String _resourceTemplateId;
	private String _viewTemplateId;

}