document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("balance").addEventListener("click", loadBalance);
    document.getElementById("changePassword").addEventListener("click", loadChangePasswordForm);
    document.getElementById("placeAnOrder").addEventListener("click", loadPlaceOrderForm);
    document.getElementById("viewSuggestions").addEventListener("click", loadViewSuggestionsForm);
    document.getElementById("viewSortedSuggestions").addEventListener("click", loadSortedSuggestionsForm);
    document.getElementById("chooseAnExpert").addEventListener("click", loadChooseExpertForm);
    document.getElementById("addComment").addEventListener("click", loadAddCommentForm);
    document.getElementById("orderHistory").addEventListener("click", loadOrderHistory);

});

function loadBalance() {
    const customerId = prompt("Enter your Customer ID:");
    if (!customerId) return;

    // Fetch customer data by ID to get the balance
    fetch(`http://localhost:8081/v1/customers/${customerId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Customer not found or unable to retrieve data.");
            }
            return response.json(); // Parse the response body to get customer data
        })
        .then(data => {
            // Display the balance
            document.getElementById("form-container").innerHTML = `
                <h3>Customer Balance</h3>
                <p>Customer ID: ${data.id}</p>
                <p>Name: ${data.firstName} ${data.lastName}</p>
                <p>Balance: ${data.balance} Rial</p>
            `;
        })
        .catch(error => {
            alert("Error: " + error.message); // Handle any errors
        });
}
// Function to load change password form
function loadChangePasswordForm() {
    document.getElementById("form-container").innerHTML = `
        <h3>Change Password</h3>
        <form id="change-password-form">
            <input type="number" id="customer-id" placeholder="Customer ID" required><br>
            <input type="password" id="current-password" placeholder="Current Password" required><br>
            <input type="password" id="new-password" placeholder="New Password" required><br>
            <button type="submit">Change Password</button>
        </form>
    `;

    document.getElementById("change-password-form").addEventListener("submit", function (event) {
        event.preventDefault();

        const request = {
            id: document.getElementById("customer-id").value,
            currentPassword: document.getElementById("current-password").value,
            newPassword: document.getElementById("new-password").value,
        };

        if (!validatePassword(request.newPassword)) {
            alert("New password does not meet security requirements.");
            return;
        }

        fetch("http://localhost:8081/v1/customers/change-password", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(request),
        })
            .then(response => {
                if (!response.ok) throw new Error("Failed to change password.");
                alert("Password changed successfully!");
            })
            .catch(error => alert(error.message));
    });
}

// Password validation function (same rules as backend)
function validatePassword(password) {
    const passwordRegex = /^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$/;
    return passwordRegex.test(password);
}

function loadPlaceOrderForm() {
    // Fetch main services
    fetch("http://localhost:8081/v1/main-services/all")  // Endpoint to get main services
        .then(response => response.json())
        .then(mainServices => {
            // Display main services
            let mainServicesHTML = "<h3>Select a Main Service</h3><ul>";
            mainServices.forEach(service => {
                mainServicesHTML += `
                    <li>
                        <button class="main-service-button" data-id="${service.id}">${service.name}</button>
                    </li>
                `;
            });
            mainServicesHTML += "</ul>";

            document.getElementById("form-container").innerHTML = mainServicesHTML;

            // Add event listeners to main service buttons
            document.querySelectorAll(".main-service-button").forEach(button => {
                button.addEventListener("click", function() {
                    const mainServiceId = this.getAttribute("data-id");
                    loadSubServices(mainServiceId);
                });
            });
        })
        .catch(error => alert("Error: " + error.message));
}

// Fetch and display sub-services based on selected main service
function loadSubServices(mainServiceId) {
    fetch(`http://localhost:8081/v1/sub-services/by-main-service/${mainServiceId}`)  // Endpoint to get sub services by main service ID
        .then(response => response.json())
        .then(subServices => {
            // Display sub services
            let subServicesHTML = "<h3>Select a Sub Service</h3><ul>";
            subServices.forEach(service => {
                subServicesHTML += `
                    <li>
                        <button class="sub-service-button" data-id="${service.id}">${service.name}</button>
                    </li>
                `;
            });
            subServicesHTML += "</ul>";

            document.getElementById("form-container").innerHTML = subServicesHTML;

            // Add event listeners to sub service buttons
            document.querySelectorAll(".sub-service-button").forEach(button => {
                button.addEventListener("click", function() {
                    const subServiceId = this.getAttribute("data-id");
                    loadOrderForm(subServiceId);
                });
            });
        })
        .catch(error => alert("Error: " + error.message));
}

// Load the order form after selecting sub service
function loadOrderForm(subServiceId) {
    document.getElementById("form-container").innerHTML = `
        <h3>Place an Order</h3>
        <form id="place-order-form">
<!--            <input type="number" id="sub-service-id" value="${subServiceId}" readonly><br>-->
            <input type="number" id="customer-id" placeholder="Customer ID" required><br>
            <input type="number" id="customer-offered-cost" placeholder="Offered Cost" required><br>
            <textarea id="customer-description" placeholder="Order Description" required></textarea><br>
            <input type="datetime-local" id="service-date" required><br>
            <input type="text" id="address" placeholder="Address" required><br>
            <button type="submit">Place Order</button>
        </form>
    `;

    document.getElementById("place-order-form").addEventListener("submit", function (event) {
        event.preventDefault();

        const request = {
            subServiceId: subServiceId,
            // subServiceId: document.getElementById("sub-service-id").value,
            customerId: document.getElementById("customer-id").value,
            customerOfferedCost: document.getElementById("customer-offered-cost").value,
            customerDescription: document.getElementById("customer-description").value,
            serviceDate: document.getElementById("service-date").value,
            address: document.getElementById("address").value,
        };

        // Validate the order data before submitting
        if (!validateOrderData(request)) {
            alert("Please ensure all fields are filled correctly.");
            return;
        }

        // Make API call to place the order
        fetch("http://localhost:8081/v1/orders", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(request),
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(message => {
                        throw new Error(message);
                    });
                }
                return response.json();
            })
            .then(data => {
                alert("Order placed successfully! Order ID: " + data.id);
            })
            .catch(error => {
                alert("Error: " + error.message);
            });
    });
}


