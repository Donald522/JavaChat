@ECHO OFF
set CLASSPATH=.
set CLASSPATH=.\target\classes\

%JAVA_HOME%\bin\java -Xms128m -Xmx384m -Xnoclassgc demo.FirstClientReaderRunner
%JAVA_HOME%\bin\java -Xms128m -Xmx384m -Xnoclassgc demo.FirstClientWriterRunner