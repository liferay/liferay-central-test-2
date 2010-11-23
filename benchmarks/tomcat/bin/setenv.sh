# JMX setting
JAVA_OPTS="$JAVA_OPTS -server -Dcom.sun.management.jmxremote=true -Dcom.sun.management.jmxremote.port=5000 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"

# Heap layout setting
#JAVA_OPTS="$JAVA_OPTS -XX:NewSize=3072m -XX:MaxNewSize=3072m -Xms6144m -Xmx6144m -XX:PermSize=200m -XX:MaxPermSize=200m -XX:SurvivorRatio=65536 -XX:TargetSurvivorRatio=0 -XX:MaxTenuringThreshold=0"
JAVA_OPTS="$JAVA_OPTS -XX:NewSize=700m -XX:MaxNewSize=700m -Xms2048m -Xmx2048m -XX:PermSize=200m -XX:MaxPermSize=200m -XX:SurvivorRatio=65536 -XX:TargetSurvivorRatio=0 -XX:MaxTenuringThreshold=0"

# Parallel setting
#JAVA_OPTS="$JAVA_OPTS -XX:+UseParNewGC -XX:ParallelGCThreads=16"
JAVA_OPTS="$JAVA_OPTS -XX:+UseParNewGC -XX:ParallelGCThreads=12"

# CMS setting
JAVA_OPTS="$JAVA_OPTS  -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+CMSCompactWhenClearAllSoftRefs -XX:CMSInitiatingOccupancyFraction=85 -XX:+CMSScavengeBeforeRemark"
# -XX:+CMSConcurrentMTEnabled -XX:ParallelCMSThreads=8"

# Misc setting
#JAVA_OPTS="$JAVA_OPTS -XX:+UseLargePages -XX:LargePageSizeInBytes=2m -XX:+UseCompressedOops -XX:+DisableExplicitGC"
JAVA_OPTS="$JAVA_OPTS -XX:+UseCompressedOops -XX:+DisableExplicitGC"

# Logging
JAVA_OPTS="$JAVA_OPTS -verbose:gc -Xloggc:/home/@portal.username@/portal-workdir/portal-gc.log -XX:+PrintGCDetails"

export JAVA_OPTS
