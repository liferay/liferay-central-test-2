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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.util.diff.DiffResult" %>
<%@ page import="com.liferay.util.diff.DiffUtil" %>

<style>
	.diff-table {
		width: 100%;
		border-spacing: 5pt;
		border-collapse: separate;
	}
	.diff-context {
		background: #EEEEEE;
	}
	.diff-deletedline, #diff-results del {
		background: #FFE6E6;
	}
	.diff-addedline, #diff-results ins {
		background: #E6FFE6;
	}
</style>

<%
String sourceName = (String)request.getAttribute("liferay-ui:diff:sourceName");
String targetName = (String)request.getAttribute("liferay-ui:diff:targetName");
List[] diffResults = (List[])request.getAttribute("liferay-ui:diff:diffResults");

Iterator sourceItr = diffResults[0].iterator();
Iterator targetItr = diffResults[1].iterator();

while (sourceItr.hasNext()) {
	DiffResult sourceResult = (DiffResult)sourceItr.next();
	DiffResult targetResult = (DiffResult)targetItr.next();

	%>
	<table id="diff-results" class="taglib-search-iterator">
		<tbody>
			<tr class="portlet-section-header">
				<th class="col-1"> <%= sourceName %> <liferay-ui:message key="at-line" /> <%= sourceResult.getLineNumber() %> </th>
				<th class="col-1"> <%= targetName %> <liferay-ui:message key="at-line" /> <%= targetResult.getLineNumber() %> </th>
			</tr>
			<tr>
				<td width="50%" valign="top">
					<table class="diff-table">
					<%
					Iterator itr2 = sourceResult.getChangedLines().iterator();

					while (itr2.hasNext()) {
						String changedLine = (String)itr2.next();

						String td = _processColumn(changedLine);
						%>
						<tr valign="top">
							<%= td %>
						</tr>
						<%
					}
					%>
					</table>
				</td>
				<td width="50%" valign="top">
					<table class="diff-table">
					<%
					itr2 = targetResult.getChangedLines().iterator();

					while (itr2.hasNext()) {
						String changedLine = (String)itr2.next();

						String td = _processColumn(changedLine);
						%>
						<tr>
							<%= td %>
						</tr>
						<%
					}
					%>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
	<%
}
%>

<%!
    private String _processColumn(String changedLine) {

    	changedLine = changedLine.replaceAll(" ", "&nbsp;");
    	changedLine = changedLine.replaceAll("\t", "&nbsp;&nbsp;&nbsp;");

    	String column = "<td>" + changedLine + "</td>";

    	if (changedLine.equals(StringPool.BLANK)) {
	    	return "<td>&nbsp;</td>";
    	}

    	if (changedLine.equals(DiffUtil.CONTEXT_LINE)) {
			return "<td class=\"diff-context\">&nbsp;</td>";
		}

		if (changedLine.equals(DiffUtil.OPEN_INS + DiffUtil.CLOSE_INS)) {
			return "<td class=\"diff-addedline\">&nbsp;</td>";
		}

		if (changedLine.equals(DiffUtil.OPEN_DEL + DiffUtil.CLOSE_DEL)) {
			return "<td class=\"diff-deletedline\">&nbsp;</td>";
		}

       return column;
    }
%>