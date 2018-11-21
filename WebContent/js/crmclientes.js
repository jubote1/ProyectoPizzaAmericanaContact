	

// Se definen las variables globales.
var server;
var tiendas;
var table;
var dtpedido;
var dtEncabezadoPedido;
var dtDetallePedido;
var productos;
var excepciones;
var memcode = 0;
var insertado = 0;
var idCliente = 0;
var idEstadoPedido = 0;
var longitud = 0;
var latitud = 0;
var marcadorAdiciones = 0;
var marcadorAdicionales = 0;
var marcadorGaseosas = 0;
var marcardorProductoCon = 0;
var marcardorProductoSin = 0;
var canvas;
var ctx;
var totalpedido = 0;
var radioEsp1Ant= 0;
var radioEsp1 = 0;
var radioEsp2Ant= 0;
var radioEsp2 = 0;
var contadorItem = 1;
var controlaCantidadIngredientes = "N";
var cantidadIngredientes = 0;
var excepcionSeleccionada = null;
var gaseosaHomologadaTienda;
var formas;
var tableOfertaCliente;


// A continuación  la ejecucion luego de cargada la pagina
$(document).ready(function() 
{
   	getListaTiendas();
	getListaMunicipios();
	getListaNomenclaturas();
    llenarSelectOferta();
	setInterval('validarVigenciaLogueo()',600000);
	$('#descDireccion').attr('disabled', true);
	// Llevamos a cero los campos cálculos de los totales
	$('#ultimospedidos').attr('disabled', true);
	// Se define evento para campo valor a devolver.
	// Se realiza la creación del DATATABLE DE CLIENTES
    table = $('#grid-clientes').DataTable( {
    		"aoColumns": [
            { "mData": "idCliente" },
            { "mData": "tienda" },
            { "mData": "nombre" },
            { "mData": "apellido" },
            { "mData": "nombrecompania" },
            { "mData": "direccion" },
            { "mData": "zona" },
            { "mData": "observacion" },
            { "mData": "telefono" },
            { "mData": "latitud" , "visible": false },
            { "mData": "longitud" , "visible": false },
            { "mData": "memcode"  , "visible": false }
        ]
    	} );

    tableOfertaCliente = $('#grid-ofertascliente').DataTable( {
            "aoColumns": [
            { "mData": "idofertacliente" },
            { "mData": "idcliente", "visible": false },
            { "mData": "idoferta", "visible": false },
            { "mData": "nombreoferta" },
            { "mData": "utilizada" },
            { "mData": "ingresooferta" },
            { "mData": "usooferta" },
            { "mData": "observacion" },
            {
                "mData": "accion",
                className: "center",
                defaultContent: '<input type="button" class="btn btn-default btn-xs" onclick="eliminarOfertaCliente()" value="Eliminar"></button>'
            }
        ]
        } );

    //Inicializamos los DataTables para historia de pedidos cliente
    dtDetallePedido = $('#grid-detallepedido').DataTable( {
            "aoColumns": [
            { "mData": "iddetallepedido" },
            { "mData": "nombreproducto" },
            { "mData": "cantidad" },
            { "mData": "especialidad1" },
            { "mData": "especialidad2" },
            { "mData": "valorunitario" , "visible": false},
            { "mData": "valortotal" },
            { "mData": "adicion" },
            { "mData": "observacion" },
            { "mData": "liquido" },
            { "mData": "excepcion" }
            
            
        ]
        } );

    dtEncabezadoPedido = $('#grid-encabezadopedido').DataTable( {
            "aoColumns": [
            { "mData": "idpedido" },
            { "mData": "tienda" },
            { "mData": "fechainsercion" },
            { "mData": "cliente" },
            { "mData": "direccion" },
            { "mData": "telefono" },
            { "mData": "totalneto" , "visible": false},
            { "mData": "estadopedido" , "visible": false },
            { "mData": "usuariopedido" , "visible": false },
            { "mData": "enviadopixel"  , "visible": false },
            { "mData": "estadoenviotienda" , "visible": false },
            { "mData": "numposheader" , "visible": false  },
            { "mData": "formapago" , "visible": false  },
            { "mData": "tiempopedido" , "visible": false },
            { "mData": "idtienda", "visible": false },
            { "mData": "urltienda", "visible": false },
            { "mData": "stringpixel", "visible": false }
        ]
        } );

     



    $('#grid-clientes tbody').on('click', 'tr', function() 
    {
        datos = table.row( this ).data();
        $("#selectTiendas").val(datos.tienda);
        var idtien = $("#selectTiendas option:selected").attr('id');
        //alert( 'Diste clic en  '+datos.nombre+'\'s row' );
        $('#selectTiendas').attr('disabled', true);
        $('#nombres').val(datos.nombre);
        $('#apellidos').val(datos.apellido);
        $('#nombreCompania').val(datos.nombrecompania);
        $('#direccion').val(datos.direccion);
        $('#zona').val(datos.zona);
        $('#observacionDir').val(datos.observacion);
        // Para evitar que modifiquen la tienda
        $("#selectNomenclaturas").val(datos.nomenclatura);
        $("#selectMunicipio").val(datos.municipio);
        $('#numNomen').val(datos.numnomenclatura1);
        $('#numNomen2').val(datos.numnomenclatura2);
        $('#num3').val(datos.num3);
        var selMunicipio = $("#selectMunicipio").val();
        if (selMunicipio == '' || selMunicipio == null || selMunicipio == 'null')
        {
        	selMunicipio = '';
        }
        $('#descDireccion').val($("#selectNomenclaturas").val() +  " "  + $("#numNomen").val() + " # " + $("#numNomen2").val() + " - " + $("#num3").val() + " " + selMunicipio);
        memcode = datos.memcode;
        idCliente = datos.idCliente;
        //Se invoca servicio para traer las promociones del cliente seleccionado
        pintarOfertasCliente(idCliente);
        $('#agregarOferta').attr('disabled', false);
        var municipio = datos.municipio;
        //Traemos los valores de la latitud y longitud 
        latitud = datos.latitud;
        longitud = datos.longitud;
        //En este punto luego de buscar la direccion del cliente, buscaremos si este tiene pedidos
        $('#ultimospedidos').attr('disabled', false);

    });
    $('#agregarOferta').attr('disabled', true);
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



function descripcionDireccion()
{
	var selMunicipio = $("#selectMunicipio").val();
    if (selMunicipio == '' || selMunicipio == null || selMunicipio == 'null')
    {
        selMunicipio = '';
    }
	$('#descDireccion').val($("#selectNomenclaturas").val() +  " "  + $("#numNomen").val() + " # " + $("#numNomen2").val() + " - " + $("#num3").val() + " " + selMunicipio);	
}

// Método que se encarga luego de introducido un teléfono en el campo de teléfono del cliente llamar al servicio
function validarTelefono(){

	//Incluimos validación del logueo dado que es un punto crítico para retomar los pedidos
	validarVigenciaLogueo();
	// Validamos el tema de la longitud del pedido
	var lngTel = $("#telefono").val().length;
	if (lngTel < 10)
	{
		alert("Tenga precaución el teléfono tiene una lontitud menor a 10");
	}

	if (!/^([0-9])*$/.test($("#telefono").val()))
	{
      alert("El valor " + $("#telefono").val() + " no es un número");
	}
	// Validamos que sean solo números
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





// Método que invoca el servicio para listar las tiendas donde se pondrán tomar domicilios.
function getListaTiendas(){
	$.getJSON(server + 'GetTiendas', function(data){
		tiendas = data;
		var str = '';
		for(var i = 0; i < data.length;i++){
			var cadaTienda  = data[i];
			str +='<option value="'+ cadaTienda.nombre +'" id ="'+ cadaTienda.id +'">' + cadaTienda.nombre +'</option>';
		}
		$('#selectTiendas').html(str);
		// Realizamos cambio para que la tienda no esté seleccionada por defecto
		$("#selectTiendas").val('');
	});
}

function getListaNomenclaturas(){
	$.getJSON(server + 'GetNomenclaturasDireccion', function(data){
		var nomenclaturas = data;
		var str = '';
		for(var i = 0; i < data.length;i++){
			var cadaNomen = data[i];
			str +='<option value="'+ cadaNomen.nomenclatura +'" id ="'+ cadaNomen.idnomenclatura +'">' + cadaNomen.nomenclatura +'</option>';
		}
		$('#selectNomenclaturas').html(str);
		// Realizamos cambio para que la tienda no esté seleccionada por defecto
		$("#selectNomenclaturas").val('');
	});
}

//Método que invoca el servicio para obtener lista de municipios parametrizados en el sistema
function getListaMunicipios(){

	$.getJSON(server + 'CRUDMunicipio?idoperacion=5', function(data){
		
		var str = '';
		for(var i = 0; i < data.length;i++){
			var cadaMunicipio  = data[i];
			str +='<option value="'+ cadaMunicipio.nombre +'" id ="'+ cadaMunicipio.idmunicipio +'">' + cadaMunicipio.nombre +'</option>';
		}
		$('#selectMunicipio').html(str);
		$("#selectMunicipio").val('');
	});

}



//Métodos para obtener todos los productos y con esto se realizan todas las validaciones de producto dentro de la capa de presentación.


function actualizarCliente()
{
	var tempTienda =  $("#selectTiendas option:selected").val();
    //Variable en la que alojamos el id de la tienda
    var tienda = $("#selectTiendas option:selected").attr('id');
    var tempMunicipio = $("#selectMunicipio option:selected").val();
    var idClienteTemporal;
    var nombresEncode = encodeURIComponent(nombres.value);
    var apellidosEncode = encodeURIComponent(apellidos.value);
    var nombreCompaniaEncode = encodeURIComponent(nombreCompania.value);;
    var direccionEncode;
    var validaDir = 'S';
    var dirTemp = $("#selectNomenclaturas").val() +  " "  + $("#numNomen").val() + " # " + $("#numNomen2").val() + " - " + $("#num3").val();
    if($('#validaDir').is(':checked'))
    {
        direccionEncode = encodeURIComponent($("#selectNomenclaturas").val() +  " "  + $("#numNomen").val() + " # " + $("#numNomen2").val() + " - " + $("#num3").val());
    }else
    {
        validaDir = 'N';
        direccionEncode = encodeURIComponent(direccion.value);
    }
    var zonaEncode = encodeURIComponent(zona.value); 
    var observacionDirEncode = encodeURIComponent(observacionDir.value);
    var nomenclatura = $("#selectNomenclaturas option:selected").attr('id');
    var numNomenclatura1 = encodeURIComponent($("#numNomen").val());
    var numNomenclatura2 = encodeURIComponent($("#numNomen2").val());
    var num3 = encodeURIComponent($("#num3").val());
    $.confirm({
                'title'     : 'Confirmacion de Creación/Actualización de Cliente',
                'content'   : 'Desea confirmar la creación/actualización del cliente ' + nombres.value + " " + apellidos.value +
                '. Con la siguiente información: <br>' +
                'DIRECCION ' +  dirTemp  + '<br>' +
                'MUNICIPIO ' + tempMunicipio + '<br>' +
                'NOMBRE COMPAÑIA ' + nombreCompania.value + '<br>' +
                'TIENDA' +  '<h1>' + tempTienda.toUpperCase() + '</h1> <br>',
                'type': 'dark',
                'typeAnimated': true,
                'buttons'   : {
                    'Si'    : {
                        'class' : 'blue',
                        'action': function()
                        {
                            //Ejecutamos la actualización
                            $.ajax({ 
                                url: server + 'ActualizarCliente?telefono=' + telefono.value + "&nombres=" + nombresEncode + "&apellidos=" + apellidosEncode + "&nombreCompania=" + nombreCompaniaEncode +  "&direccion="  + direccionEncode  + "&tienda=" + tempTienda +  "&zona=" + zonaEncode + "&observacion=" + observacionDirEncode + "&municipio=" + tempMunicipio + "&longitud=" + longitud + "&latitud=" + latitud + "&memcode=" + memcode + "&idcliente=" + idCliente + "&idnomenclatura=" + nomenclatura + "&numnomenclatura1=" + numNomenclatura1 + "&numnomenclatura2=" + numNomenclatura2 +  "&num3=" + num3, 
                                dataType: 'json', 
                                async: false, 
                                success: function(data){ 
                                    idClienteTemporal = data[0].idcliente;
                                    console.log("CLIENTE CREADO ACTUALIZADO " + idClienteTemporal);
                                    var urlTienda;
                                    var pos;
                                    var dsnodbc;
                                     $.ajax({ 
                                         url: server + 'ObtenerUrlTienda?idtienda=' + tienda, 
                                         dataType: 'json', 
                                         async: false, 
                                         success: function(data2){ 
                                            console.log(data2);
                                            urlTienda = data2[0].urltienda;
                                            pos = data2[0].pos;
                                            dsnodbc = data2[0].dsnodbc;
                                        } 
                                    });
                                    //Se consume el servicio de la tienda para la actualización creación del cliente
                                    $.getJSON(urlTienda + 'ActualizarCliente?telefono=' + telefono.value + "&nombres=" + nombresEncode + "&apellidos=" + apellidosEncode + "&nombreCompania=" + nombreCompaniaEncode +  "&direccion="  + direccionEncode  + "&tienda=" + tempTienda +  "&zona=" + zonaEncode + "&observacion=" + observacionDirEncode + "&municipio=" + tempMunicipio + "&longitud=" + longitud + "&latitud=" + latitud + "&memcode=" + memcode + "&idcliente=" + idCliente + "&idnomenclatura=" + nomenclatura + "&numnomenclatura1=" + numNomenclatura1 + "&numnomenclatura2=" + numNomenclatura2 +  "&num3=" + num3 + "&dsnodbc=" + dsnodbc + "&pos=" + pos , function(data1){
                                        console.log(data1);
                                    });
                                    // En este put
                                    if(idClienteTemporal == idCliente)
                                    {
                                        $.alert('Se Actualizó el cliente correctamente');
                                    }else if(idClienteTemporal != idCliente)
                                    {
                                        idCliente = idClienteTemporal;
                                        $.alert('Se creó el nuevo cliente correctamente');
                                    }

                                } 
                            });   
                        }
                    },
                    'No'    : {
                        'class' : 'gray',
                        'action': function(){}  // Nothing to do in this case. You can as well omit the action property.
                    }
                }
            });
    

}



function limpiarSeleccionCliente()
{
		$('#telefono').val("");
		$('#nombres').val("");
        $('#apellidos').val("");
        $('#nombreCompania').val("");
        $('#direccion').val("");
        $('#zona').val("");
        $('#observacionDir').val("");
        $("#selectTiendas").val("");
        $("#selectNomenclaturas").val('');
        $('#numNomen').val("");
        $('#numNomen2').val("");
        $('#num3').val("");
        $('#descDireccion').val("");
        //reiniciamos el arreglo de productos
        //Volvemos  a habiliar el select de tiendas si es qúe está deshabilitado
        $('#selectTiendas').attr('disabled', false);
        $("#selectMunicipio").val("");
        $('#validaDir').prop('checked', true);
        $('#ultimospedidos').attr('disabled', true);
        $('#agregarOferta').attr('disabled', true);
        //$("#validaDir input[type=checkbox]").prop('checked', true);
        memcode = 0;
        idCliente = 0;
        if ( $.fn.dataTable.isDataTable( '#grid-clientes' ) ) 
        {
			table = $('#grid-clientes').DataTable();
			table.clear().draw();
		}
}


function consultarUltimosPedidos()
{
		limpiarModalOtrosPedidos();
    	$('#grid-encabezadopedido').on('click', 'tr', function () {
    	var datosPedidoClick = dtEncabezadoPedido.row( this ).data();
        $('#NumPedidoClick').val(datosPedidoClick.idpedido);
        $('#ClienteClick').val(datosPedidoClick.cliente);
        $('#estadopedidoClick').val(datosPedidoClick.estadopedido);
        var tempEstadoPedidoPixel = datosPedidoClick.enviadopixel;
        if (tempEstadoPedidoPixel == 0)
        {
        	$('#estadotiendaClick').val("PENDIENTE TIENDA");
        	$("#estadotiendaClick").attr("disabled", true).css("background-color","#FF0000");
        }
        else
        {
        	$('#estadotiendaClick').val("ENVIADO A TIENDA");
        	$("#estadotiendaClick").attr("disabled", true).css("background-color","#00FF00");
        }
        $('#numpedidotiendaClick').val(datosPedidoClick.numposheader);
        // La idea es tomar el id pedido seleccionado y con esto ir a buscar la información.
        
        $.getJSON(server + 'GetClientePorID?idcliente=' + datosPedidoClick.idcliente, function(data1){
	                		
	                		$('#telefonoClick').val(data1[0].telefono);
	                		$('#nombresClick').val(data1[0].nombrecliente);
	                		$('#direccionClick').val(data1[0].direccion);
	                		$('#municipioClick').val(data1[0].nombremunicipio);
	                		$('#zonaClick').val(data1[0].zona);
	                		$('#observacionDirClick').val(data1[0].observacion);
	                		$('#tiendaClick').val(data1[0].nombretienda);

							
					});
        if ( $.fn.dataTable.isDataTable( '#grid-detallepedido' ) ) {
    		dtDetallePedido = $('#grid-detallepedido').DataTable();
    	}
        $.getJSON(server + 'ConsultarDetallePedido?numeropedido=' + datosPedidoClick.idpedido, function(data1){
	                		dtDetallePedido.clear().draw();
	                		for(var i = 0; i < data1.length;i++){
								dtDetallePedido.row.add(data1[i]).draw();
							}
	                		
							
					});

        //Obtenemos la forma de pago
        $.getJSON(server + 'ObtenerFormaPagoPedido?idpedido=' + datosPedidoClick.idpedido, function(data2){
	                		$('#totalpedidoClick').val(data2[0].valortotal);
	                		$('#valorpagoClick').val(data2[0].valorformapago);
	                		var valorDevolver =  data2[0].valorformapago - data2[0].valortotal;
	                		$('#valordevolverClick').val(valorDevolver);
	                		$('#formapagoClick').val(data2[0].nombre);
	                });
     

     } );
	$.getJSON(server + 'ConsultaUltimosPedidosCliente?idcliente=' + idCliente, function(data1){
	                		
	                		dtEncabezadoPedido.clear().draw();
							for(var i = 0; i < data1.length;i++){
								dtEncabezadoPedido.row.add(data1[i]).draw();
							}
							
					});
	$('div').click( function( e ) {
		e.stopPropagation();
	});
	$('#ultimosPedidosCliente').modal('show');
}

//Método que se encarga de limpiar la información de los pedidos clientes cada vez que se da sobre el botón consultar
function limpiarModalOtrosPedidos()
{
	$('#NumPedidoClick').val('');
    $('#ClienteClick').val('');
    $('#estadopedidoClick').val('');
    $('#estadotiendaClick').val('');
    $('#numpedidotiendaClick').val('');
    $('#telefonoClick').val('');
	$('#nombresClick').val('');
	$('#direccionClick').val('');
	$('#municipioClick').val('');
	$('#zonaClick').val('');
	$('#observacionDirClick').val('');
	$('#tiendaClick').val('');
	dtDetallePedido.clear().draw();
	$('#totalpedidoClick').val('');
	$('#valorpagoClick').val('');
	$('#valordevolverClick').val('');
	$('#formapagoClick').val('');
}

function initMap() {
        

        
        var map = new google.maps.Map(document.getElementById("mapas"), {
          zoom: 13,
          scrollwheel: false,
          center: {lat: 6.29139, lng: -75.53611}
        });

       
      }



// Evento para cuando se da  CLICK EN EL BOTÓN BUSCAR
function buscarMapaDigitado() {

    // Obtenemos la dirección y la asignamos a una variable
    var direccion = $('#direccion').val();
    var municipio = $("#selectMunicipio").val();
    municipio = municipio.toLowerCase();
    direccion = direccion + " " + municipio + " Antioquia Colombia";
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

// Método para el nuevo esquema de direcciones
function buscarMapaDigitado1() {

    //Se valida si es el esquema viejo de direcciones
    if($("#selectNomenclaturas").val() == '' || $("#selectNomenclaturas").val() == null || $("#numNomen").val() == '' || $("#numNomen").val() == null || $("#numNomen2").val() == '' || $("#numNomen2").val() == null )
        {
            if(($('#direccion').val() != null) && ($('#direccion').val() != '') && ($("#selectMunicipio").val() != null) && ($("#selectMunicipio").val() != ''))
            {
                buscarMapaDigitado();
            }
            return;
        }

        
    // Obtenemos la dirección y la asignamos a una variable
    var direccion = $("#selectNomenclaturas").val() +  " "  + $("#numNomen").val() + " # " + $("#numNomen2").val() ;
    var municipio = $("#selectMunicipio").val();
    if(municipio == '' || municipio== null)
    {
        $.alert('Debe ingresar el municipio para buscar la dirección.');
        return;
    }
    municipio = municipio.toLowerCase();
    direccion = direccion + " " + municipio + " Antioquia Colombia";
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
    var direccion = dir + " Antioquia Colombia" ; 
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
        longitud = results[0].geometry.location.lng();
        latitud = results[0].geometry.location.lat();
        map = new google.maps.Map($("#mapas").get(0), mapOptions);
        // fitBounds acercará el mapa con el zoom adecuado de acuerdo a lo buscado
        map.fitBounds(results[0].geometry.viewport);
        // Dibujamos un marcador con la ubicación del primer resultado obtenido
        //url: 'https://raw.githubusercontent.com/Andres-FA/KMLZonasDeReparto/master/ZonasDeRepartoTotales.kml',
        var ctaLayer = new google.maps.KmlLayer({
          url: 'https://raw.githubusercontent.com/Andres-FA/KMLZonasDeReparto/master/PizzaAmericana-ZonasDeRepartoTotales-Ver_02.kml',
          map: map,
          scrollwheel: false,
          zoom: 17
        });
        
        var markerOptions = { position: results[0].geometry.location }
        var marker = new google.maps.Marker(markerOptions);
        marker.setMap(map);
        //Luego de la ubicación en el mapa trataremos de ejecutar una función asincrona para ubicar dentro del mapa y ubicar la tienda
        ubicarTienda(latitud , longitud , map);
        
    } else {
        // En caso de no haber resultados o que haya ocurrido un error
        // lanzamos un mensaje con el error
        alert("La Geolocalización no tuvo éxito debido a: " + status);
    }
}

function geocodeSinServicio(lat, long) 
{
    longitud = long;
    latitud = lat;
    var map = new google.maps.Map($("#mapas").get(0), {
        zoom: 7,
        center: new google.maps.LatLng(6.22339, -75.6281),
        mapTypeId: google.maps.MapTypeId.ROADMAP
    });

    var ctaLayer = new google.maps.KmlLayer({
        url: 'https://raw.githubusercontent.com/Andres-FA/KMLZonasDeReparto/master/PizzaAmericana-ZonasDeRepartoTotales-Ver_02.kml',
        map: map,
        scrollwheel: false,
        zoom: 17
    });
    var infowindow = new google.maps.InfoWindow();
    var marker, i;
    marker = new google.maps.Marker({
        position: new google.maps.LatLng(latitud, longitud),
        map: map,
        icon: 'http://maps.google.com/mapfiles/ms/icons/green-dot.png'
    });
}

function llenarSelectOferta()
{
    
    $.ajax({ 
                        url: server + 'CRUDOferta'+'?idoperacion=5' , 
                        dataType: 'json', 
                        async: false, 
                        success: function(data1)
                            { 
                                var str = '';
                                for(var i = 0; i < data1.length;i++){
                                    str +='<option value="'+ data1[i].idoferta +'" id ="'+ data1[i].idoferta +'">' + data1[i].nombreoferta + '</option>';
                                }
                                $('#selectOferta').html(str);
                                                                                    
                            } 
                        });

    
}

function guardarOfertaCliente()
{
    
    
    var idoferta = $('#selectOferta').val();
    var observacionOferta = $('#observacionOferta').val();
    $.getJSON(server + 'CRUDOfertaCliente?idoperacion=1&idcliente=' + idCliente + "&idoferta=" + idoferta + "&observacion=" + observacionOferta, function(data){
        var respuesta = data[0];
        var idofertacliente = respuesta.idoferta;
                
    });
    $('#selectOferta').val('');
    $('#addData').modal('hide');
    pintarOfertasCliente(idCliente);
}

function pintarOfertasCliente(idcli)
{
    $.getJSON(server + 'CRUDOfertaCliente?idoperacion=5&idcliente=' + idcli, function(data1){
            tableOfertaCliente.clear().draw();
            for(var i = 0; i < data1.length;i++){
                tableOfertaCliente.row.add({
                    "idofertacliente": data1[i].idofertacliente, 
                    "idcliente": data1[i].idcliente,
                    "idoferta": data1[i].idoferta,
                    "nombreoferta": data1[i].nombreoferta, 
                    "utilizada": data1[i].utilizada,
                    "ingresooferta": data1[i].ingresooferta, 
                    "usooferta": data1[i].usooferta,
                    "observacion": data1[i].observacion, 
                    "accion":'<input type="button" class="btn btn-default btn-xs" onclick="eliminarOfertaCliente(' +data1[i].idofertacliente + ')" value="Eliminar"></button> <input type="button" onclick="usarOferta('+data1[i].idofertacliente+')" class="btn btn-default btn-xs editButton" ' + 'data-id="' + data1[i].idofertacliente + '" value="Usar Oferta"></button>'
                }).draw();
            }
        });
   
}


function usarOferta(idofertacliente)
{
    
           
            
            // The url and method might be different in your application
            $.ajax({ 
                    url: server + 'CRUDOfertaCliente?idoperacion=2&idofertacliente='+ idofertacliente , 
                    dataType: 'json', 
                    async: false, 
                    success: function(data){
                        pintarOfertasCliente(idCliente);
                         // Hide the dialog
                                              
                    } 
            }); 
            //
            

        

}

function eliminarOfertaCliente(idofertacliente)
{
    /*var table = $('#grid-especialidades').DataTable();
    var datos = table.row( this ).data();
    console.log(datos);
    idespecialidad = datos.idespecialidad;
    nombre = datos.nombre;*/
    $.confirm({
            'title'     : 'Confirmacion Eliminación de la Oferta Cliente',
            'content'   : 'Desea confirmar la eliminación de la Oferta Cliente ' + idofertacliente+ '.',
            'buttons'   : {
                'Si'    : {
                    'class' : 'blue',
                    'action': function(){
                    
                        var resultado
                        $.ajax({ 
                        url: server + 'CRUDOfertaCliente?idoperacion=3&idofertacliente=' + idofertacliente , 
                        dataType: 'json', 
                        async: false, 
                        success: function(data){ 
                                resultado = data[0];
                                //

                                if ( $.fn.dataTable.isDataTable( '#grid-ofertascliente' ) ) {
                                    tableOfertaCliente = $('#grid-ofertascliente').DataTable();
                                }
                                
                                pintarOfertasCliente(idCliente);

                                //

                                
                            } 
                        });
                        


                    }
                },
                'No'    : {
                    'class' : 'gray',
                    'action': function(){}  // Nothing to do in this case. You can as well omit the action property.
                }
            }
        });
}