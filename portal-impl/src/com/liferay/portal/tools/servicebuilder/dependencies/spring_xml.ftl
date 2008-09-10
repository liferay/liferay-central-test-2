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
		<#if (entity.dataSource != "liferayDataSource") || (entity.sessionFactory != "liferaySessionFactory")>
			<bean id="${packagePath}.service.persistence.${entity.name}Persistence.impl" class="${entity.getPersistenceClass()}" parent="basePersistence">
				<#if entity.dataSource != "liferayDataSource">
					<property name="dataSource" ref="${entity.getDataSource()}" />
				</#if>

				<#if entity.sessionFactory != "liferaySessionFactory" >
					<property name="sessionFactory" ref="${entity.getSessionFactory()}" />
				</#if>
			</bean>
		<#else>
			<bean id="${packagePath}.service.persistence.${entity.name}Persistence.impl" class="${entity.getPersistenceClass()}" parent="basePersistence" />		
		</#if>

		<bean id="${packagePath}.service.persistence.${entity.name}Util" class="${packagePath}.service.persistence.${entity.name}Util" >
			<property name="persistence" ref="${packagePath}.service.persistence.${entity.name}Persistence.impl" />
		</bean>
	</#if>

	<#if entity.hasFinderClass()>
		<#if (entity.dataSource != "liferayDataSource") || (entity.sessionFactory != "liferaySessionFactory")>
			<bean id="${packagePath}.service.persistence.${entity.name}Finder.impl" class="${entity.finderClass}" parent="basePersistence">
				<#if entity.dataSource != "liferayDataSource">
					<property name="dataSource" ref="${entity.getDataSource()}" />
				</#if>

				<#if entity.sessionFactory != "liferaySessionFactory" >
					<property name="sessionFactory" ref="${entity.getSessionFactory()}" />
				</#if>
			</bean>
		<#else>
			<bean id="${packagePath}.service.persistence.${entity.name}Finder.impl" class="${entity.finderClass}" parent="basePersistence" />
		</#if>

		<bean id="${packagePath}.service.persistence.${entity.name}FinderUtil" class="${packagePath}.service.persistence.${entity.name}FinderUtil">
			<property name="finder" ref="${packagePath}.service.persistence.${entity.name}Finder.impl" />
		</bean>
	</#if>
</#list>