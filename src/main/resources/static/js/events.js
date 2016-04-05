function initDateTimePicker() {
    $('.date-picker').datetimepicker({
        timepicker:false,
        format: 'Y-m-d'
    });
    $('.time-picker').datetimepicker({
        datepicker:false,
        format:'H:i'
    });
}

