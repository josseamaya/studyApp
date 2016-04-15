<?php
/**
 * Obtiene todas las maestros de la base de datos
 */

require 'maestro.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    // Manejar peticion GET
    $maestro = Maestro::getAll();

    if ($maestro) {

        $datos["estado"] = 1;
        $datos["maestro"] = $maestro;

        print json_encode($datos);
    } else {
        print json_encode(array(
            "estado" => 2,
            "mensaje" => "Ha ocurrido un error 2"
        ));
    }
}
