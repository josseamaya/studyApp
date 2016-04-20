<?php
/**
 * Obtiene el detalle de un padre especificado por
 * su identificador "cod_padre"
 */

require 'padre.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    if (isset($_GET['cod_padre'])) {

        // Obtener par�metro cod_padre
        $parametro = $_GET['cod_padre'];

        // Tratar retorno
        $retorno = Padre::getById($parametro);


        if ($retorno) {

            $padre["estado"] = 1;		// cambio "1" a 1 porque no coge bien la cadena.
            $padre["padre"] = $retorno;
            // Enviar objeto json del padre
            print json_encode($padre);
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
