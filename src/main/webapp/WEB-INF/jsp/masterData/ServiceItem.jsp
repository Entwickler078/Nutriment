<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>Service Item</title>
		<%@include file="../includes/HeadScript.jsp"%>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
		<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
	</head>
	<body class="skin-blue sidebar-mini">
		<div class="wrapper boxed-wrapper">
			<%@include file="../includes/Header.jsp"%>
			<%@include file="../includes/Sidebar.jsp"%>
			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper">
				<!-- Content Header (Page header) -->
				<div class="content-header sty-one">
					<c:if test="${empty serviceItemsDto.serviceItemsId}">
						<h1>Add Service Item</h1>
					</c:if>
					<c:if test="${serviceItemsDto.serviceItemsId ge 0}">
						<h1>Edit Service Item</h1>
					</c:if>
				</div>
				<!-- Main content -->
				<div class="content">
					<div class="card">
						<c:if test="${not empty errorMsg}">					
							<div class="alert alert-danger alert-dismissible fade show" role="alert">
								${errorMsg}
								<button type="button" class="close" data-dismiss="alert" aria-label="Close">
									<span aria-hidden="true">×</span>
								</button>
							</div>
						</c:if>						
						<div class="card-body">
							<form:form method="POST" action="${contextPath}/master-data/service-item-details" modelAttribute="serviceItemsDto" onsubmit="return Validation();" id="serviceItemForm">
								<form:hidden path="serviceItemsId" id="serviceItemsId"/>
								<div class="row">
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="itemName">Item Name</label><span class="text-danger">*</span>
											<form:input cssClass="form-control" id="itemName" path="itemName" readonly="${serviceItemsDto.serviceItemsId ge 0}"></form:input>
										</fieldset>
									</div>
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="calories">Calories (kcal)- 5 ML</label><span class="text-danger">*</span>
											<form:input cssClass="form-control" id="calories" path="calories"></form:input>
										</fieldset>
									</div>
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="protein">Protein (gms)- 5 ML</label><span class="text-danger">*</span>
											<form:input cssClass="form-control" id="protein" path="protein"></form:input>
										</fieldset>
									</div>																		
								</div>
								<div class="row">
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="dietTypesColumnNames">Diet Type</label><span class="text-danger">*</span>
											<select class="form-control selectpicker" id="dietTypesColumnNames" name="dietTypesColumnNames" onchange="dietTypeChange();" data-live-search="true" data-size="10" title="Please select"">
												<c:forEach items="${dietTypesColumnList}" var="dietTypesColumn">
													<option value="${dietTypesColumn.columnName}">${dietTypesColumn.value}</option>
												</c:forEach>
											</select>
										</fieldset>
									</div>
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="serviceMasterColumnNames">Services</label><span class="text-danger">*</span>
											<select class="form-control selectpicker" id="serviceMasterColumnNames" name="serviceMasterColumnNames" onchange="" multiple data-live-search="true" data-size="10" title="Please select">
												<c:forEach items="${serviceMasterList}" var="serviceMaster">
													<option class="serviceMasters_option" value="${serviceMaster.serviceItemsColumnName}">${serviceMaster.service}</option>
												</c:forEach>
											</select>
										</fieldset>
									</div>									
								</div>
								<button type="submit" class="submitBtn btn btn-success waves-effect waves-light" onclick="">Submit</button>
								<a href="${contextPath}/master-data/service-item-listing">
									<button type="button" class="btn btn-inverse waves-effect waves-light">Cancel</button>
								</a>	
							</form:form>
						</div>
					</div>
				</div>
				<!-- /.content --> 
			</div>
			<!-- /.content-wrapper -->
			<%@include file="../includes/Footer.jsp"%>
		</div>
		<!-- ./wrapper --> 
		<%@include file="../includes/FooterScript.jsp"%>
		<script src="${contextPath}/resources/dist/plugins/jquery-validation-1.19.3/dist/jquery.validate.js"></script>
		<script src="${contextPath}/resources/dist/plugins/moment/min/moment.min.js"></script>
		<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
		<script type="text/javascript">
		jQuery.validator.addMethod("alphanumericWithSpeCharValidator", function(value, element) {
		    $(element).val((this.elementValue(element).replace(/\s+/g, ' ')));
		    this.value = $(element).val();
		    if (alphanumericWithSpeChar.test(value)) {
		        return true;
		    } else {
		        return false;
		    };
		});

		jQuery.validator.addMethod("bigDecimalRegex", function(value, element) {
		    $(element).val((this.elementValue(element).replace(/\s+/g, ' ')));
		    this.value = $(element).val();
		    if (/^\d+\.\d{0,2}$/.test(value)) {
		        return true;
		    } else {
		        return false;
		    };
		});
		</script>  
		<script type="text/javascript">
		function dietTypeChange() {
			$("#serviceMasterColumnNames").val("");
			var validOptions = $("#dietTypesColumnNames").val() == "extraLiquid" ? extraLiquidServices : dietSubTypeServices;
	        $(".serviceMasters_option").each(function() {
	            if (validOptions.includes($(this).val())) {
	                $(this).show();
	            } else {
	                $(this).hide();
	            }
	        });
	        $('.selectpicker').selectpicker('refresh');
		}
		
		function Validation() {
			 $("#dietTypesColumnNames").attr("disabled", false);
			 $('#serviceMasterColumnNames').attr('disabled', false);
		}

		$(document).ready(function() {
			$("#Masters").addClass("active");
		    $('.selectpicker').selectpicker(); 
		    <c:if test ="${not empty serviceItemsDto.dietTypesColumnNames}">
		        $('#dietTypesColumnNames').selectpicker('val', "${serviceItemsDto.dietTypesColumnNames}"); 
		    </c:if> 
		    dietTypeChange();
		    <c:if test="${not empty serviceItemsDto.serviceMasterColumnNames}">
	       		$('#serviceMasterColumnNames').selectpicker('val', "${serviceItemsDto.serviceMasterColumnNames}".split(',')); 
	   		</c:if>
	   		<c:if test="${serviceItemsDto.serviceItemsId ge 0}">
				 $("#dietTypesColumnNames").attr("disabled", true);
				 $('#serviceMasterColumnNames').attr('disabled', true);	   		
	   		</c:if>
		   	$('.selectpicker').selectpicker('refresh');

		    $("#serviceItemForm").validate({
		        // in 'rules' user have to specify all the constraints for respective fields
		        rules: {
		        	itemName: {
		                required: true,
		                minlength: 2,
		                maxlength: 150,
		                alphanumericWithSpeCharValidator: true
		            },
		            calories: {
		                required: true,
		                bigDecimalRegex: true
		            },
		            protein: {
		                required: true,
		                bigDecimalRegex: true
		            },
		            dietTypesColumnNames: {
		                required: true
		            },
		            serviceMasterColumnNames: {
		                required: true
		            }
		        },
		        errorPlacement: function(error, element) {
		            if (element.is("select")) {
		                error.insertAfter(element.parent("div"));
		            } else {
		                error.insertAfter(element);
		            }
		        },
		        // in 'messages' user have to specify message as per rules
		        messages: {
		        	itemName: {
		                required: "Please enter Item Name",
		                minlength: "At least 2 characters required",
		                maxlength: "Max 150 characters allowed",
		                alphanumericWithSpeCharValidator: "Only Alphanumeric characters and " + allowsChars + " are allowed"
		            },
		            calories: {
		                required: "Please enter Calories",
		                bigDecimalRegex: "Please enter valid Calories"
		            },
		            protein: {
		                required: "Please enter Protein",
		                bigDecimalRegex: "Please enter valid Protein"
		            },
		            dietTypesColumnNames: {
		                required: "Please Select Diet Type",
		            },
		            serviceMasterColumnNames: {
		                required: "Please Select Services",
		            }
		        },
		        submitHandler: function(form) { // <- pass 'form' argument in
		            $(".submitBtn").attr("disabled", "disabled");
		            form.submit(); // <- use 'form' argument here.
		        }
		    });
		});
		</script>    	
	</body>
</html>