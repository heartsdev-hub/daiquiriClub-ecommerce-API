-- 1. Eliminar restricciones antiguas
ALTER TABLE cliente
DROP CONSTRAINT cliente_tipo_documento_check;

ALTER TABLE cliente
DROP CONSTRAINT cliente_tipo_sexo_check;

-- 2. Convertir TipoDocumento de INTEGER a VARCHAR
ALTER TABLE cliente
ALTER COLUMN tipo_documento TYPE VARCHAR(255)
USING (
    CASE tipo_documento
        WHEN 0 THEN 'PASAPORTE'
        WHEN 1 THEN 'DNI'
        ELSE NULL
    END
);

-- 3. Convertir TipoSexo de INTEGER a VARCHAR
ALTER TABLE cliente
ALTER COLUMN tipo_sexo TYPE VARCHAR(255)
USING (
    CASE tipo_sexo
        WHEN 0 THEN 'MASCULINO'
        WHEN 1 THEN 'FEMENINO'
        ELSE NULL
    END
);