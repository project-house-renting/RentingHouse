package fact.it.homeservice.service;

import fact.it.homeservice.dto.HomeRequest;
import fact.it.homeservice.dto.HomeResponse;
import fact.it.homeservice.dto.MaintenanceResponse;
import fact.it.homeservice.model.Home;
import fact.it.homeservice.repository.HomeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Transactional
public class HomeService {
    private final HomeRepository homeRepository;
    private final WebClient webClient;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${maintenanceService.baseurl}")
    private String maintenanceServiceBaseUrl;

    @PostConstruct
    public void loadData() {
//        mongoTemplate.getDb().drop();

        if (homeRepository.count() <= 0) {
            Home home = Home.builder()
                    .address("Oude Veerlebaan 12")
                    .yearOfConstruction("2012")
                    .type("house")
                    .isRentable(false)
                    .build();

            Home home1 = Home.builder()
                    .address("Kerkhofweg 18")
                    .yearOfConstruction("2002")
                    .type("house")
                    .isRentable(true)
                    .build();

            Home home2 = Home.builder()
                    .address("Steentjesstraat 7")
                    .yearOfConstruction("2004")
                    .type("rijhuis")
                    .isRentable(true)
                    .build();

            homeRepository.save(home);
            homeRepository.save(home1);
            homeRepository.save(home2);
        }
    }

    public List<HomeResponse> getAllHomes() {
        List<Home> homes = homeRepository.findAll();
        return homes.stream().map(this::mapToHomeResponse).toList();
    }

    public List<HomeResponse> getAllAvailableHomes() {
        List<Home> homes = homeRepository.findAll()
                .stream().filter(Home::isRentable).toList();

        return homes.stream().map(this::mapToHomeResponse).toList();
    }

    public HomeResponse getHomeById(String id) {
        Home home = homeRepository.findHomeById(id);
        return mapToHomeResponse(home);
    }

    public HomeResponse getHomeDetailsById(String id) {
        Home home = homeRepository.findHomeById(id);
        return mapToHomeWithDetailsResponse(home);
    }

    public void addHome(HomeRequest homeRequest) {
        Home home = Home.builder()
                .type(homeRequest.getType())
                .yearOfConstruction(homeRequest.getYearOfConstruction())
                .address(homeRequest.getAddress())
                .isRentable(true)
                .build();

        homeRepository.save(home);
    }

    public void updateHome(String id, HomeRequest homeRequest) {
        Home home = homeRepository.findHomeById(id);

        if (home != null) {
            home.setAddress(homeRequest.getAddress());
            home.setType(home.getType());
            home.setYearOfConstruction(homeRequest.getYearOfConstruction());
            home.setRentable(homeRequest.isRentable());
            homeRepository.save(home);
        }
    }

    public void deleteHome(String id) {
        Home home = homeRepository.findHomeById(id);

        if (home != null) {
            homeRepository.delete(home);
        }
    }

    private HomeResponse mapToHomeWithDetailsResponse(Home home) {
        HomeResponse response = mapToHomeResponse(home);

        List<MaintenanceResponse> maintenances = Arrays.stream(Objects.requireNonNull(webClient.get()
                .uri("http://" + maintenanceServiceBaseUrl + "/api/maintenance/home/{id}", home.getId())
                .retrieve()
                .bodyToMono(MaintenanceResponse[].class)
                .block())).toList();

        response.setMaintenances(maintenances);
        return response;
    }

    private HomeResponse mapToHomeResponse(Home home) {
        return HomeResponse.builder()
                .id(home.getId())
                .address(home.getAddress())
                .yearOfConstruction(home.getYearOfConstruction())
                .type(home.getType())
                .build();
    }
}
