<body>
<div class="container my-4">
    <h1 class="text-center mb-4">Reports</h1>

    <div class="text-center mb-3">
        <button class="btn btn-primary mx-2" onclick="showReport('attendance')">Attendance Report</button>
        <button class="btn btn-success mx-2" onclick="showReport('popular')">Most Popular Events</button>
    </div>

    <div id="attendanceReport" class="report-section d-none">
        <div class="d-flex justify-content-between">
            <h3 style="text-align: center">Attendance Report</h3>
            <button class="btn btn-secondary" onclick="printReport('attendanceReport')">Print</button>
        </div>
        <table class="table table-bordered mt-3">
            <thead>
            <tr>
                <th>Event Name</th>
                <th>Date</th>
                <th>Venue</th>
                <th>Capacity</th>
                <th>Registered Users</th>
                <th>Total Attendance</th>
            </tr>
            </thead>
            <tbody id="attendanceTableBody">
            <!-- Filled by JS -->
            </tbody>
        </table>
    </div>

    <div id="popularReport" class="report-section d-none">
        <div class="d-flex justify-content-between">
            <h3 style="text-align: center">Most Popular Events</h3>
            <button class="btn btn-secondary" onclick="printReport('popularReport')">Print</button>
        </div>
        <table class="table table-bordered mt-3">
            <thead>
            <tr>
                <th>Event Name</th>
                <th>Date</th>
                <th>Venue</th>
                <th>Total Attendance</th>
            </tr>
            </thead>
            <tbody id="popularTableBody">
            <!-- Filled by JS -->
            </tbody>
        </table>
    </div>
</div>
</body>
