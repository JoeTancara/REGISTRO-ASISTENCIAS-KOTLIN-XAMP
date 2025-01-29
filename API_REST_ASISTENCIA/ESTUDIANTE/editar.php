<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once '../conexion.php';
    $id_estudiante = $_POST['id_estudiante'];
    $nombre = $_POST['nombre'];
    $apellido = $_POST['apellido'];
    $grado = $_POST['grado'];
    $curso = $_POST['curso'];
    $codigo = $_POST['codigo'];
    
    $query = "UPDATE estudiantes SET nombre=?, apellido=?, grado=?, curso=?, codigo=? WHERE id_estudiante=?";
    $stmt = $mysql->prepare($query);
    $stmt->bind_param("sssssi", $nombre, $apellido, $grado, $curso, $codigo, $id_estudiante);
    
    if($stmt->execute()){
        echo "Datos actualizados correctamente";
    }else{ 
        echo "Error al actualizar los datos";
    }
    
    $stmt->close();
    $mysql->close();
}
?>