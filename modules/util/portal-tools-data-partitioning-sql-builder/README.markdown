# Partitioning Tool

The data partitioning tool allows you to physically separate one portal
instance from an already existing `logically partitioned` Liferay Portal.

What does it mean `logically partitioned`? It means you have several companies
in the same database, and you want to move a few of them to another
database. If you'd query the `Company` table, you'd have more than one row
there.

Depending on the `-W|--write-file` optional parameter, the output of the tool
will be one or several SQL files, a single file or one per table on your
current Liferay database, **you have to execute on your target database**.
After that, just start a completely different Liferay installation configured
to use this separated database to complete the separation. 

It's **important** to say that this tool does not allow moving one portal
instance to another database manager system (DBMS), because its output will
always be SQL code for the underlying DBMS. See it as an utility to isolate
companies' data into SQL files for the only purpose of moving them to
separated databases. Nothing more, nothing less.

## Executing the tool

To execute this tool, you must add both your JDBC driver and your database data
partitioning module (MySQL, PostgreSQL, Oracle, DB2, Sybase, SQL Server) to the
execution classpath, so that the tool can successfully perform its operations.

Once the classpath is satisfied, you must provide **four or five arguments** to the
tool:

* The properties file with your database configuration, following the same
    structure as in the sample files. (Required)
    
* The name of the schema you want to export. (Required)

* The ID of the Company/Shard you want to export, described by `companyId`
    field. (Required)
	
* The output directory for the generated SQL files, representing the `INSERT`
    statements to execute in the new database. (Required)
	
* Whether the export process generates one SQL file per table type (control or
    partitioned) for the whole database or one SQL file per table. If no
    argument is specified, only one file per table type will be generated.
    (Optional)

The command to execute the tool should be similar to this:

    java -classpath "PATH-TO-LIBS/*:/PATH-TO-TOOL/com.liferay.portal.tools.data.partitioning.sql.builder-1.0.0.jar" com.liferay.portal.tools.data.partitioning.sql.builder.Main [-P|--properties-file] PATH-TO-DB-PROPERTIES [-S|--schema-name] SCHEMA_NAME [-C|--company-ids] COMMA-SEPARATED-COMPANY_IDS [-O|--output-dir] OUTPUT-DIR [-W|--write-file]

Remember that you should use colon (:) as file separator on Unix-like machines,
and semicolon (;) on Windows machines.

Here are examples of executing the tool using MySQL as the DB provider:

    java -classpath "/opt/jdbc-drivers/*:com.liferay.portal.tools.data.partitioning.sql.builder-1.0.0.jar" com.liferay.portal.tools.data.partitioning.sql.builder.Main -P ~/shardingTool/mysql.properties -S lportal -C 20156 -O /tmp [-W]
    java -classpath "/opt/jdbc-drivers/*:com.liferay.portal.tools.data.partitioning.sql.builder-1.0.0.jar" -jar com.liferay.portal.tools.data.partitioning.sql.builder.Main --properties-file ~/shardingTool/mysql.properties --schema-name lportal --company-ids 20156 --output-dir /tmp [--write-file]

## Testing

To execute the tests, add a file for each database provider you want to verify
the tool against.

Under the `src` directory, we have provided sample configuration files for each
database provider; they're references for defining custom test environments.
Just copy them to the `test/resources` directory and remove the`sample-` prefix,
so test suites are able to find the configuration to set up a database
connection for each provider.