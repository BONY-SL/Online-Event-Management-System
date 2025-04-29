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
    </style>
</head>
<body>

<div class="sidebar">
    <h4 class="text-center text-white">Admin Dashboard</h4>
    <hr/>
    <a href="#" data-url="admin-dash.jsp" onclick="loadContent(event)">📝 Dashboard</a>
    <a href="#" data-url="add-event.jsp" onclick="loadContent(event)">➕ Add New Event</a>
    <a href="#" data-url="manage-event.jsp" onclick="loadContent(event)">📝 Manage Events</a>
    <a href="#" data-url="user-manage.jsp" onclick="loadContent(event)">👥 Registered Users</a>
    <a href="#"  data-url="report.jsp" onclick="loadContent(event)">📊 Reports</a>
    <a href="${pageContext.request.contextPath}"
       onclick="return confirmLogout();">🚪 Logout</a>

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
</body>
</html>
