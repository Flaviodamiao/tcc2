

$(".btnDetalhar").on("click", function(){
	var item = JSON.stringify({
		id : $(this).attr("id")
	});
	
	localStorage.setItem("itemDetalhar", item);
});

function preencherPessoas(){
	var pessoas = [];
	
	pessoas[0] = JSON.stringify({
		id : 1,
		nome : "Francisco das Chagas",
		preco : 50,
		categoria : "Pedreiro",
		telefone : "(92) 99123-4567",
		foto : "../img/p1.jpg"
	});
	
	pessoas[1] = JSON.stringify({
		id : 2,
		nome : "Maria das Graças",
		preco : 50,
		categoria : "Garçonete",
		telefone : "(92) 99000-1111",
		foto : "../img/p2.jpg"
	});
	
	pessoas[2] = JSON.stringify({
		id : 3,
		nome : "Josefa Silva",
		preco : 40,
		categoria : "Limpeza",
		telefone : "(92) 91111-0999",
		foto : "../img/p4.jpg"
	});
	
	pessoas[3] = JSON.stringify({
		id : 4,
		nome : "Joas Ferreira",
		preco : 100,
		categoria : "Carpinteiro",
		telefone : "99222-2222",
		foto : "../img/p3.jpg"
	});
	
	pessoas[4] = JSON.stringify({
		id : 5,
		nome : "Eliana Silva",
		preco : 30,
		categoria : "Artesanato",
		telefone : "99333-3333",
		foto : "../img/p6.jpg"
	});
	
	pessoas[5] = JSON.stringify({
		id : 6,
		nome : "João Santana",
		preco : 5,
		categoria : "Eventos",
		telefone : "99444-4444",
		foto : "../img/p5.jpg"
	});
	
	pessoas[6] = JSON.stringify({
		id : 7,
		nome : "Vitória Alecrim",
		preco : 50,
		categoria : "Alimentação",
		telefone : "99555-5555",
		foto : "../img/p8.jpg"
	});
	
	pessoas[7] = JSON.stringify({
		id : 8,
		nome : "Marcos Oliveira",
		preco : 40,
		categoria : "Eletricista - Eletrônica",
		telefone : "99666-6666",
		foto : "../img/p7.jpg"
	});
	
	pessoas[8] = JSON.stringify({
		id : 9,
		nome : "Adriana Sousa",
		preco : 70,
		categoria : "Pintor",
		telefone : "99777-7777",
		foto : "../img/p10.jpg"
	});
	
	localStorage.setItem("pessoas", JSON.stringify(pessoas));
}


function detalhar(){
	var itemDetalhe = $.parseJSON(localStorage.getItem("itemDetalhar"));
	var idItemDetalhe = $.parseJSON(itemDetalhe.id);
	
	var pessoas = [];
	pessoas = $.parseJSON(localStorage.getItem("pessoas"));
	var pessoa = $.parseJSON(pessoas[idItemDetalhe -1]);
	
	$("#foto").attr("src", pessoa.foto);
	$("#nome").html(pessoa.nome);
	$("#categoria").html(pessoa.categoria);
	$("#preco").html("R$" + pessoa.preco + ",00");
	$("#telefone").html(pessoa.telefone);
	$(".btnAddFavorito").attr("id", pessoa.id);
}




$(".btnAddFavorito").on("click", function(){
	var id = $(this).attr("id");
	var pessoas = [];
	pessoas = $.parseJSON(localStorage.getItem("pessoas"));
	var pessoa = $.parseJSON(pessoas[id - 1]);
	var produtoNovo = true;
	var carrinho = [];
	
	if(!localStorage.getItem("carrinho")){
		carrinho[0] = pessoa;
	} else{
		carrinho = JSON.parse(localStorage.getItem("carrinho"));
	
		qtdeItens = carrinho.length;
		
		var i = 0;
		
		for(i; i < qtdeItens; i++){
			var produto = carrinho[i];
			
			if( produto.id == id){
				alert("Produto já está nos favoritos!");
				produtoNovo = false;
				break;
			}
		}
		
		if (produtoNovo){
			carrinho[qtdeItens] = pessoa;
		}
	}
	
	localStorage.setItem("carrinho", JSON.stringify(carrinho));
});

//function addItem(){
	
//}


function exibirCarrinho(){
	if(!localStorage.getItem("carrinho")){
		$("#divCarrinho").append("<h4>Você ainda não adicionou nenhum produto ao carrinho!</h4>");
	}else{
		
		carrinho = $.parseJSON(localStorage.getItem("carrinho"));
		for(i = 0; i < carrinho.length; i++){
			var produto = carrinho[i];
			$("#divCarrinho").append(
				"<div class=\"col-md-3 col-sm-3 col-xs-12\">" +
					"<figure>" +
						"<img src=\"" + produto.foto + "\" alt=\"Foto\"></img>" +
					"</figure>" +
				"</div>" +
				"<div class=\"col-md-5 col-sm-5 col-xs-12\">" +
					"<h3 class=\"h3\">" + produto.nome + "</h3>" +
					"<h4 class=\"h4\">" + produto.categoria + "</h4>" +
				"</div>" +
				"<div class=\"col-md-4 col-sm-4 col-xs-12\">" +
					"<h6 class=\"h6\">A partir de:</h6>" +
					"<h3 class=\"h3\">R$ " + produto.preco + "<small>,00</small></h3>" +
					"<h4 id=\"telefone\" class=\"h4\"></h4>" +
				"</div>" +
				
				"<div class=\"col-md-12 col-sm-12 col-xs-12 botao\">" +
					"<br><br>" +
					"<button id=" + produto.id + " type=\"button\" class=\"btn btn-default btnRemover\">Remover dos Favoritos</button><br><br>" +
				"</div>"
			);
		}
	}
	
	$(".btnRemover").on("click", function(){
		for(i = 0; i < carrinho.length; i++){
			var codigo = parseInt($(this).attr("id"));
			var prod = carrinho[i];
			
			if (prod.id	 == codigo){
				carrinho.splice(i, 1);
				localStorage.setItem("carrinho", JSON.stringify(carrinho));
				
				if(carrinho.length == 0){
					localStorage.removeItem("carrinho");
				}
				
				window.location.reload();
			}
		}
	});
}
