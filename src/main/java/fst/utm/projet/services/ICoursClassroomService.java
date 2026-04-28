package fst.utm.projet.services;

import fst.utm.projet.entities.Utilisateur;
import fst.utm.projet.entities.Classe;
import fst.utm.projet.entities.CoursClassroom;
import fst.utm.projet.entities.Utilisateur;
import fst.utm.projet.enumerations.Niveau;
import fst.utm.projet.enumerations.Specialite;

public interface ICoursClassroomService {

    // a) Ajouter un utilisateur
    Utilisateur ajouterUtilisateur(Utilisateur utilisateur);

    // b) Ajouter une classe
    Classe ajouterClasse(Classe c);

    // c) Ajouter un CoursClassroom et l'affecter à une classe
    CoursClassroom ajouterCoursClassroom(CoursClassroom cc, Integer codeClasse);

    // d) Affecter un utilisateur à une classe
    void affecterUtilisateurClasse(Integer idUtilisateur, Integer codeClasse);

    // e) Nombre d'utilisateurs pour un niveau donné
    Integer nbUtilisateursParNiveau(Niveau nv);

    // f) Désaffecter un CoursClassroom de sa classe (mettre classe à null)
    void desaffecterCoursClassroomClasse(Integer idCours);

    // g) Archiver tous les CoursClassrooms (déclenché par le scheduler)
    void archiverCoursClassrooms();

    // h) Nombre d'heures enseignées pour une spécialité et un niveau
    Integer nbHeuresParSpecEtNiv(Specialite sp, Niveau nv);
}
