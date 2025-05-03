<body>
<div class="container my-4">
    <h1 class="text-center mb-4">All Events</h1>
    <div id="eventContainer" class="row"></div>
</div>
</body>
<div class="modal fade" id="eventModal" tabindex="-1" aria-labelledby="eventModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl modal-dialog-centered">
        <div class="modal-content rounded-4 shadow">
            <div class="modal-header bg-dark text-white">
                <h5 class="modal-title" id="eventModalLabel">Event Details</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body row">
                <div class="col-md-6">
                    <div id="eventMap" style="height: 400px; width: 100%; border-radius: 10px;"></div>
                </div>
                <div class="col-md-6">
                    <h4 id="modalEventName" class="mb-3"></h4>
                    <p><strong>Date:</strong> <span id="modalEventDate"></span></p>
                    <p><strong>Venue:</strong> <span id="modalEventVenue"></span></p>
                    <p><strong>Capacity:</strong> <span id="modalEventCapacity"></span></p>
                    <p><strong>Description:</strong></p>
                    <p id="modalEventDescription"></p>
                    <button class="btn btn-primary me-2">Book Event</button>
                </div>
            </div>
        </div>
    </div>
</div>


