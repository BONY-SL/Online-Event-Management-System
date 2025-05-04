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
            formData.append("latitude", document.getElementById("latitude").value);
            formData.append("longitude", document.getElementById("longitude").value);
            formData.append("action", "add-event");


            try {
                const response = await fetch("http://localhost:8080/eventmanage_war_exploded/admin", {
                    method: "POST",
                    body: formData,
                });

                const result = await response.json();
                console.log(result.code, result.message, result.data);
                if(result.code === 201){
                    console.log(result.code, result.message, result.data);
                    showAlert("Event created successfully!", "success");
                    document.getElementById("eventForm").reset();
                }else {
                    showAlert("Failed to create event: " + result.message, "warning");
                }
            } catch (error) {
                console.error("Registration failed:", error);
                showAlert("Network error occurred. Please try again.", "danger");
            }

        });
    }
}
window.addEventListener("DOMContentLoaded", () => {
    attachEventFormListener();
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

function getAll(){

    fetch('http://localhost:8080/eventmanage_war_exploded/admin?action=get-all')
        .then(response => response.json())
        .then(data => {
            const events = data.data;
            const container = document.getElementById('eventContainer');

            events.forEach(event => {
                const col = document.createElement('div');
                col.className = 'col-md-4';

                col.innerHTML = `
                    <div class="card2">
                        <img src="${event.brochureFilePath}" class="card2-img-top event-image" alt="Event Brochure">
                        <div class="card2-body">
                            <h5 class="card2-title" style="text-align: center">${event.name}</h5>
                                <table class="card2-text" border="0px">
                                  <tr>
                                    <th align="left">Date:</th>
                                    <td>${event.date}</td>
                                  </tr>
                                  <tr>
                                    <th align="left">Venue:</th>
                                    <td>${event.venue}</td>
                                  </tr>
                                  <tr>
                                    <th align="left">Capacity:</th>
                                    <td>${event.capacity}</td>
                                  </tr>
                                  <tr>
                                    <th align="left">Description:</th>
                                    <td>${event.description}</td>
                                  </tr>
                                </table>
                            <button class="btn btn-success me-2" onclick="updateEvent(${event.id})">Update</button>
                            <button class="btn btn-danger" onclick="deleteEvent(${event.id})">Delete</button>
                        </div>
                    </div>
                `;
                container.appendChild(col);
            });
        });

}
function updateEvent(id) {
    alert('Update function for event ID: ' + id);
}

async function deleteEvent(id) {
    const confirmResult = await Swal.fire({
        title: 'Are you sure?',
        text: "Do you really want to delete this event?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Yes, delete it!'
    });

    if (confirmResult.isConfirmed) {
        const requestOptions = {
            method: "DELETE",
            redirect: "follow"
        };

        try {
            const response = await fetch(`http://localhost:8080/eventmanage_war_exploded/admin?id=${id}`, requestOptions);
            const result = await response.json();

            if (result.code === 200) {
                Swal.fire({
                    icon: 'success',
                    title: 'Deleted!',
                    text: 'The event has been deleted.',
                    timer: 1500,
                    showConfirmButton: false
                }).then(() => {
                    location.reload();
                });
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Failed to delete the event.'
                });
            }
        } catch (error) {
            console.error('Delete Error', error);
            Swal.fire({
                icon: 'error',
                title: 'Delete Error',
                text: 'Something went wrong. Please try again.'
            });
        }
    }
}

function getAllEventWithUsers() {

    fetch(`http://localhost:8080/eventmanage_war_exploded/admin?action=get-all-events-with-users`)
        .then(response => response.json())
        .then(data => {
            const events = data.data;
            const tableBody = document.getElementById('eventsTableBody2');
            tableBody.innerHTML = ''; // Clear existing rows if any

            events.forEach(event => {
                const row = document.createElement('tr');

                const nameCell = document.createElement('td');
                nameCell.textContent = event.eventName;

                const dateCell = document.createElement('td');
                dateCell.textContent = event.date;

                const venueCell = document.createElement('td');
                venueCell.textContent = event.venue;

                const capacityCell = document.createElement('td');
                capacityCell.textContent = event.capacity;

                const userCount = document.createElement('td');
                userCount.textContent = event.totalRegisteredUsers;

                const showUsers = document.createElement('td');
                const btn = document.createElement('button');
                btn.textContent = 'Show Users';
                btn.className = 'btn btn-dark btn-sm';
                btn.setAttribute('data-bs-toggle', 'modal');
                btn.setAttribute('data-bs-target', '#userModal');

                btn.addEventListener('click', () => {
                    const userList = event.userDTOList;
                    const modalBody = document.getElementById('modalUserList');
                    modalBody.innerHTML = '';

                    if (userList.length === 0) {
                        modalBody.innerHTML = '<p>No users registered.</p>';
                    } else {
                        const ul = document.createElement('ul');
                        userList.forEach(user => {
                            const li = document.createElement('li');
                            li.textContent = `${user.name} (${user.email})`;
                            ul.appendChild(li);
                        });
                        modalBody.appendChild(ul);
                    }
                });

                showUsers.appendChild(btn);

                row.appendChild(nameCell);
                row.appendChild(dateCell);
                row.appendChild(venueCell);
                row.appendChild(capacityCell);
                row.appendChild(userCount);
                row.appendChild(showUsers);
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error fetching events:', error);
        });
}

function loadDashboardCounts() {
    fetch('http://localhost:8080/eventmanage_war_exploded/admin?action=get-dashboard-counts')
        .then(response => response.json())
        .then(result => {
            if (result.code === 200) {
                const data = result.data;

                document.getElementById("totalEventCountCard").textContent = data.totalCount;
                document.getElementById("totalRegisteredUsersCard").textContent = data.totalRegisterdUsers;
                document.getElementById("totalBookedTicketsCard").textContent = data.bookedTickets;
                document.getElementById("totalUpcomingEventsCard").textContent = data.totalUpcommingEvents;
            } else {
                console.error("Failed to load dashboard data.");
            }
        })
        .catch(error => {
            console.error("Error fetching dashboard data: ", error);
        });
}

window.addEventListener('DOMContentLoaded', loadDashboardCounts);