var server;
var table;


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
		
		
	    table = $('#grid-excepcionPrecio').DataTable( {
    		"aoColumns": [
            { "mData": "idexcepcion" },
            { "mData": "idproducto" , "visible": false },
            { "mData": "nombreproducto" },
            { "mData": "precio" },
            { "mData": "descripcion" },
            { "mData": "incluyeliquido" },
            { "mData": "idtipoliquido"  , "visible": false},
            { "mData": "nombreliquido" },
            {
                "mData": "accion",
                className: "center",
                defaultContent: '<input type="button" class="btn btn-default btn-xs" onclick="eliminarExcepcion()" value="Eliminar"></button> <input type="button" class="btn btn-default btn-xs" onclick="EditarExcepcion()" value="Editar"></button>'
            }
        ]
    	} );
  	  	
	    
		
        pintarExcepciones();

        // Llenar select de productos y de liquidos
        llenarSelectProductos();
        llenarSelectTipoLiquidos();
		
  	  	
  	  	$('#userForm')
        .bootstrapValidator({
            framework: 'bootstrap',
            icon: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                precioedit: {
                    validators: {
                        notEmpty: {
                            message: 'El precio para la excepción es requerido'
                        },
                        regexp: {
                            regexp: /^[0-9]+$/,
                            message: 'El precio de la Excepción debe ser numérico'
                        }
                    }
                },
                descripcionedit: {
                    validators: {
                        notEmpty: {
                            message: 'La descripción de la excepción es requerida'
                        },
                        regexp: {
                            regexp: /^[a-zA-Z\s]+$/,
                            message: 'La descripción de la excepción debe contener solo letras'
                        }
                    }
                }
            }
        }) 
         
        
    	//
		
});

function guardarExcepcionPrecio()
{
	var idproducto = $('#selectProductos').val();
	var precio = $('#precio').val();
	var descripcion =  $('#descripcion').val();
	var incluye_liquido = $('#incluyeLiquido').val();
	var tipoliquido = $('#selectTipoLiquido').val(); 
	var idexcepcion;
	$.getJSON(server + 'CRUDExcepcionPrecio?idoperacion=1&idproducto=' + idproducto + "&precio=" + precio + "&descripcion=" + descripcion + "&incluye_liquido=" + incluye_liquido + "&idtipoliquido=" + tipoliquido, function(data){
		var respuesta = data[0];
		idexcepcion = respuesta.idexcepcion;
				
	});
	pintarExcepciones();
}

