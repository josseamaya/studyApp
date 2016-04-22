<?php
/**
 * Obtiene todas las notass de la base de datos
 */

require 'notas.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    // Manejar peticion GET
    $notas = Notas::getAll();

    if ($notas) {

        $datos["estado"] = 1;
        $datos["notas"] = $notas;

        print json_encode($datos);
    } else {
        print json_encode(array(
            "estado" => 2,
            "mensaje" => "Ha ocurrido un error 2"
        ));
    }
}
