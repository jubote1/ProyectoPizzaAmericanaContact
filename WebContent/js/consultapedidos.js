	

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
var urlTienda ="";
var idformapago;
var totalpedido;
var valorformapago;
var stringPixel;


$(document).ready(function() {

	//Obtenemos el valor de la variable server
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	server = loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
	

	//Lo primero que realizaremos es validar si está logueado

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
            { "mData": "modespecialidad1" },
            { "mData": "especialidad2" },
            { "mData": "modespecialidad2" },
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
            { "mData": "fechainsercion" },
            { "mData": "cliente" },
            { "mData": "direccion" },
            { "mData": "telefono" },
            { "mData": "totalneto" },
            { "mData": "estadopedido" },
            { "mData": "usuariopedido" },
            { "mData": "enviadopixel"  , "visible": false },
            { "mData": "estadoenviotienda" },
            { "mData": "numposheader"  },
            { "mData": "formapago"  },
            { "mData": "tiempopedido"  },
            { "mData": "idtienda", "visible": false },
            { "mData": "urltienda", "visible": false },
            { "mData": "stringpixel", "visible": false }
        ]
    	} );

     
    $('#grid-encabezadopedido').on('click', 'tr', function () {
        datospedido = table.row( this ).data();
        //alert( 'Diste clic en  '+datos.nombre+'\'s row' );
        //$('#nombres').val(datos.nombre);
        idPedido = datospedido.idpedido;
        idCliente = datospedido.idcliente;
        urlTienda = datospedido.urltienda;
        stringPixel = datospedido.stringpixel;
        $('#NumPedido').val(idPedido);
        $('#Cliente').val(datospedido.cliente);
        $('#estadopedido').val(datospedido.estadopedido);
        var tempEstadoPedidoPixel = datospedido.enviadopixel;
        if (tempEstadoPedidoPixel == 0)
        {
        	$('#estadotienda').val("PENDIENTE TIENDA");
        	$("#estadotienda").attr("disabled", true).css("background-color","#FF0000");
        	$('#reenviarPedido').attr('disabled', false);
        }
        else
        {
        	$('#estadotienda').val("ENVIADO A TIENDA");
        	$("#estadotienda").attr("disabled", true).css("background-color","#00FF00");
        	$('#reenviarPedido').attr('disabled', true);
        }
        $('#numpedidotienda').val(datospedido.numposheader);
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
        $.getJSON(server + 'ConsultarDetallePedido?numeropedido=' + idPedido, function(data1){
	                		tabledetalle.clear().draw();
	                		for(var i = 0; i < data1.length;i++){
								tabledetalle.row.add(data1[i]).draw();
							}
	                		
							
					});

        //Obtenemos la forma de pago
        $.getJSON(server + 'ObtenerFormaPagoPedido?idpedido=' + idPedido, function(data2){
	                		var respuesta = data2[0];
							$('#totalpedido').val(data2[0].valortotal);
	                		$('#valorpago').val(data2[0].valorformapago);
	                		var valorDevolver =  data2[0].valorformapago - data2[0].valortotal;
	                		$('#valordevolver').val(valorDevolver);
	                		$('#formapago').val(data2[0].nombre);
	                		$('#descuento').val(data2[0].descuento);
	                		idformapago = data2[0].idformapago;
							totalpedido = data2[0].valortotal;
							valorformapago = data2[0].valorformapago;
							
					});


        //Obtenemos las marcaciones del Pedido
        $.getJSON(server + 'ObtenerMarcacionesPedido?idpedido=' + idPedido, function(data2){
	                		var respuesta = data2;
	                		var str = '<h1>Marcaciones Pedido</h1>';
        					str += '<table class="table table-bordered">';
							str += '<tbody>';
	                		for(var i = 0; i < respuesta.length;i++)
							{
								var cadaMarcacion  = respuesta[i];
								str +='<tr> ';
								str +='<td> ';
								str += '<label>Marcacion<input type="text" aria-label="..."' + '  value="'+ cadaMarcacion.nombremarcacion + '" id="' + cadaMarcacion.idmarcacion + '" disabled></label>';
								str += '</td>';
								str +='<td> ';
								str += '<label>Observacion<input type="text" ' + '" id="txtObsMarcacion' + cadaMarcacion.idmarcacion + '" value="'+ cadaMarcacion.observacion + '" name= "txtObsMarcacion' + cadaMarcacion.idmarcacion +'" maxlength="50" disabled></label>';
								str += '</td>';
					            str +='<td> ';
					            str += '<label>Descuento<input type="text" ' + ' id="txtDescuento' + cadaMarcacion.idmarcacion + '" name= "txtDescuento'  + cadaMarcacion.idmarcacion +'" maxlength="50" value="'+ cadaMarcacion.descuento + '" disabled></label>';
					            str += '</td>';
					            str +='<td> ';
					            str += '<label>Motivo<input type="text" ' + '" id="txtMotivo' + cadaMarcacion.idmarcacion + '" name= "txtMotivo'  + cadaMarcacion.idmarcacion +'" maxlength="50" value="'+ cadaMarcacion.motivo + '"" disabled>'  +'</label>';
					            str += '</td>';
								str += '</tr>';
							}
							$('#marcacionesPedido').html(str);
					});
     

     } );
 	

 	
 	
     

	} );


$(function(){
	
	getListaTiendas();
	getExcepcionesPrecios();
	setInterval('validarVigenciaLogueo()',600000);
	
});



