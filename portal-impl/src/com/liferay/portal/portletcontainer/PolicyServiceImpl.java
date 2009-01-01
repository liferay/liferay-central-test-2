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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2009 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.portletcontainer;

import com.liferay.portal.util.WebKeys;

import com.sun.portal.container.service.ServiceAdapter;
import com.sun.portal.container.service.policy.ContainerEventPolicy;
import com.sun.portal.container.service.policy.EventPolicy;
import com.sun.portal.container.service.policy.PolicyService;
import com.sun.portal.container.service.policy.PublicRenderParameterPolicy;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * <a href="PolicyServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Deepak Gothe
 *
 */
public class PolicyServiceImpl extends ServiceAdapter implements PolicyService {

	public void destroy() {
		_eventPolicy = null;
		_publicRenderParameterPolicy = null;
		_containerEventPolicy = null;
	}

	public ContainerEventPolicy getContainerEventPolicy() {
		return _containerEventPolicy;
	}

	public String getDescription() {
		return _DESCRIPTION;
	}

	public EventPolicy getEventPolicy() {
		return _eventPolicy;
	}

	public String getName() {
		return POLICY_SERVICE;
	}

	public PublicRenderParameterPolicy getPublicRenderParameterPolicy() {
		return _publicRenderParameterPolicy;
	}

	public void init(ServletContext context) {
		_eventPolicy = new EventPolicyImpl();
		_containerEventPolicy = new ContainerEventPolicyImpl();
		_publicRenderParameterPolicy = new PublicRenderParameterPolicyImpl();
	}

	public boolean renderPortletsInParallel(HttpServletRequest request) {
		Boolean portletParallelRender = (Boolean)request.getAttribute(
			WebKeys.PORTLET_PARALLEL_RENDER);

		if (portletParallelRender != null) {
			return portletParallelRender.booleanValue();
		}

		return false;
	}

	private static final String _DESCRIPTION =
		"Provide policy information for events and public render parameters " +
			"that are in effect";

	private EventPolicy _eventPolicy;
	private ContainerEventPolicy _containerEventPolicy;
	private PublicRenderParameterPolicy _publicRenderParameterPolicy;

}