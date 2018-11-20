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
		.navbar-brand{
			background-color: #E3E8FC;
			width: 100%;
			text-align: center;
		}
		
		
		.menu li  ul{
			display: none;
		}
		
		.menu li:hover{
			background-color: white;
		}
		
		li:hover ul{
			border: 0pxpx solid black;
			list-style-type: none;
			display: block;
			background-color: white;
			position: static;
		}
		
		li:hover ul li{
			border: 0px solid black;
		}
		
		.menu{
			list-style-type: none;
		}
		
		li img{
			width: 70px;
			padding-right: 5px;
			padding-bottom: 5px;
		}
		
		
	</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="navbar-default navbar navbar-toogle responsive">
				<div class="navbar-header">
					<span class="navbar-brand">Categorias</span>
				</div>
				<ul class="nav menu">
                                    <li class=""><img src="<c:url value='/resources/img/civil.jpg'/>"></img>Construção Civil
						<ul class="">
							<li>Pedreiro</li>
							<li>Pintor</li>
							<li>Eletricista - Predial</li>
							<li>Carpinteiro</li>
							<li>Marceneiro</li>
						</ul>
					</li>	
					<li><img src="<c:url value='/resources/img/eletrodomesticos.png'/>"></img>Eletricista - Eletrônica</li>
					<li><img src="<c:url value='/resources/img/limpeza.png'/>"></img>Limpeza</li>
					<li><img src="<c:url value='/resources/img/alimentacao.png'/>"></img>Alimentação</li>
					<li><img src="<c:url value='/resources/img/eventos.jpg'/>"></img>Eventos
						<ul>
							<li>Ornamentação</li>
							<li>Jogos de mesa</li>
							<li>Jogos de prato</li>
						</ul>
					</li>
					<li><img src="<c:url value='/resources/img/artesanato.png'/>"></img>Artesanato
						<ul>
							<li>Pintura</li>
							<li>Corte / Costura</li>
							<li>Estatuetas</li>
						</ul>
					</li>
					<li></li>
					<li></li>
				</ul>
			</div>
		</div>
	</div>
	
    <script src="<c:url value='/resources/js/jquery.js'/>"></script>
    <script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/funcoes.js'/>"></script>
</body>
</html>