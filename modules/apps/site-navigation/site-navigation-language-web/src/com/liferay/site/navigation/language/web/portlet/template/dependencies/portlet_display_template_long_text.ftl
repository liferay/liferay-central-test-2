<#assign aui = taglibLiferayHash["/WEB-INF/tld/liferay-aui.tld"] />

<style>
	.language-entry-long-text {
		padding: 0 0.5em;
	}
</style>

<#if entries?has_content>
	<#list entries as entry>
		<@aui["a"]
			cssClass="language-entry-long-text"
			href=entry.getURL()
			label=entry.getLongDisplayName()
			lang=entry.getW3cLanguageId()
		/>
	</#list>
</#if>