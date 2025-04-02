document.getElementById('filterUsers').addEventListener('click', function () {
    loadFilterForm();
});

document.getElementById('addMainService').addEventListener('click', function () {
    loadAddMainServiceForm();
});

document.getElementById('addSubService').addEventListener('click', function () {
    loadAddSubServiceForm();
});

document.getElementById('addExpertToSubService').addEventListener('click', function () {
    loadAddExpertToSubServiceForm();
});

document.getElementById('viewAllMainService').addEventListener('click', function () {
    loadAllMainServices();
});

function loadFilterForm() {
    document.getElementById('form-container').innerHTML = `
        <h2>Filter Users</h2>
        <div>
            <h3>Choose a filter:</h3>
            <button id="filterCustomersBtn">Filter Customers</button>
            <button id="filterExpertsBtn">Filter Experts</button>
        </div>
        <div id="filterFormContainer"></div>
    `;

    document.getElementById('filterCustomersBtn').addEventListener('click', loadFilterCustomersForm);
    document.getElementById('filterExpertsBtn').addEventListener('click', loadFilterExpertsForm);
}

function loadFilterCustomersForm() {
    document.getElementById('filterFormContainer').innerHTML = `
        <h3>Filter Customers</h3>
        <form id="filter-customers-form">
            <input type="text" id="customer-username" placeholder="Username"><br>
            <input type="text" id="customer-firstname" placeholder="First Name"><br>
            <input type="text" id="customer-lastname" placeholder="Last Name"><br>
            <input type="text" id="customer-nationalId" placeholder="National ID"><br>
            <input type="text" id="customer-phoneNumber" placeholder="Phone Number"><br>
            <input type="text" id="customer-email" placeholder="Email"><br>
            <select id="customer-userStatus">
                <option value="">Select User Status</option>
                <option value="NEW">New</option>
                <option value="PENDING">Pending</option>
                <option value="ACTIVE">Active</option>
            </select><br>
            <input type="number" id="customer-balance" placeholder="Balance"><br>
            <input type="date" id="customer-createdAt" placeholder="Created At"><br>
            <input type="date" id="customer-birthday" placeholder="Birthday"><br>
            <button type="submit">Filter</button>
        </form>
    `;

    const customerForm = document.getElementById('filter-customers-form');
    customerForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const params = new URLSearchParams({
            username: document.getElementById('customer-username').value,
            firstName: document.getElementById('customer-firstname').value,
            lastName: document.getElementById('customer-lastname').value,
            nationalId: document.getElementById('customer-nationalId').value,
            phoneNumber: document.getElementById('customer-phoneNumber').value,
            email: document.getElementById('customer-email').value,
            userStatus: document.getElementById('customer-userStatus').value,
            balance: document.getElementById('customer-balance').value,
            createdAt: document.getElementById('customer-createdAt').value,
            birthday: document.getElementById('customer-birthday').value
        });

        fetch(`http://localhost:8081/v1/customers/filter?${params.toString()}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                console.log('Filtered Customers:', data);
                // Make sure you're using the "content" field
                if (data && data.content && data.content.length > 0) {
                    // Call the function to display the filtered customers
                    displayFilteredCustomers(data.content);
                } else {
                    alert('No customers found with the specified filters.');
                    document.getElementById('filterFormContainer').innerHTML += `<p>No results found.</p>`;
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Failed to filter customers. Please try again later.');
            });
    });
}

function displayFilteredCustomers(customers) {
    const resultContainer = document.getElementById('filterFormContainer');
    resultContainer.innerHTML = `<h3>Filtered Customers</h3><ul id="customer-list"></ul>`;

    const customerList = document.getElementById('customer-list');

    customers.forEach(customer => {
        const listItem = document.createElement('li');
        listItem.innerHTML = `
            <strong>Username:</strong> ${customer.username} <br>
            <strong>Name:</strong> ${customer.firstName} ${customer.lastName} <br>
            <strong>Email:</strong> ${customer.email} <br>
            <strong>National ID:</strong> ${customer.nationalId} <br>
            <strong>Phone Number:</strong> ${customer.phoneNumber} <br>
            <strong>Birthday:</strong> ${customer.birthday} <br>
            <strong>Created At:</strong> ${customer.createdAt} <br>
            <strong>User Status:</strong> ${customer.userStatus} <br>
            <strong>Balance:</strong> ${customer.balance} <br>
        `;
        customerList.appendChild(listItem);
    });
}


function loadFilterExpertsForm() {
    document.getElementById('filterFormContainer').innerHTML = `
        <h3>Filter Experts</h3>
        <form id="filter-experts-form">
            <input type="text" id="expert-username" placeholder="Username"><br>
            <input type="text" id="expert-firstname" placeholder="First Name"><br>
            <input type="text" id="expert-lastname" placeholder="Last Name"><br>
            <input type="text" id="expert-nationalId" placeholder="National ID"><br>
            <input type="text" id="expert-phoneNumber" placeholder="Phone Number"><br>
            <input type="text" id="expert-email" placeholder="Email"><br>
            <input type="number" id="expert-rating" placeholder="Rating"><br>
            <select id="expert-userStatus">
                <option value="">Select User Status</option>
                <option value="NEW">New</option>
                <option value="PENDING">Pending</option>
                <option value="ACTIVE">Active</option>
            </select><br>
            <input type="number" id="expert-balance" placeholder="Balance"><br>
            <input type="date" id="expert-createdAt" placeholder="Created At"><br>
            <input type="date" id="expert-birthday" placeholder="Birthday"><br>
            <input type="number" id="expert-subServiceId" placeholder="Sub Service ID"><br>
            <button type="submit">Filter</button>
        </form>
        <div id="filter-results"></div>
    `;

    // Handle form submission
    const expertForm = document.getElementById('filter-experts-form');
    expertForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const params = new URLSearchParams();
        const addParam = (key, value) => { if (value) params.append(key, value); };

        addParam('username', document.getElementById('expert-username').value);
        addParam('firstName', document.getElementById('expert-firstname').value);
        addParam('lastName', document.getElementById('expert-lastname').value);
        addParam('nationalId', document.getElementById('expert-nationalId').value);
        addParam('phoneNumber', document.getElementById('expert-phoneNumber').value);
        addParam('email', document.getElementById('expert-email').value);
        addParam('rating', document.getElementById('expert-rating').value);
        addParam('userStatus', document.getElementById('expert-userStatus').value);
        addParam('balance', document.getElementById('expert-balance').value);
        addParam('createdAt', document.getElementById('expert-createdAt').value);
        addParam('birthday', document.getElementById('expert-birthday').value);
        addParam('subServiceId', document.getElementById('expert-subServiceId').value);

        fetch(`http://localhost:8081/v1/experts/filter?${params.toString()}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(errorData => {
                        throw new Error(errorData.message || 'An error occurred.');
                    });
                }
                return response.json();
            })
            .then(data => {
                console.log('Filtered Experts:', data);
                if (data && data.content && data.content.length > 0) {
                    displayFilteredExperts(data.content);
                } else {
                    document.getElementById('filter-results').innerHTML = `<p>No experts found.</p>`;
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert(`Error: ${error.message}`);
            });
    });
}

