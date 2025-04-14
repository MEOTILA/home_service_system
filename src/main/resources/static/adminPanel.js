
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

document.getElementById('filterUsers').addEventListener('click', function() {
    // Clear the form container first
    const formContainer = document.getElementById('form-container');
    formContainer.innerHTML = '';

    // Create the filter form
    const filterForm = document.createElement('form');
    filterForm.id = 'userFilterForm';
    filterForm.className = 'admin-form';

    // Add filter options based on UserFilterDTO
    filterForm.innerHTML = `
        <h2>Filter Users</h2>
        
        <!-- Basic Info -->
        <div class="form-row">
            <div class="form-group">
                <label for="firstName">First Name:</label>
                <input type="text" id="firstName" name="firstName">
            </div>
            <div class="form-group">
                <label for="lastName">Last Name:</label>
                <input type="text" id="lastName" name="lastName">
            </div>
        </div>
        
        <div class="form-row">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username">
            </div>
            <div class="form-group">
                <label for="nationalId">National ID:</label>
                <input type="text" id="nationalId" name="nationalId">
            </div>
        </div>
        
        <div class="form-row">
            <div class="form-group">
                <label for="phoneNumber">Phone Number:</label>
                <input type="text" id="phoneNumber" name="phoneNumber">
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email">
            </div>
        </div>
        
        <!-- User Type and Status -->
        <div class="form-row">
            <div class="form-group">
                <label for="userType">User Type:</label>
                <select id="userType" name="userType">
                    <option value="">All</option>
                    <option value="CUSTOMER">Customer</option>
                    <option value="EXPERT">Expert</option>
                    <option value="ADMIN">Admin</option>
                </select>
            </div>
            <div class="form-group">
                <label for="birthday">Birthday:</label>
                <input type="date" id="birthday" name="birthday">
            </div>
        </div>
        
        <!-- Date Range -->
        <div class="form-row">
            <div class="form-group">
                <label for="createdAtFrom">Registered From:</label>
                <input type="datetime-local" id="createdAtFrom" name="createdAtFrom">
            </div>
            <div class="form-group">
                <label for="createdAtTo">Registered To:</label>
                <input type="datetime-local" id="createdAtTo" name="createdAtTo">
            </div>
        </div>
        
        <!-- Balance Range -->
        <div class="form-row">
            <div class="form-group">
                <label for="minBalance">Min Balance:</label>
                <input type="number" id="minBalance" name="minBalance" min="0">
            </div>
            <div class="form-group">
                <label for="maxBalance">Max Balance:</label>
                <input type="number" id="maxBalance" name="maxBalance" min="0">
            </div>
        </div>
        
        <!-- Order Count Range -->
        <div class="form-row">
            <div class="form-group">
                <label for="minOrderCount">Min Order Count:</label>
                <input type="number" id="minOrderCount" name="minOrderCount" min="0">
            </div>
            <div class="form-group">
                <label for="maxOrderCount">Max Order Count:</label>
                <input type="number" id="maxOrderCount" name="maxOrderCount" min="0">
            </div>
        </div>
        
        <!-- Expert Specific -->
        <div id="expertFields" class="form-row" style="display: none;">
            <div class="form-group">
                <label for="minExpertRating">Min Expert Rating:</label>
                <input type="number" id="minExpertRating" name="minExpertRating" min="0" max="100" step="0.1">
            </div>
            <div class="form-group">
                <label for="expertStatus">Expert Status:</label>
                <select id="expertStatus" name="expertStatus">
                    <option value="">Any</option>
                    <option value="NEW">New</option>
                    <option value="PENDING">Pending</option>
                    <option value="APPROVED">Approved</option>
                </select>
            </div>
        </div>
        
        <!-- Customer Specific -->
        <div id="customerFields" class="form-row" style="display: none;">
            <div class="form-group">
                <label for="customerStatus">Customer Status:</label>
                <select id="customerStatus" name="customerStatus">
                    <option value="">Any</option>
                    <option value="NEW">New</option>
                    <option value="PENDING">Pending</option>
                    <option value="APPROVED">Approved</option>
                </select>
            </div>
        </div>
        
        <!-- Pagination and Sorting -->
        <div class="form-row">
            <div class="form-group">
                <label for="page">Page:</label>
                <input type="number" id="page" name="page" min="0" value="0">
            </div>
            <div class="form-group">
                <label for="size">Items per page:</label>
                <input type="number" id="size" name="size" min="1" max="100" value="10">
            </div>
        </div>
        
        <div class="form-row">
            <div class="form-group">
                <label for="sortBy">Sort By:</label>
                <select id="sortBy" name="sortBy">
                    <option value="id">ID</option>
                    <option value="firstName">First Name</option>
                    <option value="lastName">Last Name</option>
                    <option value="createdAt">Registration Date</option>
                    <option value="balance">Balance</option>
                    <option value="orderCount">Order Count</option>
                </select>
            </div>
            <div class="form-group">
                <label for="sortDirection">Sort Direction:</label>
                <select id="sortDirection" name="sortDirection">
                    <option value="ASC">Ascending</option>
                    <option value="DESC">Descending</option>
                </select>
            </div>
        </div>
        
        <button type="submit" class="btn">Filter Users</button>
    `;

    formContainer.appendChild(filterForm);

    // Show/hide expert/customer specific fields based on user type
    document.getElementById('userType').addEventListener('change', function() {
        const userType = this.value;
        document.getElementById('expertFields').style.display = userType === 'EXPERT' ? 'flex' : 'none';
        document.getElementById('customerFields').style.display = userType === 'CUSTOMER' ? 'flex' : 'none';
    });

    // Handle form submission
    filterForm.addEventListener('submit', function(e) {
        e.preventDefault();
        filterUsers();
    });
});

