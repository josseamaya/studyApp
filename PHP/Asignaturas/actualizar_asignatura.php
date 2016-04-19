<?php
/**
 * Actualiza un asignatura especificado por su identificador
 */

require 'asignatura.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    // Decodificando formato Json
    $body = json_decode(file_get_contents("php://input"), true);

    // Actualizar asignatura
    $retorno = Asignatura::update(
        $body['cod_asignatura'],
        $body['nombre'],
        $body['horario'],
        $body['cod_maestro']
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
