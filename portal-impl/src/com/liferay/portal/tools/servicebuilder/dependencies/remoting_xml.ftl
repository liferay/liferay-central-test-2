<#if entity.TXManager != "none">
	<#assign serviceSuffix = "transaction">
<#else>
	<#assign serviceSuffix = "impl">
</#if>

<bean name="/${serviceMapping}-burlap" class="org.springframework.remoting.caucho.BurlapServiceExporter">
	<property name="service" ref="${serviceName}.${serviceSuffix}" />
	<property name="serviceInterface" value="${serviceName}" />
</bean>
<bean name="/${serviceMapping}-hessian" class="org.springframework.remoting.caucho.HessianServiceExporter">
	<property name="service" ref="${serviceName}.${serviceSuffix}" />
	<property name="serviceInterface" value="${serviceName}" />
</bean>
<bean name="/${serviceMapping}-http" class="org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter">
	<property name="service" ref="${serviceName}.${serviceSuffix}" />
	<property name="serviceInterface" value="${serviceName}" />
</bean>