function eliminarExcepcion(idexcepcion)
{
	/*var table = $('#grid-especialidades').DataTable();
	var datos = table.row( this ).data();
	console.log(datos);
	idespecialidad = datos.idespecialidad;
	nombre = datos.nombre;*/
	$.confirm({
			'title'		: 'Confirmacion Eliminación de la Excepción de Precio',
			'content'	: 'Desea confirmar la eliminación de la Excepción ' + idexcepcion+ '.',
			'buttons'	: {
				'Si'	: {
					'class'	: 'blue',
					'action': function(){
					
						var resultado
						$.ajax({ 
	    				url: server + 'CRUDExcepcionPrecio?idoperacion=3&idexcepcion=' + idexcepcion , 
	    				dataType: 'json', 
	    				async: false, 
	    				success: function(data){ 
								resultado = data[0];
								//

								if ( $.fn.dataTable.isDataTable( '#grid-excepcionPrecio' ) ) {
							    	table = $('#grid-excepcionPrecio').DataTable();
							    }
								
								pintarExcepciones();

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

function llenarSelectProductos()
{
	
	$.ajax({ 
	    				url: server + 'GetTodosProductos' , 
	    				dataType: 'json', 
	    				async: false, 
	    				success: function(data1)
	    					{ 
								var str = '';
								for(var i = 0; i < data1.length;i++){
									str +='<option value="'+ data1[i].idproducto +'" id ="'+ data1[i].idproducto +'">' + data1[i].nombre + '-' + data1[i].descripcion + '</option>';
								}
								$('#selectProductos').html(str);
								$('#selectProductosedit').html(str);
													
							} 
						});

	
}

function llenarSelectTipoLiquidos()
{
	
	$.ajax({ 
	    				url: server + 'GetTipoLiquido' , 
	    				dataType: 'json', 
	    				async: false, 
	    				success: function(data2)
	    					{ 
								var str = '';
								str += '<option value="0" id ="0"></option>';
								for(var i = 0; i < data2.length;i++){
									str +='<option value="'+ data2[i].idtipo_liquido +'" id ="'+ data2[i].idtipo_liquido +'">' + data2[i].nombre + '-' + data2[i].capacidad + '</option>';
								}
								$('#selectTipoLiquido').html(str);
								$('#selectTipoLiquidoedit').html(str);	
								console.log(str);		
							} 

						});

}

function pintarExcepciones()
{
	$.getJSON(server + 'CRUDExcepcionPrecio?idoperacion=6'  , function(data1){
			table.clear().draw();
			for(var i = 0; i < data1.length;i++){
				var cadaExcepcion = data1[i];
				table.row.add({
					"idexcepcion": data1[i].idexcepcion, 
					"idproducto": data1[i].idproducto,
					"nombreproducto": data1[i].nombreproducto, 
					"precio": data1[i].precio, 
					"descripcion": data1[i].descripcion, 
					"incluyeliquido": data1[i].incluye_liquido,
					"idtipoliquido": data1[i].idtipoliquido,  
					"nombreliquido": data1[i].nombreliquido, 
					"accion":'<input type="button" class="btn btn-default btn-xs" onclick="eliminarExcepcion(' +data1[i].idexcepcion + ')" value="Eliminar"></button> <input type="button" onclick="editarExcepcion('+data1[i].idexcepcion+')" class="btn btn-default btn-xs editButton" ' + 'data-id="' + data1[i].idexcepcion + '" value="Edición"></button>'
				}).draw();
				//table.row.add(data1[i]).draw();
			}
		});
}

function editarExcepcion(idexcepcion)
{
	//
				
	// Get the record's ID via attribute
	
		$.ajax({ 
    				url: server + 'CRUDExcepcionPrecio?idoperacion=4&idexcepcion=' + idexcepcion, 
    				dataType: 'json', 
    				async: false, 
    				success: function(data){ 
						var respuesta = data[0];
						console.log(respuesta.idexcepcion);
				        console.log("ojo" + respuesta.idtipoliquido);
				            $('#userForm')
				                .find('[name="idexcepcionedit"]').val(respuesta.idexcepcion).end()
				                .find('[name="selectProductosedit"]').val(respuesta.idproducto).end()
				                .find('[name="precioedit"]').val(respuesta.precio).end()
				                .find('[name="descripcionedit"]').val(respuesta.descripcion).end()
				                .find('[name="incluyeLiquidoedit"]').val(respuesta.incluye_liquido).end()
				                .find('[name="selectTipoLiquidoedit"]').val(respuesta.idtipoliquido).end()
				                

				            // Show the dialog
				            bootbox
				                .dialog({
				                    title: 'Editar Excepción',
				                    message: $('#userForm'),
				                    show: false // We will show it manually later
				                })
				                .on('shown.bs.modal', function() {
				                    $('#userForm')
				                        .show()                             // Show the login form
				                        .bootstrapValidator('resetForm'); // Reset form
				                })
				                .on('hide.bs.modal', function(e) {
				                    // Bootbox will remove the modal (including the body which contains the login form)
				                    // after hiding the modal
				                    // Therefor, we need to backup the form
				                    $('#userForm').hide().appendTo('body');
				                })
				                .modal('show');
					} 
		});

		//
}

function confirmarEditarExcepcion()
{
	
           
            
                var idexcepcion =  $('input:text[name=idexcepcionedit]').val();
                var precio = $('input:text[name=precioedit]').val();
                var descripcion = $('input:text[name=descripcionedit]').val();
                var idproducto =  $('#selectProductosedit').val();
                var incluye_liquido = $('#incluyeLiquidoedit').val();
                var idtipoliquido = $('#selectTipoLiquidoedit').val();
            // The url and method might be different in your application
            $.ajax({ 
    				url: server + 'CRUDExcepcionPrecio?idoperacion=2&idexcepcion='+ idexcepcion+'&precio=' + precio + "&descripcion=" + descripcion + "&idproducto=" + idproducto + "&incluye_liquido=" + incluye_liquido + "&idtipoliquido=" + idtipoliquido, 
    				dataType: 'json', 
    				async: false, 
    				success: function(data){
    					pintarExcepciones();
               			 // Hide the dialog
                		$('#userForm').parents('.bootbox').modal('hide');

                		// You can inform the user that the data is updated successfully
                		// by highlighting the row or showing a message box
                		bootbox.alert('La excepción ha sido actualizada');
    				} 
			}); 
            //
            

        

}