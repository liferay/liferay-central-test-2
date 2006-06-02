JARs in this directory will be loaded by the application server's class loader.
If you find that these JARS are instead copied to the application's class
loader, then make sure the individual jar name is specified in the property
"classpath.global.ext" found in build.properties.