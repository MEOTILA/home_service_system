let paymentTimeout;

// Function to handle payment timeout
function handlePaymentTimeout() {
    alert("Your payment session has expired (10 minutes limit). You will be redirected to the main page.");
    window.location.href = "customerPanel.html";
}

function initPaymentTab(order) {
    // Set up the 10-minute timeout
    paymentTimeout = setTimeout(handlePaymentTimeout, 600000); // 600,000ms = 10 minutes

    // Display order details
    document.getElementById("order-id-display").textContent = order.id;
    document.getElementById("order-id").value = order.id;
    document.getElementById("customer-id").value = order.customer.id;
    document.getElementById("order-cost-display").textContent = order.customerOfferedCost + " Rial";

    // Store the payment start time in localStorage
    const currentTime = new Date().getTime();
    localStorage.setItem('paymentStartTime', currentTime);
    console.log("Payment start time set:", currentTime);

    // Render the reCAPTCHA widget
    grecaptcha.render('recaptcha-container', {
        sitekey: '6LepKQ0rAAAAAOI2bNUALeEgwXLS10xWDUeyDmhm',
        callback: function(response) {
            console.log("reCAPTCHA verified:", response);
        },
    });
}

function checkPaymentTimeout() {
    const startTime = localStorage.getItem('paymentStartTime');
    console.log("Payment start time from localStorage:", startTime);
    if (startTime) {
        const elapsed = new Date().getTime() - parseInt(startTime);
        console.log("Elapsed time:", elapsed);
        if (elapsed > 600000) { // 10 minutes in ms
            handlePaymentTimeout();
            return false;
        }
    }
    return true;
}

// Add event listener to the payment form
document.getElementById("payment-form").addEventListener("submit", function(event) {
    event.preventDefault();

    // Clear the timeout when submitting
    if (paymentTimeout) {
        clearTimeout(paymentTimeout);
    }

    // Check if session expired
    if (!checkPaymentTimeout()) {
        return;
    }

    // Check if the reCAPTCHA is completed
    const recaptchaResponse = grecaptcha.getResponse();
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
        captchaToken: recaptchaResponse
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
            // Clear the payment start time on success
            localStorage.removeItem('paymentStartTime');
            alert(`Payment successful! Order ID: ${data.id}, Status: ${data.status}`);
            window.location.href = "customerPanel.html";
        })
        .catch(error => {
            alert("Error: " + error.message);
        });
});

// Validation functions
function validateCVV2(cvv2) {
    return /^\d{3}$/.test(cvv2);
}

function validateExpirationDate(month, year) {
    return /^(0[1-9]|1[0-2])$/.test(month) && /^\d{2}$/.test(year);
}

function validatePIN(pin) {
    return /^\d{4}$/.test(pin);
}

// Initialize on page load
document.addEventListener('DOMContentLoaded', function() {
    console.log("Order details from localStorage:", localStorage.getItem("orderDetails"));
    if (checkPaymentTimeout()) {
        const orderDetails = localStorage.getItem("orderDetails");
        if (orderDetails) {
            const order = JSON.parse(orderDetails);
            initPaymentTab(order);
            localStorage.removeItem("orderDetails");
        }
    }
});

/*
let paymentTimeout;

// Function to handle payment timeout
function handlePaymentTimeout() {
    alert("Your payment session has expired (10 minutes limit). You will be redirected to the main page.");
    window.location.href = "customerPanel.html";
}

function initPaymentTab(order) {
    // Set up the 10-minute timeout
    paymentTimeout = setTimeout(handlePaymentTimeout, 600000); // 600,000ms = 10 minutes

    // Display order details
    document.getElementById("order-id-display").textContent = order.id;
    document.getElementById("order-id").value = order.id;
    document.getElementById("customer-id").value = order.customer.id;
    document.getElementById("order-cost-display").textContent = order.customerOfferedCost + " Rial";

    // Store the payment start time in localStorage
    const currentTime = new Date().getTime();
    localStorage.setItem('paymentStartTime', currentTime);
    console.log("Payment start time set:", currentTime);

    // Render the reCAPTCHA widget
    grecaptcha.render('recaptcha-container', {
        sitekey: '6LepKQ0rAAAAAOI2bNUALeEgwXLS10xWDUeyDmhm',
        callback: function(response) {
            console.log("reCAPTCHA verified:", response);
        },
    });
}

function checkPaymentTimeout() {
    const startTime = localStorage.getItem('paymentStartTime');
    console.log("Payment start time from localStorage:", startTime);
    if (startTime) {
        const elapsed = new Date().getTime() - parseInt(startTime);
        console.log("Elapsed time:", elapsed);
        if (elapsed > 600000) { // 10 minutes in ms
            handlePaymentTimeout();
            return false;
        }
    }
    return true;
}

// Add event listener to the payment form
document.getElementById("payment-form").addEventListener("submit", function(event) {
    event.preventDefault();

    // Clear the timeout when submitting
    if (paymentTimeout) {
        clearTimeout(paymentTimeout);
    }

    // Check if session expired
    if (!checkPaymentTimeout()) {
        return;
    }

    // Check if the reCAPTCHA is completed
    const recaptchaResponse = grecaptcha.getResponse();
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
        captchaToken: recaptchaResponse
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
            // Clear the payment start time on success
            localStorage.removeItem('paymentStartTime');
            alert(`Payment successful! Order ID: ${data.id}, Status: ${data.status}`);
            window.location.href = "customerPanel.html";
        })
        .catch(error => {
            alert("Error: " + error.message);
        });
});

// Validation functions
function validateCVV2(cvv2) {
    return /^\d{3}$/.test(cvv2);
}

function validateExpirationDate(month, year) {
    return /^(0[1-9]|1[0-2])$/.test(month) && /^\d{2}$/.test(year);
}

function validatePIN(pin) {
    return /^\d{4}$/.test(pin);
}

// Initialize on page load
document.addEventListener('DOMContentLoaded', function() {
    console.log("Order details from localStorage:", localStorage.getItem("orderDetails"));
    if (checkPaymentTimeout()) {
        const orderDetails = localStorage.getItem("orderDetails");
        if (orderDetails) {
            const order = JSON.parse(orderDetails);
            initPaymentTab(order);
            localStorage.removeItem("orderDetails");
        }
    }
});*/
