	

// Se definen las variables globales.
var server;
var tiendas;
var tiendasBloqueadas;
var marcaciones;
var productosIncluidos;
var table;
var dtpedido;
var dtEncabezadoPedido;
var dtDetallePedido;
var productos;
var excepciones;
var idPedido = 0;
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
var productoGaseosaHomologadaTienda;
var formas;
var idOfertaCliente;

// A continuación  la ejecucion luego de cargada la pagina
$(document).ready(function() {

			

//validamos el contenido del campo fecha del pedido y el evento que lo controlará
$("#fechapedido").change(function(){
    var fechaped = $("#fechapedido").val();
    if(existeFecha(fechaped))
	{
	}
	else
	{
		alert ('La fecha del Pedido no es correcta');
		$("#fechapedido").datepicker('setDate', new Date());
		return;
	}

	if(validarFechaMenorActual(fechaped))
	{
	}
	else
	{
		alert ('La fecha del pedido es menor a la fecha actual, favor corregir');
		$("#fechapedido").datepicker('setDate', new Date());
		return;
	}
});


	//Se invoca servicio para traerse la información de los productos disponibles en el sistema
	// En resumen se invocan todos servicios que se encargan de llenar la data del formulario.
	//getTodosProductos();
	
	getListaTiendas();
	//Obtenemos las homologaciones para los liquidos
	getHomologacionGaseosaTienda();
	getListaMunicipios();
	getExcepcionesPrecios();
	getFormasPago();
	getMarcaciones();
	obtenerProductosIncluidos();
	getListaNomenclaturas();
	setInterval('validarVigenciaLogueo()',600000);
	obtenerTiendasBloqueadas();
	setInterval('obtenerTiendasBloqueadas',300000);
	$('#descDireccion').attr('disabled', true);
	//Deshabilitamos el botón de transferir cliente
	$('#transferircliente').attr('disabled', true);
	// Llevamos a cero los campos cálculos de los totales
	$("#totalpedido").val('0');
	$("#valorpago").val('0');
	$("#valordevolver").val('0');
	$('input:radio[name=adicion]')[1].checked = true;
	$('#ultimospedidos').attr('disabled', true);
	// Se define evento para campo valor a devolver.
	$("#valorpago").change(function(){
			$("#valordevolver").val($("#valorpago").val() - $("#totalpedido").val() );
            
	});

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

    // Se realiza la creación del datatable detalle de pedido

    dtpedido = $('#grid-pedido').DataTable( {
    		"aoColumns": [
    		{ "mData": "iddetallepedido" },
    		{ "mData": "numitem" , "visible": false },
            { "mData": "pizza" },
            { "mData": "otroprod" },
            { "mData": "cantidad" },
            { "mData": "especialidad1" },
            { "mData": "especialidad2" },
            { "mData": "liquido" },
            { "mData": "adicion" },
            { "mData": "observacion" },
            { "mData": "valorunitario" },
            { "mData": "valortotal" },
            {
                "mData": "accion",
                className: "center",
                defaultContent: '<input type="button" class="btn btn-default btn-xs" onclick="eliminarDetallePedido()" value="Eliminar"></button>'
            },
            {
                "mData": "accion2",
                className: "center",
                defaultContent: '<input type="button" class="btn btn-default btn-xs" onclick="duplicarDetallePedido()" value="Duplicar"></button>'
            }
        ],
        	"order": [[ 0, "desc" ]],
        	"fnRowCallback": function( nRow, aData, iDisplayIndex ) {
        		switch(aData.numitem){
		            case 1:
		                $(nRow).css('background-color', '#FF974D')
		                break;
		            case 2:
		                $(nRow).css('background-color', '#C7F94C')
		                break;
		            case 3:
		                $(nRow).css('background-color', '#FF974D')
		                break;
		            case 4:
		                $(nRow).css('background-color', '#C7F94C')
		                break;
		            case 5:
		                $(nRow).css('background-color', '#FF974D')
		                break;
		            case 6:
		                $(nRow).css('background-color', '#C7F94C')
		                break;
		            case 7:
		                $(nRow).css('background-color', '#FF974D')
		                break;
		            case 8:
		                $(nRow).css('background-color', '#C7F94C')
		                break;
		            case 9:
		                $(nRow).css('background-color', '#FF974D')
		                break;
		            case 10:
		                $(nRow).css('background-color', '#C7F94C')
		                break;
		        }
    		}

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

     

    // Lo anterior permite el cambio de color en cada item de pedido


    // A continuación definimos el evento para cuando se de clic en el datatable de pedidos, se deberán desplegar los valores en los campos
    // de cliente y adicionalmente la variale glogal de idcliente se capturara, adicionalmente se consumirá la API de google para buscar la
    //dirección. 
    $('#grid-clientes tbody').on('click', 'tr', function () {
        datos = table.row( this ).data();
        $("#selectTiendas").val(datos.tienda);
        var idtien = $("#selectTiendas option:selected").attr('id');
        for(var i = 0; i < tiendasBloqueadas.length;i++){
    		var idtienBloq = tiendasBloqueadas[i].idtienda;
    		if(idtien == idtienBloq)
    		{
    			$.alert('La tienda elegida esta deshabilitada en este momento ' + tiendasBloqueadas[i].comentario );
    			$("#selectTiendas").val('');
    			return;
    		}

    	}
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
        getProductosTienda(idtien);
        //Traemos las gaseosas homologadas y disponibles para la tienda
        obtenerHomologacionProductoGaseosa(idtien);
        memcode = datos.memcode;
        idCliente = datos.idCliente;
        //Validaremos si el cliente seleccionado tiene ofertas vigentes
        $.getJSON(server + 'CRUDOfertaCliente?idoperacion=6&idcliente=' + idCliente, function(data1){
        	if(data1.length > 0)
        	{
        		var mensajeOferta = "<br>";
        		var obs = "";
        		for(var i = 0; i < data1.length;i++){
        			obs = data1[i].observacion;
        			if(obs == null || obs == 'null')
        			{
        				obs = "";
        			}
        			mensajeOferta += data1[i].nombreoferta + "(" + obs + ")" + "<br>";
        		}
        			$.confirm(
        			{
							'title'		: 'El cliente tiene ofertas pendientes',
							'content'	: 'El cliente tiene varias ofertas pendientes: ' + mensajeOferta + '.',
							'buttons'	: {
								'Enterado'	: {
									'class'	: 'blue',
									'action': function()
									{
									}
								}
							}
					});
        	}
        });
        var municipio = datos.municipio;
        //Traemos los valores de la latitud y longitud 
        var latCliente = datos.latitud;
        var lonCliente = datos.longitud;
        if(datos.nomenclatura == '' || datos.nomenclatura == null || datos.numnomenclatura1 == '' || datos.numnomenclatura1 == null || datos.numnomenclatura2 == '' || datos.numnomenclatura2 == null )
        {
        	if ((latCliente == 0) && (lonCliente == 0))
        	{
        		if(($('#direccion').val() != null) && ($('#direccion').val() != '') && ($("#selectMunicipio").val() != null) && ($("#selectMunicipio").val() != ''))
	        	{
	        		buscarMapaDigitado();
	        	}

        	}
        	else
        	{
        		//Llamar método  que geolocalizar dado latitud y longitud
        		geocodeSinServicio(latCliente, lonCliente);
        	}

        }
        else
        {
        	if ((latCliente == 0) && (lonCliente == 0))
        	{
        		buscarMapaDigitado1();
        	}
        	else
        	{
        		//Llamar método  que geolocalizar dado latitud y longitud
        		geocodeSinServicio(latCliente, lonCliente);
        	}
        }

        //En este punto luego de buscar la direccion del cliente, buscaremos si este tiene pedidos
        $('#ultimospedidos').attr('disabled', false);
        $('#transferircliente').attr('disabled', false);
     } );


    $("#selectTiendas").change(function(){
    	//Este podría ser el punto transversal donde intervenidos para validar si la tienda está bbloqueada
    	var idtien = $("#selectTiendas option:selected").attr('id');
    	for(var i = 0; i < tiendasBloqueadas.length;i++){
    		var idtienBloq = tiendasBloqueadas[i].idtienda;
    		if(idtien == idtienBloq)
    		{
    			$.alert('La tienda elegida esta deshabilitada en este momento ' + tiendasBloqueadas[i].comentario );
    			$("#selectTiendas").val('');
    			return;
    		}

    	}
        getProductosTienda(idtien);
        //Traemos las gaseosas homologadas y disponibles para la tienda
        obtenerHomologacionProductoGaseosa(idtiend);
    });

    //Se define acción al momento de seleccionar la excepción de precio para validar que maneje o no hora de promoción
    $("#selectExcepcion").change(function(){
    	var idExc = $("#selectExcepcion option:selected").val();
    	var fueraHora = false;
    	for(var i = 0; i < excepciones.length;i++)
    	{
    		var cadaExc = excepciones[i];
    		if(cadaExc.idexcepcion == idExc)
    		{
    			//Realizamos validación de días
    				//Recuperamos la fecha actual
    				var fechaActual = new Date();
    				//Definimos arreglos con los días de la semana
    				var diasSemana = new Array("domingo","lunes","martes","miercoles","jueves","viernes","sabado");
    				//Realizamos la validacion primero que todo del diá de la promoción
    				var diaSemana = diasSemana[fechaActual.getDay()];
    				if((diaSemana == "domingo")&&(cadaExc.domingo == "N"))
    				{
    					fueraHora = true;
    					break;
    				}else if((diaSemana == "lunes")&&(cadaExc.lunes == "N"))
    				{
    					fueraHora = true;
    					break;
    				}else if((diaSemana == "martes")&&(cadaExc.martes == "N"))
    				{
    					fueraHora = true;
    					break;
    				}else if((diaSemana == "miercoles")&&(cadaExc.miercoles == "N"))
    				{
    					fueraHora = true;
    					break;
    				}else if((diaSemana == "jueves")&&(cadaExc.jueves == "N"))
    				{
    					fueraHora = true;
    					break;
    				}else if((diaSemana == "viernes")&&(cadaExc.viernes == "N"))
    				{
    					fueraHora = true;
    					break;
    				}else if((diaSemana == "sabado")&&(cadaExc.sabado == "N"))
    				{
    					fueraHora = true;
    					break;
    				}    			
    			//Realizmos validación de horas
    			if((cadaExc.horafinal == null) && (cadaExc.horaInicial ==  null))
    			{

    			}else
    			{

    				//Tomamos la hora de promocion
    				var horIniPromo = parseInt(cadaExc.horainicial.substring(0,2));
    				var minIniPromo = parseInt(cadaExc.horainicial.substring(3,5));
    				var horFinPromo = parseInt(cadaExc.horafinal.substring(0,2));
    				var minFinPromo = parseInt(cadaExc.horafinal.substring(3,5));
    				var horaAct = fechaActual.getHours();
    				var minAct = fechaActual.getMinutes();
    				if((horaAct < horIniPromo) || (horaAct > horFinPromo))
    				{
    					fueraHora = true;
    					break;
    				}
    				if (horaAct == horIniPromo)
    				{
    					if(minAct < minIniPromo)
    					{
    						fueraHora = true;
    						break;
    					}
    				}
    				if (horaAct == horFinPromo)
    				{
    					if(minAct > minFinPromo)
    					{
    						fueraHora = true;
    						break;
    					}
    				}
    			}
    		}
    	}
    	if(fueraHora)
    	{
    		//Alertamos de la situación de la promoción
    		$.alert('La promoción seleccionada está fuera de horario o de día de la semana.');
    		// Limpiamos la selección de la promoción
    		$("#selectExcepcion").val("vacio");
    	}
    }); 

    //Incluimos acción para el cambio la forma de pago
    $("#selectformapago").change(function(){
    	//Tomamos el idformapago seleccionado
    	var idformapago =  $("#selectformapago").val();
    	var tipoForma = "";
    	for(var i = 0; i < formas.length;i++){
    		var cadaForma  = formas[i];
    		if(idformapago == cadaForma.idformapago)
			{
				if(cadaForma.tipoformapago == 'TRANSFERENCIA' || cadaForma.tipoformapago == 'PAGO-ONLINE')
				{
					$("#valorpago").val($("#totalpedido").val());
					$("#valordevolver").val("0");
					$('input:radio[name=requiereDevuelta]')[0].checked = true;
					$('#valorpago').attr('disabled', true);
				}
				else
				{
					$('#valorpago').attr('disabled', false);
				}
				break;
			}
			
		}
    });

 	
    //Implementamos acción cuando se selecciona que la forma de pago es tarjeta para que se deje automaticamente pago completo
 	
    // Se definen los eventos para para cuando demos clic sobre los botones de forma pizza de pizza entera o pizza mitad
 	$("input[name=formapizza]:radio").click(function() { 
                
 			//Validamos si hay una excepcíón de precio seleccionada y si esta controla cantidad de ingredientes en cuyo caso no debe de pintar especialidades
 			if(controlaCantidadIngredientes == 'N')
 			{
                if($(this).val() == 'mitad') {
                		
                		if($("input[name=tamanoPizza]:radio").is(':checked')) {
                				getEspecilidadesMitad();
                        	}
                        	else
                        	{
                        		alert("Debe seleccionar el tamaño de la pizza");
                        		$("input[name=formapizza]:radio").attr('checked', false);
                        		
                        	}	
                } else if($(this).val() == 'entera') {
                		if($("input[name=tamanoPizza]:radio").is(':checked')) {
                				getEspecilidadesEntera();
                        	}
                        	else
                        	{
                        		alert("Debe seleccionar el tamaño de la pizza");
                        		$("input[name=formapizza]:radio").attr('checked', false);
                        	}	

                } 
             }   
        });
 	

 	//Cuando se inicia la página
 	$('input:radio[name=requiereDevuelta]:nth(1)').attr('checked',true);
	$('#valorpago').attr('disabled', false);
 	//Definimos evento de click para el radio button de la forma de pago si completo o con devuelta
 	$("input[name=requiereDevuelta]:radio").click(function() { 

 		if($(this).val() == 'completo') {
 			console.log("completo");
			$("#valorpago").val($("#totalpedido").val());
			$('#valorpago').attr('disabled', true);
			
 		}else  if($(this).val() == 'devuelta') {
 			console.log("devuelta");
 			$('#valorpago').attr('disabled', false);
 			$("#valorpago").val('0');
 		}
 		$("#valordevolver").val($("#valorpago").val() - $("#totalpedido").val() );
 	});

     	//Pintar las adiciones
		$("input[name=adicion]:radio").click(function() { 
	                if($(this).val() == 'siadicion') {
	                		
	                		if($("input[name=adicion]:radio").is(':checked')) {
	                			//Tenemos que validar si la adición aplica para el producto
	                			if($("input[name=tamanoPizza]:radio").is(':checked')) {
	                				if($('input:radio[name=tamanoPizza]:checked').val() != 'otros')
	                				{
	                					getAdicionProductos();	
	                				}
	                				else
	                				{
	                					alert("Solo las pizzas tendrán adiciones");
	                					$('input:radio[name=adicion]')[1].checked = true;
	                				}
	                				
	                			}
	                			else
	                			{
	                				alert("Para seleccionar la adición debes primero seleccionar el tamaño de pizza");
	                				$('input:radio[name=adicion]')[1].checked = true;
	                			}
	                			
	                				
	                        }
	                        else
	                        {
	                        	str='';
	                        	$('#pintarAdiciones').html(str);
	                        		
	                        }	
	                }else{
	                	str='';
	                    $('#pintarAdiciones').html(str);
	                }
	               
	        });

	} );

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


// Función que se encarga de implementar la lógica para la eliminación de un detalle  pedido.
function eliminarDetallePedido(iddetallepedido)
{
	
	$.confirm({
			'title'		: 'Confirmacion Eliminación del Detalle Pedido',
			'content'	: 'Desea confirmar la eliminación del Detalle Pedido ' + iddetallepedido+ '.',
			'buttons'	: {
				'Si'	: {
					'class'	: 'blue',
					'action': function(){
					
						var resultado
						$.ajax({ 
	    				url: server + 'EliminarDetallePedido?iddetallepedido=' + iddetallepedido, 
	    				dataType: 'json', 
	    				async: false, 
	    				success: function(data){ 
	    						console.log(data);
	    						var cadaDetalle;
	    						if (data.length > 0)
								{

									if ( $.fn.dataTable.isDataTable( '#grid-pedido' ) ) {
							    		table = $('#grid-pedido').DataTable();
							    		
							    		for(var i = 0; i < data.length;i++)
							    		{
								    		cadaDetalle = data[i];
								    		table.rows().nodes().each(function(a,b) {
								    			// eq(0) nos permite buscar en la primera fila
									            if($(a).children().eq(0).text() == cadaDetalle.iddetallepedido){
									               table.rows(a).remove();
									            }
								         	} );
								          	table.rows().invalidate();
								          	table.draw();
								    		//table.rows().remove().draw();
								    	}
								    	$.getJSON(server + 'ObtenerTotalPedido?idpedido=' + idPedido, function(data){
											var valorTotalDespues = data[0];
											totalpedido = valorTotalDespues.valortotal;
											$('#totalpedido').val(valorTotalDespues.valortotal);
											if ($("input:radio[name=requiereDevuelta]:checked").val() == "completo")
											{
												$("#valorpago").val(valorTotalDespues.valortotal);
											}
	    									$("#valordevolver").val($("#valorpago").val() - $("#totalpedido").val() );
										});
										contadorItem = contadorItem - 1;
							    	}
								}
								else
								{
									alert("No fue posible eliminar la informacion del pedido. Por favor comunicarse con el administrador");
								}

								

								//

								
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
}

function duplicarDetallePedido(iddetallepedido)
{
	var table;
	$.confirm({
			'title'		: 'Confirmacion Duplicar Detalle Pedido',
			'content'	: 'Desea duplicar el Detalle Pedido ' + iddetallepedido+ '.',
			'buttons'	: {
				'Si'	: {
					'class'	: 'blue',
					'action': function(){
					
						var resultado
						$.ajax({ 
	    				url: server + 'DuplicarDetallePedido?iddetallepedido=' + iddetallepedido, 
	    				dataType: 'json', 
	    				async: false, 
	    				success: function(data){ 
	    						console.log(data);
	    						var cadaDetalle;
	    						if (data.length > 0)
								{
									table = $('#grid-pedido').DataTable();
									for(var i = 0; i < data.length;i++)
							    	{
							    		cadaDetalle = data[i];
							    		var pizza;
							    		var otroprod;
							    		var especialidad1;
							    		var especialidad2;
							    		var accion2;
							    		var adicion;
							    		if(cadaDetalle.tipo == "PIZZA")
							    		{
							    			pizza = cadaDetalle.pizza;
							    			otroprod = '';
							    		}else
							    		{
							    			pizza = '';
							    			otroprod = cadaDetalle.otroprod;
							    		}
							    		if(cadaDetalle.especialidad1 == null || cadaDetalle.especialidad1 == '')
							    		{
							    			especialidad1 = '';
							    		}else
							    		{
							    			especialidad1 = cadaDetalle.especialidad1;
							    		}
							    		if(cadaDetalle.especialidad2 == null || cadaDetalle.especialidad2 == '')
							    		{
							    			especialidad2 = '';
							    		}else
							    		{
							    			especialidad2 = cadaDetalle.especialidad2;
							    		}
							    		// Para controlar la adición del botón de duplicacion, contralamos que no sea ni adicion, ni modificadores y adicionalmente que no sea un producto incluido con el valor en el campo de observacion.
							    		if((cadaDetalle.tipo != "ADICION") && (cadaDetalle.tipo != "MODIFICADOR CON") && (cadaDetalle.tipo != "MODIFICADOR SIN") && (cadaDetalle.observacion.substring(0,18) != 'Producto Incluido-' ))
							    		{
							    			accion2 = '<input type="button" class="btn btn-default btn-xs" onclick="duplicarDetallePedido(' + cadaDetalle.iddetallepedido + ')" value="Duplicar"></button>';
							    			adicion = cadaDetalle.adicion;
							    		}else
							    		{
							    			accion2 = '';
							    			adicion = '';
							    		}
							    		table.row.add( {
										"iddetallepedido": cadaDetalle.iddetallepedido,
										"numitem": contadorItem,
								        "pizza":       pizza,
								        "otroprod":   otroprod,
								        "cantidad":    cadaDetalle.cantidad,
								        "especialidad1": especialidad1,
								        "especialidad2": especialidad2,
								        "liquido": cadaDetalle.liquido,
								        "observacion": cadaDetalle.observacion,
								        "adicion": adicion,
								        "valorunitario": cadaDetalle.valorunitario,
								        "valortotal": cadaDetalle.valortotal,
								        "accion":'<input type="button" class="btn btn-default btn-xs" onclick="eliminarDetallePedido(' + cadaDetalle.iddetallepedido + ')" value="Eliminar"></button>',
								        "accion2": accion2
								    	}).draw();
							    	}
									$.getJSON(server + 'ObtenerTotalPedido?idpedido=' + idPedido, function(data){
											var valorTotalDespues = data[0];
											totalpedido = valorTotalDespues.valortotal;
											$('#totalpedido').val(valorTotalDespues.valortotal);
											if ($("input:radio[name=requiereDevuelta]:checked").val() == "completo")
											{
												$("#valorpago").val(valorTotalDespues.valortotal);
											}
	    									$("#valordevolver").val($("#valorpago").val() - $("#totalpedido").val() );
									});
							    	contadorItem = contadorItem + 1;
								}
								else
								{
									alert("No fue posible duplicar el pedido");
								}
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
		$('#selectTiendasTrasladar').html(str);
		// Realizamos cambio para que la tienda no esté seleccionada por defecto
		$("#selectTiendas").val('');
		$("#selectTiendasTrasladar").val('');
	});
}

function obtenerTiendasBloqueadas(){
	$.getJSON(server + 'CRUDTiendaBloqueada?idoperacion=5', function(data){
		tiendasBloqueadas = data;
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


// Método que invoca servicio en el que obtiene para las tiendas que gaseosas están homologadas como tipo de gaseosa incluido
function getHomologacionGaseosaTienda(){
	$.getJSON(server + 'ObtenerHomologaGaseosaIncluida', function(data){
		gaseosaHomologadaTienda = data;
	});
}

function obtenerHomologacionProductoGaseosa(idtie){
	$.getJSON(server + 'ObtenerHomologacionProductoGaseosa?idtienda='+ idtie, function(data){
		productoGaseosaHomologadaTienda = data;
	});
}

// Método que se encarga de obtener todos los productos incluidos mediante la invocación del servicio correspondiente
function obtenerProductosIncluidos(){
	$.getJSON(server + 'ObtenerProductosIncluidos', function(data){
		productosIncluidos = data;
		
	});
}


//Método que invoca servicio para obtener las formas de pago parametrizados en el sistema.
function getFormasPago(){
	$.getJSON(server + 'CRUDFormaPago?idoperacion=5', function(data){
		formas = data;
		var str = '';
		for(var i = 0; i < data.length;i++){
			var cadaForma  = data[i];
			str +='<option value="'+ cadaForma.idformapago +'" id ="'+ cadaForma.idformapago +'">' + cadaForma.nombre +'</option>';
		}
		$('#selectformapago').html(str);
		
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

//Método que invoca el servicio para obtener lista de municipios parametrizados en el sistema
function getMarcaciones(){

	$.getJSON(server + 'ObtenerMarcaciones', function(data){
		marcaciones = data;
		var str = '<h2>Marcaciones Pedido</h2>';
        str += '<table class="table table-bordered">';
		str += '<tbody>';
		for(var i = 0; i < data.length;i++){
			var cadaMarcacion  = marcaciones[i];
			str +='<tr> ';
			str +='<td> ';
			str += '<label><input type="checkbox" aria-label="..."' + '  value="'+ cadaMarcacion.nombremarcacion + '" id=checkMarca"' + cadaMarcacion.idmarcacion + '" name= "checkMarca' + cadaMarcacion.idmarcacion  +'">'  + cadaMarcacion.nombremarcacion +'</label>';
			str += '</td>';
			str +='<td> ';
			str += '<label><input type="text" ' + '" id="txtObsMarcacion' + cadaMarcacion.idmarcacion + '" name= "txtObsMarcacion' + cadaMarcacion.idmarcacion +'" maxlength="50">'  +'</label>';
			str += '</td>';
			str += '</tr>';
		}
		$('#frmMarcaciones').html(str);
	});

}

function procesarMarcaciones(idPedMarcado){
	for(var i = 0; i < marcaciones.length; i++)
	{
		var cadaMarcacion  = marcaciones[i];
		if($('input:checkbox[name=checkMarca'+cadaMarcacion.idmarcacion+']').is(':checked'))
		{
			var observacion = "";
			observacion = $("#txtObsMarcacion"+cadaMarcacion.idmarcacion).val();
			$.getJSON(server + 'InsertarMarcacionPedido?idpedido='+idPedMarcado+"&idmarcacion=" + cadaMarcacion.idmarcacion + "&observacion=" + observacion, function(data){
				$("#txtObsMarcacion"+cadaMarcacion.idmarcacion).val('');
				$('input:checkbox[name=checkMarca'+cadaMarcacion.idmarcacion+']').attr('checked',false);
			});
		}
	}
}

function validarMarcaciones()
{
	for(var i = 0; i < marcaciones.length; i++)
	{
		var cadaMarcacion  = marcaciones[i];
		if($('input:checkbox[name=checkMarca'+cadaMarcacion.idmarcacion+']').is(':checked'))
		{
			var observacion = "";
			observacion = $("#txtObsMarcacion"+cadaMarcacion.idmarcacion).val();
			if(observacion == '' || observacion == null)
			{
				return(false);
			}
		}
	}
	return(true);
}

//Método que invoca el servicio para obtener las excepciones de precio parametrizadas en el sistema
function getExcepcionesPrecios(){
	$.getJSON(server + 'getExcepcionesPrecio', function(data){
		excepciones = data;
		var str = '';
		str += '<option value="vacio">Sin Excepcion</option>';
		for(var i = 0; i < excepciones.length;i++){
			var cadaExcepcion  = data[i];
			str +='<option value="'+ cadaExcepcion.idexcepcion +'" id ="' + cadaExcepcion.idproducto +'">' + cadaExcepcion.descripcion + "       $" + cadaExcepcion.precio +'</option>';
		}
		$('#selectExcepcion').html(str);

	});
	var selExcepcion;
	var idSelExcepcion;
	var selCodigoProducto;
	controlaCantidadIngredientes = "N";
	cantidadIngredientes = 0;
	excepcionSeleccionada;
	// Se realiza el control de cuando se cambia el valor de la excepción, se valida que el producto seleccionado, corresponda con la excepción
	//del precio.
	$('#selectExcepcion').bind('change', function()
	{
		//Cuando se cambia la excepcion de precio se deberia de borrar la secciones correspondiente
		var strClr = "";
	    $('#otrosproductos').html(strClr);
	    $('#especialidades').html(strClr);
	    $('#pintarAdiciones').html(strClr);
	    $('#pintarSin').html(strClr);
	    $('#pintarCon').html(strClr);
	    $('#frmgaseosas').html(strClr);
	    //$('input:text[name=cantidad]').val('');
	    //Se reinician valores por defecto
	    $('input:radio[name=adicion]:nth(1)').attr('checked',true);
	    $('input:radio[name=formapizza]').attr('checked',false);
	    $('input:radio[name=liquido]').attr('checked',false);
		//	
		selExcepcion = $(this).val();
		idSelExcepcion = $(this).children(":selected").attr("id");
		selCodigoProducto = $("input:radio[name=tamanoPizza]:checked").val();
		if($(this).val()=="vacio")
		{
			excepcionSeleccionada = null;
			controlaCantidadIngredientes = 'N';
			cantidadIngredientes = 0;
			return;
		}
		var idtien = $("#selectTiendas option:selected").attr('id');
		if (idtien > 0)
		{

		}
		else
		{
			alert("Debe seleccionar una Tienda.");
			return;
		}
		if (selCodigoProducto != 'otros')
		{

			//Recorremos el arreglo de excepciones para traerla
			for(var i = 0; i < excepciones.length;i++){
				var cadaExcepcion  = excepciones[i];
				if (cadaExcepcion.idexcepcion == selExcepcion)
				{
					excepcionSeleccionada = cadaExcepcion;
					controlaCantidadIngredientes = excepcionSeleccionada.controlacantidadingredientes;
					cantidadIngredientes = excepcionSeleccionada.cantidadingredientes;
					break;
				}
			}

			if (selCodigoProducto != idSelExcepcion)
			{
				alert("La excepción no está relacionada con el producto seleccionado, por favor corrija su elección");
				$("#selectExcepcion").val("vacio");
				$('input:radio[name=tamanoPizza]').attr('checked',false);
				return;
			}
			else
			{
				$.getJSON(server + 'GetSaboresLiquidoExcepcion?idExcepcion=' + selExcepcion +"&idtienda=" + idtien, function(data1){
                		
                		var strGas='';
                		var indfila = 1;
                		var strGas = '<h2>Gaseosa</h2>';
                		strGas += '<table class="table table-bordered">';
                		strGas += '<tbody>';
						for(var i = 0; i < data1.length;i++){
							var cadaLiq  = data1[i];
							for(var j = 0; j < gaseosaHomologadaTienda.length; j++ )
							{
								if(gaseosaHomologadaTienda[j].idtienda == $("#selectTiendas option:selected").attr('id') &&  gaseosaHomologadaTienda[j].idsabortipoliquido == cadaLiq.idSaborTipoLiquido)
								{
									if(indfila == 1)
									{
										strGas +='<tr> ';
										strGas +='<td> ';
										strGas += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaLiq.idSaborTipoLiquido + '" id="' + cadaLiq.descripcionSabor + '" name="liquido">'  + cadaLiq.descripcionSabor +'</label>';
										strGas += '</td>';
									}else if(indfila == 2)
									{
										strGas +='<td> ';
										strGas += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaLiq.idSaborTipoLiquido + '" id="' + cadaLiq.descripcionSabor + '" name="liquido">'  + cadaLiq.descripcionSabor +'</label>';
										strGas += '</td>';
									}else if(indfila == 3)
									{
										strGas +='<td> ';
										strGas += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaLiq.idSaborTipoLiquido + '" id="' + cadaLiq.descripcionSabor + '" name="liquido">'  + cadaLiq.descripcionSabor +'</label>';
										strGas += '</td> </tr>';
									}
									indfila = indfila + 1;
									if (indfila == 4)
									{
										indfila = 1;
									}
								}
							}
							
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
					$('input:radio[name=tamanoPizza]').attr('checked',false);
					return;
				}
				else
				{
					$.getJSON(server + 'GetSaboresLiquidoExcepcion?idExcepcion=' + selExcepcion + "&idtienda=" + idtien, function(data1){
	                		
	                		var strGas='';
	                		var indfila = 1;
	                		strGas = '<h2>Gaseosa</h2>';
	                		strGas += '<table class="table table-bordered">';
                			strGas += '<tbody>';
							for(var i = 0; i < data1.length;i++){
								var cadaLiq  = data1[i];
								for(var j = 0; j < gaseosaHomologadaTienda.length; j++ )
								{
									if(gaseosaHomologadaTienda[j].idtienda == $("#selectTiendas option:selected").attr('id') &&  gaseosaHomologadaTienda[j].idsabortipoliquido == cadaLiq.idSaborTipoLiquido)
									{
										if(indfila == 1)
										{
											strGas +='<tr>';
											strGas +='<td>';
											strGas += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaLiq.idSaborTipoLiquido + '" id="' + cadaLiq.descripcionLiquido + '" name="liquido">' + cadaLiq.descripcionSabor +'</label>';
											strGas += '</td>';
										}else if(indfila == 2)
										{
											strGas +='<td>';
											strGas += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaLiq.idSaborTipoLiquido + '" id="' + cadaLiq.descripcionLiquido + '" name="liquido">' + cadaLiq.descripcionSabor +'</label>';
											strGas += '</td>';
										}else if(indfila == 3)
										{
											strGas +='<td>';
											strGas += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaLiq.idSaborTipoLiquido + '" id="' + cadaLiq.descripcionLiquido + '" name="liquido">' + cadaLiq.descripcionSabor +'</label>';
											strGas += '</td> </tr>';
										}
										indfila = indfila + 1;
										if (indfila == 4)
										{
											indfila = 1;
										}
									}	
								}
							}
							strGas += '</tbody> </table>';
	                		$('#frmgaseosas').html(strGas);
					});
				}
			}
		}
		
	});
}

//Método para obtener y pintar las especilaidades cuando se elige en la forma de mitad y mitad
function getEspecilidadesMitad(){
	$.getJSON(server + 'GetEspecialidades', function(data){
		especialidades = data;
		var str = '';
		str += '<table class="table table-bordered">';
		str += '<thead><tr><td>MITAD 1</td><td>MITAD 2</td></tr></thead>';
		str += "<tbody>";
		for(var i = 0; i < data.length;i++){
			var cadaEspe  = data[i];
			str +='<tr> ';
			str +='<td onclick="cambiaColorCeldaEspMitad1(this);"> ';
			str += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaEspe.idespecialidad + '" id="' + cadaEspe.nombre + '" name="mitad1">' + cadaEspe.nombre + '</label>';
			str += '</td>';
			str += '<td onclick="cambiaColorCeldaEspMitad2(this);">';
			str += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaEspe.idespecialidad + '" id="' + cadaEspe.nombre + '" name="mitad2">' + cadaEspe.nombre + '</label>';
			str += '</td> </tr>';

		}
		str += "</tbody> </table>";
		$('#especialidades').html(str);
	});


}

// Método que se encarga de pintar las especialidad cuando se elige que la pizza será de un solo sabor.
function getEspecilidadesEntera(){

	$.getJSON(server + 'GetEspecialidades', function(data){
		especialidades = data;
		var str = '';
		var indfila = 1;
		str += '<table class="table table-bordered">';
		str += '<thead><tr><td>PIZZA ENTERA</td></tr></thead>';
		str += "<tbody>";
		for(var i = 0; i < data.length;i++){
			var cadaEspe  = data[i];
			if(indfila == 1)
			{
				str +='<tr"> ';
				str +='<td onclick="cambiaColorCeldaEspMitad1(this);">';
				str += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaEspe.idespecialidad + '" id="' + cadaEspe.nombre + '" name="mitad1">' + cadaEspe.nombre + '</label>';
				str += '</td>';
			}else if(indfila == 2)
			{
				str +='<td onclick="cambiaColorCeldaEspMitad1(this);">';
				str += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaEspe.idespecialidad + '" id="' + cadaEspe.nombre + '" name="mitad1">' + cadaEspe.nombre + '</label>';
				str += '</td>';
			}else
			{
				str +='<td onclick="cambiaColorCeldaEspMitad1(this);">';
				str += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaEspe.idespecialidad + '" id="' + cadaEspe.nombre + '" name="mitad1">' + cadaEspe.nombre + '</label>';
				str += '</td>';
				str += "</tr>";
			}
			indfila = indfila + 1;
			if (indfila == 4)
			{
				indfila = 1;
			}
		}
		str += "</tbody> </table>";
		$('#especialidades').html(str);
	});
	
}

//Métodos para obtener todos los productos y con esto se realizan todas las validaciones de producto dentro de la capa de presentación.
function getTodosProductos()
{
	$.getJSON(server + 'GetTodosProductos', function(data){
		productos = data;
		getPizzas();
	});
}

function getProductosTienda(idtienda)
{
	$.getJSON(server + 'GetProductosTienda?idtienda='+ idtienda, function(data){
		productos = data;
		getPizzas();
	});
}

//Método que se encarga de pintar los productos tipo pizza.
function getPizzas()
{

	var strPizza = '';
	var contadorFila = 1;
	strPizza += '<table class="table table-bordered">';
	for(var i = 0; i < productos.length;i++){
			var cadaProdu  = productos[i];
			if (cadaProdu.tipo == "PIZZA" )
			{
				if (contadorFila == 1)
				{
					strPizza +='<tr>';
					strPizza +='<td> ';
					strPizza += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaProdu.idproducto + '" id="' + cadaProdu.idproducto + '" name="tamanoPizza">' + cadaProdu.nombre +'</label>';
					
					
				}
				if (contadorFila == 2)
				{
					strPizza +='<td>';
					strPizza += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaProdu.idproducto + '" id="' + cadaProdu.idproducto + '" name="tamanoPizza">' + cadaProdu.nombre +'</label>';
					strPizza += '</td>';
					strPizza +='</tr>';
				}
				contadorFila = contadorFila + 1;
				if (contadorFila == 3)
				{
					contadorFila = 1;
				}
			}
			
	}
	strPizza +='<tr>';
	strPizza +='<td> ';
	strPizza += ' <label><input type="radio" aria-label="..." value="otros" id="otros" name="tamanoPizza">Otros Productos</label>';					    
	strPizza += '</td>';
	strPizza +='<td> ';
	strPizza += ' <label><input type="radio" aria-label="..." value="otrosgaseosa" id="otrosgaseosa" name="tamanoPizza">Otros Gaseosa</label>';					    
	strPizza += '</td>';
	strPizza +='</tr>';
	strPizza +='<tr>';
	strPizza +='<td> ';
	strPizza += ' <label><input type="radio" aria-label="..." value="otrosadicionales" id="otrosadicionales" name="tamanoPizza">Otros Adicionales</label>';					    
	strPizza += '</td>';
	strPizza +='<tr>';		
	strPizza += '</table>';
	$('#FormTamanoPizza').html(strPizza);
	var idtien = $("#selectTiendas option:selected").attr('id');
	$("input[name=tamanoPizza]:radio").click(function() { 

			//Validamos que la tienda este seleccionada, esto con el objetivo de controlar el desppliegue de productos como gaseosas, otros y adicionales.
			if($("#selectTiendas").val() == null ||  $("#selectTiendas").val() == '')
			{
				alert('Debe tener seleccionada la tienda del pedido');
				return;
			}

				var tamanoPizza = $("input:radio[name=tamanoPizza]:checked").val();
				
				switch($(this).val())
				{
					case 'otros':
						if($("input[name=tamanoPizza]:radio").is(':checked')) {
                				getOtrosProductos();
                				var str='';
                				$('#especialidades').html(str);
                				$('#frmgaseosas').html(str);
                        	}
                        	else
                        	{
                        		str='';
                        		$('#otrosproductos').html(str);
                        		
                        	}	
                        break;
                    case 'otrosgaseosa':
                    	$('#otrosproductos').html('');
                    	$('#especialidades').html('');
                    	$('#frmgaseosas').html('');
                    	getOtrosGaseosa();
                    	break;
                    case 'otrosadicionales':
                    	$('#otrosproductos').html('');
                    	$('#especialidades').html('');
                    	$('#frmgaseosas').html('');
                    	getOtrosAdicionales();
                    	break;
                    default:
                    	$('#otrosproductos').html('');
	                	$.getJSON(server + 'GetSaboresLiquidoProducto?idProducto=' + tamanoPizza + "&idtienda=" + idtien, function(data1){
	                		
	                		var strGas='';
	                		var indfila = 1;
	                		var strGas = '<h2>Gaseosa</h2>';
	                		strGas += '<table class="table table-bordered">';
	                		strGas += '<tbody>';
							for(var i = 0; i < data1.length;i++){
								var cadaLiq  = data1[i];
								for(var j = 0; j < gaseosaHomologadaTienda.length; j++ )
								{
									if(gaseosaHomologadaTienda[j].idtienda == $("#selectTiendas option:selected").attr('id') &&  gaseosaHomologadaTienda[j].idsabortipoliquido == cadaLiq.idSaborTipoLiquido)
									{
										if(indfila == 1)
										{
											strGas +='<tr> ';
											strGas +='<td>';
											strGas += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaLiq.idSaborTipoLiquido + '" id="' + cadaLiq.descripcionSabor + '" name="liquido">' + cadaLiq.descripcionSabor +'</label>';
											strGas += '</td>';
										}else if(indfila == 2)
										{
											strGas +='<td>';
											strGas += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaLiq.idSaborTipoLiquido + '" id="' + cadaLiq.descripcionSabor + '" name="liquido">' + cadaLiq.descripcionSabor +'</label>';
											strGas += '</td>';
										}else if(indfila == 3)
										{
											strGas +='<td>';
											strGas += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaLiq.idSaborTipoLiquido + '" id="' + cadaLiq.descripcionSabor + '" name="liquido">' + cadaLiq.descripcionSabor +'</label>';
											strGas += '</td> </tr>';
										}
										indfila = indfila + 1;
										if (indfila == 4)
										{
											indfila = 1;
										}
									}
								}		
							}
							strGas += '</tbody> </table>';
	                		$('#frmgaseosas').html(strGas);
						});
                    	break;
				}

        });
}

// Método que se encarga de pintar otros productos cuando es seleccionado.
function getOtrosProductos()
{
	var str = '';
	var indfila = 1;
	str += '<table class="table table-bordered">';
    str += '<tbody>';
    var idtien = $("#selectTiendas option:selected").attr('id');
	for(var i = 0; i < productos.length;i++){
			var cadaProdu  = productos[i];
			if (cadaProdu.tipo == "OTROS" )
			{
				if(indfila == 1)
				{
					str +='<tr> ';
					str +='<td>';
					str += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaProdu.nombre +'" id="' + cadaProdu.idproducto + '" name="otros">' + cadaProdu.nombre +'</label>';
					str += '</td>';
				}else if(indfila == 2)
				{
					str +='<td>';
					str += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaProdu.nombre +'" id="' + cadaProdu.idproducto + '" name="otros">' + cadaProdu.nombre +'</label>';
					str += '</td>';
				}else if(indfila == 3)
				{
					str +='<td>';
					str += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaProdu.nombre +'" id="' + cadaProdu.idproducto + '" name="otros">' + cadaProdu.nombre +'</label>';
					str += '</td> </tr>';
				}
				indfila = indfila + 1;
				if (indfila == 4)
				{
					indfila = 1;
				}
			}
		}
		str += '</tbody> </table>';
		$('#otrosproductos').html(str);
		$("input[name=otros]:radio").click(function() { 
			var idProductoOtros = $("input:radio[name=otros]:checked").attr('id');
			$.getJSON(server + 'GetSaboresLiquidoProducto?idProducto=' + idProductoOtros + "&idtienda=" + idtien, function(data1){
                		console.log("gaseosa para otros productos " + data1);
                		var strGas='';
                		indfila = 1;
                		var strGas = '<h2>Gaseosa</h2>';
                		strGas += '<table class="table table-bordered">';
                		strGas += '<tbody>';
						for(var i = 0; i < data1.length;i++){
							var cadaLiq  = data1[i];
							for(var j = 0; j < gaseosaHomologadaTienda.length; j++ )
								{
									if(gaseosaHomologadaTienda[j].idtienda == $("#selectTiendas option:selected").attr('id') &&  gaseosaHomologadaTienda[j].idsabortipoliquido == cadaLiq.idSaborTipoLiquido)
									{
										if(indfila == 1)
										{
											strGas +='<tr> ';
											strGas +='<td> ';
											strGas += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaLiq.idSaborTipoLiquido + '" id="' + cadaLiq.descripcionSabor + '" name="liquido">'  + cadaLiq.descripcionSabor +'</label>';
											strGas += '</td>';
										}else if(indfila == 2)
										{
											strGas +='<td> ';
											strGas += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaLiq.idSaborTipoLiquido + '" id="' + cadaLiq.descripcionSabor + '" name="liquido">'  + cadaLiq.descripcionSabor +'</label>';
											strGas += '</td>';
										}else if(indfila == 3)
										{
											strGas +='<td> ';
											strGas += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaLiq.idSaborTipoLiquido + '" id="' + cadaLiq.descripcionSabor + '" name="liquido">'  + cadaLiq.descripcionSabor +'</label>';
											strGas += '</td> </tr>';
										}
										indfila = indfila + 1;
										if (indfila == 4)
										{
											indfila = 1;
										}
									}
								}		
						}
						strGas += '</tbody> </table>';
                		$('#frmgaseosas').html(strGas);
			});
		});
}

//Método qeu se encarga de responder al evento de selección de MODIFICADORES CON
function ProductoCon()
{
	if ($("input[name=tamanoPizza]:radio").is(':checked'))
	{
		var resValidacionEsp = validaSeleccionEspecilidad();
		if ((resValidacionEsp == 1 )|| (controlaCantidadIngredientes = "S"))
		{
			if(marcardorProductoCon == 0)
			{
				var str = '';
				var marcadorMitad = 0;
				str += '<table class="table table-bordered">';
				if ($("input:radio[name=formapizza]:checked").val() == 'mitad')
				{
					var especialidad1 = $("input:radio[name=mitad1]:checked").attr('id');
					var especialidad2 = $("input:radio[name=mitad2]:checked").attr('id');
					if(especialidad1 == undefined)
					{
						especialidad1 = "";
					}
					if(especialidad2 == undefined)
					{
						especialidad2 = "";
					}
					marcadorMitad = 1;
					str += '<thead><tr><td><h3>MITAD 1 - '+ especialidad1 +'</h3></td><td><h3>MITAD 2 - ' + especialidad2 +  '</h3></td></tr></thead>';
				}
				else
				{
					if(especialidad1 == undefined)
					{
						especialidad1 = "";
					}
					if(especialidad2 == undefined)
					{
						especialidad2 = "";
					}
					var especialidad1 = $("input:radio[name=mitad1]:checked").attr('id');
					str += '<thead><tr><td><h3>PIZZA ENTERA - ' + '</h3></td><td><h3>' + especialidad1 +'</h3></td></tr></thead>';
				}
				var indfila =1;
				
			    str += '<tbody>';
				for(var i = 0; i < productos.length;i++){
						var cadaModificador  = productos[i];
						var tamapizza = $("input:radio[name=tamanoPizza]:checked").val();
						
						if ((cadaModificador.tipo == "MODIFICADOR CON") && (cadaModificador.productoasociaadicion == tamapizza ))
						{
							if (marcadorMitad == 1)
							{
								str +='<tr> ';
								str +='<td onclick="cambiaColorCeldaModSin(this);"> ';
								str += '<div class="col-md-4"> ';
								str += '<label><input type="checkbox"' + '  value="' + cadaModificador.nombre +'" name="' +'mitmodcon1' + cadaModificador.idproducto + '" onclick="revisarMarcacionModSin(this)">' + cadaModificador.nombre +'</label>';
								str += '</div>'
								str += '</td>';
								str +='<td onclick="cambiaColorCeldaModSin(this);"> ';
								str += '<div class="col-md-4"> ';
								str += '<label><input type="checkbox"' + '  value="' + cadaModificador.nombre +'" name="' +'mitmodcon2' + cadaModificador.idproducto + '" onclick="revisarMarcacionModSin(this)">' + cadaModificador.nombre +'</label>';
								str += '</div>'
								str += '</td> </tr>';
							}
							else
							{
								if (indfila == 1)
								{
									str +='<tr> ';
									str +='<td onclick="cambiaColorCeldaModSin(this);"> ';
									str += '<div class="col-md-4"> ';
									str += '<label><input type="checkbox"' + '  value="' + cadaModificador.nombre +'" name="' +'mitmodcon1' + cadaModificador.idproducto + '" onclick="revisarMarcacionModSin(this)">' + cadaModificador.nombre +'</label>';
									str += '</div>';
									str += '</td>';
								}
								else
								{
									str +='<td onclick="cambiaColorCeldaModSin(this);"> ';
									str += '<div class="col-md-4"> ';
									str += '<label><input type="checkbox"' + '  value="'+ cadaModificador.nombre +'" name="' +'mitmodcon1' +  cadaModificador.idproducto + '" onclick="revisarMarcacionModSin(this)">' + cadaModificador.nombre +'</label>';
									str += '</div>';
									str += '</td> </tr>';
								}
								indfila = indfila +1;
								if(indfila ==3)
								{
									indfila = 1;
								}

								
							}
						}	
					}
					str += '</tbody> </table>';
					str += '<div class="modal-footer">';
					str += '<button type="button" onClick="cerrarModalModCon()" class="btn btn-default" data-dismiss="modal">Eliminar lo Seleccionado</button>';
					str += '<button type="button" onClick="ocultarModalModCon()" data-dismiss="modal" class="btn btn-primary">Guardar Modificadores Con</button>';
					str += '</div>	';
					$('#pintarCon').html(str);
					$('div').click( function( e ) {
		    			e.stopPropagation();
		    			// ...
					});
					$('#conProducto').modal('show');
					marcardorProductoCon = 1;
			}
			else
			{
				$('#conProducto').modal('show');
			}
		}else
		{
			alert("Primero debe seleccionar la especialidad u Otro producto");
		}
	}
	else
	{
		alert("Para seleccionar la adición debes primero seleccionar el tamaño de pizza/otro producto");
		return;
	}
}

function cerrarModalModCon()
{
	marcardorProductoCon = 0;
	$('#conProducto').modal('hide');
}

function ocultarModalModCon()
{
	$('#conProducto').modal('hide');
}

function ProductoSin()
{
	if ($("input[name=tamanoPizza]:radio").is(':checked'))
	{	
		if (controlaCantidadIngredientes == "S")
		{
			alert("Se tiene marcada el producto/Excepción como que controla ingredientes, por lo tanto no de berían de agregarse productos SIN");
			return;
		}
		var resValidacionEsp = validaSeleccionEspecilidad();
		if (resValidacionEsp == 1)
		{
			if(marcardorProductoSin == 0)
			{
				var str = '';
				var marcadorMitad = 0;
				str += '<table class="table table-bordered">';
				if ($("input:radio[name=formapizza]:checked").val() == 'mitad')
				{
					var especialidad1 = $("input:radio[name=mitad1]:checked").attr('id');
					var especialidad2 = $("input:radio[name=mitad2]:checked").attr('id');
					marcadorMitad = 1;
					str += '<thead><tr><td><h3>MITAD 1 - '+ especialidad1 +'</h3></td><td><h3>MITAD 2 - ' + especialidad2 +  '</h3></td></tr></thead>';
				}
				else
				{
					var especialidad1 = $("input:radio[name=mitad1]:checked").attr('id');
					str += '<thead><tr><td><h3>PIZZA ENTERA - ' + especialidad1 +'</h3></td><td></td></tr></thead>';
				}
				var indfila =1;
				
			    str += '<tbody>';
				for(var i = 0; i < productos.length;i++){
						var cadaModificador  = productos[i];
						var tamapizza = $("input:radio[name=tamanoPizza]:checked").val();
						
						if ((cadaModificador.tipo == "MODIFICADOR SIN") && (cadaModificador.productoasociaadicion == tamapizza ))
						{
							if (marcadorMitad == 1)
							{
								str +='<tr> ';
								str +='<td onclick="cambiaColorCeldaModSin(this);"> ';
								str += '<div class="col-md-4"> ';
								str += '<label><input type="checkbox"' + '  value="' + cadaModificador.nombre +'" name="' +'mitmodsin1' + cadaModificador.idproducto + '" onclick="revisarMarcacionModSin(this)">' + cadaModificador.nombre +'</label>';
								str += '</div>'
								str += '</td>';
								str +='<td onclick="cambiaColorCeldaModSin(this);"> ';
								str += '<div class="col-md-4"> ';
								str += '<label><input type="checkbox"' + '  value="' + cadaModificador.nombre +'" name="' +'mitmodsin2' + cadaModificador.idproducto + '" onclick="revisarMarcacionModSin(this)">' + cadaModificador.nombre +'</label>';
								str += '</div>'
								str += '</td> </tr>';
							}
							else
							{
								if (indfila == 1)
								{
									str +='<tr> ';
									str +='<td onclick="cambiaColorCeldaModSin(this);"> ';
									str += '<div class="col-md-4"> ';
									str += '<label><input type="checkbox"' + '  value="' + cadaModificador.nombre +'" name="' +'mitmodsin1' + cadaModificador.idproducto + '" onclick="revisarMarcacionModSin(this)">' + cadaModificador.nombre +'</label>';
									str += '</div>';
									str += '</td>';
								}
								else
								{
									str +='<td onclick="cambiaColorCeldaModSin(this);"> ';
									str += '<div class="col-md-4"> ';
									str += '<label><input type="checkbox"' + '  value="' + cadaModificador.nombre +'" name="' +'mitmodsin1' +  cadaModificador.idproducto + '" onclick="revisarMarcacionModSin(this)">' + cadaModificador.nombre +'</label>';
									str += '</div>';
									str += '</td> </tr>';
								}
								indfila = indfila +1;
								if(indfila ==3)
								{
									indfila = 1;
								}

								
							}
						}	
					}
					str += '</tbody> </table>';
					str += '<div class="modal-footer">';
					str += '<button type="button" onClick="cerrarModalModSin()" class="btn btn-default" data-dismiss="modal">Eliminar lo Seleccionado</button>';
					str += '<button type="button" onClick="ocultarModalModSin()" data-dismiss="modal" class="btn btn-primary">Guardar Modificadores Sin</button>';
					str += '</div>	';
					$('#pintarSin').html(str);
					$('div').click( function( e ) {
		    			e.stopPropagation();
		    			// ...
					});
					$('#sinProducto').modal('show');
					marcardorProductoSin = 1;
			}
			else
			{
				$('#sinProducto').modal('show');
			}
		}else
		{
			alert("Primero debe seleccionar la especialidad u Otro producto");
		}
	}
	else
	{
		alert("Para seleccionar la adición debes primero seleccionar el tamaño de pizza/otro producto");
		return;
	}
}

function cerrarModalModSin()
{
	marcardorProductoSin = 0;
	$('#sinProducto').modal('hide');
}

function ocultarModalModSin()
{
	$('#sinProducto').modal('hide');
}

function getOtrosGaseosa()
{
	if (marcadorGaseosas == 0)
	{
		var str = '';
		
		str += '<table class="table table-bordered">';
		var indfila =1;
		
	    str += '<tbody>';
		for(var i = 0; i < productoGaseosaHomologadaTienda.length;i++){
				var cadaGaseosa  = productoGaseosaHomologadaTienda[i];
								
				if (indfila == 1)
				{
					str +='<tr> ';
					str +='<td onclick="cambiaColorCelda(this);"> ';
					str += '<div class="col-md-7"> ';
					str += '<label><input type="checkbox"' + '  value="'+ "GA" + cadaGaseosa.idproducto + '-' + cadaGaseosa.nombre +'" name="' + "GA" + cadaGaseosa.idproducto + '" onclick="revisarMarcacion(this)">' + cadaGaseosa.nombre + " $" + cadaGaseosa.preciogeneral +'</label>';
					str += '</div>'
					str += '</td>';
				}
				else
				{
					str +='<td onclick="cambiaColorCelda(this);"> ';
					str += '<div class="col-md-7"> ';
					str += '<label><input type="checkbox"' + '  value="'+ "GA" + cadaGaseosa.idproducto + '-' + cadaGaseosa.nombre +'" name="' + "GA" + cadaGaseosa.idproducto + '" onclick="revisarMarcacion(this)">' + cadaGaseosa.nombre + " $" + cadaGaseosa.preciogeneral +'</label>';
					str += '</div>'
					str += '</td> </tr>';
				}
				indfila = indfila +1;
				if(indfila ==3)
				{
					indfila = 1;
				}
			}
			str += '</tbody> </table>';
			str += '<div class="modal-footer">';
			str += '<button type="button" onClick="cerrarModalGaseosa()" class="btn btn-default" data-dismiss="modal">Eliminar lo Seleccionado</button>';
			str += '<button type="button" onClick="agregarGaseosa()" data-dismiss="modal" class="btn btn-primary">Agregar Gaseosas</button>';
			str += '</div>	';
			$('#pintarGaseosa').html(str);
			$('div').click( function( e ) {
    			e.stopPropagation();
    			// ...
			});
			$('#addGaseosa').modal('show');
			//marcadorGaseosas = 1;
	}
	else
	{
		$('#addGaseosa').modal('show');
	}
}

function cerrarModalGaseosa()
{
	marcadorGaseosa = 0;
	$('#addGaseosa').modal('hide');
}

function agregarGaseosa()
{
	//OJOOOO VER COMO SE COMPORTA SI NO SE SELECCIONA NINGUNA PRODUCTO ADICIONAL
	var valida = ValidacionesDatosNoPizzas();
	if (valida != 1)
	{
		return;
	}
	agregarEncabezadoPedido();
	var tamanoPizza;
	var pizza;
	var otroProd;
	var cantidad;
	var especialidad1;
	var especial1;
	var especialidad2;
	var especial2;
	var codprod;
	var valorunitario = 0;
	var valortotal = 0;
	var observacion = '';
	var liquido = '';
	var idsabortipoliquido= 0;
	table = $('#grid-pedido').DataTable();
	var observacion = '';
	var excepcionPrecio = 0;
	var adiciones = '';
	var adicion = '';
	var idDetallePedido = 0;
	for(var i = 0; i < productos.length;i++)
	{
		var cadaProdu  = productos[i];
		if (cadaProdu.tipo == "GASEOSA" )
		{
			if ($('input:checkbox[name='+'GA'+cadaProdu.idproducto+']').is(':checked'))
			{
				pizza = '--';
				codprod = cadaProdu.idproducto;
				cantidad = 1;
				especialidad1 = '--';
				especial1 = '--';
				especialidad2 = '--';
				especial2 = '--';
				liquido = '--';
				valorunitario = cadaProdu.preciogeneral;
				valortotal= cadaProdu.preciogeneral * cantidad;
				totalpedido = totalpedido + valortotal; 
				otroProd = cadaProdu.idproducto+'-'+cadaProdu.nombre;
				adicion = '';
				adiciones = '';
				adicionarDetalleProducto(codprod,especialidad1,cantidad,especialidad2,valorunitario, valortotal,adicion, observacion,idsabortipoliquido,excepcionPrecio,pizza,otroProd,especial1,especial2,liquido,'S','S');
			}
		}
	}
	contadorItem = contadorItem + 1;
	$('#totalpedido').val(totalpedido);
	if ($("input:radio[name=requiereDevuelta]:checked").val() == "completo")
	{
		$("#valorpago").val(totalpedido);
	}
	$("#valordevolver").val($("#valorpago").val() - $("#totalpedido").val() );
	marcadorGaseosas = 0;
	$('#addGaseosa').modal('hide');
}

function getOtrosAdicionales()
{
	if (marcadorAdicionales == 0)
	{
		var str = '';
		
		str += '<table class="table table-bordered">';
		var indfila =1;
		
	    str += '<tbody>';
		for(var i = 0; i < productos.length;i++){
				var cadaAdicional  = productos[i];
								
				if (cadaAdicional.tipo == "ADICIONALES")
				{
						if (indfila == 1)
						{
							str +='<tr> ';
							str +='<td onclick="cambiaColorCelda(this);"> ';
							str += '<div class="col-md-7"> ';
							str += '<label><input type="checkbox"' + '  value="'+ "ADI" +cadaAdicional.idproducto + '-' + cadaAdicional.nombre +'" name="' + "ADI" + cadaAdicional.idproducto + '" onclick="revisarMarcacion(this)">' + cadaAdicional.nombre + " $" + cadaAdicional.preciogeneral ;
							if (cadaAdicional.manejacantidad = 'S')
							{
								str += '<input type="text" value="1" name="'+ "CADI" + cadaAdicional.idproducto +'" maxlength="4" size="4">';
							}
							str +=  '</label>';
							str += '</div>';
							str += '</td>';
						}
						else
						{
							str +='<td onclick="cambiaColorCelda(this);"> ';
							str += '<div class="col-md-7"> ';
							str += '<label><input type="checkbox"' + '  value="'+ "ADI" +cadaAdicional.idproducto + '-' + cadaAdicional.nombre +'" name="' + "ADI" + cadaAdicional.idproducto + '" onclick="revisarMarcacion(this)">' + cadaAdicional.nombre + " $" + cadaAdicional.preciogeneral ;
							if (cadaAdicional.manejacantidad = 'S')
							{
								str += '<input type="text" value="1" name="'+ "CADI" + cadaAdicional.idproducto +'" maxlength="4" size="4">';
							}
							str +=  '</label>';
							str += '</div>';
							str += '</td> </tr>';
						}
						indfila = indfila +1;
						if(indfila ==3)
						{
							indfila = 1;
						}

						
					
				}	
			}
			str += '</tbody> </table>';
			str += '<div class="modal-footer">';
			str += '<button type="button" onClick="cerrarModalAdicionales()" class="btn btn-default" data-dismiss="modal">Eliminar lo Seleccionado</button>';
			str += '<button type="button" onClick="AgregarAdicionales()" data-dismiss="modal" class="btn btn-primary">Agregar Adicionales</button>';
			str += '</div>	';
			$('#pintarAdicionales').html(str);
			$('div').click( function( e ) {
    			e.stopPropagation();
    			// ...
			});
			$('#addAdicionales').modal('show');
			marcadorAdicionales = 1;
			
	}
	else
	{
		$('#addAdicionales').modal('show');
	}
}

function cerrarModalAdicionales()
{
	marcadorAdicionales = 0;
	$('#addAdicionales').modal('hide');
}

function AgregarAdicionales()
{
	//OJOOOO VER COMO SE COMPORTA SI NO SE SELECCIONA NINGUNA PRODUCTO ADICIONAL
	var valida = ValidacionesDatosNoPizzas();
	if (valida != 1)
	{
		return;
	}
	agregarEncabezadoPedido();
	var tamanoPizza;
	var pizza;
	var otroProd;
	var cantidad;
	var especialidad1;
	var especial1;
	var especialidad2;
	var especial2;
	var codprod;
	var valorunitario = 0;
	var valortotal = 0;
	var observacion = '';
	var liquido = '';
	var idsabortipoliquido= 0;
	table = $('#grid-pedido').DataTable();
	var observacion = '';
	var excepcionPrecio = 0;
	var adiciones = '';
	var adicion = '';
	var idDetallePedido = 0;
	for(var i = 0; i < productos.length;i++)
	{
		var cadaProdu  = productos[i];
		if (cadaProdu.tipo == "ADICIONALES" )
		{
			if ($('input:checkbox[name='+'ADI'+cadaProdu.idproducto+']').is(':checked'))
			{
				pizza = '--';
				codprod = cadaProdu.idproducto;
				cantidad = 1;
				// Validamos si el producto maneja cantidades para cambiar el valor de cantidad
				if(cadaProdu.manejacantidad = "S")
				{
					cantidad = $('input:text[name='+'CADI'+cadaProdu.idproducto+']').val();
					if (!/^([0-9])*$/.test(cantidad))
					{
				    	cantidad = 1;
					}
					if (cantidad == "" || cantidad == null)
					{
						cantidad = 1;
					}
				}
				especialidad1 = '--';
				especial1 = '--';
				especialidad2 = '--';
				especial2 = '--';
				liquido = '--';
				valorunitario = cadaProdu.preciogeneral;
				valortotal= cadaProdu.preciogeneral * cantidad;
				totalpedido = totalpedido + valortotal; 
				otroProd = cadaProdu.idproducto+'-'+cadaProdu.nombre;
				adicion = '';
				adiciones = '';
				adicionarDetalleProducto(codprod,especialidad1,cantidad,especialidad2,valorunitario, valortotal,adicion, observacion,idsabortipoliquido,excepcionPrecio,pizza,otroProd,especial1,especial2,liquido,'S','S');
			}
		}
	}
	contadorItem = contadorItem + 1;
	$('#totalpedido').val(totalpedido);
	if ($("input:radio[name=requiereDevuelta]:checked").val() == "completo")
	{
		$("#valorpago").val(totalpedido);
	}
	$("#valordevolver").val($("#valorpago").val() - $("#totalpedido").val() );
	marcadorAdicionales = 0;
	$('#addAdicionales').modal('hide');
}

function adicionarDetalleProducto(codprod,especialidad1,cantidad,especialidad2,valorunitario, valortotal,adicion, observacion,idsabortipoliquido,excepcionPrecio,pizza,otroProd,especial1,especial2,liquido, indDup, indAvanz)
{
	var idDetallePedido = 0;
	$.ajax({ 
	    		url: server + 'InsertarDetallePedido?idproducto=' + codprod + "&idpedido=" + idPedido +"&especialidad1=" + especialidad1 + "&cantidad=" + cantidad +"&especialidad2=" + especialidad2 + "&valorunitario=" + valorunitario + "&valortotal=" + valortotal + "&adicion=" + adicion + "&observacion=" + observacion + "&idsabortipoliquido=" + idsabortipoliquido + "&idexcepcion=" + excepcionPrecio, 
	    		dataType: 'json', 
	    		async: false, 
	    		success: function(data){ 
					respuesta = data[0];
					idDetallePedido = respuesta.iddetallepedido;
				} 
			});
			var accion2;
			if (idDetallePedido > 0)
			{
				if(indDup == 'S')
				{
					accion2 = '<input type="button" class="btn btn-default btn-xs" onclick="duplicarDetallePedido(' + idDetallePedido + ')" value="Duplicar"></button>';
				}else{
					accion2 = '';
				}
				table.row.add( {
					"iddetallepedido": idDetallePedido,
					"numitem": contadorItem,
					"pizza":       pizza,
					"otroprod":   otroProd,
					"cantidad":    cantidad,
					"especialidad1": especial1,
					"especialidad2": especial2,
					"liquido": liquido,
					"observacion": observacion,
					"adicion": '--',
					"valorunitario": valorunitario,
					"valortotal": valortotal,
					"accion":'<input type="button" class="btn btn-default btn-xs" onclick="eliminarDetallePedido(' + idDetallePedido + ')" value="Eliminar"></button>',
					"accion2": accion2
				} ).draw();
				if (indAvanz == 'S')
				{
					contadorItem = contadorItem + 1;
				}
			}else
			{
				alert("Se presentó error en la inserción del Detalle de Pedido");	
				return;
			}
}

function getAdicionProductos()
{
	
	if (marcadorAdiciones == 0)
	{
		var str = '';
		var marcadorMitad = 0;
		var excepcionPrecio = $("#selectExcepcion option:selected").val();
		str += '<table class="table table-bordered">';
		if ($("input:radio[name=formapizza]:checked").val() == 'mitad')
		{
			var especialidad1 = $("input:radio[name=mitad1]:checked").attr('id');
			var especialidad2 = $("input:radio[name=mitad2]:checked").attr('id');
			if (((especialidad1 == undefined)||(especialidad2 == undefined))&&(excepcionPrecio == 'vacio'))
			{
				alert("Debe seleccionar ambas especialidades para poder definir las adiciones");
				return;
			}
			marcadorMitad = 1;
			str += '<thead><tr><td><h3>MITAD 1 - '+ especialidad1 +'</h3></td><td><h3>MITAD 2 - ' + especialidad2 +  '</h3></td></tr></thead>';
		}
		else if ($("input:radio[name=formapizza]:checked").val() == 'entera')
		{
			
			var especialidad1 = $("input:radio[name=mitad1]:checked").attr('id');
			if ((especialidad1 == undefined) &&(excepcionPrecio == 'vacio'))
			{
				alert("Debe seleccionar primero la especialidad");
				return;
			}
			str += '<thead><tr><td><h3>PIZZA ENTERA - ' + especialidad1 +'</h3></td></tr></thead>';
		} else
		{
			alert("Debe seleccionar primero entre mitad y mitad / entera");
			return;
		}
		var indfila =1;
		
	    str += '<tbody>';
		for(var i = 0; i < productos.length;i++){
				var cadaAdicion  = productos[i];
				var tamapizza = $("input:radio[name=tamanoPizza]:checked").val();
				
				if ((cadaAdicion.tipo == "ADICION") && (cadaAdicion.productoasociaadicion == tamapizza ))
				{
					if (marcadorMitad == 1)
					{
						str +='<tr> ';
						str +='<td onclick="cambiaColorCelda(this);"> ';
						str += '<div class="col-md-7"> ';
						str += '<label><input type="checkbox"' + '  value="'+ cadaAdicion.idproducto + '-' + cadaAdicion.nombre +'" name="' +'mit1' + cadaAdicion.idproducto + '" onclick="revisarMarcacion(this)">' + cadaAdicion.nombre +'</label>';
						str += '</div>'
						str += '<div class="col-md-3"> ';
						str += '<select name="'+ 'c1' + cadaAdicion.idproducto +'" class="form-control">';
						str += '<option selected value="0.5" id ="medio">0.5</option> <option value="1" id ="entero">1.0</option> </select>'
						str += '</div>'
						str += '</td>';
						str +='<td onclick="cambiaColorCelda(this);"> ';
						str += '<div class="col-md-7"> ';
						str += '<label><input type="checkbox"' + '  value="'+ cadaAdicion.idproducto + '-' + cadaAdicion.nombre +'" name="' +'mit2' + cadaAdicion.idproducto + '" onclick="revisarMarcacion(this)">' + cadaAdicion.nombre +'</label>';
						str += '</div>'
						str += '<div class="col-md-3"> ';
						str += '<select name="'+ 'c2' + cadaAdicion.idproducto +'" class="form-control">';
						str += '<option selected value="0.5" id ="medio">0.5</option> <option value="1" id ="entero">1.0</option> </select>';
						str += '</div>'
						str += '</td> </tr>';
					}
					else
					{
						if (indfila == 1)
						{
							str +='<tr> ';
							str +='<td onclick="cambiaColorCelda(this);"> ';
							str += '<div class="col-md-7"> ';
							str += '<label><input type="checkbox"' + '  value="'+ cadaAdicion.idproducto + '-' + cadaAdicion.nombre +'" name="' +'mit1' + cadaAdicion.idproducto + '" onclick="revisarMarcacion(this)">' + cadaAdicion.nombre +'</label>';
							str += '</div>'
							str += '<div class="col-md-3"> ';
							str += '<select name="'+ 'c1' + cadaAdicion.idproducto +'" class="form-control">';
							str += '<option value="0.5" id ="medio">0.5</option> <option selected value="1" id ="entero">1.0</option> </select>';
							str += '</div>';
							str += '</td>';
						}
						else
						{
							str +='<td onclick="cambiaColorCelda(this);"> ';
							str += '<div class="col-md-7"> ';
							str += '<label><input type="checkbox"' + '  value="'+ cadaAdicion.idproducto + '-' + cadaAdicion.nombre +'" name="' +'mit1' +  cadaAdicion.idproducto + '" onclick="revisarMarcacion(this)">' + cadaAdicion.nombre +'</label>';
							str += '</div>'
							str += '<div class="col-md-3"> ';
							str += '<select name="'+ 'c1' + cadaAdicion.idproducto +'" class="form-control">';
							str += '<option value="0.5" id ="medio">0.5</option> <option selected value="1" id ="1">1.0</option> </select>';
							str += '</div>';
							str += '</td> </tr>';
						}
						indfila = indfila +1;
						if(indfila ==3)
						{
							indfila = 1;
						}

						
					}
				}	
			}
			str += '</tbody> </table>';
			str += '<div class="modal-footer">';
			str += '<button type="button" onClick="cerrarModalAdiciones()" class="btn btn-default" data-dismiss="modal">Eliminar lo Seleccionado</button>';
			str += '<button type="button" onClick="ocultarModalAdiciones()" data-dismiss="modal" class="btn btn-primary">Guardar Adiciones</button>';
			str += '</div>	';
			$('#pintarAdiciones').html(str);
			$('div').click( function( e ) {
    			e.stopPropagation();
    			// ...
			});
			$('#addAdicion').modal('show');
			marcadorAdiciones = 1;
	}
	else
	{
		$('#addAdicion').modal('show');
	}
}

function cerrarModalAdiciones()
{
	marcadorAdiciones = 0;
	$('#addAdicion').modal('hide');
}

function cerrarModalUltimosPedidos()
{
	$('#ultimosPedidosCliente').modal('hide');
}

function ocultarModalAdiciones()
{
	$('#addAdicion').modal('hide');
}


function ReiniciarPedido()
{
	var resultado;
	$.confirm({
				'title'		: 'Confirmacion para Cancelar Pedido',
				'content'	: '<h4> <p style="color:#FF0000";> Desea confirmar la Cancelación del pedido? </p> </h4>',
				'autoClose': 'No|8000',
				'buttons'	: {
					'Si'	: {
						'class'	: 'blue',
						'action': function(){
							
							//Agregamos el ingreso de la clave del usuario para confirmar el logueo
							$.confirm({
							    title: 'Confirmar reinicio de Pedido',
							    content: '' +
							    '<form action="" class="formuConfirmarReinicio" id="formuConfirmarReinicio">' +
							    '<div class="form-group">' +
							    '<label>Ingrese Usuario</label>' +
							    '<input type="text" placeholder="Usuario" name="usuario" class="usuario form-control" required />' +
							    '<label>Ingrese Clave</label>' +
							    '<input type="password" placeholder="Clave" name="clave" class="clave form-control" required />' +
							    '</div>' +
							    '</form>',
							    buttons: {
							        formSubmit: {
							            text: 'Confirmar',
							            btnClass: 'btn-blue',
							            action: function () {
							                var usuario = this.$content.find('.usuario').val();
											var password = this.$content.find('.clave').val();
											$.ajax({ 
							    				url: server + 'GetIngresarAplicacion', 
							    				dataType: 'text',
							    				type: 'post', 
							    				data: {'txtUsuario' : usuario , 'txtPassword' : password }, 
							    				async: false, 
							    				success: function(data){ 
							    						resultado = data;
							    						if (resultado == 'OK')
														{
															if (idPedido != 0)
															{
																//Es porque ya hemos realizado la inserción de algún item por lo tanto consumimos servicio para eliminar lo agregado del pedido
																$.ajax({ 
												    				url: server + 'EliminarPedidoSinConfirmar?idpedido=' + idPedido , 
												    				dataType: 'json', 
												    				async: false, 
												    				success: function(data){
												    					var resul =  data[0];
												    					if (resul.respuesta == "true")
												    					{

												    					}
												    				},
																	error: function(){
																	    alert('Se produjo un error en la Eliminación del pedido');
																	 } 

																});
															}
																	//Limpieza de las variables y campos del formulario												
																	var strClr = "";
																	idPedido = 0;
																	memcode = 0;
																	idCliente = 0;
																	contadorItem = 1;
																    $('#otrosproductos').html(strClr);
																    $('#especialidades').html(strClr);
																    $('#pintarAdiciones').html(strClr);
																    //$('input:text[name=cantidad]').val('');
																    //Se reinician valores por defecto
																    $('input:radio[name=adicion]:nth(1)').attr('checked',true);
																    $('input:radio[name=tamanoPizza]').attr('checked',false);
																    $('input:radio[name=formapizza]').attr('checked',false);
																    $('input:radio[name=liquido]').attr('checked',false);
																    $('input:radio[name=requiereDevuelta]')[1].checked = true;
																    $('#valorpago').attr('disabled', false);
																    $("#selectExcepcion").val('vacio');
																    $('#observacionDir').val('');
																    $('#observacion').val('');
																    //Adicional al clareo de todo el pedido
																    $("#NumPedido").val('');
																	$("#IdCliente").val('');
																	$("#estadopedido").val('');
																	$('#telefono').val('');
																	$('#nombres').val('');
																	$('#apellidos').val('');
																	$('#nombreCompania').val('');
															        $('#direccion').val('');
															        $('#zona').val('');
															        $('#observacionDir').val('');
															        $("#selectTiendas").val('');
															        $("#selectNomenclaturas").val('');
															        $('#numNomen').val("");
															        $('#numNomen2').val("");
															        $('#num3').val("");
															        $('#descDireccion').val("");
															        $("#selectMunicipio").val("");
															        //reiniciamos el arreglo de productos
															        productos = "";
															        $('#FormTamanoPizza').html('');
															        $('#otrosproductos').html('');
																    $('#especialidades').html('');
																    $('#pintarAdiciones').html('');

															        $("#selectformapago").val(1);
															        if ( $.fn.dataTable.isDataTable( '#grid-clientes' ) ) {
															    		table = $('#grid-clientes').DataTable();
															    		table.clear().draw();
															    	}

															    	if ( $.fn.dataTable.isDataTable( '#grid-pedido' ) ) {
															    		table = $('#grid-pedido').DataTable();
															    		table.clear().draw();
															    	}
															    	$('input:radio[name=adicion]')[1].checked = true;
															    	totalpedido = 0;
															    	$("#totalpedido").val('0');
																	$("#valorpago").val('0');
																	$("#valordevolver").val('0');
																	marcadorAdiciones = 0;
																	marcardorProductoCon = 0;
																	marcardorProductoSin = 0;
																	marcadorAdicionales = 0;
																	marcadorGaseosas = 0;
																	//Volvemos a habilitar el select de tienda
																	$('#selectTiendas').attr('disabled', false);
																	$('#limpiar').attr('disabled', false);
																	$('#ultimospedidos').attr('disabled', true);
																	$('#transferircliente').attr('disabled', true);
																	$('#validaDir').prop('checked', true);
														}
														else
														{
															$.alert('No se elimino el pedido debido a que el usuario y clave es incorrecta');
														}
													} 
												});
							            }
							        },
							        cancel: function () {
							            //close
							        },
							    },
							    onContentReady: function () {
							        // bind to events
							        var jc = this;
							        this.$content.find('formuConfirmarReinicio').on('submit', function (e) {
							            // if the user submits the form by pressing enter in the field.
							            e.preventDefault();
							            jc.$$formSubmit.trigger('click'); // reference the button and click it
							        });
							    }
							});

							//Validamos que el logueo sea exitoso para eliminar el pedido
							
						}
					},
					'No'	: {
						'class'	: 'gray',
						'action': function(){

							$.alert('La acción de Reinicio de pedido fue cancelada');
						}	// Nothing to do in this case. You can as well omit the action property.
					}
				}
			});

}

function ConfirmarPedido()
{
	//Antes de finalizar el pedido validamos que si estén correctas las marcaciones
	var validaMarcaciones = validarMarcaciones();
	if(validaMarcaciones == false)
	{
		$.alert('Una o unas de las marcaciones de pedido no tienen observación.');
		return;
	}
	if(idPedido == 0 )
	{
		$.alert("Para confirmar el pedido debe tener por lo menos un item agregado");
		return;
	}
	if(dtpedido.data().count() == 0)
	{
		$.alert('Debe agregar por lo menos un item al pedido para poderlo finalizar');
		return;
	}
	var valordevolver =  $("#valordevolver").val();
	if(valordevolver >= 0)
	{
		// Se realiza llamado de servicio en sistema contact con el fin de obtener el tiempo actual de la tienda
		var tiempopedido;
		var idtien = $("#selectTiendas option:selected").attr('id');
		$.ajax({ 
			url: server + 'CRUDTiempoPedido?idoperacion=3&idtienda=' + idtien , 
			dataType: 'json',
			type: 'get', 
			async: false, 
			success: function(data2){
				tiempopedido = data2.tiempopedido;			    
			} 
		});
		if (tiempopedido == '' || tiempopedido == null || tiempopedido == undefined || tiempopedido == 0)
		{
			tiempopedido = 'No hay tiempo definido para la tienda';
		}
		var selMunicipio = $("#selectMunicipio").val();
		var dir = $("#selectNomenclaturas").val() +  " "  + $("#numNomen").val() + " # " + $("#numNomen2").val() + " - " + $("#num3").val() + " " + selMunicipio;
		$.confirm({
				'title'		: 'Confirmacion de Creación de Pedido',
				'content'	: 'Desea confirmar el Pedido Número ' + idPedido + '<br> El Pedido pasará a estado  Finalizado?'+
				'Con la siguiente información: <br>' +
				'CLIENTE: ' + $('#nombres').val() + ' ' + $('#apellidos').val() + '<br>' +
				'DIRECCION ' +  dir + '<br>' +
				'TOTAL PEDIDO ' + $("#totalpedido").val() + '<br>' +
				'CAMBIO ' + $("#valorpago").val() + '<br>' +
				'TIENDA DEL PEDIDO ' +  '<h1>' + $("#selectTiendas").val().toUpperCase() + '</h1> <br>' +
				'<h5> Tiempo Aproximado Pedido :  ' + tiempopedido  + ' Minutos </h5>',
				'type': 'dark',
   				'typeAnimated': true,
				'buttons'	: {
					'Si'	: {
						'class'	: 'blue',
						'action': function(){
							//Antes de confirmar pedido se realiza actualización cliente
							var tempTienda =  $("#selectTiendas option:selected").val();
							var tempMunicipio = $("#selectMunicipio option:selected").val();
							var idClienteTemporal;
							var nombresEncode = encodeURIComponent(nombres.value);
							var apellidosEncode = encodeURIComponent(apellidos.value);
							var nombreCompaniaEncode = encodeURIComponent(nombreCompania.value);;
							var direccionEncode;
							var validaDir = 'S';
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
							$.ajax({ 
					    				url: server + 'ActualizarCliente?telefono=' + telefono.value + "&nombres=" + nombresEncode + "&apellidos=" + apellidosEncode + "&nombreCompania=" + nombreCompaniaEncode +  "&direccion="  + direccionEncode  + "&tienda=" + tempTienda +  "&zona=" + zonaEncode + "&observacion=" + observacionDirEncode + "&municipio=" + tempMunicipio + "&longitud=" + longitud + "&latitud=" + latitud + "&memcode=" + memcode + "&idcliente=" + idCliente + "&idnomenclatura=" + nomenclatura + "&numnomenclatura1=" + numNomenclatura1 + "&numnomenclatura2=" + numNomenclatura2 +  "&num3=" + num3, 
					    				dataType: 'json', 
					    				async: false, 
					    				success: function(data){ 
											idClienteTemporal = data;
										} 
							});
							//finalizar actualizar cliente
							var resultado;
							var idformapago =  $("#selectformapago").val();
							var valorformapago =  $("#valorpago").val();
							$.ajax({ 
		    				url: server + 'FinalizarPedido?idpedido=' + idPedido + "&idformapago=" + idformapago + "&valortotal=" + totalpedido + "&valorformapago=" + valorformapago + "&idcliente=" + idCliente + "&insertado=" + insertado + "&tiempopedido=" + tiempopedido +"&validadir=" + validaDir, 
		    				dataType: 'json', 
		    				async: false, 
		    				success: function(data){ 
		    						//En este punto realizamos la inserción de los marcadores
		    						procesarMarcaciones(idPedido);
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
						    						//Debemos de insertar el número de pedido Pixel y cambiamos el estado del pedido a enviado
						    						$.getJSON(server + 'ActualizarNumeroPedidoPixel?idpedido=' + resPedPixel.idpedido + '&numpedidopixel=' + resPedPixel.numerofactura +  '&creacliente=' + resPedPixel.creacliente +  '&membercode=' + resPedPixel.membercode + '&idcliente=' + resPedPixel.idcliente, function(data2){

						    						});
						    					}
						    					else if(resPedPixel.numerofactura == -1)
						    					{
						    						$.alert('No se ha iniciado el día de facturación en la tienda ' + tempTienda + ', por favor comuniquese con la misma, el pedido en cuestión no fue enviado' );
						    					}
												
											} 
									});
									var strClr = "";
									idPedido = 0;
									memcode = 0;
									idCliente = 0;
									contadorItem = 1;
								    $('#otrosproductos').html(strClr);
								    $('#especialidades').html(strClr);
								    $('#pintarAdiciones').html(strClr);
								    //$('input:text[name=cantidad]').val('');
								    //Se reinician valores por defecto
								    $('input:radio[name=adicion]:nth(1)').attr('checked',true);
								    $('input:radio[name=tamanoPizza]').attr('checked',false);
								    $('input:radio[name=formapizza]').attr('checked',false);
								    $('input:radio[name=liquido]').attr('checked',false);
								    $('input:radio[name=requiereDevuelta]')[1].checked = true;
								    $('#valorpago').attr('disabled', false);
								    $("#selectExcepcion").val('vacio');
								    $('#observacionDir').val('');
								    $('#observacion').val('');
								    //Adicional al clareo de todo el pedido
								    $("#NumPedido").val('');
									$("#IdCliente").val('');
									$("#estadopedido").val('');
									$('#telefono').val('');
									$('#nombres').val('');
									$('#apellidos').val('');
									$('#nombreCompania').val('');
							        $('#direccion').val('');
							        $('#zona').val('');
							        $('#observacionDir').val('');
							        $("#selectTiendas").val('');
							        $("#selectNomenclaturas").val('');
							        $('#numNomen').val("");
							        $('#numNomen2').val("");
							        $('#num3').val("");
							        $('#descDireccion').val("");
							        $("#selectMunicipio").val("");
							        //reiniciamos el arreglo de productos
							        productos = "";
							        $('#FormTamanoPizza').html('');
							        $('#otrosproductos').html('');
								    $('#especialidades').html('');
								    $('#pintarAdiciones').html('');

							        $("#selectformapago").val(1);
							          if ( $.fn.dataTable.isDataTable( '#grid-clientes' ) ) {
							    		table = $('#grid-clientes').DataTable();
							    		table.clear().draw();
							    	}

							    	if ( $.fn.dataTable.isDataTable( '#grid-pedido' ) ) {
							    		table = $('#grid-pedido').DataTable();
							    		table.clear().draw();
							    	}
							    	$('input:radio[name=adicion]')[1].checked = true;
							    	totalpedido = 0;
							    	$("#totalpedido").val('0');
									$("#valorpago").val('0');
									$("#valordevolver").val('0');
									//Volvemos a habilitar el select de tienda
									$('#selectTiendas').attr('disabled', false);
									$('#limpiar').attr('disabled', false);
									$('#validaDir').prop('checked', true);
									$('#ultimospedidos').attr('disabled', true);
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

    }
    else
    {
    	alert("El valor de pago debe ser mínimo el valor total");
    }

    
}

function ValidacionesDatosNoPizzas()
{
	//validamos campo de telefono
	var tele =  telefono.value;
	if (tele == '' || tele == null)
	{
		alert ('Debe ingresar un telefono para el cliente');
		return;
	}


	if (!/^([0-9])*$/.test(tele))
	{
      alert("El valor " + tele + " no es un número");
      return;
	}

	//validamos campo Nombres
	var nomb = nombres.value;
	if (nomb == '' || nomb == null)
	{
		alert ('Debe ingresar los nombres del Cliente');
		return;
	}
		//validamos campo Direccion
	var dir = direccion.value;
	//if (dir == '' || dir == null)
	//{
	//	alert ('Debe ingresar la dirección del cliente');
	//	return;
	//}
	var nomenclatura = $("#selectNomenclaturas option:selected").val();
	var numNomen1 =  $("#numNomen").val();
	var numNomen2 =  $("#numNomen2").val();
	var num3 = $("#num3").val();
	if($('#validaDir').is(':checked'))
	{
		if (nomenclatura == '' || nomenclatura == null || numNomen1== '' || numNomen1 == null || numNomen1 == 'null' || numNomen2== '' || numNomen2 == null || numNomen2  == 'null' || num3== '' || num3 == null || num3 == 'null')
		{
			alert ('Faltan datos de la dirección, por favor completelos');
			return;
		}
	}
	else
	{
		if (dir == '' || dir == null)
		{
			alert ('Aunque tiene definido no validar dirección, debe por lo menos ingresar la indicación del sitio');
			return;
		}
	}
	var tien = $("#selectTiendas option:selected").val();
	if (tien == '' || tien == null || tien == undefined)
	{
		alert ('Debe ingresar la tienda del pedido');
		return;
	}
	var muni = $("#selectMunicipio option:selected").val();
	if (muni == '' || muni == null|| muni == undefined)
	{
		alert ('Debe ingresar el Municipio del Cliente');
		return;
	}
	return(1);
}

function ValidacionesDatos()
{
	//validamos campo de telefono
	var tele =  telefono.value;
	if (tele == '' || tele == null)
	{
		alert ('Debe ingresar un telefono para el cliente');
		return;
	}


	if (!/^([0-9])*$/.test(tele))
	{
      alert("El valor " + tele + " no es un número");
      return;
	}

	//validamos campo Nombres
	var nomb = nombres.value;
	if (nomb == '' || nomb == null)
	{
		alert ('Debe ingresar los nombres del Cliente');
		return;
	}
		//validamos campo Direccion
	var dir = direccion.value;
	//if (dir == '' || dir == null)
	//{
	//	alert ('Debe ingresar la dirección del cliente');
	//	return;
	//}
	var nomenclatura = $("#selectNomenclaturas option:selected").val();
	var numNomen1 =  $("#numNomen").val();
	var numNomen2 =  $("#numNomen2").val();
	var num3 = $("#num3").val();
	if($('#validaDir').is(':checked'))
	{
		if (nomenclatura == '' || nomenclatura == null || numNomen1== '' || numNomen1 == null || numNomen1 == 'null' || numNomen2== '' || numNomen2 == null || numNomen2  == 'null' || num3== '' || num3 == null || num3 == 'null')
		{
			alert ('Faltan datos de la dirección, por favor completarlos');
			return;
		}
	}
	else
	{
		if (dir == '' || dir == null)
		{
			alert ('Aunque tiene definido no validar dirección, debe por lo menos ingresar la indicación del sitio');
			return;
		}
	}
	var tien = $("#selectTiendas option:selected").val();
	if (tien == '' || tien == null || tien == undefined)
	{
		alert ('Debe ingresar la tienda del pedido');
		return;
	}
	var muni = $("#selectMunicipio option:selected").val();
	if (muni == '' || muni == null|| muni == undefined)
	{
		alert ('Debe ingresar el Municipio del Cliente');
		return;
	}
	var tamapizza = $("input:radio[name=tamanoPizza]:checked").val();
	if (tamapizza == '' || tamapizza == null)
	{
		alert ('Debe seleccionar entre Pizza u otros productos');
		return;
	}
	//Realizamos validaciones del CON en caso de controlacantidadingredientes
	if(controlaCantidadIngredientes == 'S')
	{
		var cantidadConMitad1 = 0;
		var cantidadConMitad2 = 0;
		for(var i = 0; i < productos.length;i++)
		{
			var cadaProdu  = productos[i];
			if (cadaProdu.tipo == "MODIFICADOR CON")
			{
				if ($("input:radio[name=formapizza]:checked").val() == 'mitad')
				{
					if ($('input:checkbox[name='+'mitmodcon1'+cadaProdu.idproducto+']').is(':checked'))
					{
						cantidadConMitad1 = cantidadConMitad1 + 1;
					}

					if ($('input:checkbox[name='+'mitmodcon2'+cadaProdu.idproducto+']').is(':checked'))
					{
						cantidadConMitad2 = cantidadConMitad2 + 1;
					}
				}
				else
				{
					if ($('input:checkbox[name='+'mitmodcon1'+cadaProdu.idproducto+']').is(':checked'))
					{
						cantidadConMitad1 = cantidadConMitad1 + 1;
					}	
				}
			}
		}
		if ($("input:radio[name=formapizza]:checked").val() == 'mitad')
		{
			if((cantidadConMitad1 > cantidadIngredientes) || (cantidadConMitad2 > cantidadIngredientes))
			{
				alert("La excepción de precio es de " + cantidadIngredientes  + " ingredientes, y se están agregando " + cantidadConMitad1 + " ingredientes en la mitad 1 y " + cantidadConMitad2 + " ingredientes en la mitad 2, FAVOR CORREGIR");
				return;
			}
			if((cantidadConMitad1 == 0)||(cantidadConMitad2 == 0))
			{
				alert("La pizza es con control de ingredientes y al menos una de las dos mitades no tiene ingredientes");
				return;
			}
		}else
		{
			if((cantidadConMitad1 > cantidadIngredientes))
			{
				alert("La excepción de precio es de " + cantidadIngredientes  + " ingredientes, y se están agregando " + cantidadConMitad1 + " ingredientes,  FAVOR CORREGIR");
				return;
			}
			if(cantidadConMitad1 == 0)
			{
				alert("La pizza es con control de ingredientes no tiene ingredientes seleccionados");
				return;
			}
		}

	}

	if (tamapizza == 'otros'){
		var otros = $("input:radio[name=otros]:checked").val();
		if (otros == '' || otros == null || otros == undefined)
		{
			alert ('Usted seleccionó otros productos, por favor seleccione un producto de la categoría otros productos');
			return;	
		}
	}else{

		if ($("input:radio[name=formapizza]:checked").val() == 'mitad'){
			var especial1 =  $("input:radio[name=mitad1]:checked").val();
			if ((especial1 == '' || especial1 == null || especial1 == undefined) && (controlaCantidadIngredientes == 'N'))
			{
				alert ('Usted seleccionó Pizza mitad/mitad, por favor seleccione el sabor de la primera mitad');
				return;	
			}
			var especial2 =  $("input:radio[name=mitad2]:checked").val();
			if ((especial2 == '' || especial2 == null || especial2 == undefined) && (controlaCantidadIngredientes == 'N'))
			{
				alert ('Usted seleccionó Pizza mitad/mitad, por favor seleccione el sabor de la segunda mitad');
				return;	
			}
		}else if ($("input:radio[name=formapizza]:checked").val() == 'entera'){
		
			var especial1 =  $("input:radio[name=mitad1]:checked").val();
			if ((especial1 == '' || especial1 == null || especial1 == undefined) && (controlaCantidadIngredientes == 'N'))
			{
				alert ('Usted seleccionó Pizza de un solo sabor, por favor seleccione el sabor de la Pizza');
				return;	
			}
		}else if ($("input:radio[name=formapizza]:checked").val() == '' || $("input:radio[name=formapizza]:checked").val() ==  null || $("input:radio[name=formapizza]:checked").val() == undefined )
		{
			alert ('Debe seleccionar entre pizza Mitad/Mitad o entera');
			return;	
		}

	}
	
	//var cant  = cantidad.value;
	//if (cant == '' || cant == null)
	//	{
	//		alert ('Por favor seleccione la cantidad del producto que desea ordenar');
	//		return;	
	//	}
		return(1);
}

function validaSeleccionEspecilidad()
{
	//Retorna 0 si no pasa la validación o 1 si pasa la validación
	var tamapizza = $("input:radio[name=tamanoPizza]:checked").val();
	if (tamapizza == undefined)
	{
		
		return(0);
	}
	
	if (tamapizza == 'otros'){
		var otros = $("input:radio[name=otros]:checked").val();
		if (otros == '' || otros == null)
		{
			
			return(0);	
		}
		return(1);
	}else{
		var formapizza = $("input:radio[name=formapizza]:checked").val();
		if(formapizza == undefined)
		{
			return(0);
		}
		if ( formapizza == 'mitad'){
			var especial1 =  $("input:radio[name=mitad1]:checked").val();
			if (especial1 == '' || especial1 == null)
			{
				
				return(0);	
			}
			var especial2 =  $("input:radio[name=mitad2]:checked").val();
			if (especial2 == '' || especial2 == null)
			{
				return(0);	
			}
		}
		if ($("input:radio[name=formapizza]:checked").val() == 'entera'){
		
			var especial1 =  $("input:radio[name=mitad1]:checked").val();
			if (especial1 == '' || especial1 == null)
			{
				return(0);	
			}
		}

	}
	return(1);

}

function agregarEncabezadoPedido()
{
	if (idPedido == 0)
	{
		var tempTienda =  $("#selectTiendas option:selected").val();
		var tempMunicipio = $("#selectMunicipio option:selected").val();
		var respuesta;
		var idClienteTemporal;
		var fechapedido = $("#fechapedido").val();
		var nombresEncode = encodeURIComponent(nombres.value);;
		var apellidosEncode = encodeURIComponent(apellidos.value);
		var nombreCompaniaEncode = encodeURIComponent(nombreCompania.value);
		if($('#validaDir').is(':checked'))
		{
			direccionEncode = encodeURIComponent($("#selectNomenclaturas").val() +  " "  + $("#numNomen").val() + " # " + $("#numNomen2").val() + " - " + $("#num3").val());
		}else
		{
			direccionEncode = encodeURIComponent(direccion.value);
		}var zonaEncode = encodeURIComponent(zona.value); 
		var observacionDirEncode = encodeURIComponent(observacionDir.value);
		var nomenclatura =  $("#selectNomenclaturas option:selected").attr('id');
		var numNomenclatura1 = encodeURIComponent($("#numNomen").val());
		var numNomenclatura2 = encodeURIComponent($("#numNomen2").val());
		var num3 = encodeURIComponent($("#num3").val());
		$.ajax({ 
    				url: server + 'InsertarClienteEncabezadoPedido?telefono=' + telefono.value + "&nombres=" + nombresEncode + "&apellidos=" + apellidosEncode + "&nombreCompania=" + nombreCompaniaEncode +  "&direccion="  + direccionEncode  + "&tienda=" + tempTienda +  "&zona=" + zonaEncode + "&observacion=" + observacionDirEncode + "&municipio=" + tempMunicipio + "&longitud=" + longitud + "&latitud=" + latitud + "&memcode=" + memcode + "&idcliente=" + idCliente + "&fechapedido=" + fechapedido + "&idnomenclatura=" + nomenclatura + "&numnomenclatura1=" + numNomenclatura1 + "&numnomenclatura2=" + numNomenclatura2 +  "&num3=" + num3, 
    				dataType: 'json', 
    				async: false, 
    				success: function(data){ 
						respuesta = data[0];
						idClienteTemporal = respuesta.idcliente;
						if (idClienteTemporal != 0)
						{
							idPedido = respuesta.idpedido;
							idEstadoPedido = respuesta.idestadopedido;
							$("#estadopedido").val(respuesta.descripcionestadopedido);
						}
						else
						{
							alert("Se tuvieron inconvenientes al momento de insertar el cliente");
							return;
						}
						
											} 
		});
		$("#NumPedido").val(idPedido);
		$("#IdCliente").val(idClienteTemporal);
		if ((idCliente > 0 ) && (idCliente == idClienteTemporal))
		{
			insertado = 0;
		}
		if ((idCliente == 0 ) && (idClienteTemporal  > 0))
		{
			insertado = 1;
			idCliente = idClienteTemporal;
		}
		//Realizamos intervención para bloquear que se cambie la tienda
		$('#selectTiendas').attr('disabled', true);
		$('#limpiar').attr('disabled', true);
		$('#transferircliente').attr('disabled', true);
		//		
	}
}

function agregarProducto()
{
	var valida = ValidacionesDatos();
	var modespecialidad1= "";
	var modespecialidad2= "";
	var arregloAdiciones = [];
	var arregloModificadores = [];
	var ingredienteConMitad1="";
	var ingredienteConMitad2="";
	var ingredienteSinMitad1="";
	var ingredienteSinMitad2="";
	if (valida != 1)
	{
		return;
	}
	//Invocamos método para insertar EncabezadoPedido, en caso del que la variable global idPedido sea igual a cero
	agregarEncabezadoPedido();
	
	var tamanoPizza;
	var pizza;
	var otroProd;
	var cantidad;
	var especialidad1;
	var especial1;
	var especialidad2;
	var especial2;
	var codprod;
	var valorunitario = 0;
	var valortotal = 0;
	var observacion = '';
	var liquido = '';
	var idsabortipoliquido= 0;
	observacion = $("textarea#observacion").val();
	// Datatable que se manejará para el pedido
	table = $('#grid-pedido').DataTable();
	var excepcionPrecio;
	var adiciones = '';
	var adicion = '';
	var idDetallePedido = 0;
	// Manejo de las adiciones por producto
	//Hacemos validación de la excepción de precio
			excepcionPrecio = $("#selectExcepcion option:selected").val();
			var partiradiciones = "";
			if (excepcionPrecio != 'vacio')
			{
				codprod = tamanoPizza = $("input:radio[name=tamanoPizza]:checked").val();
				for(var j = 0; j < excepciones.length; j++)
				{
					var cadaExcepcion = excepciones[j];
					if(cadaExcepcion.idexcepcion == excepcionPrecio)
					{
						if(cadaExcepcion.idproducto == codprod )
						{
							partiradiciones = cadaExcepcion.partiradiciones;
						}
					}
				}
				// Si es excepción de precio en producto nos aparecerá la descripción de la excepción
				
			}

	if($("input:radio[name=adicion]:checked").val()=='noadicion')
	{
		adiciones = '--';
	}
	else
	{
		for(var i = 0; i < productos.length;i++)
		{
			var cadaProdu  = productos[i];
			if (cadaProdu.tipo == "ADICION" )
			{
				if ($("input:radio[name=formapizza]:checked").val() == 'mitad')
				{
					if ($('input:checkbox[name='+'mit1'+cadaProdu.idproducto+']').is(':checked'))
					{
						//console.log(cadaProdu.nombre);
						pizza = '--';
						codprod = cadaProdu.idproducto;
						cantidad = $('select[name='+'c1'+cadaProdu.idproducto+']').val();
						especialidad1 = '--';
						especial1 = '--';
						especialidad2 = '--';
						especial2 = '--';
						liquido = '--';
						valorunitario = cadaProdu.preciogeneral;
						valortotal= cadaProdu.preciogeneral * cantidad;
						totalpedido = totalpedido + valortotal; 
						otroProd = cadaProdu.idproducto+'-'+cadaProdu.nombre;
						adicion = cadaProdu.idproducto + '-' + cadaProdu.nombre;
						adiciones = adiciones + cadaProdu.idproducto + '-' + cadaProdu.nombre + "-MIT1" + '/';
						
						//Primero deberíamos hacer la validación que se pueda insertar en la tabla  de base de datos y de allí si lanzar 
						
						$.ajax({ 
	    				url: server + 'InsertarDetallePedido?idproducto=' + codprod + "&idpedido=" + idPedido +"&especialidad1=" + especialidad1 + "&cantidad=" + cantidad +"&especialidad2=" + especialidad2 + "&valorunitario=" + valorunitario + "&valortotal=" + valortotal + "&adicion=" + adicion + "&observacion=" + observacion + "&idsabortipoliquido=" + idsabortipoliquido + "&idexcepcion=" + excepcionPrecio, 
	    				dataType: 'json', 
	    				async: false, 
	    				success: function(data){ 
							respuesta = data[0];
							idDetallePedido = respuesta.iddetallepedido;
							
						} 
						});

						if (idDetallePedido > 0)
						{
							
							table.row.add( {
								"iddetallepedido": idDetallePedido,
								"numitem": contadorItem,
						        "pizza":       pizza,
						        "otroprod":   otroProd,
						        "cantidad":    cantidad,
						        "especialidad1": especial1,
						        "especialidad2": especial2,
						        "liquido": liquido,
						        "observacion": observacion,
						        "adicion": '--',
						        "valorunitario": valorunitario,
						        "valortotal": valortotal,
						        "accion":'<input type="button" class="btn btn-default btn-xs" onclick="eliminarDetallePedido(' + idDetallePedido + ')" value="Eliminar"></button>',
						        "accion2":''
						    } ).draw();
						    var adi = new adicionDetallePedido(0,idDetallePedido,$("input:radio[name=mitad1]:checked").val(),cantidad, 0,0);
							arregloAdiciones.push(adi);
						}else
						{
							alert("Se presentó error en la inserción del Detalle de Pedido");	
							return;
						}
					}

					// Validamos si la otra mitad tiene la adición 
					if ($('input:checkbox[name='+'mit2'+cadaProdu.idproducto+']').is(':checked'))
					{
						//console.log(cadaProdu.nombre);
						pizza = '--';
						codprod = cadaProdu.idproducto;
						cantidad = $('select[name='+'c2'+cadaProdu.idproducto+']').val();
						especialidad1 = '--';
						especial1 = '--';
						especialidad2 = '--';
						especial2 = '--';
						liquido = '--';
						valorunitario = cadaProdu.preciogeneral;
						valortotal= cadaProdu.preciogeneral * cantidad;
						totalpedido = totalpedido + valortotal; 
						otroProd = cadaProdu.idproducto+'-'+cadaProdu.nombre;
						adicion = cadaProdu.idproducto + '-' + cadaProdu.nombre;
						adiciones = adiciones + cadaProdu.idproducto + '-' + cadaProdu.nombre + "-MIT2" + '/';
						//Primero deberíamos hacer la validación que se pueda insertar en la tabla  de base de datos y de allí si lanzar 
						
						$.ajax({ 
	    				url: server + 'InsertarDetallePedido?idproducto=' + codprod + "&idpedido=" + idPedido +"&especialidad1=" + especialidad1 + "&cantidad=" + cantidad +"&especialidad2=" + especialidad2 + "&valorunitario=" + valorunitario + "&valortotal=" + valortotal + "&adicion=" + adicion + "&observacion=" + observacion + "&idsabortipoliquido=" + idsabortipoliquido + "&idexcepcion=" + excepcionPrecio, 
	    				dataType: 'json', 
	    				async: false, 
	    				success: function(data){ 
							respuesta = data[0];
							idDetallePedido = respuesta.iddetallepedido;
							
						} 
						});

						if (idDetallePedido > 0)
						{
							
							table.row.add( {
								"iddetallepedido": idDetallePedido,
								"numitem": contadorItem,
						        "pizza":       pizza,
						        "otroprod":   otroProd,
						        "cantidad":    cantidad,
						        "especialidad1": especial1,
						        "especialidad2": especial2,
						        "liquido": liquido,
						        "observacion": observacion,
						        "adicion": '--',
						        "valorunitario": valorunitario,
						        "valortotal": valortotal,
						        "accion":'<input type="button" class="btn btn-default btn-xs" onclick="eliminarDetallePedido(' + idDetallePedido + ')" value="Eliminar"></button>',
						        "accion2":'' 
						    } ).draw();
						    var adi = new adicionDetallePedido(0,idDetallePedido, 0,0,$("input:radio[name=mitad2]:checked").val(),cantidad);
							arregloAdiciones.push(adi);
						}else
						{
							alert("Se presentó error en la inserción del Detalle de Pedido");	
							return;
						}
					}
				}
				else
				{
					if ($('input:checkbox[name='+'mit1'+cadaProdu.idproducto+']').is(':checked'))
					{
						//console.log(cadaProdu.nombre);
						pizza = '--';
						codprod = cadaProdu.idproducto;
						cantidad = $('select[name='+'c1'+cadaProdu.idproducto+']').val();
						especialidad1 = '--';
						especial1 = '--';
						especialidad2 = '--';
						especial2 = '--';
						liquido = '--';
						valorunitario = cadaProdu.preciogeneral;
						valortotal= cadaProdu.preciogeneral * cantidad;
						totalpedido  = totalpedido + valortotal; 
						otroProd = cadaProdu.idproducto+'-'+cadaProdu.nombre;
						adicion = cadaProdu.idproducto + '-' + cadaProdu.nombre;
						adiciones = adiciones + cadaProdu.idproducto + '-' + cadaProdu.nombre + "-MIT1" + '/';
						//Primero deberíamos hacer la validación que se pueda insertar en la tabla  de base de datos y de allí si lanzar 
						
						$.ajax({ 
	    				url: server + 'InsertarDetallePedido?idproducto=' + codprod + "&idpedido=" + idPedido +"&especialidad1=" + especialidad1 + "&cantidad=" + cantidad +"&especialidad2=" + especialidad2 + "&valorunitario=" + valorunitario + "&valortotal=" + valortotal + "&adicion=" + adicion + "&observacion=" + observacion + "&idsabortipoliquido=" + idsabortipoliquido + "&idexcepcion=" + excepcionPrecio, 
	    				dataType: 'json', 
	    				async: false, 
	    				success: function(data){ 
							respuesta = data[0];
							idDetallePedido = respuesta.iddetallepedido;
							
						} 
						});

						if (idDetallePedido > 0)
						{
							
							table.row.add( {
								"iddetallepedido": idDetallePedido,
								"numitem": contadorItem,
						        "pizza":       pizza,
						        "otroprod":   otroProd,
						        "cantidad":    cantidad,
						        "especialidad1": especial1,
						        "especialidad2": especial2,
						        "liquido": liquido,
						        "observacion": observacion,
						        "adicion": '--',
						        "valorunitario": valorunitario,
						        "valortotal": valortotal,
						        "accion":'<input type="button" class="btn btn-default btn-xs" onclick="eliminarDetallePedido(' + idDetallePedido + ')" value="Eliminar"></button>',
						        "accion2":'' 
						    } ).draw();
							if(cantidad == 1)
							{
								if( partiradiciones == "" || partiradiciones == 'S')
								{
									var adi = new adicionDetallePedido(0,idDetallePedido, $("input:radio[name=mitad1]:checked").val(),0.5,0,0);
									arregloAdiciones.push(adi);
									adi = new adicionDetallePedido(0,idDetallePedido,0,0, $("input:radio[name=mitad1]:checked").val(),0.5);
									arregloAdiciones.push(adi);
								}
								else
								{
									var adi = new adicionDetallePedido(0,idDetallePedido, $("input:radio[name=mitad1]:checked").val(),1,0,0);
									arregloAdiciones.push(adi);
								}
								
							}
							else
							{
								var adi = new adicionDetallePedido(0,idDetallePedido, $("input:radio[name=mitad1]:checked").val(),0.5,0,0);
								arregloAdiciones.push(adi);
							}
							
						}else
						{
							alert("Se presentó error en la inserción del Detalle de Pedido");	
							return;
						}
					}
				}
				
				
			}
		}
	}
	
	// Se realiza un recorrido para tomar la informacion de los modificadores con y SIN
	for(var i = 0; i < productos.length;i++)
	{
		var cadaProdu  = productos[i];
		if (cadaProdu.tipo == "MODIFICADOR CON")
		{
			if ($("input:radio[name=formapizza]:checked").val() == 'mitad')
			{
				if ($('input:checkbox[name='+'mitmodcon1'+cadaProdu.idproducto+']').is(':checked'))
				{
					modespecialidad1 += cadaProdu.descripcion + " / ";
					ingredienteConMitad1 = ingredienteConMitad1 + " " + $('input:checkbox[name='+'mitmodcon1'+cadaProdu.idproducto+']').val();
					if (cadaProdu.preciogeneral > 0)
					{
						pizza = '--';
						codprod = cadaProdu.idproducto;
						cantidad = 0.5;
						especialidad1 = '--';
						especial1 = '--';
						especialidad2 = '--';
						especial2 = '--';
						liquido = '--';
						valorunitario = cadaProdu.preciogeneral;
						valortotal= cadaProdu.preciogeneral * cantidad;
						totalpedido = totalpedido + valortotal; 
						otroProd = cadaProdu.nombre;
						$.ajax({ 
	    				url: server + 'InsertarDetallePedido?idproducto=' + codprod + "&idpedido=" + idPedido +"&especialidad1=" + especialidad1 + "&cantidad=" + cantidad +"&especialidad2=" + especialidad2 + "&valorunitario=" + valorunitario + "&valortotal=" + valortotal + "&adicion=" + adicion + "&observacion=" + observacion + "&idsabortipoliquido=" + idsabortipoliquido + "&idexcepcion=" + excepcionPrecio, 
	    				dataType: 'json', 
	    				async: false, 
	    				success: function(data){ 
							respuesta = data[0];
							idDetallePedido = respuesta.iddetallepedido;
							
						} 
						});
						
						if (idDetallePedido > 0)
						{
							
							table.row.add( {
								"iddetallepedido": idDetallePedido,
								"numitem": contadorItem,
						        "pizza":       pizza,
						        "otroprod":   otroProd,
						        "cantidad":    cantidad,
						        "especialidad1": especial1,
						        "especialidad2": especial2,
						        "liquido": liquido,
						        "observacion": observacion,
						        "adicion": '--',
						        "valorunitario": valorunitario,
						        "valortotal": valortotal,
						        "accion":'<input type="button" class="btn btn-default btn-xs" onclick="eliminarDetallePedido(' + idDetallePedido + ')" value="Eliminar"></button>',
						        "accion2":''
						    } ).draw();
						    
						}else
						{
							alert("Se presentó error en la inserción del Detalle de Pedido");	
							return;
						}
					}
					var mod = new modificadorDetallePedido(0,cadaProdu.idproducto,0,1,idDetallePedido);
					arregloModificadores.push(mod);
					idDetallePedido = 0;
				}

				if ($('input:checkbox[name='+'mitmodcon2'+cadaProdu.idproducto+']').is(':checked'))
				{
					modespecialidad2 += cadaProdu.descripcion + " / ";
					ingredienteConMitad2 = ingredienteConMitad2 + " " + $('input:checkbox[name='+'mitmodcon2'+cadaProdu.idproducto+']').val();
					if (cadaProdu.preciogeneral > 0)
					{
						pizza = '--';
						codprod = cadaProdu.idproducto;
						cantidad = 0.5;
						especialidad1 = '--';
						especial1 = '--';
						especialidad2 = '--';
						especial2 = '--';
						liquido = '--';
						valorunitario = cadaProdu.preciogeneral;
						valortotal= cadaProdu.preciogeneral * cantidad;
						totalpedido = totalpedido + valortotal; 
						otroProd = cadaProdu.nombre;
						$.ajax({ 
	    				url: server + 'InsertarDetallePedido?idproducto=' + codprod + "&idpedido=" + idPedido +"&especialidad1=" + especialidad1 + "&cantidad=" + cantidad +"&especialidad2=" + especialidad2 + "&valorunitario=" + valorunitario + "&valortotal=" + valortotal + "&adicion=" + adicion + "&observacion=" + observacion + "&idsabortipoliquido=" + idsabortipoliquido + "&idexcepcion=" + excepcionPrecio, 
	    				dataType: 'json', 
	    				async: false, 
	    				success: function(data){ 
							respuesta = data[0];
							idDetallePedido = respuesta.iddetallepedido;
							
						} 
						});
						if (idDetallePedido > 0)
						{
							
							table.row.add( {
								"iddetallepedido": idDetallePedido,
								"numitem": contadorItem,
						        "pizza":       pizza,
						        "otroprod":   otroProd,
						        "cantidad":    cantidad,
						        "especialidad1": especial1,
						        "especialidad2": especial2,
						        "liquido": liquido,
						        "observacion": observacion,
						        "adicion": '--',
						        "valorunitario": valorunitario,
						        "valortotal": valortotal,
						        "accion":'<input type="button" class="btn btn-default btn-xs" onclick="eliminarDetallePedido(' + idDetallePedido + ')" value="Eliminar"></button>' ,
						        "accion2":''
						    } ).draw();
						    
						}else
						{
							alert("Se presentó error en la inserción del Detalle de Pedido");	
							return;
						}
					}
					var mod = new modificadorDetallePedido(0,0,cadaProdu.idproducto,1,idDetallePedido);
					arregloModificadores.push(mod);
					idDetallePedido = 0;
 				}
			}
			else
			{
				if ($('input:checkbox[name='+'mitmodcon1'+cadaProdu.idproducto+']').is(':checked'))
				{
					modespecialidad1 += cadaProdu.descripcion + " / ";
					ingredienteConMitad1 = ingredienteConMitad1 + " " + $('input:checkbox[name='+'mitmodcon1'+cadaProdu.idproducto+']').val();
					if (cadaProdu.preciogeneral > 0)
					{
						pizza = '--';
						codprod = cadaProdu.idproducto;
						cantidad = 1;
						especialidad1 = '--';
						especial1 = '--';
						especialidad2 = '--';
						especial2 = '--';
						liquido = '--';
						valorunitario = cadaProdu.preciogeneral;
						valortotal= cadaProdu.preciogeneral * cantidad;
						totalpedido = totalpedido + valortotal; 
						otroProd = cadaProdu.nombre;
						$.ajax({ 
	    				url: server + 'InsertarDetallePedido?idproducto=' + codprod + "&idpedido=" + idPedido +"&especialidad1=" + especialidad1 + "&cantidad=" + cantidad +"&especialidad2=" + especialidad2 + "&valorunitario=" + valorunitario + "&valortotal=" + valortotal + "&adicion=" + adicion + "&observacion=" + observacion + "&idsabortipoliquido=" + idsabortipoliquido + "&idexcepcion=" + excepcionPrecio, 
	    				dataType: 'json', 
	    				async: false, 
	    				success: function(data){ 
							respuesta = data[0];
							idDetallePedido = respuesta.iddetallepedido;
							
						} 
						});
						
						if (idDetallePedido > 0)
						{
							
							table.row.add( {
								"iddetallepedido": idDetallePedido,
								"numitem": contadorItem,
						        "pizza":       pizza,
						        "otroprod":   otroProd,
						        "cantidad":    cantidad,
						        "especialidad1": especial1,
						        "especialidad2": especial2,
						        "liquido": liquido,
						        "observacion": observacion,
						        "adicion": '--',
						        "valorunitario": valorunitario,
						        "valortotal": valortotal,
						        "accion":'<input type="button" class="btn btn-default btn-xs" onclick="eliminarDetallePedido(' + idDetallePedido + ')" value="Eliminar"></button>' ,
						        "accion2":''
						    } ).draw();
						    
						}else
						{
							alert("Se presentó error en la inserción del Detalle de Pedido");	
							return;
						}
					}
					var mod = new modificadorDetallePedido(0,cadaProdu.idproducto,0,1,idDetallePedido);
					arregloModificadores.push(mod);
					idDetallePedido = 0;
				}	
			}
		}
		else if(cadaProdu.tipo == "MODIFICADOR SIN")
		{
			if ($("input:radio[name=formapizza]:checked").val() == 'mitad')
			{
				if ($('input:checkbox[name='+'mitmodsin1'+cadaProdu.idproducto+']').is(':checked'))
				{
					modespecialidad1 += cadaProdu.descripcion + " / ";
					var mod = new modificadorDetallePedido(0,cadaProdu.idproducto,0,1,0);
					arregloModificadores.push(mod);
					ingredienteSinMitad1 = ingredienteSinMitad1 + " " +  $('input:checkbox[name='+'mitmodsin1'+cadaProdu.idproducto+']').val();
				}

				if ($('input:checkbox[name='+'mitmodsin2'+cadaProdu.idproducto+']').is(':checked'))
				{
					modespecialidad2 += cadaProdu.descripcion + " / ";
					var mod = new modificadorDetallePedido(0,0,cadaProdu.idproducto,1,0);
					arregloModificadores.push(mod);
					ingredienteSinMitad2 = ingredienteSinMitad2 + " " +  $('input:checkbox[name='+'mitmodsin2'+cadaProdu.idproducto+']').val();
				}
			}
			else
			{
				if ($('input:checkbox[name='+'mitmodsin1'+cadaProdu.idproducto+']').is(':checked'))
				{
					modespecialidad1 += cadaProdu.descripcion + " / ";
					var mod = new modificadorDetallePedido(0,cadaProdu.idproducto,0,1,0);
					arregloModificadores.push(mod);
					ingredienteSinMitad1 = ingredienteSinMitad1 + " " +  $('input:checkbox[name='+'mitmodsin1'+cadaProdu.idproducto+']').val();
				}
			}
		}

	}



	excepcionPrecio = $("#selectExcepcion option:selected").val();


	tamanoPizza = $("input:radio[name=tamanoPizza]:checked").val();
	//cantidad = $('input:text[name=cantidad]').val();	
	cantidad = 1;
	if (tamanoPizza == 'otros'){
		pizza = '--';
		especialidad1 = '--';
		especial1 = '--';
		especialidad2 = '--';
		especial2 = '--';
		var tempVal = $("input:radio[name=liquido]:checked").val();
		if ( tempVal > 0 ) {
  			liquido = $("input:radio[name=liquido]:checked").attr('id');
  			idsabortipoliquido = $("input:radio[name=liquido]:checked").val();
		}
		otroProd = $("input:radio[name=otros]:checked").val();
		if (otroProd == null){
			alert ("Debe Seleccionar el producto diferente a pizza");
			return;
		}
		codprod = $("input:radio[name=otros]:checked").attr('id');
		for(var i = 0; i < productos.length;i++){
			var cadaProdu  = productos[i];
			if (cadaProdu.idproducto == codprod )
			{
				valorunitario = cadaProdu.preciogeneral;
				valortotal = valorunitario * cantidad;
				//totalpedido  = totalpedido + valortotal; 
			}
		}
		if (excepcionPrecio != 'vacio')
		{
			for(var j = 0; j < excepciones.length; j++)
			{
				var cadaExcepcion = excepciones[j];
				if(cadaExcepcion.idexcepcion == excepcionPrecio)
				{
					if(cadaExcepcion.idproducto == codprod )
					{
						valorunitario = cadaExcepcion.precio;
						valortotal = valorunitario * cantidad;
						//totalpedido  = totalpedido + valortotal; 
					}
					else{
						alert("La Excepcion de precio no esta asociada al producto seleccionado por favor corrija su elección");
						return;
					}
				}
			}
		}
		//En este punto sacamos el valor total con el fin de no sumarlo dos veces
		totalpedido  = totalpedido + valortotal;
	}else{
			
			pizza = tamanoPizza.toLowerCase();
			
			for(var i = 0; i < productos.length;i++){
				var cadaProdu  = productos[i];
				if (cadaProdu.idproducto == pizza )
				{
					codprod = cadaProdu.idproducto;
					pizza = cadaProdu.nombre;
					valorunitario = cadaProdu.preciogeneral;
					valortotal = valorunitario * cantidad;
					//totalpedido  = totalpedido + valortotal; 
					break;
				}
			}
			//
			
			if (excepcionPrecio != 'vacio')
			{
				for(var j = 0; j < excepciones.length; j++)
				{
					var cadaExcepcion = excepciones[j];
					if(cadaExcepcion.idexcepcion == excepcionPrecio)
					{
						if(cadaExcepcion.idproducto == codprod )
						{
							valorunitario = cadaExcepcion.precio;
							valortotal = valorunitario * cantidad;
							
							//totalpedido  = totalpedido + valortotal; 
						}
						else{
							alert("La Excepcion de precio no esta asociada al producto seleccionado por favor corrija su elección");
							return;
						}
					}
				}
				// Si es excepción de precio en producto nos aparecerá la descripción de la excepción
				pizza = excepcionSeleccionada.descripcion;
			}
			//En este punto sacamos el valor total con el fin de no sumarlo dos veces
			totalpedido  = totalpedido + valortotal;
			otroProd = '--';
			if ($("input:radio[name=formapizza]:checked").val() == 'mitad'){
				especialidad1 =  $("input:radio[name=mitad1]:checked").val();
				if (especialidad1 == undefined)
				{
					especial1 = "--" + " " + ingredienteConMitad1 + " " + ingredienteSinMitad1;
				}
				else
				{	
					var precioAdicional = 0;
					$.ajax({ 
			    		url: server + 'ObtenerExcepcionEspecialidad?idproducto=' + codprod + "&idespecialidad=" + especialidad1 , 
			    		dataType: 'json', 
			    		async: false, 
			    		success: function(data){ 
			    			var temporal;
							temporal = data[0];
							precioAdicional = temporal.precio*0.5;
						}
					});
					valorunitario = valorunitario + precioAdicional;
					valortotal = valortotal + precioAdicional*cantidad;
					totalpedido = totalpedido + precioAdicional; 
					especial1 =  $("input:radio[name=mitad1]:checked").attr('id') + " " + ingredienteConMitad1 + " " + ingredienteSinMitad1;
				}
				
				especialidad2 =  $("input:radio[name=mitad2]:checked").val();
				if (especialidad2 == undefined)
				{
					especial2 = "--" + " " + ingredienteConMitad2 + " " + ingredienteSinMitad2;
				}
				else
				{
					var precioAdicional = 0;
					$.ajax({ 
			    		url: server + 'ObtenerExcepcionEspecialidad?idproducto=' + codprod + "&idespecialidad=" + especialidad2 , 
			    		dataType: 'json', 
			    		async: false, 
			    		success: function(data){ 
			    			var temporal;
							temporal = data[0];
							precioAdicional = temporal.precio*0.5;
						}
					});
					valorunitario = valorunitario + precioAdicional;
					valortotal = valortotal + precioAdicional*cantidad;
					totalpedido = totalpedido + precioAdicional; 
					especial2 = $("input:radio[name=mitad2]:checked").attr('id')+ " " + ingredienteConMitad2 + " " + ingredienteSinMitad2;
				}
				
			}else if ($("input:radio[name=formapizza]:checked").val() == 'entera'){
					especialidad1 =  $("input:radio[name=mitad1]:checked").val();
					if (especialidad1 == undefined)
					{
						especial1 = "--" + " " + ingredienteConMitad1 + " " + ingredienteSinMitad1;
						especial2 = "--";
					}
					else
					{	
						var precioAdicional = 0;
						$.ajax({ 
				    		url: server + 'ObtenerExcepcionEspecialidad?idproducto=' + codprod + "&idespecialidad=" + especialidad1 , 
				    		dataType: 'json', 
				    		async: false, 
				    		success: function(data){ 
				    			var temporal;
								temporal = data[0];
								precioAdicional = temporal.precio;
							}
						});
						valorunitario = valorunitario + precioAdicional;
						valortotal = valortotal + precioAdicional*cantidad;
						totalpedido = totalpedido + precioAdicional; 
						especial1 = $("input:radio[name=mitad1]:checked").attr('id') + " " + ingredienteConMitad1 + " " + ingredienteSinMitad1;
						especialidad2 = "--";
						especial2 = '--';
					}
					
			}
	}
	
		if (cantidad == 0  || cantidad ==''){
			alert ('Debe ingresar la cantidad del Producto que va a ordenar');
		}
	var idDetallePedido = 0;
	var tempVal = $("input:radio[name=liquido]:checked").val();
	if ( tempVal > 0 ) {
  			liquido = $("input:radio[name=liquido]:checked").attr('id');
  			idsabortipoliquido = $("input:radio[name=liquido]:checked").val();
	}
	
	$.ajax({ 
    				url: server + 'InsertarDetallePedido?idproducto=' + codprod + "&idpedido=" + idPedido +"&especialidad1=" + especialidad1 +  "&cantidad=" + cantidad + "&especialidad2=" + especialidad2 + "&valorunitario=" + valorunitario + "&valortotal=" + valortotal + "&adicion=" + adiciones + "&observacion=" + observacion+"&idsabortipoliquido=" +idsabortipoliquido +"&idexcepcion=" + excepcionPrecio + "&modespecialidad1=" + modespecialidad1 + "&modespecialidad2=" + modespecialidad2, 
    				dataType: 'json', 
    				async: false, 
    				success: function(data){ 
						respuesta = data[0];
						idDetallePedido = respuesta.iddetallepedido;
						
					} 
		});

	if (idDetallePedido > 0 )
	{
		table.row.add( {
			"iddetallepedido": idDetallePedido,
			"numitem": contadorItem,
	        "pizza":       pizza,
	        "otroprod":   otroProd,
	        "cantidad":    cantidad,
	        "especialidad1": especial1,
	        "especialidad2": especial2,
	        "liquido": liquido,
	        "observacion": observacion,
	        "adicion": adiciones,
	        "valorunitario": valorunitario,
	        "valortotal": valortotal,
	        "accion":'<input type="button" class="btn btn-default btn-xs" onclick="eliminarDetallePedido(' + idDetallePedido + ')" value="Eliminar"></button>',
	        "accion2":'<input type="button" class="btn btn-default btn-xs" onclick="duplicarDetallePedido(' + idDetallePedido + ')" value="Duplicar"></button>'
	    } ).draw();
	    //Ya se creó el iddetallepedido del producto grueso
	    
	    // En este punto vamos a verificar si el producto adicionado tiene productos adicionales configurados de ser así se agregan tambien
	    for (var j = 0; j<productosIncluidos.length;j++)
	    {
	    	prodInc = productosIncluidos[j];
	    	if(prodInc.idproductopadre == codprod)
	    	{
	    		adicionarDetalleProducto(prodInc.idproductohijo,'',prodInc.cantidad,'',prodInc.preciogeneral, prodInc.preciogeneral*prodInc.cantidad ,'', 'Producto Incluido-'+idDetallePedido,'','','',prodInc.nombre,'','','','N','N');
	    		// En caso tal de que el producto a agregar tenga un precio diferente a cero debremos agregar este valor al total pedido
	    		if(prodInc.preciogeneral > 0)
	    		{
	    			totalpedido = totalpedido + prodInc.preciogeneral*prodInc.cantidad; 
	    		}
	    	}
	    }

	    // Comenzamos la inserción de los detalles de la adiciones en la tabla adicional de adiciones
	    var idadicion = 0;
	    for (var i = 0; i<arregloAdiciones.length;i++)
	    {
	    	//La idea es tener aqui un tema para detallar las adiciones y realizar las inserciones.
	    	$.ajax({ 
    				url: server + 'InsertarDetalleAdicion?iddetallepedidopadre=' + idDetallePedido+ "&iddetallepedidoadicion=" + arregloAdiciones[i].idDetallePedidoAdicion +"&idespecialidad1=" + arregloAdiciones[i].idEspecialidad1 +  "&idespecialidad2=" + arregloAdiciones[i].idEspecialidad2 + "&cantidad1=" + arregloAdiciones[i].cantidad1 + "&cantidad2=" + arregloAdiciones[i].cantidad2, 
    				dataType: 'json', 
    				async: false, 
    				success: function(data){ 
						respuesta = data[0];
						idAdicion = respuesta.idadicion;
						
					} 
			});
	    }
	    

	    //En este punto realizamos la inserción de los modificadore con y sin de los productos
	    var idModificador = 0;
	    for (var i = 0; i<arregloModificadores.length;i++)
	    {
	    	//La idea es tener aqui un tema para detallar las adiciones y realizar las inserciones.
	    	$.ajax({ 
    				url: server + 'InsertarModificadorDetallePedido?iddetallepedidopadre=' + idDetallePedido +"&idproductoespecialidad1=" + arregloModificadores[i].idProductoEspecialidad1 +  "&idproductoespecialidad2=" + arregloModificadores[i].idProductoEspecialidad2 + "&cantidad=" + arregloModificadores[i].cantidad + "&iddetallepedido=" + arregloModificadores[i].idDetallePedidoAsociado , 
    				dataType: 'json', 
    				async: false, 
    				success: function(data){ 
						respuesta = data[0];
						idModificador = respuesta.idmodificador;
						
					} 
			});
	    }


	    //Se requiere clarear la información de cada item
	    arregloAdiciones = [];
	    arregloModificadores = [];
	    marcadorAdiciones = 0;
	    marcardorProductoCon = 0;
		marcardorProductoSin = 0;
		marcadorAdicionales = 0;
		marcadorGaseosas = 0;
		contadorItem = contadorItem + 1;
	    var strClr = "";
	    $('#otrosproductos').html(strClr);
	    $('#especialidades').html(strClr);
	    $('#pintarAdiciones').html(strClr);
	    $('#pintarSin').html(strClr);
	    $('#pintarCon').html(strClr);
	    //$('input:text[name=cantidad]').val('');
	    //Se reinician valores por defecto
	    $('input:radio[name=adicion]:nth(1)').attr('checked',true);
	    $('input:radio[name=tamanoPizza]').attr('checked',false);
	    $('input:radio[name=formapizza]').attr('checked',false);
	    $('input:radio[name=liquido]').attr('checked',false);
	    $("#selectExcepcion").val('vacio');
	    $('#observacion').val('');
	    $('input:radio[name=adicion]')[1].checked = true;
	    $('#totalpedido').val(totalpedido);
	    if ($("input:radio[name=requiereDevuelta]:checked").val() == "completo")
		{
			$("#valorpago").val(totalpedido);
		}
	    $("#valordevolver").val($("#valorpago").val() - $("#totalpedido").val() );
	    controlaCantidadIngredientes = "N";
		cantidadIngredientes = 0;
		excepcionSeleccionada = null;

	}else
	{
		alert("Se presentó error en la inserción del Detalle de Pedido");
	}

// PARA LOS BOTONES DE CAMBIAR CANTIDADES




}



function changeQuantity(qty) 
{
	var resultado = Number($('#cantidad').val())+Number(qty);
	if (resultado < 0 )
	{
			alert("La Cantidad no puede ser menor a cero");
	}
	else
	{


			$('#cantidad').val(Number($('#cantidad').val())+Number(qty));
	}
    //document.cart_quantity['quantity'].value = Number(document.cart_quantity['quantity'].value)+Number(qty);
    
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
          url: 'https://raw.githubusercontent.com/Andres-FA/KMLZonasDeReparto/master/PizzaAmericana-ZonasDeRepartoTotales-Ver_03.kml',
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
		url: 'https://raw.githubusercontent.com/Andres-FA/KMLZonasDeReparto/master/PizzaAmericana-ZonasDeRepartoTotales-Ver_03.kml',
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

function cambiaColorCelda(elemento)
{
	radio = elemento.getElementsByTagName('input')[0];
	label = elemento.getElementsByTagName('label')[0];
	if(elemento.style.backgroundColor=="teal")
	{
		elemento.style.backgroundColor="white";
		label.style.backgroundColor="white";
		radio.checked = false;
	}else
	{
		elemento.style.backgroundColor="teal";
		label.style.backgroundColor="teal";
		radio.checked = true;
	}
	
}

function cambiaColorCeldaModSin(elemento)
{
	radio = elemento.getElementsByTagName('input')[0];
	label = elemento.getElementsByTagName('label')[0];
	if(elemento.style.backgroundColor=="teal")
	{
		elemento.style.backgroundColor="white";
		label.style.backgroundColor="white";
		radio.checked = false;
	}else
	{
		elemento.style.backgroundColor="teal";
		label.style.backgroundColor="teal";
		radio.checked = true;
	}
	
}

function revisarMarcacionModSin(check)
{
	var label;
	var tempTable;
	var table;
	if(check.checked)
	{
		label = check.parentNode;
		label.style.backgroundColor="teal";
		tempTable = label.parentNode;
		table = tempTable.parentNode;
		table.style.backgroundColor="teal";
	}
	else
	{
		label = check.parentNode;
		label.style.backgroundColor="white";
		tempTable = label.parentNode;
		table = tempTable.parentNode;
		table.style.backgroundColor="white";
	}
}

function revisarMarcacion(check)
{
	var label;
	var tempTable;
	var table;
	if(check.checked)
	{
		label = check.parentNode;
		label.style.backgroundColor="teal";
		tempTable = label.parentNode;
		table = tempTable.parentNode;
		table.style.backgroundColor="teal";
	}
	else
	{
		label = check.parentNode;
		label.style.backgroundColor="white";
		tempTable = label.parentNode;
		table = tempTable.parentNode;
		table.style.backgroundColor="white";
	}
}

function cambiaColorCeldaEspMitad1(elemento)
{
	var elementoAnterior; 
	radioEsp1Ant = radioEsp1;
	radioEsp1 = elemento.getElementsByTagName('input')[0];
	if(elemento.style.backgroundColor=="teal")
	{
		elemento.style.backgroundColor="white";
		radioEsp1.checked = false;
	}else
	{
		elemento.style.backgroundColor="teal";
		radioEsp1.style.backgroundColor="teal";
		radioEsp1.checked = true;
	}
	
	if (radioEsp1Ant != 0)
	{
		elementoAnterior = radioEsp1Ant.parentNode;
		elementoAnterior.style.backgroundColor="white";
		elementoAnterior = elementoAnterior.parentNode;
		elementoAnterior.style.backgroundColor="white";
	}
	
}

function cambiaColorCeldaEspMitad2(elemento)
{
	var elementoAnterior; 
	radioEsp2Ant = radioEsp2;
	radioEsp2 = elemento.getElementsByTagName('input')[0];
	if(elemento.style.backgroundColor=="teal")
	{
		elemento.style.backgroundColor="white";
		radioEsp2.checked = false;
	}else
	{
		elemento.style.backgroundColor="teal";
		radioEsp2.style.backgroundColor="teal";
		radioEsp2.checked = true;
	}
	
	if (radioEsp2Ant != 0)
	{
		elementoAnterior = radioEsp2Ant.parentNode;
		elementoAnterior.style.backgroundColor="white";
		elementoAnterior = elementoAnterior.parentNode;
		elementoAnterior.style.backgroundColor="white";
	}
	
}


function adicionDetallePedido (idDetallePedidoPadre,idDetallePedidoAdicion, idEspecialidad1, cantidad1, idEspecialidad2, cantidad2)
{
	
	this.idDetallePedidoPadre = idDetallePedidoPadre;
	this.idDetallePedidoAdicion = idDetallePedidoAdicion;
	this.idEspecialidad1 = idEspecialidad1;
	this.cantidad1 = cantidad1;
	this.idEspecialidad2 = idEspecialidad2;
	this.cantidad2 = cantidad2;
}

function modificadorDetallePedido (idDetallePedidoPadre,idProductoEspecialidad1, idProductoEspecialidad2, cantidad, iddetallepedidoasociado)
{
	
	this.idDetallePedidoPadre = idDetallePedidoPadre;
	this.idProductoEspecialidad1 = idProductoEspecialidad1;
	this.idProductoEspecialidad2 = idProductoEspecialidad2;
	this.cantidad = cantidad;
	this.idDetallePedidoAsociado  = iddetallepedidoasociado;
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
		productos = "";
		$('#FormTamanoPizza').html('');
		$('#otrosproductos').html('');
		$('#especialidades').html('');
		$('#pintarAdiciones').html('');

        //Volvemos  a habiliar el select de tiendas si es qúe está deshabilitado
        $('#selectTiendas').attr('disabled', false);
        $("#selectMunicipio").val("");
        $('#validaDir').prop('checked', true);
        $('#ultimospedidos').attr('disabled', true);
        $('#transferircliente').attr('disabled', true);
        //$("#validaDir input[type=checkbox]").prop('checked', true);
        memcode = 0;
        idCliente = 0;
        if ( $.fn.dataTable.isDataTable( '#grid-clientes' ) ) 
        {
			table = $('#grid-clientes').DataTable();
			table.clear().draw();
		}
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

function validarFechaMenorActual(date1){
      	var hoy = new Date();
		var dd = hoy.getDate();
		var mm = hoy.getMonth()+1; //January is 0!

		var yyyy = hoy.getFullYear();
		if(dd<10){
		    dd='0'+dd;
		} 
		if(mm<10){
		    mm='0'+mm;
		} 
		var date2 = dd+'/'+mm+'/'+yyyy;
      var fechaPedido = new Date();
      var fechaActual = new Date();
      var fecha1 = date1.split("/");
      var fecha2 = date2.split("/");
      fechaPedido.setFullYear(fecha1[2],fecha1[1]-1,fecha1[0]);
      fechaActual.setFullYear(fecha2[2],fecha2[1]-1,fecha2[0]);
      
      if (fechaPedido >= fechaActual)
      {
        return true;
   	  }
      else
      {
        return false;
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

function transferirCliente()
{
	//Seleccionar la tienda origen
	var tiendaOrigen = $("#selectTiendas option:selected").attr('id');
	//Realiza la actualizacion
	var telef = telefono.value;
	var tempTienda =  $("#selectTiendasTrasladar option:selected").val();
    //Variable en la que alojamos el id de la tienda
    var tienda = $("#selectTiendasTrasladar option:selected").attr('id');
    var idMunicipio = $("#selectMunicipio option:selected").attr('id');
    //Realizamos la validación que la tienda origen y destino no sean las mismas
    if(tiendaOrigen == tienda)
    {
    	$.alert('La tienda origen y destino no pueden ser las mismas.');
    	$("#selectTiendasTrasladar").val('');
 		return;
    }
    var tempMunicipio = $("#selectMunicipio option:selected").val();
    var idClienteCreado;
    var idClienteTienda;
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
                            $.ajax({ 
                                url: server + 'ActualizarCliente?telefono=' + telef + "&nombres=" + nombresEncode + "&apellidos=" + apellidosEncode + "&nombreCompania=" + nombreCompaniaEncode +  "&direccion="  + direccionEncode  + "&tienda=" + tempTienda +  "&zona=" + zonaEncode + "&observacion=" + observacionDirEncode + "&municipio=" + tempMunicipio + "&longitud=" + longitud + "&latitud=" + latitud + "&memcode=0" + "&idcliente=0" + "&idnomenclatura=" + nomenclatura + "&numnomenclatura1=" + numNomenclatura1 + "&numnomenclatura2=" + numNomenclatura2 +  "&num3=" + num3, 
                                dataType: 'json', 
                                async: false, 
                                success: function(data){

                                    idClienteCreado = data[0].idcliente;
                                    //console.log("CLIENTE CREADO ACTUALIZADO " + idClienteCreado);
                                    var urlTienda;
                                    var pos;
                                    var dsnodbc;
                                     $.ajax({ 
                                         url: server + 'ObtenerUrlTienda?idtienda=' + tienda, 
                                         dataType: 'json', 
                                         async: false, 
                                         success: function(data2){ 
                                            //console.log(data2);
                                            urlTienda = data2[0].urltienda;
                                            pos = data2[0].pos;
                                            dsnodbc = data2[0].dsnodbc;
                                        } 
                                    });
                                    //Se consume el servicio de la tienda para la actualización creación del cliente
                                     $.ajax({ 
                               			 url: urlTienda + 'ActualizarCliente?telefono=' + telef + "&nombres=" + nombresEncode + "&apellidos=" + apellidosEncode + "&nombreCompania=" + nombreCompaniaEncode +  "&direccion="  + direccionEncode  + "&tienda=" + tempTienda +  "&zona=" + zonaEncode + "&observacion=" + observacionDirEncode + "&municipio=" + tempMunicipio + "&longitud=" + longitud + "&latitud=" + latitud + "&memcode=0" + "&idcliente=0" + "&idnomenclatura=" + nomenclatura + "&numnomenclatura1=" + numNomenclatura1 + "&numnomenclatura2=" + numNomenclatura2 +  "&num3=" + num3 + "&dsnodbc=" + dsnodbc + "&pos=" + pos + "&idmunicipio=" + idMunicipio + "&idtienda=" + tienda, 
                                		dataType: 'json', 
                                		async: false, 
                                		success: function(data1){
                                			idClienteTienda = data1[0].idclitienda;
                                			$.getJSON(server + 'ActualizarMemcode?membercode=' + idClienteTienda + "&idcliente=" + idClienteCreado, function(data3){
	                                        	//console.log(data3);
	                                   		 });
	                                        $.alert('Se creó el nuevo cliente correctamente');

	                                	}
                                	});
                                    
                                } 
                            }); 
    limpiarSeleccionCliente();
     $('#telefono').val(telef);
    validarTelefono(); 
	$("#selectTiendasTrasladar").val('');
	$('#transferircliente').attr('disabled', true);
	$('#transCliente').modal('hide');
}

function mostrarTransferirCliente()
{
	$('#transCliente').modal('show');
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

