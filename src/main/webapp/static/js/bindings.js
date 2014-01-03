ko.bindingHandlers.bsvisible = {
    init: function(element, valueAccessor) {
        $(element).toggleClass("hide", !valueAccessor());
    },
    update: function(element, valueAccessor) {
        $(element).toggleClass("hide", !valueAccessor());
    }
};

ko.bindingHandlers.fadeVisible = {
    init: function(element, valueAccessor) {
        // Initially set the element to be instantly visible/hidden depending on the value
        var value = valueAccessor();
        $(element).toggle(ko.utils.unwrapObservable(value)); // Use "unwrapObservable" so we can handle values that may or may not be observable
    },
    update: function(element, valueAccessor) {
        // Whenever the value subsequently changes, slowly fade the element in or out
        var value = valueAccessor();
        ko.utils.unwrapObservable(value) ? $(element).fadeIn(280) : $(element).fadeOut(280);
    }
};

ko.bindingHandlers.indexedName = {
	init: function(element, valueAccessor) {
		// count elements with same prefix as valueAccessor and add one
		var index = $("[name^=" + valueAccessor() + "]").length + 1;
        $(element).attr("name", valueAccessor() + index);
    },
    update: function(element, valueAccessor) {
        
    }
};
