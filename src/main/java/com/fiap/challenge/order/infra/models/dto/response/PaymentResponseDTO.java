package com.fiap.challenge.order.infra.models.dto.response;

import java.util.UUID;

public record PaymentResponseDTO(UUID orderId, UUID paymentId, Boolean isPaid, Long orderNumber) {
}
