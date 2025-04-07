document.getElementById("payment-form").addEventListener("submit", function (event) {
    event.preventDefault();

    // Check if the reCAPTCHA is completed
    const recaptchaResponse = grecaptcha.getResponse();
    if (!recaptchaResponse) {
        alert("Please complete the reCAPTCHA challenge.");
        return;
    }

    const paymentRequest = {
        id: document.getElementById("order-id").value,
        customerId: document.getElementById("customer-id").value,
        cardNumber: document.getElementById("card-number").value,
        captchaToken: recaptchaResponse, // Use the reCAPTCHA response token
    };

    // Send the payment request
    fetch("http://localhost:8081/v1/orders/payment", {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(paymentRequest),
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(message => {
                    throw new Error(message || "Payment failed.");
                });
            }
            return response.json();
        })
        .then(data => {
            alert(`Payment successful! Order ID: ${data.id}, Status: ${data.status}`);
            // Refresh the orders list
            loadViewAllCustomerOrders();
        })
        .catch(error => {
            alert("Error: " + error.message);
        });
});