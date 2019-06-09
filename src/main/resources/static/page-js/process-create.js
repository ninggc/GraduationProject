
function atStart() {
    $("#create_process").removeClass("d-none");
    $("#unit_list").addClass("d-none");
    $("#create_unit").addClass("d-none");
}

//弹出添加窗口，检验数据，请求url，显示结果反馈
function addStage() {
    if ((!process) || process === '') {
        layer.open({
            title: '错误'
            , content: '如果你想添加阶段，请先创建审批'
        });
        return;
    }

    let pop = $("#pop_sample_stage");
    pop.removeClass('layui-hide');
    layer.open({
        type: 1
        , title: '添加阶段点'
        , content: pop
        , btn: ['添加', '取消']
        , yes: function (index) {
            // 处理输入的role信息
            let stage = {};
            stage.name = pop.find("#input_stage_name").val();
            stage.description = pop.find("#input_stage_description").val();

            if (stage.name === '') {
                layer.msg('请至少输入阶段名称');
            } else {
                de_log_info('show process: ' + process);

                let process_id = process.id;

                stage.process_id = process.id;
                $layui_post(url + '/process/action/addStage', stage, function success(json) {
                    layer.open({
                        title: '成功'
                        , content: '添加阶段成功：\n' + JSON.stringify(json)
                    });
                    add_timeline(json.data.id, json.data.name);
                    layer.close(index);
                    // let stage = jQuery.parseJSON(json);
                    // de_log(stage);

                    // let div_stage = $("#div_stage").clone();
                    // div_stage.removeClass("d-none");
                    // $(div_stage).children("button").text(stage.id);

                    // $("#div_stage_list").append(div_stage);
                })

            }
        }, btn2: function () {
            layer.msg('取消');
            // ${url+'', role, function(){}}
        }
        , end: function () {
            pop.addClass("layui-hide");
        }
    });

}

//弹出添加窗口，检验数据，请求url，显示结果反馈
function add_unit(stage_id) {
    if ((!process) || process === '') {
        layer.open({
            title: '错误'
            , content: '如果你想添加阶段，请先创建审批'
        });
        return;
    }

    let pop = $("#sample_unit");
    pop.removeClass('layui-hide');
    layer.open({
        type: 1
        , title: '添加单元'
        , content: pop
        , btn: ['添加', '取消']
        , yes: function (index) {
            // 处理输入的role信息
            let unit = {};
            unit.name = pop.find("#input_unit_name").val();
            unit.description = pop.find("#input_unit_description").val();

            if (unit.name === '') {
                layer.msg('请至少输入单元名称');
            } else {
                de_log_info('show process: ' + process);

                process_id = process.id;

                unit.process_id = process.id;
                unit.stage_id = stage_id;

                $layui_post(url + '/process/action/addUnit', unit, function success(json) {
                    layer.open({
                        title: '成功'
                        , content: '添加单元成功：\n' + JSON.stringify(json)
                    });
                    layer.close(index);
                })

            }
        }, btn2: function () {
            layer.msg('取消');
            // ${url+'', role, function(){}}
        }
        , end: function () {
            pop.addClass("layui-hide");
        }
    });

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
    let msg = JSON.stringify($('#process_msg').val().split(' '));
    let files = JSON.stringify($('#process_files').val().split(' '));

    de_log(name + description);
    $.post(url + '/process/action/create', {
        'name': name
        , 'description': description
        , 'msg': msg
        , 'files': files
    }, function (msg) {
        de_log(msg);
        let json = jQuery.parseJSON(msg);
        de_log(json.name + json.id);
        process = json;
        process_id = json.id;

        $("#div_stage_list").removeClass("d-none");
        alert("保存成功");
    });
}

function update_process() {
    let update = process;
    update.name = $('#process_name').val();
    update.description = $('#process_description').val();
    update.msg = JSON.stringify($('#process_msg').val().split(' '));
    update.files = JSON.stringify($('#process_files').val().split(' '));

    if (process === '' ) {
        layer.msg('当前未载入审批，请先创建');
        return;
    } else if (update.name === '') {
        layer.msg('审批名称不能为空');
        return;
    }

    de_log_info(update);

    $.post(url + '/process/action/update', update, function (msg) {
        let json = jQuery.parseJSON(msg);

        process_id = json.id;
        // $("#div_stage_list").removeClass("d-none");
        layer.open({
           title: '结果'
           , content: '更新成功'
        });
        de_log_receive(msg);
    });
}

function finish_all() {
    layer.open({
        title: '警告'
        , content: '确定结束？'
        , yes: function () {
            window.location.href = url;
        }
    });
}

function action_add_unit() {
    let name = $('#unit_name').val();
    let description = $('#unit_description').val();
    de_log(name + description);
    $.post(url + '/process/action/addUnit', { 'name': name, 'description': description }, function (msg) {
        de_log(msg);
        let json = jQuery.parseJSON(msg);
        de_log(json.name + json.id);
        process = json;
    });
}
