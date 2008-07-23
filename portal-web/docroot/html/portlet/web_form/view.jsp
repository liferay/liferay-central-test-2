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

<%@ include file="/html/portlet/web_form/init.jsp" %>

<%
String title = prefs.getValue("title", StringPool.BLANK);
String description = prefs.getValue("description", StringPool.BLANK);
boolean requireCaptcha = GetterUtil.getBoolean(prefs.getValue("requireCaptcha", StringPool.BLANK));
%>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/web_form/view" /></portlet:actionURL>" class="uni-form" method="post" name="<portlet:namespace />fm" id="<portlet:namespace />fm">
<input type="hidden" name="<portlet:namespace/>redirect" value="<%= currentURL %>" />

<fieldset class="block-labels">
	<legend><%= HtmlUtil.escape(title) %></legend>

	<p class="description"><%= HtmlUtil.escape(description) %></p>

	<liferay-ui:success key="success" message="the-form-information-was-sent-successfully" />

	<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />
	<liferay-ui:error key="error" message="an-error-occurred-while-sending-the-form-information" />

	<%
	int i = 1;

	String fieldName = "field" + i;
	String fieldLabel = prefs.getValue("fieldLabel" + i, StringPool.BLANK);
	boolean fieldOptional = PrefsParamUtil.getBoolean(prefs, request, "fieldOptional" + i, false);
	String fieldValue = ParamUtil.getString(request, fieldName);

	while ((i == 1) || Validator.isNotNull(fieldLabel)) {
		String fieldType = prefs.getValue("fieldType" + i, "text");
		String fieldOptions = prefs.getValue("fieldOptions" + i, "unknown");
		String fieldValidationScript = prefs.getValue("fieldValidationScript" + i, StringPool.BLANK);
		String fieldValidationErrorMessage = prefs.getValue("fieldValidationErrorMessage" + i, "please-complete-field");

	%>
    
        <liferay-ui:error key='<%= "error" + fieldLabel %>' message="<%= fieldValidationErrorMessage %>" />
        
        <c:if test='<%= Validator.isNotNull(fieldValidationScript) %>'>
            <div id="<portlet:namespace/>validationError<%= fieldLabel %>" style="display: none">
                <span class="portlet-msg-error"><%= fieldValidationErrorMessage %></span>
            </div>
        </c:if>
        <c:if test="<%= !fieldOptional %>">
            <div id="<portlet:namespace/>fieldOptionalError<%= fieldLabel %>" style="display: none">
                <span class="portlet-msg-error"><liferay-ui:message key="please-complete-field" /></span>
            </div>
        </c:if>
        
        <c:choose>
			<c:when test='<%= fieldType.equals("paragraph") %>'>
				<p class="lfr-webform" id="<portlet:namespace /><%= fieldName %>" ><%= fieldOptions %></p>
			</c:when>
			<c:when test='<%= fieldType.equals("text") %>'>
				<div class="ctrl-holder">
					<label class='<%= fieldOptional ? "optional" : "" %>' for="<portlet:namespace /><%= fieldName %>"><%= HtmlUtil.escape(fieldLabel) %></label>

					<input class='<%= fieldOptional ? "optional" : "" %>' id="<portlet:namespace /><%= fieldName %>" name="<portlet:namespace /><%= fieldName %>" type="text" value="<%= HtmlUtil.escape(fieldValue) %>" />
				</div>
			</c:when>
			<c:when test='<%= fieldType.equals("textarea") %>'>
				<div class="ctrl-holder">
					<label class='<%= fieldOptional ? "optional" : "" %>' for="<portlet:namespace /><%= fieldName %>"><%= HtmlUtil.escape(fieldLabel) %></label>

					<textarea class='<%= fieldOptional ? "optional" : "" %>' id="<portlet:namespace /><%= fieldName %>" name="<portlet:namespace /><%= fieldName %>" wrap="soft"><%= HtmlUtil.escape(fieldValue) %></textarea>
				</div>
			</c:when>
			<c:when test='<%= fieldType.equals("checkbox") %>'>
				<div class="ctrl-holder <%= fieldOptional ? "optional" : "" %>">
					<label class='<%= fieldOptional ? "optional" : "" %>' for="<portlet:namespace /><%= fieldName %>"><input <%= Validator.isNotNull(fieldValue) ? "checked" : "" %> id="<portlet:namespace /><%= fieldName %>" name="<portlet:namespace /><%= fieldName %>" type="checkbox" /> <%= HtmlUtil.escape(fieldLabel) %></label>
				</div>
			</c:when>
			<c:when test='<%= fieldType.equals("radio") %>'>
				<div class="ctrl-holder <%= fieldOptional ? "optional" : "" %>">
					<label class='<%= fieldOptional ? "optional" : "" %>' for="<portlet:namespace /><%= fieldName %>"><%= HtmlUtil.escape(fieldLabel) %></label>

					<%
					String[] options = WebFormUtil.split(fieldOptions);

					for (int j = 0; j < options.length; j++) {
						String optionValue = options[j];
					%>

						<label><input type="radio" name="<portlet:namespace /><%= fieldName %>" <%= fieldValue.equals(optionValue) ? "checked=\"true\"" : "" %> value="<%= HtmlUtil.escape(optionValue) %>" /> <%= HtmlUtil.escape(optionValue) %></label>

					<%
					}
					%>

				</div>
			</c:when>
			<c:when test='<%= fieldType.equals("options") %>'>
				<div class="ctrl-holder <%= fieldOptional ? "optional" : "" %>">
					<label class='<%= fieldOptional ? "optional" : "" %>' for="<portlet:namespace /><%= fieldName %>"><%= HtmlUtil.escape(fieldLabel) %></label>

					<%
					String[] options = WebFormUtil.split(fieldOptions);
					%>

					<select name="<portlet:namespace /><%= fieldName %>" id="<portlet:namespace /><%= fieldName %>">

						<%
						for (int j = 0; j < options.length; j++) {
							String optionValue = options[j];
						%>

							<option <%= fieldValue.equals(optionValue) ? "selected" : "" %> value="<%= HtmlUtil.escape(optionValue) %>"><%= HtmlUtil.escape(optionValue) %></option>

						<%
						}
						%>

					</select>
				</div>
			</c:when>
		</c:choose>

    <%
		i++;

		fieldName = "field" + i;
		fieldLabel = prefs.getValue("fieldLabel" + i, "");
		fieldOptional = PrefsParamUtil.getBoolean(prefs, request, "fieldOptional" + i, false);
		fieldValue = ParamUtil.getString(request, fieldName);
    }
	%>

	<c:if test="<%= requireCaptcha %>">
		<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" var="captchaURL">
			<portlet:param name="struts_action" value="/web_form/captcha" />
		</portlet:actionURL>

		<liferay-ui:captcha url="<%= captchaURL %>" />
	</c:if>

	<div class="button-holder">
		<input type="submit" value="<liferay-ui:message key="send" />" />
	</div>
