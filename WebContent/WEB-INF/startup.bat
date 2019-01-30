echo off
rem setup classpath
echo set _CP=%%_CP%%;%%1> cp.bat
set _CP=.;.\classes
for %%i in (lib\*.jar) do call cp.bat %%i
set CLASSPATH=%_CP%
del cp.bat
echo %CLASSPATH%

set JAVA_RUN="%JAVA_HOME%\bin\java" -Dfile.encoding=GBK -Xmx256M -Xms64M
set RUN_CLASS=com.web.lottery.result.URLLotteryBatch
@echo on

%JAVA_RUN% -classpath %CLASSPATH% %RUN_CLASS%