// Function to display filtered experts
function displayFilteredExperts(experts) {
    const resultContainer = document.getElementById('filter-results');
    resultContainer.innerHTML = `<h3>Filtered Experts</h3><ul id="expert-list"></ul>`;

    const expertList = document.getElementById('expert-list');

    experts.forEach(expert => {
        const listItem = document.createElement('li');
        listItem.innerHTML = `
            <strong>Username:</strong> ${expert.username} <br>
            <strong>Name:</strong> ${expert.firstName} ${expert.lastName} <br>
            <strong>Email:</strong> ${expert.email} <br>
            <strong>National ID:</strong> ${expert.nationalId} <br>
            <strong>Phone Number:</strong> ${expert.phoneNumber} <br>
            <strong>Rating:</strong> ${expert.rating} <br>
            <strong>User Status:</strong> ${expert.userStatus} <br>
            <strong>Balance:</strong> ${expert.balance} <br>
            <strong>Created At:</strong> ${expert.createdAt} <br>
            <strong>Birthday:</strong> ${expert.birthday} <br>
            <strong>Sub Service ID:</strong> ${expert.subServiceId} <br>
        `;
        expertList.appendChild(listItem);
    });
}

// Load the form on page load
window.onload = loadFilterExpertsForm;




function loadAddMainServiceForm() {
    document.getElementById('form-container').innerHTML = `
        <h2>Add Main Service</h2>
        <form id="add-main-service-form">
            <input type="text" id="main-service-name" placeholder="Main Service Name" required><br>
            <button type="submit">Add Main Service</button>
        </form>
    `;

    // Add form submission handler
    const form = document.getElementById('add-main-service-form');
    form.addEventListener('submit', function (event) {
        event.preventDefault(); // Prevent default form submission

        const name = document.getElementById('main-service-name').value;

        // Create the payload
        const payload = {
            name: name
        };

        // Send the request to the backend using fetch
        fetch('http://localhost:8081/v1/main-services', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(payload),
        })
            .then(response => {
                if (!response.ok) {
                    // Check if the response is not successful (status code not OK)
                    return response.json().then(errorData => {
                        // Handle the error response from backend
                        const errorMessage = errorData.message || 'An unknown error occurred.';
                        alert(`Error: ${errorMessage}`);
                        throw new Error(errorMessage); // Stop further execution and throw error
                    });
                }
                return response.json(); // Proceed to parse response as JSON if successful
            })
            .then(data => {
                // Only execute this if the response is successful
                alert('Main service added successfully!');
                console.log(data); // Log the data from the backend for debugging
            })
            .catch(error => {
                // Log the error and show a generic failure message if something went wrong
                console.error('Error:', error);
                alert('Failed to add main service. Please try again later.');
            });
    });
}


