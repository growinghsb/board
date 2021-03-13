package winfly.borad_2.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import winfly.borad_2.domain.Contents;

import java.util.List;

public interface ContentsRepository {

    Long save(Contents contents);

    Contents findOne(Long id);

    List<Contents> findAll();

    void delete(Contents contents);

    List<Contents> writerSearch(String writer);

    List<String> findAllWriter();

    List<Contents> keywordSearch(String keyword);

    List<Contents> paging(int pageNum);

    Long dataCounting();


    //검색과 페이징은 추후에 추가하자.
}
