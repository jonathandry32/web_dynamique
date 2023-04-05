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
copy D:\desjava\framework\framework\build\framework.jar D:\desjava\framework\dossier_a_envoyer\WEB-INF\lib\framework.jar
del D:\desjava\framework\framework\build\framework.jar
copy D:\desjava\framework\test_framework\src\*.java D:\desjava\framework\dossier_a_envoyer\WEB-INF\files\
copy D:\desjava\framework\test_framework\web\*.jsp D:\desjava\framework\dossier_a_envoyer\
copy D:\desjava\framework\test_framework\web.xml D:\desjava\framework\dossier_a_envoyer\WEB-INF\
mkdir C:\Users\ITU\Desktop\apache-tomcat-8.5.75\webapps\test_framework
xcopy D:\desjava\framework\dossier_a_envoyer C:\Users\ITU\Desktop\apache-tomcat-8.5.75\webapps\test_framework /e
javac -d  C:/Users/ITU/Desktop/apache-tomcat-8.5.75/webapps/test_framework/WEB-INF/classes C:/Users/ITU/Desktop/apache-tomcat-8.5.75/webapps/test_framework/WEB-INF/files/*.java
del /q /s D:\desjava\framework\dossier_a_envoyer
cd D:\desjava\framework\dossier_a_envoyer\WEB-INF\ 
rd lib
rd classes
rd files
cd ../
rd WEB-INF
cd ../
rd dossier_a_envoyer

