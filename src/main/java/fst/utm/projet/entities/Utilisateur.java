package fst.utm.projet.entities;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = "classe") // Changed to singular
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idUtilisateur;

    private String prenom;
    private String nom;
    private String password;

    @ManyToOne
    @JoinColumn(name = "code_classe") // Creates a foreign key column in the Utilisateur table
    @Schema(hidden = true)
    private Classe classe; // Changed from List<Classe> to Classe
}