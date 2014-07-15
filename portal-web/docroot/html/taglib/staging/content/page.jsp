<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/html/taglib/staging/content/init.jsp" %>

<c:if test="<%= !dataSiteLevelPortlets.isEmpty() %>">
	<aui:fieldset cssClass="options-group" label="content">
		<ul class="lfr-tree list-unstyled">
			<li class="tree-item">
				<aui:input name="<%= PortletDataHandlerKeys.PORTLET_DATA %>" type="hidden" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA, true) %>" />
				<aui:input checked="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL, true) %>" helpMessage='<%= isExport ? "all-content-export-help" : "all-content-publish-help" %>' id="allContent" label="all-content" name="<%= PortletDataHandlerKeys.PORTLET_DATA_ALL %>" type="radio" value="<%= true %>" />

				<c:if test="<%= !isExport && group.isStagingGroup() && localPublishing %>">
					<div class="hide" id="<portlet:namespace />globalContent">
						<aui:fieldset cssClass="portlet-data-section" label="all-content">
							<aui:input label="delete-portlet-data-before-importing" name="<%= PortletDataHandlerKeys.DELETE_PORTLET_DATA %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.DELETE_PORTLET_DATA, true) %>" />

							<ul class="list-unstyled" id="<portlet:namespace />showDeleteContentWarning">
								<li>
									<div class="alert alert-block">
										<liferay-ui:message key="delete-content-before-importing-warning" />

										<liferay-ui:message key="delete-content-before-importing-suggestion" />
									</div>
								</li>
							</ul>

							<aui:script>
								Liferay.Util.toggleBoxes('<portlet:namespace /><%= PortletDataHandlerKeys.DELETE_PORTLET_DATA %>', '<portlet:namespace />showDeleteContentWarning');
							</aui:script>
						</aui:fieldset>
					</div>

					<ul class="hide" id="<portlet:namespace />showChangeGlobalContent">
						<li>
							<span class="selected-labels" id="<portlet:namespace />selectedGlobalContent"></span>

							<aui:a cssClass="modify-link" href="javascript:;" id="globalContentLink" label="change" method="get" />
						</li>
					</ul>
				</c:if>

				<aui:input name="<%= PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT %>" type="hidden" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT, true) %>" />
				<aui:input checked="<%= !MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL, true) %>" helpMessage='<%= isExport ? "choose-content-export-help" : "choose-content-publish-help" %>' id="chooseContent" label="choose-content" name="<%= PortletDataHandlerKeys.PORTLET_DATA_ALL %>" type="radio" value="<%= false %>" />

				<ul class="hide select-options" id="<portlet:namespace />selectContents">
					<li>
						<div class="hide" id="<portlet:namespace />range">
							<ul class="lfr-tree list-unstyled">
								<li class="tree-item">
									<aui:fieldset cssClass="portlet-data-section" label="date-range">

										<%
										String selectedRange = MapUtil.getString(parameterMap, "range");
										%>

										<aui:input checked="<%= selectedRange.equals(ExportImportDateUtil.RANGE_ALL) %>" id="rangeAll" label="all" name="range" type="radio" value="<%= ExportImportDateUtil.RANGE_ALL %>" />

										<c:if test="<%= !isExport %>">
											<aui:input checked="<%= selectedRange.equals(ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE) %>"  id="rangeLastPublish" label="from-last-publish-date" name="range" type="radio" value="<%= ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE %>" />
										</c:if>

										<aui:input checked="<%= selectedRange.equals(ExportImportDateUtil.RANGE_DATE_RANGE) %>" helpMessage="export-date-range-help" id="rangeDateRange" label="date-range" name="range" type="radio" value="<%= ExportImportDateUtil.RANGE_DATE_RANGE %>" />

										<%
										Calendar endCalendar = CalendarFactoryUtil.getCalendar(timeZone, locale);

										if (endDate != null) {
											endCalendar.setTime(endDate);
										}

										Calendar startCalendar = CalendarFactoryUtil.getCalendar(timeZone, locale);

										if (startDate != null) {
											startCalendar.setTime(startDate);
										}
										else {
											startCalendar.add(Calendar.DATE, -1);
										}
										%>

										<ul class="date-range-options hide list-unstyled" id="<portlet:namespace />startEndDate">
											<li>
												<aui:fieldset label="start-date">
													<liferay-ui:input-date
														dayParam="startDateDay"
														dayValue="<%= startCalendar.get(Calendar.DATE) %>"
														disabled="<%= false %>"
														firstDayOfWeek="<%= startCalendar.getFirstDayOfWeek() - 1 %>"
														monthParam="startDateMonth"
														monthValue="<%= startCalendar.get(Calendar.MONTH) %>"
														name="startDate"
														yearParam="startDateYear"
														yearValue="<%= startCalendar.get(Calendar.YEAR) %>"
													/>

													&nbsp;

													<liferay-ui:input-time
														amPmParam='<%= "startDateAmPm" %>'
														amPmValue="<%= startCalendar.get(Calendar.AM_PM) %>"
														dateParam="startDateTime"
														dateValue="<%= startCalendar.getTime() %>"
														disabled="<%= false %>"
														hourParam='<%= "startDateHour" %>'
														hourValue="<%= startCalendar.get(Calendar.HOUR) %>"
														minuteParam='<%= "startDateMinute" %>'
														minuteValue="<%= startCalendar.get(Calendar.MINUTE) %>"
														name="startTime"
													/>
												</aui:fieldset>
											</li>

											<li>
												<aui:fieldset label="end-date">
													<liferay-ui:input-date
														dayParam="endDateDay"
														dayValue="<%= endCalendar.get(Calendar.DATE) %>"
														disabled="<%= false %>"
														firstDayOfWeek="<%= endCalendar.getFirstDayOfWeek() - 1 %>"
														monthParam="endDateMonth"
														monthValue="<%= endCalendar.get(Calendar.MONTH) %>"
														name="endDate"
														yearParam="endDateYear"
														yearValue="<%= endCalendar.get(Calendar.YEAR) %>"
													/>

													&nbsp;

													<liferay-ui:input-time
														amPmParam='<%= "endDateAmPm" %>'
														amPmValue="<%= endCalendar.get(Calendar.AM_PM) %>"
														dateParam="startDateTime"
														dateValue="<%= endCalendar.getTime() %>"
														disabled="<%= false %>"
														hourParam='<%= "endDateHour" %>'
														hourValue="<%= endCalendar.get(Calendar.HOUR) %>"
														minuteParam='<%= "endDateMinute" %>'
														minuteValue="<%= endCalendar.get(Calendar.MINUTE) %>"
														name="endTime"
													/>
												</aui:fieldset>
											</li>
										</ul>

										<aui:input checked="<%= selectedRange.equals(ExportImportDateUtil.RANGE_LAST) %>" id="rangeLast" label='<%= LanguageUtil.get(request, "last") + StringPool.TRIPLE_PERIOD %>' name="range" type="radio" value="<%= ExportImportDateUtil.RANGE_LAST %>" />

										<ul class="hide list-unstyled" id="<portlet:namespace />rangeLastInputs">
											<li>
												<aui:select cssClass="relative-range" label="" name="last">

													<%
													String last = MapUtil.getString(parameterMap, "last");
													%>

													<aui:option label='<%= LanguageUtil.format(request, "x-hours", "12", false) %>' selected='<%= last.equals("12") %>' value="12" />
													<aui:option label='<%= LanguageUtil.format(request, "x-hours", "24", false) %>' selected='<%= last.equals("24") %>' value="24" />
													<aui:option label='<%= LanguageUtil.format(request, "x-hours", "48", false) %>' selected='<%= last.equals("48") %>' value="48" />
													<aui:option label='<%= LanguageUtil.format(request, "x-days", "7", false) %>' selected='<%= last.equals("168") %>' value="168" />
												</aui:select>
											</li>
										</ul>
									</aui:fieldset>
								</li>
							</ul>
						</div>

						<liferay-util:buffer var="selectedLabelsHTML">
							<span class="selected-labels" id="<portlet:namespace />selectedRange"></span>

							<aui:a cssClass="modify-link" href="javascript:;" id="rangeLink" label="change" method="get" />
						</liferay-util:buffer>

						<liferay-ui:icon
							iconCssClass="icon-calendar"
							label="<%= true %>"
							message='<%= LanguageUtil.get(request, "date-range") + selectedLabelsHTML %>'
						/>
					</li>

					<li class="options">
						<ul class="portlet-list">

							<%
							Set<String> displayedControls = new HashSet<String>();
							Set<String> portletDataHandlerClasses = new HashSet<String>();

							dataSiteLevelPortlets = ListUtil.sort(dataSiteLevelPortlets, new PortletTitleComparator(application, locale));

							for (Portlet portlet : dataSiteLevelPortlets) {
								String portletDataHandlerClass = portlet.getPortletDataHandlerClass();

								if (!portletDataHandlerClasses.contains(portletDataHandlerClass)) {
									portletDataHandlerClasses.add(portletDataHandlerClass);
								}
								else {
									continue;
								}

								String portletTitle = PortalUtil.getPortletTitle(portlet, application, locale);

								PortletDataHandler portletDataHandler = portlet.getPortletDataHandlerInstance();

								portletDataHandler.prepareManifestSummary(portletDataContext);

								long exportModelCount = portletDataHandler.getExportModelCount(manifestSummary);

								long modelDeletionCount = manifestSummary.getModelDeletionCount(portletDataHandler.getDeletionSystemEventStagedModelTypes());

								boolean displayCounts = (exportModelCount != 0) || (modelDeletionCount != 0);

								if (!isExport) {
									displayCounts = GetterUtil.getBoolean(liveGroupTypeSettings.getProperty(StagingUtil.getStagedPortletId(portlet.getRootPortletId())), portletDataHandler.isPublishToLiveByDefault()) && ((exportModelCount != 0) || (modelDeletionCount !=0));
								}
							%>

							<c:if test="<%= displayCounts %>">
								<li class="tree-item">
									<liferay-util:buffer var="badgeHTML">
										<span class="badge badge-info"><%= exportModelCount > 0 ? exportModelCount : StringPool.BLANK %></span>
										<span class="badge badge-warning deletions"><%= modelDeletionCount > 0 ? (modelDeletionCount + StringPool.SPACE + LanguageUtil.get(request, "deletions")) : StringPool.BLANK %></span>
									</liferay-util:buffer>

									<aui:input checked="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portlet.getPortletId(), portletDataHandler.isPublishToLiveByDefault()) %>" label="<%= portletTitle + badgeHTML %>" name="<%= PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portlet.getPortletId() %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portlet.getPortletId(), portletDataHandler.isPublishToLiveByDefault()) %>" />

									<%
									PortletDataHandlerControl[] exportControls = portletDataHandler.getExportControls();
									PortletDataHandlerControl[] metadataControls = portletDataHandler.getExportMetadataControls();

									if (ArrayUtil.isNotEmpty(exportControls) || ArrayUtil.isNotEmpty(metadataControls)) {
									%>

									<div class="hide" id="<portlet:namespace />content_<%= portlet.getPortletId() %>">
										<ul class="lfr-tree list-unstyled">
											<li class="tree-item">
												<aui:fieldset cssClass="portlet-type-data-section" label="<%= portletTitle %>">

													<%
													if (exportControls != null) {
														if (isExport) {
															request.setAttribute("render_controls.jsp-action", Constants.EXPORT);
															request.setAttribute("render_controls.jsp-controls", exportControls);
															request.setAttribute("render_controls.jsp-manifestSummary", manifestSummary);
															request.setAttribute("render_controls.jsp-parameterMap", parameterMap);
															request.setAttribute("render_controls.jsp-portletDisabled", !portletDataHandler.isPublishToLiveByDefault());
													%>

															<aui:field-wrapper label='<%= ArrayUtil.isNotEmpty(metadataControls) ? "content" : StringPool.BLANK %>'>
																<ul class="lfr-tree list-unstyled">
																	<liferay-util:include page="/html/portlet/layouts_admin/render_controls.jsp" />
																</ul>
															</aui:field-wrapper>

													<%
														}
														else {
															if (liveGroup.isStagedPortlet(portlet.getRootPortletId())) {
																request.setAttribute("render_controls.jsp-action", Constants.PUBLISH);
																request.setAttribute("render_controls.jsp-controls", exportControls);
																request.setAttribute("render_controls.jsp-manifestSummary", manifestSummary);
																request.setAttribute("render_controls.jsp-parameterMap", parameterMap);
																request.setAttribute("render_controls.jsp-portletDisabled", !portletDataHandler.isPublishToLiveByDefault());
													%>

																<aui:field-wrapper label='<%= ArrayUtil.isNotEmpty(metadataControls) ? "content" : StringPool.BLANK %>'>
																	<ul class="lfr-tree list-unstyled">
																		<liferay-util:include page="/html/portlet/layouts_admin/render_controls.jsp" />
																	</ul>
																</aui:field-wrapper>

													<%
															}
														}
													}

													if (metadataControls != null) {
														for (PortletDataHandlerControl metadataControl : metadataControls) {
															if (!displayedControls.contains(metadataControl.getControlName())) {
																displayedControls.add(metadataControl.getControlName());
															}
															else {
																continue;
															}

															PortletDataHandlerBoolean control = (PortletDataHandlerBoolean)metadataControl;

															PortletDataHandlerControl[] childrenControls = control.getChildren();

															if (ArrayUtil.isNotEmpty(childrenControls)) {
																request.setAttribute("render_controls.jsp-controls", childrenControls);
													%>

													<aui:field-wrapper label="content-metadata">
														<ul class="lfr-tree list-unstyled">
															<liferay-util:include page="/html/portlet/layouts_admin/render_controls.jsp" />
														</ul>
													</aui:field-wrapper>

													<%
															}
														}
													}
													%>

												</aui:fieldset>
											</li>
										</ul>
									</div>

									<%
									String portletId = portlet.getPortletId();

									if (!isExport) {
										portletId = portlet.getRootPortletId();
									}
									%>

									<ul class="hide" id="<portlet:namespace />showChangeContent_<%= portletId %>">
										<li>
											<span class="selected-labels" id="<portlet:namespace />selectedContent_<%= portletId %>"></span>

											<%
											Map<String,Object> data = new HashMap<String,Object>();

											data.put("portletid", portletId);
											data.put("portlettitle", portletTitle);
											%>

											<aui:a cssClass="content-link modify-link" data="<%= data %>" href="javascript:;" id='<%= "contentLink_" + portletId %>' label="change" method="get" />
										</li>
									</ul>

									<aui:script>
										Liferay.Util.toggleBoxes('<portlet:namespace /><%= PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portletId %>', '<portlet:namespace />showChangeContent<%= StringPool.UNDERLINE + portletId %>');
									</aui:script>

									<%
									}
									%>

								</li>
							</c:if>

							<%
							}
							%>

						</ul>

						<aui:fieldset cssClass="content-options" label='<%= isExport ? "for-each-of-the-selected-content-types,-export-their" : "for-each-of-the-selected-content-types,-publish-their" %>'>
							<span class="selected-labels" id="<portlet:namespace />selectedContentOptions"></span>

							<aui:a cssClass="modify-link" href="javascript:;" id="contentOptionsLink" label="change" method="get" />

							<div class="hide" id="<portlet:namespace />contentOptions">
								<ul class="lfr-tree list-unstyled">
									<li class="tree-item">
										<aui:input label="comments" name="<%= PortletDataHandlerKeys.COMMENTS %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.COMMENTS, true) %>" />

										<aui:input label="ratings" name="<%= PortletDataHandlerKeys.RATINGS %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.RATINGS, true) %>" />

										<%
										long modelDeletionCount = manifestSummary.getModelDeletionCount();
										%>

										<c:if test="<%= modelDeletionCount != 0 %>">

											<%
											String deletionsLabel = LanguageUtil.get(request, "deletions") + (modelDeletionCount > 0 ? " (" + modelDeletionCount + ")" : StringPool.BLANK);
											%>

											<aui:input checked="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.DELETIONS, !isExport) %>" data-name="<%= deletionsLabel %>" helpMessage="deletions-help" label="<%= deletionsLabel %>" name="<%= PortletDataHandlerKeys.DELETIONS %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.DELETIONS, false) %>" />
										</c:if>
									</li>
								</ul>
							</div>
						</aui:fieldset>
					</li>
				</ul>
			</li>
		</ul>
	</aui:fieldset>
</c:if>