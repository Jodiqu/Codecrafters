-- 1. Crear la base de datos (si no existe) y cambiar a ella
CREATE DATABASE IF NOT EXISTS `tienda_db`
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
USE `tienda_db`;

-- 2. Tablas
CREATE TABLE IF NOT EXISTS `Cliente` (
  `nif`         VARCHAR(9)   PRIMARY KEY,
  `nombre`      VARCHAR(100) NOT NULL,
  `domicilio`   VARCHAR(200),
  `email`       VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `Premium` (
  `nif`             VARCHAR(9)     PRIMARY KEY,
  `cuota_anual`     DECIMAL(10,2)  NOT NULL,
  `descuento_envio` DECIMAL(5,2)   NOT NULL,
  FOREIGN KEY (`nif`) REFERENCES `Cliente`(`nif`) ON DELETE CASCADE
) ENGINE=InnoDB CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `Articulo` (
  `codigo`             VARCHAR(20) PRIMARY KEY,
  `descripcion`        VARCHAR(200),
  `precio_venta`       DECIMAL(10,2) NOT NULL,
  `gastos_envio`       DECIMAL(10,2) NOT NULL,
  `tiempo_preparacion` INT NOT NULL
) ENGINE=InnoDB CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `Pedido` (
  `numero`           INT AUTO_INCREMENT PRIMARY KEY,
  `cliente_nif`      VARCHAR(9)   NOT NULL,
  `articulo_codigo`  VARCHAR(20)  NOT NULL,
  `cantidad`         INT          NOT NULL,
  `fecha_hora`       DATETIME     NOT NULL,
  `enviado`          BOOLEAN      NOT NULL DEFAULT FALSE,
  FOREIGN KEY (`cliente_nif`)    REFERENCES `Cliente`(`nif`),
  FOREIGN KEY (`articulo_codigo`) REFERENCES `Articulo`(`codigo`)
) ENGINE=InnoDB CHARSET=utf8mb4;

-- 3. Stored Procedures
DELIMITER //
CREATE PROCEDURE `sp_add_cliente`(
  IN p_nif VARCHAR(20),
  IN p_nombre VARCHAR(100),
  IN p_domicilio VARCHAR(200),
  IN p_email VARCHAR(100)
)
BEGIN
  INSERT INTO Cliente(nif,nombre,domicilio,email)
  VALUES(p_nif,p_nombre,p_domicilio,p_email);
END //

CREATE PROCEDURE `sp_add_premium`(
  IN p_nif VARCHAR(20),
  IN p_cuota_anual DECIMAL(10,2),
  IN p_descuento_envio DECIMAL(5,2)
)
BEGIN
  INSERT INTO Premium(nif,cuota_anual,descuento_envio)
  VALUES(p_nif,p_cuota_anual,p_descuento_envio);
END //

CREATE PROCEDURE `sp_add_articulo`(
  IN p_codigo VARCHAR(20),
  IN p_descripcion VARCHAR(200),
  IN p_precio_venta DECIMAL(10,2),
  IN p_gastos_envio DECIMAL(10,2),
  IN p_tiempo_preparacion INT
)
BEGIN
  INSERT INTO Articulo(codigo,descripcion,precio_venta,gastos_envio,tiempo_preparacion)
  VALUES(p_codigo,p_descripcion,p_precio_venta,p_gastos_envio,p_tiempo_preparacion);
END //

CREATE PROCEDURE `sp_add_pedido`(
  IN p_nif VARCHAR(20),
  IN p_codigo VARCHAR(20),
  IN p_cantidad INT,
  IN p_fecha_hora DATETIME,
  IN p_enviado BOOLEAN
)
BEGIN
  INSERT INTO Pedido(cliente_nif,articulo_codigo,cantidad,fecha_hora,enviado)
  VALUES(p_nif,p_codigo,p_cantidad,p_fecha_hora,p_enviado);
END //

CREATE PROCEDURE `sp_mark_pedido_enviado`(
  IN p_numero INT
)
BEGIN
  UPDATE Pedido SET enviado = TRUE WHERE numero = p_numero;
END //

CREATE PROCEDURE `sp_delete_pedido`(
  IN p_numero INT
)
BEGIN
  DELETE FROM Pedido WHERE numero = p_numero;
END //

CREATE PROCEDURE `sp_delete_cliente`(
  IN p_nif VARCHAR(20)
)
BEGIN
  DELETE FROM Premium WHERE nif = p_nif;
  DELETE FROM Cliente WHERE nif = p_nif;
END //

CREATE PROCEDURE `sp_delete_articulo`(
  IN p_codigo VARCHAR(20)
)
BEGIN
  DELETE FROM Articulo WHERE codigo = p_codigo;
END //
DELIMITER ;
