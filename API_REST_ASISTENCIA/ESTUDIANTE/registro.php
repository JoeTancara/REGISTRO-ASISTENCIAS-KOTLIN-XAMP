<?php
if($_SERVER["REQUEST_METHOD"]=="GET"){
    require_once '../conexion.php';
    $id_estudiante=$_GET['id_estudiante'];
    $query="SELECT * FROM estudiantes WHERE id_estudiante='".$id_estudiante."'";
    $resultado=$mysql->query($query);
    if($mysql->affected_rows>0){
        while($row=$resultado->fetch_assoc()){
            $array=$row;
        }
        echo json_encode($array);
    }else{
        echo "No se encontraron resultados";
    }
    $resultado->close();
    $mysql->close();
}
?>