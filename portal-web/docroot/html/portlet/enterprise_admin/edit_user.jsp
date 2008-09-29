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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

User selUser = PortalUtil.getSelectedUser(request);

Contact selContact = null;

if (selUser != null) {
	selContact = selUser.getContact();
}

PasswordPolicy passwordPolicy = null;

if (selUser == null) {
	passwordPolicy = PasswordPolicyLocalServiceUtil.getDefaultPasswordPolicy(company.getCompanyId());
}
else {
	passwordPolicy = selUser.getPasswordPolicy();
}

long classPK=0;

if (selContact != null) {
	classPK = selContact.getContactId();
}

String className = Contact.class.getName();

//Organizations

String organizationIds = ParamUtil.getString(request, "organizationIds", null);
long[] organizationIdsArray = StringUtil.split(organizationIds, 0L);

List organizations = new ArrayList();

if (organizationIds == null) {
	if (selUser != null) {
		organizations = selUser.getOrganizations();
	}
}
else {
	try {
		organizations = OrganizationLocalServiceUtil.getOrganizations(organizationIdsArray);
	}
	catch (NoSuchOrganizationException nsoe) {
	}
}

//Communities

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/communities/view");
portletURL.setParameter("tabs1", tabs1);

LinkedHashMap groupParams = new LinkedHashMap();

if (selUser != null) {
	groupParams.put("usersGroups", new Long(selUser.getUserId()));
}

List communities = GroupLocalServiceUtil.search(company.getCompanyId(), null, null, groupParams, 0, 50, new GroupNameComparator(true));

// Roles

List<Role> regularRoles = null;

if (selUser == null) {
	regularRoles = new ArrayList<Role>();
}
else {
	regularRoles = RoleLocalServiceUtil.getUserRoles(selUser.getUserId());
}
%>

<script type="text/javascript">
	function <portlet:namespace />saveUser(cmd) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = cmd;

		document.<portlet:namespace />fm.<portlet:namespace />websiteIds.value = websiteIdsArray.join(',');

		var redirect = "<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_user" /></portlet:renderURL>";

		redirect += "&<portlet:namespace />backURL=<%= HttpUtil.encodeURL(backURL) %>&<portlet:namespace />p_u_i_d=";

		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_user" /></portlet:actionURL>");
	}
</script>

<%
String toolbarItem = (selUser == null) ? "add-user" : "view-users";
%>

<%@ include file="/html/portlet/enterprise_admin/user/toolbar.jspf" %>

<form class="uni-form" method="post" name="<portlet:namespace />fm" >
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />

<input name="<portlet:namespace />redirect" type="hidden" value="" />
<input name="<portlet:namespace />backURL" type="hidden" value="<%= HtmlUtil.escape(backURL) %>" />
<input name="<portlet:namespace />p_u_i_d" type="hidden" value='<%= (selUser != null) ? selUser.getUserId() : 0 %>' />

<liferay-ui:error exception="<%= ContactFirstNameException.class %>" message="please-enter-a-valid-first-name" />
<liferay-ui:error exception="<%= ContactLastNameException.class %>" message="please-enter-a-valid-last-name" />
<liferay-ui:error exception="<%= DuplicateUserEmailAddressException.class %>" message="the-email-address-you-requested-is-already-taken" />
<liferay-ui:error exception="<%= DuplicateUserIdException.class %>" message="the-user-id-you-requested-is-already-taken" />
<liferay-ui:error exception="<%= DuplicateUserScreenNameException.class %>" message="the-screen-name-you-requested-is-already-taken" />
<liferay-ui:error exception="<%= ReservedUserEmailAddressException.class %>" message="the-email-address-you-requested-is-reserved" />
<liferay-ui:error exception="<%= ReservedUserIdException.class %>" message="the-user-id-you-requested-is-reserved" />
<liferay-ui:error exception="<%= ReservedUserScreenNameException.class %>" message="the-screen-name-you-requested-is-reserved" />
<liferay-ui:error exception="<%= UserEmailAddressException.class %>" message="please-enter-a-valid-email-address" />
<liferay-ui:error exception="<%= UserIdException.class %>" message="please-enter-a-valid-user-id" />
<liferay-ui:error exception="<%= UserScreenNameException.class %>" message="please-enter-a-valid-screen-name" />
<liferay-ui:error exception="<%= WebsiteURLException.class %>" message="please-enter-a-valid-website-url" />

