<bean id="${packagePath}.service.${entity.name}${sessionType}Service.impl" class="${packagePath}.service.impl.${entity.name}${sessionType}ServiceImpl" />
<alias name="${packagePath}.service.${entity.name}${sessionType}Service.impl" alias="${packagePath}.service.${entity.name}${sessionType}Service.velocity" />
<bean id="${packagePath}.service.${entity.name}${sessionType}ServiceUtil" class="${packagePath}.service.${entity.name}${sessionType}ServiceUtil">
	<property name="service" ref="${packagePath}.service.${entity.name}${sessionType}Service.impl" />
</bean>