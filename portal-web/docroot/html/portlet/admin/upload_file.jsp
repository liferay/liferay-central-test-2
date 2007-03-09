<%
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
%>
<liferay-ui:success key="pluginUploaded" message="the-plugin-was-uploaded-successfully-and-is-now-being-installed" />

<liferay-ui:error exception="<%= UploadException.class %>" message="an-unexpected-error-occurred-while-uploading-your-file" />

<%= LanguageUtil.get(pageContext, "upload-a-war-file-to-install-a-layout-template,-portlet,-or-theme") %>

<br><br>

<input name="<portlet:namespace />file" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="file">

<br><br>

<%= LanguageUtil.get(pageContext, "specify-an-optional-context-for-deployment") %>

<%= LanguageUtil.format(pageContext, "for-example-x", "<i>sample-jsp-portlet</i>") %>

<br><br>

<input name="<portlet:namespace />deploymentContext" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;">

<br><br>

<input type="button" value='<%= LanguageUtil.get(pageContext, "install") %>' onClick="<%= uploadProgressId %>.startProgress(); <portlet:namespace />installPluginPackage('localDeploy', '<%= uploadProgressId %>');">

<liferay-ui:upload-progress
	id="<%= uploadProgressId %>"
	message="uploading"
	redirect="<%= currentURL %>"
/>