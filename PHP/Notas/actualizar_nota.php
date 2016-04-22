<?php
/**
 * Actualiza un notas especificado por su identificador
 */

require 'notas.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    // Decodificando formato Json
    $body = json_decode(file_get_contents("php://input"), true);

    // Actualizar notas
    $retorno = Notas::update(
        $body['periodo'],
        $body['examen'],
        $body['acumulativo'],
        $body['cod_alumno'],
        $body['cod_asignatura']
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
