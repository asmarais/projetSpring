package fst.utm.projet.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import fst.utm.projet.entities.Classe;
import fst.utm.projet.entities.CoursClassroom;
import fst.utm.projet.entities.Utilisateur;
import fst.utm.projet.enumerations.Niveau;
import fst.utm.projet.enumerations.Specialite;
import fst.utm.projet.services.ICoursClassroomService;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name = "CoursClassroom API", description = "Gestion des cours et classrooms pour l'établissement FST")
public class CoursClassroomRestController {

    private final ICoursClassroomService service;

    // a) Ajouter un utilisateur
    // POST http://localhost:8089/api/utilisateurs
    @Operation(summary = "Ajouter un utilisateur", description = "Permet d'ajouter un utilisateur à la base")
    @PostMapping("/utilisateurs")
    @ResponseStatus(HttpStatus.CREATED)
    public Utilisateur ajouterUtilisateur(@RequestBody Utilisateur utilisateur) {
        return service.ajouterUtilisateur(utilisateur);
    }

    // b) Ajouter une classe
    // POST http://localhost:8089/api/classes
    @Operation(summary = "Ajouter une classe", description = "Permet de créer des classes comme 4AG1 ou 5EM1")
    @PostMapping("/classes")
    @ResponseStatus(HttpStatus.CREATED)
    public Classe ajouterClasse(@RequestBody Classe c) {
        return service.ajouterClasse(c);
    }

    // c) Ajouter un CoursClassroom et l'affecter à une classe
    // POST http://localhost:8089/api/cours?codeClasse=1
    @Operation(summary = "Ajouter un cours et l'affecter à une classe",
            description = "Crée un cours (ex: Programmation C) et le lie à une classe par son code")
    @PostMapping("/cours")
    @ResponseStatus(HttpStatus.CREATED)
    public CoursClassroom ajouterCoursClassroom(
            @RequestBody CoursClassroom cc,
            @Parameter(description = "Code unique de la classe à laquelle affecter le cours")
            @RequestParam Integer codeClasse) {
        return service.ajouterCoursClassroom(cc, codeClasse);
    }

    // d) Affecter un utilisateur à une classe
    // PUT http://localhost:8089/api/affectation?idUtilisateur=1&codeClasse=1
    @Operation(summary = "Affecter un utilisateur à une classe")
    @PutMapping("/affectation")
    public void affecterUtilisateurClasse(
            @Parameter(description = "ID de l'utilisateur (Amna ou Ahmed)") @RequestParam Integer idUtilisateur,
            @Parameter(description = "Code de la classe (4AG1 ou 5EM1)") @RequestParam Integer codeClasse) {
        service.affecterUtilisateurClasse(idUtilisateur, codeClasse);
    }

    // e) Nombre d'utilisateurs par niveau
    // GET http://localhost:8089/api/utilisateurs/count?niveau=QUATRIEME
    @Operation(summary = "Nombre d'utilisateurs par niveau",
            description = "Retourne le nombre total d'étudiants/profs pour un niveau spécifique")
    @GetMapping("/utilisateurs/count")
    public Integer nbUtilisateursParNiveau(
            @Parameter(description = "Niveau à filtrer (PREMIERE à CINQUIEME)")
            @RequestParam Niveau niveau) {
        return service.nbUtilisateursParNiveau(niveau);
    }

    // f) Désaffecter un CoursClassroom de sa classe
    // PUT http://localhost:8089/api/cours/1/desaffecter [
    @Operation(summary = "Désaffecter un cours de sa classe",
            description = "Retire le lien entre un cours (ex: Plantes) et sa classe actuelle")
    @PutMapping("/cours/{idCours}/desaffecter")
    public void desaffecterCoursClassroomClasse(
            @Parameter(description = "ID du cours à désaffecter")
            @PathVariable Integer idCours) {
        service.desaffecterCoursClassroomClasse(idCours);
    }

    // g) Archiver manuellement
    // PUT http://localhost:8089/api/cours/archiver
    @Operation(summary = "Archiver tous les cours",
            description = "Bascule l'état 'archive' à true pour tous les cours. Note: Un scheduler automatise cela toutes les 60s.")
    @PutMapping("/cours/archiver")
    public void archiverCoursClassrooms() {
        service.archiverCoursClassrooms();
    }

    // h) Nombre d'heures par spécialité et niveau
    // GET http://localhost:8089/api/cours/heures?specialite=AGRICULTURE&niveau=QUATRIEME
    @Operation(summary = "Calculer le volume horaire",
            description = "Calcule la somme des heures pour une spécialité et un niveau donnés")
    @GetMapping("/cours/heures")
    public Integer nbHeuresParSpecEtNiv(
            @Parameter(description = "Spécialité (INFORMATIQUE, GENIECIVIL, AGRICULTURE)") @RequestParam Specialite specialite,
            @Parameter(description = "Niveau universitaire") @RequestParam Niveau niveau) {
        return service.nbHeuresParSpecEtNiv(specialite, niveau);
    }
}