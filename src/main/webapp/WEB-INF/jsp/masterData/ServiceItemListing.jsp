<!DOCTYPE html>
<html lang="en">
   <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <title>Service Items</title>
      <%@include file="../includes/HeadScript.jsp"%>
      <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
      <!-- DataTables -->
      <link rel="stylesheet" href="${contextPath}/resources/dist/plugins/datatables/css/dataTables.bootstrap.min.css">
      <!-- bootstrap-switch -->
      <link href="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/css/bootstrap4-toggle.min.css" rel="stylesheet">
      <!-- SweetAlert2 -->
      <link rel="stylesheet" href="${contextPath}/resources/dist/plugins/sweetalert2-theme-bootstrap-4/bootstrap-4.min.css">
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
               <h1>Service Items</h1>
				<a href="${contextPath}/master-data/service-item-details">
				<button type="button" class="btn btn-outline-primary" style="padding: 7px 5px;position: absolute;top: 11px;right: 10px;"><i class="fa fa-plus"></i>&nbsp;ADD&nbsp;</button>
				</a>       
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
					<form action="" id="searchForm">
						<div class="row">
							<div class="col-lg-2">
								<fieldset class="form-group">
									<input type="text" id="searchText" class="form-control" placeholder="Search Item Name">
								</fieldset>
							</div>
							<div class="col-lg-8"></div>
							<div class="col-lg-2">
								<button type="button" class="btn btn-success waves-effect waves-light btn-block refreshTable">Search</button>
							</div>
						</div>
					</form>
                     <div class="table-responsive">
                        <table id="service-item-table" class="table table-bordered table-striped">
                           <thead>
								<tr>
									<th>Item Name</th>
									<th>Diet Type</th>	
									<th>Services</th>
									<th>Calorie</th>
									<th>Protein</th>								
									<th>Action</th>									
								</tr>
                           </thead>
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
      <script type="text/javascript">
     	 $("#Masters").addClass("active");
      </script>  
      <!-- DataTable --> 
      <script src="${contextPath}/resources/dist/plugins/datatables/jquery.dataTables.min.js"></script> 
      <script src="${contextPath}/resources/dist/plugins/datatables/dataTables.bootstrap.min.js"></script> 
      <!-- bootstrap-switch --> 
      <script src="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/js/bootstrap4-toggle.min.js"></script>
      <!-- SweetAlert2 -->
      <script src="${contextPath}/resources/dist/plugins/sweetalert2/sweetalert2.min.js"></script>	
      
      <script src="${contextPath}/resources/dist/plugins/moment/min/moment.min.js"></script>
      <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
      <script>
      $(document).ready(function() {
		    var serviceItemTable = $('#service-item-table').DataTable({
		        "ajax": {
		            'contentType': 'application/json',
		            'url': contextPath + "/master-data/service-item-listing-data",
		            'method': "POST",
		            'data': function(d) {
	           	         return JSON.stringify($.extend( {}, d, { 
				          "searchText": getValue("searchText")
				         }))
		            },
		            'dataSrc': function(json) {
		                $("#Service-Item-Count").text(json.count);
		                json.draw = json.data.draw;
		                json.recordsTotal = json.count;
		                json.recordsFiltered = json.data.recordsFiltered;
		                return json.data.data;
		            }
		        },
		        "columns": [{
		            "data": "itemName"
	            }, {
		            "data": "dietTypeString",
		            "orderable": false,
		            "searchable": false
		        }, {
		            "data": "serviceMasterString",
		            "orderable": false,
		            "searchable": false
		        }, {
		            "data": "calories",
		            "searchable": false
		        }, {
		            "data": "protein",
		            "searchable": false
		        }, {
        			"orderable": false,
        			"searchable": false,
        			"data": "",
        			"render": function(data, type, row) {
        				var action = '<i class="fa fa-edit fa-lg" title="Edit"></i>';
            				action += '&nbsp;&nbsp;<i class="fa fa-trash-o fa-lg" title="Delete"></i>';
        				return action;
        			}
        		}],
		        'paging': true,
		        'ordering': true,
		        "order": [
		            [0, 'asc']
		        ],		        
		        'info': true,
		        'autoWidth': false,
		        "serverSide": true,
		        "lengthMenu": [25, 50, 100, 200],
		        "pageLength": 25
		    });
		    
		    $('#adhoc-item-table_filter').html("");
		    
		    function getCheckboxValue(id) {
		        return $('#' + id).is(":checked");
		    }

		    function getValue(id) {
		    	var value = $('#' + id).val();
		        return (value != "null" && value != "undefined"  && value != undefined) ? value : "";
		    }
		    
		    $('#service-item-table').on('click', 'tbody .fa-edit', function() {
		        var data_row = serviceItemTable.row($(this).closest('tr')).data();
		        window.location = contextPath + "/master-data/service-item-details?serviceItemsId=" + data_row["serviceItemsId"];
		    })

        	$('#service-item-table').on('click', 'tbody .fa-trash-o', function(e) {
		        var data_row = serviceItemTable.row($(this).closest('tr')).data();
        		var serviceItemsId = data_row["serviceItemsId"];
        		var title_Text = "Deleted Service Item cannot be retrieved. Are you sure you want to delete the Service Item? <br>" + data_row["itemName"];
        		var confirmButtonText_Text = "Yes, delete it!";
        		Swal.fire({
        			title: title_Text,	
        			icon: 'warning',
        			showCancelButton: true,
        			confirmButtonColor: '#3085d6',
        			cancelButtonColor: '#d33',
        			confirmButtonText: confirmButtonText_Text
        		}).then((result) => {
        			if (result.isConfirmed) {
        				var formData = "serviceItemsId=" + serviceItemsId;
        				$.ajax({
        					url: contextPath + "/master-data/delete-service-item",
        					type: "DELETE",
        					data: formData,
        					success: function(data) {
        						if (data == "Service Item has been deleted") {
            						Swal.fire({
            							icon: 'success',
            							title: "Service Item has been deleted",
            							showConfirmButton: false,
            							timer: 1500
            						}).then((result) => {
            							serviceItemTable.ajax.reload();
            						})
        						} else {
            						Swal.fire({
            							icon: 'warning',
            							title: "The Service Item can not be deleted",
            							showConfirmButton: false,
            							timer: 1500
            						})
        						}        						
        					},
        					error: function() {
        						Swal.fire({
        							icon: 'warning',
        							title: "Something went wrong",
        							showConfirmButton: false,
        							timer: 1500
        						})
        					}
        				});

        			}
        		});
        	});

		    $(".refreshTable").bind("click", function() {
		    	serviceItemTable.ajax.reload();
		    });
		    
		    function refreshServiceItemTable() {
		    	serviceItemTable.ajax.reload();
		    }
		    window.setInterval(refreshServiceItemTable, setIntervalTime); 
        });
      </script>  
   </body>
</html>