<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once '../conexion.php';
    $id_materia = $_POST['id_materia'];
    $nombre_materia = $_POST['nombre_materia'];
    $profesor = $_POST['profesor'];
    $horario = $_POST['horario'];
    
    $query = "UPDATE materias SET nombre_materia=?, profesor=?, horario=? WHERE id_materia=?";
    $stmt = $mysql->prepare($query);
    $stmt->bind_param("sssi", $nombre_materia, $profesor, $horario, $id_materia);
    
    if($stmt->execute()){
        echo "Datos actualizados correctamente";
    }else{ 
        echo "Error al actualizar los datos";
    }
    
    $stmt->close();
    $mysql->close();
}
?>