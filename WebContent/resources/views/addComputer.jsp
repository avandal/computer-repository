<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.excilys.computer_database.dto.ComputerDTO"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href=<c:url value = "/resources/css/bootstrap.min.css" />
	rel="stylesheet" media="screen">
<link href=<c:url value = "/resources/css/font-awesome.css" />
	rel="stylesheet" media="screen">
<link href=<c:url value = "/resources/css/main.css" /> rel="stylesheet"
	media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"><spring:message code="title" /></a>
		</div>
		<div id="lang">
			<a href="#" onclick="changeLanguage('en')"><img class="lang_flag" src="<c:url value="/resources/assets/en-flag.png" />"/></a>
			<a href="#" onclick="changeLanguage('fr')"><img class="lang_flag" src="<c:url value="/resources/assets/fr-flag.png" />"/></a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1><spring:message code="add_computer.title" /></h1>
					<form action="addComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName"><spring:message code="add_computer.name" /></label> 
								<input
									type="text" name="computerName" class="form-control"
									id="computerName" placeholder="<spring:message code="add_computer.name" />"
									value="${computerName}" required>
								<c:choose>
									<c:when test = "${not empty errorName}">
								<div id="errorName" class="alert alert-danger">
									${errorName}
								</div>
									</c:when>
									<c:otherwise>
								<div id="errorName" class="alert alert-danger" style="display: none">
								</div>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message code="add_computer.intro" /> <br />
									<small class="text-muted">(<spring:message code="add_computer.format.please" /> 
										<i><spring:message code="add_computer.format.slash" /></i> <spring:message code="or" /> 
										<i><spring:message code="add_computer.format.dash" /></i>)
									</small>
								</label>
								<input type="text" name="introduced" class="form-control"
									id="introduced" placeholder="<spring:message code="add_computer.intro" />"
									value="${introduced}">
								<c:choose>
									<c:when test = "${not empty errorIntroduced}">
								<div id="errorIntroduced" class="alert alert-danger">
									${errorIntroduced}
								</div>
									</c:when>
									<c:otherwise>
								<div id="errorIntroduced" class="alert alert-danger" style="display: none">
								</div>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message code="add_computer.disc" /> <br />
									<small class="text-muted">(<spring:message code="add_computer.format.please" /> 
										<i><spring:message code="add_computer.format.slash" /></i> <spring:message code="or" /> 
										<i><spring:message code="add_computer.format.dash" /></i>)
									</small>
								</label>
								<input type="text" name="discontinued" class="form-control"
									id="discontinued" placeholder="<spring:message code="add_computer.disc" />"
									value="${discontinued}">
								<c:choose>
									<c:when test = "${not empty errorDiscontinued}">
								<div id="errorDiscontinued" class="alert alert-danger">
									${errorDiscontinued}
								</div>
									</c:when>
									<c:otherwise>
								<div id="errorDiscontinued" class="alert alert-danger" style="display: none">
								</div>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="form-group">
									${companyId}
								<label for="companyId"><spring:message code="add_computer.comp" /></label>
								<select
									class="form-control" name="companyId" id="companyId">
									<c:forEach items="${companyList}" var="company">
										<c:choose>
										<c:when test="${companyId == null || companyId eq '0'}">
											<option value="${company.getId()}" 
												<c:if test="${company.getId() eq '0'}">selected="selected"</c:if>
											>${company.getName()}</option>
										</c:when>
										<c:otherwise>
											<option value="${company.getId()}" 
												<c:if test="${company.getId() eq companyId}">selected="selected"</c:if>
											>${company.getName()}</option>
										</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
								<c:choose>
									<c:when test = "${not empty errorCompany}">
								<div id="errorCompany" class="alert alert-danger">
									${errorCompany}
								</div>
									</c:when>
									<c:otherwise>
								<div id="errorCompany" class="alert alert-danger" style="display: none">
								</div>
									</c:otherwise>
								</c:choose>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<spring:message code="add_computer.add" />" class="btn btn-primary" id="add">
							<spring:message code="or" /> <a href=<c:url value = "dashboard" /> class="btn btn-default">
							<spring:message code="add_computer.cancel" /></a>
							<c:choose>
								<c:when test="${status.equals('void')}"></c:when>
								<c:when test="${status.equals('success')}">Computer successfully created!</c:when>
								<c:when test="${status.equals('failed')}">Failed creating the computer!</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/validator.js"></script>
	<script src="resources/js/lang.js"></script>
</body>
</html>