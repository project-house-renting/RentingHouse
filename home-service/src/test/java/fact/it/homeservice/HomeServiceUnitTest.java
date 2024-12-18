package fact.it.homeservice;


import fact.it.homeservice.dto.HomeRequest;
import fact.it.homeservice.model.Home;
import fact.it.homeservice.repository.HomeRepository;
import fact.it.homeservice.service.HomeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HomeServiceUnitTest {
    @InjectMocks
    private HomeService homeService;

    @Mock
    private HomeRepository homeRepository;


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
}
