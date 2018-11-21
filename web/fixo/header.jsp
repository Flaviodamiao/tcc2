<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html lang="pt-br">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="view report" content="width=device-width, initial-scale=1">

    <title>Pesquisar</title>

    <link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/style.css'/>" rel="stylesheet"/>
    <style type="text/css">
    </style>
</head>
<body>
    <div class="row">
        <div class="navbar-default navbar navbar-toogle responsive">

            <!-- Logo -->
            <a href="<c:url value='/index'/>" target="_parent">
                <figure class="col-md-9 col-sm-9 col-xs-12">
                    <img src="<c:url value='/resources/img/logo.png'/>" class="img-responsive img-rounded" alt="logo"></img>
                </figure>
            </a>

            <!-- Menu Empresa -->
            <div class="ulMenu">
                <ul class="nav navbar-nav col-md-5 col-sm-5 col-xs-12">
                    <li class="li"><a href="<c:url value='/index'/>" target="_parent"><strong>Início</strong></a></li>
                    <li class="li"><a href="<c:url value='/formIndexarArtigo'/>" target="_parent"><strong>Área da Administração</strong></a></li>
                </ul>
            </div>
        </div>
    </div>

    <script src="<c:url value='/resources/js/jquery.js'/>"></script>
    <script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/funcoes.js'/>"></script>
</body>
</html>