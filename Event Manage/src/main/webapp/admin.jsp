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
    </style>
</head>
<body>

<div class="sidebar">
    <h4 class="text-center text-white">Admin Dashboard</h4>
    <hr/>
    <a href="#" data-url="admin-dash.jsp" onclick="loadContent(event)">Dashboard</a>
    <a href="#" data-url="add-event.jsp" onclick="loadContent(event)">Add New Event</a>
    <a href="#" data-url="manage-event.jsp" onclick="loadContent(event),getAll()">Manage Events</a>
    <a href="#" data-url="user-manage.jsp" onclick="loadContent(event)">Registered Users</a>
    <a href="#"  data-url="report.jsp" onclick="loadContent(event)">Reports</a>
    <a href="${pageContext.request.contextPath}"
       onclick="return confirmLogout();">Logout</a>

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

    function confirmLogout() {
        return confirm("Are you sure you want to log out?");
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
<script src="${pageContext.request.contextPath}/js/admin.js" defer></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDs3kiKgPE3Et4jRhoDY-OPegAfSV_Q9vQ&callback=initMap" async defer></script>
</body>
</html>
