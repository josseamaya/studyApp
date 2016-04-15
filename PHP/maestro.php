<?php

/**
 * Representa el la estructura de las maestro
 * almacenadas en la base de datos
 */
require 'Database.php';

class Maestro
{
    function __construct()
    {
    }

    /**
     * Retorna en la fila especificada de la tabla 'maestro'
     *
     * @param $cod_maestro Identificador del registro
     * @return array Datos del registro
     */
    public static function getAll()
    {
        $consulta = "SELECT * FROM maestro";
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
     * Obtiene los campos de un Maestro con un identificador
     * determinado
     *
     * @param $cod_maestro Identificador del alumno
     * @return mixed
     */
    public static function getById($cod_maestro)
    {
        // Consulta de la tabla maestro
        $consulta = "SELECT cod_maestro,
                            nombre,
                            email
                             FROM maestro
                             WHERE cod_maestro = ?";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($cod_maestro));
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
        $nombre,
        $apellido,
        $email,
        $telefono,
        $password
    )
    {
        // Creando consulta UPDATE
        $consulta = "UPDATE maestro" .
            " SET nombre=?,
              apellido=?,
              email=?,
              telefono=?,
              password=? " .
            "WHERE cod_maestro=?";

        // Preparar la sentencia
        $cmd = Database::getInstance()->getDb()->prepare($consulta);

        // Relacionar y ejecutar la sentencia
        $cmd->execute(array($nombre, $apellido, $email, $telefono, $password, $cod_maestro));

        return $cmd;
    }

    /**
     * Insertar un nuevo Maestro
     *
     * @param $nombre      nombre del nuevo registro
     * @param $email direccion del nuevo registro
     * @return PDOStatement
     */
    public static function insert(
        $cod_maestro,
        $nombre,
        $apellido,
        $email,
        $telefono,
        $password
    )
    {
        // Sentencia INSERT
        $comando = "INSERT INTO maestro ( " .
            "cod_maestro," .
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
                $cod_maestro,
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
     * @param $cod_maestro identificador de la tabla maestro
     * @return bool Respuesta de la eliminaci�n
     */
    public static function delete($cod_maestro)
    {
        // Sentencia DELETE
        $comando = "DELETE FROM maestro WHERE cod_maestro=?";

        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);

        return $sentencia->execute(array($cod_maestro));
    }
}

?>
