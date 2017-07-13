	

var server;
var tiendas;
var table;
var tabledetalle;
var dtpedido;
var productos;
var excepciones;
var idPedido = 0;
var idCliente = 0;
var idEstadoPedido = 0;
var longitud = 0;
var latitud = 0;



$(document).ready(function() {

	//Obtenemos el valor de la variable server
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	server = loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
	

	//Lo primero que realizaremos es validar si está logueado
	$.ajax({ 
	    	url: server + 'ValidarUsuarioAplicacion', 
	    	dataType: 'text',
	    	type: 'post', 
	    	async: false, 
	    	success: function(data){ 
		    	if(data == 'OK')
		    	{
		    	}
		    	else
		    	{
		    		location.href ="http://localhost:8080/ProyectoPizzaAmericana/Index.html";
		    	}
		    	console.log("OJO" + data);
			} 
		});

	//Llenamos arreglo con los productos
	
	//cargarMapa();
	//Cargamos los productos tipo Pizza para el menu inicial
	
	//Final cargue productos pizza


    dtpedido = $('#grid-detallepedido').DataTable( {
    		"aoColumns": [
    		{ "mData": "iddetallepedido" },
            { "mData": "nombreproducto" },
            { "mData": "cantidad" },
            { "mData": "especialidad1" },
            { "mData": "especialidad2" },
            { "mData": "valorunitario" },
            { "mData": "valortotal" },
            { "mData": "adicion" },
            { "mData": "observacion" },
            { "mData": "liquido" },
            { "mData": "excepcion" }
            
        ]
    	} );

    dtpedido = $('#grid-encabezadopedido').DataTable( {
    		"aoColumns": [
    		{ "mData": "idpedido" },
            { "mData": "tienda" },
            { "mData": "totalbruto" },
            { "mData": "impuesto" },
            { "mData": "totalneto" },
            { "mData": "idcliente" },
            { "mData": "cliente" },
            { "mData": "estadopedido" },
            { "mData": "fechapedido" }
        ]
    	} );

     
    $('#grid-encabezadopedido').on('click', 'tr', function () {
        datospedido = table.row( this ).data();
        //alert( 'Diste clic en  '+datos.nombre+'\'s row' );
        //$('#nombres').val(datos.nombre);
        var idpedido = datospedido.idpedido;
        $('#NumPedido').val(idpedido);
        $('#Cliente').val(datospedido.cliente);
        $('#estadopedido').val(datospedido.estadopedido);
        // La idea es tomar el id pedido seleccionado y con esto ir a buscar la información.
        $.getJSON(server + 'GetClientePorID?idcliente=' + datospedido.idcliente, function(data1){
	                		
	                		$('#telefono').val(data1[0].telefono);
	                		$('#nombres').val(data1[0].nombrecliente);
	                		$('#direccion').val(data1[0].direccion);
	                		$('#municipio').val(data1[0].nombremunicipio);
	                		$('#zona').val(data1[0].zona);
	                		$('#observacionDir').val(data1[0].observacion);
	                		$('#tienda').val(data1[0].nombretienda);
							
					});
        if ( $.fn.dataTable.isDataTable( '#grid-detallepedido' ) ) {
    		tabledetalle = $('#grid-detallepedido').DataTable();
    	}
        $.getJSON(server + 'ConsultarDetallePedido?numeropedido=' + idpedido, function(data1){
	                		tabledetalle.clear().draw();
	                		console.log(data1);
							for(var i = 0; i < data1.length;i++){
								tabledetalle.row.add(data1[i]).draw();
							}
	                		
							
					});
     

     } );
 	

 	
 	
     

	} );


$(function(){
	
	getListaTiendas();
	getExcepcionesPrecios();
	
});



function validarTelefono(){

	if ( $.fn.dataTable.isDataTable( '#grid-clientes' ) ) {
    	table = $('#grid-clientes').DataTable();
    }
	
	$.getJSON(server + 'GetCliente?telefono=' + telefono.value, function(data1){
			table.clear().draw();
			for(var i = 0; i < data1.length;i++){
				var cadaCliente  = data1[i];
				table.row.add(data1[i]).draw();
			}
		});
		
}


function getListaTiendas(){
	$.getJSON(server + 'GetTiendas', function(data){
		tiendas = data;
		var str = '';
		for(var i = 0; i < data.length;i++){
			var cadaTienda  = data[i];
			str +='<option value="'+ cadaTienda.nombre +'" id ="'+ cadaTienda.id +'">' + cadaTienda.nombre +'</option>';
		}
		$('#selectTiendas').html(str);
	});
}



