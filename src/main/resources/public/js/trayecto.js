/*
$(document).ready(function(){
  $("#inputUsuario").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#tabla tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});
*/


function confirmarEliminacion(id){
  document.getElementById("IDTRAYECTO").value = id;
  document.getElementById("modalEliminar").style.display = 'block';
}

function cerrarModal(){
//    var elements = document.getElementsByClassName("modal");
//    Array.from(elements).forEach(function(elem){elem.style.display='none'});
  document.getElementsByClassName("modal")[0].style.display = 'none';
}

function borrarTrayecto(id){
    //ENVIAR AJAX AL BACK
    $.ajax({
        url: "/trayecto/"+id,
        type: "DELETE",
        success: function(result){
          location.href= "/trayectos"
        }
    });
    //document.getElementsByClassName("modal"+id)[0].innerHTML = id;
}