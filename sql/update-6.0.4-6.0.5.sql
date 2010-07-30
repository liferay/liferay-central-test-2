create table ClusterGroup (
	clusterGroupId LONG not null primary key,
	clusterNodeIds VARCHAR(75) null,
	wholeCluster BOOLEAN
);

alter table User_ add digest VARCHAR(256) null;