package fact.it.application.controllers;

import fact.it.application.Dto.ContractResponse;
import fact.it.application.Dto.HomeRequest;
import fact.it.application.Dto.HomeResponse;
import fact.it.application.services.ContractService;
import fact.it.application.services.HomeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ApplicationController {
    private final HomeService homeService;
    private final ContractService contractService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/crud/create")
    public String getCreateCrud(Model model) {
        HomeResponse home = new HomeResponse();
        model.addAttribute("home", home);
        return "crud";
    }

    @GetMapping("/crud/update/{id}")
    public String getUpdateCrud(@PathVariable String id, Model model, HttpSession session) {
        HomeResponse home = homeService.getHomeDetailsById(id, session);
        model.addAttribute("home", home);
        return "crud";
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

    @GetMapping("/home/{id}")
    public String homeDetail(@PathVariable String id, HttpSession session, Model model) {
        List<ContractResponse> contracts = contractService.getContractsFromHome(id, session);
        ContractResponse activeContract = contractService.getCurrentContractFromHome(id, session);
        model.addAttribute("activeContract", activeContract);
        model.addAttribute("contracts", contracts);
        model.addAttribute("home", homeService.getHomeDetailsById(id, session));
        return "homedetail";
    }

    @GetMapping("/home/{id}/delete")
    public String deleteHome(@PathVariable String id, HttpSession session) {
        homeService.deleteHome(id, session);
        return "index";
    }

    @PostMapping("/home/create")
    public String createHome(HttpServletRequest request, HttpSession session) {
        String address = request.getParameter("address");
        String type = request.getParameter("type");
        String description = request.getParameter("description");
        float rentalPrice = Float.parseFloat(request.getParameter("rentalPrice"));
        String yearOfConstruction = request.getParameter("yearOfConstruction");

        HomeRequest homeRequest = HomeRequest.builder()
                .address(address)
                .type(type)
                .description(description)
                .rentalPrice(rentalPrice)
                .yearOfConstruction(yearOfConstruction)
                .isRentable(true)
                .build();

        homeService.createHome(homeRequest, session);

        return "index";
    }

    @PostMapping("/home/{id}/update")
    public String updateHome(@PathVariable String id, @ModelAttribute HomeResponse home, HttpServletRequest request, HttpSession session) {
        boolean isRentable = request.getParameter("isRentable") != null;
        home.setRentable(isRentable);
        homeService.updateHome(id, home, session);
        return "index";
    }
}