// Function to validate the order data
function validateOrderData(request) {
    const currentDate = new Date();
    const serviceDate = new Date(request.serviceDate);

    // Check if all fields are filled correctly
    if (!request.subServiceId || !request.customerId || !request.customerOfferedCost ||
        !request.customerDescription || !request.serviceDate || !request.address) {
        return false;
    }

    // Validate description length (10-500 characters)
    if (request.customerDescription.length < 10 || request.customerDescription.length > 500) {
        return false;
    }

    // Validate address length (5-250 characters)
    if (request.address.length < 5 || request.address.length > 250) {
        return false;
    }

    // Validate that the service date is a future date
    if (serviceDate <= currentDate) {
        alert("Service date must be a future date.");
        return false;
    }

    // Validate offered cost is not negative
    if (request.customerOfferedCost < 0) {
        alert("Offered cost cannot be negative.");
        return false;
    }

    return true;
}

// Function to load the "View Suggestions for an Order" form
function loadViewSuggestionsForm() {
    document.getElementById("form-container").innerHTML = `
        <h3>View Suggestions for an Order</h3>
        <form id="view-suggestions-form">
            <input type="number" id="order-id" placeholder="Order ID" required><br>
            <button type="submit">View Suggestions</button>
        </form>
    `;

    document.getElementById("view-suggestions-form").addEventListener("submit", function (event) {
        event.preventDefault();

        const orderId = document.getElementById("order-id").value;

        // Validate the order ID
        if (!orderId) {
            alert("Please provide a valid Order ID.");
            return;
        }

        // Send request to fetch suggestions
        fetch(`http://localhost:8081/v1/expert-suggestions/order/${orderId}`)
            .then(response => {
                if (!response.ok) {
                    // If the response is not OK, throw an error
                    return response.text().then(message => {
                        throw new Error(message || "Failed to load suggestions.");
                    });
                }
                return response.json(); // If response is OK, parse it as JSON
            })
            .then(suggestions => {
                // Display the suggestions
                let resultHTML = "<h3>Expert Suggestions</h3><ul>";
                if (suggestions.length > 0) {
                    suggestions.forEach(suggestion => {
                        resultHTML += `
                            <li>
                                <strong>Expert ID:</strong> ${suggestion.expertId}<br>
                                <strong>Suggestion ID:</strong> ${suggestion.id}<br>
                                <strong>Suggestion:</strong> ${suggestion.expertSuggestion}<br>
                                <strong>Offered Cost:</strong> $${suggestion.expertOfferedCost}<br>
                                <strong>Service Duration:</strong> ${formatDuration(suggestion.serviceTimeDuration)}<br>
                                <strong>Suggested Start Time:</strong> ${formatDateTime(suggestion.expertServiceStartDateTime)}<br>
                                <strong>Created At:</strong> ${formatDateTime(suggestion.createdAt)}<br>
                                <strong>Updated At:</strong> ${formatDateTime(suggestion.updatedAt)}<br>
                            </li>
                            <br>
                        `;
                    });
                } else {
                    resultHTML += "<li>No suggestions available for this order.</li>";
                }
                resultHTML += "</ul>";
                document.getElementById("form-container").innerHTML = resultHTML;
            })
            .catch(error => {
                // Handle the error
                alert("Error: " + error.message); // Display the error message
            });
    });
}

