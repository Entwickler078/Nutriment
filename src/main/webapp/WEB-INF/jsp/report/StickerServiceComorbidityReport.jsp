<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>Sticker Service Comorbidity Report</title>
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
					<h1>Sticker Service Comorbidity Report</h1>
					<div style="padding: 7px 5px;position: absolute;top: 11px;right: 10px;">
						<button id="Export-PDF" class="btn btn-outline-primary btn-sm" type="button"><i class="fa fa-file-pdf-o"></i>&nbsp;&nbsp;&nbsp;&nbsp;PDF</button>
					</div>  					
				</div>
				<!-- Main content -->
				<div class="content">
					<div class="card">
						<div class="card-body">
							<div class="row">
								<div class="col-sm-4">
									<table class="table-sm">
										<tr>
											<td width="28%">Date Selection</td>
											<td width="2%">:</td>
											<td width="70%">${dateSelection}</td>
										</tr>	
									</table>
								</div>
								<div class="col-sm-4">
									<table class="table-sm">
										<tr>
											<td width="28%">Service</td>
											<td width="2%">:</td>
											<td width="70%">${serviceMaster.service}</td>
										</tr>	
									</table>
								</div>
								<div class="col-sm-4">
									<table class="table-sm">
										<tr>
											<td width="28%">Item Name</td>
											<td width="2%">:</td>
											<td width="70%">${itemName}</td>
										</tr>	
									</table>
								</div>																
							</div>						
							<div class="table-responsive">
								<table id="" class="table table-bordered table-striped">
									<thead>
										<tr>
											<th>Comorbidity</th>
											<th>Qty</th>
											<th>Diet Instruction</th>
											<th>Nursing Instruction</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${stickerServiceComorbidityReportList}" var="stickerServiceComorbidityReport" varStatus="loop">
											<tr>
												<td>${stickerServiceComorbidityReport.comorbidityValue}</td>
												<td>${stickerServiceComorbidityReport.total}</td>
												<td>
												<c:forEach items="${stickerServiceComorbidityReport.dietInstructions}" var="dietInstructions" varStatus="loop1">
													${loop1.index + 1}. ${dietInstructions} </br>
												</c:forEach>
												</td>
												<td>
												<c:forEach items="${stickerServiceComorbidityReport.nursingInstructions}" var="nursingInstructions" varStatus="loop2">
													${loop2.index + 1}.  ${nursingInstructions} </br>
												</c:forEach>
												</td>												
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
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
		<!-- DataTable --> 
		<script src="${contextPath}/resources/dist/plugins/datatables/jquery.dataTables.min.js"></script> 
		<script src="${contextPath}/resources/dist/plugins/datatables/dataTables.bootstrap.min.js"></script> 
				
		<script src="${contextPath}/resources/dist/plugins/jquery-validation-1.19.3/dist/jquery.validate.js"></script>
		<script src="${contextPath}/resources/dist/plugins/moment/min/moment.min.js"></script>
		<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>

        <!-- SweetAlert2 -->
        <script src="${contextPath}/resources/dist/plugins/sweetalert2/sweetalert2.min.js"></script>			 
		<script type="text/javascript">
		var dateFormat = "DD/MM/YYYY";
		function Validation() {
		}
		$(document).ready(function() {
			$("#Stickers").addClass("active");
			
		    $("#Export-PDF").bind("click", function() {
		    	exportFile("PDF");
		    });
		    
		    function exportFile(type) {
		    	var form = document.createElement("form");

		    	var dateSelectionElement = document.createElement("input");
		    	var serviceMasterIdElement = document.createElement("input");
		    	var itemNameElement = document.createElement("input");
		    	var typeElement = document.createElement("input");

		    	dateSelectionElement.name = "dateSelection";
		    	serviceMasterIdElement.name = "serviceMasterId";
		    	itemNameElement.name = "itemName";
		    	typeElement.name = "type";

		    	dateSelectionElement.value = "${dateSelection}";
		    	serviceMasterIdElement.value = "${serviceMaster.serviceMasterId}";
		    	itemNameElement.value = "${itemName}";
		    	typeElement.value = type;

		    	form.appendChild(dateSelectionElement);
		    	form.appendChild(serviceMasterIdElement);
		    	form.appendChild(itemNameElement);
		    	form.appendChild(typeElement);
		    	document.body.appendChild(form);

		    	form.method = "POST";
		    	if (type == "PDF") {
		    		form.action = contextPath + "/reports/sticker-service-comorbidity-report-export";	
		    	}
		    	form.style.visibility = 'hidden';
		    	form.submit();
		    	document.body.removeChild(form);
		    }
		});
		</script>    	
	</body>
</html>