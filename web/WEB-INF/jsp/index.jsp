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
                    <h4>Digite os termos de sua pesquisa nos campos de busca desejados e indique se o termo é obrigatório ou não</h4>
                    <div class="form-group">
                        <label for="conteudo">Texto completo</label>
                        <select name="filtroConteudo">
                            <option value="MUST">E</option>
                            <option value="SHOULD">OU</option>
                            <option value="MUST_NOT">NÃO</option>
                        </select>
                        <input id="conteudo" name="conteudo" type="text">
                    </div>
                    <div class="form-group">
                        <label for="titulo">Título</label>
                        <select name="filtroTitulo">
                            <option value="MUST">E</option>
                            <option value="SHOULD">OU</option>
                            <option value="MUST_NOT">NÃO</option>
                        </select>
                        <input id="titulo" name="titulo" type="text">
                    </div>
                    <div class="form-group">
                        <label for="autores">Autor</label>
                        <select name="filtroAutores">
                            <option value="MUST">E</option>
                            <option value="SHOULD">OU</option>
                            <option value="MUST_NOT">NÃO</option>
                        </select>
                        <input id="autor" name="autores" type="text">
                    </div>
                    <h4>Edicão</h4>
                    <div class="form-group">
                        <label for="revista">Revista</label>
                        <select name="filtroRevista">
                            <option value="MUST">E</option>
                            <option value="SHOULD">OU</option>
                            <option value="MUST_NOT">NÃO</option>
                        </select>
                        <input id="revista" name="revista" type="text">
                    </div>
                    <div class="form-group">
                        <label for="ano">Ano</label>
                        <select name="filtroAno">
                            <option value="MUST">E</option>
                            <option value="SHOULD">OU</option>
                            <option value="MUST_NOT">NÃO</option>
                        </select>
                        <input id="ano" name="ano" type="text">
                    </div>
                    <div class="form-group">
                        <label for="volume">Volume</label>
                        <select name="filtroVolume">
                            <option value="MUST">E</option>
                            <option value="SHOULD">OU</option>
                            <option value="MUST_NOT">NÃO</option>
                        </select>
                        <input id="volume" name="volume" type="text">
                    </div>
                    <div class="form-group">
                        <label for="numero">Número</label>
                        <select name="filtroNumero">
                            <option value="MUST">E</option>
                            <option value="SHOULD">OU</option>
                            <option value="MUST_NOT">NÃO</option>
                        </select>
                        <input id="numero" name="numero" type="text">
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
    <script type="text/javascript" src="<c:url value='/resources/js/funcoes.js'/>"></script>
    <script>
        exibirMsg("${msgErro}");
    </script>
</body>
</html>