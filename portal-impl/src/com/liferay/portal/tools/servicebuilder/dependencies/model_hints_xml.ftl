<#list entities as entity>
	<#assign modelName = packagePath + ".model." + entity.name>

	<#if entity.hasColumns()>
		<model name="${modelName}">
			<#assign defaultHints = modelHintsUtil.getDefaultHints(modelName)>

			<#if defaultHints??>
				<#if defaultHints?keys?size gt 0>
					<default-hints>
						<#list defaultHints?keys as defaultHintKey>
							<hint name="${defaultHintKey}">${defaultHints[defaultHintKey]}</hint>
						</#list>
					</default-hints>
				</#if>
			</#if>

			<#list entity.columnList as column>
				<#if !column.isCollection()>
					<field name="${column.name}" type="${column.type}"

					<#if column.localized>
						localized="true"
					</#if>

					<#assign closeField = false>

					<#if modelHintsUtil.getFieldsEl(modelName, column.name)??>
						<#assign field = modelHintsUtil.getFieldsEl(modelName, column.name)>
						<#assign hints = field.elements()>

						<#if hints?size gt 0>
							<#assign closeField = true>
						</#if>
					</#if>

					<#if modelHintsUtil.getSanitizeTuple(modelName, column.name)??>
						<#assign closeField = true>
					</#if>

					<#if modelHintsUtil.getValidators(modelName, column.name)??>
						<#assign closeField = true>
					</#if>

					<#if closeField>
						>

						<#if modelHintsUtil.getFieldsEl(modelName, column.name)??>
							<#assign field = modelHintsUtil.getFieldsEl(modelName, column.name)>
							<#assign hints = field.elements()>

							<#list hints as hint>
								<#if hint.name == "hint">
									<hint name="${hint.attributeValue("name")}">${hint.text}</hint>
								<#elseif hint.name == "hint-collection">
									<hint-collection name="${hint.attributeValue("name")}" />
								</#if>
							</#list>
						</#if>

						<#if modelHintsUtil.getSanitizeTuple(modelName, column.name)??>
							<#assign sanitizeTuple = modelHintsUtil.getSanitizeTuple(modelName, column.name)>

							<#assign contentType = sanitizeTuple.getObject(1)>
							<#assign modes = sanitizeTuple.getObject(2)>

							<sanitize content-type="${contentType}" modes="${modes}" />
						</#if>

					    <#if modelHintsUtil.getValidators(modelName, column.name)??>
							<#assign validatorTuple = modelHintsUtil.getValidators(modelName, column.name)>

							<#assign name = validatorTuple.getObject(1)>
							<#assign errorMessage = validatorTuple.getObject(2)>
							<#assign value = validatorTuple.getObject(3)>

							<validator error-message="" name="${name}"

							<#if value>
								>

							    	${value}

								</validator>
						    <#else>
						    	/>
							</#if>
						</#if>

						</field>
					<#else>
						/>
					</#if>
				</#if>
			</#list>
		</model>
	</#if>
</#list>