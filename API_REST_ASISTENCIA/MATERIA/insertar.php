<?php 
if($_SERVER['REQUEST_METHOD']=='POST'){
    require_once '../conexion.php';
    $nombre_materia = $_POST['nombre_materia'];
    $profesor = $_POST['profesor'];
    $horario = $_POST['horario'];
    
    $stmt = $mysql->prepare("INSERT INTO materias (nombre_materia, profesor, horario) VALUES (?, ?, ?)");
    $stmt->bind_param("sss", $nombre_materia, $profesor, $horario);
    
    if($stmt->execute()){
        echo "Registro exitoso";
    }else{
        echo "Error al registrar";
    }
    
    $stmt->close();
    $mysql->close();
}
?>