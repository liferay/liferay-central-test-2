<#assign aui = taglibLiferayHash["/WEB-INF/tld/liferay-aui.tld"] />

<style>
	.language-entry-short-text {
		padding: 0 0.5em;
	}
</style>

<#if entries?has_content>
	<#list entries as entry>
		<@aui["a"]
			cssClass="language-entry-short-text"
			href=entry.getURL()
			label=entry.getShortDisplayName()
			lang=entry.getW3cLanguageId()
		/>
	</#list>
</#if>