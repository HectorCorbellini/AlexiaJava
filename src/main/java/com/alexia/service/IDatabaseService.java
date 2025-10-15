package com.alexia.service;

import com.alexia.dto.ConnectionResultDTO;

/**
 * Interfaz para operaciones de base de datos.
 * Define el contrato para servicios de persistencia.
 * 
 * @author Alexia Team
 * @version 1.0
 * @since 2025-10-15
 */
public interface IDatabaseService {
    
    /**
     * Prueba la conexión a la base de datos creando un registro de prueba.
     * 
     * @return ConnectionResultDTO con el resultado de la operación
     */
    ConnectionResultDTO testConnection();
}
