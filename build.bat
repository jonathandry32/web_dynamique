set emplacement_jar=D:\desjava\framework\framework\build\framework.jar
set emplacement_src=D:\desjava\framework\test_framework\src\*.java
set emplacement_web=D:\desjava\framework\test_framework\web\*.jsp
set emplacement_xml=D:\desjava\framework\test_framework\web.xml
set emplacement_exec=C:\Users\ITU\Desktop\apache-tomcat-8.5.75\webapps\

mkdir dossier_a_envoyer
cd dossier_a_envoyer
mkdir WEB-INF
cd WEB-INF
mkdir classes
mkdir lib
mkdir files
cd ../../
cd framework
javac -d build/ src/*.java
cd build
jar -cfv framework.jar *
copy %emplacement_jar% D:\desjava\framework\dossier_a_envoyer\WEB-INF\lib\framework.jar
del %emplacement_jar%
copy %emplacement_src% D:\desjava\framework\dossier_a_envoyer\WEB-INF\files\
copy %emplacement_web% D:\desjava\framework\dossier_a_envoyer\
copy %emplacement_xml% D:\desjava\framework\dossier_a_envoyer\WEB-INF\
mkdir %emplacement_exec%\test_framework
xcopy D:\desjava\framework\dossier_a_envoyer %emplacement_exec%\test_framework /e
jar -cfv test_framework.war D:\desjava\framework\dossier_a_envoyer\* 
copy D:\desjava\framework\framework\build\test_framework.war %emplacement_exec%\test_framework.war
javac -d  C:/Users/ITU/Desktop/apache-tomcat-8.5.75/webapps/test_framework/WEB-INF/classes C:/Users/ITU/Desktop/apache-tomcat-8.5.75/webapps/test_framework/WEB-INF/files/*.java
del D:\desjava\framework\framework\build\test_framework.war
del /q /s D:\desjava\framework\dossier_a_envoyer
cd D:\desjava\framework\dossier_a_envoyer\WEB-INF\
rd lib
rd classes
rd files
cd ../
rd WEB-INF
cd ../
rd dossier_a_envoyer
