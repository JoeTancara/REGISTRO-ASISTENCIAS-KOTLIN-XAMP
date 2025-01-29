<?php
if($_SERVER["REQUEST_METHOD"]=="GET"){
    require_once '../conexion.php';
    $id_materia=$_GET['id_materia'];
    $query="SELECT * FROM materias WHERE id_materia='".$id_materia."'";
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