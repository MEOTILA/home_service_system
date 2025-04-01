document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("balance").addEventListener("click", loadBalance);
    document.getElementById("rating").addEventListener("click", loadRating);

    document.getElementById("changePassword").addEventListener("click", loadChangePasswordForm);
    document.getElementById("viewOrders").addEventListener("click", loadExpertOrders);
    document.getElementById("suggestForOrder").addEventListener("click", loadSuggestForOrderForm);
    document.getElementById("viewSuggestions").addEventListener("click", loadExpertSuggestions);
    document.getElementById('addExpertToSubService').addEventListener('click', loadAddExpertToSubServiceForm);
    document.getElementById("orderHistory").addEventListener("click", loadOrderHistory);

});
function loadBalance() {
    const expertId = prompt("Enter your Expert ID:");
    if (!expertId) return;

    // Fetch customer data by ID to get the balance
    fetch(`http://localhost:8081/v1/experts/${expertId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Expert not found or unable to retrieve data.");
            }
            return response.json(); // Parse the response body to get customer data
        })
        .then(data => {
            // Display the balance
            document.getElementById("form-container").innerHTML = `
                <h3>Expert Balance</h3>
                <p>Expert ID: ${data.id}</p>
                <p>Name: ${data.firstName} ${data.lastName}</p>
                <p>Balance: ${data.balance} Rial</p>
            `;
        })
        .catch(error => {
            alert("Error: " + error.message); // Handle any errors
        });
}

function loadRating() {
    const expertId = prompt("Enter your Expert ID:");
    if (!expertId) return;

    // Fetch customer data by ID to get the balance
    fetch(`http://localhost:8081/v1/experts/${expertId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Expert not found or unable to retrieve data.");
            }
            return response.json(); // Parse the response body to get customer data
        })
        .then(data => {
            // Display the balance
            document.getElementById("form-container").innerHTML = `
                <h3>Expert Balance</h3>
                <p>Expert ID: ${data.id}</p>
                <p>Name: ${data.firstName} ${data.lastName}</p>
                <p>Rating: ${data.rating}</p>
            `;
        })
        .catch(error => {
            alert("Error: " + error.message); // Handle any errors
        });
}

