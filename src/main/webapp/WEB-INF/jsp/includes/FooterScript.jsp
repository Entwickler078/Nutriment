<!-- jQuery 3 --> 
<script src="${contextPath}/resources/dist/js/jquery.min.js"></script> 
<!-- v4.0.0-alpha.6 --> 
<%-- <script src="${contextPath}/resources/dist/bootstrap/js/bootstrap.min.js"></script>  --%>
<!-- template --> 
<script src="${contextPath}/resources/dist/js/adminkit.js"></script> 
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
<!-- Morris JavaScript --> 
<%-- <script src="${contextPath}/resources/dist/plugins/raphael/raphael-min.js"></script>  --%>
<%-- <script src="${contextPath}/resources/dist/plugins/morris/morris.js"></script>  --%>
<%-- <script src="${contextPath}/resources/dist/plugins/functions/dashboard1.js"></script>  --%>
<!-- Chart Peity JavaScript --> 
<%-- <script src="${contextPath}/resources/dist/plugins/peity/jquery.peity.min.js"></script>  --%>
<%-- <script src="${contextPath}/resources/dist/plugins/functions/jquery.peity.init.js"></script> --%>
<script type="text/javascript">
	$("body").addClass("sidebar-collapse");
	$("ul.sidebar-menu > li").each(function() {
		$(this).removeClass("active");
	});
	
    function updateNotificationsCount() {
		$.ajax({
			url: contextPath + "/notifications-count",
			type: "GET",
			success: function(data) {
				if (data && data > 0){
					$("#notifications-heartbit").show();
					$("#Notifications-Count").text(data);
				} else {
					$("#notifications-heartbit").hide();
					$("#Notifications-Count").text('');
				}
			},
			error: function() {
			}
		});
      }
    
    updateNotificationsCount();
    window.setInterval(updateNotificationsCount, setIntervalTime);
</script>