<?php

/**
 * Representa el la estructura de las asignatura
 * almacenadas en la base de datos
 */
require 'Database.php';

class Asignatura
{
    function __construct()
    {
    }

    /**
     * Retorna en la fila especificada de la tabla 'asignatura'
     *
     * @param $cod_asignatura Identificador del registro
     * @return array Datos del registro
     */
    public static function getAll()
    {
        $consulta = "SELECT * FROM asignatura";
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
     * Obtiene los campos de un asignatura con un identificador
     * determinado
     *
     * @param $cod_asignatura Identificador del asignatura
     * @return mixed
     */
    public static function getById($cod_asignatura)
    {
        // Consulta de la tabla asignatura
        $consulta = "SELECT cod_asignatura,
                            nombre,
                            horario,
                            cod_maestro
                             FROM asignatura
                             WHERE cod_asignatura = ?";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($cod_asignatura));
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
     * @param $cod_asignatura      identificador
     * @param $nombre      nuevo nombre
     * @param $email nueva email

     */
    public static function update(
        $cod_asignatura,
        $nombre,
        $horario,
        $cod_maestro
    )
    {
        // Creando consulta UPDATE
        $consulta = "UPDATE asignatura" .
            " SET nombre=?,
              horario=?,
              cod_maestro=? " .
            "WHERE cod_asignatura=?";

        // Preparar la sentencia
        $cmd = Database::getInstance()->getDb()->prepare($consulta);

        // Relacionar y ejecutar la sentencia
        $cmd->execute(array($nombre, $horario, $cod_maestro, $cod_asignatura));

        return $cmd;
    }

    /**
     * Insertar un nuevo asignatura
     *
     * @param $nombre      nombre del nuevo registro
     * @param $email direccion del nuevo registro
     * @return PDOStatement
     */
    public static function insert(
        $cod_asignatura,
        $nombre,
        $horario,
        $cod_maestro
    )
    {
        // Sentencia INSERT
        $comando = "INSERT INTO asignatura ( " .
            "cod_asignatura," .
            "nombre,".
            "horario,".
            "cod_maestro)" .
            " VALUES(?,?,?,?)";

        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);

        return $sentencia->execute(
            array(
                $cod_asignatura,
                $nombre,
                $horario,
                $cod_maestro
            )
        );

    }

    /**
     * Eliminar el registro con el identificador especificado
     *
     * @param $cod_asignatura identificador de la tabla asignatura
     * @return bool Respuesta de la eliminaci�n
     */
    public static function delete($cod_asignatura)
    {
        // Sentencia DELETE
        $comando = "DELETE FROM asignatura WHERE cod_asignatura=?";

        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);

        return $sentencia->execute(array($cod_asignatura));
    }
}

?>
