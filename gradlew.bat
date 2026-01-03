@echo off
set DIR=%~dp0
set GRADLEW_JAR=%DIR%gradle\wrapper\gradle-wrapper.jar
if not exist "%GRADLEW_JAR%" (
  echo Gradle wrapper JAR not found at %GRADLEW_JAR%. Please regenerate the wrapper.
  exit /b 1
)
set JAVA_EXE=%JAVA_HOME%\bin\java.exe
if not exist "%JAVA_EXE%" set JAVA_EXE=java
set CLASSPATH=%GRADLEW_JAR%
"%JAVA_EXE%" -Dorg.gradle.appname=gradlew -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*
