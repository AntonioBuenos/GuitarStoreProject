package by.smirnov.guitarshopproject.repository.guitar;

import by.smirnov.guitarshopproject.model.Guitar;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
public class HibernateGuitarRepo implements GuitarRepoInterface{

    private final SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    @Override
    public Guitar findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        // в чем отличие от sessionFactory.openSession()?
        return session.get(Guitar.class, id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Guitar> findOne(Long id) {
        return Optional.of(findById(id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Guitar> findAll() {
        return findAll(DEFAULT_FIND_ALL_LIMIT, DEFAULT_FIND_ALL_OFFSET);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Guitar> findAll(int limit, int offset) {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from Guitar p where p.isDeleted = false " +
                        "order by p.id", Guitar.class)
                .getResultList();

/*        return session.createQuery("select p from Guitar p where p.isDeleted = false " +
                        "order by p.id limit " + limit + " offset " + offset, Guitar.class)
                .getResultList();*/
    }

    @Transactional
    @Override
    public void create(Guitar object) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(object);
    }

    @Transactional
    @Override
    public Guitar update(Guitar guitar) {
        Session session = sessionFactory.getCurrentSession();
        Guitar toBeUpdated = session.get(Guitar.class, guitar.getId());
        toBeUpdated.setTypeof(guitar.getTypeof());
        toBeUpdated.setShape(guitar.getShape());
        toBeUpdated.setSeries(guitar.getSeries());
        toBeUpdated.setModel(guitar.getModel());
        toBeUpdated.setStringsQnt(guitar.getStringsQnt());
        toBeUpdated.setNeck(guitar.getNeck());
        toBeUpdated.setBridge(guitar.getBridge());
        toBeUpdated.setBodyMaterial(guitar.getBodyMaterial());
        toBeUpdated.setPrice(guitar.getPrice());
        toBeUpdated.setProdCountry(guitar.getProdCountry());
        toBeUpdated.setBrandId(guitar.getBrandId());
        toBeUpdated.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        return toBeUpdated;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Guitar toBeDeleted = session.get(Guitar.class, id);
        toBeDeleted.setDeleted(true);
        toBeDeleted.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Transactional
    @Override
    public Double showAverageGuitarPrice() {
        Session session = sessionFactory.getCurrentSession();

        List<Double> result = session.createQuery("select avg(p.price) from Guitar p where p.isDeleted = false", Double.class)
                .getResultList();
        return result.get(0);
    }
}
