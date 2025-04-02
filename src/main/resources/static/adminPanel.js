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
            <select id="sub-service-mainServiceId" required>
                <option value="">Select Main Service</option>
            </select><br>
            <button type="submit">Add Sub Service</button>
        </form>
    `;

    // Fetch and populate main services dropdown
    fetchMainServices();

    // Handle form submission
    const form = document.getElementById('add-sub-service-form');
    form.addEventListener('submit', function (event) {
        event.preventDefault();

        const name = document.getElementById('sub-service-name').value;
        const baseCost = document.getElementById('sub-service-baseCost').value;
        const description = document.getElementById('sub-service-description').value;
        const mainServiceId = document.getElementById('sub-service-mainServiceId').value;

        if (!validateForm(name, baseCost, description, mainServiceId)) return;

        const payload = { name, baseCost, description, mainServiceId };

        submitSubService(payload);
    });
}

function fetchMainServices() {
    fetch('http://localhost:8081/v1/main-services/all')
        .then(response => response.json())
        .then(data => {
            console.log('Main Services:', data);  // Check the data here
            populateMainServicesDropdown(data);
        })
        .catch(error => {
            console.error('Error fetching main services:', error);
            alert('Failed to load main services.');
        });
}

function populateMainServicesDropdown(services) {
    const mainServiceDropdown = document.getElementById('sub-service-mainServiceId');

    console.log('Populating Main Services Dropdown with:', services);  // Check this log

    // Clear any existing options (in case of form reload)
    mainServiceDropdown.innerHTML = `<option value="">Select Main Service</option>`;

    // If no services were fetched, show a placeholder message
    if (services.length === 0) {
        const option = document.createElement('option');
        option.textContent = "No Main Services available";
        mainServiceDropdown.appendChild(option);
    } else {
        services.forEach(service => {
            const option = document.createElement('option');
            option.value = service.id;
            option.textContent = service.name;
            mainServiceDropdown.appendChild(option);
        });
    }
}

function validateForm(name, baseCost, description, mainServiceId) {
    if (!name || !baseCost || !description || !mainServiceId) {
        alert('All fields are required.');
        return false;
    }
    return true;
}

function submitSubService(payload) {
    fetch('http://localhost:8081/v1/sub-services', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorData => {
                    alert(`Error: ${errorData.message || 'An unknown error occurred.'}`);
                    throw new Error(errorData.message);
                });
            }
            return response.json();
        })
        .then(data => {
            alert('Sub service added successfully!');
            console.log(data);
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to add sub service. Please try again later.');
        });
}






function loadAddExpertToSubServiceForm() {
    // Step 1: Load main services
    document.getElementById('form-container').innerHTML = `
        <h2>Add Expert to Sub Service</h2>
        <h3>Select a Main Service</h3>
        <select id="main-service-select" required>
            <option value="">--Select Main Service--</option>
            <!-- Main services will be populated here -->
        </select>
        <div id="sub-service-container" style="display:none;">
            <h3>Select a Sub Service</h3>
            <select id="sub-service-select" required>
                <option value="">--Select Sub Service--</option>
                <!-- Sub services will be populated here -->
            </select>
        </div>
        <div id="expert-id-container" style="display:none;">
            <input type="number" id="expert-id" placeholder="Expert ID" required><br>
            <button type="submit" id="add-expert-btn">Add Expert</button>
        </div>
    `;

    // Step 2: Fetch and load main services from the server
    fetch('http://localhost:8081/v1/main-services/all')
        .then(response => response.json())
        .then(mainServices => {
            const mainServiceSelect = document.getElementById('main-service-select');
            mainServices.forEach(service => {
                const option = document.createElement('option');
                option.value = service.id;
                option.textContent = service.name;
                mainServiceSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error loading main services:', error);
            alert('Failed to load main services.');
        });

    // Step 3: Add event listener to the main service select dropdown
    const mainServiceSelect = document.getElementById('main-service-select');
    mainServiceSelect.addEventListener('change', function () {
        const mainServiceId = mainServiceSelect.value;
        if (!mainServiceId) {
            document.getElementById('sub-service-container').style.display = 'none';
            document.getElementById('expert-id-container').style.display = 'none';
            return;
        }

        // Step 4: Fetch sub-services based on selected main service
        fetch(`http://localhost:8081/v1/sub-services/by-main-service/${mainServiceId}`)
            .then(response => response.json())
            .then(subServices => {
                const subServiceSelect = document.getElementById('sub-service-select');
                subServiceSelect.innerHTML = '<option value="">--Select Sub Service--</option>'; // Clear existing options

                subServices.forEach(service => {
                    const option = document.createElement('option');
                    option.value = service.id;
                    option.textContent = service.name;
                    subServiceSelect.appendChild(option);
                });

                document.getElementById('sub-service-container').style.display = 'block'; // Show sub-service dropdown
                document.getElementById('expert-id-container').style.display = 'none'; // Hide expert input by default
            })
            .catch(error => {
                console.error('Error loading sub-services:', error);
                alert('Failed to load sub-services.');
            });
    });

    // Step 4.1: Add event listener to the sub-service select dropdown
    const subServiceSelect = document.getElementById('sub-service-select');
    subServiceSelect.addEventListener('change', function () {
        const subServiceId = subServiceSelect.value;
        if (!subServiceId) {
            document.getElementById('expert-id-container').style.display = 'none';
            return;
        }

        // Show the expert ID input and button
        document.getElementById('expert-id-container').style.display = 'block';
    });

    // Step 5: Add event listener to the "Add Expert" button (this should be outside of other listeners)
    const addExpertButton = document.getElementById('add-expert-btn');
    addExpertButton.addEventListener('click', function (event) {
        event.preventDefault();

        const subServiceId = document.getElementById('sub-service-select').value;
        const expertId = document.getElementById('expert-id').value;

        // Validate inputs
        if (!subServiceId || !expertId) {
            alert('Both Sub Service and Expert ID are required!');
            return;
        }

        // Send the request to the backend to add expert to sub-service
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
                        console.error('Error details:', errorData);
                        throw new Error(errorMessage);
                    });
                }
                // Handle success
                alert('Expert added to sub service successfully!');
                console.log('Expert added successfully');
                document.getElementById('expert-id').value = ''; // Clear input field
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Failed to add expert to sub service. Please try again later.');
            });
    });
}




