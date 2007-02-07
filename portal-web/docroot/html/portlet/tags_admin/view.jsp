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

<%@ include file="/html/portlet/tags_admin/init.jsp" %>

<script type="text/javascript">
	var <portlet:namespace />TagsAdmin = {
		addEntry: function() {
			Liferay.Service.Tags.TagsEntry.addEntry(
				{
					name: <portlet:namespace />TagsAdmin.addEntryNameInput.val()
				},
				<portlet:namespace />TagsAdmin.getEntries
			);
		},

		addProperties: function(html) {
			<portlet:namespace />TagsAdmin.propertiesTable.append(html);

			<portlet:namespace />TagsAdmin.propertiesTable.find("tr").each(
				function(i, row) {
					_$J("input[@name='<portlet:namespace />deletePropertyButton']", row).click(<portlet:namespace />TagsAdmin.deleteProperty);
				}
			);
		},

		addProperty: function(propertyId, key, value) {
			var html = "";

			html += "<tr><td>";
			html += "<input name=\"<portlet:namespace />propertyId\" type=\"hidden\" value=\"" + propertyId + "\" />\n";
			html += "<input class=\"form-text\" name=\"<portlet:namespace />propertyKey\" type=\"text\" value=\"" + key + "\" />\n";
			html += "<input class=\"form-text\" name=\"<portlet:namespace />propertyValue\" type=\"text\" value=\"" + value + "\" />\n";
			html += "<input class=\"portlet-form-button\" name=\"<portlet:namespace />deletePropertyButton\" type=\"button\" value=\"Delete\" />\n";
			html += "</td></tr>";

			return html;
		},

		deleteEntry: function() {
			Liferay.Service.Tags.TagsEntry.deleteEntry(
				{
					entryId: <portlet:namespace />TagsAdmin.entryId
				},
				<portlet:namespace />TagsAdmin.getEntries
			);

			<portlet:namespace />TagsAdmin.editEntryFields.css("display", "none");
		},

		deleteProperty: function() {
			_$J(this).parents("tr").eq(0).remove();
		},

		displayEntries: function(entries) {
			var html = "<br />";

			_$J.each(
				entries,
				function(i, entry) {
					var hrefJS = "<portlet:namespace />TagsAdmin.editEntry(" + entry.entryId + ",'" + encodeURIComponent(entry.name) + "');";

					html += "<a href=\"javascript: " + hrefJS + "\">" + entry.name + "</a>";

					if ((i + 1) < entries.length) {
						html += ", ";
					}
				}
			);

			if (entries.length == 0) {
				html += "no-tags-found";
			}

			<portlet:namespace />TagsAdmin.searchResultsDiv.html(html);
		},

		displayFilters: function(properties) {
			var propertyKey = "";
			var html = "";

			_$J.each(
				properties,
				function(i, property) {
					propertyKey = property.key;

					html += "<option value=\"" + property.value + "\">" + property.value + "</option>";
				}
			);

			if (propertyKey != "") {
				html = "<select id=\"<portlet:namespace />" + propertyKey + "FilterSel\"><option></option>" + html + "</select>";

				<portlet:namespace />TagsAdmin.searchPropertiesSpan.append("<span style=\"padding: 0px 5px 0px 10px;\">" + propertyKey + "</span>");
				<portlet:namespace />TagsAdmin.searchPropertiesSpan.append(html);

				var filterSel = _$J("#<portlet:namespace />" + propertyKey + "FilterSel");

				filterSel.change(
					function() {
						<portlet:namespace />TagsAdmin.searchFilters[propertyKey] = this.value;

						<portlet:namespace />TagsAdmin.searchEntries();
					}
				);
			}
		},

		editEntry: function(entryId, name) {
			<portlet:namespace />TagsAdmin.entryId = entryId;

			<portlet:namespace />TagsAdmin.editEntryNameInput.val(name);

			<portlet:namespace />TagsAdmin.propertiesTable.find("tr").gt(-1).remove();

			Liferay.Service.Tags.TagsProperty.getProperties(
				{
					entryId: entryId
				},
				<portlet:namespace />TagsAdmin.editProperties
			);

			<portlet:namespace />TagsAdmin.editEntryFields.css("display", "");
		},

		editProperties: function(properties) {
			var html = "";

			_$J.each(
				properties,
				function(i, property) {
					html += <portlet:namespace />TagsAdmin.addProperty(property.propertyId, property.key, property.value);
				}
			);

			if (properties.length == 0) {
				html += <portlet:namespace />TagsAdmin.addProperty("0", "", "");
			}

			<portlet:namespace />TagsAdmin.addProperties(html);
		},

		getEntries: function() {
			Liferay.Service.Tags.TagsEntry.search(
				{
					companyId: "<%= company.getCompanyId() %>",
					name: "%",
					properties: ""
				},
				<portlet:namespace />TagsAdmin.displayEntries
			);

			<portlet:namespace />TagsAdmin.getFilters();
		},

		getFilters: function() {
			var propertyKeys = new Array("Category", "Country");

			if (propertyKeys.length > 0) {
				<portlet:namespace />TagsAdmin.searchPropertiesSpan.html("Filter By: ");
			}

			_$J.each(
				propertyKeys,
				function(i, propertyKey) {
					Liferay.Service.Tags.TagsProperty.getPropertyValues(
						{
							companyId: "<%= company.getCompanyId() %>",
							key: propertyKey
						},
						<portlet:namespace />TagsAdmin.displayFilters
					);
				}
			);
		},

		init: function() {

			// Set variables

			this.addEntryFields = _$J("#<portlet:namespace />addEntryFields");
			this.editEntryFields = _$J("#<portlet:namespace />editEntryFields");
			this.propertiesTable = _$J("#<portlet:namespace />propertiesTable");
			this.searchEntriesFields = _$J("#<portlet:namespace />searchEntriesFields");
			this.searchPropertiesSpan = _$J("#<portlet:namespace />searchPropertiesSpan");
			this.searchResultsDiv = _$J("#<portlet:namespace />searchResultsDiv");

			this.form = _$J("form[@name='<portlet:namespace />fm']");

			this.addEntryNameInput = _$J("input[@name='<portlet:namespace />addEntryName']", this.form);
			this.editEntryNameInput = _$J("input[@name='<portlet:namespace />editEntryName']", this.form);
			this.keywordsInput = _$J("input[@name='<portlet:namespace />keywords']", this.form);

			this.addEntryButton = _$J("#<portlet:namespace />addEntryButton");
			this.addPropertyButton = _$J("#<portlet:namespace />addPropertyButton");
			this.cancelEditEntryButton = _$J("#<portlet:namespace />cancelEditEntryButton");
			this.deleteEntryButton = _$J("#<portlet:namespace />deleteEntryButton");
			this.updateEntryButton = _$J("#<portlet:namespace />updateEntryButton");

			this.searchFilters = {};

			// Show all entries

			<portlet:namespace />TagsAdmin.getEntries();

			// Divs

			<portlet:namespace />TagsAdmin.editEntryFields.css("display", "none");

			// Form

			<portlet:namespace />TagsAdmin.form.submit(
				function() {
					return false;
				}
			);

			// Inputs

			<portlet:namespace />TagsAdmin.addEntryNameInput.bind(
				"keypress", this,
				function(event) {
					if (event.keyCode == 13) {
						event.data.addEntry();
					}
				}
			);

			<portlet:namespace />TagsAdmin.keywordsInput.bind(
				"keyup", this,
				function(event) {
					event.data.searchEntries();
				}
			);

			<portlet:namespace />TagsAdmin.keywordsInput.focus();

			// Buttons

			<portlet:namespace />TagsAdmin.addEntryButton.click(
				<portlet:namespace />TagsAdmin.addEntry
			);

			<portlet:namespace />TagsAdmin.addPropertyButton.click(
				function() {
					var html = <portlet:namespace />TagsAdmin.addProperty("0", "", "");

					<portlet:namespace />TagsAdmin.addProperties(html);
				}
			);

			<portlet:namespace />TagsAdmin.cancelEditEntryButton.click(
				function() {
					<portlet:namespace />TagsAdmin.editEntryFields.css("display", "none");
				}
			);

			<portlet:namespace />TagsAdmin.deleteEntryButton.click(
				<portlet:namespace />TagsAdmin.deleteEntry
			);

			<portlet:namespace />TagsAdmin.updateEntryButton.click(
				<portlet:namespace />TagsAdmin.updateEntry
			);
		},

		searchEntries: function() {
			var keywords = "%" + <portlet:namespace />TagsAdmin.keywordsInput.val() + "%";

			var properties = "";

			_$J.each(
				<portlet:namespace />TagsAdmin.searchFilters,
				function(propertyKey, propertyValue) {
					if (propertyValue != "") {
						if (properties != "") {
							properties += ",";
						}

						properties += propertyKey + "|" + propertyValue;
					}
				}
			);

			Liferay.Service.Tags.TagsEntry.search(
				{
					companyId: "<%= company.getCompanyId() %>",
					name: keywords,
					properties: properties
				},
				<portlet:namespace />TagsAdmin.displayEntries
			);
		},

		updateEntry: function() {
			var properties = "";

			var rows = <portlet:namespace />TagsAdmin.propertiesTable.find("tr");

			rows.each(
				function(i, row) {
					var propertyId = _$J("input[@name='<portlet:namespace />propertyId']", row).val();
					var propertyKey = _$J("input[@name='<portlet:namespace />propertyKey']", row).val();
					var propertyValue = _$J("input[@name='<portlet:namespace />propertyValue']", row).val();

					properties += propertyId + "|" + propertyKey + "|" + propertyValue;

					if ((i + 1) < rows.length) {
						properties += ",";
					}
				}
			);

			Liferay.Service.Tags.TagsEntry.updateEntry(
				{
					entryId: <portlet:namespace />TagsAdmin.entryId,
					name: <portlet:namespace />TagsAdmin.editEntryNameInput.val(),
					properties: properties
				},
				<portlet:namespace />TagsAdmin.getEntries
			);

			<portlet:namespace />TagsAdmin.editEntryFields.css("display", "none");
		}
	};

	_$J(
		function() {
			<portlet:namespace />TagsAdmin.init();
		}
	);
