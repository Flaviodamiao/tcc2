<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html lang="pt-br">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="view report" content="width=device-width, initial-scale=1">

    <title>Início - Pesquisar</title>

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
            <div class="col-md-12 col-sm-12 col-xs-12 q">
                <form id="formPesquisa" action="<c:url value='/realizarBusca'/>" method="post" target="_parent">
                    <h4 align="center" class="primElemento">Pesquisa</h4>
                    <div class="form-group col-md-12 col-sm-12 divFormConteudo">
                        <div class="divFormConteudo">
                            <input id="conteudo" name="conteudo" type="text" class="input-lg inputConteudo" align="center">
                            <input type="hidden" id="filtroConteudo" name="filtroConteudo" value="MUST">
                        </div>
                    </div>
                    <div class="btn-block divBtnPesquisa">
                        <button type="submit" id="btnPesquisar" class="btn btn-info btn-lg">Pesquisar</button>
                        <button type="button" id="btnModalPesqAvancada" class="btn btn-info btn-lg" data-toggle="modal" data-target="#pesqAvancadaModal">Adicionar Filtros</button>
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