function validarVigenciaLogueo()
{
	var d = new Date();
	
	var respuesta ='';
	$.ajax({ 
	   	url: server + 'ValidarUsuarioAplicacion', 
	   	dataType: 'json',
	   	type: 'post', 
	   	async: false, 
	   	success: function(data){
			    respuesta =  data[0].respuesta;		
		} 
	});
	switch(respuesta)
	{
		case 'OK':
				break;
		case 'OKA':
				break;	
		default:
				location.href = server +"Index.html";
		    	break;
	}
		    		
}

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
		str +='<option value="'+ 'TODAS' +'" id ="'+ 'TODAS' +'">' + 'TODAS' +'</option>';
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
	    				url:'https://maps.googleapis.com/maps/api/geocode/json?components=administrative_area:Medellin|country:Colombia&address=' + direccion +'&key=AIzaSyCRtUQ2WV0L2gMnb9DKiFn1PTHJQLH3suA' , 
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

//Georeferenciación de la dirección

function buscarMapa(dir) {

    // Obtenemos la dirección y la asignamos a una variable
    var direccion = dir
    var resultado;
    
    $.ajax({ 
	    				url:'https://maps.googleapis.com/maps/api/geocode/json?components=administrative_area:Medellin|country:Colombia&address=' + direccion +'&key=AIzaSyCRtUQ2WV0L2gMnb9DKiFn1PTHJQLH3suA' , 
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

function enviarPedidoTienda(){

									//Rediseños para mejorar las cosas
									$.confirm({
										'title'		: 'Confirmacion de Reenvío de Pedido',
										'content'	: 'Desea confirmar el reenvío del Pedido Número ' + idPedido + '<br> El Pedido pasará a estado  Finalizado'+
										'Con la siguiente información: <br>' +
										'CLIENTE: ' + $('#nombres').val() + ' ' + '<br>' +
										'DIRECCION ' +  $('#direccion').val() + '<br>' +
										'TOTAL PEDIDO ' + $("#totalpedido").val() + '<br>' +
										'CAMBIO ' + $("#valorpago").val() + '<br>' +
										'TIENDA DEL PEDIDO ' +  '<h1>' + $("#tienda").val().toUpperCase() + '</h1> <br>',
										'type': 'dark',
						   				'typeAnimated': true,
										'buttons'	: {
											'Si'	: {
												'class'	: 'blue',
												'action': function(){
													//OJO var idformapago =  $("#selectformapago").val();
													var formapago =  $("#formapago").val();
													var valorformapago =  $("#valorpago").val();
													var insertado = 0;
													$.ajax({ 
								    				url: server + 'FinalizarPedidoReenvio?idpedido=' + idPedido + "&idformapago=" + idformapago + "&valortotal=" + totalpedido + "&valorformapago=" + valorformapago + "&idcliente=" + idCliente + "&insertado=" + insertado , 
								    				dataType: 'json', 
								    				async: false, 
								    				success: function(data){ 

															resultado = data[0];
															var resJSON = JSON.stringify(resultado);
															var urlTienda = resultado.url;
															//Mandamos todos los párametros para la inserción de la tienda
															//Ejecutamos el servicio para insertar en Pixel

															//OJO CAMBIOS PARA EL SERVICIO CON LOS PARÁMETROS Y NO SERÁ AJAX SINO JSON ES DECIR ASINCRONO
															$.ajax({ 
													    				url: urlTienda + 'FinalizarPedidoPixel' , 
													    				dataType: 'json', 
													    				type: 'post', 
							    										data: {'datos' : resJSON }, 
													    				async: false, 
													    				success: function(data1){ 
																		var resPedPixel = data1[0];
																		if(resPedPixel.numerofactura > 0)
												    					{
												    						$.ajax({ 
														    				url: server + 'ActualizarNumeroPedidoPixel?idpedido=' + resPedPixel.idpedido + '&numpedidopixel=' + resPedPixel.numerofactura +  '&creacliente=' + resPedPixel.creacliente +  '&membercode=' + resPedPixel.membercode + '&idcliente=' + resPedPixel.idcliente, 
															    				dataType: 'json', 
															    				async: false, 
															    				success: function(data){
															    					var resul =  data[0];
															    					if (resul.resultado)
															    					{
															    						$('#estadotienda').val("ENVIADO A TIENDA");
								        												$("#estadotienda").attr("disabled", true).css("background-color","#00FF00");
								        												$('#reenviarPedido').attr('disabled', true);
								        												$('#numpedidotienda').val(resPedPixel.numerofactura);
								        												alert("El pedido se ha enviado satisfactoriamente a la tienda");
															    					}
															    				},
																				error: function(){
																				    alert('Se produjo error en la actualización del número del pedido de la tienda en el sistema central');
																				    //Posiblemente aca sería necesario actualizar el estado
																				 } 

																			});
												    					}
												    					else if(resPedPixel.numerofactura == -1)
												    					{
												    						$.alert('No se ha iniciado el día de facturación en la tienda, por favor comuniquese con la misma, el pedido en cuestión no fue enviado' );
												    					}
																		console.log("numero pedido pixel " +resPedPixel.numerofactura);
																	} 
															});
														},
														error: function(){
														    alert('Se produjo un error en la inserción del Pedido, favor revisar logs y reintentar');
														 } 

													});
													


												}
											},
											'No'	: {
												'class'	: 'gray',
												'action': function(){}	// Nothing to do in this case. You can as well omit the action property.
											}
										}
									});
									consultarPedido();
									
									
}