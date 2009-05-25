<#list entities as entity>
	<import class="${packagePath}.model.${entity.name}" />
</#list>

<#list entities as entity>
	<#if entity.hasColumns()>
		<class name="${packagePath}.model.impl.${entity.name}Impl" table="${entity.table}">
			<#if entity.isCacheEnabled()>
				<cache usage="read-write" />
			</#if>

			<#if entity.hasCompoundPK()>
				<composite-id name="primaryKey" class="${packagePath}.service.persistence.${entity.name}PK">
					<#assign pkList = entity.getPKList()>

					<#list pkList as column>
						<key-property name="${column.name}"

						<#if column.name != column.DBName>
							column="${column.DBName}"
						</#if>

						<#if column.isPrimitiveType() || column.type == "String">
							type="com.liferay.portal.dao.orm.hibernate.${serviceBuilder.getPrimitiveObj("${column.type}")}Type"
						</#if>

						<#if column.type == "Date">
							type="org.hibernate.type.TimestampType"
						</#if>

						/>
					</#list>
				</composite-id>
			<#else>
				<#assign column = entity.getPKList()?first>

				<id name="${column.name}"
					<#if column.name != column.DBName>
						column="${column.DBName}"
					</#if>

					type="<#if !entity.hasPrimitivePK()>java.lang.</#if>${column.type}">

					<#if column.idType??>
						<#assign class = serviceBuilder.getGeneratorClass("${column.idType}")>

						<#if class == "class">
							<#assign class = column.idParam>
						</#if>
					<#else>
						<#assign class = "assigned">
					</#if>

					<generator class="${class}"

					<#if class == "sequence">
							><param name="sequence">${column.idParam}</param>
						</generator>
					<#else>
						/>
					</#if>
				</id>
			</#if>

			<#list entity.columnList as column>
				<#if column.EJBName??>
					<#assign ejbName = true>
				<#else>
					<#assign ejbName = false>
				</#if>

				<#if !column.isPrimary() && !column.isCollection() && !ejbName>
					<property name="${column.name}"

					<#if column.name != column.DBName>
						column="${column.DBName}"
					</#if>

					<#if column.isPrimitiveType() || column.type == "String">
						type="com.liferay.portal.dao.orm.hibernate.${serviceBuilder.getPrimitiveObj("${column.type}")}Type"
					</#if>

					<#if column.type == "Date">
						type="org.hibernate.type.TimestampType"
					</#if>

					/>
				</#if>
			</#list>
		</class>
	</#if>
</#list>