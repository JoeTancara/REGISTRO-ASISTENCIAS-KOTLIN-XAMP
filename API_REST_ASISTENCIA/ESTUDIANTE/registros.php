<?php
if($_SERVER['REQUEST_METHOD']=="GET"){
    require_once '../conexion.php';
    $query = "SELECT * FROM estudiantes";
    $resultado = $mysql->query($query);
    $json = ""; 
    if($mysql->affected_rows>0){
        $json ="{\"data\":[";
        while($row = $resultado->fetch_assoc()){
            $json = $json.json_encode($row);
            $json = $json.",";
        }
        $json = substr(trim($json), 0, -1);
        $json = $json."]}";
    }
    echo $json;
    $resultado->close();
    $mysql->close();
}
?>