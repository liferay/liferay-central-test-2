<div class="portlet-layout columns-1-2" id="portlet-layout">
	<table class="portlet-layout">
		<tr>
			<td class="portlet-column portlet-column-only" id="column-1">
				$processor.processColumn("column-1", "portlet-column-content portlet-column-only-content")
			</td>
		</tr>
	</table>
	<table class="portlet-layout">
	<tr>
		<td class="aui-w30 portlet-column portlet-column-first" id="column-2">
			$processor.processColumn("column-2", "portlet-column-content portlet-column-first-content")
		</td>
		<td class="aui-w70 portlet-column portlet-column-last" id="column-3">
			$processor.processColumn("column-3", "portlet-column-content portlet-column-last-content")
		</td>
	</tr>
	</table>
</div>