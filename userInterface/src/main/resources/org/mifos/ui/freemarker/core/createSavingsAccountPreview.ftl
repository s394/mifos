[#ftl]
[#--
* Copyright (c) 2005-2010 Grameen Foundation USA
*  All rights reserved.
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
*  implied. See the License for the specific language governing
*  permissions and limitations under the License.
*
*  See also http://www.apache.org/licenses/LICENSE-2.0.html for an
*  explanation of the license and how it is applied.
--]

[@layout.webflow currentState="createSavingsAccount.flowState.enterAccountInfo" 
                 states=["createSavingsAccount.flowState.selectCustomer", 
                 		 "createSavingsAccount.flowState.enterAccountInfo", 
                 		 "createSavingsAccount.flowState.reviewAndSubmit"]] 

<h1>Create Savings account - <b>Preview Savings account information</b></h1>
<p>Review the information below. Click Submit if you are satisfied or click Edit to make changes. Click Cancel to return to Clients & Accounts without submitting information.</p>
<br/>

<b>Account Owner</b>: Test Customer1188
<br/>

<p><b>Savings product summary</b></p>
<div class="summary">
	<div class="row">
		<div class="attribute">[@spring.message "createSavingsAccount.enterAccountInfo.productSummary.description"/]</div>
		<div class="value">${product.savingsProductDetails.productDetails.description}</div>
	</div>
	<div class="row">
		<div class="attribute">[@spring.message "createSavingsAccount.enterAccountInfo.productSummary.typeOfDeposits"/]:</div>
		<div class="value">Voluntary</div>
	</div>
	<div class="row">
		<div class="attribute">[@spring.message "createSavingsAccount.enterAccountInfo.productSummary.maxWithdrawalAmount"/]:</div>
		<div class="value">TODO: localize ${product.savingsProductDetails.maxWithdrawal}</div>
	</div>
	<div class="row">
		<div class="attribute">[@spring.message "createSavingsAccount.enterAccountInfo.productSummary.balanceForInterestCalculation"/]:</div>
		<div class="value">TODO interest calculation: ${product.savingsProductDetails.interestCalculationType}</div>
	</div>
	<div class="row">
		<div class="attribute">[@spring.message "createSavingsAccount.enterAccountInfo.productSummary.periodForInterestCalculation"/]:</div>
		<div class="value">30 day(s)</div>
	</div>
	<div class="row">
		<div class="attribute">[@spring.message "createSavingsAccount.enterAccountInfo.productSummary.interestPostingFrequency"/]:</div>
		<div class="value">1 month(s)</div>
	</div>
	<div class="row">
		<div class="attribute">[@spring.message "createSavingsAccount.enterAccountInfo.productSummary.minBalanceForInterestCalculation"/]:</div>
		<div class="value">50.0</div>
	</div>
	<div class="row">
		<div class="attribute">[@spring.message "createSavingsAccount.enterAccountInfo.productSummary.interestRate"/]:</div>
		<div class="value">25.0 %</div>
	</div>
</div>
<br/>

<p><b>Savings account details</b></p>
<div class="summary">
		<div class="attribute">Recommended amount for deposit:</div>
		<div class="value">12345</div>
</div>

<form action="${flowExecutionUrl}" method="post">
	<div class="row">
		<input type="submit" class="submit" value="Edit" name="_eventId_edit" />
		<input type="submit" class="submit" value="Save for later" name="_eventId_saveForLater" />
		<input type="submit" class="submit" value="Save for approval" name="_eventId_saveForApproval" />
		<input type="submit" class="cancel" value="Cancel" name="_eventId_cancel" />
	</div>
</form>

[/@layout.webflow]
