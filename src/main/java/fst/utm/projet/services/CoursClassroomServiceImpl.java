package fst.utm.projet.services;

import fst.utm.projet.entities.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import fst.utm.projet.entities.Classe;
import fst.utm.projet.entities.CoursClassroom;
import fst.utm.projet.entities.Utilisateur;
import fst.utm.projet.enumerations.Niveau;
import fst.utm.projet.enumerations.Specialite;
import fst.utm.projet.repositories.ClasseRepository;
import fst.utm.projet.repositories.CoursClassroomRepository;
import fst.utm.projet.repositories.UtilisateurRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CoursClassroomServiceImpl implements ICoursClassroomService {

    private final UtilisateurRepository utilisateurRepository;
    private final ClasseRepository classeRepository;
    private final CoursClassroomRepository coursClassroomRepository;

    // -------------------------------------------------------
    // a) Ajouter un utilisateur
    // -------------------------------------------------------
    @Override
    public Utilisateur ajouterUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    // -------------------------------------------------------
    // b) Ajouter une classe
    // -------------------------------------------------------
    @Override
    public Classe ajouterClasse(Classe c) {
        return classeRepository.save(c);
    }

    // -------------------------------------------------------
    // c) Ajouter un CoursClassroom et l'affecter à une classe
    // -------------------------------------------------------
    @Override
    public CoursClassroom ajouterCoursClassroom(CoursClassroom cc, Integer codeClasse) {
        Classe classe = classeRepository.findById(codeClasse)
                .orElseThrow(() -> new RuntimeException("Classe introuvable : " + codeClasse));
        cc.setClasse(classe);
        return coursClassroomRepository.save(cc);
    }

    // -------------------------------------------------------
    // d) Affecter un utilisateur à une classe
    // -------------------------------------------------------
    @Override
    public void affecterUtilisateurClasse(Integer idUtilisateur, Integer codeClasse) {
        Utilisateur utilisateur = utilisateurRepository.findById(idUtilisateur)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable : " + idUtilisateur));
        Classe classe = classeRepository.findById(codeClasse)
                .orElseThrow(() -> new RuntimeException("Classe introuvable : " + codeClasse));

        if (utilisateur.getClasses() == null) {
            utilisateur.setClasses(new ArrayList<>());
        }
        utilisateur.getClasses().add(classe);
        utilisateurRepository.save(utilisateur);
    }

    // -------------------------------------------------------
    // e) Nombre d'utilisateurs pour un niveau donné
    // -------------------------------------------------------
    @Override
    public Integer nbUtilisateursParNiveau(Niveau nv) {
        // On récupère toutes les classes du niveau voulu
        List<Classe> classes = classeRepository.findAll().stream()
                .filter(c -> c.getNiveau() == nv)
                .toList();

        // On compte les utilisateurs distincts dans ces classes
        return (int) classes.stream()
                .filter(c -> c.getUtilisateurs() != null)
                .flatMap(c -> c.getUtilisateurs().stream())
                .distinct()
                .count();
    }

    // -------------------------------------------------------
    // f) Désaffecter un CoursClassroom de sa classe
    // -------------------------------------------------------
    @Override
    public void desaffecterCoursClassroomClasse(Integer idCours) {
        CoursClassroom cc = coursClassroomRepository.findById(idCours)
                .orElseThrow(() -> new RuntimeException("CoursClassroom introuvable : " + idCours));
        cc.setClasse(null);
        coursClassroomRepository.save(cc);
    }

    // -------------------------------------------------------
    // g) Archiver tous les CoursClassrooms toutes les 60 secondes
    // -------------------------------------------------------
    @Override
    @Scheduled(fixedRate = 60000)   // 60 000 ms = 60 secondes
    public void archiverCoursClassrooms() {
        List<CoursClassroom> tous = coursClassroomRepository.findAll();
        tous.forEach(cc -> cc.setArchive(true));
        coursClassroomRepository.saveAll(tous);
        log.info(">>> [Scheduler] {} cours archivés automatiquement", tous.size());
    }

    // -------------------------------------------------------
    // h) Nombre d'heures pour une spécialité et un niveau
    // -------------------------------------------------------
    @Override
    public Integer nbHeuresParSpecEtNiv(Specialite sp, Niveau nv) {
        Integer total = coursClassroomRepository.sumNbHeuresBySpecialiteAndNiveau(sp, nv);
        return total != null ? total : 0;
    }
}
