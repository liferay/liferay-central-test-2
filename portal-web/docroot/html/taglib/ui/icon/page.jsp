<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
%>

<%@ include file="/html/taglib/ui/icon/init.jsp" %>

<%
String randomId = StringPool.BLANK;

String cssClassHtml = StringPool.BLANK;

if (Validator.isNotNull(cssClass)) {
	cssClassHtml = "class=\"" + cssClass + "\"";
}

String idHtml = StringPool.BLANK;

boolean srcHoverIsNotNull = Validator.isNotNull(srcHover);

if (srcHoverIsNotNull) {
	randomId = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);

	idHtml = " id=\"" + randomId + "\" ";
}

String langHtml = StringPool.BLANK;

if (Validator.isNotNull(lang)) {
		langHtml = "lang=\"" + lang + "\"";
}

String onClickHtml = StringPool.BLANK;

if (method.equals("post") && (url.startsWith(Http.HTTP_WITH_SLASH) || url.startsWith(Http.HTTPS_WITH_SLASH))) {
	onClickHtml = "onclick=\"Liferay.Util.forcePost(this); return false;\"";
}

String targetHtml = StringPool.BLANK;

if (Validator.isNotNull(target) && !target.equals("_self")) {
	targetHtml = "target=\"" + target + "\"";
}

if (themeDisplay.isThemeImagesFastLoad() && !auiImage) {
	SpriteImage spriteImage = null;
	String spriteFileName = null;

	String imageFileName = StringUtil.replace(src, "common/../", "");

	String imagesPath = theme.getContextPath() + theme.getImagesPath();

	if (imageFileName.startsWith(imagesPath)) {
		imageFileName = imageFileName.substring(imagesPath.length());

		spriteImage = theme.getSpriteImage(imageFileName);

		if (spriteImage != null) {
			spriteFileName = spriteImage.getSpriteFileName();

			if (BrowserSnifferUtil.isIe(request) && (BrowserSnifferUtil.getMajorVersion(request) < 7)) {
				spriteFileName = StringUtil.replace(spriteFileName, ".png", ".gif");
			}

			spriteFileName = themeDisplay.getPathThemeImages() + spriteFileName;
		}
	}

	if (spriteImage == null) {
		Portlet portlet = (Portlet)request.getAttribute("liferay-portlet:icon_portlet:portlet");

		if (portlet == null) {
			portlet = (Portlet)request.getAttribute(WebKeys.RENDER_PORTLET);
		}

		if (portlet != null) {
			PortletApp portletApp = portlet.getPortletApp();

			imageFileName = src;

			if (portletApp.isWARFile() && imageFileName.startsWith(portlet.getContextPath())) {
				imageFileName = imageFileName.substring(portlet.getContextPath().length());
			}

			spriteImage = portletApp.getSpriteImage(imageFileName);

			if (spriteImage != null) {
				spriteFileName = spriteImage.getSpriteFileName();

				if (BrowserSnifferUtil.isIe(request) && (BrowserSnifferUtil.getMajorVersion(request) < 7)) {
					spriteFileName = StringUtil.replace(spriteFileName, ".png", ".gif");
				}

				spriteFileName = portlet.getContextPath() + spriteFileName;
			}
		}
	}

	if (spriteImage != null) {
		src = themeDisplay.getPathThemeImages() + "/spacer.png";

		details += " style=\"background-image: url('" + spriteFileName + "'); background-position: 50% -" + spriteImage.getOffset() + "px; background-repeat: no-repeat; height: " + spriteImage.getHeight() + "px; width: " + spriteImage.getWidth() + "px;\"";
	}
}

String imgClass = "icon";

if (auiImage) {
	details += " style=\"background-image: url('" + themeDisplay.getPathThemeImages() + "/aui/icon_sprite.png'); height: 16px; width: 16px;\"";
	imgClass += " aui-icon-" + image.substring(_AUI_PATH.length());
}

boolean urlIsNotNull = Validator.isNotNull(url);
%>

<c:choose>
	<c:when test="<%= (iconListIconCount != null) && ((iconListSingleIcon == null) || iconListShowWhenSingleIcon) %>">
		<li <%= cssClassHtml %>>
			<c:if test="<%= urlIsNotNull %>">
				<a class="taglib-icon" href="<%= url %>" <%= langHtml %> <%= onClickHtml %> <%= targetHtml %>>
			</c:if>

			<img class="<%= imgClass %>" src="<%= src %>" <%= details %> />

			<span class="taglib-text"><liferay-ui:message key="<%= message %>" /></span>

			<c:if test="<%= urlIsNotNull %>">
				<c:if test='<%= target == "_blank" %>'>
					<span class="opens-new-window-accessible"><liferay-ui:message key="opens-new-window" /></span>
				</c:if>

				</a>
			</c:if>
		</li>
	</c:when>
	<c:when test="<%= (iconMenuIconCount != null) && ((iconMenuSingleIcon == null) || iconMenuShowWhenSingleIcon) %>">
		<li <%= cssClassHtml %>>
			<c:if test="<%= urlIsNotNull %>">
				<a href="<%= url %>" <%= langHtml %> <%= onClickHtml %> <%= targetHtml %>>
			</c:if>

			<img class="<%= imgClass %>" src="<%= src %>" <%= details %> />

			<liferay-ui:message key="<%= message %>" />

			<c:if test="<%= urlIsNotNull %>">
				<c:if test='<%= target == "_blank" %>'>
					<span class="opens-new-window-accessible"><liferay-ui:message key="opens-new-window" /></span>
				</c:if>

				</a>
			</c:if>
		</li>
	</c:when>
	<c:otherwise>
		<span <%= cssClassHtml %> <%= idHtml %>>
			<c:if test="<%= urlIsNotNull %>">
				<a class="taglib-icon" href="<%= url %>" <%= langHtml %> <%= onClickHtml %> <%= targetHtml %>>
			</c:if>

			<img class="<%= imgClass %>" src="<%= src %>" <%= details %> />

			<c:if test="<%= label %>">
				<span class="taglib-text"><liferay-ui:message key="<%= message %>" /></span>
			</c:if>

			<c:if test="<%= urlIsNotNull %>">
				<c:if test='<%= target == "_blank" %>'>
					<span class="opens-new-window-accessible"><liferay-ui:message key="opens-new-window" /></span>
				</c:if>

				</a>
			</c:if>
		</span>
	</c:otherwise>
</c:choose>

<c:if test="<%= srcHoverIsNotNull %>">
	<aui:script use="event,node">
		var icon = A.one('#<%= randomId %>');

		if (icon) {
			icon.on(
				'mouseout',
				function(event) {
					var img = event.currentTarget.one('img');

					if (img) {
						img.attr('src', '<%= src %>');
					}
				}
			);

			icon.on(
				'mouseover',
				function(event) {
					var img = event.currentTarget.one('img');

					if (img) {
						img.attr('src', '<%= srcHover %>');
					}
				}
			);
		}
	</aui:script>
</c:if>