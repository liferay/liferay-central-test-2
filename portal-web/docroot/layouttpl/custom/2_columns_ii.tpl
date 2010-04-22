#if ($browserSniffer.isIe($request) && $browserSniffer.getMajorVersion($request) < 8)
<div class="columns-2" id="main-content" role="main">
	<table class="portlet-layout">
	<tr>
		<td class="aui-w30 portlet-column portlet-column-first" id="column-1">
			$processor.processColumn("column-1", "portlet-column-content portlet-column-content-first")
		</td>
		<td class="aui-w70 portlet-column portlet-column-last" id="column-2">
			$processor.processColumn("column-2", "portlet-column-content portlet-column-content-last")
		</td>
	</tr>
	</table>
</div>
#else
<div class="columns-2" id="main-content" role="main">
	<div class="portlet-layout">
		<div class="aui-w30 portlet-column portlet-column-first" id="column-1">
			$processor.processColumn("column-1", "portlet-column-content portlet-column-content-first")
		</div>
		<div class="aui-w70 portlet-column portlet-column-last" id="column-2">
			$processor.processColumn("column-2", "portlet-column-content portlet-column-content-last")
		</div>
	</div>
</div>
#end