<#list entities as entity>
	<#if entity.hasLocalService()>
		<#assign sessionType = "Local">

		<#include "spring_xml_session.ftl">
	</#if>
	<#if entity.hasRemoteService()>
		<#assign sessionType = "">

		<#include "spring_xml_session.ftl">
	</#if>

	<#if entity.hasColumns()>
		<bean id="${packagePath}.service.persistence.${entity.name}PersistenceImpl" class="${entity.getPersistenceClass()}" lazy-init="true">
			<property name="dataSource">
				<ref bean="${entity.getDataSource()}" />
			</property>
			<property name="sessionFactory">
				<ref bean="${entity.getSessionFactory()}" />
			</property>
		</bean>
		<bean id="${packagePath}.service.persistence.${entity.name}Util" class="${packagePath}.service.persistence.${entity.name}Util" lazy-init="true">
			<property name="persistence">
				<ref bean="${packagePath}.service.persistence.${entity.name}PersistenceImpl" />
			</property>
		</bean>
	</#if>

	<#if entity.hasFinderClass()>
		<bean id="${packagePath}.service.persistence.${entity.name}FinderImpl" class="${entity.finderClass}" lazy-init="true" />
		<bean id="${packagePath}.service.persistence.${entity.name}FinderUtil" class="${packagePath}.service.persistence.${entity.name}FinderUtil" lazy-init="true">
			<property name="finder">
				<ref bean="${packagePath}.service.persistence.${entity.name}FinderImpl" />
			</property>
		</bean>
	</#if>
</#list>