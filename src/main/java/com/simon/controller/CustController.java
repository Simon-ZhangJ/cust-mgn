package com.simon.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.simon.entity.Cust;
import com.simon.entity.EmailAddress;
import com.simon.service.CustService;

@RestController
@RequestMapping("/custs")
@Transactional
public class CustController {
    private final CustService custService;

    public CustController(CustService custService) {
        this.custService = custService;
    }

    @GetMapping(value = "/query")
    public List<Cust> custsByPartName(@RequestParam("name") String partName) {
        // System.out.println("====== partName = " + partName);
        return this.custService.queryCustsByPartName(partName);
    }

    @GetMapping
    public List<Cust> custs() {
        return this.custService.queryAllCusts();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Cust create(@RequestBody Cust param) {
        Cust cust = this.custService.createCust(param);
        return cust;
    }

    @PutMapping(consumes = "application/json")
    public Cust update(@RequestBody Cust param) {
        return this.custService.updateCust(param);
    }

    @DeleteMapping(value = "/{custId:[\\d]+}")
    public void delete(@PathVariable("custId") long custId) {
        this.custService.deleteCust(custId);
    }

    @GetMapping(value = "/{custId:[\\d]+}")
    public Cust custById(@PathVariable("custId") long custId) {
        return this.custService.queryById(custId);
    }

    @PostMapping(value = "/{custId:[\\d]+}/emails", consumes = "application/json")
    public Cust addEmailAddress(@PathVariable("custId") long custId,
        @RequestBody EmailAddress emailAddress) {
        Cust cust = this.custService.addEmailAddress(custId, emailAddress.getEmail());
        return cust;
    }

    @DeleteMapping(value = "/{custId:[\\d]+}/emails", consumes = "application/json")
    public void deleteEmailAddr(@PathVariable("custId") long custId,
        @RequestBody EmailAddress emailAddress) {
        this.custService.deleteEmailAddress(custId, emailAddress.getEmail());
    }
}
