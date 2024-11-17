$(function(){

// User Register validation

	var $userRegister=$("#userRegister");

	$userRegister.validate({
		
		rules:{
			name:{
				required:true,
				lettersonly:true
			}
			,
			email: {
				required: true,
				space: true,
				email: true
			},
			mobileNumber: {
				required: true,
				space: true,
				numericOnly: true,
				minlength: 9,
				maxlength: 9

			},
			password: {
				required: true,
				space: true

			},
			confirmpassword: {
				required: true,
				space: true,
				equalTo: '#pass'

			},
			address: {
				required: true,
				all: true

			},

			city: {
				required: true,
				space: true

			},
			state: {
				required: true,


			},
			pincode: {
				required: true,
				space: true,
				numericOnly: true

			}, img: {
				required: true,
			}
			
		},
		messages:{
		    name:{
		        required: 'nombre requerido',
		        lettersonly: 'nombre inválido'
		    },
		    email: {
		        required: 'el nombre de correo electrónico debe ser obligatorio',
		        space: 'espacios no permitidos',
		        email: 'Correo electrónico inválido'
		    },
		    mobileNumber: {
		        required: 'el número de móvil debe ser obligatorio',
		        space: 'espacios no permitidos',
		        numericOnly: 'número de móvil inválido',
		        minlength: 'mínimo 9 dígitos',
		        maxlength: 'máximo 9 dígitos'
		    },

		    password: {
		        required: 'la contraseña debe ser obligatoria',
		        space: 'espacios no permitidos'
		    },
		    confirmpassword: {
		        required: 'la confirmación de la contraseña debe ser obligatoria',
		        space: 'espacios no permitidos',
		        equalTo: 'las contraseñas no coinciden'
		    },
		    address: {
		        required: 'la dirección debe ser obligatoria',
		        all: 'inválido'
		    },

		    city: {
		        required: 'la ciudad debe ser obligatoria',
		        space: 'espacios no permitidos'
		    },
		    state: {
		        required: 'el estado debe ser obligatorio',
		        space: 'espacios no permitidos'
		    },
		    pincode: {
		        required: 'el código postal debe ser obligatorio',
		        space: 'espacios no permitidos',
		        numericOnly: 'código postal inválido'
		    },
		    img: {
		        required: 'imagen obligatoria',
		    }
		}
	})
	
	
// Orders Validation

var $orders=$("#orders");

$orders.validate({
		rules:{
			firstName:{
				required:true,
				lettersonly:true
			},
			lastName:{
				required:true,
				lettersonly:true
			}
			,
			email: {
				required: true,
				space: true,
				email: true
			},
			mobileNo: {
				required: true,
				space: true,
				numericOnly: true,
				minlength: 10,
				maxlength: 12

			},
			address: {
				required: true,
				all: true

			},

			city: {
				required: true,
				space: true

			},
			state: {
				required: true,


			},
			pincode: {
				required: true,
				space: true,
				numericOnly: true

			},
			paymentType:{
			required: true
			}
		},
		messages:{
		    firstName:{
		        required: 'primer nombre obligatorio',
		        lettersonly: 'nombre inválido'
		    },
		    lastName:{
		        required: 'apellido obligatorio',
		        lettersonly: 'apellido inválido'
		    },
		    email: {
		        required: 'el nombre de correo electrónico debe ser obligatorio',
		        space: 'espacios no permitidos',
		        email: 'Correo electrónico inválido'
		    },
		    mobileNo: {
		        required: 'el número de móvil debe ser obligatorio',
		        space: 'espacios no permitidos',
		        numericOnly: 'número de móvil inválido',
		        minlength: 'mínimo 9 dígitos',
		        maxlength: 'máximo 9 dígitos'
		    },
		    address: {
		        required: 'la dirección debe ser obligatoria',
		        all: 'inválido'
		    },
		    city: {
		        required: 'la ciudad debe ser obligatoria',
		        space: 'espacios no permitidos'
		    },
		    state: {
		        required: 'el estado debe ser obligatorio',
		        space: 'espacios no permitidos'
		    },
		    pincode: {
		        required: 'el código postal debe ser obligatorio',
		        space: 'espacios no permitidos',
		        numericOnly: 'código postal inválido'
		    },
		    paymentType:{
		        required: 'seleccionar tipo de pago'
		    }
		}
})

// Reset Password Validation

var $resetPassword=$("#resetPassword");

$resetPassword.validate({
		
		rules:{
			password: {
				required: true,
				space: true

			},
			confirmPassword: {
				required: true,
				space: true,
				equalTo: '#pass'

			}
		},
		messages:{
		    password: {
		        required: 'la contraseña debe ser obligatoria',
		        space: 'espacios no permitidos'
		    },
		    confirmpassword: {
		        required: 'la confirmación de la contraseña debe ser obligatoria',
		        space: 'espacios no permitidos',
		        equalTo: 'las contraseñas no coinciden'
		    }
		}
})



	
	
	
	
})



jQuery.validator.addMethod('lettersonly', function(value, element) {
		return /^[^-\s][a-zA-Z_\s-]+$/.test(value);
	});
	
		jQuery.validator.addMethod('space', function(value, element) {
		return /^[^-\s]+$/.test(value);
	});

	jQuery.validator.addMethod('all', function(value, element) {
		return /^[^-\s][a-zA-Z0-9_,.\s-]+$/.test(value);
	});


	jQuery.validator.addMethod('numericOnly', function(value, element) {
		return /^[0-9]+$/.test(value);
	});
