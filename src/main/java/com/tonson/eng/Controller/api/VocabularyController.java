package com.tonson.eng.Controller.api;

import com.tonson.eng.Controller.request.VocabularyRequest;
import com.tonson.eng.exception.ValidationException;
import com.tonson.eng.exception.VocabularyNotFoundException;
import com.tonson.eng.model.Vocabulary;
import com.tonson.eng.service.VocabularyService;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vocabulary")
public class VocabularyController {

    private final VocabularyService vocabularyService;

    public VocabularyController(VocabularyService vocabularyService) {
        this.vocabularyService = vocabularyService;
    }

    @GetMapping
    public List<Vocabulary> getAllVacabulary() {
        return vocabularyService.getAllVocabulary();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Vocabulary addVocabulary(@Valid VocabularyRequest vocabularyRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(fieldError -> {
                throw new ValidationException(fieldError.getField() + " : " + fieldError.getDefaultMessage());
            });
        }
        return vocabularyService.createVocabulary(vocabularyRequest);
    }

    @PutMapping("/{id}")
    public Vocabulary editVocabulary(@Valid VocabularyRequest vocabularyRequest, BindingResult bindingResult, @PathVariable long id) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(fieldError -> {
                throw new ValidationException(fieldError.getField() + " : " + fieldError.getDefaultMessage());
            });
        }
        return vocabularyService.updateVocabulary(vocabularyRequest, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteVocabulary(@PathVariable long id) {
        vocabularyService.deleteVocabulary(id);
    }

    @GetMapping(path = "/search", params = "eng")
    public List<Vocabulary> vocabularyByEng(@RequestParam String eng) {
        return vocabularyService.getVocabularyByEng(eng);
    }

    @GetMapping(path = "/search", params = "thai")
    public List<Vocabulary> vocabularyByThai(@RequestParam String thai) {
        return vocabularyService.getVocabularyByThai(thai);
    }
}
