package com.ninggc.gp.util;

import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @author Zero
 * @date 2018/11/9
 */
public class ExcelUtil {

    /**
     * 下载通用配置
     *
     * @param response  HttpServletResponse
     * @param workbook  Workbook
     * @param excelName excelName
     */
    public static void downloadExcel(HttpServletResponse response, Workbook workbook, String excelName) {
        try (OutputStream os = response.getOutputStream()) {

            response.reset();
            if (excelName == null) {
                excelName = UUID.randomUUID().toString();
            }
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "inline; filename=" + new String(excelName.getBytes("gb18030"), "ISO8859-1") + ".xlsx");
            workbook.write(os);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
