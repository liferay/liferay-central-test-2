<#if entity.TXManager == "none">
	<bean id="${packagePath}.service.${entity.name}${sessionType}Service.impl" class="${packagePath}.service.impl.${entity.name}${sessionType}ServiceImpl" primary="true" autowire="byType" />
<#else>
	<#assign txRequiredList = entity.getTxRequiredList()>

	<#if pluginName == "">
		<bean id="${packagePath}.service.${entity.name}${sessionType}Service.impl" class="${packagePath}.service.impl.${entity.name}${sessionType}ServiceImpl" />
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
	<#else>
		<bean id="${packagePath}.service.${entity.name}${sessionType}Service.impl" class="org.springframework.transaction.interceptor.TransactionProxyFactoryBean">
			<property name="transactionManager">
				<ref bean="${entity.TXManager}" />
			</property>
			<property name="target">
				<bean class="${packagePath}.service.impl.${entity.name}${sessionType}ServiceImpl" />
			</property>
			<property name="transactionAttributes">
				<props>
					<#list txRequiredList as txRequired>
						<prop key="${txRequired}">PROPAGATION_REQUIRED,-com.liferay.portal.PortalException,-com.liferay.portal.SystemException</prop>
					</#list>

					<prop key="add*">PROPAGATION_REQUIRED,-com.liferay.portal.PortalException,-com.liferay.portal.SystemException</prop>
					<prop key="check*">PROPAGATION_REQUIRED,-com.liferay.portal.PortalException,-com.liferay.portal.SystemException</prop>
					<prop key="clear*">PROPAGATION_REQUIRED,-com.liferay.portal.PortalException,-com.liferay.portal.SystemException</prop>
					<prop key="delete*">PROPAGATION_REQUIRED,-com.liferay.portal.PortalException,-com.liferay.portal.SystemException</prop>
					<prop key="set*">PROPAGATION_REQUIRED,-com.liferay.portal.PortalException,-com.liferay.portal.SystemException</prop>
					<prop key="update*">PROPAGATION_REQUIRED,-com.liferay.portal.PortalException,-com.liferay.portal.SystemException</prop>
					<prop key="*">PROPAGATION_SUPPORTS,readOnly</prop>
				</props>
			</property>
		</bean>
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