<%--
  Created by IntelliJ IDEA.
  User: danid
  Date: 4/24/2025
  Time: 9:51 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Registration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            padding-top: 40px;
            background-color: #f8f9fa;
        }
        .registration-form {
            max-width: 600px;
            margin: 20px auto;
            padding: 30px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
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
<div id="customAlert" class="alert d-none" role="alert"></div>
<div class="container">
    <div class="registration-form">
        <h2 class="text-center mb-4">User Registration</h2>
        <form id="registerForm">

        <div class="mb-3">
                <label for="name" class="form-label">Full Name</label>
                <input type="text" class="form-control" name="name" id="name" required>
            </div>

            <div class="mb-3">
                <label for="email" class="form-label">Email address</label>
                <input type="email" class="form-control" name="email" id="email" required>
            </div>

            <div class="mb-3">
                <label for="phone" class="form-label">Phone Number</label>
                <input type="text" class="form-control" name="phone" id="phone" required>
            </div>

            <div class="mb-3">
                <label for="role" class="form-label">Role</label>
                <select class="form-select" name="role" id="role">
                    <option value="USER">User</option>
                </select>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" name="password" id="password" required>
            </div>

            <button type="submit" class="btn btn-dark w-100">Register</button>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/register.js"></script>
</body>
</html>

