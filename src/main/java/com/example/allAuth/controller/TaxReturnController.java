package com.example.allAuth.controller;

import com.example.allAuth.DTOs.TaxReturnDTO;
import com.example.allAuth.entity.TaxReturn;
import com.example.allAuth.service.TaxReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/taxreturn")
public class TaxReturnController {
    @Autowired
    private TaxReturnService taxReturnService;

    @PostMapping("/submit")
    public TaxReturn submitTaxReturn(@RequestBody TaxReturnDTO taxReturnDTO ) throws IOException {
        return taxReturnService.submitTaxReturn(taxReturnDTO);
    }
    @GetMapping("/all")
    public List<TaxReturn> getAllTaxReturns() {
        return taxReturnService.findAll();
    }

    @GetMapping("/findByTaxReturnById/{id}")
    public TaxReturn getTaxReturnById(@PathVariable Long id) {
        return taxReturnService.findById(id).get();
    }


    @GetMapping("/findByTaxpayerTaxpayerId/{id}")
    public List<TaxReturn> findByTaxpayerId(@PathVariable Long id) {
        return taxReturnService.findByTaxpayerId(id);
    }


    @PutMapping("/update/{id}")
    public TaxReturn updateTaxReturn(@PathVariable Long id, @RequestBody TaxReturnDTO taxReturnDTO) throws IOException {
        return taxReturnService.updateTaxReturn(id, taxReturnDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTaxReturn(@PathVariable Long id) {
        taxReturnService.deleteTaxReturn(id);
    }
}
