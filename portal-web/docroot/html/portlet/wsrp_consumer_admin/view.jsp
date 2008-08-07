<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

<%
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
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */
%>

<%@page contentType="text/html"%>

<%@page import="java.util.Map" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="com.sun.portal.wsrp.consumer.portlets.beans.*" %>
<%@page import="com.sun.portal.wsrp.consumer.portlets.AdminPortletAction" %>

<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<portlet:defineObjects/>

<fmt:setBundle basename="ConsumerAdminPortlet" />

<script>

	var <portlet:namespace/>_registrationHandleRequired = false;
	function <portlet:namespace/>_toggleDivs(val){
		var regDescDiv = document.getElementById("propertyDesc");
		var regHandleDiv = document.getElementById("regHandle");
		if(val == "true"){
			regDescDiv.style.display = "block";
			regHandleDiv.style.display = "none";
			<portlet:namespace/>_registrationHandleRequired = false;
		} else {
			regDescDiv.style.display = "none";
			regHandleDiv.style.display = "block";
			<portlet:namespace/>_registrationHandleRequired = true;
		}
	}

	function <portlet:namespace/>_populateSelectedConfiguredProducers(formName){
		var formObj = eval(formName);
		var numOfConfiguredProducers = formObj.numberOfConfiguredProducers.value;
		var appendComma = false;
		for(var i=0; i<numOfConfiguredProducers; i++){
			var checkBoxObj = eval(formName+".check"+i);
			if(checkBoxObj.checked){
				var producerKeyObj = eval(formName+".configuredProducer"+i);
				var producerKey = producerKeyObj.value;
				if(appendComma){
					formObj.selectedConfiguredProducers.value =
					formObj.selectedConfiguredProducers.value + "," + producerKey;
				} else {
					formObj.selectedConfiguredProducers.value = producerKey;
					appendComma = true;
				}
			}
		}
		if(!appendComma){
			alert('<fmt:message key="delete.message" />');
			return;
		}
		formObj.submit();
	}

	function <portlet:namespace/>_toggleConfiguredProducerSelection(checkBoxObj){
		var numOfConfiguredProducers =
		document.deleteConfiguredProducerForm.numberOfConfiguredProducers.value;
		if(numOfConfiguredProducers == 0){
			return;
		}
		for(var i=0; i<numOfConfiguredProducers; i++){
			eval("document.deleteConfiguredProducerForm.check"+i+".checked = " + checkBoxObj.checked);
		}
		return;
	}

	function <portlet:namespace/>_checkChannelSelection(formObj){
		if(formObj.action.value != "<%=AdminPortletAction.DELETE_CHANNELS%>"){
			return true;
		}
		if(formObj.channelNames.value == ""){
			alert('<fmt:message key="select.channel.for.deletion" />');
			return false;
		}
		return true;
	}

	function <portlet:namespace/>_validateConsumer(formObj){

		//No validation is required when user press cancel button

		if(formObj.action.value=='<%=AdminPortletAction.LIST%>'){
			return true;
		}
		if(formObj.configuredProducerName.value == "" ||
			formObj.configuredProducerName.value.indexOf(" ") > -1){

			alert('<fmt:message key="provide.valid.consumer.name" />');
			formObj.configuredProducerName.focus();
			return false;
		}
		var validCharacters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
		for(var i = 0; i < formObj.configuredProducerName.value.length; i++){
			if (validCharacters.indexOf(formObj.configuredProducerName.value.charAt(i)) == -1) {
				alert ("<fmt:message key="invalid.consumer.name" />");
				formObj.configuredProducerName.focus();
				return false;
			}
		}
		if(<portlet:namespace/>_registrationHandleRequired &&
			formObj.registrationHandle.value == ""){
			alert('<fmt:message key="provide.valid.registration.handle" />');
			formObj.registrationHandle.focus();
			return false;
		}
		if(!<portlet:namespace/>_isLifetimeValid()){
			return false;
		}
		return true;
	}

	function <portlet:namespace/>_validateWSDL(formObj){

		//No validation is required when user press cancel button

		if(formObj.action.value=='<%=AdminPortletAction.LIST%>'){
			return true;
		}
		if(formObj.wsdl.value == "" || formObj.wsdl.value.indexOf("http") < 0 ||
			formObj.wsdl.value.indexOf("://") < 0){
			alert('<fmt:message key="provide.valid.wsdl" />');
			formObj.wsdl.focus();
			return false;
		}else {
			return true;
		}
	}

	function <portlet:namespace/>_validateWindowName(formObj){

		//No validation is required when user press cancel button

		if(formObj.action.value=='<%=AdminPortletAction.LIST%>'){
			return true;
		}
		if(formObj.channelName.value == "" || formObj.channelName.value.indexOf(" ") > -1){
			alert('<fmt:message key="invalid.window.name" />');
			formObj.channelName.focus();
			return false;
		}
		var validCharacters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
		for(var i = 0; i < formObj.channelName.value.length; i++){
			if (validCharacters.indexOf(formObj.channelName.value.charAt(i)) == -1) {
				alert ("<fmt:message key="invalid.window.name" />");
				formObj.channelName.focus();
				return false;
			}
		}
		return true;
	}

	function <portlet:namespace/>_toggleLifetime() {
		if(document.getElementById("lifetime.supplied").checked == true) {
			document.getElementById("lifetime.days.msg").style.display="inline";
			document.getElementById("lifetime.days").style.display="inline";
	 		document.getElementById("lifetime.hours.msg").style.display="inline";
	 		document.getElementById("lifetime.hours").style.display="inline";
	 		document.getElementById("lifetime.mins.msg").style.display="inline";
	 		document.getElementById("lifetime.mins").style.display="inline";
		}
		else {
			document.getElementById("lifetime.days.msg").style.display="none";
			document.getElementById("lifetime.days").style.display="none";
			document.getElementById("lifetime.hours.msg").style.display="none";
			document.getElementById("lifetime.hours").style.display="none";
			document.getElementById("lifetime.mins.msg").style.display="none";
			document.getElementById("lifetime.mins").style.display="none";
		}
	}

	function <portlet:namespace/>_isLifetimeValid()
	{
		var isValid = true;
		if(document.getElementById("lifetime.supplied") != null &&
			document.getElementById("lifetime.supplied").checked == true) {
			if(document.getElementById("lifetime.days").value == "" ||
				isNaN(document.getElementById("lifetime.days").value)){
				alert('<fmt:message key="provide.valid.lifetime.days" />');
				isValid = false;
			}
			if(document.getElementById("lifetime.hours").value == "" ||
				isNaN(document.getElementById("lifetime.hours").value)){
				alert('<fmt:message key="provide.valid.lifetime.hours" />');
				isValid = false;
			}
			if(document.getElementById("lifetime.mins").value == "" ||
				isNaN(document.getElementById("lifetime.mins").value)){
				alert('<fmt:message key="provide.valid.lifetime.mins" />');
				isValid = false;
			}
			return isValid;
		}else {
				return true;
		}
	}

