<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form"  uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../CSS/Bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    <link rel="stylesheet" href="../CSS/default.css">
    <title>Login</title>
</head>
<body class="d-flex align-items-center justify-content-center">
    <!-- Card : login Card -->
    <div class="card login-form-card col-4 bg-transparent border-0">
        <!-- Card Body -->
        <div class="card-body">
            <!-- Form Header -->
            <h1 class="form-hearder card-title mb-3">
                <i class="fa fa-user-circle"></i> <b>Login</b>
            </h1>
            <!-- End of Form Header -->

            <!-- Display message -->
            <c:if test="${requestScope.error != null}">
                <div class="alert alert-danger text-center border border-danger">
                    <b>${requestScope.error}</b>
                </div>
            </c:if>
            <!-- End of Display message -->

            <!-- Display message -->
            <c:if test="${logged_out != null}">
                <div class="alert alert-info text-center border border-info">
                    <b>${logged_out}</b>
                </div>
            </c:if>
            <!-- End of Display message -->

            <!-- Display message -->
            <c:if test="${requestScope.success != null}">
                <div class="alert alert-success text-center border border-success">
                    <b>${requestScope.success}</b>
                </div>
            </c:if>
             <!-- End of Display message -->

            <!-- login Form -->
            <form action="/login" method="post" class="login-form">
                <!-- Form Group -->
                <div class="form-group col">
                    <input type="email" name="email" class="form-control form-control-lg" placeholder="Enter Email"/>
                </div>
                <!-- End of Form Group -->
                 <!-- Form Group -->
                 <div class="form-group col">
                    <input type="password" name="password" class="form-control form-control-lg" placeholder="Enter Password"/>
                </div>
                <!-- End of Form Group -->

                <!-- Form Group -->
                <div class="form-group col">
                    <input type="hidden" name="_token" value="${token}"/>
                </div>
                <!-- End of Form Group -->

                <!-- Form Group -->
                <div class="form-group col">
                    <button class="btn btn-lg text-warning">Login</button>
                </div>
                <!-- End of Form Group -->
            </form>
            <!-- End of Registration Form -->

            <!-- Card Text -->
           <p class="card-text text-white my-2">
                Do not have an account ? <span class="ms-2 text-warning">
                <i class="fa-solid fa-right-to-bracket"></i>
                <a href="/register" class="btn btn-sm text-warning">Sign Up</a></span>
           </p>
            <!-- End of Card Text -->

            <!-- Back Button -->
            <small class="text-warning">
                <i class="fa fa-arrow-alt-circle-left"></i> <a href="/" class="btn btn-sm text-warning">Back</a>
            </small>
            <!-- End of Back Button -->
        </div>
        <!-- End of Card Body -->
    </div>
    <!-- End of Card : login Card -->
</body>
</html>