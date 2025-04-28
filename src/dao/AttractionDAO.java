package dao;

import model.Attraction;
import java.util.List;

public interface AttractionDAO {
    List<Attraction> getAllAttractions();
    Attraction getAttractionById(int id);
    Attraction getAttractionByName(String name);
    void addAttraction(Attraction a);
    void updateAttraction(Attraction a);
    void deleteAttraction(int id);
    void deleteAttractionByName(String name); //
}
