<#assign percentage = backgroundTaskDisplay.getPercentage() >

<div class="background-task-status-in-progress">
	<div class="active progress progress-striped progress-lg reindex-progress">
		<div class="progress-bar" style="width:${percentage}%">
			<span class="progress-percentage">${percentage}%</span>
		</div>
	</div>
</div>