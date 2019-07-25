	

// Se definen las variables globales.
var server;
var tiendas;
var table;
var productos;
var memcode = 0;
var idCliente = 0;
var dtconsultasPQRS;
var idSolicitudPQRS;

// A continuación  la ejecucion luego de cargada la pagina
$(document).ready(function() {

dtconsultasPQRS = $('#grid-consultaPQRS').DataTable( {
    		"aoColumns": [
    		{ "mData": "idconsultaPQRS" },
            { "mData": "fechasolicitud" },
            { "mData": "tiposolicitud" },
            { "mData": "cliente" },
            { "mData": "direccion" },
            { "mData": "telefono" },
            { "mData": "comentario" , "visible": false },
            { "mData": "municipio" },
            { "mData": "tienda" },
            { "mData": "nombreorigen" },
            { "mData": "accion" }
            ]
    	} );			

//validamos el contenido del campo fecha del pedido y el evento que lo controlará
	//Se invoca servicio para traerse la información de los productos disponibles en el sistema
	// En resumen se invocan todos servicios que se encargan de llenar la data del formulario.
	getListaTiendas();
	setInterval('validarVigenciaLogueo()',600000);

//Colocamos acción al DataTable en caso de dar clic sobre el DATATABLE
$('#grid-consultaPQRS tbody').on('click', 'tr', function () {
        datos = table.row( this ).data();
        $('#idSolicitudPQRS').val(datos.idconsultaPQRS);
        $('#fecha').val(datos.fechasolicitud);
        $('#tipoSolicitud').val(datos.tiposolicitud);
        $('#telefono').val(datos.telefono);
        $('#nombres').val(datos.cliente);
        $('#direccion').val(datos.direccion);
        $('#municipio').val(datos.municipio);
        $("#tienda").val(datos.tienda);
        $("#origen").val(datos.nombreorigen);
        $('#comentariosVista').val(datos.comentario);
     } );



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

// Método que se encarga luego de introducido un teléfono en el campo de teléfono del cliente llamar al servicio


// Método que invoca el servicio para listar las tiendas donde se pondrán tomar domicilios.
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
		// Realizamos cambio para que la tienda no esté seleccionada por defecto
		$("#selectTiendas").val('');
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

function validarFechas(date1, date2){
     
	  var fechaSolIni = new Date();
      var fechaSolFin = new Date();
      var fecha1 = date1.split("/");
      var fecha2 = date2.split("/");
      fechaSolIni.setFullYear(fecha1[2],fecha1[1]-1,fecha1[0]);
      fechaSolFin.setFullYear(fecha2[2],fecha2[1]-1,fecha2[0]);
      
      if (fechaSolIni <= fechaSolFin)
      {
        return true;
   	  }
      else
      {
        return false;
      }
}




function consultarPQRS() 
{

	var fechaini = $("#fechainicial").val();
	var fechafin = $("#fechafinal").val();
	var tienda = $("#selectTiendas option:selected").attr('id');
	var valida = ValidacionesDatos();
	if (valida != 1)
	{
		return;
	}
	// Si pasa a este punto es porque paso las validaciones
	if ( $.fn.dataTable.isDataTable( '#grid-consultaPQRS' ) ) {
    		table = $('#grid-consultaPQRS').DataTable();
    }
	$.getJSON(server + 'ConsultaIntegradaSolicitudesPQRS?fechainicial=' + fechaini +"&fechafinal=" + fechafin + "&tienda=" + tienda , function(data1){
	                		
	                		table.clear().draw();
							for(var i = 0; i < data1.length;i++){
							table.row.add({
			                    "idconsultaPQRS": data1[i].idconsultaPQRS, 
			                    "fechasolicitud": data1[i].fechasolicitud,
			                    "tiposolicitud": data1[i].tiposolicitud,
			                    "cliente": data1[i].cliente, 
			                    "direccion": data1[i].direccion,
			                    "telefono": data1[i].telefono,
			                    "comentario": data1[i].comentario, 
			                    "municipio": data1[i].municipio,
			                    "tienda": data1[i].tienda,
			                    "nombreorigen": data1[i].nombreorigen, 
			                    "accion": '<button type="button" class="btn btn-default btn-xs" onclick="mostrarModalObservacion(' +data1[i].idconsultaPQRS + ')"><i class="fas fa-cart-plus fa-2x"></i></button>'
			                }).draw();
							}
							
					});
	limpiarConsultaPQRS();

}


function ValidacionesDatos()
{
	var fechaini = $("#fechainicial").val();
	var fechafin = $("#fechafinal").val();
	var tienda = $("#selectTiendas option:selected").attr('id');
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
	if(validarFechas(fechaini, fechafin))
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

	return(1);
}


function mostrarModalObservacion(idSolPQRS)
{
	idSolicitudPQRS = idSolPQRS;
	$('#adicionarObservacion').modal('show');
}


function guardarObservacionPQRS()
{
	var comentariosAd = encodeURIComponent($('#comentarios').val());
	if(comentariosAd == '')
	{
		$.alert('El comentario a adicionar debe tener algún texto.' );
		return;
	}
	var controlador = false;
	$.ajax({ 
    				url: server + 'AdicionarComentarioPQRS?pqrs=' + idSolicitudPQRS + "&comentario=" + comentariosAd, 
    				dataType: 'json', 
    				async: false, 
    				success: function(data){ 
						var resultado = data;
						if(resultado[0].resultado == 'OK')
						{
							$.alert('Se ha insertado exitosamente el comentario.' );
							controlador = true;
						}else
						{
							$.alert('Se tuvo un ERROR al insertar el comentario' );
						}
					} 
		});
	if(controlador == true)
	{
		$('#comentarios').val('');
		idSolicitudPQRS = 0;
		$('#adicionarObservacion').modal('hide');
		consultarPQRS();
	}
	
}


function limpiarConsultaPQRS()
{
	    $('#idSolicitudPQRS').val('');
        $('#fecha').val('');
        $('#tipoSolicitud').val('');
        $('#telefono').val('');
        $('#nombres').val('');
        $('#direccion').val('');
        $('#municipio').val('');
        $("#tienda").val('');
        $("#origen").val('');
        $('#comentariosVista').val('');
}