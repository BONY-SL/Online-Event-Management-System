document.getElementById("registerForm").addEventListener("submit", async function (e){
    e.preventDefault();

    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    const raw = JSON.stringify({
        "name": document.getElementById('name').value,
        "email": document.getElementById('email').value,
        "password": document.getElementById('password').value,
        "role": document.getElementById('role').value,
        "phone": document.getElementById('phone').value
    });

    const requestOptions = {
        method: "POST",
        headers: myHeaders,
        body: raw,
        redirect: "follow"
    };

    try {
        const response = await fetch("http://localhost:8080/eventmanage_war_exploded/register", requestOptions);

        const result = await response.json();

        if (response.ok) {
            console.log(result.message);
            console.error(result.data);

            window.location.href = "/eventmanage_war_exploded/login.jsp";
        } else {
            console.error(result.message);
            alert(result.message);
        }
    } catch (error) {
        console.error('Registration failed:', error);
        alert('Registration failed. Please try again.');
    }
});