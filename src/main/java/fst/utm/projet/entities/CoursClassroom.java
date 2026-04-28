package fst.utm.projet.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import fst.utm.projet.enumerations.Specialite;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = "classe")
public class CoursClassroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idCours;

    @Enumerated(EnumType.STRING) // Requirement: stored as String
    private Specialite specialite;
    private String nom;
    private Integer nbHeures;
    private Boolean archive;

    @ManyToOne
    @JoinColumn(name = "code_classe")
    @JsonIgnore // Prevents infinite recursion in JSON
    @Schema(hidden = true)
    private Classe classe;
}