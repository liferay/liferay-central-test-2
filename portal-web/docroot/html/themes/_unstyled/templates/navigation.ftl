<div id="navigation" class="sort-pages modify-pages">
	<ul>
		<#list nav_items as nav_item>
			<#if nav_item.isSelected()>
				<#assign nav_item_class = "selected" />
			<#else>
				<#assign nav_item_class = "" />
			</#if>

			<li class="${nav_item_class}">
				<a href="${nav_item.getURL()}" ${nav_item.getTarget()}><span>${nav_item.getName()}</span></a>

				<#if nav_item.hasChildren()>
					<ul class="child-menu">
						<#list nav_item.getChildren() as nav_child>
							<#if nav_child.isSelected()>
								<#assign nav_child_class = "selected" />
							<#else>
								<#assign nav_child_class = "" />
							</#if>

							<li class="${nav_child_class}">
								<a href="${nav_child.getURL()}" ${nav_child.getTarget()}>${nav_child.getName()}</a>
							</li>
						</#list>
					</ul>
				</#if>
			</li>
		</#list>
	</ul>
</div>

<div class="site-breadcrumbs">
	<@liferay.breadcrumbs/>
</div>