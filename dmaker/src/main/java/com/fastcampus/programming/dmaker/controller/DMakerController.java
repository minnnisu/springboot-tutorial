package com.fastcampus.programming.dmaker.controller;

import com.fastcampus.programming.dmaker.dto.CreateDeveloper;
import com.fastcampus.programming.dmaker.dto.DeveloperDetailDto;
import com.fastcampus.programming.dmaker.dto.DeveloperDto;
import com.fastcampus.programming.dmaker.dto.EditDeveloper;
import com.fastcampus.programming.dmaker.service.DMakerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DMakerController {
    private final DMakerService dMakerService;

    @GetMapping("/developers")
    public List<DeveloperDto> getAllDevelopers() {
        log.info("GET /developers HTTP/1.1");

        return dMakerService.getAllEmployedDevelopers();
    }

    @GetMapping("/developer/{memberId}")
    public DeveloperDetailDto getDeveloperDetail(@PathVariable String memberId) {
        log.info("GET /developer/" + memberId  + "HTTP/1.1");

        return dMakerService.getDeveloperDetail(memberId);
    }

    @PostMapping ("/create-developer")
    public CreateDeveloper.Response createDevelopers(@Valid @RequestBody CreateDeveloper.Request request) { // Java Bean Validation을 동작시키기 위해 @Valid 어노테이션 추가
        log.info("request: {}", request);

        return dMakerService.createDeveloper(request);
    }

    @PutMapping("/developer/{memberId}")
    public DeveloperDetailDto editDeveloper(@PathVariable String memberId, @Valid @RequestBody EditDeveloper.Request request) {
        log.info("PUT /developer/" + memberId  + "HTTP/1.1");

        return dMakerService.editDeveloper(memberId, request);
    }

    @DeleteMapping("/developer/{memberId}")
    public DeveloperDetailDto deleteDeveloper(@PathVariable String memberId) {
        log.info("PUT /developer/" + memberId  + "HTTP/1.1");

        return dMakerService.deleteDeveloper(memberId);
    }

}