function loadChangePasswordForm() {
    document.getElementById("form-container").innerHTML = `
        <h3>Change Password</h3>
        <form id="change-password-form">
            <input type="number" id="expert-id" placeholder="Expert ID" required><br>
            <input type="password" id="current-password" placeholder="Current Password" required><br>
            <input type="password" id="new-password" placeholder="New Password" required><br>
            <button type="submit">Change Password</button>
        </form>
    `;

    document.getElementById("change-password-form")
        .addEventListener("submit", function (event) {
        event.preventDefault();

        const request = {
            id: document.getElementById("expert-id").value,
            currentPassword: document.getElementById("current-password").value,
            newPassword: document.getElementById("new-password").value,
        };

        if (!validatePassword(request.newPassword)) {
            alert("New password does not meet security requirements.");
            return;
        }

        fetch("http://localhost:8081/v1/experts/change-password", {
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

function validatePassword(password) {
    const passwordRegex = /^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$/;
    return passwordRegex.test(password);
}




function loadExpertOrders() {
    const expertId = prompt("Enter your Expert ID:");
    if (!expertId) return;

    fetch(`http://localhost:8081/v1/orders/expert/exFields/${expertId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch orders related to expert fields.");
            }
            return response.json();
        })
        .then(data => {
            let resultHTML = "<h3>Orders Related to Your Expertise</h3><ul>";
            data.forEach(order => {
                resultHTML += `
                    <li>
                        <strong>Order ID:</strong> ${order.id} <br>
                        <strong>Sub-Service ID:</strong> ${order.subServiceId} <br>
                        <strong>Customer ID:</strong> ${order.customerId} <br>
                        <strong>Customer Offered Cost:</strong> ${order.customerOfferedCost} Rial <br>
                        <strong>Description:</strong> ${order.customerDescription} <br>
                        <strong>Service Date:</strong> ${formatDateTime(order.serviceDate)} <br>
                        <strong>Address:</strong> ${order.address} <br>
                        <strong>Status:</strong> ${order.status} <br>
                        <strong>Expert Suggestions:</strong> ${order.expertSuggestionListIds.length} suggestions <br>
                        <strong>Created At:</strong> ${formatDateTime(order.createdAt)} <br>
                        <strong>Updated At:</strong> ${formatDateTime(order.updatedAt)} <br>
                        <hr>
                    </li>
                `;
            });
            resultHTML += "</ul>";
            document.getElementById("form-container").innerHTML = resultHTML;
        })
        .catch(error => alert("Error: " + error.message));
}





function loadSuggestForOrderForm() {
    document.getElementById("form-container").innerHTML = `
        <h3>Suggest for an Order</h3>
        <form id="suggestion-form">
            <input type="number" id="order-id" placeholder="Order ID" required><br>
            <input type="number" id="expert-id" placeholder="Your Expert ID" required><br>
            <textarea id="expert-suggestion" placeholder="Your Suggestion (max 500 chars)" maxlength="500" required></textarea><br>
            <input type="number" id="offered-cost" placeholder="Offered Cost" required><br>
            <input type="number" id="service-duration" placeholder="Service Duration (in minutes)" required><br>
            <input type="datetime-local" id="service-start-time" required><br>
            <button type="submit">Submit Suggestion</button>
        </form>
    `;

    document.getElementById("suggestion-form").addEventListener("submit", function (event) {
        event.preventDefault();

        const orderId = document.getElementById("order-id").value;
        const expertId = document.getElementById("expert-id").value;
        const expertSuggestion = document.getElementById("expert-suggestion").value;
        const expertOfferedCost = document.getElementById("offered-cost").value;
        const serviceTimeDuration = document.getElementById("service-duration").value;
        const expertServiceStartDateTime = document.getElementById("service-start-time").value;

        if (!orderId || !expertId || !expertSuggestion || !expertOfferedCost || !serviceTimeDuration || !expertServiceStartDateTime) {
            alert("All fields are required.");
            return;
        }

        const requestBody = {
            orderId: parseInt(orderId),
            expertId: parseInt(expertId),
            expertSuggestion: expertSuggestion,
            expertOfferedCost: parseInt(expertOfferedCost),
            serviceTimeDuration: `PT${serviceTimeDuration}M`, // Convert minutes to ISO-8601 duration format
            expertServiceStartDateTime: new Date(expertServiceStartDateTime).toISOString()
        };

        fetch("http://localhost:8081/v1/expert-suggestions", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(requestBody)
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(message => {
                        throw new Error(message || "Failed to submit suggestion.");
                    });
                }
                return response.json();
            })
            .then(data => {
                alert("Suggestion submitted successfully!");
                console.log("Suggestion Response:", data);
                document.getElementById("form-container").innerHTML = "<h3>Suggestion Submitted Successfully!</h3>";
            })
            .catch(error => alert("Error: " + error.message));
    });
}


function loadExpertSuggestions() {
    const expertId = prompt("Enter your Expert ID:");
    if (!expertId) return;

    fetch(`http://localhost:8081/v1/expert-suggestions/expert/${expertId}`)
        .then(response => {
            if (!response.ok) {
                return response.text().then(message => {
                    throw new Error(message || "Failed to retrieve suggestions.");
                });
            }
            return response.json();
        })
        .then(data => {
            if (data.length === 0) {
                document.getElementById("form-container").innerHTML = "<h3>No suggestions found.</h3>";
                return;
            }

            let resultHTML = "<h3>Your Submitted Suggestions</h3><ul>";

            data.forEach(suggestion => {
                resultHTML += `
                    <li>
                        <strong>Suggestion ID:</strong> ${suggestion.id}<br>
                        <strong>Order ID:</strong> ${suggestion.orderId}<br>
                        <strong>Expert ID:</strong> ${suggestion.expertId}<br>
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
            document.getElementById("form-container").innerHTML = resultHTML;
        })
        .catch(error => alert("Error: " + error.message));
}

// Function to format DateTime properly
function formatDateTime(dateTime) {
    if (!dateTime) return "N/A";
    const date = new Date(dateTime);
    return date.toLocaleString();
}

// Function to format Duration properly
function formatDuration(duration) {
    if (!duration) return "N/A";
    const match = duration.match(/PT(\d+H)?(\d+M)?(\d+S)?/);
    let formatted = "";
    if (match[1]) formatted += match[1].replace("H", " hours ");
    if (match[2]) formatted += match[2].replace("M", " minutes ");
    if (match[3]) formatted += match[3].replace("S", " seconds");
    return formatted.trim();
}





function loadAddExpertToSubServiceForm() {
    document.getElementById('form-container').innerHTML = `
        <h2>Add Expert to Sub Service</h2>
        <form id="add-expert-to-sub-service-form">
            <input type="number" id="sub-service-id" placeholder="Sub Service ID" required><br>
            <input type="number" id="expert-id" placeholder="Expert ID" required><br>
            <button type="submit">Add Expert</button>
        </form>
    `;

    // Add form submission handler
    const form = document.getElementById('add-expert-to-sub-service-form');
    form.addEventListener('submit', function (event) {
        event.preventDefault(); // Prevent default form submission

        const subServiceId = document.getElementById('sub-service-id').value;
        const expertId = document.getElementById('expert-id').value;

        // Validate inputs
        if (!subServiceId || !expertId) {
            alert('Both Sub Service ID and Expert ID are required!');
            return;
        }

        // Send the request to the backend using fetch
        fetch(`http://localhost:8081/v1/sub-services/${subServiceId}/add-expert/${expertId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(errorData => {
                        const errorMessage = errorData.message || 'An unknown error occurred.';
                        alert(`Error: ${errorMessage}`);
                        console.error('Error details:', errorData); // Log error details for debugging
                        throw new Error(errorMessage); // Stop further execution
                    });
                }
                // Handle empty response (no content returned on success)
                alert('Expert added to sub service successfully!');
                console.log('Expert added successfully');
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Failed to add expert to sub service. Please try again later.');
            });
    });
}





// Function to load order history
function loadOrderHistory() {
    const expertId = prompt("Enter your Expert ID:");
    if (!expertId) return;

    fetch(`http://localhost:8081/v1/orders/expert/${expertId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch order history.");
            }
            return response.json();
        })
        .then(async (data) => {
            let resultHTML = "<h3>Order History</h3><ul>";

            for (const order of data) {
                let ratingText = "No comment";
                if (order.customerCommentAndRateId) {
                    try {
                        const ratingResponse =
                            await fetch(`http://localhost:8081/v1/customer-comments/${order.customerCommentAndRateId}`);
                        if (ratingResponse.ok) {
                            const ratingData = await ratingResponse.json();
                            ratingText = `Rating: ${ratingData.rating}`;
                        }
                    } catch (error) {
                        console.error("Failed to fetch rating:", error);
                    }
                }

                resultHTML += `
                    <li>
                        <strong>Order ID:</strong> ${order.id} <br>
                        <strong>Sub-Service ID:</strong> ${order.subServiceId} <br>
                        <strong>Customer ID:</strong> ${order.customerId} <br>
                        <strong>Expert ID:</strong> ${order.expertId || "Not assigned"} <br>
                        <strong>Customer Offered Cost:</strong> ${order.customerOfferedCost} Rial <br>
                        <strong>Customer Description:</strong> ${order.customerDescription} <br>
                        <strong>Service Date:</strong> ${formatDateTime(order.serviceDate)} <br>
                        <strong>Address:</strong> ${order.address} <br>
                        <strong>Status:</strong> ${order.status} <br>
                        <strong>Expert Suggestions:</strong> ${order.expertSuggestionListIds.length} suggestions <br>
                        <strong>Rate:</strong> ${ratingText} <br>
                        <strong>Created At:</strong> ${formatDateTime(order.createdAt)} <br>
                        <strong>Updated At:</strong> ${formatDateTime(order.updatedAt)} <br>
                        <hr>
                    </li>
                `;
            }

            resultHTML += "</ul>";
            document.getElementById("form-container").innerHTML = resultHTML;
        })
        .catch(error => alert("Error: " + error.message));
}


function formatDateTime(dateTime) {
    return new Date(dateTime).toLocaleString();
}