</script>

<form name="<portlet:namespace />fm">

<fieldset id="<portlet:namespace />searchEntriesFields">
	<legend>Search Tag</legend>

	<input class="form-text" name="<portlet:namespace />keywords" type="text" />

	<span id="<portlet:namespace />searchPropertiesSpan" style="padding-left: 10px;"></span>

	<div id="<portlet:namespace />searchResultsDiv" /></div>
</fieldset>

<fieldset id="<portlet:namespace />editEntryFields">
	<legend>Edit Tag</legend>

	<input class="form-text" name="<portlet:namespace />editEntryName" type="text" value="" />

	<input class="portlet-form-button" id="<portlet:namespace />updateEntryButton" type="button" value="Save" />

	<%--<input class="portlet-form-button" id="<portlet:namespace />updateEntryButton" type="button" value="Copy" />--%>

	<input class="portlet-form-button" id="<portlet:namespace />deleteEntryButton" type="button" value="Delete" />

	<input class="portlet-form-button" id="<portlet:namespace />addPropertyButton" type="button" value="Add Property" />

	<input class="portlet-form-button" id="<portlet:namespace />cancelEditEntryButton" type="button" value="Cancel" />

	<br /><br />

	Properties

	<table border="0" cellpadding="0" cellspacing="0" id="<portlet:namespace />propertiesTable"></table>
</fieldset>

<fieldset id="<portlet:namespace />addEntryFields">
	<legend>Add Tag</legend>

	<input class="form-text" name="<portlet:namespace />addEntryName" type="text" />

	<input class="portlet-form-button" id="<portlet:namespace />addEntryButton" type="button" value="Save" />
</fieldset>

</form>