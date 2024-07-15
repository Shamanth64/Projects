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
	<title>Payment History</title>
</head>

<body>
    <!-- Header -->
    <c:import url="components/incl/header.jsp"/>

	<div class="container my-4">
	    <!-- Card Body -->
	    <div class="card transaction-history shadow">
	        <div class="card-header">
	            <i class="fas fa-credit-card me-2" aria-hidden="true"></i>Transaction History
	        </div>
	        <div class="card-body">
                <c:if test="${requestScope.transaction_history != null}">
                <table class="table text-center table-striped">
                    <tr>
                        <th>Transaction ID</th>
                        <th>Transaction Type</th>
                        <th>Amount</th>
                        <th>Source</th>
                        <th>Status</th>
                        <th>Reason Code</th>
                        <th>Created At</th>
                    </tr>
                    <c:forEach items="${requestScope.transaction_history}" var="transactionHistory">
                        <tr>
                            <td>${transactionHistory.transaction_id}</td>
                            <td>${transactionHistory.transaction_type}</td>
                            <td>${transactionHistory.amount}</td>
                            <td>${transactionHistory.source}</td>
                            <td>${transactionHistory.status}</td>
                            <td>${transactionHistory.reason_code}</td>
                            <td>${transactionHistory.created_at}</td>
                        </tr>
                    </c:forEach>
                </table>
                </c:if>
	        </div>
	    </div>
	    <!-- End of Card Body -->

	</div>

    <!-- Footer -->
    <c:import url="components/incl/footer.jsp"/>