package com.NotasBack.NotasFacil.repository

import com.NotasBack.NotasFacil.model.Pais
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID


@Repository
interface PaisRepository : JpaRepository<Pais, UUID>{


}