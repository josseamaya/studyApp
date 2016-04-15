<?php
/**
 * Actualiza un maestro especificado por su identificador
 */

require 'maestro.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    // Decodificando formato Json
    $body = json_decode(file_get_contents("php://input"), true);

    // Actualizar alumno
    $retorno = Maestro::update(
        $body['cod_maestro'],
        $body['nombre'],
        $body['apellido'],
        $body['email'],
        $body['telefono'],
        $body['password']
      );

    if ($retorno) {
        $json_string = json_encode(array("estado" => 1,"mensaje" => "Actualizacion correcta"));
		echo $json_string;
    } else {
        $json_string = json_encode(array("estado" => 2,"mensaje" => "No se actualizo el registro"));
		echo $json_string;
    }
}
?>
