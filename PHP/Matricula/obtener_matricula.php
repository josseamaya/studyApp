<?php
/**
 * Obtiene todas las matriculas de la base de datos
 */

require 'matricula.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    // Manejar peticion GET
    $matricula = Matricula::getAll();

    if ($matricula) {

        $datos["estado"] = 1;
        $datos["matricula"] = $matricula;

        print json_encode($datos);
    } else {
        print json_encode(array(
            "estado" => 2,
            "mensaje" => "Ha ocurrido un error 2"
        ));
    }
}
