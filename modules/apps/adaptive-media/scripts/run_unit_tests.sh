#! /bin/sh

set -e

run_tests()
{
	./scripts/run.sh ./gradlew compileJava compileTestJava test
}

assert_success()
{
	! find . -type f -name 'TEST-*.xml' -exec grep -F '<failure' {} \+ > /dev/null
}

run_tests && assert_success