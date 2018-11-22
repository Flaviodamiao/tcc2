<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html lang="pt-br">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta name="view report" content="width=device-width, initial-scale=1">
	
        <link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet"/>
        <link href="<c:url value='/resources/css/style.css'/>" rel="stylesheet"/>
	<style type="text/css">
	</style>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <div class="navbar-default navbar navbar-toogle responsive">
                <div class="navbar-header col-md-12 col-sm-12 col-xs-12">
                    <span class="navbar-brand">Filtros Aplicados</span>
                </div>
                <div class="list-group col-md-12 col-sm-12 col-xs-12">
                    <ul class="nav menu list-group">	
                        <c:if test="${!artigo.getConteudo().equals('')}">
                            <li class="list-group-item"><strong>Texto Completo:</strong> ${artigo.getConteudo()}</li>
                        </c:if>
                        <c:if test="${!artigo.getTitulo().equals('')}">
                            <li class="list-group-item"><strong>Título:</strong> ${artigo.getTitulo()}</li>
                        </c:if>
                        <c:if test="${!artigo.getAutores().equals('')}">
                        <li class="list-group-item"><strong>Autores:</strong> ${artigo.getAutores()}</li>
                        </c:if>
                        <c:if test="${edicao.getRevista().name() != null}">
                            <li class="list-group-item"><strong>Revista:</strong> ${edicao.getRevista()}</li>
                        </c:if>
                        <c:if test="${edicao.getAno() > 0}">
                            <li class="list-group-item"><strong>Ano:</strong> ${edicao.getAno()}</li>
                        </c:if>
                        <c:if test="${edicao.getVolume() > 0}">
                            <li class="list-group-item"><strong>Volume:</strong> ${edicao.getVolume()}</li>
                        </c:if>
                        <c:if test="${edicao.getNumero() > 0}">
                            <li class="list-group-item"><strong>Número:</strong> ${edicao.getNumero()}</li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
	
    <script src="<c:url value='/resources/js/jquery.js'/>"></script>
    <script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/funcoes.js'/>"></script>
</body>
</html>