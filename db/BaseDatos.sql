/******************/
/* CREAR BDD   */
/******************/


/*CREACION DE BDD CLIENTES*/
SELECT 'CREATE DATABASE clientes_db WITH ENCODING = ''UTF8'' OWNER = tata'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'clientes_db')\gexec


/* CREACION DE BDD PARA CUENTAS*/
SELECT 'CREATE DATABASE cuentas_db WITH ENCODING = ''UTF8'' OWNER = tata'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'cuentas_db')\gexec


/*DAR PERMISOS*/
GRANT ALL PRIVILEGES ON DATABASE clientes_db TO tata;
GRANT ALL PRIVILEGES ON DATABASE cuentas_db TO tata;


/*SELECCIONA BDD clientes_bd*/
\c clientes_db;

DROP TABLE IF EXISTS cliente CASCADE;
DROP TABLE IF EXISTS persona CASCADE;

CREATE TABLE persona(
    personaid SERIAL PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    genero VARCHAR(20) CHECK (genero IN ('Masculino', 'Femenino', 'Otro')),
    edad    INT,
    identificacion VARCHAR(30) NOT NULL UNIQUE,
    direccion   VARCHAR(200) NOT NULL,
    telefono    VARCHAR(20)
);


CREATE TABLE cliente(
    clienteid SERIAL PRIMARY KEY,
    contrasena VARCHAR(255) NOT NULL,
    estado BOOLEAN DEFAULT TRUE,
    personaid INT NOT NULL,
    CONSTRAINT fk_cliente_persona 
        FOREIGN KEY (personaid) 
        REFERENCES persona(personaid)
);





/******************/
/* CUENTAS BDD   */
/******************/

/*RETORNAR A POSTGRES*/
/*\c postgres;*/


/*SELECCIONA BDD cuentas_bd*/
\c cuentas_db;

DROP TABLE IF EXISTS movimiento CASCADE;
DROP TABLE IF EXISTS cuenta CASCADE;

CREATE TABLE cuenta(
    cuentaid SERIAL PRIMARY KEY,
    numero_cuenta VARCHAR(20) NOT NULL UNIQUE,
    tipo_cuenta VARCHAR(20) NOT NULL CHECK (tipo_cuenta IN ('AHORRO', 'CORRIENTE')),
    saldo_inicial DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    estado BOOLEAN DEFAULT TRUE,
    cliente_identificacion VARCHAR(30) NOT NULL
);


DROP TABLE IF EXISTS movimientos;/* CASCADE;*/
CREATE TABLE movimiento(
    movimientoid SERIAL PRIMARY KEY,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tipo_movimiento VARCHAR(20) NOT NULL,
    valor DECIMAL(12,2) NOT NULL,
    saldo DECIMAL(12,2) NOT NULL,
    numero_cuenta VARCHAR(20) NOT NULL,
    detalle VARCHAR(200),
    CONSTRAINT fk_movimiento_cuenta 
        FOREIGN KEY (numero_cuenta) 
        REFERENCES cuenta(numero_cuenta)
);