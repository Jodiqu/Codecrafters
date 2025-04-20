Ejecutar y compilar

Dentro de la carpeta donde se encuentra /src:

compilar: javac -d out -cp lib\mysql-connector-j-9.3.0.jar src\app\*.java src\dao\*.java src\model\*.java src\util\*.java

ejecutar: java -cp "out;lib\mysql-connector-j-9.3.0.jar" app.ConsoleApp


Credenciales de acceso a MySQL

User y password en "/util/DBConnectionUtil.java"