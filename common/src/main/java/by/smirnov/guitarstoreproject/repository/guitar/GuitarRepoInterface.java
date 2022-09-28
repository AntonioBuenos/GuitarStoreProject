package by.smirnov.guitarstoreproject.repository.guitar;

import by.smirnov.guitarstoreproject.model.Guitar;
import by.smirnov.guitarstoreproject.repository.CRUDRepository;

public interface GuitarRepoInterface extends CRUDRepository<Long, Guitar> {

Double showAverageGuitarPrice();
}
