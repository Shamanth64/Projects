<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form"  uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

            <!-- Card : Payment Card -->
			<div class="card payment-card">
				<!-- Card Body -->
				<div class="card-body">
					<!-- Payment Form -->
					<form action="/transact/payment" method="post">
                    <!-- Form Group -->
					<div class="form-group mb-2">
						<label for=""> Account holder / Beneficiary </label>
						<input type="text" name="beneficiary" class="form-control"
							placeholder="Enter Account holder / Beneficiary name">
					</div>
					<!-- End of Form Group-->

					<!-- Form Group -->
					<div class="form-group mb-2">
						<label for="">Beneficiary Account Number</label>
						<input type="text" name="account_number" class="form-control"
							placeholder="Enter Beneficiary account number">
					</div>
					<!-- End of Form Group-->

                    <!-- Form Group -->
                                <div class="form-group">
                                    <label for="">Select account to transfer from</label>
                                	<!-- Select Account Option -->
                                	<select name="account_id" class="form-control">
                                        <option value="">-- Select Account --</option>
                                		<c:if test="${userAccounts != null}">
                                	        <c:forEach items="${userAccounts}" var ="selectAccount">
                                		        <option value="${selectAccount.account_id}">${selectAccount.account_name}</option>
                                			</c:forEach>
                                		</c:if>
                                	</select>
                                <!-- End of Select Account Option -->
                                </div>
                                <!--End of Form group -->

					<!-- Form Group -->
					<div class="form-group mb-2">
						<label for=""> Reference </label>
						<input type="text" name="reference" class="form-control" placeholder="Enter Referene">
					</div>
					<!-- End of Foem Group-->

					<!-- Form Group -->
					<div class="form-group mb-2">
						<label for="">Enter Payment Account</label>
						<input type="text" name="payment_amount" class="form-control"
							placeholder="Enter Payment Amount">
					</div>
					<!-- End of Form Group-->

					<!-- Form Group -->
					<div class="form-group mb-2">
						<button id="" class="btn btn-md transact-btn">Pay</button>
					</div>
					<!-- End of Form Group-->
					</form>
					<!-- End of Payment Form -->
				</div>
				<!-- End of Card Body -->
			</div>
			<!-- End of Card : Payment Card -->