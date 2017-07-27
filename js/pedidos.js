	

var server;
var tiendas;
var table;
var dtpedido;
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
var marcardorProductoCon = 0;
var marcardorProductoSin = 0;
var canvas;
var ctx
var totalpedido = 0;
var radioEsp1Ant= 0;
var radioEsp1 = 0;
var radioEsp2Ant= 0;
var radioEsp2 = 0;



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
		    	
			} 
		});
	//var canvas = document.getElementById("pintarpedido");
	//if (canvas.getContext) 
	//{
 
    //    var ctx = canvas.getContext("2d");
    //}

    //ctx.arc(360,70,50,0,(Math.PI/180)*360,true);
	//ctx.strokeStyle = "#f00";
	//ctx.lineWidth = 10;
	//ctx.stroke();

	//Llenamos arreglo con los productos
	getTodosProductos();
	//cargarMapa();
	//Cargamos los productos tipo Pizza para el menu inicial
	
	//Final cargue productos pizza


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
            { "mData": "memcode"  , "visible": false }
        ]
    	} );

    dtpedido = $('#grid-pedido').DataTable( {
    		"aoColumns": [
    		{ "mData": "iddetallepedido" },
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
            }
        ]
    	} );

     
    $('#grid-clientes tbody').on('click', 'tr', function () {
        datos = table.row( this ).data();
        //alert( 'Diste clic en  '+datos.nombre+'\'s row' );
        $('#nombres').val(datos.nombre);
        $('#apellidos').val(datos.apellido);
        $('#nombreCompania').val(datos.nombrecompania);
        $('#direccion').val(datos.direccion);
        $('#zona').val(datos.zona);
        $('#observacionDir').val(datos.observacion);
        $("#selectTiendas").val(datos.tienda);
        $("#selectMunicipio").val(datos.municipio);
        memcode = datos.memcode;
        idCliente = datos.idCliente;
        var municipio = datos.municipio;
        var dirbuscar = datos.direccion + " " + municipio.toLowerCase();
        buscarMapa(dirbuscar);
     } );
 	

 	$("input[name=formapizza]:radio").click(function() { 
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



$(function(){
	
	getListaTiendas();
	getListaMunicipios();
	getExcepcionesPrecios();
	getFormasPago();
	$("#totalpedido").val('0');
	$("#valorpago").val('0');
	$("#valordevolver").val('0');
	$('input:radio[name=adicion]')[1].checked = true;
	$("#valorpago").change(function(){
			$("#valordevolver").val($("#valorpago").val() - $("#totalpedido").val() );
            
	});
	
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


function eliminarDetallePedido(iddetallepedido)
{
	/*var table = $('#grid-especialidades').DataTable();
	var datos = table.row( this ).data();
	console.log(datos);
	idespecialidad = datos.idespecialidad;
	nombre = datos.nombre;*/
	
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
											$('#totalpedido').val(valorTotalDespues.valortotal);
	    									$("#valordevolver").val($("#valorpago").val() - $("#totalpedido").val() );
										});
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

function getListaMunicipios(){

	$.getJSON(server + 'CRUDMunicipio?idoperacion=5', function(data){
		
		var str = '';
		for(var i = 0; i < data.length;i++){
			var cadaMunicipio  = data[i];
			str +='<option value="'+ cadaMunicipio.nombre +'" id ="'+ cadaMunicipio.idmunicipio +'">' + cadaMunicipio.nombre +'</option>';
		}
		$('#selectMunicipio').html(str);
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
		//console.log(selExcepcion + " " + idSelExcepcion);
	});
}

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
			str += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaEspe.idespecialidad + '" id="' + cadaEspe.nombre + '" name="mitad1">' + cadaEspe.abreviatura + '/' + cadaEspe.nombre + '</label>';
			str += '</td>';
			str += '<td onclick="cambiaColorCeldaEspMitad2(this);">';
			str += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaEspe.idespecialidad + '" id="' + cadaEspe.nombre + '" name="mitad2">' + cadaEspe.abreviatura + '/' + cadaEspe.nombre + '</label>';
			str += '</td> </tr>';

		}
		str += "</tbody> </table>";
		$('#especialidades').html(str);
	});


}

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
				str += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaEspe.idespecialidad + '" id="' + cadaEspe.nombre + '" name="mitad1">' + cadaEspe.abreviatura + '/' + cadaEspe.nombre + '</label>';
				str += '</td>';
			}else if(indfila == 2)
			{
				str +='<td onclick="cambiaColorCeldaEspMitad1(this);">';
				str += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaEspe.idespecialidad + '" id="' + cadaEspe.nombre + '" name="mitad1">' + cadaEspe.abreviatura + '/' + cadaEspe.nombre + '</label>';
				str += '</td>';
			}else
			{
				str +='<td onclick="cambiaColorCeldaEspMitad1(this);">';
				str += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaEspe.idespecialidad + '" id="' + cadaEspe.nombre + '" name="mitad1">' + cadaEspe.abreviatura + '/' + cadaEspe.nombre + '</label>';
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

function getTodosProductos()
{
	$.getJSON(server + 'GetTodosProductos', function(data){
		productos = data;
		getPizzas();
	});
}

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
	strPizza +='</tr>';		
	strPizza += '</table>';
	$('#FormTamanoPizza').html(strPizza);
	$("input[name=tamanoPizza]:radio").click(function() { 
				var tamanoPizza = $("input:radio[name=tamanoPizza]:checked").val();
				
                if($(this).val() == 'otros') {
                		
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
                }
                else
                {
                	//if($(this).val() == 'extragrande' || $(this).val() == 'grande'  || $(this).val() == 'mediana' || $(this).val() == 'pizzeta')
                	$('#otrosproductos').html('');
                	$.getJSON(server + 'GetSaboresLiquidoProducto?idProducto=' + tamanoPizza, function(data1){
                		
                		var strGas='';
                		var strGas = '<h2>Gaseosa</h2>';
                		strGas += '<table class="table table-bordered">';
                		strGas += '<tbody>';
						for(var i = 0; i < data1.length;i++){
							var cadaLiq  = data1[i];
							strGas +='<tr> ';
							strGas +='<td>';
							strGas += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaLiq.idSaborTipoLiquido + '" id="' + cadaLiq.descripcionLiquido + '" name="liquido">' + cadaLiq.descripcionLiquido + '-' + cadaLiq.descripcionSabor +'</label>';
							strGas += '</td> </tr>';
						}
						strGas += '</tbody> </table>';
                		$('#frmgaseosas').html(strGas);
					});
					

                } 
        });
}