// Function to format duration (e.g., "PT2H30M" to "2 hours 30 minutes")
function formatDuration(duration) {
    const hours = duration.hours;
    const minutes = duration.minutes;
    let formattedDuration = "";
    if (hours > 0) formattedDuration += `${hours} hour${hours > 1 ? 's' : ''}`;
    if (minutes > 0) formattedDuration += ` ${minutes} minute${minutes > 1 ? 's' : ''}`;
    return formattedDuration.trim();
}

// Function to format LocalDateTime to a more readable format
function formatDateTime(dateTime) {
    const date = new Date(dateTime);
    return `${date.toLocaleDateString()} ${date.toLocaleTimeString()}`;
}


function loadSortedSuggestionsForm() {
    document.getElementById("form-container").innerHTML = `
        <h3>View Sorted Suggestions</h3>
        <form id="sorted-suggestions-form">
            <input type="number" id="order-id" placeholder="Order ID" required><br>
            <select id="sort-by">
                <option value="price">Sort by Price</option>
                <option value="rating">Sort by Rating</option>
            </select><br>
            <button type="submit">View Suggestions</button>
        </form>
    `;

    document.getElementById("sorted-suggestions-form").addEventListener("submit", function (event) {
        event.preventDefault();

        const orderId = document.getElementById("order-id").value;
        const sortBy = document.getElementById("sort-by").value;

        // Log the values being sent
        console.log("Order ID:", orderId, "Sort By:", sortBy);

        // Validate the inputs
        if (!orderId) {
            alert("Please provide a valid Order ID.");
            return;
        }

        // Send request to the backend to fetch sorted suggestions
        fetch(`http://localhost:8081/v1/expert-suggestions/sortedOrders?orderId=${orderId}&sortBy=${sortBy}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(message => {
                        throw new Error(message || "Failed to retrieve sorted suggestions.");
                    });
                }
                return response.json(); // Get the sorted data from backend
            })
            .then(data => {
                console.log("Sorted suggestions received:", data); // Check if the data is sorted correctly
                displaySortedSuggestions(data); // Display the sorted suggestions
            })
            .catch(error => {
                alert("Error: " + error.message); // Show error if there is an issue
            });
    });
}

// Function to display sorted suggestions
function displaySortedSuggestions(suggestions) {
    let resultHTML = "<h3>Sorted Suggestions</h3><ul>";

    suggestions.forEach(suggestion => {
        resultHTML += `
            <li>
                <strong>Expert ID:</strong> ${suggestion.expertId}<br>
                <strong>Expert Suggestion ID:</strong> ${suggestion.id}<br>
                <strong>Suggestion:</strong> ${suggestion.expertSuggestion}<br>
                <strong>Offered Cost:</strong> ${suggestion.expertOfferedCost} Rial<br>
                <strong>Service Duration:</strong> ${formatDuration(suggestion.serviceTimeDuration)}<br>
                <strong>Suggested Start Time:</strong> ${formatDateTime(suggestion.expertServiceStartDateTime)}<br>
                <strong>Created At:</strong> ${formatDateTime(suggestion.createdAt)}<br>
                <strong>Updated At:</strong> ${formatDateTime(suggestion.updatedAt)}<br>
                <hr>
            </li>
        `;
    });

    resultHTML += "</ul>";
    document.getElementById("form-container").innerHTML = resultHTML; // Update the UI with the sorted suggestions
}





function loadChooseExpertForm() {
    document.getElementById("form-container").innerHTML = `
        <h3>Choose an Expert</h3>
        <form id="choose-expert-form">
            <input type="number" id="order-id" placeholder="Order ID" required><br>
            <input type="number" id="expert-id" placeholder="Expert ID" required><br>
            <button type="submit">Choose Expert</button>
        </form>
    `;

    document.getElementById("choose-expert-form").addEventListener("submit", function (event) {
        event.preventDefault();

        const orderId = document.getElementById("order-id").value;
        const expertId = document.getElementById("expert-id").value;

        // Validate the inputs
        if (!orderId || !expertId) {
            alert("Please provide valid Order ID and Expert ID.");
            return;
        }

        // Send request to choose the expert for the order
        fetch(`http://localhost:8081/v1/orders/acceptingOrder?orderId=${orderId}&expertId=${expertId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then(response => {
                if (!response.ok) {
                    // If the response is not OK, throw an error
                    return response.text().then(message => {
                        throw new Error(message || "Failed to choose an expert.");
                    });
                }
                return response.json(); // If response is OK, parse it as JSON
            })
            .then(data => {
                // Show the updated order response
                alert(`Expert with ID ${expertId} has been chosen for Order ID ${orderId}.`);
            })
            .catch(error => {
                // Handle errors (e.g., invalid orderId, expertId, or any server-side errors)
                alert("Error: " + error.message);
            });
    });
}

