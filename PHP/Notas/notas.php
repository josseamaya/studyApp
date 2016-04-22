<?php

/**
 * Representa el la estructura de las nota
 * almacenadas en la base de datos
 */
require 'Database.php';

class Notas
{
    function __construct()
    {
    }

    /**
     * Retorna en la fila especificada de la tabla 'nota'
     *
     * @param $cod_nota Identificador del registro
     * @return array Datos del registro
     */
    public static function getAll()
    {
        $consulta = "SELECT * FROM notas";
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
     * Obtiene los campos de un notas con un identificador
     * determinado
     *
     * @param $cod_notas Identificador del notas
     * @return mixed
     */
    public static function getById($cod_notas)
    {
        // Consulta de la tabla notas
        $consulta = "SELECT cod_notas,
                            periodo,
                            examen,
                            acumulativo,
                            cod_alumno,
                            cod_asignatura
                             FROM notas
                             WHERE cod_notas = ?";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($cod_notas));
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
     * @param $cod_notas      identificador
     * @param $nombre      nuevo nombre
     * @param $email nueva email

     */
     public static function update(
        $cod_notas,
        $periodo,
        $examen,
        $acumulativo,
        $cod_alumno,
        $cod_asignatura
     )
     {
         // Creando consulta UPDATE
         $consulta = "UPDATE notas" .
             " SET periodo=?,
               examen=?,
               acumulativo=?,
               cod_alumno=?,
               cod_asignatura=? " .
             "WHERE cod_notas=?";

         // Preparar la sentencia
         $cmd = Database::getInstance()->getDb()->prepare($consulta);

         // Relacionar y ejecutar la sentencia
         $cmd->execute(array($periodo, $examen, $acumulativo, $cod_alumno, $cod_asignatura, $cod_notas));

         return $cmd;
     }

    /**
     * Insertar un nuevo notas
     *
     * @param $nombre      nombre del nuevo registro
     * @param $email direccion del nuevo registro
     * @return PDOStatement
     */
    public static function insert(
        $cod_notas,
        $periodo,
        $examen,
        $acumulativo,
        $cod_alumno,
        $cod_asignatura
    )
    {
        // Sentencia INSERT
        $comando = "INSERT INTO notas ( " .
            "cod_notas," .
            "periodo,".
            "examen,".
            "acumulativo,".
            "cod_alumno,".
            "cod_asignatura)" .
            " VALUES(?,?,?,?,?,?)";

        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);

        return $sentencia->execute(
            array(
                  $cod_notas,
                  $periodo,
                  $examen,
                  $acumulativo,
                  $cod_alumno,
                  $cod_asignatura
            )
        );

    }

    /**
     * Eliminar el registro con el identificador especificado
     *
     * @param $cod_notas identificador de la tabla notas
     * @return bool Respuesta de la eliminaci�n
     */
    public static function delete($cod_notas)
    {
        // Sentencia DELETE
        $comando = "DELETE FROM notas WHERE cod_notas=?";

        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);

        return $sentencia->execute(array($cod_notas));
    }
}

?>
