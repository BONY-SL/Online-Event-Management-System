<body>
<div class="container my-4">
    <h1 class="text-center mb-4">Registered Users</h1>
    <div id="registerdUserContainer" class="row">
        <div class="table-container">
            <table id="eventsTable">
                <thead>
                <tr>
                    <th>Event Name</th>
                    <th>Date</th>
                    <th>Venue</th>
                    <th>Capacity</th>
                    <th>Registered Users</th>
                    <th>Show Users</th>
                </tr>
                </thead>
                <tbody id="eventsTableBody2">
                </tbody>
            </table>
        </div>
    </div>
</div>
<div class="modal fade" id="userModal" tabindex="-1" aria-labelledby="userModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="userModalLabel">Registered Users</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="modalUserList">
            </div>
        </div>
    </div>
</div>
</body>



