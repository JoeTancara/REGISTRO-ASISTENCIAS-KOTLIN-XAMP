<?php 
if($_SERVER['REQUEST_METHOD']=='POST'){
    require_once '../conexion.php';
    $fecha = $_POST['fecha'];
    $estado = $_POST['estado'];
    $cod_estudiante = $_POST['cod_estudiante'];
    $nombre_materia = $_POST['nombre_materia'];
    
    $stmt = $mysql->prepare("INSERT INTO asistencias (fecha, estado, cod_estudiante, nombre_materia) VALUES (?, ?, ?, ?)");
    $stmt->bind_param("ssss", $fecha, $estado, $cod_estudiante, $nombre_materia);
    
    if($stmt->execute()){
        echo "Registro de asistencia exitoso";
    }else{
        echo "Error al registrar asistencia";
    }
    
    $stmt->close();
    $mysql->close();
}
?>