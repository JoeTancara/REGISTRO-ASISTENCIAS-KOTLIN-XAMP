<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once '../conexion.php';
    $id_materia = $_POST['id_materia'];
    $query = "DELETE FROM materias WHERE id_materia=?";
    $stmt = $mysql->prepare($query);
    $stmt->bind_param("i", $id_materia);
    
    if($stmt->execute()){
        echo "Registro eliminado";
    }else{
        echo "Error al eliminar";
    }
    
    $stmt->close();
    $mysql->close();
}
?>