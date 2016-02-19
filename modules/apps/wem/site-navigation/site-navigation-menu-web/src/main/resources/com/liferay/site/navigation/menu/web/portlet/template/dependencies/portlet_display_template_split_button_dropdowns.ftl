<#if !entries?has_content>
	<#if preview>
		<div class="alert alert-info">
			<@liferay.language key="there-are-no-pages-to-display-for-the-current-page-level" />
		</div>
	</#if>
<#else>
	<#assign portletDisplay = themeDisplay.getPortletDisplay() />

	<#assign navbarId = "navbar_" + portletDisplay.getId() />

	<div id="${navbarId}">
		<#assign navItems = entries />

		<#list navItems as navItem>
			<#if navItem.isBrowsable() || navItem.hasBrowsableChildren() >
				<#assign nav_item_caret = "" />
				<#assign nav_item_css_class = "" />
				<#assign nav_item_href_link = "" />

				<#if navItem.isSelected() >
					<#assign nav_item_css_class = "active" />
				</#if>

				<#if navItem.hasBrowsableChildren() >
					<#assign toggle_text>
						<@liferay.language key="toggle" />
					</#assign>

					<#assign nav_item_caret = "<button aria-expanded='false' aria-haspopup='true' class='${nav_item_css_class} btn btn-default dropdown-toggle' data-toggle='dropdown' type='button'><span class='caret'></span><span class='sr-only'>${toggle_text}</span></button>" />
				</#if>

				<#if navItem.isBrowsable() >
					<#assign nav_item_href_link = "href='${navItem.getURL()}' ${navItem.getTarget()}" />
				</#if>

				<div class="btn-group navbar-site">
					<a aria-labelledby="layout_${navItem.getLayoutId()}" class="${nav_item_css_class} btn btn-default" ${nav_item_href_link}><span>${navItem.getName()}</span></a>${nav_item_caret}

					<#if navItem.hasBrowsableChildren() >
						<ul class="child-menu dropdown-menu" role="menu">
							<#list navItem.getBrowsableChildren() as childNavigationItem>
								<#assign nav_child_attr_selected = "" />
								<#assign nav_child_css_class = "" />

								<#if childNavigationItem.isSelected() >
									<#assign nav_child_attr_selected = "aria-selected='true'" />
									<#assign nav_child_css_class = "active" />
								</#if>

								<li class="${nav_child_css_class}" id="layout_${childNavigationItem.getLayoutId()}" ${nav_child_attr_selected} role="presentation">
									<a aria-labelledby="layout_${childNavigationItem.getLayoutId()}" href="${childNavigationItem.getURL()}" ${childNavigationItem.getTarget()} role="menuitem">${childNavigationItem.getName()}</a>
								</li>
							</#list>
						</ul>
					</#if>
				</div>
			</#if>
		</#list>
	</div>

	<@liferay_aui.script use="liferay-navigation-interaction">
		var navigation = A.one('#${navbarId}');

		if (navigation) {
			navigation.plug(Liferay.NavigationInteraction);
		}

		Liferay.Data.NAV_INTERACTION_LIST_SELECTOR = '.navbar-site';
		Liferay.Data.NAV_LIST_SELECTOR = '.navbar-site';
	</@>
</#if>