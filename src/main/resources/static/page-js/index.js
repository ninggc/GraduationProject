$("#header").load("/page/template/header.html", function () {
    $("#index").addClass("layui-this");

    layui.use(["element", "table"], function () {
        var table = layui.table;

        table.render({
            elem: '#list_process'
            , url: url+'process/layui/list'
            , cols: [[
                {field: 'name', title: '名称'}
            ]]
        });

        table.on('row(processes)', function(obj){
            // de_log_info(JSON.stringify(obj.tr));
            de_log_info(JSON.stringify(obj.data));
            location.href = "/process/apply?id=" + obj.data.id;
        });
    })
});