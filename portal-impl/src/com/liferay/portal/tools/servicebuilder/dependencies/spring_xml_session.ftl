<bean id="${packagePath}.service.${entity.name}${sessionType}Service.impl" class="${packagePath}.service.impl.${entity.name}${sessionType}ServiceImpl" parent="${packagePath}.service.${portletShortName}.base">
	<#list entity.referenceList as tempEntity>
		<#if tempEntity.hasLocalService()>
			<property name="${tempEntity.springPropertyName}LocalService" ref="${tempEntity.packagePath}.service.${tempEntity.name}LocalService.impl" />
		</#if>

		<#if tempEntity.hasRemoteService()>
			<property name="${tempEntity.springPropertyName}Service" ref="${tempEntity.packagePath}.service.${tempEntity.name}Service.impl" />
		</#if>

		<#if tempEntity.hasColumns()>
			<property name="${tempEntity.springPropertyName}Persistence" ref="${tempEntity.packagePath}.service.persistence.${tempEntity.name}Persistence.impl" />
		</#if>

		<#if tempEntity.hasFinderClass()>
			<property name="${tempEntity.springPropertyName}Finder" ref="${tempEntity.packagePath}.service.persistence.${tempEntity.name}Finder.impl" />
		</#if>
	</#list>
</bean>

<#if entity.TXManager != "none">
	<#assign txRequiredList = entity.getTxRequiredList()>

	<#if txRequredList?? && (txRequiredList.size > 0)>
		<aop:config>
			<aop:pointcut id="${packagePath}.service.${entity.name}${sessionType}Service.operation" expression="bean(${packagePath}.service.${entity.name}${sessionType}Service.transaction)" />
			<aop:advisor advice-ref="${packagePath}.service.${entity.name}${sessionType}Service.advice" pointcut-ref="${packagePath}.service.${entity.name}${sessionType}Service.operation" />
		</aop:config>
		<tx:advice id="${packagePath}.service.${entity.name}${sessionType}Service.advice" transaction-manager="liferayTransactionManager">
			<tx:attributes>
				<#list txRequiredList as txRequired>
					<tx:method name="${txRequired}" propagation="REQUIRED" rollback-for="com.liferay.portal.PortalException,com.liferay.portal.SystemException" />
				</#list>
			</tx:attributes>
		</tx:advice>
	</#if>
</#if>

<bean id="${packagePath}.service.${entity.name}${sessionType}Service.velocity" class="org.springframework.aop.framework.ProxyFactoryBean" parent="baseVelocityService">
	<property name="target" ref="${packagePath}.service.${entity.name}${sessionType}Service.impl" />
</bean>

<#if pluginName == "">
	<bean id="${packagePath}.service.${entity.name}${sessionType}ServiceFactory" class="${packagePath}.service.${entity.name}${sessionType}ServiceFactory">
		<property name="service" ref="${packagePath}.service.${entity.name}${sessionType}Service.impl" />
	</bean>
</#if>

<bean id="${packagePath}.service.${entity.name}${sessionType}ServiceUtil" class="${packagePath}.service.${entity.name}${sessionType}ServiceUtil">
	<property name="service" ref="${packagePath}.service.${entity.name}${sessionType}Service.impl" />
</bean>