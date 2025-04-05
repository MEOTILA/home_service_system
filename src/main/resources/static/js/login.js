document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('login-form');
    const errorMessage = document.getElementById('error-message');

    // Check for error parameter in URL
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        errorMessage.textContent = 'Invalid username or password';
        errorMessage.style.display = 'block';
    }

    // Handle form submission (optional - for AJAX login)
    loginForm.addEventListener('submit', function(e) {
        e.preventDefault();

        // You could add client-side validation here
        // Or convert to AJAX submission if needed

        // For standard form submission:
        this.submit();
    });
});