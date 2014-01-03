var runscope = runscope || {};

runscope.animate = {

    /**
     * scrollTo is a utility function to scroll the window to a given pixel
     * position
     * @param position The absolute position to scroll to in pixels
     * @param speed The speed to animate in milliseconds
     */
    scrollTo: function(position, speed) {
        $('html, body').animate({
            scrollTop: position
        }, speed);
    },

    /**
     * Vertically center an element in the window
     * @param element The jQuery wrapped element to vertically center
     * @param speed The speed to transition at in milliseconds
     */
    verticallyCenter: function(element, speed) {
        offset = window.isComparing ? 120 : 15;

        var destination = (element.offset().top - offset);

        this.scrollTo(destination, speed);
    }

};
