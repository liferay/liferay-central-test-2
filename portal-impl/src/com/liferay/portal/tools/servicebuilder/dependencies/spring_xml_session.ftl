<bean id="${packagePath}.service.${entity.name}${sessionType}Service.impl" class="${packagePath}.service.impl.${entity.name}${sessionType}ServiceImpl" />
<bean id="${packagePath}.service.${entity.name}${sessionType}ServiceUtil" class="${packagePath}.service.${entity.name}${sessionType}ServiceUtil">
	<property name="service" ref="${packagePath}.service.${entity.name}${sessionType}Service.impl" />
</bean>