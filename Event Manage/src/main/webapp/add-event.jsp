<body>
<div class="container-fluid px-3 px-md-5">
    <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10">
            <div class="card shadow rounded-4">
                <div class="card-header bg-dark text-white">
                    <h4 class="mb-0" style="text-align: center">Add New Event</h4>
                </div>
                <div class="card-body">
                    <div id="customAlert" class="alert d-none" role="alert"></div>
                    <form id="eventForm" method="post" enctype="multipart/form-data">
                        <div class="mb-3">
                            <label for="event-name" class="form-label">Event Name</label>
                            <input type="text" class="form-control" id="event-name" name="name" required>
                        </div>
                        <div class="mb-3">
                            <label for="event-date" class="form-label">Event Date</label>
                            <input type="date" class="form-control" id="event-date" name="date" required>
                        </div>

                        <div class="mb-3">
                            <label for="event-venue" class="form-label">Venue</label>
                            <input type="text" class="form-control" id="event-venue" name="venue" required>
                        </div>

                        <div class="mb-3">
                            <label for="capacity-add" class="form-label">Capacity</label>
                            <input type="number" class="form-control" id="capacity-add" name="capacity" min="1" required>
                        </div>

                        <div class="mb-3">
                            <label for="description-add" class="form-label">Description</label>
                            <textarea class="form-control" id="description-add" name="description-add" rows="3"></textarea>
                        </div>

                        <div class="mb-3">
                            <label for="brochure-add" class="form-label">Upload Brochure/Poster</label>
                            <input type="file" class="form-control" id="brochure-add" name="brochure" accept=".pdf,.jpg,.jpeg,.png">
                        </div>

                        <button type="submit" class="btn btn-success w-100">Create Event</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>