// Function to load add a comment form
function loadAddCommentForm() {
    document.getElementById("form-container").innerHTML = `
        <h3>Add a Comment</h3>
        <form id="add-comment-form">
            <input type="text" id="order-id" placeholder="Order ID" required><br>
            <textarea id="comment-text" placeholder="Comment"></textarea><br>
            <input type="number" id="rating" min="1" max="100" placeholder="Rating (1-100)" required><br>
            <button type="submit">Submit Comment</button>
        </form>
    `;

    document.getElementById("add-comment-form").addEventListener("submit", function (event) {
        event.preventDefault();
        const request = {
            orderId: document.getElementById("order-id").value,
            comment: document.getElementById("comment-text").value,
            rating: document.getElementById("rating").value,
        };

        fetch("http://localhost:8081/v1/customer-comments", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(request),
        })
            .then(response => {
                if (!response.ok) {
                    // If the response is not OK, check the response status
                    return response.text().then(message => {
                        // If it's a specific error, show it
                        throw new Error(message || "An error occurred while adding the comment.");
                    });
                }
                return response.json(); // Parse the response if successful
            })
            .then(data => {
                alert("Comment added successfully!");
            })
            .catch(error => {
                // Handle any errors from the API request
                if (error.message.includes("Order ID")) {
                    alert("Error: Invalid Order ID or Order not found.");
                } else if (error.message.includes("Rating")) {
                    alert("Error: Rating must be between 1 and 100.");
                } else {
                    alert("Error: " + error.message); // General error message
                }
            });
    });
}

// Function to load order history
function loadOrderHistory() {
    const customerId = prompt("Enter your Customer ID:");
    if (!customerId) return;

    fetch(`http://localhost:8081/v1/orders/customer/${customerId}`)
        .then(response => response.json())
        .then(data => {
            let resultHTML = "<h3>Order History</h3><ul>";
            data.forEach(order => {
                resultHTML += `<li>Order ID: ${order.id}, Status: ${order.status}, Details: ${order.details}</li>`;
            });
            resultHTML += "</ul>";
            document.getElementById("form-container").innerHTML = resultHTML;
        })
        .catch(error => alert("Error: " + error.message));
}

