<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "ra_sr";
$mysql = new mysqli($servername, $username, $password, $dbname);
if ($mysql->connect_error) {
    die("Erro de Conexion: " . $mysql->connect_error);
}else{
    // echo "Connexion exitosa";
}

?>