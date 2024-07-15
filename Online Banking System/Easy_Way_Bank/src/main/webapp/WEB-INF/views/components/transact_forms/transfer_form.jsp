<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form"  uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

            <!-- Card : Transfer Card -->
			<div class="card transfer-card">
				<!-- Card Body -->
				<div class="card-body">
				<!-- Transfer form -->
				<form action="/transact/transfer" method="post">
				<!-- Form Group -->
                                <div class="form-group">
                                    <label for="">Select account to transfer from</label>
                                	<!-- Select Account Option -->
                                	<select name="transfer_from" class="form-control">
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
                                <div class="form-group">
                                    <label for="">Select account to transfer to</label>
                                	<!-- Select Account Option -->
                                	<select name="transfer_to" class="form-control">
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
                						<label for="">Enter transfer Account</label>
                						<input type="text" name="payment_amount" class="form-control"
                							placeholder="Enter transfer Amount">
                					</div>
                					<!-- End of Form Group-->

                					<!-- Form Group -->
                					<div class="form-group mb-2">
                						<button id="" class="btn btn-md transact-btn">Transfer</button>
                					</div>
                					<!-- End of Form Group-->
				</form>
				<!-- End of Transfer Form -->
				</div>
				<!-- End of Card Body -->
			</div>
			<!-- End of Card : Transfer Card -->