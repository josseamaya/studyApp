<?php

/**
 * Representa el la estructura de las alumno
 * almacenadas en la base de datos
 */
require 'Database.php';

class Alumno
{
    function __construct()
    {
    }

    /**
     * Retorna en la fila especificada de la tabla 'alumno'
     *
     * @param $cod_alumno Identificador del registro
     * @return array Datos del registro
     */
    public static function getAll()
    {
        $consulta = "SELECT * FROM alumno";
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
     * Obtiene los campos de un alumno con un identificador
     * determinado
     *
     * @param $cod_alumno Identificador del alumno
     * @return mixed
     */
    public static function getById($cod_alumno)
    {
        // Consulta de la tabla alumno
        $consulta = "SELECT cod_alumno,
                            nombre,
                            apellido,
                            email
                             FROM alumno
                             WHERE cod_alumno = ?";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($cod_alumno));
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
     * @param $cod_alumno      identificador
     * @param $nombre      nuevo nombre
     * @param $email nueva email

     */
    public static function update(
        $cod_alumno,
        $nombre,
        $apellido,
        $email,
        $telefono,
        $password
    )
    {
        // Creando consulta UPDATE
        $consulta = "UPDATE alumno" .
            " SET nombre=?,
              apellido=?,
              email=?,
              telefono=?,
              password=? " .
            "WHERE cod_alumno=?";

        // Preparar la sentencia
        $cmd = Database::getInstance()->getDb()->prepare($consulta);

        // Relacionar y ejecutar la sentencia
        $cmd->execute(array($nombre, $apellido, $email, $telefono, $password, $cod_alumno));

        return $cmd;
    }

    /**
     * Insertar un nuevo alumno
     *
     * @param $nombre      nombre del nuevo registro
     * @param $email direccion del nuevo registro
     * @return PDOStatement
     */
    public static function insert(
        $cod_alumno,
        $nombre,
        $apellido,
        $email,
        $telefono,
        $password
    )
    {
        // Sentencia INSERT
        $comando = "INSERT INTO alumno ( " .
            "cod_alumno," .
            "nombre,".
            "apellido,".
            "email,".
            "telefono,".
            " password)" .
            " VALUES( ?,?,?,?,?,?)";

        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);

        return $sentencia->execute(
            array(
                $cod_alumno,
                $nombre,
                $apellido,
                $email,
                $telefono,
                $password
            )
        );

    }

    /**
     * Eliminar el registro con el identificador especificado
     *
     * @param $cod_alumno identificador de la tabla alumno
     * @return bool Respuesta de la eliminaci�n
     */
    public static function delete($cod_alumno)
    {
        // Sentencia DELETE
        $comando = "DELETE FROM alumno WHERE cod_alumno=?";

        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);

        return $sentencia->execute(array($cod_alumno));
    }
}

?>
