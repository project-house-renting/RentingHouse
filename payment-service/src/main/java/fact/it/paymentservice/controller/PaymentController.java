package fact.it.paymentservice.controller;

import fact.it.paymentservice.dto.PaymentResponse;
import fact.it.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// docker run --name mysql-payment -p 3335:3306 -e MYSQL_ROOT_PASSWORD=abc123 -d mysql

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/tenant/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentResponse> getAllPaymentsFromTenant(@PathVariable Long id) {
        return paymentService.getAllPaymentsFromTenant(id);
    }
}
