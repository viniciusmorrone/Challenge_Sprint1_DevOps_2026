package br.com.protons.sprintapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ALTERNATIVA")
@Getter
@Setter
@NoArgsConstructor

// Representa uma alternativa da pergunta
public class Alternativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String texto;

    @Column(nullable = false)
    private boolean correta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pergunta", nullable = false)
    private Pergunta pergunta;

    // Cria uma alternativa com texto, correção e pergunta associada
    public Alternativa(String texto, boolean correta, Pergunta pergunta) {
        this.texto = texto;
        this.correta = correta;
        this.pergunta = pergunta;
    }
}
