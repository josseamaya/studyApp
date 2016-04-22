<?php

/**
 * Representa el la estructura de las matricula
 * almacenadas en la base de datos
 */
require 'Database.php';

class Matricula
{
    function __construct()
    {
    }

    /**
     * Retorna en la fila especificada de la tabla 'matricula'
     *
     * @param $cod_maestro Identificador del registro
     * @return array Datos del registro
     */
    public static function getAll()
    {
        $consulta = "SELECT * FROM matricula";
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
     * Obtiene los campos de un matricula con un identificador
     * determinado
     *
     * @param $cod_maestro Identificador del matricula
     * @return mixed
     */
    public static function getById($cod_maestro, $cod_alumno, $cod_asignatura)
    {
        // Consulta de la tabla matricula
        $consulta = "SELECT cod_maestro,
                            cod_alumno,
                            cod_asignatura
                             FROM matricula
                             WHERE cod_maestro=? and cod_alumno=? and cod_asignatura=?";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($cod_maestro, $cod_alumno, $cod_asignatura));
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
     * @param $cod_maestro      identificador
     * @param $nombre      nuevo nombre
     * @param $email nueva email

     */
    public static function update(
        $cod_maestro,
        $cod_alumno,
        $cod_asignatura
    )
    {
        // Creando consulta UPDATE
        $consulta = "UPDATE matricula" .
            " SET cod_maestro=?,
              cod_alumno=?,
              cod_asignatura=? " .
            "WHERE cod_maestro=? AND cod_alumno=? AND cod_asignatura=?";

 /*
 UPDATE matricula
      SET cod_maestro="1014",
      cod_alumno="134543",
      cod_asignatura="IIT4015"
      WHERE cod_maestro="1014"and cod_alumno="10002" and cod_asignatura="IIT4015"; */

        // Preparar la sentencia
        $cmd = Database::getInstance()->getDb()->prepare($consulta);

        // Relacionar y ejecutar la sentencia

        $cmd->execute(array($cod_maestro, $cod_alumno, $cod_asignatura, $cod_maestro, $cod_alumno, $cod_asignatura));
        return $cmd;
    }

    /**
     * Insertar un nuevo matricula
     *
     * @param $nombre      nombre del nuevo registro
     * @param $email direccion del nuevo registro
     * @return PDOStatement
     */
    public static function insert(
        $cod_maestro,
        $cod_alumno,
        $cod_asignatura
    )
    {
        // Sentencia INSERT
        $comando = "INSERT INTO matricula ( " .
            "cod_maestro," .
            "cod_alumno,".
            "cod_asignatura)" .
            " VALUES(?,?,?)";

        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);

        return $sentencia->execute(
            array(
                $cod_maestro,
                $cod_alumno,
                $cod_asignatura
            )
        );

    }

    /**
     * Eliminar el registro con el identificador especificado
     *
     * @param $$cod_maestro identificador de la tabla matricula
     * @return bool Respuesta de la eliminaci�n
     */
    public static function delete($cod_maestro, $cod_alumno, $cod_asignatura)
    {
        // Sentencia DELETE
        $comando = "DELETE FROM matricula WHERE cod_maestro=? and cod_alumno=? and cod_asignatura=?";

        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);

        return $sentencia->execute(array($cod_maestro, $cod_alumno, $cod_asignatura));
    }
}

?>
