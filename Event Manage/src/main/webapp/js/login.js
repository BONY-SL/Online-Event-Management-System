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
        if (result.code === 200) {
            console.log(result.message)
            if(result.data.role === "ADMIN"){
                window.location.href = "/eventmanage_war_exploded/admin.jsp";

            }else if(result.data.role === "USER"){
                window.location.href = "/eventmanage_war_exploded/user.jsp";
            }
        } else {
            console.log(result.message)
        }
    } catch (error) {
        console.error('Login failed:', error);
        alert('Login failed. Please try again.');
    }
});