</fieldset>

</form>

<script type="text/javascript">
    jQuery(document).ready(function() {
        jQuery('#<portlet:namespace />fm').submit(function() {

            var fieldValidationFunctions = new Array();
            var fieldLabels = new Array();
            var fieldValidationErrorMessages = new Array();
            var fieldValues = new Array();
            var fieldOptional = new Array();

           <%
            int fieldIndex = 1;
            fieldLabel = prefs.getValue("fieldLabel" + fieldIndex, StringPool.BLANK);

            while ((fieldIndex == 1) || Validator.isNotNull(fieldLabel)) {
                String fieldValidationScript = prefs.getValue("fieldValidationScript" + fieldIndex, StringPool.BLANK);
                String fieldValidationErrorMessage = prefs.getValue("fieldValidationErrorMessage" + fieldIndex, "please-complete-field");
                fieldOptional = PrefsParamUtil.getBoolean(prefs, request, "fieldOptional" + fieldIndex, false);
                
            %>
                fieldLabels[<%= fieldIndex %>] = "<%= HtmlUtil.escape(fieldLabel) %>";
                fieldValidationErrorMessages[<%= fieldIndex %>] = "<%= fieldValidationErrorMessage %>";
                function fieldValidationFunction<%= fieldIndex %>(thisFieldValue, fieldValues) {
                    <c:choose>
                        <c:when test='<%= Validator.isNotNull(fieldValidationScript) %>'>
                        	<%= fieldValidationScript %>
                        </c:when>
                        <c:otherwise>
                            return true;
                        </c:otherwise>
                    </c:choose>
                };
                fieldValues[<%= fieldIndex %>] = jQuery("#<portlet:namespace />" + "field<%= fieldIndex %>")[0].value;
                fieldValidationFunctions[<%= fieldIndex %>] = fieldValidationFunction<%= fieldIndex %>;
                fieldOptional[<%= fieldIndex %>] = <%= fieldOptional %>;
            <%
                fieldIndex++;
                fieldLabel = prefs.getValue("fieldLabel" + fieldIndex, "");
            }
            %>

            var validationErrors = false;

            for (var i = 1; i < fieldValidationFunctions.length; i++) {
                var thisFieldValue = fieldValues[i];
                if (!fieldOptional[i] && thisFieldValue.match(/^\s*$/)) {
                    validationErrors = true;
                    jQuery(".portlet-msg-success").slideUp();
                    jQuery("#<portlet:namespace />fieldOptionalError" + fieldLabels[i]).slideDown();
               } else if (!fieldValidationFunctions[i](thisFieldValue, fieldValues)) {
                    validationErrors = true;
                    jQuery(".portlet-msg-success").slideUp();
                    jQuery("#<portlet:namespace />validationError" + fieldLabels[i]).slideDown();
                    jQuery("#<portlet:namespace />fieldOptionalError" + fieldLabels[i]).slideUp();
                } else {
                    jQuery("#<portlet:namespace />validationError" + fieldLabels[i]).slideUp();
                    jQuery("#<portlet:namespace />fieldOptionalError" + fieldLabels[i]).slideUp();
                }
            }

            if (validationErrors) {
                return false;
            }
        });
    });
</script>