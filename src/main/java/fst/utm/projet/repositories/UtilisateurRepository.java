package fst.utm.projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import fst.utm.projet.entities.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
}
