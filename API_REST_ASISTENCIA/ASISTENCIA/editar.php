<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once '../conexion.php';
    $id_asistencia = $_POST['id_asistencia'];
    $fecha = $_POST['fecha'];
    $estado = $_POST['estado'];
    $cod_estudiante = $_POST['cod_estudiante'];
    $nombre_materia = $_POST['nombre_materia'];
    
    $query = "UPDATE asistencias SET fecha=?, estado=?, cod_estudiante=?, nombre_materia=? WHERE id_asistencia=?";
    $stmt = $mysql->prepare($query);
    $stmt->bind_param("ssssi", $fecha, $estado, $cod_estudiante, $nombre_materia, $id_asistencia);
    
    if($stmt->execute()){
        echo "Datos actualizados correctamente";
    }else{ 
        echo "Error al actualizar los datos";
    }
    
    $stmt->close();
    $mysql->close();
}
?>