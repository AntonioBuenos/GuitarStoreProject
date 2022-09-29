package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.model.Guitar;
import by.smirnov.guitarstoreproject.repository.GuitarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GuitarService {

/*    private final HibernateGuitarRepo guitarRepo;*/

    private final GuitarRepository guitarRepo;

    public Guitar findById(Long id) {
        return guitarRepo.findById(id).orElse(null);
    }

    public List<Guitar> findAll() {
        return guitarRepo.findByIsDeletedOrderById(false);
    }

    public List<Guitar> findAll(int limit, int offset) {
        return guitarRepo.findByIsDeletedOrderById(false);
    }

    public void create(Guitar object) {
        guitarRepo.save(object);
    }

    //add 'isDeleted' check and forbid update deleted + message
    public Guitar update(Guitar toBeUpdated) {
        Guitar old = guitarRepo.getReferenceById(toBeUpdated.getId());
        toBeUpdated.setCreationDate(old.getCreationDate());
        toBeUpdated.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        return guitarRepo.save(toBeUpdated);
    }

    //add !=null check + message for cannot be deleted
    public void delete(Long id) {
        Guitar toBeDeleted = guitarRepo.findById(id).orElse(null);
        toBeDeleted.setIsDeleted(true);
        toBeDeleted.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
        guitarRepo.save(toBeDeleted);
    }

    //add !=null check + message for cannot be deleted
    public void hardDelete(Long id){
        //Guitar toBeHardDeleted = guitarRepo.findById(id).orElse(null);
        guitarRepo.deleteById(id);
    }

    public String showAverageGuitarPrice() {
        return String.format("%.2f", guitarRepo.findByHQLQuery()) + "$";
    }
}
