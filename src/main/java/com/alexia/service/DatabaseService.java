package com.alexia.service;

import com.alexia.constants.Messages;
import com.alexia.dto.ConnectionResultDTO;
import com.alexia.entity.ConnectionTest;
import com.alexia.exception.DatabaseConnectionException;
import com.alexia.factory.ConnectionTestFactory;
import com.alexia.repository.ConnectionTestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio para operaciones de base de datos.
 * Maneja la lógica de negocio relacionada con la persistencia.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DatabaseService implements IDatabaseService {

    private final ConnectionTestRepository connectionTestRepository;
    private final ConnectionTestFactory connectionTestFactory;

    /**
     * Prueba la conexión a la base de datos creando un registro de prueba.
     * 
     * @return ConnectionResultDTO con el resultado de la operación
     * @throws DatabaseConnectionException si hay un error de conexión
     */
    @Override
    public ConnectionResultDTO testConnection() {
        try {
            // Usar factory para crear el registro de prueba
            ConnectionTest test = connectionTestFactory.createTestRecord();
            ConnectionTest saved = connectionTestRepository.save(test);
            
            log.info("Conexión exitosa a Supabase. Registro guardado con ID: {}", saved.getId());
            
            // Contar registros totales
            long count = connectionTestRepository.count();
            
            return ConnectionResultDTO.success(
                saved.getId(),
                count,
                saved.getMessage()
            );
            
        } catch (DataAccessException e) {
            log.error("Error de acceso a datos: {}", e.getMessage());
            throw new DatabaseConnectionException("Error al conectar con Supabase", e);
        } catch (Exception e) {
            log.error("Error inesperado al probar conexión: {}", e.getMessage());
            return ConnectionResultDTO.error(e.getMessage());
        }
    }
}
