<?php
/**
 * Insertar un nuevo padre en la base de datos
 */

require 'padre.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    // Decodificando formato Json
    $body = json_decode(file_get_contents("php://input"), true);

    // Insertar padre
    $retorno = Padre::insert(
        $body['cod_padre'],
        $body['nombre'],
        $body['apellido'],
        $body['email'],
        $body['telefono'],
        $body['password']);

    if ($retorno) {
        $json_string = json_encode(array("estado" => 1,"mensaje" => "Creacion correcta"));
		echo $json_string;
    } else {
        $json_string = json_encode(array("estado" => 2,"mensaje" => "No se creo el registro"));
		echo $json_string;
    }
}

?>
