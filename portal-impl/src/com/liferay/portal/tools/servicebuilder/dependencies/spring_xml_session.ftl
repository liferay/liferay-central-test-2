<bean id="${packagePath}.service.${entity.name}${sessionType}Service.impl" class="${packagePath}.service.impl.${entity.name}${sessionType}ServiceImpl" />
<bean id="${packagePath}.service.${entity.name}${sessionType}Service.velocity" class="org.springframework.aop.framework.ProxyFactoryBean" parent="baseVelocityService">
	<property name="target" ref="${packagePath}.service.${entity.name}${sessionType}Service.impl" />
</bean>
<bean id="${packagePath}.service.${entity.name}${sessionType}ServiceUtil" class="${packagePath}.service.${entity.name}${sessionType}ServiceUtil">
	<property name="service" ref="${packagePath}.service.${entity.name}${sessionType}Service.impl" />
</bean>