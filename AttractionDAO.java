package dao;
import model.Attraction;

import java.util.*;

public interface AttractionDAO {
    List<Attraction> getAllAttractions();
    Attraction getAttractionById(int id);
    void addAttraction(Attraction a);
    void updateAttraction(Attraction a);
    void deleteAttraction(int id);
}
//ce fichier va nous être utile pour:
