#!/bin/bash
# Shared script for appcfg.sh, dev_appserver.sh, and endpoints.sh.
# Checks that the java command reports at least the minimum required version
# before running it. The arguments to this script are, first, the name of
# the script we are doing this for (for example appcfg.sh), then all of the
# arguments to be passed to the java binary.

readonly SCRIPT_NAME="$1"
shift

readonly VERSION=$(java -version 2>&1 \
    | sed -n '1s/.*version.*1\.\([0-9]\).*/\1/p')
case "${VERSION}" in
7)
  ;;

[1-6])
  cat >&2 <<EOF
${SCRIPT_NAME} requires at least Java 7 (also known as 1.7).

The java executable at $(type -p java) reports:
$(java -version 2>&1)

You can download the latest JDK from:
  http://www.oracle.com/technetwork/java/javase/downloads/index.html
EOF
  exit 1;;

[89])
  cat >&2 <<EOF
${SCRIPT_NAME} should ideally be run using Java 7 (also known as 1.7).

The java executable at $(type -p java) reports:
$(java -version 2>&1)

Running a more recent version of Java can lead to apps that are apparently
correct but do not work when uploaded to App Engine.

You can download JDK 7 from:
  http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html
EOF
  ;;

*)
  echo "Warning: unable to determine Java version" >&2;;
esac

exec java "$@"
