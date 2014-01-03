		validator.message['date'] = 'not a real date';
		$('form').on('blur',
				'input[required], input.optional, select.required',
				validator.checkField).on('change', 'select.required',
				validator.checkField).on('keypress',
				'input[required][pattern]', validator.keypress);

		$('.multi.required').on('keyup blur', 'input', function() {
			validator.checkField.apply($(this).siblings().last()[0]);
		});
		//------
		$(function() {
			$("#textid").hide();
			$("#button").button().click(function(event) {
				//event.preventDefault();
				$("#textid").show();
			});
		});
		//-----------------$$$$$$$$$------------
		$(function() {
			var scntDiv = $('#p_scents');
			var i = $('#p_scents p').size() + 1;

			$('#addScnt').on('click',function() {
			    $('<p class="inputLabel"><input type="text" size="20" " value="" placeholder="Input Value" /><span class="removeLabel">Remove</span></p>').appendTo(scntDiv);
				$('.removeLabel').click(function(event){
					this.parentNode.remove();
					console.log('Remove');
				});
			});
	
		});


		$('form').submit(function(e) {
			e.preventDefault();
			var submit = true;
			if (!validator.checkAll($(this))) {
				submit = false;
			}

			if (submit)
				this.submit();
			return false;
		});

		$('#vfields').change(function() {
			$('form').toggleClass('mode2');
		}).prop('checked', false);

		$('#alerts').change(function() {
			validator.defaults.alerts = (this.checked) ? false : true;
			if (this.checked)
				$('form .alert').remove();
		}).prop('checked', false);