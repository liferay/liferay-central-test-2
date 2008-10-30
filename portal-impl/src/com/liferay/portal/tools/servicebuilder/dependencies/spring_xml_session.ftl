<bean id="${packagePath}.service.${entity.name}${sessionType}Service.impl" class="${packagePath}.service.impl.${entity.name}${sessionType}ServiceImpl" />
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