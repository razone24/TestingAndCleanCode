package com.playtika.testingandcleancode;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/divergence")
@RequiredArgsConstructor
public class DivergenceController {

    private final DivergenceService divergenceService;

    @GetMapping
    public void getDivergence() {
        divergenceService.check();
    }
}
