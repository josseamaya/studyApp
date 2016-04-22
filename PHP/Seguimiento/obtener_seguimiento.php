<?php
/**
 * Obtiene todas las seguimientos de la base de datos
 */

require 'seguimiento_alumno.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    // Manejar peticion GET
    $seguimiento = Seguimiento::getAll();

    if ($seguimiento) {

        $datos["estado"] = 1;
        $datos["seguimiento"] = $seguimiento;

        print json_encode($datos);
    } else {
        print json_encode(array(
            "estado" => 2,
            "mensaje" => "Ha ocurrido un error 2"
        ));
    }
}
