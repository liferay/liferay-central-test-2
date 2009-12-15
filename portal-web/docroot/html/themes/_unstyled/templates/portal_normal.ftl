<!DOCTYPE html>

<#include init/>

<html dir="<@liferay.language key="lang.dir"/>">

<head>
	<title>${the_title} - ${company_name}</title>

	${theme.include(top_head_include)}
</head>

<body class="${css_class}">

<#if is_signed_in>
	<@liferay.dockbar/>
</#if>

<div id="wrapper">
	<div id="banner">
		<h1 class="logo">
			<a class="png" href="${company_url}">${the_title} - ${company_name}</a>

			<span class="current-community">
				${community_name}
			</span>
		</h1>

		<#if !is_signed_in>
			<a class="sign-in" href="${sign_in_url}" id="liferaySignInLink">${sign_in_text}</a>
		</#if>

		<#if update_available_url??>
			<div class="popup-alert-notice">
				<a class="update-available" href="${update_available_url}"><@liferay.language key="updates-are-available-for-liferay"/></a>
			</div>
		</#if>
	</div>

	<#if has_navigation>
		<#include "${full_templates_path}/navigation.ftl"/>
	</#if>

	<#if selectable>
		${theme.include(content_include)}
	<#else>
		${portletDisplay.recycle()}

		${portletDisplay.setTitle(the_title)}

		${theme.wrapPortlet("portlet.ftl", content_include)}
	</#if>

	<div id="footer">
		<div class="powered-by">
			Powered by <a href="http://www.liferay.com">Liferay</a>
		</div>
	</div>
</div>

</body>

${theme.include(bottom_include)}

</html>