function getExcepcionesPrecios(){
	$.getJSON(server + 'getExcepcionesPrecio', function(data){
		excepciones = data;
		var str = '';
		str += '<option value="vacio">Sin Excepcion</option>';
		for(var i = 0; i < excepciones.length;i++){
			var cadaExcepcion  = data[i];
			str +='<option value="'+ cadaExcepcion.idexcepcion +'" id ="' + cadaExcepcion.idproducto +'">' + cadaExcepcion.descripcion +'</option>';
		}
		$('#selectExcepcion').html(str);

	});
	var selExcepcion;
	var idSelExcepcion;
	var selCodigoProducto;
	$('#selectExcepcion').bind('change', function(){
		selExcepcion = $(this).val();
		idSelExcepcion = $(this).children(":selected").attr("id");
		selCodigoProducto = $("input:radio[name=tamanoPizza]:checked").val();
		if (selCodigoProducto != 'otros')
		{

			if (selCodigoProducto != idSelExcepcion)
			{
				alert("La excepción no está relacionada con el producto seleccionado, por favor corrija su elección");
				$("#selectExcepcion").val("vacio");
				return;
			}
			else
			{
				$.getJSON(server + 'GetSaboresLiquidoExcepcion?idExcepcion=' + selExcepcion, function(data1){
                		console.log(data1);
                		var strGas='';
                		var strGas = '<h2>Gaseosa</h2>';
                		strGas += '<table class="table table-bordered">';
                		strGas += '<tbody>';
						for(var i = 0; i < data1.length;i++){
							var cadaLiq  = data1[i];
							strGas +='<tr> ';
							strGas +='<td> ';
							strGas += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaLiq.idSaborTipoLiquido + '" id="' + cadaLiq.descripcionLiquido + '" name="liquido">' + cadaLiq.descripcionLiquido + '-' + cadaLiq.descripcionSabor +'</label>';
							strGas += '</td> </tr>';
						}
						strGas += '</tbody> </table>';
                		$('#frmgaseosas').html(strGas);
				});
			}
		}
		else
		{

			var selOtrosProductos = $("input:radio[name=otros]:checked").val();
			if(selOtrosProductos == '')
			{
				alert("Antes de seleccionar la Excepción de precio, debe terminar de seleccionar el producto");
				$("#selectExcepcion").val("vacio");
				return;
			}
			else
			{
				if (selOtrosProductos != idSelExcepcion)
				{
					alert("La excepción no está relacionada con el producto seleccionado, por favor corrija su elección");
					$("#selectExcepcion").val("vacio");
					return;
				}
				else
				{
					$.getJSON(server + 'GetSaboresLiquidoExcepcion?idExcepcion=' + selExcepcion, function(data1){
	                		console.log(data1);
	                		var strGas='';
	                		strGas = '<h2>Gaseosa</h2>';
	                		strGas += '<table class="table table-bordered">';
                			strGas += '<tbody>';
							for(var i = 0; i < data1.length;i++){
								var cadaLiq  = data1[i];
								strGas +='<tr>';
								strGas +='<td>';
								strGas += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaLiq.idSaborTipoLiquido + '" id="' + cadaLiq.descripcionLiquido + '" name="liquido">' + cadaLiq.descripcionLiquido + '-' + cadaLiq.descripcionSabor +'</label>';
								strGas += '</td> </tr>';
							}
							strGas += '</tbody> </table>';
	                		$('#frmgaseosas').html(strGas);
					});
				}
			}
		}
		//console.log(selExcepcion + " " + idSelExcepcion);
	});
}




// Evento para cuando se da  CLICK EN EL BOTÓN BUSCAR
function buscarMapa() {

    // Obtenemos la dirección y la asignamos a una variable
    var direccion = $('#direccion').val();
    var municipio = $("#selectMunicipio").val();
    municipio = municipio.loLowerCase();
    direccion = direccion + " " + municipio;
    var resultado;
    
    $.ajax({ 
	    				url:'https://maps.googleapis.com/maps/api/geocode/json?components=administrative_area:Medellin|country:Colombia&address=' + direccion +'&key=AIzaSyBH1VN3540Ux8Y92wDv61horvr8SUqNd_s' , 
	    				dataType: 'json', 
	    				async: false, 
	    				success: function(data){ 
								console.log(data);
								resultado = data;
								console.log(resultado);
							} 
						});
    // Creamos el Objeto Geocoder
    var geocoder = new google.maps.Geocoder();
    // Hacemos la petición indicando la dirección e invocamos la función
    // geocodeResult enviando todo el resultado obtenido
    geocoder.geocode({ 'address': direccion}, geocodeResult);
    //geocodeResult(resultado.results,resultado.status);
}