<div id="user">
	<table class="user-table" width="100%">
		<tr>
			<td>
				<%
				request.setAttribute("user.selUser",selUser);
				request.setAttribute("user.selContact", selContact);

				request.setAttribute("user.organizations", organizations);
				request.setAttribute("user.organizationIds", organizationIds);
				request.setAttribute("user.communities", communities);
				request.setAttribute("user.regularRoles", regularRoles);
				%>

				<div class="form-section selected" id="userDetails">
					<c:if test="<%= selUser != null %>">
						<c:if test="<%= (passwordPolicy != null) && selUser.getLockout() %>">
							<h3><liferay-ui:message key="lockout" /></h3>

							<fieldset class="block-labels">

								<div class="ctrl-holder">
									<div class="portlet-msg-alert"><liferay-ui:message key="this-user-account-has-been-locked-due-to-excessive-failed-login-attempts" /></div>

									<input type="button" value="<liferay-ui:message key="unlock" />" onClick="<portlet:namespace />saveUser('unlock');" />
								</div>
							</fieldset>
						</c:if>
					</c:if>

					<liferay-util:include page="/html/portlet/enterprise_admin/user/details.jsp" />
				</div>

				<c:if test="<%= selUser != null %>">
					<div class="form-section" id="password">
						<liferay-util:include page="/html/portlet/enterprise_admin/user/password.jsp" />
					</div>

					<div class="form-section" id="userAssociations">
						<liferay-util:include page="/html/portlet/enterprise_admin/user/user_associations.jsp" />
					</div>

					<div class="form-section" id="roles">
						<liferay-util:include page="/html/portlet/enterprise_admin/user/roles.jsp" />
					</div>

					<div class="form-section" id="websites">
						<%
							List<Website> websites = WebsiteServiceUtil.getWebsites(className, classPK);
							request.setAttribute("common.websites", websites);

						%>
						<liferay-util:include page="/html/portlet/enterprise_admin/common/websites.jsp" />
					</div>

					<div class="form-section" id="instantMessenger">
						<liferay-util:include page="/html/portlet/enterprise_admin/user/instant_messenger.jsp" />
					</div>

					<div class="form-section" id="socialNetwork">
						<liferay-util:include page="/html/portlet/enterprise_admin/user/social_network.jsp" />
					</div>

					<div class="form-section" id="sms">
						<liferay-util:include page="/html/portlet/enterprise_admin/user/sms.jsp" />
					</div>

					<div class="form-section" id="openId">
						<liferay-util:include page="/html/portlet/enterprise_admin/user/open_id.jsp" />
					</div>

					<div class="form-section" id="announcements">
						<liferay-util:include page="/html/portlet/enterprise_admin/user/announcements.jsp" />
					</div>
					<div class="form-section" id="displaySettings">
						<liferay-util:include page="/html/portlet/enterprise_admin/user/display_settings.jsp" />
					</div>
					<div class="form-section" id="comments">
						<liferay-util:include page="/html/portlet/enterprise_admin/common/comments.jsp" />
					</div>
				</c:if>

				<div class="lfr-component form-navigation">

					<div class="user-info">
						<p class="float-container">
							<c:if test="<%= selUser != null %>">
								<img alt="<liferay-ui:message key="avatar" />" class="avatar" src='<%= themeDisplay.getPathImage() %>/user_<%= selUser.isFemale() ? "female" : "male" %>_portrait?img_id=<%= selUser.getPortraitId() %>&t=<%= ImageServletTokenUtil.getToken(selUser.getPortraitId()) %>' width="34" />

								<liferay-ui:message key="editing-user" /> <span><%= selUser.getFullName() %></span>
							</c:if>
						</p>
					</div>

					<div class="menu-group">
						<h3><liferay-ui:message key="main-user-info" /></h3>
						<ul>
							<li class="selected"><a href="#userDetails" id="userDetailsLink"><liferay-ui:message key="user-details" /></a></li>
							<c:if test="<%= selUser != null %>">
								<li><a href="#password" id="passwordLink"><liferay-ui:message key="password" /></a></li>
								<li><a href="#userAssociations"><liferay-ui:message key="user-associations" /></a></li>
								<li><a href="#roles"><liferay-ui:message key="roles" /></a></li>
							</c:if>
						</ul>
					</div>

					<c:if test="<%= selUser != null %>">
						<div class="menu-group">
							<h3><liferay-ui:message key="identification" /></h3>
							<ul>
								<li><a href="#websites" id="websiteLink"><liferay-ui:message key="websites" /></a></li>
								<li><a href="#instantMessenger" id="instantMessengerLink"><liferay-ui:message key="instant-messenger" /></a></li>
								<li><a href="#socialNetwork" id="socialNetworkLink"><liferay-ui:message key="social-network" /></a></li>
								<li><a href="#sms" id="smsLink"><liferay-ui:message key="sms" /></a></li>
								<li><a href="#openId" id="openIdLink"><liferay-ui:message key="openid" /></a></li>
							</ul>
						</div>

						<div class="menu-group">
							<h3><liferay-ui:message key="miscellaneous" /></h3>
							<ul>
								<li><a href="#announcements" id="announcementsLink"><liferay-ui:message key="alerts-and-announcements" /></a></li>
								<li><a href="#displaySettings" id="displaySettingsLink"><liferay-ui:message key="display-settings" /></a></li>
								<li><a href="#comments" id="commentsLink"><liferay-ui:message key="comments" /></a></li>
							</ul>
						</div>
					</c:if>

					<div class="button-holder">
						<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveUser('<%= selUser == null ? Constants.ADD : Constants.UPDATE %>');" />  &nbsp;

						<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HttpUtil.encodeURL(backURL) %>';" /><br />
					</div>
				</div>

			</td>
		</tr>
	</table>
