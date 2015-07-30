<#assign aui = taglibLiferayHash["/WEB-INF/tld/liferay-aui.tld"] />
<#assign liferay_portlet = taglibLiferayHash["/WEB-INF/tld/liferay-portlet.tld"] />
<#assign liferay_ui = taglibLiferayHash["/WEB-INF/tld/liferay-ui.tld"] />

<style>
	.portlet-rss .feed-entry-content {
		margin-bottom: 20px;
		padding-left: 0;
	}
</style>

<#if entries?has_content>
	<#assign dateFormat = "dd MMM yyyy - HH:mm:ss" />

	<div class="container-fluid">
		<div class="row" id="<@liferay_portlet.namespace />feedsTab">
			<ul class="col-xs-4">
				<#list entries as curEntry>
					<li><a href="#tab-${curEntry_index}">${htmlUtil.escape(curEntry.getTitle())}</a></li>
				</#list>
			</ul>

			<div class="col-xs-8 tab-content">
			entries as curEntry>
					<#assign rssFeedEntries = curEntry.getRSSFeedEntries(themeDisplay) />

					<#if rssFeedEntries??>
						<div id="tab-${curEntry_index}" class="tab-pane">
							<#list rssFeedEntries as rssFeedEntry>
								<#if (rssFeedEntry_index > entriesPerFeed?number)>
									<#break>
								</#if>

								<#assign syndEntry = rssFeedEntry.getSyndEntry() />

								<div class="feed-entry-content">
									<div class="feed-title">
										<@aui["a"] href="${htmlUtil.escapeJSLink(rssFeedEntry.getSyndEntryLink())}">${htmlUtil.escape(syndEntry.getTitle())}</@>
									</div>

									<#if getterUtil.getBoolean(showFeedItemAuthor) && syndEntry.getAuthor()??>