package dao;

import model.Parc;
import java.util.List;

public interface ParcDAOInterface {
    List<Parc> getAllParcs();
    List<Parc> getAllParcsWithAttractions(); // ✅ Ajouté ici
}
//ce fichier va nous être utile pour:
