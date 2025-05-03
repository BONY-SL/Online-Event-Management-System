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


let modalMap, modalMarker;
function updateEvent(event) {

    document.getElementById("modalEventName").innerText = event.name;
    document.getElementById("modalEventDate").innerText = event.date;
    document.getElementById("modalEventVenue").innerText = event.venue;
    document.getElementById("modalEventCapacity").innerText = event.capacity;
    document.getElementById("modalEventDescription").innerText = event.description;

    const position = {
        lat: parseFloat(event.latitude),
        lng: parseFloat(event.longitude)
    };

    // Initialize or update map
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

    // Show modal
    const eventModal = new bootstrap.Modal(document.getElementById('eventModal'));
    eventModal.show();
}
