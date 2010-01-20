<?xml version="1.0" encoding="UTF-8"?>

<beans
	xmlns="http://www.springframework.org/schema/beans"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-2.5.xsd http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd"
>
	<bean id="com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil" class="com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil">
		<property name="mappingSqlQueryFactory">
			<bean class="com.liferay.portal.dao.shard.ShardMappingSqlQueryFactoryImpl" />
		</property>
	</bean>
	<bean id="com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil" class="com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil">
		<property name="sqlUpdateFactory">
			<bean class="com.liferay.portal.dao.shard.ShardSqlUpdateFactoryImpl" />
		</property>
	</bean>
	<bean id="com.liferay.portal.dao.shard.ShardAdvice" class="com.liferay.portal.dao.shard.ShardAdvice">
		<property name="shardDataSourceTargetSource">
			<bean class="com.liferay.portal.kernel.util.InfrastructureUtil" factory-method="getShardDataSourceTargetSource" />
		</property>
		<property name="shardSessionFactoryTargetSource">
			<bean class="com.liferay.portal.kernel.util.InfrastructureUtil" factory-method="getShardSessionFactoryTargetSource" />
		</property>
	</bean>
	<bean id="com.liferay.portal.dao.shard.ShardUtil" class="com.liferay.portal.dao.shard.ShardUtil">
		<property name="shardAdvice" ref="com.liferay.portal.dao.shard.ShardAdvice" />
	</bean>
	<aop:config>
		<aop:aspect ref="com.liferay.portal.dao.shard.ShardAdvice">
			<aop:around pointcut="bean(*Persistence) || bean(*Finder)" method="invokePersistence" />
		</aop:aspect>
	</aop:config>
</beans>