<?php
/**
 * Obtiene el detalle de un alumno especificado por
 * su identificador "cod_maestro"
 */

require 'maestro.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    if (isset($_GET['cod_maestro'])) {

        // Obtener par�metro cod_maestro
        $parametro = $_GET['cod_maestro'];

        // Tratar retorno
        $retorno = Maestro::getById($parametro);


        if ($retorno) {

            $maestro["estado"] = 1;		// cambio "1" a 1 porque no coge bien la cadena.
            $maestro["maestro"] = $retorno;
            // Enviar objeto json del maestro
            print json_encode($maestro);
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
