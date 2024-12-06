/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  bet10
 * Created: 31 jul 2024
 */

drop table clientes;

CREATE TABLE clientes (
id_cliente SERIAL PRIMARY KEY,
nombre VARCHAR(100) NOT NULL,
a_paterno VARCHAR(100) NOT NULL,
a_materno VARCHAR(100) NOT NULL,
correo VARCHAR(100) UNIQUE NOT NULL,
password VARCHAR(50) NOT NULL,
telefono VARCHAR(10) NOT NULL,
direccion VARCHAR(200) NOT NULL,
num_cuenta VARCHAR(18)UNIQUE NOT NULL,
estatus VARCHAR(1) NOT NULL
);

INSERT INTO clientes (nombre, a_paterno, a_materno, correo, password, telefono, direccion, num_cuenta, estatus)
VALUES ('Juan', 'Pérez', 'García', 'juan.perez@example.com', 'password123', '5551234567', 'Calle Falsa 123', '1234567890123456', '1');

-- María López
INSERT INTO clientes (nombre, a_paterno, a_materno, correo, password, telefono, direccion, num_cuenta, estatus)
VALUES ('María', 'López', 'Martínez', 'maria.lopez@example.com', 'password123', '5552345678', 'Avenida Siempre Viva 742', '2345678901234567', '1');

-- Carlos Sánchez
INSERT INTO clientes (nombre, a_paterno, a_materno, correo, password, telefono, direccion, num_cuenta, estatus)
VALUES ('Carlos', 'Sánchez', 'Fernández', 'carlos.sanchez@example.com', 'password123', '5553456789', 'Boulevard de los Sueños Rotos 456', '3456789012345678', '1');

-- Ana González
INSERT INTO clientes (nombre, a_paterno, a_materno, correo, password, telefono, direccion, num_cuenta, estatus)
VALUES ('Ana', 'González', 'Pérez', 'ana.gonzalez@example.com', 'password123', '5554567890', 'Calle de la Amargura 789', '4567890123456789', '1');


INSERT INTO clientes (nombre, a_paterno, a_materno, correo, password, telefono, direccion, num_cuenta, estatus) VALUES ('Pepe', 'Pepe', 'Pepe', 'pepe@pepe.com', 'pepe', '0123456789', 'pepe 101', '123456789012345679','1');
