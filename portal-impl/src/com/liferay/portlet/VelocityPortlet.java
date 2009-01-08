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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.StrutsUtil;
import com.liferay.portal.velocity.VelocityResourceListener;
import com.liferay.portal.velocity.VelocityVariables;

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

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.apache.velocity.io.VelocityWriter;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.util.SimplePool;

/**
 * <a href="VelocityPortlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Steven P. Goldsmith
 * @author Raymond Augé
 *
 */
public class VelocityPortlet extends GenericPortlet {

	public void init(PortletConfig portletConfig) throws PortletException {
		super.init(portletConfig);

		PortletContext portletContext = portletConfig.getPortletContext();

		_portletContextName = portletContext.getPortletContextName();

		_actionTemplate = getInitParameter("action-template");
		_editTemplate = getInitParameter("edit-template");
		_helpTemplate = getInitParameter("help-template");
		_resourceTemplate = getInitParameter("resource-template");
		_viewTemplate = getInitParameter("view-template");
	}

	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		if (Validator.isNull(_actionTemplate)) {
			return;
		}

		try {
			Template template = getTemplate(_actionTemplate);

			mergeTemplate(template, actionRequest, actionResponse);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException, IOException {

		if (Validator.isNull(_resourceTemplate)) {
			super.serveResource(resourceRequest, resourceResponse);

			return;
		}

		try {
			Template template = getTemplate(_resourceTemplate);

			mergeTemplate(template, resourceRequest, resourceResponse);
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
			Template template = getTemplate(_editTemplate);

			mergeTemplate(template, renderRequest, renderResponse);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	public void doHelp(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			Template template = getTemplate(_helpTemplate);

			mergeTemplate(template, renderRequest, renderResponse);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			Template template = getTemplate(_viewTemplate);

			mergeTemplate(template, renderRequest, renderResponse);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	protected Context getContext(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Context context = new VelocityContext();

		context.put("portletConfig", getPortletConfig());
		context.put("portletContext", getPortletContext());
		context.put("preferences", portletRequest.getPreferences());
		context.put(
			"userInfo", portletRequest.getAttribute(PortletRequest.USER_INFO));

		context.put("portletRequest", portletRequest);

		if (portletRequest instanceof ActionRequest) {
			context.put("actionRequest", portletRequest);
		}
		else if (portletRequest instanceof RenderRequest) {
			context.put("renderRequest", portletRequest);
		}
		else {
			context.put("resourceRequest", portletRequest);
		}

		context.put("portletResponse", portletResponse);

		if (portletResponse instanceof ActionResponse) {
			context.put("actionResponse", portletResponse);
		}
		else if (portletRequest instanceof RenderResponse) {
			context.put("renderResponse", portletResponse);
		}
		else {
			context.put("resourceResponse", portletResponse);
		}

		VelocityVariables.insertHelperUtilities((VelocityContext)context, null);

		return context;
	}

	protected Template getTemplate(String name) throws Exception {
		return RuntimeSingleton.getTemplate(
			_portletContextName + VelocityResourceListener.SERVLET_SEPARATOR +
				StrutsUtil.TEXT_HTML_DIR + name, StringPool.UTF8);
	}

	protected Template getTemplate(String name, String encoding)
		throws Exception {

		return RuntimeSingleton.getTemplate(
			StrutsUtil.TEXT_HTML_DIR + name, encoding);
	}

	protected void mergeTemplate(
			Template template, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws Exception {

		mergeTemplate(
			template, getContext(portletRequest, portletResponse),
			portletRequest, portletResponse);
	}

	protected void mergeTemplate(
			Template template, Context context, PortletRequest portletRequest,
			PortletResponse portletResponse)
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

			template.merge(context, velocityWriter);
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
	private String _actionTemplate;
	private String _editTemplate;
	private String _helpTemplate;
	private String _resourceTemplate;
	private String _viewTemplate;

}