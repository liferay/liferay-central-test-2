<#if entries?has_content>
	<ul class="breadcrumb breadcrumb-horizontal">
		<#assign cssClass = "" />

		<#list entries as entry>
			<#if entry?is_last>
				<#assign cssClass = "active" />
			</#if>

			<li class="${cssClass}">
				<#if entry?has_next>
					<a

					<#if entry.isBrowsable()>
						href="${entry.getURL()!""}"
					</#if>

					>
				</#if>

				${htmlUtil.escape(entry.getTitle())}

				<#if entry?has_next>
					</a>
				</#if>
			</li>
		</#list>
	</ul>
</#if>