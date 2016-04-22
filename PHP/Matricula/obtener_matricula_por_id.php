<?php
/**
 * Obtiene el detalle de un matricula especificado por
 * su identificador "cod_matricula"
 */

require 'matricula.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    if (isset($_GET['cod_maestro'])) {

        // Obtener par�metro cod_matricula
        $parametro1 = $_GET['cod_maestro'];
        $parametro2 = $_GET['cod_alumno'];
        $parametro3 = $_GET['cod_asignatura'];

        // Tratar retorno
        $retorno = Matricula::getById($parametro1, $parametro2, $parametro3);


        if ($retorno) {

            $matricula["estado"] = 1;		// cambio "1" a 1 porque no coge bien la cadena.
            $matricula["matricula"] = $retorno;
            // Enviar objeto json del matricula
            print json_encode($matricula);
        } else {
            // Enviar respuesta de error general
            print json_encode(
                array(
                    'estado' => '2',
                    'mensaje' => 'No se obtuvo el registro'
                )
            );
        }

    } else {
        // Enviar respuesta de error
        print json_encode(
            array(
                'estado' => '3',
                'mensaje' => 'Se necesita un identificador'
            )
        );
    }
}
