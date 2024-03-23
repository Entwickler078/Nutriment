<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>Generate Stickers</title>
		<%@include file="../includes/HeadScript.jsp"%>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/css/bootstrap-select.css" />
		<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
		<!-- DataTables -->
		<link rel="stylesheet" href="${contextPath}/resources/dist/plugins/datatables/css/dataTables.bootstrap.min.css">
        <!-- SweetAlert2 -->
        <link rel="stylesheet" href="${contextPath}/resources/dist/plugins/sweetalert2-theme-bootstrap-4/bootstrap-4.min.css">				
		<style type="text/css">
			.table-sm>tbody>tr>td, .table>tfoot>tr>td {
			    border-top: 1px solid #f4f4f4;
			    border-bottom: 1px solid #f4f4f4;
			}
			.showUpdated {
				color: #0c5460 !important;
				background-color: #bee5eb !important;
			}				
		</style>		
	</head>
	<body class="skin-blue sidebar-mini">
		<div class="wrapper boxed-wrapper">
			<%@include file="../includes/Header.jsp"%>
			<%@include file="../includes/Sidebar.jsp"%>
			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper">
				<!-- Content Header (Page header) -->
				<div class="content-header sty-one">
					<h1>Generate Stickers</h1>
				</div>
				<!-- Main content -->
				<div class="content">
					<div class="card">
						<div class="card-body">
							<c:if test="${not empty patientId}">
								<%@include file="IncludePatientDetails.jsp"%>
								<Br>				
							</c:if>
							<form  action="${contextPath}/diet/generate-stickers" method="GET" onsubmit="return Validation();" id="stickersForm" target="_blank">
								<input type="hidden" name="patientId" value="${patientId}"/>
								<input type="hidden" id="dateSelectionStr" value="${dateSelection}"></input>
								<input type="hidden" id="serviceMasterIdStr" value="${serviceMasterId}"></input>
								<input type="hidden" name="type" id="type"></input>
								<div class="row">
									<div class="col-lg-2">
										<fieldset class="form-group">
											<label for="medicalComorbidities">Date</label><span class="text-danger">*</span>
											<input type="text" class="form-control daterange-single" id="dateSelection" name="dateSelection" placeholder="Date"></input>
										</fieldset>
									</div>	
									<c:if test="${empty patientId}">									
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="dietType">Diet Type</label><span class="text-danger">*</span>
											<select class="form-control selectpicker" id="dietType" name="dietType" data-size="10" onchange="dietTypeChange();">
												<option value="1">Diet Type Solid</option>
												<option value="2">Diet Type Liquid Oral/TF</option>
											</select>
										</fieldset>
									</div>
									</c:if>
									<div class="col-lg-4">
										<fieldset class="form-group">
											<label for="medicalComorbidities">Service Type</label><span class="text-danger">*</span>
											<select class="form-control selectpicker" id="serviceMasters" name="serviceMasterId" data-live-search="true" data-size="10" onchange="serviceMastersChange();">
												<c:forEach items="${serviceMasterList}" var="serviceMasters">
													<option class="serviceMasters_option" value="${serviceMasters.serviceMasterId}">${serviceMasters.service}</option>
												</c:forEach>
											</select>
										</fieldset>
									</div>
								</div>
								<button type="submit" class="btn btn-success waves-effect waves-light" onclick="changeAction('1')">Generate Stickers</button>
								<c:if test="${empty patientId}">
									<button type="submit" class="btn btn-success waves-effect waves-light" onclick="changeAction('2')">Generate Report</button>
									<button type="submit" class="btn btn-outline-primary" onclick="changeAction('3')"><i class="fa fa-file-pdf-o"></i>&nbsp;&nbsp;&nbsp;&nbsp;PDF</button>	
								</c:if>							
							</form>
						</div>
					</div>
					<c:if test="${reportGenerated eq true}">
						<div class="card">
							<div class="card-body">
								<div class="table-responsive">
									<table id="" class="table table-bordered table-striped">
										<thead>
											<tr>
												<th>Sr No</th>
												<th>Diet Type</th>
												<c:if test="${showItemName eq true}"><th>Diet Sub Type</th></c:if>
												<c:if test="${showItemName eq true}"><th>Item Name</th></c:if>
												<th>Total</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${not empty stickerServiceReportList}">
												<c:forEach items="${stickerServiceReportList}" var="stickerServiceReport" varStatus="loop">
													<tr>
														<td>${loop.index + 1}</td>
														<td>${stickerServiceReport.dietType}</td>
														<c:if test="${showItemName eq true}"><td>${stickerServiceReport.dietSubType}</td></c:if>
														<c:if test="${showItemName eq true}"><td>${stickerServiceReport.itemName}</td></c:if>
														<td><a href="${contextPath}/reports/sticker-service-comorbidity-report?dateSelection=${dateSelection}&serviceMasterId=${serviceMasterId}&itemName=${stickerServiceReport.itemName}" target="_blank">${stickerServiceReport.total}</a></td>
													</tr>
												</c:forEach>
											</c:if>
											<c:if test="${empty stickerServiceReportList}">
												<tr>
													<td colspan="${showItemName eq true ? '5' : '3'}">No Data Found</td>
												</tr>
											</c:if>											
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</c:if>
			</div>
				<!-- /.content --> 
			</div>
			<!-- /.content-wrapper -->
			<footer class="main-footer">
				*generated by Nutriment and date & time of printing
			</footer>			
			<%@include file="../includes/Footer.jsp"%>
		</div>
		<!-- ./wrapper --> 
		<%@include file="../includes/FooterScript.jsp"%>
		<script src="${contextPath}/resources/dist/plugins/jquery-validation-1.19.3/dist/jquery.validate.js"></script>
		<script src="${contextPath}/resources/dist/plugins/moment/min/moment.min.js"></script>
		<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>

        <!-- SweetAlert2 -->
        <script src="${contextPath}/resources/dist/plugins/sweetalert2/sweetalert2.min.js"></script>			  
		<script type="text/javascript">
		function dietTypeChange() {
			<c:if test="${empty patientId}">
				$("#serviceMasters").val("");
				var validOptions = $("#dietType").val() == "1" ? dietTypeSolid : dietTypeLiquidOralTF;
		        $(".serviceMasters_option").each(function() {
		            if (validOptions.includes($(this).val())) {
		                $(this).show();
		            } else {
		                $(this).hide();
		            }
		        });
	        </c:if>
	        $('.selectpicker').selectpicker('refresh');
		}		
		
		function Validation() {}
		
		function changeAction(value) {
			if (value == '1') {
				$("#stickersForm").attr("method", "GET");
	    		$("#stickersForm").attr("action", "${contextPath}/diet/generate-stickers");
	    		$("#stickersForm").attr("target", "_blank");
	    	} else if (value == '2') {
	    		$("#stickersForm").attr("method", "POST");
	    		$("#stickersForm").attr("action", "${contextPath}/reports/sticker-service-report");
	    		$("#stickersForm").attr("target", "");
	    	} else if (value == '3') {
	    		$("#stickersForm").attr("method", "POST");
	    		$("#stickersForm").attr("action", "${contextPath}/reports/sticker-service-report-export");
	    		$("#stickersForm").attr("target", "_blank");
	    		$("#type").val("PDF");
	    	} 
		}
		
	    $('#dateSelection').daterangepicker({
	        alwaysShowCalendars: true,
	        singleDatePicker: true,
	        locale: {
	            format: 'DD/MM/YYYY'
	        }
	    });

		$(document).ready(function() {
		    $("#Stickers").addClass("active");
		    $('.selectpicker').selectpicker(); 
		    
		    if ($('#dateSelectionStr').val() != "") {
		        $('#dateSelection').data('daterangepicker').setStartDate($('#dateSelectionStr').val());
		    }
		    if ($('#serviceMasterIdStr').val() != "") {
		    	if (dietTypeSolid.includes($('#serviceMasterIdStr').val())) {
		    		$("#dietType").val("1");
		    	} else if (dietTypeLiquidOralTF.includes($('#serviceMasterIdStr').val())) {
		    		$("#dietType").val("2");
		    	}
		    	dietTypeChange();
		        $('#serviceMasters').val($('#serviceMasterIdStr').val());
		    } else {
		    	dietTypeChange();
		    }
		    $('.selectpicker').selectpicker('refresh');
		    	
		    $("#stickersForm").validate({
		        // in 'rules' user have to specify all the constraints for respective fields
		        rules: {
		        	dateSelection: {
		                required: true
		            },
		        	serviceMasterId: {
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
		        	dateSelection: {
		                required: "Please Select Date"
		            },
		        	serviceMasterId: {
		                required: "Please Select Service Type"
		            }
		        }
		    });
		});
		</script>    	
	</body>
</html>