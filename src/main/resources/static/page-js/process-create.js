
function atStart() {
    $("#create_process").removeClass("d-none");
    $("#unit_list").addClass("d-none");
    $("#create_unit").addClass("d-none");
}

function addStage() {
    de_log('show process: ' + process);
    if (process === "") {
        alert('请先创建审批');
    } else {
        let process_id = process.id;
        $.post(url + '/process/action/addStage', {"process_id": process_id}, function (json) {
            let stage = jQuery.parseJSON(json);
            de_log(stage);

            let div_stage = $("#div_stage").clone();
            div_stage.removeClass("d-none");
            $(div_stage).children("button").text(stage.id);

            $("#div_stage_list").append(div_stage);
        })
    }

}

function finish() {
    $.post('', function (msg) {
        de_log(msg);
    })
}

function showUnitList() {
    $("#create_process").addClass("d-none");
    $("#unit_list").removeClass("d-none");
    // let $create_unit = $("#create_unit");
    // $create_unit.removeClass("d-none");
}

function showUnit() {
    let $create_unit = $("#create_unit");
    $create_unit.removeClass("d-none");
}

var process = "";

function action_create() {
    let name = $('#process_name').val();
    let description = $('#process_description').val();
    de_log(name + description);
    $.post(url + '/process/action/create', {'name':name, 'description':description}, function(msg) {
        de_log(msg);
        let json = jQuery.parseJSON(msg);
        de_log(json.name + json.id);
        process = json;

        $("#div_stage_list").removeClass("d-none");
        alert("保存成功");
    });
}

function action_add_unit() {
    let name = $('#unit_name').val();
    let description = $('#unit_description').val();
    de_log(name + description);
    $.post(url + '/process/action/addUnit', {'name':name, 'description':description}, function(msg) {
        de_log(msg);
        let json = jQuery.parseJSON(msg);
        de_log(json.name + json.id);
        process = json;
    });
}
