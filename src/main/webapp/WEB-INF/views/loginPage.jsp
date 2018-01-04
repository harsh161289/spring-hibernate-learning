<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>Bank Guarantee Management System</title>
		<meta charset="utf-8">
		<link href="/BGMS/resource/css/index-page-style.css" rel='stylesheet' type='text/css' />
		<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
		<!--webfonts-->
		<link href='http://fonts.googleapis.com/css?family=Open+Sans:600italic,400,300,600,700' rel='stylesheet' type='text/css'>
		<!--//webfonts-->
		<style type="text/css">
		.error {
		    background-color: #f2dede;
		    border: 1px solid #ebccd1;
		    border-radius: 4px;
		    color: #a94442;
		    font-size: 14px;
		    margin-bottom: 0;
		    padding: 7px;
		}

		.msg {
		    background-color: #f2dede;
		    border: 1px solid #ebccd1;
		    border-radius: 4px;
		    color: #a94442;
		    font-size: 14px;
		    margin-bottom: 0;
		    padding: 7px;
		}
		</style>
</head>
<body>
	 <!-----start-main---->	
	 <div class="main">
		<div class="login-form">
			<h1>Member Login</h1>
					<div class="head">
						<img src="/BGMS/resource/css/images/user.png" alt="not-visible"/>
					</div>
					<c:if test="${not empty result.error}">
						<div class="error">${result.error}</div>
					</c:if>
					<c:if test="${not empty result.msg}">
						<div class="msg">${result.msg}</div>
					</c:if>
				<form action="j_spring_security_check" method="POST">
						<input name="userName" type="text" class="text" value="USERNAME" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'USERNAME';}" />
						<input name="password" type="password" value="Password" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Password';}"/>
						<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token}" />
						<div class="submit">
							<input type="submit" value="LOGIN" >
					</div>	
					<p><a href="#">Forgot Password ?</a></p>
				</form>
			</div>
			<!--//End-login-form-->
			 <!-----start-copyright---->
   					<div class="copy-right">
						<p></p> 
					</div>
				<!-----//end-copyright---->
		</div>
			 <!-----//end-main---->
		 		
</body>
</html>