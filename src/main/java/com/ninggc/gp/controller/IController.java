package com.ninggc.gp.controller;

import com.google.gson.Gson;
import com.ninggc.gp.data.User;
import com.ninggc.gp.mybatis.Factory;
import com.ninggc.gp.tool.LayuiResult;
import com.ninggc.gp.tool.Result;
import com.ninggc.gp.util.Constant;
import com.ninggc.gp.util.Log;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.lang.reflect.Type;

public abstract class IController {

    public static int CODE_SUCCESS = 200;
    public static int CODE_FAILED = 400;

    static Gson gson = Constant.gson;

//    protected Map<String, String> initResult() {
//        return new HashMap<>();
//    }

    protected SqlSession openSession() throws IOException {
        return Factory.openSession();
    }

    protected void initService(SqlSession session) throws IOException {
//        如果Controller有多个Service，可以在这里同意初始化
    };

    String getDebugLocation() {
        return this.getClass().getSimpleName() + ": ";
    }

    protected String toJson(Object o) {
        return gson.toJson(o);
    }

    @Deprecated
    protected Result initResult() {
        return new Result();
    }

    /**
     * 查看参数
     * @param o param
     */
    void paramPreview(Object o) {
        paramPreview(o, null);
    }

    void paramPreview(Object o, Object o1) {
        Log.hint("------参数信息预览------");
        Log.info(toJson(o));
        Log.info(toJson(o1));
        Log.hint("------预览结束------");
    }

    /**
     * 查看结果
     * @param o result
     */
    void resultPreview(Object o) {
        Log.hint("------结果预览------");
        Log.info(toJson(o));
        Log.hint("------预览结束------");
    }

    /**
     * 必须重写initService并在里面初始化service
     * @param handler 具体执行
     * @return
     */
//    LayuiResult operateData(OperateHandler handler) {
//        return operateData(handler, null);
//    }
    
//    Result operateData(OperateHandler handler, Type type) {
//        Result result = initResult();
//
//        try(SqlSession session = openSession()) {
//            initService(session);
//            Object o;
//            if(type == null) {
//                o = handler.onOperate();
//            } else {
//                o = handler.onOperate(type);
//            }
//            session.commit();
//            result.success(toJson(o));
////            handler.onSuccess();
//        } catch (IOException e) {
//            e.printStackTrace();
//            result.failed(e.getMessage());
////            handler.onFailed();
//        }
//        resultPreview("in operateData: " + result);
//        return result;
//    }

    /**
     * 必须重写initService并在里面初始化service
     * @param handler 具体执行
     * @return
     */
    protected <T> LayuiResult<T> operateDate(OperateHandler<T> handler) {
        return operateData(handler, new LayuiResult<T>());
    }

//    其实并不需要传递layuiResult，也可以得到需要的类型
    @Deprecated()
    <T> LayuiResult<T> operateData(OperateHandler<T> handler, LayuiResult<T> layuiResult) {
//        Result result = initResult();

        try(SqlSession session = openSession()) {
            initService(session);
            T t = handler.onOperate();
            session.commit();
            layuiResult.success(0, t);
        } catch (IOException e) {
            e.printStackTrace();
            layuiResult.failed(e.getMessage());
        } catch (RuntimeException e) {
            e.printStackTrace();
            layuiResult.failed(e.getMessage());
        }
        resultPreview("in operateData: " + layuiResult);
        return layuiResult;
    }

    /**
     * 在operateData中执行和操作结果
     * @param <T> 操作的结果
     */
    protected interface OperateHandler<T> {
        T onOperate() throws IOException;
//        T onSuccess();
//        T onFailed();
    }

    protected LayuiResult checkPrivilegeWithNotAllowed(User user, String notAllowed)  {
        LayuiResult<Object> layuiResult = new LayuiResult<>();

        String[] arr = notAllowed.split(" ");
        for (String s: arr) {
            if (user.getAddition().equals(s)) {
                layuiResult.failed("您没有权限");
                return layuiResult;
            }
        }

        return null;
    }

    class PrivilegeException extends RuntimeException {

        public PrivilegeException() {
            super();
        }

        public PrivilegeException(String msg) {
            super(msg);
        }

//        public static final String STUDENT = "student";
//        public static final String TEACHER = "teacher";
//        public static final String MANAGER = "manager";
    }

}
