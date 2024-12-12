package fact.it.application.services;


import fact.it.application.Dto.HomeResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HomeService {
    private static final String BASE_URL = "http://localhost:8081/api/home";

    public HomeResponse[] getHomes() {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = BASE_URL + "/user/" + 1;
        return restTemplate.getForObject(apiUrl, HomeResponse[].class);
    }

    public HomeResponse getHomeById(String id) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = BASE_URL + "/" + id;
        return restTemplate.getForObject(apiUrl, HomeResponse.class);
    }
}
