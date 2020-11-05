package com.sakb.spl.ui.hyperpay.demo.task;

import android.os.AsyncTask;

/**
 * Represents an async task to request a checkout id from the server.
 */
public class CheckoutIdRequestAsyncTask extends AsyncTask<String, Void, String> {

    private CheckoutIdRequestListener listener;

    public CheckoutIdRequestAsyncTask(CheckoutIdRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        return requestCheckoutId("", "");
    }

    @Override
    protected void onPostExecute(String checkoutId) {
        if (listener != null) {
            listener.onCheckoutIdReceived(checkoutId);
        }
    }

    private String requestCheckoutId(String amount,
                                     String currency) {
        return "CC68D45620120DE88CD873BFE0C67718.uat01-vm-tx03";
    }
}