</div>

<c:if test="<%= selUser != null %>">
	<liferay-ui:error exception="<%= UserPasswordException.class %>">

		<%
		UserPasswordException upe = (UserPasswordException)errorException;
		%>

		<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_ALREADY_USED %>">
			<liferay-ui:message key="that-password-has-already-been-used-please-enter-in-a-different-password" />
		</c:if>

		<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_CONTAINS_TRIVIAL_WORDS %>">
			<liferay-ui:message key="that-password-uses-common-words-please-enter-in-a-password-that-is-harder-to-guess-i-e-contains-a-mix-of-numbers-and-letters" />
		</c:if>

		<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_INVALID %>">
			<liferay-ui:message key="that-password-is-invalid-please-enter-in-a-different-password" />
		</c:if>

		<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_LENGTH %>">
			<%= LanguageUtil.format(pageContext, "that-password-is-too-short-or-too-long-please-make-sure-your-password-is-between-x-and-512-characters", String.valueOf(passwordPolicy.getMinLength()), false) %>
		</c:if>

		<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_NOT_CHANGEABLE %>">
			<liferay-ui:message key="your-password-cannot-be-changed" />
		</c:if>

		<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_SAME_AS_CURRENT %>">
			<liferay-ui:message key="your-new-password-cannot-be-the-same-as-your-old-password-please-enter-in-a-different-password" />
		</c:if>

		<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_TOO_YOUNG %>">
			<%= LanguageUtil.format(pageContext, "you-cannot-change-your-password-yet-please-wait-at-least-x-before-changing-your-password-again", LanguageUtil.getTimeDescription(pageContext, passwordPolicy.getMinAge() * 1000), false) %>
		</c:if>

		<c:if test="<%= upe.getType() == UserPasswordException.PASSWORDS_DO_NOT_MATCH %>">
			<liferay-ui:message key="the-passwords-you-entered-do-not-match-each-other-please-re-enter-your-password" />
		</c:if>
	</liferay-ui:error>

	<%
	PortalUtil.setPageSubtitle(selUser.getFullName(), request);
	%>

</c:if>

</form>