//Georeferenciación de la dirección

function buscarMapa(dir) {

    // Obtenemos la dirección y la asignamos a una variable
    var direccion = dir
    var resultado;
    
    $.ajax({ 
	    				url:'https://maps.googleapis.com/maps/api/geocode/json?components=administrative_area:Medellin|country:Colombia&address=' + direccion +'&key=AIzaSyBH1VN3540Ux8Y92wDv61horvr8SUqNd_s' , 
	    				dataType: 'json', 
	    				async: false, 
	    				success: function(data){ 
								resultado = data;
								
							} 
						});
    // Creamos el Objeto Geocoder
    var geocoder = new google.maps.Geocoder();
    // Hacemos la petición indicando la dirección e invocamos la función
    // geocodeResult enviando todo el resultado obtenido
    geocoder.geocode({ 'address': direccion}, geocodeResult);
    //geocodeResult(resultado.results,resultado.status);
}

function geocodeResult(results, status) {
    // Verificamos el estatus
    if (status == 'OK') {
        // Si hay resultados encontrados, centramos y repintamos el mapa
        // esto para eliminar cualquier pin antes puesto
        var mapOptions = {
            center: results[0].geometry.location,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        longitud = results[0].geometry.location.lng;
        latitud = results[0].geometry.location.lat;
        map = new google.maps.Map($("#mapas").get(0), mapOptions);
        // fitBounds acercará el mapa con el zoom adecuado de acuerdo a lo buscado
        map.fitBounds(results[0].geometry.viewport);
        // Dibujamos un marcador con la ubicación del primer resultado obtenido
        var ctaLayer = new google.maps.KmlLayer({
          url: 'https://raw.githubusercontent.com/Andres-FA/KMLZonasDeReparto/master/ZonasDeRepartoTotales.kml',
          map: map,
          zoom: 13
        });
        
        var markerOptions = { position: results[0].geometry.location }
        var marker = new google.maps.Marker(markerOptions);
        marker.setMap(map);
        
    } else {
        // En caso de no haber resultados o que haya ocurrido un error
        // lanzamos un mensaje con el error
        alert("Geocoding no tuvo éxito debido a: " + status);
    }
}

function consultarPedido() 
{

	var fechaini = $("#fechainicial").val();
	var fechafin = $("#fechafinal").val();
	var tienda = $("#selectTiendas").val();
	var numpedido = $("#numeropedido").val();
	if(fechaini == '' || fechaini == null)
	{
		alert ('La fecha inicial debe ser diferente a vacía');
		return;
	}

	if(fechafin == '' || fechafin == null)
	{
		alert ('La fecha final debe ser diferente a vacía');
		return;
	}
	if(existeFecha(fechaini))
	{
	}
	else
	{
		alert ('La fecha inicial no es correcta');
		return;
	}

	if(existeFecha(fechafin))
	{
	}
	else
	{
		alert ('La fecha final no es correcta');
		return;
	}
	if(validarFechaMenorActual(fechaini, fechafin))
	{
	}
	else
	{
		alert ('La fecha inicial es mayor a la fecha final, favor corregir');
		return;
	}

	if (tienda == '' || tienda == null)
	{

		alert ('La tienda no puede estar vacía');
		return;
	}
	// Si pasa a este punto es porque paso las validaciones
	if ( $.fn.dataTable.isDataTable( '#grid-encabezadopedido' ) ) {
    		table = $('#grid-encabezadopedido').DataTable();
    }
	$.getJSON(server + 'ConsultaIntegradaPedidos?fechainicial=' + fechaini +"&fechafinal=" + fechafin + "&tienda=" + tienda +  "&numeropedido=" + numpedido, function(data1){
	                		
	                		table.clear().draw();
							for(var i = 0; i < data1.length;i++){
								var cadaPedido  = data1[i];
								table.row.add(data1[i]).draw();
							}
							
					});


}


function existeFecha(fecha){
      var fechaf = fecha.split("/");
      var day = fechaf[0];
      var month = fechaf[1];
      var year = fechaf[2];
      var date = new Date(year,month,'0');
      if((day-0)>(date.getDate()-0)){
            return false;
      }
      return true;
}

function validarFechaMenorActual(date1, date2){
      var fechaini = new Date();
      var fechafin = new Date();
      var fecha1 = date1.split("/");
      var fecha2 = date2.split("/");
      fechaini.setFullYear(fecha1[2],fecha1[1]-1,fecha1[0]);
      fechafin.setFullYear(fecha2[2],fecha2[1]-1,fecha2[0]);
      
      if (fechaini > fechafin)
        return false;
      else
        return true;
}

