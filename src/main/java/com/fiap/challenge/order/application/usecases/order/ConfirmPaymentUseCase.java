package com.fiap.challenge.order.application.usecases.order;

import java.util.UUID;

public interface ConfirmPaymentUseCase {

	void confirmPayment(UUID orderId, String paymentId, Boolean isPaid, Long orderNumber);
	
}
