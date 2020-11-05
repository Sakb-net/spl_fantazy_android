package com.sakb.spl.ui.hyperpay.demo.task;


public interface PaymentStatusRequestListener {
    void onErrorOccurred();

    void onPaymentStatusReceived(String paymentStatus);
}
