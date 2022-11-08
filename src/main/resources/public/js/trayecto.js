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

function transporteSeleccionado(idMiembro, clase_tramo) {
    var div = document.getElementsByClassName(clase_tramo)[0];
    var optgroups = div.getElementsByTagName("optgroup");

    var options_publico;
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

function transporteSeleccionado2(id_miembro, clase_tramo) {
    var div = document.getElementsByClassName(clase_tramo)[0];
//    var option_selected = div.getElementsByTagName("select")[0].value;
    var select_transporte_cambiado = div.getElementsByTagName("select")[0];
    div.style.backgroundColor  = 'pink';
    var fecha = document.getElementById("fecha").value;

    var div_tramo_nuevo = document.getElementsByClassName("tramo-nuevo")[0];
    var div_tramos_editables = document.getElementById("tramos-editables");

    var path_actual = window.location.origin + window.location.pathname;
    var accion = "?action=" + new URLSearchParams(window.location.search).get("action");
    var fecha_trayecto = "&fecha=" + fecha

    var selects_transportes = div_tramos_editables.getElementsByClassName("transporte-editable");
    var transportes_editables = "";
    for(var k = 0; k < selects_transportes.length; k++) {
        transportes_editables += "&transporte" + k + "=" + selects_transportes[k].value;
    }

    var divs_paradas = div_tramos_editables.getElementsByClassName("paradas");
    var paradas_editables = "";
    for(var l = 0; l < divs_paradas.length; l++) {
        var selects_paradas = divs_paradas[l].getElementsByTagName("select");
        paradas_editables += "&parada-inicial" + l + "=" + selects_paradas[0].value;
        paradas_editables += "&parada-final" + l + "=" + selects_paradas[1].value;
    }

    var divs_ubicaciones = div_tramos_editables.getElementsByClassName("ubicaciones");
    var ubicaciones_editables = "";
    for(var m = 0; m < divs_ubicaciones.length; m++) {
        var inputs_ubicaciones = divs_ubicaciones[m].getElementsByTagName("input");
        ubicaciones_editables += "&prov-inicial" + m + "=" + inputs_ubicaciones[0].value;
        ubicaciones_editables += "&mun-inicial" + m + "=" + inputs_ubicaciones[1].value;
        ubicaciones_editables += "&loc-inicial" + m + "=" + inputs_ubicaciones[2].value;
        ubicaciones_editables += "&calle-inicial" + m + "=" + inputs_ubicaciones[3].value;
        ubicaciones_editables += "&num-inicial" + m + "=" + inputs_ubicaciones[4].value;
        ubicaciones_editables += "&lat-inicial" + m + "=" + inputs_ubicaciones[5].value;
        ubicaciones_editables += "&lon-inicial" + m + "=" + inputs_ubicaciones[6].value;

        ubicaciones_editables += "&prov-final" + m + "=" + inputs_ubicaciones[7].value;
        ubicaciones_editables += "&mun-final" + m + "=" + inputs_ubicaciones[8].value;
        ubicaciones_editables += "&loc-final" + m + "=" + inputs_ubicaciones[9].value;
        ubicaciones_editables += "&calle-final" + m + "=" + inputs_ubicaciones[10].value;
        ubicaciones_editables += "&num-final" + m + "=" + inputs_ubicaciones[11].value;
        ubicaciones_editables += "&lat-final" + m + "=" + inputs_ubicaciones[12].value;
        ubicaciones_editables += "&lon-final" + m + "=" + inputs_ubicaciones[13].value;
    }

    var transporte_nuevo = "&transporte-nuevo=" + id_transporte_nuevo;

    $.ajax({
        type: "GET",
        success: function(result) {
          location.href = path_actual + accion + transporte_nuevo + fecha_trayecto + transportes_editables + paradas_editables + ubicaciones_editables
//          location.href = "/miembro/" + idMiembro + "/trayecto?action=list"
        }
    });
}