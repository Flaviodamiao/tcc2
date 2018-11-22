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
        
       <!--Menu topo -->
        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <header>
                    <c:import url="/fixo/header.jsp"></c:import>
                </header>
            </div>
        </div>
                
        <!-- Menu lateral -->
        <div class="col-md-3 col-sm-3 col-xs-12">
            <aside>
                <c:import url="/fixo/aside.jsp"></c:import>
            </aside>
        </div>
                
        <!-- Conteudo -->
        <div class="col-md-9 col-sm-9 col-xs-12 q">
            <form id="formPesquisa" action="<c:url value='/realizarBusca'/>" method="post" target="_parent">
                <div class="form-inline col-md-12 col-sm-12 divFormConteudo">
                    <div class="divFormConteudo">
                        <input id="conteudo" name="conteudo" type="text" class="input-lg inputConteudo" align="center" value="${artigo.getConteudo()}">
                        <input type="hidden" id="filtroConteudo" name="filtroConteudo" value="MUST">
                        <button type="submit" id="btnPesquisar" class="btn btn-info btn-lg">Pesquisar</button>
                        <button type="button" id="btnModalPesqAvancada" class="btn btn-info btn-lg" data-toggle="modal" data-target="#pesqAvancadaModal">Adicionar Filtros</button>
                    </div>

                </div>

                <!-- Modal Pesquisa Avançada-->
                <div class="modal fade" id="pesqAvancadaModal" role="dialog">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button id="btnFechar" type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Pesquisa Avançada</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="conteudoAvanc">Texto completo</label>
                                <select name="filtroConteudoAvanc">
                                    <option value="MUST">E</option>
                                    <option value="SHOULD">OU</option>
                                    <option value="MUST_NOT">NÃO</option>
                                </select>
                                <input id="conteudoAvanc" name="conteudoAvanc" type="text">
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
                                <input id="autores" name="autores" type="text">
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
                                <input id="ano" name="ano" type="number">
                            </div>
                            <div class="form-group">
                                <label for="volume">Volume</label>
                                <select name="filtroVolume">
                                    <option value="MUST">E</option>
                                    <option value="SHOULD">OU</option>
                                    <option value="MUST_NOT">NÃO</option>
                                </select>
                                <input id="volume" name="volume" type="number">
                            </div>
                            <div class="form-group">
                                <label for="numero">Número</label>
                                <select name="filtroNumero">
                                    <option value="MUST">E</option>
                                    <option value="SHOULD">OU</option>
                                    <option value="MUST_NOT">NÃO</option>
                                </select>
                                <input id="numero" name="numero" type="number">
                            </div>
                          </div>
                            <div class="modal-footer">
                                <button type="submit" id="btnPesquisaAvancada" class="btn btn-default">Pesquisar</button>
                                <button type="btnCancelaPA" id="btnCancelar" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            </div>
                        </div>

                    </div>
                </div>
            </form>

            <div class="divResultado">
                <h4>Resultados da busca</h4>
                <table class="tblResultado">
                    <c:forEach var="artigo" items="${artigos}">
                        <tr >
                            <td >
                                <strong><a href="recuperarArtigo?caminho=${artigo.getCaminho()}" target="_self">${artigo.getTitulo()}</a></strong>
                                <p> Autores: ${artigo.getAutores()}</p>
                            </td>
                            <td>
                                Revista: ${artigo.getEdicao().getRevista()} <br>
                                Ano:     ${artigo.getEdicao().getAno()}<br>
                                Volume:  ${artigo.getEdicao().getVolume()}<br>
                                Número:  ${artigo.getEdicao().getNumero()}<br>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
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