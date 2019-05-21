package com.ninggc.gp.controller;

import com.google.gson.Gson;
import com.ninggc.gp.mybatis.Factory;
import com.ninggc.gp.tool.Result;
import com.ninggc.gp.tool.YanuiResult;
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

    protected String getDebugLocation() {
        return this.getClass().getSimpleName() + ": ";
    }

    protected String toJson(Object o) {
        return gson.toJson(o);
    }

    protected Result initResult() {
        return new Result();
    }

    /**
     * 查看参数
     * @param o param
     */
    protected void paramPreview(Object o) {
        paramPreview(o, null);
    }

    protected void paramPreview(Object o, Object o1) {
        Log.hint("------参数信息预览------");
        Log.info(toJson(o));
        Log.info(toJson(o1));
        Log.hint("------预览结束------");
    }

    /**
     * 查看结果
     * @param o result
     */
    protected void resultPreview(Object o) {
        Log.hint("------结果预览------");
        Log.info(toJson(o));
        Log.hint("------预览结束------");
    }

    /**
     * 必须重写initService并在里面初始化service
     * @param handler 具体执行
     * @return
     */
    Result operateData(OperateHandler handler) {
        return operateData(handler, null);
    }
    
    /**
     * 必须重写initService并在里面初始化service
     * @param handler 具体执行
     * @param type 如果结果不是list，要传入type
     * @return
     */
    Result operateData(OperateHandler handler, Type type) {
        Result result = initResult();

        try(SqlSession session = openSession()) {
            initService(session);
            Object o;
            if(type == null) {
                o = handler.onOperate();
            } else {
                o = handler.onOperate(type);
            }
            session.commit();
            result.success(toJson(o));
//            handler.onSuccess();
        } catch (IOException e) {
            e.printStackTrace();
            result.failed(e.getMessage());
//            handler.onFailed();
        }
        resultPreview("in operateData: " + result);
        return result;
    }

//    protected <V> YanuiResult<V> resultToLayuiResult(Result result) {
//
//    }

    /**
     * 在operateData中执行和操作结果
     * @param <T> 操作的结果
     */
    interface OperateHandler<T> {
        default T onOperate(){return null;};
        default T onOperate(Type type){return null;};
//        T onSuccess();
//        T onFailed();
    }
}
