<?php

/**
 * Representa el la estructura de las seguimiento
 * almacenadas en la base de datos
 */
require 'Database.php';

class Seguimiento
{
    function __construct()
    {
    }

    /**
     * Retorna en la fila especificada de la tabla 'seguimiento'
     *
     * @param $cod_padre,$cod_alumno  Identificador del registro
     * @return array Datos del registro
     */
    public static function getAll()
    {
        $consulta = "SELECT * FROM seguimiento";
        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute();

            return $comando->fetchAll(PDO::FETCH_ASSOC);

        } catch (PDOException $e) {
            return false;
        }
    }

    /**
     * Obtiene los campos de un seguimiento con un identificador
     * determinado
     *
     * @param $cod_padre Identificador del seguimiento
     * @return mixed
     */
    public static function getById($cod_padre, $cod_alumno)
    {
        // Consulta de la tabla seguimiento
        $consulta = "SELECT cod_padre,
                            cod_alumno
                             FROM seguimiento
                             WHERE cod_padre = ? and cod_alumno = ?";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($cod_padre, $cod_alumno));
            // Capturar primera fila del resultado
            $row = $comando->fetch(PDO::FETCH_ASSOC);
            return $row;

        } catch (PDOException $e) {
            // Aqu� puedes clasificar el error dependiendo de la excepci�n
            // para presentarlo en la respuesta Json
            return -1;
        }
    }

    /**
     * Actualiza un registro de la bases de datos basado
     * en los nuevos valores relacionados con un identificador
     *
     * @param $cod_padre      identificador
     * @param $nombre      nuevo nombre
     * @param $email nueva email

     */
    public static function update(
        $cod_padre,
        $cod_alumno
    )
    {
        // Creando consulta UPDATE
        $consulta = "UPDATE seguimiento" .
            " SET cod_padre=?,
              cod_alumno=? " .
            "WHERE cod_padre=? and cod_alumno=?";

        // Preparar la sentencia
        $cmd = Database::getInstance()->getDb()->prepare($consulta);

        // Relacionar y ejecutar la sentencia

        $cmd->execute(array($cod_padre, $cod_alumno, $cod_padre, $cod_alumno));

        return $cmd;
    }

    /**
     * Insertar un nuevo seguimiento
     *
     * @param $nombre      nombre del nuevo registro
     * @param $email direccion del nuevo registro
     * @return PDOStatement
     */
    public static function insert(
        $cod_padre,
        $cod_alumno
    )
    {
        // Sentencia INSERT
        $comando = "INSERT INTO seguimiento ( " .
            "cod_padre," .
            " cod_alumno)" .
            " VALUES( ?,?)";

        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);

        return $sentencia->execute(
            array(
                $cod_padre,
                $cod_alumno
            )
        );

    }

    /**
     * Eliminar el registro con el identificador especificado
     *
     * @param $cod_padre identificador de la tabla seguimiento
     * @return bool Respuesta de la eliminaci�n
     */
    public static function delete($cod_padre, $cod_alumno)
    {
        // Sentencia DELETE
        $comando = "DELETE FROM seguimiento WHERE cod_padre=? and cod_alumno=?";

        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);

        return $sentencia->execute(array($cod_padre, $cod_alumno));
    }
}

?>
