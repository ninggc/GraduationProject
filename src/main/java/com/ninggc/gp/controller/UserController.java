package com.ninggc.gp.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ninggc.gp.data.DataSample;
import com.ninggc.gp.data.Role;
import com.ninggc.gp.data.User;
import com.ninggc.gp.service.RoleHasUserService;
import com.ninggc.gp.service.UserService;
import com.ninggc.gp.tool.LayuiResult;
import com.ninggc.gp.tool.ModelPackage;
import com.ninggc.gp.util.AboutExcel.ExcelEasypoiUtil;
import com.ninggc.gp.util.AboutExcel.ExcelUser;
import com.ninggc.gp.util.ExcelUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController extends IController {
    int pageSize = 35;
    UserService userService = null;
    RoleHasUserService roleHasUserService = null;

    @Override
    public void initService(SqlSession session) throws IOException {
        userService = new UserService(session);
        roleHasUserService = new RoleHasUserService(session);
    }

    /**
     * 不支持个人注册，由管理员批量导入用户信息
     *
     * @param file 文本内获得用户信息
     * @return
     */
    @RequestMapping(value = "/register", method = {RequestMethod.POST, RequestMethod.GET})
    public String register(@ModelAttribute File file) {

        return "login";
    }

    /**
     * 返回分页用户列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public String list(@ModelAttribute User user, ModelMap map) {
        LayuiResult layuiResult = checkPrivilegeWithNotAllowed(user, "student");
        if (layuiResult != null) {
            return layuiResult.format();
        }

        int defaultIndex = 0;
        try (SqlSession session = openSession()) {
            initService(session);

            List<User> list = userService.selectWithLimit(DataSample.getStudent(), defaultIndex, pageSize);

            map.addAttribute("list", list);
            map.addAttribute("index", defaultIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "student_manage";
    }

//    @ResponseBody
//    @RequestMapping(value = "/action/nextPage", method = RequestMethod.GET)
//    public String next(@ModelAttribute ModelPackage modelPackage) {
//        Result result = initResult();
//        int index = modelPackage.getNumber() + pageSize;
//
//        try (SqlSession session = openSession()) {
//            initService(session);
//            List<User> list = userService.selectWithLimit(DataSample.getStudent(), index, pageSize);
//            result.success(toJson(list));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            result.failed(e.getMessage());
//        }
//
//        Log.debug(toJson(result));
//        return toJson(result);
//    }

    @ResponseBody
    @RequestMapping(value = "/action/update")
    public String update(@RequestBody User student) {
        paramPreview(student);

        LayuiResult<Integer> layuiResult = operateData(new OperateHandler<Integer>() {
            @Override
            public Integer onOperate() {
                return userService.update(student);
            }
        }, new LayuiResult<Integer>());

//        try(SqlSession session = openSession()) {
//            initService(session);
//            int update = userService.update(student);
//            session.commit();
//            result.success(toJson(student));
//        } catch (IOException e) {
//            e.printStackTrace();
//            result.failed(e.getMessage());
//        }

        resultPreview(layuiResult);
        return layuiResult.format();
    }

    @ResponseBody
    @RequestMapping(value = "/action/insert")
    public String insert(@SessionAttribute User user, @ModelAttribute User addUser) {
        paramPreview(addUser);

        if (addUser == null || addUser.getAccount() == null || addUser.getPass_word() == null) {
            return LayuiResult.failed("请输入账号和密码", null).format();
        }

        LayuiResult<Integer> layuiResult = operateDate(new OperateHandler<Integer>() {
            @Override
            public Integer onOperate() throws IOException {
                return userService.insert(addUser);
            }
        });

        return layuiResult.format();
    }

    @ResponseBody
    @RequestMapping(value = "/action/delete")
    public String delete(@ModelAttribute ModelPackage modelPackage) {
        LayuiResult<Integer> layuiResult = operateDate(new OperateHandler<Integer>() {
            @Override
            public Integer onOperate() throws IOException, SQLIntegrityConstraintViolationException {
                String account = modelPackage.getMsg();
                return userService.delete(account);
            }
        });

        return layuiResult.format();
    }

    @RequestMapping("/page/manage")
    public String manage() {
        return "page/user/manage.html";
    }

    @ResponseBody
    @RequestMapping("/action/list")
    public String list(@SessionAttribute User user) {
        LayuiResult checkLayuiResult = checkPrivilegeWithNotAllowed(user, "student");
        if (checkLayuiResult != null) {
            return checkLayuiResult.format();
        }

        User student = DataSample.getStudent();
        LayuiResult<List<User>> layuiResult = operateDate(new OperateHandler<List<User>>() {
            @Override
            public List<User> onOperate() throws IOException, SQLIntegrityConstraintViolationException {
                return userService.select(student);
            }
        });

        operateDate(new OperateHandler<Integer>() {
            @Override
            public Integer onOperate() throws IOException, SQLIntegrityConstraintViolationException {
                int count = userService.selectCount(student.getAddition());
                layuiResult.setCount(count);
                return count;
            }
        });

        return layuiResult.format();
    }

    @ResponseBody
    @RequestMapping("/action/list/limit")
    public String listLimit(@SessionAttribute User user, @RequestParam("page") int page, @RequestParam("limit") int size, @RequestParam(name = "addition", required = false) String addition) {
        LayuiResult checkLayuiResult = checkPrivilegeWithNotAllowed(user, "student");
        if (checkLayuiResult != null) {
            return checkLayuiResult.format();
        }

//        User sampleUser = DataSample.getStudent();
        User sampleUser = new User();
        if (addition != null && ! "".equals(addition) && "all".equals(addition)) {
            sampleUser.setAddition(addition);
        }
        LayuiResult<List<User>> layuiResult = operateDate(new OperateHandler<List<User>>() {
            @Override
            public List<User> onOperate() throws IOException, SQLIntegrityConstraintViolationException {
                return userService.selectWithLimit(sampleUser, (page - 1) * size, size);
            }
        });

        operateDate(new OperateHandler<Integer>() {
            @Override
            public Integer onOperate() throws IOException, SQLIntegrityConstraintViolationException {
                int count = userService.selectCount(sampleUser.getAddition());
                layuiResult.setCount(count);
                return count;
            }
        });

        return layuiResult.format();
    }

    @RequestMapping("/export")
    public void export(HttpServletResponse response) {
        LayuiResult<List<User>> layuiResult = operateData(new OperateHandler<List<User>>() {
            @Override
            public List<User> onOperate() {
                return userService.select(DataSample.getStudent());
            }
        }, new LayuiResult<List<User>>());

        List<User> list = layuiResult.getData();

        ExportParams params = new ExportParams();
        params.setType(ExcelType.XSSF);
        Workbook sheets = ExcelExportUtil.exportExcel(params, User.class, list);
        ExcelUtil.downloadExcel(response, sheets, "excel_export");
    }

    @RequestMapping("/test")
    public String test() {
        return "page/user/test";
    }

    @ResponseBody
    @RequestMapping("/layui/userinfo")
    public String userinfo(@SessionAttribute User user) {
        LayuiResult<User> layuiResult = operateDate(new OperateHandler<User>() {
            @Override
            public User onOperate() {
                return userService.selectOne(new User().setAccount(user.getAccount()));
            }
        });

//        过滤掉密码
        if (layuiResult.getData() != null) {
            layuiResult.getData().setPass_word("");
        }

        return layuiResult.format();
    }

    @ResponseBody
    @RequestMapping("/layui/update/password")
    public String updatePassword(@SessionAttribute User user, @RequestParam String old_password, @RequestParam String new_password) {
        if (!old_password.equals(user.getPass_word())) {
            return new LayuiResult<>().failed("密码输入错误").format();
        }

        LayuiResult<Integer> layuiResult = operateDate(new OperateHandler<Integer>() {
            @Override
            public Integer onOperate() {
                return userService.updatePassword(user.getAccount(), new_password);
            }
        });

        return layuiResult.format();
    }

    @ResponseBody
    @RequestMapping("/action/addition")
    public String addition(@SessionAttribute User user) {
        LayuiResult<String> layuiResult = operateDate(new OperateHandler<String>() {
            @Override
            public String onOperate() throws IOException {
                return user.getAddition();
            }
        });

        return layuiResult.format();
    }

    @ResponseBody
    @RequestMapping("/layui/select/by/role")
    public String selectByRole(@SessionAttribute User user, @RequestParam int role_id) {
        LayuiResult checkPrivilegeWithNotAllowed = checkPrivilegeWithNotAllowed(user, "student teacher");
        if (checkPrivilegeWithNotAllowed != null) {
            return checkPrivilegeWithNotAllowed.format();
        }

        LayuiResult<List<Map<String, Object>>> mapLayuiResult = operateDate(new OperateHandler<List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> onOperate() throws IOException, SQLIntegrityConstraintViolationException {
                return userService.selectWithRole(new Role().setId(role_id));
            }
        });

        return mapLayuiResult.format();
    }

    @ResponseBody
    @RequestMapping("/action/import")
    public String importUser(@SessionAttribute User user, @RequestParam("file")MultipartFile file, HttpServletRequest request) {
        LayuiResult checkPrivilegeWithNotAllowed = checkPrivilegeWithNotAllowed(user, "student teacher");
        if (checkPrivilegeWithNotAllowed != null) {
            return checkPrivilegeWithNotAllowed.format();
        }

        LayuiResult<Integer> layuiResult = new LayuiResult<>();
        FileController fileController = new FileController();
        try {
            String upload = fileController.upload(user, 0, file, request);

            LayuiResult<com.ninggc.gp.data.File> fileLayuiResult = gson.fromJson(upload,
                new TypeToken<LayuiResult<com.ninggc.gp.data.File>>() {}.getType());
            String location = fileLayuiResult.getData().getLocation();

            List<ExcelUser> excelUsers = new ExcelEasypoiUtil().importFromExcel(location, "");
            layuiResult = operateDate(new OperateHandler<Integer>() {
                @Override
                public Integer onOperate() throws IOException, SQLIntegrityConstraintViolationException {
                    return userService.insertListFromExcel(excelUsers);
                }
            });
        } catch (FileNotFoundException e) {
            layuiResult.failed("文件不存在！");
        } catch (JsonSyntaxException e) {
            layuiResult.failed("文件上传异常！");
        } catch (NullPointerException e) {
            layuiResult.failed("解析文件地址失败！");
        }

        return layuiResult.format();
    }

    @ResponseBody
    @RequestMapping("/action/export")
    public void exportUser(@SessionAttribute User user, HttpServletResponse response) {
        LayuiResult checkPrivilegeWithNotAllowed = checkPrivilegeWithNotAllowed(user, "student teacher");
        if (checkPrivilegeWithNotAllowed != null) {
            return;
        }

        List<User> list = operateDate(new OperateHandler<List<User>>() {
            @Override
            public List<User> onOperate() throws IOException {
                return userService.select(new User());
            }
        }).getData();

        File file = new ExcelEasypoiUtil().exportFile(list);
        LayuiResult<String> layuiResult = new LayuiResult<>();

        try (InputStream inputStream = new FileInputStream(file);
             OutputStream outputStream = response.getOutputStream();) {

            //设置内容类型为下载类型
            response.setContentType("application/x-download");
            //设置请求头 和 文件下载名称
            response.addHeader("Content-Disposition", "attachment;filename=" + "all_user_info.xls");
            //用 common-io 工具 将输入流拷贝到输出流
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            layuiResult.failed("文件不存在！");
        } catch (IOException e) {
            e.printStackTrace();
            layuiResult.failed("IO异常！");
        }
    }

//    #################################
//
    @ResponseBody
    @RequestMapping("/layui/rhu/delete")
    public String deleteRoleHasUser(@SessionAttribute User user, @RequestParam(name = "role_id", required = false) int role_id, @RequestParam(name = "user_account", required = false) String user_account) {
        LayuiResult checkPrivilegeWithNotAllowed = checkPrivilegeWithNotAllowed(user, "student teacher");
        if (checkPrivilegeWithNotAllowed != null) {
            return checkPrivilegeWithNotAllowed.format();
        }

        LayuiResult<Integer> layuiResult = operateDate(new OperateHandler<Integer>() {
            @Override
            public Integer onOperate() throws IOException, SQLIntegrityConstraintViolationException {
                return roleHasUserService.delete(role_id, user_account);
            }
        });

        return layuiResult.format();
    }

    @ResponseBody
    @RequestMapping("/layui/rhu/select")
    public String selectRoleHasUser(@SessionAttribute User user, @RequestParam(name = "role_id", required = false) int role_id, @RequestParam(name = "user_account", required = false) String user_account) {
        LayuiResult checkPrivilegeWithNotAllowed = checkPrivilegeWithNotAllowed(user, "student teacher");
        if (checkPrivilegeWithNotAllowed != null) {
            return checkPrivilegeWithNotAllowed.format();
        }

        LayuiResult<List<Map<String, Object>>> layuiResult = operateDate(new OperateHandler<List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> onOperate() throws IOException, SQLIntegrityConstraintViolationException {
                return roleHasUserService.select(role_id, user_account);
            }
        });

        layuiResult.setCount(layuiResult.getData().size());
        return layuiResult.format();
    }

   @ResponseBody
    @RequestMapping("/layui/rhu/insert")
    public String insertRoleHasUser(@SessionAttribute User user, @RequestParam(name = "role_id") int role_id, @RequestParam(name = "user_account") String user_account) {
       LayuiResult checkPrivilegeWithNotAllowed = checkPrivilegeWithNotAllowed(user, "student teacher");
       if (checkPrivilegeWithNotAllowed != null) {
           return checkPrivilegeWithNotAllowed.format();
       }

       LayuiResult<Integer> layuiResult = operateDate(new OperateHandler<Integer>() {
            @Override
            public Integer onOperate() throws IOException, SQLIntegrityConstraintViolationException {
                return roleHasUserService.insert(role_id, user_account);
            }
        });

        return layuiResult.format();
    }
}
