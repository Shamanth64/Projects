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

	<div class="container">
	    <!-- Card Body -->
	    <div class="card">
	        <div class="card-header">
                <i class="fas fa-credit-card me-2" aria-hidden="true"></i>Payment History
            </div>
	        <div class="card-body">
                <c:if test="${requestScope.payment_history != null}">
                <table class="table text-center table-striped">
                    <tr>
                        <th>Record Number</th>
                        <th>Beneficiary</th>
                        <th>Beneficiary Account Number</th>
                        <th>Amount</th>
                        <th>Status</th>
                        <th>References</th>
                        <th>Reason Code</th>
                        <th>Created At</th>
                    </tr>
                    <c:forEach items="${requestScope.payment_history}" var="payments">
                        <tr>
                            <td>${payments.payment_id}</td>
                            <td>${payments.beneficiary}</td>
                            <td>${payments.beneficiary_account_no}</td>
                            <td>${payments.amount}</td>
                            <td>${payments.status}</td>
                            <td>${payments.reference_no}</td>
                            <td>${payments.reason_code}</td>
                            <td>${payments.created_at}</td>
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