getAll();

function getAll(){

  fetch('http://localhost:8080/eventmanage_war_exploded/user?action=get-all')
        .then(response => response.json())
        .then(data => {
            const events = data.data;
            const container = document.getElementById('eventContainer');
            console.log(events)
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
                                  <tr>
                                      <td rowspan="4" style="vertical-align: top;">
                                            <button class="btn btn-success me-2" onclick='updateEvent(${JSON.stringify(event)})'>View</button>
                                      </td>
                                   </tr>
                                </table>
                        </div>
                    </div>
                `;
                container.appendChild(col);
            });
        });
}

let getEventId;
let modalMap, modalMarker;
function updateEvent(event) {

    document.getElementById("modalEventName").innerText = event.name;
    document.getElementById("modalEventDate").innerText = event.date;
    document.getElementById("modalEventVenue").innerText = event.venue;
    document.getElementById("modalEventCapacity").innerText = event.capacity;
    document.getElementById("modalEventDescription").innerText = event.description;
    document.getElementById("availableTickets").innerText = event.availableTickets;
    getEventId = event.id;


    const position = {
        lat: parseFloat(event.latitude),
        lng: parseFloat(event.longitude)
    };

    if (!modalMap) {
        modalMap = new google.maps.Map(document.getElementById("eventMap"), {
            zoom: 14,
            center: position,
        });
        modalMarker = new google.maps.Marker({
            position: position,
            map: modalMap,
        });
    } else {
        modalMap.setCenter(position);
        modalMarker.setPosition(position);
    }

    const eventModal = new bootstrap.Modal(document.getElementById('eventModal'));
    eventModal.show();
}

async function bookEvent() {

    const getCount = document.getElementById('book-space').value;

    if(getCount === ''){
        showAlert("Please Enter Ticket Count...", "warning");
    }else {
        const email = localStorage.getItem('userEmail');
        const bookEvent = {
            email: email,
            eventId: getEventId,
            count:getCount,
            action : 'book-event'
        };

        try {
            const response = await fetch("http://localhost:8080/eventmanage_war_exploded/user", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(bookEvent)
            });

            const result = await response.json();
            console.log(result)
            if (result.code === 201) {
                showAlert( "Book Event Successfully...", "success");
                location.reload();
            } else {
                showAlert(result.message, "error");
            }

        } catch (error) {
            alert(error.message);
            showAlert(error.message, "error");

        }
    }
}

function showAlert(message, type , duration = 5000) {
    const alertDiv = document.getElementById("customAlert");
    alertDiv.className = `alert alert-${type}`;
    alertDiv.textContent = message;
    alertDiv.classList.remove("d-none");

    setTimeout(() => {
        alertDiv.classList.add("d-none");
    }, duration);
}

function getAllMyBooks() {
    const myEmail = localStorage.getItem("userEmail");

    fetch(`http://localhost:8080/eventmanage_war_exploded/user?email=${myEmail}&action=get-my-events`)
        .then(response => response.json())
        .then(data => {
            const events = data.data;
            const tableBody = document.getElementById('eventsTableBody');
            tableBody.innerHTML = ''; // Clear existing rows if any

            events.forEach(event => {
                const row = document.createElement('tr');

                const nameCell = document.createElement('td');
                nameCell.textContent = event.eventName;

                const dateCell = document.createElement('td');
                dateCell.textContent = event.date;

                const venueCell = document.createElement('td');
                venueCell.textContent = event.venue;

                const ticketsCell = document.createElement('td');
                ticketsCell.textContent = event.ticketsBooked;

                row.appendChild(nameCell);
                row.appendChild(dateCell);
                row.appendChild(venueCell);
                row.appendChild(ticketsCell);

                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error fetching events:', error);
        });
}
