<?php

/**
 * Representa el la estructura de las padre
 * almacenadas en la base de datos
 */
require 'Database.php';

class Padre
{
    function __construct()
    {
    }

    /**
     * Retorna en la fila especificada de la tabla 'padre'
     *
     * @param $cod_padre Identificador del registro
     * @return array Datos del registro
     */
    public static function getAll()
    {
        $consulta = "SELECT * FROM padre";
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
     * Obtiene los campos de un padre con un identificador
     * determinado
     *
     * @param $cod_padre Identificador del padre
     * @return mixed
     */
    public static function getById($cod_padre)
    {
        // Consulta de la tabla padre
        $consulta = "SELECT cod_padre,
                            nombre,
                            apellido,
                            email
                             FROM padre
                             WHERE cod_padre = ?";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($cod_padre));
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
        $nombre,
        $apellido,
        $email,
        $telefono,
        $password
    )
    {
        // Creando consulta UPDATE
        $consulta = "UPDATE padre" .
            " SET nombre=?,
              apellido=?,
              email=?,
              telefono=?,
              password=? " .
            "WHERE cod_padre=?";

        // Preparar la sentencia
        $cmd = Database::getInstance()->getDb()->prepare($consulta);

        // Relacionar y ejecutar la sentencia
        $cmd->execute(array($nombre, $apellido, $email, $telefono, $password, $cod_padre));

        return $cmd;
    }

    /**
     * Insertar un nuevo padre
     *
     * @param $nombre      nombre del nuevo registro
     * @param $email direccion del nuevo registro
     * @return PDOStatement
     */
    public static function insert(
        $cod_padre,
        $nombre,
        $apellido,
        $email,
        $telefono,
        $password
    )
    {
        // Sentencia INSERT
        $comando = "INSERT INTO padre ( " .
            "cod_padre," .
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
                $cod_padre,
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
     * @param $cod_padre identificador de la tabla padre
     * @return bool Respuesta de la eliminaci�n
     */
    public static function delete($cod_padre)
    {
        // Sentencia DELETE
        $comando = "DELETE FROM padre WHERE cod_padre=?";

        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);

        return $sentencia->execute(array($cod_padre));
    }
}

?>
