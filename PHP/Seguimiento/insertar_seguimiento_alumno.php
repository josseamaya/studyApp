<?php
/**
 * Insertar un nuevo seguimiento en la base de datos
 */

require 'seguimiento_alumno.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    // Decodificando formato Json
    $body = json_decode(file_get_contents("php://input"), true);

    // Insertar seguimiento
    $retorno = Seguimiento::insert(
        $body['cod_padre'],
        $body['cod_alumno']);

    if ($retorno) {
        $json_string = json_encode(array("estado" => 1,"mensaje" => "Creacion correcta"));
		echo $json_string;
    } else {
        $json_string = json_encode(array("estado" => 2,"mensaje" => "No se creo el registro"));
		echo $json_string;
    }
}

?>
