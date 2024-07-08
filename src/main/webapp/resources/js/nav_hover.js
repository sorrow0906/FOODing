$(document).ready(function() {
    $(".nav-div").hover(
        function() {
            $(this).find(".snb").stop(true, true).slideDown(300);
        },
        function() {
            $(this).find(".snb").stop(true, true).slideUp(300);
        }
    );
});
