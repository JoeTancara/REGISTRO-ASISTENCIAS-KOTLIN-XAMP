<?php
if($_SERVER["REQUEST_METHOD"]=="GET"){
    require_once '../conexion.php';
    $id_asistencia=$_GET['id_asistencia'];
    $query="SELECT * FROM asistencias WHERE id_asistencia='".$id_asistencia."'";
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