function getOtrosProductos()
{
	var str = '';
	str += '<table class="table table-bordered">';
    str += '<tbody>';
	for(var i = 0; i < productos.length;i++){
			var cadaProdu  = productos[i];
			if (cadaProdu.tipo == "OTROS" )
			{
				str +='<tr> ';
				str +='<td>';
				str += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaProdu.idproducto +'" id="' + cadaProdu.idproducto + '" name="otros">' + cadaProdu.nombre +'</label>';
				str += '</td> </tr>';
			}
		}
		str += '</tbody> </table>';
		$('#otrosproductos').html(str);
		$("input[name=otros]:radio").click(function() { 
			var idProductoOtros = $("input:radio[name=otros]:checked").val();
			$.getJSON(server + 'GetSaboresLiquidoProducto?idProducto=' + idProductoOtros, function(data1){
                		console.log(data1);
                		var strGas='';
                		var strGas = '<h2>Gaseosa</h2>';
                		strGas += '<table class="table table-bordered">';
                		strGas += '<tbody>';
						for(var i = 0; i < data1.length;i++){
							var cadaLiq  = data1[i];
							strGas +='<td"> ';
							strGas +='<tr> ';
							strGas += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaLiq.idSaborTipoLiquido + '" id="' + cadaLiq.descripcionLiquido + '" name="liquido">' + cadaLiq.descripcionLiquido + '-' + cadaLiq.descripcionSabor +'</label>';
							strGas += '</td> </tr>';
						}
						strGas += '</tbody> </table>';
                		$('#frmgaseosas').html(strGas);
			});
		});
}

