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

<%@ include file="/html/portlet/webform/init.jsp" %>

<form action='<portlet:actionURL><portlet:param name="struts_action" value="/webform/send"/></portlet:actionURL>'
	  method='post'>

	<input type="hidden" name="redirect" value='<portlet:renderURL><portlet:param name="struts_action" value="/webform/view"/></portlet:renderURL>'>

<%
String title = prefs.getValue("title","");
String description = prefs.getValue("description", "");
%>

<h3><%= title %></h3>
<p><%= description %></p>

<liferay-ui:error key="pleaseFillAllFields" message="please-fill-all-fields"/>
<liferay-ui:error key="messageNotSent" message="the-message-could-not-be-sent"/>
<liferay-ui:success key="messageSent" message="the-message-was-sent-successfuly"/>

<%
for (int i = 1; i <= 10; i++) {
    String fieldName = renderResponse.getNamespace() + "field" + i;
    String fieldLabel = prefs.getValue("fieldLabel" + i, "");
    if (fieldLabel.length() == 0) {
       continue;
    }
    String type = prefs.getValue("fieldType" + i,"text");
%>
    <label for="<%= fieldName %>"><%= fieldLabel %></label> <br/>
<%
    if (type.equals("text")) {
%>
    <input name="<%= fieldName %>" type="text" class="form-text" size="80"/>
<%
    } else if (type.equals("textarea")) {
%>
    <textarea name="<%= fieldName %>" rows="15" cols="90" class="form-text"></textarea>
<%
    } else if (type.equals("options")) {
       String[] options = WebformUtil.split(prefs.getValue("fieldOptions" + i,"unknown"));
%>
      <select name="<%= fieldName %>" class="form-text">
      <%
        for (int j = 0; j < options.length; j++) {
      %>
        <option value="<%=options[j]%>"><%=options[j]%></option>
      <%
       }
      %>
      </select>
      <%
    }
%>
<br/>
<br/>
<%
}
%>

<input type="submit" value="Enviar"/>

</form>