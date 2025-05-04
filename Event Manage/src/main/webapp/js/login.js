// login manage

document.getElementById("loginForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const payload = {
        email: document.getElementById('email').value,
        password: document.getElementById('password').value
    };

    try {
        const response = await fetch("http://localhost:8080/eventmanage_war_exploded/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        const result = await response.json();
        console.log(result)
        if (result.code === 200 && result.data.role) {
            const userRole = result.data.role;
            const email = result.data.email;
            localStorage.setItem('userRole', userRole);
            localStorage.setItem('userEmail',email);
            if(result.data.role === "ADMIN"){
                window.location.href = "/eventmanage_war_exploded/admin.jsp";
            } else if(result.data.role === "USER"){
                window.location.href = "/eventmanage_war_exploded/user.jsp";
            }
        } else {
            showAlert(result.message, "warning");
        }

    } catch (error) {
        showAlert(error, "error");
    }
});

function showAlert(message, type , duration = 5000) {
    const alertDiv = document.getElementById("customAlert");
    alertDiv.className = `alert alert-${type}`;
    alertDiv.textContent = message;
    alertDiv.classList.remove("d-none");

    setTimeout(() => {
        alertDiv.classList.add("d-none");
    }, duration);
}