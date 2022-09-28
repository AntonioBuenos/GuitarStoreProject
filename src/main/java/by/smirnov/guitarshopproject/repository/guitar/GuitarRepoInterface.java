package by.smirnov.guitarshopproject.repository.guitar;

import by.smirnov.guitarshopproject.model.Guitar;
import by.smirnov.guitarshopproject.repository.CRUDRepository;

public interface GuitarRepoInterface extends CRUDRepository<Long, Guitar> {

Double showAverageGuitarPrice();
}
