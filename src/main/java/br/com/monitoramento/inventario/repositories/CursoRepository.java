package br.com.monitoramento.inventario.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.monitoramento.inventario.models.Curso;
@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

	Curso findByNome(String nome);

}
