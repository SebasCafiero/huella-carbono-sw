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
        document.getElementsByClassName("tramo-nuevo")[0]
        .getElementsByTagName("fieldset")[0]
        .disabled = false;
    else
        document.getElementsByClassName("tramo-nuevo")[0]
        .getElementsByTagName("fieldset")[0]
        .disabled = true;
}

function trayectoCompartido() {
    if(document.getElementById("trayecto-compartido-ack").checked) {
        document.getElementById("trayecto-id").disabled = false;
        document.getElementById("fecha").disabled = true;
        document.getElementsByClassName("tramo-nuevo")[0]
                .getElementsByTagName("fieldset")[0]
                .disabled = true;
    } else {
        document.getElementById("trayecto-id").disabled = true;
        document.getElementById("fecha").disabled = false;
        document.getElementsByClassName("tramo-nuevo")[0]
                .getElementsByTagName("fieldset")[0]
                .disabled = false;
    }
}

function transporteSeleccionado(clase_tramo) {
    var div = document.getElementsByClassName(clase_tramo)[0];
    var optgroups = div.getElementsByTagName("optgroup");

    var options_publico
    for(var i = 0; i < optgroups.length; i++){
        if(optgroups[i].getAttribute('label') == "Públicos")
            options_publico = (optgroups[i].getElementsByTagName("option"));
    }
//    var options_publico = optgroups.filter(o => o.getAttribute('label') == "Públicos")[0].getElementsByTagName("option"); //no es array

    var option_selected = div.getElementsByTagName("select")[0].value;  //no se si siempre será el [0]
    var seleccion = false;
    for(var i = 0; i < options_publico.length; i++){
        if((options_publico[i].selected) || (options_publico[i].value == option_selected))
            seleccion = true;
    }

    var privados = div.getElementsByClassName("coordenadas")[0].getElementsByTagName("fieldset");
    var publicos = div.getElementsByClassName("paradas")[0].getElementsByTagName("fieldset");

    if(seleccion){
        for(var j = 0; j < privados.length; j++){
            privados[j].disabled = true;
        }
        publicos[0].disabled = false;
    //          div.getElementsByClassName("coordenadas")[0].style.display = 'none';
    } else {
        for(var j = 0; j < privados.length; j++){
            privados[j].disabled = false;
        }
        publicos[0].disabled = true;
    }

    /*if(options_publico.some(o => o.selected == true)){
        for(var j = 0; j < fieldsets.length; j++){
            fieldsets[j].disabled = false;
        }
//          div.getElementsByClassName("coordenadas")[0].style.display = 'none';
    } else {
        for(var j = 0; j < fieldsets.length; j++){
            fieldsets[j].disabled = true;
        }
    }*/

    /*for(var i = 0; i < optgroups.length; i++) {
        var tipo_transporte = optgroups[i].getAttribute('label');
        if(tipo_transporte == "Públicos") {
            for(var j = 0; j < fieldsets.length; j++){
                fieldsets[j].disabled = false;
            }
//          div.getElementsByClassName("coordenadas")[0].style.display = 'none';
        } else {
            for(var j = 0; j < fieldsets.length; j++){
                fieldsets[j].disabled = true;
            }
        }
    }*/
}