<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once '../conexion.php';
    $id_estudiante = $_POST['id_estudiante'];
    $query = "DELETE FROM estudiantes WHERE id_estudiante=?";
    $stmt = $mysql->prepare($query);
    $stmt->bind_param("i", $id_estudiante);
    
    if($stmt->execute()){
        echo "Registro eliminado";
    }else{
        echo "Error al eliminar";
    }
    
    $stmt->close();
    $mysql->close();
}
?>