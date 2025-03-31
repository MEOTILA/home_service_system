document.getElementById("signupForm").addEventListener("submit", async function(event) {
    event.preventDefault();

    const expertImageFile = document.getElementById("expertImage").files[0];

    // Check if image is valid (JPEG, max 300KB)
    if (expertImageFile && expertImageFile.type !== "image/jpeg") {
        document.getElementById("message").textContent = "Please upload a JPEG image.";
        document.getElementById("message").style.color = "red";
        return;
    }

    if (expertImageFile && expertImageFile.size > 300 * 1024) { // 300KB
        document.getElementById("message").textContent = "Image size must not exceed 300KB.";
        document.getElementById("message").style.color = "red";
        return;
    }

    const expertData = {
        firstName: document.getElementById("firstName").value,
        lastName: document.getElementById("lastName").value,
        username: document.getElementById("username").value,
        password: document.getElementById("password").value,
        nationalId: document.getElementById("nationalId").value,
        phoneNumber: document.getElementById("phoneNumber").value,
        birthday: document.getElementById("birthday").value,
        email: document.getElementById("email").value
    };

    const formData = new FormData();
    formData.append("firstName", expertData.firstName);
    formData.append("lastName", expertData.lastName);
    formData.append("username", expertData.username);
    formData.append("password", expertData.password);
    formData.append("nationalId", expertData.nationalId);
    formData.append("phoneNumber", expertData.phoneNumber);
    formData.append("birthday", expertData.birthday);
    formData.append("email", expertData.email);
    formData.append("expertImage", expertImageFile);

    try {
        const response = await fetch("http://localhost:8081/api/experts", {
            method: "POST",
            body: formData
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
