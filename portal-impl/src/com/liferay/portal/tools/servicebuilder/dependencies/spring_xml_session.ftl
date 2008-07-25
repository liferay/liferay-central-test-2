<bean id="${packagePath}.service.${entity.name}${sessionType}Service.impl" class="${packagePath}.service.impl.${entity.name}${sessionType}ServiceImpl" lazy-init="true" />

<#if entity.TXManager != "none">
	<bean id="${packagePath}.service.${entity.name}${sessionType}Service.transaction" class="org.springframework.transaction.interceptor.TransactionProxyFactoryBean" lazy-init="true">
		<property name="transactionManager">
			<ref bean="${entity.TXManager}" />
		</property>
		<property name="target">
			<ref bean="${packagePath}.service.${entity.name}${sessionType}Service.impl" />
		</property>
		<property name="transactionAttributes">
			<props>
				<#assign txRequiredList = entity.getTxRequiredList()>

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

<bean id="${packagePath}.service.${entity.name}${sessionType}Service.exceptionSafe" class="org.springframework.aop.framework.ProxyFactoryBean" lazy-init="true">
	<property name="proxyInterfaces">
		<value>${packagePath}.service.${entity.name}${sessionType}Service</value>
	</property>
	<property name="interceptorNames">
		<list>
			<value>com.liferay.portal.spring.aop.ExceptionSafeInterceptor</value>
		</list>
	</property>
	<property name="target">
		<#if entity.TXManager != "none">
			<ref bean="${packagePath}.service.${entity.name}${sessionType}Service.transaction" />
		<#else>
			<ref bean="${packagePath}.service.${entity.name}${sessionType}Service.impl" />
		</#if>
	</property>
</bean>
<bean id="${packagePath}.service.${entity.name}${sessionType}ServiceFactory" class="${packagePath}.service.${entity.name}${sessionType}ServiceFactory" lazy-init="true">
	<property name="service">
		<#if entity.TXManager != "none">
			<ref bean="${packagePath}.service.${entity.name}${sessionType}Service.transaction" />
		<#else>
			<ref bean="${packagePath}.service.${entity.name}${sessionType}Service.impl" />
		</#if>
	</property>
</bean>