function filterUsers() {
    const form = document.getElementById('userFilterForm');
    const formData = new FormData(form);

    // Build query parameters from form data
    const params = new URLSearchParams();

    // Add all simple fields
    const simpleFields = [
        'firstName', 'lastName', 'username', 'nationalId',
        'phoneNumber', 'email', 'userType', 'sortBy', 'sortDirection',
        'expertStatus', 'customerStatus', 'minOrderCount', 'maxOrderCount'
    ];

    simpleFields.forEach(field => {
        const value = formData.get(field);
        if (value) params.append(field, value);
    });

    // Add date fields
    const dateFields = ['birthday', 'createdAtFrom', 'createdAtTo'];
    dateFields.forEach(field => {
        const value = formData.get(field);
        if (value) params.append(field, value);
    });

    // Add numeric fields
    const numericFields = ['minBalance', 'maxBalance', 'minExpertRating', 'page', 'size'];
    numericFields.forEach(field => {
        const value = formData.get(field);
        if (value) params.append(field, value);
    });

    // Make GET request with query parameters
    fetch(`/v1/admin/filter?${params.toString()}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            displayUsers(data.content, data.totalElements,
                parseInt(formData.get('page') || 0),
                parseInt(formData.get('size') || 10));
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error filtering users: ' + error.message);
        });
}



function displayUsers(users, totalElements, currentPage, pageSize) {
    const formContainer = document.getElementById('form-container');

    // Clear previous results
    formContainer.innerHTML = '';

    if (!users || users.length === 0) {
        formContainer.innerHTML = '<p>No users found matching your criteria.</p>';
        return;
    }

    const resultsDiv = document.createElement('div');
    resultsDiv.className = 'results-container';

    // Show pagination info
    const paginationInfo = document.createElement('div');
    paginationInfo.className = 'pagination-info';
    const startItem = currentPage * pageSize + 1;
    const endItem = Math.min((currentPage + 1) * pageSize, totalElements);
    paginationInfo.textContent = `Showing ${startItem}-${endItem} of ${totalElements} users`;
    resultsDiv.appendChild(paginationInfo);

    // Create users table
    const usersTable = document.createElement('table');
    usersTable.className = 'users-table';

    // Create table header
    const headerRow = document.createElement('tr');
    headerRow.innerHTML = `
        <th>ID</th>
        <th>Name</th>
        <th>Username</th>
        <th>Type</th>
        <th>Status</th>
        <th>Balance</th>
        <th>Rating</th>
        <th>Order Count</th>
        <th>Registered</th>
        <th>Actions</th>
    `;
    usersTable.appendChild(headerRow);

    // Add user rows
    users.forEach(user => {
        const userRow = document.createElement('tr');
        userRow.innerHTML = `
            <td>${user.id}</td>
            <td>${user.firstName} ${user.lastName}</td>
            <td>${user.username}</td>
            <td>${user.userType}</td>
            <td>${user.userStatus || '-'}</td>
            <td>${user.balance || '0'}</td>
            <td>${user.expertRating || '-'}</td>
            <td>${user.orderCount || '0'}</td>
            <td>${new Date(user.createdAt).toLocaleDateString()}</td>
            <td>
                <button class="btn view-orders" data-user-id="${user.id}" data-user-type="${user.userType}">View Orders</button>
            </td>
        `;
        usersTable.appendChild(userRow);
    });

    resultsDiv.appendChild(usersTable);

    // Add pagination controls
    const totalPages = Math.ceil(totalElements / pageSize);
    if (totalPages > 1) {
        const paginationDiv = document.createElement('div');
        paginationDiv.className = 'pagination-controls';

        // Previous button
        if (currentPage > 0) {
            const prevButton = document.createElement('button');
            prevButton.textContent = 'Previous';
            prevButton.className = 'btn';
            prevButton.addEventListener('click', () => {
                document.getElementById('page').value = currentPage - 1;
                filterUsers();
            });
            paginationDiv.appendChild(prevButton);
        }

        // Page info
        const pageInfo = document.createElement('span');
        pageInfo.textContent = `Page ${currentPage + 1} of ${totalPages}`;
        paginationDiv.appendChild(pageInfo);

        // Next button
        if (currentPage < totalPages - 1) {
            const nextButton = document.createElement('button');
            nextButton.textContent = 'Next';
            nextButton.className = 'btn';
            nextButton.addEventListener('click', () => {
                document.getElementById('page').value = currentPage + 1;
                filterUsers();
            });
            paginationDiv.appendChild(nextButton);
        }

        resultsDiv.appendChild(paginationDiv);
    }

    formContainer.appendChild(resultsDiv);

    // Add event listeners to view orders buttons
    document.querySelectorAll('.view-orders').forEach(button => {
        button.addEventListener('click', function() {
            const userId = this.getAttribute('data-user-id');
            const userType = this.getAttribute('data-user-type');
            viewUserOrders(userId, userType);
        });
    });
}


function viewUserOrders(userId, userType) {
    let url;

    // Determine the URL to fetch the expert or customer ID
    if (userType === 'EXPERT') {
        url = `http://localhost:8081/v1/users/expert-id/${userId}`;
    } else if (userType === 'CUSTOMER') {
        url = `http://localhost:8081/v1/users/customer-id/${userId}`;
    } else {
        alert('Orders cannot be viewed for this user type.');
        return;
    }

    // Fetch the expert or customer ID
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Failed to fetch expert/customer ID: ${response.statusText}`);
            }
            return response.json();
        })
        .then(data => {
            const expertOrCustomerId = data.id; // Assuming the response contains the ID

            // Fetch orders using the expert or customer ID
            let ordersUrl;
            if (userType === 'EXPERT') {
                ordersUrl = `http://localhost:8081/v1/orders/expert/${expertOrCustomerId}`;
            } else if (userType === 'CUSTOMER') {
                ordersUrl = `http://localhost:8081/v1/orders/customer/${expertOrCustomerId}`;
            }

            // Fetch the orders
            return fetch(ordersUrl);
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Failed to fetch orders: ${response.statusText}`);
            }
            return response.json();
        })
        .then(orders => {
            // Display the orders
            displayUserOrders(orders, userType);
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error fetching user orders: ' + error.message);
        });
}

function displayUserOrders(orders, userType) {
    const formContainer = document.getElementById('form-container');

    // Create a back button
    const backButton = document.createElement('button');
    backButton.textContent = 'Back to Users';
    backButton.className = 'btn back-btn';
    backButton.addEventListener('click', function() {
        // Re-trigger the filter to show users again
        document.getElementById('filterUsers').click();
    });

    // Create orders table
    const ordersTable = document.createElement('table');
    ordersTable.className = 'orders-table';

    // Create table header based on user type
    const headerRow = document.createElement('tr');
    if (userType === 'CUSTOMER') {
        headerRow.innerHTML = `
            <th>Order ID</th>
            <th>Service</th>
            <th>Expert</th>
            <th>Status</th>
            <th>Price</th>
            <th>Created At</th>
        `;
    } else if (userType === 'EXPERT') {
        headerRow.innerHTML = `
            <th>Order ID</th>
            <th>Service</th>
            <th>Customer</th>
            <th>Status</th>
            <th>Price</th>
            <th>Created At</th>
        `;
    }
    ordersTable.appendChild(headerRow);

    // Add order rows
    if (orders && orders.length > 0) {
        orders.forEach(order => {
            const orderRow = document.createElement('tr');
            if (userType === 'CUSTOMER') {
                orderRow.innerHTML = `
                    <td>${order.id}</td>
                    <td>${order.subServiceId}</td>
                    <td>${order.expertId || 'Not assigned'}</td>
                    <td>${order.status}</td>
                    <td>${order.customerOfferedCost}</td>
                    <td>${new Date(order.createdAt).toLocaleDateString()}</td>
                `;
            } else if (userType === 'EXPERT') {
                orderRow.innerHTML = `
                    <td>${order.id}</td>
                    <td>${order.subServiceId}</td>
                    <td>${order.customerId}</td>                   
                    <td>${order.status}</td>
                    <td>${order.customerOfferedCost}</td>
                    <td>${new Date(order.createdAt).toLocaleDateString()}</td>
                `;
            }
            ordersTable.appendChild(orderRow);
        });
    } else {
        const noOrdersRow = document.createElement('tr');
        noOrdersRow.innerHTML = '<td colspan="6">No orders found for this user.</td>';
        ordersTable.appendChild(noOrdersRow);
    }

    // Clear container and add elements
    formContainer.innerHTML = '';
    formContainer.appendChild(backButton);
    formContainer.appendChild(document.createElement('br'));
    formContainer.appendChild(document.createElement('br'));
    formContainer.appendChild(ordersTable);

    // Add a filter button for orders
    const filterOrdersButton = document.createElement('button');
    filterOrdersButton.textContent = 'Filter Orders';
    filterOrdersButton.className = 'btn filter-orders-btn';
    filterOrdersButton.addEventListener('click', function() {
        showOrderFilterForm(userType);
    });
    formContainer.appendChild(filterOrdersButton);
}

function showOrderFilterForm(userType) {
    const formContainer = document.getElementById('form-container');

    // Create the filter form
    const filterForm = document.createElement('form');
    filterForm.id = 'order-filter-form';
    filterForm.innerHTML = `
        <h2>Filter Orders</h2>
        
        <!-- Main-Service ID -->
        <label for="mainServiceId">Main-Service ID:</label>
        <input type="number" id="mainServiceId" name="mainServiceId"><br>
        
        <!-- Sub-Service ID -->
        <label for="subServiceId">Sub-Service ID:</label>
        <input type="number" id="subServiceId" name="subServiceId"><br>

        <!-- Customer ID -->
        <label for="customerId">Customer ID:</label>
        <input type="number" id="customerId" name="customerId"><br>

        <!-- Expert ID -->
        <label for="expertId">Expert ID:</label>
        <input type="number" id="expertId" name="expertId"><br>

        <!-- Min Cost -->
        <label for="minCost">Min Cost:</label>
        <input type="number" id="minCost" name="minCost"><br>

        <!-- Max Cost -->
        <label for="maxCost">Max Cost:</label>
        <input type="number" id="maxCost" name="maxCost"><br>

        <!-- Description -->
        <label for="description">Description:</label>
        <input type="text" id="description" name="description"><br>

        <!-- Service Start Date -->
        <label for="serviceStartDate">Service Start Date:</label>
        <input type="datetime-local" id="serviceStartDate" name="serviceStartDate"><br>

        <!-- Service End Date -->
        <label for="serviceEndDate">Service End Date:</label>
        <input type="datetime-local" id="serviceEndDate" name="serviceEndDate"><br>

        <!-- Address -->
        <label for="address">Address:</label>
        <input type="text" id="address" name="address"><br>

        <!-- Status -->
        <label for="status">Status:</label>
        <select id="status" name="status">
            <option value="">--Select Status--</option>
            <option value="WAITING_FOR_EXPERT_TO_RESPONSE">Waiting for Expert to Respond</option>
            <option value="WAITING_FOR_CUSTOMER_TO_ACCEPT">Waiting for Customer to Accept</option>
            <option value="WAITING_FOR_EXPERT_TO_ARRIVE">Waiting for Expert to Arrive</option>
            <option value="SERVICE_IS_STARTED">Service Started</option>
            <option value="SERVICE_IS_DONE">Service Done</option>
            <option value="SERVICE_IS_PAID">Service Paid</option>
        </select><br>

        <!-- Payment Type -->
        <label for="paymentType">Payment Type:</label>
        <select id="paymentType" name="paymentType">
            <option value="">--Select Payment Type--</option>
            <option value="BY_CREDIT_CARD">Credit Card</option>
            <option value="BY_BALANCE">Balance</option>
        </select><br>

        <!-- Created After -->
        <label for="createdAfter">Created After:</label>
        <input type="datetime-local" id="createdAfter" name="createdAfter"><br>

        <!-- Created Before -->
        <label for="createdBefore">Created Before:</label>
        <input type="datetime-local" id="createdBefore" name="createdBefore"><br>

        <!-- Has Comment -->
        <label for="hasComment">Has Comment:</label>
        <input type="checkbox" id="hasComment" name="hasComment"><br>

        <!-- Sort By -->
        <label for="sortBy">Sort By:</label>
        <select id="sortBy" name="sortBy">
            <option value="createdAt">Created At</option>
            <option value="serviceStartDate">Service Start Date</option>
            <option value="serviceEndDate">Service End Date</option>
            <option value="minCost">Min Cost</option>
            <option value="maxCost">Max Cost</option>
        </select><br>

        <!-- Sort Direction -->
        <label for="sortDirection">Sort Direction:</label>
        <select id="sortDirection" name="sortDirection">
            <option value="ASC">Ascending</option>
            <option value="DESC">Descending</option>
        </select><br>

        <!-- Submit Button -->
        <button type="submit">Apply Filters</button>
    `;

    // Clear container and add the filter form
    formContainer.innerHTML = '';
    formContainer.appendChild(filterForm);

    // Handle form submission
    filterForm.addEventListener('submit', function(event) {
        event.preventDefault();
        filterUserOrders(userType);
    });
}

function filterUserOrders(userType) {
    const form = document.getElementById('order-filter-form');
    const formData = new FormData(form);
    const filter = {};

    // Convert form data to a JSON object
    formData.forEach((value, key) => {
        filter[key] = value;
    });

    // Add user-specific filter (expert or customer ID)
    const userId = getUserIdFromSession(); // Replace with your logic to get the user ID
    if (userType === 'EXPERT') {
        filter.expertId = userId;
    } else if (userType === 'CUSTOMER') {
        filter.customerId = userId;
    }

    // Convert filter object to query parameters
    const queryParams = new URLSearchParams(filter).toString();

    // Fetch filtered orders
    fetch(`http://localhost:8081/v1/orders/paginated?${queryParams}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Failed to fetch filtered orders: ${response.statusText}`);
            }
            return response.json();
        })
        .then(data => {
            // Display the filtered orders
            displayUserOrders(data.content, userType);
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error fetching filtered orders: ' + error.message);
        });
}

