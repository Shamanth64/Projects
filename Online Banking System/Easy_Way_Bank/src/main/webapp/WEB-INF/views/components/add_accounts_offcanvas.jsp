<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form"  uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- Start of Accounts Offcanvas -->
	<div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRight" aria-labelledby="offcanvasRightLabel">
		<div class="offcanvas-header">
			<h5 class="offcanvas-title text-white" id="offcanvasRightLabel">Create / Add an Account</h5>
			<button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
		</div>
		<!-- Offcanvas : Accounts Body -->
		<div class="offcanvas-body">
			<!-- Card : Account Form Card -->
			<div class="card">
				<!-- Card Body -->
				<div class="card-body">
					<form action="/account/create_account" method="post" class="add-account-form">
						<!-- Form Group -->
						<div class="form-group mb-3">
							<label>Enter Account name</label>
							<input type="text" name="account_name" class="form-control" placeholder="Enter account name">
						</div>
						<!-- End of Form Group -->

						<!-- Form Group -->
						<div class="form-group mb-3">
							<label>Select Account type</label>
							<select name="account_type" class="form-control" id="">
								<option value="select_account">-- Select Account Type --</option>
								<option value="check">Check</option>
								<option value="savings">Savings</option>
								<option value="business">Business</option>
							</select>
						</div>
						<!-- End of Form Group -->

						<!-- Form Group -->
						<div class="form-group mb-3">
							<div class="form-group my-2">
								<button id="" class="btn btn-md transact-btn">Add Account</button>
							</div>
							<!-- End of Form Group-->
						</div>
						<!-- End of Form Group -->
					</form>
				</div>
				<!-- End of Card Body-->
			</div>
			<!-- End of Card : Account Form Card -->
		</div>
		<!-- End of Offcanvas : Accounts Body -->
	</div>
	<!-- End of Account Offcanvas -->