</script>

<noscript>Javascript has been disabled on this browser. Please enable Javascript for proper functionality.</noscript>

<% String inaccessibleHostMBeanServer = (String)renderRequest.getPortletSession().getAttribute("consumer_mbeanServerNotAccessible");

if(inaccessibleHostMBeanServer != null){
%>
	<span style="color:red">
		<fmt:message key="mbean.server.notstarted" /><br />
		<b><%=inaccessibleHostMBeanServer%></b>.
	</span>
<%
} else {
	int action = AdminPortletAction.LIST;
	String actionString = renderRequest.getParameter("action");
	if(actionString != null){
		action = Integer.parseInt(actionString);
	}
	System.out.println("ConsumerPortlet:view.jsp:action="+actionString);

	/////////////////////////////////////////////////////////
	//GET_PRODUCER_INFO ask for new configured producer name
	/////////////////////////////////////////////////////////
	if(action == AdminPortletAction.GET_VERSION_INFO) {
	%>
		<form name="producerInfoForm" action="<%=renderResponse.createActionURL().toString()%>"
		method="post" onSubmit='return <portlet:namespace/>_validateWSDL(this)'>

		<table>
			<tbody>
			<%
			String wsdlURL = "";
			if(request.getAttribute("CONSUMER_ADMIN_ERROR")!=null){
				wsdlURL = request.getParameter("wsdl");
			%>
				<tr>
					<td colspan='2'>
						<span style="color:red">
							<fmt:message key="invalid.wsdl.url" /><br />
						</span>
					</td>
				</tr>
				<%
				}
				%>
				<tr>
					<td><span color="red">*
						</span><label for="wsdlId"><fmt:message key="consumer.wsdl.url" /></label>
					</td>
					<td>
						<input type="text" name="wsdl" class="textboxStyle" id="wsdlId" value="<%=wsdlURL%>">&nbsp;
						<div id="testDivId" style="display:none">
							<label for="testId"><fmt:message key="test" /></label>
						</div>
						<input type="button" class="buttonStyle" id="testId" value="<fmt:message key="test" />"
						onclick="javascript:window.open(document.producerInfoForm.wsdl.value);" />
						&nbsp;
						<div id="clearDivId" style="display:none">
							<label for="clearId"><fmt:message key="clear" /></label>
						</div>
						<input type="button" class="buttonStyle" id="clearId" value="<fmt:message key="clear" />"
						onclick="document.producerInfoForm.wsdl.value=''" />
					</td>
				</tr>
				<tr>
					<td>
						<input type="submit" class="buttonStyle" value="<fmt:message key="get.info" />" />
					</td>
					<td>
						<input type="submit" class="longButtonStyle" value="<fmt:message key="backto.list" />"
						onclick="document.producerInfoForm.action.value='<%=AdminPortletAction.LIST%>'" />
					</td>
				</tr>

			</tbody>
		</table>

		<input type="hidden" name="action" value="<%=AdminPortletAction.GET_VERSION_INFO%>" />

		</form>

		<%
	} else if (action == AdminPortletAction.GET_PRODUCER_INFO) {
		%>
		<form name="producerInfoForm" action="<%=renderResponse.createActionURL().toString()%>"
			method="post" onSubmit='return <portlet:namespace/>_validateWSDL(this)'>
			<table>
				<tbody>
				<%
				if(request.getAttribute("CONSUMER_ADMIN_ERROR")!=null){
				%>
					<tr>
						<td colspan='2'>
							<span style="color:red">
								<fmt:message key="producer.getinfo.failed" /><br />
							</span>
						</td>
					</tr>
				<%
				}
				%>

				<%
				String wsdlURL = request.getParameter("wsdl");
				List supported_versions = (List)renderRequest.getPortletSession(false).getAttribute("producerSupportedVersions");
				%>
					<tr>
						<td><label for="wsdlId"><fmt:message key="consumer.wsdl.url" /></label>
						</td>
						<td>
						<%=wsdlURL%>
						<input type="hidden" name="wsdl" value="<%=renderRequest.getParameter("wsdl")%>" />
						</td>
					</tr>
					<!--Following is added for version-->
					<tr>
						<td>
							<span color="red">*</span><label for="wsdlVersion"><fmt:message key="supported.versions" /></label>
						</td>
						<td>
							<select name="version">
						<%
						for(int i=0; i<supported_versions.size(); i++){
						%>
								<option value="<%=supported_versions.get(i)%>"><%=supported_versions.get(i)%></option>
								<%
								}
								%>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<input type="submit" class="buttonStyle" value=<fmt:message key="get.info" />/>
						</td>
						<td>
							<input type="submit" class="buttonStyle" value="<fmt:message key="backto.list" />"
							onclick="document.producerInfoForm.action.value='<%=AdminPortletAction.LIST%>'" />
						</td>
					</tr>
				</tbody>
			</table>
			<input type="hidden" name="action" value="<%=AdminPortletAction.GET_PRODUCER_INFO%>" />
		</form>

		<%
	} else if (action == AdminPortletAction.GET_DEFAULT) {
	%>
		<form name="consumerForm" action="<%=renderResponse.createActionURL().toString()%>"

		method="post" onSubmit="return <portlet:namespace/>_validateConsumer(this)">
			<table>
				<tbody>
				<%
				String configuredProducerName = "";
				if(request.getAttribute("CONSUMER_ADMIN_ERROR")!=null){
					configuredProducerName = request.getParameter("configuredProducerName");
				%>
					<tr>
						<td colspan='2'>
							<span style="color:red">
							<fmt:message key="configured.producer.creation.failed" /><br />
							</span>
						</td>
					</tr>
				<%
				}
				%>

					<tr>
						<td>
							<span color="red">*
							</span><label for="configuredProducerNameId">
							<fmt:message key="configured.producer.name" /></label>
						</td>
						<td>
							<input type="text" name="configuredProducerName" class="textboxStyle"
							value="<%=configuredProducerName%>" id="configuredProducerNameId" />
						</td>
					</tr>
					<tr>
						<td>
							<label><fmt:message key="consumer.wsdl.url" /></label>
						</td>
						<td>
							<a href="<%=renderRequest.getParameter("wsdl")%>"><%=renderRequest.getParameter("wsdl")%></a>
							<input type="hidden" name="wsdl" value="<%=renderRequest.getParameter("wsdl")%>" />
						</td>
					</tr>
					<tr>
						<td>
							<label><fmt:message key="wsrp.version" /></label>
						</td>
						<td>
							<%=request.getParameter("version")%>
							<input type="hidden" name="version" value="<%=renderRequest.getParameter("version")%>" />
						</td>
					</tr>
					<%
					Map producerInfoMap = (Map)renderRequest.getPortletSession(false).getAttribute("producerInfoMap");
					if(producerInfoMap != null && !producerInfoMap.isEmpty()) {
						boolean registrationRequired =
						Boolean.parseBoolean(producerInfoMap.get("RegistrationRequired").toString());
						if(registrationRequired) {
						%>

							<%
							boolean inbandRegistrationSupported =
							Boolean.parseBoolean(producerInfoMap.get("InbandSupported").toString());

							if(inbandRegistrationSupported) {
								Map registrationPropertyDescription =
								(Map)producerInfoMap.get("RegistrationPropertyDescription");

							%>
							<tr>
								<td colspan="2">
									<input type="radio" id="inbandRegistrationId" name="inbandRegistration" value="true" checked
									onclick="<portlet:namespace/>_toggleDivs(this.value);" />
									<label for="inbandRegistrationId"><fmt:message key="register.inband" /></label>
									<br>
									<input type="radio" id="outOfBandRegistrationId" name="inbandRegistration" value="false"
									onclick="<portlet:namespace/>_toggleDivs(this.value);" />
									<label for="outOfBandRegistrationId"><fmt:message key="provide.outofband.registration.handle" /></label>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div id="propertyDesc" style="display:block">

									<table id="regPropDesc" width="75%" style="border: 1px solid black;" cellpadding="2" cellspacing="1">
									<caption>
									<fmt:message key="registration.property.descs" /></caption>
										<tbody class="withBackground">
											<tr class="thStyle">
												<th scope="col"><fmt:message key="registration.property.name" /></td>
												<th scope="col"><fmt:message key="registration.property.value" /></td>
												<th scope="col"><fmt:message key="registration.property.desc" /></td>
											</tr>
											<%
											if(registrationPropertyDescription == null
											|| registrationPropertyDescription.isEmpty()) {
											%>
									<tr>
									<td colspan="3"><fmt:message key="registration.no.properties" /></td>
									</tr>
									<%
									} else {
										Iterator entryIterator = registrationPropertyDescription.entrySet().iterator();
										int index = 0;
										while(entryIterator.hasNext()) {
											Map.Entry entry = (Map.Entry)entryIterator.next();
											String name = (String)entry.getKey();
											String description = (String)entry.getValue();
										%>
										<tr>
											<td>
												<label for="regPropValue<%=index%>Id"><%=name%></label>
												<input type="hidden" name="regPropName<%=index%>" value="<%=name%>" />
											</td>
											<td>
												<input type="text" class="textboxStyle" name="regPropValue<%=index%>"
												id="regPropValue<%=index%>Id">
											</td>
											<td>
												<%=description%><input type="hidden" name="regPropDescription<%=index%>"
												value="<%=description%>" />
											</td>
										</tr>
										<%
											index++;
										}
									}
									%>
										</tbody>
									</table>
									</div>

									<div id="regHandle" style="display:none">
									<span color="red">*</span>
									<label for="registrationHandleId"><fmt:message key="registration.handle" /></label>
									<input type="text" class="textboxStyle" name="registrationHandle" id="registrationHandleId">
									</div>
								</td>
							</tr>
									<%
							} else {
							%>
							<tr>
								<td>
									<span color="red">*</span>
									<label for="registrationHandleId"><fmt:message key="registration.handle" /></label>
								</td>
								<td>
									<input type="text" class="textboxStyle" name="registrationHandle" id="registrationHandleId">
									<script>
										<portlet:namespace/>_registrationHandleRequired = true;
									</script>
								</td>
							</tr>
							<%
							}
						} else {
						%>
						<!--<tr>
							<td>
								<span color="red">*</span><label for="registrationHandleId">
								<fmt:message key="registration.handle" /></label>
							</td>
							<td>
								<input type="text" class="textboxStyle" name="registrationHandle" id="registrationHandleId">
							</td>
						</tr> -->
						<%
						}
					}
					%>
					<%if("V2".equals(request.getParameter("version"))) {
						boolean inbandRegistrationSupported =
						Boolean.parseBoolean(producerInfoMap.get("InbandSupported").toString());
						if(inbandRegistrationSupported) { %>
							<tr>
								<td>
									<div id="lifetime.supplied.msg"><fmt:message key="lifetime.supplied.msg" /></div>
									<input id="lifetime.supplied" type="checkbox" class="checkboxStyle"
									name="lifetimeSupplied" value="true" onclick="javascript:<portlet:namespace/>_toggleLifetime();" />
								</td>
								<td>
									<div id="lifetime.days.msg" style="display:none"><fmt:message key="lifetime.days.msg" /></div>
									<input id="lifetime.days" type="text" name="lifetimeDays" size="5" style="display:none" />
									<div id="lifetime.hours.msg" style="display:none"><fmt:message key="lifetime.hours.msg" /></div>
									<input id="lifetime.hours" type="text" name="lifetimeHours" size="5" style="display:none" />
									<div id="lifetime.mins.msg" style="display:none"><fmt:message key="lifetime.mins.msg" /></div>
									<input id="lifetime.mins" type="text" name="lifetimeMins" size="5" style="display:none" />
								</td>
							</tr>
						<%}
					}%>
					<tr>
						<td>
							<input type="submit" value=<fmt:message key="save" /> class="buttonStyle" />
						</td>
						<td>
							<input type="submit" value=<fmt:message key="cancel" /> class="buttonStyle"
							onclick="document.consumerForm.action.value='<%=AdminPortletAction.LIST%>'"
							<%--onKeyPress="document.consumerForm.action.value='<%=AdminPortletAction.LIST%>'"--%>/>
						</td>
					</tr>
				</tbody>
			</table>
			<input type="hidden" name="identityPropogation" value="none" />
			<input type="hidden" name="action" value="<%=AdminPortletAction.CREATE%>" />
		</form>
		<%
	////////////////////////////////////////////////////////
	} else if (action == AdminPortletAction.LIST) {
	////////////////////////////////////////////////////////

		%>
		<form name="addConfiguredProducerForm" action="<%=renderResponse.createActionURL().toString()%>" method="post">
			<input type="hidden" name="action" value="<%=AdminPortletAction.GET_VERSION_INFO%>" />
		</form>

		<form name="createChannelForm" action="<%=renderResponse.createActionURL().toString()%>" method="post">
			<input type="hidden" name="configuredProducerId" />
			<input type="hidden" name="configuredProducerName" />
			<input type="hidden" name="action" value="<%=AdminPortletAction.GET_INFO_FOR_CHANNEL%>" />
		</form>

		<form name="listChannelForm" action="<%=renderResponse.createActionURL().toString()%>" method="post">
			<input type="hidden" name="action" value="<%=AdminPortletAction.LIST_CHANNELS%>" />
		</form>

		<form name="deleteConfiguredProducerForm" action="<%=renderResponse.createActionURL().toString()%>" method="post">
			<input type="hidden" name="action" value="<%=AdminPortletAction.DELETE%>" />
			<input type="hidden" name="selectedConfiguredProducers" />

			<table width="100%" class="withBackground">
				<thead class="tableHead">
				<tr>
					<th colspan="2" scope="col">
					<fmt:message key="configured.producers" />
					</th>
				</tr>
				</thead>
				<tbody>
				<%
				String errorKey = (String)request.getAttribute("CONSUMER_ADMIN_ERROR");
				if(errorKey!=null){
				%>
					<tr>
						<td colspan='2'>
							<span style="color:red">
							<% if(errorKey.equals("DELETION_ERROR")){ %>
								<fmt:message key="error.in.consumer.deletion" /><br />
							<%}else if(errorKey.equals("UPDATE_SD_ERROR")){%>
								<fmt:message key="error.in.update.sd" /><br />
							<%}else if(errorKey.equals("LIST_CONFIGURED_PRODUCERS_ERROR")){%>
								<fmt:message key="error.in.obtaining.list" /><br />
							<%}%>
							</span>
						</td>
					</tr>
				<%
				}
				%>
				<tr>
					<td>
						<div id="newDiv" style="display:none">
							<label for="newId"><fmt:message key="create.new.configured.producer" /></label>
						</div>
						<input type="button" id="newId" value=<fmt:message key="new" /> class="buttonStyle"
						onclick="document.addConfiguredProducerForm.submit();"
						<%--onKeyPress="document.addConfiguredProducerForm.submit();"--%>/>
						<div id="deleteDiv" style="display:none">
						<label for="deleteId"><fmt:message key="delete.configured.producer" /></label>
						</div>
						<input type="button" id="deleteId" value=<fmt:message key="delete" /> class="buttonStyle"
						onclick="<portlet:namespace/>_populateSelectedConfiguredProducers('document.deleteConfiguredProducerForm');" />
					</td>

					<td align="right" />
						<a href="<%=renderResponse.createActionURL().toString()%>&action=<%=AdminPortletAction.LIST_CHANNELS%>">
						<fmt:message key="channels.delete" />
						</a>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table border="0" width="100%" cellspacing="1" cellpadding="2">
							<thead class="thStyle">
								<tr>
									<th style="width:5%;" scope="col">
										<input type="checkbox" name="checkMain" id="checkMainId" value="0"
										onclick="<portlet:namespace/>_toggleConfiguredProducerSelection(this);" />
										<div id="checkMainDiv" style="display:none">
										<label for="checkMainId"><fmt:message key="select.all" /></label>
										</div>
									</th>
									<th scope="col"><fmt:message key="configured.producer.name" /></th>
									<th scope="col"><fmt:message key="configured.producer.id" /></th>
									<th scope="col"><fmt:message key="configured.producer.status" /></th>
									<th scope="col"><fmt:message key="configured.producer.channel" /></th>
								</tr>
							</thead>
							<tbody class="tdStyle">
							<%
							if(renderRequest.getAttribute("listConfiguredProducerBeans") != null){
								ConfiguredProducerElementBean[] configuredProducerBeans =
									(ConfiguredProducerElementBean[])renderRequest.getAttribute("listConfiguredProducerBeans");
								for(int i=0; i<configuredProducerBeans.length; i++){
							%>
								<tr>
									<td>
										<input type="checkbox" id="check<%=i%>Id" name="check<%=i%>">
										<div id="checkDiv<%=i%>" style="display:none">
											<label for="check<%=i%>Id"><fmt:message key="select" /></label>
										</div>
									</td>
									<td>
										<a href="<%=renderResponse.createActionURL().toString()%>&configuredProducerId=<%=configuredProducerBeans[i].getId()%>&action=<%=AdminPortletAction.GET_DETAILS%>">
										<%=configuredProducerBeans[i].getName()%>
										</a>
									</td>
									<td><%=configuredProducerBeans[i].getId()%></td>
									<%
									if( configuredProducerBeans[i].getStatus()){
									%>
									<td><fmt:message key="configured.producer.enabled" /></td>
									<%
									} else {
									%>
									<td><fmt:message key="configured.producer.disabled" /></td>
									<% } %>
									<td>
										<a href="<%=renderResponse.createActionURL().toString()%>&action=<%=AdminPortletAction.GET_INFO_FOR_CHANNEL%>&configuredProducerId=<%=configuredProducerBeans[i].getId()%>&configuredProducerName=<%=configuredProducerBeans[i].getName()%>">
										<fmt:message key="configured.producer.create.channel" />
										</a>
									</td>
									<input type="hidden" name="configuredProducer<%=i%>" value="<%=configuredProducerBeans[i].getId()%>" />
								</tr>
								<%
		 						}
								%>
								<input type="hidden" name="numberOfConfiguredProducers" value="<%=configuredProducerBeans.length%>" />
								<%
								} else {
								%>
								<input type="hidden" name="numberOfConfiguredProducers" value="0" />
								<%
								}
								%>
							</tbody>
						</table>
					</td>
				</tr>
				</tbody>
			</table>
		</form>
	<%
	////////////////////////////////////////////////////////
	} else if (action == AdminPortletAction.GET_DETAILS) {
	////////////////////////////////////////////////////////

	ConfiguredProducerBean bean =
	(ConfiguredProducerBean)renderRequest.getPortletSession(false).getAttribute("configuredProducerBean");
	%>

		<form name="consumerForm" action="<%=renderResponse.createActionURL().toString()%>" method="post">
			<table>
				<tbody>
				<tr>
					<td><label><fmt:message key="configured.producer.name" /></label></td>
					<td>
						<%=bean.getName()%>
						<input type="hidden" name="configuredProducerName" value="<%=bean.getName()%>" />
						<input type="hidden" name="configuredProducerId" value="<%=bean.getConfiguredProducerId()%>" />
					</td>
				</tr>
				<tr>
					<td>
						<label><fmt:message key="consumer.wsdl.url" /></label>
					</td>
					<td>
						<a href="<%=bean.getWsdlURL()%>"><%=bean.getWsdlURL()%></a>
					</td>
				</tr>
				<tr>
					<td>
						<label><fmt:message key="wsrp.version" /></label>
					</td>
					<td>
						<%=bean.getVersion()%></a>
					</td>
				</tr>
				<tr>
					<td><label><fmt:message key="configured.producer.status" /></label></td>
					<td>
						<input type="radio" id="enabledId" name="enabled" value="true" <%=bean.isStatus()?"checked":""%>/>
						<label for="enabledId"><fmt:message key="configured.producer.enabled" /></label>
						<br>
						<input type="radio" id="disabledId" name="enabled" value="false" <%=!bean.isStatus()?"checked":""%>/>
						<label for="disabledId"><fmt:message key="configured.producer.disabled" /></label>
					</td>
				</tr>
				<tr>
					<td>
						<label><fmt:message key="configured.producer.user.identity" /></label>
					</td>
					<td>
						<%=bean.getUserIdentityPropagation()%>
					</td>
				</tr>
				<tr>
					<td><label><fmt:message key="registration.handle" /></label></td>
					<td>
						<%=bean.getRegistrationHandle()%>
					</td>
				</tr>
				<tr>
					<td>
						<label><fmt:message key="update.service.description" /></label>
					</td>
					<td>
						<input type="submit" value=<fmt:message key="update.sd.button" /> class="buttonStyle"
						onclick="document.consumerForm.action.value='<%=AdminPortletAction.UPDATE_SD%>'" />
					</td>
				</tr>
				<tr>
					<td><label><fmt:message key="registration.properties" /></label></td>
					<td>
						<table id="regPropDesc" width="75%" style="border: 1px solid black;" cellpadding="2" cellspacing="1">
							<caption><fmt:message key="registration.property.descs" /></caption>
							<tbody class="withBackground">
							<tr class="thStyle">
								<th scope="col"><fmt:message key="registration.property.name" /></td>
								<th scope="col"><fmt:message key="registration.property.value" /></td>
								<th scope="col"><fmt:message key="registration.property.desc" /></td>
							</tr>

							<%
							Map registrationPropertyDescription = bean.getRegistrationPropertyDescriptions();
							if(registrationPropertyDescription == null
							|| registrationPropertyDescription.isEmpty()) {
							%>

							<tr>
								<td colspan="3"><fmt:message key="registration.no.properties" /></td>
							</tr>

							<%
							} else {
							Map registrationProperties = bean.getRegistrationProperties();
							Iterator entryIterator = registrationPropertyDescription.entrySet().iterator();
							int index = 0;
							while(entryIterator.hasNext()) {
								Map.Entry entry = (Map.Entry)entryIterator.next();
								String name = (String)entry.getKey();
								String description = (String)entry.getValue();
								String value = "";
								if(registrationProperties != null){
									Object object = registrationProperties.get(name);
									if(object !=null){
										value = (String)object;
									}
								}
							%>
							<tr>
								<td>
									<%=name%><input type="hidden" name="regPropName<%=index%>" value="<%=name%>" />
								</td>
								<td>
									<input type="text" class="textboxStyle" name="regPropValue<%=index%>" value="<%=value%>">
								</td>
								<td>
									<%=description%><input type="hidden" name="regPropDescription<%=index%>"
									value="<%=description%>" />
								</td>
							</tr>
							<%
							index++;
							}
							}
							%>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
				<%if(bean.getTerminationTime() != null) {%>
					<td>
						<label><fmt:message key="wsrp.terminationTime" /></label>
					</td>
					<td>
						<%=bean.getTerminationTime()%>
					</td>
					<%}%>
				</tr>
				<tr>
					<td>
						<input type="submit" value=<fmt:message key="save" /> class="buttonStyle" />
					</td>
					<td>
						<input type="submit" value=<fmt:message key="cancel" /> class="buttonStyle"
						onclick="document.consumerForm.action.value='<%=AdminPortletAction.LIST%>'"
						onKeyPress="document.consumerForm.action.value='<%=AdminPortletAction.LIST%>'" />
					</td>
				</tr>
				</tbody>
			</table>
			<input type="hidden" name="action" value="<%=AdminPortletAction.UPDATE%>" />
			<input type="hidden" name="configuredProducerId" value="<%=bean.getConfiguredProducerId()%>" />
		</form>

	<%
	///////////////////////////////////////////////////////////////
	} else if (action == AdminPortletAction.GET_INFO_FOR_CHANNEL) {
	///////////////////////////////////////////////////////////////

	%>

	<b><fmt:message key="create.new.channel" /></b>

		<form name="channelForm" method="post" action="<%=renderResponse.createActionURL().toString()%>"
		onsubmit="return <portlet:namespace/>_validateWindowName(this);">
			<table>
				<tbody>
				<tr>
					<td><label><fmt:message key="configured.producer.name" /></label></td>
					<td><%=renderRequest.getParameter("configuredProducerName")%>
						<input type="hidden" name="configuredProducerName"
					value="<%=renderRequest.getParameter("configuredProducerName")%>" />
					</td>
				</tr>
				<tr>
					<td><label><fmt:message key="configured.producer.id" /></label></td>
					<td><%=renderRequest.getParameter("configuredProducerId")%>
						<input type="hidden" name="configuredProducerId"
					value="<%=renderRequest.getParameter("configuredProducerId")%>" />
					</td>
				</tr>
				<tr>
					<td><label for="portletHandleId"><fmt:message key="configured.producer.available.portlets" /></label></td>
					<td>
						<select name="portletHandle" id="portletHandleId">
						<%
							Map channelInfo = (Map)renderRequest.getPortletSession(false).
								getAttribute("infoForChannel");
							if(channelInfo != null && !channelInfo.isEmpty()) {
								Iterator iter = channelInfo.entrySet().iterator();
								while(iter.hasNext()){
									Map.Entry entry = (Map.Entry)iter.next();
									String portletHandle = (String)entry.getKey();
									String displayName = (String)entry.getValue();
								%>
									<option value="<%=portletHandle%>"><%=displayName%></option>
								<%
								}
							}
						%>
						</select>
					</td>
				</tr>
				<tr>
					<td><label for="channelNameId"><fmt:message key="channel.name" /></label></td>
					<td><input type="text" class="textboxStyle" name="channelName" id="channelNameId"></td>
				</tr>
				<tr>
					<td>
						<input type="submit" value=<fmt:message key="save" /> class="buttonStyle" />
					</td>
					<td>
						<input type="submit" value=<fmt:message key="cancel" /> class="buttonStyle"
						onclick="document.channelForm.action.value='<%=AdminPortletAction.LIST%>'"
						onKeyPress="document.channelForm.action.value='<%=AdminPortletAction.LIST%>'" />
					</td>
				</tr>
				</tbody>
			</table>
			<input type="hidden" name="action" value="<%=AdminPortletAction.CREATE_CHANNEL%>" />
		</form>
	<%
	////////////////////////////////////////////////////////
	} else if(action == AdminPortletAction.LIST_CHANNELS) {
	////////////////////////////////////////////////////////
	%>
	<b><fmt:message key="channels.delete" /></b>
		<form name="deleteChannelForm" action="<%=renderResponse.createActionURL().toString()%>"
			method="post" onsubmit="return <portlet:namespace/>_checkChannelSelection(this);">
			<table>
				<tbody>
				<tr>
					<td><label for="channelNamesId"><fmt:message key="channel.name" /></label></td>
					<td>
					<select name="channelNames" id="channelNamesId" multiple>
					<%
						List channelNames = (List)renderRequest.getPortletSession(false).getAttribute("channelNames");
						if(channelNames != null && !channelNames.isEmpty()) {
							Iterator iter = channelNames.iterator();
							while(iter.hasNext()) {
								String name = (String)iter.next();
							%>
							<option value="<%=name%>"><%=name%></option>
							<%
							}
						}
						%>
					</select>
					</td>
					<td>
					<fmt:message key="use.cntl.click" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value=<fmt:message key="delete" /> class="buttonStyle" />
					</td>
					<td>
						<input type="submit" value=<fmt:message key="cancel" /> class="buttonStyle"
						onclick="document.deleteChannelForm.action.value='<%=AdminPortletAction.LIST%>'"
						onKeyPress="document.deleteChannelForm.action.value='<%=AdminPortletAction.LIST%>'" />
					</td>
				</tr>
				</tbody>
			</table>
			<input type="hidden" name="action" value="<%=AdminPortletAction.DELETE_CHANNELS%>" />
		</form>
	<%
	}
}
%>