function getUserIdFromSession() {
    // Replace this with your logic to get the user ID from the session or state
    return 1; // Example: Return a hardcoded user ID for testing
}



document.getElementById('filterOrders').addEventListener('click', function () {
    const formContainer = document.getElementById('form-container');
    formContainer.innerHTML = `
        <h2>Filter Orders</h2>
        <form id="order-filter-form">
        
            <label for="mainServiceId">Main-Service ID:</label>
            <input type="number" id="mainServiceId" name="mainServiceId"><br>
        
            <label for="subServiceId">Sub-Service ID:</label>
            <input type="number" id="subServiceId" name="subServiceId"><br>

            <label for="customerId">Customer ID:</label>
            <input type="number" id="customerId" name="customerId"><br>

            <label for="expertId">Expert ID:</label>
            <input type="number" id="expertId" name="expertId"><br>

            <label for="minCost">Min Cost:</label>
            <input type="number" id="minCost" name="minCost"><br>

            <label for="maxCost">Max Cost:</label>
            <input type="number" id="maxCost" name="maxCost"><br>

            <label for="description">Description:</label>
            <input type="text" id="description" name="description"><br>

            <label for="serviceStartDate">Service Start Date:</label>
            <input type="datetime-local" id="serviceStartDate" name="serviceStartDate"><br>

            <label for="serviceEndDate">Service End Date:</label>
            <input type="datetime-local" id="serviceEndDate" name="serviceEndDate"><br>

            <label for="address">Address:</label>
            <input type="text" id="address" name="address"><br>

            <label for="status">Status:</label>
            <select id="status" name="status">
                <option value="">--Select Status--</option>
                <option value="PENDING">Pending</option>
                <option value="COMPLETED">Completed</option>
                <option value="CANCELED">Canceled</option>
                <!-- Add other statuses as needed -->
            </select><br>

            <label for="paymentType">Payment Type:</label>
            <select id="paymentType" name="paymentType">
                <option value="">--Select Payment Type--</option>
                <option value="BY_CREDIT_CARD">Credit Card</option>
                <option value="BY_BALANCE">Balance</option>
                <!-- Add other payment types as needed -->
            </select><br>

            <label for="createdAfter">Created After:</label>
            <input type="datetime-local" id="createdAfter" name="createdAfter"><br>

            <label for="createdBefore">Created Before:</label>
            <input type="datetime-local" id="createdBefore" name="createdBefore"><br>

            <label for="hasComment">Has Comment:</label>
            <input type="checkbox" id="hasComment" name="hasComment"><br>

            <button type="submit">Apply Filters</button>
        </form>
    `;

    // Handle form submission
    document.getElementById('order-filter-form').addEventListener('submit', function (event) {
        event.preventDefault();
        filterOrders();
    });
});

