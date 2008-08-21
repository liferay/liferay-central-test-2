<bean id="${packagePath}.service.${entity.name}${sessionType}Service.impl" class="${packagePath}.service.impl.${entity.name}${sessionType}ServiceImpl" />

<#if entity.TXManager != "none">
	<#assign txRequiredList = entity.getTxRequiredList()>

	<#if txRequredList?? && txRequiredList.size > 0>
		<aop:config>
			<aop:pointcut id="${packagePath}.service.${entity.name}${sessionType}Service.operation" expression="bean(${packagePath}.service.${entity.name}${sessionType}Service.transaction)"/>
			<aop:advisor advice-ref="${packagePath}.service.${entity.name}${sessionType}Service.advice" pointcut-ref="${packagePath}.service.${entity.name}${sessionType}Service.operation"/>
		</aop:config>
	
		<tx:advice id="${packagePath}.service.${entity.name}${sessionType}Service.advice" transaction-manager="liferayTransactionManager">
			<tx:attributes>
				<#list txRequiredList as txRequired>	
					<tx:method name="${txRequired}" propagation="REQUIRED" rollback-for="com.liferay.portal.PortalException,com.liferay.portal.SystemException"/>
				</#list>
	  		</tx:attributes>
		</tx:advice>
	</#if>
	<bean id="${packagePath}.service.${entity.name}${sessionType}Service.transaction" parent="${packagePath}.service.${entity.name}${sessionType}Service.impl" />
</#if>

<#if propsUtil == "com.liferay.portal.util.PropsUtil">
	<bean id="${packagePath}.service.${entity.name}${sessionType}Service.base" abstract="true">
		<#if entity.TXManager != "none">
			<property name="service" ref="${packagePath}.service.${entity.name}${sessionType}Service.transaction" />
		<#else>
			<property name="service" ref="${packagePath}.service.${entity.name}${sessionType}Service.impl" />
		</#if>
	</bean>
</#if>
<bean id="${packagePath}.service.${entity.name}${sessionType}Service.velocity" class="org.springframework.aop.framework.ProxyFactoryBean" parent="velocityBase" lazy-init="true">
	<#if entity.TXManager != "none">
		<property name="target" ref="${packagePath}.service.${entity.name}${sessionType}Service.transaction" />
	<#else>
		<property name="target" ref="${packagePath}.service.${entity.name}${sessionType}Service.impl" />
	</#if>
</bean>
<bean id="${packagePath}.service.${entity.name}${sessionType}ServiceFactory" class="${packagePath}.service.${entity.name}${sessionType}ServiceFactory" parent="${packagePath}.service.${entity.name}${sessionType}Service.base" />
<bean id="${packagePath}.service.${entity.name}${sessionType}ServiceUtil" class="${packagePath}.service.${entity.name}${sessionType}ServiceUtil" parent="${packagePath}.service.${entity.name}${sessionType}Service.base" />
