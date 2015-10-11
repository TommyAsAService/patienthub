$(document).ready(function() {
    // Setup - add a text input to each footer cell
    var total_columns =$('#datatable tfoot th').length;
    var column_count = 1;
    var columns_without_search = 4;
    console.log(total_columns);
    $('#datatable tfoot th').each( function () {
        var title = $('#datatable thead th').eq( $(this).index() ).text();
        if (column_count > total_columns - columns_without_search){
          $(this).html( '<input type="text" class="hidden" placeholder="Search '+title+'" />' );
        } else {
          $(this).html( '<input type="text" placeholder="Search '+title+'" />' );
        }
        column_count++;
    } );
 
    // DataTable
    var table = $('#datatable').DataTable();
 
    // Apply the search
    table.columns().every( function () {
        var that = this;
        $( 'input', this.footer() ).on( 'keyup change', function () {
            if ( that.search() !== this.value ) {
                that
                    .search( this.value )
                    .draw();
            }
        } );
    } );
} );

