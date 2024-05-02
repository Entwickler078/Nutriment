<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="user" property="principal"></sec:authentication>
<sec:authorize access="hasRole('ROLE_NURSING')" var="isNursing" />
<sec:authorize access="hasRole('ROLE_DIETITIAN')" var="isDietitian" />
<sec:authorize access="hasRole('ROLE_KITCHEN')" var="isKitchen" />
<sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin" />
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!-- Tell the browser to be responsive to screen width -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- v4.0.0 -->
<link rel="stylesheet" href="${contextPath}/resources/dist/bootstrap/css/bootstrap.min.css">
<!-- Favicon -->
<%-- <link rel="icon" type="image/png" sizes="16x16" href="${contextPath}/resources/dist/img/favicon.ico"> --%>
<!-- Google Font -->
<link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700" rel="stylesheet">
<!-- Theme style -->
<link rel="stylesheet" href="${contextPath}/resources/dist/css/style.css">
<link rel="stylesheet" href="${contextPath}/resources/dist/css/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${contextPath}/resources/dist/plugins/fontawesome-free-6.1.1-web/css/all.min.css">
<link rel="stylesheet" href="${contextPath}/resources/dist/css/et-line-font/et-line-font.css">
<link rel="stylesheet" href="${contextPath}/resources/dist/css/themify-icons/themify-icons.css">
<link rel="stylesheet" href="${contextPath}/resources/dist/css/simple-lineicon/simple-line-icons.css">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->
<style type="text/css">
	@media (max-width: 768px){
	.media-sidebar {
	padding: 10px;
	border-bottom: solid 1px #ebebeb;
	}
	}
</style>
<script type="text/javascript">
	var contextPath = '<c:out value="${contextPath}"/>';
	var isNursing = ${isNursing};
	var isDietitian = ${isDietitian};
	var isKitchen = ${isKitchen};
	var isAdmin = ${isAdmin};
	var	dietTypeSolid = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11'];
	var	dietTypeLiquidOralTF = ['12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30'] ;
	var extraLiquidServices = ['s_10amExtraLiq','s_2pmExtraLiq','s_6pmExtraLiq','s_10pmExtraLiq'];
	var dietSubTypeServices = ['s_6am','s_7am','s_8am','s_9am','s_10am','s_11am','s_12pm','s_1pm','s_2pm','s_3pm','s_4pm','s_5pm','s_6pm','s_7pm','s_8pm','s_9pm','s_10pm','s_11pm','s_12am'];
	var alphanumericWithSpeChar = new RegExp('^[a-zA-Z0-9.,\-\/"& ]*$');
	var allowsChars = '.,-/"&'
	var setIntervalTime = 1000 * 60 * 3;
</script>