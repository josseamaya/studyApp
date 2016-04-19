<?php
/**
 * Obtiene el detalle de un asignatura especificado por
 * su identificador "cod_asignatura"
 */

require 'asignatura.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    if (isset($_GET['cod_asignatura'])) {

        // Obtener par�metro cod_asignatura
        $parametro = $_GET['cod_asignatura'];

        // Tratar retorno
        $retorno = Asignatura::getById($parametro);


        if ($retorno) {

            $asignatura["estado"] = 1;		// cambio "1" a 1 porque no coge bien la cadena.
            $asignatura["asignatura"] = $retorno;
            // Enviar objeto json del asignatura
            print json_encode($asignatura);
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
