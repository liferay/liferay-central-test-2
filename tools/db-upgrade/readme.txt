Modify portal-upgrade-ext.properties with your custom settings so that the
upgrade tool can connect your database. All Liferay servers must be turned off
when performing an upgrade.

Put any required JDBC drivers into the lib directory. JAR files in that
directory will be available to the upgrade tool.

The upgrade tool requires Ant. Please refer to the Ant documentation on how to
setup Ant for your environment.

To upgrade your Liferay database, execute "ant upgrade".