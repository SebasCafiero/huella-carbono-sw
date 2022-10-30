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


function cancelarBorradoTrayecto(){
//    var elements = document.getElementsByClassName("modal");
//    Array.from(elements).forEach(function(elem){elem.style.display='none'});
//  document.getElementsByClassName("confirmar")[0].style.display = 'none';
  document.getElementById("confirmacion").style.display = 'none';
}

function borrarTrayecto(idMiembro, idTrayecto){
    document.getElementById("confirmacion").style.display = 'block';
    document.getElementById("accion-borrado").onclick = () => concretarBorradoTrayecto(idMiembro, idTrayecto);
//    document.getElementById("borrar-id-miembro").value = idMiembro;
//    document.getElementById("borrar-id-trayecto").value = idTrayecto;
}

function concretarBorradoTrayecto(idMiembro, idTrayecto) {
    //ENVIAR AJAX AL BACK
    $.ajax({
        url: "/miembro/" + idMiembro + "/trayecto/" + idTrayecto,
        type: "DELETE",
        success: function(result){
          location.href = "/miembro/" + idMiembro + "/trayecto?action=list"
        }
    });
    //document.getElementsByClassName("modal"+id)[0].innerHTML = id;
}

function tramoNuevoHabilitacion() {
    if(document.getElementById("tramo-nuevo-ack").checked)
        document.getElementsByClassName("tramos-nuevos")[0]
        .getElementsByTagName("fieldset")[0]
        .disabled = false;
    else
        document.getElementsByClassName("tramos-nuevos")[0]
        .getElementsByTagName("fieldset")[0]
        .disabled = true;
}

function trayectoCompartido() {
    if(document.getElementById("trayecto-compartido-ack").checked) {
        document.getElementById("trayecto-id").disabled = false;
        document.getElementById("fecha").disabled = true;
        document.getElementsByClassName("tramos-nuevos")[0]
                .getElementsByTagName("fieldset")[0]
                .disabled = true;
    } else {
        document.getElementById("trayecto-id").disabled = true;
        document.getElementById("fecha").disabled = false;
        document.getElementsByClassName("tramos-nuevos")[0]
                .getElementsByTagName("fieldset")[0]
                .disabled = false;
    }
}