function filterOrders() {
    const form = document.getElementById('order-filter-form');
    const formData = new FormData(form);
    const filter = {};

    // Convert form data to a JSON object
    formData.forEach((value, key) => {
        filter[key] = value;
    });

    // Convert checkbox value to boolean
    filter.hasComment = filter.hasComment === 'on';

    // Convert filter object to query parameters
    const queryParams = new URLSearchParams(filter).toString();

    // Fetch paginated and filtered orders
    fetch(`http://localhost:8081/v1/orders/paginated?${queryParams}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Failed to fetch filtered orders: ${response.statusText}`);
            }
            return response.json();
        })
        .then(data => {
            // Display the filtered orders
            displayFilteredOrders(data);
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error fetching filtered orders: ' + error.message);
        });
}


function displayFilteredOrders(filteredOrderResponse) {
    const formContainer = document.getElementById('form-container');

    // Clear previous results
    formContainer.innerHTML = '';

    if (!filteredOrderResponse.content || filteredOrderResponse.content.length === 0) {
        formContainer.innerHTML = '<p>No orders found matching your criteria.</p>';
        return;
    }

    const ordersTable = document.createElement('table');
    ordersTable.className = 'orders-table';

    // Create table header
    const headerRow = document.createElement('tr');
    headerRow.innerHTML = `
        <th>Order ID</th>
        <th>Service</th>
        <th>Expert</th>
        <th>Status</th>
        <th>Price</th>
        <th>Created At</th>
    `;
    ordersTable.appendChild(headerRow);

    // Add order rows
    filteredOrderResponse.content.forEach(order => {
        const orderRow = document.createElement('tr');
        orderRow.innerHTML = `
            <td>${order.id}</td>
            <td>${order.subServiceId}</td>
            <td>${order.expertId || 'Not assigned'}</td>
            <td>${order.status}</td>
            <td>${order.customerOfferedCost}</td>
            <td>${new Date(order.createdAt).toLocaleDateString()}</td>
        `;
        ordersTable.appendChild(orderRow);
    });

    formContainer.appendChild(ordersTable);
}







function loadAddMainServiceForm() {
    document.getElementById('form-container').innerHTML = `
        <h2>Add Main Service</h2>
        <form id="add-main-service-form">
            <input type="text" id="main-service-name" placeholder="Main Service Name" required><br>
            <button type="submit" class="btn">Add Main Service</button>
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
            <button type="submit" class="btn">Add Sub Service</button>
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
            <button type="submit" id="add-expert-btn" class="btn">Add Expert</button>
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

