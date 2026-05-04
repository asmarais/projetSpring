package fst.utm.projet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import fst.utm.projet.enumerations.Niveau;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = {"utilisateurs", "coursClassrooms"})
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer codeClasse;

    private String titre;

    @Enumerated(EnumType.STRING)
    private Niveau niveau;

    @OneToMany(mappedBy = "classe") // Points to the 'classe' field in Utilisateur
    @JsonIgnore
    @Schema(hidden = true)
    private List<Utilisateur> utilisateurs;

    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL)
    @JsonIgnore
    @Schema(hidden = true)
    private List<CoursClassroom> coursClassrooms;
}