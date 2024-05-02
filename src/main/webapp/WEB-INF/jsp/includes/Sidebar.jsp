<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
	<!-- sidebar -->
	<div class="sidebar">
		<!-- Sidebar user panel -->
		<div class="media-sidebar">
		</div>
		<!-- sidebar menu -->
		<ul class="sidebar-menu" data-widget="tree">
			<li class="" id="Dashboard"> <a href="${contextPath}/"> <i class="fa fa-tachometer"></i> <span>Dashboard</span> </a></li>
			<c:if test="${isAdmin}">
				<li class="" id="Users"><a href="${contextPath}/users"> <i
					class="fa fa-users"></i> <span>Users</span>
					</a>
				</li>
			</c:if>
			<li class="" id="Patients">
				<a href="${contextPath}/diet/patients"> <i class="fa fa-user-plus"></i> <span>Patients</span> </a>
			</li>
			<li class="" id="DietPlan">
				<a href="${contextPath}/diet/diet-plan"> <i class="fa fa-bowl-food"></i> <span>Diet Plan</span></a>
			</li>
			<c:if test="${isDietitian || isKitchen || isAdmin}">
				<li class="" id="AdHocOder">
					<a href="${contextPath}/diet/adhoc-order-listing"> <i class="fa fa-cart-plus"></i> <span>AdHoc Oder</span></a>
				</li>		
			</c:if>
			<c:if test="${isDietitian || isKitchen || isAdmin}">
				<li class="" id="Stickers">
					<a href="${contextPath}/diet/stickers"> <i class="fa fa-tags"></i> <span>Stickers</span></a>
				</li>
			</c:if>
			<c:if test="${isDietitian || isAdmin}">
				<li class="treeview menu-open" id="Reports"> <a href="#"> <i class="fa fa-file"></i> <span>Reports</span> <span class="pull-right-container"> <i class="fa fa-angle-left pull-right"></i> </span> </a>
		          <ul class="treeview-menu" style="display: block;">
		            <li><a href="${contextPath}/reports/patient-service-report"><i class="fa fa-angle-right"></i> Diet MIS report</a></li>
		          </ul>
				</li>	
			</c:if>
			<c:if test="${isAdmin}">
				<li class="treeview menu-open" id="Masters"> <a href="#"> <i class="fa fa-database"></i> <span>Masters</span> <span class="pull-right-container"> <i class="fa fa-angle-left pull-right"></i> </span> </a>
		          <ul class="treeview-menu" style="display: block;">
		            <li><a href="${contextPath}/master-data/adhoc-item-listing"><i class="fa fa-angle-right"></i> AdHoc Items</a></li>
		            <li><a href="${contextPath}/master-data/service-item-listing"><i class="fa fa-angle-right"></i> Service Items</a></li>
		          </ul>
				</li>	
			</c:if>										
		</ul>
	</div>
	<!-- /.sidebar --> 
</aside>