$(document).ready(function(){

    
//Cria um atributo artigos na sessionStorage para ser recuperao pelo controller,
//obtendo modificações feitas pelo usuário
function setArtigosExtraidos(){
    var artigosExtraidos = [];

    $(".linhaArtigo").each(function(index, linha){
        var artigo = {
            titulo: $(linha + " #titulo").attr("value"),
            autores: function() {
                var autores = [];

                $(linha + " #autor").each(function(idx, autor){
                    autores[idx] = $(autor).attr("value") + " - "
                });
                
                return autores.toString();
            }
        };

        artigosExtraidos[index] = artigo;
    });

    sessionStorage.setItem("artigosExtraidos", artigosExtraidos);
}
});