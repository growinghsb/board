package winfly.borad_2.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import winfly.borad_2.controller.dto.ContentsDto;
import winfly.borad_2.service.ContentsService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ContentsController {

    private final ContentsService service;

    @GetMapping("/")
    public String main(@RequestParam(value = "writer", required = false) String writer,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "pageNum", required = false) Integer pageNum,
                       Model model) {
        model.addAttribute("contents", parameterHandler(writer, keyword, pageNum));
        model.addAttribute("writers", service.findAllWriter());
        model.addAttribute("totalDataCount", service.dataTotalCount());
        return "main";
    }

    public List<ContentsDto> parameterHandler(String writer, String keyword, Integer pageNum) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (writer != null && writer.equals("전체보기")) {
            return service.findAllPagination(pageNum);
        }

        if (writer != null) {
            return service.writerSearch(writer);
        }

        if (keyword != null) {
            return service.keywordSearch(keyword);
        }
        return service.findAllPagination(pageNum);
    }


    @GetMapping("write")
    public String write(Model model) {
        model.addAttribute("dto", new ContentsDto());
        return "write";
    }

    @PostMapping("write")
    public String wirte(ContentsDto dto) {
        service.save(dto);
        return "redirect:/";
    }

    @GetMapping("detail")
    public String detail(@RequestParam("id") Long id, Model model) {
        service.viewsUp(id);
        model.addAttribute("content", service.findOne(id));
        model.addAttribute("dto", new ContentsDto());
        return "detail";
    }

    @PostMapping("revise")
    public String revise(@RequestParam("id") Long id, ContentsDto dto) {
        service.revise(dto);
        return "redirect:/";
    }

    @GetMapping("delete")
    public String delete(@RequestParam("id") Long id) {
        service.delete(id);
        return "redirect:/";
    }
}
