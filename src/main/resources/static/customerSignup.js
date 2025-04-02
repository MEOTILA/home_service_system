document.getElementById("signupForm").addEventListener("submit", async function(event) {
    event.preventDefault();

    const customerData = {
        firstName: document.getElementById("firstName").value,
        lastName: document.getElementById("lastName").value,
        username: document.getElementById("username").value,
        password: document.getElementById("password").value,
        nationalId: document.getElementById("nationalId").value,
        phoneNumber: document.getElementById("phoneNumber").value,
        birthday: document.getElementById("birthday").value,
        email: document.getElementById("email").value
    };

    try {
        const response = await fetch("http://localhost:8081/v1/customers", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(customerData)
        });

        if (!response.ok) {
            const errorData = await response.json();
            document.getElementById("message").textContent = "Signup failed: " + JSON.stringify(errorData);
            document.getElementById("message").style.color = "red";
        } else {
            document.getElementById("message").textContent = "Signup successful!";
            document.getElementById("message").style.color = "green";
        }
    } catch (error) {
        document.getElementById("message").textContent = "Error contacting server!";
        console.error("Error:", error);
    }
});
