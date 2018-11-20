
            <div class="row">
                <div class="navbar-default navbar navbar-toogle responsive">

                    <!-- Logo -->
                    <a href="<c:url value='/index'/>" target="_parent">
                        <figure class="col-md-3 col-sm-3 col-xs-12">
                            <img src="<c:url value='/resources/img/logo.png'/>" class="img-responsive img-rounded" alt="logo"></img>
                        </figure>
                    </a>

                    <!-- Menu Empresa -->
                    <div class="ulMenu">
                        <ul class="nav navbar-nav col-md-5 col-sm-5 col-xs-12">
                            <li class="li"><a href="<c:url value='/index'/>" target="_parent"><strong>Início</strong></a></li>
                            <li class="li"><a href="<c:url value='/carrinho'/>" target="_parent"><strong>Carrinho</strong></a></li>
                            <li class="li"><a href="<c:url value='/formCadCliente'/>" target="_parent"><strong>Cadastro Cliente</strong></a></li>
                            <li class="li liLast"><a href="<c:url value='/trabalhe_conosco'/>" target="_parent"><strong>Trabalhe Conosco</strong></a></li>
                        </ul>
                    </div>

                    <!-- Caixa de pesquisa -->
                    <div class="col-md-4 col-sm-4 col-xs-12 cxPesquisa">
                        <form id="pesquisar" action="<c:url value='/index'/>" target="_parent" method="post" class="form-inline">
                            <div class="form-group">
                                <input id="textoPesquisa" type="text" class="form-control" placeholder="Digite aqui sua pesquisa">
                                <button id="btPesquisar" type="submit" class="btn default">
                                    <span class="glyphicon glyphicon-search"></span>
                                    Pesquisar
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
