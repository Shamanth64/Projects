<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form"  uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="../CSS/Bootstrap/bootstrap.min.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
	<link rel="stylesheet" href="../CSS/main.css">
	<script src="../JS/bootstrap.bundle.min.js"></script>
	<title>Dashboard</title>
</head>

<body>
    <!-- Header -->
    <c:import url="components/incl/header.jsp"/>

    <!-- Transact Offcanvas: pulls from left -->
    <c:import url="components/transact_offcanvas.jsp"/>

    <!-- Add Accounts Offcanvas: pulls from right -->
    <c:import url="components/add_accounts_offcanvas.jsp"/>

    <!-- Container -->
    <div class="container">
    <!-- Display message -->
        <c:if test="${success != null}">
            <div class="alert alert-info text-center border border-info">
                <b>${success}</b>
            </div>
        </c:if>
        <!-- End of Display message -->

        <!-- Display message -->
        <c:if test="${error != null}">
            <div class="alert alert-danger text-center border border-danger">
                <b>${error}</b>
            </div>
        </c:if>
        <!-- End of Display message -->
    </div>
    <!-- End of Container -->

    <c:choose>
        <c:when test="${fn:length(userAccounts) > 0}">
            <!-- Display Accounts -->
            <c:import url="components/accounts_display.jsp"/>
        </c:when>
        <c:otherwise>
            <!-- Do not display accounts -->
            <c:import url="components/no_accounts_display.jsp"/>
        </c:otherwise>
    </c:choose>

    <!-- Footer -->
    <c:import url="components/incl/footer.jsp"/>
