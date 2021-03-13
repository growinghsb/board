package winfly.borad_2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public List<Contents> writerSearch(String writer) {
        return em.createQuery("select c from Contents c where c.writer = :writer", Contents.class)
                .setParameter("writer", writer)
                .getResultList();
    }

    @Override
    public List<String> findAllWriter() {
        return em.createQuery("select distinct c.writer from Contents c order by c.writer asc", String.class)
                .getResultList();
    }

    @Override
    public List<Contents> keywordSearch(String keyword) {
        return em.createQuery("select c from Contents c where c.title like :keyword", Contents.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
    }

    @Override
    public List<Contents> paging(int pageNum) {
        return em.createQuery("select c from Contents c order by c.id asc", Contents.class)
                .setFirstResult((pageNum - 1) * 5)
                .setMaxResults(5)
                .getResultList();
    }

    @Override
    public Long dataCounting() {
        return em.createQuery("select count(c) from Contents c", Long.class).getSingleResult();
    }
}
