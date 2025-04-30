function attachEventFormListener() {
    const form = document.getElementById("eventForm");
    if (form) {
        form.addEventListener("submit", async function (e) {
            e.preventDefault();

            const myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");


            const formData = new FormData();
            formData.append("name", document.getElementById("event-name").value);
            formData.append("date", document.getElementById("event-date").value);
            formData.append("venue", document.getElementById("event-venue").value);
            formData.append("capacity", document.getElementById("capacity-add").value);
            formData.append("description", document.getElementById("description-add").value);
            formData.append("brochureFilePath", document.getElementById("brochure-add").files[0]);
            formData.append("action", "add-event");


            try {
                const response = await fetch("http://localhost:8080/eventmanage_war_exploded/admin", {
                    method: "POST",
                    body: formData,
                });

                const result = await response.json();
                console.log(result.code, result.message, result.data);
            } catch (error) {
                console.error("Registration failed:", error);
                alert("Registration failed. Please try again.");
            }

        });
    }
}