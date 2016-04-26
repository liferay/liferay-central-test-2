<#if userCardVisible>
	<#assign main_menu_style = "style='background-image: url(${user.getPortraitURL(theme_display)});'">

	<div class="aspect-ratio-bg-cover text-center user-container">
		<div class="user-info">
			<div class="aspect-ratio-bg-cover user-icon user-icon-xl" ${main_menu_style}></div>

			<div class="h3">${htmlUtil.escape(user_name)}</div>
		</div>

		<div class="aspect-ratio-bg-cover bg-icon" ${main_menu_style}></div>
	</div>
</#if>

<#assign VOID = freeMarkerPortletPreferences.setValue("portletSetupPortletDecoratorId", "barebone")>

<div aria-expanded="false">
	<#if has_navigation && is_setup_complete>
		<nav class="${nav_css_class} site-navigation" id="navigation" role="navigation">
			<@liferay.navigation_menu default_preferences="${freeMarkerPortletPreferences}" />
		</nav>
	</#if>
</div>

<#assign VOID = freeMarkerPortletPreferences.reset()>