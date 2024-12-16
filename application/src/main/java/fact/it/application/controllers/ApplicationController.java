package fact.it.application.controllers;

import fact.it.application.Dto.ContractResponse;
import fact.it.application.Dto.HomeResponse;
import fact.it.application.services.ContractService;
import fact.it.application.services.HomeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ApplicationController {
    private final HomeService homeService;
    private final ContractService contractService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/homes")
    public String homes(Model model) {
        ArrayList<HomeResponse> homes = homeService.getAvailableHomes();
        model.addAttribute("homes", homes);
        return "homes";
    }

    @GetMapping("/admin")
    public String allHomes(Model model, HttpSession session) {
        ArrayList<HomeResponse> homes = homeService.getAllHomes(session);
        model.addAttribute("homes", homes);
        return "admin";
    }

    @GetMapping("/homes/{id}")
    public String homeDetail(@PathVariable String id, HttpSession session, Model model) {
        List<ContractResponse> contracts = contractService.getContractsFromHome(id, session);
        ContractResponse activeContract = contractService.getCurrentContractFromHome(id, session);

        if (activeContract != null) {
            model.addAttribute("activeContract", activeContract);

        } else {
            model.addAttribute("message", "Geen actieve huurder voor dit huis.");
        }

        model.addAttribute("contracts", contracts);
        model.addAttribute("home", homeService.getHomeDetailsById(id, session));
        return "homedetail";
    }
}
