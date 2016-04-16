<?php
/**
 * Obtiene todas las alumnos de la base de datos
 */

require 'alumno.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    // Manejar peticion GET
    $alumno = Alumno::getAll();

    if ($alumno) {

        $datos["estado"] = 1;
        $datos["alumno"] = $alumno;

        print json_encode($datos);
    } else {
        print json_encode(array(
            "estado" => 2,
            "mensaje" => "Ha ocurrido un error 2"
        ));
    }
}
