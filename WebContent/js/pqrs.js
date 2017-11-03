	

// Se definen las variables globales.
var server;
var tiendas;
var table;
var productos;
var memcode = 0;
var idCliente = 0;

// A continuación  la ejecucion luego de cargada la pagina
$(document).ready(function() {

			

//validamos el contenido del campo fecha del pedido y el evento que lo controlará
$("#fecha").change(function(){
    var fechaped = $("#fecha").val();
    if(existeFecha(fechaped))
	{
	}
	else
	{
		alert ('La fecha de la solicitud no es correcta');
		$("#fecha").datepicker('setDate', new Date());
		return;
	}

	if(validarFechaMenorActual(fechaped))
	{
	}
	else
	{
		alert ('La fecha de la solicitud es menor a la fecha actual, favor corregir');
		$("#fecha").datepicker('setDate', new Date());
		return;
	}
});


	//Se invoca servicio para traerse la información de los productos disponibles en el sistema
	// En resumen se invocan todos servicios que se encargan de llenar la data del formulario.
	getListaTiendas();
	getListaMunicipios();
	// Llevamos a cero los campos cálculos de los totales
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
            { "mData": "memcode"  , "visible": false }
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
        // Para evitar que modifiquen la tienda
        $('#selectTiendas').attr('disabled', true);
        $("#selectMunicipio").val(datos.municipio);
        memcode = datos.memcode;
        idCliente = datos.idCliente;
        var municipio = datos.municipio;
        var dirbuscar = datos.direccion + " " + municipio.toLowerCase();
        
     } );
 	

  	

	} );



// Método que se encarga luego de introducido un teléfono en el campo de teléfono del cliente llamar al servicio
function validarTelefono(){

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


//Método que invoca el servicio para obtener lista de municipios parametrizados en el sistema
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
        //Volvemos  a habiliar el select de tiendas si es qúe está deshabilitado
        $('#selectTiendas').attr('disabled', false);
        $("#selectMunicipio").val("");
        memcode = 0;
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

function ConfirmarPQRS()
{
	var valida = ValidacionesDatos();
	if (valida != 1)
	{
		return;
	}
	var tempTienda =  $("#selectTiendas option:selected").val();
	var tempMunicipio = $("#selectMunicipio option:selected").val();
	var fechaSolicitud = $("#fecha").val();
	var nombresEncode = encodeURIComponent(nombres.value);;
	var apellidosEncode = encodeURIComponent(apellidos.value);;
	var nombreCompaniaEncode = encodeURIComponent(nombreCompania.value);;
	var direccionEncode = encodeURIComponent(direccion.value);
	var zonaEncode = encodeURIComponent(zona.value); 
	var observacionDirEncode = encodeURIComponent(observacionDir.value);
	var comentario = encodeURIComponent(comentario.value);
	var tipoSolicitud = $("#selectSolicitud option:selected").val();
}

function ValidacionesDatos()
{
	//validamos campo de telefono
	var tele =  telefono.value;
	if (tele == '' || tele == null)
	{
		alert ('Debe ingresar un telefono de contacto para el cliente');
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
	if (dir == '' || dir == null)
	{
		alert ('Debe ingresar la dirección del cliente');
		return;
	}
	var tien = $("#selectTiendas option:selected").val();
	if (tien == '' || tien == null || tien == undefined)
	{
		alert ('Debe ingresar la tienda del cliente PQRS');
		return;
	}
	var muni = $("#selectMunicipio option:selected").val();
	if (muni == '' || muni == null|| muni == undefined)
	{
		alert ('Debe ingresar el Municipio del Cliente del PQRS');
		return;
	}
	
	var coment = comentarios.value;
	if (coment == '' || coment == null || coment == undefined)
	{
		alert ('Debe ingresar el comentario del PQRS');
		return;
	}

	return(1);
}
