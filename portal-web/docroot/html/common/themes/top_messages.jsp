<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

<%@ include file="/html/common/init.jsp" %>

<c:if test="<%= MessagingUtil.isJabberEnabled() && themeDisplay.isSignedIn() %>">
	<script type="text/javascript">
		var chatHTML = Cookie.read("<%= request.getRemoteUser() %>_chats");

		if (chatHTML != null)  {
			document.write(decodeURIComponent(chatHTML));
		}
	</script>
</c:if>

<c:if test="<%= ShutdownUtil.isInProcess() %>">
	<table border="0" cellpadding="1" cellspacing="0" width="100%">
	<tr>
		<td bgcolor="<%= colorScheme.getPortletMsgError() %>">
			<table border="0" cellpadding="8" cellspacing="0" width="100%">
			<tr>
				<td bgcolor="<%= colorScheme.getLayoutBg() %>">
					<font class="bg" size="2"><span class="bg-neg-alert">

					<%= LanguageUtil.get(pageContext, "maintenance-alert") %> &nbsp;&nbsp;&nbsp;<%= DateFormat.getTimeInstance(DateFormat.SHORT, locale).format(Time.getDate(new GregorianCalendar(timeZone))) %> <%= timeZone.getDisplayName(false, TimeZone.SHORT, locale) %><br><br>

					<%= LanguageUtil.format(pageContext, "the-portal-will-shutdown-for-maintenance-in-x-minutes", Long.toString(ShutdownUtil.getInProcess() / Time.MINUTE), false) %>

					</span></font>
				</td>
			</tr>

			<c:if test="<%= Validator.isNotNull(ShutdownUtil.getMessage()) %>">
				<tr>
					<td bgcolor="<%= colorScheme.getLayoutBg() %>">
						<font class="bg" size="2">
						<%= ShutdownUtil.getMessage() %>
						</font>
					</td>
				</tr>
			</c:if>

			</table>
		</td>
	</tr>
	</table>
</c:if>






<style type="text/css">
	#alert-table {
		margin:auto;
		margin-top:100px;
		background:white;
		width:300px;
		border:1px solid black;
	}

	#warning {
		filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='<%= themeDisplay.getPathThemeImage() %>/common/grey.png', sizingMethod='scale');
	}
</style>

<script type="text/javascript">
	function killAlert(action) {
		document.getElementById("warning").style.display = "none";

		function() {action};
	}

function fireMessageBox(modal, message, okAction, cancelAction) {
	var background = document.createElement("div");

	background.setAttribute("id", "warning");
	background.style.width = "100%";
	background.style.position = "absolute";
	background.style.top = "0";
	background.style.left = "0";
	background.style.zIndex = "99";

	var height1 = document.getElementById("layout-outer-side-decoration").offsetHeight;
	var height2 = document.body.clientHeight;

	if (height1 > height2) {
		background.style.height=height1;
	}
	else {
		background.style.height=height2;
	}

	if (modal == true) {
		if (navigator.appName != "Microsoft Internet Explorer") {
			background.style.background = "url('<%= themeDisplay.getPathThemeImage() %>/common/grey.png')";
		}

		if (document.getElementsByTagName("body")) {
				var body    = document.getElementsByTagName("body")[0];
				mytable     = document.createElement("table");
				mytable.setAttribute("id","alert-table");
		        mytablebody = document.createElement("tbody");
		
		// Message Row Start
		
		        mycurrent_row  = document.createElement("tr");
		        mycurrent_cell = document.createElement("td");
		        mycurrent_cell.setAttribute("align","center");
		        mycurrent_cell.colSpan="2";
		        
		        mycurrent_cell.innerHTML=message;
		        mycurrent_row.appendChild(mycurrent_cell);
		        
		        mytablebody.appendChild(mycurrent_row);
		// Message Row End
		
		// Button Row Start        
		        mycurrent_row  = document.createElement("tr");
		        mycurrent_cell = document.createElement("td");
		        mycurrent_cell.width="50%";

				var ok = "<input type='button' value='<%= LanguageUtil.get(pageContext, "ok") %>' onClick='killAlert(\" "+okAction+" \");' /> ";
				
				
				mycurrent_cell.innerHTML=ok;
				
				mycurrent_row.appendChild(mycurrent_cell);
				
				var mycurrent_cell2 = document.createElement("td");
				
				var cancel = "<input type='button' value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick='killAlert(\" "+cancelAction+" \");' /> ";
				
				mycurrent_cell2.innerHTML=cancel;
				
				mycurrent_row.setAttribute('align','center');
		        mycurrent_row.appendChild(mycurrent_cell2);
		
		        mytablebody.appendChild(mycurrent_row);
		
		// Button Row End        
		        
		        mytable.appendChild(mytablebody);
		        mytable.setAttribute("border","0");
					
		        background.appendChild(mytable);
				
				body.appendChild(background);
				
			}
		}

if (modal == false) {
	if (document.getElementsByTagName("body")) {
				var body    = document.getElementsByTagName("body")[0];
				mytable     = document.createElement("table");
				mytable.setAttribute("id","alert-table");
		        mytablebody = document.createElement("tbody");
		
		// Message Row Start
		
		        mycurrent_row  = document.createElement("tr");
		        mycurrent_cell = document.createElement("td");
		        mycurrent_cell.setAttribute("align","center");
		        mycurrent_cell.colSpan="2";
		        
		        mycurrent_cell.innerHTML=message;
		        mycurrent_row.appendChild(mycurrent_cell);
		        
		        mytablebody.appendChild(mycurrent_row);
		// Message Row End
		
		// Button Row Start        
		        mycurrent_row  = document.createElement("tr");
		        mycurrent_cell = document.createElement("td");
		        mycurrent_cell.width="50%";

				var ok = "<input type='button' value='<%= LanguageUtil.get(pageContext, "ok") %>' onClick='killAlert(\" "+okAction+" \");' /> ";
				
				
				mycurrent_cell.innerHTML=ok;
				
				mycurrent_row.appendChild(mycurrent_cell);
				
				var mycurrent_cell2 = document.createElement("td");
				
				var cancel = "<input type='button' value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick='killAlert(\" "+cancelAction+" \");' /> ";
				
				mycurrent_cell2.innerHTML=cancel;
				
				mycurrent_row.setAttribute('align','center');
		        mycurrent_row.appendChild(mycurrent_cell2);
		
		        mytablebody.appendChild(mycurrent_row);
		
		// Button Row End        
		        
		        mytable.appendChild(mytablebody);
		        mytable.setAttribute("border","0");
					
		        background.appendChild(mytable);
				
				body.appendChild(background);
				
			}
		}
	}

