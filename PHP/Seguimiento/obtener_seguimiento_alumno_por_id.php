<?php
/**
 * Obtiene el detalle de un seguimiento especificado por
 * su identificador "cod_seguimiento"
 */

require 'seguimiento_alumno.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    if (isset($_GET['cod_padre'])) {

        // Obtener par�metro cod_padre
        $parametro1 = $_GET['cod_padre'];
        $parametro2 = $_GET['cod_alumno'];

        // Tratar retorno
        $retorno = Seguimiento::getById($parametro1, $parametro2);


        if ($retorno) {

            $seguimiento["estado"] = 1;		// cambio "1" a 1 porque no coge bien la cadena.
            $seguimiento["seguimiento"] = $retorno;
            // Enviar objeto json del seguimiento
            print json_encode($seguimiento);
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
