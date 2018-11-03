<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    <div class="container">
        
        <!-- Menu topo ->
        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <header>
                    <iframe id="ifHeader" src="<c:url value='/fixo/header.jsp'/>" class="embed-responsive-item"></iframe>
                </header>
            </div>
        </div>

        <div class="row">

            <!-- Menu lateral ->
            <div class="col-md-3 col-sm-3 col-xs-12">
                <aside>
                    <iframe id="ifAside" src="<c:url value='/fixo/aside.jsp'/>"></iframe>
                </aside>
            </div>-->
                
            <!-- Conteudo -->
            <div class="col-md-9 col-sm-9 col-xs-12 q">
                <form id="formPesquisa" action="<c:url value='/realizarBusca'/>" method="post" target="_parent">
                    <div class="form-group">
                        <label for="conteudo">Pesquisar no texto completo dos documentos</label>
                        <input id="conteudo" name="conteudo" type="text">
                    </div>
                    <div class="form-roup">
                        <button id="btnPesquisar" type="submit">Pesquisar</button>
                    </div>
                </form>
            </div>
                
        <!--/div>
                
        <!-- Rodapé ->
        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <footer>
                    <iframe id="ifFooter" src="<c:url value='/fixo/footer.jsp'/>" class="embed-responsive-item"></iframe>
                </footer>
            </div>	
        </div-->
    </div>

    <script src="<c:url value='/resources/js/jquery.js'/>"></script>
    <script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
    <!--script type="text/javascript" src="<c:url value='/resources/js/funcoes.js'/>"></script-->
    <script>
        if("${msgErro}" != ""){
            window.alert("${msgErro}")
        }
    </script>
</body>
</html>