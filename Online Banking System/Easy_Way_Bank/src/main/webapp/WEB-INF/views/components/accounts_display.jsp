<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form"  uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- Container -->
	<div class="container d-flex">
		<!-- Accounts Button -->
		<button id="add-account-btn" class="btn btn-lg" type="button" data-bs-toggle="offcanvas"
			data-bs-target="#offcanvasRight" aria-controls="offcanvasRight">
			<i class="fa fa-credit-card"></i> Add new Account
		</button>
		<!-- End of Accounts Button -->

		<!-- Transact Button -->
		<button id="transact-btn" class="btn btn-lg ms-auto" type="button" data-bs-toggle="offcanvas"
			data-bs-target="#offcanvasExample" aria-controls="offcanvasExample">
			<i class="fa fa-wallet"></i> Transact
		</button>
		<!-- End of Transact Button -->
	</div>
	<!-- End of Container -->

	<!-- Container -->
	<div class="container d-flex py-3">
		<h2 class="me-auto">Total Accounts Balance:</h2>
		<h2 class="ms-auto">
		    <c:if test="${requestScope.totalBalance != null}">
		        <c:out value="${totalBalance}"/>
		    </c:if>
		</h2>
	</div>
	<!-- end of Container-->

	<!-- Container -->
	<div class="container">
		<!-- Accordian Menu-->
		<c:if test="${requestScope.userAccounts != null}">
		    <c:forEach items="${requestScope.userAccounts}" var="account">
		       <div class="accordion accordian-flush" id="accordionExample">
               			<div class="accordion-item">
               				<h2 class="accordion-header" id="headingOne">
               					<button class="accordion-button" type="button" data-bs-toggle="collapse"
               						data-bs-target="#flush-${account.account_id}" aria-expanded="true" aria-controls="collapseOne">
               						${account.account_name}
               					</button>
               				</h2>
               				<div id="flush-${account.account_id}" class="accordion-collapse collapse show" aria-labelledby="headingOne"
               					data-bs-parent="#accordionExample">
               					<div class="accordion-body">
                                    <ul class="list-group list-group-flush">
										<li class="list-group-item d-flex">Account Name<span class="ms-auto"><b>${account.account_name}</b></span></li>
										<li class="list-group-item d-flex">Account Number<span class="ms-auto"><b>${account.account_number}</b></span></li>
										<li class="list-group-item d-flex">Account Type<span class="ms-auto"><b>${account.account_type}</b></span></li>
										<li class="list-group-item d-flex">Account Balance<span class="ms-auto"><b>${account.balance}</b></span></li>
										<li class="list-group-item d-flex">Created At<span class="ms-auto"><b>${account.created_at}</b></span></li>
									</ul>
               					</div>
               				</div>
               			</div>
               		</div>
		    </c:forEach>
		</c:if>
		<!-- End of Accordian Menu-->
	</div>
	<!-- End of Container -->