function firePopup(modal, message, okAction) {
	
var background = document.createElement("div");
background.setAttribute("id","warning");
background.style.width="100%";
background.style.position="absolute";
background.style.top="0";
background.style.left="0";
background.style.zIndex="99";
	
var height1 = document.getElementById('layout-outer-side-decoration').offsetHeight;
var height2 = document.body.clientHeight;

if (height1 > height2) {
	background.style.height=height1;
}
else {
	background.style.height=height2;
}

if (modal == true) {
	
	if (navigator.appName != "Microsoft Internet Explorer") {
			background.style.background="url('/html/common/themes/images/grey.png')";
	}
	
		if (document.getElementsByTagName("body")) {
				var body    = document.getElementsByTagName("body")[0];
				mytable     = document.createElement("table");
				mytable.setAttribute("id","alert-table");
		        mytablebody = document.createElement("tbody");
		
		// Message Row Start
		
		        mycurrent_row  = document.createElement("tr");
		        mycurrent_cell = document.createElement("td");
		        mycurrent_cell.setAttribute("align","center");
		        
		        mycurrent_cell.innerHTML=message;
		        mycurrent_row.appendChild(mycurrent_cell);
		        
		        mytablebody.appendChild(mycurrent_row);
		// Message Row End
		
		// Button Row Start        
		        mycurrent_row  = document.createElement("tr");
		        mycurrent_cell = document.createElement("td");
		        
		        var ok = document.createElement('input');
				ok.setAttribute('type','button');
				ok.setAttribute('name','Ok');
				ok.setAttribute('value','Ok');
				var ok = "<input type='button' value='<%= LanguageUtil.get(pageContext, "ok") %>' onClick='killAlert(\" "+okAction+" \");' /> ";
				
				mycurrent_cell.innerHTML=ok;
				
				mycurrent_row.appendChild(mycurrent_cell);
				
				mycurrent_row.setAttribute('align','center');
		        
		        mytablebody.appendChild(mycurrent_row);
		
		// Button Row End        
		        
		        mytable.appendChild(mytablebody);
		        background.appendChild(mytable);
		        
		        mytable.setAttribute("border","0");
				
				body.appendChild(background);
				
			}
		}

if (modal == false) {
	
	var killAlert = "document.getElementById('warning').style.display='none';";
	
		if (document.getElementsByTagName("body")) {
				var body    = document.getElementsByTagName("body")[0];
				mytable     = document.createElement("table");
				mytable.setAttribute("id","alert-table");
		        mytablebody = document.createElement("tbody");
		
		// Message Row Start
		
		        mycurrent_row  = document.createElement("tr");
		        mycurrent_cell = document.createElement("td");
		        mycurrent_cell.setAttribute("colspan","2");
		        mycurrent_cell.setAttribute("align","center");
		        
		        mycurrent_cell.innerHTML=message;
		        mycurrent_row.appendChild(mycurrent_cell);
		        
		        mytablebody.appendChild(mycurrent_row);
		// Message Row End
		
		// Button Row Start        
		        mycurrent_row  = document.createElement("tr");
		        mycurrent_cell = document.createElement("td");
		        
		        var ok = document.createElement('input');
				ok.setAttribute('type','button');
				ok.setAttribute('name','Ok');
				ok.setAttribute('value','Ok');
				var ok = "<input type='button' value='<%= LanguageUtil.get(pageContext, "ok") %>' onClick='killAlert(\" "+okAction+" \");' /> ";
				
				mycurrent_cell.innerHTML=ok;
				
				mycurrent_row.appendChild(mycurrent_cell);
				
				mycurrent_row.setAttribute('align','center');
		        
		        mytablebody.appendChild(mycurrent_row);
		
		// Button Row End   
		        
		        mytable.appendChild(mytablebody);
		        background.appendChild(mytable);
		        
		        mytable.setAttribute("border","0");	
				
				body.appendChild(background);
				
			}
		}
	}
</script>