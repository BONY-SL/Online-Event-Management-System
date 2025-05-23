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
            td:nth-of-type(4)::before { content: "Tickets Booked"; }
        }
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
                    <a class="nav-link"  data-url="view-my-book.jsp" href="#" onclick="loadContent(event),getAllMyBooks()">View Booking History</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}"
                       onclick="return confirmLogout(event);">LogOut</a>
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
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11" defer></script>
<script src="https://maps.googleapis.com/maps/api/js?key=xxx&callback=initMap" async defer></script>
</html>
