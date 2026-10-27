package br.com.protons.sprintapi.repository;

import br.com.protons.sprintapi.model.Categoria;
import br.com.protons.sprintapi.model.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Define operações de acesso aos dados das perguntas
public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {

    List<Pergunta> findByEnunciadoContainingIgnoreCase(String texto);

    List<Pergunta> findByCategoria(Categoria categoria);
}