<script type="text/javascript">
	jQuery(
		function () {
			var formNav = jQuery('.form-navigation');
			var formSections = jQuery('#user .form-section');

			var revealSection = function(id, currentNavItem) {
				var li = currentNavItem || formNav.find('[@href$=' + id + ']').parent();
				id = id.split('#');

				if (!id[1]) {
					return;
				}

				id = '#' + id[1];

				var section = jQuery(id);

				formNav.find('.selected').removeClass('selected');
				formSections.removeClass('selected');

				section.addClass('selected');
				li.addClass('selected');
			};

			var markAsModifiedUserDetails = function() {
				return markAsModified('#userDetailsLink');
			}
			var markAsModifiedPassword = function() {
				return markAsModified('#passwordLink');
			}

			var markAsModifiedWebsite = function() {
				return markAsModified('#websiteLink');
			}

			var markAsModifiedInstantMessenger = function() {
				return markAsModified('#instantMessengerLink');
			}

			var markAsModifiedSocialNetwork = function() {
				return markAsModified('#socialNetworkLink');
			}

			var markAsModifiedSms = function() {
				return markAsModified('#smsLink');
			}

			var markAsModifiedOpenId = function() {
				return markAsModified('#openIdLink');
			}

			var markAsModifiedComments = function() {
				return markAsModified('#commentsLink')
			}

			var markAsModifiedAnnouncements = function() {
				return markAsModified('#announcementsLink')
			}

			var markAsModifiedDisplaySettings = function() {
				return markAsModified('#displaySettingsLink')
			}

			var markAsModified = function(id) {
				if (jQuery(id).text().indexOf(' (<liferay-ui:message key="modified" />)') == -1) {
					jQuery(id).append(' <strong class="form-section-modified">(<liferay-ui:message key="modified" />)</strong>');
					//jQuery(id).style('font-weight: bold');
				}
			}

			jQuery('.form-navigation li a').click(
				function(event) {
					var li = jQuery(this.parentNode);

					if (!li.is('.selected')) {
						revealSection(this.href, li);
					}

					return true;
				}
				);

			revealSection(location.hash);

			jQuery('#userDetails input').change(markAsModifiedUserDetails)
			jQuery('#userDetails select').change(markAsModifiedUserDetails)
			jQuery('#password input').change(markAsModifiedPassword)
			jQuery('#websites select').change(markAsModifiedWebsite)
			jQuery('#websites input').change(markAsModifiedWebsite)
			jQuery('#instantMessenger input').change(markAsModifiedInstantMessenger)
			jQuery('#sms input').change(markAsModifiedSms)
			jQuery('#openId input').change(markAsModifiedOpenId)
			jQuery('#socialNetwork input').change(markAsModifiedSocialNetwork)
			jQuery('#comments textarea').change(markAsModifiedComments)
			jQuery('#announcements input').change(markAsModifiedAnnouncements)
			jQuery('#displaySettings input').change(markAsModifiedDisplaySettings)
		}
	);

	Liferay.autoFields2 = new Class({
		initialize: function(options) {
			var instance = this;

			instance._itemsArray =  options.itemsArray;
			var container = jQuery(options.container);
			var baseRows = jQuery(options.baseRows);

			var fullContainer = jQuery('<div class="row-container"></div>');
			var baseContainer = jQuery('<div class="form-row"></div>');
			var rowControls = jQuery('<span class="row-controls"><a href="javascript: ;" class="add-row">Add row</a><a href="javascript: ;" class="delete-row">Delete row</a></span>');
			var undoManager = jQuery('<div class="portlet-msg-info undo-queue queue-empty"><a class="undo-action" href="javascript: ;">Undo <span class="items-left">(0)</span></a><a class="clear-undos" href="javascript: ;">Clear History</a></div>');

			fullContainer.click(
				function(event) {
					if (event.target.parentNode.className.indexOf('row-controls') > -1) {
						var target = jQuery(event.target);
						var currentRow = target.parents('.form-row:first')[0];

						if (target.is('.add-row')) {
							instance.addRow(currentRow);
						}

						if (target.is('.delete-row')) {
							instance.deleteRow(currentRow);
						}
					}
				}
				);

			instance._container = container;
			instance._rowContainer = fullContainer;

			instance._undoManager = undoManager;

			instance._undoItemsLeft = undoManager.find('.items-left');
			instance._undoButton = undoManager.find('.undo-action');
			instance._clearUndos = undoManager.find('.clear-undos');

			instance._clearUndos.click(
				function(event) {
					instance._undoCache = [];
					instance._rowContainer.find('.form-row:hidden').remove();

					Liferay.trigger('updateUndoList');
				}
				);

			instance._undoButton.click(
				function(event) {
					instance.undoLast();
				}
				);

			fullContainer.prepend(undoManager);

			baseRows.each(
				function(i) {
					var formRow;
					var controls = rowControls.clone();
					var currentRow = jQuery(this);

					if (currentRow.is('.form-row')) {
						formRow = currentRow;
					}
					else {
						formRow = baseContainer.clone();
						formRow.append(this);
					}

					formRow.append(controls);
					fullContainer.append(formRow);
					if (i == 0) {
						instance._rowTemplate = formRow.clone();
						instance._rowTemplate.clearForm();
					}
				}
				);

			container.append(fullContainer);

			Liferay.bind('updateUndoList',
				function(event) {
					instance._updateUndoList();
				}
				);
		},

		addRow: function(el) {
			var instance = this;

			var currentRow = jQuery(el);
			var clone = currentRow.clone(true);

			var newSeed = (instance._idSeed++)+1;
			if (newSeed<10){
				newSeed = "0"+newSeed;
			}
			clone.find('input,select').each(
				function() {
					var el = jQuery(this);
					var oldName = el.attr('name');
					var originalName = oldName.substring(0,oldName.length-2);
					var newName = originalName + newSeed;

					if (!el.is(':radio')) {
						el.attr('name', newName);
					}

					el.attr('name', newName);
					el.attr('id', newName);
					clone.find('label[for=' + oldName + ']').attr('for', newName);


				}
			);

			instance._itemsArray.push(newSeed);
			clone.clearForm();
			clone.find("input[type=hidden]").each(function() {
				this.value = '';
			});

			currentRow.after(clone);

			clone.find('input:text:first').trigger('focus');
		},

		deleteRow: function(el) {
			var instance = this;

			var visibleRows = instance._rowContainer.find('.form-row:visible');

			if (visibleRows.length == 1) {
				instance.addRow(el);
			}

			var deletedElement = jQuery(el);

			deletedElement.hide();

			instance._queueUndo(deletedElement);
			var index;
			var currentRow = jQuery(el);
			currentRow.find('select').each(
				function(){
				var el = jQuery(this);
				var oldName = el.attr('name');
				id = oldName.substring(oldName.length-2,oldName.length)});

				for(var i=0;i<instance._itemsArray.length;i++){
					if (id==instance._itemsArray[i]) {
						instance._itemsArray.splice(i, 1);
					}
			}
		},

		undoLast: function() {
			var instance = this;

			var itemsLeft = instance._undoCache.length;

			if (itemsLeft > 0) {
				var deletedElement = instance._undoCache.pop();

				deletedElement.show();

				Liferay.trigger('updateUndoList');
			}
		},

		serialize: function(filter) {
			var instance = this;

			var fields = instance.baseContainer('.form-row:visible :input');
			var serializedData = '';

			if (filter) {
				filter.apply(instance, [fields]);
			}

			return '';
		},

		_updateUndoList: function() {
			var instance = this;

			var itemsLeft = instance._undoCache.length;
			var undoManager = instance._undoManager;

			if (itemsLeft == 1) {
				undoManager.addClass('queue-single');
			}
			else {
				undoManager.removeClass('queue-single');
			}

			if (itemsLeft > 0) {
				undoManager.removeClass('queue-empty');
			}
			else {
				undoManager.addClass('queue-empty');
			}

			instance._undoItemsLeft.text('(' + itemsLeft + ')');
		},

		_queueUndo: function(deletedElement) {
			var instance = this;

			instance._undoCache.push(deletedElement);

			Liferay.trigger('updateUndoList');
		},

		_undoCache: [],
		_idSeed: lastIndex,
		_itemsArray: []
	});


</script>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<script type="text/javascript">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />screenName);
	</script>
</c:if>