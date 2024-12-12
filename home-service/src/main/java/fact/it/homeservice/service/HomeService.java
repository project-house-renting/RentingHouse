package fact.it.homeservice.service;

import fact.it.homeservice.dto.HomeRequest;
import fact.it.homeservice.dto.HomeResponse;
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

import java.util.ArrayList;
import java.util.List;


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
                    .userId(1L)
                    .address("Oude Veerlebaan 12")
                    .yearOfConstruction("2012")
                    .type("house")
                    .build();

            Home home1 = Home.builder()
                    .userId(2L)
                    .address("Kerkhofweg 18")
                    .yearOfConstruction("2002")
                    .type("house")
                    .build();

            homeRepository.save(home);
            homeRepository.save(home1);
        }
    }

    public List<HomeResponse> getAllHomes() {
        List<Home> homes = homeRepository.findAll();
        return homes.stream().map(this::mapToHomeResponse).toList();
    }

    public HomeResponse getHomeById(String id) {
        Home home = homeRepository.findHomeById(id);
        return mapToHomeResponse(home);
    }

    public List<HomeResponse> getHomesByUserId(Long id) {
        List<Home> home = homeRepository.findHomesByUserId(id);
        return home.stream().map(this::mapToHomeResponse).toList();
    }

    public void addHome(HomeRequest homeRequest) {
        Home home = Home.builder()
                .type(homeRequest.getType())
                .yearOfConstruction(homeRequest.getYearOfConstruction())
                .address(homeRequest.getAddress())
                .build();

        homeRepository.save(home);
    }

    public void updateHome(String id, HomeRequest homeRequest) {
        Home home = homeRepository.findHomeById(id);

        if (home != null) {
            home.setAddress(homeRequest.getAddress());
            home.setType(home.getType());
            home.setYearOfConstruction(homeRequest.getYearOfConstruction());

            homeRepository.save(home);
        }
    }

    public void deleteHome(String id) {
        Home home = homeRepository.findHomeById(id);

        if (home != null) {
            homeRepository.delete(home);
        }
    }

    private HomeResponse mapToHomeResponse(Home home) {
        return HomeResponse.builder()
                .id(home.getId())
                .userId(home.getUserId())
                .address(home.getAddress())
                .yearOfConstruction(home.getYearOfConstruction())
                .type(home.getType())
                .build();
    }
}
