<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html lang="pt-br">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="view report" content="width=device-width, initial-scale=1">

    <title>Adicionar Artigos</title>

    <link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/style.css'/>" rel="stylesheet"/>
    <style type="text/css">


    </style>
</head>
<body>
    <div class="container">
        
        <!--Menu topo -->
        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <header>
                    <c:import url="/fixo/header.jsp"></c:import>
                </header>
            </div>
        </div>

        <div class="row">
                
            <!-- Conteudo -->
            <div class="col-md-9 col-sm-9 col-xs-12 q">
                <form id="formIndexarArtigo" action="<c:url value='/extrairArtigo'/>" method="post" enctype="multipart/form-data" target="_parent">
                    <div class="form-group">
                        <label>Revista:</label>
                        <input type="text" name="revista" required>
                    </div>
                    <div class="form-group">
                        <label>Ano:</label>
                        <input type="number" name="ano" required>
                    </div>
                    <div class="form-group">
                        <label>Volume:</label>
                        <input type="number"  name="volume" required>
                    </div>
                    <div class="form-group">
                        <label>Número:</label>
                        <input type="number" name="numero" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="arquivo" class="control-label col-md-2 col-sm-2">Arquivo da Edição:</label>
                        <div class="col-md-9 col-sm-9">
                            <input name="arquivo" type="file" class="form-control" required>
                        </div>
                    </div>
                    
                    <div class="form-roup">
                        <button id="btnPesquisar" type="submit">Adicionar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="<c:url value='/resources/js/jquery.js'/>"></script>
    <script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/funcoes.js'/>"></script>
    <script>
        exibirMsg("${msgErro}");
    </script>
</body>
</html>