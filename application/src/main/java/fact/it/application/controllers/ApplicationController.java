package fact.it.application.controllers;

import fact.it.application.Dto.ContractResponse;
import fact.it.application.services.ContractService;
import fact.it.application.services.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ApplicationController {
    private final HomeService homeService;
    private final ContractService contractService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("homes", homeService.getHomes());
        return "index";
    }

    @GetMapping("/homes")
    public String homes(Model model) {
        model.addAttribute("homes", homeService.getHomes());
        return "homes";
    }

    @GetMapping("/homes/{id}")
    public String homeDetail(@PathVariable String id, Model model) {
        List<ContractResponse> contracts = contractService.getContractsFromHome(id);
        Optional<ContractResponse> activeContract = contractService.getActiveContract(contracts);
        contracts = contracts.stream().filter(contract -> !contract.isActive()).collect(Collectors.toList());

//        List<Payment> payments = paymentService.findPaymentsForHome(homeId);

        if (activeContract.isPresent()) {
            model.addAttribute("activeContract", activeContract.get());

        } else {
            model.addAttribute("message", "Geen actieve huurder voor dit huis.");
        }

        model.addAttribute("contracts", contracts);
        model.addAttribute("home", homeService.getHomeById(id));
        return "homedetail";
    }

    @GetMapping("/payments/{id}")
    public String payments(@PathVariable Long id, Model model) {

        return "payments";
    }
}
