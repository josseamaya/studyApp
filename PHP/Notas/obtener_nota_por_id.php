<?php
/**
 * Obtiene el detalle de un notas especificado por
 * su identificador "cod_notas"
 */

require 'notas.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    if (isset($_GET['cod_notas'])) {

        // Obtener par�metro cod_notas
        $parametro = $_GET['cod_notas'];

        // Tratar retorno
        $retorno = Notas::getById($parametro);


        if ($retorno) {

            $notas["estado"] = 1;		// cambio "1" a 1 porque no coge bien la cadena.
            $notas["notas"] = $retorno;
            // Enviar objeto json del notas
            print json_encode($notas);
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
