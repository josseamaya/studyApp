<?php
/**
 * Obtiene todas las padres de la base de datos
 */

require 'padre.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    // Manejar peticion GET
    $padre = Padre::getAll();

    if ($padre) {

        $datos["estado"] = 1;
        $datos["padre"] = $padre;

        print json_encode($datos);
    } else {
        print json_encode(array(
            "estado" => 2,
            "mensaje" => "Ha ocurrido un error 2"
        ));
    }
}
