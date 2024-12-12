package fact.it.contractservice.controller;


import fact.it.contractservice.dto.ContractResponse;
import fact.it.contractservice.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// docker run --name mysql-home-tenant -p 3334:3306 -e MYSQL_ROOT_PASSWORD=abc123 -d mysql

@RestController
@RequestMapping("/api/contract")
@RequiredArgsConstructor
public class ContractController {
    private final ContractService contractService;

//    @GetMapping("/all")
//    @ResponseStatus(HttpStatus.OK)
//    public List<HomeTenantResponse> getAllHomeTenants() {
//        return homeTenantService.getAll();
//    }

    @GetMapping("/home/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<ContractResponse> getAllContractsByUserId(@PathVariable String id) {
        return contractService.getAllContractsByHomeId(id);
    }

//    @GetMapping("/home/{id}/current")
//    @ResponseStatus(HttpStatus.OK)
//    public HomeTenantResponse getCurrentHomeTenantsByUserId(@PathVariable String id) {
//        return homeTenantService.getCurrentHomeTenantByHomeId(id);
//    }
}
