$(document).ready(function() {
    $(window).on('hashchange', function(hash) {
        if( location.hash == "#edit"){
            event.preventDefault();
            $(':input').prop("disabled", false);
            $( '#action').css('display','inline');
            $( "button[name='action']").css('display','inline');
            $( "a[href='#edit']").css('display','none');
            window.History.back();
        }
    });
});

function cancelForm(){
    $(':input').prop("disabled", false);
    history.back();
}