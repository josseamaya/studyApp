<?php
/**
 * Insertar un nuevo notas en la base de datos
 */

require 'notas.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    // Decodificando formato Json
    $body = json_decode(file_get_contents("php://input"), true);

    // Insertar notas
    $retorno = Notas::insert(
    $body['cod_notas'],
    $body['periodo'],
    $body['examen'],
    $body['acumulativo'],
    $body['cod_alumno'],
    $body['cod_asignatura']);

    if ($retorno) {
        $json_string = json_encode(array("estado" => 1,"mensaje" => "Creacion correcta"));
		echo $json_string;
    } else {
        $json_string = json_encode(array("estado" => 2,"mensaje" => "No se creo el registro"));
		echo $json_string;
    }
}

?>
