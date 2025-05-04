<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Dashboard</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .sidebar {
            height: 100vh;
            background-color: #343a40;
            padding-top: 20px;
            position: fixed;
            left: 0;
            top: 0;
            width: 230px;
            color: #fff;
        }
        .sidebar a {
            color: #ddd;
            padding: 15px;
            display: block;
            text-decoration: none;
        }
        .sidebar a:hover {
            background-color: #495057;
            color: #fff;
        }
        .main-content {
            margin-left: 220px;
            padding: 20px;
        }
        .card {
            border-radius: 1rem;
            margin-top: 20px;
        }
        #customAlert {
            position: fixed;
            top: 20px;
            right: 20px;
            min-width: 250px;
            z-index: 1050;
            padding: 15px;
            border-radius: 4px;
            color: white;
            font-weight: bold;
        }
        .card2 {
            width: 100%;
            max-width: 400px;
            margin: 20px auto;
            border: 1px solid #e0e0e0;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            background-color: #fff;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .card2-img-top {
            width: 100%;
            height: 200px;
            object-fit: cover;
        }

        .card2-body {
            padding: 20px;
        }

        .card2-title {
            font-size: 1.25rem;
            font-weight: 600;
            margin-bottom: 15px;
            color: #333;
        }

        .card2-text {
            width: 100%;
            font-size: 0.95rem;
            margin-bottom: 20px;
            border-collapse: collapse;
        }

        .card2-text th {
            padding-right: 10px;
            vertical-align: top;
            color: #555;
            font-weight: 500;
            white-space: nowrap;
        }

        .card2-text td {
            color: #666;
            word-break: break-word;
        }

        button.btn {
            padding: 8px 16px;
            font-size: 0.95rem;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .btn-success {
            background-color: #28a745;
            color: white;
            margin-right: 10px;
        }

        .btn-danger {
            background-color: #dc3545;
            color: white;
        }

        @media (max-width: 480px) {
            .card2 {
                margin: 10px;
            }

            .card2-body {
                padding: 15px;
            }
        }
        .alert-success { background-color: #28a745; }
        .alert-error { background-color: #dc3545; }
        .alert-warning { background-color: #ffc107; color: #212529; }

        #map {
            height: 400px;
            width: 100%;
            margin-bottom: 20px;
        }
        .table-container {
            max-width: 90%;
            margin: 40px auto;
            overflow-x: auto;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            background: #fff;
            border-radius: 8px;
            padding: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            text-align: left;
        }

        thead {
            background-color: #007bff;
            color: white;
        }

        th, td {
            padding: 12px 16px;
            border-bottom: 1px solid #ddd;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        @media screen and (max-width: 600px) {
            table, thead, tbody, th, td, tr {
                display: block;
            }

            thead {
                display: none;
            }

            td {
                position: relative;
                padding-left: 50%;
                text-align: right;
            }

            td::before {
                position: absolute;
                top: 12px;
                left: 16px;
                width: 45%;
                white-space: nowrap;
                font-weight: bold;
                text-align: left;
            }

            td:nth-of-type(1)::before { content: "Event Name"; }
            td:nth-of-type(2)::before { content: "Date"; }
            td:nth-of-type(3)::before { content: "Venue"; }
            td:nth-of-type(4)::before { content: "Tickets Booked"; }}
    </style>
</head>
<body>

<div class="sidebar">
    <h4 class="text-center text-white">Admin Dashboard</h4>
    <hr/>
    <a href="#" data-url="admin-dash.jsp" onclick="loadContent(event),loadDashboardCounts()">Dashboard</a>
    <a href="#" data-url="add-event.jsp" onclick="loadContent(event)">Add New Event</a>
    <a href="#" data-url="manage-event.jsp" onclick="loadContent(event),getAll()">Manage Events</a>
    <a href="#" data-url="user-manage.jsp" onclick="loadContent(event),getAllEventWithUsers()">Registered Users</a>
    <a href="#"  data-url="report.jsp" onclick="loadContent(event)">Reports</a>
    <a href="${pageContext.request.contextPath}"
       onclick="return confirmLogout(event);">Logout</a>

</div>

<div class="main-content">
    <div id="content-area" class="mt-4">

    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function loadContent(eventOrUrl) {
        let url;
        if (typeof eventOrUrl === 'string') {
            url = eventOrUrl;
        } else {
            eventOrUrl.preventDefault();
            url = eventOrUrl.currentTarget.getAttribute("data-url");
        }

        fetch(url)
            .then(response => {
                if (!response.ok) throw new Error("Page load failed");
                return response.text();
            })
            .then(html => {
                document.getElementById("content-area").innerHTML = html;


                if (document.getElementById("chartContainer") && typeof renderDashboardChart === "function") {
                    renderDashboardChart(); // Call your chart drawing function
                }

                if (document.getElementById("eventForm")) {
                    attachEventFormListener();
                    // Only call initMap if #map exists
                    if (document.getElementById("map") && typeof initMap === "function") {
                        initMap();
                    }
                }
            })
            .catch(error => {
                document.getElementById("content-area").innerHTML = "<p class='text-danger'>Failed to load content.</p>";
                console.error("Error loading content:", error);
            });
    }

    function confirmLogout(event) {
        event.preventDefault();

        Swal.fire({
            title: 'Are you sure?',
            text: "You will be logged out.",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, log out!',
            cancelButtonText: 'Cancel'
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = event.target.href;
            }
        });
    }


    document.addEventListener("DOMContentLoaded", function () {
        loadContent("admin-dash.jsp");
    });
</script>
<script>
    let map, marker;

    function initMap() {
        const defaultLocation = { lat: 6.9271, lng: 79.8612 }; // Colombo

        map = new google.maps.Map(document.getElementById("map"), {
            center: defaultLocation,
            zoom: 13
        });

        marker = new google.maps.Marker({
            position: defaultLocation,
            map: map,
            draggable: true
        });

        // Set initial lat/lng values
        updateLatLngInputs(marker.getPosition());

        marker.addListener("dragend", () => {
            updateLatLngInputs(marker.getPosition());
        });

        map.addListener("click", (e) => {
            marker.setPosition(e.latLng);
            updateLatLngInputs(e.latLng);
        });
    }

    function updateLatLngInputs(position) {
        document.getElementById("latitude").value = position.lat();
        document.getElementById("longitude").value = position.lng();
    }
</script>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11" defer></script>
<script src="${pageContext.request.contextPath}/js/admin.js" defer></script>
<script src="${pageContext.request.contextPath}/js/chart.js" defer></script>
<script src="https://maps.googleapis.com/maps/api/js?key=YOUR_KEY&callback=initMap" async defer></script>

</body>
</html>
