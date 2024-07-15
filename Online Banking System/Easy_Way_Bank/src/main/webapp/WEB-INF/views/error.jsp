<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form"  uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../CSS/Bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    <style>
        * {
            box-sizing: border-box;
            font-family: comfortaa;

        }
        body {
            height: 100vh;
            background-color: rgb(57, 57, 57);
            background-size: cover;
            background-position: center center;
            background-repeat: no-repeat;
        }

        .card .card-text {
            font-size: 16px;
        }

        .card {
            box-shadow: 0px 3px 6px rgb(0, 12, 53);
        }
    </style>
    <title>Error</title>
</head>
<body class="d-flex align-items-center justify-content-center">
    <!-- Card : Error Card -->
    <div class="card col-4 alert alert-danger border border-danger text-danger">
        <!-- Card Title -->
        <h3 class="card-title">
            <i class="fa fa-window-close me-2"></i>Errors:
        </h3>
        <!-- End of Card Title -->
        <hr>
            <!-- Card Body -->
            <div class="card-body">
                <!-- Card Text -->
                <p class="card-text">
                  <!-- Display message -->
                              <c:if test="${requestScope.error != null}">
                                  <div class="alert alert-danger text-center border border-danger">
                                      <b>${requestScope.error}</b>
                                  </div>
                              </c:if>
                              <!-- End of Display message -->
                </p>
                <!-- End of Card Text -->
                <hr>
                <!-- BacK to Login Page-->
                <a href="/login" class="btn btn-sm btn-danger">
                    <i class="fa fa-arrow-alt-circle-left me-1"></i> Back
                </a>
                <!-- End of BacK to Login Page-->
            </div>
            <!-- End of Card Body -->
    </div>
    <!-- End of Card: Error Card -->
</body>
</html>