package fact.it.homeservice;


import fact.it.homeservice.dto.HomeRequest;
import fact.it.homeservice.dto.HomeResponse;
import fact.it.homeservice.dto.MaintenanceResponse;
import fact.it.homeservice.model.Home;
import fact.it.homeservice.repository.HomeRepository;
import fact.it.homeservice.service.HomeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HomeServiceUnitTest {
    @InjectMocks
    private HomeService homeService;

    @Mock
    private HomeRepository homeRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Value("${maintenanceService.baseurl}")
    private String maintenanceServiceBaseUrl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetAllHomes() {
        // Arrange
        List<Home> homes = List.of(
                Home.builder().id("home1").address("Oude Veerlebaan 12").build(),
                Home.builder().id("home2").address("Kerkhofweg 18").build()
        );
        when(homeRepository.findAll()).thenReturn(homes);

        // Act
        List<HomeResponse> homeResponses = homeService.getAllHomes();

        // Assert
        assertNotNull(homeResponses);
        assertEquals(2, homeResponses.size());
        verify(homeRepository, times(1)).findAll();
    }

    @Test
    void testGetAllAvailableHomes() {
        // Arrange
        List<Home> availableHomes = List.of(
                Home.builder().id("home2").address("Kerkhofweg 18").isRentable(true).build()
        );
        when(homeRepository.findAll()).thenReturn(availableHomes);

        // Act
        List<HomeResponse> homeResponses = homeService.getAllAvailableHomes();

        // Assert
        assertNotNull(homeResponses);
        assertEquals(1, homeResponses.size());
        verify(homeRepository, times(1)).findAll();
    }

    @Test
    void testGetHomeDetailsById() {
        // Arrange
        String homeId = "home1";
        Home home = Home.builder().id(homeId).address("Oude Veerlebaan 12").build();
        when(homeRepository.findHomeById(homeId)).thenReturn(home);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), eq(homeId))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(MaintenanceResponse[].class)).thenReturn(Mono.just(new MaintenanceResponse[0]));

        // Act
        HomeResponse homeResponse = homeService.getHomeDetailsById(homeId);

        // Assert
        assertNotNull(homeResponse);
        assertEquals(homeId, homeResponse.getId());
        verify(homeRepository, times(1)).findHomeById(homeId);
    }


    @Test
    public void testAddHome() {
        // Arrange
        HomeRequest homeRequest = HomeRequest.builder()
                .type("Apartment")
                .yearOfConstruction("2000")
                .address("123 Main Street")
                .build();

        Home savedHome = Home.builder()
                .id("home1")
                .type("Apartment")
                .yearOfConstruction("2000")
                .address("123 Main Street")
                .isRentable(true)
                .build();

        when(homeRepository.save(any(Home.class))).thenReturn(savedHome);

        // Act
        homeService.addHome(homeRequest);

        // Assert
        verify(homeRepository, times(1)).save(any(Home.class));
    }

    @Test
    void testUpdateHome() {
        // Arrange
        String homeId = "home1";
        HomeRequest homeRequest = new HomeRequest("Updated Street 20", "house", "2015", "Updated description", 1500.0f, true);
        Home home = Home.builder().id(homeId).address("Old Address").type("house").build();
        when(homeRepository.findHomeById(homeId)).thenReturn(home);

        // Act
        homeService.updateHome(homeId, homeRequest);

        // Assert
        verify(homeRepository, times(1)).save(any(Home.class));
        assertEquals(homeRequest.getAddress(), home.getAddress());
    }

    @Test
    void testDeleteHome() {
        // Arrange
        String homeId = "home1";
        Home home = Home.builder().id(homeId).build();
        when(homeRepository.findHomeById(homeId)).thenReturn(home);

        // Act
        homeService.deleteHome(homeId);

        // Assert
        verify(homeRepository, times(1)).delete(home);
    }
}
