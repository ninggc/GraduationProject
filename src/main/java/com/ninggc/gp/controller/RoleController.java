package com.ninggc.gp.controller;

import com.ninggc.gp.data.Role;
import com.ninggc.gp.data.User;
import com.ninggc.gp.service.RoleService;
import com.ninggc.gp.tool.LayuiResult;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/role")
public class RoleController extends IController {
    RoleService roleService = null;

    @Override
    protected void initService(SqlSession session) throws IOException {
        super.initService(session);
        roleService = new RoleService(session);
    }

    @ResponseBody
    @RequestMapping(value = "/action/add", method = RequestMethod.POST)
    public String insert(@SessionAttribute User user, Role role) {
        if ("student".equals(user.getAddition())) {
            return new LayuiResult<>().failed("您没有权限").format();
        }

        paramPreview(role);

        LayuiResult<Role> layuiResult = operateDate(new OperateHandler<Role>() {
            @Override
            public Role onOperate() throws IOException {
                Role sample = new Role();
                sample.setProcess_id(role.getProcess_id());
                sample.setName(role.getName());

//                如果存在同一process_id下相同的角色名，则告知错误
                if (roleService.selectOne(sample) == null) {
                    String msg = sample.getProcess_id() == 0 ? "全局范围内存在相同的角色名": "当前审批下存在相同的角色名";
                    throw new IOException(msg);
                } else {
                    roleService.insert(role);
                    return role;
                }
            }
        });

        return layuiResult.format();
    }

    @ResponseBody
    @RequestMapping("/layui/select")
    public String select() {
        LayuiResult<List<Role>> layuiResult = operateDate(new OperateHandler<List<Role>>() {
            @Override
            public List<Role> onOperate() throws IOException {
                return roleService.select(new Role());
            }
        });

        return layuiResult.format();
    }

    @ResponseBody
    @RequestMapping("/layui/select/one")
    public String selectOne(@SessionAttribute User user, @RequestParam int id) {
        LayuiResult<List<Role>> layuiResult = operateDate(new OperateHandler<List<Role>>() {
            @Override
            public List<Role> onOperate() throws IOException {
                return roleService.select(new Role().setId(id));
            }
        });

        return layuiResult.format();
    }

    @ResponseBody
    @RequestMapping("/layui/select/user")
    public String selectWithUser(@SessionAttribute User user, @RequestParam String account) {
        LayuiResult<List<Role>> layuiResult = operateDate(new OperateHandler<List<Role>>() {
            @Override
            public List<Role> onOperate() throws IOException {
                return roleService.selectWithUser(account);
            }
        });

        return layuiResult.format();
    }

    @ResponseBody
    @RequestMapping("/layui/select/process")
    public String selectWithProcess(@SessionAttribute User user, @RequestParam int process_id) {
        LayuiResult<List<Role>> layuiResult = operateDate(new OperateHandler<List<Role>>() {
            @Override
            public List<Role> onOperate() throws IOException {
                return roleService.selectWithProcess(process_id);
            }
        });

        return layuiResult.format();
    }

    @ResponseBody
    @RequestMapping("/layui/delete")
    public String delete(@SessionAttribute User user, @RequestParam int id) {
        LayuiResult<Integer> layuiResult = operateDate(new OperateHandler<Integer>() {
            @Override
            public Integer onOperate() throws IOException {
                return roleService.delete(id);
            }
        });

        return layuiResult.format();
    }
}
