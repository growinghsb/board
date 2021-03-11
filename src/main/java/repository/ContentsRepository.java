package repository;


import domain.Contents;

import java.util.List;

public interface ContentsRepository {

    Long save(Contents contents);

    Contents findOne(Long id);

    List<Contents> findAll();

    void delete(Contents contents);

    //검색과 페이징은 추후에 추가하자.
}
