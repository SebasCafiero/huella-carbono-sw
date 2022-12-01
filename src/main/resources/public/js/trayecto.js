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
    $.ajax({
        url: "/miembro/" + idMiembro + "/trayecto/" + idTrayecto,
        type: "DELETE",
        success: function(result){
          location.href = "/miembro/" + idMiembro + "/trayecto?action=list"
        }
    });
}

function tramoNuevoHabilitacion() {
    if(document.getElementById("tramo-nuevo-ack").checked)
        document.getElementById("tramo-nuevo")
        .getElementsByTagName("fieldset")[0]
        .disabled = false;
    else
        document.getElementById("tramo-nuevo")
        .getElementsByTagName("fieldset")[0]
        .disabled = true;
}

function trayectoCompartido() {
    if(document.getElementById("trayecto-compartido-ack").checked) {
        document.getElementById("trayecto-id").disabled = false;
        document.getElementById("fecha").disabled = true;
        document.getElementById("tramo-nuevo")
                .getElementsByTagName("fieldset")[0]
                .disabled = true;
    } else {
        document.getElementById("trayecto-id").disabled = true;
        document.getElementById("fecha").disabled = false;
        document.getElementById("tramo-nuevo")
                .getElementsByTagName("fieldset")[0]
                .disabled = false;
    }
}


