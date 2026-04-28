package fst.utm.projet.entities;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = "classes")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY) // Hides ID in Swagger POST body
    private Integer idUtilisateur;

    private String prenom;
    private String nom;
    private String password;

    @ManyToMany
    @JoinTable(
            name = "utilisateur_classe",
            joinColumns = @JoinColumn(name = "id_utilisateur"),
            inverseJoinColumns = @JoinColumn(name = "code_classe")
    )
    @Schema(hidden = true) // Hide relationships from simple creation forms
    private List<Classe> classes;
}