function loadAllMainServices() {
    document.getElementById('form-container').innerHTML = "<h2>Loading Main Services...</h2>";

    fetch('http://localhost:8081/v1/main-services/all', {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' }
    })
        .then(response => response.json())
        .then((mainServices) => {
            if (mainServices && mainServices.length > 0) {
                displayMainServicesList(mainServices);
            } else {
                document.getElementById('form-container').innerHTML = "<p>No Main Services available.</p>";
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to fetch main services. Please try again later.');
        });
}

function displayMainServicesList(mainServices) {
    const container = document.getElementById('form-container');
    container.innerHTML = "<h2>Main Services</h2><ul id='main-service-list'></ul>";

    const list = document.getElementById('main-service-list');

    mainServices.forEach(mainService => {
        const listItem = document.createElement('li');
        listItem.innerHTML = `<a href="#" onclick="toggleSubServices(${mainService.id})">${mainService.name}</a>`;

        const subServiceContainer = document.createElement('div');
        subServiceContainer.id = `sub-service-${mainService.id}`;
        subServiceContainer.style.display = "none";

        listItem.appendChild(subServiceContainer);
        list.appendChild(listItem);
    });
}

async function toggleSubServices(mainServiceId) {
    const subServiceContainer = document.getElementById(`sub-service-${mainServiceId}`);

    if (subServiceContainer.style.display === "block") {
        subServiceContainer.style.display = "none"; // Hide if already shown
        return;
    }

    // Show loading message inside the sub-service container
    subServiceContainer.innerHTML = "<p>Loading sub-services...</p>";
    subServiceContainer.style.display = "block";

    try {
        const response = await fetch(`http://localhost:8081/v1/sub-services/by-main-service/${mainServiceId}`, {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }
        });

        if (!response.ok) throw new Error(`Failed to fetch sub-services for main service ID: ${mainServiceId}`);

        const subServices = await response.json();

        if (subServices.length > 0) {
            subServiceContainer.innerHTML = "<ul>" +
                subServices.map(subService => `
                    <li>
                        <strong>${subService.name}</strong><br>
                        <em>Base Cost:</em> ${subService.baseCost} <br>
                        <em>Description:</em> ${subService.description}
                    </li>
                `).join('') +
                "</ul>";
        } else {
            subServiceContainer.innerHTML = "<p>No sub-services available.</p>";
        }
    } catch (error) {
        console.error('Error fetching sub-services:', error);
        subServiceContainer.innerHTML = "<p>Failed to load sub-services.</p>";
    }
}

