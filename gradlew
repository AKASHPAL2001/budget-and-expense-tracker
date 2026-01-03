#!/usr/bin/env sh

# Gradle wrapper startup script

DIR="$(cd "$(dirname "$0")" && pwd)"
GRADLEW_JAR="$DIR/gradle/wrapper/gradle-wrapper.jar"

if [ ! -f "$GRADLEW_JAR" ]; then
  echo "Gradle wrapper JAR not found at $GRADLEW_JAR. Please regenerate the wrapper (e.g., from Android Studio or 'gradle wrapper')."
  exit 1
fi

JAVA_EXEC="${JAVA_HOME:-}/bin/java"
if [ ! -x "$JAVA_EXEC" ]; then
  JAVA_EXEC="java"
fi

CLASSPATH="$GRADLEW_JAR"

exec "$JAVA_EXEC" -Dorg.gradle.appname=gradlew -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