function loadAddSubServiceForm() {
    document.getElementById('form-container').innerHTML = `
        <h2>Add Sub Service</h2>
        <form id="add-sub-service-form">
            <input type="text" id="sub-service-name" placeholder="Sub Service Name" required><br>
            <input type="number" id="sub-service-baseCost" placeholder="Base Cost" required><br>
            <textarea id="sub-service-description" placeholder="Description" required></textarea><br>
            <input type="number" id="sub-service-mainServiceId" placeholder="Main Service ID" required><br>
            <button type="submit">Add Sub Service</button>
        </form>
    `;

    // Add form submission handler
    const form = document.getElementById('add-sub-service-form');
    form.addEventListener('submit', function (event) {
        event.preventDefault(); // Prevent default form submission

        const name = document.getElementById('sub-service-name').value;
        const baseCost = document.getElementById('sub-service-baseCost').value;
        const description = document.getElementById('sub-service-description').value;
        const mainServiceId = document.getElementById('sub-service-mainServiceId').value;

        // Create the payload
        const payload = {
            name: name,
            baseCost: baseCost,
            description: description,
            mainServiceId: mainServiceId
        };

        // Send the request to the backend using fetch
        fetch('http://localhost:8081/v1/sub-services', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(payload),
        })
            .then(response => {
                if (!response.ok) {
                    // Check if the response is not successful (status code not OK)
                    return response.json().then(errorData => {
                        // Handle the error response from backend
                        const errorMessage = errorData.message || 'An unknown error occurred.';
                        alert(`Error: ${errorMessage}`);
                        throw new Error(errorMessage); // Stop further execution and throw error
                    });
                }
                return response.json(); // Proceed to parse response as JSON if successful
            })
            .then(data => {
                // Only execute this if the response is successful
                alert('Sub service added successfully!');
                console.log(data); // Log the data from the backend for debugging
            })
            .catch(error => {
                // Log the error and show a generic failure message if something went wrong
                console.error('Error:', error);
                alert('Failed to add sub service. Please try again later.');
            });
    });
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



function loadAllMainServices() {
    document.getElementById('form-container').innerHTML = `<h2>Loading Main Services...</h2>`;

    // Fetch all main services from the backend
    fetch('http://localhost:8081/v1/main-services/all', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => response.json())
        .then(data => {
            if (data && data.length > 0) {
                displayAllMainServices(data);
            } else {
                document.getElementById('form-container').innerHTML = `<p>No Main Services available.</p>`;
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to fetch main services. Please try again later.');
        });
}

function displayAllMainServices(services) {
    const container = document.getElementById('form-container');
    container.innerHTML = `<h2>All Main Services</h2><ul id="main-service-list"></ul>`;

    const list = document.getElementById('main-service-list');

    services.forEach(service => {
        const listItem = document.createElement('li');
        listItem.innerHTML = `
            <strong>Name:</strong> ${service.name} <br>
            <strong>Created At:</strong> ${service.createdAt} <br>
            <strong>Updated At:</strong> ${service.updatedAt} <br>
            <strong>Sub Service IDs:</strong> ${service.subServiceIds.join(', ')} <br>
        `;
        list.appendChild(listItem);
    });
}