const url = 'http://localhost:8080/';

function initCheck() {
    checkAddition();
}

function checkAddition() {
    $("#header").addClass('layui-hide');
    let addition = '';
// $('#index').remove();
    $.post(url+'user/action/addition', {}, function (result) {
        let json = jQuery.parseJSON(result);
        addition = json.data;

        if (addition === 'student') {
            // $('#personal').remove();
            // $('#progress').remove();
            $('#create').remove();
            $('#notify').remove();
            $('#manage').remove();
            // $('#personal_info').remove();
            // $('#personal_history').remove();
            $('#role_manage').remove();
            $('#personal_manage').remove();
        } else if (addition === 'teacher') {
            $('#manage').remove();
            $('#personal_manage').remove();
        }

        $("#header").removeClass('layui-hide');
    });
}

function $layui_post(full_url, map, success, exception, after) {
    $.post(full_url, map, function (result) {
        de_log_send(JSON.stringify(map));
        de_log_receive(result);

        let json = jQuery.parseJSON(result);
        if (!json) {
            layer.open({
                title: '服务器异常'
                , content: json.msg
            });
        } else if (json.code === 0) {
            if (success) {
                success(json);
            }
        } else if (json.code === 1 || json.code !== 0) {
            if (exception) {
                exception(json);
            } else {
                layer.open({
                    title: '逻辑异常'
                    , content: json.msg
                });
            }
        }

        if (after) {
            after();
        }
    })
}

// 初始化每个时间线节点的点击事件
function init_timeline() {
    // $(".layui-timeline-item").find("li").click(click_timeline);
}

function click_timeline(timeline_id) {
    let id = timeline_id;
    // alert(id);
    layui.use('table', function () {
        let table = layui.table;

        // unit list
        table.render({
            elem: '#list_unit' + id
            , url: url + 'process/layui/list/unit' //数据接口
            , where: {"stage_id": id, "process_id": process_id}
            // , page: true //开启分页
            , cols: [[ //表头
                { field: 'id', title: 'id', width: 80, sort: true, hide: true }
                , { field: 'name', title: '名称', width: 80, sort: true }
                , { field: 'pass', title: '审核状态', width: 160 }
                , { field: 'description', title: '单元描述' }
            ]]
            , done: function (res, page, count) {
                $("[data-field='pass']").children().each(function () {
                    if ($(this).text() === '0') {
                        $(this).text('等待审核');
                    } else if($(this).text() === '1') {
                        $(this).text('通过审核');
                    } else if($(this).text() === '2') {
                        $(this).text('不通过审核');
                    }
                })
            }
        });

        table.on('row(unit)', function (obj) {
            let data = obj.data;
            if (data.pass === 0) {
                layer.msg('未审核');
            } else if (data.pass === 1) {
                layer.msg('审核通过');
            } else if (data.pass === 2) {
                layer.open({
                    title: '不通过审核'
                    , content: '驳回原因：' + data.progress_description
                    , btn: ['修改申请', '撤销申请', '知道了']
                    , yes: function (index) {
                        window.location.href = url + "process/apply?id=" + process_id;
                    }, btn2: function (index) {
                        if (progress_id === -1) {
                            layer.msg('进度编号初始化失败，请刷新');
                        } else {
                            layer.close(index);
                            layer.open({
                                title: '撤销申请'
                                , content: '确定要撤销吗？这将删除您的进度！'
                                , yes: function (index) {
                                    $layui_post(url + 'progress/layui/delete', {
                                        'progress_id': progress_id
                                    }, function success(result) {
                                        layer.msg('申请已撤销');
                                        layer.close(index);
                                    })
                                }
                            });
                        }
                    }, btn3: function (index) {
                        layer.close(index);
                    }
                })
            }
        })
    });
};

var stage_size = 0;

/**
 *
 * @param id stage_id
 * @param name stage_name
 */
function add_timeline(id, name) {
    let clone = $("#sample_li_timeline").clone(true).attr("id", id);
    clone.find('table').attr('id', 'list_unit' + id);
    clone.find('table').attr('lay-filter', 'unit');
    clone.find("h3:first").text(name);

    // alert(clone.find("table:first").attr("id", id));
    $("#ul_timeline").append(clone);
}