function ProductoCon()
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
			marcadorMitad = 1;
			str += '<thead><tr><td><h3>MITAD 1 - '+ especialidad1 +'</h3></td><td><h3>MITAD 2 - ' + especialidad2 +  '</h3></td></tr></thead>';
		}
		else
		{
			var especialidad1 = $("input:radio[name=mitad1]:checked").attr('id');
			str += '<thead><tr><td><h3>PIZZA ENTERA - ' + especialidad1 +'</h3></td></tr></thead>';
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
						str += '<div class="col-md-6"> ';
						str += '<label><input type="checkbox"' + '  value="'+ cadaModificador.idproducto + '-' + cadaModificador.nombre +'" name="' +'mitmodcon1' + cadaModificador.idproducto + '" onclick="revisarMarcacionModSin(this)">' + cadaModificador.nombre +'</label>';
						str += '</div>'
						str += '</td>';
						str +='<td onclick="cambiaColorCeldaModSin(this);"> ';
						str += '<div class="col-md-6"> ';
						str += '<label><input type="checkbox"' + '  value="'+ cadaModificador.idproducto + '-' + cadaModificador.nombre +'" name="' +'mitmodcon2' + cadaModificador.idproducto + '" onclick="revisarMarcacionModSin(this)">' + cadaModificador.nombre +'</label>';
						str += '</div>'
						str += '</td> </tr>';
					}
					else
					{
						if (indfila == 1)
						{
							str +='<tr> ';
							str +='<td onclick="cambiaColorCeldaModSin(this);"> ';
							str += '<div class="col-md-6"> ';
							str += '<label><input type="checkbox"' + '  value="'+ cadaModificador.idproducto + '-' + cadaModificador.nombre +'" name="' +'mitmodcon1' + cadaModificador.idproducto + '" onclick="revisarMarcacionModSin(this)">' + cadaModificador.nombre +'</label>';
							str += '</div>';
							str += '</td>';
						}
						else
						{
							str +='<td onclick="cambiaColorCeldaModSin(this);"> ';
							str += '<div class="col-md-6"> ';
							str += '<label><input type="checkbox"' + '  value="'+ cadaModificador.idproducto + '-' + cadaModificador.nombre +'" name="' +'mitmodcon1' +  cadaModificador.idproducto + '" onclick="revisarMarcacionModSin(this)">' + cadaModificador.nombre +'</label>';
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
			str += '<button type="button" onClick="cerrarModalModCon()" class="btn btn-default" data-dismiss="modal">Cerrar</button>';
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
			str += '<thead><tr><td><h3>PIZZA ENTERA - ' + especialidad1 +'</h3></td></tr></thead>';
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
						str += '<div class="col-md-6"> ';
						str += '<label><input type="checkbox"' + '  value="'+ cadaModificador.idproducto + '-' + cadaModificador.nombre +'" name="' +'mitmodsin1' + cadaModificador.idproducto + '" onclick="revisarMarcacionModSin(this)">' + cadaModificador.nombre +'</label>';
						str += '</div>'
						str += '</td>';
						str +='<td onclick="cambiaColorCeldaModSin(this);"> ';
						str += '<div class="col-md-6"> ';
						str += '<label><input type="checkbox"' + '  value="'+ cadaModificador.idproducto + '-' + cadaModificador.nombre +'" name="' +'mitmodsin2' + cadaModificador.idproducto + '" onclick="revisarMarcacionModSin(this)">' + cadaModificador.nombre +'</label>';
						str += '</div>'
						str += '</td> </tr>';
					}
					else
					{
						if (indfila == 1)
						{
							str +='<tr> ';
							str +='<td onclick="cambiaColorCeldaModSin(this);"> ';
							str += '<div class="col-md-6"> ';
							str += '<label><input type="checkbox"' + '  value="'+ cadaModificador.idproducto + '-' + cadaModificador.nombre +'" name="' +'mitmodsin1' + cadaModificador.idproducto + '" onclick="revisarMarcacionModSin(this)">' + cadaModificador.nombre +'</label>';
							str += '</div>';
							str += '</td>';
						}
						else
						{
							str +='<td onclick="cambiaColorCeldaModSin(this);"> ';
							str += '<div class="col-md-6"> ';
							str += '<label><input type="checkbox"' + '  value="'+ cadaModificador.idproducto + '-' + cadaModificador.nombre +'" name="' +'mitmodsin1' +  cadaModificador.idproducto + '" onclick="revisarMarcacionModSin(this)">' + cadaModificador.nombre +'</label>';
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
			str += '<button type="button" onClick="cerrarModalModSin()" class="btn btn-default" data-dismiss="modal">Cerrar</button>';
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

function getAdicionProductos()
{
	
	if (marcadorAdiciones == 0)
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
			str += '<thead><tr><td><h3>PIZZA ENTERA - ' + especialidad1 +'</h3></td></tr></thead>';
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
			str += '<button type="button" onClick="cerrarModalAdiciones()" class="btn btn-default" data-dismiss="modal">Cerrar</button>';
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

function ocultarModalAdiciones()
{
	$('#addAdicion').modal('hide');
}

/*function getOtrosProductos()
{
	$.getJSON(server + 'GetOtrosProductos', function(data){
		var str = '';
		for(var i = 0; i < data.length;i++){
			var cadaProdu  = data[i];
			str +='<div class="row"> ';
			str +='<div class="col-md-2"> ';
			str += '<div class="input-group">';
			str += '<label><input type="radio" aria-label="..."' + '  value="'+ cadaProdu.idproducto + '-' + cadaProdu.nombre +' " id="' + cadaProdu.idproducto + '" name="otros">' + cadaProdu.nombre +'</label>';
			str += '</div> </div> </div>';

		}
		$('#otrosproductos').html(str);
	});
}*/

//Procedimiento Ejecutado con todas las validaciones necesarias previo a realizar la insercion del  pedido en la base de datos
function ConfirmarPedido()
{
	
	var valordevolver =  $("#valordevolver").val();
	if(valordevolver >= 0)
	{
		$.confirm({
				'title'		: 'Confirmacion de Creación de Pedido',
				'content'	: 'Desea confirmar el Pedido Número ' + idPedido + '<br />El Pedido pasará a estado  Finalizado?',
				'buttons'	: {
					'Si'	: {
						'class'	: 'blue',
						'action': function(){
						
							var resultado
							var idformapago =  $("#selectformapago").val();
							var valorformapago =  $("#valorpago").val();
							$.ajax({ 
		    				url: server + 'FinalizarPedido?idpedido=' + idPedido + "&idformapago=" + idformapago + "&valortotal=" + totalpedido + "&valorformapago=" + valorformapago + "&idcliente=" + idCliente + "&insertado=" + insertado , 
		    				dataType: 'json', 
		    				async: false, 
		    				success: function(data){ 
									resultado = data[0];
									var urlTienda = resultado.url;
									console.log(urlTienda);
									var strClr = "";
									idPedido = 0;
									memcode = 0;
									idCliente = 0;
								    $('#otrosproductos').html(strClr);
								    $('#especialidades').html(strClr);
								    $('#pintarAdiciones').html(strClr);
								    $('input:text[name=cantidad]').val('');
								    //Se reinician valores por defecto
								    $('input:radio[name=adicion]:nth(1)').attr('checked',true);
								    $('input:radio[name=tamanoPizza]').attr('checked',false);
								    $('input:radio[name=formapizza]').attr('checked',false);
								    $('input:radio[name=liquido]').attr('checked',false);
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
							          if ( $.fn.dataTable.isDataTable( '#grid-clientes' ) ) {
							    		table = $('#grid-clientes').DataTable();
							    		table.clear().draw();
							    	}

							    	if ( $.fn.dataTable.isDataTable( '#grid-clientes' ) ) {
							    		table = $('#grid-pedido').DataTable();
							    		table.clear().draw();
							    	}
							    	$('input:radio[name=adicion]')[1].checked = true;
							    	totalpedido = 0;
							    	$("#totalpedido").val('0');
									$("#valorpago").val('0');
									$("#valordevolver").val('0');
									
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

function ValidacionesDatos()
{
	//validamos campo de telefono
	var tele =  telefono.value;
	if (tele == '' || tele == null)
	{
		alert ('Debe ingresar un telefono para el cliente');
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
	if (dir == '' || dir == null)
	{
		alert ('Debe ingresar la dirección del cliente');
		return;
	}
	var tien = $("#selectTiendas option:selected").val();
	if (tien == '' || tien == null)
	{
		alert ('Debe ingresar la tienda del pedido');
		return;
	}
	var tamapizza = $("input:radio[name=tamanoPizza]:checked").val();
	if (tamapizza == '' || tamapizza == null)
	{
		alert ('Debe seleccionar entre Pizza u otros productos');
		return;
	}
	if (tamapizza == 'otros'){
		var otros = $("input:radio[name=otros]:checked").val();
		if (otros == '' || otros == null)
		{
			alert ('Usted seleccionó otros productos, por favor seleccione un producto de la categoría otros productos');
			return;	
		}
	}else{

		if ($("input:radio[name=formapizza]:checked").val() == 'mitad'){
			var especial1 =  $("input:radio[name=mitad1]:checked").val();
			if (especial1 == '' || especial1 == null)
			{
				alert ('Usted seleccionó Pizza mitad/mitad, por favor seleccione el sabor de la primera mitad');
				return;	
			}
			var especial2 =  $("input:radio[name=mitad2]:checked").val();
			if (especial2 == '' || especial2 == null)
			{
				alert ('Usted seleccionó Pizza mitad/mitad, por favor seleccione el sabor de la segunda mitad');
				return;	
			}
		}
		if ($("input:radio[name=formapizza]:checked").val() == 'entera'){
		
			var especial1 =  $("input:radio[name=mitad1]:checked").val();
			if (especial1 == '' || especial1 == null)
			{
				alert ('Usted seleccionó Pizza de un solo sabor, por favor seleccione el sabor de la Pizza');
				return;	
			}
		}

	}
	
	var cant  = cantidad.value;
	if (cant == '' || cant == null)
		{
			alert ('Por favor seleccione la cantidad del producto que desea ordenar');
			return;	
		}
		return(1);
}


function agregarProducto()
{
	var valida = ValidacionesDatos();
	var modespecialidad1= "";
	var modespecialidad2= "";
	var arregloAdiciones = [];
	var arregloModificadores = [];
	var tempTienda;
	var tempMunicipio;
	if (valida != 1)
	{
		return;
	}
	tempTienda =  $("#selectTiendas option:selected").val();
	tempMunicipio = $("#selectMunicipio option:selected").val();
	
	if (idPedido == 0)
	{
		var respuesta;
		//Realizar el llamado síncrono
		//$.ajax({ 
    	//			url: server + 'GetInsertarClientePedido?telefono=' + telefono.value + "&nombres=" + nombres.value +  "&direccion=" + direccion.value + "&tienda=" +  tempTienda +  "&zona=" + zona.value + "&observacion=", 
    	//			dataType: 'json', 
    	//			async: false, 
    	//			success: function(data){ 
		//				respuesta = data[0];
		//				idCliente = respuesta.idcliente;
		//			} 
		//});

		// Se realiza inserción del encabezado del pedido
		
		//Se hizo neceasari hacerlo sincrono pues la insercion del detalle pedido, depende de la inserción del encabezado pedido
		
		/*$.getJSON(server + 'InsertarClienteEncabezadoPedido?telefono=' + telefono.value + "&nombres=" + nombres.value +  "&direccion=" + direccion.value + "&tienda=" +  tempTienda +  "&zona=" + zona.value + "&observacion="  , function(data){
		respuesta = data[0];
		idPedido = respuesta.idpedido;
		idCliente = respuesta.idcliente;
		console.log("id pedido " + idPedido + " id cliente " + idCliente);
		});*/
		var idClienteTemporal;
		var direccionEncode = encodeURIComponent(direccion.value);
		$.ajax({ 
    				url: server + 'InsertarClienteEncabezadoPedido?telefono=' + telefono.value + "&nombres=" + nombres.value + "&apellidos=" + apellidos.value + "&nombreCompania=" + nombreCompania.value +  "&direccion="  + direccionEncode  + "&tienda=" + tempTienda +  "&zona=" + zona.value + "&observacion=" + observacionDir.value + "&municipio=" + tempMunicipio + "&longitud=" + longitud + "&latitud=" + latitud + "&memcode=" + memcode + "&idcliente=" + idCliente, 
    				dataType: 'json', 
    				async: false, 
    				success: function(data){ 
						respuesta = data[0];
						idClienteTemporal = respuesta.idcliente;
						console.log(respuesta);
						if (idClienteTemporal != 0)
						{
							idPedido = respuesta.idpedido;
							idEstadoPedido = respuesta.idestadopedido;
							$("#estadopedido").val(respuesta.descripcionestadopedido);
						}
						//else
						//{
						//	alert("Se tiene inconvenientes con el cliente existe en sistema Call Center pero no existe en sistema de Tienda");
						//	return;
						//}
						
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
		
	}
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
						adiciones = adiciones + cadaProdu.idproducto + '-' + cadaProdu.nombre + '/';
						
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
						        "accion":'<input type="button" class="btn btn-default btn-xs" onclick="eliminarDetallePedido(' + idDetallePedido + ')" value="Eliminar"></button>' 
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
						adiciones = adiciones + cadaProdu.idproducto + '-' + cadaProdu.nombre + '/';
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
						        "accion":'<input type="button" class="btn btn-default btn-xs" onclick="eliminarDetallePedido(' + idDetallePedido + ')" value="Eliminar"></button>' 
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
						adiciones = adiciones + cadaProdu.idproducto + '-' + cadaProdu.nombre + '/';
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
						        "accion":'<input type="button" class="btn btn-default btn-xs" onclick="eliminarDetallePedido(' + idDetallePedido + ')" value="Eliminar"></button>' 
						    } ).draw();
							if(cantidad == 1)
							{
								var adi = new adicionDetallePedido(0,idDetallePedido, $("input:radio[name=mitad1]:checked").val(),0.5,0,0);
								arregloAdiciones.push(adi);
								adi = new adicionDetallePedido(0,idDetallePedido,0,0, $("input:radio[name=mitad1]:checked").val(),0.5);
								arregloAdiciones.push(adi);
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
					var mod = new modificadorDetallePedido(0,cadaProdu.idproducto,0,1);
					arregloModificadores.push(mod);
				}

				if ($('input:checkbox[name='+'mitmodcon2'+cadaProdu.idproducto+']').is(':checked'))
				{
					modespecialidad2 += cadaProdu.descripcion + " / ";
					var mod = new modificadorDetallePedido(0,0,cadaProdu.idproducto,1);
					arregloModificadores.push(mod);
				}
			}
			else
			{
				if ($('input:checkbox[name='+'mitmodcon1'+cadaProdu.idproducto+']').is(':checked'))
				{
					modespecialidad1 += cadaProdu.descripcion + " / ";
					var mod = new modificadorDetallePedido(0,cadaProdu.idproducto,0,1);
					arregloModificadores.push(mod);
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
					var mod = new modificadorDetallePedido(0,cadaProdu.idproducto,0,1);
					arregloModificadores.push(mod);
				}

				if ($('input:checkbox[name='+'mitmodsin2'+cadaProdu.idproducto+']').is(':checked'))
				{
					modespecialidad2 += cadaProdu.descripcion + " / ";
					var mod = new modificadorDetallePedido(0,0,cadaProdu.idproducto,1);
					arregloModificadores.push(mod);
				}
			}
			else
			{
				if ($('input:checkbox[name='+'mitmodsin1'+cadaProdu.idproducto+']').is(':checked'))
				{
					modespecialidad1 += cadaProdu.descripcion + " / ";
					var mod = new modificadorDetallePedido(0,cadaProdu.idproducto,0,1);
					arregloModificadores.push(mod);
				}
			}
		}

	}



	excepcionPrecio = $("#selectExcepcion option:selected").val();


	tamanoPizza = $("input:radio[name=tamanoPizza]:checked").val();
	cantidad = $('input:text[name=cantidad]').val();	
	if (tamanoPizza == 'otros'){
		pizza = '--';
		especialidad1 = '--';
		especial1 = '--';
		especialidad2 = '--';
		especial2 = '--';
		var tempVal = $("input:radio[name=liquido]:checked").val();
		if ( tempVal > 0 ) {
  			liquido = $("input:radio[name=liquido]:checked").val() +"-" + $("input:radio[name=liquido]:checked").attr('id');
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
				totalpedido  = totalpedido + valortotal; 
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
						totalpedido  = totalpedido + valortotal; 
					}
					else{
						alert("La Excepcion de precio no esta asociada al producto seleccionado por favor corrija su elección");
						return;
					}
				}
			}
		}
	}else{
			
			pizza = tamanoPizza.toLowerCase();
			
			for(var i = 0; i < productos.length;i++){
				var cadaProdu  = productos[i];
				if (cadaProdu.idproducto == pizza )
				{
					codprod = cadaProdu.idproducto;
					pizza = codprod + "-" + cadaProdu.nombre;
					valorunitario = cadaProdu.preciogeneral;
					valortotal = valorunitario * cantidad;
					totalpedido  = totalpedido + valortotal; 
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
							totalpedido  = totalpedido + valortotal; 
						}
						else{
							alert("La Excepcion de precio no esta asociada al producto seleccionado por favor corrija su elección");
							return;
						}
					}
				}
			}
			//
			otroProd = '--';
			if ($("input:radio[name=formapizza]:checked").val() == 'mitad'){
				especialidad1 =  $("input:radio[name=mitad1]:checked").val();
				especial1 = $("input:radio[name=mitad1]:checked").val() + "-" + $("input:radio[name=mitad1]:checked").attr('id');
				especialidad2 =  $("input:radio[name=mitad2]:checked").val();
				especial2 = $("input:radio[name=mitad2]:checked").val() + "-" +$("input:radio[name=mitad2]:checked").attr('id');
				if(especialidad1 =='' || especialidad2==''){
					alert("Debe seleccionar las dos especialidades");
					return;
				}
			}else if ($("input:radio[name=formapizza]:checked").val() == 'entera'){
					especialidad1 =  $("input:radio[name=mitad1]:checked").val();
					especial1 = $("input:radio[name=mitad1]:checked").val() + "-" + $("input:radio[name=mitad1]:checked").attr('id');
					especialidad2 = "--";
					especial2 = '--';
					if (especialidad1 ==''){
						alert('Debe seleccionar especialidad de la Pizza');
					}
			}
	}
	
		if (cantidad == 0  || cantidad ==''){
			alert ('Debe ingresar la cantidad del Producto que va a ordenar');
		}
	var idDetallePedido = 0;
	var tempVal = $("input:radio[name=liquido]:checked").val();
	if ( tempVal > 0 ) {
  			liquido = $("input:radio[name=liquido]:checked").val() +"-" + $("input:radio[name=liquido]:checked").attr('id');
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
	        "accion":'<input type="button" class="btn btn-default btn-xs" onclick="eliminarDetallePedido(' + idDetallePedido + ')" value="Eliminar"></button>'
	    } ).draw();
	    //Ya se creó el iddetallepedido del producto grueso
	    
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
    				url: server + 'InsertarModificadorDetallePedido?iddetallepedidopadre=' + idDetallePedido +"&idproductoespecialidad1=" + arregloModificadores[i].idProductoEspecialidad1 +  "&idproductoespecialidad2=" + arregloModificadores[i].idProductoEspecialidad2 + "&cantidad=" + arregloModificadores[i].cantidad, 
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
	    var strClr = "";
	    $('#otrosproductos').html(strClr);
	    $('#especialidades').html(strClr);
	    $('#pintarAdiciones').html(strClr);
	    $('input:text[name=cantidad]').val('');
	    //Se reinician valores por defecto
	    $('input:radio[name=adicion]:nth(1)').attr('checked',true);
	    $('input:radio[name=tamanoPizza]').attr('checked',false);
	    $('input:radio[name=formapizza]').attr('checked',false);
	    $('input:radio[name=liquido]').attr('checked',false);
	    $("#selectExcepcion").val('vacio');
	    $('#observacionDir').val('');
	    $('#observacion').val('');
	    $('input:radio[name=adicion]')[1].checked = true;
	    $('#totalpedido').val(totalpedido);
	    $("#valordevolver").val($("#valorpago").val() - $("#totalpedido").val() );

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


/*function initMap(){
	var inicio = new google.maps.LatLng(6.29139, -75.53611);
    var opciones = {
        zoom: 4,
        center: inicio,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map($("#mapa").get(0), opciones);
}*/

function initMap() {
		

		/*var map = new google.maps.Map(document.getElementById('mapas'), {
          zoom: 11,
          center: {lat: 41.876, lng: -87.624}
        });

        var ctaLayer = new google.maps.KmlLayer({
          url: 'http://googlemaps.github.io/js-v2-samples/ggeoxml/cta.kml',
          map: map
        });*/
		var map = new google.maps.Map(document.getElementById("mapas"), {
          zoom: 13,
          scrollwheel: false,
          center: {lat: 6.29139, lng: -75.53611}
        });

        /*var ctaLayer = new google.maps.KmlLayer({
          url: 'https://raw.githubusercontent.com/Andres-FA/KMLZonasDeReparto/master/ZonasDeRepartoTotales.kml',
          map: map
        });
        console.log(ctaLayer);*/
        

      }



// Evento para cuando se da  CLICK EN EL BOTÓN BUSCAR
function buscarMapaDigitado() {

    // Obtenemos la dirección y la asignamos a una variable
    var direccion = $('#direccion').val();
    var municipio = $("#selectMunicipio").val();
    municipio = municipio.toLowerCase();
    direccion = direccion + " " + municipio;
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
        longitud = results[0].geometry.location.lng();
        latitud = results[0].geometry.location.lat();
        map = new google.maps.Map($("#mapas").get(0), mapOptions);
        // fitBounds acercará el mapa con el zoom adecuado de acuerdo a lo buscado
        map.fitBounds(results[0].geometry.viewport);
        // Dibujamos un marcador con la ubicación del primer resultado obtenido
        var ctaLayer = new google.maps.KmlLayer({
          url: 'https://raw.githubusercontent.com/Andres-FA/KMLZonasDeReparto/master/ZonasDeRepartoTotales.kml',
          map: map,
          scrollwheel: false,
          zoom: 17
        });
        
        var markerOptions = { position: results[0].geometry.location }
        var marker = new google.maps.Marker(markerOptions);
        marker.setMap(map);
        
    } else {
        // En caso de no haber resultados o que haya ocurrido un error
        // lanzamos un mensaje con el error
        alert("La Geolocalización no tuvo éxito debido a: " + status);
    }
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

function modificadorDetallePedido (idDetallePedidoPadre,idProductoEspecialidad1, idProductoEspecialidad2, cantidad)
{
	
	this.idDetallePedidoPadre = idDetallePedidoPadre;
	this.idProductoEspecialidad1 = idProductoEspecialidad1;
	this.idProductoEspecialidad2 = idProductoEspecialidad2;
	this.cantidad = cantidad;
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
        $("#selectMunicipio").val("");
        memcode = 0;
}
