package utcn.ordermanagement.models;

import utcn.ordermanagement.data_access.annotations.Id;
import utcn.ordermanagement.data_access.annotations.Table;

import java.time.LocalDateTime;

@Table(name = "log")
public record Bill(
        @Id
        Long id,
        double totalPrice,
        String clientName,
        String productName,
        LocalDateTime placedAt
) {
}
