$(document).ready(function(){

});

$("#btnModalPesqAvancada").on("click", function(){
    $("#conteudoAvanc").val($("#conteudo").val());
});

$("#btnCancelar").on("click", function(){
    $("#conteudo").val($("#conteudoAvanc").val());
    $("#titulo").val("");
    $("#autores").val("");
    $("#ano").val("");
    $("#volume").val("");
    $("#numero").val("");
});

$("#btnFechar").on("click", function(){
    $("#conteudo").val($("#conteudoAvanc").val());
    $("#titulo").val("");
    $("#autores").val("");
    $("#ano").val("");
    $("#volume").val("");
    $("#numero").val("");
});

//limpa os campos da pesquisa avançada ao clicar no botão de pesquisa simples
$("#btnPesquisar").on("click", function(){
    if($("#conteudo").val().length < 3){
        alert("O campo da pesquisa precisa conter ao menos 3 letras");
        return false;
    }
});

$("#btnPesquisaAvancada").on("click", function(){
    
    if($("#conteudoAvanc").val().length < 3 && $("#titulo").val().length < 3
            && $("#autores").val().length < 3 && $("#revista").val().length < 3
            && $("#ano").val() <= 0 && $("#volume").val().length <= 0 && $("#numero").val() <= 0){
        
        alert("É necessário que ao menos um campo seja preenchido com um valor válido!\n\
                Campos \"Texto Completo\", \"Título\" e \"Autores\" devem ter ao menos 3 letras\n\
                Os demais campos devem possuir valor maior que zero.");
        return false;
    }
    
    $("#filtroConteudo").val($("select[name='filtroConteudoAvanc']").val());
    $("#conteudo").val($("#conteudoAvanc").val());
});


function exibirMsg(msg){
    if(msg !== ""){
        alert(msg);
    }
}