// Function to load all customer orders
function loadViewAllCustomerOrders() {
    const customerId = prompt("Enter your Customer ID:");
    if (!customerId) return;

    // Fetch all orders for the customer
    fetch(`http://localhost:8081/v1/orders/customer/${customerId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch orders.");
            }
            return response.json();
        })
        .then(orders => {
            displayOrders(orders);
        })
        .catch(error => {
            alert("Error: " + error.message);
        });
}

// Function to display all orders
function displayOrders(orders) {
    let ordersHTML = "<h3>Your Orders</h3><ul>";

    orders.forEach(order => {
        ordersHTML += `
            <li>
                <strong>Order ID:</strong> ${order.id}<br>
                <strong>Status:</strong> ${order.status}<br>
                <strong>Offered Cost:</strong> ${order.customerOfferedCost} Rial<br>
                <strong>Description:</strong> ${order.customerDescription}<br>
                <strong>Service Date:</strong> ${formatDateTime(order.serviceDate)}<br>
                ${order.status === "SERVICE_IS_DONE" ? `<button class="pay-button" data-order-id="${order.id}">Pay</button>` : ""}
                <hr>
            </li>
        `;
    });

    ordersHTML += "</ul>";
    document.getElementById("form-container").innerHTML = ordersHTML;

    // Add event listeners to the pay buttons
    document.querySelectorAll(".pay-button").forEach(button => {
        button.addEventListener("click", handlePayment);
    });
}


/*let recaptchaLoaded = false; // Global flag to track if reCAPTCHA is loaded

// Function to load the reCAPTCHA script
function loadRecaptchaScript(callback) {
    if (recaptchaLoaded) {
        callback(); // If already loaded, call the callback immediately
        return;
    }

    const script = document.createElement("script");
    script.src = "https://www.google.com/recaptcha/api.js?render=explicit";
    script.async = true;
    script.defer = true;
    script.onload = () => {
        recaptchaLoaded = true;
        callback(); // Call the callback once the script is loaded
    };
    document.head.appendChild(script);
}*/

// Function to handle payment for an order
function handlePayment(event) {
    const orderId = event.target.getAttribute("data-order-id");
    if (!orderId) return;

    // Fetch the order details
    fetch(`http://localhost:8081/v1/orders/${orderId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch order details.");
            }
            return response.json();
        })
        .then(order => {
            // Check if the order object is defined
            if (!order) {
                throw new Error("Order details are missing.");
            }

            // Fetch the customer details
            return fetch(`http://localhost:8081/v1/customers/${order.customerId}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Failed to fetch customer details.");
                    }
                    return response.json();
                })
                .then(customer => {
                    // Merge customer details with the order
                    order.customer = customer;
                    return order;
                });
        })
        .then(order => {
            // Check if the customer has enough balance
            if (order.customer.balance >= order.customerOfferedCost) {
                // Automatically pay the order using the customer's balance
                payByBalance(order);
            } else {
                // Redirect to the payment tab for credit card payment
                alert("You do not have enough balance. Redirecting to payment tab...");
                loadPaymentTab(order);
            }
        })
        .catch(error => {
            alert("Error: " + error.message);
        });
}

// Function to automatically pay the order using the customer's balance
function payByBalance(order) {
    const paymentRequest = {
        id: order.id,
        customerId: order.customer.id,
        captchaToken: null, // Not required for balance payment
        cardNumber: null, // Not required for balance payment
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
            // Refresh the orders list
            loadViewAllCustomerOrders();
        })
        .catch(error => {
            alert("Error: " + error.message);
        });
}

function loadPaymentTab(order) {
    // Open the static payment tab HTML file
    window.open("payment-tab.html", "_blank");

    // Pass the order details to the static HTML file
    const orderDetails = JSON.stringify(order);
    localStorage.setItem("orderDetails", orderDetails);
}

// When the static HTML file loads, retrieve the order details
window.addEventListener("DOMContentLoaded", function () {
    const orderDetails = localStorage.getItem("orderDetails");
    if (orderDetails) {
        const order = JSON.parse(orderDetails);
        initPaymentTab(order); // Call the function to initialize the payment tab
        localStorage.removeItem("orderDetails"); // Clear the stored data
    }
});

// Add event listener for the "View My Orders" link
document.getElementById("viewAllCustomerOrders").addEventListener("click", loadViewAllCustomerOrders);