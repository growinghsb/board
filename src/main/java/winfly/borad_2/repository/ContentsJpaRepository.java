package winfly.borad_2.repository;

import winfly.borad_2.domain.Contents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ContentsJpaRepository implements ContentsRepository {

    private final EntityManager em;

    @Override
    public Long save(Contents contents) {
        em.persist(contents);
        return contents.getId();
    }

    @Override
    public Contents findOne(Long id) {
        return em.find(Contents.class, id);
    }

    @Override
    public List<Contents> findAll() {
        return em.createQuery("select c from Contents c", Contents.class)
                .getResultList();
    }

    @Override
    public void delete(Contents contents) {
        em.remove(contents);
    }
}
