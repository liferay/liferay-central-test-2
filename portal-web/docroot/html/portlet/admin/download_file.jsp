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

<liferay-ui:success key="pluginDownloaded" message="the-plugin-was-downloaded-successfully-and-is-now-being-installed" />

<%= LanguageUtil.get(pageContext, "specify-a-URL-for-a-remote-layout-template,-portlet,-or-theme") %>

<%= LanguageUtil.format(pageContext, "for-example-x", "<i>http://easynews.dl.sourceforge.net/sourceforge/lportal/sample-jsp-portlet-" + ReleaseInfo.getVersion() + ".war</i>") %>

<br /><br />

<input name="<portlet:namespace />url" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" value="">

<br /><br />

<%= LanguageUtil.get(pageContext, "specify-an-optional-context-for-deployment") %>

<%= LanguageUtil.format(pageContext, "for-example-x", "<i>sample-jsp-portlet</i>") %>

<br /><br />

<input name="<portlet:namespace />remoteDeployWARName" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" value="">

<br /><br />

<input type="button" value='<%= LanguageUtil.get(pageContext, "install") %>' onClick="<%= downloadProgressId %>.startProgress(); <portlet:namespace />installPluginPackage('remoteDeploy', '<%= downloadProgressId %>');">

<liferay-ui:upload-progress
	id="<%= downloadProgressId %>"
	message="downloading"
	redirect="<%= currentURL %>"
/>