@include portal-tables.sql

COMMIT_TRANSACTION;

@include portal-data-common.sql

@include portal-data-counter.sql

@include portal-data-cms-content.sql

@include portal-data-cms-layout.sql

@include portal-data-company.sql

@include portal-data-company.vm

@include portal-data-image.sql

@include portal-data-release.sql

@include portal-data-shopping.sql

COMMIT_TRANSACTION;