<?php 
if($_SERVER['REQUEST_METHOD']=='POST'){
    require_once '../conexion.php';
    $nombre = $_POST['nombre'];
    $apellido = $_POST['apellido'];
    $grado = $_POST['grado'];
    $curso = $_POST['curso'];
    $codigo = $_POST['codigo'];
    
    $stmt = $mysql->prepare("INSERT INTO estudiantes (nombre, apellido, grado, curso, codigo) VALUES (?, ?, ?, ?, ?)");
    $stmt->bind_param("sssss", $nombre, $apellido, $grado, $curso, $codigo);
    
    if($stmt->execute()){
        echo "Registro exitoso";
    }else{
        echo "Error al registrar";
    }
    
    $stmt->close();
    $mysql->close();
}
?>