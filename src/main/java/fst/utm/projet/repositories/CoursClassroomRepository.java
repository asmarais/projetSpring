package fst.utm.projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import fst.utm.projet.entities.CoursClassroom;
import fst.utm.projet.enumerations.Niveau;
import fst.utm.projet.enumerations.Specialite;

public interface CoursClassroomRepository extends JpaRepository<CoursClassroom, Integer> {

    // Question h) : nombre d'heures pour une spécialité et un niveau donnés
    @Query("SELECT SUM(cc.nbHeures) FROM CoursClassroom cc " +
           "WHERE cc.specialite = :sp AND cc.classe.niveau = :nv")
    Integer sumNbHeuresBySpecialiteAndNiveau(
            @Param("sp") Specialite sp,
            @Param("nv") Niveau nv);
}
