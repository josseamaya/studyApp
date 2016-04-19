<?php
/**
 * Obtiene todas las asignaturas de la base de datos
 */

require 'asignatura.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    // Manejar peticion GET
    $asignatura = Asignatura::getAll();

    if ($asignatura) {

        $datos["estado"] = 1;
        $datos["asignatura"] = $asignatura;

        print json_encode($datos);
    } else {
        print json_encode(array(
            "estado" => 2,
            "mensaje" => "Ha ocurrido un error 2"
        ));
    }
}
