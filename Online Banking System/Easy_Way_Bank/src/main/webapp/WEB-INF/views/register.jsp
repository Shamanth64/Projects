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
    <title>Register</title>
</head>
<body class="d-flex align-items-center justify-content-center">
    <!-- Card : Registration Card -->
    <div class="card registration-form-card col-6 bg-transparent border-0">
        <!-- Card Body -->
        <div class="card-body">
            <!-- Form Header -->
            <h1 class="form-header card-title mb-3">
                <i class="fa fa-edit"></i> <b>Register</b>
            </h1>
            <!-- End of Form Header -->

            <!-- Display message -->
            <c:if test="${requestScope.passwordMismatch != null}">
                <div class="alert alert-danger text-center border border-danger">
                    <b>${requestScope.passwordMismatch}</b>
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

            <!-- Registration Form -->
            <form:form  action="/register" class="reg-form" modelAttribute="registerUser">
                <!-- Form row -->
                <div class="row">
                    <!-- Form Group -->
                    <div class="form-group col">
                        <form:input type="text" path="first_name" class="form-control form-control-lg" placeholder="Enter Firstname"/>
                        <form:errors path="first_name" class="text-white bg-danger"/>
                    </div>
                    <!-- End of Form Group -->

                     <!-- Form Group -->
                     <div class="form-group col">
                        <form:input type="text" path="last_name" class="form-control form-control-lg" placeholder="Enter Lastname"/>
                        <form:errors path="last_name" class="text-white bg-danger"/>
                    </div>
                    <!-- End of Form Group -->
                </div>
                <!-- End of Form row -->

                <!-- Form Group -->
                <div class="form-group col">
                    <form:input type="email" path="email" class="form-control form-control-lg" placeholder="Enter Email"/>
                    <form:errors path="email" class="text-white bg-danger"/>
                </div>
                <!-- End of Form Group -->

                <!-- Form row -->
                <div class="row">
                    <!-- Form Group -->
                    <div class="form-group col">
                        <form:input type="password" path="password" class="form-control form-control-lg" placeholder="Enter Password"/>
                        <form:errors path="password" class="text-white bg-danger"/>
                    </div>
                    <!-- End of Form Group -->

                     <!-- Form Group -->
                     <div class="form-group col">
                        <input type="password" name="confirm_password" class="form-control form-control-lg" placeholder="Confirm Password"/>
                        <small class="text-white bg-danger">${confirm_pass}</small>
                    </div>
                    <!-- End of Form Group -->
                </div>
                <!-- End of Form row -->

                <!-- Form Group -->
                <div class="form-group col">
                    <button class="btn btn-lg">Register</button>
                </div>
                <!-- End of Form Group -->
            </form:form>
            <!-- End of Registration Form -->

            <!-- Card Text -->
           <p class="card-text text-white my-2">
                Already have an account ? <span class="ms-2 text-warning">
                <i class="fa-solid fa-right-to-bracket"></i>
                <a href="/login" class="btn btn-sm text-warning">Sign In</a></span>
           </p>
            <!-- End of Card Text -->

            <!-- Back Button -->
            <small class="text-warning">
                <i class="fa fa-arrow-alt-circle-left"></i>
                <a href="/" class="btn btn-sm text-warning">Back</a>
            </small>
            <!-- End of Back Button -->
        </div>
        <!-- End of Card Body -->
    </div>
    <!-- End of Card : Registration Card -->
</body>
</html>