function transporteCambiado() {
    //Función que simula un form para no perder los valores ingresados
    var path_actual = window.location.origin + window.location.pathname;
    var accion = "?action=" + new URLSearchParams(window.location.search).get("action");

    var fecha = "";
    var input_fecha = document.getElementById("fecha");
    if(input_fecha != null && input_fecha.value != null && input_fecha.value != "")
        fecha = "&fecha=" + input_fecha.value;

    var transportes = "";
    var paradas = "";
    var ubicaciones = "";

    //TRAMO NUEVO
    var div_tramo_nuevo = document.getElementById("tramo-nuevo");
    var select_transporte_nuevo = document.getElementById("transporte-nuevo");
    if(select_transporte_nuevo.value != null && select_transporte_nuevo.value != "")
        transportes += "&transporte-nuevo=" + select_transporte_nuevo.value;

    if(div_tramo_nuevo.getElementsByClassName("paradas").length > 0) {
        var div_paradas_nuevas = div_tramo_nuevo.getElementsByClassName("paradas")[0];
        var selects_paradas_nuevas = div_paradas_nuevas.getElementsByTagName("select");
        if(selects_paradas_nuevas[0].value != null && selects_paradas_nuevas[0].value != "")
            paradas += "&parada-inicial-nueva=" + selects_paradas_nuevas[0].value;
        if(selects_paradas_nuevas[1].value != null && selects_paradas_nuevas[1].value != "")
            paradas += "&parada-final-nueva=" + selects_paradas_nuevas[1].value;
    }

    if(div_tramo_nuevo.getElementsByClassName("ubicaciones").length > 0) {
        var div_ubicaciones = div_tramo_nuevo.getElementsByClassName("ubicaciones")[0];
        var inputs_ubicaciones = div_ubicaciones.getElementsByTagName("input");
        if(inputs_ubicaciones[0].value != null && inputs_ubicaciones[0].value != "") { //quizas debería validar cada uno
            ubicaciones += "&prov-inicial-nueva=" + inputs_ubicaciones[0].value;
            ubicaciones += "&mun-inicial-nueva=" + inputs_ubicaciones[1].value;
            ubicaciones += "&loc-inicial-nueva=" + inputs_ubicaciones[2].value;
            ubicaciones += "&calle-inicial-nueva=" + inputs_ubicaciones[3].value;
            ubicaciones += "&num-inicial-nueva=" + inputs_ubicaciones[4].value;
            ubicaciones += "&lat-inicial-nueva=" + inputs_ubicaciones[5].value;
            ubicaciones += "&lon-inicial-nueva=" + inputs_ubicaciones[6].value;
        }
        if(inputs_ubicaciones[7].value != null && inputs_ubicaciones[7].value != "") { //quizas debería validar cada uno
            ubicaciones += "&prov-final-nueva=" + inputs_ubicaciones[7].value;
            ubicaciones += "&mun-final-nueva=" + inputs_ubicaciones[8].value;
            ubicaciones += "&loc-final-nueva=" + inputs_ubicaciones[9].value;
            ubicaciones += "&calle-final-nueva=" + inputs_ubicaciones[10].value;
            ubicaciones += "&num-final-nueva=" + inputs_ubicaciones[11].value;
            ubicaciones += "&lat-final-nueva=" + inputs_ubicaciones[12].value;
            ubicaciones += "&lon-final-nueva=" + inputs_ubicaciones[13].value;
        }
    }

    //TRAMOS EDITABLES
    var id_tramos = "";
    var div_tramos_editables = document.getElementById("tramos-editables");
    var divs_tramos = div_tramos_editables.getElementsByClassName("tramo");

    for(var k = 0; k < divs_tramos.length; k++) {
        var div_tramo = document.getElementById("tramo-" + k);

        var select_transporte = document.getElementById("transporte-" + k);
        if(select_transporte.value != null && select_transporte.value != "")
            transportes += "&transporte" + k + "=" + select_transporte.value;

        if(div_tramo.getElementsByClassName("paradas").length > 0) {
            var div_paradas = div_tramo.getElementsByClassName("paradas")[0];
            var selects_paradas = div_paradas.getElementsByTagName("select");
            if(selects_paradas[0].value != null && selects_paradas[0].value != "")
                paradas += "&parada-inicial" + k + "=" + selects_paradas[0].value;
            if(selects_paradas[1].value != null && selects_paradas[1].value != "")
                paradas += "&parada-final" + k + "=" + selects_paradas[1].value;
        }

        if(div_tramo.getElementsByClassName("ubicaciones").length > 0) {
            var div_ubicaciones = div_tramo.getElementsByClassName("ubicaciones")[0];
            var inputs_ubicaciones = div_ubicaciones.getElementsByTagName("input");
            if(inputs_ubicaciones[0].value != null && inputs_ubicaciones[0].value != "") { //quizas debería validar cada uno
                ubicaciones += "&prov-inicial" + k + "=" + inputs_ubicaciones[0].value;
                ubicaciones += "&mun-inicial" + k + "=" + inputs_ubicaciones[1].value;
                ubicaciones += "&loc-inicial" + k + "=" + inputs_ubicaciones[2].value;
                ubicaciones += "&calle-inicial" + k + "=" + inputs_ubicaciones[3].value;
                ubicaciones += "&num-inicial" + k + "=" + inputs_ubicaciones[4].value;
                ubicaciones += "&lat-inicial" + k + "=" + inputs_ubicaciones[5].value;
                ubicaciones += "&lon-inicial" + k + "=" + inputs_ubicaciones[6].value;
            }
            if(inputs_ubicaciones[7].value != null && inputs_ubicaciones[7].value != "") { //quizas debería validar cada uno
                ubicaciones += "&prov-final" + k + "=" + inputs_ubicaciones[7].value;
                ubicaciones += "&mun-final" + k + "=" + inputs_ubicaciones[8].value;
                ubicaciones += "&loc-final" + k + "=" + inputs_ubicaciones[9].value;
                ubicaciones += "&calle-final" + k + "=" + inputs_ubicaciones[10].value;
                ubicaciones += "&num-final" + k + "=" + inputs_ubicaciones[11].value;
                ubicaciones += "&lat-final" + k + "=" + inputs_ubicaciones[12].value;
                ubicaciones += "&lon-final" + k + "=" + inputs_ubicaciones[13].value;
            }
        }

        id_tramos += "&tramo-id" + k + "=" + document.getElementById("tramo-id-" + k).value;
    }

    //window.location.replace("http://stackoverflow.com");
    $.ajax({
        type: "GET",
        success: function(result) {
          location.href = path_actual + accion + fecha + transportes + paradas + ubicaciones + id_tramos
        }
    });
}