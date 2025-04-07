// Function to initialize the payment tab with order details
function initPaymentTab(order) {
    document.getElementById("order-id-display").textContent = order.id;
    document.getElementById("order-id").value = order.id;
    document.getElementById("customer-id").value = order.customer.id;
    document.getElementById("order-cost-display").textContent = order.cost + " Rial"; // Display the order cost

    // Render the reCAPTCHA widget
    grecaptcha.render('recaptcha-container', {
        sitekey: '6LepKQ0rAAAAAOI2bNUALeEgwXLS10xWDUeyDmhm', // Your site key
        callback: function (response) {
            console.log("reCAPTCHA verified:", response); // Debugging
        },
    });
}

// Add event listener to the payment form
document.getElementById("payment-form").addEventListener("submit", function (event) {
    event.preventDefault();

    // Check if the reCAPTCHA is completed
    const recaptchaResponse = grecaptcha.getResponse();
    console.log("reCAPTCHA Response:", recaptchaResponse); // Debugging

    if (!recaptchaResponse) {
        alert("Please complete the reCAPTCHA challenge.");
        return;
    }

    // Validate additional fields
    const cvv2 = document.getElementById("cvv2").value;
    const expirationMonth = document.getElementById("expiration-month").value;
    const expirationYear = document.getElementById("expiration-year").value;
    const pin = document.getElementById("pin").value;

    if (!validateCVV2(cvv2)) {
        alert("Invalid CVV2. It must be 3 digits.");
        return;
    }

    if (!validateExpirationDate(expirationMonth, expirationYear)) {
        alert("Invalid expiration date. Month must be between 01-12 and Year must be 2 digits.");
        return;
    }

    if (!validatePIN(pin)) {
        alert("Invalid PIN. It must be 4 digits.");
        return;
    }

    const paymentRequest = {
        id: document.getElementById("order-id").value,
        customerId: document.getElementById("customer-id").value,
        cardNumber: document.getElementById("card-number").value,
        cvv2: cvv2,
        expirationMonth: expirationMonth,
        expirationYear: expirationYear,
        pin: pin,
        captchaToken: recaptchaResponse, // Use the reCAPTCHA response token
    };

    // Make the payment request
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
            // Optionally, redirect back to the main page or refresh the orders list
            window.location.href = "customerPanel.html"; // Replace with your main page URL
        })
        .catch(error => {
            alert("Error: " + error.message);
        });
});

// Validation functions
function validateCVV2(cvv2) {
    return /^\d{3}$/.test(cvv2); // CVV2 must be exactly 3 digits
}

function validateExpirationDate(month, year) {
    // Validate month (01-12) and year (2 digits)
    return /^(0[1-9]|1[0-2])$/.test(month) && /^\d{2}$/.test(year);
}

function validatePIN(pin) {
    return /^\d{4}$/.test(pin); // PIN must be exactly 4 digits
}