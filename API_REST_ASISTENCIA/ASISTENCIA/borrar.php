<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once '../conexion.php';
    $id_asistencia = $_POST['id_asistencia'];
    $query = "DELETE FROM asistencias WHERE id_asistencia=?";
    $stmt = $mysql->prepare($query);
    $stmt->bind_param("i", $id_asistencia);
    
    if($stmt->execute()){
        echo "Registro eliminado";
    }else{
        echo "Error al eliminar";
    }
    
    $stmt->close();
    $mysql->close();
}
?>