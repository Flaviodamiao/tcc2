<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html lang="pt-br">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta name="view report" content="width=device-width, initial-scale=1">
	
	<title>Carros Online</title>
	
        <link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet"/>
        <link href="<c:url value='/resources/css/style.css'/>" rel="stylesheet"/>
	<style type="text/css">
		body{
			overflow: visible;
		}
		
		.alinhar{
			text-align: center;
		}
	</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="navbar-default navbar navbar-toogle responsive alinhar">
				<ul class="nav navbar-nav col-md-12 col-sm-12 col-xs-12">
                                    <li class="li"><a href="<c:url value='/sobre_nos'/>" target="_parent"><strong>Sobre nós</strong></a></li>
                                    <li class="li liLast"><a><strong>Desenvolvido por TADS Association</strong></a></li>
				</ul>
			</div>
		</div>
	</div>
	
    <script src="<c:url value='/resources/js/jquery.js'/>"></script>
    <script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/funcoes.js'/>"></script>
</body>
</html>