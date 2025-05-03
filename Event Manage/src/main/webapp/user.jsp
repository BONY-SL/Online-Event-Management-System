<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Event Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            padding-top: 60px;
        }
        body::before {
            content: "";
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-image: url("./images/backgraound.png");
            background-size: cover;
            background-position: center;
            filter: blur(3px);
            z-index: -1;
        }
        .navbar {
            margin-bottom: 20px;
        }
        .jumbotron {
            padding: 2rem;
            border-radius: 10px;
            margin-top: 50px;
        }
        .jumbotron h1,
        .jumbotron p {
            font-weight: bold;
        }
        .main-content2 {
            padding: 20px;
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
    </style>
</head>

<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Event Management System</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link"  data-url="search-event.jsp" href="#" onclick="loadContent(event),getAll()">Search Event</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link"  data-url="view-my-book.jsp" href="#" onclick="loadContent(event)">View Booking History</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}"
                       onclick="return confirmLogout();">LogOut</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="main-content2">
    <div id="content-area2" class="mt-4">

    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
</body>

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
                document.getElementById("content-area2").innerHTML = html;

                if (document.getElementById("eventMap") && typeof initMap === "function") {
                    initMap();
                }

            })
            .catch(error => {
                document.getElementById("content-area2").innerHTML = "<p class='text-danger'>Failed to load content.</p>";
                console.error("Error loading content:", error);
            });
    }

    function confirmLogout() {
        return confirm("Are you sure you want to log out?");
    }
    document.addEventListener("DOMContentLoaded", function () {
        loadContent("search-event.jsp");
    });
</script>
<script>
    let map, marker;

    function initMap() {
        const defaultLocation = { lat: 6.9271, lng: 79.8612 };

        map = new google.maps.Map(document.getElementById("eventMap"), {
            center: defaultLocation,
            zoom: 13
        });

        marker = new google.maps.Marker({
            position: defaultLocation,
            map: map,
            draggable: true
        });
    }
</script>
<script src="${pageContext.request.contextPath}/js/user.js" defer></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDs3kiKgPE3Et4jRhoDY-OPegAfSV_Q9vQ&callback=initMap" async defer></script>
</html>
